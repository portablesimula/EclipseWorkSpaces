package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CALL;
import bec.util.Util;

@SuppressWarnings("unused")
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
//	public final static int iPUSHR = 25;
	public final static int iPRECALL = 26;
	public final static int iPOPK = 27;
	public final static int iENTER = 28;
	public final static int iPOPGADR = 29;
	public final static int iADDREG = 30;
	public final static int iXOR = 31;
	public final static int iINITO = 32;
	public final static int iGETO = 33;
	public final static int iSETO = 34;
	public final static int iINCO = 35;
	public final static int iDECO = 36;
	public final static int iDIST = 37;
	public final static int iEQV = 38;
	public final static int iIMP = 39;
	public final static int iDUP = 40;
	public final static int iASSIGN = 41;
	public final static int iPUSHLEN = 42;
	public final static int iSAVE = 43;
	public final static int iRESTORE = 44;
	public final static int iSHIFT = 45;
	public final static int iCALL_TOS = 46;
	public final static int iPUSHA = 47;
	public final static int iEVAL = 48;
	
	public final static int iLOAD = 49;
	public final static int iLOADC = 50;
	public final static int iPUSHR = 51;
	public final static int iMULREG = 52;

	public final static int iSJEKK_DETTE = 80;
	public final static int iREGUSE = 81;

	public final static int iMax = 99;
	

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
		int opcode = inpt.readOpcode();
//		IO.println("SVM_Instruction.read: opcode="+edOpcode(opcode));
		switch(opcode) {
			case iADD:		return SVM_ADD.read(inpt);
			case iAND:		return SVM_AND.read(inpt);
			case iOR:		return SVM_OR.read(inpt);
			case iXOR:		return SVM_XOR.read(inpt);
			case iEQV:		return SVM_EQV.read(inpt);
			case iIMP:		return SVM_IMP.read(inpt);
			case iCALL:		return SVM_CALL.read(inpt);
			case iCALL_TOS:	return SVM_CALL_TOS.read(inpt);
			case iCOMPARE:	return SVM_COMPARE.read(inpt);
			case iCONVERT:	return SVM_CONVERT.read(inpt);
			case iDIV:		return SVM_DIV.read(inpt);
			case iJUMP:		return SVM_JUMP.read(inpt);
			case iJUMPIF:	return SVM_JUMPIF.read(inpt);
			case iMULT:		return SVM_MULT.read(inpt);
			case iNEG:		return SVM_NEG.read(inpt);
			case iPUSH:		return SVM_PUSH.read(inpt);
//			case iLOADA:	return QVM_LOADA.read(inpt);
			case iPUSHC:	return SVM_PUSHC.read(inpt);
//			case iPUSHR:	return QVM_PUSHR.read(inpt);
			case iPUSHLEN:	return SVM_PUSHLEN.read(inpt);
			case iSAVE:		return SVM_SAVE.read(inpt);
			case iRESTORE:	return SVM_RESTORE.read(inpt);
			case iRETURN:	return SVM_RETURN.read(inpt);
			case iLOAD:		return SVM_LOAD.read(inpt);
			case iLOADC:	return SVM_LOADC.read(inpt);
			case iPUSHR:	return SVM_PUSHR.read(inpt);
			case iPOP2REG:	return SVM_POP2REG.read(inpt);
			case iPOP2MEM:	return SVM_POP2MEM.read(inpt);
			case iREM:		return SVM_REM.read(inpt);
			case iSUB:		return SVM_SUB.read(inpt);
			case iSWITCH:	return SVM_SWITCH.read(inpt);
			case iLINE:		return SVM_LINE.read(inpt);
			case iCALLSYS:	return SVM_CALL_SYS.read(inpt);
			case iNOOP:		return SVM_NOOP.read(inpt);
			case iNOT:		return SVM_NOT.read(inpt);
			case iGOTO:		return SVM_GOTO.read(inpt);
			case iPRECALL:	return SVM_PRECALL.read(inpt);
			case iPOPK:		return SVM_POPK.read(inpt);
			case iENTER:	return SVM_ENTER.read(inpt);
			case iPOPGADR:	return SVM_POPGADR.read(inpt);
			case iADDREG:	return SVM_ADDREG.read(inpt);
			case iMULREG:	return SVM_MULREG.read(inpt);
			case iINITO:	return SVM_INITO.read(inpt);
			case iGETO:		return SVM_GETO.read(inpt);
			case iSETO:		return SVM_SETO.read(inpt);
			case iINCO:		return SVM_INCO.read(inpt);
			case iDECO:		return SVM_DECO.read(inpt);
			case iDIST:		return SVM_DIST.read(inpt);
			case iDUP:		return SVM_DUP.read(inpt);
			case iASSIGN:	return SVM_ASSIGN.read(inpt);
			case iSHIFT:	return SVM_SHIFT.read(inpt);
			case iPUSHA:	return SVM_PUSHA.read(inpt);
			case iEVAL:		return SVM_EVAL.read(inpt);

			case iSJEKK_DETTE: return SVM_SJEKK_DETTE.read(inpt);
			case iREGUSE:      return SVM_CLEAR_REGUSE.read(inpt);

			default: Util.IERR("MISSING: " + edOpcode(opcode));
		}
		return null;
	}
	
	public static String edOpcode(int opcode) {
		switch(opcode) {
			case iADD:		return "iADD";
			case iAND:		return "iAND";
			case iOR:		return "iOR";
			case iXOR:		return "iXOR";
			case iEQV:		return "iEQV";
			case iIMP:		return "iIMP";
			case iCALL:		return "iCALL";
			case iCALL_TOS:	return "iCALL_TOS";
			case iCOMPARE:	return "iCOMPARE";
			case iCONVERT:	return "iCONVERT";
			case iDIV:		return "iDIV";
			case iJUMP:		return "iJUMP";
			case iJUMPIF:	return "iJUMPIF";
			case iMULT:		return "iMULT";
			case iNEG:		return "iNEG";
			case iPUSH:		return "iPUSH";
//			case iLOADA:	return "iLOADA";
			case iPUSHC:	return "iPUSHC";
//			case iPUSHR:	return "iPUSHR";
			case iPUSHLEN:	return "iPUSHLEN";
			case iSAVE:		return "iSAVE";
			case iRESTORE:	return "iRESTORE";
			case iRETURN:	return "iRETURN";
			case iLOAD:		return "iLOAD";
			case iLOADC:	return "iLOADC";
			case iPUSHR:	return "iPUSHR";
			case iPOP2REG:	return "iPOP2REG";
			case iPOP2MEM:	return "iPOP2MEM";
			case iREM:	 	return "iSTOREC";
			case iSUB:		return "iSUB";
			case iSWITCH:	return "iSWITCH";
			case iCALLSYS:	return "iCALLSYS";
			case iLINE:		return "iNOOP";
			case iNOOP:		return "iNOOP";
			case iNOT:		return "iNOT";
			case iGOTO:		return "iGOTO";
			case iPRECALL:	return "iPRECALL";
			case iPOPK:		return "iPOPK";
			case iENTER:	return "iENTER";
			case iPOPGADR:	return "iPOPGADR";
			case iADDREG:	return "iADDREG";
			case iMULREG:	return "iMULREG";
			case iINITO:	return "iINITO";
			case iGETO:		return "iGETO";
			case iSETO:		return "iSETO";
			case iINCO:		return "iINCO";
			case iDECO:		return "iDECO";
			case iDIST:		return "iDIST";
			case iDUP:		return "iDUP";
			case iASSIGN:	return "iASSIGN";
			case iSHIFT:	return "iSHIFT";
			case iPUSHA:	return "iPUSHA";
			case iEVAL:		return "iEVAL";

			case iSJEKK_DETTE: return "iSJEKK_DETTE";
			case iREGUSE:      return "iREGUSE";

			default:		return "UNKNOWN:" + opcode;
		}
	}

}
