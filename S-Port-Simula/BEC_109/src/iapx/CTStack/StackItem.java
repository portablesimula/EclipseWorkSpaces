package iapx.CTStack;

import iapx.Kind;
import iapx.records.Objekt;
import iapx.util.Type;

//Record StackItem:Object;
//begin short integer repdist;
//      ref(StackItem) suc;
//      ref(StackItem) pred;
//end;
public class StackItem extends Objekt {
	public Kind kind;
	public Type type;
	
	public int repdist; //() { return (type == null)? 0 : type.size; }
	public StackItem suc;
	StackItem pred;

	public StackItem(Type type) {
		this.type = type;
		this.repdist = (type == null)? 0 : type.size;
	}

}
