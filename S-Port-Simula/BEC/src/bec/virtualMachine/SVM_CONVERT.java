package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CONVERT;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.Value;
import bec.virtualMachine.RTStack.RTStackItem;

public class SVM_CONVERT extends SVM_Instruction {
	Type fromType;
	Type toType;
	
	private final boolean DEBUG = false;
		
	public SVM_CONVERT(Type fromType, Type toType) {
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
		switch(fromType.tag) {
		case Scode.TAG_OADDR: fromValue = RTStack.popOADDR(); break;
		case Scode.TAG_GADDR: fromValue = RTStack.popGADDR(); break;
		default:			  fromValue = RTStack.pop().value();
		}

		if(DEBUG) {
			System.out.println("SVM_CONVERT.execute: fromValue="+fromValue+"  ==> " + toType);
		}

//		RTStackItem item = RTStack.pop();
//		Value fromValue = item.value();
		Value toValue = CONVERT.convValue(fromValue, fromType, toType);
		RTStack.push(toValue, "CONVERT: ");
		Global.PSC.ofst++;
//		Util.IERR("");
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
		fromType.write(oupt);;
		toType.write(oupt);;
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_CONVERT instr = new SVM_CONVERT(Type.read(inpt), Type.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
