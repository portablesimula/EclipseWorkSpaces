package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.descriptor.Attribute;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.value.ObjectAddress;

/**
 * 
 */
public abstract class REMOTE extends Instruction {

	/**
	 * addressing_instruction ::= remote attribute:tag | remotev attribute:tag
	 * 
	 * remote attr:tag
	 * force TOS value; check TOS type(OADDR);
	 * pop;
	 * push( REF, attr.TYPE, BASE = value(TOS), OFFSET = attr.OFFSET" );
	 * 
	 * This instruction uses one step of indirection. The value is considered to be the address of an
	 * object of the type 'REC' in which attr is an attribute. TOS is replaced by a descriptor of the
	 * designated component of that object. Note again that no qualification check is implied (neither
	 * could it be done).
	 * 
	 *         (TOS) -------- > OADDR VALUE ---------->.=====================.
	 *                                                 |-----.
	 *                                                 |     |               |
	 *                                                 |     | attribute     |
	 *                                                 |     |  OFFSET       |
	 *         The resulting           REF             |     V               |
	 *               TOS ------------------------------|----->.======.       |
	 *          after remote                           |      | attr |       |
	 *                                                 |      '======'       |
	 *                                                 |                     |
	 *                                                 '====================='
	 */
	private REMOTE() {}
	public static void ofScode(int instr) {
		Tag tag = Tag.ofScode();
		CTStack.forceTosValue();			
		CTStack.checkTosType(Type.T_OADDR);
		CTStack.forceTosValue();
		Attribute attr = (Attribute) tag.getMeaning();
		CTStack.pop();
		
		ObjectAddress memAddr = ObjectAddress.ofRemoteAddr();
		AddressItem adr = new AddressItem(attr.type, attr.rela, memAddr);
		CTStack.push(adr);
		
        if(instr == Scode.S_REMOTEV) {
        	CTStack.forceTosValue();
        }
	}

}
