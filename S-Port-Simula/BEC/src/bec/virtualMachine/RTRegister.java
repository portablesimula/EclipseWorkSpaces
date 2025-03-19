package bec.virtualMachine;

import bec.value.IntegerValue;
import bec.value.Value;

public final class RTRegister {
	Value value;
	
	private static int nRegUsed;
	
	public static final int qEAX = 1; // TODO: SKAL FJERNES
	public static final int qEBX = 2; // TODO: SKAL FJERNES
//	public static final int qECX = 3;
//	public static final int qEDX = 4;
	public static final int rMax = 10;
//	private static Value[] register = new Value[rMax+1];
	private static RTRegister[] register = new RTRegister[rMax];
	
	private RTRegister(Value value) {
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
	
	public static void putValue(int reg, Value index) {
		register[reg-1] = new RTRegister(index);
//		System.out.println("RTRegister.putValue: "+index+", reg="+reg);
	}
	
	public static Value getValue(int reg) {
		Value value = register[reg-1].value;
		return value;
	}
	
	public static int getIndex(int reg) {
		System.out.println("RTRegister.getIndex: reg="+reg+"  "+register[reg-1]);
//		Thread.dumpStack();
		try {
			IntegerValue val = (IntegerValue) register[reg-1].value;
			return IntegerValue.intValue(val);
		} catch(Exception e) {
//			e.printStackTrace();
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
		return value.toString();
	}

}
