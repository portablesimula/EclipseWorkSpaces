/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.value;

import java.io.IOException;

import bec.Global;
import bec.Option;
import bec.scode.Scode;
import bec.scode.Sinstr;
import bec.scode.Type;
import bec.segment.DataSegment;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Util;

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
//		IO.println("TextValue.parse: curinstr=" + Sinstr.edInstr(Scode.curinstr));
//		String textValue = Scode.inLongString();

		TextValue txtval = new TextValue();
		txtval.textValue = Scode.inLongString();
		
//		TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
//		if(Global.TSEG == null) Global.TSEG = new DataSegment("TSEG", Kind.K_SEG_CONST);
//		txtval.addr = Global.TSEG.emitChars(txtval.textValue, "Part of: "+txtval.textValue);			
//		txtval.length = txtval.textValue.length();
		if(DEBUG) {
			IO.println("TextValue.ofScode: textValue="+txtval.textValue+"  ==> "+txtval);
			Global.CSEG.dump(txtval.textValue);
			Util.IERR("");
		}
		return txtval;
	}
	
	public ObjectAddress emitChars(DataSegment dseg) {
//		TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
//		if(Global.TSEG == null) Global.TSEG = new DataSegment("TSEG", Kind.K_SEG_CONST);
		return dseg.emitChars(textValue);			
	}
	
	@Override
	public void emit(DataSegment dseg) {
		ObjectAddress addr = emitChars(Global.TSEG);
		dseg.emit(addr);
		dseg.emit(null);
		dseg.emit(IntegerValue.of(Type.T_INT, textValue.length()));
	}
	
	public String toString() {
//		return "TEXT with length "+length+" at " + addr;
		return "TEXT: " + textValue;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private TextValue(AttributeInputStream inpt) throws IOException {
		this.type = Type.T_TEXT;
		textValue = inpt.readString();
		if(Option.ATTR_INPUT_TRACE) IO.println("TextValue.read: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("Value.write: " + this);
		oupt.writeByte(Sinstr.S_TEXT);
		oupt.writeString(textValue);
	}

	public static TextValue read(AttributeInputStream inpt) throws IOException {
		return new TextValue(inpt);
	}


}
