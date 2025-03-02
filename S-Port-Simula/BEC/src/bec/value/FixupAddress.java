package bec.value;

import bec.descriptor.Descriptor;
import bec.util.Type;

public class FixupAddress extends ProgramAddress {
	Descriptor descr; // LabelDescr or RoutineDescr
	
	public FixupAddress(Type type, Descriptor descr) {
		super(type, null, 0);
		this.descr = descr;
	}

	public void setAddress(ProgramAddress paddr) {
//		System.out.println("FixupAddress.setAddress: "+paddr+"  "+paddr.segID);
		this.segID = paddr.segID;
		this.ofst = paddr.ofst;
	}
}
