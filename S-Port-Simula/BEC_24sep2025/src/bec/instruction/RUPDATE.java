package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.Reg;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.dataAddress.DataAddress;
import bec.value.dataAddress.RelAddress;
import bec.virtualMachine.SVM_LOADA;
import bec.virtualMachine.SVM_POP2MEM;
import bec.virtualMachine.SVM_PUSHR;

public abstract class RUPDATE extends Instruction {
	
	private static final boolean DEBUG = false;
	
	/**
	 * assign_instruction ::= assign | update | rupdate
	 * 
	 * rupdate (dyadic)
	 * 
	 * check TOS ref;
	 * force SOS value; check types identical;
	 * pop;
	 * 
	 * This instruction (“reverse update”) works almost like update with the sole exception that the
	 * roles of TOS and SOS are interchanged, i.e. the value transfer is from SOS to TOS.
	 */
	public static void ofScode() {
		boolean TESTING = true;
		
		if(TESTING) {
			GQrupdate();
		} else {
			if(DEBUG)
				CTStack.dumpStack("RUPDATE.ofScode: ");
			CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
			AddressItem adr = (AddressItem) CTStack.pop();
			CTStack.forceTosValue();			
			if(DEBUG) {
				IO.println("RUPDATE.ofScode: adr="+adr.getClass().getSimpleName()+"  "+adr);
				IO.println("RUPDATE.ofScode: sos="+CTStack.TOS());
	//			Util.IERR("");
	//			CTStack.dumpStack("RUPDATE-2: ");
			}
	//		Global.PSEG.emit(new SVM_STORE(adr.objadr.addOffset(adr.offset), adr.getIndexReg(), adr.size), "RUPDATE: "); // Store into adr
			Global.PSEG.emit(new SVM_POP2MEM(adr.objadr.addOffset(adr.offset), adr.size), "RUPDATE: "); // Store into adr
			
	//		RTRegister.clearReg(adr.getIndexReg());
	//		if(adr.objadr instanceof RemoteAddress rem) RTRegister.clearReg(rem.xReg);
	//		else if(adr.objadr instanceof ReferAddress rel) RTRegister.clearReg(rel.xReg);
		}
	}

	
	public static void GQrupdate() {
//	begin infix(MemAddr) opr; range(0:MaxByte) cTYP;
//	      range(0:MaxWord) nbyte; range(0:MaxType) st,dt;
		CTStack.checkTosRef(); CTStack.checkSosValue(); CTStack.checkTypesEqual();
//	      st:=TOS.suc.type; dt:=TOS.type; nbyte:=TTAB(dt).nbyte;
//	      if dt<=T_MAX then cTYP:=cTYPE(dt) else cTYP:=cANY endif;
		Type type = CTStack.TOS().type;
		if(type.size() == 0) Util.IERR("CODER.GQrupdate-1");
		DataAddress opr = Reg.getTosDstAdr(); CTStack.pop();
		if(CTStack.TOS() instanceof AddressItem) {
//	    	Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg:=bEDI+iEDI;
//	    	opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
			int qEDI = Reg.getFreeReg();
			Global.PSEG.emit(new SVM_LOADA(qEDI, opr), "RUPDATE: ");
			opr = new RelAddress(0);
			opr.setBreg(qEDI);
			opr.setIreg(qEDI);
			Util.IERR("SJEKK DETTE");
		}
//	      case 0:12 (if nbyte<=12 then nbyte else 0)
//	      when 1: GetTosAsBYT1(qAL);
//	              PreMindMask:=wOR(PreMindMask,uAL); Qf1(qPUSHR,qAL,cTYP);
//	              Qf3(qSTORE,0,qAL,cTYP,opr)
//	      when 2: GetTosAsBYT2(qAX);
//	              PreMindMask:=wOR(PreMindMask,uAX); Qf1(qPUSHR,qAX,cTYP);
//	              Qf3(qSTORE,0,qAX,cTYP,opr)
//	      when 4: GetTosAsBYT4(qEAX);
//	              PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	              Qf3(qSTORE,0,qEAX,cTYP,opr)
		
//		Qf1(qPUSHR,qEAX,cTYP);
//		Qf3(qSTORE,0,qEAX,cTYP,opr)
		IO.println("RUPDATE.GQrupdate: regUsed="+Reg.edCTRegused());
//		Global.PSEG.emit(new SVM_PUSHR(Reg.qEAX), "RUPDATE: ");
		Global.PSEG.emit(new SVM_POP2MEM(opr, type.size), "RUPDATE: ");
		
		
//	      when 8: GetTosValueIn86R3(qEAX,qECX,0);
//	              PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//	              PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	              PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	              opr.rela.val:=opr.rela.val+AllignFac;
//	              Qf3(qSTORE,0,qECX,cTYP,opr);
//	      when 12: GetTosValueIn86R3(qEAX,qECX,qEDX);
//	              PreMindMask:=wOR(PreMindMask,uEDX); Qf1(qPUSHR,qEDX,cTYP);
//	              PreMindMask:=wOR(PreMindMask,uECX); Qf1(qPUSHR,qECX,cTYP);
//	              PreMindMask:=wOR(PreMindMask,uEAX); Qf1(qPUSHR,qEAX,cTYP);
//	              PresaveOprRegs(opr); Qf3(qSTORE,0,qEAX,cTYP,opr);
//	              opr.rela.val:=opr.rela.val+AllignFac;
//	              PresaveOprRegs(opr); Qf3(qSTORE,0,qECX,cTYP,opr);
//	              opr.rela.val:=opr.rela.val+AllignFac;
//	              Qf3(qSTORE,0,qEDX,cTYP,opr);
//	      otherwise
//	           if TOS.kind <> K_Address
//	           then
//	                Qf3(qLOADA,0,qEDI,cADR,opr); opr.sibreg:=bEDI+iEDI;
//	                opr.kind:=reladr; opr.rela.val:=0; opr.segmid.val:=0;
//	           endif;
//	           GQfetch;
//	           Qf2(qMOV,0,qESI,cSTP,qESP);
//	           Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	           Qf2(qRSTRW,qRMOV,qCLD,cTYP,qREP);
//	      endcase;
//		Util.IERR("");
	}

}
