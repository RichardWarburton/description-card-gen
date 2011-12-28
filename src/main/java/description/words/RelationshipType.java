package description.words;

public enum RelationshipType {

	SYNONYM,
	ANTONYM,
	RELATED,
	SIMILAR,
	USER;
	
	public static RelationshipType fromAPI(String str) {
		switch (str) {
		case "syn": return SYNONYM;
		case "ant": return ANTONYM;
		case "rel": return RELATED;
		case "sim": return SIMILAR;
		case "usr": return USER;

		default:
			throw new ParseException("No known relationship type for: "+str);
		}
	}
	
}
