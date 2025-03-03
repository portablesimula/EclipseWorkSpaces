package PREV.syntaxClass.value;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class RecordValue extends PREV_Value {
	int tag;
	Vector<AttributeValue> attrValues;
	
	public RecordValue() {
		attrValues = new Vector<AttributeValue>();
		parse();
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
	public void parse() {
		tag = Scode.inTag();
		while(Scode.nextByte() == Scode.S_ATTR) {
			Scode.inputInstr();
			attrValues.add(new AttributeValue());
		}
//		if(Scode.inputTrace > 3) printTree(0);
//		System.out.println("MainProgram.parse: Curinstr = "+Scode.edInstr(Scode.curinstr));
//		System.out.println("MainProgram.parse: Nxtinstr = "+Scode.edInstr(Scode.nextByte()));
		Scode.expect(Scode.S_ENDRECORD);
//		Scode.checkEqual(Scode.S_ENDRECORD);
		
//		if(Scode.inputTrace > 3) printTree(0);
	}


	@Override
	public void printTree(final int indent) {
		sLIST(indent, "C-RECORD " + Scode.edTag(tag));
		for(AttributeValue value:attrValues) {
//			sLIST(indent, "   ATTR " + value);
			sLIST(indent + 1, ""+value);
		}
		sLIST(indent, "ENDRECORD");
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("C-RECORD " + Scode.edTag(tag));
		for(AttributeValue value:attrValues) {
			sb.append(" ATTR " + value);
		}
		sb.append(" ENDRECORD");
		return sb.toString();
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RecordValue(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag();
		attrValues = new Vector<AttributeValue>();
		int kind = inpt.readKind();
		while(kind != Scode.S_ENDRECORD) {
			AttributeValue value = AttributeValue.read(inpt);
			attrValues.add(value);
			kind = inpt.readKind();
		}
		System.out.println("NEW RECORD VALUE: ");
		printTree(2);
//		Util.IERR("");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_RECORD);
		oupt.writeTag(tag);
		for(AttributeValue value:attrValues) value.write(oupt);
		oupt.writeInstr(Scode.S_ENDRECORD);
		
		this.printTree(2);
//		Util.IERR("");
	}

	public static RecordValue read(AttributeInputStream inpt) throws IOException {
		return new RecordValue(inpt);
	}

	
}
