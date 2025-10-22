package bec.compileTimeStack;

import bec.util.Scode;
import bec.util.Type;

public class Temp extends CTStackItem {
	int count;
	
	public Temp(Mode mode, Type type, int count) {
		this.mode = mode;
		this.type = type;
		this.count = count;
	}

	@Override
	public CTStackItem copy() {
		return new Temp(mode, type, count);
	}

	@Override
	public String toString() {
		return edMode() + "TEMP: " + Scode.edTag(type.tag) + ", count=" + count;
	}
}
