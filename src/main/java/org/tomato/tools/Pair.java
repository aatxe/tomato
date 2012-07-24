package org.tomato.tools;

import java.io.Serializable;

/**
 * A pair of two objects, based on generics.
 * @author tomato
 * @version 1.0
 * @param <E> the type of the left object
 * @param <F> the type of the right object
 * @since alpha
 */
public class Pair<E, F> implements Serializable {
	private static final long serialVersionUID = -634786879149311046L;
	private E left;
	private F right;

	/**
	 * Creates a pair of two objects based on generics.
	 * @param left the left object of the pair
	 * @param right the right object of the pair
	 */
	public Pair(E left, F right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * Gets the left object of the pair.
	 * @return the left object of the pair
	 */
	public E getLeft() {
		return left;
	}

	/**
	 * Gets the right object of the pair.
	 * @return the right object of the pair
	 */
	public F getRight() {
		return right;
	}
	
	/**
	 * Updates the values of the pair.
	 * @param left the left object of the pair
	 * @param right the right object of the pair
	 */
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
