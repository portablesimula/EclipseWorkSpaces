package bec.virtualMachine;

public final class RTRegister {
//	public Value value;
//	
//	public static final boolean DEBUG = false; //true;
//	
//	public static boolean REUSE_REGISTER = true;  // Should be used in final version of the BEC Compiler
////	public static boolean REUSE_REGISTER = false; // For testing register usage
//	
//	// Initiated:
//	private static int nIRegs; // = 2;
//	private static int nARegs; // = 4;
//	
//	private static int aReg1; // = nIRegs;
//	private static int rMax; // = nIRegs + nARegs + nRRegs; 
//	private static RTRegister[] register; // = new RTRegister[rMax];
//	public static BitSet CTregused; // = new BitSet(); // in PASS1
//	private static BitSet RTregused; // = new BitSet(); // in PASS2
//
//	// Variables:
//	private static int nIRegUsed;
//	private static int nARegUsed;
//	private static int maxIRegUsed;
//	private static int maxARegUsed;
//	
//	public static boolean isRTregisterUsed(int reg) { return RTregused.get(reg); } // in PASS2
//	public static void clearRTUse(int reg) {
//		if(DEBUG) IO.println("RTRegister.clearRTUse: Clear inUse: " + RTregused);
//		if(! RTRegister.isRTregisterUsed(reg)) Util.IERR("RTRegister is not used: " + RTRegister.edRegVal(reg));
//		register[reg - 1] = null;
//		RTregused.set(reg, false);
//	}
//	
//	public  static boolean isIntReg(int reg) { return reg < nIRegs; }
//	private static boolean isAdrReg(int reg) { return reg >= aReg1; }
//	
//	private RTRegister(int value) {
//		this.value = IntegerValue.of(Type.T_INT, value);
//	}
//	
//	private RTRegister(Value value) {
//		this.value = value;
//	}
//	
//	public static void init() {
//		if(REUSE_REGISTER) {
//			nIRegs = 2;
//			nARegs = 8;
//		} else {
//			nIRegs = 150;
//			nARegs = 1500;
//		}
//		aReg1 = nIRegs;
//		rMax = nIRegs + nARegs;
//		register = new RTRegister[rMax];
//		CTregused = new BitSet(); // in PASS1
//		RTregused = new BitSet(); // in PASS1
//	}
//	
//	public static int getFreeIndexReg() { // Used by: INDEX only
//		if(Global.duringEXEC()) Util.IERR("");
//		nIRegUsed++; CTregused.set(nIRegUsed);//		Global.PSEG.emit(new SVM_CLEAR_REGUSE(nIRegUsed, true), "");
//		if(nIRegUsed > nIRegs) Util.IERR("RTRegister.getFreeIndexReg: " + nIRegUsed);
//		maxIRegUsed = Math.max(maxIRegUsed, nIRegUsed);
//		return nIRegUsed;
//	}
//
//	public static int getFreeAddressReg() {
//		Util.IERR("SKAL IKKE BRUKES MED IBREGs");
//		if(Global.duringEXEC()) Util.IERR("");
//		nARegUsed++; int aReg = aReg1 + nARegUsed - 1;
//		CTregused.set(aReg);
//		if(nARegUsed > nARegs) Util.IERR("RTRegister.getFreeAddressReg: " + nARegUsed);
//		maxARegUsed = Math.max(maxARegUsed, nARegUsed);
//		return aReg;
//	}
//
//	public static void clearAllRegUsed() { // Used by RoutineDescr at ENDROUTINE
//		if(Global.duringEXEC()) Util.IERR("");
//		nIRegUsed = 0; nARegUsed = 0; //nRRegUsed = 0;
//		CTregused.clear();
//	}
//	
//	public static void clearReg(int reg) {
//		if(Global.duringEXEC()) Util.IERR("");
//		if(reg > 0) {
//			Global.PSEG.emit(new SVM_CLEAR_REGUSE(reg), "");
//			CTregused.clear(reg);
//			if(REUSE_REGISTER & CTregused.cardinality() == 0) {
//				nIRegUsed = 0; nARegUsed = 0; //nRRegUsed = 0;
//			}
//		}
//	}
//	
//	
//	public static void clearRTReg(int reg) {
//		if(! Global.duringEXEC()) Util.IERR("");
//		if(reg > 0) {
//			RTregused.clear(reg);
//		}
//	}
//	
//	
//	public static void putIntValue(int reg, int value) {
//		if(DEBUG) IO.println("\nBEGIN RTRegister.putIntValue: reg="+edReg(reg) + " := " + value + "    RTregused="+edRTRegused());
//		if(! Global.duringEXEC()) Util.IERR("");
//		if(! isIntReg(reg)) Util.IERR("");
//		if(isRTregisterUsed(reg)) Util.IERR("RTRegister already used: " + edRegVal(reg));
//		register[reg-1] = new RTRegister(value);
//		RTregused.set(reg);
//	}
//	
//	public static int getIntValue(int reg) {
//		if(DEBUG) IO.println("RTRegister.getIntValue: reg="+edReg(reg));
//		if(! Global.duringEXEC()) Util.IERR("");
//		if(! isIntReg(reg)) Util.IERR("");
//		if(! isRTregisterUsed(reg)) Util.IERR("RTRegister is not used: " + edRegVal(reg));
//		IntegerValue ival = (IntegerValue) register[reg-1].value;
//		return (ival == null)? 0 : ival.value;
//	}
//
//	public static void putAddrValue(int reg, DataAddress value) {
//		if(DEBUG) IO.println("\nBEGIN RTRegister.putAddrValue: reg="+edReg(reg) + " := " + value + "    RTregused="+edRTRegused());
//		if(! Global.duringEXEC()) Util.IERR("");
//		if(! isAdrReg(reg))	Util.IERR("");
//		if(DEBUG) IO.println("BEFORE isRTregisterUsed: " + RTregused);
//		if(isRTregisterUsed(reg)) Util.IERR("RTRegister already used: " + edRegVal(reg));
//		register[reg-1] = new RTRegister(value);
//		RTregused.set(reg);
//	}
//	
//	public static DataAddress getAddrValue(int reg) {
//		if(DEBUG) IO.println("RTRegister.getAddrValue: reg="+edReg(reg));
//		if(! Global.duringEXEC()) Util.IERR("");
//		if(! isAdrReg(reg)) Util.IERR(""+edReg(reg));
//		if(! isRTregisterUsed(reg)) Util.IERR("RTRegister is not used: " + edRegVal(reg));
//		DataAddress aval = (DataAddress) register[reg-1].value;
//		return aval;
//	}
//
//	public static String toLine() {
//		StringBuilder sb = new StringBuilder();
//		boolean first = true;
//		for(int i=0;i<rMax;i++) {
//			if(register[i] != null) {
//				if(first) sb.append("xReg: "); else sb.append(", "); first = false;
//				sb.append(edReg(i+1));
//				sb.append('(').append(register[i].value).append(')');
//			}
//		}
//		return sb.toString();
//	}
//	
//	public static void clearAllRegs() {
//		for(int i=0;i<rMax;i++) register[i] = null;
//	}

//	public static String edReg(int reg) {
//		if(isIntReg(reg)) return "xReg" + reg;
//		if(isAdrReg(reg)) return "aReg" + (reg - aReg1 + 1);
//		Util.IERR(""); return null;
//	}
//
//	public static String edRegVal(int reg) {
//		if(Global.duringEXEC()) {
//			RTRegister rval = register[reg-1];
//			Value val = (rval == null)? null : rval.value;
//			String regValue = "("+val+')';
//			return edReg(reg) + regValue;
//		} else {
//			return edReg(reg);			
//		}
//	}
//	
//	public static String edCTRegused(String title) {
//		String s = (CTregused.cardinality() == 0)? "null" : edCTRegused();
//		return title + s;
//	}
//	
//	public static String edCTRegused() {
//		String s = ""; String sep = "";
//		for (int i = CTregused.nextSetBit(0); i >= 0; i = CTregused.nextSetBit(i+1)) {
//			s += sep + edReg(i); sep = "|";
//			// operate on index i here
//			if (i == Integer.MAX_VALUE) {
//				break; // or (i+1) would overflow
//			}
//		}
//		return s;
//	}
//	
//	public static String edRTRegused(String title) {
//		String s = (CTregused.cardinality() == 0)? "null" : edCTRegused();
//		return title + s;
//	}
//	
//	public static String edRTRegused() {
//		String s = ""; String sep = "";
//		for (int i = RTregused.nextSetBit(0); i >= 0; i = CTregused.nextSetBit(i+1)) {
//			s += sep + edRegVal(i); sep = "|";
//			// operate on index i here
//			if (i == Integer.MAX_VALUE) {
//				break; // or (i+1) would overflow
//			}
//		}
//		return s;
//	}
//
//	public static void printSummary() {
//		IO.println("RTRegister.printSummary: maxIndexRegUsed=" + maxIRegUsed + ", maxAddressRegUsed=" + maxARegUsed);
////		IO.println("RTRegister.printSummary: iReg1=" + 1 + ", aReg1=" + aReg1 + ", rReg1=" + rReg1);
//	}
//
//	public String toString() {
//		return ""+value;
//	}
//
}
