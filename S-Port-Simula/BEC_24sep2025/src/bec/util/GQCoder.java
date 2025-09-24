package bec.util;

import bec.virtualMachine.SVM_SJEKK_DETTE;

public class GQCoder {

	public static void GQeMultc(int cnst) {
		IO.println("GQCoder.GQeMultc: " + cnst);
//	%+E begin integer n; n:=if cnst>0 then cnst else -cnst;
//	%+E   if n > 10 then goto LX endif;
//	%+E   case 0:10 (n)
//	%+E   when 10: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX);
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf2(qMOV,0,qEDX,cVAL,qEAX);
//	%+E           Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX); goto L6;
//	%+E   when 6: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX);
//	%+E           PreMindMask:=wOR(PreMindMask,uEAX); Qf2(qMOV,0,qEDX,cVAL,qEAX);
//	%+E       L6: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX);
//	%+E           Qf2(qDYADR,qADDF,qEAX,cVAL,qEDX);
//	%+E           if cnst<0 then Qf2(qMONADR,qNEGF,qEAX,cVAL,0) endif;
//	%+E   when 8:     Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX); goto L4;
//	%+E   when 4: L4: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX); goto L2;
//	%+E   when 2: L2: Qf2(qDYADR,qADDF,qEAX,cVAL,qEAX); goto L1;
//	%+E   when 1: L1: if cnst<0 then Qf2(qMONADR,qNEGF,qEAX,cVAL,0) endif;
//	%+E   otherwise LX: Qf2(qLOADC,0,qEDX,cVAL,cnst);
//	%+E                 NotMindMask:=uEDX; Qf2(qTRIADR,qIMULF,qEDX,cVAL,0);
//	%+E   endcase;
		Global.PSEG.emit(new SVM_SJEKK_DETTE(), "GQCoder.GQeMultc: ");			
		Util.IERR("");
	}

}
