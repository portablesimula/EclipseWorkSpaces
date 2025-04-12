package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.SVM_PUSHC;

public class TextValue extends Value {
	public ObjectAddress addr; // Pointer to a sequence of Characters.
	public int length;
	
	private static final boolean DEBUG = false;
	
	private TextValue() {
		this.type = Type.T_TEXT;
	}
	
	/**
	 * text_value ::= text long_string
	 */
	public static TextValue ofScode() {
//		System.out.println("TextValue.parse: curinstr=" + Scode.edInstr(Scode.curinstr));
		String str = Scode.inLongString();

		TextValue txtval = new TextValue();
//		TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
		if(Global.TSEG == null) Global.TSEG = new DataSegment("TSEG", Kind.K_SEG_CONST);

//		txtval.addr = Global.CSEG.emitChars(str, "Part of: "+str);			
		txtval.addr = Global.TSEG.emitChars(str, "Part of: "+str);			
		txtval.length = str.length();
		if(DEBUG) {
			System.out.println("TextValue.ofScode: str="+str+"  ==> "+txtval);
			Global.CSEG.dump(str);
			Util.IERR("");
		}
		return txtval;
	}
	
	public String getString() {
		// SENERE: SJEKK BRUKEN AV DENNE METODEN
		if(addr == null) return null;
		StringBuilder sb = new StringBuilder();
		ObjectAddress x = addr.addOffset(0);
		for(int i=0;i<length;i++) {
			IntegerValue val = (IntegerValue) x.load(); x.incrOffset();
//			if(val.type != Type.T_CHAR) Util.IERR(""+val.type);
			sb.append((char)val.value);
		}
		Util.IERR(""+sb);
		return sb.toString();
	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		dseg.emit(this.addr, "TEXT'CHRADR'oaddr: " + comment);
		dseg.emit(null, "TEXT'CHRADR'ofst:  " + comment);
		dseg.emit(IntegerValue.of(Type.T_INT, length), "TEXT'lng:   " + comment);
	}
	
	public String toString() {
		return "TEXT with length "+length+" at " + addr;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private TextValue(AttributeInputStream inpt) throws IOException {
//		System.out.println("BEGIN TextValue.read: " + this);
		this.type = Type.T_TEXT;
		length = inpt.readShort();
		addr = (ObjectAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("TextValue.read: " + this);
//		System.out.println("NEW TextValue: " + this);
//		Util.IERR("SJEKK DETTE");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_TEXT);
//		System.out.println("TextValue.write: addr.segID=" + addr.segID);
//		Util.IERR("");
		oupt.writeShort(length);
		addr.write(oupt);
	}

	public static TextValue read(AttributeInputStream inpt) throws IOException {
		return new TextValue(inpt);
	}


}
