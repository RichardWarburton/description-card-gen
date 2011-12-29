package description.titles;

import java.util.Map.Entry;

import org.junit.Test;

import com.google.inject.Inject;

import description.GuiceTestUtil;
import description.SettingsModule;

public class TestWikipediaTitles extends GuiceTestUtil {

	@Inject
	WikipediaSingleTitles test;
	
	public TestWikipediaTitles() {
		super(new SettingsModule());
	}

	@Test
	public void parseOfficialFile() {
		for(Entry<String, String> title:test.getTitles(5000).entries()) {
			System.out.println(title);
		}
	}
	
}
