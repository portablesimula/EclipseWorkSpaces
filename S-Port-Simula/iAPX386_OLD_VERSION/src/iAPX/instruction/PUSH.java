package iAPX.instruction;

import iAPX.ctStack.Address;
import iAPX.ctStack.CTStack;
import iAPX.dataAddress.MemAddr;
import iAPX.descriptor.Descriptor;
import iAPX.descriptor.ExternVar;
import iAPX.descriptor.GlobalVar;
import iAPX.descriptor.LocalVar;
import iAPX.descriptor.Parameter;
import iAPX.util.Display;
import iAPX.util.Option;
import iAPX.util.Scode;
import iAPX.util.Tag;
import iAPX.util.Util;

public abstract class PUSH extends Instruction {
	
	private static final boolean DEBUG = false;
	
	/**
	 * stack_instruction ::= push obj:tag | pushv obj:tag
	 * 
	 * End-Condition: Scode'nextByte = First byte after the tag
	 */
	public static void ofScode() {
		Tag tag = Tag.inTag();
		if(DEBUG) IO.println("PUSH.ofScode: tag=" + tag);
		Descriptor descr = Display.lookup(tag);
		if(Option.GENERATE_Q_CODE) {
//	      	switch(descr.kind) {
//	           case K_GlobalVar: MemAddr adr = ((GlobalVar)descr).adr;
	      	MemAddr adr = null;
	      	if     (descr instanceof GlobalVar gvar) { adr = gvar.adr; }
	      	else if(descr instanceof ExternVar xvar) { adr = MemAddr.ofExtAddr(xvar.adr.smbx, xvar.adr.rela); }
	      	else if(descr instanceof LocalVar lvar)  { adr = MemAddr.ofLocAddr(lvar.rela); }
	      	else if(descr instanceof Parameter par)  { adr = MemAddr.ofLocAddr(par.rela); }
//	      	else if(descr instanceof Export lvar)    { adr = MemAddr.ofLocAddr(lvar.rela); }
//	           case K_Export:
//	                adr.kind = reladr; adr.segmid.val = 0;
//	                adr.rela.val = descr qua LocDescr.rela;
//	%+E             adr.sibreg = bEBP+NoIREG;
	      	else Util.IERR("Illegal push of: " + descr);
	      	
	      	CTStack.Push(new Address(descr.type, adr, 0));
	      	if(descr instanceof Parameter) {
	      		// TOS.repdist =  - wAllign(%TOS.repdist%);
	      		CTStack.TOS.repdist =  - CTStack.TOS.repdist;
	      		IO.println("SJEKK DETTE SEINERE");
	      	}
	      	IO.println("Parser.instruction'PUSH: " + Scode.curinstr);
	      	if(Scode.curinstr == Scode.S_PUSHV) FETCH.GQfetch();
		} else {
//			if(descr instanceof Variable var) {
//				AddressItem addr = AddressItem.ofREF(var.type,0,var.address);
//				if(DEBUG) {
//					IO.println("PUSH.doCode: var="+var);
//					IO.println("PUSH.doCode: addr="+addr);				
//				}
//				CTStack.push(addr);
//			} else if(descr instanceof ConstDescr cns) {
//				CTStack.push(AddressItem.ofREF(cns.type,0,cns.getAddress()));
//			} else Util.IERR("");
//	        if(instr == Scode.S_PUSHV) FETCH.doFetch("PUSHV: ");
	        Util.IERR("");
		}
        
//		CTStack.dumpStack("PUSH: "+Scode.edInstr(instr));
	}

}
