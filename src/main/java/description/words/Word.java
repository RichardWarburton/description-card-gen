package description.words;

public class Word {

	private final String word;
	private final WordType type;
	private final RelationshipType relationship;
	
	public String getWord() {
		return word;
	}

	public WordType getType() {
		return type;
	}

	public RelationshipType getRelationship() {
		return relationship;
	}

	public Word(String word, WordType type, RelationshipType relationship) {
		super();
		this.word = word;
		this.type = type;
		this.relationship = relationship;
	}

	@Override
	public String toString() {
		return "Word [word=" + word + ", type=" + type + ", relationship="
				+ relationship + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (relationship != other.relationship)
			return false;
		if (type != other.type)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
	
}
