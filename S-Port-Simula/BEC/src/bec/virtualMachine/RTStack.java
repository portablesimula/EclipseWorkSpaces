package bec.virtualMachine;

import java.util.Stack;

import bec.segment.Segment;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.LongRealValue;
import bec.value.RealValue;
import bec.value.Value;

public abstract class RTStack {
	public record RTStackItem (Value value, String comment) {}
	
	private static Stack<RTStackItem> stack = new Stack<RTStackItem>();
	public static Stack<CallStackFrame> precallStack = new Stack<CallStackFrame>();
	public static Stack<CallStackFrame> callStack = new Stack<CallStackFrame>();
	
	private static Stack<Stack<RTStackItem>> saveStack = new Stack<Stack<RTStackItem>>();
	
	public static void saveStack() {
		System.out.println("RTStack.saveStack:    SAVE-RESTORE " + stack.hashCode() + " " + stack);
		saveStack.push(stack);
		stack = new Stack<RTStackItem>();
	}
	
	public static void restoreStack() {
		stack = saveStack.pop();
		System.out.println("RTStack.restoreStack: SAVE-RESTORE " + stack.hashCode() + " " + stack);
	}

	public static void checkStackEmpty() {
		if(curSize() != 0) {
			CallStackFrame callStackTop = callStack_TOP();
			int frameHeadSize = (callStackTop == null)? 0 : callStackTop.headSize();
			int idx = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
			dumpRTStack("Check RTStack Empty - FAILED: ");
			if(callStackTop != null) callStackTop.dump("Check RTStack Empty - FAILED: ");
			printCallStack("Check RTStack Empty - FAILED: ");
			Util.IERR("Check RTStack Empty - FAILED: size="+size()+"  rtStackIndex=" + idx + "  frameHeadSize=" + frameHeadSize );
		}
	}
	
	public static CallStackFrame callStack_TOP() {
		if(RTStack.callStack.empty()) return null;
		return RTStack.callStack.peek();
	}
	
	private static void printCallStack(String title) {
		System.out.println("CallStack["+callStack.size()+"]: " +title);
		System.out.println("     at "+Global.PSC);
		for(int i=callStack.size()-1;i>=0;i--) {
			CallStackFrame frame = callStack.get(i);
			ProgramAddress curAddr =  frame.curAddr;
			System.out.println("     at "+curAddr);	
			frame.print(33, "");
		}
	}
	
	public static void printCallTrace(String title) {
//		RTStack.dumpRTStack(title);
		System.out.println("CallStack["+callStack.size()+"]: " +title);// + " " + callStack_TOP().ident);
//		System.out.println("     at "+Global.PSC);
		int n = callStack.size()-1;
		for(int i=n;i>=0;i--) {
			CallStackFrame frame = callStack.get(i);
			String ident = (frame.curAddr == null)? "SYSRUT_" + frame.ident : ""+frame.curAddr;
			if(i == n) {
//				System.out.println("     " + kind + " "+ident + frame);
				
			} else {
//				System.out.println("     called from "+ident + frame);
				System.out.println("     called from " + frame.ident);
			}
			if(Global.CALL_TRACE_LEVEL > 1)
				frame.print(11, "");
		}
	}
	
	/**
	 * Return Stack size within the current Frame
	 * @return Stack size within the current Frame
	 */
	public static int curSize() {
		CallStackFrame callStackTop = callStack_TOP();
		if(callStackTop == null) return size();
		int frameHeadSize = callStackTop.headSize();
		int idx = callStackTop.rtStackIndex;
		return size() - (idx + frameHeadSize);
	}
	
	public static int size() {
		return stack.size();
	}
	
	public static RTStackItem load(int index) {
		try { return stack.get(index); } catch(Exception e) { return null; }
	}
	
	public static void store(int index, Value value, String comment) {
		stack.set(index, new RTStackItem(value, comment));
	}

	public static void push(Value value, String comment) {
//		System.out.println("RTStack.push: " + value);
		stack.push(new RTStackItem(value, comment));
//		Util.IERR("");
	}

	public static void pushr(int reg, String comment) {
		Value value = RTRegister.getValue(reg);
		if(value instanceof GeneralAddress gaddr) {
			stack.push(new RTStackItem(gaddr.base, comment));
			stack.push(new RTStackItem(IntegerValue.of(Type.T_INT, gaddr.ofst), comment));
		} else{
			stack.push(new RTStackItem(value, comment));
		}
	}

	// Current Stack;
	//
	// 		IMPORT					idx = last - (nSlotStacked - 1)
	// 		...		nSlotStacked
	// TOP:	IMPORT					idx = last
	//
	// ===>
	//
	// 		EXPORT
	//		...		nExportSlots
	// 		EXPORT
	// 		IMPORT
	// 		...		nSlotStacked
	// TOP:	IMPORT
	//
	public static void addExport(int nSlotStacked, int nExportSlots) {
		int idx = RTStack.size() - nSlotStacked;
//		System.out.println("RTStack.addExport: " + idx + ", nExportSlots="+nExportSlots);
		for(int i=0; i<nExportSlots;i++)
			stack.add(idx, new RTStackItem(null, "EXPORT"));
	}
	
	public static RTStackItem pop() {
		RTStackItem itm = stack.pop();
//		if(stack.size() == 0) RTRegister.clearAllRegs();
		return itm;
//		Util.IERR("");
	}
	
	public static RTStackItem peek() {
		return stack.peek();
//		Util.IERR("");
	}
	
	public static int popInt() {
		IntegerValue ival = (IntegerValue) pop().value();
		return (ival==null)? 0 : ival.value;
	}
	
	public static float popReal() {
		RealValue rval = (RealValue) pop().value();
//		System.out.println("RTStack.popReal: rval="+rval);
		return (rval==null)? 0 : rval.value;
	}
	
	public static double popLongReal() {
		LongRealValue rval = (LongRealValue) pop().value();
//		System.out.println("RTStack.popLongReal: rval="+rval);
		return (rval==null)? 0 : rval.value;
	}
	
	public static GeneralAddress popGADDR() {
//		RTStack.dumpRTStack("RTStack.popGADDRasOADDR:");
//		RTStack.printCallStack("RTStack.popGADDRasOADDR:");
		int ofst = RTStack.popInt();
		ObjectAddress base = (ObjectAddress) RTStack.pop().value();
//		System.out.println("RTStack.popGADDRasOADDR: chradr="+chradr+", ofst="+ofst);
		return new GeneralAddress(base,ofst);
	}
	
	public static ObjectAddress popGADDRasOADDR() {
//		RTStack.dumpRTStack("RTStack.popGADDRasOADDR:");
//		RTStack.printCallStack("RTStack.popGADDRasOADDR:");
		int ofst = RTStack.popInt();
		ObjectAddress chradr = (ObjectAddress) RTStack.pop().value();
//		System.out.println("RTStack.popGADDRasOADDR: chradr="+chradr+", ofst="+ofst);
		if(chradr == null) {
			if(ofst != 0) Util.IERR("");
			return null;
		}
		if(ofst != 0) chradr = chradr.addOffset(ofst);
		return chradr;
	}
	
	public static int frameIndex() {
		CallStackFrame top = RTStack.callStack_TOP();
		int frmx = (top == null)? 0 : top.rtStackIndex;
//		System.out.println("RTStack.popOADDR; frmx="+frmx);
		return frmx;
	}
	
	public static ObjectAddress popOADDR() {
		try {
			ObjectAddress oadr = (ObjectAddress) RTStack.pop().value();
//			System.out.println("RTStack.popOADDR: "+oadr);
			return oadr;
		} catch(Exception e) {
//			System.out.println("RTStack.popOADDR; frmx="+RTStack.frameIndex());
//			Segment.lookup("PSEG_ADHOC00").dump("RTStack.popOADDR; ");
//			Segment.lookup("PSEG_CENT_B_SUB:BODY").dump("RTStack.popOADDR; ");
			Util.IERR(""+e);
			return null;
		}
	}
	
	public static String popString() {
		int nchr = RTStack.popInt();
//		int ofst = RTStack.popInt();
//		ObjectAddress chradr = (ObjectAddress) RTStack.pop().value();
//		ObjectAddress x = chradr.ofset(ofst);
		ObjectAddress oaddr = RTStack.popGADDRasOADDR();
		if(oaddr == null) {
			if(nchr != 0) Util.IERR("");
			return null;
		}
		ObjectAddress x = (ObjectAddress) oaddr.copy();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<nchr;i++) {
			IntegerValue ival = (IntegerValue) x.load(); x.incrOffset();
			char c = (ival==null)? '.' : (char) ival.value;
//			System.out.println("SVM_SYSCALL.edString: c="+c);
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static void listRTStack() {
		String s = "     RTStack ===> ";
		for(RTStackItem item:stack) {
//			s += ("   " + value.getClass().getSimpleName());
			s += ("   " + item.value());
		}
		System.out.println(s);
	}
	
	public static String toLine() {
		StringBuilder sb = new StringBuilder();
		int n = stack.size();
		sb.append("Stack["+n+"]: ");
		boolean first = true;
		for(int i=0;i<n;i++) {
			RTStackItem item = stack.get(i);
			if(! first) sb.append(", "); first = false;
			sb.append(item.value());
		}
		String s = sb.toString();
		while(s.length() < 30) s = s + ' ';
		return s + "      " + RTRegister.toLine();
	}
	
	public static void dumpRTStack(String title) {
//		if(stack.size() == 0) return;
		System.out.println("==== RTStack ================ " + title + " RTStack'DUMP ====================");
//		for(Value value:stack) {
//			System.out.println("   " + value);
//		}
		int n = stack.size();
//		System.out.println("   " + n);
		for(int i=0;i<n;i++) {
//		for(int i=n-1;i>=0;i--) {
			RTStackItem item = stack.get(i);
			String s = "   " + i + ": " + item.value();
			while(s.length() < 30) s += " ";
			System.out.println(s + "  " + item.comment);
		}
		System.out.println("==== RTStack ================ " + title + " RTStack' END  ====================");
	}
}
