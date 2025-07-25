package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;

public class SVM_RESTORE extends SVM_Instruction {

	private static final boolean DEBUG = false;

	public SVM_RESTORE() {
		this.opcode = SVM_Instruction.iRESTORE;
	}
	
	@Override
	public void execute() {
		restoreStack();
//		Util.IERR("");
		BecGlobal.PSC.addOfst(1);
	}
	
	private static void restoreStack() {
//		RTStack.dumpRTStack("RTStack.restoreStack: ");
		ObjectAddress savePos = RTStack.popOADDR();
		if(savePos == null) {
//			Util.IERR("");
		} else {
			ObjectAddress saveObj = savePos.addOffset(-SVM_SAVE.saveEntityHead);
			IntegerValue entitySize = (IntegerValue) saveObj.addOffset(SVM_SAVE.sizeOffset).load();
			int size = entitySize.value - SVM_SAVE.saveEntityHead;
			
			if(DEBUG) {
				System.out.println("RTStack.restoreStack: BEGIN RESTORE ++++++++++++++++++++++++++++++++++++++++");
				RTUtil.dumpEntity(saveObj);
				System.out.println("RTStack.restoreStack: RESTORE  entitySize = " + entitySize);
				System.out.println("RTStack.restoreStack: RESTORE  Size = " + size);
			}
	
			for(int i=size-1;i>=0;i--) {
//			for(int i=0;i<size;i++) {
//				Value item = saveObj.addOffset(SVM_SAVE.saveEntityHead + i).load();
				Value item = saveObj.load(SVM_SAVE.saveEntityHead + i);
//				System.out.println("RTStack.restoreStack: RESTORE  item = " + item);
				if(DEBUG) {
					System.out.println("RTStack.saveStack:    SAVE-RESTORE " + item + " <=== saveObj("+(SVM_SAVE.saveEntityHead + i)+")");
				}
				RTStack.push(item, "RESTORE: ");
			}
	
			if(DEBUG) {
				RTStack.dumpRTStack("RTStack.restoreStack: ");
				Util.IERR("");
			}
		}
	}

	
	@Override
	public String toString() {
		return "RESTORE  ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_RESTORE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iRESTORE;
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RESTORE(inpt);
	}

}
