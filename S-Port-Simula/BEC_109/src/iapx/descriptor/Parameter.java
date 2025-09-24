package iapx.descriptor;

import static iapx.util.Global.*;

import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.Kind;
import iapx.util.Option;
import iapx.util.Tag;
import iapx.util.Type;

public class Parameter extends Descriptor {
//	int tag;  // Inherited
//	int type; // Inherited
	public int rela;
//	int nextag;  // NOTE: Only used for Parameters, and only in 'inprofile'
	
//	Record LocDescr:Descriptor;      // K_Attribute  Offset = rela    SIZE = 8 bytes
//    // K_Parameter  Address = [BP] + rela
//    // K_Export     Address = [BP] + rela
//    // K_LocalVar   Address = [BP] - rela
//	begin int rela;
//		infix(WORD) nextag;     // NOTE: Only used for Parameters
//								// NOTE: And only in 'inprofile'
//		int UNUSED;   // To 4-byte align record in all cases
//	end;  
	
	public Parameter(Tag tag, Type type, int rela) {
		this.kind = Kind.K_Parameter;
		this.tag  = tag;
		this.type = type;
		this.rela = rela;
	}
	
	@Override
	public String toString() {
		String s = "Parameter:";
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

	private Parameter() {
		this.kind = Kind.K_Parameter;
	}

	public static Parameter read(AttributeInputStream inpt) throws IOException {
		Parameter par = new Parameter();
		Parameter.read(par, inpt);
		if(Option.ATTR_INPUT_DUMP) IO.println("Parameter.read: "+par);
//		Util.IERR("NOT IMPL");
		return par;
	}

	protected static void read(Parameter par, AttributeInputStream inpt) throws IOException {
		Descriptor.read(par, inpt);
		par.rela = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("Parameter.read: rela="+par.rela);
	}
	
}
