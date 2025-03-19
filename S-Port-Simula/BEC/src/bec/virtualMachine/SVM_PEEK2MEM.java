package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.AddressItem;
import bec.util.Global;
import bec.value.ObjectAddress;
import bec.value.Value;
import bec.virtualMachine.RTStack.RTStackItem;

// POP RT-Stack'TOS --> MemAddr

//The count values on the top of the operand stack is popped off and stored at addr...
public class SVM_PEEK2MEM extends SVM_Instruction {
	RTAddress addr;
	int count;
	
	private final boolean DEBUG = true;
	
	public SVM_PEEK2MEM(AddressItem itm, int count) {
		this.opcode = SVM_Instruction.iPEEK2MEM;
		this.addr = new RTAddress(itm);
		this.count = count;
	}
	
	public SVM_PEEK2MEM(ObjectAddress addr, int count) {
		this.opcode = SVM_Instruction.iPEEK2MEM;
		this.addr = new RTAddress(addr,0);
		this.count = count;
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
//			Global.PSC.segment().dump("PEEK2MEM.execute: ");
			RTStack.dumpRTStack("PEEK2MEM.execute: ");
		}
		if(this.addr.segID == null) {
			// this.addr is Stack Relative Address
			ObjectAddress objAddr = addr.reladdr2ObjAddr();
			int n = RTStack.size()-1;
			int idx = count - 1;
//			for(int i=count-1;i>=0;i--) {
			for(int i=0;i<count;i++) {
				RTStackItem item = RTStack.load(n-i);
				if(DEBUG) System.out.println("PEEK2MEM: "+item.value()+" |==> "+objAddr + "["+idx+"]" + "      " + item.comment());
				objAddr.store(idx--, item.value(), item.comment());
			}
//			Util.IERR("");
		} else {
			// this.addr is Segment Address
			int n = RTStack.size()-1;
//			System.out.println("PEEK2MEM.execute: STACK["+n+"]="+RTStack.load(n));
//			System.out.println("PEEK2MEM.execute: count="+count);
//			for(int i=count-1;i>=0;i--) {
			int idx = count - 1;
			for(int i=0;i<count;i++) {
//				RTStackItem item = RTStack.pop();
				RTStackItem item = RTStack.load(n-i);
				if(DEBUG) System.out.println("PEEK2MEM: "+item.value()+" ==> "+addr + "["+idx+"]" + "      " + item.comment());
				addr.store(idx--, item.value(), item.comment());
			}
		}
		Global.PSC.ofst++;		
	}
	
//	public void OLD_execute() {
//		if(DEBUG) {
////			Global.PSC.segment().dump("PEEK2MEM.execute: ");
//			RTStack.dumpRTStack("PEEK2MEM.execute: ");
//		}
//		ObjectAddress target = addr.toObjectAddress();
////		target.ofst = target.ofst + count;
//		target = target.addOffset(count);
////		for(int i=0;i<count;i++) {
//		int n = RTStack.size()-1;
////		System.out.println("PEEK2MEM.execute: STACK["+n+"]="+RTStack.load(n));
////		System.out.println("PEEK2MEM.execute: count="+count);
////		for(int i=count-1;i>=0;i--) {
//		for(int i=0;i<count;i++) {
////			RTStackItem item = RTStack.pop();
//			RTStackItem item = RTStack.load(n-i);
//			target.decrOffset();
//			if(DEBUG) {
//				System.out.println("PEEK2MEM: "+item.value()+" ==> "+target + "      " + item.comment());
//			}
//			target.store(item.value(), item.comment());
//		}
//		if(DEBUG) {
//			if(CALL.USE_FRAME_ON_STACK) {
////				DataSegment seg = target.segment();
////				if(seg != null) {
////					seg.dump("PEEK2MEM.execute: ");
////				} else {
////					RTStack.dumpRTStack("PEEK2MEM.execute: ");
////				}
////				dumpString(target);
////				Util.IERR("");
//			} else {
//				target.segment().dump("PEEK2MEM.execute: ");
////				dumpString(target);
////				Util.IERR("");
//			}
//		}
//		Global.PSC.ofst++;
//	}
	
	private void dumpString(ObjectAddress addr) {
		ObjectAddress x = addr.addOffset(0);
		Value val = x.load();
		System.out.println("PEEK2MEM.dumpString: val-1  "+val.getClass().getSimpleName()+"  "+val);
		x.incrOffset();
		val = x.load();
		System.out.println("PEEK2MEM.dumpString: val-2  "+val.getClass().getSimpleName()+"  "+val);
	}
	
	public String toString() {
		String cnt = "";
		if(count > 1) cnt = ", count=" + count;
		return "PEEK2MEM  " + addr + cnt;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_PEEK2MEM(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPEEK2MEM;
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
		oupt.writeOpcode(opcode);
//		addr.write(oupt);
		if(addr != null) {
			oupt.writeBoolean(true);
			addr.write(oupt);
		} else oupt.writeBoolean(false);
		oupt.writeShort(count);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_PEEK2MEM(inpt);
	}

}
