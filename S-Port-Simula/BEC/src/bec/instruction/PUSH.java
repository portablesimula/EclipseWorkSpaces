package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.descriptor.ConstDescr;
import bec.descriptor.Descriptor;
import bec.descriptor.Variable;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;

public abstract class PUSH extends Instruction {
	
	private static final boolean DEBUG = false;
	
	/**
	 * stack_instruction ::= push obj:tag | pushv obj:tag
	 * 
	 * End-Condition: Scode'nextByte = First byte after the tag
	 */
	public static void ofScode(int instr) {
		Tag tag = Tag.ofScode();
		if(DEBUG) System.out.println("PUSH.doCode: tag="+Scode.edTag(tag.val)+"  "+tag);
		Descriptor x = tag.getMeaning();
		if(x instanceof Variable var) {
			AddressItem addr = new AddressItem(var.type,0,var.address);
			if(DEBUG) {
				System.out.println("PUSH.doCode: var="+var);
				System.out.println("PUSH.doCode: addr="+addr);				
			}
			CTStack.push(addr);
		} else if(x instanceof ConstDescr cns) {
//			CTStack.push(new AddressItem(cns.tag.val,0,cns.address));
			CTStack.push(new AddressItem(cns.type,0,cns.getAddress()));
		} else Util.IERR("");
//        if v.kind=K_Import
//        then TOS.size:= - wAllign(%TOS.size%) endif;
        if(instr == Scode.S_PUSHV) FETCH.doFetch("PUSHV: ");
//      CTStack.dumpStack("PUSH: "+Scode.edInstr(instr));
	}

}
