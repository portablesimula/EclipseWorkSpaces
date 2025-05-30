package bec.virtualMachine;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import bec.util.Util;
import bec.virtualMachine.RTFileAction._CreateAction;

public class RTDirectBytefile extends RTByteFile {
	
	/// The underlying RandomAccessFile used.
	private RandomAccessFile randomAccessFile;

	/// The variable MAXLOC indicates the maximum possible location on the external
	/// file. If this is not meaningful MAXLOC has the value of "maxint"-1. The
	/// procedure "maxloc" gives access to the current MAXLOC value.
	public int MAXLOC;

	/// The initial value of LAST_LOC.
	private int INITIAL_LAST_LOC;

	public RTDirectBytefile(String fileName, int type, String action, int imglng) {
		super(fileName, type, action);
		File file = fileAction.doCreateAction(fileName);
		if (!file.exists()) {
//			File selected = trySelectFile(file.getAbsoluteFile().toString());
//			if (selected != null)
//				file = selected;
			RTUtil.set_STATUS(3); // File does not exist;
			return;
		}
		MAXLOC = Integer.MAX_VALUE - 1;
		try {
			System.out.println("NEW RTInbytefile: fileName=" + fileName);
//			lineReader = new BufferedReader(new FileReader(file));
			String mode = "rws"; // mode is one of "r", "rw", "rws", or "rwd"
			if (fileAction._SYNCHRONOUS)
				mode = "rws";
			else
				mode = (fileAction._CANREAD & !fileAction._CANWRITE) ? "r" : "rw";
			randomAccessFile = new RandomAccessFile(file, mode);
			if (fileAction._CREATE == _CreateAction.create) {
				try {
					randomAccessFile.setLength(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fileAction._APPEND)
				INITIAL_LAST_LOC = lastloc();
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
			System.out.println("clfile failed" + e);
			RTUtil.set_STATUS(17);
		}
	}

	public int inbyte() {
		try {
			int b = randomAccessFile.read();
			return (b == -1) ? 0 : b;
		} catch (EOFException e) {
			return (0);

		} catch (IOException e) {
			System.out.println("Outbyte failed" + e);
			RTUtil.set_STATUS(17);
			return 0;
		}
	}

	public void outbyte(final int b) {
		try {
			randomAccessFile.write(b);
		} catch (IOException e) {
			System.out.println("Outbyte failed" + e);
			RTUtil.set_STATUS(17);
		}
	}

	public void locate(final int p) {
		try {
//			System.out.println("RTDirectBytefile.locate: " + p);
			randomAccessFile.seek(p - 1);
		} catch (IOException e) {
			System.out.println("locate failed" + e);
			RTUtil.set_STATUS(17);
		}
	}

	public int lastloc() {
		try {
			// the length of this file, measured in bytes.
			long length = randomAccessFile.length();
//			long fpntr = randomAccessFile.getFilePointer();
//			length = Math.max(length, fpntr);
//			System.out.println("RTDirectBytefile.lastloc: " + length);
			return ((int) length);
		} catch (IOException e) {
			System.out.println("Lastloc failed" + e);
			RTUtil.set_STATUS(17);
			return 0;
		}
	}

}
