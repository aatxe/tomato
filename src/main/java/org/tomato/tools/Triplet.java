package org.tomato.tools;

import java.io.Serializable;

/**
 * A tuple of three objects, based on generics.
 * @author tomato
 * @version 1.0
 * @param <E> the type of the left object
 * @param <F> the type of the center object
 * @param <G> the type of the right object
 * @since alpha
 */
public class Triplet<E, F, G> implements Serializable {
	private static final long serialVersionUID = 9000504761605746651L;
	private E left;
	private F center;
	private G right;
	

	/**
	 * Creates a tuple of three objects based on generics.
	 * @param left the left object of the tuple
	 * @param center the center object of the tuple
	 * @param right the right object of the tuple
	 */
	public Triplet(E left, F center, G right) {
		this.left = left;
		this.center = center;
		this.right = right;
	}

	/**
	 * Gets the left object of the triplet.
	 * @return the left object of the triplet
	 */
	public E getLeft() {
		return left;
	}
	
	/**
	 * Gets the center object of the triplet.
	 * @return the center object of the triplet
	 */
	public F getCenter() {
		return center;
	}

	/**
	 * Gets the right object of the triplet.
	 * @return the right object of the triplet
	 */
	public G getRight() {
		return right;
	}
	
	/**
	 * Updates the values of the tuple.
	 * @param left the left value of the triplet
	 * @param center the center value of the triplet
	 * @param right the right value of the triplet
	 */
	public void update(E left, F center, G right) {
		this.left = left;
		this.center = center;
		this.right = right;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean equals(Object o) {
	    if (this.getClass() != o.getClass()) return false;
	    if (this == (Triplet) o) return true;
	    Triplet compare = (Triplet) o;
	    return compare.getLeft() == this.getLeft() && compare.getCenter() == this.getCenter() && compare.getRight() == this.getRight();
	}
}