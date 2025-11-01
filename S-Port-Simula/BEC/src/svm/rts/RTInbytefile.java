/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.rts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import svm.RTUtil;

public class RTInbytefile extends RTByteFile {
	
	/// The InputStream used.
	private InputStream inputStream;


	public RTInbytefile(String fileName, int type, String action, int imglng) {
		super(fileName, type, action);
		File file = fileAction.doCreateAction(fileName);
		if (!file.exists()) {
//			File selected = trySelectFile(file.getAbsoluteFile().toString());
//			if (selected != null)
//				file = selected;
			RTUtil.set_STATUS(3); // File does not exist;
			return;
		}
		try {
//			IO.println("NEW RTInbytefile: fileName=" + fileName);
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void clfile() {
		try {
			inputStream.close();
			inputStream = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int inbyte() {
		try {
			// read a single byte
			int b = inputStream.read();
			// _RT.TRACE("InbyteFile.inbyte: read byte: +" + b);
			if (b == -1) {
				RTUtil.set_STATUS(13); // End of file on input
				return (0);
			}
			return (b);
		} catch (IOException e) {
			IO.println("Inbyte failed" + e);
			RTUtil.set_STATUS(17);
			return 0;
		}
	}

}
