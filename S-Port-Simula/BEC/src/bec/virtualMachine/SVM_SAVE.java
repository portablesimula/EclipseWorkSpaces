package bec.virtualMachine;

import java.io.IOException;
import java.util.Stack;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;

//The value is pushed onto the operand stack.
public class SVM_SAVE extends SVM_Instruction {

	private static final boolean DEBUG = false;

	public SVM_SAVE() {
		this.opcode = SVM_Instruction.iSAVE;
	}
	
	/// save
	/// * force TOS value; check TOS type(OADDR);
	/// * pop;
	/// * remember stack;
	/// * purge stack;
	///
	/// TOS describes the address of a save-object. The size of this object is as determined by the
	/// preceding pushlen. The complete state of the stack is remembered (together with the values of
	/// ALLOCATED and MARKS) and the compilation continues with an empty stack.
	///
	/// Code is generated, which - if TOS.VALUE <> onone (see note below) - at run time will save the
	/// used part of the temporary area, and set the SAVE-MARKS attribute.
	///
	/// TOS is popped.
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
	public static int saveEntityHead = 7;
	public static int sizeOffset = 3;
	private static void saveStack() {
//		RTStack.dumpRTStack("RTStack.saveStack: ");
		ObjectAddress savePos = RTStack.popOADDR();
		ObjectAddress saveObj = savePos.addOffset(-saveEntityHead);
		IntegerValue entitySize = (IntegerValue) saveObj.addOffset(sizeOffset).load();
		int size = entitySize.value - saveEntityHead;
		if(size != RTStack.size()) Util.IERR("");
		
		if(DEBUG) {
//			RTUtil.dumpEntity(saveObj);
//			System.out.println("RTStack.saveStack:    SAVE-RESTORE  entitySize = " + entitySize);
//			System.out.println("RTStack.saveStack:    SAVE-RESTORE  Size = " + size);
		}
		for(int i=0;i<size;i++) {
			Value item = RTStack.pop();
			if(DEBUG) {
				System.out.println("RTStack.saveStack:    SAVE-RESTORE " + item + " ===> saveObj("+(saveEntityHead + i)+")");
			}
			saveObj.store(saveEntityHead + i, item, "");
		}
		if(DEBUG) {
			RTUtil.dumpEntity(saveObj);
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
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_SAVE(inpt);
	}

}
