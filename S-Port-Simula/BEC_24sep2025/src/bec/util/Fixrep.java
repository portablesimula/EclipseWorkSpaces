package bec.util;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;

public class Fixrep {
	int rep;
	
	public Fixrep() {
		parse();
	}

	public void parse() {
		rep = Scode.inNumber();
	}
	
	public String toString() {
		return "FIXREP " + rep;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private Fixrep(AttributeInputStream inpt) throws IOException {
		rep = inpt.readShort();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_FIXREP);
		oupt.writeShort(rep);
	}

	public static Fixrep read(AttributeInputStream inpt) throws IOException {
		return new Fixrep(inpt);
//		Util.IERR("Method 'readObject' needs a redefiniton");
//		return(null);
	}

}
