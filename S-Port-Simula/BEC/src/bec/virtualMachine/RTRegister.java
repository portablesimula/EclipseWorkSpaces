package bec.virtualMachine;

import bec.value.Value;

public final class RTRegister {
	Value value;
	
	private static int nRegUsed;
	
	public static final int rMax = 50;
	private static RTRegister[] register = new RTRegister[rMax];
	
	private RTRegister(Value value) {
		this.value = value;
	}
	
	public static int getFreeReg() {
		nRegUsed++;
		System.out.println("RTRegister.getFreeReg: "+nRegUsed);
		return nRegUsed;
//		return 1;
	}
	
	public static void clearFreeRegs() {
//		System.out.println("RTRegister.clearFreeRegs: "+nRegUsed+" ===> 0");
		nRegUsed = 0;
	}
	
	public static void putValue(int reg, Value value) {
		register[reg-1] = new RTRegister(value);
//		System.out.println("RTRegister.putValue: "+value+", reg="+reg+", value="+register[reg-1]);
	}
	
	public static Value getValue(int reg) {
		try {
			Value value = register[reg-1].value;
			return value;
		} catch(Exception e) {
//			return -1;
			return null;
		}
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
//		Thread.dumpStack();
		for(int i=0;i<rMax;i++) register[i] = null;
	}

	public static String edReg(int reg) {
		return("R"+reg);
	}
	
	public String toString() {
		return ""+value;
	}

}
