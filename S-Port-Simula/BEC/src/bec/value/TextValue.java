package bec.value;

import java.io.IOException;
import java.util.Vector;

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
//	private ObjectAddress addr; // Pointer to a sequence of Characters.
//	private int length;
	public String textValue;
	
	private static final boolean DEBUG = false;
	
	private TextValue() {
		this.type = Type.T_TEXT;
	}
	
	/**
	 * text_value ::= text long_string
	 */
	public static TextValue ofScode() {
//		System.out.println("TextValue.parse: curinstr=" + Scode.edInstr(Scode.curinstr));
//		String textValue = Scode.inLongString();

		TextValue txtval = new TextValue();
		txtval.textValue = Scode.inLongString();
		
//		TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
//		if(Global.TSEG == null) Global.TSEG = new DataSegment("TSEG", Kind.K_SEG_CONST);
//		txtval.addr = Global.TSEG.emitChars(txtval.textValue, "Part of: "+txtval.textValue);			
//		txtval.length = txtval.textValue.length();
		if(DEBUG) {
			System.out.println("TextValue.ofScode: textValue="+txtval.textValue+"  ==> "+txtval);
			Global.CSEG.dump(txtval.textValue);
			Util.IERR("");
		}
		return txtval;
	}
	
	public ObjectAddress emitChars(DataSegment dseg) {
//		TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
//		if(Global.TSEG == null) Global.TSEG = new DataSegment("TSEG", Kind.K_SEG_CONST);
		return dseg.emitChars(textValue, "Part of: "+textValue);			
	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		ObjectAddress addr = emitChars(Global.TSEG);
		dseg.emit(addr, "TEXT'CHRADR'oaddr: " + comment);
		dseg.emit(null, "TEXT'CHRADR'ofst:  " + comment);
		dseg.emit(IntegerValue.of(Type.T_INT, textValue.length()), "TEXT'lng:   " + comment);
	}
	
	public String toString() {
//		return "TEXT with length "+length+" at " + addr;
		return "TEXT: " + textValue;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private TextValue(AttributeInputStream inpt) throws IOException {
//		System.out.println("BEGIN TextValue.read: " + this);
		this.type = Type.T_TEXT;
//		length = inpt.readShort();
//		addr = (ObjectAddress) Value.read(inpt);
		textValue = inpt.readString();
		if(Global.ATTR_INPUT_TRACE) System.out.println("TextValue.read: " + this);
//		System.out.println("NEW TextValue: " + this);
//		Util.IERR("SJEKK DETTE");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_TEXT);
//		System.out.println("TextValue.write: addr.segID=" + addr.segID);
//		Util.IERR("");
//		oupt.writeShort(length);
//		addr.write(oupt);
		oupt.writeString(textValue);
	}

	public static TextValue read(AttributeInputStream inpt) throws IOException {
		return new TextValue(inpt);
	}


}
