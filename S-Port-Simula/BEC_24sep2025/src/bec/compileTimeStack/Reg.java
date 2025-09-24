package bec.compileTimeStack;

import java.util.BitSet;

import bec.instruction.POP;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.Value;
import bec.value.dataAddress.DataAddress;
import bec.value.dataAddress.GeneralAddress;
import bec.value.dataAddress.RemoteAddress;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_LOAD;
import bec.virtualMachine.SVM_LOADC;
import bec.virtualMachine.SVM_POP2REG;

public class Reg {
	public final static int
//    qAL=0,qCL=1,qDL=2,qBL=3,qAH=4,qCH=5,qDH=6,qBH=7,
//    qAX=8,qCX=9,qDX=10,qBX=11,qSP=12,qBP=13,qSI=14,qDI=15,
    qEAX=1, qECX=2, qEDX=3, qEBX=4,
    qEAI=5, qECI=6, qEDI=7, qEBI=8,
//    QESP=20,qEBP=21,qESI=22,qEDI=23,
//    qF=24,qM=25,
    rMax=9;

	private static Value[] reg; // = new Value[rMax];
	private static BitSet mindMask = new BitSet();

	
	public static void init() {
		reg = new Value[rMax];
		mindMask = new BitSet();
	}
	
	
	public static void reads(String who, DataAddress adr) {
		int iReg = adr.getIreg(); if(iReg != 0) mindMask.set(iReg, false);
		int bReg = adr.getBreg(); if(bReg != 0) mindMask.set(bReg, false);
//		if(adr instanceof RemoteAddress rem) reads(who, rem.xReg);
//		if(adr instanceof RelAddress ref) reads(ref.xReg);
		if(adr instanceof GeneralAddress gaddr) reads(who, gaddr.base);
		IO.println("Reg.reads: " + who + adr + " ==> UsedRegs=" + edCTRegused());
	}
	
	public static void reads(String who, int... reg) {
		for(int r:reg) {
			if(! mindMask.get(r)) Util.IERR("Reg.reads: Register " + edReg(r) + " is not minded");
			mindMask.set(r, false);
			IO.println("Reg.reads: " + who + edReg(r) + " ==> UsedRegs=" + edCTRegused());
		}
	}
	
	public static void writes(String who, int... reg) {
		for(int r:reg) {
			if(mindMask.get(r)) Util.IERR("Reg.writes: Register " + edReg(r) + " is already minded");
			mindMask.set(r, true);
			IO.println("Reg.writes: " + who + edReg(r) + " ==> UsedRegs=" + edCTRegused());
//			Thread.dumpStack();
		}
	}
	
	public static void checkMindMaskEmpty() {
		if(! mindMask.isEmpty()) Util.IERR("Reg.checkMindMaskEmpty: FAILED: " + edCTRegused());
	}
	
//	protected void minds(int... reg) {
//		
//	}
	
	public static int nRegUsed() {
		return mindMask.cardinality();
	}

	public static Value getValue(int xReg) {
//		if(DEBUG) IO.println("Reg.getValue: reg="+edReg(reg));
		if(! Global.duringEXEC()) Util.IERR("");
		return reg[xReg-1];
	}

	public static int getIntValue(int xReg) {
//		if(DEBUG) IO.println("RTRegister.getIntValue: reg="+edReg(reg));
		if(! Global.duringEXEC()) Util.IERR("");
		IntegerValue ival = (IntegerValue) reg[xReg-1];
		return (ival == null)? 0 : ival.value;
	}

	public static int getFreeReg() {
//		if (! mindMask.get(qEAX)) return qEAX;
//		if (! mindMask.get(qEBX)) return qEBX;
		if (! mindMask.get(qECX)) return qECX;
		if (! mindMask.get(qEDX)) return qEDX;
		if (! mindMask.get(qEAI)) return qEAI;
		if (! mindMask.get(qEBI)) return qEBI;
		if (! mindMask.get(qECI)) return qECI;
		if (! mindMask.get(qEDI)) return qEDI;
	    Util.IERR("FreePartReg -- Not available"); return qEAX;
	}

//	public static int FreePartReg() { // export range(0:nregs) res;
//		if ((mindMask & uEAX) == 0) return qEAX;
//		if ((mindMask & uEDX) == 0) return qEDX;
//		if ((mindMask & uECX) == 0) return qECX;
//	    if ((mindMask & uEBX) == 0) return qEBX;
//	    Util.IERR("FreePartReg -- Not available"); return qEAX;
//	}

	public static String edReg(int reg) {
		switch(reg) {
			case qEAX: return "EAX"; case qECX: return "ECX";
			case qEBX: return "EBX"; case qEDX: return "EDX";
			case qEAI: return "EAI"; case qECI: return "ECI";
			case qEBI: return "EBI"; case qEDI: return "EDI";
//			case qESP: return "ESP"; case qEBP: return "EBP";
//			case qESI: return "ESI"; case qEDI: return "EDI";
//			case qAL:  return "AL";  case qCL:  return "CL"; case qDL: return "DL";
//			case qBL:  return "BL";  case qAH:  return "AH"; case qCH: return "CH";
//			case qDH:  return "DH";  case qBH:  return "BH"; case qAX: return "AX";
//			case qCX:  return "CX";  case qDX:  return "DX"; case qBX: return "BX";
//			case qF:   return "F";   case qM:   return "M";
			default:   return "R" + reg;
		}
	}

	public static String toLine() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(int i=0;i<rMax;i++) {
			if(reg[i] != null) {
				if(first) sb.append("xReg: "); else sb.append(", "); first = false;
				sb.append(edReg(i+1));
				sb.append('(').append(reg[i]).append(')');
			}
		}
		return sb.toString();
	}

	public static void getTosAdjustedIn86(int reg) {
//	begin range(0:255) nbyte; infix(ValueItem) itm; range(0:MaxByte) type,cTYP;
//	%+D   RST(R_GetTosAdjustedIn86);
		if(CTStack.TOS() == null) Util.IERR("CODER.GetTosAdjusted-1");
		Type type = CTStack.TOS().type; int nbyte = type.size;
//	      if type<=T_MAX then cTYP = cTYPE(type) else cTYP = cANY endif;
		if(nbyte == 0) Util.IERR("CODER.GetTosAdjustedIn86-1");
//	%+C   if nbyte > AllignFac
//	%+C   then WARNING("CODER.GetTosAdjusted-2");
//	%+C        repeat while nbyte > AllignFac
//	%+C        do qPOPKill(AllignFac); nbyte = nbyte-AllignFac endrepeat
//	%+C   endif;
		
//		if(CTStack.TOS().kind == Kind.K_Coonst) {
		if(CTStack.TOS() instanceof ConstItem cns) {
			POP.qPOPKill(nbyte); Value value = cns.value;
			switch(type.tag) {
			case Scode.TAG_OADDR,Scode.TAG_PADDR,Scode.TAG_RADDR:
//	%+E             case 0:adrMax (value.base.kind)
//	%+E             case 0: Qf2(qLOADC,0,reg,cTYP,0) -- NONE/NOWHERE/NOBODY
//	%+E             case reladr,locadr: Qf3(qLOADA,0,reg,cTYP,value.base);
//	%+E             case segadr,fixadr,extadr: Qf2b(qLOADC,0,reg,cTYP,0,value.base)
//	%+E %+C         otherwise IERR("CODER.GetTosAdjusted-4")
//		%+E        endcase;
				Util.IERR("");
				break;
			case Scode.TAG_BOOL, Scode.TAG_CHAR, Scode.TAG_INT, Scode.TAG_REAL, Scode.TAG_SIZE, Scode.TAG_AADDR:
//				Qf2(qLOADC,0,reg,cTYP,value.int);
//				new Q_LOADC(reg,value);
			Global.PSEG.emit(new SVM_LOADC(reg, value), "getTosAdjustedIn86: ");
				break;
			default: Util.IERR("CODER:GetTosAdjusted-6"); // Qf2(qLOADC,0,reg,cTYP,0);
			}
		} else {
			getTosValueIn86(reg);
		}
	}

	public static void getTosValueIn86(int reg) {
		// Må ikke bruke qDI p.g.a. RUPDATE.
		Type type = CTStack.TOS().type;
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//		switch(CTStack.TOS.kind) {
//		case K_Temp,K_Result,K_Coonst:
////			Qf1(qPOPR,reg,cTYP);
//			new Q_POPR(reg);
//			break;
//		case K_Address:
//			MemAddr opr = MemAddr.getTosSrcAdr();
////	        Qf4c(qLOAD,0,reg,cTYP,0,opr,0);
//			new Q_LOAD(reg,opr);
//			CTStack.pop(); CTStack.pushTemp(type);
//		default: Util.IERR("");
//		}
		
		if(CTStack.TOS() instanceof AddressItem) {
			DataAddress opr = getTosSrcAdr();
			Global.PSEG.emit(new SVM_LOAD(reg, opr), "getTosValueIn86: ");
			CTStack.pop(); CTStack.pushTempREF(type, 1, "getTosValueIn86: ");
		} else if(CTStack.TOS() instanceof TempItem) {
			Global.PSEG.emit(new SVM_POP2REG(reg), "getTosValueIn86: ");
		} else Util.IERR("");
	}


	public static DataAddress getTosSrcAdr() { // export infix(MemAddr) memaddr;
		AddressItem tos = (AddressItem) CTStack.TOS();
		IO.println("Reg.getTosSrcAdr: " + tos);
		DataAddress objadr = tos.objadr;
		objadr.ofst = objadr.ofst + tos.offset;
		switch(tos.atrState) {
	      case AddressItem.NotStacked: break; // Nothing
	      case AddressItem.FromConst:  POP.qPOPKill(1); break; // qPOPKill(AllignFac);
	      case AddressItem.Calculated:
//	    	  if(objadr.getIreg() != 0) Util.IERR("getTosSrcAdr-0: " + edReg(objadr.getIreg()));
	    	  if(objadr.getIreg() != 0) IO.println("ERROR getTosSrcAdr-0: " + edReg(objadr.getIreg()));
//	    	  Qfunc.Qf1(Opcode.qPOPR, Reg.qESI,cVAL);
//	    	  new Q_POPR(Reg.qESI);
//	    	  int qESI = RTRegister.getFreeAddressReg();
	    	  int qESI = Reg.getFreeReg();
	    	  Global.PSEG.emit(new SVM_POP2REG(qESI), "getTosSrcAdr: ");
	    	  // if objadr.sibreg=NoIBREG then objadr.sibreg = bESI+iESI;
	    	  // else objadr.sibreg = wOR(wAND(objadr.sibreg,BaseREG),iESI) endif;
	    	  objadr.setIreg(qESI);
		}
		switch(tos.objState) {
	      case AddressItem.NotStacked: break; // Nothing
	      case AddressItem.FromConst:  POP.qPOPKill(1); break; // qPOPKill(AllignFac);
	      case AddressItem.Calculated:
//	    	  Qfunc.Qf1(Opcode.qPOPR, Reg.qEBX,cOBJ);
//	    	  new Q_POPR(Reg.qEBX);
//	    	  int qEBX = RTRegister.getFreeAddressReg();
	    	  int qEBX = Reg.getFreeReg();
	    	  Global.PSEG.emit(new SVM_POP2REG(qEBX), "getTosSrcAdr: ");
	    	  // objadr.sibreg = bOR(bAND(objadr.sibreg,IndxREG),bEBX);
	    	  objadr.setBreg(qEBX);
		}
		return objadr;
	}

	public static DataAddress getTosDstAdr() { // export infix(MemAddr) a;
		AddressItem tos = (AddressItem) CTStack.TOS();
		DataAddress objadr = tos.objadr;
		objadr.ofst = objadr.ofst + tos.offset;
		
		switch(tos.objState) {
	      case AddressItem.NotStacked: break; // Nothing
	      case AddressItem.FromConst:  POP.qPOPKill(1); break; // qPOPKill(AllignFac);
	      case AddressItem.Calculated:
	    	  if(objadr.getIreg() != 0) Util.IERR("GetTosDstAdr-0");
//	    	  Qf1(qPOPR,qEDI,cVAL);
//	    	  new Q_POPR(Reg.qEDI);
//	    	  int qEDI = RTRegister.getFreeAddressReg();
	    	  int qEDI = Reg.getFreeReg();
	    	  Global.PSEG.emit(new SVM_POP2REG(qEDI), "getTosSrcAdr: ");
	    	  // if a.sibreg=NoIBREG then a.sibreg:=bEDI+iEDI;
	    	  // else a.sibreg:=wOR(wAND(a.sibreg,BaseREG),iEDI) endif;
	    	  objadr.setIreg(qEDI);
		}
		switch(tos.objState) {
	      case AddressItem.NotStacked: break; // Nothing
	      case AddressItem.FromConst:  POP.qPOPKill(1); break; // qPOPKill(AllignFac);
	      case AddressItem.Calculated:
//	    	  Qf1(qPOPR,qEBX,cOBJ);
//	    	  new Q_POPR(Reg.qEBX);
//	    	  int qEBX = RTRegister.getFreeAddressReg();
	    	  int qEBX = Reg.getFreeReg();
	    	  Global.PSEG.emit(new SVM_POP2REG(qEBX), "getTosSrcAdr: ");
	    	  // a.sibreg:=bOR(bAND(a.sibreg,IndxREG),bEBX);
	    	  objadr.setBreg(qEBX);
		}
		return objadr;
	}

	public static String edCTRegused(String title) {
		String s = (mindMask.cardinality() == 0)? "null" : edCTRegused();
		return title + s;
	}

	public static String edCTRegused() {
		String s = ""; String sep = "";
		for (int i = mindMask.nextSetBit(0); i >= 0; i = mindMask.nextSetBit(i+1)) {
			s += sep + edReg(i); sep = "|";
			// operate on index i here
			if (i == Integer.MAX_VALUE) {
				break; // or (i+1) would overflow
			}
		}
		return s;
	}

}
