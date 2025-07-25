package bec;

import java.io.IOException;
import java.util.Vector;

import bec.compileTimeStack.CTStack;
import bec.descriptor.ConstDescr;
import bec.descriptor.Kind;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RecordDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.Variable;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.Array;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Util;

public class InterfaceModule extends S_Module {
	
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
	public InterfaceModule() {
		BecGlobal.currentModule = this;
		Scode.inputInstr();
		if(Scode.curinstr != Scode.S_MODULE) Util.IERR("Missing - MODULE");
		BecGlobal.modident = Scode.inString();
		BecGlobal.modcheck = Scode.inString();
		BecGlobal.moduleID = BecGlobal.modident.toUpperCase();
		String sourceID = BecGlobal.getSourceID();
		BecGlobal.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		BecGlobal.TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
		BecGlobal.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		BecGlobal.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		if(BecGlobal.PROGID == null) BecGlobal.PROGID = BecGlobal.modident;
		BecGlobal.routineSegments = new Vector<Segment>();
		LOOP: while(true) {
			Scode.inputInstr();
//			System.out.println("InterfaceModule'LOOP: Curinstr="+Scode.edInstr(Scode.curinstr));
			switch(Scode.curinstr) {
				case Scode.S_GLOBAL:	Variable.ofGlobal(BecGlobal.DSEG); break;
				case Scode.S_CONSTSPEC: ConstDescr.ofConstSpec(); break;
				case Scode.S_CONST:		ConstDescr.ofConstDef(); break;
				case Scode.S_RECORD:	RecordDescr.ofScode(); break;
				case Scode.S_PROFILE:   ProfileDescr.ofScode(); break;
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
		BecGlobal.iTAGTAB = new Array<Integer>();
		BecGlobal.xTAGTAB = new Array<Integer>();
		int nXtag = 0;
		while(Scode.curinstr == Scode.S_TAG) {
			int itag = Scode.ofScode();
			int xtag = Scode.inNumber();
			BecGlobal.iTAGTAB.set(xtag, itag); // Index xTag --> value iTag
			BecGlobal.xTAGTAB.set(itag, xtag); // Index iTag --> value xTag
			Scode.inputInstr();
			if(xtag > nXtag) nXtag = xtag;
		}
			
			
		try {
			ModuleIO.outputModule(nXtag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Util.IERR("");
		}

//		Tag.dumpITAGTABLE("MONITOR.interfaceModule'END: ");
//		Tag.dumpXTAGTABLE("MONITOR.interfaceModule'END: ");
//		DataType.dumpDataTypes("MONITOR.interfaceModule'END: ");
//		Global.DSEG.dump("MONITOR.interfaceModule'END: ");
//		Global.CSEG.dump("MONITOR.interfaceModule'END: ");
//		Global.dumpDISPL("MONITOR.interfaceModule'END: ");
//		Scode.dumpTAGIDENTS("MONITOR.interfaceModule'END: ");
//		Type.dumpTypes("MONITOR.interfaceModule'END: ");
		if(Scode.curinstr != Scode.S_BODY) Util.IERR("Illegal termination of module head");
		Scode.inputInstr();
//	%+SC    repeat InputInstr while CurInstr=S_INIT
//	%+SC    do IERR("InterfaceModule: Init values is not supported");
//	%+SC       InTag(%wrd%); intype; SkipRepValue;
//	%+SC    endrepeat;

		if(Scode.curinstr != Scode.S_ENDMODULE) Util.IERR("Improper termination of module");
	}
	

}
