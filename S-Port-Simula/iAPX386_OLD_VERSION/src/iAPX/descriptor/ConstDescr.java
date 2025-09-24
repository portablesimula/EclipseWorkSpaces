package iAPX.descriptor;

import static iAPX.util.Global.*;

import java.io.IOException;

import iAPX.dataAddress.MemAddr;
import iAPX.enums.Kind;
import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.statement.InsertStatement;
import iAPX.util.Display;
import iAPX.util.Option;
import iAPX.util.Scode;
import iAPX.util.Tag;
import iAPX.util.Type;
import iAPX.util.Util;

public class ConstDescr extends Descriptor {
//	Record IntDescr:Descriptor;	// K_Globalvar
//	begin MemAddr adr;			// K_IntLabel
//	end;						// K_IntRoutine   Local Routine

//	int tag;  // Inherited
//	int type; // Inherited
	MemAddr adr;
	
	private static final boolean DEBUG = false;

	public ConstDescr(Tag tag, Type type, MemAddr adr) {
		this.kind = Kind.K_Constant;
		this.tag  = tag;
		this.type = type;
		this.adr  = adr;
	}
	
	
	public static void inConstant(boolean ConstDef) {
//	begin ref(RepValue) cnst; ref(IntDescr) cnst; infix(WORD) tag,count;
//	      range(0:MaxWord) nbyte; range(0:MaxType) type;
		Tag tag = Tag.inTag();
		Type type = Scode.inType(); int size = type.getSize();
		if(size == 0) Util.IERR("Illegal Type on Constant");
		if(DEBUG) IO.println("\nIntDescr.inConstant: BEGIN PARSING: " + tag + ", type=" + type);
		
		int count = 1;
		if(Scode.accept(Scode.S_REP)) {
			count = Scode.inNumber();
			if(count == 0) { Util.IERR("Illegal 'REP 0'"); count = 1; }
		}
		
		ConstDescr cnst = (ConstDescr) Display.maybe(tag);
		if(DEBUG) IO.println("IntDescr.inConstant: cnst=" + cnst);
		if(cnst == null) {
			cnst = new ConstDescr(tag, type, null);;
			//cnst.type = type; cnst.adr = null;
			Display.add(cnst);
		} else {
			if(cnst.kind != Kind.K_Constant) Util.IERR("Display-entry is not defined as a constant: " + tag);
//			if(cnst.type != type) Util.IERR("Type not same as given by CONSTSPEC");
			if(! cnst.type.equals(type)) Util.IERR("Type not same as given by CONSTSPEC");
		}
		if(DEBUG) IO.println("IntDescr.inConstant: cnst=" + cnst);
		if(ConstDef) {
			if(Scode.accept(Scode.S_SYSTEM)) {
//	%+S             cnst.adr.kind = extadr; cnst.adr.rela.val = 0;
//	%+S             cnst.adr.smbx = InExtr('G',DGROUP);
//	%+S %-E         cnst.adr.sbireg = 0;
//	%+SE            cnst.adr.sibreg = NoIBREG;
				String id = Scode.inExtr('G',DGROUP.ident);
				cnst.adr = MemAddr.ofExtAddr(id, 0);
				Util.IERR("SJEKK DETTE !!!");
			} else {
				cnst.adr = DSEGID.nextAddress();
				if(size == 0) Util.IERR("Illegal type on ConstDescr");
//				DSEGID.emitZero(count * size, "CONST:" + Scode.edTag(tag));
				DSEGID.emitRepValue(cnst, size);
			}
		}
	}
	
	@Override
	public String toString() {
		return "ConstDescr: type=" + type + ", adr=" + adr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		adr.write(oupt);
	}

	private ConstDescr() {
		this.kind = Kind.K_Constant;
	}

	public static ConstDescr read(AttributeInputStream inpt) throws IOException {
		ConstDescr cns = new ConstDescr();
		ConstDescr.read(cns, inpt);
		if(Option.ATTR_INPUT_DUMP) IO.println("ConstDescr.read: "+cns);
//		Util.IERR("");
		return cns;
	}

	protected static void read(ConstDescr cns, AttributeInputStream inpt) throws IOException {
		Descriptor.read(cns, inpt);
		cns.adr = MemAddr.read(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("ConstDescr.read: adr="+cns.adr);
	}

}
