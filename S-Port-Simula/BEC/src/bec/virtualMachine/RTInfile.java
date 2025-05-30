package bec.virtualMachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;

public class RTInfile extends RTImageFile {
	
	/// The BufferedReader used.
	private BufferedReader lineReader;
	
	/// Utility variable
	private String rest = null;


	public RTInfile(String fileName, int type, String action, int imglng) {
		super(fileName, type, action, imglng);
		File file = fileAction.doCreateAction(fileName);
		if (!file.exists()) {
//			File selected = trySelectFile(file.getAbsoluteFile().toString());
//			if (selected != null)
//				file = selected;
			RTUtil.set_STATUS(3); // File does not exist;
			return;
		}
		try {
			System.out.println("NEW RTInfile: fileName=" + fileName);
			lineReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void clfile() {
		try {
			lineReader.close();
			lineReader = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/// Procedure inimage.
	///
	/// A record is read from the current position of the data set into the image. If the number of characters
	/// in the record exceeds the image length, the action taken is system dependent:
	///
	/// - If the system permits partial record read, image.length characters are read, filled := image.length,
	///   and status 34 is returned. In this case the next inimage (on this data set) should continue reading
	///   from the next position in the partially read record.
	///
	/// - If partial record reading is not possible, status error 12 is set and filled is set to zero; the remainder
	///   of the record is in this case skipped.
	///
	/// Except for the case of partial record reading the data set will be positioned at the sequentially next record.
	///
	/// Inimage is legal on infiles and directfiles only.
	///
	/// This routine may change the value of the global variable status to one of the values given in app. C.
	/// If the status returned is non-zero, filled must be zero, except for the partial read case discussed
	/// above (status 12).
	/// 
	public int inimage(ObjectAddress chrAddr, int nchr) {
		try {
			String line = (rest != null) ? rest : lineReader.readLine();
//			System.out.println("RTInfile.inimage: line=\"" + line + '"');
			rest = null;
			if(line == null) {
				RTUtil.set_STATUS(13); // End of file on input";
				return 0;
			}
//			if (line != null) {
//				if (line.length() > lng) {
//					Util.IERR(this.spec + ": Image too short: input.length=" + line.length() + ", image.length=" + lng);
//				}
//				ASGSTR(image, line);
//			} else {
//				ASGSTR(image, "" + (char) 25);
//				_ENDFILE = true;
//			}
			if(line.length() <= nchr) {
				for(int i=0;i<line.length();i++) {
					chrAddr.store(i, IntegerValue.of(Type.T_CHAR, line.charAt(i)), "INIMA: ");
				}
				return line.length();
			} else {
				for(int i=0;i<nchr;i++) {
					chrAddr.store(i, IntegerValue.of(Type.T_CHAR, line.charAt(i)), "INIMA: ");
				}
				rest = line.substring(nchr);
				return nchr;
			}
		} catch (IOException e) {
//			throw new RTS_SimulaRuntimeError("Inimage failed", e);
			Util.IERR("Inimage failed: " + e);
		}
		return 0;
	}

}
