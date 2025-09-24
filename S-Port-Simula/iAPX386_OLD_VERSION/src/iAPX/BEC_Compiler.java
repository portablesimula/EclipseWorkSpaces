package iAPX;

import static iAPX.util.Global.*;

import java.util.HashMap;

import iAPX.dataAddress.Fixup;
import iAPX.descriptor.Descriptor;
import iAPX.enums.Kind;
import iAPX.parser.Parser;
import iAPX.util.Array;
import iAPX.util.Display;
import iAPX.util.Global;
import iAPX.util.Option;
import iAPX.util.Scode;
import iAPX.util.Util;
import svm.segment.DataSegment;
import svm.segment.Segment;

public class BEC_Compiler {

	public static void main(String[] argv) {
		Util.IERR("NOT IMPL");
	}
	
	public static void doCompile(String fileName) {
		INITIATE();
		
//		PARAM(argv);
		MINI_PARAM();
		SCODID = fileName;
		BEGPROG();
		Parser.MONITOR();
//		RELOUT;
	    IO.println("BEC_Compiler.doCompile: Metoden 'ENDPROG' MÅ KANSKJE SKRIVES");
//		ENDPROG;
//
//		TERMINATE; EnvTerm(3,"Internal Error");
//		Util.IERR("");
	}
	
	private static void INITIATE() {
		Display.init();
		Global.SEGMAP = new HashMap<String, Segment>();
		Global.FIXTAB = new Array<Fixup>();
	}

	
	private static void MINI_PARAM() {
//		begin infix(string) idn; range(0:MaxWord) n;
//		%+S   envpar = EnvGetIntInfo(1) = 0;  // ????????   Returns Status!=0 !!!!!!!!!
//		%+S   if status!=0 then status = 0; envpar = true endif;

//	%+S SYSGEN = 0;    //  System generation
	                    //      1: Generation of Runtime System
	                    //      2: Generation of S-Compiler
	                    //      3: Generation of Environment
	                    //      4: Generation of Library module
	// %+S   SEGLIM = 20000 //  Max seg-size befor segment-split
	// %+S   QBFLIM = 0;    //  No.of Q-instr in buff before Exhaust Half
	// %+S   RNGCHK = 0;    //  >0: produce integer --> char range check
	// %+S   IDXCHK = 1;    //  >0: produce array index check
	// %+S   LINTAB = 1;    //  >0: generate line info to LIN_CODE Segment
	// %+S   DEBMOD = 2;    //  >2: generate line break instructions
	// %+S   MXXERR = 200;  //  Max number of errors
	      SK1LIN = 0;    //  S-Compiler-Trace - Pass 1 starting line
	      SK1TRC = 0;    //  Pass 1 Trace level=SEOMTI (one digit each)
	      SK2LIN = 0;    //  S-Compiler-Trace - Pass 2 starting line
	      SK2TRC = 0;    //  Pass 2 Trace level=SEOMTI (one digit each)
	                    //      I = 0..9 Input trace level
	                    //      T = 0..9 Trace-mode level
	                    //      M = 0..9 Module input/output trace level
	                    //      O = 0..9 Output trace level  listsw
	                    //      E = 0..9 Output trace level  listq1
	                    //      S = 0..9 Output trace level  listq2
	// %+S   MASSLV = 255;  //  Massage level
	      Option.MASSDP = 25;   //  Massage recursion depth
	// %-E %+S BNKLNK = 0;    //  >0: Prepare Produced code for BANKING
	      CombAtr = 0;

//	      PRFXID.val = 0;  PROGID.val = 0;  SCODID.val = 0; CSEGNAM.val = 0; DSEGNAM.val = 0;
	      PRFXID = null;  PROGID = null;  SCODID = null; //CSEGNAM = null; DSEGNAM = null;
	// %+A   ASMID.val = 0;
	      ATTRID = null;  RELID = null;   SCRID = null;
	      //sMap.n = 0;

	      //MainEntry.kind = 0;  // Main program's entry-point
	      DsegEntry = null;    // Data Segment start address
	      nerr = 0;            // Number of error messages until now
	      curline = 0;         // Current source line number
	      LabelSequ = 0;       // No.of assembly labels created by 'NewLabno'
	      SymbSequ = 0;        // No.of symbols created by 'NewPubl'
	// %+S   CPUID = iAPX86;  NUMID = iAPX87; OSID = 0; TSTOFL = false;
	// %-E   doJUMPrel = false;
	      
	      // DefSinstr;

	// %+S   if envpar
	// %+S   then --- Parameters are taken from environment
	      Option.MASSLV = 30; //EnvGetIntInfo(6);
	      if(Option.MASSLV > 63) Option.MASSDP = Option.MASSLV-38;
	// %-E        doJUMPrel =  (MASSLV>=63);
	     
	// %-E        BNKLNK = EnvGetIntInfo(7)
	// %-E        if Status!=0 then Status = 0; BNKLNK = 0 endif;   --- TEMP ?????
	// %+S        SYSGEN = EnvGetIntInfo(8)

//	           QBFLIM = EnvGetIntInfo(9)     //  I.e.  STKLNG   Temp !??
//	           if QBFLIM=0 then QBFLIM = 32000 endif // imposs. large number
//
//	           MXXERR = EnvGetIntInfo(10)
//	           SK1LIN = EnvGetIntInfo(11)    SK1TRC = EnvGetIntInfo(12)
//	           SK2LIN = EnvGetIntInfo(13)    SK2TRC = EnvGetIntInfo(14)
//	           BECDEB = EnvGetIntInfo(22)
//	           LINTAB = EnvGetIntInfo(23)    RNGCHK = EnvGetIntInfo(24)
//	           DEBMOD = EnvGetIntInfo(30)    IDXCHK = EnvGetIntInfo(25)
//	           TSTOFL = EnvGetIntInfo(26)<>0
//	           CPUID = EnvGetIntInfo(28)
//	           NUMID = EnvGetIntInfo(29)
//	           OSID = EnvGetIntInfo(31)
//	           CBIND = EnvGetIntInfo(27)
//	           if Status!=0 then CBIND = 0; Status = 0 endif;
//	           SEGLIM = EnvGetIntInfo(2)
	        		   
	// %-E        CHKSTK = EnvGetIntInfo(32)<>0;
	// ?????   edtextinfo(sysedit,14); idn = pickup(sysedit);
	// ?????   if idn.nchr > 0 then PROGID = DefSymb(idn) endif;
	           
	           // edtextinfo(sysedit,5); idn = pickup(sysedit);
	           // if idn.nchr!=0 then RELID = DefSymb(idn) endif;
	           RELID = "RELID";
	           
	//%          edtextinfo(sysedit,8); idn = pickup(sysedit);
	//%          if idn.nchr!=0 then PRFXID = DefSymb(idn) endif;
	           
	           // edtextinfo(sysedit,8); edchar(sysedit,'@');
	           // idn = pickup(sysedit);
	           // if idn.nchr > 1 then PRFXID = DefSymb(idn) endif;
	           PRFXID = "PRFXID";
	           
	           // edtextinfo(sysedit,9); idn = pickup(sysedit);
	           // if idn.nchr!=0 then CSEGNAM = DefSymb(idn) endif;
	           CSEGNAM = "CSEGNAM";
	           
	           // edtextinfo(sysedit,10); idn = pickup(sysedit);
	           // if idn.nchr!=0 then DSEGNAM = DefSymb(idn) endif;
	           // status = 0;
	           DSEGNAM = "DSEGNAM";

//	%+A        edtextinfo(sysedit,6);
//	%+A        if sysedit.pos!=0
//	%+A        then idn = pickup(sysedit);
//	%+A             n = idn.nchr; repeat while n<>0
//	%+A             do n = n-1;
//	%+A                if var(idn.chradr)(n)='.' then idn.nchr = n; n = 0 endif
//	%+A             endrepeat;
//	%+A             ed(sysedit,idn); ed(sysedit,".asm"); idn = pickup(sysedit);
//	%+A             ASMID = DefSymb(idn);
//	%+AD            if TLIST!=0
//	%+AD            then outstring("ASSEMBLY SOURCE OUTPUT: ");
//	%+AD                 outstring(idn); outimage;
//	%+AD            endif;
//	%+A        endif;

	// %+S   else --- Parameters are taken from sysin
	// %+SD       outstring("S-Compiler iAPX/109 "); eddate(sysout); outimage;
	// %+S        outstring("Enter Parameters:"); outimage; inimage(sysin);
	// %+S        repeat idn = InItem(sysin); while not STEQ(idn,"END")
	// %+S        do    if STEQ(idn,"ENVPAR:") then envpar = ParValue(sysin) = 0
	// %+SD          elsif STEQ(idn,"TLIST:")  then TLIST = ParValue(sysin)
	// %+S           elsif STEQ(idn,"SYSGEN:") then SYSGEN = ParValue(sysin)
	// %+S           elsif STEQ(idn,"SEGLIM:") then SEGLIM = ParValue(sysin)
	// %+S           elsif STEQ(idn,"QBFLIM:") then QBFLIM = ParValue(sysin)
	// %+S           elsif STEQ(idn,"CPUID:")  then CPUID = ParValue(sysin)
	// %+S           elsif STEQ(idn,"NUMID:")  then NUMID = ParValue(sysin)
	// %+S           elsif STEQ(idn,"OSID:")   then OSID = ParValue(sysin)
	// %+S           elsif STEQ(idn,"TSTOFL:") then TSTOFL = ParValue(sysin)>0
	// %+S           elsif STEQ(idn,"LINTAB:") then LINTAB = ParValue(sysin)
	// %+S           elsif STEQ(idn,"DEBMOD:") then DEBMOD = ParValue(sysin)
	// %-E %+S       elsif STEQ(idn,"CHKSTK:") then CHKSTK = ParValue(sysin)>0
	// %+S           elsif STEQ(idn,"MAXERR:") then MXXERR = ParValue(sysin)
	// %+SD          elsif STEQ(idn,"SK1LIN:") then SK1LIN = ParValue(sysin)
	// %+SD          elsif STEQ(idn,"SK1TRC:") then SK1TRC = ParValue(sysin)
	// %+SD          elsif STEQ(idn,"SK2LIN:") then SK2LIN = ParValue(sysin)
	// %+SD          elsif STEQ(idn,"SK2TRC:") then SK2TRC = ParValue(sysin)
	// %+SD          elsif STEQ(idn,"BECDEB:") then BECDEB = ParValue(sysin)
	// %+S           elsif STEQ(idn,"RNGCHK:") then RNGCHK = ParValue(sysin)
	// %+S           elsif STEQ(idn,"IDXCHK:") then IDXCHK = ParValue(sysin)
	// %+S           elsif STEQ(idn,"MASSLV:") then MASSLV = ParValue(sysin)

	// %+S           elsif STEQ(idn,"PRFXID:") then PRFXID = ParText(sysin)
	// %+S           elsif STEQ(idn,"PROGID:") then PROGID = ParText(sysin)
	// %+S           elsif STEQ(idn,"CSEG:")   then CSEGNAM = ParText(sysin)
	// %+S           elsif STEQ(idn,"DSEG:")   then DSEGNAM = ParText(sysin)
	// %+S           elsif STEQ(idn,"SCODE:")  then SCODID = ParText(sysin)
	// %+S           elsif STEQ(idn,"ATTR:")   then ATTRID = ParText(sysin)
	// %+S           elsif STEQ(idn,"OUTPUT:") then RELID = ParText(sysin)
	// %+SA          elsif STEQ(idn,"ASMOUT:") then ASMID = ParText(sysin)
	// %+S           elsif STEQ(idn,"SCR:")    then SCRID = ParText(sysin)
	// %+S           elsif STEQ(idn,"END")    then goto E1;
	// %+S           else ed(errmsg,idn); ERROR("Illegal Parameter: ") endif;
	// %+S        endrepeat;
	// %+S   endif;
	// %+S E1:
	}

	
//	private static void PARAM(String[] argv) {
////		begin infix(string) idn; range(0:MaxWord) n;
////		%+S   envpar = EnvGetIntInfo(1) = 0;  // ????????   Returns Status!=0 !!!!!!!!!
////		%+S   if status!=0 then status = 0; envpar = true endif;
//
//		TLIST = 0;     //  D - Major Event Trace of S-Compiler
//		BECDEB = 0;    //  Debuging level (0: skip all debug info)
////	%+S SYSGEN = 0;    //  System generation
//	                    //      1: Generation of Runtime System
//	                    //      2: Generation of S-Compiler
//	                    //      3: Generation of Environment
//	                    //      4: Generation of Library module
//	// %+S   SEGLIM = 20000 //  Max seg-size befor segment-split
//	// %+S   QBFLIM = 0;    //  No.of Q-instr in buff before Exhaust Half
//	// %+S   RNGCHK = 0;    //  >0: produce integer --> char range check
//	// %+S   IDXCHK = 1;    //  >0: produce array index check
//	// %+S   LINTAB = 1;    //  >0: generate line info to LIN_CODE Segment
//	// %+S   DEBMOD = 2;    //  >2: generate line break instructions
//	// %+S   MXXERR = 200;  //  Max number of errors
//	      SK1LIN = 0;    //  S-Compiler-Trace - Pass 1 starting line
//	      SK1TRC = 0;    //  Pass 1 Trace level=SEOMTI (one digit each)
//	      SK2LIN = 0;    //  S-Compiler-Trace - Pass 2 starting line
//	      SK2TRC = 0;    //  Pass 2 Trace level=SEOMTI (one digit each)
//	                    //      I = 0..9 Input trace level
//	                    //      T = 0..9 Trace-mode level
//	                    //      M = 0..9 Module input/output trace level
//	                    //      O = 0..9 Output trace level  listsw
//	                    //      E = 0..9 Output trace level  listq1
//	                    //      S = 0..9 Output trace level  listq2
//	// %+S   MASSLV = 255;  //  Massage level
//	      MASSDP = 25;   //  Massage recursion depth
//	// %-E %+S BNKLNK = 0;    //  >0: Prepare Produced code for BANKING
//	      CombAtr = 0;
//
////	      PRFXID.val = 0;  PROGID.val = 0;  SCODID.val = 0; CSEGNAM.val = 0; DSEGNAM.val = 0;
//	      PRFXID = null;  PROGID = null;  SCODID = null; //CSEGNAM = null; DSEGNAM = null;
//	// %+A   ASMID.val = 0;
//	      ATTRID = null;  RELID = null;   SCRID = null;   sMap.n = 0;
//
//	      MainEntry.kind = 0;  // Main program's entry-point
//	      DsegEntry.val  = 0;  // Data Segment start address
//	      nerr = 0;            // Number of error messages until now
//	      curline = 0;         // Current source line number
//	      LabelSequ = 0;       // No.of assembly labels created by 'NewLabno'
//	      SymbSequ = 0;        // No.of symbols created by 'NewPubl'
//	// %+S   CPUID = iAPX86;  NUMID = iAPX87; OSID = 0; TSTOFL = false;
//	// %-E   doJUMPrel = false;
//	      DefSinstr;
//
//	// %+S   if envpar
//	// %+S   then --- Parameters are taken from environment
//	           MASSLV = EnvGetIntInfo(6);
//	           if MASSLV > 63 then MASSDP = MASSLV-38 endif
//	// %-E        doJUMPrel =  (MASSLV>=63);
//	     
//	           TLIST = EnvGetIntInfo(3)
//	// %-E        BNKLNK = EnvGetIntInfo(7)
//	// %-E        if Status!=0 then Status = 0; BNKLNK = 0 endif;   --- TEMP ?????
//	// %+S        SYSGEN = EnvGetIntInfo(8)
//
////	           QBFLIM = EnvGetIntInfo(9)     //  I.e.  STKLNG   Temp !??
////	           if QBFLIM=0 then QBFLIM = 32000 endif // imposs. large number
////
////	           MXXERR = EnvGetIntInfo(10)
////	           SK1LIN = EnvGetIntInfo(11)    SK1TRC = EnvGetIntInfo(12)
////	           SK2LIN = EnvGetIntInfo(13)    SK2TRC = EnvGetIntInfo(14)
////	           BECDEB = EnvGetIntInfo(22)
////	           LINTAB = EnvGetIntInfo(23)    RNGCHK = EnvGetIntInfo(24)
////	           DEBMOD = EnvGetIntInfo(30)    IDXCHK = EnvGetIntInfo(25)
////	           TSTOFL = EnvGetIntInfo(26)<>0
////	           CPUID = EnvGetIntInfo(28)
////	           NUMID = EnvGetIntInfo(29)
////	           OSID = EnvGetIntInfo(31)
////	           CBIND = EnvGetIntInfo(27)
////	           if Status!=0 then CBIND = 0; Status = 0 endif;
////	           SEGLIM = EnvGetIntInfo(2)
//	        		   
//	// %-E        CHKSTK = EnvGetIntInfo(32)<>0;
//	// ?????   edtextinfo(sysedit,14); idn = pickup(sysedit);
//	// ?????   if idn.nchr > 0 then PROGID = DefSymb(idn) endif;
//	           edtextinfo(sysedit,5); idn = pickup(sysedit);
//	           if idn.nchr!=0 then RELID = DefSymb(idn) endif;
//	//%          edtextinfo(sysedit,8); idn = pickup(sysedit);
//	//%          if idn.nchr!=0 then PRFXID = DefSymb(idn) endif;
//	           edtextinfo(sysedit,8); edchar(sysedit,'@');
//	           idn = pickup(sysedit);
//	           if idn.nchr > 1 then PRFXID = DefSymb(idn) endif;
//	           edtextinfo(sysedit,9); idn = pickup(sysedit);
//	           if idn.nchr!=0 then CSEGNAM = DefSymb(idn) endif;
//	           edtextinfo(sysedit,10); idn = pickup(sysedit);
//	           if idn.nchr!=0 then DSEGNAM = DefSymb(idn) endif;
//	           status = 0;
//
//	           if TLIST!=0
//	           then outstring("S-Compiler iAPX/109 ");
//	                eddate(sysout); outimage;
//	           endif;
//
//	%+A        edtextinfo(sysedit,6);
//	%+A        if sysedit.pos!=0
//	%+A        then idn = pickup(sysedit);
//	%+A             n = idn.nchr; repeat while n<>0
//	%+A             do n = n-1;
//	%+A                if var(idn.chradr)(n)='.' then idn.nchr = n; n = 0 endif
//	%+A             endrepeat;
//	%+A             ed(sysedit,idn); ed(sysedit,".asm"); idn = pickup(sysedit);
//	%+A             ASMID = DefSymb(idn);
//	%+AD            if TLIST!=0
//	%+AD            then outstring("ASSEMBLY SOURCE OUTPUT: ");
//	%+AD                 outstring(idn); outimage;
//	%+AD            endif;
//	%+A        endif;
//
//	// %+S   else --- Parameters are taken from sysin
//	// %+SD       outstring("S-Compiler iAPX/109 "); eddate(sysout); outimage;
//	// %+S        outstring("Enter Parameters:"); outimage; inimage(sysin);
//	// %+S        repeat idn = InItem(sysin); while not STEQ(idn,"END")
//	// %+S        do    if STEQ(idn,"ENVPAR:") then envpar = ParValue(sysin) = 0
//	// %+SD          elsif STEQ(idn,"TLIST:")  then TLIST = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"SYSGEN:") then SYSGEN = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"SEGLIM:") then SEGLIM = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"QBFLIM:") then QBFLIM = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"CPUID:")  then CPUID = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"NUMID:")  then NUMID = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"OSID:")   then OSID = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"TSTOFL:") then TSTOFL = ParValue(sysin)>0
//	// %+S           elsif STEQ(idn,"LINTAB:") then LINTAB = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"DEBMOD:") then DEBMOD = ParValue(sysin)
//	// %-E %+S       elsif STEQ(idn,"CHKSTK:") then CHKSTK = ParValue(sysin)>0
//	// %+S           elsif STEQ(idn,"MAXERR:") then MXXERR = ParValue(sysin)
//	// %+SD          elsif STEQ(idn,"SK1LIN:") then SK1LIN = ParValue(sysin)
//	// %+SD          elsif STEQ(idn,"SK1TRC:") then SK1TRC = ParValue(sysin)
//	// %+SD          elsif STEQ(idn,"SK2LIN:") then SK2LIN = ParValue(sysin)
//	// %+SD          elsif STEQ(idn,"SK2TRC:") then SK2TRC = ParValue(sysin)
//	// %+SD          elsif STEQ(idn,"BECDEB:") then BECDEB = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"RNGCHK:") then RNGCHK = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"IDXCHK:") then IDXCHK = ParValue(sysin)
//	// %+S           elsif STEQ(idn,"MASSLV:") then MASSLV = ParValue(sysin)
//
//	// %+S           elsif STEQ(idn,"PRFXID:") then PRFXID = ParText(sysin)
//	// %+S           elsif STEQ(idn,"PROGID:") then PROGID = ParText(sysin)
//	// %+S           elsif STEQ(idn,"CSEG:")   then CSEGNAM = ParText(sysin)
//	// %+S           elsif STEQ(idn,"DSEG:")   then DSEGNAM = ParText(sysin)
//	// %+S           elsif STEQ(idn,"SCODE:")  then SCODID = ParText(sysin)
//	// %+S           elsif STEQ(idn,"ATTR:")   then ATTRID = ParText(sysin)
//	// %+S           elsif STEQ(idn,"OUTPUT:") then RELID = ParText(sysin)
//	// %+SA          elsif STEQ(idn,"ASMOUT:") then ASMID = ParText(sysin)
//	// %+S           elsif STEQ(idn,"SCR:")    then SCRID = ParText(sysin)
//	// %+S           elsif STEQ(idn,"END")    then goto E1;
//	// %+S           else ed(errmsg,idn); ERROR("Illegal Parameter: ") endif;
//	// %+S        endrepeat;
//	// %+S   endif;
//	// %+S E1:
//	end;

	private static void BEGPROG() {
//		begin range(0:MaxWord) i; infix(string) action;
//		%+D   integer trc,txx; 

		//  Create and open scode (inbytefile=5) ---
//		%+S   if envpar then
//		edtextinfo(sysedit,4);
//		%+S   else if SCODID.val = 0
//				%+S        then ed(sysedit,"INPUT.SCD")
//				%+S        else EdSymb(sysedit,SCODID) endif;
//		%+S   endif;
//		----  action:="!0!!1!!1!!0!!1!!2!!0!!0!!0!!8!NOBUFFER!0!";  -- in(byte)file
//		action:=
//		"!0!!1!!1!!0!!1!!2!!0!!0!!0!!8!!78!!79!!66!!85!!70!!70!!69!!82!!0!";
//		scode:=Open(pickup(sysedit),5,action,0); InitScode;
		Scode.initScode();

//	      --- Create and Open Relocatable/Assembly Scratch File
//	%+S   if envpar then
//	           edtextinfo(sysedit,7);
//	%+S   else if SCRID.val = 0
//	%+S        then ed(sysedit,"ICODE.TMP")
//	%+S        else EdSymb(sysedit,SCRID) endif;
//	%+S   endif;
//	      action:="!1!!1!!2!!1!!1!!2!!0!!0!!0!!0!";  -- out(byte)file
//	%+A   if ASMID.val = 0
//	%+A   then asmgen:=false;
//	           scrfile:=Open(pickup(sysedit),6,action,0);
//	%+A   else asmgen:=true; scrfile:=Open(pickup(sysedit),2,action,0) endif;
//
//	      CurInstr:=0; modinpt:=0; modoupt:=0; objfile:=0;
//
//	      ---  Clear all free object lists ---
//	      FreePool:=none;
//	      i:=0; repeat while i<K_Max
//	      do FreeObj(i):=none;
//	%+D      ObjCount(i):=0;
//	         i:=i+1
//	      endrepeat
//
//	%+S   if    SYSGEN=1
//	%+S   then outimage; outstring("** RTS Generating-mode **");
//	%+S        outimage;
//	%+S        if PRFXID.val=0 then PRFXID:=DefSymb("R@") endif;
//	%+S   elsif SYSGEN=2
//	%+S   then outimage; outstring("** S-Compiler Generating-mode **");
//	%+S        outimage;
//	%+S        if PRFXID.val=0 then PRFXID:=DefSymb("A@") endif;
//	%+S   elsif SYSGEN=3
//	%+S   then outimage; outstring("** Environment Generating-mode **")
//	%+S        outimage;
//	%+S        if PRFXID.val=0 then PRFXID:=DefSymb("E@") endif;
//	%+S   elsif SYSGEN=4
//	%+S   then outimage; outstring("** Library Generating-mode **")
//	%+S        outimage;
//	%+S   elsif PRFXID.val=0 then PRFXID:=DefSymb("S@") endif;
//	%-S      if PRFXID.val=0 then PRFXID:=DefSymb("S@") endif;
		
//	      DataType.initBaseTypes();
	      
//	      ntype = Type.T_max();

	      IO.println("BEC_Compiler.BEGPROG: DETTE MÅ RETTES FØR SEGMENTER KAN BRUKES !");
	      Thread.dumpStack();
//	      // Create System Segments
//	      DumSEG  = DefSegm("NOTHING",aCODE);
//	      DGROUP  = DefSegm("_DATA",aDGRP);
			DGROUP = new DataSegment("DSEG_" + PROGID, Kind.K_SEG_DATA);
//	      EnvCSEG = DefSegm("S@ENV_TEXT",aCODE);
//	      PutSegx(DGROUP);
			DSEGID = DGROUP;
			// SLIK ER DET I BEC:
//			Global.CSEG = new DataSegment("CSEG_" + sourceID, Kind.K_SEG_CONST);
//			Global.TSEG = new DataSegment("TSEG_" + sourceID, Kind.K_SEG_CONST);
//			Global.DSEG = new DataSegment("DSEG_" + sourceID, Kind.K_SEG_DATA);
//			Global.PSEG = new ProgramSegment("PSEG_" + sourceID, Kind.K_SEG_CODE);
	      

//	%+F   if (OSID=oUNIX386) or (OSID=oUNIX386W)
//	%+F   then X_OSSTAT := NewExtAdr(DefExtr("G@OSSTAT",DGROUP));
//	%+F        X_KNLAUX := NewExtAdr(DefExtr("G@KNLAUX",DGROUP));
//	%+F        X_STATUS := NewExtAdr(DefExtr("G@STATUS",DGROUP));
//	%+F        X_STMFLG := NewExtAdr(DefExtr("G@STMFLG",DGROUP));
//	%+F %-E    X_IMUL   := NewExtAdr(DefExtr("E@IMUL",EnvCSEG));
//	%+F %-E    X_IDIV   := NewExtAdr(DefExtr("E@IDIV",EnvCSEG));
//	%+F %-E    X_IREM   := NewExtAdr(DefExtr("E@IREM",EnvCSEG));
//	%+F %-E    X_GOTO   := NewExtAdr(DefExtr("E@GOTO",EnvCSEG));
//	%+F %-E    X_CALL   := NewExtAdr(DefExtr("E@CALL",EnvCSEG));
//	%+F %+D    X_ECASE  := NewExtAdr(DefExtr("E@ECASE",EnvCSEG));
//	%+F %+S    X_INITO  := NewExtAdr(DefExtr("E@INITO",EnvCSEG));
//	%+F %+S    X_GETO   := NewExtAdr(DefExtr("E@GETO",EnvCSEG));
//	%+F %+S    X_SETO   := NewExtAdr(DefExtr("E@SETO",EnvCSEG));
//	%+F %-E    X_CHKSTK := NewExtAdr(DefExtr("E@CHKSTK",EnvCSEG));
//	%+FSC %-E  X_STKBEG := NewExtAdr(DefExtr("G@STKBEG",DGROUP));
//	%+FSC %-E  X_STKOFL := NewExtAdr(DefExtr("E@STKOFL",EnvCSEG));
//	%+F        X_SSTAT  := NewExtAdr(DefExtr("E@SSTAT",EnvCSEG));
//	%+F        TMPAREA  := NewRelxAdr(DefExtr("G@TMP8687",DGROUP));
//	%+F        TMPQNT   := NewRelxAdr(DefExtr("G@TMPQNT",DGROUP));
//	%+F        X_INITSP := NewRelxAdr(DefExtr("G@INITSP",DGROUP));
//	%+F   else
//	           X_OSSTAT := NewExtAdr(DefExtr("G@OSSTAT",DGROUP));
//	           X_KNLAUX := NewExtAdr(DefExtr("G@KNLAUX",DGROUP));
//	           X_STATUS := NewExtAdr(DefExtr("G@STATUS",DGROUP));
//	           X_STMFLG := NewExtAdr(DefExtr("G@STMFLG",DGROUP));
//	%-E        X_IMUL   := NewExtAdr(DefExtr("E@IMUL",EnvCSEG));
//	%-E        X_IDIV   := NewExtAdr(DefExtr("E@IDIV",EnvCSEG));
//	%-E        X_IREM   := NewExtAdr(DefExtr("E@IREM",EnvCSEG));
//	%-E        X_RENEG  := NewExtAdr(DefExtr("E@RENEG",EnvCSEG));
//	%-E        X_READD  := NewExtAdr(DefExtr("E@READD",EnvCSEG));
//	%-E        X_RESUB  := NewExtAdr(DefExtr("E@RESUB",EnvCSEG));
//	%-E        X_REMUL  := NewExtAdr(DefExtr("E@REMUL",EnvCSEG));
//	%-E        X_REDIV  := NewExtAdr(DefExtr("E@REDIV",EnvCSEG));
//	%-E        X_RECMP  := NewExtAdr(DefExtr("E@RECMP",EnvCSEG));
//	%-E        X_LRNEG  := NewExtAdr(DefExtr("E@LRNEG",EnvCSEG));
//	%-E        X_LRADD  := NewExtAdr(DefExtr("E@LRADD",EnvCSEG));
//	%-E        X_LRSUB  := NewExtAdr(DefExtr("E@LRSUB",EnvCSEG));
//	%-E        X_LRMUL  := NewExtAdr(DefExtr("E@LRMUL",EnvCSEG));
//	%-E        X_LRDIV  := NewExtAdr(DefExtr("E@LRDIV",EnvCSEG));
//	%-E        X_LRCMP  := NewExtAdr(DefExtr("E@LRCMP",EnvCSEG));
//	%-E        X_IN2RE  := NewExtAdr(DefExtr("E@IN2RE",EnvCSEG));
//	%-E        X_IN2LR  := NewExtAdr(DefExtr("E@IN2LR",EnvCSEG));
//	%-E        X_RE2IN  := NewExtAdr(DefExtr("E@RE2IN",EnvCSEG));
//	%-E        X_RE2LR  := NewExtAdr(DefExtr("E@RE2LR",EnvCSEG));
//	%-E        X_LR2IN  := NewExtAdr(DefExtr("E@LR2IN",EnvCSEG));
//	%-E        X_LR2RE  := NewExtAdr(DefExtr("E@LR2RE",EnvCSEG));
//	%-E        X_GOTO   := NewExtAdr(DefExtr("E@GOTO",EnvCSEG));
//	%-E        X_CALL   := NewExtAdr(DefExtr("E@CALL",EnvCSEG));
//	%+D        X_ECASE  := NewExtAdr(DefExtr("E@ECASE",EnvCSEG));
//	%+S        X_INITO  := NewExtAdr(DefExtr("E@INITO",EnvCSEG));
//	%+S        X_GETO   := NewExtAdr(DefExtr("E@GETO",EnvCSEG));
//	%+S        X_SETO   := NewExtAdr(DefExtr("E@SETO",EnvCSEG));
//	%-E        X_CHKSTK := NewExtAdr(DefExtr("E@CHKSTK",EnvCSEG));
//	%-E %+SC   X_STKBEG := NewExtAdr(DefExtr("G@STKBEG",DGROUP));
//	%-E %+SC   X_STKOFL := NewExtAdr(DefExtr("E@STKOFL",EnvCSEG));
//	           X_SSTAT  := NewExtAdr(DefExtr("E@SSTAT",EnvCSEG));
//	           TMPAREA  := NewRelxAdr(DefExtr("G@TMP8687",DGROUP));
//	           TMPQNT   := NewRelxAdr(DefExtr("G@TMPQNT",DGROUP));
//	           X_INITSP := NewRelxAdr(DefExtr("G@INITSP",DGROUP));
//	%+F   endif;
//
//	%+D   ---   Set initial list and trace switches  ---
//	%+D   TraceMode:=0; ModuleTrace:=0;
//	%+D   if SK1LIN = 1
//	%+D   then trc:=SK1TRC; txx:=trc/10; InputTrace:=  trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    TraceMode:=   trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    ModuleTrace:= trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    listsw:=      trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    listq1:=      trc-(10*txx);
//	%+D        trc:=txx; txx:=trc/10;    listq2:=      trc-(10*txx);
//	%+D        SK1LIN:=0; SK1TRC:=0;
//	%+D   endif;
//
//	      curseg:=none; NewCurSeg(NEWOBJ(K_BSEG,size(BSEG)));
//	      nSubSeg:=0; FreeSeg:=none; InsideRoutine:=false;
//	      reversed:=false; InMassage:=false; DeadCode:=false;
//	      deleteOK:= MASSLV>61 and (DEBMOD<3);
//	      MindMask:=uSPBPM; PreReadMask:=NotMindMask:=0; PreMindMask:=uSPBPM;
//	%+S   PreWriteMask:=0;
	}
	
	
}
