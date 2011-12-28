package description;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public abstract class GuiceTestUtil {

	protected static Injector injector = null;
	
	public synchronized static void init(Module ... modules) {
		injector = Guice.createInjector(modules);
	}
	
	public GuiceTestUtil() {
		injector.injectMembers(this);
	}
	
}
