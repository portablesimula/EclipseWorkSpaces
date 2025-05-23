package bec.virtualMachine.sysrut;

import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.RTUtil;
import bec.virtualMachine.SVM_CALL_SYS;

public abstract class SysFile {
	
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
		System.out.println("SVM_SYSCALL.gdspec: index=" + index + ", result=" +result);
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
		if(spec.equalsIgnoreCase("SYSIN")) key = 1;
		else if(spec.equalsIgnoreCase("SYSOUT")) key = 2;
		else if(spec.equalsIgnoreCase("SYSTRACE")) key = 3;
		else {
			Util.IERR("NOT IMPL");
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
//		System.out.println("SVM_SYSCALL.clfile: action="+action);
//		System.out.println("SVM_SYSCALL.clfile: key="+key);
		if(key > 3) {
			Util.IERR("NOT IMPL");
		}
		SVM_CALL_SYS.EXIT("CLFILE: ");
	}
	
	/// Visible sysroutine("NEWPAG") NEWPAG;
	/// import range(1:MAX_KEY) key; end;
	public static void newpag() {
		SVM_CALL_SYS.ENTER("NEWPAG: ", 0, 1); // exportSize, importSize
		@SuppressWarnings("unused")
		int key = RTStack.popInt();
		if(key > 3) {
			Util.IERR("NOT IMPL");
		}
		SVM_CALL_SYS.EXIT("NEWPAG: ");
	}

}
