package bec.compileTimeStack;

import java.util.Stack;

import bec.instruction.FETCH;
import bec.util.Global;
import bec.util.NamedStack;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;

public class CTStack {
//	private static Stack<CTStackItem> stack = new Stack<CTStackItem>();
	private static NamedStack<CTStackItem> stack = new NamedStack<CTStackItem>("MAIN");
	
//	private static Stack<Stack<CTStackItem>> saveStack = new Stack<Stack<CTStackItem>>();
	private static Stack<NamedStack<CTStackItem>> saveStack = new Stack<NamedStack<CTStackItem>>();
	private static Stack<NamedStack<CTStackItem>> bsegStack = new Stack<NamedStack<CTStackItem>>();

//	private static final boolean DEBUG = false;
	
	public static String ident() {
		return stack.ident();
	}

	public static NamedStack<CTStackItem> current() {
		return stack;
	}
	
//	@SuppressWarnings("unchecked")
//	public static NamedStack<CTStackItem> getClone() {
////		IO.println("CTStack.get: "+stack.ident());
//		return (NamedStack<CTStackItem>) stack.clone();
//	}
	public static NamedStack<CTStackItem> copy(String ident) {
//		IO.println("CTStack.get: "+stack.ident());
		NamedStack<CTStackItem> copy = new NamedStack<CTStackItem>(ident);
		for(CTStackItem item:stack) {
			copy.add(item.copy());
		}
//		if(stack.size() > 1) {
//			stack.dumpStack("THIS STACK");
//			stack.dumpStack("COPY STACK");
////			Util.IERR("");
//		}
		return copy;
	}

	public static void reestablish(NamedStack<CTStackItem> saved) {
//		stack = saved;
//		stack = saved.getCopy();
		stack = new NamedStack<CTStackItem>(saved.ident());
		for(CTStackItem item:saved) {
			stack.add(item.copy());
		}
		
//		IO.println("CTStack.reestablish: "+stack.ident());
//		stack.dumpStack("RESTORE: ");
	}
	
	public static boolean equals(NamedStack<CTStackItem> stack1, NamedStack<CTStackItem> stack2) {
//		IO.println("\nCTStack.equals: "+stack1.ident()+"  "+stack2.ident());
//		stack1.dumpStack("CTStack.equals: STACK-1");
//		stack2.dumpStack("CTStack.equals: STACK-2");
		if(stack1.size() != stack2.size()) return false;
		for(int i=0;i<stack1.size();i++) {
			CTStackItem itm1 = stack1.get(i);
			CTStackItem itm2 = stack2.get(i);
			if(itm1.type == null) {
				if(itm2.type != null) return false;				
			} else {
				if(itm1.type.tag != itm2.type.tag) return false;
			}
		}
		return true;
	}

	/// * remember stack;
	/// * purge stack;
	public static void SAVE(String ident) {
//		IO.println("CTStack.SAVE: STACK="+stack.ident());
		saveStack.push(stack);
		stack = new NamedStack<CTStackItem>(ident);
	}

	public static void RESTORE() {
		stack = saveStack.pop();
//		IO.println("CTStack.RESTORE: STACK="+stack.ident());
//		stack.dumpStack("RESTORE: ");
	}

	/// * remember stack;
	/// * purge stack;
	public static void BSEG(String ident) {
//		IO.println("CTStack.BSEG: STACK="+stack.ident());
		bsegStack.push(stack);
		stack = new NamedStack<CTStackItem>(ident);
	}

	public static void ESEG() {
		stack = bsegStack.pop();
//		IO.println("CTStack.ESEG: STACK="+stack.ident());
//		stack.dumpStack("RESTORE: ");
	}
	
	public static CTStackItem TOS() { // Top of Compile-time stack
		if(stack.size() < 1) {
			stack.dumpStack("CTSTACK UNDERFLOW at TOS");
			Util.IERR("CTSTACK UNDERFLOW at SOS");
		}
		return stack.getLast();
	}
	
	public static CTStackItem SOS() { // Second of Compile-time stack
		if(stack.size() < 2) {
			stack.dumpStack("CTSTACK UNDERFLOW at SOS");
			Util.IERR("CTSTACK UNDERFLOW at SOS");
		}
		return stack.get(stack.size()-2);
	}
	
	public static CTStackItem getItem(int i) {
		return stack.get(i);
	}
	
	public static int size() {
		return stack.size();
	}
	
	public static void push(CTStackItem s) {
//		IO.println("CTStack.push: " + s.edMode() + " " + s+"  STACK="+stack.ident());
		stack.push(s);
	}
	
	public static void pushTempVAL(Type type, int count, String comment) {
//		IO.println("CTStack.pushTempVAL: " + type + " " + count + " " + comment+"  STACK="+stack.ident);
		push(new TempItem(CTStackItem.Mode.VAL, type, count, comment));
	}
	
	public static void pushTempREF(Type type, int count, String comment) {
//		IO.println("CTStack.pushTempREF: " + type + " " + count + " " + comment);
		push(new TempItem(CTStackItem.Mode.REF, type, count, comment));
	}
	
	public static void pushCoonst(Type type, Value value) {
		push(new ConstItem(type, value));
	}
	
	public static CTStackItem pop() {
//		if(stack.size() == 1) RTRegister.clearFreeRegs();
		return stack.pop();
	}
	
	public static void dup() {
//		IO.println("CTStack.dup: TOS=" + TOS());
		stack.push(TOS().copy());
	}

//	public static void main(String[] argv) {
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 111) ));
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 222) ));
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 333) ));
//		CTStack.push(new ConstItem(Type.T_INT, IntegerValue.of(Type.T_INT, 444) ));
//		CTStack.dumpStack("TEST-1");
//		IO.println("TOS = " + CTStack.TOS());
//		IO.println("SOS = " + CTStack.SOS());
//		IO.println("Size = " + CTStack.size());
//		CTStack.pop();
//		CTStack.dumpStack("TEST-2");
//		IO.println("TOS = " + CTStack.TOS());
//		IO.println("SOS = " + CTStack.SOS());
//		IO.println("Size = " + CTStack.size());
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
		IO.println("\nERROR: " + msg + " ================================================");
		CTStack.dumpStack("STKERR: ");
		Global.PSEG.dump("STKERR: ");
		Util.IERR("FORCED EXIT: " + msg);
	}

	public static void forceTosValue() {
		FETCH.GQfetch("forceTosValue");
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
//		IO.println("CTStack.checkTosArith: " + xTOS.type);
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
		if(stack.size() != 0) {
			STKERR("Stack should be empty");
			stack = new NamedStack<CTStackItem>("ERR");
		}
	}

	public static void dumpStack(String title) {
		stack.dumpStack(0, title);
	}

	public static void dumpStack(int level, String title) {
		stack.dumpStack(level, title);
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
