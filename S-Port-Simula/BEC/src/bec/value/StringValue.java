package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Option;
import bec.util.Scode;
import bec.util.Type;

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
		String segID = inpt.readString();
		int ofst = inpt.readShort();
		addr = ObjectAddress.ofSegAddr(segID,	ofst);
		lng = inpt.readShort();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("Value.write: " + this);
		oupt.writeKind(Scode.S_STRING);
		oupt.writeString(addr.segID);
		oupt.writeShort(addr.ofst);
		
		oupt.writeShort(lng);
	}

	public static StringValue read(AttributeInputStream inpt) throws IOException {
		return new StringValue(inpt);
	}


}
