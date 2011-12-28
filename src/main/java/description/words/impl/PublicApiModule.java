package description.words.impl;

import com.google.inject.AbstractModule;

import description.words.WordsAPI;

public class PublicApiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(WordsAPI.class).to(PublicApiImplementation.class);
	}

}
