package PREV;

import java.io.IOException;
import java.util.HashMap;

import bec.ModuleIO;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.DataType;
import bec.descriptor.ConstDescr;
import bec.descriptor.LabelDescr;
import bec.descriptor.Kind;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.RecordDescr;
import bec.descriptor.Variable;
import bec.instruction.Instruction;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.statement.IfStatement;
import bec.statement.InsertStatement;
import bec.statement.ProtectStatement;
import bec.statement.SkipifStatement;
import bec.util.Array;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.virtualMachine.SVM_LINE;
import bec.virtualMachine.SVM_NOT_IMPL;

public class PREV_MONITOR {

	public static void parse() {
		Scode.inputInstr();
		if(Scode.curinstr == Scode.S_PROGRAM) {
//	  		System.out.println("Parse.MONITOR: S_PROGRAM");
	  		Global.progIdent = Scode.inString();
			Scode.inputInstr();
			if(Scode.curinstr == Scode.S_GLOBAL) {
				interfaceModule();
				Scode.inputInstr();
			}
			while(Scode.curinstr == Scode.S_MODULE) {
				moduleDefinition();
				Scode.inputInstr();
			}
			if(Scode.curinstr == Scode.S_MAIN) {
				mainProgram();
			}
		} else Util.IERR("Illegal S-Program");
	}
	
	/**
	 * 	MainPprogram ::= main <local_quantity>* <program_element>*
	 * 
	 * 		local_quantity ::= local var:newtag quantity_descriptor
	 * 
	 *			quantity_descriptor ::= resolved_type < Rep count:number >?
	 *
	 *		program_element
	 *			::= instruction
	 *			::= label_declaration
	 *			::= routine_profile
	 *			::= routine_definition
	 *			::= if_statement
	 *			::= skip_statement
	 *			::= insert_statement
	 *			::= protect_statement
	 *			::= delete_statement
	 */
	private static void mainProgram() {
		//  M a i n   P r o g r a m  ---
//        if PROGID.val=0 then PROGID:=DefSymb("MAIN") endif;
//        BEGASM(CSEGNAM,DSEGNAM); ed(sysedit,"SIM_");
//        EdSymb(sysedit,PROGID); entx:=DefPubl(pickup(sysedit));
//        MainEntry:=NewFixAdr(CSEGID,entx);
//        DefLABEL(qBPROC,MainEntry.fix.val,entx.val);
	
		while(Scode.nextByte() == Scode.S_LOCAL) {
			Scode.inputInstr(); 
			Util.IERR("NOT IMPL");
//			Minut.inGlobal();
		}
//        if LtabEntry.kind <> 0
//        then Ltab.kind:=segadr; Ltab.rela.val:=0;
//             Ltab.segmid:=LSEGID;
//             opr.kind:=extadr; opr.rela.val:=0;
//             opr.smbx:=DefExtr("G@PRGINF",DGROUP);
//%-E                  Ltab.sbireg:=0;       opr.sbireg:=oSS;
//%+E                  Ltab.sibreg:=NoIBREG; opr.sibreg:=NoIBREG;
//%-E                  Qf2b(qLOADC,0,qAX,cOBJ,F_OFFSET,Ltab);
//%-E                  Qf3(qSTORE,0,qAX,cOBJ,opr);
//%-E                  opr.rela.val:=opr.rela.val+2;
//%-E                  Qf2b(qLOADC,0,qAX,cOBJ,F_BASE,Ltab);
//%-E                  Qf3(qSTORE,0,qAX,cOBJ,opr);
//%+E                  Qf2b(qLOADC,0,qEAX,cOBJ,0,Ltab);
//%+E                  Qf3(qSTORE,0,qEAX,cOBJ,opr);
//        endif;

//		Util.IERR("NOT IMPL");
		Scode.inputInstr(); 
		programElements();

//        Qf2(qRET,0,0,0,0);
//        DefLABEL(qEPROC,MainEntry.fix.val,entx.val);
//        peepExhaust(true); ENDASM;
	
		if(Scode.curinstr != Scode.S_ENDPROGRAM)
			Util.IERR("Illegal termination of program");
		
	}
	
	
	/**
	 * 	interface_module
	 * 		::= global module module_id:string checkcode:string
	 * 					<global interface>* tag_list
	 * 					body < init global:tag type repetition_value >*
	 * 			endmodule
	 * 
	 * 		global_interface
	 * 			::= record_descriptor
	 * 			::= constant_definition < system sid:string >?
	 * 			::= global_definition < system sid:string >?
	 * 			::= routine_profile
	 * 			::= info_setting
	 * 
	 * 				global_definition ::= global internal:newtag quantity_descriptor
	 * 
	 * 		tag_list ::= < tag internal:tag external:number >+
	 * 
	 */
	private static void interfaceModule() {
		Scode.inputInstr();
		if(Scode.curinstr != Scode.S_MODULE) Util.IERR("Missing - MODULE");
		Global.modident = Scode.inString();
		Global.modcheck = Scode.inString();
		Global.moduleID = Global.modident;
		String sourceID = Global.getSourceID();
		Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		if(Global.PROGID == null) Global.PROGID = Global.modident;
		LOOP: while(true) {
			Scode.inputInstr();
			switch(Scode.curinstr) {
				case Scode.S_GLOBAL:	Variable.ofGlobal(Global.DSEG); break;
				case Scode.S_CONSTSPEC: ConstDescr.inConstant(false); break;
				case Scode.S_CONST:		ConstDescr.inConstant(true);	break;
				case Scode.S_RECORD:	RecordDescr.of(); break;
				case Scode.S_PROFILE:   ProfileDescr.inProfile(Kind.P_VISIBLE); break;
				case Scode.S_ROUTINE:	RoutineDescr.ofRoutineDef();	break;
				case Scode.S_LINE:		setLine(0); break;
				case Scode.S_DECL:		CTStack.checkStackEmpty(); setLine(Kind.qDCL); break;
				case Scode.S_STMT:		CTStack.checkStackEmpty(); setLine(Kind.qSTM); break;
				case Scode.S_SETSWITCH:	setSwitch(); break;
				case Scode.S_INFO:		Util.WARNING("Unknown info: " + Scode.inString());
//				case Scode.S_INSERT, Scode.S_SYSINSERT: Combine; TERMINATE
				default: break LOOP;
			}
		}

		// tag_list ::= < tag internal:tag external:number >+
		Global.iTAGTAB = new Array<Integer>();
		Global.xTAGTAB = new Array<Integer>();
		int nXtag = 0;
		while(Scode.curinstr == Scode.S_TAG) {
			int itag = Scode.inTag();
			int xtag = Scode.inNumber();
			Global.iTAGTAB.set(xtag, itag); // Index xTag --> value iTag
			Global.xTAGTAB.set(itag, xtag); // Index iTag --> value xTag
			Scode.inputInstr();
			if(xtag > nXtag) nXtag = xtag;
		}
			
			
		try {
			ModuleIO.outputModule(nXtag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Tag.dumpITAGTABLE("MONITOR.interfaceModule'END: ");
		Tag.dumpXTAGTABLE("MONITOR.interfaceModule'END: ");
		DataType.dumpDataTypes("MONITOR.interfaceModule'END: ");
		Global.DSEG.dump("MONITOR.interfaceModule'END: ");
		Global.CSEG.dump("MONITOR.interfaceModule'END: ");
		Global.dumpDISPL("MONITOR.interfaceModule'END: ");
		Scode.dumpTAGIDENTS("MONITOR.interfaceModule'END: ");
		if(Scode.curinstr != Scode.S_BODY) Util.IERR("Illegal termination of module head");
		Scode.inputInstr();
//	%+SC    repeat InputInstr while CurInstr=S_INIT
//	%+SC    do IERR("InterfaceModule: Init values is not supported");
//	%+SC       InTag(%wrd%); intype; SkipRepValue;
//	%+SC    endrepeat;

		if(Scode.curinstr != Scode.S_ENDMODULE) Util.IERR("Improper termination of module");
	}
	
	
	/**
	 * 	module_definition ::= module module_id:string check_code:string
	 * 							visible_existing
	 * 							body <local_quantity>* <program_element>* endmodule
	 * 
	 * 		visible_existing ::= <visible>* tag_list | existing
	 * 
	 * 			visible
	 * 				::= record_descriptor | routine_profile
	 * 				::= routine_specification | label_specification
	 * 				::= constant_specification | insert_statement
	 * 				::= info_setting
	 * 
	 * 			tag_list ::= < tag internal:tag external:number >+
	 * 
	 * 			local_quantity
	 * 				::= local var:newtag quantity_descriptor
	 * 				::= constant_definition                                             // DETTE ER NYTT
	 * 
	 *				constant_definition                                                 // DETTE ER NYTT
	 *					::= const const:spectag quantity_descriptor repetition_value    // DETTE ER NYTT
	 */
	public static void moduleDefinition() {
//	       range(0:MaxWord) nXtag; ref(ModElt) m;
//	       infix(WORD) itag,xtag; infix(Fixup) Fx;
//	       modident:=inMsymb; modcheck:=inSymb;
//	       if PROGID.val = 0
//	       then edsymb(sysedit,modident);
//	            PROGID:=DefSymb(pickup(sysedit));
//	       endif;
//	       BEGASM(CSEGNAM,DSEGNAM); nXtag:=0;
//	       InputInstr;
//	       m:=DICREF(modident);
//	%+S    if SYSGEN=0
//	%+S    then
//	            m.RelElt:=RELID;
//	%+S    endif;
//	       if LtabEntry.kind=0 then -- No LineNumberTable
//	       else
//	%+D         if LtabEntry.kind<>fixadr then IERR("PARSE:Md-1") endif
//	            Fx:=FIXTAB(LtabEntry.fix.HI).elt(LtabEntry.fix.LO);
//	%+D         if Fx.smbx.val=0 then IERR("PARSE:Md-2") endif;
//	            m.LinTab:=Fx.smbx;
//	       endif;
		Global.modident = Scode.inString();
		Global.modcheck = Scode.inString();
		Global.moduleID = Global.modident;
		String sourceID = Global.getSourceID();
		Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		if(Global.PROGID == null) Global.PROGID = Global.modident;

		Scode.inputInstr();
		while(viisible()) { Scode.inputInstr(); }
		
		
//	       repeat while CurInstr=S_TAG
//	       do InTag(%itag%);
//	%+D       xtag:=InputNumber;
//	%-D       InNumber(%xtag%);
//	%+D       if xtag.HI >= MxpXtag then CAPERR(CapTags) endif;
//	          if   TAGTAB(xtag.HI)=none
//	          then TAGTAB(xtag.HI):=
//	               NEWOBJ(K_WordBlock,size(WordBlock)) endif;
//	          TAGTAB(xtag.HI).elt(xtag.LO):=itag;
//	          InputInstr;
//	          if xtag.val>nXtag then nXtag:=xtag.val endif;
//	       endrepeat;
//	       OutputModule(nXtag);

		// tag_list ::= < tag internal:tag external:number >+
		Global.iTAGTAB = new Array<Integer>();
		Global.xTAGTAB = new Array<Integer>();
		int nXtag = 0;
		while(Scode.curinstr == Scode.S_TAG) {
			int itag = Scode.inTag();
			int xtag = Scode.inNumber();
			Global.iTAGTAB.set(xtag, itag); // Index xTag --> value iTag
			Global.xTAGTAB.set(itag, xtag); // Index iTag --> value xTag
			System.out.println("MONITOR: xtag="+xtag+"  itag="+itag);
			Scode.inputInstr();
			if(xtag > nXtag) nXtag = xtag;
		}
		
		
//
//	       if CurInstr <> S_BODY then
//	       IERR("Illegal termination of module head") endif;

		if(Scode.curinstr != Scode.S_BODY) Util.IERR("Illegal termination of module head");
		Scode.inputInstr();
//%+SC    repeat InputInstr while CurInstr=S_INIT
//%+SC    do IERR("InterfaceModule: Init values is not supported");
//%+SC       InTag(%wrd%); intype; SkipRepValue;
//%+SC    endrepeat;
		while(Scode.curinstr == Scode.S_INIT) {
			Util.IERR("InterfaceModule: Init values is not supported");
		}

		
		
//	       repeat while NextByte = S_LOCAL
//	       do inputInstr; inGlobal endrepeat;
		while(Scode.nextByte() == Scode.S_LOCAL) {
//			inGlobal;
			Util.IERR("SJEKK DETTE");
		}

		programElements();
//
//	       if CurInstr <> S_ENDMODULE then
//	       IERR("Improper termination of module") endif;
//	       peepExhaust(true);
//	       ENDASM;

		if(Scode.curinstr != Scode.S_ENDMODULE) Util.IERR("Improper termination of module");

		
		Tag.dumpITAGTABLE("MONITOR.moduleDefinition'END: ");
		Tag.dumpXTAGTABLE("MONITOR.moduleDefinition'END: ");
//		DataType.dumpDataTypes("MONITOR.moduleDefinition'END: ");
//		Global.DSEG.dump("MONITOR.moduleDefinition'END: ");
//		Global.CSEG.dump("MONITOR.moduleDefinition'END: ");
		Global.dumpDISPL("MONITOR.moduleDefinition'END: ");
//		Scode.dumpTAGIDENTS("MONITOR.moduleDefinition'END: ");
		try { ModuleIO.outputModule(nXtag);
		} catch (IOException e) { e.printStackTrace(); }
	}

	/**
	 * 	visible
	 * 		::= record_descriptor | routine_profile
	 * 		::= routine_specification | label_specification
	 * 		::= constant_specification | insert_statement
	 * 		::= info_setting
	 */
	private static boolean viisible() {
		System.out.println("MONITOR.viisible: CurInstr="+Scode.edInstr(Scode.curinstr));
		switch(Scode.curinstr) {
			case Scode.S_CONSTSPEC:		ConstDescr.inConstant(false); break;
			case Scode.S_LABELSPEC:		LabelDescr.ofLabelSpec(); break;
			case Scode.S_RECORD:		RecordDescr.of(); break;
			case Scode.S_PROFILE:		ProfileDescr.inProfile(Kind.P_VISIBLE); break;
			case Scode.S_ROUTINESPEC:	RoutineDescr.ofRoutineSpec(); break;
			case Scode.S_INSERT:		new InsertStatement(false); break;
			case Scode.S_SYSINSERT:		new InsertStatement(true); break;
			case Scode.S_LINE:			setLine(0); break;
			case Scode.S_DECL:			CTStack.checkStackEmpty(); setLine(Kind.qDCL); break;
			case Scode.S_STMT:			CTStack.checkStackEmpty(); setLine(Kind.qSTM); break;
			case Scode.S_SETSWITCH:		setSwitch(); break;
			case Scode.S_INFO:			Util.WARNING("Unknown info: " + Scode.inString());
			default:
				System.out.println("MONITOR.viisible: CurInstr="+Scode.edInstr(Scode.curinstr) + " TERMINATES VIISIBLE");
				return false;
		}
		return true;
	}

	/**
	 * 	program_element
	 * 		::= instruction
	 * 		::= label_declaration
	 * 		::= routine_profile | routine_definition
	 * 		::= skip_statement | if_statement
	 * 		::= protect_statement
	 * 		::= goto_statement | insert_statement
	 * 		::= delete_statement
	 */
	public static void programElements() {
		LOOP: while(true) {
//			Scode.inputInstr();
			System.out.println("MONITOR.programElements: CurInstr="+Scode.edInstr(Scode.curinstr));
			switch(Scode.curinstr) {
				case Scode.S_LABELSPEC:		LabelDescr.ofLabelSpec(); break;
				case Scode.S_LABEL:			LabelDescr.ofLabel(Tag.inTag()); break;
				case Scode.S_PROFILE:		ProfileDescr.inProfile(Kind.P_ROUTINE); break;
				case Scode.S_ROUTINE:		RoutineDescr.ofRoutineDef(); break;
				case Scode.S_IF:			new IfStatement(); break;
				case Scode.S_SKIPIF:		new SkipifStatement(); break;
				case Scode.S_SAVE:			new ProtectStatement(); break;
				case Scode.S_INSERT:		new InsertStatement(false); break;
				case Scode.S_SYSINSERT:		new InsertStatement(true); break;
				default: if(Instruction.inInstruction() == null) break LOOP;
			}
			Scode.inputInstr();
		}
	}

	public static void setSwitch() {
		Util.IERR("NOT IMPL");
	}

	public static void setLine(int type) {
		Global.curline = Scode.inNumber();
		Global.PSEG.emit(new SVM_LINE(type, Global.curline), "MONITOR.setLine: ");
	}

}
