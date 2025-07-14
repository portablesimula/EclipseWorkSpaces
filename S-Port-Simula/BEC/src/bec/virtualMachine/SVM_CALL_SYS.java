package bec.virtualMachine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.segment.DataSegment;
import bec.util.EndProgram;
import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.BooleanValue;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;
import bec.virtualMachine.sysrut.SysDeEdit;
import bec.virtualMachine.sysrut.SysDraw;
import bec.virtualMachine.sysrut.SysEdit;
import bec.virtualMachine.sysrut.SysFile;
import bec.virtualMachine.sysrut.SysInfo;
import bec.virtualMachine.sysrut.SysKnown;
import bec.virtualMachine.sysrut.SysMath;

public class SVM_CALL_SYS extends SVM_Instruction {
	int kind;

	public SVM_CALL_SYS(int kind) {
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
			case P_DATTIM:   dattim(); break;
			case P_CPUTIM:   cputime(); break;
			case P_TERMIN:   terminate(); break;
			case P_STREQL:   stringEqual(); break;
			case P_PRINTO:   printo(); break;
			case P_MOVEIN:   movein(); break;

			case P_DMPENT:   SysInfo.dmpent(); break;
			case P_GINTIN:   SysInfo.getIntinfo(); break;
			case P_SIZEIN:   SysInfo.sizein(); break;
			case P_GVIINF:   SysInfo.gviinf(); break;

			case P_GDSPEC:   SysFile.gdspec(); break;
			case P_GETLPP:   SysFile.getlpp(); break;
			case P_OPFILE:   SysFile.opfile(); break;
			case P_NEWPAG:   SysFile.newpag(); break;
			case P_CLFILE:   SysFile.clfile(); break;
			case P_INIMAG:   SysFile.INIMAG(); break;
			case P_OUTIMA:   SysFile.OUTIMA(); break;
			case P_BREAKO:   SysFile.BREAKO(); break;
			case P_INBYTE:   SysFile.INBYTE(); break;
			case P_OUTBYT:   SysFile.OUTBYT(); break;
			case P_LOCATE:   SysFile.LOCATE(); break;
			case P_MAXLOC:   SysFile.MXLOC();  break;
			case P_LSTLOC:   SysFile.LSTLOC(); break;

			case P_GETINT:   SysDeEdit.getint(); break;
			case P_GTFRAC:   SysDeEdit.getfrac(); break;
			case P_GTREAL:   SysDeEdit.getreal(); break;

			case P_PUTSTR:   SysEdit.putstr(); break;
			case P_PUTINT:   SysEdit.putint(); break;
			case P_PUTINT2:  SysEdit.putint2(); break;
			case P_PTFRAC:   SysEdit.putfrac(); break;
			case P_PTREAL:   SysEdit.putreal(); break;
			case P_PTREAL2:  SysEdit.putreal2(); break;
			case P_PLREAL:   SysEdit.putlreal(); break;
			case P_PLREAL2:  SysEdit.putlreal2(); break;
			case P_PUTFIX:   SysEdit.putfix(); break;
			case P_PUTFIX2:  SysEdit.putfix2(); break;
			case P_PTLFIX:   SysEdit.putlfix(); break;
			case P_PTLFIX2:  SysEdit.putlfix2(); break;
			case P_PUTHEX:   SysEdit.puthex(); break;
			case P_PUTSIZE:  SysEdit.putsize(); break;
			case P_PTOADR2:  SysEdit.ptoadr2(); break;
			case P_PTPADR2:  SysEdit.ptpadr2(); break;
			case P_PTRADR2:  SysEdit.ptradr2(); break;
			case P_PTAADR2:  SysEdit.ptaadr2(); break;
			case P_PTGADR2:  SysEdit.ptgadr2(); break;
			
			case P_RADDEP:   SysMath.raddep(); break;
			case P_RSUBEP:   SysMath.rsubep(); break;
			case P_DADDEP:   SysMath.daddep(); break;
			case P_DSUBEP:   SysMath.dsubep(); break;
			case P_MODULO:   SysMath.modulo(); break;
			case P_IIPOWR:   SysMath.iipowr(); break;
			case P_RIPOWR:   SysMath.ripowr(); break;
			case P_DIPOWR:   SysMath.dipowr(); break;
			case P_RRPOWR:   SysMath.rrpowr(); break;
			case P_DDPOWR:   SysMath.ddpowr(); break;
			
			case P_RSQROO:   SysMath.rsqroo(); break;
			case P_SQROOT:   SysMath.sqroot(); break;
			case P_RLOGAR:   SysMath.rlogar(); break;
			case P_LOGARI:   SysMath.logari(); break;
			case P_REXPON:   SysMath.rexpon(); break;
			case P_EXPONE:   SysMath.expone(); break;

			case P_RSINUS:   SysMath.rsinus(); break;
			case P_SINUSR:   SysMath.sinusr(); break;
			case P_RCOSIN:   SysMath.rcosin(); break;
			case P_COSINU:   SysMath.cosinu(); break;
			case P_RARTAN:   SysMath.rartan(); break;
			case P_ARCTAN:   SysMath.arctan(); break;

			case P_DRAWRP:   SysDraw.drawrp(); break;

			case P_CMOVE:    SysKnown.cmove(); break;
			case P_CBLNK:    SysKnown.cblnk(); break;
			default: Util.IERR("SVM_SYSCALL: Unknown System Routine " + edKind(kind));
		}
		Global.PSC.addOfst(1);
	}
	
	public static void ENTER(String ident, int exportSize, int importSize) {
		if(Global.EXEC_TRACE > 4)
			RTStack.dumpRTStack(ident+"ENTER: ");
		int rtStackIndex = RTStack.size() - (exportSize + importSize);
		CallStackFrame callStackFrame = new CallStackFrame(ident, rtStackIndex, exportSize, importSize);
		RTStack.callStack.push(callStackFrame);
		
		if(Global.EXEC_TRACE > 0) {
			ProgramAddress.printInstr("CALLSYS  " + ident,false);
			if(Global.EXEC_TRACE > 2)
				RTStack.callStack_TOP().dump(ident+"ENTER: ");
		}

		if(Global.CALL_TRACE_LEVEL > 0)
			RTStack.printCallTrace("SVM_CALLSYS.ENTER: ");
	}
	
	public static void EXIT(String ident) {
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
		ProgramAddress exchdl = (ProgramAddress) RTStack.pop();
		if(Global.verbose) System.out.println("SVM_SYSCALL.initia: "+exchdl);
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
		if(Global.DUMPS_AT_EXIT) {
//			Segment.lookup("DSEG_ADHOC02").dump("SVM_SYSCALL.terminate: ");
			Global.DSEG.dump("SVM_SYSCALL.terminate: FINAL DATA SEGMENT ");
			Global.CSEG.dump("SVM_SYSCALL.terminate: FINAL CONSTANT SEGMENT ");
			Global.TSEG.dump("SVM_SYSCALL.terminate: FINAL CONSTANT TEXT SEGMENT ");
//			Segment.lookup("DSEG_RT").dump("SVM_SYSCALL.terminate: BIOINS", 30, 82);
//			Segment.lookup("POOL_1").dump("SVM_SYSCALL.terminate: FINAL POOL_1", 0, 20);
			RTUtil.printPool("POOL_1");
		}

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
	 *  Visible sysroutine("PRINTO") PRINTO;
	 *  	 import range(1:MAX_KEY) key; infix(string) image; integer spc;
	 *  end;
	 */
	private void printo() {
		ENTER("PRINTO: ", 0, 5); // exportSize, importSize

		int spc = RTStack.popInt();
		int nchr = RTStack.popInt();
		int ofst = RTStack.popInt();
		ObjectAddress chradr = (ObjectAddress) RTStack.pop();
		/*int key = */RTStack.popInt();

		ObjectAddress x = chradr.addOffset(ofst);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<nchr;i++) {
			IntegerValue ival = (IntegerValue) x.load(); x.incrOffset();
			char c = (ival == null)? '.' : ((char) ival.value);
			sb.append(c);
		}
		String res = sb.toString().stripTrailing();
//		System.out.println("SVM_CALL.printo: \""+res+'"');
		if(Global.console != null)
			Global.console.write(res+'\n');
		System.out.println(res);
		
		if(spc != 1) {
			if(spc < 1) {
				RTUtil.set_STATUS(19);
			} else {
				for(int i=1;i<spc;i++) System.out.println();
			}
		}
		
		EXIT("PRINTO: ");
	}

	/**
	 *  Visible sysroutine("MOVEIN") MOVEIN;
	 *  import ref() from,to; size length  end;
	 */
	private void movein() {
		ENTER("MOVEIN: ", 0, 5); // exportSize, importSize		
		int lng = RTStack.popInt();
		ObjectAddress to = RTStack.popOADDR();
		ObjectAddress from = RTStack.popOADDR();
//		System.out.println("SVM_CALL.movein: from="+from);
//		System.out.println("SVM_CALL.movein: to="+to);
//		System.out.println("SVM_CALL.movein: lng="+lng);

		for(int i=0;i<lng;i++) {
			Value val = from.load(i);
//			System.out.println("SVM_CALL.movein: idx="+i+", value="+val);
			to.store(i, val, "MOVEIN: ");
		}
		EXIT("MOVEIN: ");
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
		RTStack.push(null, ""); // ?????
		EXIT("ZEROAREA: ");
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

	/// Visible sysroutine("CPUTIM") CPUTIM;
	/// export long real sec  end;
	private void cputime() {
		ENTER("CPUTIM: ", 0, 1); // exportSize, importSize
		RTStack.push(null, "CPUTIM: ");
		EXIT("CPUTIM: ");
	}
	
	/// Visible sysroutine("DATTIM") DATTIM;
	/// import infix(string) item; export integer filled  end;
	///
	/// The result of a call on the routine will be filled into the string.
	/// The string should have the following syntax:
	/// "yyyy-mm-dd hh:nn:ss.ppp""
	private void dattim() {
		ENTER("DATTIM: ", 1, 3); // exportSize, importSize
		int nchr = RTStack.popInt();
		ObjectAddress oaddr = RTStack.popGADDRasOADDR();
		
//		String s = "2025-06-27 08:22:13.123";
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String s = LocalDateTime.now().format(form);
		int length = s.length();
		if(nchr < length) {
			RTUtil.set_STATUS(24);
			length = 0;
		} else {
			for(int i=0;i<length;i++) {
				Value val = IntegerValue.of(Type.T_CHAR, s.charAt(i));
				oaddr.store(i, val, "DATTIM: ");
			}
		}
		RTStack.push(IntegerValue.of(Type.T_INT, length), "DATTIM: ");
		EXIT("DATTIM: ");
	}



	public static int getSysKind(String s) {
//		System.out.println("ProfileDescr.getSysKind: "+s);
//		Thread.dumpStack();
		
		//--- Search for inline index ---
		if(s.equalsIgnoreCase("TERMIN")) return SVM_CALL_SYS.P_TERMIN;
		if(s.equalsIgnoreCase("INTRHA")) return SVM_CALL_SYS.P_INTRHA;
		if(s.equalsIgnoreCase("PXCHDL")) return SVM_CALL_SYS.P_PXCHDL;
//		if(s.equalsIgnoreCase("PEXERR")) return SVM_CALL_SYS.P_PEXERR;
		if(s.equalsIgnoreCase("PSIMOB")) return SVM_CALL_SYS.P_PSIMOB;
		if(s.equalsIgnoreCase("PobSML")) return SVM_CALL_SYS.P_PobSML;
		if(s.equalsIgnoreCase("Palloc")) return SVM_CALL_SYS.P_Palloc;
		if(s.equalsIgnoreCase("Pfree"))  return SVM_CALL_SYS.P_Pfree;
		if(s.equalsIgnoreCase("Pmovit")) return SVM_CALL_SYS.P_Pmovit;

		if(s.equalsIgnoreCase("STREQL")) return SVM_CALL_SYS.P_STREQL;
		if(s.equalsIgnoreCase("PRINTO")) return SVM_CALL_SYS.P_PRINTO;
		if(s.equalsIgnoreCase("INITIA")) return SVM_CALL_SYS.P_INITIA;
		if(s.equalsIgnoreCase("SETOPT")) return SVM_CALL_SYS.P_SETOPT;
		if(s.equalsIgnoreCase("DMPSEG")) return SVM_CALL_SYS.P_DMPSEG;
		if(s.equalsIgnoreCase("DMPENT")) return SVM_CALL_SYS.P_DMPENT;
		if(s.equalsIgnoreCase("DMPOOL")) return SVM_CALL_SYS.P_DMPOOL;
		if(s.equalsIgnoreCase("VERBOSE")) return SVM_CALL_SYS.P_VERBOSE;
		if(s.equalsIgnoreCase("GINTIN")) return SVM_CALL_SYS.P_GINTIN;
		if(s.equalsIgnoreCase("GTEXIN")) return SVM_CALL_SYS.P_GTEXIN;
		if(s.equalsIgnoreCase("SIZEIN")) return SVM_CALL_SYS.P_SIZEIN;
		if(s.equalsIgnoreCase("GVIINF")) return SVM_CALL_SYS.P_GVIINF;
		if(s.equalsIgnoreCase("GIVINF")) return SVM_CALL_SYS.P_GIVINF;
		if(s.equalsIgnoreCase("CPUTIM")) return SVM_CALL_SYS.P_CPUTIM;
		if(s.equalsIgnoreCase("DWAREA")) return SVM_CALL_SYS.P_DWAREA;
		if(s.equalsIgnoreCase("MOVEIN")) return SVM_CALL_SYS.P_MOVEIN;
		if(s.equalsIgnoreCase("OPFILE")) return SVM_CALL_SYS.P_OPFILE;
		if(s.equalsIgnoreCase("CLFILE")) return SVM_CALL_SYS.P_CLFILE;
		if(s.equalsIgnoreCase("LSTLOC")) return SVM_CALL_SYS.P_LSTLOC;
		if(s.equalsIgnoreCase("MAXLOC")) return SVM_CALL_SYS.P_MAXLOC;
		if(s.equalsIgnoreCase("CHKPNT")) return SVM_CALL_SYS.P_CHKPNT;
		if(s.equalsIgnoreCase("LOCKFI")) return SVM_CALL_SYS.P_LOCKFI;
		if(s.equalsIgnoreCase("UNLOCK")) return SVM_CALL_SYS.P_UNLOCK;
		if(s.equalsIgnoreCase("INIMAG")) return SVM_CALL_SYS.P_INIMAG;
		if(s.equalsIgnoreCase("OUTIMA")) return SVM_CALL_SYS.P_OUTIMA;
		if(s.equalsIgnoreCase("BREAKO")) return SVM_CALL_SYS.P_BREAKO;
		if(s.equalsIgnoreCase("LOCATE")) return SVM_CALL_SYS.P_LOCATE;
		if(s.equalsIgnoreCase("DELETE")) return SVM_CALL_SYS.P_DELETE;
		if(s.equalsIgnoreCase("GDSNAM")) return SVM_CALL_SYS.P_GDSNAM;
		if(s.equalsIgnoreCase("GDSPEC")) return SVM_CALL_SYS.P_GDSPEC;
		if(s.equalsIgnoreCase("GETLPP")) return SVM_CALL_SYS.P_GETLPP;
		if(s.equalsIgnoreCase("NEWPAG")) return SVM_CALL_SYS.P_NEWPAG;
		if(s.equalsIgnoreCase("PRINTO")) return SVM_CALL_SYS.P_PRINTO;
		if(s.equalsIgnoreCase("STREQL")) return SVM_CALL_SYS.P_STREQL;
		if(s.equalsIgnoreCase("INBYTE")) return SVM_CALL_SYS.P_INBYTE;
		if(s.equalsIgnoreCase("OUTBYT")) return SVM_CALL_SYS.P_OUTBYT;
		if(s.equalsIgnoreCase("GETINT")) return SVM_CALL_SYS.P_GETINT;
		if(s.equalsIgnoreCase("GTREAL")) return SVM_CALL_SYS.P_GTREAL;
		if(s.equalsIgnoreCase("GTFRAC")) return SVM_CALL_SYS.P_GTFRAC;
		if(s.equalsIgnoreCase("PUTSTR")) return SVM_CALL_SYS.P_PUTSTR;
		if(s.equalsIgnoreCase("PUTINT")) return SVM_CALL_SYS.P_PUTINT;
		if(s.equalsIgnoreCase("PUTINT2")) return SVM_CALL_SYS.P_PUTINT2;
		if(s.equalsIgnoreCase("PUTSIZE")) return SVM_CALL_SYS.P_PUTSIZE;
		if(s.equalsIgnoreCase("PUTHEX")) return SVM_CALL_SYS.P_PUTHEX;
		if(s.equalsIgnoreCase("PUTFIX")) return SVM_CALL_SYS.P_PUTFIX;
		if(s.equalsIgnoreCase("PUTFIX2")) return SVM_CALL_SYS.P_PUTFIX2;
		if(s.equalsIgnoreCase("PTLFIX")) return SVM_CALL_SYS.P_PTLFIX;
		if(s.equalsIgnoreCase("PTLFIX2")) return SVM_CALL_SYS.P_PTLFIX2;
		if(s.equalsIgnoreCase("PTREAL")) return SVM_CALL_SYS.P_PTREAL;
		if(s.equalsIgnoreCase("PTREAL2")) return SVM_CALL_SYS.P_PTREAL2;
		if(s.equalsIgnoreCase("PLREAL")) return SVM_CALL_SYS.P_PLREAL;
		if(s.equalsIgnoreCase("PLREAL2")) return SVM_CALL_SYS.P_PLREAL2;
		if(s.equalsIgnoreCase("PTFRAC")) return SVM_CALL_SYS.P_PTFRAC;
		if(s.equalsIgnoreCase("PTSIZE")) return SVM_CALL_SYS.P_PTSIZE;
		if(s.equalsIgnoreCase("PTOADR")) return SVM_CALL_SYS.P_PTOADR;
		if(s.equalsIgnoreCase("PTOADR2")) return SVM_CALL_SYS.P_PTOADR2;
		if(s.equalsIgnoreCase("PTAADR")) return SVM_CALL_SYS.P_PTAADR;
		if(s.equalsIgnoreCase("PTAADR2")) return SVM_CALL_SYS.P_PTAADR2;
		if(s.equalsIgnoreCase("PTGADR")) return SVM_CALL_SYS.P_PTGADR;
		if(s.equalsIgnoreCase("PTGADR2")) return SVM_CALL_SYS.P_PTGADR2;
		if(s.equalsIgnoreCase("PTPADR")) return SVM_CALL_SYS.P_PTPADR;
		if(s.equalsIgnoreCase("PTPADR2")) return SVM_CALL_SYS.P_PTPADR2;
		if(s.equalsIgnoreCase("PTRADR")) return SVM_CALL_SYS.P_PTRADR;
		if(s.equalsIgnoreCase("PTRADR2")) return SVM_CALL_SYS.P_PTRADR2;
		if(s.equalsIgnoreCase("DRAWRP")) return SVM_CALL_SYS.P_DRAWRP;
		if(s.equalsIgnoreCase("DATTIM")) return SVM_CALL_SYS.P_DATTIM;
		if(s.equalsIgnoreCase("LOWTEN")) return SVM_CALL_SYS.P_LOWTEN;
		if(s.equalsIgnoreCase("DCMARK")) return SVM_CALL_SYS.P_DCMARK;
		if(s.equalsIgnoreCase("RSQROO")) return SVM_CALL_SYS.P_RSQROO;
		if(s.equalsIgnoreCase("SQROOT")) return SVM_CALL_SYS.P_SQROOT;
		if(s.equalsIgnoreCase("RLOGAR")) return SVM_CALL_SYS.P_RLOGAR;
		if(s.equalsIgnoreCase("LOGARI")) return SVM_CALL_SYS.P_LOGARI;
		if(s.equalsIgnoreCase("RLOG10")) return SVM_CALL_SYS.P_RLOG10;
		if(s.equalsIgnoreCase("DLOG10")) return SVM_CALL_SYS.P_DLOG10;
		if(s.equalsIgnoreCase("REXPON")) return SVM_CALL_SYS.P_REXPON;
		if(s.equalsIgnoreCase("EXPONE")) return SVM_CALL_SYS.P_EXPONE;
		if(s.equalsIgnoreCase("RSINUS")) return SVM_CALL_SYS.P_RSINUS;
		if(s.equalsIgnoreCase("SINUSR")) return SVM_CALL_SYS.P_SINUSR;
		if(s.equalsIgnoreCase("RCOSIN")) return SVM_CALL_SYS.P_RCOSIN;
		if(s.equalsIgnoreCase("COSINU")) return SVM_CALL_SYS.P_COSINU;
		if(s.equalsIgnoreCase("RTANGN")) return SVM_CALL_SYS.P_RTANGN;
		if(s.equalsIgnoreCase("TANGEN")) return SVM_CALL_SYS.P_TANGEN;
		if(s.equalsIgnoreCase("RCOTAN")) return SVM_CALL_SYS.P_RCOTAN;
		if(s.equalsIgnoreCase("COTANG")) return SVM_CALL_SYS.P_COTANG;
		if(s.equalsIgnoreCase("RARTAN")) return SVM_CALL_SYS.P_RARTAN;
		if(s.equalsIgnoreCase("ARCTAN")) return SVM_CALL_SYS.P_ARCTAN;
		if(s.equalsIgnoreCase("RARCOS")) return SVM_CALL_SYS.P_RARCOS;
		if(s.equalsIgnoreCase("ARCCOS")) return SVM_CALL_SYS.P_ARCCOS;
		if(s.equalsIgnoreCase("RARSIN")) return SVM_CALL_SYS.P_RARSIN;
		if(s.equalsIgnoreCase("ARCSIN")) return SVM_CALL_SYS.P_ARCSIN;
		if(s.equalsIgnoreCase("RATAN2")) return SVM_CALL_SYS.P_RATAN2;
		if(s.equalsIgnoreCase("ATAN2")) return SVM_CALL_SYS.P_ATAN2;
		if(s.equalsIgnoreCase("RSINH")) return SVM_CALL_SYS.P_RSINH;
		if(s.equalsIgnoreCase("SINH")) return SVM_CALL_SYS.P_SINH;
		if(s.equalsIgnoreCase("RCOSH")) return SVM_CALL_SYS.P_RCOSH;
		if(s.equalsIgnoreCase("COSH")) return SVM_CALL_SYS.P_COSH;
		if(s.equalsIgnoreCase("RTANH")) return SVM_CALL_SYS.P_RTANH;
		if(s.equalsIgnoreCase("TANH")) return SVM_CALL_SYS.P_TANH;
		if(s.equalsIgnoreCase("BEGDEB")) return SVM_CALL_SYS.P_BEGDEB;
		if(s.equalsIgnoreCase("ENDDEB")) return SVM_CALL_SYS.P_ENDDEB;
		if(s.equalsIgnoreCase("BEGTRP")) return SVM_CALL_SYS.P_BEGTRP;
		if(s.equalsIgnoreCase("ENDTRP")) return SVM_CALL_SYS.P_ENDTRP;
		if(s.equalsIgnoreCase("GTPADR")) return SVM_CALL_SYS.P_GTPADR;
		if(s.equalsIgnoreCase("GTOUTM")) return SVM_CALL_SYS.P_GTOUTM;
		if(s.equalsIgnoreCase("GTLNID")) return SVM_CALL_SYS.P_GTLNID;
		if(s.equalsIgnoreCase("GTLNO")) return SVM_CALL_SYS.P_GTLNO;
		if(s.equalsIgnoreCase("BRKPNT")) return SVM_CALL_SYS.P_BRKPNT;
		if(s.equalsIgnoreCase("STMNOT")) return SVM_CALL_SYS.P_STMNOT;
		if(s.equalsIgnoreCase("DMPOBJ")) return SVM_CALL_SYS.P_DMPOBJ;

		// KNOWN 
		if(s.equalsIgnoreCase("MODULO")) return SVM_CALL_SYS.P_MODULO;
		if(s.equalsIgnoreCase("RADDEP")) return SVM_CALL_SYS.P_RADDEP;
		if(s.equalsIgnoreCase("DADDEP")) return SVM_CALL_SYS.P_DADDEP;
		if(s.equalsIgnoreCase("RSUBEP")) return SVM_CALL_SYS.P_RSUBEP;
		if(s.equalsIgnoreCase("DSUBEP")) return SVM_CALL_SYS.P_DSUBEP;
		if(s.equalsIgnoreCase("IIPOWR")) return SVM_CALL_SYS.P_IIPOWR;
		if(s.equalsIgnoreCase("RIPOWR")) return SVM_CALL_SYS.P_RIPOWR;
		if(s.equalsIgnoreCase("RRPOWR")) return SVM_CALL_SYS.P_RRPOWR;
		if(s.equalsIgnoreCase("RDPOWR")) return SVM_CALL_SYS.P_RDPOWR;
		if(s.equalsIgnoreCase("DIPOWR")) return SVM_CALL_SYS.P_DIPOWR;
		if(s.equalsIgnoreCase("DRPOWR")) return SVM_CALL_SYS.P_DRPOWR;
		if(s.equalsIgnoreCase("DDPOWR")) return SVM_CALL_SYS.P_DDPOWR;

//		Util.IERR(""+s);
		return 0;
	}


	public static int getKnownKind(String s) {
//		System.out.println("ProfileDescr.getKnownKind: "+s);
//		Thread.dumpStack();
		
		//--- Search for inline index ---
		if(s.equalsIgnoreCase("CBLNK")) return SVM_CALL_SYS.P_CBLNK;
		if(s.equalsIgnoreCase("CMOVE")) return SVM_CALL_SYS.P_CMOVE;

//		Util.IERR(""+s);
		return 0;
	}

//	---------     S y s t e m    K i n d    C o d e s      ---------
	
	public static String edKind(int kind) {
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

		case P_CBLNK: return "CBLNK";
		case P_CMOVE: return "CMOVE";
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
	public static final int P_CBLNK=125; // Known("CBLNK")
	public static final int P_CMOVE=126; // Known("CMOVE")
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
		SVM_CALL_SYS instr = new SVM_CALL_SYS(inpt.readKind());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}


}
