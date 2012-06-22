package tools;

import java.io.Serializable;

public class Pair<E, F> implements Serializable {
	private static final long serialVersionUID = -634786879149311046L;
	private E left;
	private F right;

	public Pair(E left, F right) {
		this.left = left;
		this.right = right;
	}

	public E getLeft() {
		return left;
	}

	public F getRight() {
		return right;
	}
	
	public void update(E left, F right) {
		this.left = left;
		this.right = right;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean equals(Object o) {
	    if (this.getClass() != o.getClass()) return false;
	    if (this == (Pair) o) return true;
	    Pair compare = (Pair) o;
	    return compare.getLeft() == this.getLeft() && compare.getRight() == this.getRight();
	}
}
