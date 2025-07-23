package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;

public class SVM_CALL_TOS extends SVM_Instruction {
//	private ProgramAddress rutAddr;
	private String prfIdent;
	private ObjectAddress returSlot;
	private int nParSlots;
	private int exportSize;
	private int importSize;

	public SVM_CALL_TOS(String prfIdent, ObjectAddress returSlot, int nParSlots,	int exportSize,	int importSize) {
		this.opcode = SVM_Instruction.iCALL_TOS;
//		this.rutAddr = rutAddr;
		this.prfIdent = prfIdent;
		this.returSlot = returSlot;
		this.nParSlots = nParSlots;
		this.exportSize = exportSize;
		this.importSize = importSize;
//		System.out.println("NEW SVM_CALL: "+this);
	}
	
	@Override	
	public void execute() {
		ProgramAddress retur = BecGlobal.PSC.copy();
//		retur.ofst++;
		retur.addOfst(1);
		if(BecGlobal.EXEC_TRACE > 0) {
			ProgramAddress.printInstr(this,false);
		}
//		System.out.println("SVM_CALL:execute: " + this);
		// CALL-TOS
		
		boolean TESTING = true;
		
		if(TESTING) {
//			String rutIdent = "CALL-TOS";
//			int nParSlots = 1;
//			int exportSize = 0;
//			int importSize = 0;
			RTStack.precallStack.push(new CallStackFrame(prfIdent, RTStack.size() - nParSlots, exportSize, importSize));
			if(exportSize > 0) {
				if(nParSlots > 0) {
//					RTStack.dumpRTStack("SVM_PRECALL.execute-1");
					RTStack.addExport(nParSlots, exportSize);
//					RTStack.dumpRTStack("SVM_PRECALL.execute-2");
//					Util.IERR("");
				} else {
					for(int i=0;i<exportSize;i++) {
						RTStack.push(null, "EXPORT"); // Export slots		
					}
				}
			}
		}
		
		BecGlobal.PSC = (ProgramAddress) RTStack.pop().copy();
		System.out.println("SVM_CALL.execute: PSC="+BecGlobal.PSC);
		BecGlobal.PSC.segment().dump("SVM_CALL.execute: ", 0, 10);
		RTStack.push(retur, "RETUR");

//		if(rutAddr != null && rutAddr.toString().equals("PSEG_FIL_OUTTXT:BODY[0]")) {
//			int idx = RTStack.size() - 2;
//			System.out.println("SVM_CALL.execute: idx="+idx);
//			RTStack.dumpRTStack("");
//			RTStack.guard(idx);	
////			Util.IERR("");
//		}
	}
	
	@Override	
	public String toString() {
		String tail = ", Return=" + returSlot;
		return "CALL_TOS " + prfIdent + "nParSlots=" + nParSlots +", exportSize=" + exportSize + "importSize=" + importSize + tail;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_CALL_TOS(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iCALL_TOS;
		prfIdent = inpt.readString();
		this.returSlot = (ObjectAddress) Value.read(inpt);
		nParSlots = inpt.readShort();
		exportSize = inpt.readShort();
		importSize = inpt.readShort();
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

//	private String prfIdent;
//	private ObjectAddress returSlot;
//	private int nParSlots;
//	private int exportSize;
//	private int importSize;

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeString(prfIdent);
		returSlot.write(oupt);
		oupt.writeShort(nParSlots);
		oupt.writeShort(exportSize);
		oupt.writeShort(importSize);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_CALL_TOS(inpt);
	}


}
