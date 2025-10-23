/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.compileTimeStack;

import bec.util.Type;
import bec.value.ObjectAddress;

/// Address Item.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/compileTimeStack/AddressItem.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class AddressItem extends CTStackItem {
	public ObjectAddress objadr;
	public int offset;
	
	public AddressItem(Type type, int offset, ObjectAddress objadr) {
		this.mode = Mode.REF;
		this.type = type;
		this.size = type.size();
		this.objadr = objadr;
		this.offset = offset;
	}
	
	public boolean getIndexed() {
		return objadr.indexed;
	}
	
	public void setIndexed(boolean val) {
		objadr.indexed = val;
	}

	@Override
	public CTStackItem copy() {
		return new AddressItem(type, offset, objadr);
	}

	@Override
	public String toString() {
		String s = "" + type + " AT " + objadr + "[" + offset;
		if(getIndexed()) s += "+IDX";
		s =  s  + "]";
		if(objadr.kind == ObjectAddress.REMOTE_ADDR) s = s + " withRemoteBase";
		return edMode() + "ADDR: " + s;
	}

}
