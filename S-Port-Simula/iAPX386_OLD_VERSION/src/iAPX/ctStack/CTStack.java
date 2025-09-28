package iAPX.ctStack;

import static iAPX.util.Global.*;

import iAPX.dataAddress.MemAddr;
import iAPX.enums.Kind;
import iAPX.instruction.POP;
import iAPX.qInstr.Q_DYADC;
import iAPX.util.Option;
import iAPX.util.Reg;
import iAPX.util.Type;
import iAPX.util.Util;
import svm.value.IntegerValue;
import svm.value.Value;

public class CTStack {
	
	// --- Current Stack ---
	public static int StackDepth87; // initial(0)
	public static StackItem TOS;      // Top of Compile-time stack
	public static StackItem BOS;      // Bot of Compile-time stack
	public static StackItem SAV;   // Last Compile-time stack-item for which
	                          // the corresponding Runtime-item is saved.
	                          // NOTE: SAV =/= none implies TOS =/= none

	public static void Push(StackItem s) {
		checkPush(s);
		if(TOS == null) {
			TOS = BOS = s; s.suc = null;
		} else {
			s.suc = TOS;
			TOS = s.suc.pred = s;
		}
		if(Option.traceMode > 1) dumpStack();
	}
	
	public static void checkStackEmpty() {
		if(TOS != null) Util.IERR("Stack should be empty");
		if(StackDepth87 != 0) Util.IERR("StackDepth87 <> 0");
	}

	private static void checkPush(StackItem s) {
		if(s.suc != null | s.pred != null) Util.IERR("CODER.CheckPush: " + s);
	}

	public static void precede(StackItem NEW, StackItem x) {
		if(x == null | x == SAV) Util.IERR("CODER.Precede");
		if(Option.traceMode > 1) {
			IO.println("              ***PRECEDE:  " + x);
			IO.println("                      BY:  " + NEW);
		}
		NEW.suc = x.suc; NEW.pred = x; x.suc = NEW;
		if(NEW.suc != null) NEW.suc.pred = NEW;
		else if(BOS == x) BOS = NEW;
		if(Option.traceMode > 1) dumpStack();
	}
	
	public static void pushCoonst(Type type, Value itm) {
		Coonst cns = new Coonst(type, itm);
		if(type.size == 0) Util.IERR("No info TYPE-4");
		doPush(cns);
	}

	public static void pushTemp(Type type) {
//	import range(0:MaxType) type;
//	begin ref(Object) tmp;
//	      tmp = FreeObj(K_Temp);
//	      if tmp <> none
//	      then FreeObj(K_Temp) = tmp qua FreeObject.next;
//	      else L: tmp = PoolNxt; PoolNxt = PoolNxt+size(Temp);
//	           if PoolNxt >= PoolBot
//	           then PALLOC(size(Temp),tmp); goto L endif;
//	%+D        ObjCount(K_Temp) = ObjCount(K_Temp)+1;
//	      endif;
//	      tmp.kind = K_Temp; tmp.type = type;
//	      tmp qua StackItem.repdist = TTAB(type).nbyte;
//	      tmp qua StackItem.suc = none; tmp qua StackItem.pred = none;
		if(type.size == 0) Util.IERR("No info TYPE-3");
		Temp tmp = new Temp(type);
		doPush(tmp);
	}

	private static void doPush(StackItem s) {
		checkPush(s);
		if(TOS == null) {
			TOS = BOS = s; s.suc = null;
		} else {
			s.suc = TOS; TOS = s.suc.pred = s;
		}
		if(Option.traceMode > 1) dumpStack();
	}
	
	public static void pop() {
//	begin ref(Object) x; range(0:MaxByte) kind;
		checkPop();
		StackItem x = TOS;
//		Kind kind = x.kind;
		TOS = x.suc;
		if(TOS == null) BOS = null; else TOS.pred = null;
//	%+D   x qua StackItem.suc = x qua StackItem.pred = none;
//	      x qua FreeObject.next = FreeObj(kind); FreeObj(kind) = x;
//	%+D   x.kind  =  kind+128;
	}

	private static void checkPop() {
		if(Option.traceMode > 1) IO.println("*** POP:  " + TOS);
		if(TOS == null) Util.IERR("CODER.CheckPop");
//	%+D   elsif TOS.kind >= K_Max
//	%+D   then IERR("POP -- TOS is already deleted"); print(TOS) endif;
	}

	public static void clearSTK() {
//	 begin range(0:MaxWord) nbt; ref(StackItem) s,sx;
//
		if(Option.traceMode != 0) {
			IO.println("*** ClearSTK ");
			dumpStack();
		}
//	       if SAV=none then sx:=BOS else sx:=SAV.pred endif;
		StackItem sx = (SAV == null)? BOS : SAV.pred;
		StackItem s = TOS;
//	    repeat
		LOOP: while(true) {
			switch(s.kind) {
			case K_ProfileItem: break; // Nothing to save (params stacked)
			case K_Address:
				if( ((Address)s).atrState != Address.NotStacked) POP.qPOPKill(1);
				if( ((Address)s).objState != Address.NotStacked) POP.qPOPKill(1);
				break;
			case K_Temp,K_Coonst,K_Result:
				int nbt = s.type.size;
				if(nbt != 0) {
					if(s.kind == Kind.K_Result) {
//						Qf2(qDYADC,qADD,qESP,cSTP,wAllign(%nbt%))
						new Q_DYADC(Q_DYADC.Subc.qADD, Reg.qESP, IntegerValue.of(Type.T_INT, nbt));
					} else {
						POP.qPOPKill(nbt);
					}
				}
					break;
				default: Util.IERR("PARSE.ClearSTK");
			}
//	        while s <> sx do s:=s.suc endrepeat;
			if(s == sx) break LOOP;
			s = s.suc;
		}
	}

	public static void checkRef(StackItem s) {
		if(s.kind != Kind.K_Address) Util.IERR("Check-ref fails");
	}

	public static void checkTosRef() { checkRef(TOS); }
	public static void checkSosRef() { checkRef(TOS.suc); }

	public static void checkSosValue() {
		if(TOS.suc.kind == Kind.K_Address) Util.IERR("CheckSosValue fails");
	}

	public static void checkTosType(Type type) {
		if(! TOS.type.equals(type)) Util.IERR("Illegal type of TOS: " + TOS.type + ", not " + type);
	}

	public static void checkSosType(Type type) {
		if(! TOS.suc.type.equals(type)) Util.IERR("Illegal type of SOS: " + TOS.suc.type + ", not " + type);
	}
	
	public static void checkTypesEqual() {
		Type t1 = TOS.type; Type t2 = TOS.suc.type;
		if(t1.equals(t2)) return;
		t1 = Type.arithType(t1,t1); t2 = Type.arithType(t2,t2);
		if(t1.equals(t2)) return;

		//%+C       if (t1>T_BYT1) or (t2>T_BYT1)
		if( (! t1.isT_INT()) | (! t2.isT_INT()) ) Util.IERR("Different types of TOS and SOS");
	}

	public static void dumpStack() { dumpStack(0, ""); }
	public static void dumpStack(String title) { dumpStack(0, title); }
	
	public static void dumpStack(int level, String title) {
		String indent = "";
		for(int i=0;i<level;i++) indent = indent + "      ";

//	%+D begin ref(StackItem) s;
		if(Option.InputTrace != 0) {
			IO.println("CTStack.dumpStack: Bruken av 'inptrace' må implementeres");
			// printout(inptrace);
		}
		String lead = indent + title + ": Current Stack ";
		if(TOS == null) {
			IO.println(lead + ": **Empty**");				
		} else {
			StackItem s = TOS;
			IO.println(lead);
			lead = indent + "        TOS: ";
			do {
				IO.print(lead);
				if(s.type != null) IO.print("" + s.type + '(' + s.repdist + ")-");
				switch(s.kind) {
				case K_ProfileItem: IO.print("PROFILE:  "); break;
				case K_Address:     IO.print("REF:  "); break;
				default:            IO.print("VAL:  ");
				}
				IO.println(s.toString());
				lead = indent + "             ";					
				s = s.suc;
			} while(s != null);
		}
		if(StackDepth87 != 0) IO.println("              StackDepth87: " +StackDepth87);
	}


}
