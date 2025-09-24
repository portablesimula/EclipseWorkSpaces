package iAPX.descriptor;

import static iAPX.util.Global.*;

import java.io.IOException;

import iAPX.dataAddress.MemAddr;
import iAPX.enums.Kind;
import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Tag;
import iAPX.util.Util;

public class LabelDescr extends Descriptor {
//	Record IntDescr:Descriptor;      // K_Globalvar
//	begin MemAddr adr;        // K_IntLabel
//	end;                             // K_IntRoutine   Local Routine

//	int tag;  // Inherited
//	int type; // Inherited
	MemAddr adr;        // K_IntLabel
	
	public LabelDescr(Tag tag) {
		this.kind = Kind.K_IntLabel;
		this.tag  = tag;
	}
	
	@Override
	public String toString() {
		return "LabelDescr: type=" + type + ", adr=" + adr;
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
