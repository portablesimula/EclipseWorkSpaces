/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm;

import svm.value.ProgramAddress;
import svm.value.Value;

/// CallStackFrame.
/// 
///	FRAME:
///		EXPORT ?
///		IMPORT
///		...
///		IMPORT
///		RETURN ADDRESS
///		LOCAL
///		...
///		LOCAL
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/compileTimeStack/AddressItem.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class CallStackFrame {
	
	/// Routine ident
	public String ident;
	
	/// The Runtime Stack index
	public int rtStackIndex;
	public int exportSize;
	public int importSize;
	public ProgramAddress curAddr;
	public int localSize;
	
	public CallStackFrame(final String ident, int rtStackIndex, int exportSize, int importSize) {
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
		return (ProgramAddress) RTStack.load(idx);
	}
	
	public ProgramAddress callAddress() {
		ProgramAddress addr = returnAddress();
		addr.ofst--;
		return addr;
	}

	@Override
	public String toString() {
		return ident + ": rtStackIndex=" + rtStackIndex + ", exportSize=" + exportSize + ", importSize=" + importSize + ", localSize=" + localSize;
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
