package description.titles;

import com.google.common.collect.Multimap;
import com.google.inject.ImplementedBy;

@ImplementedBy(WikipediaSingleTitles.class)
public interface Titles {

	public Multimap<String, String> getTitles(int max);
	
}
