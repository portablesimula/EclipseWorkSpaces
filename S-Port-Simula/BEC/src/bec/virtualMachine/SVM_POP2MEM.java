package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.AddressItem;
import bec.instruction.CALL;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;
import bec.virtualMachine.RTStack.RTStackItem;

// POP RT-Stack'TOS --> MemAddr

//The count values on the top of the operand stack is popped off and stored at addr...
public class SVM_POP2MEM extends SVM_Instruction {
	RTAddress addr;
	int count;
	
	public SVM_POP2MEM(AddressItem itm, int count) {
		this.opcode = SVM_Instruction.iPOP2MEM;
		this.addr = new RTAddress(itm);
		this.count = count;
	}
	
	public SVM_POP2MEM(ObjectAddress addr, int count) {
		this.opcode = SVM_Instruction.iPOP2MEM;
		this.addr = new RTAddress(addr);
		this.count = count;
	}
	
	@Override
	public void execute() {
//		Global.PSC.segment().dump("POP2MEM.execute: ");
		RTStack.dumpRTStack("POP2MEM.execute: ");
		ObjectAddress target = addr.toObjectAddress();
		target.ofst = target.ofst + count;
		for(int i=0;i<count;i++) {
			RTStackItem item = RTStack.pop();
			target.ofst--;
			System.out.println("POP2MEM: "+item.value()+" ==> "+target + "      " + item.comment());
			target.store(item.value(), item.comment());
		}
		if(CALL.USE_FRAME_ON_STACK) {
//			DataSegment seg = target.segment();
//			if(seg != null) {
//				seg.dump("POP2MEM.execute: ");
//			} else {
//				RTStack.dumpRTStack("POP2MEM.execute: ");
//			}
//			dumpString(target);
//			Util.IERR("");
		} else {
			target.segment().dump("POP2MEM.execute: ");
//			dumpString(target);
//			Util.IERR("");
		}
		Global.PSC.ofst++;
	}
	
	private void dumpString(ObjectAddress addr) {
		ObjectAddress x = addr.ofset(0);
		Value val = x.load();
		System.out.println("POP2MEM.dumpString: val-1  "+val.getClass().getSimpleName()+"  "+val);
		x.ofst++;
		val = x.load();
		System.out.println("POP2MEM.dumpString: val-2  "+val.getClass().getSimpleName()+"  "+val);
	}
	
	public String toString() {
		String cnt = "";
		if(count > 1) cnt = ", count=" + count;
		return "POP2MEM  " + addr + cnt;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_POP2MEM(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPOP2MEM;
//		this.addr = RTAddress.read(inpt);
		boolean present = inpt.readBoolean();
		if(present) {
			this.addr = RTAddress.read(inpt);
		}
		this.count = inpt.readShort();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeKind(opcode);
//		addr.write(oupt);
		if(addr != null) {
			oupt.writeBoolean(true);
			addr.write(oupt);
		} else oupt.writeBoolean(false);
		oupt.writeShort(count);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_POP2MEM(inpt);
	}

}
