package bec;

import java.io.IOException;

import bec.compileTimeStack.CTStack;
import bec.descriptor.ConstDescr;
import bec.descriptor.Kind;
import bec.descriptor.LabelDescr;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RecordDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.Variable;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.statement.InsertStatement;
import bec.util.Array;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;

public class ModuleDefinition extends S_Module {
	
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
	public ModuleDefinition() {
		Global.currentModule = this;
		Global.modident = Scode.inString();
		Global.modcheck = Scode.inString();
		Global.moduleID = Global.modident.toUpperCase();
		String sourceID = Global.getSourceID();
		Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		if(Global.PROGID == null) Global.PROGID = Global.modident;

		Scode.inputInstr();
		while(viisible()) { Scode.inputInstr(); }

		// tag_list ::= < tag internal:tag external:number >+
		Global.iTAGTAB = new Array<Integer>();
		Global.xTAGTAB = new Array<Integer>();
		int nXtag = 0;
		while(Scode.curinstr == Scode.S_TAG) {
			int itag = Scode.ofScode();
			int xtag = Scode.inNumber();
			Global.iTAGTAB.set(xtag, itag); // Index xTag --> value iTag
			Global.xTAGTAB.set(itag, xtag); // Index iTag --> value xTag
//			System.out.println("MONITOR: xtag="+xtag+"  itag="+itag);
			Scode.inputInstr();
			if(xtag > nXtag) nXtag = xtag;
		}

		if(Scode.curinstr != Scode.S_BODY) Util.IERR("Illegal termination of module head");
		Scode.inputInstr();

		while(Scode.curinstr == Scode.S_INIT) {
			//%+SC       InTag(%wrd%); intype; SkipRepValue;
			Util.IERR("InterfaceModule: Init values is not supported");
		}

		System.out.println("ModuleDefinition: curinstr="+Scode.edInstr(Scode.curinstr));
		LOOP: while(true) {
			switch(Scode.curinstr) {
				case Scode.S_CONST: ConstDescr.ofConstDef(); break;
				case Scode.S_LOCAL:
					Variable.ofGlobal(Global.DSEG);
					Global.DSEG.dump("ModuleDefinition: ");
//					Util.IERR("SJEKK DETTE");
					break;
				default:
//					System.out.println("ModuleDefinition'LOOP: Terminated by: " + Scode.edInstr(Scode.curinstr));
					break LOOP;
			}
			Scode.inputInstr();
//			System.out.println("ModuleDefinition: +++++++++++++ REPEAT: "+Scode.edInstr(Scode.curinstr));
		}

		programElements();

		if(Scode.curinstr != Scode.S_ENDMODULE) Util.IERR("Improper termination of module: "+Scode.edInstr(Scode.curinstr));

//		Tag.dumpITAGTABLE("MONITOR.moduleDefinition'END: ");
//		Tag.dumpXTAGTABLE("MONITOR.moduleDefinition'END: ");
//		DataType.dumpDataTypes("MONITOR.moduleDefinition'END: ");
//		Global.DSEG.dump("MONITOR.moduleDefinition'END: ");
//		Global.CSEG.dump("MONITOR.moduleDefinition'END: ");
//		Global.dumpDISPL("MONITOR.moduleDefinition'END: ");
//		Scode.dumpTAGIDENTS("MONITOR.moduleDefinition'END: ");
		
		try { ModuleIO.outputModule(nXtag);
		} catch (IOException e) { e.printStackTrace(); Util.IERR(""); }
	}


	/**
	 * 	visible
	 * 		::= record_descriptor | routine_profile
	 * 		::= routine_specification | label_specification
	 * 		::= constant_specification | insert_statement
	 * 		::= info_setting
	 */
	private static boolean viisible() {
//		System.out.println("MONITOR.viisible: CurInstr="+Scode.edInstr(Scode.curinstr));
		switch(Scode.curinstr) {
			case Scode.S_CONSTSPEC:		ConstDescr.ofConstSpec(); break;
			case Scode.S_CONST:			ConstDescr.ofConstDef(); break;
			case Scode.S_LABELSPEC:		LabelDescr.ofLabelSpec(); break;
			case Scode.S_RECORD:		RecordDescr.of(); break;
			case Scode.S_PROFILE:		ProfileDescr.ofProfile(); break;
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

}
