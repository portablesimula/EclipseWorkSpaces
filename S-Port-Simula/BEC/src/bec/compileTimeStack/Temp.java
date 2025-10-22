package bec.compileTimeStack;

import bec.util.Scode;
import bec.util.Type;

public class Temp extends CTStackItem {
	int count;
//	String comment;
	int sequ;
	
	private static int SEQU = 1;
	
	// Value is pushed on RT-stack
//	public Temp(Mode mode, Type type, int count, String comment) {
	public Temp(Mode mode, Type type, int count) {
		this.mode = mode;
		this.type = type;
		this.count = count;
//		this.comment = comment;
		this.sequ = SEQU++;
	}

	@Override
	public CTStackItem copy() {
//		return new Temp(mode, type, count, comment);
		return new Temp(mode, type, count);
	}

	@Override
	public String toString() {
//		return edMode() + "TEMP("+sequ+"): " + Scode.edTag(type.tag) + ", count=" + count + " " + comment;
		return edMode() + "TEMP("+sequ+"): " + Scode.edTag(type.tag) + ", count=" + count;
	}
}
