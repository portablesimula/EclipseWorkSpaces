package bec.compileTimeStack;

import bec.descriptor.ProfileDescr;
import bec.util.Type;

public class ProfileItem extends CTStackItem {
	Type type;
	public ProfileDescr spc;
	public int nasspar;
	
	public ProfileItem(Type tagVoid, ProfileDescr spec) {
		this.mode = Mode.PROFILE;
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
		return edMode() + "PROF: " + spc;
	}

}
