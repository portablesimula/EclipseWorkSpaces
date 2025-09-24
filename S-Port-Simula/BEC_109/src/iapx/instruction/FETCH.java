package iapx.instruction;

import static iapx.util.Global.*;

import iapx.Kind;
import iapx.CTStack.CTStack;
import iapx.qInstr.Q_PUSHM;
import iapx.util.Option;
import iapx.util.Type;
import iapx.value.MemAddr;

public abstract class FETCH extends Instruction {

	private static final boolean DEBUG = false;

	/**
	 * addressing_instruction : =  fetch
	 * 
	 * force TOS value;
	 * 
	 * TOS.MODE should be REF, otherwise fetch has no effect.
	 * TOS is modified to describe the contents of the area previously described.
	 * 
	 *      (TOS) -------------------,
	 *                               |
	 *                               V
	 *      The resulting            .============.
	 *          TOS -----------------|---> VALUE  |
	 *      after fetch              '============'
	 */
	public static void ofScode() {
		GQfetch();
	}

	public static void GQfetch() { //  Må ikke bruke qDI(se rupdate) --
//	begin infix(MemAddr) opr; range(0:MaxType) type;
//	      range(0:MaxWord) nbyte; range(0:MaxByte) cTYP;
		IO.println("FETCH.GQfetch: " + CTStack.TOS);
		if(CTStack.TOS.kind == Kind.K_Address) {
			MemAddr opr = MemAddr.getTosSrcAdr();
			Type type = CTStack.TOS.type; int size = type.size;
//	           if type<=T_MAX then cTYP = cTYPE(type) else cTYP = cANY endif;
			int cTYP = cANY;
			IO.println("FETCH.GQfetch: SJEKK DETTE SEINERE");

			if(Option.traceMode > 1) {
				IO.println("***GQfetch " + type + ", size=" + size);
				CTStack.dumpStack();
			}
//	%+C        if nbyte=0 then IERR("CODER.GQfetch-1") endif;
//	           if nbyte <= AllignFac then Qf4(qPUSHM,0,0,cTYP,nbyte,opr)
//	           elsif nbyte <= (3*AllignFac)
//	           then opr.rela.val = opr.rela.val+nbyte-AllignFac;
//	                repeat if nbyte>AllignFac then PresaveOprRegs(opr) endif;
//	                   Qf4(qPUSHM,0,0,cTYP,AllignFac,opr); nbyte = nbyte-AllignFac;
//	                while nbyte <> 0
//	                do opr.rela.val = opr.rela.val-AllignFac endrepeat;
//	           else
			
//			Qfunc.Qf4(Opcode.qPUSHM,0,0,cTYP,size,opr);
			new Q_PUSHM(size, opr);
			CTStack.pop();
			CTStack.pushTemp(type);
//			Util.IERR("");
		}
	}
	

}
