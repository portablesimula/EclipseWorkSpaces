package iAPX.util;

import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import iAPX.ctStack.CTStack;
import iAPX.enums.Kind;
import iAPX.qInstr.Q_DYADR;
import iAPX.qInstr.Q_MOV;

public class Relation {

	public static Scode GQrelation() { // export range(0:255) res;
//	begin range(0:MaxType) at; range(0:255) a,d,qCond,qType; range(0:255) srel;
//	      infix(MemAddr) opr; range(0:MaxWord) s,nbyte; range(0:MaxByte) cTYP;
//	%-E   range(0:nregs) segreg;
		
//	      xFJUMP = none;
		CTStack.checkTypesEqual(); CTStack.checkSosValue();
		Scode.inputInstr(); Scode srel = Scode.curinstr;
		switch(srel) {
		case Scode.S_LT, Scode.S_LE, Scode.S_EQ,
		     Scode.S_GE, Scode.S_GT, Scode.S_NE: break; // OK
		default: Util.IERR("Illegal Relation: " + srel);
	}
		Type at = CTStack.TOS.type; boolean reversed = false;
		IO.println("Relation.GQrelation: tosType=" + at + ", sosType=" + CTStack.TOS.suc.type);
		if(at.isBasic) at = Type.arithType(at,CTStack.TOS.suc.type);
		int nbyte = at.size;
		if(nbyte == 0) Util.IERR("CODER.GQrel-0");
//	      if TTAB(at).kind=tFloat
//	      then reversed = false;
//	           GQconvert(at); PopTosToNPX;
//	           GQconvert(at); PopTosToNPX;
//	%+S        WARNING("Floating point Relation");
//	%+E        Qf1b(qFDYAD,qFCOM,Fwf87(at),cTYP);
//	      else
		
		if(nbyte == 1) { // nbyte <= AllignFac
//			a = accreg(nbyte); d = datareg(nbyte);
			int a = Reg.qEAX; int d = Reg.qEDX;
			Convert.GQconvert(at); Reg.getTosAdjustedIn86(d); CTStack.pop();
			Convert.GQconvert(at); Reg.getTosAdjustedIn86(a); CTStack.pop();
			reversed = false;
//			Qf2(qDYADR,qCMP,a,cTYP,d);
			new Q_DYADR(Q_DYADR.Subc.qCMP,a,d);
		} else if(nbyte > 1 ) {
			Util.IERR("");
//	%+E        elsif nbyte=8
//	%+E        then GetTosValueIn86R3(qEAX,qEDX,0); Pop;
//	%+E             GetTosValueIn86R3(qECX,qEBX,0); Pop;
//	%+E             Qf2(qDYADR,qCMP,qECX,cTYP,qEAX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qMOV,0,qAL,cVAL,qAH);
//	%+E             Qf2(qDYADR,qCMP,qEBX,cTYP,qEDX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAH,cVAL,qAL);
//	%+E             Qf1(qSAHF,0,cVAL);
//	%+E        elsif nbyte=12
//	%+E        then GetTosValueIn86R3(qEAX,qECX,qEDX); Pop;
//	%+E             GQfetch; -- To prevent SI from being destroyed
//	%+E             GetTosValueIn86R3(qEDI,qESI,qEBX); Pop;
//	%+E             Qf2(qDYADR,qCMP,qEDI,cTYP,qEAX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qMOV,0,qAL,cVAL,qAH);
//	%+E             Qf2(qDYADR,qCMP,qESI,cTYP,qECX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAL,cVAL,qAH);
//	%+E             Qf2(qDYADR,qCMP,qEBX,cTYP,qEDX);
//	%+E             Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAH,cVAL,qAL);
//	%+E             Qf1(qSAHF,0,cVAL);
		} else if(CTStack.TOS.kind != Kind.K_Address) {
//			Qf2(qMOV,0,qEDI,cSTP,qESP);
			new Q_MOV(Reg.qEDI, Reg.qESP);
//	                if TOS.suc.kind=K_Address
//	                then
//	%+E                  opr = GetSosAddr(qEBX,qESI);
//	%+E                  Qf3(qLOADA,0,qESI,cADR,opr);
//	                else
//	                     opr.kind = reladr; opr.segmid.val = 0;
//	                     opr.rela.val = wAllign(%nbyte%);
//	%+E                  opr.sibreg = bEDI+iEDI;
//	%+E                  PreMindMask = wOR(PreMindMask,uEDI)
//	%+E                  Qf3(qLOADA,0,qESI,cADR,opr);
//	                endif;
//	%+E             Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                Qf2(qRSTRW,qRCMP,qCLD,cTYP,qREPEQ);
//	                Qf1(qLAHF,0,cVAL); qPOPKill(nbyte);
//	                if TOS.suc.kind=K_Address
//	                then repeat while SosAdrNwk>0
//	                     do qPOPKill(AllignFac);
//	                        SosAdrNwk = SosAdrNwk-1;
//	                     endrepeat;
//	                else qPOPKill(nbyte) endif;
//	                Qf1(qSAHF,0,cVAL); Pop; Pop;
			Util.IERR("");
		} else { // TOS.kind=K_Address or TOS.kind=K_Content */
//	                opr = GetTosDstAdr;
//	%+E             Qf3(qLOADA,0,qEDI,cADR,opr);
//	                Pop;
//	                if  (TOS.kind<>K_Address)
//	                then s = nbyte;
//	%+E                  Qf2(qMOV,0,qESI,cSTP,qESP);
//	                else s = 0;
//	                     opr = GetTosSrcAdr;
//	%+E                  Qf3(qLOADA,0,qESI,cADR,opr);
//	                endif;
//	%+E             Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                Qf2(qRSTRW,qRCMP,qCLD,cTYP,qREPEQ);
//	                if s<>0
//	                then Qf1(qLAHF,0,cVAL); qPOPKill(s);
//	                     Qf1(qSAHF,0,cVAL);
//	                endif;
//	                Pop;
			Util.IERR("");
		}
//	      if    at=T_INT  then qtype = q_ILT
//	      elsif at=T_WRD2 then qType = q_ILT else qType = q_WLT endif;
		
//	      qCond = (srel-S_LT)+qType;
//	      if reversed then qCond = RevQcond(qCond) endif;
//	      res =  qCond;
		if(reversed) srel = rev(srel);
//		Util.IERR("");
		return srel;
	}
	
	public static Scode not(Scode rel) {
		switch(rel) {
		case Scode.S_LT: return Scode.S_GE;
		case Scode.S_LE: return Scode.S_GT;
		case Scode.S_EQ: return Scode.S_NE;
		case Scode.S_GE: return Scode.S_LT;
		case Scode.S_GT: return Scode.S_LE;
		case Scode.S_NE: return Scode.S_EQ;
		}
		Util.IERR("Illegal Relation: " + rel);
		return rel;
	}
	
	public static Scode rev(Scode rel) {
		switch(rel) {
			case Scode.S_LT: return Scode.S_GT; // lhs <  rhs   ==   rhs >  lhs
			case Scode.S_LE: return Scode.S_GE; // lhs <= rhs   ==   rhs >= lhs
			case Scode.S_EQ: return Scode.S_EQ; // lhs == rhs   ==   rhs == lhs
			case Scode.S_GE: return Scode.S_LE; // lhs >= rhs   ==   rhs <= lhs
			case Scode.S_GT: return Scode.S_LT; // lhs >  rhs   ==   rhs <  lhs
			case Scode.S_NE: return Scode.S_NE; // lhs != rhs   ==   rhs != lhs
			default: Util.IERR("Illegal Relation: " + rel); return rel;
		}
	}
	

	
}
