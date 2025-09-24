package iAPX.ctStack;

import iAPX.enums.Kind;
import iAPX.util.Type;
import svm.value.Value;

public class Coonst extends Temp {
	public Value value;

	public Coonst(Type type, Value value) {
		super(type);
		this.kind = Kind.K_Coonst;
		this.value = value;
	}

	public String toString() {
		return("Coonst: " + type + ", value=" + value);
	}
}
