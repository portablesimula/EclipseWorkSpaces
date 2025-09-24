package bec.segment;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.TextValue;
import bec.value.Value;
import bec.value.dataAddress.DataAddress;
import bec.value.dataAddress.SegmentAddress;

public class DataSegment extends Segment {
	Vector<Value> values;
	Vector<String> comment;
	private int guard = -1;
	
	private static final boolean DEBUG = false;

	public DataSegment(String ident, int segmentKind) {
		super(ident, segmentKind);
//		IO.println("NEW DataSegment: " + this);
//		Thread.dumpStack();
		this.ident = ident.toUpperCase();
		this.segmentKind = segmentKind;
		values = new Vector<Value>();
		comment = new Vector<String>();
	}
	
	public void addGuard(int ofst) {
		guard = ofst;
	}
	
	public SegmentAddress ofOffset(int ofst) {
		return new SegmentAddress(this.ident,ofst);
	}
	
	public DataAddress nextAddress() {
		return new SegmentAddress(this.ident, values.size());
	}
	
	public int size() {
		return values.size();
	}
	
	public void store(int index, Value value) {
//		IO.println("DataSegment.store: " + this + "["+index+"] = "+value);
		if(index == guard) Util.IERR("FATAL ERROR: Attempt to change Guarded location: "+(new SegmentAddress(this.ident, index))+" from "+values.get(index)+" to "+value);
		values.set(index, value);
	}
	
	public Value load(int index) {
//		IO.println("DataSegment.load: "+this+", index="+index);
		try {
			return values.get(index);
		} catch(Exception e) {
			e.printStackTrace();
			IO.println("DataSegment.load: FAILED - SE PÅ DETTE SEINERE ETTER AT FEC => BEC ER TESTET !! e="+e);
//			this.dump("DataSegment.load: FAILED: " + e + " ");
//			Util.IERR("DataSegment.load: FAILED");
			return null;
		}
	}
	
	public DataAddress emit(final Value value,final String cmnt) {
		DataAddress addr = nextAddress();
		values.add(value);
		comment.add(cmnt);
		if(Global.PRINT_GENERATED_SVM_DATA) {
			int offset = ((SegmentAddress)addr).getOfst();
			listData("                                 ==> ", value, cmnt,offset);
		}
		return addr;
	}

	private void listData(String indent, Value value,String cmnt, int idx) {
		String line = ident + "[" + idx + "] ";
		while(line.length() < 8) line = " " +line;
		String val = ""+value;
		while(val.length() < 50) val = val + ' ';
		IO.println(indent + line + val + "   " + cmnt);
		
	}

	public void emitDefaultValue(int size, int repCount, String cmnt) {
//		IO.println("DataSegment.emitDefaultValue: size="+size);
		if(repCount < 1) Util.IERR("");
		boolean option = Global.PRINT_GENERATED_SVM_DATA;
		int LIMIT = 30;
		int n = size * repCount;
		for(int i=0;i<n;i++) {
			if(Global.PRINT_GENERATED_SVM_DATA && i == LIMIT) {
				IO.println("                                 ==> ... " + (n-LIMIT) + " more truncated");
				Global.PRINT_GENERATED_SVM_DATA = false;
			}
			emit(null, cmnt);
		}
		Global.PRINT_GENERATED_SVM_DATA = option;
	}
	
	public SegmentAddress emitChars(final String chars, final String cmnt) {
			SegmentAddress addr = (SegmentAddress) nextAddress();
			int n = chars.length();
			for(int i=0;i<n;i++) {
				emit(IntegerValue.of(Type.T_CHAR, chars.charAt(i)), cmnt);
			}
			return addr;
	}
	
	public DataAddress emitRepText(String cmnt) {
		Vector<TextValue> texts = new Vector<TextValue>();
		do { Scode.inputInstr(); texts.add(TextValue.ofScode());
		} while(Scode.nextByte() == Scode.S_TEXT);
		DataAddress addr = nextAddress();
		int n = texts.size();
		for(int i=0;i<n;i++) {
			TextValue tval = texts.get(i);
			if(DEBUG) IO.println("DataSegment.emitRepText["+i+"]: "+tval);
			
//			emit(tval.addr,"CHRADR");
//			emit(IntegerValue.of(Type.T_INT,0),"OFST");
//			emit(IntegerValue.of(Type.T_INT,tval.length),"LNG");
			tval.emit(this, cmnt);
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
			IO.println(line + value + "   " + comment.get(i));
		}
		IO.println("==================== " + title + ident + " END  ====================");
//		Util.IERR("");
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
		comment = new Vector<String>();
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			comment.add(inpt.readString());
			values.add(Value.read(inpt));
		}
//		IO.println("NEW IMPORT: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("DataSegment.Write: " + this + ", Size=" + values.size());
//		oupt.writeInstr(Scode.S_BSEG);
		oupt.writeKind(segmentKind);
		oupt.writeString(ident);
		oupt.writeShort(values.size());
		for(int i=0;i<values.size();i++) {
			oupt.writeString(comment.get(i));
			Value val = values.get(i);
//			IO.println("DataSegment.Write: "+val);
			if(val == null)
				 oupt.writeInstr(Scode.S_NULL);
			else val.write(oupt);
		}
	}

	public static DataSegment readObject(AttributeInputStream inpt, int segmentKind) throws IOException {
//		int segmentKind = inpt.readKind();
		String ident = inpt.readString();
//		IO.println("DataSegment.readObject: ident="+ident+", segmentKind="+segmentKind);
		DataSegment seg = new DataSegment(ident, segmentKind, inpt);
		if(Global.ATTR_INPUT_TRACE) IO.println("DataSegment.Read: " + seg);
		if(Global.ATTR_INPUT_DUMP) seg.dump("DataSegment.readObject: ");
//		Util.IERR("");
		return seg;
	}
	

}
