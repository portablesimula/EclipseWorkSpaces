/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;

/// SVM-INSTRUCTION: SAVE
/// 
///	  Runtime Stack
///		oaddr →
///		value1, value2, ... , value'size  
///
/// The oaddr of a save-object is popped off the Runtime stack.
/// Then the complete Runtime stack is restored from the save-object.
///
/// See: SVM_PUSHLEN and SVM_RERSTORE
/// See also S-Port - Definition of S-code - sect. 7. INTERMEDIATE RESULTS.
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_AND.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_RESTORE extends SVM_Instruction {

	private static final boolean DEBUG = false;

	public SVM_RESTORE() {
		this.opcode = SVM_Instruction.iRESTORE;
	}
	
	@Override
	public void execute() {
		ObjectAddress savePos = RTStack.popOADDR();
		if(savePos != null)	restoreStack(savePos);
		Global.PSC.addOfst(1);
	}
	
	private static void restoreStack(ObjectAddress savePos) {
		IntegerValue entitySize = (IntegerValue) savePos.addOffset(SVM_SAVE.sizeOffset - SVM_SAVE.saveEntityHead).load();
		int size = entitySize.value - SVM_SAVE.saveEntityHead;
		
		if(DEBUG) {
			IO.println("RTStack.restoreStack: BEGIN RESTORE ++++++++++++++++++++++++++++++++++++++++");
			ObjectAddress saveObj = savePos.addOffset(-SVM_SAVE.saveEntityHead);
			RTUtil.dumpEntity(saveObj);
			IO.println("RTStack.restoreStack: RESTORE  entitySize = " + entitySize);
			IO.println("RTStack.restoreStack: RESTORE  Size = " + size);
		}

		for(int i=size-1;i>=0;i--) {
			Value item = savePos.load(i);
			if(DEBUG) {
				IO.println("RTStack.saveStack:    SAVE-RESTORE " + item + " <=== saveObj("+(SVM_SAVE.saveEntityHead + i)+")");
			}
			RTStack.push(item);
		}

		if(DEBUG) {
			RTStack.dumpRTStack("RTStack.restoreStack: ");
			Util.IERR("");
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
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_RESTORE(inpt);
	}

}
