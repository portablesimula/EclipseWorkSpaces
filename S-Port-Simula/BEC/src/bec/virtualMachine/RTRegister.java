package bec.virtualMachine;

import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.Value;

public final class RTRegister {
	int value;
	
	private static int nRegUsed;
	
	public static final int rMax = 10;
	private static RTRegister[] register = new RTRegister[rMax];
	
	private RTRegister(int value) {
		this.value = value;
	}
	
	public static int getFreeReg() {
		nRegUsed++;
		System.out.println("RTRegister.getFreeReg: "+nRegUsed);
		return nRegUsed;
	}
	
	public static void ckearFreeRegs() {
		nRegUsed = 0;
	}
	
	public static void putValue(int reg, int index) {
		register[reg-1] = new RTRegister(index);
//		System.out.println("RTRegister.putValue: "+index+", reg="+reg+", value="+register[reg-1]);
	}
	
	public static int getValue(int reg) {
		try {
			int value = register[reg-1].value;
			return value;
		} catch(Exception e) {
			return -1;
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
