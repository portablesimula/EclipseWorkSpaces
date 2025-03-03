package PREV.syntaxClass.instruction;

import PREV.util.PREV_ResolvedType;
import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.util.Scode;
import bec.util.Util;
import bec.value.MemAddr;

public class REFER extends PREV_Instruction {
	PREV_ResolvedType type;
	
	/**
	 * addressing_instruction ::= refer resolved_type
	 * 
	 * force TOS value; check TOS type(GADDR);
	 * TOS.MODE := REF; TOS.TYPE := type;
	 * 
	 * TOS is modified to describe a quantity of the given type, at the address described by TOS.
	 */
	public REFER() {
		type = new PREV_ResolvedType();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkTosType(Scode.TAG_GADDR);
		MemAddr a = new MemAddr(null, 0);
		Address adr = new Address(type.tag, 0, a);
        Util.GQfetch("REFER " + Scode.edTag(type.tag) + ": ");
		adr.objState = adr.atrState = Address.State.Calculated;
        CTStack.pop(); 
        CTStack.push(adr);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "REFER " + type;
	}
	

}
