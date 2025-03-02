package bec.virtualMachine;

import java.io.IOException;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;

public class SVM_PRECALL extends SVM_Instruction {
	RTFrame frame;
	SVM_PRECALL prevPRECALL;

//	public SVM_PRECALL(int exportSize) {
	public SVM_PRECALL(RTFrame frame) {
		this.opcode = SVM_Instruction.iPRECALL;
//		this.exportSize = exportSize;
		this.frame = frame;
	}

	@Override
	public void execute() {
		prevPRECALL = RTStack.PRECALL;
		RTStack.PRECALL = this;
//		thisFrame = RTStack.nextFrame();
		frame.enclFrame = RTStack.curFrame;
		frame.rtStackIndex = RTStack.size();
		for(int i=0;i<frame.exportSize;i++) {
			RTStack.push(null, "EXPORT"); // Export slots		
		}
		
		Global.PSC.ofst++;
//		Util.IERR(""+res);
	}
	
	@Override	
	public String toString() {
		return "PRECALL   ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
//		oupt.writeShort(exportSize);
		frame.write(oupt);
	}

	public static SVM_PRECALL read(AttributeInputStream inpt) throws IOException {
		SVM_PRECALL instr = new SVM_PRECALL(RTFrame.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
