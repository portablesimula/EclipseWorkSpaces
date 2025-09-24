package iAPX.enums;

import java.io.IOException;

import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;

public enum Opcode {
   	
	qWAIT, qEVAL, qTSTOFL, qLAHF, qSAHF, qCWD, qFDUP, qIRET, qDOS2, qFLDCK, qFMONAD, qFDYAD, qFPUSH, qFPOP, qPUSHR, qPOPR,             // Format 1
	qRSTRB, qRSTRW, qMONADR, qTRIADR, qCONDEC, qMOV, qXCHG, qDYADR, qSHIFT, qPOPK, qRET, qINT, qADJST, qENTER, qLEAVE, qLINE, qDYADC,  // Format 2
	qPUSHC, qLOADC,                                                                                                                    // Format 2b
	qJMPM, qBOUND, qPUSHA, qLOADA, qSTORE, qXCHGM, qFLD, qFLDC, qFST, qFSTP, qDYADM, qDYADMR, qMONADM, qTRIADM,                        // Format3
	qLOAD, qFDYADM, qPUSHM, qPOPM, qMOVMC, qDYADMC,                                                                                    // Format 4	
	qCALL, qJMP,                                                                                                                       // Format 5 
	qFDEST, qBDEST, qLABEL,                                                                                                            // Format 6
	qMXX;
	
	private static Opcode[] values = Opcode.values();
	private static Opcode of(int ordinale) {
		return values[ordinale];
	}
	
//	public static void write(Kind kind, AttributeOutputStream oupt) throws IOException {
//		oupt.writeShort(kind.ordinal());
//	}
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(this.ordinal());
	}

	public static Opcode read(AttributeInputStream inpt) throws IOException {
		return Opcode.of(inpt.readShort());
	}

}
