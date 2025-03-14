package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CALL;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;

public class SVM_RETURN extends SVM_Instruction {
	public ObjectAddress returAddr;

	public SVM_RETURN(ObjectAddress returAddr) {
		this.opcode = SVM_Instruction.iRETURN;
		this.returAddr = returAddr;
		if(returAddr == null) Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "RETURN   " + returAddr;
	}
	
	@Override	
	public void execute() {
//		System.out.println("SVM_RETURN.execute: returAddr=" + returAddr);
		if(CALL.USE_FRAME_ON_STACK) {
			
//			RTStack.printCallStack("SVM_RETURN.execute: TESTING");

//			RTStack.curFrame.dump("SVM_RETURN: ");
			RTStack.checkStackEmpty();
			ProgramAddress padr = (ProgramAddress) returAddr.load();
//			System.out.println("SVM_RETURN.execute: padr=" + padr);
//			RTStack.dumpRTStack("SVM_RETURN.execute: returAddr="+padr);
			if(Global.EXEC_TRACE > 0) {
				String tail = (RTStack.curFrame == null)? RTStack.toLine() : RTStack.curFrame.toLine();
				String line = "EXEC: "+Global.PSC+"  "+this;
				while(line.length()<70) line=line+' ';
				System.out.println(line+"   "+tail);
//				RTStack.dumpRTStack("ProgramAddress.execute:");
//				Util.IERR("");
			}
			
			int n = RTStack.size() - RTStack.curFrame.rtStackIndex - RTStack.curFrame.exportSize;
			for(int i=0;i<n;i++) RTStack.pop();				
			RTStack.curFrame = RTStack.curFrame.enclFrame;
			
			if(Global.EXEC_TRACE > 1) {
				if(RTStack.curFrame == null) {
					RTStack.dumpRTStack("SVM_RETURN: Continue at " + padr);
				} else {
					RTStack.curFrame.dump("SVM_RETURN: Continue at " + padr);
				}
			}
			Global.PSC = padr.copy();
//			Util.IERR("");
		} else {
			returAddr.segment().dump("SVM_RETURN.execute: ");
			
			DataSegment DSEG = (DataSegment) returAddr.segment();
//			DSEG.dump("SVM_RETURN.execute: ");
			ProgramAddress padr = (ProgramAddress) DSEG.load(returAddr.getOfst());
//			System.out.println("SVM_RETURN.execute: padr=" + padr);
			Global.PSC = padr.copy();
		}
	}

	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_RETURN(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iRETURN;
		this.returAddr = (ObjectAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		returAddr.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RETURN(inpt);
	}

}
