package description;

import static com.google.common.collect.Iterables.concat;
import static java.util.Collections.singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map.Entry;

import com.google.common.base.Joiner;
import com.google.common.collect.Multimap;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import description.titles.Titles;

public class DescriptionPrinter {

	public static void main(String[] args) {
		if(args.length != 2) {
			System.err.println("Usage: DescriptionPrinter <number of words> <output file>");
			System.exit(-1);
		}
		
		int max = Integer.parseInt(args[0]);
		String location = args[1];
		Injector injector = Guice.createInjector(new SettingsModule());
		DescriptionPrinter printer = injector.getInstance(DescriptionPrinter.class);
		printer.print(max,location);
	}

	@Inject
	Titles titleExtractor;

	public void print(int max, String location) {
		try (PrintWriter out = new PrintWriter(new FileWriter(location))) {
			Multimap<String, String> titles = titleExtractor.getTitles(max);
			Joiner joiner = Joiner.on(", ");
			for(Entry<String, Collection<String>> e:titles.asMap().entrySet()) {
				Iterable<String> lineElements = concat(singleton(e.getKey()), e.getValue());
				out.println(joiner.join(lineElements));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
