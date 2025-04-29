package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_REFER;

/**
 * 
 * refer resolved_type
 * 
 * force TOS value; check TOS type(GADDR);
 * TOS.MODE := REF; TOS.TYPE := type;
 * 
 * TOS is modified to describe a quantity of the given type, at the address described by TOS.
 * 
 *                           =================
 *       (TOS) ==============|==> GADDR VALUE –|----------.
 *                           =================            |
 *                                                        |
 *                                                        |
 *  The resulting                                         V
 *       TOS -------------------------------------------->.==========.
 *   after refer                  REF                     |  object  |
 *                                                        |    of    |
 *                                                        |  "type"  |
 *                                                        '=========='
 */
public abstract class REFER extends Instruction {

	private static final boolean DEBUG = false;

	/**
	 * addressing_instruction ::= refer resolved_type
	 * 
	 * force TOS value; check TOS type(GADDR);
	 * TOS.MODE := REF; TOS.TYPE := type;
	 * 
	 * TOS is modified to describe a quantity of the given type, at the address described by TOS.
	 */
	public static void ofScode() {
		if(DEBUG) CTStack.dumpStack("REFER.ofScode");
		Type type = Type.ofScode();
		CTStack.checkTosType(Type.T_GADDR);
		
		if(DEBUG) System.out.println("REFER.ofScode: TOS="+CTStack.TOS().getClass().getSimpleName()+"  "+CTStack.TOS());

		int reg = RTRegister.getFreeReg();
		Global.PSEG.emit(new SVM_REFER(reg), "REFER: " + Scode.edTag(type.tag));
		
		// LOAD GADDR FROM TOS
		ObjectAddress a = ObjectAddress.ofRelAddr(null);
		AddressItem adr = new AddressItem(type, 0, a);
		adr.xReg = reg;
        CTStack.pop(); 
        CTStack.push(adr);			
	}

}
