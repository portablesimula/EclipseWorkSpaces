package iAPX.descriptor;

import java.io.IOException;

import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Util;

public class SwitchDescr extends Descriptor {
//	Record SwitchDescr:Descriptor;
//	begin range(0:MaxSdest) ndest;   // No. of Sdest in this switch
//	      range(0:MaxSdest) nleft;   // No. of Sdest left to be defined
//	      MemAddr swtab;      // Start of Sdest-Table
//	      ref(AddrBlock) DESTAB(MxpSdest); // All SDEST addresses
//	end;


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
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
