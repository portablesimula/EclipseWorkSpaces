package bec.compileTimeStack;

import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;
import bec.virtualMachine.RTAddress;
import bec.virtualMachine.SVM_NOT_IMPL;
import bec.virtualMachine.SVM_POP2REG;
import bec.virtualMachine.SVM_PUSH;
import bec.virtualMachine.SVM_PUSHC;

public class CTStack {
	
//  --- Current Stack ---
//  range(0:8) StackDepth87; -- initial(0)
	public static StackItem TOS; // Top of Compile-time stack
	static StackItem BOS; // Bot of Compile-time stack
	public static StackItem SAV; // Last Compile-time stack-item for which
                          		 // the corresponding Runtime-item is saved.
                          		 // NOTE: SAV =/= none implies TOS =/= none


	public static void push(StackItem s) {
		if(s.suc != null || s.pred != null) STKERR("CODER.CheckPush");
		if(TOS == null) {
			TOS = BOS = s; s.suc = null;
		} else {
			s.suc = TOS;
			TOS = s.suc.pred = s;
		}
//	%+D   if TraceMode > 1 then DumpStack endif;
	} // endmacro;
	
	public static void pushTemp(Type type, int reg, int count, String comment) {
		Temp tmp = new Temp(type, reg, count, comment);
//		Util.IERR("NOT IMPL");
//	      tmp.kind:=K_Temp; tmp.type:=type;
//	      tmp qua StackItem.size:=TTAB(type).nbyte;
//	%+C   if TTAB(type).nbyte=0 then IERR("No info TYPE-3") endif;
		push(tmp);
	}
	
	public static void pushCoonst(Type type, Value value) {
//	begin ref(Object) cns;
//	      cns:=FreeObj(K_Coonst);
//	      if cns <> none
//	      then FreeObj(K_Coonst):=cns qua FreeObject.next;
//	      else L: cns:=PoolNxt; PoolNxt:=PoolNxt+size(Coonst);
//	           if PoolNxt >= PoolBot
//	           then PALLOC(size(Coonst),cns); goto L endif;
//	%+D        ObjCount(K_Coonst):=ObjCount(K_Coonst)+1;
//	      endif;
//	      cns.kind:=K_Coonst; cns.type:=type;
//	      cns qua StackItem.repdist:=TTAB(type).nbyte;
//	      cns qua StackItem.suc:=none; cns qua StackItem.pred:=none;
//	      cns qua Coonst.itm:=itm;
//	%+C   if TTAB(type).nbyte=0 then IERR("No info TYPE-4") endif;
//	      DOpush(cns);
		ConstItem cnst = new ConstItem(type, value);
		push(cnst);
	}


	public static void precede(StackItem newItem, StackItem item) {
//	begin
//	%+D   RST(R_Precede);
		if(item == null || item == SAV) STKERR("CODER.Precede");
//	%+D   if TraceMode > 1
//	%+D   then setpos(sysout,14); outstring("***PRECEDE:  "); print(item);
//	%+D        setpos(sysout,14); outstring("        BY:  "); print(new);
//	%+D   endif;
	      newItem.suc = item.suc; newItem.pred = item; item.suc = newItem;
	      if(newItem.suc != null) newItem.suc.pred = newItem;
	      else if(BOS == item) BOS = newItem;
//	%+D   if TraceMode > 1 then DumpStack endif;
	}

	public static StackItem pop() {
		if(TOS == null) STKERR("CODER.CheckPop");
		StackItem x = TOS;
		TOS = x.suc;
		if(TOS == null) BOS = null; else TOS.pred = null;
		x.suc = x.pred = null;
		return x;
	}

	public static StackItem takeTOS() { //; export ref(StackItem) x;
		return pop();
	}
//
//	--- private static void TakeRef; export ref(Address) x;
//	--- begin
//	--- %+D   RST(R_TakeRef);
//	--- %+C   if TOS = none then IERR("CODER.TakeRef"); x:=none;
//	--- %+C   else
//	--- %+C        checkRef(TOS);
//	---            x:=TakeTOS;
//	--- %+C   endif;
//	--- end;

	private static void checkRef(StackItem s) {
		if(! (s instanceof AddressItem)) STKERR("CheckRef fails");
	}
	
	private static void STKERR(String msg) {
		System.out.println("\nERROR: " + msg + " ================================================");
		CTStack.dumpStack("STKERR: ");
		Global.PSEG.dump("STKERR: ");
		Util.IERR("FORCED EXIT: " + msg);
	}

	public static void checkTosRef() {
		checkRef(TOS);
	}

	public static void checkSosRef() {
		checkRef(TOS.suc);		
	}

	public static void checkSosValue() {
		if(TOS.suc instanceof AddressItem) STKERR("CheckSosValue fails");
	}

	public static void checkTosType(Type t) {
		if(TOS.type != t) STKERR("Illegal type of TOS");
	}

	public static void checkSosType(Type t) {
		if(TOS.suc.type != t) STKERR("Illegal type of TOS");
	}

	public static void checkTosInt() {
		switch(TOS.type.tag) {
			case Scode.TAG_INT, Scode.TAG_SINT: break; 
			default: STKERR("Illegal type of TOS");
		}
	}

	public static void checkTosArith() {
//		System.out.println("CTStack.checkTosArith: " + TOS.type);
		switch(TOS.type.tag) {
			case Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL: break; 
			default: STKERR("Illegal type of TOS");
		}
	}

	public static void checkSosInt() {
		switch(TOS.suc.type.tag) {
			case Scode.TAG_INT, Scode.TAG_SINT: break; 
			default: STKERR("Illegal type of TOS");
		}
	}

	public static void checkSosArith() {
		Type type = TOS.suc.type;
		switch(type.tag) {
			case Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL: break; 
			default: STKERR("Illegal type of SOS: " + type);
		}
	}

	public static void checkSosType2(Type t1, Type t2) {
		if(TOS.suc.type == t1) ; // OK
		else if(TOS.suc.type == t2) ; // OK
		else STKERR("Illegal type of SOS");
	}

	public static void checkTypesEqual() {
//	%+C begin range(0:MaxType) t1,t2;
		Type t1 = TOS.type;
		Type t2 = TOS.suc.type;
		if(t1 == t2) return;
//		if(t1 > Scode.TAG_SIZE) STKERR("CODER.CheckTypesEqual-1: " + Scode.edTag(t1));
//		if(t2 > Scode.TAG_SIZE) STKERR("CODER.CheckTypesEqual-2: " + Scode.edTag(t2));
		t1 = arithType(t1,t1); t2 = arithType(t2,t2);
		if(t1 == t2) return;
//	%+C       if (t1>T_BYT1) or (t2>T_BYT1)
		Type.dumpTypes("checkTypesEqual: ");
		STKERR("Different types of TOS=" + t1 + " and SOS=" + t2);
	}

	public static void checkStackEmpty() {
		if(TOS != null) STKERR("Stack should be empty");
		TOS = BOS = null;
	}

	public static void getTosValueIn86(int reg) { // import range(0:255) reg;
//	--     /* M} ikke bruke qDI p.g.a. RUPDATE. */
//	begin infix(MemAddr) opr; range(0:MaxType) type; range(0:MaxByte) cTYP;
//	      type:=TOS.type;
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
//	      case 0:K_Max (TOS.kind)
//	      when K_Temp,K_Result,K_Coonst: Qf1(qPOPR,reg,cTYP)
//	      when K_Address:
//	           opr:=GetTosSrcAdr;
//	           Qf4c(qLOAD,0,reg,cTYP,0,opr,0);
//	           Pop; pushTemp(type);
//	      endcase;
		StackItem tos = CTStack.TOS;
		System.out.println("CTStack.getTosValueIn86: reg="+reg+" TOS="+TOS);
		if(tos instanceof AddressItem) {
			Global.PSEG.emit(new SVM_POP2REG(reg), "getTosValueIn86'Address: ");
			Global.PSEG.dump("getTosValueIn86'Address: ");
			Util.IERR("NOT IMPL");
		} else if(tos instanceof Temp) {
			Global.PSEG.emit(new SVM_POP2REG(reg), "getTosValueIn86'Temp: ");
			Global.PSEG.dump("getTosValueIn86'Temp: ");
//			Util.IERR("NOT IMPL");			
		} else if(tos instanceof ConstItem) {
			Global.PSEG.dump("getTosValueIn86: ");
			Util.IERR("NOT IMPL");			
		}
	}

	public static void dumpStack(String title) {
		StackItem item;
		StringBuilder sb = new StringBuilder();
//	%+D   if InputTrace <> 0 then printout(inptrace) endif;
		String lead = title + ": Current Stack";
		if(TOS == null) {
			System.out.println(lead + ": **Empty**");
		} else {
			System.out.println(lead + ": SAV=" + SAV); item = TOS;
			lead ="  TOS: ";
			do {
//				if(item.type != 0) System.out.print(Scode.edTag(item.type) + '(' + item.size + ")-");
				if(item instanceof ProfileItem) System.out.println(lead+"PROFILE:  " + item);
				else if(item instanceof AddressItem) System.out.println(lead+"REF:      " + item);
				else System.out.println(lead+"VAL:      " + item.getClass().getSimpleName() + "  " + item);
				lead ="       ";
				item = item.suc;
				if(item == BOS) lead ="  BOS: ";
			} while(item != null);
		}
	}

	public static Type arithType(Type t1, Type t2) { // export range(0:MaxType) ct;
	switch(t1.tag) {
	      case Scode.TAG_LREAL:
	    	  switch(t2.tag) {
		    	  case Scode.TAG_LREAL: return Type.T_LREAL;
		    	  case Scode.TAG_REAL:  return Type.T_LREAL;
		    	  case Scode.TAG_INT:   return Type.T_LREAL;
		    	  default: return t1;
	    	  }
	      case Scode.TAG_REAL:
	    	  switch(t2.tag) {
		    	  case Scode.TAG_LREAL: return Type.T_LREAL;
		    	  case Scode.TAG_REAL:  return Type.T_REAL;
		    	  case Scode.TAG_INT:   return Type.T_REAL;
		    	  default: return t1;
	    	  }
	      case Scode.TAG_INT:
	    	  switch(t2.tag) {
		    	  case Scode.TAG_LREAL: return Type.T_LREAL;
		    	  case Scode.TAG_REAL:  return Type.T_REAL;
		    	  case Scode.TAG_INT:   return Type.T_INT;
		    	  default: return t1;
	    	  }
	      default: return t1;
	}
	}

}
