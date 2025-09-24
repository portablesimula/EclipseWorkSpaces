package iapx.value;

import iapx.descriptor.AttrDescr;
import iapx.descriptor.ConstDescr;
import iapx.descriptor.Descriptor;
import iapx.util.Display;
import iapx.util.Scode;
import iapx.util.Tag;
import iapx.util.Type;
import iapx.util.Util;

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
			Tag aTag = Tag.inTag();
//			Descriptor descr = Global.getMeaning(aTag);
//			System.out.println("DotAddress.ofScode: descr=" + descr);
			AttrDescr attr = (AttrDescr) Display.lookup(aTag);
			offset += attr.rela;
//			System.out.println("DotAddress.ofScode: " + attr + "  offset="+offset);
			Scode.inputInstr();
		} while (Scode.curinstr == Scode.S_C_DOT);

		Scode terminator = Scode.curinstr;
		Tag globalOrConstTag = Tag.inTag();
		switch(terminator) {
			case Scode.S_C_AADDR:{
				AttrDescr attr = (AttrDescr) Display.lookup(globalOrConstTag);
//				System.out.println("DotAddress.ofScode: AADDR: " + attr + "  offset="+(offset+attr.rela) + " = " + offset + " + " + attr.rela);
//				System.out.println("DotAddress.ofScode: NEXT INSTR: " + Scode.edInstr(Scode.nextByte()));
//				Util.IERR("SJEKK DETTE");
				return IntegerValue.of(Type.T_AADDR, offset + attr.rela);
			}
			case Scode.S_C_GADDR:{
//				Variable var = (Variable) Global.getMeaning(globalOrConstTag);
//				return new GeneralAddress(var.address, offset);
				Descriptor descr = Display.lookup(globalOrConstTag);
				if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
				if(descr instanceof Variable var) {
//					System.out.println("DotAddress.ofScode: var.address="+var.address);
					return new GeneralAddress(var.address, offset);
				} else if(descr instanceof ConstDescr cns) {
//					System.out.println("DotAddress.ofScode: cns.address="+cns.address);
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
