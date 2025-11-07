/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.env.filespec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import bec.Global;
import bec.Option;
import bec.scode.Type;
import bec.util.Util;
import svm.RTUtil;
import svm.value.IntegerValue;
import svm.value.ObjectAddress;

public class InfileSpec extends ImageFileSpec {
	
	/// The BufferedReader used.
	private BufferedReader lineReader;
	
	/// Utility variable
	private String rest = null;


	public InfileSpec(String fileName, int type, String action, int imglng) {
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
//			IO.println("NEW RTInfile: fileName=" + fileName);
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
//			IO.println("RTInfile.inimage: line=\"" + line + '"');
			rest = null;
			if(line == null) {
				RTUtil.set_STATUS(13); // End of file on input";
				return 0;
			}
			if(line.length() <= nchr) {
				for(int i=0;i<line.length();i++) {
					chrAddr.store(i, IntegerValue.of(Type.T_CHAR, line.charAt(i)));
				}
				return line.length();
			} else {
				for(int i=0;i<nchr;i++) {
					chrAddr.store(i, IntegerValue.of(Type.T_CHAR, line.charAt(i)));
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
	
	private static String sysinRest = null;
	
	private static String readLine() throws IOException, TimeoutException {
		StringBuilder sb = new StringBuilder();
		if(Global.console != null) {
			int c = Global.console.read();
			while(c != '\n') {
				sb.append((char)c);
				c = Global.console.read();
			}
		} else {
			int c = IO_read(5, TimeUnit.SECONDS);
			while(c != '\n') {
				sb.append((char)c);
				c = IO_read(5, TimeUnit.SECONDS);
			}
		}
		return sb.toString();
	}
	
	private static int IO_read(int timeout, TimeUnit unit) throws IOException, TimeoutException {
		long sleep = unit.toMillis(timeout);
		LOOP: while(true) {
			if(System.in.available() > 0) return System.in.read();
			if((--sleep) < 0) break LOOP;
			try { Thread.sleep(1); } catch (InterruptedException e) {}
		}
		if(Option.execVerbose)
			IO.println("RTInfile.readLine: throw new TimeoutException()");
		throw new TimeoutException();
	}

	public static int sysinInimage(ObjectAddress chrAddr, int nchr) {
		try {
			String line = (sysinRest != null) ? sysinRest : readLine();
//			IO.println("RTInfile.sysinInimage: line=\"" + line + '"');
			sysinRest = null;
			if(line == null) {
				RTUtil.set_STATUS(13); // End of file on input";
				return 0;
			}
			if(line.length() <= nchr) {
				for(int i=0;i<line.length();i++) {
					chrAddr.store(i, IntegerValue.of(Type.T_CHAR, line.charAt(i)));
				}
				return line.length();
			} else {
				for(int i=0;i<nchr;i++) {
					chrAddr.store(i, IntegerValue.of(Type.T_CHAR, line.charAt(i)));
				}
				sysinRest = line.substring(nchr);
				return nchr;
			}
		} catch (Exception e) {
//			throw new RTS_SimulaRuntimeError("Inimage failed", e);
			Util.IERR("sysinInimage failed: " + e);
		}
		return 0;
	}

}
