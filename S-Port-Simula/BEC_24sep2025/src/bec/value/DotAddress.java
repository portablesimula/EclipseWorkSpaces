package bec.value;

import bec.descriptor.Attribute;
import bec.descriptor.ConstDescr;
import bec.descriptor.Descriptor;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.value.dataAddress.GeneralAddress;

public abstract class DotAddress {
	
	/**
	 * 	attribute_address
	 * 		::= < c-dot attribute:tag >* c-aaddr attribute:tag
	 * 		::= anone
	 * 
	 * 	general_address
	 * 		::= < c-dot attr:tag >* c-gaddr global_or_const:tag
	 * 		::= gnone
	 */
	public static Value ofScode() {
		int offset = 0;
		
		do {
			Tag aTag = Tag.ofScode();
//			Descriptor descr = Global.getMeaning(aTag);
//			IO.println("DotAddress.ofScode: descr=" + descr);
			Attribute attr = (Attribute) Global.getMeaning(aTag);
			offset += attr.rela;
//			IO.println("DotAddress.ofScode: " + attr + "  offset="+offset);
			Scode.inputInstr();
		} while (Scode.curinstr == Scode.S_C_DOT);

		int terminator = Scode.curinstr;
		Tag globalOrConstTag = Tag.ofScode();
		switch(terminator) {
			case Scode.S_C_AADDR:{
				Attribute attr = (Attribute) Global.getMeaning(globalOrConstTag);
//				IO.println("DotAddress.ofScode: AADDR: " + attr + "  offset="+(offset+attr.rela) + " = " + offset + " + " + attr.rela);
//				IO.println("DotAddress.ofScode: NEXT INSTR: " + Scode.edInstr(Scode.nextByte()));
//				Util.IERR("SJEKK DETTE");
				return IntegerValue.of(Type.T_AADDR, offset + attr.rela);
			}
			case Scode.S_C_GADDR:{
//				Variable var = (Variable) Global.getMeaning(globalOrConstTag);
//				return new GeneralAddress(var.address, offset);
				Descriptor descr = Global.getMeaning(globalOrConstTag);
				if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
				if(descr instanceof Variable var) {
//					IO.println("DotAddress.ofScode: var.address="+var.address);
					return new GeneralAddress(var.address, offset);
				} else if(descr instanceof ConstDescr cns) {
//					IO.println("DotAddress.ofScode: cns.address="+cns.address);
					return new GeneralAddress(cns.getAddress(), offset);
				}
				Util.IERR("NOT IMPL: " + descr.getClass().getSimpleName());
				return null;
			}
		}
		Util.IERR("Illegal termination of C-DOT value");
		return null;
	}

}
