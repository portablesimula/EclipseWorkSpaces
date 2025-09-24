package iapx.util;

import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.Kind;
import iapx.CTStack.CTStack;
import iapx.qInstr.Q_DYADR;
import iapx.qInstr.Q_MOV;
import iapx.value.BooleanValue;
import iapx.value.IntegerValue;
import iapx.value.Value;

public class Relation {
	public Scode relation;
	
	private static final boolean DEBUG = false;

	public Relation(Scode relation) {
		this.relation = relation;
	}

	/**
	 * relation ::= ?lt | ?le | ?eq | ?ge | ?gt | ?ne
	 */
	public static Relation ofScode() {
		Scode.inputInstr();
		Relation rel = new Relation(Scode.curinstr);
		if(DEBUG) {
			IO.println("Relation.ofScode: CurInstr="+Scode.curinstr);
			IO.println("Relation.ofScode: relation="+rel.relation);
		}
		switch(rel.relation) {
			case Scode.S_LT, Scode.S_LE, Scode.S_EQ,
			     Scode.S_GE, Scode.S_GT, Scode.S_NE: break; // OK
			default: Util.IERR("Illegal Relation: " + rel.relation);
		}
		return rel;
	}


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

	public Relation not() {
		switch(this.relation) {
		case Scode.S_LT: return new Relation(Scode.S_GE);
		case Scode.S_LE: return new Relation(Scode.S_GT);
		case Scode.S_EQ: return new Relation(Scode.S_NE);
		case Scode.S_GE: return new Relation(Scode.S_LT);
		case Scode.S_GT: return new Relation(Scode.S_LE);
		case Scode.S_NE: return new Relation(Scode.S_EQ);
		}
		Util.IERR("Illegal Relation: " + this);
		return this;
	}
	
	public Relation rev() {
		switch(this.relation) {
		case Scode.S_LT: return new Relation(Scode.S_GT); // lhs <  rhs   ==   rhs >  lhs
		case Scode.S_LE: return new Relation(Scode.S_GE); // lhs <= rhs   ==   rhs >= lhs
		case Scode.S_EQ: return new Relation(Scode.S_EQ); // lhs == rhs   ==   rhs == lhs
		case Scode.S_GE: return new Relation(Scode.S_LE); // lhs >= rhs   ==   rhs <= lhs
		case Scode.S_GT: return new Relation(Scode.S_LT); // lhs >  rhs   ==   rhs <  lhs
		case Scode.S_NE: return new Relation(Scode.S_NE); // lhs != rhs   ==   rhs != lhs
		}
		Util.IERR("Illegal Relation: " + this);
		return this;
	}
	
	public boolean compare(Value lhs, Value rhs) {
		boolean res = false;
		if(lhs != null) {
			res = lhs.compare(relation, rhs);
		} else if(rhs != null) {
			res = rhs.compare(rev().relation, lhs);
		} else {
			switch(relation) {
				case Scode.S_LT: res = /* 0 < 0  */ false; break;
				case Scode.S_LE: res = /* 0 <= 0 */ true; break;
				case Scode.S_EQ: res = /* 0 == 0 */ true; break;
				case Scode.S_GE: res = /* 0 >= 0 */ true; break;
				case Scode.S_GT: res = /* 0 > 0  */ false; break;
				case Scode.S_NE: res = /* 0 != 0 */ false; break;
			}
		}
//		IO.println("Relation.compare: " + lhs + " " + this + " " + rhs + " ==> " + res);
		return res;
	}
	
	public static boolean compare(int LHS, Scode relation, int RHS) {
		boolean res = false;
		switch(relation) {
			case Scode.S_LT: res = LHS <  RHS; break;
			case Scode.S_LE: res = LHS <= RHS; break;
			case Scode.S_EQ: res = LHS == RHS; break;
			case Scode.S_GE: res = LHS >= RHS; break;
			case Scode.S_GT: res = LHS >  RHS; break;
			case Scode.S_NE: res = LHS != RHS; break;
		}
//		IO.println("Segment.compare: " + LHS + " " + Scode.edInstr(relation) + " " + RHS + " ==> " + res);
//		Util.IERR("");
		return res;		
	}
	
	public String toString() {
		return "" + relation;
	}
	
	// ***********************************************************************************************
	// *** TESTING
	// ***********************************************************************************************
	
	public static void main(String[] args) {
		int nErr = 0;
		Value vTrue = BooleanValue.of(true);
		Value v44 = IntegerValue.of(Type.T_INT,44);
		Value v66 = IntegerValue.of(Type.T_INT,66);
		
//		TEST_Boolean(Scode.S_EQ);
		nErr += TEST(null,  Scode.S_EQ, null, true);
		nErr += TEST(vTrue, Scode.S_EQ, null, false);
		nErr += TEST(null,  Scode.S_EQ, vTrue, false);
		nErr += TEST(vTrue, Scode.S_EQ, vTrue, true);		

//		TEST_Boolean(Scode.S_NE);
		nErr += TEST(null,  Scode.S_NE, null, false);
		nErr += TEST(vTrue, Scode.S_NE, null, true);
		nErr += TEST(null,  Scode.S_NE, vTrue, true);
		nErr += TEST(vTrue, Scode.S_NE, vTrue, false);		

//		TEST_Integer(Scode.S_LT);
		nErr += TEST(null, Scode.S_LT, null, false);
		nErr += TEST(v44,  Scode.S_LT, null, false);
		nErr += TEST(null, Scode.S_LT, v44, true);
		nErr += TEST(v66,  Scode.S_LT, v44, false);
		nErr += TEST(v66,  Scode.S_LT, v66, false);

//		TEST_Integer(Scode.S_LE);
		nErr += TEST(null, Scode.S_LE, null, true);
		nErr += TEST(v44,  Scode.S_LE, null, false);
		nErr += TEST(null, Scode.S_LE, v44, true);
		nErr += TEST(v66,  Scode.S_LE, v44, false);
		nErr += TEST(v66,  Scode.S_LE, v66, true);
		
//		TEST_Integer(Scode.S_EQ);
		nErr += TEST(null, Scode.S_EQ, null, true);
		nErr += TEST(v44,  Scode.S_EQ, null, false);
		nErr += TEST(null, Scode.S_EQ, v44, false);
		nErr += TEST(v66,  Scode.S_EQ, v44, false);
		nErr += TEST(v66,  Scode.S_EQ, v66, true);
		
//		TEST_Integer(Scode.S_GE);
		nErr += TEST(null, Scode.S_GE, null, true);
		nErr += TEST(v44,  Scode.S_GE, null, true);
		nErr += TEST(null, Scode.S_GE, v44, false);
		nErr += TEST(v66,  Scode.S_GE, v44, true);
		nErr += TEST(v66,  Scode.S_GE, v66, true);
		
//		TEST_Integer(Scode.S_GT);
		nErr += TEST(null, Scode.S_GT, null, false);
		nErr += TEST(v44,  Scode.S_GT, null, true);
		nErr += TEST(null, Scode.S_GT, v44, false);
		nErr += TEST(v66,  Scode.S_GT, v44, true);
		nErr += TEST(v66,  Scode.S_GT, v66, false);
		
//		TEST_Integer(Scode.S_NE);
		nErr += TEST(null, Scode.S_NE, null, false);
		nErr += TEST(v44,  Scode.S_NE, null, true);
		nErr += TEST(null, Scode.S_NE, v44, true);
		nErr += TEST(v66,  Scode.S_NE, v44, true);
		nErr += TEST(v66,  Scode.S_NE, v66, false);
		

		IO.println("Number of errors: " + nErr);
	}
	
	private static int TEST(Value lhs, Scode code, Value rhs, boolean expected) {
		Relation rel = new Relation(code);
		boolean b = rel.compare(lhs, rhs);
		if(b != expected) {
			IO.println("TEST: " + lhs + " " + rel + " " + rhs + " ==> " + b);
			return 1;
		}
		return 0;
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private Relation(AttributeInputStream inpt) throws IOException {
		relation = inpt.readShort();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(relation.ordinal());
	}

	public static Relation read(AttributeInputStream inpt) throws IOException {
		return new Relation(inpt);
	}

}
