package PREV.syntaxClass.instruction;

import java.util.Vector;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Scode;
import bec.value.MemAddr;
import bec.virtualMachine.SVM_SWITCH;

public class SWITCH extends PREV_Instruction {
	int tag;
	int size;
	MemAddr[] DESTAB;
	
	/**
	 * forward_jump ::= switch switch:newtag size:number
	 */
	public SWITCH() {
		tag = Scode.inTag();
		size = Scode.inNumber();
//		if(size >= MxpSdest) Util.ERROR("Too large Case-Statement");
		DESTAB = new MemAddr[size];
	}

	@Override
	public void doCode() {
//        InTag(%tag%);
//%+D        ndest:=InputNumber;
//        sw:=NEWOBJ(K_SwitchDescr,size(SwitchDescr));
//        sw.ndest:=ndest.val; sw.nleft:=ndest.val;
//        i.val:=0; sw.swtab:=NewFixAdr(DSEGID,i); IntoDisplay(sw,tag);
		CTStack.checkTosInt();
//        if TOS.type < T_WRD2 then GQconvert(T_WRD2) endif;
//        a:=sw.swtab;
		int qEBX = 1; // MÅ RETTES
		CTStack.getTosAdjustedIn86(qEBX);
		CTStack.pop();
//
//%+D        if IDXCHK <> 0 then --- pje 22.10.90
//%+D           PreMindMask:=wOR(PreMindMask,uBX);
//%+DE          Qf2(qDYADC,qCMP,qEBX,cVAL,ndest.val);
//%+DE          PreReadMask:=uBX;
//%+D           LL:=ForwJMP(q_WLT);
//%+D           Qf5(qCALL,0,0,0,X_ECASE); -- OutOfRange ==> ERROR
//%+DE          Qf2(qLOADC,0,qEBX,cVAL,0);
//%+D           PreReadMask:=uBX;
//%+DE          PreMindMask:=wOR(PreMindMask,uBX);
//%+D           DefFDEST(LL);
//%+D        endif; --- pje 22.10.90
//
//%+E        a.sibreg:=bOR(bOR(128,bEBX),iEBX); -- swtab+[4*EBX] 
//        Qf3(qJMPM,0,0,0,a);
      	Global.PSEG.emit(new SVM_SWITCH(DESTAB), "");
      	Global.PSEG.dump("SWITCH: ");
	}
	
	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "SWITCH " + Scode.edTag(tag) + " " + size;
	}
	

}
