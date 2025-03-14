package bec.instruction;

import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;

public abstract class DELETE extends Instruction {

	/**
	 * delete_statement ::= delete from:tag
	 * 
     * check stacks empty;
	 */
	public static void ofScode() {
		Scode.dumpBytes(10);
		Tag tag = Tag.ofScode();
//		Util.IERR("NOT IMPL");
	}

}
