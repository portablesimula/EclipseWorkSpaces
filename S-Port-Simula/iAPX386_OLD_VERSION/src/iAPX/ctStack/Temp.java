package iAPX.ctStack;

import iAPX.enums.Kind;
import iAPX.util.Type;

public class Temp extends StackItem {
//	Record Temp:StackItem;            // Value is pushed on RT-stack
//	begin  end;

	public Temp(Type type) {
		super(type);
		this.kind = Kind.K_Temp;
	}
	
	public String toString() {
		return("Temp: " + type);
	}
}
