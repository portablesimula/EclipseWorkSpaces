package bec.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.compileTimeStack.Temp;
import bec.virtualMachine.RTAddress;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_NOT_IMPL;
import bec.virtualMachine.SVM_PUSH;

public class Util {

	public static void setLine(Type type) {
		Scode.curline = Scode.inNumber();
	}

	public static void ITRC(String id, String msg) {
		if(Global.SCODE_INPUT_TRACE) {
			Scode.traceBuff = new StringBuilder("Line " + Scode.curline + "  " + id + ": " + msg);
		}
	}

	public static void WARNING(String msg) {
		if(Global.SCODE_INPUT_TRACE) {
			System.out.println(Scode.traceBuff);
		}
		System.out.println("ERROR: " + msg);
	}

	public static void ERROR(String msg) {
		if(Global.SCODE_INPUT_TRACE) {
			System.out.println(Scode.traceBuff);
		}
		System.out.println("ERROR: " + msg);
	}

	public static void IERR(String msg) {
		ERROR("Internal error: " + msg);
		Thread.dumpStack();
		System.exit(0);
	}


	/**
	 * Utility method: TRACE_OUTPUT
	 * @param msg the message to print
	 */
	public static void TRACE_OUTPUT(final String msg) {
		if (Global.ATTR_OUTPUT_TRACE)
			System.out.println("ATTR OUTPUT: " + msg);
	}

	/**
	 * Utility method: TRACE_INPUT
	 * @param msg the message to print
	 */
	public static void TRACE_INPUT(final String msg) {
		if (Global.ATTR_INPUT_TRACE)
			System.out.println("ATTR INPUT: " + msg);
	}

//	%title *********    D i c t i o n a r y    *********
	public static HashMap<Integer,String> dicMap = new HashMap<Integer,String>();
	public static int nSymb;
	
	public static int DefSymb(String symb) {
		int key = nSymb++;
		dicMap.put(key, symb);
		return key;
	}

	public static String DICSMB(int n) {
		String s = dicMap.get(n);
		return s;
	}

	public static String edSymb(int i) {
		return DICSMB(i);
	}

	
	// ***************************************************************
	// *** EXECUTE OS COMMAND
	// ***************************************************************
//	public static int exec(String... cmd) throws IOException {
//		Runtime runtime = Runtime.getRuntime();
//		String line="";
//		for(int i=0;i<cmd.length;i++) line=line+" "+cmd[i];
//        System.out.println("MakeCompiler.execute: command="+line);
////	    String cmd=command.trim()+'\n';
//		Process process = runtime.exec(cmd);
//		//try
//		{ InputStream err=process.getErrorStream();
//		  InputStream inp=process.getInputStream();
//		  while(process.isAlive())
//		  { while(err.available()>0) System.err.append((char)err.read());
//		    while(inp.available()>0) System.out.append((char)inp.read());
//			
//		  }
//		  // process.waitFor();
//		} //catch(InterruptedException e) { e.printStackTrace(); }
//		return(process.exitValue());
//	}

	public static int exec(String... cmd) throws IOException {
		String line="";
		for(int i=0;i<cmd.length;i++) line=line+" "+cmd[i];
        System.out.println("MakeSML.execute: command="+line);
		ProcessBuilder processBuilder = new ProcessBuilder(cmd);
		processBuilder.redirectErrorStream(true);
		try {
			Process process = processBuilder.start();		
			InputStream output = process.getInputStream();  // Process' output
			while (process.isAlive()) {
				while (output.available() > 0)
					System.out.append((char) output.read());
//				System.out.println("ALIVE: "+process.isAlive());
			}
//			System.out.println("RETURN: "+process.exitValue());
//			Thread.dumpStack();
			return (process.exitValue());

		} catch(Exception e) {
			System.out.println("ERROR: "+e);
			throw new RuntimeException("Process Execution failed: " + line, e);
		}
	}

	// ***************************************************************
	// *** GQ-Utilities
	// ***************************************************************

	public static void GQfetch(String comment) { //  ; --  M} ikke bruke qDI(se rupdate) --
//	%-D Visible Routine GQfetchxx; --  M} ikke bruke qDI(se rupdate) --
//	begin infix(MemAddr) opr; range(0:MaxType) type;
//	      range(0:MaxWord) nbyte; range(0:MaxByte) cTYP;
		if(CTStack.TOS instanceof AddressItem) {
//	           opr:=GetTosSrcAdr;
//			ObjectAddress addr = getTosSrcAdr();
			AddressItem addr = (AddressItem) CTStack.TOS;
			System.out.println("Util.GQfetch: addr="+addr);
			Type type = CTStack.TOS.type;
//			int size = DataType.typeSize(type);
			RTAddress rtAddr = new RTAddress(addr); 
			Global.PSEG.emit(new SVM_PUSH(rtAddr, type.size()), comment + " " +type);
			CTStack.pop(); CTStack.pushTemp(type, RTRegister.qEAX, 1, "GQFetch: ");
			CTStack.dumpStack("GQfetch: "+comment);
			Global.PSEG.dump("GQfetch: "+comment);
//			Util.IERR("NOT IMPL");
		}
	}

	public static void GQpop() {
//	begin range(0:MaxWord) nbyte;
//	      case 0:K_Max (TOS.kind)
//	      when K_Coonst,K_Temp,K_Result:
		StackItem tos = CTStack.TOS;
		if(tos instanceof Temp tmp) { // when K_Coonst,K_Temp,K_Result:
//	           nbyte:=TTAB(TOS.type).nbyte;
//	           if nbyte <= AllignFac then qPOPKill(nbyte)
//	           elsif nbyte <= (3*AllignFac)
//	           then repeat qPOPKill(AllignFac); nbyte:=nbyte-AllignFac;
//	                while nbyte<>0 do endrepeat;
//	           else qPOPKill(nbyte) endif;
			qPOPKill(tmp.size);
		} else if(tos instanceof AddressItem) {
//	      when K_Address:
//	           if TOS qua Address.AtrState <> NotStacked
//	           then qPOPKill(AllignFac) endif;
//	           if TOS qua Address.ObjState <> NotStacked
//	           then qPOPKill(AllignFac);
//	%-E             qPOPKill(2);
//	           endif;
//	      endcase;
			Util.IERR("");
		} else Util.IERR("MISSING: "+tos.getClass().getSimpleName());
		CTStack.pop();
	}

	public static void qPOPKill(int aux) {
//	begin ref(Object) qi; qi:=FreeObj(K_Qfrm2);
//	      if qi=none then qi:=NEWOBX(size(Qfrm2));
//	%+D        ObjCount(K_Qfrm2):=ObjCount(K_Qfrm2)+1;
//	      else FreeObj(K_Qfrm2):=qi qua FreeObject.next endif;
//	      qi.kind:=K_Qfrm2; qi qua Qfrm2.fnc:=qPOPK;
//	      qi qua Qfrm2.subc:=qi qua Qfrm2.reg:=qi qua Qfrm2.type:=0;
//	      qi qua Qfrm2.aux.val:=aux; AppendQinstr(qi);

//		Global.PSEG.emit(new SVM_POPK(aux), "qPOPKill: ");
		Global.PSEG.emit(new SVM_NOT_IMPL(), "qPOPKill: qPOPKill: ");
		Global.PSEG.dump("qPOPKill: ");
		CTStack.dumpStack("qPOPKill: ");
//		Util.IERR("qPOPKill:  ");
	}
	
	public static int GQrelation() { // export range(0:255) res;
//	%-E   import boolean jmprel;
//	      export range(0:255) res;
//	begin range(0:MaxType) at; range(0:255) a,d,qCond,qType; range(0:255) srel;
//	      infix(MemAddr) opr; range(0:MaxWord) s,nbyte; range(0:MaxByte) cTYP;
//	%-E   range(0:nregs) segreg;
//	      xFJUMP:=none;
//	      inputInstr; srel:=curInstr;
		CTStack.checkTypesEqual(); //	%+C   CheckTypesEqual;
		CTStack.checkSosValue();
//	%+D   if (srel<S_LT) or (srel>S_NE) then IERR("Illegal relation") endif;
//	      at:=TOS.type; reversed:=false;
//	      if at <= T_max then at:=ArithType(at,TOS.suc.type); cTYP:=cTYPE(at)
//	      else cTYP:=cANY endif;
//	      nbyte:=TTAB(at).nbyte;
//	%+C   if nbyte=0 then IERR("CODER.GQrel-0") endif;
//	      if TTAB(at).kind=tFloat
//	      then reversed:=false;
//	%-E        if NUMID=NoNPX
//	%-E        then
//	%-E             if    at=T_REAL  then EM4CMP; goto E1
//	%-E             elsif at=T_LREAL then EM8CMP; goto E3;
//	%-E             endif;
//	%-E        else
//	                GQconvert(at); PopTosToNPX;
//	                GQconvert(at); PopTosToNPX;
//	%+S             WARNING("Floating point Relation");
//	%-E             Qf1(qFDYAD,qFCOM,cTYP);
//	%+E             Qf1b(qFDYAD,qFCOM,Fwf87(at),cTYP);
//	%-E        endif;
//	      else
//	           if nbyte <= AllignFac
//	           then a:=accreg(nbyte); d:=datareg(nbyte);
//	                GQconvert(at); GetTosAdjustedIn86(d); Pop;
//	                GQconvert(at); GetTosAdjustedIn86(a); Pop;
//	                reversed:=false; Qf2(qDYADR,qCMP,a,cTYP,d);
//	%-E        elsif at=T_WRD4 then res:=GQrel32(srel,jmprel); goto E2
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
//	%-E        elsif nbyte <= (3*AllignFac)
//	%-E        then if srel=S_EQ then goto EN1 elsif srel=S_NE
//	%-E             then EN1:
//	%-E                  if nbyte=6 -- Gaddr etc.
//	%-E                  then GetTosValueIn86R3(qAX,qCX,qDX); Pop;
//	%-E                       GQfetch; -- To prevent SI from being destroyed
//	%-E                       GetTosValueIn86R3(qDI,qSI,qBX); Pop;
//	%-E                       Qf2(qDYADR,qCMP,qAX,cTYP,qDI);
//	%-E                       Qf1(qLAHF,0,cVAL); Qf2(qMOV,0,qAL,cVAL,qAH);
//	%-E                       Qf2(qDYADR,qCMP,qCX,cTYP,qSI);
//	%-E                       Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAL,cVAL,qAH);
//	%-E                       Qf2(qDYADR,qCMP,qDX,cTYP,qBX);
//	%-E                       Qf1(qLAHF,0,cVAL); Qf2(qDYADR,qAND,qAH,cVAL,qAL);
//	%-E                       Qf1(qSAHF,0,cVAL);
//	%-E                  else if IsONONE(TOS)
//	%-E                       then GQpop; GQfetch;
//	%-E                            qPOPKill(2); Qf1(qPOPR,qAX,cOBJ);
//	%-E                            NotMindMask:=uAX;
//	%-E                            Pop; Qf2(qDYADR,qORM,qAX,cOBJ,qAX);
//	%-E                       elsif IsONONE(TOS.suc)
//	%-E                       then GQfetch; qPOPKill(2);
//	%-E                            Qf1(qPOPR,qAX,cOBJ); Pop; GQpop;
//	%-E                            NotMindMask:=uAX;
//	%-E                            Qf2(qDYADR,qORM,qAX,cOBJ,qAX);
//	%-E                       else GetTosValueIn86R3(qAX,qDX,0); Pop;
//	%-E                            GetTosValueIn86R3(qCX,qBX,0); Pop;
//	%-E                            Qf2(qDYADR,qCMP,qAX,cOBJ,qCX);
//	%-E                            if jmprel
//	%-E                            then mindMask:=wOR(uSPBPM,uF);
//	%-E                                 xFJUMP:=forwJMP(q_WNE);
//	%-E                                 MindMask:=wOR(uDX,uBX);
//	%-E                                 Qf2(qDYADR,qCMP,qDX,cOBJ,qBX);
//	%-E                                 mindMask:=wOR(uSPBPM,uF);
//	%-E                            else Qf1(qLAHF,0,cVAL); Qf2(qMOV,0,qAL,cVAL,qAH);
//	%-E                                 Qf2(qDYADR,qCMP,qDX,cOBJ,qBX);
//	%-E                                 Qf1(qLAHF,0,cVAL);
//	%-E                                 Qf2(qDYADR,qAND,qAH,cVAL,qAL);
//	%-E                                 Qf1(qSAHF,0,cVAL);
//	%-E                            endif
//	%-E                       endif
//	%-E                  endif;
//	%-E                  reversed:=false;
//	%-E             else -- oaddr < <= > >=
//	%-E %+C              if at <> T_OADDR then IERR("CODER.GQrel-1") endif;
//	%-E                  GQfetch; Qf1(qPOPR,qDX,cOBJ); qPOPKill(2); Pop;
//	%-E                  GQfetch; Qf1(qPOPR,qAX,cOBJ); qPOPKill(2); Pop;
//	%-E                  reversed:=false;
//	%-E                  Qf2(qDYADR,qCMP,qAX,cOBJ,qDX);
//	%-E             endif;
//	           elsif (TOS.kind<>K_Address)
//	           then
//	%-E             Qf2(qMOV,0,qDI,cSTP,qSP);
//	%-E             Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%+E             Qf2(qMOV,0,qEDI,cSTP,qESP);
//	                if TOS.suc.kind=K_Address
//	                then
//	%-E                  opr:=GetSosAddr(qDS,qBX,qSI);
//	%+E                  opr:=GetSosAddr(qEBX,qESI);
//	%-E                  if bAND(opr.sbireg,oSREG) <> oDS
//	%-E                  then segreg:=GetSreg(opr);
//	%-E                       PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                       Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%-E                  endif;
//	%-E                  Qf3(qLOADA,0,qSI,cADR,opr);
//	%+E                  Qf3(qLOADA,0,qESI,cADR,opr);
//	                else
//	%-E                  Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	                     opr.kind:=reladr; opr.segmid.val:=0;
//	                     opr.rela.val:=wAllign(%nbyte%);
//	%-E                  opr.sbireg:=rmDI;PreMindMask:=wOR(PreMindMask,uDI)
//	%+E                  opr.sibreg:=bEDI+iEDI;
//	%+E                  PreMindMask:=wOR(PreMindMask,uEDI)
//	%-E                  Qf3(qLOADA,0,qSI,cADR,opr);
//	%+E                  Qf3(qLOADA,0,qESI,cADR,opr);
//	                endif;
//	%-E             Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//	%+E             Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                Qf2(qRSTRW,qRCMP,qCLD,cTYP,qREPEQ);
//	                Qf1(qLAHF,0,cVAL); qPOPKill(nbyte);
//	                if TOS.suc.kind=K_Address
//	                then repeat while SosAdrNwk>0
//	                     do qPOPKill(AllignFac);
//	                        SosAdrNwk:=SosAdrNwk-1;
//	                     endrepeat;
//	                else qPOPKill(nbyte) endif;
//	                Qf1(qSAHF,0,cVAL); Pop; Pop;
//	           else -- TOS.kind=K_Address or TOS.kind=K_Content */
//	                opr:=GetTosDstAdr;
//	%-E             if bAND(opr.sbireg,oSREG) <> oES
//	%-E             then segreg:=GetSreg(opr);
//	%-E                  PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                  Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E             endif;
//	%-E             Qf3(qLOADA,0,qDI,cADR,opr);
//	%+E             Qf3(qLOADA,0,qEDI,cADR,opr);
//	                Pop;
//	                if  (TOS.kind<>K_Address)
//	                then s:=nbyte;
//	%-E                  Qf2(qMOV,0,qSI,cSTP,qSP);
//	%-E                  Qf1(qPUSHR,qSS,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%+E                  Qf2(qMOV,0,qESI,cSTP,qESP);
//	                else s:=0;
//	                     opr:=GetTosSrcAdr;
//	%-E                  if bAND(opr.sbireg,oSREG) <> oDS
//	%-E                  then segreg:=GetSreg(opr);
//	%-E                       PreMindMask:=wOR(PreMindMask,uMask(segreg)); 
//	%-E                       Qf1(qPUSHR,segreg,cOBJ); Qf1(qPOPR,qDS,cOBJ);
//	%-E                  endif;
//	%-E                  Qf3(qLOADA,0,qSI,cADR,opr);
//	%+E                  Qf3(qLOADA,0,qESI,cADR,opr);
//	                endif;
//	%-E             Qf2(qLOADC,0,qCX,cVAL,nbyte/2);
//	%+E             Qf2(qLOADC,0,qECX,cVAL,nbyte/4);
//	                Qf2(qRSTRW,qRCMP,qCLD,cTYP,qREPEQ);
//	                if s<>0
//	                then Qf1(qLAHF,0,cVAL); qPOPKill(s);
//	                     Qf1(qSAHF,0,cVAL);
//	                endif;
//	                Pop;
//	           endif;
//	      endif;
//	%-E E1:E3:
//	      if    at=T_INT  then qtype:=q_ILT
//	      elsif at=T_WRD2 then qType:=q_ILT else qType:=q_WLT endif;
//	      qCond:=(srel-S_LT)+qType;
//	      if reversed then qCond:=RevQcond(qCond) endif;
//	      res:= qCond;
//	%-E E2:
//		Util.IERR("");
		return 0;
	}

}
