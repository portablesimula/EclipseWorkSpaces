package bec.virtualMachine;

import java.util.Stack;

import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.RealValue;
import bec.value.Value;

public abstract class RTStack {
	public record RTStackItem (Value value, String comment) {}
	
//	private static Stack<Value> stack = new Stack<Value>();
	private static Stack<RTStackItem> stack = new Stack<RTStackItem>();
	public static RTFrame curFrame;
	public static SVM_PRECALL PRECALL;

	public static void checkStackEmpty() {
		if(curSize() != 0) {
			int frameHeadSize = (curFrame == null)? 0 : curFrame.headSize();
			int idx = (curFrame == null)? 0 : curFrame.rtStackIndex;
			dumpRTStack("Check RTStack Empty - FAILED: ");
			if(curFrame != null) curFrame.dump("Check RTStack Empty - FAILED: ");
			printCallStack("Check RTStack Empty - FAILED: ");
			Util.IERR("Check RTStack Empty - FAILED: size="+size()+"  rtStackIndex=" + idx + "  frameHeadSize=" + frameHeadSize );
		}
	}
	
	public static void printCallStack(String title) {
		System.out.println("CallStack: " +title);
		System.out.println("     at "+Global.PSC);
		RTFrame frame = curFrame;
		while(frame != null) {
			ProgramAddress ret =  frame.returAddress();
			System.out.println("     at "+ret);
			frame = frame.enclFrame;
		}
	}
	
	/**
	 * Return Stack size within the current Frame
	 * @return Stack size within the current Frame
	 */
	public static int curSize() {
		if(curFrame == null) return size();
		int frameHeadSize = curFrame.headSize();
		int idx = curFrame.rtStackIndex;
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

//	private static void push(Type type, Value value, String comment) {
//		
//		switch(type.tag) {
//		case Scode.TAG_TEXT:
//			Util.IERR("FJERN DETTE !!!");
////			System.out.println("RTStack.push: " + value.getClass().getSimpleName());
//			TextValue text = (TextValue) value;
//			push(Type.T_OADDR, text.addr, comment+"'CHRADR.OADDR");
//			push(Type.T_INT, null,        comment+"'CHRADR.Offset");
//			push(Type.T_INT, new IntegerValue(Type.T_INT, text.getString().length()), comment+"'NCHR");
////			Util.IERR("");
//			return;
////			break;
//		case Scode.TAG_GADDR:
//			Util.IERR("FJERN DETTE !!!");
//			if(value == null) {
//				stack.push(null);
//				stack.push(null);
//			} else {
////				System.out.println("RTStack.push: " + value.getClass().getSimpleName());
////				RepetitionValue repval = (RepetitionValue) value;
////				for(Value val:repval.values) {
////					push(type, value);
////				}
//				GeneralAddress gaddr = (GeneralAddress) value;
//				push(Type.T_OADDR, gaddr.base, comment+"'GADDR'base");
//				push(Type.T_INT, new IntegerValue(Type.T_INT, gaddr.ofst), comment+"'GADDR'ofst");
////				Util.IERR("");
//			}
//		}
////		System.out.println("RTStack.push: " + value);
//		stack.push(new RTStackItem(value, comment));
////		Util.IERR("");
//	}

	public static void pushr(Type type, int reg, String comment) {
		int value = RTRegister.getValue(reg);
//		stack.push(type, value, comment);
		stack.push(new RTStackItem( new IntegerValue(Type.T_INT, value), comment));
//		dumpRTStack("");
//		Util.IERR("");
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
		System.out.println("RTStack.popReal: rval="+rval);
		return (rval==null)? 0 : rval.value;
	}
	
	public static ObjectAddress popGADDR() {
//		RTStack.dumpRTStack("RTStack.popGADDR:");
//		RTStack.printCallStack("RTStack.popGADDR:");
		int ofst = RTStack.popInt();
		ObjectAddress chradr = (ObjectAddress) RTStack.pop().value();
//		System.out.println("RTStack.popGADDR: chradr="+chradr+", ofst="+ofst);
		return chradr.addOffset(ofst);
	}
	
	public static ObjectAddress popOADDR() {
		ObjectAddress oadr = (ObjectAddress) RTStack.pop().value();
//		System.out.println("RTStack.popOADDR: "+oadr);
		return oadr;
	}
	
	public static String popString() {
		int nchr = RTStack.popInt();
//		int ofst = RTStack.popInt();
//		ObjectAddress chradr = (ObjectAddress) RTStack.pop().value();
//		ObjectAddress x = chradr.ofset(ofst);
		ObjectAddress x = RTStack.popGADDR();
		
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
