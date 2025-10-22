package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.descriptor.Attribute;
import bec.util.Scode;
import bec.util.Tag;

/**
 * select attr:tag
 * 
 * check TOS ref;
 * TOS.TYPE := attr.TYPE;
 * "TOS.OFFSET := TOS.OFFSET ++ attr.OFFSET";
 * 
 * (note that the BASE component of TOS is unchanged)
 * The area described by TOS is considered to be holding a record of the type, say 'REC', in which
 * the instruction argument attr is an attribute. TOS is modified to describe the designated
 * component of that record. Note that no qualification check is implied, i.e. TOS.TYPE may be
 * different from 'REC'.
 * 
 *       BASE ---------------------------> .============================.
 *                                         |-----.                      |
 *                                         |     |                      |
 *                                         |     | TOS.OFFSET           |
 *                                         |     |                      |
 *                      REF                |     V                      |
 *       (TOS) ----------------------------|---> .==================.   |
 *                                         |     |----              |   |
 *                                         |     |    |             |   |
 *                                         |     |    | attr.OFFSET |   |
 *    The resulting         REF            |     |    V             |   |
 *          TOS ---------------------------|-----|--> .======.      |   |
 *     after select                        |     |    | attr |      |   |
 *                                         |     |    .======.      |   |
 *                                         |     |                  |   |
 *                                         |     .==================.   |
 *                                         |                            |
 *                                         .============================.
 */
public abstract class SELECT extends Instruction {

	private static final boolean DEBUG = false;

	/**
	 * addressing_instruction ::= select attribute:tag | selectv attribute:tag
	 */
	public static void ofScode(int instr) {
//		CTStack.dumpStack();
		Tag tag = Tag.ofScode();
		CTStack.checkTosRef();
		Attribute attr = (Attribute) tag.getMeaning();
		CTStack.TOS().type = attr.type;
		AddressItem adr = (AddressItem) CTStack.TOS();
		adr.offset = adr.offset + attr.rela;
		if(DEBUG) IO.println("SELECT.ofScode: ofst="+adr.offset + ", rela=" + attr.rela);
		adr.type = attr.type;
		adr.size = attr.size;
		if(DEBUG) IO.println("SELECT.ofScode: TOS="+adr);
		if(instr == Scode.S_SELECTV) CTStack.forceTosValue();
//		CTStack.dumpStack();
//		Util.IERR("");
	}

}
