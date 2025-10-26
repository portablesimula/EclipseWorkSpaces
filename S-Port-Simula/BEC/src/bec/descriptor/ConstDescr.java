/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Type;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.FixupOADDR;
import bec.value.ObjectAddress;
import bec.value.RepetitionValue;
import bec.value.Value;

/// Constant descriptor.
///
/// S-CODE:
///
/// constant_declaration
/// 		::= constant_specification | constant_definition
/// 
///	constant_specification
///		::= constspec const:newtag quantity_descriptor
///
///	constant_definition
///		::= const const:spectag quantity_descriptor repetition_value
///
///		quantity_descriptor ::= resolved_type < Rep count:number >?
///
///			resolved_type
///				::= resolved_structure | simple_type
///				::= INT range lower:number upper:number
///				::= SINT
///
///				resolved_structure ::= structured_type < fixrep count:ordinal >?
///
///					structured_type ::= record_tag:tag
///
///		repetition_value
///			::= <boolean_value>+
///			::= <character_value>+ | text_value
///			::= <integer_value>+ | <size_value>+
///			::= <real_value>+ | <longreal_value>+
///			::= <attribute_address>+ | <object_address>+
///			::= <general_address>+ | <program_address>+
///			::= <routine_address>+ | <record_value>+
///
///			text_value
///				::= text long_string
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/descriptor/ConstDescr.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class ConstDescr extends Descriptor {
	public Type type;
	private ObjectAddress address;
	private Vector<RepetitionValue> values;
	public static int fixrepTail;
	
	private ConstDescr(int kind, Tag tag) {
		super(kind, tag);
		this.values = new Vector<RepetitionValue>();
	}

//	%title ***   C o n s t   and   C o n s t s p e c   ***
	/**
	 * constant_declaration
	 * 		::= constant_specification | constant_definition
	 * 
	 *	constant_specification
	 *		::= constspec const:newtag quantity_descriptor
	 *
	 *	constant_definition
	 *		::= const const:spectag quantity_descriptor repetition_value
	 *
	 *		quantity_descriptor ::= resolved_type < Rep count:number >?
	 *
	 *			resolved_type
	 *				::= resolved_structure | simple_type
	 *				::= INT range lower:number upper:number
	 *				::= SINT
	 *
	 *				resolved_structure ::= structured_type < fixrep count:ordinal >?
	 *
	 *					structured_type ::= record_tag:tag
	 *
	 *		repetition_value
	 *			::= <boolean_value>+
	 *			::= <character_value>+ | text_value
	 *			::= <integer_value>+ | <size_value>+
	 *			::= <real_value>+ | <longreal_value>+
	 *			::= <attribute_address>+ | <object_address>+
	 *			::= <general_address>+ | <program_address>+
	 *			::= <routine_address>+ | <record_value>+
	 *
	 *			text_value
	 *				::= text long_string
	 */
	public static ConstDescr ofConstSpec() {
		Tag tag = Tag.ofScode();
		ConstDescr cnst = (ConstDescr) Global.DISPL.get(tag.val);
		if(cnst != null) Util.IERR("New CONSPEC but cnst="+cnst);
		
		cnst = new ConstDescr(Kind.K_Coonst, tag);
		cnst.type = Type.ofScode();
		
		if(Scode.accept(Scode.S_FIXREP)) {
			Scode.inNumber();
		}

		int repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		return cnst;
		
	}
	
	public static ConstDescr ofConstDef() {
		Tag tag = Tag.ofScode();
		ConstDescr cnst = (ConstDescr) Global.DISPL.get(tag.val);
		if(cnst == null) {
			cnst = new ConstDescr(Kind.K_Coonst, tag);
		}
		cnst.type = Type.ofScode();
		
		fixrepTail = 0;
		if(Scode.accept(Scode.S_FIXREP)) {
			int fixrep = Scode.inNumber();
			RecordDescr rec = (RecordDescr) Global.getMeaning(cnst.type.tag);
			int count = rec.size + rec.rep0size * fixrep;
			fixrepTail = rec.rep0size * fixrep;
		}

		int repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		
		if(cnst.address instanceof FixupOADDR fix) {
			fix.setAddress(Global.CSEG.nextAddress());
		}
		cnst.address = Global.CSEG.nextAddress();
		for(int i=0;i<repCount;i++) {
			RepetitionValue value = RepetitionValue.ofScode();
			cnst.values.add(value);
			value.emit(Global.CSEG);
		}		return cnst;
	}
	
	public ObjectAddress getAddress() {
		if(address == null)	address = new FixupOADDR(Type.T_PADDR, this);
		return address;
	}
	
	@Override
	public void print(final String indent) {
		for(RepetitionValue value:values) {
			boolean done = false;
			if(value.values instanceof Vector<Value> vector) {
				if(vector instanceof Vector<?> elts) {
					boolean first = true;
					for(Object rVal:elts) {
						if(first) IO.println(indent + "CONST " + tag);
						first = false;
						((Value)rVal).print(indent + "   ");							
					} done = true;
				}
			}
			if(! done) IO.println(indent + "   " + toString());
		}
	}
	
	public String toString() {
		if(address != null) {
			 return "CONST " + tag + " " + address;
		} else if(values != null) {
			 return "CONST " + tag + " " + values;
		} else return "CONSTSPEC " + tag;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("CONST.Write: " + this);
		oupt.writeByte(kind);
		tag.write(oupt);
		type.write(oupt);
		address.write(oupt);
	}

	public static ConstDescr read(AttributeInputStream inpt) throws IOException {
		Tag tag = Tag.read(inpt);
		ConstDescr cns = new ConstDescr(Kind.K_Coonst, tag);
		cns.type = Type.read(inpt);
		cns.address = (ObjectAddress) Value.read(inpt);
		return(cns);
	}


}
