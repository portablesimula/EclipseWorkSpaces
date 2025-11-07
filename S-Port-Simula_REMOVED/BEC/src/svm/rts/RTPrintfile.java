/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.rts;

public class RTPrintfile extends RTOutfile {
	
//	FileWriter writer;

	public RTPrintfile(String fileName, int type, String action, int imglng) {
		super(fileName, type, action, imglng);
//		File file = fileAction.doCreateAction(fileName);
//		if (!file.exists()) {
////			File selected = trySelectFile(file.getAbsoluteFile().toString());
////			if (selected != null)
////				file = selected;
//			RTUtil.set_STATUS(3); // File does not exist;
//			return;
//		}
//		try {
//			IO.println("NEW RTPrintfile: fileName=" + fileName);
//			writer = new FileWriter(file, this.fileAction._APPEND);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

//	@Override
//	public void outimage(String image) {
//		try {
//			writer.write(image);
//			writer.write("\n");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		Util.IERR(""+image);
//	}

}
