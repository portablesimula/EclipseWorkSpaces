/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.value;

import bec.descriptor.Attribute;
import bec.descriptor.ConstDescr;
import bec.descriptor.Descriptor;
import bec.descriptor.Variable;
import bec.scode.Scode;
import bec.scode.Sinstr;
import bec.scode.Tag;
import bec.scode.Type;
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
			Attribute attr = (Attribute) aTag.getMeaning();
			offset += attr.rela;
			Scode.inputInstr();
		} while (Scode.curinstr == Sinstr.S_C_DOT);

		int terminator = Scode.curinstr;
		Tag globalOrConstTag = Tag.ofScode();
		switch(terminator) {
			case Sinstr.S_C_AADDR:{
				Attribute attr = (Attribute) globalOrConstTag.getMeaning();
				return IntegerValue.of(Type.T_AADDR, offset + attr.rela);
			}
			case Sinstr.S_C_GADDR:{
				Descriptor descr = globalOrConstTag.getMeaning();
				if(descr instanceof Variable var) {
					return new GeneralAddress(var.address, offset);
				} else if(descr instanceof ConstDescr cns) {
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
