package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.descriptor.ConstDescr;
import bec.descriptor.Descriptor;
import bec.descriptor.Variable;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.ObjectAddress;

public abstract class PUSH extends Instruction {

	/**
	 * stack_instruction ::= push obj:tag | pushv obj:tag
	 * 
	 * End-Condition: Scode'nextByte = First byte after the tag
	 */
	public static void ofScode(int instr) {
		Tag tag = Tag.ofScode();
		Descriptor x = tag.getMeaning();
		if(x instanceof Variable var) {
				ObjectAddress oaddr = (ObjectAddress) var.address.copy();
				CTStack.push(new AddressItem(var.type,0,oaddr));
		} else if(x instanceof ConstDescr cns) {
				ObjectAddress oaddr = (ObjectAddress) cns.getAddress().copy();
				CTStack.push(new AddressItem(cns.type,0,oaddr));
		} else Util.IERR("");
        if(instr == Scode.S_PUSHV) CTStack.forceTosValue();
	}

}
