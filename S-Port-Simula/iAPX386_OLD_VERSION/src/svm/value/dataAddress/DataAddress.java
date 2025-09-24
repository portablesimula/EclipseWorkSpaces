package svm.value.dataAddress;

import iAPX.util.Util;
import svm.value.Value;

public abstract class DataAddress extends Value {

	public Value load(int idx) {
		Util.IERR("MISSING load: "+this.getClass().getSimpleName());
		return null;
	}

	public Value load() {
		return load(0);
	}

	public void store(int idx, Value value, String comment) {
		Util.IERR("MISSING store: "+this.getClass().getSimpleName());
	}

}
