/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/

package svm.rts.sysrut;

import bec.scode.Type;
import bec.util.Util;
import svm.RTStack;
import svm.instruction.SVM_CALL_SYS;
import svm.value.IntegerValue;
import svm.value.LongRealValue;
import svm.value.RealValue;

public abstract class SysMath {


//	 Visible sysroutine("RADDEP") RADDEP;
//	 import real arg; export real val  end;
	public static void raddep() {
		SVM_CALL_SYS.ENTER("RADDEP: ", 1, 1); // exportSize, importSize
		float val = RTStack.popReal();
//		IO.println("SysMath.RADDEP: inp="+val);
		val = Math.nextUp(val);
//		IO.println("SysMath.RADDEP: out="+val);
		RTStack.push(RealValue.of(val));
		SVM_CALL_SYS.EXIT("RADDEP: ");
	}

//	 Visible sysroutine("DADDEP") DADDEP;
//	 import long real arg; export long real val  end;
	public static void daddep() {
		SVM_CALL_SYS.ENTER("DADDEP: ", 1, 1); // exportSize, importSize
		double val = RTStack.popLongReal();
		val = Math.nextUp(val);
		RTStack.push(LongRealValue.of(val));
		SVM_CALL_SYS.EXIT("DADDEP: ");
	}

//	 Visible sysroutine("RSUBEP") RSUBEP;
//	 import real arg; export real val  end;
	public static void rsubep() {
		SVM_CALL_SYS.ENTER("RSUBEP: ", 1, 1); // exportSize, importSize
		float val = RTStack.popReal();
		val = Math.nextDown(val);
		RTStack.push(RealValue.of(val));
		SVM_CALL_SYS.EXIT("RSUBEP: ");
	}

//	 Visible sysroutine("DSUBEP") DSUBEP;
//	 import long real arg; export long real val  end;
	public static void dsubep() {
		SVM_CALL_SYS.ENTER("DSUBEP: ", 1, 1); // exportSize, importSize
		double val = RTStack.popLongReal();
		val = Math.nextDown(val);
		RTStack.push(LongRealValue.of(val));
		SVM_CALL_SYS.EXIT("DSUBEP: ");
	}

//	 Visible sysroutine("IIPOWR") IIPOWR; --- v:=b**x
//	 import integer b,x; export integer v  end;
	public static void iipowr() {
		SVM_CALL_SYS.ENTER("IIPOWR: ", 1, 2); // exportSize, importSize
		int x = RTStack.popInt();
		int b = RTStack.popInt();
		int res = IPOW(b, x);
		RTStack.push(IntegerValue.of(Type.T_INT, res));
		SVM_CALL_SYS.EXIT("IIPOWR: ");
	}

	/// Visible sysroutine("MODULO") MOD;
	/// import integer x,y; export integer val  end;
	///
	/// The modulo operator in Java, denoted by the percent sign (%),
	/// calculates the remainder of a division operation.
	/// It is used with the following syntax:
	///		 operand1 % operand2,
	///	 where operand1 is the dividend and operand2 is the divisor.
	/// The result is the remainder when operand1 is divided by operand2. 
	///
	/// Simula Standard:
	///		integer procedure mod(i,j); integer i,j; begin
	///			integer res;
	///			res := i - (i//j)*j;
	///			mod := if res = 0 then 0
	///			else if sign(res) <> sign(j) then res+j
	///			else res
	///		end mod;
	///The result is the mathematical modulo value of the parameters.
	public static void modulo() {
		SVM_CALL_SYS.ENTER("MODULO: ", 1, 2); // exportSize, importSize
		int y = RTStack.popInt();
		int x = RTStack.popInt();
		int res = x % y;
//		IO.println("SysMath.modulo: "+x+" mod "+y+" ===> "+res);
		if(res == 0); //OK
		else if(sign(res) != sign(y)) res = res + y;
		
//		int res = y % x;
		RTStack.push(IntegerValue.of(Type.T_INT, res));
		SVM_CALL_SYS.EXIT("MODULO: ");
	}
	private static int sign(int i) {
		if(i < 0) return -1;
		if(i > 0) return +1;
		return 0;
	}

    //*******************************************************************************
    //*** IPOW - Integer Power: b ** x
    //*******************************************************************************
	/// Utility: Integer Power: b ** x
	/// @param base argument base
	/// @param x argument x
	/// @return Returns the value of 'base' raised to the power of 'x'
	private static int IPOW(final long base, long x) {
		if (x == 0) {
			if (base == 0)
				Util.ERROR("Exponentiation: " + base + " ** " + x + "  Result is undefined.");
			return (1); // any ** 0 ==> 1
		} else if (x < 0)
			Util.ERROR("Exponentiation: " + base + " ** " + x + "  Result is undefined.");
		else if (base == 0)
			return (0); // 0 ** non_zero ==> 0
		
		long res=(long) Math.pow((double)base,(double)x);
		if(res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
			Util.ERROR("Arithmetic overflow: "+base+" ** "+x+" ==> "+res
					+" which is outside integer value range["+Integer.MIN_VALUE+':'+Integer.MAX_VALUE+']');
		return((int)res);
	}

//	 Visible sysroutine("RIPOWR") RIPOWR; --- v:=b**x
//	 import real b; integer x; export real v  end;
	public static void ripowr() {
		SVM_CALL_SYS.ENTER("RIPOWR: ", 1, 2); // exportSize, importSize
		int x = RTStack.popInt();
		float b = RTStack.popReal();
		double res = Math.pow(b, x);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("RIPOWR: ");
	}

//	 Visible sysroutine("RRPOWR") RRPOWR; --- v:=b**x
//	 import real b,x; export real v  end;
	public static void rrpowr() {
		SVM_CALL_SYS.ENTER("RRPOWR: ", 1, 2); // exportSize, importSize
//		double x = RTStack.popLongReal();
//		double b = RTStack.popLongReal();
		float x = RTStack.popReal();
		float b = RTStack.popReal();
		float res = (float) Math.pow(b, x);
		RTStack.push(RealValue.of(res));
		SVM_CALL_SYS.EXIT("RRPOWR: ");
	}

//	 Visible sysroutine("RDPOWR") RDPOWR; --- v:=b**x
//	 import real b; long real x; export long real v  end;

//	 Visible sysroutine("DIPOWR") DIPOWR; --- v:=b**x
//	 import long real b; integer x; export long real v  end;
	public static void dipowr() {
		SVM_CALL_SYS.ENTER("DIPOWR: ", 1, 2); // exportSize, importSize
		int x = RTStack.popInt();
		double b = RTStack.popLongReal();
		double res = Math.pow(b, x);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("DIPOWR: ");
	}

//	 Visible sysroutine("DRPOWR") DRPOWR; --- v:=b**x
//	 import long real b; real x; export real v  end; -- NOTE: real result
//
//	 Visible sysroutine("DDPOWR") DDPOWR; --- v:=b**x
//	 import long real b,x; export long real v  end;
	public static void ddpowr() {
		SVM_CALL_SYS.ENTER("DDPOWR: ", 1, 2); // exportSize, importSize
		double x = RTStack.popLongReal();
		double b = RTStack.popLongReal();
		double res = Math.pow(b, x);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("DDPOWR: ");
	}

	
// Visible sysroutine("DATTIM") DATTIM;
// import infix(string) item; export integer filled  end;

// Visible sysroutine("LOWTEN") LTEN;
// import character c  end;

// Visible sysroutine("DCMARK") DECMRK;
// import character c  end;

// Visible sysroutine ("RSQROO") RSQROO;
// import real arg; export real val  end;
	public static void rsqroo() {
		SVM_CALL_SYS.ENTER("RSQROO: ", 1, 1); // exportSize, importSize
		float r = RTStack.popReal();
		float res = (float) Math.sqrt(r);
		RTStack.push(RealValue.of(res));
		SVM_CALL_SYS.EXIT("RSQROO: ");
	}

// Visible sysroutine("SQROOT") SQROOT;
// import long real arg; export long real val  end;
	public static void sqroot() {
		SVM_CALL_SYS.ENTER("SQROOT: ", 1, 1); // exportSize, importSize
		double r = RTStack.popLongReal();
		double res = Math.sqrt(r);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("SQROOT: ");
	}

// Visible sysroutine ("RLOGAR") RLOGAR;
// import real arg; export real val  end;
	public static void rlogar() {
		SVM_CALL_SYS.ENTER("RLOGAR: ", 1, 1); // exportSize, importSize
		float r = RTStack.popReal();
		double res = Math.log(r);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("RLOGAR: ");
	}

// Visible sysroutine("LOGARI") LOGARI;
// import long real arg; export long real val  end;
	public static void logari() {
		SVM_CALL_SYS.ENTER("LOGARI: ", 1, 1); // exportSize, importSize
		double r = RTStack.popLongReal();
		double res = Math.log(r);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("LOGARI: ");
	}

// Visible sysroutine("RLOG10") RLOG10;
// import real arg; export real val  end;

// Visible sysroutine("DLOG10") DLOG10;
// import long real arg; export long real val  end;
	public static void dlog10() {
		SVM_CALL_SYS.ENTER("DLOG10: ", 1, 1); // exportSize, importSize
		double r = RTStack.popLongReal();
		double res = Math.log10(r);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("DLOG10: ");
	}

// Visible sysroutine ("REXPON") REXPON;
// import real arg; export real val  end;
	public static void rexpon() {
		SVM_CALL_SYS.ENTER("REXPON: ", 1, 1); // exportSize, importSize
//		float r = (float) RTStack.popLongReal();
		float r = RTStack.popReal();
		float res = (float) Math.exp(r);
		RTStack.push(RealValue.of(res));
		SVM_CALL_SYS.EXIT("REXPON: ");
	}

// Visible sysroutine("EXPONE") EXPONE;
// import long real arg; export long real val  end;
	public static void expone() {
		SVM_CALL_SYS.ENTER("EXPONE: ", 1, 1); // exportSize, importSize
		double r = RTStack.popLongReal();
		double res = Math.exp(r);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("EXPONE: ");
	}

// Visible sysroutine("RSINUS") RSINUS;
// import real arg; export real val  end;
	public static void rsinus() {
		SVM_CALL_SYS.ENTER("RSINUS: ", 1, 1); // exportSize, importSize
		float r = RTStack.popReal();
		float res = (float) Math.sin(r);
		RTStack.push(RealValue.of(res));
		SVM_CALL_SYS.EXIT("RSINUS: ");
	}

// Visible sysroutine("SINUSR") SINUSR;
// import long real arg; export long real val  end;
	public static void sinusr() {
		SVM_CALL_SYS.ENTER("SINUSR: ", 1, 1); // exportSize, importSize
		double r = RTStack.popLongReal();
		double res = Math.sin(r);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("SINUSR: ");
	}

// Visible sysroutine("RCOSIN") RCOSIN;
// import real arg; export real val  end;
	public static void rcosin() {
		SVM_CALL_SYS.ENTER("RCOSIN: ", 1, 1); // exportSize, importSize
		float r = RTStack.popReal();
		float res = (float) Math.cos(r);
		RTStack.push(RealValue.of(res));
		SVM_CALL_SYS.EXIT("RCOSIN: ");
	}

// Visible sysroutine("COSINU") COSINU;
// import long real arg; export long real val  end;
	public static void cosinu() {
		SVM_CALL_SYS.ENTER("COSINU: ", 1, 1); // exportSize, importSize
		double r = RTStack.popLongReal();
		double res = Math.cos(r);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("COSINU: ");
	}

// Visible sysroutine("RTANGN") RTANGN;
// import real arg; export real val  end;

// Visible sysroutine("TANGEN") TANGEN;
// import long real arg; export long real val  end;

// Visible sysroutine("RCOTAN") COTANR;
// import real arg; export real val  end;

// Visible sysroutine("COTANG") COTANG;
// import long real arg; export long real val  end;

// Visible sysroutine("RARTAN") RARTAN;
// import real arg; export real val  end;
	public static void rartan() {
		SVM_CALL_SYS.ENTER("RARTAN: ", 1, 1); // exportSize, importSize
		float r = RTStack.popReal();
		float res = (float) Math.atan(r);
		RTStack.push(RealValue.of(res));
		SVM_CALL_SYS.EXIT("RARTAN: ");
	}

// Visible sysroutine("ARCTAN") ARCTAN;
// import long real arg; export long real val  end;
	public static void arctan() {
		SVM_CALL_SYS.ENTER("ARCTAN: ", 1, 1); // exportSize, importSize
		double r = RTStack.popLongReal();
		double res = Math.atan(r);
		RTStack.push(LongRealValue.of(res));
		SVM_CALL_SYS.EXIT("ARCTAN: ");
	}

// Visible sysroutine("RARCOS") RARCOS;
// import real arg; export real val  end;

// Visible sysroutine("ARCCOS") ARCCOS;
// import long real arg; export long real val  end;

// Visible sysroutine("RARSIN") RARSIN;
// import real arg; export real val  end;

// Visible sysroutine("ARCSIN") ARCSIN;
// import long real arg; export long real val  end;

// Visible sysroutine("RATAN2") ATAN2R;
// import real y,x; export real val  end;

// Visible sysroutine("ATAN2") ATAN2;
// import long real y,x; export long real val  end;

// Visible sysroutine("RSINH") SINHR;
// import real arg; export real val  end;

// Visible sysroutine("SINH") SINH;
// import long real arg; export long real val  end;

// Visible sysroutine("RCOSH") COSHR;
// import real arg; export real val  end;

// Visible sysroutine("COSH") COSH;
// import long real arg; export long real val  end;

// Visible sysroutine("RTANH") TANHR;
// import real arg; export real val  end;

// Visible sysroutine("TANH") TANH;
// import long real arg; export long real val  end;

}
