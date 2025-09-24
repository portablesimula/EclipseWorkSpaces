package iapx.util;

import java.util.Map;

import iapx.qPkt.Qpkt;
import iapx.records.Fixup;
import iapx.segment.DataSegment;
import iapx.segment.Segment;
import iapx.value.MemAddr;

public class Global {
	
	public static DataSegment CSEG; // Constant Segment
	public static DataSegment TSEG; // Constant TextValue Segment
	public static DataSegment DSEG; 
	public static ProgramSegment PSEG; // Current PSEG

	
	 public final static int MinTag=32;       // First user tag
	 public final static int MaxKey=255;      // Max number of file keys
	 public final static int MaxType=63;      // Max number of data types
	 public final static int MaxByte=255;     // Max value of 8-bit byte  (2**8-1)
	 public final static int MaxWord=65535;   // Max value of 16-bit word (2**16-1)
	 public final static int MaxSint=32767;   // Max value of SINT        (2**15-1)
	 public final static int MaxSdest=8192;   // Max number of dest-in-switch
	 public final static int MaxString=32000; // Max number of chars in a string
	 public final static int MaxLine=32000;   // Max number of source lines
	 public final static int MaxPool=63;   // Max number of pools in trace version

//	 public final static int  TAG_BOOL    = 1,  TAG_CHAR    = 2,  TAG_INT     = 3;
//	 public final static int  TAG_SINT    = 4,  TAG_REAL    = 5,  TAG_LREAL   = 6;
//	 public final static int  TAG_AADDR   = 7,  TAG_OADDR   = 8,  TAG_GADDR   = 9;
//	 public final static int  TAG_PADDR   = 10, TAG_RADDR   = 11, TAG_SIZE    = 12;
//
//	 public final static int T_VOID=0,T_INT=1,T_REAL=2,T_LREAL=3,T_TREAL=4,T_BOOL=5,T_CHAR=6,T_SIZE=7;
//	 public final static int T_OADDR=8,T_AADDR=9,T_GADDR=10,T_PADDR=11,T_RADDR=12,T_NPADR=13,T_NOINF=14;
//	 public final static int T_max=14;  // Max value of predefined type

//	 public final static int T_VOID=0;
//	 public final static int T_BOOL=5;
//	 public final static int T_REAL=2;
//	 public final static int T_VOID=0,T_INT=1,T_REAL=2,T_LREAL=3,T_TREAL=4,T_BOOL=5,T_CHAR=6,T_SIZE=7;
//	 public final static int T_VOID=0,T_INT=1,T_REAL=2,T_LREAL=3,T_TREAL=4,T_BOOL=5,T_CHAR=6,T_SIZE=7;
//	 public final static int T_VOID=0,T_INT=1,T_REAL=2,T_LREAL=3,T_TREAL=4,T_BOOL=5,T_CHAR=6,T_SIZE=7;
//	 public final static int T_VOID=0,T_INT=1,T_REAL=2,T_LREAL=3,T_TREAL=4,T_BOOL=5,T_CHAR=6,T_SIZE=7;
//	 public final static int T_VOID=0,T_INT=1,T_REAL=2,T_LREAL=3,T_TREAL=4,T_BOOL=5,T_CHAR=6,T_SIZE=7;
//	 public final static int T_OADDR=8,T_AADDR=9,T_GADDR=10,T_PADDR=11,T_RADDR=12,T_NPADR=13,T_NOINF=14;
//	 public final static int T_max=14;  // Max value of predefined type

	 public final static int FMF_REAL  = 0; // 00 0B
	 public final static int FMF_LREAL = 4; // 10 0B
	 public final static int FMF_INT   = 2; // 01 0B
	 public final static int FMF_SINT  = 6; // 11 0B
	 public final static int FMF_TEMP  = 3; // 01 1B  // Special Case

	 public final static int O_SSEG=1,O_IDATA=2,O_LDATA=3;
	 public final static int O_AEXT=4,O_ASEG=5,O_AFIX=6;
	 public final static int O_LINE=7,O_TRC=8,O_END=9;
	 public final static int O_max=9;

	 public final static int aDGRP=0,aDATA=1,aCODE=2,aLINE=3;

	 public final static int iAPX86=1,iAPX186=2,iAPX286=3,iAPX386=4;
	 public final static int NoNPX=0,iAPX87=1,iAPX287=2,iAPX387=3,WTLx167=4;

	 public final static int oMSDOS     = 0; // DOS    without numeric coprocessor
	 public final static int oMSDOS87   = 1; // DOS       with 8087 or higher
	 public final static int oMSOS2     = 2; // OS/2      with 8087 or higher
	 public final static int oXENIX286  = 3; // XENIX/286 with 80287 (or emulator) or higher
	 public final static int oXENIX386  = 4; // XENIX/386 with 80287 (or emulator) or higher
	 public final static int oXENIX386W = 5; // XENIX/386 with Weitek 1167 or higher
	 public final static int oUNIX386   = 6; // UNIX/386  with 80287 (or emulator) or higher
	 public final static int oUNIX386W  = 7; // UNIX/386  with Weitek 1167 or higher
	 public final static int oSINTRAN   = 8; // SINTRAN/ND500

	 public final static int cNONE=0;        // No C-Binding defined
	 public final static int cMS=1;          // Simula used together with MicroSoft C
	 public final static int cTURBO=2;       // Simula used together with TURBO C

	 public final static int xGOTO=0,xCALL=1;

//		 ------ Symbol Class Codes ------
	 public final static int sSYMB=0,sEXTR=1,sPUBL=2,sSEGM=3,sMODL=4;
	 public final static int sMAX=4;

//		%title ***    G l o b a l   V a r i a b l e s    ***

		public static int CPUID;          // 1=iAPX86,2=iAPX88,3=iAPX186,4=iAPX286
		// 5=iAPX386,6=iAPX486
		public static int NUMID;          // 1=iAPX87,2=iAPX287,3=iAPX387
		public static int OSID;           // 0=DOS,1=OS2,2=XENIX/286,3=XENIX/386,4=UNIX/386
		public static int CBIND;          // cNONE=0,cMS=1,cTURBO=2
		public static int CombAtr;   // 0:Normal, 1:Combined Sysinsert

		boolean TSTOFL;             // True:Insert test-on-overflow
		// %-E Boolean CHKSTK;             // True:Produce Call on E.CHKSTK

		public static int ntype;     // Number of data types defined
		//public static int T_INT,T_SINT; // Integer type mapping

		//%+S Range(0:P_max) PsysKind; --
//		public static int TypeLength; // Nbytes of last nonstandard InType
		public static int TagIdent;        // Tag-Ident from InXtag

		public static int nerr;        // Number of error messages until now
		public static int curline;   // Current source line number
		public static int curinstr;  // Current instr-byte read from scode
		// %-E Range(0:255) BNKLNK;        // >0: Prepare Produced code for BANKING
		public static float InitTime;              // Initiation CPU-time
		// %+S Boolean envpar;             // True: Parameters from environment
		public static boolean errormode;          // Treating an Error
		//%+A Boolean asmgen;             // ASM-output generating mode
		public static boolean InsideRoutine;      // Inside Routine Body indicator

		//    --- Dictionary ---
//		public static Dictionary DIC;
		public static String PRFXID;     // Prefix to entry popoblic static int symbols
		public static String PROGID;     // Ident of program being defined
		
		public static Map<String, Segment> SEGMAP;
		public static String CSEGID;     // Index of program's current code Segment
		//public static String DSEGID;     // Index of program's data Segment
		public static String LSEGID;     // Index of program's LineTable Segment
		public static String DumSEG;     // Index of the dummy Segment
		//public static String DGROUP;     // Index of the _DATA Segment
		public static String EnvCSEG;    // Index of environment's code Segment

		//public static DataSegment CSEGID;     // Index of program's current code Segment
		public static DataSegment DSEGID;     // Index of program's data Segment
		//public static DataSegment LSEGID;     // Index of program's LineTable Segment
		//public static DataSegment DumSEG;     // Index of the dummy Segment
		public static DataSegment DGROUP;     // Index of the _DATA Segment
		//public static DataSegment EnvCSEG;    // Index of environment's code Segment

		// SLIK ER DET I BEC:
//		Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
//		Global.TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
//		Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
//		Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
		
		public static String SCODID;     // Name of Scode input file
		public static String ATTRID;     // Prefix to attribute file
		public static String RELID;      // Name of RELocatable object Output file
		public static String SCRID;      // Name of rel/asm scratch file
		public static String ASMID;      // Name of assembly source output file
		public static String ProgIdent;  // S-Code PROG String
		public static String modident;   // Ident of module being defined
		public static String modcheck;   // Check code of module being defined
		public static String DsegEntry;  // Data Segment start symbol
		
		public static final String sysInsertDir = "C:/SPORT/RTS/";
		public static String outputDIR = sysInsertDir; // Attributes and SVM-Code output directory
		
		public static String CSEGNAM,DSEGNAM;
		public static int IfDepth;

		//    ---   Entry Points ---
		public static MemAddr MainEntry; // Main program's entry-point
		public static MemAddr LtabEntry; // Line-no-table's entry-point
		public static MemAddr X_OSSTAT;  // Entry-popoblic static int of G.OSSTAT
		public static MemAddr X_KNLAUX;  // Entry-popoblic static int of G.KNLAUX
		public static MemAddr X_STATUS;  // Entry-popoblic static int of G.STATUS
		public static MemAddr X_STMFLG;  // Entry-popoblic static int of G.STMFLG
		public static MemAddr X_ECASE;   // Entry-popoblic static int of ECASE routine
		// %+S MemAddr X_INITO;   // Entry-popoblic static int of INITO routine
		// %+S MemAddr X_GETO;    // Entry-popoblic static int of GETO routine
		// %+S MemAddr X_SETO;    // Entry-popoblic static int of SETO routine
		public static MemAddr X_SSTAT;   // Entry-popoblic static int of XENIX get errno rut.

		public static MemAddr TMPAREA;   // Temp area
		public static MemAddr TMPQNT;    // Temp quant area (of RTS)
		public static MemAddr X_INITSP;  // Entry-popoblic static int of G.INITSP


		BSEG curseg;           // Current program BSEG
		BSEG FreeSeg;          // Free program BSEG list
		public static int nSubSeg;   // No.of sub-segments gen. by BSEG

		public static int MXXERR;   // Max number of errors
		// %+S Range(0:10) SYSGEN;   //  System generation
		                             //      0: User program
		                             //      1: Generation of Runtime System
		                             //      2: Generation of S-Compiler
		                             //      3: Generation of Environment
		                             //      4: Generation of Library

		public static int SEGLIM; // Max seg-size befor segment-split
		public static int QBFLIM;   // No.of Q-instr in buf before Exhaust Half
		public static int RNGCHK;      // >0: produce Range --> char range check
		public static int IDXCHK;      // >0: produce array index check
		public static int LINTAB;      // 0:No-LineTab, else:Generate LineTab
		public static int DEBMOD;      // >2:Generate line breaks, else: do not!

		public static int LabelSequ; // No.of labels  created by 'NewLabno'
		public static int SymbSequ;  // No.of symbols created by 'NewPubl'

		public static int SK1LIN;    //  S-Compiler-Trace - Pass 1 starting line
		public static int SK1TRC;    //  Pass 1 Trace level=SEOMTI (one digit each)
		public static int SK2LIN;    //  S-Compiler-Trace - Pass 2 starting line
		public static int SK2TRC;    //  Pass 2 Trace level=SEOMTI (one digit each)
		                                 //   I = 0..9 Input trace level
		                                 //   T = 0..9 Trace-mode level
		                                 //   M = 0..9 Module IO trace level
		                                 //   O = 0..9 Output trace level
		                                 //   E = 0..9 Output trace level listq1
		                                 //   S = 0..9 Output trace level listq2

		public static int nTag;         // No.of tags defined
		
		public static int nFix;         // No.of Fixups defined
		public static Array<Fixup> FIXTAB;
		
		
		public static int nXsmb;        // Size of SMBMAP
		public static Qpkt[] DESTAB = new Qpkt[255];         // Jump Destination table
		public static Qpkt DESTAB256;           // ???? TEMP ????
		public static Qpkt[] FWRTAB = new Qpkt[255];         // Extra Jump Destination table
		public static Qpkt FWRTAB256;           // ???? TEMP ????
		public static Qpkt xFJUMP;              //--- see parse/coder (gqrelation)
		
		public static WordBlock[] TIDTAB; // = new WordBlock[MxpTag]; // Tag-Identifier table
		//public static FixBlock[] FIXTAB;// = new FixBlock[MxpFix];  // FIXUP table
		//public static WordBlock[] TAGTAB = new WordBlock[MxpXtag]; // Tag Table (during Module I/O)
		//public static WordBlock[] TYPMAP = new WordBlock[MxpXtyp]; // Type-mapping (during Module I/O)
		public static WordBlock[] SMBMAP;// = new WordBlock[MxpXsmb]; // Symbol-mapping (during Module I/O)

//		public static WordBlock[] TIDTAB = new WordBlock[MxpTag]; // Tag-Identifier table
//		public static FixBlock[] FIXTAB = new FixBlock[MxpFix];  // FIXUP table
		
		
//		public static Array<Integer> TYPMAP; // Type-mapping (during Module I/O)
//		public static WordBlock[] SMBMAP = new WordBlock[MxpXsmb]; // Symbol-mapping (during Module I/O)

		public static int NXTYPE; // Type of First/Next buffer
		public static int NXTLNG;      // Length of First/Next buffer
		public static SCodeBuffer SBUF; // S-Code Buffer
		public static ICodeBuffer CBUF; // Code Buffer
		public static RelocBuffer CREL; // Code Relocations
		public static ICodeBuffer DBUF; // Data Buffer
		public static RelocBuffer DREL; // Data Relocations


	 //------ SIB-register packing in Operand ------
	 //
	 //     ireg=iESP                 ==>  no index register
	 //     breg=ireg & breg <> bESP  ==>  no base register
	 //     It is impossible to specify same base and index register.
	 public final static int iEAX=0, bEAX=0;        //   0=00 000 000   0=00 000 000
	 public final static int iECX=8, bECX=1;        //   8=00 001 000   1=00 000 001
	 public final static int iEDX=16,bEDX=2;        //  16=00 010 000   2=00 000 010
	 public final static int iEBX=24,bEBX=3;        //  24=00 011 000   3=00 000 011
	 public final static int iESP=32,bESP=4;        //  32=00 100 000   4=00 000 100
	 public final static int iEBP=40,bEBP=5;        //  40=00 101 000   5=00 000 101
	 public final static int iESI=48,bESI=6;        //  48=00 110 000   6=00 000 110
	 public final static int iEDI=56,bEDI=7;        //  56=00 111 000   7=00 000 111
	 public final static int ssMASK=192;            // 192=11 000 000
	 public final static int IndxREG=56,BaseREG=7;  //  56=00 111 000   7=00 000 111
	 public final static int NoIBREG=228;           // 228=11 100 100   IS RULED OUT.
	 public final static int NoIREG=32;             //  32=00 100 000   IS RULED OUT.

				


				 
				 
				// %title ***    Q  -  I  N  S  T  R  U  C  T  I  O  N  S    ***

				//-- Instruction value classification codes ---
				    public final static int cANY=0; // Don't know
				    public final static int cVAL=1; // Value // integer, character, boolean, real etc.
				    public final static int cOBJ=2; // Pure Object Address (segment and/or base)
				    public final static int cSTP=3; // Address into Stack (SP etc.)
				    public final static int cADR=4; // Address otherwise (NOT pure object)
				    public final static int cMAX=4;

				// Visible const range(0:cMAX) cTYPE(18) = // T_MAX+1
				// (  cANY,cVAL,cVAL,cVAL,cVAL,cVAL,cVAL, cVAL, cVAL,cVAL,cVAL,cOBJ, cVAL, cADR,
				// // VOID,WRD4,WRD2,BYT2,BYT1,REAL,LREAL,TREAL,BOOL,CHAR,SIZE,OADDR,AADDR,GADDR,
				//    cANY, cVAL, cVAL, cANY );
				// // PADDR,RADDR,NPADR,NOINF;
				public static int cTYPE[] = {
				   cANY,cVAL,cVAL,cVAL,cVAL,cVAL,cVAL, cVAL, cVAL,cVAL,cVAL,cOBJ, cVAL, cADR,
				// VOID,WRD4,WRD2,BYT2,BYT1,REAL,LREAL,TREAL,BOOL,CHAR,SIZE,OADDR,AADDR,GADDR,
				   cANY, cVAL, cVAL, cANY };
				// PADDR,RADDR,NPADR,NOINF;


//				%title ***   T h e   E n v i r o n m e n t   I n t e r f a c e   ***
				public static int status; // system "STATUS";

//				Record string; info "TYPE";
//				begin name(character) chradr; integer nchr; end;
//
//				---   E r r o r   H a n d l i n g   ---
//
//				global profile Perhandl;
//				import range(0:13) code;
//				       infix(string) msg;
//				       label addr;
//				export label cont end;
//
//				global profile Perror;
//				import infix(string) msg end;
//
//				Entry(Perror) Erroutine;
				
				



				//%title ***   S t r u c t u r e d    T y p e s   ***

				//------   W O R D  /  D W O R D  /  Q W O R D   ------

//				Record WORD; info "TYPE";
//				begin variant public static int val;
//				      variant range(0:MaxByte) LO,HI;
//				end;
//
//				Record DWORD; info "TYPE";
//				begin variant integer          val;
//				      variant range(0:MaxByte) LO,LOHI,HILO,HI;
//				      variant infix(WORD) LowWord;
//				              infix(WORD) HighWord;
//				end;
//
//				Record wWORD; info "TYPE";
//				begin
//				%-E   variant public static int val;
//				  variant integer          val;
//				%-E   variant range(0:MaxByte) LO,HI;
//				  variant range(0:MaxByte) LO,LOHI,HILO,HI;
//				      variant infix(WORD) LowWord;
//				          infix(WORD) HighWord;
//				end;

				//------   V  a  l  u  e     I  t  e  m   ------



				// Record File;
				// begin range(0:MaxKey) key;     --- File key
				//       range(0:132) pos;        --- Posision indicatior (0..nchr-1)
				//       range(0:MaxByte) nchr;
				//    //   character chr(0);        --- File buffer
				// end;

				//------  R e l o c a t i o n    P a c k e t s  ------

				// mrk = <0>1<FieldType>3<RelType>2<Offset>10
				// FieldType:
				public final static int fPOINTER=3,  mfPOINTER=12288;  // 3000H Segm-relative
				public final static int fSEGMENT=2,  mfSEGMENT=8192;   // 2000H Segm-relative
				public final static int fOFFSET=1,   mfOFFSET=4096;    // 1000H Segm-relative
				public final static int fOFST32=5,   mfOFST32=20480;   // 5000H Segm-relative
				public final static int fFULLDISP=4, mfFULLDISP=16384; // 4000H Self-relative
				public final static int fBYTEDISP=0, mfBYTEDISP=0;     // 0000H Self-relative
				// RelType:
				public final static int rSEG=0,mrSEG=0;                // 0000H Segment base
				public final static int rEXT=1,mrEXT=1024;             // 0400H External
				public final static int rFIX=2,mrFIX=2048;             // 0800H Fixup



				// %+F Record CoffRelocPkt;
				// %+F begin integer vAddr;
				// %+F       integer SymNdx;
				// %+F       public static int Type;  // Relocation Type
				// %+F end;

				// %+F     public final static int R_ABS=0;       // No Relocation
				// %+F %-E public final static int R_REL16=1;     // Self-relative 16-bit Relocation
				// %+F %-E public final static int R_OFF8=7;      // Segment relative 8-bit Offset
				// %+F %-E public final static int R_OFF16=8;     // Segment relative 16-bit Offset
				// %+F %-E public final static int R_SEG12=9;     // Segment relative 16-bit Selector
				// %+FE    public final static int R_DIR32=6;     // Segment relative 32-bit Relocation
				// %+FE    public final static int R_PCRLONG=20;  // Self-relative 32-bit Relocation

				// %+F Record RelocObj;
				// %+F begin ref(RelocObj) next;
				// %+F       infix(CoffRelocPkt) Cpkt;
				// %+F end;


				// %title ***   D y n a m i c     O b j e c t s    ***

				// Record Object;
				// begin range(0:MaxByte) kind;   --- Object kind code
				//       range(0:MaxType) type; 
				// end;

				// Record FreeObject:Object;
				// begin ref(Object) next end;    --- Free list pointer

				// Record FreeArea;
				// begin ref(FreeArea) next;      --- Free list pointer
				//       size PoolSize;
				// end;

				// Record PoolSpec; info "TYPE";
				// begin ref() PoolTop;
				//       size PoolSize;
				// end;

				// ---------------------------------------------------------
				// ---       M  o  d  u  l  e     H  e  a  d  e  r       ---
				// ---------------------------------------------------------

				// Record ModuleHeader;
				// begin public static int Magic;  // Magic number
				//       range(0:MaxByte) Layout; // File Layout number
				//       range(0:MaxByte) Comb;   // 0:Normal, 1:Combined
				//       Infix(WORD) modid;       // Module's identification
				//       Infix(WORD) check;       // Module's check code
				//       public static int nXtag;  // No.of external tags
				//       public static int nType;  // No.of Types
				//       public static int nSymb;  // No.of Symboles
				//       public static int sDesc;  // Size of Descriptor area
				//       public static int sFeca;  // Size of FEC-Attribute area
				//       public static int nTmap;  // 0:No TAGMAP, no.of tags in TAGMAP
				//       integer DescLoc;         // Fileloc: Descriptor area
				//       integer TypeLoc;         // Fileloc: Type table
				//       integer TgidLoc;         // Fileloc: Tagid table
				//       integer SymbLoc;         // Fileloc: Symbol Table
				//       integer FecaLoc;         // Fileloc: FEC-Attributes
				//       integer TmapLoc;         // Fileloc: TAGMAP table
				// end;


				//--- Current Location Counter Data ---
				public static Qpkt qfirst,qlast;   // initial(none) q-list of CSEG
				public static int qcount;    // initial(0), No.of Qpkt's in q-list
				//------------------------------------------------------------------------

				public static boolean reversed;          // whether compare exchanged operands
				public static boolean InMassage;         // True while in massage
				public static boolean deadCode;          // inital(false)
				public static boolean Changeable;        // Changereg in massage
				public static int RecDepth;     // Current Masseur recursion depth left
				public static int stackMod1, stackMod2; // see massage
				public static boolean deleteOK;          // see massage'peepExhaust

//
//
//				end;

}
