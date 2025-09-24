package iAPX.ctStack;

import iAPX.dataAddress.MemAddr;
import iAPX.enums.Kind;
import iAPX.util.Type;
import iAPX.util.Util;

public class Address extends StackItem {
//	Record Address:StackItem;
//	begin MemAddr Objadr;   // Object Address
//	      int Offset; // Attribute Offset
//	      range(0:2) ObjState;     // NotStacked ! FromConst ! Calculated
//	      range(0:2) AtrState;     // NotStacked ! FromConst ! Calculated
//	end;

	public final static int NotStacked=0,FromConst=1,Calculated=2;
	public int objState;     // NotStacked ! FromConst ! Calculated
	public int atrState;     // NotStacked ! FromConst ! Calculated

	public MemAddr objadr;   // Object Address
	public int offset; // Attribute Offset

	public Address(Type type, MemAddr objadr, int offset) {
		super(type);
		this.kind = Kind.K_Address;
		this.objadr = objadr;
		this.offset = offset;
	}
	
	public String edState(int state) {
		switch(state) {
			case NotStacked: return "NotStacked";
			case FromConst:  return "FromConst";
			case Calculated: return "Calculated";
			default: Util.IERR(""); return null;
		}
	}
	
	public String toString() {
		return "Address: " + objadr + ", offset="+offset + ", objState="+edState(objState) + ", atrState="+edState(atrState);
	}
}
