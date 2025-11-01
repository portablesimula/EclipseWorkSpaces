/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.rts;

import bec.util.Util;
import svm.value.ObjectAddress;

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
