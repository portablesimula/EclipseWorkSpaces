package bec.compileTimeStack;

import bec.util.Type;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTRegister;

public class AddressItem extends StackItem {
	public ObjectAddress objadr;
	public int offset;
	public boolean withRemoteBase;
	public int xReg;
	
	public AddressItem(Type type, int offset, ObjectAddress objadr) {
		this.type = type;
		this.size = type.size();
		this.objadr = objadr;
		this.offset = offset;
	}

	public String toString() {
		String s = "" + type + " AT " + objadr + "[" + offset;
		if(xReg > 0) s += "+" + RTRegister.edReg(xReg);
		return s  + "]";
	}

}
