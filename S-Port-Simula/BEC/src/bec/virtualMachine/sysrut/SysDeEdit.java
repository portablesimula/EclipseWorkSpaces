package bec.virtualMachine.sysrut;

import bec.util.Type;
import bec.value.IntegerValue;
import bec.value.LongRealValue;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.RTUtil;
import bec.virtualMachine.SVM_CALL_SYS;

public abstract class SysDeEdit {

	/// Scan the input text for an integer item.
	/// <pre>
	/// INTEGER-ITEM = SIGN-PART DIGITS
	/// 
	///    SIGN-PART = BLANKS [ SIGN ] BLANKS
	/// 
	///       SIGN = + | -
	/// 
	///       DIGITS = DIGIT { DIGIT }
	/// 
	///       DIGIT = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
	/// </pre>
	private static String getIntegerItem(String T) {
		
		boolean DEBUG = false;
		
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		char c = 0;
		LOOP1:while(pos < T.length()) { // SKIP BLANKS
			c = T.charAt(pos);
			if (c != ' ') break LOOP1;
			pos++;
		}
		if(DEBUG) System.out.println("SysDeEdit.getIntegerItem(1): \""+T+"\"  pos="+pos);
		c = T.charAt(pos);
		if (c == '+' || c == '-') {
			sb.append(c);
			if(DEBUG) System.out.println("SysDeEdit.getIntegerItem(2): \""+T+"\"  res=\""+sb+"\"  pos="+pos);
			pos++;
			LOOP2:while(pos < T.length()) { // SKIP BLANKS
				c = T.charAt(pos);
				if (c != ' ') break LOOP2;
				pos++;
			}
			if(DEBUG) System.out.println("SysDeEdit.getIntegerItem(3): "+T+"  res=\""+sb+"\"  pos="+pos);
		}
		LOOP3:while(pos < T.length()) { // KEEP DIGITS
			c = T.charAt(pos);
			if (!Character.isDigit(c)) break LOOP3;
			sb.append(c);
			pos++;
		}
		if(DEBUG) System.out.println("SysDeEdit.getIntegerItem: \""+T+"\", ITEMSIZE="+(pos)+" ===> \""+sb+'"');
		RTUtil.set_ITEM_SIZE(pos);
		return (sb.toString());
	}

	/// Procedure getint.
	/// <pre>
	/// INTEGER-ITEM = SIGN-PART DIGITS
	/// 
	///    SIGN-PART = BLANKS [ SIGN ] BLANKS
	/// 
	///       SIGN = + | -
	/// 
	///       DIGITS = DIGIT { DIGIT }
	/// 
	///       DIGIT = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
	/// </pre>
	/// The procedure locates an INTEGER ITEM. The function value is
	/// equal to the corresponding integer.
	/// 
	/// Visible sysroutine("GETINT") GETINT;
	/// import infix (string) item; export integer res end;
	public static void getint() {
		SVM_CALL_SYS.ENTER("GETINT: ", 1, 3); // exportSize, importSize
		String arg = RTStack.popString();
		String item = getIntegerItem(arg);
		int res = Integer.parseInt(item);
//		System.out.println("SysEdit.GETINT: item="+item+"  ===>  "+res);
		RTStack.push(IntegerValue.of(Type.T_INT, res), "EXPORT");
		SVM_CALL_SYS.EXIT("GETINT: ");
	}


	/// Scan the input text for a real item.
	/// <pre>
	/// REAL-ITEM = DECIMAL-ITEM [ EXPONENT ] | SIGN-PART EXPONENT
	/// 
	///    DECIMAL-ITEM = INTEGER-ITEM [ FRACTION ] | SIGN-PART FRACTION
	/// 
	///       INTEGER-ITEM = SIGN-PART DIGITS
	/// 
	///       FRACTION = DECIMAL-MARK DIGITS
	/// 
	///       SIGN-PART = BLANKS [ SIGN ] BLANKS
	/// 
	///    EXPONENT = LOWTEN-CHARACTER INTEGER-ITEM
	/// 
	///          SIGN = + | -
	/// 
	///          DIGITS = DIGIT { DIGIT }
	/// 
	///          DIGIT = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
	/// 
	///          LOWTEN-CHARACTER = & | ...
	/// 
	///          DECIMAL-MARK = . | ,
	/// 
	///          BLANKS = { BLANK | TAB }
	/// </pre>
	private static String getRealItem(String T) {
		
		boolean DEBUG = false;
		
		StringBuilder sb = new StringBuilder();
		char c = 0;
		int pos = 0;
		LOOP1:while(pos < T.length()) { // SKIP BLANKS
			c = T.charAt(pos);
			if (c != ' ') break LOOP1;
			pos++;
		}
		if(DEBUG) System.out.println("SysDeEdit.getFracItem(1): \""+T+"\"  pos="+pos);

		if (c == '+' || c == '-') {
			sb.append(c);
			pos++;
			LOOP2:while(pos < T.length()) { // SKIP BLANKS
				c = T.charAt(pos);
				if (c != ' ') break LOOP2;
				pos++;
			}
			if(DEBUG) System.out.println("SysDeEdit.getIntegerItem(3): "+T+"  res=\""+sb+"\"  pos="+pos);
		}
		int lastDigPos = pos;
		LOOP3:while(pos < T.length()) { // KEEP DIGITS
			c = T.charAt(pos);
			if (Character.isDigit(c)) ; // OK
			else if (c == RTUtil.CURRENTDECIMALMARK) c = '.'; // OK NOTE: THIS WAS WRONG IN PC-SIMULA
			else if (c == '+') ; // OK
			else if (c == '-') ; // OK
			else if (c == RTUtil.CURRENTLOWTEN) c = 'E'; // OK
			else { pos = lastDigPos; break LOOP3;}
			sb.append(c);
			lastDigPos = pos;
			pos++;
		}
		if(DEBUG) System.out.println("SysDeEdit.getFracItem: \""+T+"\", ITEMSIZE="+(lastDigPos)+" ===> \""+sb+'"');
		RTUtil.set_ITEM_SIZE(lastDigPos);
		return (sb.toString());
	}

	/// Procedure getreal.
	/// <pre>
	/// REAL-ITEM = DECIMAL-ITEM [ EXPONENT ] | SIGN-PART EXPONENT
	/// 
	///    DECIMAL-ITEM = INTEGER-ITEM [ FRACTION ] | SIGN-PART FRACTION
	/// 
	///       INTEGER-ITEM = SIGN-PART DIGITS
	/// 
	///       FRACTION = DECIMAL-MARK DIGITS
	/// 
	///       SIGN-PART = BLANKS [ SIGN ] BLANKS
	/// 
	///    EXPONENT = LOWTEN-CHARACTER INTEGER-ITEM
	/// 
	///          SIGN = + | -
	/// 
	///          DIGITS = DIGIT { DIGIT }
	/// 
	///          DIGIT = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
	/// 
	///          LOWTEN-CHARACTER = & | ...
	/// 
	///          DECIMAL-MARK = . | ,
	/// 
	///          BLANKS = { BLANK | TAB }
	/// </pre>
	/// The procedure locates a REAL ITEM. The function value is equal
	/// to or approximates to the corresponding number. An INTEGER ITEM
	/// exceeding a certain implementation-defined range may lose
	/// precision when converted to long real.
	/// 
	/// Visible sysroutine("GTREAL") GTREAL;
	/// import infix (string) item; export long real res  end;
	public static void getreal() {
		SVM_CALL_SYS.ENTER("GTREAL: ", 1, 3); // exportSize, importSize
		String arg = RTStack.popString();
		String item = getRealItem(arg);
		double res = Double.parseDouble(item);
//		System.out.println("SysEdit.GTREAL: item="+item+"  ===>  "+res);
		RTStack.push(LongRealValue.of(res), "EXPORT");
		SVM_CALL_SYS.EXIT("GTREAL: ");
	}

	/// Scan the input text for a fraction item.
	/// <pre>
	/// GROUPED-ITEM = SIGN-PART GROUPS [ DECIMAL-MARK GROUPS ]
	///              | SIGN-PART DECIMAL-MARK GROUPS
	/// 
	/// SIGN-PART = BLANKS [ SIGN ] BLANKS
	/// 
	/// SIGN = + | -
	/// 
	/// GROUPS = DIGITS { BLANK DIGITS } DIGITS = DIGIT { DIGIT }
	/// 
	/// DIGIT = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
	/// </pre>
	private static String getFracItem(String T) {
		
		boolean DEBUG = false;
		
		StringBuilder sb = new StringBuilder();
		char c = 0;
		int pos = 0;
		LOOP1:while(pos < T.length()) { // SKIP BLANKS
			c = T.charAt(pos);
			if (c != ' ') break LOOP1;
			pos++;
		}
		if(DEBUG) System.out.println("SysDeEdit.getFracItem(1): \""+T+"\"  pos="+pos);
		
		if (c == '+' || c == '-') {
			sb.append(c);
			if(DEBUG) System.out.println("SysDeEdit.getFracItem(2): \""+T+"\"  res=\""+sb+"\"  pos="+pos);
			pos++;
			LOOP2:while(pos < T.length()) { // SKIP BLANKS
				c = T.charAt(pos);
				if (c != ' ') break LOOP2;
				pos++;
			}
			if(DEBUG) System.out.println("SysDeEdit.getFracItem(3): "+T+"  res=\""+sb+"\"  pos="+pos);
		}
		int lastDigPos = pos;
		LOOP3:while(pos < T.length()) { // KEEP DIGITS
			c = T.charAt(pos);
			if (Character.isDigit(c)) {
				sb.append(c);
				lastDigPos = pos;
			} // OK
			else if (c == RTUtil.CURRENTDECIMALMARK) ; // OK NOTE: THIS WAS WRONG IN PC-SIMULA
			else if (c == ' ') ; // OK
			else break LOOP3;
			pos++;
		}
		if(DEBUG) System.out.println("SysDeEdit.getFracItem: \""+T+"\", ITEMSIZE="+(lastDigPos)+" ===> \""+sb+'"');
		RTUtil.set_ITEM_SIZE(lastDigPos);
		return (sb.toString());
	}

	/// Procedure getfrac.
	/// <pre>
	/// GROUPED-ITEM = SIGN-PART GROUPS [ DECIMAL-MARK GROUPS ]
	///              | SIGN-PART DECIMAL-MARK GROUPS
	/// 
	/// SIGN-PART = BLANKS [ SIGN ] BLANKS
	/// 
	/// SIGN = + | -
	/// 
	/// GROUPS = DIGITS { BLANK DIGITS } DIGITS = DIGIT { DIGIT }
	/// 
	/// DIGIT = 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
	/// </pre>
	/// The procedure locates a GROUPED ITEM. The function value is
	/// equal to the resulting integer. The digits of a GROUPED ITEM
	/// may be interspersed with BLANKS and a single DECIMAL MARK which
	/// are ignored by the procedure.
	/// 
	/// Visible sysroutine("GTFRAC") GTFRAC;
	/// import infix (string) item; export integer res  end;
	public static void getfrac() {
		SVM_CALL_SYS.ENTER("GTFRAC: ", 1, 3); // exportSize, importSize
		String arg = RTStack.popString();
		String item = getFracItem(arg);
		int res = Integer.parseInt(item);
//		System.out.println("SysEdit.GTFRAC: item="+item+"  ===>  "+res);
		RTStack.push(IntegerValue.of(Type.T_INT, res), "EXPORT");
		SVM_CALL_SYS.EXIT("GTFRAC: ");
	}

}
