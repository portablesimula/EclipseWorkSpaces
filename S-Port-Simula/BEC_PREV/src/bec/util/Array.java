package bec.util;

import java.util.Vector;

@SuppressWarnings("serial")
public class Array<E> extends Vector<E> {
	
	public Array() {
		super();
	}
	
	public E set(int index, E elt) {
		int minSize = index + 1;
		if(size() < minSize)
			setSize(minSize);
		return super.set(index, elt);
	}
	
	public E get(int index) {
		try {
			return super.get(index);
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
}
