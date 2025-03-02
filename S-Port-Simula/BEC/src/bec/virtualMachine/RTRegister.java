package bec.virtualMachine;

import bec.value.IntegerValue;
import bec.value.Value;

public abstract class RTRegister {
	
//	private static int[] register = new int[4];
	private static Value[] register = new Value[4];
	public static final int qEAX = 1;
	public static final int qEBX = 2;
	public static final int qECX = 3;
	public static final int qEDX = 4;
	
	public static void putValue(int reg, Value index) {
		register[reg-1] = index;
	}
	
	public static Value getValue(int reg) {
		Value value = register[reg-1];
		return value;
	}
	
	public static int getIndex(int reg) {
		IntegerValue val = (IntegerValue) register[reg-1];
		return IntegerValue.intValue(val);
	}
	
	public static String edReg(int reg) {
		switch(reg) {
		case 1: return("qEAX");
		case 2: return("qEBX");
		case 3: return("qECX");
		case 4: return("qEDX");
		}
		return("UNDEF_REG_"+reg);
	}

}
