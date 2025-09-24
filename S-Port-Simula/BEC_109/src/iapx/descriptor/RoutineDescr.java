package iapx.descriptor;

import static iapx.util.Global.*;


//import static iAPX.util.Global.CSEGID;

import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.Kind;
import iapx.PKind;
import iapx.Parser;
import iapx.CTStack.CTStack;
import iapx.qInstr.Q_ENTER;
import iapx.qInstr.Q_LABEL;
import iapx.qInstr.Q_LEAVE;
import iapx.qInstr.Q_RET;
import iapx.qPkt.Qfunc;
import iapx.qPkt.Qpkt;
import iapx.util.Display;
import iapx.util.Option;
import iapx.util.Scode;
import iapx.util.Tag;
import iapx.util.Type;
import iapx.util.Util;
import iapx.value.MemAddr;

public class RoutineDescr extends Descriptor {
//	int tag;  // Inherited
//	int type; // Inherited
	public MemAddr adr;

	private static final boolean DEBUG = true;//false;

	public RoutineDescr(Tag tag, Type type, MemAddr adr) {
		this.kind = Kind.K_IntRoutine;
		this.tag  = tag;
		this.type = type;
		this.adr  = adr;
	}
	
	public static void inRoutine() {
		Tag tag = Tag.inTag(); Tag prftag = Tag.inTag();
//      r = if DISPL(tag.HI)=none then none else DISPL(tag.HI).elt(tag.LO);
		RoutineDescr rut = (RoutineDescr) Display.lookup(tag.val);
		if(DEBUG) IO.println("\nRecordDescr.inRecord: BEGIN PARSING: tag=" + tag + ", prftag=" + prftag);
		String smbx = null;
		if(rut == null) {
			rut = new RoutineDescr(tag, null, MemAddr.NewFixAdr(CSEGID, smbx));

//      then rut = NEWOBJ(K_IntRoutine,size(IntDescr)); smbx.val = 0;
//           rut.adr = NewFixAdr(CSEGID,smbx); IntoDisplay(rut,tag);
		} else {
//			rut = DISPL(tag.HI).elt(tag.LO);
//          smbx = FIXTAB(rut.adr.fix.HI).elt(rut.adr.fix.LO).smbx;
			int fix = rut.adr.fix;
//			smbx = Global.FIXTAB.get(fix);
			smbx = "RecordDescr.inRecord: ???";
//			Util.IERR("SJEKK DETTE");
		}
		ProfileDescr prf = (ProfileDescr) Display.lookup(prftag);
		if(DEBUG) {
			IO.println("RecordDescr.inRecord: prf=" + prf);
			prf.print("RecordDescr.inRecord: ");
		}
		int visflag = (prf.pKind == PKind.P_ROUTINE)? 1 : 0;
		rut.type = prf.type; int nlocbyte = 0; InsideRoutine = true;
//		repeat InputInstr while CurInstr=S_LOCAL
		while(Scode.accept(Scode.S_LOCAL)) nlocbyte = LocDescr.inLocal(nlocbyte);
//		Qfunc.defLABEL(Qfrm6.qBPROC,rut.adr.fix,smbx);
//		Qfunc.Qf2(Opcode.qENTER,visflag,0,0,nlocbyte);
		new Q_LABEL(Q_LABEL.Subc.qBPROC, rut.adr.fix, smbx);
		new Q_ENTER(nlocbyte);
		
		Scode.inputInstr();
		while(Parser.instruction()) { Scode.inputInstr(); }
   
		if(Scode.curinstr != Scode.S_ENDROUTINE) Util.IERR("Missing - endroutine");
		CTStack.checkStackEmpty();
//		Qfunc.Qf2(Opcode.qLEAVE,0,0,0,nlocbyte);
//		Qfunc.Qf2(Opcode.qRET,0,0,0,prf.nparWords);
//		Qfunc.defLABEL(Qfrm6.qEPROC,rut.adr.fix,smbx);
		new Q_LEAVE(nlocbyte);
		new Q_RET(prf.nparWords);
		new Q_LABEL(Q_LABEL.Subc.qEPROC, rut.adr.fix, smbx);
		Qfunc.peepExhaust(true);
		Qpkt.printQlist();
		InsideRoutine = false;
//		Util.IERR("");
	}

	@Override
	public String toString() {
		return "RoutineDescr: type=" + type + ", adr=" + adr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		adr.write(oupt);
	}

	public RoutineDescr() {
		this.kind = Kind.K_IntRoutine;
	}

	public static RoutineDescr read(AttributeInputStream inpt) throws IOException {
		RoutineDescr rut = new RoutineDescr();
		RoutineDescr.read(rut, inpt);
		if(Option.ATTR_INPUT_DUMP) IO.println("GlobalVar.read: "+rut);
//		Util.IERR("");
		return rut;
	}

	protected static void read(RoutineDescr rut, AttributeInputStream inpt) throws IOException {
		Descriptor.read(rut, inpt);
		rut.adr = MemAddr.read(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("GlobalVar.read: adr="+rut.adr);
	}


}
