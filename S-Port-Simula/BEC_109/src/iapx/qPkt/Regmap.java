package iapx.qPkt;

import static iapx.util.Reg.*;

import java.util.BitSet;

import iapx.Opcode;

import static iapx.qPkt.Qfrm1.*;
import static iapx.qPkt.Qfrm2.*;
import static iapx.qPkt.Qfrm2b.*;
import static iapx.qPkt.Qfrm3.*;
import static iapx.qPkt.Qfrm4.*;
import static iapx.qPkt.Qfrm5.*;
import static iapx.qPkt.Qfrm6.*;
import static iapx.util.Global.*;

import iapx.util.Option;
import iapx.util.Reg;
import iapx.util.Util;
import iapx.value.IntegerValue;
import iapx.value.MemAddr;

public class Regmap {

	public static int wOR(int x, int y) { return x | y; }
	public static int wNOT(int x) { return ~x; }

	public static int bAND(int x, int y) { return x & y; }
	public static int bOR(int x, int y) { return x | y; }
	public static int bSHR(int x, int y) { return x >> y; }

	public static int GetBreg(MemAddr opr) { // export range(0:nregs) res;
//		int ir, res;
//	    if(opr.sibreg == NoIBREG) return 0;
//	    else {
//	    	res = bOR(qEAX,bAND(opr.sibreg,BaseREG));
//	    	ir = bOR(qEAX,bSHR(bAND(opr.sibreg,IndxREG),3));
//	    	if(ir == qESP) ; // Nothing
//	    	else if(res == ir) res=0;
//	    }
//	    return res;
		return opr.bReg;
	}

	public static void MakeRegmap(Qpkt qi) {
		// %-E    range(0:MaxByte) segreg,bireg;
		int qR, s, subc, sibreg, ir, br;
//		int read = 0, write = 0, mind = 0, uR, uRx, unused, m;
		BitSet read = new BitSet();
		BitSet write = new BitSet();
		BitSet mind = new BitSet();
//	      RST(R_MakeRegmap);

		boolean doPostCode = false;
		
	//    NOTE: It is not necessary to 'mind' SP,BP,M since this
	//          is always done automatically.

//	case 0:qMXX (qi.fnc);
		switch(qi.fnc) { 
	      case qPUSHR: //---- PUSHR   reg ----------------------- Format 1
//	           read = wOR(uMask[qi.subc],uSP); write = uSP; mind = 0;
	           read.set(qi.subc); read.set(qSP); write.set(qSP);
	           break;
	      case qPOPR: //----- POPR    reg ----------------------- Format 1
//	           subc = qi.subc; mind = uMask[subc];
//	           write = wOR(mind,uSP); read = uSP;
//	           if(subc <= qBL) write = wOR(write,uMask[subc+4]);
	           write.set(qi.subc); write.set(qSP); read.set(qSP); mind.set(qi.subc);
	           break;
	      case qPUSHC: //---- PUSHC   reg const ----------------- Format 2
	                   //---- PUSHC   reg fld addr -------------- Format 2b
//	           read = uSP; write = uSP; mind = 0;
	           read.set(qSP); write.set(qSP);
	           break;
	      case qPUSHA: //---- PUSHA   reg opr ------------------- Format 3
//	           read = uSP; mind = 0; write = wOR(uMask[qi.reg],uSP);
	           write.set(qi.reg); write.set(qSP); read.set(qSP);
	           
//	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O3xL1;
	           break;
	      case qPUSHM: //---- PUSHM   const opr ----------------- Format 4
//	           read = uSP | uM; write = uSP; mind = 0;
	           read.set(qSP); read.set(qM); write.set(qSP);
	    	           
//	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O4L1;
	           break;
	      case qPOPK: //----- POPK    const --------------------- Format 2
//	           read = uSP; write = wOR(uSP,uF); mind = 0;
	           write.set(qSP); write.set(qF); read.set(qSP);
	           break;
	      case qPOPM: //----- POPM    reg const opr ------------- Format 4
//	           read = uSP; write = wOR(uSP,uM); mind = 0;
	           write.set(qSP); write.set(qF); read.set(qSP);
//	           Macro UnAlligned(1);begin (bAND(%1,AllignFac-1) != 0) endmacro;
//	           if UnAlligned(%qi qua Qfrm4.aux.LO%)
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O4L2
	           break;
	      case qLOADC: //---- LOADC   reg const ----------------- Format 2
	                   //---- LOADC   reg fld addr -------------- Format 2b
//	           write = mind = uMask[qi.reg]; read = 0;
	           write.set(qi.reg); mind.set(qi.reg);
	           break;
	      case qLOADA: //---- LOADA   reg opr ------------------- Format 3
//	           write = mind = uMask[qi.reg]; read = 0;
	           write.set(qi.reg); mind.set(qi.reg);
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O3xL2;
	           break;
	      case qBOUND: //---- BOUND   reg opr ------------------- Format 3
//	           mind = uMask[qi.reg]; read = wOR(mind,uM); write = 0;
	           read.set(qi.reg); read.set(qM); mind.set(qi.reg);
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O3L4x;
	           break;
	      case qLOAD: //----- LOAD    subc reg ofst opr nrep ---- Format 4c
//	           read = uM;
//	           if(qi.subc != 0)
//	        	    write = mind = uMask[WholeReg[qi.reg]];
//	           else write = mind = uMask[qi.reg];
	           read.set(qM); write.set(qi.reg); mind.set(qi.reg);
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O3L4;
	           break;
	      case qSTORE: //---- STORE   reg opr ------------------- Format 3
//	           read = uMask[qi.reg]; write = uM; mind = 0;
	           read.set(qi.reg); write.set(qM);
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O3L5;
	           break;
	      case qMOV: //------ MOV     reg reg2 ------------------ Format 2
	           IntegerValue aux = (IntegerValue) ((Qfrm2)qi).aux;
//	           read = uMask[aux.value];
//	           if(qi.subc != 0)
//	        	    write = mind = uMask[WholeReg[qi.reg]];
//	           else write = mind = uMask[qi.reg];
	           read.set(aux.value); write.set(qi.reg);
	           break;
	      case qMOVMC: //---- MOVMC   width const opr ----------- Format 4
	                   //---- MOVMC   width fld opr addr -------- Format 4b
	           read = 0; write = uM; mind = 0;
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O4L3;
	           break;
	      case qXCHG: //----- XCHG    reg reg2 ------------------ Format 2
	           aux = (IntegerValue) ((Qfrm2)qi).aux;
	           read = write = mind = wOR(uMask[qi.reg],uMask[aux.value]);
	           break;
	      case qXCHGM: //---- XCHGM   reg opr ------------------- Format 3
	           mind = uMask[qi.reg]; read = write = wOR(mind,uM);
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O3L6;
	           break;
	      case qMONADR: //--- MONADR  subc reg ------------------ Format 2
	           subc = qi.subc; read = write = mind = uMask[qi.reg];
	           if(subc != qNOT) {
	        	   write = wOR(write,uF);
	               if(subc == qNEGM) mind = wOR(mind,uF); 
	           }
	           break;
	      case qMONADM: //--- MONADM  subc width opr ------------ Format 3
	           read = uM; write = uM; mind = 0;
	           if(qi.subc != qNOT) write = wOR(write,uF);
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O3L7;
	           break;
	      case qDYADC: //---- DYADC   subc reg const ------------ Format 2
	                   //---- DYADC   subc reg fld addr --------- Format 2b
	           read = 0;
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           //goto DR1;
	           break;
	      case qDYADM: //---- DYADM   subc reg opr -------------- Format 3
	           read = uM;
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto DR2;
	           break;
	      case qDYADR: //---- DYADR   subc reg reg2 ------------- Format 2
	           aux = (IntegerValue) ((Qfrm2)qi).aux;
	           read = uMask[aux.value];
	           
	           Util.IERR("SJEKK DENNE GOTO: DR1,DR2");
//	  DR1:DR2: 
		       uR = uMask[qi.reg]; read = wOR(read,uR);
	           //case 0:19 (qi.subc)
	  		   switch(qi.subc) {
		           case qCMP: write = mind = uF; break;
		           case qADDM,qSUBM,qANDM,qORM,qXORM:
		                      write = mind = wOR(uR,uF); break;
		           case qADC,qADCF,qSBB,qSBBF:
		                      read = wOR(read,uF);
		                      write = wOR(uR,uF); mind = uR;
		                      break;
		           default:  mind = uR; write = wOR(mind,uF);
	  		   }
	           if(qi.fnc == Opcode.qDYADM) {
		           
		           Util.IERR("SJEKK DENNE GOTO");
	        	   // goto O3L8 endif;
	        	   break;
	           }
	           break;
	      case qSHIFT: //---- SHIFT   subc reg CL --------------- Format 2
	           mind = uMask[qi.reg];
	           read = wOR(mind,uCL); write = wOR(mind,uF);
	      case qDYADMC: //--- DYADMC  subc width const opr ------ Format 4
	                    //--- DYADMC  subc width fld opr addr --- Format 4b
	           read = 0;
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto DM1; // KOPIERT INN HER !
	 	       mind = 0; read = wOR(read,uM);
	           //case 0:19 (qi.subc)
	           switch(qi.subc) {
		           case qCMP: write = mind = uF;
		           
		           Util.IERR("SJEKK DENNE GOTO");
		           		//goto O3L9;
		           		break;
		           case qADDM,qSUBM,qANDM,qORM,qXORM: mind = uF; break;
		           case qADC,qADCF,qSBB,qSBBF: read = wOR(read,uF); break;
	           }
	           write = wOR(uF,uM);
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           //goto O4L4;
	           
	           break;
	      case qDYADMR: //--- DYADMR  subc reg opr -------------- Format 3
	           read = uMask[qi.reg];
	      	   
//	      DM1: // FJERNET - SE OVENFOR
	    	  mind = 0; read = wOR(read,uM);
	           //case 0:19 (qi.subc)
	           switch(qi.subc) {
		           case qCMP: write = mind = uF;
		           
		           Util.IERR("SJEKK DENNE GOTO");
		           		//goto O3L9;
		           		break;
		           case qADDM,qSUBM,qANDM,qORM,qXORM: mind = uF; break;
		           case qADC,qADCF,qSBB,qSBBF: read = wOR(read,uF); break;
	           }
	           write = wOR(uF,uM);
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           //goto O4L4;
	           break;
	      case qTRIADR: //--- TRIADR  subc reg ------------------ Format 2
	           qR = qi.reg; s = RegSize[qR];
	           uR = uMask[accreg[s]]; read = wOR(uR,uMask[qR]);
	           uRx = uMask[extreg[s]]; write = wOR(uRx,wOR(uR,uF));
	           // case 0:15 (qi.subc)
	           switch(qi.subc) {
		           case qWMUL,qIMUL,qWMULF,qIMULF: mind = wOR(uR,uRx); break;
		           case qWDIV,qIDIV,qWDIVF,qIDIVF:
		                  read = wOR(read,uRx); mind = uR; break;
		           case qWMOD,qIMOD,qWMODF,qIMODF:
		                  read = wOR(read,uRx); mind = uRx; break;
	           }
	           break;
	      case qTRIADM: //--- TRIADM  subc width opr ------------ Format 3
	           if(qi.reg < qw_W) {
	        	   uR = uAL; uRx = uAH;
	           } else {
	        	   uR = uAX; uRx = uDX;
	           }
	           read = wOR(uR,uM); write = wOR(uR,wOR(uF,uRx));
	           //case 0:15 (qi.subc)
	           switch(qi.subc) { 
		           case qWMUL,qIMUL,qWMULF,qIMULF: mind = wOR(uR,uRx); break;
		           case qWDIV,qIDIV,qWDIVF,qIDIVF:
		                  read = wOR(read,uRx); mind = uR; break;
		           case qWMOD,qIMOD,qWMODF,qIMODF:
		                  read = wOR(read,uRx); mind = uRx; break;
	           }
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O3L10;
	           break;
	      case qCWD: //------ CWD   width ----------------------- Format 1
// %-E         read = uAX; write = uDX; mind = wOR(uAX,uDX);
	           if(qi.subc == qAL) {
	        	   read = uAL; write = uAH; mind = uAX;
	           } else {
	        	   read = uAX; write = uDX; mind = wOR(uAX,uDX);
	           }
	           break;
	      case qCONDEC: //--- CONDEC  subc reg ------------------ Format 2
	           write = uMask[WholeReg[qi.reg]];
	           mind = uMask[qi.reg]; read = wOR(mind,uF);
	           break;
	      case qRSTRB, //---- RSTRB   subc dir rep -------------- Format 2
	           qRSTRW: //---- RSTRB   subc dir rep -------------- Format 2
// %-E    ---  read = wOR(wOR(uCX,uDI),uES); write = wOR(uCX,uDI);
	           read = wOR(uECX,uEDI); write = wOR(uECX,uEDI);
	           //case 0:6 (qi.subc)
	           switch(qi.subc) {
	           case qRMOV:
// %-E              read = wOR(wOR(wOR(uCX,uDI),uES),wOR(wOR(uM,uSI),uDS));
// %-E              write = wOR(wOR(uCX,uDI),wOR(uM,uSI)); mind = 0;
	                read = wOR(read,wOR(uM,uESI));
	                write = wOR(write,wOR(uM,uESI)); mind = 0;
	 	           break;
	           case qRCMP:
// %-E              read = wOR(wOR(wOR(uCX,uDI),uES),wOR(wOR(uM,uSI),uDS));
// %-E              write = wOR(wOR(uCX,uDI),wOR(uF,uSI)); mind = uF;
	                read = wOR(read,wOR(uM,uESI));
	                write = wOR(write,wOR(uF,uESI)); mind = uF;
	 	           break;
	           case qZERO:
// %-E              if qi.fnc=qRSTRB then read = wOR(wOR(wOR(uCX,uDI),uES),uAL)
// %-E              else read = wOR(wOR(wOR(uCX,uDI),uES),uAX) endif;
// %-E              write = wOR(wOR(uCX,uDI),wOR(uF,uM)); mind = 0;
	                if(qi.fnc == Opcode.qRSTRB)
	                	 read = wOR(read,uAL);
	                else read = wOR(read,uEAX);
	                write = wOR(write,wOR(uF,uM)); mind = 0;
	 	           break;
	           case qRCMPS:
// %-E              read = wOR(wOR(wOR(uCX,uDI),uES),wOR(wOR(uM,uSI),uDS));
// %-E              write = wOR(wOR(uCX,uDI),wOR(wOR(uF,uAX),uSI)); mind = uAL;
	                read = wOR(read,wOR(uM,uESI));
	                write = wOR(write,wOR(wOR(uF,uEAX),uESI)); mind = uAL;
	 	           break;
	           case qRSCAS:
// %-E              read = wOR(wOR(wOR(uCX,uDI),uES),wOR(uM,uAL));
// %-E              write = wOR(wOR(uCX,uDI),uF); mind = uCX;
	                read = wOR(read,wOR(uM,uAL));
	                write = wOR(write,uF); mind = uECX;
	 	           break;
	           case qRSTOS:
// %-E              read = wOR(wOR(wOR(uCX,uDI),uES),uAL);
// %-E              write = wOR(wOR(uCX,uDI),uM); mind = 0;
	                read = wOR(read,uAL);
	                write = wOR(write,uM); mind = 0;
	 	           break;
	           }
	           break;
	      case qLAHF: //----- LAHF ------------------------------ Format 1
	           read = uF; write = uAH; mind = uAH;
	           break;
	      case qSAHF: //----- SAHF ------------------------------ Format 1
	           read = uAH; write = uF; mind = uF;
	           break;
	      case qFDEST, //---- FDEST   subc fix rela qi ---------- Format 6
	           qBDEST, //---- BDEST   subc labno rela ----------- Format 6
	           qLABEL: //---- LABEL   subc fix ------------------ Format 6
	           read = uSPBPM; write = uALL; mind = 0;
	           break;
	      case qJMP: //------ JMP     subc addr ----------------- Format 5
	           read = uSPBPM; write = uALL; mind = 0;
	           if(qi.subc != 0) read = wOR(read,uF);
	           break;
	      case qJMPM: //----- JMPM    opr ----------------------- Format 3
	           read = uSPBPM; write = uALL; mind = 0;
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           //goto O3L11;
	           break;
// %-E    case qJMPFM: //---- JMPFM   opr ----------------------- Format 3
// %-E         read = uSPBPM; write = uALL; mind = 0; goto O3L12;
	      case qCALL: //----- CALL    subc pxlng addr ----------- Format 5
	           read = wOR(uSP,uM); write = uALLbutBP; mind = 0;
	           if(((Qfrm5)qi).addr.kind == MemAddr.reladr) // -- Special Case: CALL [reg]
	        	   read = wOR(read,uMask[GetBreg(((Qfrm5)qi).addr)]);
	           break;
	      case qADJST: //---- ADJST   const --------------------- Format 2
	           if(((Qfrm2)qi).aux == null)
	                read = write = 0;
	           else read = write = uSP;
	           mind = 0;
	           break;
// %-E    case qDOS2: //----- DOS2          --------------------- Format 1
// %-E         read = wOR(uSPBPM,uAH); write = wOR(wOR(uAX,uM),uF); mind = 0;
// %-E    case qINT: //------ INT     const --------------------- Format 2
// %-E         if ((Qfrm2)qi).aux.val=33  -- DOS-CALL
// %-E         then read = wOR(uSPBPM,uAH); write = wOR(wOR(uAX,uM),uF);
// %-E         else read = uSPBPM; write = uALL endif; mind = 0;
	      case qENTER, //---- ENTER   const --------------------- Format 2
	           qLEAVE, //---- LEAVE   const --------------------- Format 2
	           qRET, //------ RET     const --------------------- Format 2
	           qINT, //------ INT     const --------------------- Format 2
	           qIRET: //----- IRET ------------------------------ Format 1
	           read = uSPBPM; write = uALL; mind = 0;
	           break;
	      case qFLD: //------ FLD     fmf opr ------------------- Format 3
	           if(NUMID == WTLx167)
	        	    write = uAX;
	           else write = 0;
	           read = uM; mind = 0;
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           // goto O3L13;
	           break;
	      case qFLDC: //----- FLDC    sreg fmf lrv/rev/val ------ Format 3+
// %-E         read = uMask[qi.subc); write = 0; mind = 0;
	           if(NUMID == WTLx167)
	        	    { read = 0; write = uAX; mind = 0; }
	           else { read = 0; write = 0; mind = 0; }
	           break;
	      case qFST, //------ FST     fmf opr ------------------- Format 3
	           qFSTP: //----- FSTP    fmf opr ------------------- Format 3
	           if(NUMID == WTLx167)
	        	    write = wOR(uM,uAX);
	           else write = uM;
	           read = 0; mind = 0;
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           //goto O3L14;
	           break;
	      case qFPUSH: //---- FPUSH   fSD fmf ------------------- Format 1
// %-E         read = uSP; write = wOR(uSP,uDI); mind = 0;
	           read = uSP; write = uSP; mind = 0;
	           break;
	      case qFPOP: //----- FPOP    fSD fmf ------------------- Format 1
// %-E         read = uSP; write = wOR(uSP,uDI); mind = 0;
	           read = uSP; write = uSP; mind = 0;
	           break;
	      case qFLDCK, //---- FLDCK   subc ---------------------- Format 1
	           qFDUP, //----- FDUP ------------------------------ Format 1
	           qFMONAD, //--- FMONAD  subc ---------------------- Format 1
	           qWAIT, //----- WAIT ------------------------------ Format 1
	           qTSTOFL, //--- INTO ------------------------------ Format 1
	           qEVAL: //----- EVAL ------------------------------ Format 1
	           read = write = mind = 0;
	      break;
	      case qFDYAD: //---- FDYAD   subc ---------------------- Format 1
	           read = write = mind = 0;
	           if(qi.subc == qFCOM) { write = wOR(uAX,uF); mind = uF; }
	      case qFDYADM: //--- FDYADM  subc fmf opr -------------- Format 3
	           if(NUMID == WTLx167)
	        	    write = uAX;
	           else write = 0;
	           read = uM; mind = 0;
	           if(qi.subc == qFCOM) { write = wOR(uAX,uF); mind = uF; }
	           
	           Util.IERR("SJEKK DENNE GOTO");
	           //goto O3L16;
	           break;
	      case qLINE: //----- LINE    subc const ---------------- Format 2
	           read = write = mind = 0;
//	           if qi.subc=qSTM then
//	%+S %-D    if qi.subc=qSTM then
//	      -- ???? if DEBMOD > 2 then read = uSP; write = wOR(uSP,uF) endif;
//	              if DEBMOD > 2 then            write =         uF  endif;
//	           endif;
//	%+S %-D    endif;
	           break;
		}

        
        //Util.IERR("SJEKK DENNE GOTO");
        // goto FF1;

//	O4L1:O4L2:O4L3:O4L4:
//	    O3L4x:
//	O3L4:O3L5:O3L6:O3L7:O3L8:O3L9:
//	O3L10:O3L11:O3L13:O3L14:      O3L16:
//	O3xL1:O3xL2:

        if(doPostCode) {
//			sibreg = ((Qfrm3)qi).opr.sibreg;
//			if(sibreg  !=  NoIBREG) {
//				br = bOR(qEAX,bAND(sibreg,BaseREG));
//				ir = bOR(qEAX,bSHR(bAND(sibreg,IndxREG),3));
//				if( (br != ir) | (br == qESP) ) read = wOR(read,uMask[br]);
//				if( ir != qESP ) read = wOR(read,uMask[ir]);
//			}
			Qfrm3 qi3 = (Qfrm3) qi;
			MemAddr opr = qi3.opr;
			int bReg = opr.getBreg();
			int iReg = opr.getIreg();
			if(bReg + iReg > 0) {
				if( (bReg != iReg) | (bReg == qESP) ) read = read | uMask[bReg];
				if( iReg != qESP ) read = read | uMask[iReg];				
			}
        }
	      
//        Util.IERR("SJEKK DENNE GOTO FF1");
//	FF1:
		
        if(! InMassage) {
        	if(Option.listq1 > 1) {
        		if( (PreReadMask != 0) | (NotMindMask != 0) | (PreMindMask != uSPBPM) ) {
        			String line = Reg.EdRegMask("          PreRead",PreReadMask);
        			line = line + Reg.EdRegMask("  PreMind",PreMindMask);
        			line = line + Reg.EdRegMask("  NotMind",NotMindMask);
        			IO.println("" + line);
        		}
        	}
        	read = wOR(read,PreReadMask); PreReadMask = 0;
        	write = wOR(write,PreWriteMask); int PreWriteMask = 0;
        	unused = wNOT(wOR(read,write));
        	m = wOR(PreMindMask,NotMindMask);

        	//m = wAND( wAND( wNOT(uSPBPM) ,unused) ,m);
        	m = wNOT(uSPBPM) & unused & m;

        	if(m  !=  0) Util.IERR(Reg.EdRegMask("Can't Presave",m));

        	// m = wAND(wNOT(MindMask),read);
        	m = wNOT(MindMask) & read;

        	if(m  !=  0) {
        		IO.println("DETTE MÅ SJEKKES SEINERE: ERROR: " + Reg.EdRegMask("Can't Read",m));
        		//Util.IERR(Edit.EdRegMask("Can't Read",m));
        	}

        	// m = wAND(wAND(wNOT(wOR(read,uM)),write),MindMask);
        	m = wNOT(wOR(read,uM)) & write & MindMask;

        	if(m  !=  0) Util.IERR(Reg.EdRegMask("Can't Write",m));
        	//-        m = wOR(PreMindMask,wOR(mind,wAND(MindMask,wNOT(wOR(read,write)))));
        	//-        MindMask = wAND(wNOT(NotMindMask),m);

        	//MindMask = wAND(wOR(PreMindMask, wOR(mind,wAND(MindMask,wNOT(wOR(read,write))))), wNOT(NotMindMask));
        	int xxx = MindMask & wNOT(wOR(read,write));
        	MindMask = wOR(PreMindMask, wOR(mind,xxx)) & wNOT(NotMindMask);

        	PreMindMask = uSPBPM; NotMindMask = 0;
        }
        qi.read = read; qi.write = write;
	}

}
