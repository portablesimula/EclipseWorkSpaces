package iapx.descriptor;

import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.Kind;
import iapx.util.Option;
import iapx.util.Tag;
import iapx.util.Type;

//Record ExtDescr:Descriptor;      // K_ExternVar                   SIZE = 8 bytes
//begin range(0:MaxWord) UNUSED;   // To 4-byte align record in all cases
//      infix(ExtRef) adr;         // K_ExtLabel
//end;                             // K_ExtRoutine   External Routine
public class ExtDescr extends Descriptor {
	public String extRef;
	
	public ExtDescr(Tag tag, Type type, String extRef) {
		this.kind = Kind.K_ExternVar;
		this.tag  = tag;
		this.type = type;
		this.extRef = extRef;
	}

	@Override
	public String toString() {
		return "ExternVar: type=" + type + ", extRef=" + extRef;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		oupt.writeString(extRef);
	}

	private ExtDescr() {
		this.kind = Kind.K_ExternVar;
	}

	public static ExtDescr read(AttributeInputStream inpt) throws IOException {
		ExtDescr var = new ExtDescr();
		ExtDescr.read(var, inpt);
		if(Option.ATTR_INPUT_DUMP) IO.println("ExternVar.read: "+var);
//		Util.IERR("");
		return var;
	}

	protected static void read(ExtDescr var, AttributeInputStream inpt) throws IOException {
		Descriptor.read(var, inpt);
		var.extRef = inpt.readString();
		if(Option.ATTR_INPUT_TRACE) IO.println("ExternVar.read: extRef="+var.extRef);
	}


}
