package iAPX.asmListing;

import iAPX.qPkt.Qpkt;
import iAPX.util.Option;

public class AsmListing {
	
	public static void asmListing(int rela, Qpkt qi, boolean Alist) {
// begin range(0:nregs) qreg,qreg2;  -- Q-Code registers
//   range(0:7) reg,reg2;            -- I-Code registers
//   range(0:20) subc;
//   infix(MemAddr) opr; ref(Qfrm6) dst;
//   infix(wWORD) cnst;
//   range(0:MaxWord) i; infix(WORD) ix,fix;
//   Boolean OvflTail;
//   infix(ValueItem) val;
//   range(0:MaxByte) fmf;
//  range(0:MaxByte) fSD,fwf,fSP,tx,fx,qereg,ereg; infix(String) opc;

//   qreg:=qi.reg; subc:=qi.subc; OvflTail:=false; ListI:=Alist;

//		if(Option.InputTrace > 0) printout(inptrace);
//   setpos(sysout,P_FLAG);
//   if listq2>2 then outchar('P');
//      setpos(sysout,P_SEG); edhex(sysout,CSEGID.val,2);
//      setpos(sysout,P_RELA) endif;
//   if rela.val<>0 then edhex(sysout,rela.val,4) endif;
//   if    qi.type=cANY then outstring(" ANY ")
//   elsif qi.type=cVAL then outstring(" VAL ")
//   elsif qi.type=cOBJ then outstring(" OBJ ")
//   elsif qi.type=cSTP then outstring(" STP ")
//   elsif qi.type=cADR then outstring(" ADR ") endif;
//   if listq2>2 then setpos(sysout,Q_OPRATR) endif;
//   case 0:qMXX (qi.fnc);
//   when qPUSHR: ------ PUSHR   reg ----------------------  Format 1
//        --- Q-Code:
//        outstring("PUSHR  "); outreg(subc);
//        if Alist
//        then I_CODE;
//             outstring("PUSH   "); outreg(WholeReg(subc));
//        endif;
//   when qPOPR: ------- POPR    reg ----------------------  Format 1
//        --- Q-Code:
//        outstring("POPR   "); outreg(subc);
//        if Alist
//        then I_CODE;
//             outstring("POP    "); outreg(WholeReg(subc));
// %-E         if subc >= qES then edASSUME(subc,DumSEG) endif;
//        endif;
//   when qPUSHC: ------ PUSHC   reg const ----------------  Format 2
//                ------ PUSHC   reg fld addr -------------  Format 2b
//        --- Q-Code:
//        outstring("PUSHC  "); outreg(qreg);
//        outchar(','); edval(qi);
//        if Alist
//        then I_CODE;
// %-E         if CPUID >= iAPX186
// %-E         then
//                  outstring("PUSH   "); edval(qi);
// %-E         else if qi.kind=K_Qfrm2
// %-E              then EdLoadCnst(qi,qreg,qi qua Qfrm2.aux.val);
// %-E              else outstring("MOV    "); outreg(qreg);
// %-E                   outchar(','); edval(qi);
// %-E              endif;
// %-E              I_LINE; outstring("PUSH   ");
// %-E              outreg(WholeReg(qreg));
// %-E         endif;
//        endif;
//   when qPUSHA: ------ PUSHA   reg opr ------------------  Format 3
//        opr:=qi qua Qfrm3.opr;
// %-E    opr.sbireg:=SetSBIreg(opr.sbireg,GetDefaultSreg(opr));
//        --- Q-Code:
//        outstring("PUSHA  "); outreg(qreg);
//        outchar(','); outopr(opr);
//        if Alist
//        then I_CODE; outstring("LEA    "); outreg(qreg);
//             outchar(','); outopr(opr); I_LINE;
//             outstring("PUSH   "); outreg(WholeReg(qreg));
//        endif;
//   when qPUSHM: ------ PUSHM   const opr ----------------  Format 4
//        cnst:=qi qua Qfrm4.aux; opr:=qi qua Qfrm4.opr;
//        --- Q-Code:
//        outstring("PUSHM  "); outword(cnst.val);
//        outchar(','); outopr(opr);
//        if Alist
//        then I_CODE; cnst.val:=wAllign(%cnst.val%);
//             opr.rela.val:=opr.rela.val+cnst.val;
//             repeat while cnst.val>0
//             do cnst.val:=cnst.val-AllignFac;
//                opr.rela.val:=opr.rela.val-AllignFac;
//                outstring("PUSH   "); outopr(opr); I_LINE;
//             endrepeat; goto X1;
//        endif;
//   when qPOPK: ------- POPK    const --------------------  Format 2
//        cnst:=qi qua Qfrm2.aux;
//        --- Q-Code:
//        outstring("POPK   "); outword(cnst.val);
//        if Alist then I_CODE; EdAddSP(qi,wAllign(%cnst.val%)) endif;
//   when qPOPM: ------- POPM    reg const opr ------------  Format 4
//        cnst:=qi qua Qfrm4.aux; opr:=qi qua Qfrm4.opr;
//        --- Q-Code:
//        outstring("POPM   "); outreg(qreg);
//        outchar(','); outword(cnst.val);
//        outchar(','); outopr(opr);
//        if Alist
//        then I_CODE;
//             repeat while cnst.val>0
//             do outstring("POP    ");
//                if cnst.val >= AllignFac
//                then cnst.val:=cnst.val-AllignFac;
//                     outopr(opr); I_LINE;
//                else cnst.val:=0; outreg(WholeReg(qreg)); I_LINE;
//                     outstring("MOV    ");
//                     if qreg<qAX then outstring("BYTE PTR ") endif;
//                     outopr(opr);
//                     outchar(','); outreg(qreg);
//                endif;
//                opr.rela.val:=opr.rela.val+AllignFac;
//             endrepeat;
//        endif;
//   when qLOADC: ------ LOADC   reg const ----------------  Format 2
//                ------ LOADC   reg fld addr -------------  Format 2b
//        --- Q-Code:
//        outstring("LOADC  "); outreg(qreg);
//        outchar(','); edval(qi);
//        if Alist
//        then I_CODE;
//             if qi.kind=K_Qfrm2
//             then EdLoadCnst(qi,qreg,qi qua Qfrm2.aux.val);
//             else outstring("MOV    "); outreg(qreg);
//                  outchar(','); edval(qi);
//             endif;
//        endif;
// %-E when qLOADSC: ----- LOADSC  sreg reg fld addr -------  Format 2b
// %-E    qi qua Qfrm2b.aux.val:=F_BASE;
// %-E    --- Q-Code:
// %-E    outstring("LOADSC "); outreg(subc);
// %-E    outchar(','); outreg(qreg);
// %-E    outchar(','); edval(qi);
// %-E    if Alist
// %-E    then I_CODE;
// %-E         outstring("MOV    "); outreg(qreg);
// %-E         outchar(','); edval(qi); I_LINE;
// %-E         outstring("MOV    "); outreg(subc);
// %-E         outchar(',');   outreg(qreg);
// %-E         edASSUME(subc,qi qua Qfrm2b.addr.segmid);
// %-E    endif;
//   when qLOADA: ------ LOADA   reg opr ------------------  Format 3
//        opr:=qi qua Qfrm3.opr;
// %-E    opr.sbireg:=SetSBIreg(opr.sbireg,GetDefaultSreg(opr));
//        --- Q-Code:
//        outstring("LOADA  "); outreg(qreg);
//        outchar(','); outopr(opr);
//        if Alist
//        then I_CODE; outstring("LEA    "); outreg(qreg);
//             outchar(','); outopr(opr);
//        endif;
// %-E when qLDS: -------- LDS   reg ofst opr nrep ---------  Format 4c
// %-E    i:=qi qua Qfrm4c.nrep; cnst:=qi qua Qfrm4c.aux;
// %-E    --- Q-Code:
// %-E    outstring("LDS    "); outreg(qreg);
// %-E    outchar(','); outopr(qi qua Qfrm3.opr);
// %-E    outchar(','); outword(i);
// %-E    outchar(','); outword(cnst.val);
// %-E    if Alist
// %-E    then I_CODE; outstring("LDS    "); outreg(qreg);
// %-E         outstring(",DWORD PTR ");
// %-E         outopr(qi qua Qfrm3.opr);
// %-E         repeat while i <> 0
// %-E         do i:=i-1; I_LINE; outstring("LDS    "); outreg(qreg);
// %-E            outstring(",DS:["); outreg(qreg);
// %-E            outstring("]+"); outword(cnst.val);
// %-E         endrepeat;
// %-E         edASSUME(qDS,DumSEG);
// %-E    endif;
// %-E when qLES: -------- LES   reg ofst opr nrep ---------  Format 4c
// %-E    i:=qi qua Qfrm4c.nrep; cnst:=qi qua Qfrm4c.aux;
// %-E    --- Q-Code:
// %-E    outstring("LES    "); outreg(qreg);
// %-E    outchar(','); outopr(qi qua Qfrm3.opr);
// %-E    outchar(','); outword(i);
// %-E    outchar(','); outword(cnst.val);
// %-E    if Alist
// %-E    then I_CODE; outstring("LES    "); outreg(qreg);
// %-E         outstring(",DWORD PTR ");
// %-E         outopr(qi qua Qfrm3.opr);
// %-E         repeat while i <> 0
// %-E         do i:=i-1; I_LINE; outstring("LES    "); outreg(qreg);
// %-E            outstring(",ES:["); outreg(qreg);
// %-E            outstring("]+"); outword(cnst.val);
// %-E         endrepeat;
// %-E         edASSUME(qES,DumSEG);
// %-E    endif;
//  when qBOUND: ------ BOUND   reg opr ------------------  Format 3
//       opr:=qi qua Qfrm3.opr;
//       --- Q-Code:
//       outstring("BOUND  "); outreg(qreg);
//       outchar(','); outopr(opr);
//       if Alist
//       then I_CODE; outstring("BOUND  "); outreg(qreg);
//            outchar(','); outopr(opr);
//       endif;
//   when qLOAD: ------- LOAD    subc reg ofst opr nrep ---  Format 4c
//       i:=qi qua Qfrm4c.nrep; cnst:=qi qua Qfrm4c.aux;
//        opr:=qi qua Qfrm3.opr;
//        --- Q-Code:
//        outstring("LOAD   ");
//       if subc=qSEXT then outstring("SEXT,");
//       elsif subc=qZEXT then outstring("ZEXT,") endif;
//        outreg(qreg); outchar(','); outopr(opr);
//       outchar(','); outword(i);
//       outchar(','); outint(cnst.val);
//        if Alist
//        then I_CODE;
//            if subc=qSEXT
//            then outstring("MOVSX  "); outreg(WholeReg(qreg));
//            elsif subc=qZEXT
//            then outstring("MOVZX  "); outreg(WholeReg(qreg));
//            else
//                   outstring("MOV    "); outreg(qreg);
//            endif;
//             outchar(','); outopr(opr);
//            repeat while i <> 0
//            do i:=i-1; I_LINE; outstring("MOV    "); outreg(qreg);
//               outstring(",["); outreg(qreg);
//               outstring("]+"); outint(cnst.val);
//            endrepeat;
// %-E         if    qreg = qDS then edASSUME(qDS,DumSEG)
// %-E         elsif qreg = qES then edASSUME(qES,DumSEG) endif;
//        endif;
//   when qSTORE: ------ STORE   reg opr ------------------  Format 3
//        opr:=qi qua Qfrm3.opr;
//        --- Q-Code:
//        outstring("STORE  "); outreg(qreg);
//        outchar(','); outopr(opr);
//        if Alist
//        then I_CODE; outstring("MOV    "); outopr(opr);
//             outchar(',');   outreg(qreg);
//        endif;
//   when qMOV: -------- MOV     subc reg reg2 ------------  Format 2
//        --- Q-Code:
//        outstring("MOV    ");
//       if subc=qSEXT then outstring("SEXT,");
//       elsif subc=qZEXT then outstring("ZEXT,") endif;
//        outreg(qreg); outchar(',');
//        outreg(qi qua Qfrm2.aux.val);
//        if Alist
//        then I_CODE; qreg2:=qi qua Qfrm2.aux.val;
//             if RegSize(qreg) <> RegSize(qreg2)
//             then IERR("COASM.qMOV byte/word register");
//                  setpos(sysout,I_OPERATOR);
//                  qreg:=WholeReg(qreg); qreg2:=WholeReg(qreg2);
//             endif;
//            if subc=qSEXT
//            then outstring("MOVSX  "); outreg(WholeReg(qreg));
//            elsif subc=qZEXT
//            then outstring("MOVZX  "); outreg(WholeReg(qreg));
//            else outstring("MOV    "); outreg(qreg) endif;
//            outchar(',');  outreg(qreg2);
// %-E         if (qreg >= qES) and (qreg2 >= qES)
// %-E         then  outstring("PUSH   "); outreg(qreg2); I_LINE;
// %-E               outstring("POP    "); outreg(qreg);
// %-E         else  outstring("MOV    "); outreg(qreg);
// %-E               outchar(',');  outreg(qreg2);
// %-E         endif;
// %-E         if    qreg = qDS then edASSUME(qDS,DumSEG)
// %-E         elsif qreg = qES then edASSUME(qES,DumSEG) endif;
//        endif;
//   when qMOVMC: ------ MOVMC   width const opr ----------  Format 4
//                ------ MOVMC   width fld opr addr -------  Format 4b
//        --- Q-Code:
//        outstring("MOVMC  "); outwidth(qreg);
//        outchar(' '); outopr(qi qua Qfrm4.opr);
//        outchar(','); edval(qi);
//        if Alist
//        then I_CODE; outstring("MOV    "); outwidth(qreg);
//             outopr(qi qua Qfrm4.opr); outchar(',');
//             edval(qi);
//        endif;
//   when qXCHG: ------- XCHG    reg reg2 -----------------  Format 2
//        qreg2:=qi qua Qfrm2.aux.val;
//        --- Q-Code:
//        outstring("XCHG   "); outreg(qreg);
//        outchar(','); outreg(qreg2);
//        if Alist
//        then I_CODE;
//             if RegSize(qreg) <> RegSize(qreg2)
//             then IERR("COASM.qXCHG byte/word register");
//                  setpos(sysout,I_OPERATOR);
//                  qreg:=WholeReg(qreg); qreg2:=WholeReg(qreg2);
//             endif;
//             outstring("XCHG   "); outreg(qreg);
//             outchar(','); outreg(qreg2);
//        endif;
//   when qXCHGM: ------ XCHGM   reg opr ------------------  Format 3
//        --- Q-Code:
//        outstring("XCHGM  "); outreg(qreg);
//        outchar(','); outopr(qi qua Qfrm3.opr);
//        if Alist
//        then I_CODE; outstring("XCHG   "); outreg(qreg);
//             outchar(','); outopr(qi qua Qfrm3.opr);
//        endif;
//   when qMONADR: ----- MONADR  subc reg -----------------  Format 2
//        --- Q-Code:
//        edmonad(subc); outreg(qreg);
//        if Alist
//        then I_CODE;
//             if subc>8 then subc:=subc-7; OvflTail:=TSTOFL endif;
//             if subc=qNEGM then subc:=qNEG endif;
//            if RegSize(qreg) > 1 then qreg:=WholeReg(qreg) endif;
//             edmonad(subc); outreg(qreg);
//             if subc>=qSHL1 then outstring(",1") endif;
//        endif;
//   when qMONADM: ----- MONADM  subc width opr -----------  Format 3
//        --- Q-Code:
//        edmonad(subc); outwidth(qreg);
//        outchar(' '); outopr(qi qua Qfrm3.opr);
//        if Alist
//        then I_CODE;
//             if subc>8 then subc:=subc-7; OvflTail:=TSTOFL endif;
//             if subc=qNEGM then subc:=qNEG endif;
//             edmonad(subc); outwidth(qreg);
//             outopr(qi qua Qfrm3.opr);
//             if subc>=qSHL1 then outstring(",1") endif;
//        endif;
//   when qDYADR: ------ DYADR   subc reg reg2 ------------  Format 2
//        qreg2:=qi qua Qfrm2.aux.val;
//        --- Q-Code:
//        eddyad(subc); outreg(qreg);
//        outchar(','); outreg(qreg2);
//        if Alist
//        then I_CODE; subc:=a_dyad(subc);
//             if qi.subc>=qADDF then OvflTail:=TSTOFL endif;
//             if RegSize(qreg) <> RegSize(qreg2)
//             then IERR("COASM.qDYADR byte/word register");
//                  setpos(sysout,I_OPERATOR);
//                  qreg:=WholeReg(qreg); qreg2:=WholeReg(qreg2);
//             endif;
//             eddyad(subc); outreg(qreg);
//             outchar(','); outreg(qreg2);
// %-E         if (qi.subc=qINCO) or (qi.subc=qDECO)
// %-E         then edinco(qi.subc,qreg) endif;
//        endif;
//   when qSHIFT: ------ SHIFT   subc reg CL --------------  Format 2
//        --- Q-Code:
//        outstring("SHIFT  ");
//        outword(subc); outchar(',');
//        outreg(qreg); outstring(",CL");
//        if Alist
//        then I_CODE; if subc=qSHL then outstring("SHL    ");
//             elsif subc=qSHR then outstring("SHR    ");
//             else outstring("SAR    ") endif;
//             outreg(qreg); outstring(",CL");
//        endif;
//   when qDYADC: ------ DYADC   subc reg const -----------  Format 2
//                ------ DYADC   subc reg fld addr --------  Format 2b
//        --- Q-Code:
//        eddyad(subc); outreg(qreg);
//        outchar(','); edval(qi);
//        if Alist
//        then I_CODE; subc:=a_dyad(subc);
//             if qi.subc>=qADDF then OvflTail:=TSTOFL endif;
// %-E         if    (qreg=qSP) and (subc=qADD) and (qi.kind=K_Qfrm2)
//            if    (qreg=qESP) and (subc=qADD) and (qi.kind=K_Qfrm2)
//                             then EdAddSP(qi,qi qua Qfrm2.aux.val)
// %-E         elsif (qreg=qSP) and (subc=qSUB) and (qi.kind=K_Qfrm2)
//            elsif (qreg=qESP) and (subc=qSUB) and (qi.kind=K_Qfrm2)
//                             then EdSubSP(qi qua Qfrm2.aux.val)
//             else eddyad(subc); outreg(qreg);
//                  outchar(','); edval(qi);
//             endif;
// %-E         if (qi.subc=qINCO) or (qi.subc=qDECO)
// %-E         then edinco(qi.subc,qreg) endif;
//        endif;
//   when qDYADM: ------ DYADM   subc reg opr -------------  Format 3
//        --- Q-Code:
//        eddyad(subc); outreg(qreg);
//        outchar(','); outopr(qi qua Qfrm3.opr);
//        if Alist
//        then I_CODE; subc:=a_dyad(subc);
//             if qi.subc>=qADDF then OvflTail:=TSTOFL endif;
//             eddyad(subc); outreg(qreg);
//             outchar(','); outopr(qi qua Qfrm3.opr);
// %-E         if (qi.subc=qINCO) or (qi.subc=qDECO)
// %-E         then edinco(qi.subc,qreg) endif;
//        endif;
//   when qDYADMC: ----- DYADMC  subc width const opr -----  Format 4
//                 ----- DYADMC  subc width fld opr addr --  Format 4b
//        --- Q-Code:
//        eddyad(subc); outwidth(qreg);
//        outopr(qi qua Qfrm4.opr);
//        outchar(','); edval(qi);
//        if Alist
//        then I_CODE; subc:=a_dyad(subc);
//             if qi.subc>=qADDF then OvflTail:=TSTOFL endif;
//             eddyad(subc); outwidth(qreg);
//             outopr(qi qua Qfrm4.opr); outchar(',');
//             edval(qi);
// %-E         if (qi.subc=qINCO) or (qi.subc=qDECO)
// %-E         then
// %-E              ---- ??????????????????????????
// %-E         endif;
//        endif;
//   when qDYADMR: ----- DYADMR  subc reg opr -------------  Format 3
//        --- Q-Code:
//        eddyad(subc); outreg(qreg);
//        outchar(','); outopr(qi qua Qfrm3.opr);
//        if Alist
//        then I_CODE; subc:=a_dyad(subc);
//             if qi.subc>=qADDF then OvflTail:=TSTOFL endif;
//             eddyad(subc); outopr(qi qua Qfrm3.opr)
//             outchar(','); outreg(qreg);
// %-E         if (qi.subc=qINCO) or (qi.subc=qDECO)
// %-E         then edinco(qi.subc,qreg) endif;
//        endif;
//   when qTRIADR: ----- TRIADR  subc reg -----------------  Format 2
//        --- Q-Code:
//        outtriadr(subc); outreg(qreg);
//        if Alist
//        then I_CODE; subc:=bAND(subc,11);
//             if subc <> qi.subc then OvflTail:=TSTOFL endif;
//             outtriadr(subc); outreg(qreg);
//        endif;
//   when qTRIADM: ----- TRIADM  subc width opr -----------  Format 3
//        --- Q-Code:
//        outtriadr(subc); outwidth(qreg);
//        outchar(' '); outopr(qi qua Qfrm3.opr);
//        if Alist
//        then I_CODE; subc:=bAND(subc,11);
//             if subc <> qi.subc then OvflTail:=TSTOFL endif;
//             outtriadr(subc); outwidth(qreg);
//             outopr(qi qua Qfrm3.opr);
//        endif;
//   when qCWD: -------- CWD  width -----------------------  Format 1
//        --- Q-Code:
//        outstring("CWD    ");
//       outreg(subc);
//        if Alist
//        then I_CODE;
// %-E         outstring("CWD");
//            if subc<qAX then outstring("CBW");
//            elsif subc<qEAX then outstring("CWD");
//            else outstring("CDQ") endif;
//        endif;
//   when qCONDEC: ----- CONDEC  subc reg -----------------  Format 2
//        --- Q-Code:
//        outstring("CONDEC ");
//        edqcond(subc); outreg(qreg);
//        if Alist
//        then I_CODE;
// %-E         EdCondJMP(NotQcond(subc));
// %-E         outstring("    SHORT $+3"); I_LINE;
// %-E         outstring("DEC    "); outreg(WholeReg(qreg));
//            EdCondSETB(subc); outreg(qreg);
//        endif;
//   when qRSTRB: ------ RSTRB   subc dir rep -------------  Format 2
//        ListRSTR(subc,qi.reg,qi qua Qfrm2.aux.val,'B');
//   when qRSTRW: ------ RSTRW   subc dir rep -------------  Format 2
//        ListRSTR(subc,qi.reg,qi qua Qfrm2.aux.val,'W');
//   when qLAHF: ------- LAHF -----------------------------  Format 1
//        --- Q-Code:
//        outstring("LAHF   ");
//        if Alist then I_CODE; outstring("LAHF") endif;
//   when qSAHF: ------- SAHF -----------------------------  Format 1
//        --- Q-Code:
//        outstring("SAHF   ");
//        if Alist then I_CODE; outstring("SAHF") endif;
//   when qFDEST: ------ FDEST   fixno jmp ----------------  Format 6
//        --- Q-Code:
//        outstring("FDEST ?FIX") outword(qi qua Qfrm6.aux);
//        if Alist
//        then I_CODE; putpos(sysout,I_LABEL); outstring("?FIX");
//             outword(qi qua Qfrm6.aux); outchar(':')
//        endif;
//   when qBDEST: ------ BDEST   labno rela jmp ------------  Format 6
//        --- Q-Code:
//        outstring("BDEST ");
//        outword(qi qua Qfrm6.aux); outchar(',');
//        outword(qi qua Qfrm6.rela.val);
//        if Alist
//        then I_CODE; putpos(sysout,I_LABEL); outstring("?LAB");
//             outword(qi qua Qfrm6.aux); outchar(':')
//        endif;
//   when qLABEL: ------ LABEL   subc fixno smbx ----------  Format 6
//        --- Q-Code:
//        outstring("LABEL ");
//        case 0:3 (subc)
//        when qBPROC: outstring("BPROC,")
//        when qEPROC: outstring("EPROC,")
//        endcase;
//        outword(qi qua Qfrm6.aux);
//        if Alist
//        then I_CODE; putpos(sysout,I_LABEL);
//             fix.val:=qi qua Qfrm6.aux;
//             ix.val:=qi qua Qfrm6.smbx;
//             case 0:3 (subc)
//             when qBPROC: if ix.val > 0
//                  then repeat while qi.reg<>0 -- counts alignment bytes
//                       do setpos(sysout,I_OPERATOR); outstring("NOP");
//                          I_LINE; putpos(sysout,I_LABEL); qi.reg:=qi.reg-1
//                       endrepeat;
//                       outsymb(ix); setpos(sysout,I_OPERATOR);
//                       outstring("PROC  FAR");
//                       I_LINE; setpos(sysout,I_OPERATOR);
//                       outstring("PUBLIC   "); outsymb(ix);
//                  else outstring("?FIX");
//                       outword(fix.val);
//                       setpos(sysout,I_OPERATOR);
//                       outstring("PROC  FAR");
//                  endif;
//             when qEPROC: if ix.val > 0
//                  then outsymb(ix); setpos(sysout,I_OPERATOR);
//                       outstring("ENDP");
//                  else outstring("?FIX");
//                       outword(fix.val);
//                       setpos(sysout,I_OPERATOR); outstring("ENDP");
//                  endif;
//             otherwise if ix.val > 0
//                  then outsymb(ix); setpos(sysout,I_OPERATOR);
//                       outstring("LABEL  FAR"); I_LINE;
//                       setpos(sysout,I_OPERATOR);
//                       outstring("PUBLIC   "); outsymb(ix);
//                  else outstring("?FIX");
//                       outword(fix.val);
//                       setpos(sysout,I_OPERATOR);
//                       outstring("LABEL  NEAR");
//                  endif;
//             endcase;
//        endif;
//   when qJMP: -------- JMP     subc addr dst ------------  Format 5
//        --- Q-Code:
//        outstring("JMP    "); edqcond(subc);
//        outadr(qi qua Qfrm5.addr); dst:=qi qua Qfrm5.dst;
//        if dst=none then outstring(",none")
//        elsif dst.fnc=qBDEST
//        then outstring(",BDST"); outword(dst.aux);
//        elsif dst.fnc=qFDEST
//        then outstring(",FIX") outword(dst.aux) endif;
//        if Alist
//        then I_CODE; -- aux field set by coasm
//             case 0:5 (qi qua Qfrm5.aux)
//             when 0: -- short conditional
//                  edCondJMP(subc);
//             when 1: -- short unconditional
//                  outstring("JMP    SHORT ");
//             when 2: -- near conditional
// %+E              edCondJMP(subc); outstring("NEAR PTR ");
// %-E              edCondJMP(NotQcond(subc)); outstring("$+5"); I_LINE;
// %-E              outstring("JMP    NEAR PTR ");
//             when 3: -- near unconditional
//                  outstring("JMP    NEAR PTR ");
//             when 4: -- far conditional
// %-E              edCondJMP(NotQcond(subc)); outstring("$+7"); I_LINE;
// %-E              outstring("JMP    FAR PTR ");
//             when 5: -- far unconditional
// %-E              outstring("JMP    FAR PTR ");
//             endcase
//             if dst=none then outadr(qi qua Qfrm5.addr)
//             elsif dst.fnc=qBDEST
//             then outstring("?LAB"); outword(dst.aux);
//             elsif dst.fnc=qFDEST
//             then outstring("?FIX"); outword(dst.aux);
//             else outadr(qi qua Qfrm5.addr) endif;
//        endif;
//   when qJMPM: ------- JMPM    opr ----------------------  Format 3
//        --- Q-Code:
//        outstring("JMPM   "); outopr(qi qua Qfrm3.opr);
//        if Alist
//        then I_CODE; outstring("JMP    WORD PTR ");
//             outopr(qi qua Qfrm3.opr);
//        endif;
// %-E when qJMPFM: ------ JMPFM   opr --------------------  Format 3
// %-E    --- Q-Code:
// %-E    outstring("JMPFM  "); outopr(qi qua Qfrm3.opr);
// %-E    if Alist
// %-E    then I_CODE; outstring("JMP    DWORD PTR ");
// %-E         outopr(qi qua Qfrm3.opr);
// %-E    endif;
//   when qCALL: ------- CALL    subc pxlng addr ----------  Format 5
//        --- Q-Code:
//        outstring("CALL   "); outword(qi qua Qfrm5.subc);
//        outchar(','); outword(qi qua Qfrm5.reg);
//        outchar(','); outword(qi qua Qfrm5.aux);
//        outchar(',');
//       if qi qua Qfrm5.addr.kind=reladr -- Special Case: CALL [reg]
//       then outopr(qi qua Qfrm5.addr);
//       else
//             outadr(qi qua Qfrm5.addr);
//       endif;
//        if Alist
//        then I_CODE;
// %-E         if (subc=qXNX) or (subc=qC)
// %-E         then outstring("PUSH   SS"); I_LINE;
// %-E              outstring("POP    DS"); I_LINE;
// %-E         endif;
//             if subc=qC
//             then EdCall(qi qua Qfrm5.addr);
//                  cnst.val:=qi qua Qfrm5.aux;
//                  if cnst.val > 0
//                  then I_LINE;
// %-E                   outstring("ADD    SP,");
//                      outstring("ADD    ESP,");
//                       outword(cnst.val);
//                  endif;
//             elsif subc=qXNX
//             then EdCall(qi qua Qfrm5.addr);
//                  cnst.val:=qi qua Qfrm5.aux;
//                  if cnst.val > 0
//                  then I_LINE;
// %-E                   outstring("ADD    SP,");
//                      outstring("ADD    ESP,");
//                       outword(cnst.val);
//                  endif;
//                  I_LINE; outstring("INC    AX");
// %-E              I_LINE; outstring("JNZ    $+");
// %-E              outword(if EnvCSEG=CSEGID then 5 else 7);
//                 I_LINE; outstring("JNZ    $+7");
//                  I_LINE; EdCall(X_SSTAT);
//                  I_LINE; outstring("DEC    AX");
//             elsif subc=qKNL
//             then 
// %-E              outstring("MOV    AX,");
//                 outstring("MOV    EAX,");
//                  outword(qi qua Qfrm5.addr.knlx.val); I_LINE;
// %-E              I_LINE; outstring("CALLF  144:0");
//                 I_LINE; outstring("CALLF  7:0");
//                  I_LINE; outstring("JNC    $+??"); I_LINE;
// %-E              outstring("MOV    SS:OFFSET DGROUP:G@OSSTAT,AX");
//                 outstring("MOV    G@OSSTAT,EAX");
//                  I_LINE;
// %-E              outstring("MOV    SS:OFFSET DGROUP:G@KNLAUX,AX");
//                 outstring("MOV    G@KNLAUX,EAX");
//                  cnst.val:=qi qua Qfrm5.aux;
//                  if cnst.val > 0
//                  then I_LINE;
// %-E                   outstring("ADD    SP,");
//                      outstring("ADD    ESP,");
//                       outword(cnst.val);
//                  endif;
// %-E         elsif subc=qOS2
// %-E         then EdCall(qi qua Qfrm5.addr); I_LINE;
// %-E              outstring("MOV    SS:OFFSET DGROUP:G@OSSTAT,AX");
//             else EdCall(qi qua Qfrm5.addr) endif;
//        endif;
//   when qADJST: ------ ADJST   const --------------------  Format 2
//        --- Q-Code:
//        outstring("ADJST  "); outword(qi qua Qfrm2.aux.val);
//        if Alist then I_CODE endif;
//   when qENTER: ------ ENTER   const --------------------  Format 2
//        --- Q-Code:
//        cnst:=qi qua Qfrm2.aux;
//        outstring("ENTER  "); outword(cnst.val);
//        if Alist
//        then I_CODE;
// %-E         if qi.subc=0
// %-E         then outstring("POP    AX"); I_LINE;
// %-E              outstring("PUSH   CS"); I_LINE;
// %-E              outstring("PUSH   AX"); I_LINE endif;
// %-E         if CPUID >= iAPX186
// %-E         then
//                  outstring("ENTER  "); outword(cnst.val);
// %-E         else outstring("PUSH   BP"); I_LINE;
// %-E              outstring("MOV    BP,SP");
// %-E              if cnst.val>0
// %-E              then I_LINE; outstring("SUB    SP,");
// %-E                   outword(cnst.val);
// %-E              endif;
// %-E         endif;
//        endif;
//   when qLEAVE: ------ LEAVE   const --------------------  Format 2
//        --- Q-Code:
//        cnst:=qi qua Qfrm2.aux;
//        outstring("LEAVE  "); outword(cnst.val);
//        if Alist
//        then I_CODE;
// %-E         if CPUID >= iAPX186
// %-E         then
//                  outstring("LEAVE");
// %-E         else if cnst.val>0
// %-E              then outstring("MOV    SP,BP"); I_LINE endif;
// %-E              outstring("POP    BP");
// %-E         endif;
//        endif;
//   when qRET: -------- RET     const --------------------  Format 2
//        --- Q-Code:
//        outstring("RET    "); outword(qi qua Qfrm2.aux.val);
//        if Alist
//        then I_CODE;
// %-E         outstring("RETF   ");
//            outstring("RET    ");
//             outword(qi qua Qfrm2.aux.val);
//        endif;
// %-E when qDOS2: ------- DOS2 ---------------------------  Format 1
// %-E    --- Q-Code:
// %-E    outstring("DOS2");
// %-E    if Alist
// %-E    then I_CODE;
// %-E         outstring("INT    33"); I_LINE;
// %-E         outstring("JNC    $+6"); I_LINE;
// %-E         outstring("MOV    SS:OFFSET DGROUP:G@OSSTAT,AL");
// %-E    endif;
//   when qINT: -------- INT     const --------------------  Format 2
//        --- Q-Code:
//        outstring("INT    "); outword(qi qua Qfrm2.aux.val);
//        if Alist
//        then I_CODE;
//             outstring("INT    ");outword(qi qua Qfrm2.aux.val);
//        endif;
//   when qIRET: ------- IRET -----------------------------  Format 1
//        --- Q-Code:
//        outstring("IRET");
//        if Alist then I_CODE; outstring("IRET") endif;
//   when qFLD: -------- FLD     fSD fmf opr --------------  Format 3
//        EdFLD(qi);
//   when qFLDC: ------- FLDC    sreg fmf val -------------  Format 3+
//               ------- FLDC    fSD  fmf val -------------  Format 3+
//        EdFLDC(qi);
//   when qFDUP: ------- FDUP    fSD ----------------------  Format 1
//        EdFDUP(qi);
//   when qFST: -------- FST     fSD fmf opr --------------  Format 3
//        EdFST(qi);
//   when qFSTP: ------- FSTP    fSD fmf opr --------------  Format 3
//        EdFSTP(qi);
//   when qFPUSH: ------ FPUSH   fSD fmf ------------------  Format 1
//        EdFPUSH(qi);
//   when qFPOP: ------- FPOP    fSD fmf ------------------  Format 1
//        EdFPOP(qi);
//   when qFLDCK: ------ FLDCK   subc ---------------------  Format 1
//        EdFLDCK(qi);
//   when qFMONAD: ----- FMONAD  subc fSD -----------------  Format 1
//        EdFMONAD(qi);
//   when qFDYAD: ------ FDYAD   subc fSD -----------------  Format 1
//        EdFDYAD(qi);
//   when qFDYADM: ----- FDYADM  subc fmf fSD opr ---------  Format 4
//        EdFDYADM(qi);
//   when qWAIT: ------- WAIT -----------------------------  Format 1
//        --- Q-Code:
//        outstring("WAIT");
//        if Alist then I_CODE; outstring("WAIT") endif;
//   when qEVAL: ------- EVAL -----------------------------  Format 1
//        --- Q-Code:
//        outstring("EVAL");
//        if Alist then I_CODE endif;
//   when qLINE: ------- LINE    subc const ---------------  Format 2
//        putpos(sysout,I_LABEL);
//        case 0:2 (subc)
//        when 0:    outstring(";     SOURCE LINE ");
//        when qDCL: outstring(";     DECLARATION LINE ");
//        when qSTM: outstring(";     STATEMENT LINE ");
//        endcase;
//        outint(qi qua Qfrm2.aux.val);
//        if DEBMOD > 2 then if subc=qSTM
//        then I_LINE; outstring("POPF");
//             I_LINE; outstring("PUSHF"); endif endif;
//   endcase;

//   if OvflTail then I_LINE; outstring("INTO") endif;
//   I_LINE; X1: putpos(sysout,0);
// end;
	}
}
