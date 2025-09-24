package iapx.descriptor;

import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.Kind;
import iapx.records.Objekt;
import iapx.util.Option;
import iapx.util.Tag;
import iapx.util.Type;

//Record Descriptor:Object;
//begin end;
public abstract class Descriptor extends Objekt {
	public Kind kind;
	public Tag tag;
	public Type type;

	public void print(final String indent) {
//		IO.println("Missing Method 'print' in " + this.getClass().getSimpleName());
		IO.println(this.toString());
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("Descriptor.Write: " + this.kind + ':' + this.kind.ordinal() + "  " + this);
		kind.write(oupt);
		Tag.writeTag(tag, oupt);
		Type.write(type, oupt);
	}

	protected static void read(Descriptor d, AttributeInputStream inpt) throws IOException {
		d.tag = Tag.readTag(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("Descriptor.read: tag="+d.tag);
		d.type = Type.readType(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("Descriptor.read: type="+d.type);
	}


}
