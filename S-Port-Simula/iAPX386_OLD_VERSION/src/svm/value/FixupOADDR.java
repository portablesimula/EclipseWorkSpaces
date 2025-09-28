package svm.value;

import bec.descriptor.Descriptor;
import bec.util.Type;
import svm.value.dataAddress.SegmentAddress;

public class FixupOADDR extends SegmentAddress {
	Descriptor descr; // ConstDescr
	
	public FixupOADDR(Type type, Descriptor descr) {
//		super(DataAddress.SEGMNT_ADDR, null, 0, 0);
		super(null, 0);
		this.descr = descr;
	}

	public void setAddress(SegmentAddress oaddr) {
//		IO.println("FixupAddress.setAddress: "+oaddr+"  "+oaddr.segID);
		this.segID = oaddr.segID;
		this.ofst = oaddr.ofst;
	}
	
	@Override
	public String toString() {
		if(this.segID != null) return super.toString();
		return "FIXUP["+ descr.tag + ']';
	}

}
