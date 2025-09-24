package iapx.descriptor;

import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.Kind;
import iapx.util.Display;
import iapx.util.Option;
import iapx.util.Scode;
import iapx.util.Tag;
import iapx.util.Type;
import iapx.util.Util;

//Record LocDescr:Descriptor;      // K_Attribute  Offset = rela    SIZE = 8 bytes
//                                 // K_Parameter  Address = [BP] + rela
//                                 // K_Export     Address = [BP] + rela
//                                 // K_LocalVar   Address = [BP] - rela
//begin range(0:MaxWord) rela;
//   infix(WORD) nextag;        // NOTE: Only used for Parameters
//                              // NOTE: And only in 'inprofile'
//   range(0:MaxWord) UNUSED;   // To 4-byte align record in all cases
//end;  
public class LocDescr extends Descriptor {
//	int rela;
//	int nextag;

//	int tag;  // Inherited
//	int type; // Inherited
	public int rela;
//	int nextag;  // NOTE: Only used for Parameters, and only in 'inprofile'
	
	public LocDescr(Tag tag, Type type, int rela) {
		this.kind = Kind.K_LocalVar;
		this.tag  = tag;
		this.type = type;
		this.rela = rela;
	}
	
	public static int inLocal(int rela) { // export range(0:MaxWord) res;
		Tag tag = Tag.inTag();
		Type type = Scode.inType(); int size = type.getSize();
		if(Scode.accept(Scode.S_REP)) {
			int count = Scode.inNumber();
	        if(count == 0) Util.IERR("Illegal 'REP 0'");
	        size = size * count;
		}
		rela = rela + size;
		LocDescr var = new LocDescr(tag, type, rela);
		Display.add(var);
		Util.IERR("");
		return rela;
	}

	@Override
	public String toString() {
		String s = "LocDescr:";
		if(Option.GENERATE_DEBUG_CODE) s += tag + ", ";
		s += "type=" + type + ", rela=" +rela;
//		if(nextag != 0) s = s + ", nextag=" + Scode.edTag(nextag);
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		oupt.writeShort(rela);
	}

	private LocDescr() {
		this.kind = Kind.K_LocalVar;
	}

	public static LocDescr read(AttributeInputStream inpt) throws IOException {
		LocDescr var = new LocDescr();
		LocDescr.read(var, inpt);
		if(Option.ATTR_INPUT_DUMP) IO.println("Parameter.read: "+var);
//		Util.IERR("NOT IMPL");
		return var;
	}

	protected static void read(LocDescr par, AttributeInputStream inpt) throws IOException {
		Descriptor.read(par, inpt);
		par.rela = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("Parameter.read: rela="+par.rela);
	}
	

}
