package iAPX.instruction;

import iAPX.ctStack.Address;
import iAPX.ctStack.CTStack;
import iAPX.descriptor.AttrDescr;
import iAPX.massage.Massage;
import iAPX.util.Display;
import iAPX.util.Option;
import iAPX.util.Scode;
import iAPX.util.Tag;

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
	public static void ofScode(Scode instr) {
//		CTStack.dumpStack();
		Tag tag = Tag.inTag();
		CTStack.checkTosRef();
		AttrDescr attr = (AttrDescr) Display.lookup(tag);
		Address tos = (Address) CTStack.TOS;
		tos.offset = tos.offset + attr.rela;
        tos.repdist = tos.type.size;
		tos.type = attr.type;
		if(DEBUG) IO.println("SELECT.ofScode: ofst="+tos.offset + ", rela=" + attr.rela);
		if(Option.GENERATE_Q_CODE) {
			if(tos.atrState == Address.FromConst) {
				tos.atrState = Address.NotStacked;
				POP.qPOPKill(1);
			}
		} else {
			if(DEBUG) IO.println("SELECT.ofScode: TOS="+tos);
		}
		if(instr == Scode.S_SELECTV) FETCH.doFetch("SELECTV " + tag + ": ");
//		CTStack.dumpStack();
//		Util.IERR("");
	}

}
