package iapx.CTStack;

import iapx.Kind;
import iapx.util.Type;
import iapx.value.Value;

//Record Coonst:Temp;               --- Value is also in 'itm'
//begin infix(ValueItem) itm;
//end;
public class Coonst extends Temp {
	public Value itm;
	
	public Coonst(Type type, Value itm) {
		super(type);
		this.kind = Kind.K_Coonst;
		this.itm = itm;
	}

	
	public String toString() {
		return "CNST: " +type + " " + itm;
	}

}
