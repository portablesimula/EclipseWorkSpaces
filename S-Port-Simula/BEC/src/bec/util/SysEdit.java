package bec.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public abstract class SysEdit {

	private static final char UNICODE_MINUS_SIGN = 0;

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
