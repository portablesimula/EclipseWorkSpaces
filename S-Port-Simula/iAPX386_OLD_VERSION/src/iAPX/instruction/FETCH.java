package iAPX.instruction;

import static iAPX.util.Global.*;

import iAPX.ctStack.Address;
import iAPX.ctStack.CTStack;
import iAPX.dataAddress.MemAddr;
import iAPX.enums.Kind;
import iAPX.enums.Opcode;
import iAPX.qInstr.Q_POPR;
import iAPX.qInstr.Q_PUSHM;
import iAPX.qPkt.Qfunc;
import iAPX.util.Global;
import iAPX.util.Option;
import iAPX.util.Reg;
import iAPX.util.Type;
import iAPX.util.Util;

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
		if(Option.GENERATE_Q_CODE) {
			GQfetch();
		} else {
			doFetch("FETCH");
		}
	}

	
	public static void doFetch(String comment) {
		if(CTStack.TOS instanceof Address addr) {
			if(DEBUG) IO.println("FETCH.doFetch: addr="+addr);
			Type type = addr.type;
			Global.PSEG.emit(new SVM_LOAD(addr.objadr.addOffset(addr.offset), addr.xReg, type.size()), comment + " " +type);
			CTStack.pop(); CTStack.pushTempVAL(type, 1, "GQFetch: ");
			if(DEBUG) {
				CTStack.dumpStack("GQfetch: "+comment);
				Global.PSEG.dump("GQfetch: "+comment);
			}
//		} else {
//			Global.PSEG.emit(new SVM_NOOP(), "GQfetch: "+comment);
		}
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
