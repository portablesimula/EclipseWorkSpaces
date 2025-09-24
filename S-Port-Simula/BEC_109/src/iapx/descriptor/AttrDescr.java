package iapx.descriptor;

import static iapx.util.Global.*;

import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.Kind;
import iapx.util.Option;
import iapx.util.Tag;
import iapx.util.Type;

public class AttrDescr extends Descriptor {
//	int tag;  // Inherited
//	int type; // Inherited
	public int rela;
	
//	Record LocDescr:Descriptor;      // K_Attribute  Offset = rela    SIZE = 8 bytes
//    // K_Parameter  Address = [BP] + rela
//    // K_Export     Address = [BP] + rela
//    // K_LocalVar   Address = [BP] - rela
//	begin int rela;
//		infix(WORD) nextag;     // NOTE: Only used for Parameters
//								// NOTE: And only in 'inprofile'
//		int UNUSED;   // To 4-byte align record in all cases
//	end;  
	
	public AttrDescr(Tag tag, Type type, int rela) {
		this.kind = Kind.K_Attribute;
		this.tag  = tag;
		this.type = type;
		this.rela = rela;
	}
	
	@Override
	public String toString() {
		String s = "AttrDescr: ";
		if(Option.GENERATE_DEBUG_CODE) s += tag + ", ";
		s += "type=" + type + ", rela=" +rela;
		return s;
	}

	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		oupt.writeShort(rela);
	}

	private AttrDescr() {
		this.kind = Kind.K_Attribute;
	}
	
	public static AttrDescr read(AttributeInputStream inpt) throws IOException {
		AttrDescr atr = new AttrDescr();
		AttrDescr.read(atr, inpt);
		if(Option.ATTR_INPUT_DUMP) IO.println("AttrDescr.read: "+atr);
//		Util.IERR("");
		return atr;
	}

	protected static void read(AttrDescr atr, AttributeInputStream inpt) throws IOException {
		Descriptor.read(atr, inpt);
		atr.rela = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("AttrDescr.read: rela="+atr.rela);
	}
	
}
