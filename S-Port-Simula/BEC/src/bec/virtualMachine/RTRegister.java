package bec.virtualMachine;

import java.util.BitSet;

import bec.util.Global;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;

public final class RTRegister {
	
	private static final boolean DEBUG = false;
	
	public static final int rMax = 50;
//	private static RTRegister[] register = new RTRegister[rMax];
	private static Value[] register; // = new Value[rMax];
	private static BitSet mindMask = new BitSet();
	private static int maxRegUsed;
	

	public static void init() {
		register = new Value[rMax];
		mindMask = new BitSet();
		maxRegUsed = 0;
	}
	
	
	public static void reads(String who, ObjectAddress adr) {  // TODO: FJERNES ?????
//		int iReg = adr.getIreg(); if(iReg != 0) mindMask.set(iReg, false);
//		int bReg = adr.getBreg(); if(bReg != 0) mindMask.set(bReg, false);
//		if(adr instanceof RemoteAddress rem) reads(who, rem.xReg);
//		if(adr instanceof RelAddress ref) reads(ref.xReg);
//		if(adr instanceof GeneralAddress gaddr) reads(who, gaddr.base);
//		if(DEBUG) IO.println("RTRegister.reads: " + who + adr + " ==> UsedRegs=" + edRegused());
//		Util.IERR("NOT IMPL");
	}
	
	public static void reads(String who, int... reg) {
		for(int r:reg) {
			if(r != 0) {
				if(! mindMask.get(r)) Util.IERR("RTRegister.reads: Register " + edReg(r) + " is not minded");
				mindMask.set(r, false);
				if(DEBUG) IO.println("RTRegister.reads: " + who + ": " + edReg(r) + " ==> UsedRegs=" + edRegused());
			}
		}
	}
	
	public static void writes(String who, int... reg) {
		for(int r:reg) {
			if(r != 0) {
				if(mindMask.get(r)) Util.IERR("RTRegister.writes: Register " + edReg(r) + " is already minded");
				mindMask.set(r, true);
				if(DEBUG) IO.println("RTRegister.writes: " + who + ": " + edReg(r) + " ==> UsedRegs=" + edRegused());
//				Thread.dumpStack();
			}
		}
	}
	
	public static boolean isMindMaskEmpty() {
		return mindMask.isEmpty();
	}
	
	public static void checkMindMaskEmpty() {
		if(! mindMask.isEmpty()) Util.IERR("RTRegister.checkMindMaskEmpty: FAILED: " + edRegused());
	}
	
//	protected void minds(int... reg) {
//		
//	}
	
	public static int nRegUsed() {
		return mindMask.cardinality();
	}

	public static Value getValue(int reg) {
		if(DEBUG) IO.println("RTRegister.getValue: reg="+edReg(reg));
		if(! Global.duringEXEC()) Util.IERR("");
		return register[reg-1];
	}

	public static int getIntValue(int reg) {
//		if(DEBUG) IO.println("RTRegister.getIntValue: reg="+edReg(reg));
		if(! Global.duringEXEC()) Util.IERR("");
		IntegerValue ival = (IntegerValue) register[reg-1];
		return (ival == null)? 0 : ival.value;
	}

	public static int getFreeReg() {
		int r = mindMask.nextClearBit(0);
	    if(r >= 0) {
			if(DEBUG) IO.println("RTRegister.getFreeReg: UsedRegs=" + ((isMindMaskEmpty())? "empty" : edRegused()) + " ==> " + edReg(r+1));
			maxRegUsed = Math.max(maxRegUsed, r + 1);
	    	return r + 1; 
	    } else {
	    	Util.IERR("FreePartReg -- Not available"); return 0;
	    }
	}
	
	public static void putValue(int r, Value value) {
		
		if(Global.TESTING_STACK_ADDRESS) {
			if(value instanceof ObjectAddress oaddr) {
//				IO.println("RTRegister.putValue: "+value+", reg="+reg+", value="+register[reg-1]);
//				IO.println("RTRegister.putValue: OADDR: "+oaddr);
				if(oaddr != null && oaddr.kind == ObjectAddress.REL_ADDR) {
//					RTStack.dumpRTStack("RTRegister.putValue: NOTE: ");
					Util.IERR("DETTE SKAL IKKE FOREKOMME");
				}			
			}
			if(value instanceof GeneralAddress gaddr) {
//				IO.println("RTRegister.putValue: "+value+", reg="+reg+", value="+register[reg-1]);
//				IO.println("RTRegister.putValue: GADDR: "+gaddr);
				ObjectAddress oaddr = gaddr.base;
				if(oaddr != null && oaddr.kind == ObjectAddress.REL_ADDR) {
//					RTStack.dumpRTStack("RTRegister.putValue: NOTE: ");
					gaddr.base = oaddr.toStackAddress();
//					IO.println("RTRegister.putValue: GADDR: "+gaddr);
					Util.IERR("DETTE SKAL IKKE FOREKOMME");
				}
			}
		}

//		register[reg-1] = new RTRegister(value);
		register[r-1] = value;

	}
	
//	public static Value getValue(int reg) {
//		try {
//			Value value = register[reg-1].value;
//			return value;
//		} catch(Exception e) {
////			return -1;
//			return null;
//		}
//	}
//
//	public static int getIntValue(int reg) {
//		Value value = RTRegister.getValue(reg);
//		if(value == null) return 0;
//		if(value instanceof IntegerValue ival) {
//			return ival.value;
//		} else if(value instanceof GeneralAddress gaddr) {
//			Value val = gaddr.base.load(gaddr.ofst);
//			IO.println("RTRegister.getIntValue: gaddr="+gaddr);
////			IO.println("RTRegister.getIntValue: "+val.getClass().getSimpleName()+"  "+val);
//			IO.println("RTRegister.getIntValue: "+val);
////			Util.IERR("");
//			return 0;
//		} else {
//			Util.IERR(""+value.getClass().getSimpleName()+"  "+value);
//			return 0;
//		}
//	}

	public static String toLine() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(int i=0;i<rMax;i++) {
			if(register[i] != null) {
				if(first) sb.append("reg: "); else sb.append(", "); first = false;
				sb.append(edReg(i+1));
				sb.append('(').append(register[i]).append(')');
			}
		}
		return sb.toString();
	}

	public static String edRegused() {
		String s = ""; String sep = "";
		for (int i = mindMask.nextSetBit(0); i >= 0; i = mindMask.nextSetBit(i+1)) {
			s += sep + edReg(i); sep = "|";
			// operate on index i here
			if (i == Integer.MAX_VALUE) {
				break; // or (i+1) would overflow
			}
		}
		return s;
	}

	public static String edReg(int reg) {
		return("R"+reg);
	}

	public static String edRegValue(int reg) {
//		if(reg == 0) return "";
		if(! Global.duringEXEC()) return edReg(reg);
		return "+R" + reg + "(" + RTRegister.getValue(reg) + ')';
	}
	
	public static void printSummary() {
		IO.println("RTRegister.printSummary: maxRegUsed=" + maxRegUsed);
	}

//	public String toString() {
//		return ""+value;
//	}

}
