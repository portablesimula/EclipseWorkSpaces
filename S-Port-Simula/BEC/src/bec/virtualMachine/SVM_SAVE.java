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

/// Operation SAVE
/// 
///	  Runtime Stack
///		value1, value2, ... , value'size, oaddr →
///		- empty
///
/// The oaddr of a save-object is popped off the Runtime stack.
/// Then the complete Runtime stack is saved within the save-object.
///
/// See: SVM_PUSHLEN and SVM_RERSTORE
/// See also S-Port - Definition of S-code - sect. 7. INTERMEDIATE RESULTS.
///
public class SVM_SAVE extends SVM_Instruction {

	private static final boolean DEBUG = false;

	public SVM_SAVE() {
		this.opcode = SVM_Instruction.iSAVE;
	}
	
	@Override
	public void execute() {
		saveStack();
//		Util.IERR("");
		Global.PSC.addOfst(1);
	}
//	private static Stack<Stack<Value>> saveStack = new Stack<Stack<Value>>();
	
//	 Visible record entity;  info "DYNAMIC";
//	 begin ref(inst)                sl;   -- during GC used as GCL!!!!
//	       range(0:MAX_BYT)         sort; -- S_SAV =  9 for Save Object
//	       range(0:MAX_BYT)         misc;
//	       variant ref(ptp)         pp;   -- used for instances
//	       variant range(0:MAX_TXT) ncha; -- used for text entities
//	       variant size             lng;  -- used for other entities
//	 end;
//
//	 Visible record inst:entity;
//	 begin ref(entity)              gcl;
//	       variant ref(inst)        dl;
//	               label            lsc;
//	       variant entry(Pmovit)    moveIt;
//	 end;
//	 Visible record savent:inst;
//	 begin  end;
	public final static int saveEntityHead = 7;
	public final static int sizeOffset = 3;
	private static void saveStack() {
//		RTStack.dumpRTStack("RTStack.saveStack: ");
		ObjectAddress savePos = RTStack.popOADDR();
		if(savePos == null) {
//			Util.IERR("");
		} else {
			ObjectAddress saveObj = savePos.addOffset(-saveEntityHead);
			IntegerValue entitySize = (IntegerValue) saveObj.addOffset(sizeOffset).load();
			int size = entitySize.value - saveEntityHead;
			if(size != RTStack.size()) Util.IERR("");
			
			if(DEBUG) {
//				RTUtil.dumpEntity(saveObj);
//				IO.println("RTStack.saveStack:    SAVE-RESTORE  entitySize = " + entitySize);
//				IO.println("RTStack.saveStack:    SAVE-RESTORE  Size = " + size);
			}
			for(int i=0;i<size;i++) {
				Value item = RTStack.pop();
				if(DEBUG) {
					IO.println("RTStack.saveStack:    SAVE-RESTORE " + item + " ===> saveObj("+(saveEntityHead + i)+")");
				}
				saveObj.store(saveEntityHead + i, item, "");
			}
			if(DEBUG) {
				RTUtil.dumpEntity(saveObj);
			}
		}
	}
	
	@Override
	public String toString() {
		return "SAVE     ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_SAVE(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iSAVE;
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_SAVE(inpt);
	}

}
