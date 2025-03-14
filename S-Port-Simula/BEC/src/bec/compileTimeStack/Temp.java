package bec.compileTimeStack;

import bec.util.Scode;
import bec.util.Type;
import bec.virtualMachine.RTRegister;

public class Temp extends StackItem {
	int reg;
	int count;
	String comment;
	
	// Value is pushed on RT-stack
	public Temp(Type type, int reg, int count, String comment) {
		this.type = type;
		this.reg = reg;
		this.count = count;
		this.comment = comment;
	}
	
	public String toString() {
		return "Temp " + Scode.edTag(type.tag) + ", reg=" + RTRegister.edReg(reg) + ", count=" + count + " " + comment;
	}
}
