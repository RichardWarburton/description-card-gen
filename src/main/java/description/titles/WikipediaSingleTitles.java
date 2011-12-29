package description.titles;

import static com.google.common.collect.Sets.intersection;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import description.words.ParseException;

public class WikipediaSingleTitles extends DefaultHandler implements Titles {

	class FakeSAXException extends SAXException {

		private static final long serialVersionUID = -1431553472239626066L;

		@Override
		public Throwable fillInStackTrace() {
			return this;
		}

	}

	@Inject
	@Named("wikipedia_file")
	private String wikipediaFile;

	private final Set<String> bannedCategories = Sets.newHashSet(
			"Requests for audio pronunciation (English)", "Chemical elements",
			"Cerebral palsy types",
			"Articles with inconsistent citation formats");

	private int max;

	private final Pattern categoryName = Pattern
			.compile("\\[\\[Category:(.*?)\\]\\]");

	@Override
	public void startDocument() throws SAXException {
		buffer = HashMultimap.create();
	}

	private Multimap<String, String> buffer;
	private String within;
	private String title;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		within = localName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		within = null;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (within != null) {
			switch (within) {

			case "title":
				title = new String(ch, start, length);
				return;

			case "text":
				if (title.length() <= 3 || containsWhitespace(title)
						|| containsUppercase(title) || tooManyVowels(title))
					return;

				final String text = new String(ch, start, length);
				Set<String> categories = Sets.newHashSet();
				Matcher m = categoryName.matcher(text);
				while (m.find()) {
					String category = m.group(1);
					if (category.endsWith("| ") || category.endsWith("|*"))
						category = category.substring(0, category.length() - 2);
					categories.add(category);
				}
				if (intersection(bannedCategories, categories).isEmpty()) {
					buffer.putAll(title, categories);
					checkSize();
				}
			}
		}
	}

	private void checkSize() throws FakeSAXException {
		if (buffer.size() >= max) {
			Iterator<Entry<String, Collection<String>>> it = buffer.asMap()
					.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Collection<String>> e = it.next();
				if (e.getValue().size() < 5)
					it.remove();
			}
			if (buffer.keySet().size() >= max) {
				throw new FakeSAXException();
			}
		}
	}

	// NB: consecutive vowels
	private boolean tooManyVowels(String s) {
		final int maxVowels = 3;
		int vowels = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
				vowels++;
			else
				vowels = 0;
			if (vowels >= maxVowels)
				return true;
		}
		return false;
	}

	private boolean containsWhitespace(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isWhitespace(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	private boolean containsUppercase(String s) {
		// NB: ignores first letter
		for (int i = 1; i < s.length(); i++) {
			if (Character.isUpperCase(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Multimap<String, String> getTitles(int max) {
		try {
			this.max = max;
			XMLReader xr = XMLReaderFactory.createXMLReader();
			xr.setContentHandler(this);
			xr.parse(new InputSource(new FileReader(wikipediaFile)));
			return buffer;
		} catch (FakeSAXException e) {
			return buffer;
		} catch (SAXException | IOException e) {
			throw new ParseException(e);
		}
	}

}
