package bec.descriptor;

import java.io.IOException;

import bec.AttributeOutputStream;
import bec.instruction.Instruction;
import bec.util.Global;
import bec.util.Tag;
import bec.util.Util;

//Record Object;
//begin range(0:MaxByte) kind;   --- Object kind code
//      range(0:MaxType) type; 
//end;
public class Descriptor extends Instruction {
	int kind;  // Object kind code
	public Tag tag; 

	public  Descriptor(int kind, Tag tag) {
		this.kind = kind;
		this.tag = tag;
		if(tag != null) Global.intoDisplay(this, tag.val);
	}
	

	/**
	 * Utility print method.
	 * 
	 * @param indent number of spaces leading the lines
	 */
	public void print(final String indent) {
		Util.IERR("Method printTree need a redefinition in "+this.getClass().getSimpleName());
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

//	public static Descriptor read(AttributeInputStream inpt, int kind) throws IOException {
//		Util.IERR("Static Method 'readObject' needs a redefiniton");
//		return(null);
//	}


}
