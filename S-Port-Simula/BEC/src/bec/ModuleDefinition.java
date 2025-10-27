/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec;

import java.util.Vector;

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
import bec.segment.Segment;
import bec.statement.InsertStatement;
import bec.util.Array;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

/// This is an implementation of S-Code Module definition.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/ModuleDefinition.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class ModuleDefinition extends S_Module {
	
	/// 	module_definition ::= module module_id:string check_code:string
	/// 							visible_existing
	/// 							body <local_quantity>* <program_element>* endmodule
	/// 
	/// 		visible_existing ::= <visible>* tag_list | existing
	/// 
	/// 			visible
	/// 				::= record_descriptor | routine_profile
	/// 				::= routine_specification | label_specification
	/// 				::= constant_specification | insert_statement
	/// 				::= info_setting
	/// 
	/// 			tag_list ::= < tag internal:tag external:number >+
	/// 
	/// 			local_quantity
	/// 				::= local var:newtag quantity_descriptor
	/// 				::= constant_definition                                       // Extension to S-Code
	/// 
	///				constant_definition                                               // Extension to S-Code
	///					::= const const:spectag quantity_descriptor repetition_value  // Extension to S-Code
	///
	public ModuleDefinition() {
		Global.currentModule = this;
		Global.modident = Scode.inString();
		Global.modcheck = Scode.inString();
		Global.moduleID = Global.modident.toUpperCase();
		String sourceID = Global.getSourceID();
		Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
		Global.TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
		Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		if(Global.PROGID == null) Global.PROGID = Global.modident;
		Global.routineSegments = new Vector<Segment>();

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
			Scode.inputInstr();
			if(xtag > nXtag) nXtag = xtag;
		}

		if(Scode.curinstr != Scode.S_BODY) Util.IERR("Illegal termination of module head");
		Scode.inputInstr();

		while(Scode.curinstr == Scode.S_INIT)
			Util.IERR("InterfaceModule: Init values is not supported");

		LOOP: while(true) {
			switch(Scode.curinstr) {
				case Scode.S_CONST: ConstDescr.ofConstDef(); break;
				case Scode.S_LOCAL: Variable.ofGlobal(Global.DSEG); break;
				default: break LOOP;
			}
			Scode.inputInstr();
		}
		
		programElements();
		if(Scode.curinstr != Scode.S_ENDMODULE) Util.IERR("Improper termination of module: "+Scode.edInstr(Scode.curinstr));
		outputModule(nXtag);
	}


	/// 	visible
	/// 		::= record_descriptor | routine_profile
	/// 		::= routine_specification | label_specification
	/// 		::= constant_specification | insert_statement
	/// 		::= info_setting
	///
	private static boolean viisible() {
		switch(Scode.curinstr) {
			case Scode.S_CONSTSPEC:		ConstDescr.ofConstSpec(); break;
			case Scode.S_CONST:			ConstDescr.ofConstDef(); break;
			case Scode.S_LABELSPEC:		LabelDescr.ofLabelSpec(); break;
			case Scode.S_RECORD:		RecordDescr.ofScode(); break;
			case Scode.S_PROFILE:		ProfileDescr.ofScode(); break;
			case Scode.S_ROUTINESPEC:	RoutineDescr.ofRoutineSpec(); break;
			case Scode.S_INSERT:		new InsertStatement(false); break;
			case Scode.S_SYSINSERT:		new InsertStatement(true); break;
			case Scode.S_LINE:			setLine(0); break;
			case Scode.S_DECL:			CTStack.checkStackEmpty(); setLine(Kind.qDCL); break;
			case Scode.S_STMT:			CTStack.checkStackEmpty(); setLine(Kind.qSTM); break;
			case Scode.S_SETSWITCH:		setSwitch(); break;
			case Scode.S_INFO:			Util.WARNING("Unknown info: " + Scode.inString());
			default:
				return false;
		}
		return true;
	}

}
