package iapx.CTStack;

import iapx.Kind;
import iapx.util.Type;

//Record Temp:StackItem;            --- Value is pushed on RT-stack
//begin  end;
public class Temp extends StackItem {

	public Temp(Type type) {
		super(type);
		this.kind = Kind.K_Temp;
	}

	
	public String toString() {
		return "TEMP: " +type;
	}

}
