package description.words.impl;

import static description.words.RelationshipType.SYNONYM;
import static description.words.WordType.noun;
import static description.words.WordType.verb;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import description.words.Word;

public class TestPublicApiImplementation {

	private static final Word word2 = new Word("roll in the hay", verb, SYNONYM);
	private static final Word word1 = new Word("passion", noun, SYNONYM);
	private final PublicApiImplementation test = new PublicApiImplementation();
	
	@Test
	public void parseWord() {
		assertEquals(word1, test.parseWord("noun|syn|passion"));
		assertEquals(word2, test.parseWord("verb|syn|roll in the hay"));
	}
	
	@Test
	public void parseWords() throws Exception {
		List<Word> words = test.parseWords(new URL("file://"+System.getProperty("user.dir")+"/src/test/resources/api-example.txt"));
		assertTrue(words.contains(word1));
		assertTrue(words.contains(word2));
		assertEquals(55,words.size());
	}

}
