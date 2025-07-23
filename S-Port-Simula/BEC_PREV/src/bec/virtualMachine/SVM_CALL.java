package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.Segment;
import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;

public class SVM_CALL extends SVM_Instruction {
	private ProgramAddress rutAddr;
	private ObjectAddress returSlot;

	public SVM_CALL(ProgramAddress rutAddr, ObjectAddress returSlot) {
		this.opcode = SVM_Instruction.iCALL;
		this.rutAddr = rutAddr;
		this.returSlot = returSlot;
//		System.out.println("NEW SVM_CALL: "+this);
	}
	
	public static SVM_CALL ofTOS(ObjectAddress returSlot) {
		return new SVM_CALL(null, returSlot);
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
			
			BecGlobal.PSC = (ProgramAddress) RTStack.pop().copy();
//			System.out.println("SVM_CALL.execute: PSC="+Global.PSC);
//			Global.PSC.segment().dump("SVM_CALL.execute: ", 0, 10);
		} else {
			BecGlobal.PSC = rutAddr.copy();
		}
		RTStack.push(retur, "RETUR");

//		if(rutAddr != null && rutAddr.toString().equals("PSEG_KNWN_AR1IND:BODY[0]")) {
////			int idx = RTStack.size() - 2;
////			System.out.println("SVM_CALL.execute: idx="+idx);
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
		this.returSlot = (ObjectAddress) Value.read(inpt);
		boolean present = inpt.readBoolean();
		if(present)	this.rutAddr = (ProgramAddress) Value.read(inpt);
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
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
