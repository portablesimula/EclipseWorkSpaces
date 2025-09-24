package svm.virtualMachine;

import svm.value.IntegerValue;
import svm.value.Value;

public final class RTRegister {
	public Value value;
	
	public static int indexReg  = 1; // BARE HVIS Global.TESTING_REGUSE
	public static int remoteReg = 2; // BARE HVIS Global.TESTING_REGUSE
	public static int referReg  = 3; // BARE HVIS Global.TESTING_REGUSE
	
	private static int nRegUsed;
	
	public static final int rMax = 127;
	private static RTRegister[] register = new RTRegister[rMax];
	
	private RTRegister(Value value) {
		this.value = value;
	}
	
	public static int getFreeReg() {
		nRegUsed++;
		return nRegUsed;
	}
	
	public static void clearFreeRegs() {
		nRegUsed = 0;
	}
	
	public static void putValue(int reg, Value value) {
		register[reg-1] = new RTRegister(value);
	}
	
	public static Value getValue(int reg) {
		try { return register[reg-1].value;
		} catch(Exception e) { return null; }
	}

	public static int getIntValue(int reg) {
		Value value = RTRegister.getValue(reg);
		if(value == null) return 0;
		if(value instanceof IntegerValue ival) {
			return ival.value;
		} else return 0;
	}

	public static String toLine() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(int i=0;i<rMax;i++) {
			if(register[i] != null) {
				if(first) sb.append("xReg: "); else sb.append(", "); first = false;
				sb.append(edReg(i+1));
				sb.append('(').append(register[i].value).append(')');
			}
		}
		return sb.toString();
	}
	
	public static void clearAllRegs() {
		for(int i=0;i<rMax;i++) register[i] = null;
	}

	public static String edReg(int reg) {
		return("R"+reg);
	}

	public static String edRegVal(int reg) {
//		Value val = getValue(reg);
		String regValue = "";//(val == null)? "" : "("+val+')';
		return("R"+reg+regValue);
	}
	
	public String toString() {
		return ""+value;
	}

}
