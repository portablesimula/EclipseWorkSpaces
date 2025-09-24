package iapx.instruction;

import iapx.CTStack.Address;
import iapx.CTStack.CTStack;
import iapx.descriptor.AttrDescr;
import iapx.util.Display;
import iapx.util.Option;
import iapx.util.Scode;
import iapx.util.Tag;
import iapx.util.Type;
import iapx.util.Util;
import iapx.value.MemAddr;

/**
 * 
 */
public abstract class REMOTE extends Instruction {
	
	private static final boolean DEBUG = false;

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
//    when S_REMOTE,S_REMOTEV:
//         CheckTosType(T_OADDR);
//         GQfetch; InTag(%tag%);
//         attr:=DISPL(tag.HI).elt(tag.LO); Pop;
//         a.kind:=reladr; a.rela.val:=0; a.segmid.val:=0;
//%+E      a.sibreg:=NoIBREG;
//         adr:=NewAddress(attr.type,attr.rela,a);
//         adr.ObjState:=Calculated; Push(adr);
//         if CurInstr=S_REMOTEV then GQfetch endif;
	private REMOTE() {}
	public static void ofScode(Scode instr) {
		if(DEBUG) CTStack.dumpStack("REMOTE-1: ");
		CTStack.checkTosType(Type.T_OADDR);
		Tag tag = Tag.inTag();
		FETCH.GQfetch();
		AttrDescr attr = (AttrDescr) Display.lookup(tag);
		CTStack.pop();
		MemAddr memAddr = MemAddr.ofRelAddr(0);
		Address adr = new Address(attr.type, memAddr, attr.rela);
		adr.objState = Address.Calculated;
		CTStack.Push(adr);
		if(instr == Scode.S_REMOTEV) FETCH.GQfetch();
        if(DEBUG) {
        	CTStack.dumpStack("REMOTE-2: ");
//    		Global.PSEG.dump("REMOTE-2: ");
       }
	}

}
