package bec.compileTimeStack;

import bec.util.Type;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTRegister;

//Record Address:StackItem;
//begin infix(MemAddr) Objadr;   -- Object Address
//      range(0:MaxWord) Offset; -- Attribute Offset
//      range(0:2) ObjState;     -- NotStacked ! FromConst ! Calculated
//      range(0:2) AtrState;     -- NotStacked ! FromConst ! Calculated
//end;
public class AddressItem extends StackItem {
//	Visible Define NotStacked=0,FromConst=1,Calculated=2;
	
//	public enum ObjState { NotStacked , FromConst , Calculated }
//	public enum ObjState { NotStacked , objFromConst , Remote }
//	public enum ObjState { zzNotStacked , Remote }
	
//	public enum AtrState { NotStacked , FromConst , Calculated }
	public enum AtrState { NotStacked , FromConst , Indexed }
	public ObjectAddress objadr;
	public int offset;
	public boolean isRemoteBase; // objaddr is base address before dot
	public boolean isRefered;
//	public ObjState objState;
	public AtrState atrState;
//	public int objReg;
//	public int atrReg;
	public int xReg;
	
	public AddressItem(Type type, int offset, ObjectAddress objadr) {
		this.type = type;
//		this.size = DataType.TTAB[type].nbyte;
		this.size = type.size();
		this.objadr = objadr;
		this.offset = offset;
//		this.objState = ObjState.zzNotStacked;
		this.atrState = AtrState.NotStacked;
	}

	public String toString() {
		String s = "" + type + " AT " + objadr + "[" + offset;
		if(xReg > 0) s += "+" + RTRegister.edReg(xReg);
//		if(objReg > 0) s += "+" + RTRegister.edReg(objReg);
//		if(atrReg > 0) s += "+" + RTRegister.edReg(atrReg);
//				+ " objState="+objState + ", atrState="+atrState;
		return s  + "]";
	}

}
