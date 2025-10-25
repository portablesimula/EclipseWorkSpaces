/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
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
