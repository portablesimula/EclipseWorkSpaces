/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.value;

import bec.descriptor.Descriptor;
import bec.scode.Type;

/// BooleanValue.
/// 
///		boolean_value ::= true | false
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/value/FixupAddress.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class FixupAddress extends ProgramAddress {
	
	public FixupAddress(Type type, Descriptor descr) {
		super(type, null, 0);
	}

	public void setAddress(ProgramAddress paddr) {
		this.segID = paddr.segID;
		this.setOfst(paddr.getOfst());
	}
}
