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
import bec.value.BooleanValue;
import bec.value.DotAddress;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.LongRealValue;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.RealValue;
import bec.value.TextValue;
import bec.value.Value;

public class DataSegment extends Segment {
	Vector<Value> values;
	Vector<String> comment;
	
	private static final boolean DEBUG = false;

	public DataSegment(String ident, int segmentKind) {
		super(ident, segmentKind);
//		System.out.println("NEW DataSegment: " + this);
//		Thread.dumpStack();
		this.ident = ident.toUpperCase();
		this.segmentKind = segmentKind;
		values = new Vector<Value>();
		comment = new Vector<String>();
	}
	
	public ObjectAddress ofOffset(int ofst) {
		return ObjectAddress.ofSegAddr(this,ofst);
	}
	
	public ObjectAddress nextAddress() {
		return ObjectAddress.ofSegAddr(this,values.size());
	}
	
	public void store(int index, Value value) {
		values.set(index, value);
	}
	
	public Value load(int index) {
//		System.out.println("DataSegment.load: "+this+", index="+index);
		try {
			return values.get(index);
		} catch(Exception e) {
//			e.printStackTrace();
			this.dump("DataSegment.load: ");
			Util.IERR("");
			return null;
		}
	}
	
	public ObjectAddress emit(final Value value,final String cmnt) {
		ObjectAddress addr = nextAddress();
		values.add(value);
		comment.add(cmnt);
		if(Global.PRINT_GENERATED_SVM_DATA)
			listData("                                 ==> ", value, cmnt, addr.getOfst());
		return addr;
	}

	private void listData(String indent, Value value,String cmnt, int idx) {
		String line = ident + "[" + idx + "] ";
		while(line.length() < 8) line = " " +line;
		String val = ""+value;
		while(val.length() < 50) val = val + ' ';
		System.out.println(indent + line + val + "   " + cmnt);
		
	}

	public void emitDefaultValue(int size, int repCount, String cmnt) {
//		System.out.println("DataSegment.emitDefaultValue: size="+size);
		if(repCount < 1) Util.IERR("");
		int n = size * repCount;
//		for(int i=0;i<size;i++) {
		for(int i=0;i<n;i++) {
			emit(null, cmnt);
		}
	}
	
	public ObjectAddress emitChars(final String chars, final String cmnt) {
		ObjectAddress addr = nextAddress();
		int n = chars.length();
		for(int i=0;i<n;i++) {
			emit(IntegerValue.of(Type.T_CHAR, chars.charAt(i)), cmnt);
		}
		return addr;
	}
	
	public ObjectAddress emitRepText(String cmnt) {
		Vector<TextValue> texts = new Vector<TextValue>();
		do { Scode.inputInstr(); texts.add(TextValue.ofScode());
		} while(Scode.nextByte() == Scode.S_TEXT);
		ObjectAddress addr = nextAddress();
		int n = texts.size();
		for(int i=0;i<n;i++) {
			TextValue tval = texts.get(i);
			if(DEBUG) System.out.println("DataSegment.emitRepText["+i+"]: "+tval);
			emit(tval.addr,"CHRADR");
			emit(IntegerValue.of(Type.T_INT,0),"OFST");
			emit(IntegerValue.of(Type.T_INT,tval.length),"LNG");
//			ELLER:
//			emit(IntegerValue.of(Type.T_INT,tval.length),"LNG");
//			emit(IntegerValue.of(Type.T_INT,0),"OFST");
//			emit(tval.addr,"CHRADR");
		}
		return addr;
	}

//	public ObjectAddress emitRepetitionValue(String comment) {
//		if(Scode.nextByte() == Scode.S_TEXT) return emitRepText(comment);
////		MemAddr addr = Global.DSEG.nextAddress();
//		ObjectAddress addr = nextAddress();
//		if(DEBUG) {
//			System.out.println("DataSegment.emitRepetitionValue: "+Scode.edInstr(Scode.nextByte())+"  Comment="+comment);
//		}
//	LOOP:while(true) {
//			if(DEBUG)
//				System.out.println("DataSegment.emitRepetitionValue: "+Scode.edInstr(Scode.nextByte())+"  Comment="+comment);
//			switch(Scode.nextByte()) {
//				case Scode.S_TEXT:     Scode.inputInstr(); emit(TextValue.ofScode(), comment); break;
//			    case Scode.S_C_INT:    Scode.inputInstr(); emit(IntegerValue.ofScode_INT(), comment); break;
//			    case Scode.S_C_CHAR:   Scode.inputInstr(); emit(IntegerValue.ofScode_CHAR(), comment); break;
//			    case Scode.S_C_SIZE:   Scode.inputInstr(); emit(IntegerValue.ofScode_SIZE(), comment); break;
//			    case Scode.S_C_REAL:   Scode.inputInstr(); emit(RealValue.ofScode(), comment); break;
//			    case Scode.S_C_LREAL:  Scode.inputInstr(); emit(LongRealValue.ofScode(), comment); break;
//			    case Scode.S_TRUE:     Scode.inputInstr(); emit(BooleanValue.of(true), comment); break;
//			    case Scode.S_FALSE:    Scode.inputInstr(); emit(BooleanValue.of(false), comment); break;
//			    case Scode.S_NOSIZE:   Scode.inputInstr(); emit(null, comment); break;
//			    case Scode.S_ANONE:    Scode.inputInstr(); emit(null, comment); break;
//			    case Scode.S_NOWHERE:  Scode.inputInstr(); emit(null, comment); break;
//			    case Scode.S_NOBODY:   Scode.inputInstr(); emit(null, comment); break;
//			    case Scode.S_ONONE:    Scode.inputInstr(); emit(null, comment); break;
//			    case Scode.S_GNONE:    Scode.inputInstr(); emit(null, comment); emit(null, comment); break;
//			    case Scode.S_C_AADDR:  Scode.inputInstr(); emit(IntegerValue.ofScode_AADDR(), comment); break;
//			    case Scode.S_C_PADDR:  Scode.inputInstr(); emit(ProgramAddress.ofScode(Type.T_PADDR), comment); break;
//			    case Scode.S_C_RADDR:  Scode.inputInstr(); emit(ProgramAddress.ofScode(Type.T_RADDR), comment); break;
//			    case Scode.S_C_OADDR:  Scode.inputInstr(); emit(ObjectAddress.ofScode(), comment); break;
//			    case Scode.S_C_RECORD: Scode.inputInstr(); emitRecordValue(comment); break;
//			    case Scode.S_C_DOT:
//			    	Scode.inputInstr();
//					Value dotAddr = DotAddress.ofScode();
////					System.out.println("DataSegment.emitRepetitionValue: "+dotAddr.getClass().getSimpleName()+"  "+dotAddr);
//					if(dotAddr instanceof GeneralAddress gaddr) {
//						emit(gaddr.base, comment);
//						emit(IntegerValue.of(Type.T_INT, gaddr.ofst), comment);						
//					} else if(dotAddr instanceof GeneralAddress oaddr) {
//						emit(oaddr, comment);
//					} else {
//						emit(dotAddr, comment);
//					}
//			    	break;
//			    case Scode.S_C_GADDR:
//			    	Scode.inputInstr();
////			    	emit(GeneralAddress.ofScode(), comment);
//					GeneralAddress gaddr = GeneralAddress.ofScode();
//					emit(gaddr.base, comment);
//					emit(IntegerValue.of(Type.T_INT, gaddr.ofst), comment);
//					break;
//				default:
//					if(DEBUG)
//						System.out.println("DataSegment.emitRepetitionValue: TERMINATED BY: "+Scode.edInstr(Scode.nextByte())+"  Comment="+comment);
//					break LOOP;
//			}
//		}
//		if(Global.ATTR_OUTPUT_TRACE)
//			this.dump("emitRepetitionValue: ");
////		System.out.println("DataSegment.emitRepetitionValue: " + addr);
//		return addr;
//	}

	
//	/**
//	 *	record_value
//	 *		::= c-record structured_type
//	 *				 <attribute_value>+
//	 *			endrecord
//	 *
//	 * 			attribute_value
//	 * 				::= attr attribute:tag type repetition_value
//	 */
//	private void emitRecordValue(String comment) {
//		Scode.ofScode(); 
//		Scode.inputInstr();
//		while(Scode.curinstr == Scode.S_ATTR) {
//			int atag = Scode.ofScode();
//			Type type = Type.ofScode();
////			System.out.println("DataSegment.emitRecordValue'S_ATTR: "+Scode.edTag(atag)+"  "+type);
//
//			emitRepetitionValue(comment + "  " + Scode.edTag(atag) + "  " + Scode.edTag(type.tag));
//			Scode.inputInstr();
////			System.out.println("DataSegment.emitRecordValue: Curinstr="+Scode.edInstr(Scode.curinstr));
//		}
//		if(Scode.curinstr != Scode.S_ENDRECORD) Util.IERR("Syntax error in record-constant");
//	}


	@Override
	public void dump(String title) {
		dump(title,0,values.size());
	}
	
	@Override
	public void dump(String title,int from,int to) {
		if(values.size() == 0) return;
		System.out.println("==================== " + title + ident + " DUMP ====================" + this.hashCode());
		for(int i=from;i<to;i++) {
			String line = "" + i + ": ";
			while(line.length() < 8) line = " " +line;
			String value = ""+values.get(i);
			while(value.length() < 25) value = value + ' ';
			System.out.println(line + value + "   " + comment.get(i));
		}
		System.out.println("==================== " + title + ident + " END  ====================");
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
//		System.out.println("NEW IMPORT: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("DataSegment.Write: " + this + ", Size=" + values.size());
//		oupt.writeInstr(Scode.S_BSEG);
		oupt.writeKind(segmentKind);
		oupt.writeString(ident);
		oupt.writeShort(values.size());
		for(int i=0;i<values.size();i++) {
			oupt.writeString(comment.get(i));
			Value val = values.get(i);
//			System.out.println("DataSegment.Write: "+val);
			if(val == null)
				 oupt.writeInstr(Scode.S_NULL);
			else val.write(oupt);
		}
	}

	public static DataSegment readObject(AttributeInputStream inpt, int segmentKind) throws IOException {
//		int segmentKind = inpt.readKind();
		String ident = inpt.readString();
//		System.out.println("DataSegment.readObject: ident="+ident+", segmentKind="+segmentKind);
		DataSegment seg = new DataSegment(ident, segmentKind, inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("DataSegment.Read: " + seg);
		if(Global.ATTR_INPUT_DUMP) seg.dump("DataSegment.readObject: ");
//		Util.IERR("");
		return seg;
	}
	

}
