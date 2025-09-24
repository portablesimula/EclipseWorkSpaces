package iapx.instruction;

import iapx.CTStack.Address;
import iapx.CTStack.CTStack;
import iapx.descriptor.Descriptor;
import iapx.descriptor.ExtDescr;
import iapx.descriptor.GlobalVar;
import iapx.descriptor.LocDescr;
import iapx.descriptor.Parameter;
import iapx.util.Display;
import iapx.util.Option;
import iapx.util.Scode;
import iapx.util.Tag;
import iapx.util.Util;
import iapx.value.MemAddr;

public abstract class PUSH extends Instruction {
	
	private static final boolean DEBUG = false;
	
	/**
	 * stack_instruction ::= push obj:tag | pushv obj:tag
	 * 
	 * End-Condition: Scode'nextByte = First byte after the tag
	 */
	public static void ofScode() {
		Tag tag = Tag.inTag();
		if(DEBUG) System.out.println("PUSH.ofScode: tag=" + tag);
		Descriptor descr = Display.lookup(tag);
		if(Option.GENERATE_Q_CODE) {
//	      	switch(descr.kind) {
//	           case K_GlobalVar: MemAddr adr = ((GlobalVar)descr).adr;
	      	MemAddr adr = null;
	      	if     (descr instanceof GlobalVar gvar) { adr = gvar.adr; }
	      	else if(descr instanceof ExtDescr xvar)  { adr = MemAddr.ofExtAddr(xvar.extRef, 0); }
	      	else if(descr instanceof LocDescr lvar)  { adr = MemAddr.ofLocAddr(lvar.rela); }
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
//					System.out.println("PUSH.doCode: var="+var);
//					System.out.println("PUSH.doCode: addr="+addr);				
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
