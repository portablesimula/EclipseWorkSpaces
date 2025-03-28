package bec.virtualMachine;

import bec.value.ProgramAddress;
import bec.virtualMachine.RTStack.RTStackItem;

//	FRAME:
//		EXPORT ?
//		IMPORT
//		...
//		IMPORT
//		RETURN ADDRESS
//		LOCAL
//		...
//		LOCAL
public class CallStackFrame {
	public String ident;
	public int rtStackIndex;
	public int exportSize;
	public int importSize;
	public ProgramAddress curAddr;
	int localSize;
	
	public String toString() {
		return ident + ": rtStackIndex=" + rtStackIndex + ", exportSize=" + exportSize + ", importSize=" + importSize + ", localSize=" + localSize;
	}
	public CallStackFrame(String ident, int rtStackIndex, int exportSize, int importSize) {
		this.ident = ident;
		this.rtStackIndex = rtStackIndex;
		this.exportSize = exportSize;
		this.importSize = importSize;
	}

	public int headSize() {
		return exportSize + importSize + 1 + localSize; 
	}
	
	public ProgramAddress returnAddress() {
		int idx = rtStackIndex + exportSize + importSize;
		return (ProgramAddress) RTStack.load(idx).value();
	}
	
	public ProgramAddress callAddress() {
		ProgramAddress addr = returnAddress();
		addr.ofst--;
		return addr;
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
			sb.append((item == null)? null : item.value());
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

	public void print(String title) {
		CallStackFrame callStackTop = RTStack.callStack_TOP();
//		System.out.println("==================== " + title + " RTFrame'DUMP ====================");
//		System.out.println("   ROUTINE: " + rutAddr + " callStackTop.rtStackIndex=" + callStackTop.rtStackIndex);
		String indent = "            ";
		try {
//			int idx = callStackTop.rtStackIndex;
			int idx = rtStackIndex;
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
			System.out.println(indent+"RETURN: " + idx + ": " + RTStack.load(idx).value()); idx++;
			for(int i=0;i<localSize;i++) {
				RTStackItem item = RTStack.load(idx);
				System.out.println(indent+"LOCAL:  " + idx + ": " + item.value()); idx++;
			}
//			while(idx < RTStack.size()) {
//				RTStackItem item = RTStack.load(idx);
//				System.out.println(indent+"STACK:  " + idx + ": " + item.value()); idx++;			
//			}
		} catch(Exception e) {}
//		System.out.println("==================== " + title + " RTFrame' END  ====================");
//		if(rutAddr != null) rutAddr.segment().dump(title);
	}

	public void dump(String title) {
		CallStackFrame callStackTop = RTStack.callStack_TOP();
		System.out.println("==================== " + title + " RTFrame'DUMP ====================");
		System.out.println("   ROUTINE: " + curAddr + " callStackTop.rtStackIndex=" + callStackTop.rtStackIndex);
//		String indent = "            ";
//		try {
//			int idx = callStackTop.rtStackIndex;
//			if(exportSize > 0) {
//				for(int i=0;i<exportSize;i++) {
//					RTStackItem item = RTStack.load(idx);
//					System.out.println(indent+"EXPORT: " + idx + ": " + item.value()); idx++;
//				}
//			}
//			for(int i=0;i<importSize;i++) {
//				RTStackItem item = RTStack.load(idx);
//				System.out.println(indent+"IMPORT: " + idx + ": " + item.value()); idx++;
//			}
//			System.out.println(indent+"RETURN: " + idx + ": " + RTStack.load(idx)); idx++;
//			for(int i=0;i<localSize;i++) {
//				RTStackItem item = RTStack.load(idx);
//				System.out.println(indent+"LOCAL:  " + idx + ": " + item.value()); idx++;
//			}
//			while(idx < RTStack.size()) {
//				RTStackItem item = RTStack.load(idx);
//				System.out.println(indent+"STACK:  " + idx + ": " + item.value()); idx++;			
//			}
//		} catch(Exception e) {}
		
		print(title);
		
		System.out.println("==================== " + title + " RTFrame' END  ====================");
		if(curAddr != null) curAddr.segment().dump(title);
	}

}
