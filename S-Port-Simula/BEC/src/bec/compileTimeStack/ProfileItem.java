/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.compileTimeStack;

import bec.descriptor.ProfileDescr;
import bec.util.Type;

/// Profile Item.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/compileTimeStack/ProfileItem.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class ProfileItem extends CTStackItem {
	Type type;
	public ProfileDescr spc;
	public int nasspar;
	
	public ProfileItem(Type tagVoid, ProfileDescr spec) {
//		this.mode = Mode.PROFILE;
		this.type = tagVoid;
		this.spc = spec;
		this.nasspar = 0;
	}

	@Override
	public CTStackItem copy() {
		ProfileItem addr = new ProfileItem(type, spc);
		addr.nasspar = nasspar;
		return addr;
	}

	public String toString() {
//		return edMode() + "PROF: " + spc;
		return "PROF: " + spc;
	}

}
