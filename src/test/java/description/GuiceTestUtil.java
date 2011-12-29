package description;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class GuiceTestUtil {

	protected Injector injector = null;

	protected GuiceTestUtil(Module ... modules) {
		injector = Guice.createInjector(modules);
		injector.injectMembers(this);
	}
	
}
