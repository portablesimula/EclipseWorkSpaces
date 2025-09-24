package iapx.CTStack;

import iapx.Kind;
import iapx.util.Type;
import iapx.util.Util;
import iapx.value.MemAddr;

//Record Address:StackItem;
//begin infix(MemAddr) Objadr;   // Object Address
//      range(0:MaxWord) Offset; // Attribute Offset
//      range(0:2) ObjState;     // NotStacked ! FromConst ! Calculated
//      range(0:2) AtrState;     // NotStacked ! FromConst ! Calculated
//end;
public class Address extends StackItem {
	MemAddr Objadr; // Object Address
	int Offset;     // Attribute Offset
	int ObjState;   // NotStacked ! FromConst ! Calculated
	int AtrState;   // NotStacked ! FromConst ! Calculated

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
