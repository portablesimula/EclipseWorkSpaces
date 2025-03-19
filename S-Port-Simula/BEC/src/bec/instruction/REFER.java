package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTAddress;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.SVM_POP2REG;
import bec.virtualMachine.SVM_POPK;
import bec.virtualMachine.SVM_REFER;

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
		
		if(DEBUG) System.out.println("REFER.ofScode: TOS="+CTStack.TOS.getClass().getSimpleName()+"  "+CTStack.TOS);
		// %+E             Qf2(qLOADC,0,qEAX,cVAL,TOS qua Address.Offset);
		// %+E             Qf1(qPOPR,qEBX,cVAL);
		// %+E             Qf2(qDYADR,qADD,qEAX,cVAL,qEBX); Qf1(qPUSHR,qEAX,cVAL);

//		int gOfst = RTStack.popInt();
//		ObjectAddress objadr = (ObjectAddress) RTStack.pop().value();
//		RTAddress rtaddr = new RTAddress(objadr,gOfst);
//		RTStack.push(rtaddr, "");
		
//		Global.PSEG.emit(new SVM_REFER(type), "REFER: " + type);
		int reg = RTRegister.getFreeReg();
		Global.PSEG.emit(new SVM_POP2REG(reg), "REFER: ");
		
//		// LOAD GADDR FROM TOS
		ObjectAddress a = ObjectAddress.ofRelAddr(null);
		AddressItem adr = new AddressItem(type, 0, a);
		adr.xReg = reg;

		
//		Util.GQfetch("REFER " + Scode.edTag(type.tag) + ": ");
		
//		adr.objState = AddressItem.ObjState.Calculated;
//		adr.atrState = AddressItem.AtrState.Calculated;
//		adr.objState = AddressItem.ObjState.Remote;
		
//		adr.isRemoteBase = true;
		adr.isRefered = true;
//		adr.atrState = AddressItem.AtrState.Indexed;
//		Util.IERR("DETTE MÅ TESTES");
        CTStack.pop(); 
        CTStack.push(adr);			
	}
	public static void OLD_ofScode() {
		if(DEBUG) CTStack.dumpStack("REFER.ofScode");
		Type type = Type.ofScode();
		CTStack.checkTosType(Type.T_GADDR);
		
		if(DEBUG) System.out.println("REFER.ofScode: TOS="+CTStack.TOS.getClass().getSimpleName()+"  "+CTStack.TOS);
		// %+E             Qf2(qLOADC,0,qEAX,cVAL,TOS qua Address.Offset);
		// %+E             Qf1(qPOPR,qEBX,cVAL);
		// %+E             Qf2(qDYADR,qADD,qEAX,cVAL,qEBX); Qf1(qPUSHR,qEAX,cVAL);

		// LOAD GADDR FROM TOS
		ObjectAddress a = ObjectAddress.ofRelAddr(null);
		AddressItem adr = new AddressItem(type, 0, a);
		Util.GQfetch("REFER " + Scode.edTag(type.tag) + ": ");
		
//		adr.objState = AddressItem.ObjState.Calculated;
//		adr.atrState = AddressItem.AtrState.Calculated;
//		adr.objState = AddressItem.ObjState.Remote;
		
//		adr.isRemoteBase = true;
		adr.isRefered = true;
		adr.atrState = AddressItem.AtrState.Indexed;
//		Util.IERR("DETTE MÅ TESTES");
        CTStack.pop(); 
        CTStack.push(adr);			
	}

}
