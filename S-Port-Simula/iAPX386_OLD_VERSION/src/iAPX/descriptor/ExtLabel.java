package iAPX.descriptor;

import java.io.IOException;

import iAPX.ExtRef;
import iAPX.enums.Kind;
import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Util;

public class ExtLabel extends Descriptor {
//	Record ExtDescr:Descriptor;      // K_ExternVar                   SIZE = 8 bytes
//	begin int UNUSED;   // To 4-byte align record in all cases
//	      infix(ExtRef) adr;         // K_ExtLabel
//	end;                             // K_ExtRoutine   External Routine

//	int tag;  // Inherited
//	int type; // Inherited
	ExtRef adr;
	
	public ExtLabel() {
		this.kind = Kind.K_ExtLabel;
	}
	
	@Override
	public String toString() {
		return "ExtLabel: type=" + type + ", adr=" + adr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
//		super.write(oupt);
//		oupt.writeBitSet(pntmap);
		Util.IERR("NOT IMPL");
	}

	public static RecordDescr read(AttributeInputStream inpt) throws IOException {
		Util.IERR("NOT IMPL");
		return null;
	}

	
}
