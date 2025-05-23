package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class StringValue extends Value {
	public ObjectAddress addr;
	public int lng;
	
	public StringValue(ObjectAddress addr, int lng) {
		this.type = Type.T_STRING;
		this.addr = addr;
		this.lng = lng;
	}
	
	public String toString() {
		return "String: " + addr + ", lng="+lng;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private StringValue(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_STRING;
//		System.out.println("NEW StringValue: ");
//		addr = ObjectAddress.read(inpt);
		String segID = inpt.readString();
		int ofst = inpt.readShort();
		addr = new ObjectAddress(segID,	ofst);
//		System.out.println("NEW StringValue: addr="+addr);
		lng = inpt.readShort();
//		System.out.println("NEW StringValue: " + this);
//		Util.IERR("SJEKK DETTE");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_STRING);
//		addr.write(oupt);
		oupt.writeString(addr.segID);
		oupt.writeShort(addr.ofst);
		
		oupt.writeShort(lng);
	}

	public static StringValue read(AttributeInputStream inpt) throws IOException {
		return new StringValue(inpt);
	}


}
