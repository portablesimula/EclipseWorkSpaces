package bec.virtualMachine.sysrut;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import bec.util.Type;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.RTUtil;
import bec.virtualMachine.SVM_CALLSYS;

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
		SVM_CALLSYS.ENTER("PUTSTR: ", 1, 6); // exportSize, importSize
		int valNchr = RTStack.popInt();
		ObjectAddress valAddr = RTStack.popGADDRasOADDR();
		if(DEBUG) {
			// valAddr.segment().dump("SVM_SYSCALL.putstr:");
			System.out.println("SVM_SYSCALL.putstr: valAddr="+valAddr);
			System.out.println("SVM_SYSCALL.putstr: valNchr="+valNchr);
		}
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		if(DEBUG) {
			// itemAddr.segment().dump("SVM_SYSCALL.putstr:");
			System.out.println("SVM_SYSCALL.putstr: itemAddr="+itemAddr);
			System.out.println("SVM_SYSCALL.putstr: itemNchr="+itemNchr);
		}
		if(valNchr > 0) RTUtil.move(valAddr, itemAddr, valNchr);

		RTStack.push(IntegerValue.of(Type.T_INT, valNchr), "EXPORT");
		SVM_CALLSYS.EXIT("PUTSTR: ");
	}
	
	/**
	 *  Visible sysroutine("PUTINT") PUTINT;
	 *      import infix (string) item; integer val
	 *  end;
	 */
	public static void putint() {
		SVM_CALLSYS.ENTER("PUTINT: ", 0, 4); // exportSize, importSize
		PUTINT();
		SVM_CALLSYS.EXIT("PUTINT: ");
	}
	
	/**
	 *  Visible sysroutine("PUTINT2") PUTINT2;
	 *      import infix (string) item; integer val
	 *      export integer lng;
	 *  end;
	 */
	public static void putint2() {
		SVM_CALLSYS.ENTER("PUTINT2: ", 1, 4); // exportSize, importSize
		int nchr = PUTINT();
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PUTINT2: ");
	}

	private static int PUTINT() {
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
//		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		return nchr;
	}
	
	/**
	 *  Visible sysroutine("PTREAL") PTREAL;
	 *      import infix (string) item; real val; integer frac; 
	 *  end;
	 */
	public static void putreal() {
		SVM_CALLSYS.ENTER("PTREAL: ", 0, 5); // exportSize, importSize
		RTStack.dumpRTStack("PTREAL: ");
		PUTREAL();
		SVM_CALLSYS.EXIT("PTREAL: ");
	}
	
	/**
	 *  Visible sysroutine("PTREAL2") PTREAL2;
	 *      import infix (string) item; real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	public static void putreal2() {
		SVM_CALLSYS.ENTER("PTREAL2: ", 1, 5); // exportSize, importSize
		int nchr = PUTREAL();
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PTREAL2: ");
	}
	
	private static int PUTREAL() {
		int frac = RTStack.popInt();
		float val = RTStack.popReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putreal(val,frac);
		sval = sval.replace(',', '.').replace('E', '&');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		return nchr;		
	}
	
	/**
	 *  Visible sysroutine("PLREAL") PLREAL;
	 *      import infix (string) item; long real val; integer frac; 
	 *  end;
	 */
	public static void putlreal() {
		SVM_CALLSYS.ENTER("PLREAL2: ", 0, 5); // exportSize, importSize
		PUTLREAL();
		SVM_CALLSYS.EXIT("PLREAL2: ");
	}
	
	/**
	 *  Visible sysroutine("PLREAL2") PLREAL2;
	 *      import infix (string) item; long real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	public static void putlreal2() {
		SVM_CALLSYS.ENTER("PLREAL2: ", 1, 5); // exportSize, importSize
		int nchr = PUTLREAL();
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PLREAL2: ");
	}
	
	private static int PUTLREAL() {
		int frac = RTStack.popInt();
		double val = RTStack.popLongReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putreal(val,frac);
		sval = sval.replace(',', '.').replace('E', '&');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		return nchr;		
	}
	
	/**
	 *  Visible sysroutine("PUTFIX2") PUTFIX2;
	 *      import infix (string) item; real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	public static void putfix2() {
		SVM_CALLSYS.ENTER("PUTFIX2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		float val = RTStack.popReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putfix(val,frac);
		sval = sval.replace(',', '.');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PUTFIX2: ");
	}
	
	/**
	 *  Visible sysroutine("PTLFIX2") PTLFIX2;
	 *      import infix (string) item; long real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	public static void putlfix2() {
		SVM_CALLSYS.ENTER("PUTFIX2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		double val = RTStack.popLongReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putfix(val,frac);
		sval = sval.replace(',', '.');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PUTFIX2: ");
	}
	
	/**
	 *  Visible sysroutine("PUTHEX") PUTHEX;
	 *      import infix (string) item; integer val
	 *      export integer lng;
	 *  end;
	 */
	public static void puthex() {
		SVM_CALLSYS.ENTER("PUTHEX: ", 1, 4); // exportSize, importSize
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = "0x" + Integer.toHexString(val).toUpperCase();
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PUTHEX: ");
	}
	
	/**
	 *  Visible sysroutine("PUTSIZE") PUTSIZE;
	 *      import infix (string) item; size val
	 *      export integer lng;
	 *  end;
	 */
	public static void putsize() {
		SVM_CALLSYS.ENTER("PUTSIZE: ", 1, 4); // exportSize, importSize
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PUTSIZE: ");
	}
	
	/**
	 *  Visible sysroutine("PTOADR2") PTOADR2;
	 *      import infix (string) item; ref() val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptoadr2() {
		SVM_CALLSYS.ENTER("PTOADR2: ", 1, 4); // exportSize, importSize
		ObjectAddress val = RTStack.popOADDR();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NONE" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PTOADR2: ");
	}
	
	/**
	 *  Visible sysroutine("PTPADR2") PTPADR2;
	 *      import infix (string) item; label val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptpadr2() {
		SVM_CALLSYS.ENTER("PTPADR2: ", 1, 4); // exportSize, importSize
		ProgramAddress val = (ProgramAddress) RTStack.pop().value();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NOWHERE" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PTPADR2: ");
	}
	
	/**
	 *  Visible sysroutine("PTAADR2") PTAADR2	;
	 *      import infix (string) item; entry() val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptradr2() {
		SVM_CALLSYS.ENTER("PTAADR2	: ", 1, 4); // exportSize, importSize
		ProgramAddress val = (ProgramAddress) RTStack.pop().value();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NOBODY" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PTRADR2	: ");
	}
	
	/**
	 *  Visible sysroutine("PTRADR2	") PTRADR2	;
	 *      import infix (string) item; entry() val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptaadr2() {
		SVM_CALLSYS.ENTER("PTRADR2	: ", 1, 4); // exportSize, importSize
		IntegerValue val = (IntegerValue) RTStack.pop().value();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NOFIELD" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PTAADR2	: ");
	}
	
	/**
	 *  Visible sysroutine("PTGADR2	") PTGADR2	;
	 *      import infix (string) item; name() val;
	 *      export integer lng;
	 *  end;
	 */
	public static void ptgadr2() {
		SVM_CALLSYS.ENTER("PTGADR2	: ", 1, 4); // exportSize, importSize
		GeneralAddress val = RTStack.popGADDR();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "GNONE" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("SVM_CALLSYS.move edit-buffer");
		RTUtil.move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		SVM_CALLSYS.EXIT("PTGADR2	: ");
	}


	/// Procedure putfix.
	/// 
	/// The resulting numeric item is an INTEGER ITEM if n=0 or a DECIMAL ITEM with a
	/// FRACTION of n digits if n>0. It designates a number equal to the value of r
	/// or an approximation to the value of r, correctly rounded to n decimal places.
	/// If n<0, a run-time error is caused.
	/// 
	/// @param T the text reference
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
	/// @param T the text reference
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
	/// @param T the text reference
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
	/// @param T the text reference
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
