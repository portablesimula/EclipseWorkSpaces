package iAPX.util;

import static iAPX.util.Global.*;

import java.io.FileInputStream;
import java.io.IOException;

import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;

/*
 * Usage:
 * 
 * 	int i /* NormalDefined.ordinal();
 * 
 * 	TestEnum normalDefined /* TestEnum.of(3);

 */
public enum Scode {

	//  ------   S  -  I  N  S  T  R  U  C  T  I  O  N  S   ------

	S_NULL/*0*/, S_RECORD/*1*/, 
	S_LSHIFTL/*2*/,    // Extension to S-Code:  Left shift logical
	S_PREFIX/*3*/, S_ATTR/*4*/,  
	S_LSHIFTA/*5*/,    // Extension to S-Code:  Left shift arithmetical
	S_REP/*6*/, S_ALT/*7*/, S_FIXREP/*8*/, S_ENDRECORD/*9*/, S_C_RECORD/*10*/, S_TEXT/*11*/, S_C_CHAR/*12*/, S_C_INT/*13*/, S_C_SIZE/*14*/, S_C_REAL/*15*/, S_C_LREAL/*16*/, 
	S_C_AADDR/*17*/, S_C_OADDR/*18*/, S_C_GADDR/*19*/, S_C_PADDR/*20*/, S_C_DOT/*21*/, S_C_RADDR/*22*/, S_NOBODY/*23*/, S_ANONE/*24*/, S_ONONE/*25*/, S_GNONE/*26*/, 
	S_NOWHERE/*27*/, S_TRUE/*28*/, S_FALSE/*29*/, S_PROFILE/*30*/, S_KNOWN/*31*/, S_SYSTEM/*32*/, S_EXTERNAL/*33*/, S_IMPORT/*34*/, S_EXPORT/*35*/, S_EXIT/*36*/, 
	S_ENDPROFILE/*37*/, S_ROUTINESPEC/*38*/, S_ROUTINE/*39*/,  S_LOCAL/*40*/, S_ENDROUTINE/*41*/, S_MODULE/*42*/, S_EXISTING/*43*/, S_TAG/*44*/, S_BODY/*45*/,
	S_ENDMODULE/*46*/, S_LABELSPEC/*47*/, S_LABEL/*48*/, S_RANGE/*49*/, S_GLOBAL/*50*/, S_INIT/*51*/, S_CONSTSPEC/*52*/, S_CONST/*53*/, S_DELETE/*54*/, S_FDEST/*55*/, 
	S_BDEST/*56*/, S_SAVE/*57*/, S_RESTORE/*58*/, S_BSEG/*59*/, S_ESEG/*60*/, S_SKIPIF/*61*/, S_ENDSKIP/*62*/, S_IF/*63*/, S_ELSE/*64*/, S_ENDIF/*65*/, 
	S_RSHIFTL/*66*/,   // Extension to S-Code:  Right shift logical
	S_PRECALL/*67*/, S_ASSPAR/*68*/, S_ASSREP/*69*/, S_CALL/*70*/, S_FETCH/*71*/, S_REFER/*72*/, S_DEREF/*73*/, S_SELECT/*74*/, S_REMOTE/*75*/, S_LOCATE/*76*/, 
	S_INDEX/*77*/, S_INCO/*78*/, S_DECO/*79*/, S_PUSH/*80*/, S_PUSHC/*81*/, S_PUSHLEN/*82*/, S_DUP/*83*/, S_POP/*84*/, S_EMPTY/*85*/, S_SETOBJ/*86*/, S_GETOBJ/*87*/, 
	S_ACCESS/*88*/, S_FJUMP/*89*/, S_BJUMP/*90*/, S_FJUMPIF/*91*/, S_BJUMPIF/*92*/, S_SWITCH/*93*/, S_GOTO/*94*/, S_T_INITO/*95*/, S_T_GETO/*96*/, S_T_SETO/*97*/, 
	S_ADD/*98*/, S_SUB/*99*/, S_MULT/*100*/, S_DIV/*101*/, S_REM/*102*/, S_NEG/*103*/, S_AND/*104*/,  S_OR /*105*/, S_XOR/*106*/, S_IMP/*107*/, S_EQV/*108*/, S_NOT/*109*/, 
	S_DIST/*110*/, S_ASSIGN/*111*/, S_UPDATE/*112*/, S_CONVERT/*113*/, S_SYSINSERT/*114*/, S_INSERT/*115*/, S_ZEROAREA/*116*/, S_INITAREA/*117*/, S_COMPARE/*118*/, 
	S_LT/*119*/, S_LE/*120*/, S_EQ/*121*/, S_GE/*122*/, S_GT/*123*/, S_NE/*124*/, S_EVAL/*125*/, S_INFO/*126*/, S_LINE/*127*/, S_SETSWITCH/*128*/, 
	S_RSHIFTA/*129*/,  // Extension to S-Code:  Right shift arithmetical
	S_PROGRAM/*130*/, S_MAIN/*131*/, S_ENDPROGRAM/*132*/, S_DSIZE/*133*/, S_SDEST/*134*/, S_RUPDATE/*135*/, S_ASSCALL/*136*/, S_CALL_TOS/*137*/, S_DINITAREA/*138*/, 
	S_NOSIZE/*139*/, S_POPALL/*140*/, S_REPCALL/*141*/,  S_INTERFACE/*142*/,  S_MACRO/*143*/, S_MARK/*144*/, S_MPAR/*145*/, S_ENDMACRO/*146*/, S_MCALL/*147*/,
	S_PUSHV/*148*/, S_SELECTV/*149*/, S_REMOTEV/*150*/, S_INDEXV/*151*/, S_ACCESSV/*152*/, S_DECL/*153*/, S_STMT/*154*/, 

	S_STRING, /*155*/
	;

	public static int S_max = 155;  // Max value of S-Instruction codes

	private static Scode[] values = Scode.values(); // Get all enum constants as an array
	public static Scode of(int ordinale) {
		return values[ordinale];
	}
	
	public void write(Scode testEnum, AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(testEnum.ordinal());
	}
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(this.ordinal());
	}

	public static Scode read(AttributeInputStream inpt) throws IOException {
		return Scode.of(inpt.readShort());
	}

//	%title ***  B A S I C    S - C O D E    I N P U T  ***
	private static byte[] SBUF;
	private static int SBUF_nxt;
	public static Scode curinstr;	// Current instr-byte read from scode
	
	static StringBuilder traceBuff;         // Input Trace's StringBuilder
	public static Scode curline;		// Current source line number


	public static void initScode() {
		if(S_STRING.ordinal() != 155) Util.IERR("");
		Tag.initTags();

		String fileName = SCODID;
//		IO.println("Open SCode file: " + fileName);
		try (FileInputStream scode = new FileInputStream(fileName)) {
			SBUF = scode.readAllBytes();
			SBUF_nxt = 0;
//			if(Global.verbose) {
			if(Option.verbose) {
				IO.println("Open SCode file: " + fileName + "   size = " + SBUF.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initTraceBuff(String head) {
		Scode.traceBuff = new StringBuilder(head);		
	}
	
	public static void flushTraceBuff() {
		if(Option.SCODE_INPUT_TRACE) {
//			String ctstk = (CTStack.size() == 0)? "" : " "+CTStack.ident()+"-STACK"+"["+CTStack.size()+"]: "+CTStack.TOS();
			String line = traceBuff.toString();
			while(line.length() < 60) line = line + " ";
			IO.println(line); // + ctstk);
//			if(CTStack.size() > 1) {
//				line = "";
//				while(line.length() < 71) line = line + " ";
//				for(int i=CTStack.size()-2;i>=0;i--) {
//					CTStackItem item = CTStack.getItem(i);
//					IO.println(line + item);
//				}
////				CTStack.dumpStack("Scode.flushTraceBuff");
//			}
		}
//		if(Global.PRINT_GENERATED_SVM_CODE) {
//			if(Global.PSEG != null) Global.PSEG.listInstructions();
//		}
	}

	public static void close() {
//		if(Global.SCODE_INPUT_TRACE) IO.println(traceBuff);	
		flushTraceBuff();
		traceBuff = null;
	}
	
	public static Scode nextByte() {
		try {
			return Scode.of(SBUF[SBUF_nxt] & 0xff);
		} catch (ArrayIndexOutOfBoundsException e) {
			return Scode.of(0xff);
		}
	}
	
	private static int getByte() {
		int b = SBUF[SBUF_nxt++] & 0xff;
//		IO.println("Scode.getByte: " + b + ", asInstr="+edInstr(b));
		return b;
	}
	
	public static int get2Bytes() {
		int HI = getByte();
		int LO = getByte();
		return (HI << 8) | LO; 
	}

	public static void inputInstr() {
		Scode.curinstr = Scode.of(getByte());
//		IO.println("Scode.inputInstr: " + (SBUF_nxt-1) + ": " + Scode.curinstr + " " + Scode.curinstr);
//		       if SBUF.nxt >= sBufLen then InSbuffer endif;
		if(Scode.curinstr.ordinal() < 1 || Scode.curinstr.ordinal() > S_max) {
			if(traceBuff != null) IO.println(traceBuff);
			dumpBytes(4);
			Util.IERR("Illegal instruction["+(SBUF_nxt -1)+"]: " + Scode.curinstr);
		}
		if(Option.SCODE_INPUT_TRACE) {
			if(traceBuff != null) {
//				IO.println(traceBuff);
				flushTraceBuff();
			}
			String instr = "" + Scode.curinstr;
			while(instr.length() < 10) instr = instr + ' ';
//			Util.ITRC("Ininstr["+(SBUF_nxt-1)+",CTStack="+CTStack.size()+']', instr);
			Util.ITRC("Ininstr["+(SBUF_nxt-1)+']', instr);
//			IO.println("Scode.inputInstr: " + edInstr(Scode.curinstr)+":"+Scode.curinstr);
		}
	}

	public static Type inType() { // export range(0:MaxType) type;
		Tag tag = Tag.inTag();
		int fixrep = 0;
		if(tag.isBasic()) {
			if(tag.val == Tag.TAG_INT) {
				//        	   IO.println("Scode.inType: curinstr="+edInstr(curinstr));
				//        	   IO.println("Scode.inType: nextByte="+edInstr(nextByte()));
				//if NextByte <> S_RANGE then type = T_INT;
				if(Scode.accept(Scode.S_RANGE)) {
					int lb = Scode.inNumber(); int ub = Scode.inNumber();
					//        		   if(lb > ub) Util.IERR("SBASE:intype");
					//                   if (ub <= MaxByte) type = T_BYT1;
					//                   else if (ub <= MaxWord) type = T_BYT2;
					//                   else
					tag = new Tag(Tag.TAG_INT);
				}
			}
		} else {
			if(Scode.accept(Scode.S_FIXREP)) {
				Util.IERR("Check dette");// inputInstr; // ????
				fixrep = Scode.inNumber();
			}
		}
//		return new Type(tag, fixrep);
		return new Type(tag);
	}

	public static void dumpBytes(int n) {
		try {
			IO.println("Scode.dumpBytes around byte " + SBUF_nxt);
			int lim = n + n;
			for(int i=0;i<lim;i++) {
				int x = SBUF_nxt -n + i;
				int val = SBUF[x] & 0xff;
				IO.println("Byte["+x+"] = " + val + ':' + Scode.of(val));
			}
		} catch(Throwable t) {}
		Thread.dumpStack();
	}
	
	public static void expect(Scode instr) {
		if(nextByte() == instr) {
			Scode.inputInstr();
		} else Util.IERR("Missing " + instr + " - Got " + nextByte());
	}
	
	public static void checkEqual(Scode instr) {
		if(curinstr != instr)
			Util.IERR("Missing " + instr + " - Got " + curinstr);
	}
	
	public static boolean accept(Scode instr) {
		if(nextByte() == instr) {
			Scode.inputInstr();
			return true;
		}
		return false;
	}
	
	public static String getString() {
		StringBuilder sb = new StringBuilder();
		int n = getByte();
		for(int i=0;i<n;i++) sb.append((char)getByte());
//		IO.println("Scode.getString: n="+n+", \""+sb+'"');
//		Thread.dumpStack();
		return sb.toString();
	}
	
	public static String inString() {
		String s = getString();
		if(Option.SCODE_INPUT_TRACE) {
			traceBuff.append(" \"").append(s).append('"');
		}
		return s;
	}
	
	public static String inExtr(char c, String segID) {
		String s = inString();
		return "" + c + '@' + s + '_' + segID;
	}
	
	public static String inLongString() {
		StringBuilder sb = new StringBuilder();
		int n = get2Bytes();
		for(int i=0;i<n;i++) sb.append((char)getByte());
		String s = sb.toString();
		if(Option.SCODE_INPUT_TRACE) {
			traceBuff.append(" \"").append(s).append('"');
		}
		return s;
	}

//	public static String InMsymb() { //; export infix(WORD) n;
////	begin n = DefModl(InSymbString);
////	   if InputTrace <> 0 then edsymb(traceBuff,n); ITRC("InMsymb") endif;
//	}
	
	public static int inNumber() {
		int n = get2Bytes();
		if(Option.SCODE_INPUT_TRACE) {
			traceBuff.append(" ").append(n);
		}
		return n;
	}	
	
//	Visible Macro InByte(1);
//	begin %1 = SBUF.byt(SBUF.nxt); SBUF.nxt = SBUF.nxt+1;
//	      if SBUF.nxt >= sBufLen then InSbuffer endif;
//	endmacro;
//	 Visible Routine InputByte; export range(0:MaxByte) b;
//	 begin b = SBUF.byt(SBUF.nxt); SBUF.nxt = SBUF.nxt+1;
//	       if SBUF.nxt >= sBufLen then InSbuffer endif;
//	       if InputTrace<>0 then EdWrd(traceBuff,b); ITRC("Inbyte") endif;
//	 end;
	
	public static int inByte() {
		int n = getByte();
		if(Option.SCODE_INPUT_TRACE) {
			traceBuff.append(" ").append(n);
		}
		return n;
	}	

//	Visible Routine inint; export integer n;
//	begin infix(String) s;
//	      s.nchr:=SBUF.byt(SBUF.nxt); SBUF.nxt:=SBUF.nxt+1;
//	      if SBUF.nxt+s.nchr >= sBufLen then InSbuffer endif;
//	      s.chradr:=@SBUF.chr(SBUF.nxt); SBUF.nxt:=SBUF.nxt+s.nchr;
//	      n:=EnvGetInt(s);
//	      if status<>0 then ERROR("Integer constant is out of range") endif;
//	%+D   if InputTrace <> 0 then Ed(traceBuff,s); ITRC("Inint") endif;
//	end;
//
//	Visible Routine inreal; export real r;
//	begin infix(String) s;
//	      s.nchr:=SBUF.byt(SBUF.nxt); SBUF.nxt:=SBUF.nxt+1;
//	      if SBUF.nxt+s.nchr >= sBufLen then InSbuffer endif;
//	      s.chradr:=@SBUF.chr(SBUF.nxt); SBUF.nxt:=SBUF.nxt+s.nchr;
//	      r:=EnvGetReal(s) qua real;
//	      if status<>0 then ERROR("Real constant is out of range") endif;
//	%+D   if InputTrace <> 0 then Ed(traceBuff,s); ITRC("Inreal") endif;
//	end;
//
//	Visible Routine inlreal; export long real lr;
//	begin infix(String) s;
//	      s.nchr:=SBUF.byt(SBUF.nxt); SBUF.nxt:=SBUF.nxt+1;
//	      if SBUF.nxt+s.nchr >= sBufLen then InSbuffer endif;
//	      s.chradr:=@SBUF.chr(SBUF.nxt); SBUF.nxt:=SBUF.nxt+s.nchr;
//	      lr:=EnvGetReal(s);
//	      if status<>0 then ERROR("Long real const. is out of range") endif;
//	%+D   if InputTrace<>0 then Ed(traceBuff,s); ITRC("Inlreal") endif;
//	end;

}
