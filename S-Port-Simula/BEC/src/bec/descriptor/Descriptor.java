/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.descriptor;

import java.io.IOException;

import bec.instruction.Instruction;
import bec.util.AttributeOutputStream;
import bec.util.Global;
import bec.util.Tag;
import bec.util.Util;

/// Descriptor.
///
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/descriptor/Descriptor.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public abstract class Descriptor extends Instruction {
	
	/// Object kind code
	int kind;  
	
	/// The tag. Used to lookup descriptors
	public Tag tag; 

	/// Create a new Descriptor
	/// @param kind the Object kind code
	/// @param tag used to lookup descriptors
	public  Descriptor(int kind, Tag tag) {
		this.kind = kind;
		this.tag = tag;
		if(tag != null) Global.intoDisplay(this, tag.val);
	}
	
	/// Utility print method.
	/// @param indent number of spaces leading the lines
	///
	public void print(final String indent) {
		Util.IERR("Method printTree need a redefinition in "+this.getClass().getSimpleName());
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}


}
