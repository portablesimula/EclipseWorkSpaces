package bec.value;

import bec.descriptor.Attribute;
import bec.descriptor.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;

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
//			System.out.println("DotAddress.ofScode: descr=" + descr);
			Attribute attr = (Attribute) Global.getMeaning(aTag);
			offset += attr.rela;
			System.out.println("DotAddress.ofScode: " + attr + "  offset="+offset);
			Scode.inputInstr();
		} while (Scode.curinstr == Scode.S_C_DOT);

		int terminator = Scode.curinstr;
		Tag globalOrConstTag = Tag.ofScode();
		switch(terminator) {
			case Scode.S_C_AADDR:{
				Attribute attr = (Attribute) Global.getMeaning(globalOrConstTag);
				System.out.println("DotAddress.ofScode: " + attr + "  offset="+offset+attr.rela);
//				Util.IERR("SJEKK DETTE");
				return new IntegerValue(Type.T_AADDR, offset + attr.rela);
			}
			case Scode.S_C_GADDR:{
				Variable var = (Variable) Global.getMeaning(globalOrConstTag);
				return new GeneralAddress(var.address, offset);
			}
		}
		Util.IERR("Illegal termination of C-DOT value");
		return null;
	}

}
