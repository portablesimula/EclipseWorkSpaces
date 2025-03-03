package PREV.syntaxClass.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Scode;

public class DEREF extends PREV_Instruction {
	
	/**
	 * addressing_instruction ::= deref
	 * 
	 * 
	 * check TOS ref;
	 * TOS.MODE := VAL; TOS.TYPE := GADDR;
	 * TOS is modified to describe the address of the area.
	 * 
	 *     (TOS) ------------------------------------------,
     *                              REF                     |
     *                                                      |
     *                                                      |
     *  The resulting           .==================.        V
     *       TOS ---------------|--> GADDR VALUE --|------->.========,
     *  after deref             '=================='        |        |
     *                                                      |        |
     *                                                      '========'
	 */
	public DEREF() {
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();

		CTStack.checkTosRef();
//        adr:=TOS;
//%+S        if SYSGEN <> 0
//%+S        then if adr.repdist <> (TTAB(adr.type).nbyte)
//%+S             then WARNING("DEREF on parameter") endif;
//%+S        endif;

		CTStack.assertAtrStacked();
		CTStack.pop();
		CTStack.pushTemp(Scode.TAG_GADDR);

//		Global.PSEG.dump();
//		Util.IERR(""+this);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return "DEREF";
	}
	

}
