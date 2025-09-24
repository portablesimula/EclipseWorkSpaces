package iapx.util;

import iapx.Kind;
import iapx.CTStack.CTStack;
import iapx.CTStack.Coonst;
import iapx.instruction.FETCH;
import iapx.instruction.POP;
import iapx.qInstr.Q_PUSHC;
import iapx.value.IntegerValue;
import iapx.value.Value;

public class Convert {

	public static void GQconvert(Type type) {
		FETCH.GQfetch();
		if(! CTStack.TOS.type.equals(type)) DOconvert(type);
	}

//	%+D Visible Routine GQconvert; import range(0:MaxType) totype;
	public static void DOconvert(Type totype) {
//	begin range(0:MaxType) fromtype; Boolean ILL;
//	      infix(ValueItem) value; infix(MemAddr) opr; range(0:nregs) a,d;
//	%+S   GQfetch;
		Type fromtype = CTStack.TOS.type;
//	%+D   if fromtype=totype then -- Nothing
//	%+D   elsif fromtype > T_max
//	%+D   then EdWrd(errmsg,fromtype); Ed(errmsg," ==> ");
//	%+D        EdWrd(errmsg,totype); IERR(" CODER:GQconvert-1")
//	%+D   elsif totype > T_max
//	%+D   then EdWrd(errmsg,fromtype); Ed(errmsg," ==> ");
//	%+D        EdWrd(errmsg,totype); IERR(" CODER:GQconvert-2")
//	%+D   else
		
		if(CTStack.TOS.kind == Kind.K_Coonst) ConvConst(totype);
//	      else ILL = false;
//	           case 0:T_max (fromtype)
//	           case T_TREAL:       -- temp real
//	                case 0:T_max (totype)
//	                case T_LREAL, -- temp real --> long real
//	                     T_REAL,  -- temp real --> real
//	                     T_WRD4:  -- temp real --> 32bit integer
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTemp(totype);
//	                case T_WRD2,T_BYT2,T_BYT1,T_CHAR:
//	                     PopTosToNPX; PushFromNPX(fromtype,T_WRD4);
//	                     pushTemp(T_WRD4); goto LL1;
//	                otherwise ILL = true endcase;
//	           case T_LREAL:       -- long real
//	                case 0:T_max (totype)
//	                case T_TREAL: -- long real --> temp real
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTemp(totype);
//	                case T_REAL: -- long real --> real
//	%-E                  if NUMID=NoNPX then EM8CNV4(X_LR2RE)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_REAL);
//	                case T_WRD4: -- long real --> 32bit integer
//	%-E                  if NUMID=NoNPX then EM8CNV4(X_LR2IN)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_WRD4);
//	                case T_WRD2,T_BYT2,T_BYT1,T_CHAR:
//	%-E                  if NUMID=NoNPX then EM8CNV4(X_LR2IN)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,T_WRD4);
//	%-E                  endif;
//	                     pushTemp(T_WRD4); goto LL2;
//	                otherwise ILL = true endcase;
//	           case T_REAL:       -- real
//	                case 0:T_max (totype)
//	                case T_TREAL: -- real --> temp real
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTemp(T_TREAL);
//	                case T_LREAL: -- real --> long real
//	%-E                  if NUMID=NoNPX then EM4CNV8(X_RE2LR)
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_LREAL);
//	                case T_WRD4: -- real --> 32bit integer
//	%-E                  if NUMID=NoNPX then EM4MONAD(X_RE2IN);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_WRD4);
//	                case T_WRD2,T_BYT2,T_BYT1,T_CHAR:
//	%-E                  if NUMID=NoNPX then EM4MONAD(X_RE2IN);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,T_WRD4);
//	%-E                  endif;
//	                     pushTemp(T_WRD4); goto LL3;
//	                otherwise ILL = true endcase;
//	           case T_WRD4:       -- 4-byte signed integer in 86
//	LL1:LL2:LL3:LL4:LL5:LL6:
//	                case 0:T_max (totype)
//	                case T_TREAL: -- 32bit integer --> temp real
//	                     PopTosToNPX; PushFromNPX(fromtype,totype);
//	                     pushTemp(T_TREAL);
//	                case T_LREAL: -- 32bit integer --> long real
//	%-E                  if NUMID=NoNPX then EM4CNV8(X_IN2LR);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_LREAL);
//	                case T_REAL: -- 32bit integer --> real
//	%-E                  if NUMID=NoNPX then EM4MONAD(X_IN2RE);
//	%-E                  else
//	                          PopTosToNPX; PushFromNPX(fromtype,totype);
//	%-E                  endif;
//	                     pushTemp(T_REAL);
//	                case T_WRD2: -- 32bit integer --> 16bit signed integer
//	                     -- CHECK_RANGE(-32768:32767)
//	                     a = FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2); Qf1(qPUSHR,a,cVAL);
//	%+E                  Qf1(qPUSHR,WordReg(a),cVAL);
//	                case T_BYT2: -- 32bit integer  --> 16bit unsigned integer
//	                     -- CHECK_RANGE(0:65535)
//	                     a = FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2); Qf1(qPUSHR,a,cVAL);
//	%+E                  Qf1(qPUSHR,WordReg(a),cVAL);
//	                case T_BYT1, -- 32bit integer --> 1-byte unsigned integer
//	                     T_CHAR: -- 32bit integer --> Character
//	                     -- CHECK_RANGE(0:255)
//	                     a = FreePartReg;
//	                     NotMindMask = wOR(NotMindMask,uMask(HighPart(%a%)));
//	                     Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2);
//	                     Qf1(qPUSHR,LowPart(%a%),cVAL);
//	                otherwise ILL = true endcase;
//	           case T_WRD2:       -- 2-byte signed integer in 86
//	                case 0:T_max (totype)
//	                case T_TREAL,T_LREAL,T_REAL:   --> any real
//	                     Qf1(qPOPR,qAX,cVAL);
//	%-E                  Qf1(qCWD,qAX,cVAL);
//	%-E                  Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E                  Qf2(qMOV,qSEXT,qAX,cVAL,qAX); Qf1(qPUSHR,qEAX,cVAL);
//	                     Pop; pushTemp(T_WRD4); goto LL4;
//	                case T_WRD4:   --> 4-byte signed integer in 86
//	                     Qf1(qPOPR,qAX,cVAL);
//	%-E                  Qf1(qCWD,qAX,cVAL);
//	%-E                  Qf1(qPUSHR,qDX,cVAL); Qf1(qPUSHR,qAX,cVAL);
//	%+E                  Qf2(qMOV,qSEXT,qAX,cVAL,qAX); Qf1(qPUSHR,qEAX,cVAL);
//	                case T_BYT2:   --> 2-byte unsigned integer in 86
//	                     -- CHECK_POSITIVE
//	                case T_BYT1,  -- real etc. --> 1-byte unsigned int in 86
//	                     T_CHAR:  -- real etc. --> Character
//	                     -- CHECK_RANGE(0:255)
//	                     a = FreePartReg;
//	                     NotMindMask = wOR(NotMindMask,uMask(HighPart(%a%)));
//	                     Qf1(qPOPR,WordReg(a),cVAL); Qf1(qPUSHR,LowPart(%a%),cVAL);
//	                otherwise ILL = true endcase;
//	           case T_BYT2:       -- 2-byte unsigned integer in 86
//	                case 0:T_max (totype)
//	                case T_TREAL,T_LREAL,T_REAL:   --> any real
//	%-E                  a = FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  d = FreePartReg; Qf2(qPUSHC,0,d,cVAL,0); Qf1(qPUSHR,a,cVAL);
//	%+E                  a = FreePartReg; GetTosAdjustedIn86(a);
//	%+E                  Qf1(qPUSHR,a,cVAL);
//	                     Pop; pushTemp(T_WRD4); goto LL5;
//	                case T_WRD4:   --> 4-byte signed integer in 86
//	%-E                  a = FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  d = FreePartReg; Qf2(qPUSHC,0,d,cVAL,0); Qf1(qPUSHR,a,cVAL);
//	%+E                  a = FreePartReg; GetTosAdjustedIn86(a);
//	%+E                  Qf1(qPUSHR,a,cVAL);
//	                case T_WRD2:   --> 2-byte signed integer in 86
//	                case T_BYT1,  -- real etc. --> 1-byte unsigned int in 86
//	                     T_CHAR:  -- real etc. --> Character
//	                     -- CHECK_RANGE(0:255)
//	                     a = FreePartReg;
//	                     NotMindMask = wOR(NotMindMask,uMask(HighPart(%a%)));
//	                     Qf1(qPOPR,WordReg(a),cVAL); Qf1(qPUSHR,LowPart(%a%),cVAL);
//	                otherwise ILL = true endcase;
//	           case T_BYT1,       -- 1-byte unsigned integer in 86
//	                T_CHAR:       -- Character
//	                case 0:T_max (totype)
//	                case T_TREAL,T_LREAL,T_REAL:   --> any real
//	                     a = FreePartReg; GetTosAdjustedIn86(a);
//	%-E                  d = FreePartReg; Qf2(qPUSHC,0,d,cVAL,0);
//	                     Qf1(qPUSHR,a,cVAL);
//	                     Pop; pushTemp(T_WRD4); goto LL6;
//	                case T_WRD4:  --> 4-byte signed integer in 86
//	                     a = FreePartReg; GetTosAdjustedIn86(a);
//	%-E                  d = FreePartReg; Qf2(qPUSHC,0,d,cVAL,0);
//	                     Qf1(qPUSHR,a,cVAL);
//	                case T_WRD2,  --> 2-byte signed integer in 86
//	                     T_BYT2:  --> 2-byte unsigned integer in 86
//	                     a = WordReg(FreePartReg);
//	                     GetTosAdjustedIn86(a); Qf1(qPUSHR,a,cVAL);
//	                case T_BYT1,  --> 1-byte unsigned integer in 86
//	                     T_CHAR:  --> Character
//	                otherwise ILL = true endcase;
//	           case T_OADDR:
//	                if totype = T_GADDR
//	                then Qf2(qPUSHC,0,FreePartReg,cVAL,0) else ILL = true endif;
//	           case T_GADDR:
//	                if totype = T_AADDR
//	                then a = FreePartReg; Qf1(qPOPR,a,cVAL);
//	%-E                  qPOPKill(2);
//	                     qPOPKill(AllignFac); Qf1(qPUSHR,a,cVAL);
//	                elsif totype = T_OADDR
//	                then qPOPKill(AllignFac) else ILL = true endif;
//	           otherwise ILL = true endcase;
//	           if ILL then ERROR("Type conversion is undefined") endif;
//	           Pop; pushTemp(totype);
//	      endif;
//	%+D   endif;
		Util.IERR("");
	}

	//	%title ***    C o n v e r t   C o n s t a n t   V a l u e    ***
	public static void ConvConst(Type totype) {
//	begin integer v; infix(ValueItem) value; Boolean ILL;
//	%+C   if TOS.type > T_max then IERR("CODER.ConvConst-1") endif;
//	%+C   if totype > T_max then IERR("CODER.ConvConst-2") endif;
		
		Coonst cnsTOS = (Coonst) CTStack.TOS;
		Value value = cnsTOS.value;
		boolean ILL = false;
		switch(CTStack.TOS.type.tag.val) {
//	      case T_BYT1,T_CHAR: qPOPKill(1); goto I1;
//	      case T_WRD2,T_BYT2: qPOPKill(2); goto I2;
		case Tag.TAG_INT:
			POP.qPOPKill(1);
//	I1:I2:
			switch(totype.tag.val) {
	           case Tag.TAG_CHAR:
	        	   IntegerValue cval = (IntegerValue) value;
	               if(cval.value > 255 | cval.value < 0) ILL = true;
	               int reg = Reg.FreePartReg();
//	                Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,value.byt);
	               new Q_PUSHC(reg, cval);
	               break;
	           case Tag.TAG_INT: GQpushVAL4(value); break;
	           case Tag.TAG_REAL:
//	                value.rev = value.int qua real; GQpushVAL4(value)
	        	   Util.IERR("NOT IMPL");
	           case Tag.TAG_LREAL:
//	                value.lrv = value.int qua long real; GQpushVAL8(value)
	        	   Util.IERR("NOT IMPL");
	        	   default: ILL = true;
			} break;
		case Tag.TAG_REAL:
			POP.qPOPKill(1);
			Util.IERR("NOT IMPL");
			switch(totype.tag.val) {
//	           case T_BYT1,T_CHAR:
//	                value.int = value.rev qua integer;
//	                if value.int > 255 then ILL = true
//	                elsif value.int < 0 then ILL = true endif;
//	                Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,value.byt);
//	           case T_BYT2:
//	                value.int = value.rev qua integer;
//	                if value.int > 65535 then ILL = true
//	                elsif value.int < 0 then ILL = true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,value.wrd);
//	           case T_WRD2:
//	                value.int = value.rev qua integer;
//	                if value.int > 32767 then ILL = true
//	                elsif value.int < -32768 then ILL = true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,value.wrd);
//	           case T_WRD4:
//	                value.int = value.rev qua integer; GQpushVAL4(value)
//	           case T_REAL:
//	                value.rev = value.rev qua real; GQpushVAL4(value)
//	           case T_LREAL:
//	                value.lrv = value.rev qua long real; GQpushVAL8(value)
//	           otherwise ILL = true
			} break;
		case Tag.TAG_LREAL:
			POP.qPOPKill(1);
			Util.IERR("NOT IMPL");
			switch(totype.tag.val) {
//	           case T_BYT1,T_CHAR:
//	                value.int = value.lrv qua integer;
//	                if value.int > 255 then ILL = true
//	                elsif value.int < 0 then ILL = true endif;
//	                Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,value.byt);
//	           case T_BYT2:
//	                value.int = value.lrv qua integer;
//	                if value.int > 65535 then ILL = true
//	                elsif value.int < 0 then ILL = true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,value.wrd);
//	           case T_WRD2:
//	                value.int = value.lrv qua integer;
//	                if value.int > 32767 then ILL = true
//	                elsif value.int < -32768 then ILL = true endif;
//	                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,value.wrd);
//	           case T_WRD4:
//	                value.int = value.lrv qua integer; GQpushVAL4(value)
//	           case T_REAL:
//	                value.rev = value.lrv qua real; GQpushVAL4(value)
//	           case T_LREAL:
//	                value.lrv = value.lrv qua long real; GQpushVAL8(value)
//	           otherwise ILL = true
			} break;
	      case Tag.TAG_OADDR:
				Util.IERR("NOT IMPL");
//	           if totype = T_GADDR
//	           then value.ofst = 0; Qf2(qPUSHC,0,FreePartReg,cVAL,0);
//	           else ILL = true endif;
	    	  break;
	      case Tag.TAG_GADDR:
				Util.IERR("NOT IMPL");
//	           if totype = T_AADDR
//	           then qPOPKill(AllignFac); qPOPKill(AllignFac);
//	%-E             qPOPKill(2);
//	                value.int = value.Ofst; Qf2(qPUSHC,0,FreePartReg,cVAL,value.wrd);
//	           elsif totype = T_OADDR then qPOPKill(AllignFac);
//	           else ILL = true endif;
				break;
				default: ILL = true;
		}
		if(ILL) Util.IERR("Constant conversion is undefined");
		cnsTOS.value = value; cnsTOS.type = totype;
	}

	private static void GQpushVAL4(Value value) {
		int reg = Reg.FreePartReg();
		new Q_PUSHC(reg, value);
	}

}
