package bec.compileTimeStack;

import bec.util.Scode;
import bec.util.Type;

public class TempItem extends CTStackItem {
	int count;
	String comment;
	int sequ;
	
	private static int SEQU = 1;
	
	// Value is pushed on RT-stack
	public TempItem(Mode mode, Type type, int count, String comment) {
		this.mode = mode;
		this.type = type;
		this.count = count;
		this.comment = comment;
		this.sequ = SEQU++;
	}

	@Override
	public CTStackItem copy() {
		return new TempItem(mode, type, count, comment);
	}

	@Override
	public String toString() {
		return edMode() + "TEMP("+sequ+"): " + Scode.edTag(type.tag) + ", count=" + count + " " + comment;
	}
}
