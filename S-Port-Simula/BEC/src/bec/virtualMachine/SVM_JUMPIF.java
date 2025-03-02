package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.value.Value;

public class SVM_JUMPIF extends SVM_JUMP {
	Relation relation;
//	MemAddr destination;

	public SVM_JUMPIF(Relation relation, ProgramAddress destination) {
		super(destination);
		this.opcode = SVM_Instruction.iJUMPIF;
		this.relation = relation;
//		this.destination = destination;
	}

	@Override
	public void execute() {
//		RTStack.dumpRTStack("SVM_JUMPIF: ");
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
		boolean res = relation.eval(sos, tos);
//		System.out.println("SVM_JUMPIF: " + tos + "  " + relation + "  " + sos + " = " + res);
//		RTStack.push(type, res);
		if(res) Global.PSC = destination;
		else Global.PSC.ofst++;
//		Util.IERR("");
	}
	
	@Override	
	public String toString() {
		return "JUMPIF   " + relation + " " + destination;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_JUMPIF(AttributeInputStream inpt) throws IOException {
		super(inpt);
		this.opcode = SVM_Instruction.iJUMPIF;
		this.relation = Relation.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		destination.write(oupt);
		relation.write(oupt);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_JUMPIF(inpt);
	}

}
