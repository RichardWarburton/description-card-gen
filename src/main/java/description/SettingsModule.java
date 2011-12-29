package description;

import static com.google.inject.name.Names.named;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import com.google.inject.AbstractModule;

public class SettingsModule extends AbstractModule {

	@Override
	protected void configure() {
		Properties props = new Properties();
		try {
			props.load(new FileReader("src/main/resources/settings.properties"));
			for(Entry<Object, Object> e:props.entrySet()) {
				String key = (String) e.getKey(), value = (String) e.getValue();
				bind(String.class).annotatedWith(named(key)).toInstance(value);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
