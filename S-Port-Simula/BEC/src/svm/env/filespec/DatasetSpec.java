/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.env.filespec;

import bec.util.Util;
import svm.env.FileAction;

public class DatasetSpec {
	public String fileName;
	int fileType;
	public FileAction fileAction; // Used by OPEN
	
	public static final int FIL_INFILE         = 1; // record oriented sequential read access.
	public static final int FIL_OUTFILE        = 2; // record oriented sequential write access.
	public static final int FIL_PRINTFILE      = 3; // printer formatted outfile.
	public static final int FIL_DIRECTFILE     = 4; // record oriented random read/write access.
	public static final int FIL_INBYTEFILE     = 5; // stream oriented sequential read access.
	public static final int FIL_OUTBYTEFILE    = 6; // stream oriented sequential write access.
	public static final int FIL_DIRECTBYTEFILE = 7; // byte oriented random read/write access.
	
	public static final int KEY_SYSIN    = 1;
	public static final int KEY_SYSOUT   = 2;
	public static final int KEY_SYSTRACE = 3;

	
	public DatasetSpec(String fileName, int fileType, String action) {
		this.fileName = fileName;
		this.fileType = fileType;
//		IO.println("RTFile.opfile: fileName="+this.fileName);
//		IO.println("RTFile.opfile: fileType="+this.fileType+"  "+edFileType(this.fileType));
//		IO.println("RTFile.opfile: action="+action);
		fileAction = new FileAction(action);
	}

	public void clfile() { // Needs redefinition
		Util.IERR("Routine clfile need a redefinition in "+this.getClass().getSimpleName());
	}

	public static String edFileType(int fileType) {
		switch(fileType) {
		case FIL_INFILE:         return("INFILE");
		case FIL_OUTFILE:        return("OUTFILE");
		case FIL_PRINTFILE:      return("PRINTFILE");
		case FIL_DIRECTFILE:     return("DIRECTFILE");
		case FIL_INBYTEFILE:     return("INBYTEFILE");
		case FIL_OUTBYTEFILE:    return("OUTBYTEFILE");
		case FIL_DIRECTBYTEFILE: return("DIRECTBYTEFILE");
		default:                 return"UNKNOWN";
		}
	}
		
	public String toString() {
		return edFileType(fileType) + ": " + fileName;
	}

}
