package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.descriptor.Attribute;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
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
	 */
	private REMOTE() {}
	public static void ofScode(int instr) {
//		%+C        CheckTosType(T_OADDR);
//        GQfetch; InTag(%tag%);
//        attr:=DISPL(tag.HI).elt(tag.LO); Pop;
//        a.kind:=reladr; a.rela.val:=0; a.segmid.val:=0;
//%+E        a.sibreg:=NoIBREG;
//        adr:=NewAddress(attr.type,attr.rela,a);
//        adr.ObjState:=Calculated; Push(adr);
//        if CurInstr=S_REMOTEV then GQfetch endif;

		CTStack.dumpStack("REMOTE-1: ");
		Tag tag = Tag.ofScode();
//		CTStack.checkTosRef();
		CTStack.checkTosType(Type.T_OADDR); // CheckTosType(T_OADDR);
		Util.GQfetch("REMOTE-1 " + tag + ": ");
		Attribute attr = (Attribute) tag.getMeaning();
		CTStack.pop();
		
//		AddressItem adr = (AddressItem) CTStack.TOS;
//		CTStack.dumpStack("REMOTE-2: ");
		
		ObjectAddress memAddr = ObjectAddress.ofRelAddr(null); // a
		
		AddressItem adr = new AddressItem(attr.type, attr.rela, memAddr);
        adr.objState = AddressItem.State.Calculated;
//        System.out.println("REMOTE.doCode: adr="+adr);
		CTStack.push(adr);
        if(instr == Scode.S_REMOTEV)
        	Util.GQfetch("REMOTE-2 " + tag + ": ");
		CTStack.dumpStack("REMOTE-2: ");
		Global.PSEG.dump("REMOTE-2: ");
//		Util.IERR("");
	}

}
