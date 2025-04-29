package bec.compileTimeStack;

import bec.util.Scode;
import bec.util.Type;

public class Temp extends CTStackItem {
	int count;
	String comment;
	
	// Value is pushed on RT-stack
	public Temp(Mode mode, Type type, int count, String comment) {
		this.mode = mode;
		this.type = type;
		this.count = count;
		this.comment = comment;
	}

	@Override
	public CTStackItem copy() {
		return new Temp(mode, type, count, comment);
	}

	@Override
	public String toString() {
		return "Temp " + Scode.edTag(type.tag) + ", count=" + count + " " + comment;
	}
}
