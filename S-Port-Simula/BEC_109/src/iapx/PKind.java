package iapx;

import java.io.IOException;

/*
 * Usage:
 * 
 * 	int i = NormalDefined.ordinal();
 * 
 * 	TestEnum normalDefined = TestEnum.of(3);

 */
public enum PKind {
// 	---------     P r o f i l e    K i n d    C o d e s      ---------

	P_NULL,
	P_ROUTINE,     // Normal local routine profile
	P_VISIBLE,     // Normal visible routine profile
	P_INTERFACE,   // Interface profile
	P_SYSTEM,      // System routine (not inline)
	P_KNOWN,       // Known routine (not inline)
	P_OS2,         // MS-OS2 routine (partially inline)
	P_XNX,         // UNIX/XENIX C-lib routine (partially inline)
	P_KNL,         // UNIX/XENIX Kernel routine (partially inline)
	P_EXTERNAL,    // External <unknown> routine
	P_SIMULETTA,   // External SIMULETTA routine
	P_ASM,         // External ASSEMBLY routine
	P_C,           // External C routine
	P_FTN,         // External FORTRAN routine
	P_PASCAL,      // External PASCAL routine

	P_GTOUTM,      // Sysroutine("GTOUTM")
	P_MOVEIN,      // Sysroutine("MOVEIN")

	P_RSQROO,      // Sysroutine ("RSQROO")
	P_SQROOT,      // Sysroutine("SQROOT")
	P_RLOGAR,      // Sysroutine ("RLOGAR")
	P_LOGARI,      // Sysroutine("LOGARI")
	P_REXPON,      // Sysroutine ("REXPON")
	P_EXPONE,      // Sysroutine("EXPONE")
	P_RSINUS,      // Sysroutine("RSINUS")
	P_SINUSR,      // Sysroutine("SINUSR")
	P_RARTAN,      // Sysroutine("RARTAN")
	P_ARCTAN,      // Sysroutine("ARCTAN")

	P_RLOG10,      // Known("RLOG10")
	P_DLOG10,      // Known("DLOG10")
	P_RCOSIN,      // Known("RCOSIN")
	P_COSINU,      // Known("COSINU")
	P_RTANGN,      // Known("RTANGN")
	P_TANGEN,      // Known("TANGEN")
	P_RARCOS,      // Known("RARCOS")
	P_ARCCOS,      // Known("ARCCOS")
	P_RARSIN,      // Known("RARSIN")
	P_ARCSIN,      // Known("ARCSIN")

	P_ERRNON,      // Known("ERRNON")
	P_ERRQUA,      // Known("ERRQUA")
	P_ERRSWT,      // Known("ERRSWT")
	P_ERROR,       // Known("ERROR")

	P_CBLNK,       // Known("CBLNK")
	P_CMOVE,       // Known("CMOVE")
	P_STRIP,       // Known("STRIP")
	P_TXTREL,      // Known("TXTREL")
	P_TRFREL,      // Known("TRFREL")

	P_AR1IND,      // Known("AR1IND")
	P_AR2IND,      // Known("AR2IND")
	P_ARGIND,      // Known("ARGIND")

	P_IABS,        // Known("IABS")
	P_RABS,        // Known("RABS")
	P_DABS,        // Known("DABS")
	P_RSIGN,       // Known("RSIGN")
	P_DSIGN,       // Known("DSIGN")
	P_MODULO,      // Known("MODULO")
	P_RENTI,       // Known("RENTI")
	P_DENTI,       // Known("DENTI")
	P_DIGIT,       // Known("DIGIT")
	P_LETTER,      // Known("LETTER")

	P_RIPOWR,      // Known("RIPOWR")
	P_RRPOWR,      // Known("RRPOWR")
	P_RDPOWR,      // Known("RDPOWR")
	P_DIPOWR,      // Known("DIPOWR")
	P_DRPOWR,      // Known("DRPOWR")
	P_DDPOWR,      // Known("DDPOWR")

	P_DOS_CREF,    // Sysroutine("M?CREF")
	P_DOS_OPEN,    // Sysroutine("M?OPEN")
	P_DOS_CLOSE,   // Sysroutine("M?CLOSE")
	P_DOS_READ,    // Sysroutine("M?READ")
	P_DOS_WRITE,   // Sysroutine("M?WRITE")
	P_DOS_DELF,    // Sysroutine("M?DELF")
	P_DOS_FPTR,    // Sysroutine("M?FPTR")
	P_DOS_CDIR,    // Sysroutine("M?CDIR")
	P_DOS_ALOC,    // Sysroutine("M?ALOC")
	P_DOS_TERM,    // Sysroutine("M?TERM")
	P_DOS_TIME,    // Sysroutine("M?TIME")
	P_DOS_DATE,    // Sysroutine("M?DATE")
	P_DOS_VERS,    // Sysroutine("M?VERS")
	P_DOS_EXEC,    // Sysroutine("M?EXEC")
	P_DOS_IOCTL,   // Sysroutine("M?IOCTL")
	P_DOS_LOCK,    // Sysroutine("M?LOCK")
	P_DOS_GDRV,    // Sysroutine("M?GDRV")
	P_DOS_GDIR,    // Sysroutine("M?GDIR")

	P_APX_SCMPEQ,  // Sysroutine("S?SCMPEQ")
	P_APX_SMOVEI,  // Sysroutine("S?MOVEI")
	P_APX_SMOVED,  // Sysroutine("S?MOVED")
	P_APX_SSKIP,   // Sysroutine("S?SKIP")
	P_APX_STRIP,   // Sysroutine("S?TRIP")
	P_APX_SFINDI,  // Sysroutine("S?FINDI")
	P_APX_SFINDD,  // Sysroutine("S?FINDD")
	P_APX_SFILL,   // Sysroutine("S?FILL")

	P_APX_BOBY,    // Sysroutine("S?BOBY")
	P_APX_BYBO,    // Sysroutine("S?BYBO")
	P_APX_SZ2W,    // Sysroutine("S?SZ2W")
	P_APX_W2SZ,    // Sysroutine("S?W2SZ")
	P_APX_RF2N,   // Sysroutine("S?RF2N")
	P_APX_N2RF,   // Sysroutine("S?N2RF")
	P_APX_BNOT,   // Sysroutine("S?BNOT")
	P_APX_BAND,   // Sysroutine("S?BAND")
	P_APX_BOR,    // Sysroutine("S?BOR")
	P_APX_BXOR,   // Sysroutine("S?BXOR")
	P_APX_WNOT,   // Sysroutine("S?WNOT")
	P_APX_WAND,   // Sysroutine("S?WAND")
	P_APX_WOR,    // Sysroutine("S?WOR")
	P_APX_WXOR,   // Sysroutine("S?WXOR")
	P_APX_BSHL,   // Sysroutine("S?BSHL")
	P_APX_WSHL,   // Sysroutine("S?WSHL")
	P_APX_BSHR,   // Sysroutine("S?BSHR")
	P_APX_WSHR,   // Sysroutine("S?WSHR")

	P_DOS_SDMODE, // Sysroutine("M?SVDM")
	P_DOS_UPDPOS, // Sysroutine("M?UPOS") 
	P_DOS_CURSOR, // Sysroutine("M?CURS") 
	P_DOS_SDPAGE, // Sysroutine("M?SDPG") 
	P_DOS_SROLUP, // Sysroutine("M?SRUP") 
	P_DOS_SROLDW, // Sysroutine("M?SRDW") 
	P_DOS_GETCEL, // Sysroutine("M?GETC") 
	P_DOS_PUTCHR, // Sysroutine("M?PUTC") 
	P_DOS_GDMODE, // Sysroutine("M?GVDM") 
	P_DOS_SETPAL, // Sysroutine("M?SPAL") 

	P_DOS_RDCHK,  // Sysroutine("M?RCHK") 
	P_DOS_KEYIN;  // Sysroutine("M?KEYI") 

	//	 P_max=125;

	public static PKind getKnownKind(String s) {
		//--- Search for inline index ---
		     if(s.equals("RLOG10")) return PKind.P_RLOG10;
		else if(s.equals("DLOG10")) return PKind.P_DLOG10;
		else if(s.equals("RCOSIN")) return PKind.P_RCOSIN;
		else if(s.equals("COSINU")) return PKind.P_COSINU;
		else if(s.equals("RTANGN")) return PKind.P_RTANGN;
		else if(s.equals("TANGEN")) return PKind.P_TANGEN;
		else if(s.equals("RARCOS")) return PKind.P_RARCOS;
		else if(s.equals("ARCCOS")) return PKind.P_ARCCOS;
		else if(s.equals("RARSIN")) return PKind.P_RARSIN;
		else if(s.equals("ARCSIN")) return PKind.P_ARCSIN;
		else if(s.equals("ERRNON")) return PKind.P_ERRNON;
		else if(s.equals("ERRQUA")) return PKind.P_ERRQUA;
		else if(s.equals("ERRSWT")) return PKind.P_ERRSWT;
		else if(s.equals("ERROR") ) return PKind.P_ERROR;
		else if(s.equals("CBLNK") ) return PKind.P_CBLNK;
		else if(s.equals("CMOVE") ) return PKind.P_CMOVE;
		else if(s.equals("STRIP") ) return PKind.P_STRIP;
		else if(s.equals("TXTREL")) return PKind.P_TXTREL;
		else if(s.equals("TRFREL")) return PKind.P_TRFREL;
		else if(s.equals("AR1IND")) return PKind.P_AR1IND;
		else if(s.equals("AR2IND")) return PKind.P_AR2IND;
		else if(s.equals("ARGIND")) return PKind.P_ARGIND;
		else if(s.equals("IABS")  ) return PKind.P_IABS;
		else if(s.equals("RABS")  ) return PKind.P_RABS;
		else if(s.equals("DABS")  ) return PKind.P_DABS;
		else if(s.equals("RSIGN") ) return PKind.P_RSIGN;
		else if(s.equals("DSIGN") ) return PKind.P_DSIGN;
		else if(s.equals("MODULO")) return PKind.P_MODULO;
		else if(s.equals("RENTI") ) return PKind.P_RENTI;
		else if(s.equals("DENTI") ) return PKind.P_DENTI;
		else if(s.equals("DIGIT") ) return PKind.P_DIGIT;
		else if(s.equals("LETTER")) return PKind.P_LETTER;
		else if(s.equals("RIPOWR")) return PKind.P_RIPOWR;
		else if(s.equals("RRPOWR")) return PKind.P_RRPOWR;
		else if(s.equals("RDPOWR")) return PKind.P_RDPOWR;
		else if(s.equals("DIPOWR")) return PKind.P_DIPOWR;
		else if(s.equals("DRPOWR")) return PKind.P_DRPOWR;
		else if(s.equals("DDPOWR")) return PKind.P_DDPOWR;
		return PKind.P_KNOWN;
	}


	public static PKind getSystemKind(String s) {
	     	 if(s.equals("GTOUTM")) return PKind.P_GTOUTM;
		else if(s.equals("MOVEIN")) return PKind.P_MOVEIN;
		else if(s.equals("RSQROO")) return PKind.P_RSQROO;
		else if(s.equals("SQROOT")) return PKind.P_SQROOT;
		else if(s.equals("RLOGAR")) return PKind.P_RLOGAR;
		else if(s.equals("LOGARI")) return PKind.P_LOGARI;
		else if(s.equals("REXPON")) return PKind.P_REXPON;
		else if(s.equals("EXPONE")) return PKind.P_EXPONE;
		else if(s.equals("RSINUS")) return PKind.P_RSINUS;
		else if(s.equals("SINUSR")) return PKind.P_SINUSR;
		else if(s.equals("RARTAN")) return PKind.P_RARTAN;
		else if(s.equals("ARCTAN")) return PKind.P_ARCTAN;
		else if(s.equals("M?CREF")) return PKind.P_DOS_CREF;
		else if(s.equals("M?OPEN")) return PKind.P_DOS_OPEN;
		else if(s.equals("M?CLOSE")) return PKind.P_DOS_CLOSE;
		else if(s.equals("M?READ")) return PKind.P_DOS_READ;
		else if(s.equals("M?WRITE")) return PKind.P_DOS_WRITE;
		else if(s.equals("M?DELF")) return PKind.P_DOS_DELF;
		else if(s.equals("M?FPTR")) return PKind.P_DOS_FPTR;
		else if(s.equals("M?CDIR")) return PKind.P_DOS_CDIR;
		else if(s.equals("M?ALOC")) return PKind.P_DOS_ALOC;
		else if(s.equals("M?TERM")) return PKind.P_DOS_TERM;
		else if(s.equals("M?TIME")) return PKind.P_DOS_TIME;
		else if(s.equals("M?DATE")) return PKind.P_DOS_DATE;
		else if(s.equals("M?VERS")) return PKind.P_DOS_VERS;
		else if(s.equals("M?EXEC")) return PKind.P_DOS_EXEC;
		else if(s.equals("M?IOCTL")) return PKind.P_DOS_IOCTL;
		else if(s.equals("M?LOCK")) return PKind.P_DOS_LOCK;
		else if(s.equals("M?GDRV")) return PKind.P_DOS_GDRV;
		else if(s.equals("M?GDIR")) return PKind.P_DOS_GDIR;
		else if(s.equals("S?SCMPEQ")) return PKind.P_APX_SCMPEQ;
		else if(s.equals("S?SMOVEI")) return PKind.P_APX_SMOVEI;
		else if(s.equals("S?SMOVED")) return PKind.P_APX_SMOVED;
		else if(s.equals("S?SSKIP")) return PKind.P_APX_SSKIP;
		else if(s.equals("S?STRIP")) return PKind.P_APX_STRIP;
		else if(s.equals("S?SFINDI")) return PKind.P_APX_SFINDI;
		else if(s.equals("S?SFINDD")) return PKind.P_APX_SFINDD;
		else if(s.equals("S?SFILL")) return PKind.P_APX_SFILL;
		else if(s.equals("S?BOBY")) return PKind.P_APX_BOBY;
		else if(s.equals("S?BYBO")) return PKind.P_APX_BYBO;
		else if(s.equals("S?SZ2W")) return PKind.P_APX_SZ2W;
		else if(s.equals("S?W2SZ")) return PKind.P_APX_W2SZ;
		else if(s.equals("S?RF2N")) return PKind.P_APX_RF2N;
		else if(s.equals("S?N2RF")) return PKind.P_APX_N2RF;
		else if(s.equals("S?BNOT")) return PKind.P_APX_BNOT;
		else if(s.equals("S?BAND")) return PKind.P_APX_BAND;
		else if(s.equals("S?BOR") ) return PKind.P_APX_BOR;
		else if(s.equals("S?BXOR")) return PKind.P_APX_BXOR;
		else if(s.equals("S?WNOT")) return PKind.P_APX_WNOT;
		else if(s.equals("S?WAND")) return PKind.P_APX_WAND;
		else if(s.equals("S?WOR") ) return PKind.P_APX_WOR;
		else if(s.equals("S?WXOR")) return PKind.P_APX_WXOR;
		else if(s.equals("S?BSHL")) return PKind.P_APX_BSHL;
		else if(s.equals("S?WSHL")) return PKind.P_APX_WSHL;
		else if(s.equals("S?BSHR")) return PKind.P_APX_BSHR;
		else if(s.equals("S?WSHR")) return PKind.P_APX_WSHR;
		else if(s.equals("M?SVDM")) return PKind.P_DOS_SDMODE;
		else if(s.equals("M?UPOS")) return PKind.P_DOS_UPDPOS;
		else if(s.equals("M?CURS")) return PKind.P_DOS_CURSOR;
		else if(s.equals("M?SDPG")) return PKind.P_DOS_SDPAGE;
		else if(s.equals("M?SRUP")) return PKind.P_DOS_SROLUP;
		else if(s.equals("M?SRDW")) return PKind.P_DOS_SROLDW;
		else if(s.equals("M?GETC")) return PKind.P_DOS_GETCEL;
		else if(s.equals("M?PUTC")) return PKind.P_DOS_PUTCHR;
		else if(s.equals("M?GVDM")) return PKind.P_DOS_GDMODE;
		else if(s.equals("M?SPAL")) return PKind.P_DOS_SETPAL;
		else if(s.equals("M?RCHK")) return PKind.P_DOS_RDCHK;
		else if(s.equals("M?KEYI")) return PKind.P_DOS_KEYIN;
//		Util.IERR("" + s);
		return PKind.P_SYSTEM;
	}
	



	private static PKind[] values = PKind.values();
	public static PKind of(int ordinale) {
		return values[ordinale];
	}

//	public static void write(PKind kind, AttributeOutputStream oupt) throws IOException {
//		oupt.writeShort(kind.ordinal());
//	}
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(this.ordinal());
	}

	public static PKind read(AttributeInputStream inpt) throws IOException {
		return PKind.of(inpt.readShort());
	}

}
