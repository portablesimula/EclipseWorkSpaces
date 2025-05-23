package bec.value;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Attribute;
import bec.descriptor.ConstDescr;
import bec.descriptor.RecordDescr;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;

public class RecordValue extends Value {
	public Tag tag;
	public Vector<Value> attrValues;
	
	private static final boolean DEBUG = false;
	
	private RecordValue() {
		attrValues = new Vector<Value>();
	}
	
	public static RecordValue ofString(ObjectAddress addr, IntegerValue lng) {
		RecordValue recValue = new RecordValue();
		recValue.type = Type.T_STRING;
		recValue.tag = new Tag(Scode.TAG_STRING);
		recValue.attrValues.add(addr);
		recValue.attrValues.add(null);
//		recValue.attrValues.add(IntegerValue.of(Type.T_INT, 0));
		recValue.attrValues.add(lng);
//		Util.IERR("");
		return recValue;
	}

	/**
	 * record_value
	 * 		::= c-record structured_type
	 * 			<attribute_value>+ endrecord
	 * 
	 * 		structured_type ::= record_tag:tag
	 * 
	 * 		attribute_value
	 * 			::= attr attribute:tag type repetition_value
	 * 
	 * End-Condition: Scode'nextByte = First byte after ENDRECORD
	 */
	public static RecordValue ofScode() {
		RecordValue recValue = new RecordValue();
		recValue.tag = Tag.ofScode();
		RecordDescr rec = (RecordDescr) Global.getMeaning(recValue.tag);
		if(DEBUG) System.out.println("RecordValue.ofScode: rec.size="+rec.size);
		
		for(int i=0;i<rec.size;i++)
			 recValue.attrValues.add(null);
		
		while(Scode.accept(Scode.S_ATTR)) {
			Tag tag = Tag.ofScode();
			Type type = Type.ofScode();
			RepetitionValue atrvalue = RepetitionValue.ofScode();
			Attribute attr = (Attribute) Global.getMeaning(tag);
			if(DEBUG) System.out.println("RecordValue.ofScode: "+attr);
			
			if(attr.repCount == 0) {
				if(DEBUG) System.out.println("RecordValue.ofScode: FIXREP = " + ConstDescr.fixrepTail);
				int n = 0;
				for(Value val:atrvalue.values) {
					if(val != null && val.type == Type.T_TEXT && attr.type == Type.T_CHAR) {
						n += recValue.addChars((TextValue) val);
					} else n += recValue.addValue(val);
				}
				if(ConstDescr.fixrepTail > 0 && ConstDescr.fixrepTail < n) Util.IERR("Too many elements in repetition: " + n + " > " + ConstDescr.fixrepTail);
				for(int i=n;i<ConstDescr.fixrepTail;i++)
					recValue.addValue(null);
			} else {
				int idx = attr.rela;
				for(Value val:atrvalue.values) {
//					System.out.println("RecordValue.ofScode: SET VALUE: rela=" + attr.rela + ", idx=" + idx + ", val="+val);
					idx = setValue(recValue.attrValues, idx, val);
				}
//				if(idx > (attr.repCount * attr.size)) Util.IERR("idx="+idx+" > repCount="+attr.repCount+"  attr.size="+attr.size);
			}
		}
		
		if(DEBUG) {
			recValue.dump("RecordValue.ofScode: FIRST");
			recValue.print("RecordValue.ofScode: ");
//			Util.IERR("");
		}
		
		Scode.expect(Scode.S_ENDRECORD);
		
//		if(Scode.inputTrace > 3) printTree(0);
		RecordDescr recordDescr = (RecordDescr) Global.DISPL.get(recValue.tag.val);
		recValue.type = Type.lookupType(recordDescr);
		return recValue;
	}

	public boolean compare(int relation, Value other) {
//		int LHS = this.value;
//		int RHS = (other == null)? 0 : ((IntegerValue)other).value;
//		return Relation.compare(LHS, relation, RHS);
		System.out.println("RecordValue.compare: " + this + " " + Scode.edInstr(relation) + " " + other);
////		Util.IERR("");
//		return res;
		Util.IERR("Method 'compare' need a redefinition in " + this.getClass().getSimpleName());
		return false;
	}
	
	private void dump(String title) {
		System.out.println("RecordValue.ofScode: " + title);
		for(int i=0;i<attrValues.size();i++) {
			System.out.println("RecordValue.ofScode: attrValues["+i+"] = "+attrValues.get(i));
		}
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}
	
	private static int setValue(Vector<Value> target, int idx, Value value) {
		if(DEBUG) {
			String qual = (value == null)? "" : " "+value.getClass().getSimpleName();
			System.out.println("RecordValue.ofScode: attrValues["+idx+"] ="+qual+" "+value);
		}
		if(value instanceof GeneralAddress gaddr) {
			Util.IERR("");
		} else if(value instanceof StringValue str) {
			Util.IERR("");
		} else if(value instanceof TextValue txt) {
			idx = setValue(target, idx, txt.emitChars(Global.TSEG));
			idx = setValue(target, idx, null);
			idx = setValue(target, idx, IntegerValue.of(Type.T_INT, txt.textValue.length()));
//			System.out.println("RecordValue.ofScode: return(1) "+idx);
			return idx;
		} else if(value instanceof RecordValue recval) {
			if(DEBUG) {
				System.out.println("RecordValue.ofScode: INNER:");
				recval.dump("RecordValue.ofScode: INNER-1: ");
			}
			for(Value atrval:recval.attrValues) {
				idx = setValue(target, idx, atrval);
			}
//			System.out.println("RecordValue.ofScode: return(2) "+idx);
			return idx;
		} else if(value instanceof RepetitionValue repval) {
			Util.IERR("");
		}
//		System.out.println("RecordValue.ofScode: attrValues["+idx+"] = "+value);
		target.set(idx, value);
//		System.out.println("RecordValue.ofScode: return(3) "+(idx+1));
//		Thread.dumpStack();
		return idx+1;
	}
	
	private int addValue(Value value) {
//		System.out.println("RecordValue.ofScode: attrValues["+idx+"] = "+value);
		if(value instanceof GeneralAddress gaddr) {
			Util.IERR("");
		} else if(value instanceof StringValue str) {
			Util.IERR("");
		} else if(value instanceof TextValue txt) {
			System.out.println("RecordValue.ofScode: attrValue: txt.typee="+txt.type);
			addValue(txt.emitChars(Global.TSEG));
			addValue(null);
			addValue(IntegerValue.of(Type.T_INT, txt.textValue.length()));
			return 3;
		} else if(value instanceof RecordValue rval) {
			Util.IERR("");
		} else if(value instanceof RepetitionValue rval) {
			Util.IERR("");
		}
		attrValues.add(value);
		return 1;
	}

	private int addChars(TextValue txt) {
		int n = txt.textValue.length();
		for(int i=0;i<n;i++) {
			addValue(IntegerValue.of(Type.T_CHAR, txt.textValue.charAt(i)));			
		}
		return n;
	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		for(Value value:attrValues) {
			if(value == null)
				 dseg.emit(null, comment);
			else value.emit(dseg, comment);
		}
	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + "C-RECORD " + tag);
		for(Value value:attrValues) {
//			System.out.println(indent + "   ATTR " + value);
			System.out.println(indent + "   "+value);
		}
		System.out.println(indent + "ENDRECORD");
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("C-RECORD " + tag);
		for(Value value:attrValues) {
			sb.append(" ATTR " + value);
		}
		sb.append(" ENDRECORD");
		return sb.toString();
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RecordValue(AttributeInputStream inpt) throws IOException {
		tag = Tag.read(inpt);
		attrValues = new Vector<Value>();
		int kind = inpt.readKind();
		while(kind != Scode.S_ENDRECORD) {
			Value value = null;
			boolean present = inpt.readBoolean();
			if(present) value = Value.read(inpt);
			attrValues.add(value);
			kind = inpt.readKind();
		}
		System.out.println("NEW RECORD VALUE: ");
//		printTree(2);
//		Util.IERR("");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_RECORD);
		tag.write(oupt);
		for(Value value:attrValues) {
			if(value != null) {
				oupt.writeBoolean(true);
				value.write(oupt);
			} else {
				oupt.writeBoolean(false);
			}
		}
		oupt.writeInstr(Scode.S_ENDRECORD);
		
//		this.printTree(2);
//		Util.IERR("");
	}

	public static RecordValue read(AttributeInputStream inpt) throws IOException {
		return new RecordValue(inpt);
	}

	
}
