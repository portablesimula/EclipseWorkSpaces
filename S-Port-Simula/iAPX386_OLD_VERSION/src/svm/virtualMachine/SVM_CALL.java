package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import svm.value.ProgramAddress;
import svm.value.Value;
import svm.value.dataAddress.FrameAddress;

public class SVM_CALL extends SVM_Instruction {
	private ProgramAddress rutAddr;
	private FrameAddress returSlot;

	public SVM_CALL(ProgramAddress rutAddr, FrameAddress returSlot2) {
		this.opcode = SVM_Instruction.iCALL;
		this.rutAddr = rutAddr;
		this.returSlot = returSlot2;
//		IO.println("NEW SVM_CALL: "+this);
	}
	
	public static SVM_CALL ofTOS(FrameAddress returSlot2) {
		return new SVM_CALL(null, returSlot2);
	}
	
	@Override	
	public void execute() {
		ProgramAddress retur = Global.PSC.copy();
//		retur.ofst++;
		retur.addOfst(1);
		if(Global.EXEC_TRACE > 0) {
			ProgramAddress.printInstr(this,false);
		}
//		IO.println("SVM_CALL:execute: " + this);
		if(rutAddr == null) {
			// CALL-TOS
			
//			boolean TESTING = false;
//			
//			if(TESTING) {
//				String rutIdent = "CALL-TOS";
//				int nParSlots = 1;
//				int exportSize = 0;
//				int importSize = 0;
//				RTStack.precallStack.push(new CallStackFrame(rutIdent, RTStack.size() - nParSlots, exportSize, importSize));
//				if(exportSize > 0) {
//					if(nParSlots > 0) {
////						RTStack.dumpRTStack("SVM_PRECALL.execute-1");
//						RTStack.addExport(nParSlots, exportSize);
////						RTStack.dumpRTStack("SVM_PRECALL.execute-2");
////						Util.IERR("");
//					} else {
//						for(int i=0;i<exportSize;i++) {
//							RTStack.push(null, "EXPORT"); // Export slots		
//						}
//					}
//				}
//			}
			
			Global.PSC = (ProgramAddress) RTStack.pop().copy();
//			IO.println("SVM_CALL.execute: PSC="+Global.PSC);
//			Global.PSC.segment().dump("SVM_CALL.execute: ", 0, 10);
		} else {
			Global.PSC = rutAddr.copy();
		}
		RTStack.push(retur, "RETUR");

//		IO.println("SVM_CALL: ROUTINE: "+rutAddr);
//		if(rutAddr != null && rutAddr.toString().equals("PSEG_SML_RANK_INTO:BODY[0]")) {
////			int idx = RTStack.size() - 2;
////			IO.println("SVM_CALL.execute: idx="+idx);
//			RTStack.dumpRTStack("");
//			Segment.lookup("POOL_1").dump("SVM_CALL.execute: ", 707, 727);
//			RTUtil.dumpCurins();
//			RTUtil.printPool("POOL_1");
////			RTStack.guard(idx);	
//			Util.IERR("");
//		}
	}
	
	@Override	
	public String toString() {
		String tail = " Return=" + returSlot;
		if(rutAddr == null)
		return "CALL     TOS" + tail;
		return "CALL     " + rutAddr + tail;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_CALL(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iCALL;
		this.returSlot = (FrameAddress) Value.read(inpt);
		boolean present = inpt.readBoolean();
		if(present)	this.rutAddr = (ProgramAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		returSlot.write(oupt);
		if(rutAddr != null) {
			oupt.writeBoolean(true);
			rutAddr.write(oupt);
		} else oupt.writeBoolean(false);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_CALL(inpt);
	}


}
