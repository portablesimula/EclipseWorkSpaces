package bec.virtualMachine;

import bec.util.BecGlobal;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
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
//		System.out.println("RTRegister.getFreeReg: "+nRegUsed);
		return nRegUsed;
	}
	
	public static void clearFreeRegs() {
//		System.out.println("RTRegister.clearFreeRegs: "+nRegUsed+" ===> 0");
		nRegUsed = 0;
	}
	
	public static void putValue(int reg, Value value) {
		
		if(BecGlobal.TESTING_STACK_ADDRESS) {
			if(value instanceof ObjectAddress oaddr) {
//				System.out.println("RTRegister.putValue: "+value+", reg="+reg+", value="+register[reg-1]);
//				System.out.println("RTRegister.putValue: OADDR: "+oaddr);
				if(oaddr != null && oaddr.kind == ObjectAddress.REL_ADDR) {
//					RTStack.dumpRTStack("RTRegister.putValue: NOTE: ");
					Util.IERR("DETTE SKAL IKKE FOREKOMME");
				}			
			}
			if(value instanceof GeneralAddress gaddr) {
//				System.out.println("RTRegister.putValue: "+value+", reg="+reg+", value="+register[reg-1]);
//				System.out.println("RTRegister.putValue: GADDR: "+gaddr);
				ObjectAddress oaddr = gaddr.base;
				if(oaddr != null && oaddr.kind == ObjectAddress.REL_ADDR) {
//					RTStack.dumpRTStack("RTRegister.putValue: NOTE: ");
					gaddr.base = oaddr.toStackAddress();
//					System.out.println("RTRegister.putValue: GADDR: "+gaddr);
					Util.IERR("DETTE SKAL IKKE FOREKOMME");
				}
			}
		}

		register[reg-1] = new RTRegister(value);

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

	public static int getIntValue(int reg) {
		Value value = RTRegister.getValue(reg);
		if(value == null) return 0;
		if(value instanceof IntegerValue ival) {
			return ival.value;
		} else if(value instanceof GeneralAddress gaddr) {
			Value val = gaddr.base.load(gaddr.ofst);
			System.out.println("RTRegister.getIntValue: gaddr="+gaddr);
//			System.out.println("RTRegister.getIntValue: "+val.getClass().getSimpleName()+"  "+val);
			System.out.println("RTRegister.getIntValue: "+val);
//			Util.IERR("");
			return 0;
		} else {
			Util.IERR(""+value.getClass().getSimpleName()+"  "+value);
			return 0;
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
