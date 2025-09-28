package bec.virtualMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;

public class RTDirectfile extends RTImageFile {
	
	/// The underlying RandomAccessFile used.
	private RandomAccessFile randomAccessFile;

	/// The variable "RECORDSIZE" is the fixed size of all external images. It is set
	/// by "open" and subdivide the external file into series of external images,
	/// without any image separating characters.
	int RECORDSIZE;

	/// The variable MAXLOC indicates the maximum possible location on the external
	/// file. If this is not meaningful MAXLOC has the value of "maxint"-1. The
	/// procedure "maxloc" gives access to the current MAXLOC value.
	public int MAXLOC;

	/// The initial value of LAST_LOC
	private int INITIAL_LAST_LOC;

	public RTDirectfile(String fileName, int type, String action, int imglng) {
		super(fileName, type, action, imglng);
		File file = fileAction.doCreateAction(fileName);
		if (!file.exists()) {
//			File selected = trySelectFile(file.getAbsoluteFile().toString());
//			if (selected != null)
//				file = selected;
			RTUtil.set_STATUS(3); // File does not exist;
			return;
		}
		RECORDSIZE = imglng;
		MAXLOC = Integer.MAX_VALUE - 1;
		try {
//			IO.println("NEW RTDirectfile: fileName=" + fileName);
//			writer = new FileWriter(file, this.fileAction._APPEND);
			String mode = "rws"; // mode is one of "r", "rw", "rws", or "rwd"
			if (fileAction._SYNCHRONOUS)
				mode = "rws";
			else
				mode = (fileAction._CANREAD & !fileAction._CANWRITE) ? "r" : "rw";
			randomAccessFile = new RandomAccessFile(file, mode);
//			if (fileAction._APPEND)
//				INITIAL_LAST_LOC = lastloc();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void clfile() {
		try {
			if (fileAction._PURGE) {
				randomAccessFile.setLength(0);
				randomAccessFile.close();
//				File file = new File(FILE_NAME.edText().trim());
//				if (file.exists()) {
//					RTS_UTIL.printWarning("Purge " + this.getClass().getSimpleName() + " \"" + file.getName()
//							+ "\" failed - the underlying OS was unable to perform the delete operation");
//				}
			} else
				randomAccessFile.close();
			randomAccessFile = null;
		} catch (IOException e) {
			IO.println("clfile failed" + e);
			RTUtil.set_STATUS(17);
		}
	}

	public int inimage(ObjectAddress chrAddr, int nchr) {
//		try {
//			String line = (rest != null) ? rest : readLine();
			String line = readLine();
//			IO.println("RTInfile.inimage: line=\"" + line + '"');
//			rest = null;
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
				Util.IERR("");
			}
//		} catch (IOException e) {
////			throw new RTS_SimulaRuntimeError("Inimage failed", e);
//			Util.IERR("Inimage failed: " + e);
//		}
		return 0;
	}
	
	private String readLine() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<this.imglng;i++) {
			try {
				int b = randomAccessFile.read();
				sb.append((char)b);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	@Override
	public void outimage(String image) {
		try {
//			writer.write(image);
//			writer.write("\n");
			randomAccessFile.write(image.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Util.IERR(""+image);
	}

	public void locate(int p) {
//		if (p < 1 | p > MAXLOC)
//			throw new RTS_SimulaRuntimeError("Locate: Parameter out of range");
//		else
			try {
				randomAccessFile.seek((p - 1) * RECORDSIZE);
			} catch (IOException e) {
//				throw new RTS_SimulaRuntimeError("Locate failed", e);
				RTUtil.set_STATUS(99);
			}
//		_LOC = p;
//		Util.IERR("");
	}
	
	public int lastloc() {
		try {
			// the length of this file, measured in bytes.
			long length = randomAccessFile.length();
			return ((int) length / RECORDSIZE);
		} catch (IOException e) {
			IO.println("Lastloc failed" + e);
			RTUtil.set_STATUS(17);
			return 0;
		}
	}

}
