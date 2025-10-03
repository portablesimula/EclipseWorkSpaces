package bec.compileTimeStack;

import bec.util.Type;
import bec.value.ObjectAddress;

public class AddressItem extends CTStackItem {
	public ObjectAddress objadr;
	public int offset;
	public boolean indexed;
	
	public AddressItem(Type type, int offset, ObjectAddress objadr) {
		this.mode = Mode.REF;
		this.type = type;
		this.size = type.size();
		this.objadr = objadr;
		this.offset = offset;
	}

	@Override
	public CTStackItem copy() {
		AddressItem addr = new AddressItem(type, offset, objadr);
		addr.indexed = indexed;
		return addr;
	}

	@Override
	public String toString() {
		String s = "" + type + " AT " + objadr + "[" + offset;
		if(indexed) s += "+IDX";
		s =  s  + "]";
		if(objadr.kind == ObjectAddress.REMOTE_ADDR) s = s + " withRemoteBase";
		return edMode() + "ADDR: " + s;
	}

}
