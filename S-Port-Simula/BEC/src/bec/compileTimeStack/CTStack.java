package bec.compileTimeStack;

import java.util.Stack;

import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;

public class CTStack {
	private static Stack<CTStackItem> stack = new Stack<CTStackItem>();
	private static Stack<Stack<CTStackItem>> saveStack = new Stack<Stack<CTStackItem>>();
	
//	private static final boolean DEBUG = false;
	
	@SuppressWarnings("unchecked")
	public static void saveState() {
		saveStack.push((Stack<CTStackItem>) stack.clone());
	}

	public static void saveAndPurge() {
		saveStack.push(stack);
		stack = new Stack<CTStackItem>();
	}

	public static void restoreState() {
		stack = saveStack.pop();
	}
	
	public static CTStackItem TOS() { // Top of Compile-time stack
		return stack.getLast();
	}
	
	public static CTStackItem SOS() { // Second of Compile-time stack
		return stack.get(stack.size()-2);
	}
	
	public static CTStackItem getItem(int i) {
		return stack.get(i);
	}
	
	public static int size() {
		return stack.size();
	}
	
	public static void push(CTStackItem s) {
//		System.out.println("CTStack.push: " + s.edMode() + " " + s);
		stack.push(s);
	}
	
	public static void pushTempVAL(Type type, int count, String comment) {
//		System.out.println("CTStack.pushTempVAL: " + type + " " + count + " " + comment);
		push(new Temp(CTStackItem.Mode.VAL, type, count, comment));
	}
	
	public static void pushTempREF(Type type, int count, String comment) {
//		System.out.println("CTStack.pushTempREF: " + type + " " + count + " " + comment);
		push(new Temp(CTStackItem.Mode.REF, type, count, comment));
	}
	
	public static void pushCoonst(Type type, Value value) {
		push(new ConstItem(type, value));
	}
	
	public static CTStackItem pop() {
		return stack.pop();
	}
	
	public static void dup() {
//		System.out.println("CTStack.dup: TOS=" + TOS());
		stack.push(TOS().copy());
	}

//	public static void main(String[] argv) {
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 111) ));
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 222) ));
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 333) ));
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 444) ));
//		CTStack.dumpStack("TEST-1");
//		System.out.println("TOS = " + CTStack.TOS());
//		System.out.println("SOS = " + CTStack.SOS());
//		System.out.println("Size = " + CTStack.size());
//		CTStack.pop();
//		CTStack.dumpStack("TEST-2");
//		System.out.println("TOS = " + CTStack.TOS());
//		System.out.println("SOS = " + CTStack.SOS());
//		System.out.println("Size = " + CTStack.size());
//		
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 555) ));
//		CTStack.saveState();
//		CTStack.dumpStack("TEST-3");
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 666) ));
//		CTStack.dumpStack("TEST-4");
//		CTStack.restoreState();
//		CTStack.dumpStack("TEST-5");
//	}
	
	private static void STKERR(String msg) {
		System.out.println("\nERROR: " + msg + " ================================================");
		CTStack.dumpStack("STKERR: ");
		Global.PSEG.dump("STKERR: ");
		Util.IERR("FORCED EXIT: " + msg);
	}

	public static void checkTosRef() {
		checkRef(TOS());
	}

	public static void checkSosRef() {
		checkRef(SOS());	
	}

	private static void checkRef(CTStackItem s) {
		if(! (s instanceof AddressItem)) STKERR("CheckRef fails: " + s);
	}

	public static void checkSosValue() {
		if(SOS() instanceof AddressItem) STKERR("CheckSosValue fails");
	}

	public static void checkTosType(Type t) {
		if(TOS().type != t) STKERR("Illegal type of TOS: " + TOS().type + " expected: " + t);
	}

	public static void checkSosType(Type t) {
		if(SOS().type != t) STKERR("Illegal type of TOS");
	}

	public static void checkTosInt() {
		switch(TOS().type.tag) {
			case Scode.TAG_INT, Scode.TAG_SINT: break; 
			default: STKERR("Illegal type of TOS");
		}
	}

	public static void checkTosArith() {
//		System.out.println("CTStack.checkTosArith: " + xTOS.type);
		switch(TOS().type.tag) {
			case Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL: break; 
			default: STKERR("Illegal type of TOS");
		}
	}

	public static void checkSosInt() {
		switch(SOS().type.tag) {
			case Scode.TAG_INT, Scode.TAG_SINT: break; 
			default: STKERR("Illegal type of xTOS");
		}
	}

	public static void checkSosArith() {
		Type type = SOS().type;
		switch(type.tag) {
			case Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL: break; 
			default: STKERR("Illegal type of SOS: " + type);
		}
	}

	public static void checkSosType2(Type t1, Type t2) {
		if(SOS().type == t1) ; // OK
		else if(SOS().type == t2) ; // OK
		else STKERR("Illegal type of SOS");
	}

	public static void checkTypesEqual() {
		Type t1 = TOS().type;
		Type t2 = SOS().type;
		if(t1 == t2) return;
//		if(t1 > Scode.TAG_SIZE) STKERR("CODER.CheckTypesEqual-1: " + Scode.edTag(t1));
//		if(t2 > Scode.TAG_SIZE) STKERR("CODER.CheckTypesEqual-2: " + Scode.edTag(t2));
		t1 = arithType(t1,t1); t2 = arithType(t2,t2);
		if(t1 == t2) return;
		if(arithType(t1,t2).tag == Type.T_INT.tag) return;
//	%+C       if (t1>T_BYT1) or (t2>T_BYT1)
		Type.dumpTypes("checkTypesEqual: ");
		STKERR("Different types of TOS=" + t1 + " and SOS=" + t2);
	}

	public static void checkStackEmpty() {
		if(stack.size() != 0) STKERR("Stack should be empty");
		stack = new Stack<CTStackItem>();
	}

	public static void dumpStack(String title) {
		String lead = title + ": Current Stack";
		if(stack.empty()) {
			System.out.println(lead + ": **Empty**");				
		} else {
			System.out.println(lead);
			lead ="  TOS: ";
			for(int i=stack.size()-1;i>=0;i--) {
				CTStackItem item = stack.get(i);
				String mode = item.edMode();
//				if(item instanceof ProfileItem) System.out.println(lead+"PROFILE:  " + item);
//				else if(item instanceof AddressItem) System.out.println(lead+"REF:      " + item);
//				else System.out.println(lead+"VAL:      " + item.getClass().getSimpleName() + "  " + item);
				System.out.println(lead + mode + item);
				lead ="       ";					
			}
		}
	}

	public static Type arithType(Type t1, Type t2) { // export range(0:MaxType) ct;
		switch(t1.tag) {
		      case Scode.TAG_LREAL:
		    	  switch(t2.tag) {
			    	  case Scode.TAG_LREAL: return Type.T_LREAL;
			    	  case Scode.TAG_REAL:  return Type.T_LREAL;
			    	  case Scode.TAG_INT:   return Type.T_LREAL;
			    	  case Scode.TAG_SINT:  return Type.T_LREAL;
			    	  default: return t1;
		    	  }
		      case Scode.TAG_REAL:
		    	  switch(t2.tag) {
			    	  case Scode.TAG_LREAL: return Type.T_LREAL;
			    	  case Scode.TAG_REAL:  return Type.T_REAL;
			    	  case Scode.TAG_INT:   return Type.T_REAL;
			    	  case Scode.TAG_SINT:  return Type.T_REAL;
			    	  default: return t1;
		    	  }
		      case Scode.TAG_INT:
		    	  switch(t2.tag) {
			    	  case Scode.TAG_LREAL: return Type.T_LREAL;
			    	  case Scode.TAG_REAL:  return Type.T_REAL;
			    	  case Scode.TAG_INT:   return Type.T_INT;
			    	  case Scode.TAG_SINT:  return Type.T_INT;
			    	  default: return t1;
		    	  }
		      case Scode.TAG_SINT:
		    	  switch(t2.tag) {
			    	  case Scode.TAG_LREAL: return Type.T_LREAL;
			    	  case Scode.TAG_REAL:  return Type.T_REAL;
			    	  case Scode.TAG_INT:   return Type.T_INT;
			    	  case Scode.TAG_SINT:  return Type.T_SINT;
			    	  default: return t1;
		    	  }
		      default: return t1;
		}
	}

}
