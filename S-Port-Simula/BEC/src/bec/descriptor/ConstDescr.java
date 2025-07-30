package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Type;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.FixupOADDR;
import bec.value.ObjectAddress;
import bec.value.RepetitionValue;
import bec.value.Value;

public class ConstDescr extends Descriptor {
	public Type type;
	private ObjectAddress address;
	private Vector<RepetitionValue> values;
	public static int fixrepTail;
	
	private static final boolean DEBUG = false;
	
	private ConstDescr(int kind, Tag tag) {
		super(kind, tag);
		this.values = new Vector<RepetitionValue>();
//		this.tag = tag;
////		Global.Display.set(tag, this);
//		Global.intoDisplay(this, tag);
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
//		Tag tag = Tag.ofScode();
		Tag tag = Tag.ofScode();
		Descriptor descr = Global.DISPL.get(tag.val);
		if(descr != null) Util.IERR("New CONSPEC: Descriptor already defined: "+descr);
		
		ConstDescr cnst = new ConstDescr(Kind.K_Coonst, tag);
//		System.out.println("NEW ConstDescr.ofConstSpec: "+cnst);
		
//		cnst.quant = new QuantityDescriptor();
		cnst.type = Type.ofScode();
		
		if(Scode.accept(Scode.S_FIXREP)) {
			Scode.inNumber();
//			Util.IERR("DETTE ER EN 'ResolvedType' - HVA NÅ ?");
//			System.out.println("DETTE ER EN 'ResolvedType' - HVA NÅ ?");
		}

		@SuppressWarnings("unused")
		int repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;

		if(DEBUG) {
			System.out.println("CONST.inConstant: " + cnst);
//			if(Global.traceMode > 3)
				cnst.print("   ");
//			Util.IERR("");
		}
		return cnst;
		
	}
	
	public static ConstDescr ofConstDef() {
//		Tag tag = Tag.ofScode();
		Tag tag = Tag.ofScode();
		ConstDescr cnst = (ConstDescr) Global.DISPL.get(tag.val);
		if(cnst == null) {
			cnst = new ConstDescr(Kind.K_Coonst, tag);
		}
		if(DEBUG) System.out.println("NEW ConstDescr.ofConstDef: "+cnst);
//		cnst.quant = new QuantityDescriptor();
		cnst.type = Type.ofScode();
		
		fixrepTail = 0;
		if(Scode.accept(Scode.S_FIXREP)) {
			int fixrep = Scode.inNumber();
//			System.out.println("ConstDescr.ofConstDef: "+cnst);
//			System.out.println("ConstDescr.ofConstDef: "+cnst.type);
//			System.out.println("ConstDescr.ofConstDef: FIXREP "+fixrep);
			RecordDescr rec = (RecordDescr) Global.getMeaning(cnst.type.tag);
//			System.out.println("ConstDescr.ofConstDef: descr="+rec);
			@SuppressWarnings("unused")
			int count = rec.size + rec.nbrep * fixrep;
			fixrepTail = rec.nbrep * fixrep;
//			System.out.println("ConstDescr.ofConstDef: count="+count);
//			Util.IERR("DETTE ER EN 'ResolvedType' - HVA NÅ ?");
//			System.out.println("DETTE ER EN 'ResolvedType' - HVA NÅ ?");
		}

		int repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		
		if(cnst.address instanceof FixupOADDR fix) {
			fix.setAddress(Global.CSEG.nextAddress());
//			Util.IERR("");
		}
		cnst.address = Global.CSEG.nextAddress();
		for(int i=0;i<repCount;i++) {
			RepetitionValue value = RepetitionValue.ofScode();
			cnst.values.add(value);
			if(DEBUG) {
				System.out.println("NEW ConstDescr.ofConstDef: "+value.getClass().getSimpleName());
				value.print("NEW ConstDescr.ofConstDef: ");
			}
			
			String comment = tag + " type=" + cnst.type;
			value.emit(Global.CSEG, comment);
		}
		
		if(DEBUG) {
			System.out.println("CONST.inConstant: " + cnst);
//			if(Global.traceMode > 3) cnst.print("   ");
			Global.CSEG.dump("ConstDescr.ofConstDef: ");
			Global.DSEG.dump("ConstDescr.ofConstDef: ");
//			Util.IERR("");
		}
//		if(fixrepTail > 0) Util.IERR("");
		return cnst;
	}
	
	public ObjectAddress getAddress() {
		if(address == null)	address = new FixupOADDR(Type.T_PADDR, this);
		return address;
	}
	
	@Override
	public void print(final String indent) {
		for(RepetitionValue value:values) {
//		if(value != null) {
			boolean done = false;
			if(value.values instanceof Vector<Value> vector) {
				if(vector instanceof Vector<?> elts) {
					boolean first = true;
					for(Object rVal:elts) {
						if(first) System.out.println(indent + "CONST " + tag);
						first = false;
						((Value)rVal).print(indent + "   ");							
					} done = true;
				}
			}
			if(! done) System.out.println(indent + "   " + toString());
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
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("CONST.Write: " + this);
		oupt.writeKind(kind);
//		oupt.writeShort(ModuleIO.chgType(tag));
		tag.write(oupt);
		type.write(oupt);
		address.write(oupt);
//		quant.write(oupt);
	}

	public static ConstDescr read(AttributeInputStream inpt) throws IOException {
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  BEGIN CONST.Read");
		Tag tag = Tag.read(inpt);
		ConstDescr cns = new ConstDescr(Kind.K_Coonst, tag);
//		System.out.println("AFTER NEW CONST: "+cns);
		cns.type = Type.read(inpt);
		cns.address = (ObjectAddress) Value.read(inpt);
//		System.out.println("AFTER NEW MEMADDR: "+cns);
//		Util.IERR("Static Method 'readObject' needs a redefiniton");
//		if(Global.ATTR_INPUT_TRACE) System.out.println("ConstDescr.Read: " + cns);
		return(cns);
	}


}
