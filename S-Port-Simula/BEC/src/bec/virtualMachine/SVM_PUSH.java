package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Variable;
import bec.instruction.CALL;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

// The count values at addr... is pushed onto the operand stack.
public class SVM_PUSH extends SVM_Instruction {
	RTAddress addr;
	int count;
	
//	public SVM_PUSH(Type type, ObjectAddress addr, int indexReg, int count) {
//		this.opcode = SVM_Instruction.iPUSH;
//		this.type = type;
//		this.addr = addr;
//		this.indexReg = indexReg;
//		this.count = count;
//	}
	
	public SVM_PUSH(RTAddress addr, int count) {
		this.opcode = SVM_Instruction.iPUSH;
		this.addr = addr;
		this.count = count;
	}
	
	public SVM_PUSH(Variable var) {
		this.opcode = SVM_Instruction.iPUSH;
		this.addr = new RTAddress(var.address);
		this.count = var.repCount;
	}
	
	@Override
	public void execute() {
		if(CALL.USE_FRAME_ON_STACK) {
			ObjectAddress target = addr.toObjectAddress();
//			System.out.println("SVM_PUSH: target=" + target);
			for(int i=0;i<count;i++) {
				Value value = target.load(); target.ofst++;
//				System.out.println("SVM_PUSH: " + value);
				RTStack.push(value, "SVM_PUSH");
			}
//			Util.IERR("");
		} else {
			ObjectAddress target = addr.toObjectAddress();
			DataSegment dseg = (DataSegment) target.segment();
//			dseg.dump("SVM_PUSH: " + addr + ", count=" + count);
			int ofst = target.ofst;
			for(int i=0;i<count;i++) {
				Value value = dseg.load(ofst++);
//				System.out.println("SVM_PUSH: " + value);
				RTStack.push(value, "SVM_PUSH");
			}
		}
		Global.PSC.ofst++;
	}
	
	@Override
	public String toString() {
		String s = "PUSH     " + addr;
		if(count > 1) s += ", " + count;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PUSH(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSH;
		this.addr = RTAddress.read(inpt);
		this.count = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
		addr.write(oupt);
		oupt.writeShort(count);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_PUSH(inpt);
	}

}
