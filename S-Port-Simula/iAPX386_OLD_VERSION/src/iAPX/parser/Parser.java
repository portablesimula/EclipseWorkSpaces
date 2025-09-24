package iAPX.parser;

import static iAPX.util.Global.*;

import java.io.IOException;

import iAPX.descriptor.ConstDescr;
import iAPX.descriptor.GlobalVar;
import iAPX.descriptor.LabelDescr;
import iAPX.descriptor.ProfileDescr;
import iAPX.descriptor.RecordDescr;
import iAPX.descriptor.RoutineDescr;
import iAPX.enums.PKind;
import iAPX.instruction.CallInstruction;
import iAPX.instruction.FJUMPIF;
import iAPX.instruction.LINE;
import iAPX.instruction.PUSH;
import iAPX.instruction.PUSHC;
import iAPX.instruction.REMOTE;
import iAPX.instruction.RUPDATE;
import iAPX.instruction.SELECT;
import iAPX.minut.Minut;
import iAPX.qInstr.Q_LINE;
import iAPX.qPkt.Qfunc;
import iAPX.statement.InsertStatement;
import iAPX.util.Array;
import iAPX.util.Display;
import iAPX.util.Option;
import iAPX.util.Scode;
import iAPX.util.Tag;
import iAPX.util.Util;

public class Parser {

	//%title ***     M  O  N  I  T  O  R     ***
	public static void MONITOR() {
//	begin infix(WORD) tag,entx; infix(MemAddr) Ltab;
//	      range(0:MaxByte) n; infix(MemAddr) opr;
//	%-S   warnDEBUG3 =  DEBMOD > 2;  -- TEMP FIX
	      IfDepth = 0;
	      Scode.inputInstr();
	      if(Scode.curinstr == Scode.S_PROGRAM) {
//	           ProgIdent = inSymb;
	           ProgIdent = Scode.inString();
	 	       Scode.inputInstr();
	 	       if(Option.verbose) IO.println("Parser.MONITOR: ProgIdent="+ProgIdent);
//	 	       Util.IERR(""+Scode.curinstr);
	           if(Scode.curinstr == Scode.S_GLOBAL) { interfaceModule(); Scode.inputInstr(); }
	           while(Scode.curinstr == Scode.S_MODULE) { moduleDefinition(); Scode.inputInstr(); }
	           if(Scode.curinstr == Scode.S_MAIN) {
	                // ---  M a i n   P r o g r a m  ---
	                if(PROGID == null) PROGID = "MAIN";
//	                BEGASM(CSEGNAM,DSEGNAM);
//	                
//	                ed(sysedit,"SIM_");
//	                EdSymb(sysedit,PROGID); entx = DefPubl(pickup(sysedit));
//	                
//	                MainEntry = NewFixAdr(CSEGID,entx);
//	                DefLABEL(qBPROC,MainEntry.fix.val,entx.val);

//	                while(NextByte == Scode.S_LOCAL) { Scode.inputInstr(); inGlobal; }
	                if(LtabEntry.kind != 0) {
//	                	 MemAddr Ltab = new MemAddr();
//	                     Ltab.kind = segadr; Ltab.rela.val = 0;
//	                     Ltab.segmid = LSEGID;
//	                     
//	                	 MemAddr opr = new MemAddr();
//	                     opr.kind = extadr; opr.rela.val = 0;
//	                     opr.smbx = DefExtr("G@PRGINF",DGROUP);
////%-E                    Ltab.sbireg = 0;       opr.sbireg = oSS;
//	                     Ltab.sibreg = NoIBREG; opr.sibreg = NoIBREG;
////%-E                    Qf2b(qLOADC,0,qAX,cOBJ,F_OFFSET,Ltab);
////%-E                    Qf3(qSTORE,0,qAX,cOBJ,opr);
////%-E                    opr.rela.val = opr.rela.val+2;
////%-E                    Qf2b(qLOADC,0,qAX,cOBJ,F_BASE,Ltab);
////%-E                    Qf3(qSTORE,0,qAX,cOBJ,opr);
//	                     Qf2b(qLOADC,0,qEAX,cOBJ,0,Ltab);
//	                     Qf3(qSTORE,0,qEAX,cOBJ,opr);
	                }

//	                programElements();
//
//	                Qf2(qRET,0,0,0,0);
//	                DefLABEL(qEPROC,MainEntry.fix.val,entx.val);
//	                peepExhaust(true); ENDASM();
	           }
	           if(Scode.curinstr != Scode.S_ENDPROGRAM) Util.IERR("Illegal termination of program: " + Scode.curinstr);
	      } else Util.IERR("Illegal S-Program");
//	%+D   --- Release Display ---
//	%+D   tag = GetLastTag; n = tag.HI;
//	%+D   repeat DELETE(DISPL(n)); DISPL(n) = none
//	%+D   while n<>0 do n = n-1 endrepeat;
	}

	private static void interfaceModule() {
//	      range(0:MaxWord) nXtag; infix(WORD) itag,xtag,wrd;
//	      range(0:MaxByte) b1,b2;
	   Scode.inputInstr();
	   if(Scode.curinstr != Scode.S_MODULE) Util.IERR("Missing - MODULE");
//	   modident = inMsymb; modcheck = inSymb;
	   modident = Scode.inString(); modcheck = Scode.inString();
	   if(Option.verbose) IO.println("Parser.InterfaceModule: modident="+modident+", modcheck="+modcheck);
	   if(PROGID == null) PROGID = modident;
//	   BEGASM(CSEGNAM,DSEGNAM); nXtag = 0;
		LOOP: while(true) {
			Scode.inputInstr();
//			IO.println("InterfaceModule'LOOP: Curinstr="+Scode.curinstr);
			switch(Scode.curinstr) {
				case Scode. S_GLOBAL:    GlobalVar.inGlobal(); break;
				case Scode. S_CONSTSPEC: ConstDescr.inConstant(false); break;
				case Scode. S_CONST:     ConstDescr.inConstant(true); break;
				case Scode. S_RECORD:    RecordDescr.inRecord(); break;
				case Scode. S_PROFILE:   ProfileDescr.inProfile(PKind.P_VISIBLE); break;
//		          case Scode. S_ROUTINE:   InRoutine(); break;
//		          case Scode. S_INFO:      WARNING("Unknown info: "+Scode.inString()); break;
		          case Scode. S_LINE:      SetLine(Q_LINE.Subc.src); break;
//		          case Scode. S_DECL:
//		                            CheckStackEmpty;
//		                            SetLine(qDCL);
//		                            break;
//		          case Scode. S_STMT:
//		                            CheckStackEmpty;
//		                            SetLine(qSTM);
//		                            break;
//		          case Scode. S_SETSWITCH: SetSwitch(); break
//		          case Scode. S_INSERT,
//		               S_SYSINSERT: Combine; TERMINATE(); break;
					
					default: break LOOP;
				}
			}
		
		Tag.xTAGTAB = new Array<Integer>();
		while(Scode.curinstr == Scode.S_TAG) {
			Tag iTag = Tag.inTag();
			int xTag = Scode.inNumber();
			Scode.inputInstr();
			Tag.xTAGTAB.set(iTag.val, Integer.valueOf(xTag));
		}
//		printTags("Minut.writeDescriptors: ");

		try { Minut.outputModule(); } catch (IOException e) { e.printStackTrace(); }

		if(Scode.curinstr != Scode.S_BODY) Util.IERR("Illegal termination of module head");
		
		while(Scode.accept(Scode.S_INIT)) {
			Util.IERR("InterfaceModule: Init values is not supported");
			// InTag(%wrd%); intype; SkipRepValue;	    	
		}
		Scode.expect(Scode.S_ENDMODULE);
		////---     peepExhaust(); --- nothing to work on
		IO.println("Parser.InterfaceModule: Metoden 'ENDASM' MÅ KANSKJE SKRIVES");
		//    ENDASM;
		//   Util.IERR("");
	}
	
//	%title ***   M o d u l e   D e f i n i t i o n   ***

	private static void OLD_moduleDefinition() {
//	 begin range(0:MaxWord) nXtag; ref(ModElt) m;
//	       infix(WORD) itag,xtag; infix(Fixup) Fx;
//	       modident = inMsymb; modcheck = inSymb;
		modident = Scode.inString(); modcheck = Scode.inString();
		IO.println("Parser.moduleDefinition: modident="+modident+", modcheck="+modcheck);
		if(PROGID == null) PROGID = modident;
//	       BEGASM(CSEGNAM,DSEGNAM); nXtag = 0;
		Scode.inputInstr();
//	       m = DICREF(modident);
//	%+S    if SYSGEN=0
//	%+S    then
//	            m.RelElt = RELID;
//	%+S    endif;
//	       if LtabEntry.kind=0 then -- No LineNumberTable
//	       else
//	%+D         if LtabEntry.kind<>fixadr then IERR("PARSE:Md-1") endif
//	            Fx = FIXTAB(LtabEntry.fix.HI).elt(LtabEntry.fix.LO);
//	%+D         if Fx.smbx.val=0 then IERR("PARSE:Md-2") endif;
//	            m.LinTab = Fx.smbx;
//	       endif;

////		Scode.inputInstr();
//		IO.println("BEFORE visible'LOOP: Curinstr="+Scode.curinstr);
//		while(visible()) { Scode.inputInstr(); }


//		if(Scode.curinstr != Scode.S_BODY) Util.IERR("Illegal termination of module head");
		
//		while(Scode.accept(Scode.S_INIT)) {
//			Util.IERR("InterfaceModule: Init values is not supported");
//			// InTag(%wrd%); intype; SkipRepValue;	    	
//		}
		
		Scode.inputInstr();
		IO.println("BEFORE visible'LOOP: Curinstr="+Scode.curinstr);
		while(visible()) { Scode.inputInstr(); }

		Tag.xTAGTAB = new Array<Integer>();
		while(Scode.curinstr == Scode.S_TAG) {
			Tag iTag = Tag.inTag();
			int xTag = Scode.inNumber();
			Scode.inputInstr();
			Tag.xTAGTAB.set(iTag.val, Integer.valueOf(xTag));
		}
//		printTags("Minut.writeDescriptors: ");

		try { Minut.outputModule(); } catch (IOException e) { e.printStackTrace(); }

		Scode.expect(Scode.S_ENDMODULE);

	    Qfunc.peepExhaust(true);
	    IO.println("Parser.InterfaceModule: Metoden 'ENDASM' MÅ KANSKJE SKRIVES");
//	       ENDASM;
	    Util.IERR("");
	}
	
	private static void moduleDefinition() {
		modident = Scode.inString(); modcheck = Scode.inString();
		IO.println("Parser.moduleDefinition: modident="+modident+", modcheck="+modcheck);
		if(PROGID == null) PROGID = modident;
//	       BEGASM(CSEGNAM,DSEGNAM); nXtag = 0;
		Scode.inputInstr();
//	       m = DICREF(modident);
//	%+S    if SYSGEN=0
//	%+S    then
//	            m.RelElt = RELID;
//	%+S    endif;
//	       if LtabEntry.kind=0 then -- No LineNumberTable
//	       else
//	%+D         if LtabEntry.kind<>fixadr then IERR("PARSE:Md-1") endif
//	            Fx = FIXTAB(LtabEntry.fix.HI).elt(LtabEntry.fix.LO);
//	%+D         if Fx.smbx.val=0 then IERR("PARSE:Md-2") endif;
//	            m.LinTab = Fx.smbx;
//	       endif;

		while(visible()) { Scode.inputInstr(); }
		
		Tag.xTAGTAB = new Array<Integer>();
		while(Scode.curinstr == Scode.S_TAG) {
			Tag iTag = Tag.inTag();
			int xTag = Scode.inNumber();
			Scode.inputInstr();
			Tag.xTAGTAB.set(iTag.val, Integer.valueOf(xTag));
		}
//		printTags("Minut.writeDescriptors: ");
		
		try { Minut.outputModule(); } catch (IOException e) { e.printStackTrace(); }
		
		if(Scode.curinstr != Scode.S_BODY) Util.IERR("Illegal termination of module head");
		Scode.inputInstr();
		
//	       repeat while NextByte = Scode.S_LOCAL
//	       do inputInstr; inGlobal endrepeat;
		while(Scode.accept(Scode.S_LOCAL)) {
			IO.println("BEFORE inGlobal: Curinstr="+Scode.curinstr);
			Util.IERR("");
//			inGlobal()
		}
		
		IO.println("BEFORE programElements: Curinstr="+Scode.curinstr);
		programElements();
//		Util.IERR("");

		Scode.expect(Scode.S_ENDMODULE);
	    Qfunc.peepExhaust(true);
	    IO.println("Parser.InterfaceModule: Metoden 'ENDASM' MÅ KANSKJE SKRIVES");
//	       ENDASM;
	    Util.IERR("");
	}

	private static boolean visible() {
		IO.println("visible'LOOP: Curinstr="+Scode.curinstr);
		switch(Scode.curinstr) {
			case Scode.S_CONSTSPEC:   ConstDescr.inConstant(false); break;
			case Scode.S_LABELSPEC:   Util.IERR("NOT IMPL: " + Scode.curinstr); //  SpecLab
			case Scode.S_RECORD:      RecordDescr.inRecord(); break;
			case Scode.S_PROFILE:     ProfileDescr.inProfile(PKind.P_VISIBLE); break;
			case Scode.S_ROUTINESPEC: Util.IERR("NOT IMPL: " + Scode.curinstr); // SpecRut(true)
			case Scode.S_ROUTINE:     Util.IERR("NOT IMPL: " + Scode.curinstr); // SpecRut(true)
			case Scode.S_INSERT:      new InsertStatement(false); break;
			case Scode.S_SYSINSERT:   new InsertStatement(true); break;
			case Scode.S_LINE:        SetLine(Q_LINE.Subc.src);
			case Scode.S_DECL:
				Util.IERR("NOT IMPL: " + Scode.curinstr); // 
//				CheckStackEmpty;
//				SetLine(qDCL);
			case Scode.S_STMT: 
				Util.IERR("NOT IMPL: " + Scode.curinstr); // 
//				CheckStackEmpty;
//				SetLine(qSTM)
			case Scode.S_SETSWITCH:    Util.IERR("NOT IMPL: " + Scode.curinstr); // SetSwitch
			case Scode.S_INFO:         Util.IERR("NOT IMPL: " + Scode.curinstr); // Ed(errmsg,InString); WARNING("Unknown info: ");
			default: return false;
		}
		return true;
	}

	public static void programElements() {
		LOOP: while(true) {
//			System.out.println("S_Module.programElements: CurInstr="+Scode.curinstr);
			switch(Scode.curinstr) {
				case S_LABELSPEC ->   Util.IERR("NOT IMPL"); //  SpecLab; goto L1;
				case S_LABEL ->       Util.IERR("NOT IMPL"); //      DefLab; goto L2;
				case S_PROFILE ->     Util.IERR("NOT IMPL"); //    InProfile(P_ROUTINE); goto L3;
				case S_ROUTINE ->     RoutineDescr.inRoutine();
				case S_IF ->          Util.IERR("NOT IMPL"); //         IfConstruction(true); goto L5;
				case S_SKIPIF ->      Util.IERR("NOT IMPL"); //     SkipifConstruction(true); goto L6;
				case S_SAVE ->        Util.IERR("NOT IMPL"); //       ProtectConstruction(true); goto L7;
				case S_INSERT ->      Util.IERR("NOT IMPL"); //     InputModule(false); goto L8;
				case S_SYSINSERT ->   Util.IERR("NOT IMPL"); //  InputModule(true); goto L9;
//				case Scode.S_LOCAL -> Variable.ofGlobal(Global.DSEG);
				default -> { if(! instruction()) break LOOP; }
			}
			Scode.inputInstr();
		}
//		System.out.println("S_Module.programElements: Terminated by: " + Scode.curinstr);
//		Util.IERR("");
	}


	private static void SetLine(Q_LINE.Subc subc) {
		curline = Scode.inNumber();
//	%+D    if SK1LIN <> 0
//	%+D    then if curline >= SK1LIN
//	%+D         then trc = SK1TRC; txx = trc/10; InputTrace =   trc-(10*txx);
//	%+D              trc = txx; txx = trc/10;    TraceMode =    trc-(10*txx);
//	%+D              trc = txx; txx = trc/10;    ModuleTrace =  trc-(10*txx);
//	%+D              trc = txx; txx = trc/10;    listsw =       trc-(10*txx);
//	%+D              trc = txx; txx = trc/10;    listq1 =       trc-(10*txx);
//	%+D              trc = txx; txx = trc/10;    listq2 =       trc-(10*txx);
//	%+D              if SK1TRC = 0 then SK1LIN = 0
//	%+D              else SK1LIN = SK1LIN+5; SK1TRC = 0 endif;
//	%+D         endif;
//	%+D    endif;
		
//		Qfunc.Qf2(Opcode.qLINE,type,0,cANY,curline);
		new Q_LINE(subc, curline);
		
//	%-D    --- only STM is inserted in prod. versions ---
//	%-D    if type=qSTM then Qf2(qLINE,type,0,cANY,curline) endif;
	}

	private static void DefLab() {
//	begin infix(WORD) tag,smbx; ref(IntDescr) v; InTag(%tag%);
//	%+D   RST(R_DefLab);
//	      v = if DISPL(tag.HI)=none then none else DISPL(tag.HI).elt(tag.LO);
		Tag tag = Tag.inTag();
		LabelDescr v = (LabelDescr) Display.lookup(tag);
		if(v == null) {
//	      then v = NEWOBJ(K_IntLabel,size(IntDescr)); smbx.val = 0;
			v = new LabelDescr(tag);
//	        v.adr = NewFixAdr(CSEGID,smbx);
	        Display.add(v);
//	%+C   else v = DISPL(tag.HI).elt(tag.LO);
//	%+C        if v.adr.kind <> fixadr then IERR("Parse.DefLAB-1") endif;
		};
//	      DefLABEL(0,v.adr.fix.val,0);
//	%+C   CheckStackEmpty;
		Util.IERR("");
	}

//	%title ***   I n s t r u c t i o n   ***

	public static boolean instruction() { // export Boolean result;
//	begin range(0:MaxWord) n,s,ofst; ref(Temp) tmp;
//	      infix(WORD) tag,i,sx,wrd,ndest; Boolean OldTSTOFL;
//	      range(0:MaxByte) cond,b,b1,b2; short integer repdist;
//	      ref(Descriptor) v; infix(ValueItem) itm;
//	      infix(MemAddr) d,a; ref(Qpkt) LL;
//	      range(0:MaxType) type; ref(Temp) x,y,z;
//	      ref(RecordDescr) fixrec; ref(LocDescr) attr;
//	      ref(Address) adr; ref(SwitchDescr) sw;

		boolean result = true;
		IO.println("BEFORE switch in instruction: Curinstr="+Scode.curinstr);
//		Util.IERR("NOT IMPL");
		switch(Scode.curinstr) {
	      case S_CONSTSPEC:     Util.IERR("NOT IMPL"); //   inConstant(false)
	      case S_CONST:     Util.IERR("NOT IMPL"); //       inConstant(true)
	      case S_RECORD:     Util.IERR("NOT IMPL"); //      InRecord
	      case S_ROUTINESPEC:     Util.IERR("NOT IMPL"); // SpecRut(false)
	      case S_SETOBJ:     Util.IERR("NOT IMPL"); //
//	%+D        CheckTosInt; CheckSosValue; CheckSosType(T_OADDR);
//	%+D        IERR("SSTMT.SETOBJ is not implemented");
	      case S_GETOBJ:     Util.IERR("NOT IMPL"); //
//	%+D        CheckTosInt;
//	%+D        IERR("SSTMT.GETOBJ is not implemented");
	      case S_ACCESS,S_ACCESSV:     Util.IERR("NOT IMPL"); //
//	%+D        IERR("SSTMT.ACCESS is not implemented");
	      case S_PUSH,S_PUSHV:   PUSH.ofScode(); break; 
	      case S_PUSHC:          PUSHC.ofScode(); break; // var PushConst;
	      case S_INDEX,S_INDEXV:     Util.IERR("NOT IMPL"); //
//	%+C        CheckTosInt; CheckSosRef;
//	           adr = TOS.suc; repdist = adr.repdist;
//	           if repdist=0 then IERR("PARSE.INDEX: Not info type") endif;
//	           if TOS.kind=K_Coonst
//	           then itm = TOS qua Coonst.itm;
//	                adr.Offset = adr.Offset+(repdist*itm.wrd);
//	                GQpop;
//	                if adr.AtrState=FromConst
//	                then adr.AtrState = NotStacked;
//	                     qPOPKill(AllignFac);
//	                endif;
//	           else
//	%-E             if TOS.type <> T_WRD2 then GQconvert(T_WRD2) endif;
//	%-E             GetTosAdjustedIn86(qAX); Pop; AssertObjStacked;
//	%-E             GQiMultc(repdist); -- AX = AX*repdist
//	%-E             if    adr.AtrState=FromConst then qPOPKill(2)
//	%-E             elsif adr.AtrState=Calculated
//	%-E             then Qf1(qPOPR,qBX,cVAL); Qf2(qDYADR,qADDF,qAX,cVAL,qBX) endif;
//	%-E             Qf1(qPUSHR,qAX,cVAL); adr.AtrState = Calculated;
//	%+E             if TOS.type <> T_WRD4 then GQconvert(T_WRD4) endif;
//	%+E             GetTosAdjustedIn86(qEAX); Pop; AssertObjStacked;
//	%+E             GQeMultc(repdist); -- EAX = EAX*repdist
//	%+E             if    adr.AtrState=FromConst then qPOPKill(4)
//	%+E             elsif adr.AtrState=Calculated
//	%+E             then Qf1(qPOPR,qEBX,cVAL);
//	%+E                  Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
//	%+E             endif;
//	%+E             Qf1(qPUSHR,qEAX,cVAL); adr.AtrState = Calculated;
//	           endif;
//	           if CurInstr=S_INDEXV then GQfetch endif;
	      case S_SELECT,S_SELECTV: SELECT.ofScode(Scode.curinstr); break;
	      case S_REMOTE,S_REMOTEV: REMOTE.ofScode(Scode.curinstr); break;
//	%+C        CheckTosType(T_OADDR);
//	           GQfetch; InTag(%tag%);
//	           attr = DISPL(tag.HI).elt(tag.LO); Pop;
//	           a.kind = reladr; a.rela.val = 0; a.segmid.val = 0;
//	%-E        a.sbireg = 0;
//	%+E        a.sibreg = NoIBREG;
//	           adr = NewAddress(attr.type,attr.rela,a);
//	           adr.ObjState = Calculated; Push(adr);
//	           if CurInstr=S_REMOTEV then GQfetch endif;
	      case S_FETCH:     Util.IERR("NOT IMPL"); // GQfetch;
	      case S_DEREF:     Util.IERR("NOT IMPL"); //
//	%+C        CheckTosRef;
//	           adr = TOS;
//	%+S        if SYSGEN <> 0
//	%+S        then if adr.repdist <> (TTAB(adr.type).nbyte)
//	%+S             then WARNING("DEREF on parameter") endif;
//	%+S        endif;
//	           AssertAtrStacked; Pop; pushTemp(T_GADDR);
	      case S_REFER:     Util.IERR("NOT IMPL"); //
//	           type = intype;
//	%+C        CheckTosType(T_GADDR);
//	           a.kind = reladr; a.rela.val = 0; a.segmid.val = 0;
//	%-E        a.sbireg = 0;
//	%+E        a.sibreg = NoIBREG;
//	           adr = NewAddress(type,0,a);
//	           GQfetch; adr.ObjState = adr.AtrState = Calculated;
//	           Pop; Push(adr);
//	      case S_DSIZE:     Util.IERR("NOT IMPL"); //
//	           InTag(%tag%); fixrec = DISPL(tag.HI).elt(tag.LO);
//	           if fixrec.nbrep <> 0
//	           then n = fixrec.nbrep;
//	%+C             CheckTosInt;
//	                if TOS.kind=K_Coonst
//	                then itm = TOS qua Coonst.itm; GQpop;
//	                     n = wAllign(%(n*(itm.wrd))+fixrec.nbyte%);
//	%-E                  Qf2(qPUSHC,0,qAX,cVAL,n); itm.int = n;
//	%+E                  Qf2(qPUSHC,0,qEAX,cVAL,n); itm.int = n;
//	                     pushCoonst(T_SIZE,itm);
//	                else
//	%-E                  if TOS.Type = T_WRD4
//	%-E                  then GQfetch; Qf1(qPOPR,qAX,cVAL);
//	%-E                       Qf1(qPOPR,qDX,cVAL);
//	%-E                       NotMindMask = uDX;
//	%-E                       Qf2(qDYADR,qORM,qDX,cVAL,qDX);
//	%-E                       PreReadMask = uAX;
//	%-E                       PreMindMask = wOR(PreMindMask,uAX);
//	%-E                       LL = ForwJMP(q_WEQ);
//	%-E                       Qf2(qDYADR,qXOR,qAX,cVAL,qAX);
//	%-E                       Qf2(qMONADR,qDEC,qAX,cVAL,0);
//	%-E                       PreReadMask = uAX;
//	%-E                       PreMindMask = wOR(PreMindMask,uAX);
//	%-E                       DefFDEST(LL); Qf1(qPUSHR,qAX,cVAL);
//	%-E                       Pop; pushTemp(T_BYT2);
//	%-E                  elsif TOS.type <> T_BYT2
//	%-E                  then GQconvert(T_BYT2) endif;
//	%-E                  GetTosAdjustedIn86(qAX); Pop;
//	%-E                  OldTSTOFL = TSTOFL; TSTOFL = true;
//	%-E                  if n > 1
//	%-E                  then GQwMultc(n); -- AX = AX*n
//	%-E                       Qf2(qDYADC,qADDF,qAX,cVAL,fixrec.nbyte);
//	%-E                  else Qf2(qDYADC,qADDF,qAX,cVAL,fixrec.nbyte+1);
//	%-E                       Qf2(qDYADC,qAND,qAX,cVAL,65534);
//	%-E                  endif;
//	%-E                  TSTOFL = OldTSTOFL;
//	%-E                  Qf1(qPUSHR,qAX,cVAL);
//
//	%+E                  GetTosAdjustedIn86(qEAX); Pop;
//	%+E                  OldTSTOFL = TSTOFL; TSTOFL = true;
//	%+E                  if n > 3
//	%+E                  then GQeMultc(n); -- EAX = EAX*n
//	%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte);
//	%+E                  else if n>1 then GQeMultc(n) endif; -- EAX = EAX*n
//	%+E                       Qf2(qDYADC,qADDF,qEAX,cVAL,fixrec.nbyte+3);
//	%+E                       Qf2(qDYADC,qAND,qEAX,cVAL,-4);
//	%+E                  endif;
//	%+E                  TSTOFL = OldTSTOFL;
//	%+E                  Qf1(qPUSHR,qEAX,cVAL);
//	                     pushTemp(T_SIZE);
//	                endif;
//	           else
//	%+D             edit(errmsg,fixrec);
//	                IERR("Illegal DSIZE on: ");
//	                GQpop; itm.int = 0; pushCoonst(T_SIZE,itm);
//	           endif;
	      case S_DUP:     Util.IERR("NOT IMPL"); // GQdup
	      case S_POP:     Util.IERR("NOT IMPL"); // if TOS <> none then GQpop
//	                  else IERR("POP -- Stack is empty") endif;
	      case S_POPALL:     Util.IERR("NOT IMPL"); //
//	%+D        b = InputByte;
//	%-D        InByte(%b%);
//	           repeat while TOS <> none
//	           do
//	%+C           if TOS.kind = K_ProfileItem
//	%+C           then b = (b-1)+TOS qua ProfileItem.nasspar
//	%+C           elsif TOS.kind <> K_Result then b = b-1 endif;
//	%+D           if TraceMode <> 0
//	%+D           then outstring("*** Pop: "); edit(sysout,TOS);
//	%+D                outstring(", n:"); outword(b); printout(sysout);
//	%+D           endif;
//	              --- do not generate superfluous POPRs
//	              if nextbyte<>S_ENDSKIP then GQpop else pop endif;
//	           endrepeat;
//	%+C        if b <> 0 then IERR("POPALL n  --  wrong value of n") endif;
//	%+C        CheckStackEmpty;
	      case S_ASSIGN:     Util.IERR("NOT IMPL"); //     GQassign
	      case S_UPDATE:     Util.IERR("NOT IMPL"); //     GQupdate
	      case S_RUPDATE: 	 RUPDATE.ofScode(); break;
	      case S_BSEG:     Util.IERR("NOT IMPL"); //       BSEGInstruction
	      case S_IF:     Util.IERR("NOT IMPL"); //         IfConstruction(false)
	      case S_SKIPIF:     Util.IERR("NOT IMPL"); //     SkipifConstruction(false)
	      case S_PRECALL:    CallInstruction.ofScode(0); break;
	      case S_ASSCALL:     Util.IERR("NOT IMPL"); //    CallInstruction(1)
	      case S_REPCALL:     Util.IERR("NOT IMPL"); //    
//	%+D                      b = InputByte; CallInstruction(b)
//	%-D                      InByte(%b%);  CallInstruction(b)
	      case S_GOTO:     Util.IERR("NOT IMPL"); //
//	%-E        if TOS.type = T_NPADR
//	%-E        then
//	%-E             if TOS.kind=K_Coonst
//	%-E             then qPOPKill(2);
//	%-E                  a = TakeTOS qua Coonst.itm.base;
//	%-E                  if InsideRoutine
//	%-E                  then PreReadMask = uSP; Qf4c(qLOAD,0,qSP,cSTP,0,X_INITSP,0);
//	%-E %+D                    showWARN("SP set to INITSP");
//	%-E                       PreReadMask = uBP; Qf2(qLOADC,0,qBP,cSTP,0);
//	%-E                  endif;
//	%-E                  Qf5(qJMP,0,0,0,a);
//	%-E             else if InsideRoutine
//	%-E                  then GetTosValueIn86(qAX); Pop;
//	%-E                       PreReadMask = uSP; Qf4c(qLOAD,0,qSP,cSTP,0,X_INITSP,0);
//	%-E %+D                    showWARN("SP set to INITSP");
//	%-E                       PreReadMask = uBP; Qf2(qLOADC,0,qBP,cSTP,0);
//	%-E                       Qf1(qPUSHR,qCS,cANY); Qf1(qPUSHR,qAX,cANY);
//	%-E                  else Qf1(qPUSHR,qCS,cANY); GQfetch; Pop endif;
//	%-E                  Qf2(qRET,0,0,0,0);
//	%-E             endif;
//	%-E        else
//	%+C             CheckTosType(T_PADDR);
//	                if TOS.kind=K_Coonst
//	                then qPOPKill(AllignFac);
//	%-E                  qPOPKill(2);
//	                     a = TakeTOS qua Coonst.itm.base;
//	                     if InsideRoutine
//	                     then
//	%-E                     PreReadMask = uSP;  Qf4c(qLOAD,0,qSP,cSTP,0,X_INITSP,0);
//	%+E                     PreReadMask = uESP; Qf4c(qLOAD,0,qESP,cSTP,0,X_INITSP,0);
//	%-E                     PreReadMask = uBP;  Qf2(qLOADC,0,qBP,cSTP,0);
//	%+E                     PreReadMask = uEBP; Qf2(qLOADC,0,qEBP,cSTP,0);
//	%+D                        showWARN("SP set to INITSP");
//	                     endif;
//	                     Qf5(qJMP,0,0,0,a)
//	%-E             elsif (BNKLNK=0) and (not InsideRoutine)
//	%-E             then GQfetch; Pop; Qf2(qRET,0,0,0,0);
//	                else
//	%-E %+D              showWARN("E-GOTO called");
//	%-E                  GetTosValueIn86R3(qAX,qBX,0); Pop; a = X_GOTO;
//	%-E                  PreReadMask = wOR(uAX,uBX); Qf5(qJMP,0,0,0,a)
//	-- ????? %+E                  GQfetch; Pop; Qf2(qRET,0,0,0,0);
//	%+E                  if InsideRoutine
//	%+E                  then GetTosAdjustedIn86(qEAX); Pop;
//	%+E                     PreReadMask = uESP; Qf4c(qLOAD,0,qESP,cSTP,0,X_INITSP,0);
//	%+E %+D                    showWARN("SP set to INITSP");
//	%+E                     PreReadMask = uEBP; Qf2(qLOADC,0,qEBP,cSTP,0);
//	%+E                     Qf1(qPUSHR,qEAX,cANY);
//	%+E                  else GQfetch; Pop endif;
//	%+E                  Qf2(qRET,0,0,0,0);
//	                endif;
//	%-E        endif;
//	%+C        CheckStackEmpty;
	      case S_PUSHLEN:     Util.IERR("NOT IMPL"); // itm.int = Pushlen;
//	                      if not SkipProtect
//	                      then pushCoonst(T_SIZE,itm);
//	%-E                        Qf2(qPUSHC,0,qAX,cVAL,itm.wrd);
//	%+E                        Qf2(qPUSHC,0,qEAX,cVAL,itm.wrd);
//	                      endif;
	      case S_SAVE:     Util.IERR("NOT IMPL"); //    ProtectConstruction(false)
	      case S_T_INITO:     Util.IERR("NOT IMPL"); //
//	%+SC                     CheckTosType(T_OADDR);
//	%+S                      GQfetch; Pop;
//	%+S                      Qf5(qCALL,0,0,4,X_INITO);
	      case S_T_GETO:     Util.IERR("NOT IMPL"); //
//	%+S %-E                  Qf2(qDYADC,qSUB,qSP,cSTP,4);
//	%+SE                     Qf2(qDYADC,qSUB,qESP,cSTP,4);
//	%+S                      Qf5(qCALL,0,0,4,X_GETO);
//	%+S                      Qf2(qADJST,0,0,0,4);
//	%+S                      pushTemp(T_OADDR);
	      case S_T_SETO:     Util.IERR("NOT IMPL"); //
//	%+SC                     CheckTosType(T_OADDR);
//	%+S                      GQfetch; Pop;
//	%+S                      Qf5(qCALL,0,0,4,X_SETO);
	      case S_LINE:   LINE.ofScode(Q_LINE.Subc.src); break;
	      case S_DECL:     Util.IERR("NOT IMPL"); //
//	%+C                      CheckStackEmpty;
//	                         SetLine(qDCL)
	      case S_STMT:     Util.IERR("NOT IMPL"); // 
//	%+C                      CheckStackEmpty;
//	                         SetLine(qSTM)
	      case S_EMPTY:     Util.IERR("NOT IMPL"); //
//	%+C                      CheckStackEmpty
	      case S_SETSWITCH:     Util.IERR("NOT IMPL"); //  SetSwitch
	      case S_INFO:     Util.IERR("NOT IMPL"); //       Ed(errmsg,InString); WARNING("Unknown info: ");
	      case S_DELETE:     Util.IERR("NOT IMPL"); //
//	           i = GetLastTag; InTag(%tag%);
//	%+C        if (tag.val<MinTag) or (tag.val>i.val)
//	%+C        then IERR("Argument to DELETE is out of range")
//	%+C        else
//	                repeat if DISPL(i.HI) = none then -- Nothing
//	                       elsif DISPL(i.HI).elt(i.LO) <> none
//	                       then DELETE(DISPL(i.HI).elt(i.LO));
//	                            DISPL(i.HI).elt(i.LO) = none;
//	                       endif;
//	                while i.val>tag.val do i.val = i.val-1 endrepeat;
//	%+C        endif;
	      case S_ZEROAREA:     Util.IERR("NOT IMPL"); //
//	%+C        CheckTosType(T_OADDR); CheckSosValue; CheckSosType(T_OADDR);
//	%-E        GQfetch; Qf1(qPOPR,qCX,cVAL); qPOPKill(2); Pop;
//	%-E        GQfetch; Qf1(qPOPR,qDI,cOBJ); Qf1(qPOPR,qES,cOBJ);
//	%-E        PreMindMask = wOR(PreMindMask,uES); Qf1(qPUSHR,qES,cOBJ);
//	%-E        PreMindMask = wOR(PreMindMask,uDI); Qf1(qPUSHR,qDI,cOBJ);
//	%-E        Qf2(qLOADC,0,qAX,cVAL,0); Qf2(qRSTRW,qZERO,qCLD,cVAL,qREP);
//	%+E        GQfetch; Qf1(qPOPR,qECX,cVAL); Pop; GQfetch; Qf1(qPOPR,qEDI,cOBJ);
//	%+E        PreMindMask = wOR(PreMindMask,uEDI); Qf1(qPUSHR,qEDI,cOBJ);
//	%+E        Qf2(qLOADC,0,qEAX,cVAL,0); Qf2(qRSTRW,qZERO,qCLD,cVAL,qREP);
	      case S_INITAREA:     Util.IERR("NOT IMPL"); //    intype;
//	%+C                       CheckTosType(T_OADDR);
	      case S_EVAL:     Util.IERR("NOT IMPL"); //        --  Qf1(qEVAL,0) -- REMOVED FOR AD'HOC TEST ???????
	      case S_FJUMPIF:  FJUMPIF.ofScode(); break;
	      case S_FJUMP:     Util.IERR("NOT IMPL"); //
//	%+D        b = InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b) <> none then IERR("PARSE:FJUMP") endif;
//	           DESTAB(b) = ForwJMP(0);
//	%+C        CheckStackEmpty;
	      case S_FDEST:     Util.IERR("NOT IMPL"); //
//	%+D        b = InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b)=none then IERR("PARSE.FDEST") endif;
//	           DefFDEST(DESTAB(b)); DESTAB(b) = none;
//	%-E        if FWRTAB(b)<>none then DefFDEST(FWRTAB(b)); FWRTAB(b) = none; endif
//	%+C        CheckStackEmpty;
	      case S_BDEST:     Util.IERR("NOT IMPL"); //
//	%+D        b = InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b) <> none then IERR("PARSE.BDEST") endif;
//	           DESTAB(b) = DefBDEST;
//	%+C        CheckStackEmpty;
	      case S_BJUMP:     Util.IERR("NOT IMPL"); //
//	%+D        b = InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b)=none then IERR("PARSE.BJUMP") endif;
//	           BackJMP(0,DESTAB(b)); DESTAB(b) = none;
//	%+C        CheckStackEmpty;
	      case S_BJUMPIF:     Util.IERR("NOT IMPL"); //
//	%-E        cond = GQrelation(false);
//	%+E        cond = GQrelation;
//	%+D        b = InputByte;
//	%-D        InByte(%b%);
//	%+C        if DESTAB(b)=none then IERR("PARSE.BJUMPIF") endif;
//	           if TOS=SAV then BackJMP(cond,DESTAB(b))
//	           else LL = ForwJMP(NotQcond(cond)); ClearSTK;
//	                BackJMP(0,DESTAB(b)); DefFDEST(LL);
//	           endif;
//	           DESTAB(b) = none;
	      case S_SWITCH:     Util.IERR("NOT IMPL"); //
//	           InTag(%tag%);
//	%+D        ndest = InputNumber;
//	%-D        InNumber(%ndest%);
//	           sw = NEWOBJ(K_SwitchDescr,size(SwitchDescr));
//	           sw.ndest = ndest.val; sw.nleft = ndest.val;
//	           if ndest.HI >= MxpSdest
//	           then ERROR("Too large Case-Statement") endif;
//	           i.val = 0; sw.swtab = NewFixAdr(DSEGID,i); IntoDisplay(sw,tag);
//	%+C        CheckTosInt;
//	           if TOS.type < T_WRD2 then GQconvert(T_WRD2) endif;
//	           a = sw.swtab;
//	%-E        if DSEGID=DGROUP then a.sbireg = bOR(oSS,rmBX);
//	%-E        else Qf2b(qLOADSC,qDS,qBX,cOBJ,0,sw.swtab);
//	%-E             a.sbireg = bOR(oDS,rmBX);
//	%-E        endif;
//	%-E        GetTosAdjustedIn86(qBX); Pop;
//	%+E        GetTosAdjustedIn86(qEBX); Pop;
//
//	%+D        if IDXCHK <> 0 then --- pje 22.10.90
//	%+D           PreMindMask = wOR(PreMindMask,uBX);
//	%+D %-E       Qf2(qDYADC,qCMP,qBX,cVAL,ndest.val);
//	%+DE          Qf2(qDYADC,qCMP,qEBX,cVAL,ndest.val);
//	%+D %-E       if DSEGID=DGROUP then PreReadMask = uBX
//	%+D %-E       else PreReadMask = wOR(uDS,uBX) endif;
//	%+DE          PreReadMask = uBX;
//	%+D           LL = ForwJMP(q_WLT);
//	%+D           Qf5(qCALL,0,0,0,X_ECASE); -- OutOfRange ==> ERROR
//	%+D %-E       Qf2(qLOADC,0,qBX,cVAL,0);
//	%+DE          Qf2(qLOADC,0,qEBX,cVAL,0);
//	%+D           PreReadMask = uBX;
//	%+D %-E       if DSEGID=DGROUP then PreMindMask = wOR(PreMindMask,uBX)
//	%+D %-E       else PreMindMask = wOR(PreMindMask,wOR(uDS,uBX)) endif;
//	%+DE          PreMindMask = wOR(PreMindMask,uBX);
//	%+D           DefFDEST(LL);
//	%+D        endif; --- pje 22.10.90
//
//	%-E        Qf2(qDYADR,qADD,qBX,cVAL,qBX);
//	%+E        a.sibreg = bOR(bOR(128,bEBX),iEBX); -- swtab+[4*EBX] 
//	           Qf3(qJMPM,0,0,0,a);
	      case S_SDEST:     Util.IERR("NOT IMPL"); //
//	           InTag(%tag%); sw = DISPL(tag.HI).elt(tag.LO);
//	%+C        if sw.kind <> K_SwitchDescr
//	%+C        then IERR("Display-entry is not defined as a switch") endif;
//	%+D        sx = InputNumber;
//	%-D        InNumber(%sx%);
//	%+C        if sx.val >= sw.ndest then IERR("Illegal switch index")
//	%+C        else
//	                i.val = 0; a = NewFixAdr(CSEGID,i);
//	                if sw.DESTAB(sx.HI)=none
//	                then sw.DESTAB(sx.HI) = 
//	                        NEWOBJ(K_AddrBlock,size(AddrBlock)) endif;
//	                sw.DESTAB(sx.HI).elt(sx.LO) = a;
//	                DefLABEL(0,a.fix.val,0);
//	%+C        endif;
//	           sw.nleft = sw.nleft-1;
//	           if sw.nleft=0
//	           then EmitSwitch(sw); DELETE(sw);
//	                DISPL(tag.HI).elt(tag.LO) = none;
//	           endif;
	      case S_CONVERT:     Util.IERR("NOT IMPL"); //  type = intype;
//	           if type < T_REAL
//	           then if TOS.type=T_CHAR then type = T_BYT1 endif endif;
//	           GQconvert(type)
	      case S_NEG:     Util.IERR("NOT IMPL"); //  GQneg
	      case S_ADD:     Util.IERR("NOT IMPL"); //  GQadd
	      case S_SUB:     Util.IERR("NOT IMPL"); //  GQsub
	      case S_MULT:     Util.IERR("NOT IMPL"); // GQmult
	      case S_DIV:     Util.IERR("NOT IMPL"); //  GQdiv
	      case S_REM:     Util.IERR("NOT IMPL"); //  GQrem
	      case S_NOT:     Util.IERR("NOT IMPL"); //  GQnot
	      case S_AND:     Util.IERR("NOT IMPL"); //  GQandxor(qAND)
	      case S_OR:     Util.IERR("NOT IMPL"); //   GQandxor(qOR )
	      case S_XOR:     Util.IERR("NOT IMPL"); //  GQandxor(qXOR)
	      case S_EQV:     Util.IERR("NOT IMPL"); //  GQeqv
	      case S_IMP:     Util.IERR("NOT IMPL"); //  GQimp
	      case S_LSHIFTL:     Util.IERR("NOT IMPL"); // GQshift(qSHL); -- Extension to S-Code: Left shift logical
	      case S_LSHIFTA:     Util.IERR("NOT IMPL"); // GQshift(qSHL); -- Extension to S-Code: Left shift arithm.
	      case S_RSHIFTL:     Util.IERR("NOT IMPL"); // GQshift(qSHR); -- Extension to S-Code: Right shift logical
	      case S_RSHIFTA:     Util.IERR("NOT IMPL"); // GQshift(qSAR); -- Extension to S-Code: Right shift arithm.
	      case S_LOCATE:     Util.IERR("NOT IMPL"); //
//	%+C        CheckTosType(T_AADDR); CheckSosValue;
//	%+C        CheckSosType2(T_OADDR,T_GADDR);
//	%-E        GetTosValueIn86(qAX); Pop; GQfetch;
//	%+E        GetTosValueIn86(qEAX); Pop; GQfetch;
//	           if TOS.type=T_GADDR
//	           then
//	%-E             Qf1(qPOPR,qBX,cVAL); Qf2(qDYADR,qADDF,qAX,cVAL,qBX);
//	%+E             Qf1(qPOPR,qEBX,cVAL); Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
//	           endif;
//	%-E        Qf1(qPUSHR,qAX,cVAL);
//	%+E        Qf1(qPUSHR,qEAX,cVAL);
//	           Pop; pushTemp(T_GADDR);
	      case S_INCO:     Util.IERR("NOT IMPL"); // GQinco_deco(true)
	      case S_DECO:     Util.IERR("NOT IMPL"); // GQinco_deco(false)
	      case S_DIST:     Util.IERR("NOT IMPL"); //
//	%+C        CheckTosType(T_OADDR); CheckSosValue; CheckSosType(T_OADDR);
//	%-E        GQfetch; Qf1(qPOPR,qDX,cOBJ); qPOPKill(2); Pop;
//	%-E        GQfetch; Qf1(qPOPR,qAX,cOBJ); qPOPKill(2); Pop;
//	%-E        Qf2(qDYADR,qSUBF,qAX,cVAL,qDX); Qf1(qPUSHR,qAX,cVAL);
//	%+E        GQfetch; Qf1(qPOPR,qEDX,cOBJ); Pop;
//	%+E        GQfetch; Qf1(qPOPR,qEAX,cOBJ); Pop;
//	%+E        Qf2(qDYADR,qSUBF,qEAX,cVAL,qEDX); Qf1(qPUSHR,qEAX,cVAL);
//	           pushTemp(T_SIZE);
	      case S_COMPARE:     Util.IERR("NOT IMPL"); //
//	%-E        cond = GQrelation(false);
//	%+E        cond = GQrelation;
//	           Qf2(qLOADC,0,qAL,cVAL,0); Qf2(qCONDEC,cond,qAL,cVAL,0);
//	           Qf1(qPUSHR,qAL,cVAL);     pushTemp(T_BOOL);
	      default: result = false;
		}
//
//	%+D   if TraceMode <> 0 then DumpStack endif;
		return result;
	}

}
