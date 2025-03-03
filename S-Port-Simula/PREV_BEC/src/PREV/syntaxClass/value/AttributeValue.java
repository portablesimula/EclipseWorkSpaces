package PREV.syntaxClass.value;

import java.io.IOException;

import PREV.util.PREV_ResolvedType;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class AttributeValue extends PREV_Value {
	int tag;
	PREV_ResolvedType type;
	RepetitionValue value;
	
	public AttributeValue() {
		parse();
	}

	/**
	 * 	attribute_value
	 * 		::= attr attribute:tag type repetition_value
	 */
	public void parse() {
		tag = Scode.inTag();
		type = new PREV_ResolvedType();
		value = new RepetitionValue();
//		System.out.println("AttributeValue.parse: value=" + value);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "ATTR " + Scode.edTag(tag) + " "+ value;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private AttributeValue(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag();
		type = PREV_ResolvedType.read(inpt);
		value = RepetitionValue.read(inpt);
		System.out.println("NEW ATTR-VALUE: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_ATTR);
		oupt.writeTag(tag);
		type.write(oupt);
		value.write(oupt);
		
		this.printTree(2);
//		Util.IERR("");
	}

	public static AttributeValue read(AttributeInputStream inpt) throws IOException {
		return new AttributeValue(inpt);
	}
	

}
