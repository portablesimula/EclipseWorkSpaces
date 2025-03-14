package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Util;
import bec.value.ProgramAddress;
import bec.value.Value;
import bec.virtualMachine.RTStack.RTStackItem;

// FRAME:
//			EXPORT ?
//			IMPORT
//			...
//			IMPORT
//			RETURN ADDRESS
//			LOCAL
//			...
//			LOCAL
public class RTFrame {
	public RTFrame enclFrame;		// Set at Runtime: SVM_CALLSYS, SCM_PRECALL, SVM_RETIRN
	public ProgramAddress rutAddr;	// Set at Runtime
	public int rtStackIndex;		// Set at Runtime
	int exportSize;
	int importSize;
	int localSize;					// Set at Runtime 
	
	public RTFrame(int exportSize, int importSize) {
		this.exportSize = exportSize;
		this.importSize = importSize;
	}
	
	public int headSize() {
		return exportSize + importSize + 1 + localSize; 
	}
	
	public String toLine() {
		StringBuilder sb = new StringBuilder();
		int idx = rtStackIndex;
		int stx = idx + exportSize + importSize + 1 + localSize;
		boolean first = true;
		sb.append("Frame["+(stx-idx)+"]: ");
		while(idx < stx) {
			RTStackItem item = RTStack.load(idx++);
			if(! first) sb.append(", "); first = false;
			sb.append(item.value());
		}
		sb.append("  Stack["+(RTStack.size()-idx)+"]: ");
		first = true;
		while(idx < RTStack.size()) {
			RTStackItem item = RTStack.load(idx++);
			if(! first) sb.append(", "); first = false;
			sb.append(item.value());
		}
		return sb.toString();
	}
	
	public void dump(String title) {
		System.out.println("==================== " + title + " RTFrame'DUMP ====================");
		System.out.println("   ROUTINE: " + rutAddr + " curFrame.rtStackIndex=" + RTStack.curFrame.rtStackIndex);
		String indent = "            ";
		try {
			int idx = RTStack.curFrame.rtStackIndex;
			if(exportSize > 0) {
				for(int i=0;i<exportSize;i++) {
					RTStackItem item = RTStack.load(idx);
					System.out.println(indent+"EXPORT: " + idx + ": " + item.value()); idx++;
				}
			}
			for(int i=0;i<importSize;i++) {
				RTStackItem item = RTStack.load(idx);
				System.out.println(indent+"IMPORT: " + idx + ": " + item.value()); idx++;
			}
			System.out.println(indent+"RETURN: " + idx + ": " + RTStack.load(idx)); idx++;
			for(int i=0;i<localSize;i++) {
				RTStackItem item = RTStack.load(idx);
				System.out.println(indent+"LOCAL:  " + idx + ": " + item.value()); idx++;
			}
			while(idx < RTStack.size()) {
				RTStackItem item = RTStack.load(idx);
				System.out.println(indent+"STACK:  " + idx + ": " + item.value()); idx++;			
			}
		} catch(Exception e) {}
		System.out.println("==================== " + title + " RTFrame' END  ====================");
		if(rutAddr != null) rutAddr.segment().dump(title);
	}
	
	public ProgramAddress returAddress() {
//		RTStack.dumpRTStack("RTFrame.returAddress: STACK");
//		this.dump("");
//		System.out.println("RTFrame.returAddress: enclFrame="+enclFrame);
//		System.out.println("RTFrame.returAddress: rutAddr="+rutAddr);
//		System.out.println("RTFrame.returAddress: rtStackIndex="+rtStackIndex);
//		System.out.println("RTFrame.returAddress: exportSize="+exportSize);
//		System.out.println("RTFrame.returAddress: importSize="+importSize);
//		int idx = RTStack.curFrame.rtStackIndex+exportSize + importSize;
		int idx = rtStackIndex+exportSize + importSize;
//		System.out.println("RTFrame.returAddress: idx="+idx);
		RTStackItem ret = RTStack.load(idx);
		ProgramAddress value = (ProgramAddress) ret.value();
//		System.out.println("RTFrame.returAddress: value="+value.getClass().getSimpleName());
//		Util.IERR(""+ret.getClass().getSimpleName()+"  "+ret);
		return value;
	}
	
	@Override	
	public String toString() {
		return "FRAME " + rutAddr;// + " Return=" + returSlot;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RTFrame(AttributeInputStream inpt) throws IOException {
		this.rtStackIndex = inpt.readShort();
		this.exportSize = inpt.readShort();
		this.importSize = inpt.readShort();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(rtStackIndex);
		oupt.writeShort(exportSize);
		oupt.writeShort(importSize);
	}

	public static RTFrame read(AttributeInputStream inpt) throws IOException {
		return new RTFrame(inpt);
	}

}
