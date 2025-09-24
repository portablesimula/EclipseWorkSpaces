package iAPX.instruction;

import iAPX.ctStack.Address;
import iAPX.ctStack.CTStack;
import iAPX.dataAddress.MemAddr;
import iAPX.descriptor.AttrDescr;
import iAPX.util.Display;
import iAPX.util.Option;
import iAPX.util.Scode;
import iAPX.util.Tag;
import iAPX.util.Type;
import iAPX.util.Util;

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
		if(Option.GENERATE_Q_CODE) {
			FETCH.GQfetch();
			AttrDescr attr = (AttrDescr) Display.lookup(tag);
			CTStack.pop();
			MemAddr memAddr = MemAddr.ofRelAddr(0);
			Address adr = new Address(attr.type, memAddr, attr.rela);
			adr.objState = Address.Calculated;
			CTStack.Push(adr);
			if(instr == Scode.S_REMOTEV) FETCH.GQfetch();
		} else {
//			CTStack.forceTosValue();			
//			FETCH.doFetch("REMOTE-1 " + tag + ": ");
//			AttrDescr attr = (AttrDescr) Display.lookup(tag);
//			CTStack.pop();
//			
//	
//			DataAddress memAddr = null;
//			if(Global.TESTING_REMOTE) {
//	//			memAddr = new RemoteAddress(0, 0);				
//				Global.PSEG.emit(new SVM_FIXREMOTE(new RemoteAddress(0, 0)), "REMOTE: ");
//			} else {
//				int xReg = 0;
//				if(Global.TESTING_REGUSE) {
//					xReg = RTRegister.remoteReg;
//				} else {
//					xReg = RTRegister.getFreeReg();				
//				}
//				Global.PSEG.emit(new SVM_STORE2REG(xReg), "REMOTE: ");
//				memAddr = new RemoteAddress(xReg, 0);	
//			}
//			
//			
//			AddressItem adr = AddressItem.ofREF(attr.type, attr.rela, memAddr);
//			CTStack.push(adr);
			
	        if(instr == Scode.S_REMOTEV) {
	        	FETCH.doFetch("REMOTE-2 " + tag + ": ");
	        }
			Util.IERR("");
		}
        if(DEBUG) {
        	CTStack.dumpStack("REMOTE-2: ");
//    		Global.PSEG.dump("REMOTE-2: ");
       }
	}

}
