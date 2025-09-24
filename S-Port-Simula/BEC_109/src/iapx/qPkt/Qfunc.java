package iapx.qPkt;

import static iapx.util.Global.*;

import iapx.Opcode;

import static iapx.qPkt.Qfrm2.*;

import iapx.util.Option;
import iapx.util.Type;
import iapx.util.Util;
import iapx.value.IntegerValue;
import iapx.value.MemAddr;
import iapx.value.Value;

public class Qfunc {

	public static void Qf1(Opcode fnc, int reg, int aux) {
		Qfrm1 qi = new Qfrm1(fnc, reg, aux);
		qi.appendQinstr();
	}

	public static void Qf2(Opcode fnc, int subc, int reg, int type, Value aux) {
	      Qfrm2 qi = new Qfrm2(fnc, subc, reg, type, aux);
	      qi.appendQinstr();
	}

	public static void Qf2(Opcode fnc, int subc, int reg, int type, int aux) {
		Qf2(fnc, subc, reg, type, IntegerValue.of(Type.T_INT, aux));
	}

	public static Qfrm2 insertQf2(Qpkt qx, Opcode fnc, int subc, int reg, int type, Value aux) {
	      Qfrm2 qi = new Qfrm2(fnc, subc, reg, type, aux);
	      insertQinstr(qi,qx);
	      return qi;
	}

	public static void Qf2b(Opcode fnc, int subc, int reg, int type, int fld, MemAddr addr) {
	      Qfrm2 qi = new Qfrm2b(fnc, subc, reg, type, fld, addr);
	      qi.appendQinstr();
	}

	public static Qfrm2b insertQf2b(Qpkt qx, Opcode fnc, int subc, int reg, int type, int fld, MemAddr addr) {
	      Qfrm2b qi = new Qfrm2b(fnc, subc, reg, type, fld, addr);
	      insertQinstr(qi,qx);
	      return qi;
	}

	public static void Qf3(Opcode fnc, int subc, int reg, int type, MemAddr opr) {
	      Qfrm3 qi = new Qfrm3(fnc, subc, reg, type, opr);
	      qi.appendQinstr();
	}

	public static Qfrm3 insertQf3(Qpkt qx, Opcode fnc, int subc, int reg, int type, MemAddr opr) {
	      Qfrm3 qi = new Qfrm3(fnc, subc, reg, type, opr);
	      insertQinstr(qi,qx);
	      return qi;
	}

	public static void Qf3b(Opcode fnc, int subc, int reg, int type, ValueItem val) {
	      Qfrm3 qi = new Qfrm3(fnc, subc, reg, type, val);
	      qi.appendQinstr();
	}

	public static Qfrm3 insertQf3b(Qpkt qx, Opcode fnc, int subc, int reg, int type, ValueItem val) {
	      Qfrm3 qi = new Qfrm3(fnc, subc, reg, type, val);
	      insertQinstr(qi,qx);
	      return qi;
	}

	public static void Qf4(Opcode fnc, int subc, int reg, int type, int aux, MemAddr opr) {
	      Qfrm4 qi = new Qfrm4(fnc, subc, reg, type, aux, opr);
	      qi.appendQinstr();
	}

	public static Qfrm4 insertQf4(Qpkt qx, Opcode fnc, int subc, int reg, int type, int aux, MemAddr opr) {
	      Qfrm4 qi = new Qfrm4(fnc, subc, reg, type, aux, opr);
	      insertQinstr(qi,qx);
	      return qi;
	}
	
	public static void Qf4b(Opcode fnc, int subc, int reg, int type, int aux, MemAddr opr, MemAddr addr) {
	      Qfrm4 qi = new Qfrm4(fnc, subc, reg, type, aux, opr, addr);
	      qi.appendQinstr();
	}

	public static Qfrm4 insertQf4b(Qpkt qx, Opcode fnc, int subc, int reg, int type, int aux, MemAddr opr, MemAddr addr) {
	      Qfrm4 qi = new Qfrm4(fnc, subc, reg, type, aux, opr, addr);
	      insertQinstr(qi,qx);
	      return qi;
	}
	
	public static void Qf4c(Opcode fnc, int subc, int reg, int type, int aux, MemAddr opr, int nrep) {
	      Qfrm4 qi = new Qfrm4(fnc, subc, reg, type, aux, opr, nrep);
	      qi.appendQinstr();
	}

	public static Qfrm4 insertQf4c(Qpkt qx, Opcode fnc, int subc, int reg, int type, int aux, MemAddr opr, int nrep) {
	      Qfrm4 qi = new Qfrm4(fnc, subc, reg, type, aux, opr, nrep);
	      insertQinstr(qi,qx);
	      return qi;
	}

	public static void Qf5(Opcode fnc, int i, int reg, int aux, MemAddr addr) {
	      Qfrm5 qi = new Qfrm5(fnc, i, reg, aux, addr);
	      qi.appendQinstr();
	}

	public static void defLABEL(int subc, int fixval, String smbx) {
		Qfrm6 lab = new Qfrm6(Opcode.qLABEL, subc, fixval, smbx);
		lab.appendQinstr();
	}

	public static void insertQinstr(Qpkt qi, Qpkt suc) {
		// --- Check that 'suc' is in queue ---
//		if(qinstrIsDeleted(suc)) Util.IERR("INSERT: Q-Instruction is DELETED");
		if(suc.isDeleted()) Util.IERR("INSERT: Q-Instruction is DELETED");
		qi.isize = 0; qcount = qcount+1;
		if(suc != qfirst) {
			suc.pred.next = qi; qi.pred = suc.pred;
			qi.next = suc; suc.pred = qi;
		} else {
			qi.pred = null; qi.next = suc; suc.pred = qi; qfirst = qi;
		}
		Regmap.MakeRegmap(qi);
		if(Option.listq1 > 1) ListQinstr("Insert:   ",qi,true);
	}

	
	static boolean msgGiven = false;
	public static void peepExhaust(boolean all) {
		//Util.IERR("NOT IMPL: SEE MASSAGE.DEF");
		if(! msgGiven) IO.println("Qfunc.peepExhaust: DENNE MÅ IMPLEMENTERES SENERE - SEE MASSAGE.DEF");
		msgGiven = true;
//		Thread.dumpStack();
	}

}
