package bec.virtualMachine.sysrut;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import bec.util.Util;

public abstract class SysEdit {

	/// See: https://en.wikipedia.org/wiki/Plus_and_minus_signs
	private static final char UNICODE_MINUS_SIGN = 0x2212;


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
	public static String putfix(double r, int n) {
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
	public static String putfix(float r, int n) {
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
	public static String putreal(double r, int n) {
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
	public static String putreal(float r, int n) {
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
