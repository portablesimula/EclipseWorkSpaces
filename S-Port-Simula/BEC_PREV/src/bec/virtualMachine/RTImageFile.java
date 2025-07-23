package bec.virtualMachine;

import bec.util.Util;
import bec.value.ObjectAddress;

public class RTImageFile extends RTFile {
	int imglng;

	public RTImageFile(String spec, int type, String action, int imglng) {
		super(spec, type, action);
		this.imglng = imglng;
	}

	public void outimage(String image) { // Needs redefinition
		Util.IERR("Routine outimage need a redefinition in "+this.getClass().getSimpleName());
	}

	public void breakOutimage(String image) { // Needs redefinition
		Util.IERR("Routine breakOutimage need a redefinition in "+this.getClass().getSimpleName());
	}

	public int inimage(ObjectAddress chrAddr, int nchr) {
		Util.IERR("Routine inimage need a redefinition in "+this.getClass().getSimpleName());
		return 0;
	}



}
