package PREV.syntaxClass.instruction;

import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.Coonst;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.virtualMachine.SVM_NEG;
import bec.virtualMachine.SVM_NOT_IMPL;

public class INDEX extends PREV_Instruction {
	int instr;
	
	/**
	 * addressing_instruction ::= ::= index | indexv
	 * 
	 * force TOS value; check TOS type(INT);
	 * check SOS ref;
	 * pop;
	 * 
	 * TOS.OFFSET := SOS.OFFSET ++ "SOS.SIZE * value(TOS)"
	 * 
	 * SOS is considered to describe an element of a repetition, and the purpose of the instruction is to
	 * select one of the components of the repetition by indexing relative to the current position. The
	 * effect may perhaps best be understood by considering an infinite array A with elements of
	 * SOS.TYPE. The array is placed so that element A(0) is the quantity described by SOS. After
	 * index the stack top will describe A(N), where N is the value of TOS. No bounds checking should
	 * be performed.
	 */
	public INDEX(int instr) {
		this.instr = instr;
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();

		CTStack.checkTosInt();
		CTStack.checkSosRef();
//        adr:=TOS.suc; repdist:=adr.repdist;
		Address adr = (Address) CTStack.TOS.suc;
		int repdist = adr.repdist;
		if(repdist == 0) Util.IERR("PARSE.INDEX: Not info type");
//        if TOS.kind=K_Coonst
		StackItem tos = CTStack.TOS;
		if(tos instanceof Coonst itm) {
//        then itm:=TOS qua Coonst.itm;
			IntegerValue ival = (IntegerValue) itm.value;
			adr.offset = adr.offset + (repdist * ival.value);
			Util.GQpop();
             if(adr.atrState == Address.State.FromConst) {
            	 adr.atrState = Address.State.NotStacked;
//            	 qPOPKill(AllignFac);
            	 Global.PSEG.emit(new SVM_NOT_IMPL(), "INDEX-1");
             }
		} else {
//%+E             if TOS.type <> T_WRD4 then GQconvert(T_WRD4) endif;
//%+E             GetTosAdjustedIn86(qEAX); Pop; AssertObjStacked;
//%+E             GQeMultc(repdist); -- EAX:=EAX*repdist
//%+E             if    adr.AtrState=FromConst then qPOPKill(4)
//%+E             elsif adr.AtrState=Calculated
//%+E             then Qf1(qPOPR,qEBX,cVAL);
//%+E                  Qf2(qDYADR,qADDF,qEAX,cVAL,qEBX);
//%+E             endif;
//%+E             Qf1(qPUSHR,qEAX,cVAL); adr.AtrState:=Calculated;
			CTStack.pop();
			Global.PSEG.emit(new SVM_NOT_IMPL(), "INDEX-2");
//        endif;
		}
		CTStack.checkTosRef();
		
		if(instr == Scode.S_INDEXV) Util.GQfetch("INDEXV");

//		Global.PSEG.dump();
//		CTStack.dumpStack();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr);
	}
	

}
