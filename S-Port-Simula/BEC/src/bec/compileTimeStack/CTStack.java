/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.compileTimeStack;

import java.util.Stack;

import bec.instruction.FETCH;
import bec.util.Global;
import bec.util.NamedStack;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;

/// Compile time Stack.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/compileTimeStack/CTStack.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class CTStack {
	/// Current Compil-time Stack.
	private static NamedStack<CTStackItem> stack = new NamedStack<CTStackItem>("MAIN");
	
	/// Stack of saved SAVE-RESTORE Stacks
	private static Stack<NamedStack<CTStackItem>> saveStack = new Stack<NamedStack<CTStackItem>>();
	
	/// Stack of saved BSEG-ESEG Stacks
	private static Stack<NamedStack<CTStackItem>> bsegStack = new Stack<NamedStack<CTStackItem>>();
	
	/// Returns the Stack ident
	/// @return the Stack ident
	public static String ident() {
		return stack.ident();
	}

	/// Returns the current Stack
	/// @return the current Stack
	public static NamedStack<CTStackItem> current() {
		return stack;
	}
	
	/// Returns copy of the current Stack
	/// @return copy of the current Stack
	public static NamedStack<CTStackItem> copy(String ident) {
		NamedStack<CTStackItem> copy = new NamedStack<CTStackItem>(ident);
		for(CTStackItem item:stack) {
			copy.add(item.copy());
		}
		return copy;
	}

	/// Reestablish the current Stack.
	/// @param saved the saved Stack to be reestablished
	public static void reestablish(NamedStack<CTStackItem> saved) {
		stack = new NamedStack<CTStackItem>(saved.ident());
		for(CTStackItem item:saved) {
			stack.add(item.copy());
		}
	}
	
	/// Returns true if the two specified Stacks are equal to one another.
	///
	/// Two Stacks are considered equal if they contain the same number of elements,
	/// and all corresponding pairs of elements in the two arrays are equal.
	///
	/// @param stack1 one Stack to be tested for equality
	/// @param stack2 the other Stack to be tested for equality
	/// @return true if the two Stacks are equal
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

	/// Utility for the protect_statement.
	///
	/// * remember stack;
	/// * purge stack;
	public static void SAVE(String ident) {
		saveStack.push(stack);
		stack = new NamedStack<CTStackItem>(ident);
	}

	/// Utility for the protect_statement.
	///
	///  * check stack empty;
	///  * reestablish stack remembered at corresponding SAVE;
	public static void RESTORE() {
		stack = saveStack.pop();
	}

	/// Utility for the segment_instruction.
	///
	/// * remember stack;
	/// * purge stack;
	public static void BSEG(String ident) {
		bsegStack.push(stack);
		stack = new NamedStack<CTStackItem>(ident);
	}

	/// Utility for the segment_instruction.
	///
	///  * check stack empty;
	///  * reestablish stack remembered at corresponding BSEG;
	public static void ESEG() {
		checkStackEmpty();
		stack = bsegStack.pop();
	}
	
	/// Returns the Top of the Compile-time stack
	/// @return the Top of the Compile-time stack
	public static CTStackItem TOS() {
		if(stack.size() < 1) {
			stack.dumpStack("CTSTACK UNDERFLOW at TOS");
			Util.IERR("CTSTACK UNDERFLOW at TOS");
		}
		return stack.getLast();
	}
	
	/// Returns the Second of the Compile-time stack
	/// @return the Second of the Compile-time stack
	public static CTStackItem SOS() {
		if(stack.size() < 2) {
			stack.dumpStack("CTSTACK UNDERFLOW at SOS");
			Util.IERR("CTSTACK UNDERFLOW at SOS");
		}
		return stack.get(stack.size()-2);
	}
	
	/// Returns the indexed item of the Compile-time stack
	/// @return the indexed item of the Compile-time stack
	public static CTStackItem getItem(int i) {
		return stack.get(i);
	}
	
	/// Returns the size of the Compile-time stack
	/// @return the size of the Compile-time stack
	public static int size() {
		return stack.size();
	}
	
	/// Push a Compile-time stack item onto the Compile-time stack.
	/// @param item a Compile-time stack item
	public static void push(CTStackItem item) {
		stack.push(item);
	}
	
	public static void pushTempItem(Type type, int count) {
		push(new TempItem(CTStackItem.Mode.VAL, type, count));
	}
	
	public static void pushTempItem(Type type) {
		push(new TempItem(CTStackItem.Mode.VAL, type, 1));
	}
	
//	public static void pushTempREF(Type type, int count) {
//		push(new TempItem(CTStackItem.Mode.REF, type, count));
//	}
	
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

	public static Type checkSosType2(Type t1, Type t2) {
		Type type = SOS().type;
		if(type == t1) ; // OK
		else if(type == t2) ; // OK
		else STKERR("Illegal type of SOS");
		return type;
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
