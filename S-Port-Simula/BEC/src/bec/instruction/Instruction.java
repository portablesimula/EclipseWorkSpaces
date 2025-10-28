/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.instruction;

import bec.descriptor.ConstDescr;
import bec.descriptor.RecordDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.SwitchDescr;
import bec.statement.IfConstrction;
import bec.statement.ProtectConstruction;
import bec.statement.SkipifConstruction;
import bec.util.Scode;
import bec.util.Util;

/// S-INSTRUCTION:
///
/// instruction
/// 		::= constant_declaration
/// 		::= record_descriptor | routine_specification
/// 		::= stack_instruction | assign_instruction
/// 		::= addressing_instruction | protect_instruction
/// 		::= temp_control | access_instruction
/// 		::= arithmetic_instruction | convert_instruction
/// 		::= jump_instruction | goto_instruction
/// 		::= if_instruction | skip_instruction
/// 		::= segment_instruction | call_instruction
/// 		::= area_initialisation | eval_instruction
/// 		::= info_setting | macro_call
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/instruction/Instruction.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class Instruction {
		
	public static void inInstructions() {
		LOOP:while(true) {
			if(! inInstruction()) break LOOP;
			Scode.inputInstr();
		}
	}

	public static boolean inInstruction() {
		switch(Scode.curinstr) {
			case Scode.S_CONSTSPEC ->   ConstDescr.ofConstSpec();
			case Scode.S_CONST ->	    ConstDescr.ofConstDef();
			case Scode.S_ROUTINESPEC -> RoutineDescr.ofRoutineSpec();
			case Scode.S_RECORD ->      RecordDescr.ofScode();
			case Scode.S_SETOBJ ->      Util.IERR("SETOBJ is not implemented");
			case Scode.S_GETOBJ ->      Util.IERR("GETOBJ is not implemented");
			case Scode.S_ACCESS, Scode.S_ACCESSV -> Util.IERR("ACCESS is not implemented");
			case Scode.S_PUSH ->        PUSH.ofScode(Scode.S_PUSH);
			case Scode.S_PUSHV ->       PUSH.ofScode(Scode.S_PUSHV);
			case Scode.S_PUSHC ->       PUSHC.ofScode();
			case Scode.S_INDEX, Scode.S_INDEXV -> INDEX.ofScode(Scode.curinstr);
			case Scode.S_FETCH ->       FETCH.ofScode();
			case Scode.S_SELECT ->      SELECT.ofScode(Scode.S_SELECT);
			case Scode.S_SELECTV ->     SELECT.ofScode(Scode.S_SELECTV);
			case Scode.S_REMOTE ->      REMOTE.ofScode(Scode.S_REMOTE);
			case Scode.S_REMOTEV ->     REMOTE.ofScode(Scode.S_REMOTEV);
			case Scode.S_REFER ->       REFER.ofScode();
			case Scode.S_DSIZE ->       DSIZE.ofScode();
			case Scode.S_DUP ->         DUP.ofScode();
			case Scode.S_POP ->         POP.ofScode();
			case Scode.S_POPALL ->      POPALL.ofScode();
			case Scode.S_ASSIGN ->      ASSIGN.ofScode();
			case Scode.S_UPDATE ->      UPDATE.ofScode();
			case Scode.S_RUPDATE ->     RUPDATE.ofScode();
			case Scode.S_BSEG ->        BSEG.ofScode();
			case Scode.S_IF ->          IfConstrction.ofScode();
			case Scode.S_SKIPIF ->      SkipifConstruction.ofScode();
			case Scode.S_PRECALL ->     CALL.ofScode(0);
			case Scode.S_ASSCALL ->     CALL.ofScode(1);
			case Scode.S_REPCALL ->     CALL.ofScode(Scode.inByte());
			case Scode.S_GOTO ->        GOTO.ofScode();
			case Scode.S_PUSHLEN ->     PUSHLEN.ofScode();
			case Scode.S_SAVE ->        ProtectConstruction.ofInstruction();
			case Scode.S_T_INITO ->     INITO.ofScode();
			case Scode.S_T_GETO ->      GETO.ofScode();
			case Scode.S_T_SETO ->      SETO.ofScode();
			case Scode.S_DECL ->        LINE.ofScode(1);
			case Scode.S_STMT ->        LINE.ofScode(2);
			case Scode.S_LINE ->        LINE.ofScode(0);
			case Scode.S_EMPTY ->       EMPTY.ofScode();
			case Scode.S_SETSWITCH ->   Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr));
			case Scode.S_INFO ->        Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr));
			case Scode.S_DELETE ->      DELETE.ofScode();
			case Scode.S_ZEROAREA ->    ZEROAREA.ofScode();
			case Scode.S_INITAREA ->    INITAREA.ofScode();
			case Scode.S_DINITAREA ->   DINITAREA.ofScode();
			case Scode.S_EVAL ->        EVAL.ofScode();
			case Scode.S_FJUMPIF ->     FJUMPIF.ofScode();
			case Scode.S_FJUMP ->       FJUMP.ofScode();
			case Scode.S_FDEST ->       FDEST.ofScode();
			case Scode.S_BDEST ->       BDEST.ofScode();
			case Scode.S_BJUMP ->       BJUMP.ofScode();
			case Scode.S_BJUMPIF ->     BJUMPIF.ofScode();
			case Scode.S_SWITCH ->      SwitchDescr.ofScode();
			case Scode.S_SDEST ->       SDEST.ofScode();
			case Scode.S_CONVERT ->     CONVERT.ofScode();
			case Scode.S_NEG ->	        NEG.ofScode();
			case Scode.S_ADD ->         ADD.ofScode();
			case Scode.S_SUB ->         SUB.ofScode();
			case Scode.S_MULT ->        MULT.ofScode();
			case Scode.S_DIV ->         DIV.ofScode();
			case Scode.S_REM ->         REM.ofScode();
			case Scode.S_NOT ->         NOT.ofScode();
			case Scode.S_AND ->         AND.ofScode();
			case Scode.S_OR ->          OR.ofScode();
			case Scode.S_XOR ->         XOR.ofScode();
			case Scode.S_EQV ->         EQV.ofScode();
			case Scode.S_IMP ->         IMP.ofScode();
			case Scode.S_LSHIFTL ->     SHIFT.ofScode(Scode.S_LSHIFTL); // Extension to S-Code: Left shift logical
			case Scode.S_LSHIFTA ->     SHIFT.ofScode(Scode.S_LSHIFTA); // Extension to S-Code: Left shift arithm.
			case Scode.S_RSHIFTL ->     SHIFT.ofScode(Scode.S_RSHIFTL); // Extension to S-Code: Right shift logical
			case Scode.S_RSHIFTA ->     SHIFT.ofScode(Scode.S_RSHIFTA); // Extension to S-Code: Right shift arithm.
			case Scode.S_LOCATE ->      LOCATE.ofScode();
			case Scode.S_INCO ->        INCO.ofScode();
			case Scode.S_DECO ->        DECO.ofScode();
			case Scode.S_DIST ->        DIST.ofScode();
			case Scode.S_COMPARE ->     COMPARE.ofScode();
			case Scode.S_DEREF ->       DEREF.ofScode();
			default -> { return false; }
		}
		return true;
	}
	

}
