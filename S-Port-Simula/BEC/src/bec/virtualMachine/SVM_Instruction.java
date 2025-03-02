package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CALL;
import bec.util.Util;

public class SVM_Instruction {
	public int opcode;

	public final static int iADD = 1;
	public final static int iAND = 2;
	public final static int iOR = 3;
	public final static int iCALL = 4;
	public final static int iCOMPARE = 5;
	public final static int iCONVERT = 6;
	public final static int iDIV = 7;
	public final static int iJUMP = 8;
	public final static int iJUMPIF = 9;
	public final static int iMULT = 10;
	public final static int iNEG = 11;
	public final static int iPUSH = 12;
	public final static int iPUSHC = 13;
	public final static int iRETURN = 14;
	public final static int iPOP2REG = 15;
	public final static int iPOP2MEM = 16;
	public final static int iREM = 17;
	public final static int iSUB = 18;
	public final static int iSWITCH = 19;
	public final static int iCALLSYS = 20;
	public final static int iLINE = 21;
	public final static int iNOOP = 22;
	public final static int iNOT = 23;
	public final static int iGOTO = 24;
	public final static int iPUSHR = 25;
	public final static int iPRECALL = 26;
	public final static int iPOPK = 27;
	public final static int iENTER = 28;
	
	public final static int iNOT_IMPL = 99;
	

	public void execute() {
		Util.IERR("Method execute need a redefinition in "+this.getClass().getSimpleName());
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' need a redefinition in " + this.getClass().getSimpleName());
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		Util.IERR("Method 'read' need a redefinition"); // in " + this.getClass().getSimpleName());
		return null;
	}
	
	public static SVM_Instruction readObject(AttributeInputStream inpt) throws IOException {
		int opcode = inpt.readKind();
//		System.out.println("SVM_Instruction.read: opcode="+edOpcode(opcode));
		switch(opcode) {
			case iADD:		return SVM_ADD.read(inpt);
			case iAND:		return SVM_AND.read(inpt);
			case iOR:		return SVM_OR.read(inpt);
			case iCALL:
				if(CALL.USE_FRAME_ON_STACK)
					 return SVM_CALL.read(inpt);
				else return SVM_CALL_DSEG.read(inpt);
				
			case iCOMPARE:	return SVM_COMPARE.read(inpt);
			case iCONVERT:	return SVM_CONVERT.read(inpt);
			case iDIV:		return SVM_DIV.read(inpt);
			case iJUMP:		return SVM_JUMP.read(inpt);
			case iJUMPIF:	return SVM_JUMPIF.read(inpt);
			case iMULT:		return SVM_MULT.read(inpt);
			case iNEG:		return SVM_NEG.read(inpt);
			case iPUSH:		return SVM_PUSH.read(inpt);
			case iPUSHC:	return SVM_PUSHC.read(inpt);
			case iRETURN:	return SVM_RETURN.read(inpt);
			case iPOP2REG:	return SVM_POP2REG.read(inpt);
			case iPOP2MEM:	return SVM_POP2MEM.read(inpt);
			case iREM:		return SVM_REM.read(inpt);
			case iSUB:		return SVM_SUB.read(inpt);
			case iSWITCH:	return SVM_SWITCH.read(inpt);
			case iLINE:		return SVM_LINE.read(inpt);
			case iCALLSYS:	return SVM_CALLSYS.read(inpt);
			case iNOOP:		return SVM_NOOP.read(inpt);
			case iNOT:		return SVM_NOT.read(inpt);
			case iGOTO:		return SVM_GOTO.read(inpt);
			case iPUSHR:	return SVM_PUSHR.read(inpt);
			case iPRECALL:	return SVM_PRECALL.read(inpt);
			case iPOPK:		return SVM_POPK.read(inpt);
			case iENTER:	return SVM_ENTER.read(inpt);
			
			case iNOT_IMPL:	return SVM_NOT_IMPL.read(inpt);
			default: Util.IERR("MISSING: " + edOpcode(opcode));
		}
		return null;
	}
	
	public static String edOpcode(int opcode) {
		switch(opcode) {
			case iADD:		return "iADD";
			case iAND:		return "iAND";
			case iOR:		return "iCALL_TOS";
			case iCALL:		return "iCALL";
			case iCOMPARE:	return "iCOMPARE";
			case iCONVERT:	return "iCONVERT";
			case iDIV:		return "iDIV";
			case iJUMP:		return "iJUMP";
			case iJUMPIF:	return "iJUMPIF";
			case iMULT:		return "iMULT";
			case iNEG:		return "iNEG";
			case iPUSH:		return "iPUSH";
			case iPUSHC:	return "iPUSHC";
			case iRETURN:	return "iRETURN";
			case iPOP2REG:	return "iPOPtoREG";
			case iPOP2MEM:	return "iPOPtoMEM";
			case iREM:	 	return "iSTOREC";
			case iSUB:		return "iSUB";
			case iSWITCH:	return "iSWITCH";
			case iCALLSYS:	return "iCALLSYS";
			case iLINE:		return "iNOOP";
			case iNOOP:		return "iNOOP";
			case iNOT:		return "iNOT";
			case iGOTO:		return "iGOTO";
			case iPUSHR:	return "iPUSHR";
			case iPRECALL:	return "iPRECALL";
			case iPOPK:		return "iPOPK";
			case iENTER:	return "iENTER";

			case iNOT_IMPL:	return "iNOT_IMPL";
			default:		return "UNKNOWN:" + opcode;
		}
	}

}
