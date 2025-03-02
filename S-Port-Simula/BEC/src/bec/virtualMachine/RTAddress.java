package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.AddressItem;
import bec.util.Global;
import bec.value.ObjectAddress;
import bec.value.Value;

public class RTAddress {
	public ObjectAddress objadr;
	public int offset;
	public RTIndex objIndex;
	public RTIndex atrIndex;

	public RTAddress(AddressItem itm) {
		this.objadr = itm.objadr;
		this.offset = itm.offset;
		if(itm.objReg > 0) this.objIndex = new RTIndex(itm.size, itm.objReg);
		if(itm.atrReg > 0) this.atrIndex = new RTIndex(itm.size, itm.atrReg);
	}

	public RTAddress(ObjectAddress objadr) {
		this.objadr = objadr;
	}
	
	public ObjectAddress toObjectAddress() {
		ObjectAddress target = this.objadr.ofset(this.offset);
		if(this.objIndex != null) target.ofst += RTRegister.getIndex(objIndex.reg) * objIndex.size;
		if(this.atrIndex != null) target.ofst += RTRegister.getIndex(atrIndex.reg) * atrIndex.size;
		return target;
	}
	
	public Value load() {
		return toObjectAddress().load();
	}
	
	public void store(Value val, String comment) {
		toObjectAddress().store(val, comment);
	}

	public String toString() {
		String s = "" + objadr + "[" + offset;
		if(objIndex != null) s += "+" + objIndex;
		if(atrIndex != null) s += "+" + atrIndex;
		return s + "]";
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RTAddress(AttributeInputStream inpt) throws IOException {
		boolean present = inpt.readBoolean();
		if(present) this.objadr = (ObjectAddress) Value.read(inpt);
		this.offset = inpt.readShort();
		present = inpt.readBoolean();
		if(present) this.objIndex = RTIndex.read(inpt);
		present = inpt.readBoolean();
		if(present) this.atrIndex = RTIndex.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

//	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
//		objadr.write(oupt);
		if(objadr != null) {
			oupt.writeBoolean(true);
			objadr.write(oupt);
		} else oupt.writeBoolean(false);
		oupt.writeShort(offset);
		if(objIndex != null) {
			oupt.writeBoolean(true);
			objIndex.write(oupt);
		} else oupt.writeBoolean(false);
		if(atrIndex != null) {
			oupt.writeBoolean(true);
			atrIndex.write(oupt);
		} else oupt.writeBoolean(false);
	}

	public static RTAddress read(AttributeInputStream inpt) throws IOException {
		return new RTAddress(inpt);
	}


}
