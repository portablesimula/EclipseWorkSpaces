package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.util.Global;
import bec.util.Tag;
import bec.util.Type;

public abstract class DELETE extends Instruction {

	/**
	 * delete_statement ::= delete from:tag
	 * 
     * check stacks empty;
     * 
     * All tags defined with values greater than or equal to from:tag are made undefined, i.e. the
     * corresponding descriptors may be released. The tags become available for reuse. The stack and all
     * saved stacks must be empty, otherwise: error.
	 */
	public static void ofScode() {
//		Scode.dumpBytes(10);
		Tag tag = Tag.ofScode();
//		System.out.println("DELETE.ofScode: " + tag);
		
		// check stacks empty;
		CTStack.checkStackEmpty();
//		System.out.println("DELETE.ofScode: SJEKK DETTE SEINERE EN GANG: check stacks empty;");

		int startTag = tag.val;
//		Global.dumpDISPL("DELETE.ofScode: ", startTag -2);

		for(int t=startTag;t<Global.DISPL.size();t++) {
//			System.out.println("DELETE.ofScode: DELETE: " + t + ": " + Global.DISPL.get(t));
			Global.DISPL.set(t, null);
			Type.removeFromTMAP(t);
		}
	}

}
