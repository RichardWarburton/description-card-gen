package description.words.impl;

import static description.words.RelationshipType.SYNONYM;
import static description.words.WordType.noun;
import static description.words.WordType.verb;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.inject.Inject;

import description.GuiceTestUtil;
import description.SettingsModule;
import description.words.Word;

/**
 * 
 * NB: makes calls to public api so should be enabled as part of test suite
 * 
 * @author rw
 *
 */
public class RunAPITest extends GuiceTestUtil {

	@Inject
	PublicApiImplementation test;
	
	public RunAPITest() {
		super(new SettingsModule(), new PublicApiModule());
	}
	
	@Test
	public void getLove() {
		Word word2 = new Word("roll in the hay", verb, SYNONYM);
		Word word1 = new Word("passion", noun, SYNONYM);
		
		List<Word> words = test.getWords("love");
		assertTrue(words.contains(word1));
		assertTrue(words.contains(word2));
	}
	
}
