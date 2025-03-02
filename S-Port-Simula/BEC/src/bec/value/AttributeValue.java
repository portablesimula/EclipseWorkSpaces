package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;

public class AttributeValue extends Value {
	Tag tag;
	public Type type;
	public RepetitionValue value;
	
	private AttributeValue() {
//		parse();
	}

	/**
	 * 	attribute_value
	 * 		::= attr attribute:tag type repetition_value
	 */
	public static AttributeValue ofScode() {
		AttributeValue aval = new AttributeValue();
		aval.tag = Tag.ofScode();
		aval.type = Type.ofScode();
		aval.value = RepetitionValue.ofScode();
//		System.out.println("AttributeValue.parse: value=" + value);
		return aval;
	}

	public static AttributeValue of(Tag tag, Type type, RepetitionValue value) {
		AttributeValue aval = new AttributeValue();
		aval.tag = tag;
		aval.type = type;
		aval.value = value;
//		System.out.println("AttributeValue.parse: value=" + value);
		return aval;
	}

//	@Override
//	public void print(final String indent) {
//		System.out.println(indent + toString());
//	}
	
	public String toString() {
		return "ATTR " + tag + " " + type + " "+ value;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private AttributeValue(AttributeInputStream inpt) throws IOException {
		tag = Tag.read(inpt);
		type = Type.read(inpt);
		value = RepetitionValue.read(inpt);
		System.out.println("NEW ATTR-VALUE: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_ATTR);
		tag.write(oupt);;
		type.write(oupt);
		value.write(oupt);
		
//		this.printTree(2);
//		Util.IERR("");
	}

	public static AttributeValue read(AttributeInputStream inpt) throws IOException {
		return new AttributeValue(inpt);
	}
	

}
