/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.value;

import java.io.IOException;

import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
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
		oupt.writeByte(Scode.S_STRING);
		oupt.writeString(addr.segID);
		oupt.writeShort(addr.ofst);
		
		oupt.writeShort(lng);
	}

	public static StringValue read(AttributeInputStream inpt) throws IOException {
		return new StringValue(inpt);
	}


}
