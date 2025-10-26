/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.segment;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.util.Option;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.TextValue;
import bec.value.Value;

/// Data Segment.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/segment/DataSegment.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class DataSegment extends Segment {
	Vector<Value> values;
	private int guard = -1;
	
	private static final boolean DEBUG = false;

	public DataSegment(String ident, int segmentKind) {
		super(ident, segmentKind);
		this.ident = ident.toUpperCase();
		this.segmentKind = segmentKind;
		values = new Vector<Value>();
	}
	
	public void addGuard(int ofst) {
		guard = ofst;
	}
	
	public ObjectAddress ofOffset(int ofst) {
		return ObjectAddress.ofSegAddr(this,ofst);
	}
	
	public ObjectAddress nextAddress() {
		return ObjectAddress.ofSegAddr(this,values.size());
	}
	
	public int size() {
		return values.size();
	}
	
	public void store(int index, Value value) {
		if(index == guard) Util.IERR("FATAL ERROR: Attempt to change Guarded location: "+ObjectAddress.ofSegAddr(this, index)+" from "+values.get(index)+" to "+value);
		values.set(index, value);
	}
	
	public Value load(int index) {
		return values.get(index);
	}
	
	public ObjectAddress emit(final Value value) {
		ObjectAddress addr = nextAddress();
		values.add(value);
		if(Option.PRINT_GENERATED_SVM_DATA)
			listData("                                 ==> ", value, addr.getOfst());
		return addr;
	}

	private void listData(String indent, Value value, int idx) {
		String line = ident + "[" + idx + "] ";
		while(line.length() < 8) line = " " +line;
		String val = ""+value;
//		while(val.length() < 50) val = val + ' ';
		IO.println(indent + line + val);
		
	}

	public void emitDefaultValue(int size, int repCount) {
//		IO.println("DataSegment.emitDefaultValue: size="+size);
		if(repCount < 1) Util.IERR("");
		boolean option = Option.PRINT_GENERATED_SVM_DATA;
		int LIMIT = 30;
		int n = size * repCount;
		for(int i=0;i<n;i++) {
			if(Option.PRINT_GENERATED_SVM_DATA && i == LIMIT) {
				IO.println("                                 ==> ... " + (n-LIMIT) + " more truncated");
				Option.PRINT_GENERATED_SVM_DATA = false;
			}
			emit(null);
		}
		Option.PRINT_GENERATED_SVM_DATA = option;
	}
	
	public ObjectAddress emitChars(final String chars) {
		ObjectAddress addr = nextAddress();
		int n = chars.length();
		for(int i=0;i<n;i++) {
			emit(IntegerValue.of(Type.T_CHAR, chars.charAt(i)));
		}
		return addr;
	}
	
	public ObjectAddress emitRepText() {
		Vector<TextValue> texts = new Vector<TextValue>();
		do { Scode.inputInstr(); texts.add(TextValue.ofScode());
		} while(Scode.nextByte() == Scode.S_TEXT);
		ObjectAddress addr = nextAddress();
		int n = texts.size();
		for(int i=0;i<n;i++) {
			TextValue tval = texts.get(i);
			if(DEBUG) IO.println("DataSegment.emitRepText["+i+"]: "+tval);
			tval.emit(this);
		}
		return addr;
	}



	@Override
	public void dump(String title) {
		dump(title,0,values.size());
	}
	
	@Override
	public void dump(String title,int from,int to) {
		if(values.size() == 0) return;
		IO.println("==================== " + title + ident + " DUMP ====================" + this.hashCode());
		for(int i=from;i<to;i++) {
			String line = "" + i + ": ";
			while(line.length() < 8) line = " " +line;
			String value = ""+values.get(i);
			while(value.length() < 25) value = value + ' ';
			IO.println(line + value);
		}
		IO.println("==================== " + title + ident + " END  ====================");
	}
	
	public String toString() {
		if(segmentKind == Kind.K_SEG_CONST)
			return "ConstSegment \"" + ident + '"';
		return "DataSegment \"" + ident + '"';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private DataSegment(String ident, int segmentKind, AttributeInputStream inpt) throws IOException {
		super(ident, segmentKind);
		values = new Vector<Value>();
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			values.add(Value.read(inpt));
		}
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("DataSegment.Write: " + this + ", Size=" + values.size());
		oupt.writeByte(segmentKind);
		oupt.writeString(ident);
		oupt.writeShort(values.size());
		for(int i=0;i<values.size();i++) {
			Value val = values.get(i);
			if(val == null)
				 oupt.writeByte(Scode.S_NULL);
			else val.write(oupt);
		}
	}

	public static DataSegment readObject(AttributeInputStream inpt, int segmentKind) throws IOException {
		String ident = inpt.readString();
		DataSegment seg = new DataSegment(ident, segmentKind, inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("DataSegment.Read: " + seg);
		if(Option.ATTR_INPUT_DUMP) seg.dump("DataSegment.readObject: ");
		return seg;
	}
	

}
