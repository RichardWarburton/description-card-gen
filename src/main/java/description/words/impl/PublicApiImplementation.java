package description.words.impl;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.io.Resources.readLines;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import description.words.ParseException;
import description.words.RelationshipType;
import description.words.UnknownAPIKeyException;
import description.words.Word;
import description.words.WordType;
import description.words.WordsAPI;

class PublicApiImplementation implements WordsAPI {

	@Inject
	@Named("bht_api_key")
	private String apiKey;
	
	@Override
	public List<Word> getWords(String forWord) {
		if(apiKey == null)
			throw new UnknownAPIKeyException();
		
		try {
			return parseWords(new URL("http://words.bighugelabs.com/api/2/"+apiKey+"/"+forWord+"/"));
		} catch (MalformedURLException e) {
			// don't really expect this to happen
			throw new RuntimeException(e);
		}
	}
	
	List<Word> parseWords(URL url)  {
		try {
			List<String> urlAsLines = readLines(url, Charset.defaultCharset());
			// this#parseWord (wtb java 8)
			return newArrayList(transform(urlAsLines, new Function<String,Word>() {
				@Override
				public Word apply(String input) {
					return parseWord(input);
				}
			}));
		} catch (IOException e) {
			throw new ParseException(e);
		}
	}
	
	Word parseWord(String from) {
		String[] split = from.split("\\|");
//		System.out.println(Arrays.toString(split));
		if(split.length != 3)
			throw new ParseException();
		return new Word(split[2], WordType.valueOf(split[0]), RelationshipType.fromAPI(split[1]));
	}

}
