package bec.virtualMachine.sysrut;

import java.util.Vector;

import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTDirectBytefile;
import bec.virtualMachine.RTDirectfile;
import bec.virtualMachine.RTFile;
import bec.virtualMachine.RTFileAction;
import bec.virtualMachine.RTImageFile;
import bec.virtualMachine.RTInbytefile;
import bec.virtualMachine.RTInfile;
import bec.virtualMachine.RTOutbytefile;
import bec.virtualMachine.RTOutfile;
import bec.virtualMachine.RTPrintfile;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.RTUtil;
import bec.virtualMachine.SVM_CALL_SYS;

public abstract class SysFile {
	
	private static Vector<RTFile> rtFiles = new Vector<RTFile>();
	
	private static int addRTFile(RTFile spec) {
//		System.out.println("SysFile.addRTFile: "+spec);
		rtFiles.add(spec);
		return(rtFiles.size()+3);
	}
	
	private static RTFile lookup(int key) {
//		System.out.println("SysFile.lookup: key="+key);
		return rtFiles.get(key-4);
	}
	
	/// Visible sysroutine("GDSPEC") GDSPEC;
	/// import range(1:3) code; infix(string) spec;
	/// export integer filled  end;
	public static void gdspec() {
		SVM_CALL_SYS.ENTER("GDSPEC: ", 1, 4); // exportSize, importSize
		@SuppressWarnings("unused")
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		int index = RTStack.popInt();
		String result = null;
		switch(index) {
			case 1: // What is the data set specification for SYSIN.
				result = "SYSIN"; break;
			case 2: // What is the data set specification for SYSOUT.
				result = "SYSOUT"; break;
			case 3: // What is the data set specification for SYSTRACE.
				result = "SYSTRACE"; break;
			default: Util.IERR("");
		}
//		System.out.println("SVM_SYSCALL.gdspec: index=" + index + ", result=" +result);
		RTUtil.move(result, itemAddr, result.length());

		RTStack.push(IntegerValue.of(Type.T_INT, result.length()), "EXPORT");
		SVM_CALL_SYS.EXIT("GDSPEC: ");
	}
	
	/// Visible sysroutine("GETLPP") GETLPP;
	/// import range(1:MAX_KEY) key; export integer lpp end;
	public static void getlpp() {
		SVM_CALL_SYS.ENTER("GETLPP: ", 1, 1); // exportSize, importSize
		@SuppressWarnings("unused")
		int key = RTStack.popInt();
		RTStack.push(IntegerValue.of(Type.T_INT, 66), "GETLPP");
		SVM_CALL_SYS.EXIT("GETLPP: ");
	}
	
	/// Visible sysroutine("OPFILE") OPFILE;
	/// import  infix(string)      spec;   -- dsetspec;
	///         range(0:MAX_FIL)   type;   -- dsettype;
	///         infix(string)      action;
	///         integer            imglmg; -- img_lng;
	/// export  range(0:MAX_KEY)   key;    -- filekey;
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
	/// end;
	public static void opfile() {
		SVM_CALL_SYS.ENTER("OPFILE: ", 1, 8); // exportSize, importSize
//		RTStack.dumpRTStack("SVM_SYSCALL.opfile: ");
		int imglng = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.opfile: imglng="+imglng);
		String action = RTStack.popString();
		int type = RTStack.popInt();
		String spec = RTStack.popString();
//		System.out.println("SVM_SYSCALL.opfile: spec="+spec);
//		System.out.println("SVM_SYSCALL.opfile: type="+type);
//		System.out.println("SVM_SYSCALL.opfile: action="+action);
//		System.out.println("SVM_SYSCALL.opfile: imglng="+imglng);
		int key = 0;
		if(spec.equalsIgnoreCase("SYSIN"))         key = RTFile.KEY_SYSIN;
		else if(spec.equalsIgnoreCase("SYSOUT"))   key = RTFile.KEY_SYSOUT;
		else if(spec.equalsIgnoreCase("SYSTRACE")) key = RTFile.KEY_SYSTRACE;
		else {
//			RTFile fileSpec = new RTFile(spec, type, action, imglng);
//			key = addRTFile(fileSpec);
			
			RTFile fileSpec = null;
			switch(type) {
				case RTFile.FIL_INFILE ->		  fileSpec = new RTInfile(spec, type, action, imglng);
				case RTFile.FIL_OUTFILE ->		  fileSpec = new RTOutfile(spec, type, action, imglng);
				case RTFile.FIL_PRINTFILE ->	  fileSpec = new RTPrintfile(spec, type, action, imglng);
				case RTFile.FIL_DIRECTFILE ->	  fileSpec = new RTDirectfile(spec, type, action, imglng);
				case RTFile.FIL_INBYTEFILE ->	  fileSpec = new RTInbytefile(spec, type, action, imglng);
				case RTFile.FIL_OUTBYTEFILE ->    fileSpec = new RTOutbytefile(spec, type, action, imglng);
				case RTFile.FIL_DIRECTBYTEFILE -> fileSpec = new RTDirectBytefile(spec, type, action, imglng);
				default -> Util.IERR(""+RTFile.edFileType(type));
			}
			key = addRTFile(fileSpec);
			if(Global.execVerbose) System.out.println("SVM_SYSCALL.opfile: key=" + key + ", spec="+spec);
//			Util.IERR("NOT IMPL");
		}
		RTStack.push(IntegerValue.of(Type.T_INT, key), "OPFILE");
//		Util.IERR("");
		SVM_CALL_SYS.EXIT("OPFILE: ");
	}

	/// Visible sysroutine("CLFILE") OPFILE;
	/// import  range(0:MAX_KEY)   key;    -- filekey;
	///         infix(string)      action;
	/// end;
	/// -- see OPFILE for encoding of action string --
	public static void clfile() {
		SVM_CALL_SYS.ENTER("CLFILE: ", 0, 4); // exportSize, importSize
//		RTStack.dumpRTStack("SVM_SYSCALL.clfile: ");
		String action = RTStack.popString();
		int key = RTStack.popInt();
		if(key > 3) {
//			System.out.println("SVM_SYSCALL.clfile: key="+key);
//			System.out.println("SVM_SYSCALL.clfile: action="+action);
			RTFile fileSpec = lookup(key);
//			System.out.println("SVM_SYSCALL.clfile: spec="+spec);
			fileSpec.clfile();
			
			RTFileAction fileAction = new RTFileAction(action);
			fileAction.doPurgeAction(fileSpec.fileName);
//			Util.IERR("NOT IMPL");
		}
		SVM_CALL_SYS.EXIT("CLFILE: ");
	}

	/// Visible sysroutine("INIMAG") fINIMA;
	/// import range(1:MAX_KEY) key; infix(string) image;
	/// export integer filled  end;
	///
	/// Key: The key associated with the data set.
	/// Image: Input buffer.
	/// Filled: The number of characters placed in image.
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
	public static void INIMAG() {
		SVM_CALL_SYS.ENTER("INIMAG: ", 1, 4); // exportSize, importSize
//		String image = RTStack.popString();
		int nchr = RTStack.popInt();
		ObjectAddress chrAddr = RTStack.popGADDRasOADDR();
		int key = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.INIMAG: key="+key);
//		System.out.println("SVM_SYSCALL.INIMAG: nchr="+nchr);
//		System.out.println("SVM_SYSCALL.INIMAG: chrAddr="+chrAddr);
		int filled = 0;
		if(key == RTFile.KEY_SYSIN) {
//			System.out.println("SVM_SYSCALL.INIMAG: SYSIN:  nchr="+nchr);
			filled = RTInfile.sysinInimage(chrAddr, nchr);
//			Util.IERR("");
		} else if(key > 3) {
			RTImageFile spec = (RTImageFile) lookup(key);
//			System.out.println("SVM_SYSCALL.INIMAG: spec="+spec);
			filled = spec.inimage(chrAddr, nchr);
//			Util.IERR("NOT IMPL");
		} else {
			Util.IERR("");
		}
		RTStack.push(IntegerValue.of(Type.T_INT, filled), "INIMAG");
		SVM_CALL_SYS.EXIT("INIMAG: ");
	}

	/// Visible sysroutine("OUTIMA")  fUTIMA;
	/// import range(1:MAX_KEY) key; infix(string) img  end;
	///
	///		Key: The key associated with the data set.
	/// 	img: Output buffer.
	///
	/// If the file is of type 2 or 4, image is copied into the record at the current position of the data set, and
	/// the data set is positioned at the sequentially next record. On printfiles the image is printed from the
	/// current print position, without vertical spacing (i.e. the next print position is the next position on the
	/// same line); this is utilised by the Simula breakoutimage procedure.
	///
	/// Outimage is legal on outfiles, printfiles and directfiles only.
	/// This routine may change the value of the global variable status to one of the values given in app. C.
	public static void OUTIMA() {
		SVM_CALL_SYS.ENTER("OUTIMA: ", 0, 4); // exportSize, importSize
		String image = RTStack.popString();
		int key = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.OUTIMA: key="+key);
//		System.out.println("SVM_SYSCALL.OUTIMA: nchr="+nchr);
//		System.out.println("SVM_SYSCALL.OUTIMA: chrAddr="+chrAddr);
		if(key == RTFile.KEY_SYSOUT || key == RTFile.KEY_SYSTRACE) {
			Util.IERR("");
		} else if(key > 3) {
			RTImageFile spec = (RTImageFile) lookup(key);
//			System.out.println("SVM_SYSCALL.OUTIMA: spec="+spec);
			spec.outimage(image);
//			Util.IERR("NOT IMPL");
		} else {
			Util.IERR("");
		}
		SVM_CALL_SYS.EXIT("OUTIMA: ");
	}
	
	/// Visible sysroutine("BREAKO") BREAKO;
	/// import range(1:MAX_KEY) key; infix(string) img  end;
	///
	///		Key: The key associated with the data set.
	/// 	img: A string to be output
	///
	/// The routine Breako will output the string img to the current record of the data set, beginning at the
	/// current position. The record should not be "closed" i.e. the next Breako (or Outima) will output to
	/// the same record, beginning at the new position. If this is not possible, Breako shall perform as Outima.
	///
	/// On display terminals, Breako will output the string from the current cursor position, and leave the
	/// cursor positioned after the last charcter of img. Note that trailing blanks (of img) shall be output.
	///
	/// On other external data sets, it may be necessary for the environment to buffer the output internally.
	///
	/// The routine is legal for outfiles and printfiles only.
	public static void BREAKO() {
		SVM_CALL_SYS.ENTER("BREAKO: ", 0, 4); // exportSize, importSize
		String image = RTStack.popString();
		int key = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.BREAKO: key="+key);
//		System.out.println("SVM_SYSCALL.BREAKO: nchr="+nchr);
//		System.out.println("SVM_SYSCALL.BREAKO: chrAddr="+chrAddr);
		if(key == RTFile.KEY_SYSOUT || key == RTFile.KEY_SYSTRACE) {
//			System.out.println("SVM_SYSCALL.BREAKO: SYSOUT:  key="+key);
			if(Global.console != null)
				 Global.console.write(image);
			else
				System.out.print(image);
//			Util.IERR("");
		} else if(key > 3) {
			RTImageFile spec = (RTImageFile) lookup(key);
//			System.out.println("SVM_SYSCALL.BREAKO: spec="+spec);
			spec.breakOutimage(image);
//			Util.IERR("NOT IMPL");
		} else {
			Util.IERR("");
		}
		SVM_CALL_SYS.EXIT("BREAKO: ");
	}

	/// Visible sysroutine("INBYTE") fINBYT;
	/// import range(1:MAX_KEY) key; export range(0:MAX_BYT) byte  end;
	public static void INBYTE() {
		SVM_CALL_SYS.ENTER("INBYTE: ", 1, 1); // exportSize, importSize
		int key = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.INBYTE: key="+key);
		RTFile spec = lookup(key);
		int byt = 0;
		if(spec instanceof RTInbytefile ifile) {
			byt =ifile.inbyte();
		}
		else if(spec instanceof RTDirectBytefile dbfile) {
			byt = dbfile.inbyte();
		}
		else Util.IERR("");
		RTStack.push(IntegerValue.of(Type.T_INT, byt), "INBYTE");
		SVM_CALL_SYS.EXIT("INBYTE: ");
//		Util.IERR("");
	}

	/// Visible sysroutine("OUTBYT") fUTBYT;
	/// import range(1:MAX_KEY) key; range(0:MAX_BYT) byte  end;
	public static void OUTBYT() {
		SVM_CALL_SYS.ENTER("OUTBYT: ", 0, 2); // exportSize, importSize
		int byt = RTStack.popInt();
		int key = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.OUTBYT: key="+key);
//		System.out.println("SVM_SYSCALL.OUTBYT: byt="+byt);
		RTFile spec = lookup(key);
		if(spec instanceof RTOutbytefile ofile) {
			ofile.outbyte(byt);
		}
		else if(spec instanceof RTDirectBytefile dbfile) {
			dbfile.outbyte(byt);
		}
		else Util.IERR("");
		SVM_CALL_SYS.EXIT("OUTBYT: ");
//		Util.IERR("");
	}

	/// Visible sysroutine("NEWPAG") NEWPAG;
	/// import range(1:MAX_KEY) key; end;
	public static void newpag() {
		SVM_CALL_SYS.ENTER("NEWPAG: ", 0, 1); // exportSize, importSize
		int key = RTStack.popInt();
		if(key > 3) {
			Util.IERR("NOT IMPL");
		}
		SVM_CALL_SYS.EXIT("NEWPAG: ");
	}

	
	/// Visible sysroutine("LOCATE") fLOCAT;
	/// import range(1:MAX_KEY) key; integer loc  end;
	public static void LOCATE() {
		SVM_CALL_SYS.ENTER("LOCATE: ", 0, 2); // exportSize, importSize
		int loc = RTStack.popInt();
		int key = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.LOCATE: key="+key);
//		System.out.println("SVM_SYSCALL.LOCATE: loc="+loc);
		if(key > 3) {
			RTFile spec = lookup(key);
			if(spec instanceof RTDirectfile dfile) {
				dfile.locate(loc);
			}
			else if(spec instanceof RTDirectBytefile dbfile) {
				dbfile.locate(loc);
			}
			else Util.IERR("");
		}
		SVM_CALL_SYS.EXIT("LOCATE: ");
//		Util.IERR("");
	}

	/// Visible sysroutine("MAXLOC") MXLOC;
	/// import range(1:MAX_KEY) key; export integer res  end;
	public static void MXLOC() {
		SVM_CALL_SYS.ENTER("MXLOC: ", 1, 1); // exportSize, importSize
		int key = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.MXLOC: key="+key);
		int maxloc = 0;
		if(key > 3) {
			RTFile spec = lookup(key);
			if(spec instanceof RTDirectfile dfile) {
				maxloc = dfile.MAXLOC;
			}
			else if(spec instanceof RTDirectBytefile dbfile) {
				maxloc = dbfile.MAXLOC;
			}
			else Util.IERR("");
		}
		RTStack.push(IntegerValue.of(Type.T_INT, maxloc), "MXLOC");
		SVM_CALL_SYS.EXIT("MXLOC: ");
//		Util.IERR("");
	}

	/// Visible sysroutine("LSTLOC") LSTLOC;
	/// import range(1:MAX_KEY) key; export integer res  end;
	public static void LSTLOC() {
		SVM_CALL_SYS.ENTER("LSTLOC: ", 1, 1); // exportSize, importSize
		int key = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.LSTLOC: key="+key);
		int maxloc = 0;
		if(key > 3) {
			RTFile spec = lookup(key);
			if(spec instanceof RTDirectfile dfile) {
				maxloc = dfile.lastloc();
			}
			else if(spec instanceof RTDirectBytefile dbfile) {
				maxloc = dbfile.lastloc();
			}
			else Util.IERR("");
		}
		RTStack.push(IntegerValue.of(Type.T_INT, maxloc), "LSTLOC");
		SVM_CALL_SYS.EXIT("LSTLOC: ");
//		Util.IERR("");
	}

}
