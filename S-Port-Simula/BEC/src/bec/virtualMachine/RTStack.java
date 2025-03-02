package bec.virtualMachine;

import java.util.Stack;

import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.TextValue;
import bec.value.Value;

public abstract class RTStack {
	public record RTStackItem (Value value, String comment) {}
	
//	private static Stack<Value> stack = new Stack<Value>();
	private static Stack<RTStackItem> stack = new Stack<RTStackItem>();
	public static RTFrame curFrame;
	public static SVM_PRECALL PRECALL;

	public static void checkStackEmpty() {
		int frameHeadSize = curFrame.headSize();
		int idx = (curFrame == null)? 0 : curFrame.rtStackIndex;
//		System.out.println("RTStack.checkStackEmpty: size="+size()+"  rtStackIndex=" + idx + "  frameHeadSize=" + frameHeadSize );
		if((idx + frameHeadSize) != size()) {
			dumpRTStack("Check RTStack Empty - FAILED: ");
			curFrame.dump("Check RTStack Empty - FAILED: ");
			Util.IERR("Check RTStack Empty - FAILED: ");
		}
	}
	
	public static int size() {
		return stack.size();
	}
	
	public static RTStackItem load(int index) {
		return stack.get(index);
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
		Value value = RTRegister.getValue(reg);
//		stack.push(type, value, comment);
		stack.push(new RTStackItem(value, comment));
//		dumpRTStack("");
//		Util.IERR("");
	}
	
	public static RTStackItem pop() {
		return stack.pop();
//		Util.IERR("");
	}
	
	public static int popInt() {
		IntegerValue ival = (IntegerValue) pop().value();
		return (ival==null)? 0 : ival.value;
	}
	
	public static String popString() {
		int nchr = RTStack.popInt();
		int ofst = RTStack.popInt();
		ObjectAddress chradr = (ObjectAddress) RTStack.pop().value();
		
		ObjectAddress x = chradr.ofset(ofst);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<nchr;i++) {
			IntegerValue ival = (IntegerValue) x.load(); x.ofst++;
			char c = (char) ival.value;
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
