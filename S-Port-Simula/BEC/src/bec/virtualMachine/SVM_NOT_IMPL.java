package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Util;

public class SVM_NOT_IMPL extends SVM_Instruction {
	String info;

	public SVM_NOT_IMPL(String info) {
		this.opcode = SVM_Instruction.iNOT_IMPL;
		this.info = info;
		if(info == null) Util.IERR("");
	}

	@Override
	public void execute() {
		System.out.println("SVM_NOT_IMPL: DETTE MÅ RETTES: " + info);
		Global.PSC.ofst++;
//		Segment.dumpAll("PMD: ");
//		Segment.lookup("PSEG_MNTR_B_PROG").dump(info);
		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "NOT_IMPL: " + info;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_NOT_IMPL(AttributeInputStream inpt, String info) throws IOException {
		this.opcode = SVM_Instruction.iNOT_IMPL;
		this.info = info;
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeString(info);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		String info = inpt.readString();
		return new SVM_NOT_IMPL(inpt, info);
	}

}
