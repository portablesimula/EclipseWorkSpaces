package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_ADD;

public abstract class INCO extends Instruction {
	
	/**
	 * addressing_instruction ::= inco
	 * 
	 * inco, deco (dyadic)
	 * force TOS value; check TOS type(SIZE);
	 * force SOS value; check SOS type(OADDR);
	 * pop; pop;
	 * push( VAL, OADDR, "value(SOS) +/- value(TOS)" );
	 * 
	 * The two top elements are replaced by a descriptor of the object address RESULT defined
	 * through the equation
	 * dist(RESULT,value(SOS)) = +/- value(TOS)
	 * where + corresponds to inco and - to deco.
	 */
	public static void ofScode() {
		CTStack.checkTosType(Type.T_SIZE); CTStack.checkSosValue(); CTStack.checkSosType(Type.T_OADDR);
//		%+E   GetTosValueIn86(qEDX); Pop;
//		%+E   GQfetch; Qf1(qPOPR,qEAX,cOBJ); Pop;
//		      if inco then y:=qINCO else y:=qDECO endif;
//		%-E   Qf2(qDYADR,y,qAX,cOBJ,qDX); Qf1(qPUSHR,qAX,cOBJ);
//		%+E   Qf2(qDYADR,y,qEAX,cOBJ,qEDX); Qf1(qPUSHR,qEAX,cOBJ);
//		      pushTemp(T_OADDR);
		Global.PSEG.emit(new SVM_ADD(), "");
		CTStack.pop();
		CTStack.pop();
	    CTStack.pushTemp(Type.T_OADDR, RTRegister.qEAX, 1, "INCO: ");
//		Util.IERR("NOT IMPL");
	}

//	Visible Routine GQinco_deco; import Boolean inco;
//	begin range(0:255) y;
//	%+C   CheckTosType(T_SIZE); CheckSosValue; CheckSosType(T_OADDR);
//	%-E   GetTosValueIn86(qDX); Pop;
//	%+E   GetTosValueIn86(qEDX); Pop;
//	%-E   GQfetch; Qf1(qPOPR,qAX,cOBJ); Pop;
//	%+E   GQfetch; Qf1(qPOPR,qEAX,cOBJ); Pop;
//	      if inco then y:=qINCO else y:=qDECO endif;
//	%-E   Qf2(qDYADR,y,qAX,cOBJ,qDX); Qf1(qPUSHR,qAX,cOBJ);
//	%+E   Qf2(qDYADR,y,qEAX,cOBJ,qEDX); Qf1(qPUSHR,qEAX,cOBJ);
//	      pushTemp(T_OADDR);
//	end;

}
