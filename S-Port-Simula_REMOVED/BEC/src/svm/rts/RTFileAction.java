/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.rts;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.swing.JFileChooser;

import bec.util.Util;
import svm.RTUtil;

public class RTFileAction {
	
	private static final boolean DEBUG = false;

	/// CREATE: Action is performed at 'open'
	/// 
	/// If the value is "create", the external file associated with FILENAME must not
	/// exist at "open" (if it does, "open" returns false); a new file is created by
	/// the environment. If the value is "nocreate", the associated file must exist
	/// at "open". The value "anycreate" implies that if the file does exist at
	/// "open" the file is opened, otherwise a new file is created.
	protected enum _CreateAction {
		/// not applicable
		NA,
		/// the associated file must exist at "open"
		noCreate,
		/// the external file associated with FILENAME must not exist at "open"
		create,
		/// if the file does exist at "open" the file is opened,
		/// otherwise a new file is created
		anyCreate
	};

	/// CREATE: Action is performed as 'open'
	protected _CreateAction _CREATE = _CreateAction.NA; // May be Redefined

	/// PURGE: Action is performed at 'close'
	/// 
	/// The value "purge" implies that the external file may be deleted by the
	/// environment when it is closed (in the sense that it becomes inaccessible to
	/// further program access). The value "nopurge" implies no such deletion.
	protected boolean _PURGE = false; // True:purge, False:noPurge

	/// If the value is true, input operations can be performed.
	/// 
	/// This mode is relevant only for direct files.
	protected boolean _CANREAD = true;
	
	/// If the value is true, output operations can be performed.
	/// 
	/// This mode is relevant only for direct files.
	protected boolean _CANWRITE = true;

	/// The output to the file is added to
	/// the existing contents of the file.
	/// 
	/// If the value is true, output to the file is added to the existing
	/// contents of the file. The value false implies
	/// for a sequential file that, after "close", the external file will
	/// contain only the output produced while the file was open.
	/// 
	/// The mode is not relevant for in(byte)files.
	protected boolean _APPEND = false;
	
	/// The current character set when encode/decode files.
	protected Charset _CHARSET = Charset.defaultCharset();

	/// The default BYTESIXE is 8 in this implementation.
	protected final int _DEFAULT_BYTESIZE = 8;
	
	/// The access mode SYNCHRONOUS.
	/// 
	/// It is available for Out- files and Direct- files.
	/// For Outfile/OutBytefile, each write operation will be followed by a flush to ensure that the
	/// underlying storage device is updated. For Directfile/DirectBytefile the underlaying Java
	/// RandomAccessFile will be created open for reading and writing, and also require that every
	/// update to the file's content or metadata be written synchronously to the underlying storage device.
	protected boolean _SYNCHRONOUS;

//	/// Returns true when this file is open.
//	/// @return true when this file is open
//	public boolean isopen() {
//		return (_OPEN);
//	}

	
	public RTFileAction(String action) {
		decodeActions(action);
	}
	
	/// -- action encoding: (a digit gives the rank of the character, e.g. 0 is NUL)
	/// --      action == <0 ! 1 >          -- shared/noshared
	/// --                <0 ! 1 >          -- append/noappend
	/// --                <0 ! 1 ! 2 >      -- create/nocreate/anycreate
	/// --                <0 ! 1 ! 2 >      -- readonly/writeonly/readwrite
	/// --                <0 ! 1 >          -- purge/nopurge
	/// --                <0 ! 1 ! 2 ! 3 ! 4 ! 5 >
	/// --                -- rewind/norewind/next/previous/repeat/release
	/// --                <<char>>          -- bytesize: rank(char) (!0! default)
	/// --                <<c1><c2>>        -- move:<rank(c1)*256+rank(c2)>
	/// --                ( <l><string> )*  -- unknown access modes
	/// --                0                 -- terminating NUL character
	/// --
	/// -- The action string will always be at least 10 chars long, encoded
	/// -- with the predefined modes in the above given sequence (e.g. char
	/// -- number 3 will always specify the CREATE mode). If no value is given 
	/// -- for some mode, RTS will insert the appropriate default character
	/// -- at the relevant position. These defaults are:
	/// --
	/// --      in(byte)file:     "!0!!1!!1!!0!!1!!2!!0!!0!!0!!0!!0!"
	/// --      out(byte)file:    "!1!!1!!2!!1!!1!!2!!0!!0!!0!!0!!0!"
	/// --      direct(byte)file: "!1!!1!!1!!2!!1!!5!!0!!0!!0!!0!!0!"
	/// --
	/// -- If an unknown (i.e. non-Sport-defined) value are given as parameter
	/// -- to procedure "setaccess", the first character must be '%' (percent),
	/// -- otherwise "setaccess" returns FALSE (in all other cases it is TRUE).
	/// -- Accepted values will be concatenated with the standard string, with 
	/// -- '%' replaced by a character (l) whose rank gives the length of the
	/// -- string, excluding the overwritten '%'.
	/// -- The action string is always terminated by the NUL character ('!0!').
	///
	///                       Files of kind
	///  Mode:        In-        Out-        Direct-   Takes effect at
	///  SHARED       shared     noshared    noshared     open
	///  APPEND       NA         noappend    noappend     open
	///  CREATE       NA         anycreate   nocreate     open
	///  READWRITE    NA         NA          readwrite    open
	///  BYTESIZE:x   *          *           *            open
	///  REWIND       norewind   norewind    NA           open,close
	///  PURGE        nopurge    nopurge     nopurge      close
	///
	private void decodeActions(String action) {
		if(action.length() != 10) Util.IERR("NOT IMPL");
		int chr = getActionChar(action, 0);
		if(DEBUG) IO.println("RTFileAction.decodeActions: shared/noshared = " + chr);
		
		chr = getActionChar(action, 1);
		_APPEND = chr != 1;
		if(DEBUG) IO.println("RTFileAction.decodeActions: append/noappend = " + chr + "              ===> APPEND = " + _APPEND);
		
		chr = getActionChar(action, 2);
		switch(chr) {
			case 0: _CREATE = _CreateAction.create; break;
			case 1: _CREATE = _CreateAction.noCreate; break;
			case 2: _CREATE = _CreateAction.anyCreate;
		}
		if(DEBUG) IO.println("RTFileAction.decodeActions: create/nocreate/anycreate = " + chr + "    ===> CREATE = " + _CREATE);
		
		chr = getActionChar(action, 3);
		switch(chr) {
			case 0: _CANWRITE = false; _CANREAD = true;  break;
			case 1: _CANWRITE = true;  _CANREAD = false; break;
			case 2: _CANWRITE = true;  _CANREAD = true;
		}
		if(DEBUG) IO.println("RTFileAction.decodeActions: readonly/writeonly/readwrite = " + chr + " ===> CANREAD = " + _CANREAD + ", CANWRITE= " + _CANWRITE);
		
		chr = getActionChar(action, 4);
		_PURGE = chr != 1;
		if(DEBUG) IO.println("RTFileAction.decodeActions: purge/nopurge = " + chr + "                ===> PURGE = " + _PURGE);

//		chr = getActionChar(5);
//		IO.println("RTFileAction.decodeActions: rewind/norewind/next/previous/repeat/release = " + chr);
//		chr = getActionChar(6);
//		IO.println("RTFileAction.decodeActions: append/noappend = " + chr);
//		chr = getActionChar(7);
//		IO.println("RTFileAction.decodeActions: append/noappend = " + chr);
//		chr = getActionChar(8);
//		IO.println("RTFileAction.decodeActions: append/noappend = " + chr);
//		chr = getActionChar(9);
//		IO.println("RTFileAction.decodeActions: append/noappend = " + chr);
//		Util.IERR("");
	}

	private int getActionChar(String action, int idx) {
		int c = action.charAt(idx);
		if(c == 46) return 0;
		return c;
	}
	

	/// Do the Create action.
	/// @return the File 
	public File doCreateAction(String fileName) {
		File file = new File(fileName);
		try {
//			if (!file.isAbsolute() && RTS_Option.RUNTIME_USER_DIR.length() > 0) {
//				file = new File(RTS_Option.RUNTIME_USER_DIR + '/' + FILE_NAME.edText());
//			}
			switch (_CREATE) {
			case NA -> {
			}
			case noCreate -> {
				// If the value is "nocreate", the associated file must exist at "open".
				if (!file.exists()) {
//					IO.println("File access mode=noCreate but File \"" + file + "\" does not exist");
//					RTUtil.set_STATUS(3); // File does not exist
					
					File selected = trySelectFile(fileName);
					if(selected != null) {
						file = selected;
					} else {
						IO.println("File access mode=noCreate but File \"" + file + "\" does not exist");
						RTUtil.set_STATUS(3); // File does not exist						
					}
				}
			}
			case create -> {
				// If the value is "create", the external file associated with FILENAME
				// must not exist at "open" (if it does, "open" returns false);
				// a new file is created by the environment.
				if (!file.exists()) {
					boolean success = file.createNewFile();
					if (!success) {
						IO.println("File access mode=Create but couldn't create a new empty file: " + file);
						RTUtil.set_STATUS(18); // Specified action cannot be performed
					}
				}
			}
			case anyCreate -> {
				// The value "anycreate" implies that if the file does exist
				// at "open" the file is opened, otherwise a new file is created.
				if (!file.exists()) {
					IO.println("RTFileAction.doCreateAction: " + file + " +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//					file.mkdirs();
					boolean success = file.createNewFile();
					// IO.println("FILE.doCreateAction: Create on "+file+",
					// success="+success);
					if (!success) {
						IO.println("File access mode=anyCreate but couldn't create a new empty file: " + file);
					}
				}
			}
			}
		} catch (IOException e) {
    		e.printStackTrace();
		}
		return (file);
	}

	/// Try to select a file named 'fileName'.
	/// 
	/// If no file exists with that fileName it will try several possibilities:
	/// 
	/// - First it will search the Option.internal.RUNTIME_USER_DIR
	/// - Second, system properties "user.dir" and "java.class.path".parent.
	/// - Finally, a JFileChooser dialog is opened to let the user select the file.
	/// 
	/// @param fileName the given file name
	/// @return the resulting file or null
	protected File trySelectFile(final String fileName) {
		File file = new File(fileName);
		if (file.exists())
			return (file);
//		if (!file.isAbsolute()) {
//			File tryFile = new File(RTS_Option.RUNTIME_USER_DIR, fileName);
//			if (tryFile.exists())
//				return (tryFile);
//			File dir = new File(System.getProperty("user.dir", null));
//			tryFile = new File(dir, fileName);
//			if (tryFile.exists())
//				return (tryFile);
//
//			File javaClassPath = new File(System.getProperty("java.class.path"));
//			if (javaClassPath.exists())
//				try {
//					dir = javaClassPath.getParentFile().getParentFile();
//					tryFile = new File(dir, fileName);
//					if (tryFile.exists())
//						return (tryFile);
//				} catch (Throwable e) {
//		    		if(RTS_Option.VERBOSE) e.printStackTrace();
//				}
//		}
//		JFileChooser fileChooser = new JFileChooser(file.getParent());
		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir", null));
		fileChooser.setDialogTitle("Can't Open " + fileName + ", select another");
		int answer = fileChooser.showOpenDialog(null);
		if (answer == JFileChooser.APPROVE_OPTION) {
			return (fileChooser.getSelectedFile());
		}
		return (null);
	}

	
	/// Do the Purge action
	public void doPurgeAction(String fileName) {
		try {
			File file = new File(fileName.trim());
			if (_PURGE) {
				if (!file.delete()) {
					IO.println("Purge " + this.getClass().getSimpleName() + " \"" + file.getName() 
									   + "\" failed - the underlying OS was unable to perform the delete operation");
					RTUtil.set_STATUS(18); // Specified action cannot be performed
				}
			}
		} catch (Exception e) {
    		e.printStackTrace();
		}
	}

}
