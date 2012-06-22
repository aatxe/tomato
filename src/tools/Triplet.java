package tools;

import java.io.Serializable;

public class Triplet<E, F, G> implements Serializable {
	private static final long serialVersionUID = 9000504761605746651L;
	private E left;
	private F middle;
	private G right;

	public Triplet(E left, F middle, G right) {
		this.left = left;
		this.middle = middle;
		this.right = right;
	}

	public E getLeft() {
		return left;
	}
	
	public F getMiddle() {
		return middle;
	}

	public G getRight() {
		return right;
	}
	
	public void update(E left, F middle, G right) {
		this.left = left;
		this.middle = middle;
		this.right = right;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean equals(Object o) {
	    if (this.getClass() != o.getClass()) return false;
	    if (this == (Triplet) o) return true;
	    Triplet compare = (Triplet) o;
	    return compare.getLeft() == this.getLeft() && compare.getMiddle() == this.getMiddle() && compare.getRight() == this.getRight();
	}
}