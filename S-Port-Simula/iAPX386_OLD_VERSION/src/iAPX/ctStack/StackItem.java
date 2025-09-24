package iAPX.ctStack;

import iAPX.enums.Kind;
import iAPX.util.Type;

public class StackItem {
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
