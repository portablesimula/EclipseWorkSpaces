package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.EndProgram;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.BooleanValue;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;
import bec.virtualMachine.sysrut.SysEdit;

public class SVM_CALLSYS extends SVM_Instruction {
	int kind;

	public SVM_CALLSYS(int kind) {
		this.opcode = SVM_Instruction.iCALLSYS;
		if(kind == 0) Util.IERR("Undefined System Routine: " + kind);
		this.kind = kind;
	}

	@Override
	public void execute() {
		switch(kind) {
			case P_VERBOSE:  verbose(); break;
			case P_INITIA:   initia(); break;
			case P_DWAREA:   dwarea(); break;
			case P_ZEROAREA: zeroarea(); break;
			case P_CPUTIM:   cputime(); break;
			case P_GETLPP:   getlpp(); break;
			case P_OPFILE:   opfile(); break;
			case P_CLFILE:   clfile(); break;
			case P_GDSPEC:   gdspec(); break;
			case P_TERMIN:   terminate(); break;
			case P_STREQL:   stringEqual(); break;
			case P_GINTIN:   getIntinfo(); break;
			
			case P_PUTSTR:   putstr(); break;
			case P_PRINTO:   printo(); break;
			case P_PUTINT2:  putint2(); break;
			case P_PTREAL2:  putreal2(); break;
			case P_PLREAL2:  putlreal2(); break;
			case P_PUTFIX2:  putfix2(); break;
			case P_PTLFIX2:  putlfix2(); break;
			case P_PUTHEX:   puthex(); break;
			case P_PUTSIZE:  putsize(); break;
			case P_SIZEIN:   sizein(); break;
			case P_PTOADR2:  ptoadr2(); break;
			case P_PTPADR2:  ptpadr2(); break;
			case P_PTRADR2:  ptradr2(); break;
			case P_PTAADR2:  ptaadr2(); break;
			case P_PTGADR2:  ptgadr2(); break;
			default: Util.IERR("SVM_SYSCALL: Unknown System Routine " + edKind(kind));
		}
		Global.PSC.ofst++;
	}
	
	public void ENTER(String ident, int exportSize, int importSize) {
		if(Global.EXEC_TRACE > 4)
			RTStack.dumpRTStack(ident+"ENTER: ");
		int rtStackIndex = RTStack.size() - (exportSize + importSize);
		CallStackFrame callStackFrame = new CallStackFrame(ident, rtStackIndex, exportSize, importSize);
		RTStack.callStack.push(callStackFrame);
		
		if(Global.EXEC_TRACE > 0) {
			ProgramAddress.printInstr(this,false);
			if(Global.EXEC_TRACE > 2)
				RTStack.callStack_TOP().dump(ident+"ENTER: ");
		}

		if(Global.CALL_TRACE_LEVEL > 0)
			RTStack.printCallTrace("SVM_CALLSYS.ENTER: ");
	}
	
	public void EXIT(String ident) {
		if(Global.CALL_TRACE_LEVEL > 0)
			RTStack.printCallTrace("SVM_CALLSYS.EXIT: ");
		CallStackFrame top = RTStack.callStack.pop();
		if(Global.EXEC_TRACE > 2) {
			RTStack.callStack_TOP().dump(ident+"RETURN: Called from " + top.curAddr);		
		}
	}
	
	@Override	
	public String toString() {
		return "CALLSYS  " + edKind(kind);
	}
	
	public static boolean RUNTIME_VERBOSE = false;
	
	/**
	 * Visible sysroutine("VERBOSE") VERBOSE;
	 *  import range(0:127) index; export integer result  end;
	 */
	private void verbose() {
		RTStack.push(BooleanValue.of(RUNTIME_VERBOSE), "EXPORT");
		ENTER("VERBOSE: ", 1, 0); // exportSize, importSize
		EXIT("VERBOSE: ");
	}
	
	/**
	 * Visible sysroutine("INITIA") INITIA;
	 *  import entry(PXCHDL) exchdl  end;
	 */
	private void initia() {
		ENTER("INITIA: ", 0, 1); // exportSize, importSize
		ProgramAddress exchdl = (ProgramAddress) RTStack.pop().value();
		System.out.println("SVM_SYSCALL.initia: "+exchdl);
		EXIT("INITIA: ");
	}
	
	/**
	 * Visible sysroutine("TERMIN") TERMIN;
	 *  import range(0:3) code; infix(string) msg  end;
	 */
	private void terminate() {
		ENTER("TERMIN: ", 0, 4); // exportSize, importSize
		String str = RTStack.popString();
		int code = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.terminate: "+str+" with exit code " + code);
//		System.exit(code);
		throw new EndProgram(code,"SVM_SYSCALL.terminate: "+str+" with exit code " + code);
	}
	
	
	/**
	 *  Visible sysroutine("STREQL") STREQL;
	 *   import infix(string) str1,str2; export boolean res  end;
	 */
	private void stringEqual() {
		ENTER("STREQL: ", 1, 6); // exportSize, importSize
		String str1 = RTStack.popString();
		String str2 = RTStack.popString();
		boolean result = str1.equals(str2);
//		System.out.println("SVM_CALLSYS.stringEqual: " + str1 + " equals " + str2 + " ==> " + result);
		RTStack.push(BooleanValue.of(result), "EXPORT");
		EXIT("STREQL: " + result);
	}
		
		/**
		 * Visible sysroutine("GINTIN") GINTIN;
		 *  import range(0:127) index; export integer result  end;
		 */
		private void getIntinfo() {
			ENTER("GINTIN: ", 1, 1); // exportSize, importSize
			int index = RTStack.popInt();
			System.out.println("SVM_SYSCALL.getIntinfo: "+index);
			int result=0;
			switch(index) {
				case 19: result = 0; break; // 19 Should the symbolic debugger SIMOB be entered prior to the execution of the
				                            //    program, and at program termination? An answer greater than zero will give this effect.
				case 24: result = 1; break; // 24 How many work areas may be requested (see chapter 5)?
				case 33: result = 0; break; // 33 Result: 0 - no, this is not an interactive execution
				                            //            1 - yes, this is an interactive execution
				case 99: Segment.lookup("DSEG_RT").dump("",0,100); break; // AD'HOC DUMP UTILITY
				default: Util.IERR("");
			}
			RTStack.push(IntegerValue.of(Type.T_INT, result), "EXPORT");
			EXIT("GINTIN: ");
		}


	/**
	 *  Visible sysroutine("PRINTO") PRINTO;
	 *  	 import range(1:MAX_KEY) key; infix(string) image; integer spc;
	 *  end;
	 */
	private void printo() {
		ENTER("PRINTO: ", 0, 5); // exportSize, importSize

		/*int spc = */RTStack.popInt();
		int nchr = RTStack.popInt();
		int ofst = RTStack.popInt();
		ObjectAddress chradr = (ObjectAddress) RTStack.pop().value();
		/*int key = */RTStack.popInt();

		ObjectAddress x = chradr.addOffset(ofst);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<nchr;i++) {
			IntegerValue ival = (IntegerValue) x.load(); x.incrOffset();
			char c = (ival == null)? '.' : ((char) ival.value);
			sb.append(c);
		}
		System.out.println(sb);
		EXIT("PRINTO: ");
	}
	
	/**
	 * Visible sysroutine("PUTSTR") PUTSTR;
	 *		import infix (string) item; infix(string) val;
	 *		export integer lng;
	 * end;
	 */
	private void putstr() {
		boolean DEBUG = false;
		ENTER("PUTSTR: ", 1, 6); // exportSize, importSize
		int valNchr = RTStack.popInt();
		ObjectAddress valAddr = RTStack.popGADDRasOADDR();
		if(DEBUG) {
			// valAddr.segment().dump("SVM_SYSCALL.putstr:");
			System.out.println("SVM_SYSCALL.putstr: valAddr="+valAddr);
			System.out.println("SVM_SYSCALL.putstr: valNchr="+valNchr);
		}
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		if(DEBUG) {
			// itemAddr.segment().dump("SVM_SYSCALL.putstr:");
			System.out.println("SVM_SYSCALL.putstr: itemAddr="+itemAddr);
			System.out.println("SVM_SYSCALL.putstr: itemNchr="+itemNchr);
		}
		if(valNchr > 0) move(valAddr, itemAddr, valNchr);

		RTStack.push(IntegerValue.of(Type.T_INT, valNchr), "EXPORT");
		EXIT("PUTSTR: ");
	}
	
	/**
	 *  Visible sysroutine("PUTINT2") PUTINT2;
	 *      import infix (string) item; integer val
	 *      export integer lng;
	 *  end;
	 */
	private void putint2() {
		ENTER("PUTINT2: ", 1, 4); // exportSize, importSize
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");

		EXIT("PUTINT2: ");
	}
	
	/**
	 *  Visible sysroutine("PTREAL2") PTREAL2;
	 *      import infix (string) item; real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	private void putreal2() {
		ENTER("PTREAL2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		float val = RTStack.popReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putreal(val,frac);
		sval = sval.replace(',', '.').replace('E', '&');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PTREAL2: ");
	}
	
	/**
	 *  Visible sysroutine("PLREAL2") PLREAL2;
	 *      import infix (string) item; long real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	private void putlreal2() {
		ENTER("PLREAL2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		double val = RTStack.popLongReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putreal(val,frac);
		sval = sval.replace(',', '.').replace('E', '&');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PLREAL2: ");
	}
	
	/**
	 *  Visible sysroutine("PUTFIX2") PUTFIX2;
	 *      import infix (string) item; real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	private void putfix2() {
		ENTER("PUTFIX2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		float val = RTStack.popReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putfix(val,frac);
		sval = sval.replace(',', '.');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PUTFIX2: ");
	}
	
	/**
	 *  Visible sysroutine("PTLFIX2") PTLFIX2;
	 *      import infix (string) item; long real val; integer frac; 
	 *      export integer lng;
	 *  end;
	 */
	private void putlfix2() {
		ENTER("PUTFIX2: ", 1, 5); // exportSize, importSize
		int frac = RTStack.popInt();
		double val = RTStack.popLongReal();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = SysEdit.putfix(val,frac);
		sval = sval.replace(',', '.');
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PUTFIX2: ");
	}
	
	/**
	 *  Visible sysroutine("PUTHEX") PUTHEX;
	 *      import infix (string) item; integer val
	 *      export integer lng;
	 *  end;
	 */
	private void puthex() {
		ENTER("PUTHEX: ", 1, 4); // exportSize, importSize
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = "0x" + Integer.toHexString(val).toUpperCase();
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PUTHEX: ");
	}
	
	/**
	 *  Visible sysroutine("PUTSIZE") PUTSIZE;
	 *      import infix (string) item; size val
	 *      export integer lng;
	 *  end;
	 */
	private void putsize() {
		ENTER("PUTSIZE: ", 1, 4); // exportSize, importSize
		int val = RTStack.popInt();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		
		String sval = ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PUTSIZE: ");
	}
	
	/**
	 *  Visible sysroutine("PTOADR2") PTOADR2;
	 *      import infix (string) item; ref() val;
	 *      export integer lng;
	 *  end;
	 */
	private void ptoadr2() {
		ENTER("PTOADR2: ", 1, 4); // exportSize, importSize
		ObjectAddress val = RTStack.popOADDR();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NONE" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PTOADR2: ");
	}
	
	/**
	 *  Visible sysroutine("PTPADR2") PTPADR2;
	 *      import infix (string) item; label val;
	 *      export integer lng;
	 *  end;
	 */
	private void ptpadr2() {
		ENTER("PTPADR2: ", 1, 4); // exportSize, importSize
		ProgramAddress val = (ProgramAddress) RTStack.pop().value();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NOWHERE" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PTPADR2: ");
	}
	
	/**
	 *  Visible sysroutine("PTAADR2") PTAADR2	;
	 *      import infix (string) item; entry() val;
	 *      export integer lng;
	 *  end;
	 */
	private void ptradr2() {
		ENTER("PTAADR2	: ", 1, 4); // exportSize, importSize
		ProgramAddress val = (ProgramAddress) RTStack.pop().value();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NOBODY" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PTRADR2	: ");
	}
	
	/**
	 *  Visible sysroutine("PTRADR2	") PTRADR2	;
	 *      import infix (string) item; entry() val;
	 *      export integer lng;
	 *  end;
	 */
	private void ptaadr2() {
		ENTER("PTRADR2	: ", 1, 4); // exportSize, importSize
		IntegerValue val = (IntegerValue) RTStack.pop().value();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "NOFIELD" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PTAADR2	: ");
	}
	
	/**
	 *  Visible sysroutine("PTGADR2	") PTGADR2	;
	 *      import infix (string) item; name() val;
	 *      export integer lng;
	 *  end;
	 */
	private void ptgadr2() {
		ENTER("PTGADR2	: ", 1, 4); // exportSize, importSize
		GeneralAddress val = RTStack.popGADDR();
		int itemNchr = RTStack.popInt();
		ObjectAddress itemAddr = RTStack.popGADDRasOADDR();
		String sval = (val == null)? "GNONE" : ""+val;
		int nchr = sval.length();
		if(nchr > itemNchr) Util.IERR("Editing span edit-buffer");
		move(sval, itemAddr, nchr);
		RTStack.push(IntegerValue.of(Type.T_INT, nchr), "EXPORT");
		EXIT("PTGADR2	: ");
	}
	
	/// Visible sysroutine("GDSPEC") GDSPEC;
	/// import range(1:3) code; infix(string) spec;
	/// export integer filled  end;
	private void gdspec() {
		ENTER("GDSPEC: ", 1, 4); // exportSize, importSize
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
		move(result, itemAddr, result.length());

		RTStack.push(IntegerValue.of(Type.T_INT, result.length()), "EXPORT");
		EXIT("GDSPEC: ");
	}
	
	/**
	 *  Visible sysroutine ("SIZEIN") SIZEIN;
	 *  	import range(0:127) index; range(0:255) ano;
	 *  	export size result  end;
	 */
	private void sizein() {
		ENTER("SIZEIN: ", 1, 2); // exportSize, importSize
		int warea = RTStack.popInt();
		int index = RTStack.popInt();
		int result = 0;
		switch(index) {
			case 1: // The minimum size of this work area.
//				result = 150000; break;
				result = 1500; break;
			case 2: // The extension/contraction step size.
//				Util.IERR("The extension/contraction step size.");
				break;
			case 3: // The minimum gap left in this work area after a garbage collection if the area is the current work area.
//				Util.IERR("The minimum gap left in this work area after a garbage collection if the area is the current work area.");
				break;
			default: Util.IERR("");
		}
		System.out.println("SVM_SYSCALL.sizein: index=" + index + ", warea=" + warea + ", result=" +result);
		RTStack.push(IntegerValue.of(Type.T_SIZE, result), "EXPORT");
		EXIT("SIZEIN: ");
	}
	
	/**
	 *  Visible inline'routine ("ZEROAREA")  ZEROAREA;
	 * 		import ref() fromAddr, toAddr;
	 * 
	 *  The area between fromAddr and toAddr (from included, toAddr not) is to be zero-filled
	 */
	private void zeroarea() {
		ENTER("ZEROAREA: ", 0, 2); // exportSize, importSize
		ObjectAddress toAddr = RTStack.popOADDR();
		ObjectAddress fromAddr = RTStack.popOADDR();
		ObjectAddress from = fromAddr.addOffset(0);
		ObjectAddress to = toAddr.addOffset(0);
		if(! from.segID.equals(to.segID)) Util.IERR("");
		DataSegment dseg = from.segment();
		int idxFrom = from.getOfst();
		int idxTo = to.getOfst();
//		dseg.dump("SVM_SYSCALL.zeroarea: ", idxFrom, idxTo);
		for(int i=idxFrom; i<idxTo;i++) {
			dseg.store(i, null);
		}
//		dseg.dump("SVM_SYSCALL.zeroarea: ", idxFrom, idxTo);
//		Util.IERR("");
		RTStack.push(null, null);
		EXIT("ZEROAREA: ");
	}
	
	/// Visible sysroutine("CPUTIM") CPUTIM;
	/// export long real sec  end;
	private void cputime() {
		ENTER("CPUTIM: ", 0, 1); // exportSize, importSize
		RTStack.push(null, "CPUTIM: ");
		EXIT("CPUTIM: ");
	}
	
	/// Visible sysroutine("GETLPP") GETLPP;
	/// import range(1:MAX_KEY) key; export integer lpp end;
	private void getlpp() {
		ENTER("GETLPP: ", 1, 1); // exportSize, importSize
		@SuppressWarnings("unused")
		int key = RTStack.popInt();
		RTStack.push(IntegerValue.of(Type.T_INT, 66), "GETLPP");
		EXIT("GETLPP: ");
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
	private void opfile() {
		ENTER("OPFILE: ", 1, 8); // exportSize, importSize
		RTStack.dumpRTStack("SVM_SYSCALL.opfile: ");
		int imglng = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.opfile: imglng="+imglng);
		String action = RTStack.popString();
		int type = RTStack.popInt();
		String spec = RTStack.popString();
		System.out.println("SVM_SYSCALL.opfile: spec="+spec);
		System.out.println("SVM_SYSCALL.opfile: type="+type);
		System.out.println("SVM_SYSCALL.opfile: action="+action);
		System.out.println("SVM_SYSCALL.opfile: imglng="+imglng);
		int key = 0;
		if(spec.equalsIgnoreCase("SYSIN")) key = 1;
		else if(spec.equalsIgnoreCase("SYSOUT")) key = 2;
		else if(spec.equalsIgnoreCase("SYSTRACE")) key = 3;
		else {
			Util.IERR("NOT IMPL");
		}
		RTStack.push(IntegerValue.of(Type.T_INT, key), "OPFILE");
//		Util.IERR("");
		EXIT("OPFILE: ");
	}

	/// Visible sysroutine("CLFILE") OPFILE;
	/// import  range(0:MAX_KEY)   key;    -- filekey;
	///         infix(string)      action;
	/// end;
	/// -- see OPFILE for encoding of action string --
	private void clfile() {
		ENTER("CLFILE: ", 0, 4); // exportSize, importSize
		RTStack.dumpRTStack("SVM_SYSCALL.clfile: ");
		String action = RTStack.popString();
		int key = RTStack.popInt();
		System.out.println("SVM_SYSCALL.clfile: action="+action);
		System.out.println("SVM_SYSCALL.clfile: key="+key);
		if(key > 3) {
			Util.IERR("NOT IMPL");
		}
		EXIT("CLFILE: ");
	}

	/**
	 *  Visible sysroutine ("DWAREA")  DWAREA;
	 * 		import size lng; range(0:255) ano;
	 *  	export ref() pool  end;
	 */
	private void dwarea() {
		ENTER("DWAREA: ", 1, 2); // exportSize, importSize
		int warea = RTStack.popInt();
		int lng = RTStack.popInt();
//		System.out.println("SVM_SYSCALL.dwarea: lng=" + lng + ", warea=" + warea);
		
		DataSegment pool = new DataSegment("POOL_" + warea, Kind.K_SEG_DATA);
		pool.emitDefaultValue(1, lng, pool.ident);
		RTStack.push(ObjectAddress.ofSegAddr(pool, 0) , "EXPORT");

//		Util.IERR("");
		EXIT("DWAREA: ");
	}
	
	private static void move(String src, ObjectAddress dst, int count) {
		ObjectAddress into = dst.addOffset(0);
		for(int i=0;i<count;i++) {
			Value x = IntegerValue.of(Type.T_CHAR, src.charAt(i));
			into.store(i, x, "MOVE DEST: "); //into.incrOffset();
		}
	}
	
	private static void move(ObjectAddress src, ObjectAddress dst, int count) {
		ObjectAddress from = src.addOffset(0);
		ObjectAddress into = dst.addOffset(0);
		for(int i=0;i<count;i++) {
			Value x = from.load(); from.incrOffset();
			into.store(i, x, "MOVE DEST: "); //into.incrOffset();
		}
	}

//	---------     S y s t e m    K i n d    C o d e s      ---------
	
	public String edKind(int kind) {
		switch(kind) {
		case P_TERMIN: return "TERMIN";
		case P_INTRHA: return "INTRHA";
		case P_PXCHDL: return "PXCHDL";
		case P_PEXERR: return "PEXERR";
		case P_PSIMOB: return "PSIMOB";
		case P_PobSML: return "PobSML";
		case P_Palloc: return "Palloc";
		case P_Pfree:  return "Pfree";
		case P_Pmovit: return "Pmovit";
		case P_STREQL: return "STREQL";
		case P_PRINTO: return "PRINTO";
		case P_INITIA: return "INITIA";
		case P_SETOPT: return "SETOPT";
		case P_DMPSEG: return "DMPSEG";
		case P_DMPENT: return "DMPENT";
		case P_DMPOOL: return "DMPOOL";
		case P_VERBOSE: return "VERBOSE";
		case P_GINTIN: return "GINTIN";
		case P_GTEXIN: return "GTEXIN";
		case P_SIZEIN: return "SIZEIN";
		case P_GVIINF: return "GVIINF";
		case P_GIVINF: return "GIVINF";
		case P_CPUTIM: return "CPUTIM";
		case P_DWAREA: return "DWAREA";
		case P_ZEROAREA: return "ZEROAREA";
		case P_MOVEIN: return "MOVEIN";
//
//		case P_STRIP:  return  "STRIP";  // Known
//		case P_TRFREL: return "TRFREL"; // Known
		
		case P_OPFILE: return "OPFILE";
		case P_CLFILE: return "CLFILE";
		case P_LSTLOC: return "LSTLOC";
		case P_MAXLOC: return "MXLOC";
		case P_CHKPNT: return "CHECKP";
		case P_LOCKFI: return "LOCKFI";
		case P_UNLOCK: return "UPLOCK";
		case P_INIMAG: return "INIMAG";
		case P_OUTIMA: return "OUTIMA";
		case P_BREAKO: return "BREAKO";
		case P_LOCATE: return "LOCATE";
		case P_DELETE: return "DELETE";
		case P_GDSNAM: return "GDSNAM";
		case P_GDSPEC: return "GDSPEC";
		case P_GETLPP: return "GETLPP";
		case P_NEWPAG: return "NEWPAG";
		case P_INBYTE: return "INBYTE";
		case P_OUTBYT: return "OUTBYT";
		case P_GETINT: return "GETINT";
		case P_GTREAL: return "GTREAL";
		case P_GTFRAC: return "GTFRAC";
		case P_PUTSTR: return "PUTSTR";
		case P_PUTINT: return "PUTINT";
		case P_PUTINT2: return "PUTINT2";
		case P_PUTSIZE: return "PUTSIZE";
		case P_PUTHEX: return "PUTHEX";
		case P_PUTFIX: return "PUTFIX";
		case P_PUTFIX2: return "PUTFIX2";
		case P_PTLFIX: return "PTLFIX";
		case P_PTLFIX2: return "PTLFIX2";
		case P_PTREAL: return "PTREAL";
		case P_PTREAL2: return "PTREAL2";
		case P_PLREAL: return "PLREAL";
		case P_PLREAL2: return "PLREAL2";
		case P_PTFRAC: return "PTFRAC";
		case P_PTSIZE: return "PTSIZE";
		case P_PTOADR: return "PTOADR";
		case P_PTOADR2: return "PTOADR2";
		case P_PTAADR: return "PTAADR";
		case P_PTAADR2: return "PTAADR2";
		case P_PTGADR: return "PTGADR";
		case P_PTGADR2: return "PTGADR2";
		case P_PTPADR: return "PTPADR";
		case P_PTPADR2: return "PTPADR2";
		case P_PTRADR: return "PTRADR";
		case P_PTRADR2: return "PTRADR2";
		case P_DRAWRP: return "DRAWRP";
		case P_DATTIM: return "DATTIM";
		case P_LOWTEN: return "LTEN";
		case P_DCMARK: return "DECMRK";
		case P_RSQROO: return "RSQROO";
		case P_SQROOT: return "SQROOT";
		case P_RLOGAR: return "RLOGAR";
		case P_LOGARI: return "LOGARI";
		case P_RLOG10: return "RLOG10";
		case P_DLOG10: return "DLOG10";
		case P_REXPON: return "REXPON";
		case P_EXPONE: return "EXPONE";
		case P_RSINUS: return "RSINUS";
		case P_SINUSR: return "SINUSR";
		case P_RCOSIN: return "RCOSIN";
		case P_COSINU: return "COSINU";
		case P_RTANGN: return "RTANGN";
		case P_TANGEN: return "TANGEN";
		case P_RCOTAN: return "COTANR";
		case P_COTANG: return "COTANG";
		case P_RARTAN: return "RARTAN";
		case P_ARCTAN: return "ARCTAN";
		case P_RARCOS: return "RARCOS";
		case P_ARCCOS: return "ARCCOS";
		case P_RARSIN: return "RARSIN";
		case P_ARCSIN: return "ARCSIN";
		case P_RATAN2: return "ATAN2R";
		case P_ATAN2: return "ATAN2";
		case P_RSINH: return "SINHR";
		case P_SINH: return "SINH";
		case P_RCOSH: return "COSHR";
		case P_COSH: return "COSH";
		case P_RTANH: return "TANHR";
		case P_TANH: return "TANH";
		case P_BEGDEB: return "BEGDEB";
		case P_ENDDEB: return "ENDDEB";
		case P_BEGTRP: return "BEGTRP";
		case P_ENDTRP: return "ENDTRP";
		case P_GTPADR: return "GTPADR";
		case P_GTOUTM: return "GTOUTM";
		case P_GTLNID: return "GTLNID";
		case P_GTLNO: return "GTLNO";
		case P_BRKPNT: return "BRKPNT";
		case P_STMNOT: return "STMNOT";
		case P_DMPOBJ: return "DMPOBJ";

		// KNOWN
		case P_MODULO: return "MODULO";
		case P_RADDEP: return "RADDEP";
		case P_DADDEP: return "DADDEP";
		case P_RSUBEP: return "RSUBEP";
		case P_DSUBEP: return "DSUBEP";
		case P_IIPOWR: return "IIPOWR";
		case P_RIPOWR: return "RIPOWR";
		case P_RRPOWR: return "RRPOWR";
		case P_RDPOWR: return "RDPOWR";
		case P_DIPOWR: return "DIPOWR";
		case P_DRPOWR: return "DRPOWR";
		case P_DDPOWR: return "DDPOWR";
		}
		return "UNKNOWN:" + kind;
	}

	public static final int P_TERMIN=1; // System profile
	public static final int P_INTRHA=2; // System profile
	public static final int P_PXCHDL=140; // System profile
	public static final int P_PEXERR=141; // System profile
	public static final int P_PSIMOB=142; // System profile
	public static final int P_PobSML=143; // System profile
	public static final int P_Palloc=144; // System profile
	public static final int P_Pfree =145; // System profile
	public static final int P_Pmovit=146; // System profile
	public static final int P_STREQL=3; // System routine
	public static final int P_PRINTO=4; // System routine
	public static final int P_INITIA=5; // System routine
	public static final int P_SETOPT=6; // System routine
	public static final int P_DMPSEG=7; // System routine
	public static final int P_DMPENT=8; // System routine
	public static final int P_DMPOOL=9; // System routine
	public static final int P_VERBOSE=10; // System routine
	public static final int P_GINTIN=11; // System routine
	public static final int P_GTEXIN=12; // System routine
	public static final int P_SIZEIN=13; // System routine
	public static final int P_GVIINF=14; // System routine
	public static final int P_GIVINF=15; // System routine
	public static final int P_CPUTIM=16; // System routine
	public static final int P_DWAREA=17; // System routine
	public static final int P_MOVEIN=18; // System routine

	public static final int P_OPFILE=19; // OPFILE;
	public static final int P_CLFILE=20; // CLFILE;
	public static final int P_LSTLOC=21; // LSTLOC;
	public static final int P_MAXLOC=22; // MXLOC;
	public static final int P_CHKPNT=23; // CHECKP;
	public static final int P_LOCKFI=24; // LOCKFI;
	public static final int P_UNLOCK=25; // UPLOCK;
	public static final int P_INIMAG=26; // INIMAG;
	public static final int P_OUTIMA=27; // OUTIMA;
	public static final int P_BREAKO=28; // BREAKO;
	public static final int P_LOCATE=29; // LOCATE;
	public static final int P_DELETE=30; // DELETE;
	public static final int P_GDSNAM=31; // GDSNAM;
	public static final int P_GDSPEC=32; // GDSPEC;
	public static final int P_GETLPP=33; // GETLPP;
	public static final int P_NEWPAG=34; // NEWPAG;
	public static final int P_INBYTE=35; // INBYTE;
	public static final int P_OUTBYT=36; // OUTBYT;
	public static final int P_GETINT=37; // GETINT;
	public static final int P_GTREAL=38; // GTREAL;
	public static final int P_GTFRAC=39; // GTFRAC;
	public static final int P_PUTSTR=40; // PUTSTR;
	public static final int P_PUTINT=41; // PUTINT;
	public static final int P_PUTINT2=42; // PUTINT2;
	public static final int P_PUTSIZE=43; // PUTSIZE;
	public static final int P_PUTHEX=44; // PUTHEX;
	public static final int P_PUTFIX=45; // PUTFIX;
	public static final int P_PUTFIX2=46; // PUTFIX2;
	public static final int P_PTLFIX=47; // PTLFIX;
	public static final int P_PTLFIX2=48; // PTLFIX2;
	public static final int P_PTREAL=49; // PTREAL;
	public static final int P_PTREAL2=50; // PTREAL2;
	public static final int P_PLREAL=51; // PLREAL;
	public static final int P_PLREAL2=52; // PLREAL2;
	public static final int P_PTFRAC=53; // PTFRAC;
	public static final int P_PTSIZE=54; // PTSIZE;
	public static final int P_PTOADR=55; // PTOADR;
	public static final int P_PTOADR2=56; // PTOADR2;
	public static final int P_PTAADR=57; // PTAADR;
	public static final int P_PTAADR2=58; // PTAADR2;
	public static final int P_PTGADR=59; // PTGADR;
	public static final int P_PTGADR2=60; // PTGADR2;
	public static final int P_PTPADR=61; // PTPADR;
	public static final int P_PTPADR2=62; // PTPADR2;
	public static final int P_PTRADR=63; // PTRADR;
	public static final int P_PTRADR2=64; // PTRADR2;
	public static final int P_DRAWRP=65; // DRAWRP;
	public static final int P_DATTIM=66; // DATTIM;
	public static final int P_LOWTEN=67; // LTEN;
	public static final int P_DCMARK=68; // DECMRK;
	public static final int P_RSQROO=69; // RSQROO;
	public static final int P_SQROOT=70; // SQROOT;
	public static final int P_RLOGAR=71; // RLOGAR;
	public static final int P_LOGARI=72; // LOGARI;
	public static final int P_RLOG10=73; // RLOG10;
	public static final int P_DLOG10=74; // DLOG10;
	public static final int P_REXPON=75; // REXPON;
	public static final int P_EXPONE=76; // EXPONE;
	public static final int P_RSINUS=77; // RSINUS;
	public static final int P_SINUSR=78; // SINUSR;
	public static final int P_RCOSIN=79; // RCOSIN;
	public static final int P_COSINU=80; // COSINU;
	public static final int P_RTANGN=81; // RTANGN;
	public static final int P_TANGEN=82; // TANGEN;
	public static final int P_RCOTAN=83; // COTANR;
	public static final int P_COTANG=84; // COTANG;
	public static final int P_RARTAN=85; // RARTAN;
	public static final int P_ARCTAN=86; // ARCTAN;
	public static final int P_RARCOS=87; // RARCOS;
	public static final int P_ARCCOS=88; // ARCCOS;
	public static final int P_RARSIN=89; // RARSIN;
	public static final int P_ARCSIN=90; // ARCSIN;
	public static final int P_RATAN2=91; // ATAN2R;
	public static final int P_ATAN2=92; // ATAN2;
	public static final int P_RSINH=93; // SINHR;
	public static final int P_SINH=94; // SINH;
	public static final int P_RCOSH=95; // COSHR;
	public static final int P_COSH=96; // COSH;
	public static final int P_RTANH=97; // TANHR;
	public static final int P_TANH=98; // TANH;
	public static final int P_BEGDEB=99; // BEGDEB;
	public static final int P_ENDDEB=100; // ENDDEB;
	public static final int P_BEGTRP=101; // BEGTRP;
	public static final int P_ENDTRP=102; // ENDTRP;
	public static final int P_GTPADR=103; // GTPADR;
	public static final int P_GTOUTM=104; // GTOUTM;
	public static final int P_GTLNID=105; //  GTLNID;
	public static final int P_GTLNO=106; // GTLNO;
	public static final int P_BRKPNT=107; // BRKPNT;
	public static final int P_STMNOT=108; // STMNOT;
	public static final int P_DMPOBJ=109; //  DMPOBJ;

	// KNOWN
	public static final int P_MODULO=110; //  KNOWN
	public static final int P_RADDEP=111; //  KNOWN
	public static final int P_DADDEP=112; //  KNOWN
	public static final int P_RSUBEP=113;
	public static final int P_DSUBEP=114;
	public static final int P_IIPOWR=115;
	public static final int P_RIPOWR=116;
	public static final int P_RRPOWR=117;
	public static final int P_RDPOWR=118;
	public static final int P_DIPOWR=119;
	public static final int P_DRPOWR=120;
	public static final int P_DDPOWR=121;
	public static final int P_STRIP=122;  // Known("STRIP")
	public static final int P_TRFREL=123; // Known("TRFREL")
	public static final int P_ZEROAREA=124; // Known("TRFREL")
//
//	 Define P_RLOG10=32      -- Known("RLOG10")
//	 Define P_DLOG10=33      -- Known("DLOG10")
//	 Define P_RCOSIN=34      -- Known("RCOSIN")
//	 Define P_COSINU=35      -- Known("COSINU")
//	 Define P_RTANGN=36      -- Known("RTANGN")
//	 Define P_TANGEN=37      -- Known("TANGEN")
//	 Define P_RARCOS=38      -- Known("RARCOS")
//	 Define P_ARCCOS=39      -- Known("ARCCOS")
//	 Define P_RARSIN=40      -- Known("RARSIN")
//	 Define P_ARCSIN=41      -- Known("ARCSIN")
//
//	 Define P_ERRNON=42      -- Known("ERRNON")
//	 Define P_ERRQUA=43      -- Known("ERRQUA")
//	 Define P_ERRSWT=44      -- Known("ERRSWT")
//	 Define P_ERROR=45       -- Known("ERROR")
//
//	 Define P_CBLNK=46       -- Known("CBLNK")
//	 Define P_CMOVE=47       -- Known("CMOVE")
//	 Define P_TXTREL=49      -- Known("TXTREL")
//
//	 Define P_AR1IND=51      -- Known("AR1IND")
//	 Define P_AR2IND=52      -- Known("AR2IND")
//	 Define P_ARGIND=53      -- Known("ARGIND")
//
//	 Define P_IABS=54        -- Known("IABS")
//	 Define P_RABS=55        -- Known("RABS")
//	 Define P_DABS=56        -- Known("DABS")
//	 Define P_RSIGN=57       -- Known("RSIGN")
//	 Define P_DSIGN=58       -- Known("DSIGN")
//	 Define P_MODULO=59      -- Known("MODULO")
//	 Define P_RENTI=60       -- Known("RENTI")
//	 Define P_DENTI=61       -- Known("DENTI")
//	 Define P_DIGIT=62       -- Known("DIGIT")
//	 Define P_LETTER=63      -- Known("LETTER")
//
//	 Define P_RIPOWR=64      -- Known("RIPOWR")
//	 Define P_RRPOWR=65      -- Known("RRPOWR")
//	 Define P_RDPOWR=66      -- Known("RDPOWR")
//	 Define P_DIPOWR=67      -- Known("DIPOWR")
//	 Define P_DRPOWR=68      -- Known("DRPOWR")
//	 Define P_DDPOWR=69      -- Known("DDPOWR")
//	 Define P_max=125

//	 Visible sysroutine("OPFILE") OPFILE;
//	 Visible sysroutine("CLFILE") CLFILE;
//	 Visible sysroutine("LSTLOC") LSTLOC;
//	 Visible sysroutine("MAXLOC") MXLOC;
//	 Visible sysroutine("CHKPNT") CHECKP;
//	 Visible sysroutine("LOCKFI") LOCKFI;
//	 Visible sysroutine("UNLOCK") UPLOCK;
//	 Visible sysroutine("INIMAG") INIMAG;
//	 Visible sysroutine("OUTIMA") OUTIMA;
//	 Visible sysroutine("BREAKO") BREAKO;
//	 Visible sysroutine("LOCATE") LOCATE;
//	 Visible sysroutine("DELETE") DELETE;
//	 Visible sysroutine("GDSNAM") GDSNAM;
//	 Visible sysroutine("GDSPEC") GDSPEC;
//	 Visible sysroutine("GETLPP") GETLPP;
//	 Visible sysroutine("NEWPAG") NEWPAG;
//	 Visible sysroutine("PRINTO") PRINTO;
//	 Visible sysroutine("STREQL") STREQL;
//	 Visible sysroutine("INBYTE") INBYTE;
//	 Visible sysroutine("OUTBYT") OUTBYT;
//	 Visible sysroutine("GETINT") GETINT;
//	 Visible sysroutine("GTREAL") GTREAL;
//	 Visible sysroutine("GTFRAC") GTFRAC;
//	 Visible sysroutine("PUTSTR") PUTSTR;
//	 Visible sysroutine("PUTINT") PUTINT;
//	 Visible sysroutine("PUTINT2") PUTINT2;
//	 Visible sysroutine("PUTSIZE") PUTSIZE;
//	 Visible sysroutine("PUTHEX") PUTHEX;
//	 Visible sysroutine("PUTFIX") PUTFIX;
//	 Visible sysroutine("PUTFIX2") PUTFIX2;
//	 Visible sysroutine("PTLFIX") PTLFIX;
//	 Visible sysroutine("PTLFIX2") PTLFIX2;
//	 Visible sysroutine("PTREAL") PTREAL;
//	 Visible sysroutine("PTREAL2") PTREAL2;
//	 Visible sysroutine("PLREAL") PLREAL;
//	 Visible sysroutine("PLREAL2") PLREAL2;
//	 Visible sysroutine("PTFRAC") PTFRAC;
//	 Visible sysroutine ("PTSIZE") PTSIZE;
//	 Visible sysroutine ("PTOADR") PTOADR;
//	 Visible sysroutine ("PTOADR2") PTOADR2;
//	 Visible sysroutine ("PTAADR") PTAADR;
//	 Visible sysroutine ("PTAADR2") PTAADR2;
//	 Visible sysroutine ("PTGADR") PTGADR;
//	 Visible sysroutine ("PTGADR2") PTGADR2;
//	 Visible sysroutine ("PTPADR") PTPADR;
//	 Visible sysroutine ("PTPADR2") PTPADR2;
//	 Visible sysroutine ("PTRADR") PTRADR;
//	 Visible sysroutine ("PTRADR2") PTRADR2;
//	 Visible sysroutine("DRAWRP") DRAWRP;
//	 Visible sysroutine("DATTIM") DATTIM;
//	 Visible sysroutine("LOWTEN") LTEN;
//	 Visible sysroutine("DCMARK") DECMRK;
//	 Visible sysroutine ("RSQROO") RSQROO;
//	 Visible sysroutine("SQROOT") SQROOT;
//	 Visible sysroutine ("RLOGAR") RLOGAR;
//	 Visible sysroutine("LOGARI") LOGARI;
//	 Visible sysroutine("RLOG10") RLOG10;
//	 Visible sysroutine("DLOG10") DLOG10;
//	 Visible sysroutine ("REXPON") REXPON;
//	 Visible sysroutine("EXPONE") EXPONE;
//	 Visible sysroutine("RSINUS") RSINUS;
//	 Visible sysroutine("SINUSR") SINUSR;
//	 Visible sysroutine("RCOSIN") RCOSIN;
//	 Visible sysroutine("COSINU") COSINU;
//	 Visible sysroutine("RTANGN") RTANGN;
//	 Visible sysroutine("TANGEN") TANGEN;
//	 Visible sysroutine("RCOTAN") COTANR;
//	 Visible sysroutine("COTANG") COTANG;
//	 Visible sysroutine("RARTAN") RARTAN;
//	 Visible sysroutine("ARCTAN") ARCTAN;
//	 Visible sysroutine("RARCOS") RARCOS;
//	 Visible sysroutine("ARCCOS") ARCCOS;
//	 Visible sysroutine("RARSIN") RARSIN;
//	 Visible sysroutine("ARCSIN") ARCSIN;
//	 Visible sysroutine("RATAN2") ATAN2R;
//	 Visible sysroutine("ATAN2") ATAN2;
//	 Visible sysroutine("RSINH") SINHR;
//	 Visible sysroutine("SINH") SINH;
//	 Visible sysroutine("RCOSH") COSHR;
//	 Visible sysroutine("COSH") COSH;
//	 Visible sysroutine("RTANH") TANHR;
//	 Visible sysroutine("TANH") TANH;
//	 Visible sysroutine ("BEGDEB") BEGDEB;
//	 Visible sysroutine ("ENDDEB") ENDDEB;
//	 Visible sysroutine ("BEGTRP") BEGTRP;
//	 Visible sysroutine ("ENDTRP") ENDTRP;
//	 Visible sysroutine ("GTPADR") GTPADR;
//	 Visible sysroutine("GTOUTM") GTOUTM;
//	 Visible sysroutine("GTLNID")  GTLNID;
//	 Visible sysroutine("GTLNO") GTLNO;
//	 Visible sysroutine("BRKPNT") BRKPNT;
//	 Visible sysroutine("STMNOT") STMNOT;
//	 Visible sysroutine("DMPOBJ")  DMPOBJ;

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeKind(kind);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_CALLSYS instr = new SVM_CALLSYS(inpt.readKind());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}


}
