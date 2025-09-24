package iAPX.descriptor;

import java.io.IOException;

import iAPX.ExtRef;
import iAPX.enums.Kind;
import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Option;
import iAPX.util.Tag;
import iAPX.util.Type;

public class ExternVar extends Descriptor {
//	Record ExtDescr:Descriptor;      // K_ExternVar                   SIZE = 8 bytes
//	begin int UNUSED;   // To 4-byte align record in all cases
//	      infix(ExtRef) adr;         // K_ExtLabel
//	end;                             // K_ExtRoutine   External Routine

//	int tag;  // Inherited
//	int type; // Inherited
	public ExtRef adr;
	
	public ExternVar(Tag tag, Type type, ExtRef adr) {
		this.kind = Kind.K_ExternVar;
		this.tag  = tag;
		this.type = type;
		this.adr = adr;
	}

	@Override
	public String toString() {
		return "ExternVar: type=" + type + ", adr=" + adr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		adr.write(oupt);
	}

	private ExternVar() {
		this.kind = Kind.K_ExternVar;
	}

	public static ExternVar read(AttributeInputStream inpt) throws IOException {
		ExternVar var = new ExternVar();
		ExternVar.read(var, inpt);
		if(Option.ATTR_INPUT_DUMP) IO.println("ExternVar.read: "+var);
//		Util.IERR("");
		return var;
	}

	protected static void read(ExternVar var, AttributeInputStream inpt) throws IOException {
		Descriptor.read(var, inpt);
		var.adr = ExtRef.read(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("ExternVar.read: adr="+var.adr);
	}

}
