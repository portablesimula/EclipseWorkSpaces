package bec.value;

import bec.descriptor.Descriptor;
import bec.util.Type;

public class FixupOADDR extends ObjectAddress {
	Descriptor descr; // ConstDescr
	
	public FixupOADDR(Type type, Descriptor descr) {
		super(ObjectAddress.SEGMNT_ADDR, null, 0, false);
		this.descr = descr;
	}

	public void setAddress(ObjectAddress oaddr) {
		this.segID = oaddr.segID;
		this.ofst = oaddr.ofst;
	}
	
	@Override
	public String toString() {
		if(this.segID != null) return super.toString();
		return "FIXUP["+ descr.tag + ']';
	}

}
