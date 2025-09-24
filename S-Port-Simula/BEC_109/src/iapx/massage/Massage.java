package iapx.massage;

import static iapx.qPkt.Qfrm2.qADJST;
import static iapx.qPkt.Qfrm2.qENTER;
import static iapx.qPkt.Qfrm2.qINT;
import static iapx.qPkt.Qfrm2.qLEAVE;
import static iapx.qPkt.Qfrm2.qLINE;
import static iapx.qPkt.Qfrm2.qRET;
import static iapx.qPkt.Qfrm2.qRSTRB;
import static iapx.qPkt.Qfrm2.qRSTRW;
import static iapx.qPkt.Qfrm2.qSHIFT;
import static iapx.qPkt.Qfrm2.qXCHG;
import static iapx.qPkt.Qfrm2b.qPUSHC;
import static iapx.util.Global.*;

import iapx.qPkt.Qpkt;
import iapx.util.Global;
import iapx.util.Option;
import iapx.util.Util;

public class Massage {

	public static boolean QinstrIsDeleted(Qpkt qi) { return qi.isDeleted();	}

	public static void RemoveQinstr(Qpkt qi) { qi.remove();	}

	public static void deleteLastQ() { DeleteQinstr(qlast); }

	public static void DeleteQinstr(Qpkt qi) {
		if(qi == null) Util.IERR("DeleteQinstr(none)");
		RemoveQinstr(qi);
	}
	
	public static void DeleteQPosibJ(Qpkt qi) { qi.deletePosibJump(); }


	public static void DOMASSAGE(Qpkt qi) {
		//Util.IERR("NOT IMPL: SEE MASSAGE.DEF");
//		IO.println("Qfunc.DOMASSAGE: DENNE MÅ IMPLEMENTERES SENERE - SEE MASSAGE.DEF");
//		Thread.dumpStack();
		switch(qi.fnc) {
//    ----  K_Qfrm1 **********************************************
//    case qFDYAD:
//%+E        if NUMID <> WTLx167 then
//            if MASSLV > 19 then mFDYAD(qi)      endif
//%+E        endif
//    case qFPOP:        if MASSLV > 18 then mFPOP(qi)       endif
//%+S   case qPUSHR:       if MASSLV >  1 then mPUSHR(qi)      endif
//%-S   case qPUSHR:                           mPUSHR(qi)
//		case qPOPR:        if(Option.MASSLV >  4) mPOPR(qi); break;
//%-S   case qPOPR:                            mPOPR(qi)
//%+C   case qWAIT, qEVAL, qTSTOFL, qLAHF,  qSAHF, qCWD,    -- nothing
//%+C        qFDUP, qIRET, qDOS2, qFLDCK, qFMONAD, qFPUSH:  -- nothing
//    ---- K_Qfrm2 , K_Qfrm2b ************************************
//    case qMONADR:      if MASSLV > 12 then mMONADR(qi)     endif
//    case qTRIADR:      if MASSLV > 15 then mTRIADR(qi)     endif
//    case qCONDEC:      if MASSLV > 20 then mCondition(qi)  endif
//    case qMOV:         if MASSLV > 10 then mMOV(qi)        endif
//    case qDYADR:       if MASSLV > 13 then mDYADR(qi)      endif
//%+S   case qPOPK:        if MASSLV >  3 then mPOPK(qi)       endif
//%-S   case qPOPK:                            mPOPK(qi)
//    case qDYADC:    if qi . kind <> K_Qfrm2b then
//                       if MASSLV > 14 then mDYADC(qi)      endif endif
//%+S %-E   case qLOADSC:  if MASSLV >  7 then mLOADSC(qi)     endif
//%+S       case qLOADC:   if MASSLV >  6 then mLOADC(qi)      endif
//%-S %-E   case qLOADSC:                      mLOADSC(qi)
//%-S       case qLOADC:                       mLOADC(qi)
		
		case qRSTRB, qRSTRW, qXCHG, qSHIFT,  qRET,   qINT,  //-- nothing
             qADJST, qENTER, qLEAVE, qLINE, qPUSHC:         //-- nothing
        	 break;
		
//    ----  K_Qfrm3  ----  mOPR except for FLDC ******************
//%+S   case qLOADA:  mOPR(qi); if MASSLV >  9 then mLOADA(qi)  endif
//%-S   case qLOADA:  mOPR(qi);                     mLOADA(qi)
//    case qSTORE:  mOPR(qi); if MASSLV > 11 then mSTORE(qi)  endif
//    case qTRIADM: mOPR(qi); if MASSLV > 16 then mTRIADM(qi) endif
//%+C   case qFLDC:           -- nothing
		
		case qJMPM, qPUSHA, qXCHGM,  qFLD, qFST, qFSTP, qDYADM,
        	 qBOUND, qDYADMR, qMONADM,
        	 //---- K_Qfrm4 , K_Qfrm4b, K_Qfrm4c  ---- mOPR for all *******
        	 qFDYADM, qPUSHM, qMOVMC, qDYADMC:
        		 IO.println("Massage: DENNE MÅ SKRIVES SEINERE: mOPR");
        		 // mOPR(qi);
				 break;
		
//%+S   case qLOAD:   mOPR(qi); if MASSLV >  8 then mLOAD(qi)  endif
//%-S   case qLOAD:   mOPR(qi);                     mLOAD(qi)
//%-E   case qLDS,
//%-E        qLES:    mOPR(qi); if MASSLV > 30 then mLSR(qi)   endif
//%+S   case qPOPM:   mOPR(qi); if MASSLV >  5 then mPOPM(qi)  endif
//%-S   case qPOPM:   mOPR(qi);                     mPOPM(qi)
//    ----  K_Qfrm5 **********************************************
//    case qJMP:         if MASSLV > 20 then mCondition(qi)  endif
		case qCALL: break; // nothing
//    ----  K_Qfrm6 **********************************************
//    case qFDEST:       if MASSLV > 21 then mFDEST(qi)      endif
		case qBDEST, qLABEL: break; // nothing
		default:
			IO.println("Massage.DOMASSAGE: Wrong Qcode.fnc in Masseur: " + qi.fnc);
			//Util.IERR("Wrong Qcode.fnc in Masseur: " + qi.fnc);
		}
	}


}
