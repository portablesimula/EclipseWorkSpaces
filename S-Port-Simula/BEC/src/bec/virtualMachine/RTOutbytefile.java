package bec.virtualMachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RTOutbytefile extends RTByteFile {
	
	/// The OutputStream used.
	private OutputStream outputStream;

	public RTOutbytefile(String fileName, int type, String action, int imglng) {
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
//			System.out.println("NEW RTOutbytefile: fileName=" + fileName);
			outputStream = new FileOutputStream(file, fileAction. _APPEND);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void clfile() {
		try {
			outputStream.flush();
			outputStream.close();
			outputStream = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void outbyte(final int b) {
		try {
			outputStream.write(b);
			if (fileAction._SYNCHRONOUS)
				outputStream.flush();
		} catch (IOException e) {
			System.out.println("Outbyte failed" + e);
			RTUtil.set_STATUS(17);
		}
//		Util.IERR("");
	}

}
