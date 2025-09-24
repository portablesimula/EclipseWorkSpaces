package bec.compileTimeStack;

import bec.util.Type;
import bec.util.Util;

public abstract class CTStackItem {
	
	public enum Mode { VAL, REF, PROFILE }
	public Mode mode;
	public Type type;
	public int size;
	
	public CTStackItem copy() {
		Util.IERR("Method 'copy' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}
	
	public int repdist() {
		return (type == null)? 0 : type.size;
	}
	
	public String edMode() {
		if(mode == null) return("NULL    ");
		switch(mode) {
			case PROFILE: return("PRF ");
			case REF:     return("REF ");
			case VAL:     return("VAL ");
		}
		Util.IERR("");
		return null;
	}

}
