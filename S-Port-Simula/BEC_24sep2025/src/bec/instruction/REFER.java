package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.CTStackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.dataAddress.DataAddress;
import bec.value.dataAddress.RelAddress;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_POPGADR;

/**
 * 
 * refer resolved_type
 * 
 * force TOS value; check TOS type(GADDR);
 * TOS.MODE  =  REF; TOS.TYPE  =  type;
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
	 * addressing_instruction : =  refer resolved_type
	 * 
	 * force TOS value; check TOS type(GADDR);
	 * TOS.MODE  =  REF; TOS.TYPE  =  type;
	 * 
	 * TOS is modified to describe a quantity of the given type, at the address described by TOS.
	 */
	public static void ofScode() {
		
		boolean TESTING = true;
	
		if(TESTING) {
//	           type:=intype;
//	           %+C        CheckTosType(T_GADDR);
			Type type = Type.ofScode();
			CTStack.checkTosType(Type.T_GADDR);
//	                      a.kind:=reladr; a.rela.val:=0; a.segmid.val:=0;
//	           %+E        a.sibreg:=NoIBREG;
			DataAddress objadr = new RelAddress(0);  // RelAddress(ofst)
//	                      adr:=NewAddress(type,0,a);
			AddressItem adr = AddressItem.ofVAL(type, 0, objadr);
//	                      GQfetch;
			FETCH.GQfetch(null);
			
			adr.objState = adr.atrState = AddressItem.Calculated;
			CTStack.pop(); CTStack.push(adr);
			IO.println("REFER.ofScode: DETTE MÅ SJEKKES NØYE");
//			Util.IERR("");
		} else {
//			if(DEBUG) CTStack.dumpStack("REFER.ofScode");
//			Type type = Type.ofScode();
//			CTStack.forceTosValue();			
//			CTStack.checkTosType(Type.T_GADDR);
//			
//			if(DEBUG) IO.println("REFER.ofScode: TOS="+CTStack.TOS().getClass().getSimpleName()+"  "+CTStack.TOS());
//	
//			@SuppressWarnings("unused")
//			CTStackItem tos = CTStack.pop(); 
//			// LOAD GADDR FROM TOS
//	//		int xReg = RTRegister.getFreeTempReg();	 // To hold the GADDR
//			int xReg = RTRegister.getFreeAddressReg(); // To hold the GADDR
//			DataAddress adr = new RelAddress(xReg, 0);	
//			CTStackItem itm =  AddressItem.ofREF(type, 0, adr);
//			Global.PSEG.emit(new SVM_POPGADR(xReg), "REFER: " + Scode.edTag(type.tag));
//			CTStack.push(itm);	
//	
//	//		CTStack.dumpStack("REFER.ofScode: ");
//	//		Util.IERR("");
	 	}
	}

}
