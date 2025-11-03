/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/

package svm.env;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Stack;

import bec.scode.Type;
import bec.util.Util;
import svm.RTStack;
import svm.RTUtil;
import svm.instruction.SVM_CALL_SYS;
import svm.value.GeneralAddress;
import svm.value.IntegerValue;
import svm.value.ObjectAddress;
import svm.value.ProgramAddress;

public abstract class SysEdit {

	/// See: https://en.wikipedia.org/wiki/Plus_and_minus_signs
	private static final char UNICODE_MINUS_SIGN = 0x2212;

	/**
	 * Visible sysroutine("PUTSTR") PUTSTR;
	 *		import infix (string) item; infix(string) val;
	 *		export integer lng;
	 * end;
	 */
	public static void putstr() {
		boolean DEBUG = false;
		SVM_CALL_SYS.ENTER("PUTSTR: ", 1, 6); // exportSize, importSize
		int valNchr = RTStack.popInt();
		ObjectAddress valAddr = RTStack.popGADDRasOADDR();
		if(DEBUG) {
			valAddr.segment().dump("SVM_SYSCALL.putstr:");
			IO.println("SVM_SYSCALL.putstr: valAddr="+valAddr);
			IO.println("SVM_SYSCALL.putstr: valNchr="+valNchr);
		}
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		if(DEBUG) {
//			itemAddr.segment().dump("SVM_SYSCALL.putstr:");
			IO.println("SVM_SYSCALL.putstr: itemAddr="+itemAddr);
			IO.println("SVM_SYSCALL.putstr: itemNchr="+itemNchr);
		}
		if(valNchr > 0) RTUtil.move(valAddr, itemAddr, valNchr);

//		itemAddr.segment().dump("SVM_SYSCALL.putstr:",54,70);
//		RTUtil.printBasicIO();

		RTStack.push(IntegerValue.of(Type.T_INT, valNchr));
		SVM_CALL_SYS.EXIT("PUTSTR: ");
	}
	
	/**
	 *  Visible sysroutine("PUTINT") PUTINT;
	 *      import infix (string) item; integer val
	 *  end;
	 */
	public static void putint() {
		SVM_CALL_SYS.ENTER("PUTINT: ", 0, 4); // exportSize, importSize
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) {
			RTUtil.set_STATUS(24); // Text string too short
		} else {
			int diff = itemNchr - nchr;
			RTUtil.move(sval, itemAddr.addOffset(diff));
//			IO.println("SysEdit.PUTINT: diff="+diff);
			IntegerValue blnk = IntegerValue.of(Type.T_CHAR, ' ');
			for(int i=diff-1;i>=0;i--) itemAddr.store(i, blnk);
		}
		SVM_CALL_SYS.EXIT("PUTINT: ");
	}
	
	/**
	 *  Visible sysroutine("PUTINT2") PUTINT2;
	 *      import infix (string) item; integer val
	 *      export integer lng;
	 *  end;
	 */
	public static void putint2() {
		SVM_CALL_SYS.ENTER("PUTINT2: ", 1, 4); // exportSize, importSize
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PUTINT2: ");
	}
	
	/**
	 *  Visible sysroutine("PTFRAC") PTFRAC;
	 *      import infix (string) item; integer val, n
	 *  end;
	 */
	public static void putfrac() {
		SVM_CALL_SYS.ENTER("PTFRAC: ", 0, 4); // exportSize, importSize
		int n = RTStack.popInt();
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
//		String sval = ""+val;
		String sval = putfrac(val, n);
//		IO.println("SysEdit.putfrac: "+val+", n="+n +" ===> "+sval);
		
		int nchr = sval.length();
//		IO.println("SysEdit.PTFRAC: itemAddr="+itemAddr);
//		IO.println("SysEdit.PTFRAC: itemNchr="+itemNchr);
//		IO.println("SysEdit.PTFRAC: nchr="+nchr);
		if(nchr > itemNchr) {
			RTUtil.set_STATUS(24); // Text string too short
		} else {
			int diff = itemNchr - nchr;
			RTUtil.move(sval, itemAddr.addOffset(diff));
//			IO.println("SysEdit.PTFRAC: diff="+diff);
			IntegerValue blnk = IntegerValue.of(Type.T_CHAR, ' ');
			for(int i=diff-1;i>=0;i--) itemAddr.store(i, blnk);
		}
		SVM_CALL_SYS.EXIT("PTFRAC: ");
	}


	/// Procedure putfrac.
	/// 
	/// The resulting numeric item is a GROUPED ITEM with no DECIMAL MARK if n<=0,
	/// and with a DECIMAL MARK followed by total of n digits if n>0. Each digit
	/// group consists of 3 digits, except possibly the first one, and possibly the
	/// last one following a DECIMAL MARK. The numeric item is an exact
	/// representation of the number i * 10**(-n).
	/// 
	/// @param val an integer value
	/// @param n number of digits after a decimal mark
//	public static void putfrac(final RTS_TXT T, final int val, final int n) {
	private static String putfrac(final int val, final int n) {
		int v; // Scaled value (abs)
		int d; // Number of digits written
		int r; // Remaining digits in current group
		int p; // Next available position in item
		int c; // Current digit (numerical)
//		char[] item = new char[T.LENGTH];
//		char[] item = new char[lng];
		char[] item = new char[100];
		Stack<Character> chars = new Stack<Character>();

		if (n <= 0)
			r = 3;
		else {
			r = n % 3;
			if (r == 0)
				r = 3;
		}

		v = Math.abs(val);
		d = 0;
		p = item.length - 1;
		try {
			while ((v > 0) || (d < n)) {
				c = v % 10;
				v = v / 10;
				if (r == 0) {
					r = 3;
					if (d != n) {
						item[p--] = ' ';
						chars.add(' ');
					}
				}
//				IO.println("SysEdit.putfrac: item["+p+"] <== "+(char) (c + '0'));
				item[p--] = (char) (c + '0');
				chars.add((char) (c + '0'));
				r = r - 1;
				d = d + 1;
				if (d == n) {
					item[p--] = (char) RTUtil.CURRENTDECIMALMARK;
					chars.add((char) RTUtil.CURRENTDECIMALMARK);
				}
			}
			if (val < 0) {
				item[p--] = '-';
				chars.add('-');
			}
			while (p >= 0) {
				item[p--] = ' ';
				chars.add(' ');
			}
		} catch (ArrayIndexOutOfBoundsException e) {
//			RTS_UTIL.numberOfEditOverflows++;
			for (int i = 0; i < item.length; i++)
				item[i] = '*';
		}
		String res = "";
		for (int i = 0; i < item.length; i++) {
//			T.OBJ.MAIN[T.START + i] = item[i];
			res = res + item[i];
		}
//		IO.println("SysEdit.putfrac: res="+res);

		String res2 = "";
		for (Character cc:chars) {
			res2 = res2 + cc;
		}
//		IO.println("SysEdit.putfrac: res2="+res2);

		res = res.trim();
		return res;
	}


	
	/**
	 *  Visible sysroutine("PTREAL") PTREAL;
	 *      import infix (string) item; real val; integer frac; 
	 *  end;
	 */
	public static void putreal() {
		SVM_CALL_SYS.ENTER("PTREAL: ", 0, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		float val = RTStack.popReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
//		IO.println("SysEdit.putreal: "+val);
		
		String sval = SysEdit.putreal(val,frac);
		sval = sval.replace(',', '.').replace('E', '&');
		int nchr = sval.length();
		if(nchr > itemNchr) {
			RTUtil.set_STATUS(24); // Text string too short
		} else {
			int diff = itemNchr - nchr;
			RTUtil.move(sval, itemAddr.addOffset(diff));
			IntegerValue blnk = IntegerValue.of(Type.T_CHAR, ' ');
			for(int i=diff-1;i>=0;i--) itemAddr.store(i, blnk);
		}
		SVM_CALL_SYS.EXIT("PTREAL: ");
		
	}

	/**
	 *  Visible sysroutine("PTREAL2") PTREAL2;
	 *      import infix (string) item; real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	public static void putreal2() {
		SVM_CALL_SYS.ENTER("PTREAL2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		float val = RTStack.popReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putreal(val,frac);
		sval = sval.replace(',', '.').replace('E', '&');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PTREAL2: ");
	}
	
	/**
	 *  Visible sysroutine("PLREAL") PLREAL;
	 *      import infix (string) item; long real val; integer frac; 
	 *  end;
	 */
	public static void putlreal() {
		SVM_CALL_SYS.ENTER("PLREAL2: ", 0, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		double val = RTStack.popLongReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putreal(val,frac);
		sval = sval.replace(',', '.').replace('E', '&');
		int nchr = sval.length();
		if(nchr > itemNchr) {
			RTUtil.set_STATUS(24); // Text string too short
		} else {
			int diff = itemNchr - nchr;
			RTUtil.move(sval, itemAddr.addOffset(diff));
			IntegerValue blnk = IntegerValue.of(Type.T_CHAR, ' ');
			for(int i=diff-1;i>=0;i--) itemAddr.store(i, blnk);
		}
		SVM_CALL_SYS.EXIT("PLREAL2: ");
	}
	
	/**
	 *  Visible sysroutine("PLREAL2") PLREAL2;
	 *      import infix (string) item; long real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	public static void putlreal2() {
		SVM_CALL_SYS.ENTER("PLREAL2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		double val = RTStack.popLongReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putreal(val,frac);
		sval = sval.replace(',', '.').replace('E', '&');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PLREAL2: ");
	}
	
	/**
	 *  Visible sysroutine("PUTFIX") PUTFIX;
	 *      import infix (string) item; real val; integer frac; 
	 *  end;
	 */
	public static void putfix() {
		SVM_CALL_SYS.ENTER("PUTFIX: ", 0, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		float val = RTStack.popReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putfix(val,frac);
		sval = sval.replace(',', '.');
		int nchr = sval.length();
		if(nchr > itemNchr) {
			RTUtil.set_STATUS(24); // Text string too short
		} else {
			int diff = itemNchr - nchr;
			RTUtil.move(sval, itemAddr.addOffset(diff));
			IntegerValue blnk = IntegerValue.of(Type.T_CHAR, ' ');
			for(int i=diff-1;i>=0;i--) itemAddr.store(i, blnk);
		}
		SVM_CALL_SYS.EXIT("PUTFIX: ");
	}
	
	/**
	 *  Visible sysroutine("PUTFIX2") PUTFIX2;
	 *      import infix (string) item; real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	public static void putfix2() {
		SVM_CALL_SYS.ENTER("PUTFIX2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		float val = RTStack.popReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putfix(val,frac);
		sval = sval.replace(',', '.');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PUTFIX2: ");
	}
	
	/**
	 *  Visible sysroutine("PTLFIX") PTLFIX;
	 *      import infix (string) item; long real val; integer frac; 
	 *  end;
	 */
	public static void putlfix() {
		SVM_CALL_SYS.ENTER("PUTFIX: ", 0, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		double val = RTStack.popLongReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putfix(val,frac);
		sval = sval.replace(',', '.');
		int nchr = sval.length();
		if(nchr > itemNchr) {
			RTUtil.set_STATUS(24); // Text string too short
		} else {
			int diff = itemNchr - nchr;
			RTUtil.move(sval, itemAddr.addOffset(diff));
			IntegerValue blnk = IntegerValue.of(Type.T_CHAR, ' ');
			for(int i=diff-1;i>=0;i--) itemAddr.store(i, blnk);
		}
		SVM_CALL_SYS.EXIT("PUTFIX: ");
	}
	
	/**
	 *  Visible sysroutine("PTLFIX2") PTLFIX2;
	 *      import infix (string) item; long real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	public static void putlfix2() {
		SVM_CALL_SYS.ENTER("PUTFIX2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		double val = RTStack.popLongReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putfix(val,frac);
		sval = sval.replace(',', '.');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PUTFIX2: ");
	}
	
	/**
	 *  Visible sysroutine("PUTHEX") PUTHEX;
	 *      import infix (string) item; integer val
	 *      export integer lng;
	 *  end;
	 */
	public static void puthex() {
		SVM_CALL_SYS.ENTER("PUTHEX: ", 1, 4); // exportSize, importSize
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = "0x" + Integer.toHexString(val).toUpperCase();
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PUTHEX: ");
	}
	
	/**
	 *  Visible sysroutine("PUTSIZE") PUTSIZE;
	 *      import infix (string) item; size val
	 *      export integer lng;
	 *  end;
	 */
	public static void putsize() {
		SVM_CALL_SYS.ENTER("PUTSIZE: ", 1, 4); // exportSize, importSize
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PUTSIZE: ");
	}
	
	/**
	 *  Visible sysroutine("PTOADR2") PTOADR2;
	 *      import infix (string) item; ref() val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptoadr2() {
		SVM_CALL_SYS.ENTER("PTOADR2: ", 1, 4); // exportSize, importSize
		ObjectAddress val = RTStack.popOADDR();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NONE" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PTOADR2: ");
	}
	
	/**
	 *  Visible sysroutine("PTPADR2") PTPADR2;
	 *      import infix (string) item; label val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptpadr2() {
		SVM_CALL_SYS.ENTER("PTPADR2: ", 1, 4); // exportSize, importSize
		ProgramAddress val = (ProgramAddress) RTStack.pop();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NOWHERE" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PTPADR2: ");
	}
	
	/**
	 *  Visible sysroutine("PTAADR2") PTAADR2	;
	 *      import infix (string) item; entry() val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptradr2() {
		SVM_CALL_SYS.ENTER("PTAADR2	: ", 1, 4); // exportSize, importSize
		ProgramAddress val = (ProgramAddress) RTStack.pop();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NOBODY" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PTRADR2	: ");
	}
	
	/**
	 *  Visible sysroutine("PTRADR2	") PTRADR2	;
	 *      import infix (string) item; entry() val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptaadr2() {
		SVM_CALL_SYS.ENTER("PTRADR2	: ", 1, 4); // exportSize, importSize
		IntegerValue val = (IntegerValue) RTStack.pop();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NOFIELD" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PTAADR2	: ");
	}
	
	/**
	 *  Visible sysroutine("PTGADR2	") PTGADR2	;
	 *      import infix (string) item; name() val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptgadr2() {
		SVM_CALL_SYS.ENTER("PTGADR2	: ", 1, 5); // exportSize, importSize
//		RTStack.dumpRTStack("SysEdit.ptgadr2: ");
		GeneralAddress val = RTStack.popGADDR();
//		IO.println("SysEdit.ptgadr2: val="+val);
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "GNONE" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr));
		SVM_CALL_SYS.EXIT("PTGADR2	: ");
	}


	/// Procedure putfix.
	/// 
	/// The resulting numeric item is an INTEGER ITEM if n=0 or a DECIMAL ITEM with a
	/// FRACTION of n digits if n>0. It designates a number equal to the value of r
	/// or an approximation to the value of r, correctly rounded to n decimal places.
	/// If n<0, a run-time error is caused.
	/// 
	/// @param r the long real value to be edited
	/// @param n the number of digits after decimal sign
	private static String putfix(double r, int n) {
		if (n < 0)
//			throw new RTS_SimulaRuntimeError("putfix(r,n) - n < 0");
			Util.IERR("putfix(r,n) - n < 0");
		if (n == 0) {
//			putint(T, (int) (r + 0.5));
//			return;
			return "" + ((int) (r + 0.5));
		}
		StringBuilder pattern = new StringBuilder("##0.");
		while ((n--) > 0)
			pattern.append('0');
		DecimalFormat myFormatter = new DecimalFormat(pattern.toString());
		if (r == -0.0)
			r = 0.0; // NOTE: Java har både +0.0 og -0.0
		String output = myFormatter.format(r);
		output = output.replace((char) UNICODE_MINUS_SIGN, '-');
		return output;
	}
	
	/// Procedure putfix.
	/// 
	/// See <b>{@link RTS_TXT#putfix(RTS_TXT,double,int)}</b>
	/// 
	/// @param r the real value to be edited
	/// @param n the number of digits after decimal sign
	private static String putfix(float r, int n) {
//		putfix(T, (double) r, n);
		return putfix((double) r, n);
	}
	
	/// Procedure putreal.
	/// 
	/// The resulting numeric item is a REAL ITEM containing an EXPONENT with a fixed
	/// implementation-defined number of characters. The EXPONENT is preceded by a
	/// SIGN PART if n=0, or by an INTEGER ITEM with one digit if n=1, or if n>1, by
	/// a DECIMAL ITEM with an INTEGER ITEM of 1 digit only, and a fraction of n-1
	/// digits. If n<0 a runtime error is caused.
	/// 
	/// @param r the long real value to be edited
	/// @param n the number of digits after decimal sign
	private static String putreal(double r, int n) {
		if (n < 0)
//			throw new RTS_SimulaRuntimeError("putreal(r,n) - n < 0");
			Util.IERR("putreal(r,n) - n < 0");
		if (r == -0.0d)
			r = 0.0d;
		StringBuilder pattern = new StringBuilder("0");
		if (n > 1)
			pattern.append('.');
		while ((n--) > 1)
			pattern.append('0');
		pattern.append("E000");
		DecimalFormat myFormatter = new DecimalFormat(pattern.toString());
		myFormatter.setRoundingMode(RoundingMode.HALF_EVEN); // Java Default
		String output = myFormatter.format(r);
		output = output.replace((char) UNICODE_MINUS_SIGN, '-');
		output = addPlussExponent(output);
		return output;
	}

	/// Procedure putreal.
	/// 
	/// See <b>{@link RTS_TXT#putreal(RTS_TXT,double,int)}</b>
	/// 
	/// @param r the real value to be edited
	/// @param n the number of digits after decimal sign
	private static String putreal(float r, int n) {
		if (n < 0)
//			throw new RTS_SimulaRuntimeError("putreal(r,n) - n < 0");
			Util.IERR("putreal(r,n) - n < 0");
		if (r == -0.0f)
			r = 0.0f;
		StringBuilder pattern = new StringBuilder("0");
		if (n > 1)
			pattern.append('.');
		while ((n--) > 1)
			pattern.append('0');
		pattern.append("E00");
		DecimalFormat myFormatter = new DecimalFormat(pattern.toString());
		myFormatter.setRoundingMode(RoundingMode.HALF_EVEN); // Java Default
		String output = myFormatter.format(r);
		output = output.replace((char) UNICODE_MINUS_SIGN, '-');
		output = addPlussExponent(output);
		return output;
	}


	/// Real Edit Utility: Add plus exponent to the given string 
	/// @param s the given string
	/// @return the resulting string
	private static String addPlussExponent(String s) {
		s = s.replace((char) UNICODE_MINUS_SIGN, '-');
		String[] part = s.split("E");
		if (part.length == 2) {
			if (!(part[1].startsWith("-")))
				s = part[0] + "E+" + part[1];
		}
		return (s);
	}

}
