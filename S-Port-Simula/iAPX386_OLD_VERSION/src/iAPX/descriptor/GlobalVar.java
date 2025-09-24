package iAPX.descriptor;

import static iAPX.util.Global.*;

import java.io.IOException;

import iAPX.ExtRef;
import iAPX.dataAddress.MemAddr;
import iAPX.enums.Kind;
import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Display;
import iAPX.util.Option;
import iAPX.util.Scode;
import iAPX.util.Tag;
import iAPX.util.Type;
import iAPX.util.Util;

public class GlobalVar extends Descriptor {
//	Record IntDescr:Descriptor;      // K_Globalvar
//	begin MemAddr adr;        // K_IntLabel
//	end;                             // K_IntRoutine   Local Routine

//	int tag;  // Inherited
//	int type; // Inherited
	public MemAddr adr;
	
	public GlobalVar(Tag tag, Type type, MemAddr adr) {
		this.kind = Kind.K_GlobalVar;
		this.tag  = tag;
		this.type = type;
		this.adr  = adr;
	}
	
	
	public static void inGlobal() {
		Tag tag = Tag.inTag();
		Type type = Scode.inType(); int size = type.getSize();
//		IO.println("\nGlobalVar.inGlobal: BEGIN PARSING: " + tag + ", size=" + size);
		int count = 1;
		if(Scode.accept(Scode.S_REP)) {
			count = Scode.inNumber();
	        if(count == 0) {
	        	Util.IERR("Illegal 'REP 0'");
	        	count = 1;
	        }
		}
		if(Scode.accept(Scode.S_SYSTEM)) {
			String id = Scode.inExtr('G',DGROUP.ident);
			ExternVar xvar = new ExternVar(tag, type, new ExtRef(id, 0));
			Display.add(xvar);
		} else {
			MemAddr adr = DSEGID.nextAddress();
//			adr.sibreg = NoIBREG;
			if(size == 0) Util.IERR("Illegal type on GlobalVar");
			DSEGID.emitZero(count * size, "GLOBAL:" + tag);
			GlobalVar gvar = new GlobalVar(tag, type, adr);
			Display.add(gvar);
		}
	}

	@Override
	public String toString() {
		return "GlobalVar: type=" + type + ", adr=" + adr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		adr.write(oupt);
	}

	private GlobalVar() {
		this.kind = Kind.K_GlobalVar;
	}

	public static GlobalVar read(AttributeInputStream inpt) throws IOException {
		GlobalVar var = new GlobalVar();
		GlobalVar.read(var, inpt);
		if(Option.ATTR_INPUT_DUMP) IO.println("GlobalVar.read: "+var);
//		Util.IERR("");
		return var;
	}

	protected static void read(GlobalVar var, AttributeInputStream inpt) throws IOException {
		Descriptor.read(var, inpt);
		var.adr = MemAddr.read(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("GlobalVar.read: adr="+var.adr);
	}

}
