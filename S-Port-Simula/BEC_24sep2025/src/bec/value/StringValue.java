package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.value.dataAddress.SegmentAddress;

public class StringValue extends Value {
	public SegmentAddress addr;
	public int lng;
	
	public StringValue(SegmentAddress addr2, int lng) {
		this.type = Type.T_STRING;
		this.addr = addr2;
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
//		IO.println("NEW StringValue: ");
//		addr = DataAddress.read(inpt);
		String segID = inpt.readString();
		int ofst = inpt.readShort();
//		addr = new DataAddress(segID,	ofst);
//		addr = DataAddress.ofSegAddr(segID,	ofst);
		addr = new SegmentAddress(segID,	ofst);
//		IO.println("NEW StringValue: addr="+addr);
		lng = inpt.readShort();
//		IO.println("NEW StringValue: " + this);
//		Util.IERR("SJEKK DETTE");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("Value.write: " + this);
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
