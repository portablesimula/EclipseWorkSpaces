package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.descriptor.RecordDescr;
import bec.util.Global;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.virtualMachine.SVM_ADD;
import bec.virtualMachine.SVM_MULT;
import bec.virtualMachine.SVM_LOADC;

public abstract class DSIZE extends Instruction {
	
	/**
	 * addressing_instruction ::= dsize structured_type
	 * 
	 *		structured_type ::= record_tag:tag
	 *
	 * dsize structured_type
	 * 
	 * force TOS value; check TOS type(INT);
	 * pop;
	 * push( VAL, SIZE, "size(type with mod. rep.count)" );
	 * 
	 * The structured type must be prefixed with a "DYNAMIC" type (see 4.3.6),
	 * and it must contain an indefinite repetition, otherwise: error.
	 */
	public static void ofScode() {
		Tag tag = Tag.ofScode();
		RecordDescr fixrec = (RecordDescr) tag.getMeaning();
//		System.out.println("DSIZE.ofScode: fixrec="+fixrec+"  nbrep="+fixrec.nbrep);
		if(fixrec.nbrep != 0) {
//			CTStack.dumpStack("DSIZE.ofScode: ");
			int n = fixrec.nbrep;
			CTStack.checkTosInt();
			CTStack.pop();
			IntegerValue nbrepValue = IntegerValue.of(Type.T_INT, n);
			Global.PSEG.emit(new SVM_LOADC(Type.T_INT, nbrepValue), "DSIZE'nbrep: ");
			Global.PSEG.emit(new SVM_MULT(), "MULT: ");
			IntegerValue fixValue = IntegerValue.of(Type.T_INT, fixrec.size);
			Global.PSEG.emit(new SVM_LOADC(Type.T_INT, fixValue), "DSIZE'recSize: ");
			Global.PSEG.emit(new SVM_ADD(), "ADD: ");
			
			CTStack.pushTempVAL(Type.T_SIZE, 1, "DSIZE: ");
		} else {
			Util.IERR("Illegal DSIZE on: " + fixrec);
//           GQpop; itm.int:=0; pushCoonst(T_SIZE,itm);
		}
	}

}
