package bec.virtualMachine;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * Remove two items on the Runtime-Stack and push the value (SOS + TOS)
 */
public class SVM_ASSIGN extends SVM_Instruction {
	private int size; // Value's type.size()
	private int offset;

	private final boolean DEBUG = false;

	/**
	 * assign_instruction ::= assign | update | rupdate
	 * 
	 * assign (dyadic)
	 * 
	 * - force TOS value;
	 * - check SOS ref; check types identical;
	 * - pop; pop;
	 * 
	 * Code is generated to transfer the value described by TOS to the location designated by SOS.
	 * This implies that the stack elements must be evaluated, and that any code generation involving
	 * TOS or SOS, that has been deferred for optimisation purposes, must take place before the
	 * assignment code is generated. SOS and TOS are popped from the stack.
	 */
	public SVM_ASSIGN(int size, int offset) {
		this.opcode = SVM_Instruction.iASSIGN;
		this.size = size;
		this.offset = offset;
	}

	@Override
	public void execute() {
		if(this.size > 1) {
			if(DEBUG) {
				RTUtil.printCurins();
//				RTUtil.printEntity(new ObjectAddress("POOL_1", 718));
//				RTUtil.printPool("POOL_1");
//				((DataSegment) Segment.lookup("POOL_1")).dump("POOL_1: " , 718, 718+11);
//				Util.IERR("");
				RTStack.dumpRTStack("SVM_ASSIGN: ");
			}
			Vector<Value> values = new Vector<Value>();
			for(int i=0;i<size;i++) {
				Value value = RTStack.pop().value();
				System.out.println("SVM_ASSIGN: pop: " + value);
				values.add(value);
			}
			ObjectAddress sos = (ObjectAddress) RTStack.pop().value();
			System.out.println("SVM_ASSIGN: sos="+sos+", size="+size);
//			for(int i=0;i<size;i++) {
//			for(int i=size-1;i<=0;i--) {
			for(int i=size-1;i>=0;i--) {
				System.out.println("SVM_ASSIGN: sos.store: " + (offset+i) + " " + values.get(i));
				sos.store(offset + i, values.get(i), "");
			}
			if(DEBUG) {
				RTUtil.printCurins();
//				RTUtil.printEntity(new ObjectAddress("POOL_1", 718));
//				RTUtil.printPool("POOL_1");
				((DataSegment) Segment.lookup("POOL_1")).dump("SVM_ASSIGN: " , 714, 714+11);
				((DataSegment) Segment.lookup("CSEG_ADHOC02")).dump("SVM_ASSIGN: ");
				Util.IERR("");
			}
		} else {
			Value tos = RTStack.pop().value();
			ObjectAddress sos = (ObjectAddress) RTStack.pop().value();
			if(DEBUG) {
				if(tos != null)	System.out.println("SVM_ASSIGN: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
				if(sos != null)	System.out.println("SVM_ASSIGN: SOS: " + sos.getClass().getSimpleName() + "  " + sos);
				System.out.println("SVM_ASSIGN: " + sos + " := " + tos);
//				RTUtil.printCurins();
//				RTUtil.printPool("POOL_1");
//				RTUtil.printEntity(new ObjectAddress("POOL_1", 715));
//				Util.IERR("");
			}
			int idx = 0;
			sos.store(idx, tos, "");
			if(DEBUG) {
				RTUtil.printCurins();
				RTUtil.printEntity(new ObjectAddress("POOL_1", 715));
			}
//			Util.IERR("");
		}
		Global.PSC.ofst++;
	}

	@Override	
	public String toString() {
		return "ASSIGN      size=" + size + ", offset="+offset;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(size);
		oupt.writeShort(offset);
	}

	public static SVM_ASSIGN read(AttributeInputStream inpt) throws IOException {
		SVM_ASSIGN instr = new SVM_ASSIGN(inpt.readShort(), inpt.readShort());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
