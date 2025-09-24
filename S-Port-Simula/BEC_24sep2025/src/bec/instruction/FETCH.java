package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.Reg;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.dataAddress.DataAddress;
import bec.virtualMachine.SVM_PUSH;

public abstract class FETCH extends Instruction {

	private static final boolean DEBUG = false;

	/**
	 * addressing_instruction ::= fetch
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
		GQfetch("FETCH");
	}

	
//	public static void doFetch(String comment) {
//		if(CTStack.TOS() instanceof AddressItem addr) {
//			if(DEBUG) IO.println("FETCH.doFetch: addr="+addr);
//			Type type = addr.type;
////			Global.PSEG.emit(new SVM_PUSH(addr.objadr.addOffset(addr.offset), addr.getIndexReg(), type.size()), comment + " " +type);
//			Global.PSEG.emit(new SVM_PUSH(addr.objadr.addOffset(addr.offset), type.size()), comment + " " +type);
//			CTStack.pop(); CTStack.pushTempVAL(type, 1, "GQFetch: ");
//			
////			RTRegister.clearReg(addr.getIndexReg());
////			if(addr.objadr instanceof RemoteAddress rem) RTRegister.clearReg(rem.xReg);
////			else if(addr.objadr instanceof ReferAddress rel) RTRegister.clearReg(rel.xReg);
//			Reg.reads(addr.objadr);
//			
//			if(DEBUG) {
//				CTStack.dumpStack("GQfetch: "+comment);
//				Global.PSEG.dump("GQfetch: "+comment);
//			}
//		}
//	}

//	Visible Routine GQfetch  ; --  Må ikke bruke qDI(se rupdate) --
//	begin infix(MemAddr) opr; range(0:MaxType) type;
//	      range(0:MaxWord) nbyte; range(0:MaxByte) cTYP;
//	      if TOS.kind=K_Address
//	      then
//	           opr:=GetTosSrcAdr;
//	           type:=TOS.type; nbyte:=TTAB(type).nbyte;
//	           if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	           RST(R_GQfetch);
//	           if TraceMode > 1
//	           then outstring("***GQfetch "); edtype(sysout,type);
//	                outstring("  nbyte="); outword(nbyte);
//	                printout(sysout); DumpStack;
//	           endif;
//	           if nbyte=0 then IERR("CODER.GQfetch-1") endif;
//	           if nbyte <= AllignFac then Qf4(qPUSHM,0,0,cTYP,nbyte,opr)
//	           elsif nbyte <= (3*AllignFac)
//	           then opr.rela.val:=opr.rela.val+nbyte-AllignFac;
//	                repeat if nbyte>AllignFac then PresaveOprRegs(opr) endif;
//	                   Qf4(qPUSHM,0,0,cTYP,AllignFac,opr); nbyte:=nbyte-AllignFac;
//	                while nbyte <> 0
//	                do opr.rela.val:=opr.rela.val-AllignFac endrepeat;
//	           else Qf4(qPUSHM,0,0,cTYP,nbyte,opr) endif;
//	           Pop; pushTemp(type);
//	      endif;
//	end;

	public static void GQfetch(String comment) { // Må ikke bruke qDI(se rupdate) --
		if(CTStack.TOS() instanceof AddressItem addr) {
			if(DEBUG) IO.println("FETCH.GQfetch: addr="+addr);
			DataAddress opr = Reg.getTosSrcAdr();
			Type type = addr.type; int size = type.size();
			if(size == 0) Util.IERR("FETCH.GQfetch-1");
//			Qf4(qPUSHM,0,0,cTYP,nbyte,opr)
			Global.PSEG.emit(new SVM_PUSH(addr.objadr.addOffset(addr.offset), size), comment + " " +type);
			Reg.reads("FETCH.GQfetch: ", addr.objadr);
			
			CTStack.pop(); CTStack.pushTempREF(type, 1, comment);
		}
	}

}
