package PREV.syntaxClass.instruction;

import java.util.Vector;

import PREV.syntaxClass.programElement.ProgramElement;
import PREV.syntaxClass.programElement.routine.PREV_ROUTINE;
import PREV.syntaxClass.programElement.routine.PREV_ROUTINESPEC;
import PREV.syntaxClass.value.PREV_CONST;
import bec.segment.ProgramSegment;
import bec.util.Scode;
import bec.util.Util;

public class PREV_Instruction extends ProgramElement {
	
//	%title ***   I n s t r u c t i o n   ***
	public static Vector<PREV_Instruction> inInstructionSet(){
		Vector<PREV_Instruction> instructionSet = new Vector<PREV_Instruction>();
		LOOP:while(true) {
			PREV_Instruction instr = inInstruction();
			if(instr == null) break LOOP;
			instructionSet.add(instr);
			Scode.inputInstr();
		}
		
//		System.out.println("\nNEW InstructionSet");
//		for(ProgramElement instr:instructionSet)
//			System.out.println("   " + instr);
//		System.out.println("END InstructionSet");

		return instructionSet;
	}

	public static PREV_Instruction inInstruction() {
//		System.out.println("Parse.instruction: "+Scode.edInstr(Scode.curinstr));
		switch(Scode.curinstr) {
			case Scode.S_CONSTSPEC:   return PREV_CONST.of(false);
			case Scode.S_CONST:       return PREV_CONST.of(true);
			case Scode.S_RECORD:      return new RECORD();
			case Scode.S_ROUTINESPEC: return PREV_ROUTINE.ofSpec(false); // SpecRut(false)
			case Scode.S_SETOBJ:      Util.IERR("SSTMT.SETOBJ is not implemented");
			case Scode.S_GETOBJ:      Util.IERR("SSTMT.GETOBJ is not implemented");
			case Scode.S_ACCESS, Scode.S_ACCESSV: Util.IERR("SSTMT.ACCESS is not implemented");
			case Scode.S_PUSH:        return new PUSH(Scode.S_PUSH);
			case Scode.S_PUSHV:       return new PUSH(Scode.S_PUSHV);
			case Scode.S_PUSHC:       return new PUSHC();
			case Scode.S_INDEX, Scode.S_INDEXV: return new INDEX(Scode.curinstr);
			case Scode.S_FETCH:       return new FETCH();
			case Scode.S_SELECT:      return new SELECT(Scode.S_SELECT);
			case Scode.S_SELECTV:     return new SELECT(Scode.S_SELECTV);
			case Scode.S_REMOTE:      return new REMOTE(Scode.S_REMOTE);
			case Scode.S_REMOTEV:     return new REMOTE(Scode.S_REMOTEV);
			case Scode.S_REFER:       return new REFER();
			case Scode.S_DSIZE:       return new DSIZE();
			case Scode.S_DUP:         return new DUP();
			case Scode.S_POP:         return new POP();
			case Scode.S_POPALL:      return new POPALL();
			case Scode.S_ASSIGN:      return new ASSIGN();
			case Scode.S_UPDATE:      return new UPDATE();
			case Scode.S_RUPDATE:     return new RUPDATE();
			case Scode.S_BSEG:        return new BSEG();
			case Scode.S_IF:          return new IF();
			case Scode.S_SKIPIF:      return new SKIPIF();
			case Scode.S_PRECALL:     return new CallInstruction(0);
			case Scode.S_ASSCALL:     return new CallInstruction(1);
			case Scode.S_REPCALL:     int b = Scode.inByte(); return new CallInstruction(b);
			case Scode.S_GOTO:        return new GOTO();
			case Scode.S_PUSHLEN:     return new PUSHLEN();
			case Scode.S_SAVE:        return new SAVE(); //   ProtectConstruction(false)
			case Scode.S_T_INITO:     return new INITO();
			case Scode.S_T_GETO:      return new GETO();
			case Scode.S_T_SETO:      return new SETO();
			case Scode.S_DECL:        return new LINE(1);
			case Scode.S_STMT:        return new LINE(2);
			case Scode.S_LINE:        return new LINE(0);
			case Scode.S_EMPTY:       return new EMPTY();
			case Scode.S_SETSWITCH:   Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr)); //  SetSwitch
			case Scode.S_INFO:        Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr)); //       Ed(errmsg,InString); WARNING("Unknown info: ");
			case Scode.S_DELETE:      return new DELETE();
			case Scode.S_ZEROAREA:    return new ZEROAREA();
			case Scode.S_INITAREA:    Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr)); //    intype;
			case Scode.S_EVAL:        return new EVAL();
			case Scode.S_FJUMPIF:     return new FJUMPIF();
			case Scode.S_FJUMP:       return new FJUMP();
			case Scode.S_FDEST:       return new FDEST();
			case Scode.S_BDEST:       return new BDEST();
			case Scode.S_BJUMP:       return new BJUMP();
			case Scode.S_BJUMPIF:     return new BJUMPIF();
			case Scode.S_SWITCH:      return new SWITCH();
			case Scode.S_SDEST:       return new SDEST();
			case Scode.S_CONVERT:     return new CONVERT();
			case Scode.S_NEG:	      return new NEG();
			case Scode.S_ADD:         return new ADD();
			case Scode.S_SUB:         return new SUB();
			case Scode.S_MULT:        return new MULT();
			case Scode.S_DIV:         return new DIV();
			case Scode.S_REM:         return new REM();
			case Scode.S_NOT:         return new NOT();
			case Scode.S_AND:         return new AND();
			case Scode.S_OR:          return new OR();
			case Scode.S_XOR:         Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr));
			case Scode.S_EQV:         Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr));
			case Scode.S_IMP:         return new IMP();
			case Scode.S_LSHIFTL:     return new BITWISE_SHIFT(Scode.S_LSHIFTL); // Extension to S-Code: Left shift logical
			case Scode.S_LSHIFTA:     return new BITWISE_SHIFT(Scode.S_LSHIFTA); // Extension to S-Code: Left shift arithm.
			case Scode.S_RSHIFTL:     return new BITWISE_SHIFT(Scode.S_RSHIFTL); // Extension to S-Code: Right shift logical
			case Scode.S_RSHIFTA:     return new BITWISE_SHIFT(Scode.S_RSHIFTA); // Extension to S-Code: Right shift arithm.
			case Scode.S_LOCATE:      return new LOCATE();
			case Scode.S_INCO:        return new INCO();
			case Scode.S_DECO:        return new DECO();
			case Scode.S_DIST:        return new DIST();
			case Scode.S_COMPARE:     return new COMPARE();
			case Scode.S_DEREF:       return new DEREF();
		}
		return null;
	}
	

}
