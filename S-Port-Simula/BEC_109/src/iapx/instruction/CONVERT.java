package iapx.instruction;

import iapx.CTStack.CTStack;
import iapx.CTStack.StackItem;
import iapx.util.Scode;
import iapx.util.Type;
import iapx.util.Util;

public abstract class CONVERT extends Instruction {

	private static final boolean DEBUG = false;

	/**
	 * convert_instruction ::= convert simple_type
	 * 
	 * TOS must be of simple type, otherwise: error.
	 * 
	 * The TYPE of TOS is changed to the type specified in the instruction, this may imply code generation.
	 */
	public static void ofScode() {
//		CTStack.dumpStack("BEGIN CONVERT.ofScode: ");
		Type toType = Scode.inType();
//		System.out.println("CONVERT: "+tos.getClass().getSimpleName());

		if(CTStack.TOS().type != toType) doConvert(toType);
		
//		CTStack.dumpStack("END CONVERT.ofScode: ");
//		Global.PSEG.dump("END CONVERT.ofScode: ");
//		Util.IERR("");
	}
	
	public static void SVM_GQconvert(Type totype) {
		StackItem TOS = CTStack.TOS;
		Type fromtype = TOS.type;
		if(totype != fromtype) {
			if(DEBUG) System.out.println("CONVERT.doConvert: " + TOS + " ==> " + totype);
			Global.PSEG.emit(new SVM_CONVERT(fromtype.tag.val, totype.tag.val), "");
			CTStack.pop(); CTStack.pushTempVAL(totype, 1, "CONVERT: ");
			TOS.type = totype;
			if(DEBUG) System.out.println("CONVERT.doConvert: " + totype + " ==> " + TOS);
		}
	}

	private static void SVM_doConvert(Type totype) {
		FETCH.doFetch("CONVERT: ");
		StackItem TOS = CTStack.TOS;
		Type fromtype = TOS.type;
		if(DEBUG) System.out.println("CONVERT.doConvert: " + TOS + " ==> " + totype);
		Global.PSEG.emit(new SVM_CONVERT(fromtype.tag.val, totype.tag.val), "");
		CTStack.pop(); CTStack.pushTempVAL(totype, 1, "CONVERT: ");
	}

	public static void GQconvert(Type totype) {
//	begin range(0:MaxType) fromtype; Boolean ILL;
//	      infix(ValueItem) itm; infix(MemAddr) opr; range(0:nregs) a,d;
		FETCH.GQfetch();
		Type fromtype = CTStack.TOS.type;
		IO.println("CONVERT.GQconvert: " + fromtype + " ==> " + totype);
		if(fromtype.equals(totype)) return; // Nothing
		
//	%+D   if fromtype=totype then -- Nothing
//	%+D   elsif fromtype > T_max
//	%+D   then EdWrd(errmsg,fromtype); Ed(errmsg," ==> ");
//	%+D        EdWrd(errmsg,totype); IERR(" CODER:GQconvert-1")
//	%+D   elsif totype > T_max
//	%+D   then EdWrd(errmsg,fromtype); Ed(errmsg," ==> ");
//	%+D        EdWrd(errmsg,totype); IERR(" CODER:GQconvert-2")
//	%+D   else
		IO.println("CONVERT.GQconvert: " + fromtype + " ==> " + totype);
//	      if TOS.kind = K_Coonst then ConvConst(totype)
//	      else ILL:=false;
//	           case 0:T_max (fromtype)
//	           when T_TREAL:       -- temp real
//	                case 0:T_max (totype)
//	                when T_LREAL, -- temp real --> long real
//	                     T_REAL,  -- temp real --> real
//	                     T_WRD4:  -- temp real --> 32bit integer
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTemp(totype);
//	                when T_WRD2,T_BYT2,T_BYT1,T_CHAR:
//	                     PopTosToNPX; PushFromNPX(fromtype,T_WRD4);
//	                     pushTemp(T_WRD4); goto LL1;
//	                otherwise ILL:=true endcase;
//	           when T_LREAL:       -- long real
//	                case 0:T_max (totype)
//	                when T_TREAL: -- long real --> temp real
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTemp(totype);
//	                when T_REAL: -- long real --> real
//	%-E                  if NUMID=NoNPX then EM8CNV4(X_LR2RE)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_REAL);
//	                when T_WRD4: -- long real --> 32bit integer
//	%-E                  if NUMID=NoNPX then EM8CNV4(X_LR2IN)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_WRD4);
//	                when T_WRD2,T_BYT2,T_BYT1,T_CHAR:
//	%-E                  if NUMID=NoNPX then EM8CNV4(X_LR2IN)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,T_WRD4);
//	%-E                  endif;
//	                     pushTemp(T_WRD4); goto LL2;
//	                otherwise ILL:=true endcase;
//	           when T_REAL:       -- real
//	                case 0:T_max (totype)
//	                when T_TREAL: -- real --> temp real
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTemp(T_TREAL);
//	                when T_LREAL: -- real --> long real
//	%-E                  if NUMID=NoNPX then EM4CNV8(X_RE2LR)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_LREAL);
//	                when T_WRD4: -- real --> 32bit integer
//	%-E                  if NUMID=NoNPX then EM4MONAD(X_RE2IN);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_WRD4);
//	                when T_WRD2,T_BYT2,T_BYT1,T_CHAR:
//	%-E                  if NUMID=NoNPX then EM4MONAD(X_RE2IN);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,T_WRD4);
//	%-E                  endif;
//	                     pushTemp(T_WRD4); goto LL3;
//	                otherwise ILL:=true endcase;
//	           when T_WRD4:       -- 4-byte signed integer in 86
//	LL1:LL2:LL3:LL4:LL5:LL6:
//	                case 0:T_max (totype)
//	                when T_TREAL: -- 32bit integer --> temp real
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTemp(T_TREAL);
//	                when T_LREAL: -- 32bit integer --> long real
//	%-E                  if NUMID=NoNPX then EM4CNV8(X_IN2LR);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_LREAL);
//	                when T_REAL: -- 32bit integer --> real
//	%-E                  if NUMID=NoNPX then EM4MONAD(X_IN2RE);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_REAL);
//	                when T_WRD2: -- 32bit integer --> 16bit signed integer
//	                     -- CHECK_RANGE(-32768:32767)
//	                     a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2); Qf1(qPUSHR,a,cVAL);
//	%+E                  Qf1(qPUSHR,WordReg(a),cVAL);
//	                when T_BYT2: -- 32bit integer  --> 16bit unsigned integer
//	                     -- CHECK_RANGE(0:65535)
//	                     a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2); Qf1(qPUSHR,a,cVAL);
//	%+E                  Qf1(qPUSHR,WordReg(a),cVAL);
//	                when T_BYT1, -- 32bit integer --> 1-byte unsigned integer
//	                     T_CHAR: -- 32bit integer --> Character
//	                     -- CHECK_RANGE(0:255)
//	                     a:=FreePartReg;
//	                     NotMindMask:=wOR(NotMindMask,uMask(HighPart(%a%)));
//	                     Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2);
//	                     Qf1(qPUSHR,LowPart(%a%),cVAL);
//	                otherwise ILL:=true endcase;
//	           when T_WRD2:       -- 2-byte signed integer in 86
//	                case 0:T_max (totype)
//	                when T_TREAL,T_LREAL,T_REAL:   --> any real
//	                     Qf1(qPOPR,qAX,cVAL);
//	%-E                  Qf1(qCWD,qAX,cVAL);
//	%-E                  Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E                  Qf2(qMOV,qSEXT,qAX,cVAL,qAX); Qf1(qPUSHR,qEAX,cVAL);
//	                     Pop; pushTemp(T_WRD4); goto LL4;
//	                when T_WRD4:   --> 4-byte signed integer in 86
//	                     Qf1(qPOPR,qAX,cVAL);
//	%-E                  Qf1(qCWD,qAX,cVAL);
//	%-E                  Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E                  Qf2(qMOV,qSEXT,qAX,cVAL,qAX); Qf1(qPUSHR,qEAX,cVAL);
//	                when T_BYT2:   --> 2-byte unsigned integer in 86
//	                     -- CHECK_POSITIVE
//	                when T_BYT1,  -- real etc. --> 1-byte unsigned int in 86
//	                     T_CHAR:  -- real etc. --> Character
//	                     -- CHECK_RANGE(0:255)
//	                     a:=FreePartReg;
//	                     NotMindMask:=wOR(NotMindMask,uMask(HighPart(%a%)));
//	                     Qf1(qPOPR,WordReg(a),cVAL); Qf1(qPUSHR,LowPart(%a%),cVAL);
//	                otherwise ILL:=true endcase;
//	           when T_BYT2:       -- 2-byte unsigned integer in 86
//	                case 0:T_max (totype)
//	                when T_TREAL,T_LREAL,T_REAL:   --> any real
//	%-E                  a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  d:=FreePartReg; Qf2(qPUSHC,0,d,cVAL,0); Qf1(qPUSHR,a,cVAL);
//	%+E                  a:=FreePartReg; GetTosAdjustedIn86(a);
//	%+E                  Qf1(qPUSHR,a,cVAL);
//	                     Pop; pushTemp(T_WRD4); goto LL5;
//	                when T_WRD4:   --> 4-byte signed integer in 86
//	%-E                  a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  d:=FreePartReg; Qf2(qPUSHC,0,d,cVAL,0); Qf1(qPUSHR,a,cVAL);
//	%+E                  a:=FreePartReg; GetTosAdjustedIn86(a);
//	%+E                  Qf1(qPUSHR,a,cVAL);
//	                when T_WRD2:   --> 2-byte signed integer in 86
//	                when T_BYT1,  -- real etc. --> 1-byte unsigned int in 86
//	                     T_CHAR:  -- real etc. --> Character
//	                     -- CHECK_RANGE(0:255)
//	                     a:=FreePartReg;
//	                     NotMindMask:=wOR(NotMindMask,uMask(HighPart(%a%)));
//	                     Qf1(qPOPR,WordReg(a),cVAL); Qf1(qPUSHR,LowPart(%a%),cVAL);
//	                otherwise ILL:=true endcase;
//	           when T_BYT1,       -- 1-byte unsigned integer in 86
//	                T_CHAR:       -- Character
//	                case 0:T_max (totype)
//	                when T_TREAL,T_LREAL,T_REAL:   --> any real
//	                     a:=FreePartReg; GetTosAdjustedIn86(a);
//	%-E                  d:=FreePartReg; Qf2(qPUSHC,0,d,cVAL,0);
//	                     Qf1(qPUSHR,a,cVAL);
//	                     Pop; pushTemp(T_WRD4); goto LL6;
//	                when T_WRD4:  --> 4-byte signed integer in 86
//	                     a:=FreePartReg; GetTosAdjustedIn86(a);
//	%-E                  d:=FreePartReg; Qf2(qPUSHC,0,d,cVAL,0);
//	                     Qf1(qPUSHR,a,cVAL);
//	                when T_WRD2,  --> 2-byte signed integer in 86
//	                     T_BYT2:  --> 2-byte unsigned integer in 86
//	                     a:=WordReg(FreePartReg);
//	                     GetTosAdjustedIn86(a); Qf1(qPUSHR,a,cVAL);
//	                when T_BYT1,  --> 1-byte unsigned integer in 86
//	                     T_CHAR:  --> Character
//	                otherwise ILL:=true endcase;
//	           when T_OADDR:
//	                if totype = T_GADDR
//	                then Qf2(qPUSHC,0,FreePartReg,cVAL,0) else ILL:=true endif;
//	           when T_GADDR:
//	                if totype = T_AADDR
//	                then a:=FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2);
//	                     qPOPKill(AllignFac); Qf1(qPUSHR,a,cVAL);
//	                elsif totype = T_OADDR
//	                then qPOPKill(AllignFac) else ILL:=true endif;
//	           otherwise ILL:=true endcase;
//	           if ILL then ERROR("Type conversion is undefined") endif;
//	           Pop; pushTemp(totype);
//	      endif;
//	%+D   endif;
		Util.IERR("");
	}

}
