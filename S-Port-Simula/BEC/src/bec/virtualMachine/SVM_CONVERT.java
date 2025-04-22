package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CONVERT;
import bec.util.Global;
import bec.util.Scode;
import bec.value.Value;
import bec.virtualMachine.RTStack.RTStackItem;

public class SVM_CONVERT extends SVM_Instruction {
	int fromType;
	int toType;
	
	private final boolean DEBUG = false;
		
	public SVM_CONVERT(int fromType, int toType) {
		this.opcode = SVM_Instruction.iCONVERT;
		this.fromType = fromType;
		this.toType = toType;
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
			System.out.println("SVM_CONVERT.execute: "+fromType+"  ==> " + toType);
			RTStackItem tos = RTStack.peek();
			System.out.println("SVM_CONVERT.execute: TOS="+tos);
		}
		
		Value fromValue = null;
		switch(fromType) {
		case Scode.TAG_OADDR: fromValue = RTStack.popOADDR(); break;
		case Scode.TAG_GADDR: fromValue = RTStack.popGADDR(); break;
		default:			  fromValue = RTStack.pop().value();
		}

		if(DEBUG) {
			System.out.println("SVM_CONVERT.execute: fromValue="+fromValue+"  ==> " + toType);
		}

		Value toValue = CONVERT.convValue(fromValue, fromType, toType);
		RTStack.push(toValue, "CONVERT: ");
		Global.PSC.ofst++;
	}
	
	public String toString() {
		return "CONVERT  " + fromType + " ==> " + toType;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(fromType);
		oupt.writeShort(toType);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_CONVERT instr = new SVM_CONVERT(inpt.readShort(), inpt.readShort());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
