package bec.compileTimeStack;

import bec.descriptor.ProfileDescr;
import bec.util.Type;

public class ProfileItem extends StackItem {
	Type type;
	public ProfileDescr spc;
	public int nasspar;
	
	public ProfileItem(Type tagVoid, ProfileDescr spec) {
//	      prf.kind:=K_ProfileItem;
		this.type = tagVoid;
//	      prf qua StackItem.size:=TTAB(type).nbyte;
//	      prf qua StackItem.suc:=none; prf qua StackItem.pred:=none;
		this.spc = spec;
		this.nasspar = 0;
//	%+C   if type=T_NOINF then IERR("No info TYPE-1") endif;
	}

	public String toString() {
		return ""+spc;
	}

}
