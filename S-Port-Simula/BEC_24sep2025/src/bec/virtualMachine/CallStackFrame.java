package bec.virtualMachine;

import bec.value.ProgramAddress;
import bec.value.Value;

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
//		IO.println("NEW CallStackFrame: " + toLine());
//		IO.println("NEW CallStackFrame: " + ident + " rtStackIndex="+rtStackIndex + ", exportSize="+exportSize + ", importSize="+importSize);
	}

	public int headSize() {
		return exportSize + importSize + 1 + localSize; 
	}
	
	public ProgramAddress returnAddress() {
		int idx = rtStackIndex + exportSize + importSize;
		return (ProgramAddress) RTStack.load(idx);
	}
	
	public ProgramAddress callAddress() {
		ProgramAddress addr = returnAddress();
//		addr.ofst--;
		addr.addOfst(-1);
		return addr;
	}

	public String toLine() {
		StringBuilder sb = new StringBuilder();
		int idx = rtStackIndex;
		int stx = idx + exportSize + importSize + 1 + localSize;
		boolean first = true;
		sb.append("Frame["+(stx-idx)+"]: ");
		while(idx < stx) {
			Value item = RTStack.load(idx++);
			if(! first) sb.append(", "); first = false;
			sb.append((item == null)? null : item);
		}
		sb.append("  Stack["+(RTStack.size()-idx)+"]: ");
		first = true;
		while(idx < RTStack.size()) {
			Value item = RTStack.load(idx++);
			if(! first) sb.append(", "); first = false;
			sb.append(item);
		}
		return sb.toString();
	}

	public void print(String title) {
		String indent = "            ";
		try {
			int idx = rtStackIndex;
			IO.println("    "+ident + ": callStackTop.rtStackIndex=" + idx);
			if(exportSize > 0) {
				for(int i=0;i<exportSize;i++) {
					Value item = RTStack.load(idx);
					IO.println(indent+"EXPORT: " + idx + ": " + item); idx++;
				}
			}
			for(int i=0;i<importSize;i++) {
				Value item = RTStack.load(idx);
				IO.println(indent+"IMPORT: " + idx + ": " + item); idx++;
			}
			IO.println(indent+"RETURN: " + idx + ": " + RTStack.load(idx)); idx++;
			for(int i=0;i<localSize;i++) {
				Value item = RTStack.load(idx);
				IO.println(indent+"LOCAL:  " + idx + ": " + item); idx++;
			}
		} catch(Exception e) {}
	}

	public void dump(String title) {
		CallStackFrame callStackTop = RTStack.callStack_TOP();
		IO.println("==================== " + title + " RTFrame'DUMP ====================");
		IO.println("   ROUTINE: " + curAddr + " callStackTop.rtStackIndex=" + callStackTop.rtStackIndex);
		
		print(title);
		
		IO.println("==================== " + title + " RTFrame' END  ====================");
		if(curAddr != null) curAddr.segment().dump(title);
	}

}
