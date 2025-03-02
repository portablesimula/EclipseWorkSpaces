package bec.compileTimeStack;

import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;

//Record Coonst:Temp;               --- Value is also in 'itm'
//begin infix(ValueItem) itm;
//end;
//
//Record ValueItem; info "TYPE";
//begin variant range(0:MaxByte) byt(8); -- ?? variant range(0:MaxWord) byt(8);
//      variant range(0:MaxWord) wrd(4);
//      variant integer int(2);
//      variant real rev(2);
//      variant long real lrv;
//      variant infix(MemAddr) base;     -- size 6(-E) / 8(+E)
//              range(0:MaxWord) Ofst;
//end;

public class ConstItem extends Temp {
	public Value value;

	public ConstItem(Type type, Value value) {
		super(type, 0, 0, "ConstItem: ");
		this.value = value;
		if(type.tag == 0) Util.IERR("NEW Coonst: NO TYPE: "+value);
	}

	public void convert(Type totype) { // ConvConst(totype)
//		%title ***    C o n v e r t   C o n s t a n t   V a l u e    ***
//		Routine ConvConst; import range(0:MaxType) totype;
//		begin integer v; infix(ValueItem) itm; Boolean ILL;
//		%+C   if TOS.type > T_max then IERR("CODER.ConvConst-1") endif;
//		%+C   if totype > T_max then IERR("CODER.ConvConst-2") endif;
//		      itm:=TOS qua Coonst.itm;
		System.out.println("ConstItem.convert: " + value.type + " ==> " + totype);
		boolean ILL = false;
		switch(value.type.tag) {
			case Scode.TAG_TEXT:
				System.out.println("ConstItem.convert: totype="+totype+" HASH:"+totype.hashCode());
				System.out.println("ConstItem.convert: String="+Type.T_STRING+" HASH:"+Type.T_STRING.hashCode());
				if(totype != Type.T_STRING) ILL = true; break;
			default: Util.IERR("FYLL PÅ FLERE CASER: " + value.type);
		}
//		      case 0:T_max (TOS.type)
//		      when T_BYT1,T_CHAR: qPOPKill(1); goto I1;
//		      when T_WRD2,T_BYT2: qPOPKill(2); goto I2;
//		      when T_WRD4:
//		%-E                qPOPKill(2);
//		                   qPOPKill(AllignFac);
//		I1:I2:     case 0:T_max (totype)
//		           when T_BYT1,T_CHAR:
//		                if itm.int > 255 then ILL:=true
//		                elsif itm.int < 0 then ILL:=true endif;
//		                Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,itm.byt);
//		           when T_BYT2:
//		                if (TOS.type=T_WRD2) and (RNGCHK=0) then -- OK
//		                elsif itm.int > 65535 then ILL:=true
//		                elsif itm.int < 0 then ILL:=true endif;
//		                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//		           when T_WRD2:
//		                if (TOS.type=T_BYT2) and (RNGCHK=0) then -- OK
//		                elsif itm.int > 32767 then ILL:=true
//		                elsif itm.int < -32768 then ILL:=true endif;
//		                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//		           when T_WRD4: GQpushVAL4(itm)
//		           when T_REAL:
//		                itm.rev:=itm.int qua real; GQpushVAL4(itm)
//		           when T_LREAL:
//		                itm.lrv:=itm.int qua long real; GQpushVAL8(itm)
//		           otherwise ILL:=true endcase;
//		      when T_REAL:
//		%-E        qPOPKill(2); qPOPKill(2);
//		%+E        qPOPKill(4);
//		           case 0:T_max (totype)
//		           when T_BYT1,T_CHAR:
//		                itm.int:=itm.rev qua integer;
//		                if itm.int > 255 then ILL:=true
//		                elsif itm.int < 0 then ILL:=true endif;
//		                Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,itm.byt);
//		           when T_BYT2:
//		                itm.int:=itm.rev qua integer;
//		                if itm.int > 65535 then ILL:=true
//		                elsif itm.int < 0 then ILL:=true endif;
//		                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//		           when T_WRD2:
//		                itm.int:=itm.rev qua integer;
//		                if itm.int > 32767 then ILL:=true
//		                elsif itm.int < -32768 then ILL:=true endif;
//		                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//		           when T_WRD4:
//		                itm.int:=itm.rev qua integer; GQpushVAL4(itm)
//		           when T_REAL:
//		                itm.rev:=itm.rev qua real; GQpushVAL4(itm)
//		           when T_LREAL:
//		                itm.lrv:=itm.rev qua long real; GQpushVAL8(itm)
//		           otherwise ILL:=true endcase;
//		      when T_LREAL:
//		%-E        qPOPKill(2); qPOPKill(2);
//		%-E        qPOPKill(2); qPOPKill(2);
//		%+E        qPOPKill(4); qPOPKill(4);
//		           case 0:T_max (totype)
//		           when T_BYT1,T_CHAR:
//		                itm.int:=itm.lrv qua integer;
//		                if itm.int > 255 then ILL:=true
//		                elsif itm.int < 0 then ILL:=true endif;
//		                Qf2(qPUSHC,0,LowPart(%FreePartReg%),cVAL,itm.byt);
//		           when T_BYT2:
//		                itm.int:=itm.lrv qua integer;
//		                if itm.int > 65535 then ILL:=true
//		                elsif itm.int < 0 then ILL:=true endif;
//		                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//		           when T_WRD2:
//		                itm.int:=itm.lrv qua integer;
//		                if itm.int > 32767 then ILL:=true
//		                elsif itm.int < -32768 then ILL:=true endif;
//		                Qf2(qPUSHC,0,WordReg(FreePartReg),cVAL,itm.wrd);
//		           when T_WRD4:
//		                itm.int:=itm.lrv qua integer; GQpushVAL4(itm)
//		           when T_REAL:
//		                itm.rev:=itm.lrv qua real; GQpushVAL4(itm)
//		           when T_LREAL:
//		                itm.lrv:=itm.lrv qua long real; GQpushVAL8(itm)
//		           otherwise ILL:=true endcase;
//		      when T_OADDR:
//		           if totype = T_GADDR
//		           then itm.ofst:=0; Qf2(qPUSHC,0,FreePartReg,cVAL,0);
//		           else ILL:=true endif;
//		      when T_GADDR:
//		           if totype = T_AADDR
//		           then qPOPKill(AllignFac); qPOPKill(AllignFac);
//		%-E             qPOPKill(2);
//		                itm.int:=itm.Ofst; Qf2(qPUSHC,0,FreePartReg,cVAL,itm.wrd);
//		           elsif totype = T_OADDR then qPOPKill(AllignFac);
//		           else ILL:=true endif;
//		      otherwise ILL:=true endcase;
		if(ILL) Util.ERROR("Constant conversion is undefined");
//		      TOS qua Coonst.itm:=itm;
		CTStack.TOS.type = totype;
	}
	
	public String toString() {
		return "" +type + " " + value;
	}
}
