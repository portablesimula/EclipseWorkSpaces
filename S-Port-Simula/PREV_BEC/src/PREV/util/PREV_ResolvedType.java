package PREV.util;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Fixrep;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class PREV_ResolvedType extends Type {
//	int tag;
//	Range range;
	Fixrep fixrep;
	
	/**
	 *	 type ::= structured_type | simple_type
	 * 
	 *	 simple_type ::= BOOL | CHAR | INT | REAL | LREAL | SIZE | OADDR | AADDR | GADDR | PADDR | RADDR
	 * 
	 *	 structured_type ::= record_tag:tag
	 *
	 *	 resolved_type
	 *		::= resolved_structure | simple_type
	 *		::= INT range lower:number upper:number
	 *		::= SINT
	 *
	 *	 resolved_structure ::= structured_type < fixrep count:ordinal >?
	 */
	public PREV_ResolvedType() {
		super();
		if(tag > Scode.TAG_SIZE) {
			if(Scode.accept(Scode.S_FIXREP)) fixrep = new Fixrep();
		}
	}
	
	public String toString() {
//		if(range != null) return "INT " + range;
		if(fixrep != null) return Scode.edTag(tag) + " " + fixrep;
		return Scode.edTag(tag);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	private PREV_ResolvedType(AttributeInputStream inpt) throws IOException {
//		tag = inpt.readTag();
		super(inpt);
		inpt.readInstr();
		System.out.println("NEW ResolvedType(inpt): " + Scode.edInstr(Scode.curinstr));
//		if(inpt.curinstr == Scode.S_RANGE) { range = Range.read(inpt); inpt.readInstr(); }
//		else
			if(inpt.curinstr == Scode.S_FIXREP) { fixrep = Fixrep.read(inpt); inpt.readInstr(); }
		
		if(inpt.curinstr != Scode.S_ENDIF) Util.IERR("IMPOSSIBLE: "+Scode.edInstr(inpt.curinstr));
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeTag(tag);
//		if(range != null) range.write(oupt);
		if(fixrep != null) fixrep.write(oupt);
		oupt.writeInstr(Scode.S_ENDIF);
	}

	public static PREV_ResolvedType read(AttributeInputStream inpt) throws IOException {
		return new PREV_ResolvedType(inpt);
	}


}
