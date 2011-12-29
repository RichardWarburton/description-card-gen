package description.titles;

import com.google.common.collect.Multimap;
import com.google.inject.AbstractModule;

public class StubTitlesModule extends AbstractModule {

	private final Multimap<String, String> titles;
	
	public StubTitlesModule(Multimap<String, String> titles) {
		super();
		this.titles = titles;
	}

	@Override
	protected void configure() {
		bind(Titles.class).toInstance(new Titles() {
			@Override
			public Multimap<String, String> getTitles(int max) {
				return titles;
			}
		});
	}

}
