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
	private static NamedStack<CTStackItem> stack = new NamedStack<CTStackItem>("MAIN");
	private static Stack<NamedStack<CTStackItem>> saveStack = new Stack<NamedStack<CTStackItem>>();
	private static Stack<NamedStack<CTStackItem>> bsegStack = new Stack<NamedStack<CTStackItem>>();
	
	public static String ident() {
		return stack.ident();
	}

	public static NamedStack<CTStackItem> current() {
		return stack;
	}
	
	public static NamedStack<CTStackItem> copy(String ident) {
		NamedStack<CTStackItem> copy = new NamedStack<CTStackItem>(ident);
		for(CTStackItem item:stack) {
			copy.add(item.copy());
		}
		return copy;
	}

	public static void reestablish(NamedStack<CTStackItem> saved) {
		stack = new NamedStack<CTStackItem>(saved.ident());
		for(CTStackItem item:saved) {
			stack.add(item.copy());
		}
	}
	
	public static boolean equals(NamedStack<CTStackItem> stack1, NamedStack<CTStackItem> stack2) {
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
		saveStack.push(stack);
		stack = new NamedStack<CTStackItem>(ident);
	}

	public static void RESTORE() {
		stack = saveStack.pop();
	}

	/// * remember stack;
	/// * purge stack;
	public static void BSEG(String ident) {
		bsegStack.push(stack);
		stack = new NamedStack<CTStackItem>(ident);
	}

	public static void ESEG() {
		stack = bsegStack.pop();
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
		stack.push(s);
	}
	
	public static void pushTempVAL(Type type, int count) {
		push(new Temp(CTStackItem.Mode.VAL, type, count));
	}
	
	public static void pushTempREF(Type type, int count) {
		push(new Temp(CTStackItem.Mode.REF, type, count));
	}
	
	public static void pushCoonst(Type type, Value value) {
		push(new ConstItem(type, value));
	}
	
	public static CTStackItem pop() {
		return stack.pop();
	}
	
	public static void dup() {
		stack.push(TOS().copy());
	}
	
	private static void STKERR(String msg) {
		IO.println("\nERROR: " + msg + " ================================================");
		CTStack.dumpStack("STKERR: ");
		Global.PSEG.dump("STKERR: ");
		Util.IERR("FORCED EXIT: " + msg);
	}

	public static void forceTosValue() {
		FETCH.doFetch();
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
		t1 = arithType(t1,t1); t2 = arithType(t2,t2);
		if(t1 == t2) return;
		if(arithType(t1,t2).tag == Type.T_INT.tag) return;
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
