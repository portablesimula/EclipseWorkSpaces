package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.ObjectAddress;

public abstract class REFER extends Instruction {
	
	/**
	 * addressing_instruction ::= refer resolved_type
	 * 
	 * force TOS value; check TOS type(GADDR);
	 * TOS.MODE := REF; TOS.TYPE := type;
	 * 
	 * TOS is modified to describe a quantity of the given type, at the address described by TOS.
	 */
	public static void ofScode() {
//		CTStack.dumpStack();
		Type type = Type.ofScode();
		CTStack.checkTosType(Type.T_GADDR);
		ObjectAddress a = ObjectAddress.ofRelAddr(null);
		AddressItem adr = new AddressItem(type, 0, a);
        Util.GQfetch("REFER " + Scode.edTag(type.tag) + ": ");
		adr.objState = adr.atrState = AddressItem.State.Calculated;
        CTStack.pop(); 
        CTStack.push(adr);
	}

}
