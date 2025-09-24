package iapx.CTStack;

import iapx.Kind;
import iapx.descriptor.ProfileDescr;
import iapx.records.DataType;
import iapx.util.Type;

//Record ProfileItem:StackItem;
//begin ref(ProfileDescr) spc;
//      range(0:MaxPar) nasspar;
//end;
public class ProfileItem extends StackItem {
	Type type;
	public ProfileDescr spc;
	public int nasspar;

	public ProfileItem(Type type, ProfileDescr spc) {
		super(type);
//	export ref(Object) prf;
//	begin prf = FreeObj(K_ProfileItem);
//	      if prf <> none
//	      then FreeObj(K_ProfileItem) = prf qua FreeObject.next;
//	      else L: prf = PoolNxt; PoolNxt = PoolNxt+size(ProfileItem);
//	           if PoolNxt >= PoolBot
//	           then PALLOC(size(ProfileItem),prf); goto L endif;
//	%+D        ObjCount(K_ProfileItem) = ObjCount(K_ProfileItem)+1;
//	      endif;
		
		this.kind = Kind.K_ProfileItem; this.type = type;
//	      prf qua StackItem.suc = none; prf qua StackItem.pred = none;
		this.spc = spc;
//	      prf qua ProfileItem.nasspar = 0;
//	%+C   if type=T_NOINF then IERR("No info TYPE-1") endif;
	}

	public String toString() {
		return "ProfileItem: type=" + type + ", nasspar=" + nasspar + " " + spc;
	}

}
