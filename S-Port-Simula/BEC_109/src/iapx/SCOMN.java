package iapx;

import iapx.CTStack.StackItem;
import iapx.qPkt.Qpkt;

public class SCOMN {
//	 Global SCOMN("iAPX");
//	 begin
//	       -----------------------------------------------------------------
//	       ---  COPYRIGHT 1988 by                                        ---
//	       ---  Simula a.s.                                              ---
//	       ---  Oslo, Norway                                             ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---              P O R T A B L E     S I M U L A              ---
//	       ---                                                           ---
//	       ---                   F O R    I B M    P C                   ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---           S   -   C   O   M   P   I   L   E   R           ---
//	       ---                                                           ---
//	       ---            G l o b a l   D e f i n i t i o n s            ---
//	       ---                                                           ---
//	       ---  Selection Switches:                                      ---
//	       ---                                                           ---
//	       ---     A - Includes Assembly Output                          ---
//	       ---     C - Includes Consistency Checks                       ---
//	       ---     D - Includes Tracing Dumps                            ---
//	       ---     S - Includes System Generation                        ---
//	       ---     E - Extended mode // 32-bit 386                       ---
//	       ---     F - Optionally Produce COFF output                    ---
//	       ---     V - New version. (+V:New, -V:Previous)                ---
//	       -----------------------------------------------------------------

	public static final int MxpSymb=64;  // I.e. 16384 Symbols
	public static final int MxpSegm=16;  // I.e.  4096 Segments Names
	public static final int MxpPubl=32;  // I.e.  8192 Public Definitions
	public static final int MxpExtr=32;  // I.e.  8192 External References
	public static final int MxpModl=8;   // I.e.  2048 Module Names
	public static final int MxpTag=64;   // I.e. 16384 Tags
	public static final int MxpFix=64;   // I.e. 16384 Fixups
	public static final int MxpSdest=32; // I.e.  8192 SDEST per S-Code Switch
	public static final int MxpXtag=64;  // I.e. 16384 Tags in Attribute file
	public static final int MxpXtyp=1;   // I.e.   256 Types in Attribute file
	public static final int MxpXsmb=64;  // I.e. 16384 Symbols in Attribute file

	 public static final int MaxPnt=63;        // Max number of 'pointers' per SAVE-Object
	 public static final int MaxHash=255;     // Max no.of Hash keys-1  DO NOT CHANGE IT !!!
	 public static final int MaxPar=63;        // Max number of routine-parameters
	 public static final int MaxParbyte=200;   // Max byte size of routine-call-stack

	 public static final int MinTag=32;        // First user tag
	 public static final int MaxKey=255;       // Max number of file keys
	 public static final int MaxType=63;       // Max number of data types
	 public static final int MaxByte=255;      // Max value of 8-bit byte  (2**8-1)
	 public static final int MaxWord=65535;    // Max value of 16-bit word (2**16-1)
	 public static final int MaxSint=32767;    // Max value of SINT        (2**15-1)
	 public static final int MaxSdest=8192;    // Max number of dest-in-switch
	 public static final int MaxString=32000;  // Max number of chars in a string
	 public static final int MaxLine=32000;    // Max number of source lines
	 public static final int MaxPool=63;    // Max number of pools in trace version

	 public static final int BufLng=1024;     // Max body size of :OBJ file records

	 public static final int  TAG_BOOL    = 1,  TAG_CHAR    = 2,  TAG_INT     = 3;
	 public static final int  TAG_SINT    = 4,  TAG_REAL    = 5,  TAG_LREAL   = 6;
	 public static final int  TAG_AADDR   = 7,  TAG_OADDR   = 8,  TAG_GADDR   = 9;
	 public static final int  TAG_PADDR   = 10, TAG_RADDR   = 11, TAG_SIZE    = 12;

	 public static final int T_VOID=0,T_WRD4=1,T_WRD2=2,T_BYT2=3,T_BYT1=4;
	 public static final int T_REAL=5,T_LREAL=6,T_TREAL=7,T_BOOL=8,T_CHAR=9;
	 public static final int T_SIZE=10,T_OADDR=11,T_AADDR=12,T_GADDR=13,T_PADDR=14,T_RADDR=15;
	 public static final int T_NPADR=16,T_NOINF=17;
	 public static final int T_max=17;  // Max value of predefined type

	 public static final int FMF_REAL  = 0; // 00 0B
	 public static final int FMF_LREAL = 4; // 10 0B
	 public static final int FMF_INT   = 2; // 01 0B
	 public static final int FMF_SINT  = 6; // 11 0B
	 public static final int FMF_TEMP  = 3; // 01 1B  // Special Case

	 public static final int O_SSEG=1,O_IDATA=2,O_LDATA=3;
	 public static final int O_AEXT=4,O_ASEG=5,O_AFIX=6;
	 public static final int O_LINE=7,O_TRC=8,O_END=9;
	 public static final int O_max=9;

	 public static final int aDGRP=0,aDATA=1,aCODE=2,aLINE=3;

	 public static final int iAPX86=1,iAPX186=2,iAPX286=3,iAPX386=4;
	 public static final int NoNPX=0,iAPX87=1,iAPX287=2,iAPX387=3,WTLx167=4;

	 public static final int oMSDOS     = 0; // DOS    without numeric coprocessor
	 public static final int oMSDOS87   = 1; // DOS       with 8087 or higher
	 public static final int oMSOS2     = 2; // OS/2      with 8087 or higher
	 public static final int oXENIX286  = 3; // XENIX/286 with 80287 (or emulator) or higher
	 public static final int oXENIX386  = 4; // XENIX/386 with 80287 (or emulator) or higher
	 public static final int oXENIX386W = 5; // XENIX/386 with Weitek 1167 or higher
	 public static final int oUNIX386   = 6; // UNIX/386  with 80287 (or emulator) or higher
	 public static final int oUNIX386W  = 7; // UNIX/386  with Weitek 1167 or higher
	 public static final int oSINTRAN   = 8; // SINTRAN/ND500

	 public static final int cNONE=0;        // No C-Binding defined
	 public static final int cMS=1;          // Simula used together with MicroSoft C
	 public static final int cTURBO=2;       // Simula used together with TURBO C

	 public static final int xGOTO=0,xCALL=1;

	 // ------ Symbol Class Codes ------
	 public static final int sSYMB=0,sEXTR=1,sPUBL=2,sSEGM=3,sMODL=4;
	 public static final int sMAX=4;

	 //------ SIB-register packing in Operand ------
	 //
	 //     ireg=iESP                 ==>  no index register
	 //     breg=ireg & breg <> bESP  ==>  no base register
	 //     It is impossible to specify same base and index register.
	
	 public static final int iEAX=0, bEAX=0;        //   0=00 000 000   0=00 000 000
	 public static final int iECX=8, bECX=1;        //   8=00 001 000   1=00 000 001
	 public static final int iEDX=16,bEDX=2;        //  16=00 010 000   2=00 000 010
	 public static final int iEBX=24,bEBX=3;        //  24=00 011 000   3=00 000 011
	 public static final int iESP=32,bESP=4;        //  32=00 100 000   4=00 000 100
	 public static final int iEBP=40,bEBP=5;        //  40=00 101 000   5=00 000 101
	 public static final int iESI=48,bESI=6;        //  48=00 110 000   6=00 000 110
	 public static final int iEDI=56,bEDI=7;        //  56=00 111 000   7=00 000 111
	 public static final int ssMASK=192;            // 192=11 000 000
	 public static final int IndxREG=56,BaseREG=7;  //  56=00 111 000   7=00 000 111
	 public static final int NoIBREG=228;           // 228=11 100 100   IS RULED OUT.
	 public static final int NoIREG=32;             //  32=00 100 000   IS RULED OUT.

	  //    ------   S  -  I  N  S  T  R  U  C  T  I  O  N  S   ------

	  public static final int S_LSHIFTL=2;   //--- Extension to S-Code:  Left shift logical
	  public static final int S_LSHIFTA=5;   //--- Extension to S-Code:  Left shift arithmetical
	  public static final int S_RSHIFTL=66;  //--- Extension to S-Code:  Right shift logical
	  public static final int S_RSHIFTA=129; //--- Extension to S-Code:  Right shift arithmetical

	  public static final int S_RECORD=1, S_PREFIX=3, S_ATTR=4, S_REP=6, S_ALT=7,
	         S_FIXREP=8, S_ENDRECORD=9, S_C_RECORD=10, S_TEXT=11,
	         S_C_CHAR=12, S_C_INT=13, S_C_SIZE=14, S_C_REAL=15,
	         S_C_LREAL=16, S_C_AADDR=17, S_C_OADDR=18, S_C_GADDR=19,
	         S_C_PADDR=20, S_C_DOT=21, S_C_RADDR=22, S_NOBODY=23,
	         S_ANONE=24, S_ONONE=25, S_GNONE=26, S_NOWHERE=27, S_TRUE=28,
	         S_FALSE=29, S_PROFILE=30, S_KNOWN=31, S_SYSTEM=32,
	         S_EXTERNAL=33, S_IMPORT=34, S_EXPORT=35, S_EXIT=36,
	         S_ENDPROFILE=37, S_ROUTINESPEC=38, S_ROUTINE=39, S_LOCAL=40,
	         S_ENDROUTINE=41, S_MODULE=42, S_EXISTING=43, S_TAG=44,
	         S_BODY=45, S_ENDMODULE=46, S_LABELSPEC=47, S_LABEL=48,
	         S_RANGE=49, S_GLOBAL=50, S_INIT=51, S_CONSTSPEC=52,
	         S_CONST=53, S_DELETE=54, S_FDEST=55, S_BDEST=56,
	         S_SAVE=57, S_RESTORE=58, S_BSEG=59, S_ESEG=60,
	         S_SKIPIF=61, S_ENDSKIP=62, S_IF=63, S_ELSE=64,
	         S_ENDIF=65, S_PRECALL=67, S_ASSPAR=68,
	         S_ASSREP=69, S_CALL=70, S_FETCH=71, S_REFER=72,
	         S_DEREF=73, S_SELECT=74, S_REMOTE=75, S_LOCATE=76,
	         S_INDEX=77, S_INCO=78, S_DECO=79, S_PUSH=80, S_PUSHC=81,
	         S_PUSHLEN=82, S_DUP=83, S_POP=84, S_EMPTY=85,
	         S_SETOBJ=86, S_GETOBJ=87, S_ACCESS=88, S_FJUMP=89,
	         S_BJUMP=90, S_FJUMPIF=91, S_BJUMPIF=92, S_SWITCH=93,
	         S_GOTO=94, S_T_INITO=95, S_T_GETO=96, S_T_SETO=97,
	         S_ADD=98, S_SUB=99, S_MULT=100, S_DIV=101, S_REM=102,
	         S_NEG=103, S_AND=104, S_OR =105, S_XOR=106, S_IMP=107,
	         S_EQV=108, S_NOT=109, S_DIST=110, S_ASSIGN=111,
	         S_UPDATE=112, S_CONVERT=113, S_SYSINSERT=114, S_INSERT=115,
	         S_ZEROAREA=116, S_INITAREA=117, S_COMPARE=118, S_LT=119,
	         S_LE=120, S_EQ=121, S_GE=122, S_GT=123, S_NE=124,
	         S_EVAL=125, S_INFO=126, S_LINE=127, S_SETSWITCH=128,
	         S_PROGRAM=130, S_MAIN=131, S_ENDPROGRAM=132,
	         S_DSIZE=133, S_SDEST=134, S_RUPDATE=135, S_ASSCALL=136,
	         S_CALL_TOS=137, S_DINITAREA=138, S_NOSIZE=139, S_POPALL=140,
	         S_REPCALL=141, S_INTERFACE=142, S_MACRO=143, S_MARK=144,
	         S_MPAR=145, S_ENDMACRO=146, S_MCALL=147, S_PUSHV=148,
	         S_SELECTV=149, S_REMOTEV=150, S_INDEXV=151, S_ACCESSV=152,
	         S_DECL=153, S_STMT=154;

	       public static final int S_max=154;  // Max value of S-Instruction codes

	 //---------     O b j e c t   K i n d   C o d e s      ---------

	 public static final int K_Qfrm1=1,K_Qfrm2=2,K_Qfrm2b=3,K_Qfrm3=4;
	 public static final int K_Qfrm4=5,K_Qfrm4b=6,K_Qfrm4c=7,K_Qfrm5=8,K_Qfrm6=9;
	 //--- Descriptors ---
	 public static final int K_RecordDescr=10,K_TypeRecord=11,K_Attribute=12;
	 public static final int K_Parameter=13,K_Export=14,K_LocalVar=15;
	 public static final int K_GlobalVar=16,K_ExternVar=17;
	 public static final int K_ProfileDescr=18,K_IntRoutine=19,K_ExtRoutine=20;
	 public static final int K_IntLabel=21,K_ExtLabel=22,K_SwitchDescr=23;
	 //--- Stack Items ---
	 public static final int K_ProfileItem=24,K_Address=25,K_Temp=26,K_Coonst=27;
	 public static final int K_Result=28;
	 //--- Arrays etc. ---
	 public static final int K_RefBlock=29,K_WordBlock=30,K_AddrBlock=31;
	 //--- Others ---
	 public static final int K_BSEG=32;

	 public static final int K_Max=33;  // Max value of object kind codes  + 1

	 //---------     P r o f i l e    K i n d    C o d e s      ---------

	 public static final int P_ROUTINE=0;      // Normal local routine profile
	 public static final int P_VISIBLE=1;      // Normal visible routine profile
	 public static final int P_INTERFACE=2;    // Interface profile
	 public static final int P_SYSTEM=3;       // System routine (not inline)
	 public static final int P_KNOWN=4;        // Known routine (not inline)
	 public static final int P_OS2=5;          // MS-OS2 routine (partially inline)
	 public static final int P_XNX=6;          // UNIX/XENIX C-lib routine (partially inline)
	 public static final int P_KNL=7;          // UNIX/XENIX Kernel routine (partially inline)
	 public static final int P_EXTERNAL=8;     // External <unknown> routine
	 public static final int P_SIMULETTA=9;    // External SIMULETTA routine
	 public static final int P_ASM=10;         // External ASSEMBLY routine
	 public static final int P_C=11;           // External C routine
	 public static final int P_FTN=12;         // External FORTRAN routine
	 public static final int P_PASCAL=13;      // External PASCAL routine
	 //     14 .. 19         // Reserved

	 public static final int P_GTOUTM=20;      // Sysroutine("GTOUTM")
	 public static final int P_MOVEIN=21;      // Sysroutine("MOVEIN")

	 public static final int P_RSQROO=22;      // Sysroutine ("RSQROO")
	 public static final int P_SQROOT=23;      // Sysroutine("SQROOT")
	 public static final int P_RLOGAR=24;      // Sysroutine ("RLOGAR")
	 public static final int P_LOGARI=25;      // Sysroutine("LOGARI")
	 public static final int P_REXPON=26;      // Sysroutine ("REXPON")
	 public static final int P_EXPONE=27;      // Sysroutine("EXPONE")
	 public static final int P_RSINUS=28;      // Sysroutine("RSINUS")
	 public static final int P_SINUSR=29;      // Sysroutine("SINUSR")
	 public static final int P_RARTAN=30;      // Sysroutine("RARTAN")
	 public static final int P_ARCTAN=31;      // Sysroutine("ARCTAN")

	 public static final int P_RLOG10=32;      // Known("RLOG10")
	 public static final int P_DLOG10=33;      // Known("DLOG10")
	 public static final int P_RCOSIN=34;      // Known("RCOSIN")
	 public static final int P_COSINU=35;      // Known("COSINU")
	 public static final int P_RTANGN=36;      // Known("RTANGN")
	 public static final int P_TANGEN=37;      // Known("TANGEN")
	 public static final int P_RARCOS=38;      // Known("RARCOS")
	 public static final int P_ARCCOS=39;      // Known("ARCCOS")
	 public static final int P_RARSIN=40;      // Known("RARSIN")
	 public static final int P_ARCSIN=41;      // Known("ARCSIN")

	 public static final int P_ERRNON=42;      // Known("ERRNON")
	 public static final int P_ERRQUA=43;      // Known("ERRQUA")
	 public static final int P_ERRSWT=44;      // Known("ERRSWT")
	 public static final int P_ERROR=45;       // Known("ERROR")

	 public static final int P_CBLNK=46;      // Known("CBLNK")
	 public static final int P_CMOVE=47;      // Known("CMOVE")
	 public static final int P_STRIP=48;      // Known("STRIP")
	 public static final int P_TXTREL=49;      // Known("TXTREL")
	 public static final int P_TRFREL=50;     // Known("TRFREL")

	 public static final int P_AR1IND=51;     // Known("AR1IND")
	 public static final int P_AR2IND=52;     // Known("AR2IND")
	 public static final int P_ARGIND=53;     // Known("ARGIND")

	 public static final int P_IABS=54;       // Known("IABS")
	 public static final int P_RABS=55;       // Known("RABS")
	 public static final int P_DABS=56;       // Known("DABS")
	 public static final int P_RSIGN=57;      // Known("RSIGN")
	 public static final int P_DSIGN=58;      // Known("DSIGN")
	 public static final int P_MODULO=59;     // Known("MODULO")
	 public static final int P_RENTI=60;      // Known("RENTI")
	 public static final int P_DENTI=61;      // Known("DENTI")
	 public static final int P_DIGIT=62;      // Known("DIGIT")
	 public static final int P_LETTER=63;     // Known("LETTER")

	 public static final int P_RIPOWR=64;     // Known("RIPOWR")
	 public static final int P_RRPOWR=65;     // Known("RRPOWR")
	 public static final int P_RDPOWR=66;     // Known("RDPOWR")
	 public static final int P_DIPOWR=67;     // Known("DIPOWR")
	 public static final int P_DRPOWR=68;     // Known("DRPOWR")
	 public static final int P_DDPOWR=69;     // Known("DDPOWR")

	 public static final int P_DOS_CREF=70;   // Sysroutine("M?CREF")
	 public static final int P_DOS_OPEN=71;   // Sysroutine("M?OPEN")
	 public static final int P_DOS_CLOSE=72;  // Sysroutine("M?CLOSE")
	 public static final int P_DOS_READ=73;   // Sysroutine("M?READ")
	 public static final int P_DOS_WRITE=74;  // Sysroutine("M?WRITE")
	 public static final int P_DOS_DELF=75;   // Sysroutine("M?DELF")
	 public static final int P_DOS_FPTR=76;   // Sysroutine("M?FPTR")
	 public static final int P_DOS_CDIR=77;   // Sysroutine("M?CDIR")
	 public static final int P_DOS_ALOC=78;   // Sysroutine("M?ALOC")
	 public static final int P_DOS_TERM=79;   // Sysroutine("M?TERM")
	 public static final int P_DOS_TIME=80;   // Sysroutine("M?TIME")
	 public static final int P_DOS_DATE=81;   // Sysroutine("M?DATE")
	 public static final int P_DOS_VERS=82;   // Sysroutine("M?VERS")
	 public static final int P_DOS_EXEC=83;   // Sysroutine("M?EXEC")
	 public static final int P_DOS_IOCTL=84;  // Sysroutine("M?IOCTL")
	 public static final int P_DOS_LOCK=85;   // Sysroutine("M?LOCK")
	 public static final int P_DOS_GDRV=86;   // Sysroutine("M?GDRV")
	 public static final int P_DOS_GDIR=87;   // Sysroutine("M?GDIR")

	 public static final int P_APX_SCMPEQ=88; // Sysroutine("S?SCMPEQ")
	 public static final int P_APX_SMOVEI=89; // Sysroutine("S?MOVEI")
	 public static final int P_APX_SMOVED=90; // Sysroutine("S?MOVED")
	 public static final int P_APX_SSKIP=91;  // Sysroutine("S?SKIP")
	 public static final int P_APX_STRIP=92;  // Sysroutine("S?TRIP")
	 public static final int P_APX_SFINDI=93; // Sysroutine("S?FINDI")
	 public static final int P_APX_SFINDD=94; // Sysroutine("S?FINDD")
	 public static final int P_APX_SFILL=95;  // Sysroutine("S?FILL")

	 public static final int P_APX_BOBY=96;   // Sysroutine("S?BOBY")
	 public static final int P_APX_BYBO=97;   // Sysroutine("S?BYBO")
	 public static final int P_APX_SZ2W=98;   // Sysroutine("S?SZ2W")
	 public static final int P_APX_W2SZ=99;   // Sysroutine("S?W2SZ")
	 public static final int P_APX_RF2N=100;  // Sysroutine("S?RF2N")
	 public static final int P_APX_N2RF=101;  // Sysroutine("S?N2RF")
	 public static final int P_APX_BNOT=102;  // Sysroutine("S?BNOT")
	 public static final int P_APX_BAND=103;  // Sysroutine("S?BAND")
	 public static final int P_APX_BOR=104;   // Sysroutine("S?BOR")
	 public static final int P_APX_BXOR=105;  // Sysroutine("S?BXOR")
	 public static final int P_APX_WNOT=106;  // Sysroutine("S?WNOT")
	 public static final int P_APX_WAND=107;  // Sysroutine("S?WAND")
	 public static final int P_APX_WOR=108;   // Sysroutine("S?WOR")
	 public static final int P_APX_WXOR=109;  // Sysroutine("S?WXOR")
	 public static final int P_APX_BSHL=110;  // Sysroutine("S?BSHL")
	 public static final int P_APX_WSHL=111;  // Sysroutine("S?WSHL")
	 public static final int P_APX_BSHR=112;  // Sysroutine("S?BSHR")
	 public static final int P_APX_WSHR=113;  // Sysroutine("S?WSHR")

	 public static final int P_DOS_SDMODE=114;// Sysroutine("M?SVDM")
	 public static final int P_DOS_UPDPOS=115;// Sysroutine("M?UPOS") 
	 public static final int P_DOS_CURSOR=116;// Sysroutine("M?CURS") 
	 public static final int P_DOS_SDPAGE=117;// Sysroutine("M?SDPG") 
	 public static final int P_DOS_SROLUP=118;// Sysroutine("M?SRUP") 
	 public static final int P_DOS_SROLDW=119;// Sysroutine("M?SRDW") 
	 public static final int P_DOS_GETCEL=120;// Sysroutine("M?GETC") 
	 public static final int P_DOS_PUTCHR=121;// Sysroutine("M?PUTC") 
	 public static final int P_DOS_GDMODE=122;// Sysroutine("M?GVDM") 
	 public static final int P_DOS_SETPAL=123;// Sysroutine("M?SPAL") 

	 public static final int P_DOS_RDCHK=124; // Sysroutine("M?RCHK") 
	 public static final int P_DOS_KEYIN=125; // Sysroutine("M?KEYI") 

	 public static final int P_max=125;
			 
			 
	 //%title ***  Routine Statistics  ***
	 //------  SBASE  ------
	 public static final int R_DefType=1,R_DICREF=2,R_DICSMB=3,R_DefSymb=4;
	 public static final int R_DefSegm=5,R_PutSegx=6,R_GetSegx=7,R_DefExtr=8;
	 public static final int R_PutExtern=9,R_DefPubl=10,R_PutPublic=11,R_NewPubl=12;
	 public static final int R_DefModl=13,R_PutModule=14,R_GetDefaultSreg=15;
	 public static final int R_OverrideSreg=16,R_MakeRegmap=17,R_SamePart=18;
	 public static final int R_RegDies=19,R_RegxAvailable=20,R_IntoDisplay=21,R_GetRec=22;
	 public static final int R_GetAtr=23,R_GetPrf=24,R_GetRut=25,R_GetLab=26;
	 public static final int R_NEWOBX=27,R_NEWOBJ=28,R_DELETE=29;
	 //------  COASM  ------
	 public static final int R_iCodeSize=30,R_ShrtJMP=31,R_EmitSOP=32;
	 public static final int R_EmitCall=33,R_EmitAddr=34,R_EncodeEA=35;
	 public static final int R_ModifySP=36,R_LoadCnst=37,R_EmitJMP=38,R_QtoI=39;
	 //------  MASSAGE  ------
	 public static final int R_Massage=40,R_PrevQinstr=41;
	 public static final int R_AppendQinstr=42,R_InsertQinstr=43,R_DeleteQinstr=44;
	 public static final int R_DeleteQPosibJ=45,R_MoveFdest=46,R_QinstrEqual=47;
	 public static final int R_OprEqual=48,R_ForwJMP=49,R_DefFDEST=50,R_DefBDEST=51;
	 public static final int R_RegsWrittenDies=52,R_RegLastused=53,R_RegLastWrite=54;
	 public static final int R_RegOneshot=55,R_StackModification=56,R_FindPush2=57;
	 public static final int R_FindPush=58,R_StackEqual=59,R_RegsReadUnmodified=60;
	 public static final int R_RegUnused=61,R_OperandregsModified=62,R_MemoryUse=63;
	 public static final int R_MemoryUnused=64,R_MemoryLastused=65,R_mOPR=66;
	 public static final int R_mPUSHR=67,R_mPUSHM=68,R_mPOPK=69,R_mPOPR=70;
	 public static final int R_mPOPR2=71,R_mPOPM=72,R_mLOADC=73,R_mLOADSC=74,R_mLOAD=75;
	 public static final int R_mLDS=76,R_mLES=77,R_mLOADA=78;
	 public static final int R_mMOV=79,R_mSTORE=80,R_mMONADR=81,R_QinstrBefore=82;
	 public static final int R_TryReverse=83,R_mDYADR=84,R_mDYADC=85,R_mTRIADR=86;
	 public static final int R_mTRIADM=87,R_mFDYAD=89,R_mFDYADrev=90;
	 public static final int R_mCondition=91,R_mJMP=92,R_mFDEST=93;
	 //------  CODER  ------
	 public static final int R_Push=94,R_Precede=95,R_Pop=96,R_TakeTOS=97,R_TakeRef=98;
	 public static final int R_CopyBSEG=99,R_AssertObjStacked=100;
	 public static final int R_AssertAtrStacked=101,R_PresaveOprRegs=102,R_GetTosAddr=103;
	 public static final int R_GetSosAddr=104,R_GetTosValueIn86R3=105;
	 public static final int R_GetTosValueIn86=106,R_GetTosAdjustedIn86=107;
	 public static final int R_GetTosAsBYT1=108,R_GetTosAsBYT2=109,R_GetTosAsBYT4=110;
	 public static final int R_GQfetch=111,R_GQdup=112,R_DupIn86=113,R_GQpop=114;
	 public static final int R_GetOprAddr=115,R_ArithType=116;
	 //------  PARSE  ------
	 public static final int R_SpecLab=117,R_DefLab=118,R_Viisible=119;
	 public static final int R_ProgramElement=120,R_Instruction=121,R_CallInstruction=122;
	 public static final int R_WordsOnStack=123,R_MoveOnStack=124,R_CallDefault=125;
	 public static final int R_PopExport=126,R_PushExport=127,R_PutPar=128,R_ParType=129;
	 public static final int R_ConvRepWRD2=130,R_ConvRepWRD4=131,R_IfConstruction=132;
	 public static final int R_SkipifConstruction=133,R_ProtectConstruction=134;
	 public static final int R_Max=135;

//
//	%title ******   R e g i s t e r    U s a g e   ******
//	------------   For  R e a l    U N I X    3 8 6   ------------
	 public static final int
	     qAL=0,qCL=1,qDL=2,qBL=3,qAH=4,qCH=5,qDH=6,qBH=7,
	     qAX=8,qCX=9,qDX=10,qBX=11, //  qSP=12,qBP=13,qSI=14,qDI=15,
	     qEAX=16,qECX=17,qEDX=18,qEBX=19,qESP=20,qEBP=21,qESI=22,qEDI=23,
	     qF=24,qM=25,
	     nregs=26;

	 public static final int qw_B=0,qw_W=8,qw_D=16;  // Instruksjons-"bredde"

	 public static final int
	                uAL=1,     // 0001H = 0000 0000 0000 0001B
	                uAH=2,     // 0002H = 0000 0000 0000 0010B
	         uAX=3, uEAX=3,    // 0003H = 0000 0000 0000 0011B
	                uCL=4,     // 0004H = 0000 0000 0000 0100B
	                uCH=8,     // 0008H = 0000 0000 0000 1000B
	         uCX=12,uECX=12,   // 000CH = 0000 0000 0000 1100B
	                uDL=16,    // 0010H = 0000 0000 0001 0000B
	                uDH=32,    // 0020H = 0000 0000 0010 0000B
	         uDX=48,uEDX=48,   // 0030H = 0000 0000 0011 0000B
	                uBL=64,    // 0040H = 0000 0000 0100 0000B
	                uBH=128,   // 0080H = 0000 0000 1000 0000B
	        uBX=192,uEBX=192,  // 00C0H = 0000 0000 1100 0000B
	        uSP=256,uESP=256,  // 0100H = 0000 0001 0000 0000B
	        uBP=512,uEBP=512,  // 0200H = 0000 0010 0000 0000B
	       uSI=1024,uESI=1024, // 0400H = 0000 0100 0000 0000B
	       uDI=2048,uEDI=2048, // 0800H = 0000 1000 0000 0000B
	                uF =16384, // 4000H = 0100 0000 0000 0000B
	                uM =32768, // 8000H = 1000 0000 0000 0000B
	             uSPBPM=33536, // 8300H = 1000 0011 0000 0000B
	               uALL=65535, // FFFFH = 1111 1111 1111 1111B
	          uALLbutBP=65023; // FDFFH = 1111 1101 1111 1111B

//	  public Const Range(0:MaxWord) uMask(nregs) = (
//	 uAL,uCL,uDL,uBL,uAH,uCH,uDH,uBH,uEAX,uECX,uEDX,uEBX,
//	 uESP,uEBP,uESI,uEDI,uEAX,uECX,uEDX,uEBX,uESP,uEBP,uESI,uEDI,uF,uM )
//
//	  public Const Range(0:nregs) WordReg(nregs) = (
//	 qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,
//	   0,  0,  0,  0,qAX,qCX,qDX,qBX,  0,  0,  0,  0,qF,qM )
//
//	  public Const Range(0:nregs) WholeReg(nregs) = (
//	 qEAX,qECX,qEDX,qEBX,qEAX,qECX,qEDX,qEBX,qEAX,qECX,qEDX,qEBX,
//	 qESP,qEBP,qESI,qEDI,qEAX,qECX,qEDX,qEBX,qESP,qEBP,qESI,qEDI,qF,qM )
//
//	  public Const Range(0:2) RegSize(nregs) =
//	  ( 1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,4,4,4,4,4,4,4,4,0,0 )
//
//	  public Const Range(0:1) wBIT(nregs) =
//	  ( 0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0 )
//
//	  public Const Boolean RegParts(nregs) =
//	  ( false,false,false,false,false,false,false,false,
//	    true ,true ,true ,true ,false,false,false,false,
//	    true ,true ,true ,true ,false,false,false,false,false,false )
	 
	 
	//%title ***    Q  -  I  N  S  T  R  U  C  T  I  O  N  S    ***

	// Instruction value classification codes ---
	    public static final int cANY=0; // Don't know
	    public static final int cVAL=1; // Value // integer, character, boolean, real etc.
	    public static final int cOBJ=2; // Pure Object Address (segment and/or base)
	    public static final int cSTP=3; // Address into Stack (SP etc.)
	    public static final int cADR=4; // Address otherwise (NOT pure object)
	    public static final int cMAX=4;

//	 public const range(0:cMAX) cTYPE(18) = // T_MAX+1
//	 (  cANY,cVAL,cVAL,cVAL,cVAL,cVAL,cVAL, cVAL, cVAL,cVAL,cVAL,cOBJ, cVAL, cADR,
//	 // VOID,WRD4,WRD2,BYT2,BYT1,REAL,LREAL,TREAL,BOOL,CHAR,SIZE,OADDR,AADDR,GADDR,
//	    cANY, cVAL, cVAL, cANY );
//	 // PADDR,RADDR,NPADR,NOINF;

//	 public Record Qpkt:Object;
//	begin ref(Qpkt) next,pred;
//	      range(0:MaxWord) read;       // Reg read mask
//	      range(0:MaxWord) write;      // Reg write mask
//	      range(0:20) isize;           // I-code size in bytes
//	      range(0:127) fnc;            // Operation code
//	      range(0:20) subc;            // Operation subcode
//	      range(0:nregs) reg;
//	      range(0:MaxType) type;       // Type of value          --- NEW !!!
//	end;
//
//	------------------------------------------------------------------------
//	-- Format 1.  Generated by Qf1(fnc,subc)   or   Qf1b(fnc,subc,reg)
//	------------------------------------------------------------------------
//	 public Record Qfrm1:Qpkt; begin end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC //   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
	public static final int
	   qWAIT    =  1, //
	   qEVAL    =  2, //
	   qTSTOFL  =  3, //  val
	   qLAHF    =  4, //
	   qSAHF    =  5, //
	   qCWD     =  6, // width
	   qFDUP    =  7, //  fSD
	   qIRET    =  8, //
	   qDOS2    =  9, //
	   qFLDCK   = 10, // subc
	                                                   qFLD1  =1, qFLDL2T=2,
	                                                   qFLDL2E=3, qFLDPI =4,
	                                                   qFLDLG2=5, qFLDLN2=6,
	                                                   qFLDZ  =7,
	   qFMONAD  = 11, // subc     fSD
	                                                   qFNEG=1, qFSQRT=2,
	                                                   qFABS=3,
	                                                   qFRND=4, qFREM=5,
	   qFDYAD   = 12, // subc     fSD
	                                                   qFCOM=1, qFADD=2,
	                                                   qFSUB=3, qFSUBR=4,
	                                                   qFMUL=5, qFDIV=6,
	                                                   qFDIVR=7,
	   qFPUSH   = 13, // fSD      fmf
	   qFPOP    = 14, // fSD      fmf
	   qPUSHR   = 15, // reg
	   qPOPR    = 16; // reg
//	------------------------------------------------------------------------
//	-- Format 2.  Generated by Qf2(fnc,subc,reg,aux)
//	------------------------------------------------------------------------
//	 public Record Qfrm2:Qpkt;
//	begin infix(wWORD) aux end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC //   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
	public static final int
	   qRSTRB   = 17, //   subc   dir    rep
	   qRSTRW   = 18, //   subc   dir    rep
	                                                   qRMOV=1,   // subc
	                                                   qRCMP=2,   // subc
	                                                   qZERO=3,   // subc
	                                                   qRCMPS=4,  // subc
	                                                   qRSCAS=5,  // subc
	                                                   qRSTOS=6,  // subc
	                                                   qCLD=0,    // dir
	                                                   qSTD=1,    // dir
	                                                   qREP=0,    // rep
	                                                   qREPEQ=1,  // rep
	                                                   qREPNE=2,  // rep
	   qMONADR  = 19, //   subc   reg
	                                                   qNOT=1, qNEGM=8,
	                                                   qNEG=2, qNEGF=9,
	                                                   qINC=3, qINCF=10,
	                                                   qDEC=4, qDECF=11,
	                                                   qSHL1=5,qSHL1F=12,
	                                                   qSHR1=6,qSHR1F=13,
	                                                   qSAR1=7,qSAR1F=14,
	   qTRIADR  = 20, //   subc   reg
	                                                   qWMUL=1,  qWMULF=5,
	                                                   qWDIV=2,  qWDIVF=6,
	                                                   qWMOD=3,  qWMODF=7,
	                                                   qIMUL=9,  qIMULF=13,
	                                                   qIDIV=10, qIDIVF=14,
	                                                   qIMOD=11, qIMODF=15,
	   qCONDEC  = 21, //   subc   reg
	                                                   q_WLT=1,  q_WLE=2,
	                                                   q_WEQ=3,  q_WGE=4,
	                                                   q_WGT=5,  q_WNE=6,
	                                                   q_ILT=9,  q_ILE=10,
	                                                   q_IEQ=11, q_IGE=12,
	                                                   q_IGT=13, q_INE=14,
	   qMOV     = 22, //          reg    reg2
	                                                qSEXT=1, qZEXT=2,
	   qXCHG    = 23, //          reg    reg2
	   qDYADR   = 24, //   subc   reg    reg2
	                                                   qAND=1,  qOR=2,
	                                                   qXOR=3,
	                                                   qANDM=4, qORM=5,
	                                                   qXORM=6,
	                                                   qINCO=7, qDECO=8,
	                                                   qCMP=9,  qADD=10,
	                                                   qSUB=11,
	                                                   qADDM=12,qSUBM=13,
	                                                   qADC=14, qSBB=15,
	                                                   qADDF=16,qSUBF=17,
	                                                   qADCF=18,qSBBF=19,
	   qSHIFT   = 25, //   subc   reg
	                                                   qSHL=1,qSHR=2,qSAR=3,
	   qPOPK    = 26, //                 const
	   qRET     = 27, //                 const
	   qINT     = 28, //                 const
	   qADJST   = 29, //                 const
	   qENTER   = 30, //                 const
	   qLEAVE   = 31, //                 const
	   qLINE    = 32, //   subc          const
	                                                   qDCL=1,qSTM=2,
	   qDYADC   = 33; //   subc   reg    const     // Same subcodes as DYADR
//	------------------------------------------------------------------------
//	-- Format 2.  Generated by Qf2(fnc,subc,reg,aux)
//	-- Format 2b. Generated by Qf2b(fnc,subc,reg,aux,addr)
//	------------------------------------------------------------------------
//	 public Record Qfrm2b:Qfrm2;
//	begin infix(MemAddr) addr end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC //   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
	public static final int
//	%-E qLOADSC = 34, //   sreg   reg           addr
	   qPUSHC   = 35, //          reg   const
	                  //          reg   fld     addr
	   qLOADC   = 36; //          reg   const
	                  //          reg   fld     addr
//	------------------------------------------------------------------------
//	-- Format 3.  Generated by Qf3(fnc,subc,reg,opr)
//	-- Format 3b. Generated by Qf3b(fnc,subc,reg,val)
//	------------------------------------------------------------------------
//	 public Record Qfrm3:Qpkt;
//	begin variant infix(MemAddr) opr;
//	      variant infix(ValueItem) val;
//	end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC //   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
	public static final int
	   qJMPM    = 37, //                        opr
//	%-E qJMPFM  = 38, //                        opr
	   qBOUND  = 39, //                        opr
	   qPUSHA   = 40, //          reg           opr
	   qLOADA   = 41, //          reg           opr
	   qSTORE   = 42, //          reg           opr
	   qXCHGM   = 43, //          reg           opr
	   qFLD     = 44, //   fSD    fmf           opr
//	%-E qFLDC   = 45, //   sreg   fmf       lrv/rev/val
	   qFLDC    = 45, //   fSD    fmf       lrv/rev/val
	   qFST     = 46, //   fSD    fmf           opr
	   qFSTP    = 47, //   fSD    fmf           opr
	   qDYADM   = 48, //   subc   reg           opr   // Same subc as DYADR
	   qDYADMR  = 49, //   subc   reg           opr   // Same subc as DYADR
	   qMONADM  = 50, //   subc  width          opr   // Same subc as MONADR
	   qTRIADM  = 51; //   subc  width          opr   // Same subc as TRIADR
//	------------------------------------------------------------------------
//	-- Format 4.  Generated by Qf4(fnc,subc,reg,aux,opr)
//	-- Format 4b. Generated by Qf4b(fnc,subc,reg,aux,opr,addr)
//	-- Format 4c. Generated by Qf4c(fnc,subc,reg,aux,opr,nrep)
//	------------------------------------------------------------------------
//	 public Record Qfrm4:Qfrm3;
//	begin infix(wWORD) aux end;
//
//	 public Record Qfrm4b:Qfrm4;
//	begin infix(MemAddr) addr end;
//
//	 public Record Qfrm4c:Qfrm4;
//	begin range(0:MaxByte) nrep end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC //   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
	public static final int

	   qLOAD    = 52, //   subc   reg    ofst   opr nrep // Same subc as MOV
//	%-E qLDS    = 53, //          reg    ofst   opr nrep
//	%-E qLES    = 54, //          reg    ofst   opr nrep

	   qFDYADM  = 55, //   subc   fmf    fSD    opr   // Same subc as FDYAD
	   qPUSHM   = 56, //                const   opr
	   qPOPM    = 57, //          reg   const   opr
	   qMOVMC   = 58, //         width  const   opr
	                  //         width   fld    opr addr
	   qDYADMC  = 59; //   subc  width  const   opr   // Same subc as DYADR
	                  //   subc  width   fld    opr addr
//	------------------------------------------------------------------------
//	-- Format 5.  Generated by Qf5(fnc,subc,addr)
//	------------------------------------------------------------------------
//	 public Record Qfrm5:Qpkt;
//	begin infix(MemAddr) addr;
//	      range(0:MaxWord) aux;
//	      ref(Qpkt) dst;
//	end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC //   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
	public static final int
	   qCALL    = 60, //   subc         pxlng  addr
	                                                   qEXIT=1, qXNX=2,
	                                                   qOS2=3,  qC=4,
	                                                   qKNL=5,
	   qJMP     = 61; //   subc                addr dst // Same subc as CONDEC 
	                                                    // plus subc=0
//	------------------------------------------------------------------------
//	-- Format 6.  Generated by Qf6(fnc,subc,reg,aux)
//	------------------------------------------------------------------------
//	 public Record Qfrm6:Qpkt;
//	begin range(0:MaxWord) aux;
//	      ref(Qfrm5) jmp;
//	      variant infix(wWORD) rela;
//	      variant range(0:MaxWord) smbx;
//	end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC //   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
	public static final int
	   qFDEST   = 62, //                fixno       jmp
	   qBDEST   = 63, //                labno  rela jmp
	   qLABEL   = 64, //   subc         fixno  smbx
	                                                   qBPROC=1,qEPROC=2,

	   qMXX     = 64;
	
//	%title ***   T h e   E n v i r o n m e n t   I n t e r f a c e   ***
//	Range(0:36) status system "STATUS";
//
//	Record string; info "TYPE";
//	begin name(character) chradr; integer nchr; end;
//
//	---   E r r o r   H a n d l i n g   ---
//
//	global profile Perhandl;
//	import range(0:13) code;
//	       infix(string) msg;
//	       label addr;
//	export label cont end;
//
//	global profile Perror;
//	import infix(string) msg end;
//
//	Entry(Perror) Erroutine;
//	%title ***    G l o b a l   V a r i a b l e s    ***
//
//	    Range(0:MaxKey) scode;      // File key for S-Code input
//	    Range(0:MaxKey) modoupt;    // File key for module output file
//	    Range(0:MaxKey) modinpt;    // File key for module input file
//	    Range(0:MaxKey) scrfile;    // File key for relocatable scratch file
//	    Range(0:MaxKey) objfile;    // File key for relocatable object file
//	    Ref(File) sysin;            // Main input's file-object
//	    Ref(File) sysout;           // Print output's file-object
//	%+D Ref(File) inptrace;         // Input Trace's file-object
//	%+D Ref(File) ouptrace;         // Output Trace's file-object
//	    Ref(File) sysedit;          // Text editing's file-object
//	    Ref(File) errmsg;           // Error message editing's file-object
//	    Range(0:10) CPUID;          // 1=iAPX86,2=iAPX88,3=iAPX186,4=iAPX286
//	                                // 5=iAPX386,6=iAPX486
//	    Range(0:10) NUMID;          // 1=iAPX87,2=iAPX287,3=iAPX387
//	    Range(0:10) OSID;           // 0=DOS,1=OS2,2=XENIX/286,3=XENIX/386
//	                                // 4=UNIX/386
//	    Range(0:10) CBIND;          // cNONE=0,cMS=1,cTURBO=2
//	    Range(0:MaxByte) CombAtr;   // 0:Normal, 1:Combined Sysinsert
//
//	    Boolean TSTOFL;             // True:Insert test-on-overflow
//
	public int ntype;     // Number of data types defined
	public int T_INT,T_SINT; // Integer type mapping
//	    Infix(DataType) TTAB(MaxType); // Type specification table
	public DataType[] TTAB = new DataType[MaxType];
//
//	%+S Range(0:P_max) PsysKind; --
	public int TypeLength; // Nbytes of last nonstandard InType
	public int TagIdent;        // Tag-Ident from InXtag
//
//	    Range(0:16000) nerr;        // Number of error messages until now
//	    Range(0:MaxWord) curline;   // Current source line number
//	    Range(0:MaxByte) curinstr;  // Current instr-byte read from scode
//	    Range(0:10) InputTrace;     // Input trace switch
//	    Range(0:10) listq1;         // Output Q-code 1 listing switch
//	    Range(0:10) listq2;         // Output Q-code 2 listing switch
//	    Range(0:10) listsw;         // Output I-code listing switch
//	    Range(0:10) TraceMode;      // Processing trace switch
//	    Range(0:10) ModuleTrace;    // Module I/O trace switch
//	    Range(0:255) MASSLV;        // Massage depth level
//	    Range(0:255) MASSDP;        // Massage max recursion depth
//	    Real InitTime;              // Initiation CPU-time
//	%+S Boolean envpar;             // True: Parameters from environment
//	    Boolean errormode;          // Treating an Error
//	%+A Boolean asmgen;             // ASM-output generating mode
//	    Boolean InsideRoutine;      // Inside Routine Body indicator
//
//	    --- Dictionary ---
//	    Infix(Dictionary) DIC;
//	    Infix(WORD) PRFXID;     // Prefix to entry point symbols
//	    Infix(WORD) PROGID;     // Ident of program being defined
//	    Infix(WORD) CSEGID;     // Index of program's current code Segment
//	    Infix(WORD) DSEGID;     // Index of program's data Segment
//	    Infix(WORD) LSEGID;     // Index of program's LineTable Segment
//	    Infix(WORD) DumSEG;     // Index of the dummy Segment
//	    Infix(WORD) DGROUP;     // Index of the _DATA Segment
//	    Infix(WORD) EnvCSEG;    // Index of environment's code Segment
//	    Infix(WORD) SCODID;     // Name of Scode input file
//	    Infix(WORD) ATTRID;     // Prefix to attribute file
//	    Infix(WORD) RELID;      // Name of RELocatable object Output file
//	    Infix(WORD) SCRID;      // Name of rel/asm scratch file
//	%+A Infix(WORD) ASMID;      // Name of assembly source output file
//	    Infix(WORD) ProgIdent;  // S-Code PROG String
//	    Infix(WORD) modident;   // Ident of module being defined
//	    Infix(WORD) modcheck;   // Check code of module being defined
//	    Infix(WORD) DsegEntry;  // Data Segment start symbol
//	    ---   Entry Points ---
//	    Infix(MemAddr) MainEntry; // Main program's entry-point
//	    Infix(MemAddr) LtabEntry; // Line-no-table's entry-point
//	    Infix(MemAddr) X_OSSTAT;  // Entry-point of G.OSSTAT
//	    Infix(MemAddr) X_KNLAUX;  // Entry-point of G.KNLAUX
//	    Infix(MemAddr) X_STATUS;  // Entry-point of G.STATUS
//	    Infix(MemAddr) X_STMFLG;  // Entry-point of G.STMFLG
//	%+D Infix(MemAddr) X_ECASE;   // Entry-point of ECASE routine
//	%+S Infix(MemAddr) X_INITO;   // Entry-point of INITO routine
//	%+S Infix(MemAddr) X_GETO;    // Entry-point of GETO routine
//	%+S Infix(MemAddr) X_SETO;    // Entry-point of SETO routine
//	%-E %+SC Infix(MemAddr) X_STKOFL; // Entry-point of STKOFL routine
//	    Infix(MemAddr) X_SSTAT;   // Entry-point of XENIX get errno rut.
//
//	    Infix(MemAddr) TMPAREA;   // Temp area
//	    Infix(MemAddr) TMPQNT;    // Temp quant area (of RTS)
//	    Infix(MemAddr) X_INITSP;  // Entry-point of G.INITSP
//
//	    --- Current Stack ---
	public int StackDepth87; // initial(0)
	public StackItem TOS; // Top of Compile-time stack
	public StackItem BOS; // Bot of Compile-time stack
	public StackItem SAV; // Last Compile-time stack-item for which
	                      // the corresponding Runtime-item is saved.
	                      // NOTE: SAV =/= none implies TOS =/= none

//	    Ref(BSEG) curseg;           // Current program BSEG
//	    Ref(BSEG) FreeSeg;          // Free program BSEG list
//	    Range(0:MaxWord) nSubSeg;   // No.of sub-segments gen. by BSEG
//
//	    Ref() PoolTop;              // Storage boundary pointer
//	    Ref() PoolNxt;              // Storage boundary pointer
//	    Ref() PoolBot;              // Storage boundary pointer
//	    Range(0:MaxByte) npool;     // No.of data pools allocated
//	    Ref(FreeArea) FreePool;     // Head of Free Pool list
//	%+D Infix(PoolSpec) PoolTab(MaxPool); // Storage pool statistics
//	    Ref(Object) FreeObj(K_Max); // Free Object lists
//	%+D Range(0:MaxWord) ObjCount(K_Max); // No.of Objects generated
//	%+D Integer CalCount(R_Max);    // No.of Routine calls
//
//	%+D Range(0:10) BECDEB;      // Debugging level   (Debugging purposes)
//	%+D Range(0:10) TLIST;       // Option D - Major Event Trace Level
//	    Range(0:16000) MXXERR;   // Max number of errors
//	%+S Range(0:10) SYSGEN;      //  System generation
//	                             //      0: User program
//	                             //      1: Generation of Runtime System
//	                             //      2: Generation of S-Compiler
//	                             //      3: Generation of Environment
//	                             //      4: Generation of Library
//
//	    Range(0:MaxWord) SEGLIM; // Max seg-size befor segment-split
//	    Range(0:16000) QBFLIM;   // No.of Q-instr in buf before Exhaust Half
//	    Range(0:10) RNGCHK;      // >0: produce Range --> char range check
//	    Range(0:10) IDXCHK;      // >0: produce array index check
//	    Range(0:10) LINTAB;      // 0:No-LineTab, else:Generate LineTab
//	    Range(0:10) DEBMOD;      // >2:Generate line breaks, else: do not!
//
//	    Range(0:MaxWord) LabelSequ; // No.of labels  created by 'NewLabno'
//	    Range(0:MaxWord) SymbSequ;  // No.of symbols created by 'NewPubl'
//
//	%+D Integer SK1LIN;    //  S-Compiler-Trace - Pass 1 starting line
//	%+D Integer SK1TRC;    //  Pass 1 Trace level=SEOMTI (one digit each)
//	%+D Integer SK2LIN;    //  S-Compiler-Trace - Pass 2 starting line
//	%+D Integer SK2TRC;    //  Pass 2 Trace level=SEOMTI (one digit each)
//	                                 //   I = 0..9 Input trace level
//	                                 //   T = 0..9 Trace-mode level
//	                                 //   M = 0..9 Module IO trace level
//	                                 //   O = 0..9 Output trace level
//	                                 //   E = 0..9 Output trace level listq1
//	                                 //   S = 0..9 Output trace level listq2
//
//	    Range(0:MaxWord) nTag;         // No.of tags defined
//	    Range(0:MaxWord) nFix;         // No.of Fixups defined
//	    Range(0:MaxWord) nXtag;        // Size of TAGTAB
//	    Range(0:MaxWord) nXtyp;        // Size of TYPMAP
//	    Range(0:MaxWord) nXsmb;        // Size of SMBMAP
//	    Ref(Qpkt) DESTAB(255);         // Jump Destination table
//	    Ref(Qpkt) DESTAB256;           // ???? TEMP ????
//	    Ref(Qpkt) FWRTAB(255);         // Extra Jump Destination table
//	    Ref(Qpkt) FWRTAB256;           // ???? TEMP ????
//	    ref(qpkt) xFJUMP;              --- see parse/coder (gqrelation)
//	    Ref(RefBlock)  DISPL(MxpTag);  // Descriptor Display Table
//	%+D Ref(WordBlock) TIDTAB(MxpTag); // Tag-Identifier table
//	    Ref(FixBlock) FIXTAB(MxpFix);  // FIXUP table
//	    Ref(WordBlock)TAGTAB(MxpXtag); // Tag Table (during Module I/O)
//	    Ref(WordBlock)TYPMAP(MxpXtyp); // Type-mapping (during Module I/O)
//	    Ref(WordBlock)SMBMAP(MxpXsmb); // Symbol-mapping (during Module I/O)
//
//	    Range(0:MaxByte) NXTYPE; // Type of First/Next buffer
//	    Infix(WORD) NXTLNG;      // Length of First/Next buffer
//	    Infix(Scodebuffer) SBUF; // S-Code Buffer
//	    Infix(iCodebuffer) CBUF; // Code Buffer
//	    Infix(Relocbuffer) CREL; // Code Relocations
//	    Infix(iCodebuffer) DBUF; // Data Buffer
//	    Infix(Relocbuffer) DREL; // Data Relocations
//
//	%title ***   M e m o r y   A d d r e s s e s   ***
	public static final int reladr=1,locadr=2,segadr=3,extadr=4,fixadr=5,knladr=6;
	public static final int adrMax=6;

//	Record MemAddr; info "TYPE";
//	-- NOTE: All variants of MemAddr are treated as Compact and
//	--       of the same size. So, test for equality between two
//	--       MemAddrs may be performed directly on the type MemAddr.
//	--       E.g.  'opr1' and 'opr2'  are equal iff  'opr1=opr2'.
//	begin
//	      infix(wWORD) rela;       // Relative byte address
//	   range(0:MaxByte) sibreg; // <ss>2<ireg>3<breg>3
//	                            // ss: Scale Factor 00=1,01=2,10=4,11=8
//	                            // ireg,breg: 000:[EAX]   001:[ECX]
//	                            //            010:[EDX]   011:[EBX]
//	                            //            100:[ESP]   101:[EBP]
//	                            //            110:[ESI]   111:[EDI]
//	                            // E.g.   10 110 011=DS:[EBX]+[ESI]*4
//	                            //        11 111 101=SS:[EBP]+[EDI]*8
//	                            // Note:  11 100 100=228 is ruled out,
//	                            //        meaning no breg or ireg
//	      range(0:adrMax) kind;    // Variant kind code
//	                                       // reladr: + rela
//	      variant range(0:MaxWord) loca;   // locadr: + rela - loca
//	      variant infix(WORD) segmid;      // segadr: SEG(segid)+rela
//	      variant infix(WORD) smbx;        // extadr: SYMBOL(smbx)+rela
//	      variant infix(WORD) fix;         // fixadr: FIXUP(fix)+rela
//	      variant infix(WORD) knlx;        // knladr: KERNEL(knlx)
//	end;
//
//	Record Fixup; info "TYPE";
//	begin Boolean Matched;
//	%+D   range(0:MaxLine) line;     // Created at line
//	%+D   range(0:MaxWord) fixno;    // Assembly label serial number
//	      Infix(WORD) segid;         // Segment name index
//	      variant Infix(WORD) smbx;        // Unmatched: External Symbol
//	      variant Infix(wWORD) rela;       // Relative byte address
//	end;
//
//	Record FixBlock;
//	begin infix(Fixup) elt(255);
//	      infix(Fixup) elt256;   --- TEMP
//	end;
//
//	Record ExtRef; info "TYPE";
//	begin range(0:MaxWord) rela;   // Relative byte address
//	      Infix(WORD) smbx;        // External Symbol index
//	end;
//	%title ***   S t r u c t u r e d    T y p e s   ***
//	------   D a t a   T y p e s   ------

	public static final int tUnsigned=0,tSigned=1,tFloat=2;

//	Record DataType; info "TYPE";
//	begin range(0:MaxByte) nbyte;    // Size of type in bytes
//	      range(0:2) kind;           // tUnsigned,tSigned,tFloat
//	      infix(WORD) pntmap;        // 0:no pointers,
//	end;                             // else: Reladdr of pointers
//
//	------   W O R D  /  D W O R D  /  Q W O R D   ------
//
//	Record WORD; info "TYPE";
//	begin variant range(0:MaxWord) val;
//	      variant range(0:MaxByte) LO,HI;
//	end;
//
//	Record DWORD; info "TYPE";
//	begin variant integer          val;
//	      variant range(0:MaxByte) LO,LOHI,HILO,HI;
//	      variant infix(WORD) LowWord;
//	              infix(WORD) HighWord;
//	end;
//
//	Record wWORD; info "TYPE";
//	begin
//	%-E   variant range(0:MaxWord) val;
//	   variant integer          val;
//	%-E   variant range(0:MaxByte) LO,HI;
//	   variant range(0:MaxByte) LO,LOHI,HILO,HI;
//	      variant infix(WORD) LowWord;
//	           infix(WORD) HighWord;
//	end;
//
//	------   V  a  l  u  e     I  t  e  m   ------
//
//	Record ValueItem; info "TYPE";
//	begin variant range(0:MaxByte) byt(8); // ?? variant range(0:MaxWord) byt(8);
//	      variant range(0:MaxWord) wrd(4);
//	      variant integer int(2);
//	      variant real rev(2);
//	      variant long real lrv;
//	      variant infix(MemAddr) base;     // size 6(-E) / 8(+E)
//	              range(0:MaxWord) Ofst;
//	end;
//
//	Record LinePkt;
//	begin ref(LinePkt) next;
//	      range(0:MaxLine) line;
//	      infix(wWORD) rela;
//	end;
//
//	Record File;
//	begin range(0:MaxKey) key;     --- File key
//	      range(0:132) pos;        --- Posision indicatior (0..nchr-1)
//	      range(0:MaxByte) nchr;
//	      character chr(0);        --- File buffer
//	end;
//
//	%title ***  B U F F E R S  ***
//
//	Record Buffer;
//	begin character hed(2);     // 1:Scode, 2:Code, 3:Reloc
//	      range(0:MaxWord) nxt; // Next available byte position
//	end;
//
//	public static final int sBufLen = 2048
//	Record ScodeBuffer:Buffer;
//	begin variant character chr(sbuflen);
//	      variant range(0:MaxByte) byt(sbuflen);
//	end;
//
//	Record iCodeBuffer:Buffer;
//	begin
//	%-E   infix(WORD) segx;                // NOTE: This record is written
//	%-E   infix(WORD) ofst;                //       to give the same layout
//	   range(0:MaxWord) segx;           //       with %+E on 286 and 386
//	   range(0:MaxWord) ofstLO,ofstHI;  // -----------------------------
//	      variant character chr(1024);
//	      variant range(0:MaxByte) byt(1024);
//	end;
//
//	------  R e l o c a t i o n    P a c k e t s  ------
//
//	-- mrk = <0>1<FieldType>3<RelType>2<Offset>10
//	-- FieldType:
//	    public static final int fPOINTER=3,  mfPOINTER=12288;  // 3000H Segm-relative
//	    public static final int fSEGMENT=2,  mfSEGMENT=8192;   // 2000H Segm-relative
//	    public static final int fOFFSET=1,   mfOFFSET=4096;    // 1000H Segm-relative
//	 public static final int fOFST32=5,   mfOFST32=20480;   // 5000H Segm-relative
//	    public static final int fFULLDISP=4, mfFULLDISP=16384; // 4000H Self-relative
//	    public static final int fBYTEDISP=0, mfBYTEDISP=0;     // 0000H Self-relative
//	-- RelType:
//	    public static final int rSEG=0,mrSEG=0;                // 0000H Segment base
//	    public static final int rEXT=1,mrEXT=1024;             // 0400H External
//	    public static final int rFIX=2,mrFIX=2048;             // 0800H Fixup
//
//	Record RelocPkt; info "TYPE";
//	begin range(0:MaxWord) mrk;
//	      variant infix(WORD) segx;
//	      variant infix(WORD) extx;
//	      variant infix(WORD) fix;
//	end;
//
//	Record RelocBuffer:Buffer;
//	begin infix(RelocPkt) elt(255);
//	      infix(RelocPkt) elt256;
//	end;
//
//	%+F Record CoffRelocPkt;
//	%+F begin integer vAddr;
//	%+F       integer SymNdx;
//	%+F       range(0:MaxWord) Type;  // Relocation Type
//	%+F end;
//
//	%+F     public static final int R_ABS=0;       // No Relocation
//	%+F %-E public static final int R_REL16=1;     // Self-relative 16-bit Relocation
//	%+F %-E public static final int R_OFF8=7;      // Segment relative 8-bit Offset
//	%+F %-E public static final int R_OFF16=8;     // Segment relative 16-bit Offset
//	%+F %-E public static final int R_SEG12=9;     // Segment relative 16-bit Selector
//	%+FE    public static final int R_DIR32=6;     // Segment relative 32-bit Relocation
//	%+FE    public static final int R_PCRLONG=20;  // Self-relative 32-bit Relocation
//
//	%+F Record RelocObj;
//	%+F begin ref(RelocObj) next;
//	%+F       infix(CoffRelocPkt) Cpkt;
//	%+F end;
//	%title ***   D i c t i o n a r y    ***
//	Record Dictionary;
//	begin range(0:MaxWord) nSymb;       // No.of Symbols
//	      range(0:MaxWord) nSegm;       // No.of Segments
//	      range(0:MaxWord) nPubl;       // No.of Public Definitions
//	      range(0:MaxWord) nExtr;       // No.of External References
//	      range(0:MaxWord) nModl;       // No.of Module Names
//	      infix(WORD) HashKey(255);     // Hash Key table
//	      infix(WORD) HashKey256;       // !!!! TEMP
//	      ref(RefBlock)  Symb(MxpSymb); // Symbol table
//	      ref(WordBlock) Segm(MxpSegm); // Segment Table
//	      ref(WordBlock) Publ(MxpPubl); // Public Definition Table
//	      ref(WordBlock) Extr(MxpExtr); // External Reference Table
//	      ref(WordBlock) Modl(MxpModl); // Module Definition Table
//	end;
//
//	Record SmbElt;
//	begin range(0:MaxByte) clas;   --- Symbol Class
//	      range(0:MaxByte) nchr;   --- No.of chars in symbol
//	      infix(WORD) link;        --- Next Symbol with same hash-key
//	end;
//
//	Record Symbol:SmbElt;          // clas=sSYMB
//	begin character chr(0) end;
//
//	Record Extern:SmbElt;          // clas=sEXTR
//	begin infix(WORD) extx;        // Extern index
//	      infix(WORD) segid;       // Segment name
//	      character chr(0);
//	end;
//
//	Record Public:SmbElt;          // clas=sPUBL
//	begin infix(WORD) pubx;        // Public index
//	      infix(WORD) segx;        // Segment index
//	      infix(wWORD) rela;       // Relative address
//	      character chr(0);
//	end;
//
//	Record ModElt:SmbElt;          // clas=sMODL
//	begin infix(WORD) modx;        // Module index
//	      Infix(WORD) LinTab;      // Entry-Point of Line-no-Table
//	      Infix(WORD) RelElt;      // REL-file's file-spec
//	      Infix(WORD) AtrElt;      // AT2-file's file-spec
//	      Infix(WORD) AtrNam;      // AT2-file's DataSetName i.e. Full name
//	      character chr(0);
//	end;
//
//	Record Segment:SmbElt;         // clas=sSEGM
//	begin infix(WORD) segx;           // Segment index
//	      range(0:2) type;            // 0:Stack, 1:Data, 2:Code
//	      infix(wWord) lng;           // Segment length in bytes
//	      infix(wWord) rela;
//	      range(0:MaxWord) MindMask;  // Accumulated Reg mind mask
//	      ref(Qpkt) qfirst,qlast;     // Q-Code Queue
//	      range(0:MaxWord) qcount;
//	      ref(LinePkt) lfirst,llast;  // Line-no-Table
//	      range(0:MaxWord) lcount;
//	%+F   integer pAddr;              // Start addr within raw text in COFF
//	%+F   ref(RelocObj) FstRel,LstRel;
//	%+F   range(0:MaxWord) Sectn;     // Mapped to COFF Section no.
//	      character chr(0);           // 
//	end;
//	%title ***   D y n a m i c     O b j e c t s    ***
//
//	Record Object;
//	begin range(0:MaxByte) kind;   --- Object kind code
//	      range(0:MaxType) type; 
//	end;
//
//	Record FreeObject:Object;
//	begin ref(Object) next end;    --- Free list pointer
//
//	Record FreeArea;
//	begin ref(FreeArea) next;      --- Free list pointer
//	      size PoolSize;
//	end;
//
//	%+D Record PoolSpec; info "TYPE";
//	%+D begin ref() PoolTop;
//	%+D       size PoolSize;
//	%+D end;
//
//	Record RefBlock:Object;
//	begin ref() elt(255);
//	      ref() elt256;     --- TEMP
//	end;
//
//	Record WordBlock:Object;
//	begin infix(WORD) elt(255);
//	      infix(WORD) elt256;   --- TEMP
//	end;
//
//
//	Record AddrBlock:Object;
//	begin infix(MemAddr) elt(255);
//	      infix(MemAddr) elt256;   --- TEMP
//	end;
//
//	%page
//	      ---------------------------------------------------
//	      ---       D  e  s  c  r  i  p  t  o  r  s       ---
//	      ---------------------------------------------------
//
//	Record Descriptor:Object;
//	begin end;
//
//	Record RecordDescr:Descriptor;   // K_RecordDescr                 SIZE = 8 bytes
//	begin range(0:MaxWord) nbyte;    // Record size information
//	      range(0:MaxWord) nbrep;    // Size of rep(0) attribute
//	      infix(WORD) pntmap;        // Only used by TypeRecord
//	end;
//
//	Record TypeRecord:RecordDescr;   // K_TypeRecord                  SIZE = 8 bytes
//	begin // infix(WORD) pntmap;     // 0:no pointers,
//	end;                             // else: Reladdr of pointers
//
//	Record LocDescr:Descriptor;      // K_Attribute  Offset = rela    SIZE = 8 bytes
//	                                 // K_Parameter  Address = [BP] + rela
//	                                 // K_Export     Address = [BP] + rela
//	                                 // K_LocalVar   Address = [BP] - rela
//	begin range(0:MaxWord) rela;
//	      infix(WORD) nextag;        // NOTE: Only used for Parameters
//	                                 // NOTE: And only in 'inprofile'
//	      range(0:MaxWord) UNUSED;   // To 4-byte align record in all cases
//	end;  
//
//	Record IntDescr:Descriptor;      // K_Globalvar
//	begin infix(MemAddr) adr;        // K_IntLabel
//	end;                             // K_IntRoutine   Local Routine
//
//	Record ExtDescr:Descriptor;      // K_ExternVar                   SIZE = 8 bytes
//	begin range(0:MaxWord) UNUSED;   // To 4-byte align record in all cases
//	      infix(ExtRef) adr;         // K_ExtLabel
//	end;                             // K_ExtRoutine   External Routine
//
//	Record ParamSpec; info "TYPE";
//	begin range(0:MaxType) type;
//	      range(0:MaxByte) count;
//	end;
//
//	Record ProfileDescr:Descriptor;  // K_ProfileDescr     SIZE = (6+npar*2) align 4
//	begin range(0:MaxByte) npar;     // No.of parameters
//	      range(0:1) WithExit;
//	      range(0:MaxParByte) nparbyte;
//	      range(0:P_max) Pkind;
//	      infix(ParamSpec) Par(0);   // Parameter Specifications
//	end;
//
//	Record SwitchDescr:Descriptor;
//	begin range(0:MaxSdest) ndest;   // No. of Sdest in this switch
//	      range(0:MaxSdest) nleft;   // No. of Sdest left to be defined
//	      infix(MemAddr) swtab;      // Start of Sdest-Table
//	      ref(AddrBlock) DESTAB(MxpSdest); // All SDEST addresses
//	end;
//	%page
//
//	---------------------------------------------------------
//	---       M  o  d  u  l  e     H  e  a  d  e  r       ---
//	---------------------------------------------------------
//
//	Record ModuleHeader;
//	begin range(0:MaxWord) Magic;  // Magic number
//	      range(0:MaxByte) Layout; // File Layout number
//	      range(0:MaxByte) Comb;   // 0:Normal, 1:Combined
//	      Infix(WORD) modid;       // Module's identification
//	      Infix(WORD) check;       // Module's check code
//	      range(0:MaxWord) nXtag;  // No.of external tags
//	      range(0:MaxWord) nType;  // No.of Types
//	      range(0:MaxWord) nSymb;  // No.of Symboles
//	      range(0:MaxWord) sDesc;  // Size of Descriptor area
//	      range(0:MaxWord) sFeca;  // Size of FEC-Attribute area
//	      range(0:MaxWord) nTmap;  // 0:No TAGMAP, no.of tags in TAGMAP
//	      integer DescLoc;         // Fileloc: Descriptor area
//	      integer TypeLoc;         // Fileloc: Type table
//	      integer TgidLoc;         // Fileloc: Tagid table
//	      integer SymbLoc;         // Fileloc: Symbol Table
//	      integer FecaLoc;         // Fileloc: FEC-Attributes
//	      integer TmapLoc;         // Fileloc: TAGMAP table
//	end;
//
//	%page
//	---------------------------------------------
//	---------    S  e  g  m  e  n  t    ---------
//	---------------------------------------------
//
//	Record BSEG:Object;
//	begin ref(BSEG) next;
//	      Infix(WORD) segid;       // Program Segment's name index
//	      range(0:8) StackDepth87; // initial(0)
//	      ref(StackItem) TOS;      // Top of Compile-time stack
//	      ref(StackItem) BOS;      // Bot of Compile-time stack
//	      ref(StackItem) SAV;   // Last Compile-time stack-item for which
//	                            // the corresponding Runtime-item is saved.
//	                            // NOTE: SAV =/= none implies TOS =/= none
//	end;
//
//
//	---------------------------------------------
//	---------   S t a c k   I t e m s   ---------
//	---------------------------------------------
//
//	Record StackItem:Object;
//	begin short integer repdist;
//	      ref(StackItem) suc;
//	      ref(StackItem) pred;
//	end;
//
//	Record ProfileItem:StackItem;
//	begin ref(ProfileDescr) spc;
//	      range(0:MaxPar) nasspar;
//	end;
//
//	Record Address:StackItem;
//	begin infix(MemAddr) Objadr;   // Object Address
//	      range(0:MaxWord) Offset; // Attribute Offset
//	      range(0:2) ObjState;     // NotStacked ! FromConst ! Calculated
//	      range(0:2) AtrState;     // NotStacked ! FromConst ! Calculated
//	end;

	public static final int NotStacked=0,FromConst=1,Calculated=2;

//	Record Temp:StackItem;            --- Value is pushed on RT-stack
//	begin  end;
//
//	Record Coonst:Temp;               --- Value is also in 'itm'
//	begin infix(ValueItem) itm;
//	end;
//	%title
//	--- Current Location Counter Data ---
	public Qpkt qfirst,qlast;   // initial(none) q-list of CSEG
	public int qcount;    // initial(0), No.of Qpkt's in q-list
//	------------------------------------------------------------------------

	 public boolean   reversed;          // whether compare exchanged operands
	 public boolean   inMassage;         // True while in massage
	 public boolean   deadCode;          // inital(false)
	 public boolean   changeable;        // Changereg in massage
	 public int       RecDepth;     // Current Masseur recursion depth left
	 public int       stackMod1, stackMod2; // see massage
	 public boolean   deleteOK;          // see massage'peepExhaust

//	 public Range(0:MaxWord) MindMask; // Registers 'mind' after last qi
//	 public Range(0:MaxWord) PreReadMask;  // Do 'read'  opposite normal
//	%+S  public Range(0:MaxWord) PreWriteMask; // Do 'write' opposite normal
//	 public Range(0:MaxWord) PreMindMask;  // Do 'mind'  opposite normal
//	 public Range(0:MaxWord) NotMindMask;  // Not 'mind' opposite normal
//
//	  public Const Range(0:nregs) accreg(5)=(0,qAL,qAX,0,qEAX);
//	  public Const Range(0:nregs) extreg(5)=(0,qAH,qDX,0,qEDX);
//	  public Const Range(0:nregs) countreg(5)=(0,qCL,qCX,0,qECX);
//	  public Const Range(0:nregs) datareg(5)=(0,qDL,qDX,0,qEDX);
//
//	 public Const Range(0:14) NotQcond(16) = (
//	-- original:      <       <=      =       >=      >       <>         ---
//	-- test flipped:  >=      >       <>      <       <=      =          ---
//	------- 0       q_WLT   q_WLE   q_WEQ   q_WGE   q_WGT   q_WNE   7    ---
//	        0,      q_WGE,  q_WGT,  q_WNE,  q_WLT,  q_WLE,  q_WEQ,  0,
//	------- 8       q_ILT   q_ILE   q_IEQ   q_IGE   q_IGT   q_INE   15   ---
//	        0,      q_IGE,  q_IGT,  q_INE,  q_ILT,  q_ILE,  q_IEQ,  0);
//
//	 public Const Range(0:14) RevQcond(16) = (
//	-- original:      <       <=      =       >=      >       <>         ---
//	-- data flipped:  >       >=      =       <=      <       <>         ---
//	------- 0       q_WLT   q_WLE   q_WEQ   q_WGE   q_WGT   q_WNE   7    ---
//	        0,      q_WGT,  q_WGE,  q_WEQ,  q_WLE,  q_WLT,  q_WNE,  0,
//	------- 8       q_ILT   q_ILE   q_IEQ   q_IGE   q_IGT   q_INE   15   ---
//	        0,      q_IGT,  q_IGE,  q_IEQ,  q_ILE,  q_ILT,  q_INE,  0);
//
//
//	end;

}
