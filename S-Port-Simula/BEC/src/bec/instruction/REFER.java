package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Type;
import bec.value.ObjectAddress;

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
		CTStack.forceTosValue();			
		CTStack.checkTosType(Type.T_GADDR);
		
		if(DEBUG) IO.println("REFER.ofScode: TOS="+CTStack.TOS().getClass().getSimpleName()+"  "+CTStack.TOS());

		ObjectAddress a = ObjectAddress.ofReferAddr();	
		AddressItem adr = new AddressItem(type, 0, a);
        CTStack.pop(); 
        CTStack.push(adr);			

	}

}
