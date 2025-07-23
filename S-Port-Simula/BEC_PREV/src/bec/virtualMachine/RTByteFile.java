package bec.virtualMachine;

import bec.util.Util;
import bec.value.ObjectAddress;

public class RTByteFile extends RTFile {

	public RTByteFile(String spec, int type, String action) {
		super(spec, type, action);
	}

//	public void outimage(String image) { // Needs redefinition
//		Util.IERR("Routine outimage need a redefinition in "+this.getClass().getSimpleName());
//	}
//
//	public int inimage(ObjectAddress chrAddr, int nchr) {
//		Util.IERR("Routine inimage need a redefinition in "+this.getClass().getSimpleName());
//		return 0;
//	}



}
