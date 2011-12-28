package description.words.impl;

import java.util.List;

import com.google.inject.AbstractModule;

import description.words.Word;
import description.words.WordsAPI;

public class StubApiModule extends AbstractModule {

	private final List<Word> words;
	
	public StubApiModule(List<Word> words) {
		super();
		this.words = words;
	}

	@Override
	protected void configure() {
		bind(WordsAPI.class).toInstance(new WordsAPI() {
			@Override
			public List<Word> getWords(String forWord) {
				return words;
			}
		});
	}

}
