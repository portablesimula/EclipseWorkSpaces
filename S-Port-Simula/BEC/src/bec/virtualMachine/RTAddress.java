package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.AddressItem;
import bec.util.Global;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;

public class RTAddress {
	public ObjectAddress objadr;
	public int offset;
	public AddressItem.State objState;

	public RTAddress(AddressItem itm) {
//		this.objState  = itm.objState;
		this.objState  = itm.atrState;
		this.objadr = itm.objadr;
		this.offset = itm.offset;
	}

	public RTAddress(ObjectAddress objadr) {
		this.objadr = objadr;
	}

	public ObjectAddress toObjectAddress() {
		if(objState == AddressItem.State.Calculated) {
			ObjectAddress temp = RTStack.popGADDR();
			ObjectAddress res = temp.addOffset(offset);
			return res;
		} else {
			return this.objadr.addOffset(offset);
		}
	}
	
	public Value load() {
		return toObjectAddress().load();
	}
	
	public void store(Value val, String comment) {
		toObjectAddress().store(val, comment);
	}

	public String toString() {
		if(objadr != null) {
			String s = "SEGADR["+objadr.segID+':' + objadr.getOfst() + "+" + offset;
			return s + "]";			
		}
		String relID = null;
		if(objState != null) switch(objState) {
			case Calculated: relID = "Temp";  break;
			case FromConst:  relID = "Const"; break;
			case NotStacked: relID = "Frame"; break;
			default: Util.IERR("");
		}
		int ofst = (objadr==null)? 0 : objadr.getOfst();
		String s = "RELADR["+relID+':' + ofst + "+" + offset;
		return s + "]";
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RTAddress(AttributeInputStream inpt) throws IOException {
		boolean present = inpt.readBoolean();
		if(present) this.objadr = (ObjectAddress) Value.read(inpt);
		this.offset = inpt.readShort();
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
	}

	public static RTAddress read(AttributeInputStream inpt) throws IOException {
		return new RTAddress(inpt);
	}


}
