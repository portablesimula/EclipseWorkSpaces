package bec.descriptor;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CALL;
import bec.segment.DataSegment;
import bec.util.BecGlobal;
import bec.util.Type;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.LongRealValue;
import bec.value.ObjectAddress;
import bec.value.RealValue;
import bec.value.Value;

public class Variable extends Descriptor {
//	public int instr; // S_GLOBAL, S_LOCAL, S_IMPORT, S_EXPORT, S_EXIT
	public ObjectAddress address;
	public Type type;
	public int repCount;
	
//	String system;

	/**
	 * 	global_definition ::= global internal:newtag quantity_descriptor
	 * 
	 * 	local_quantity ::= local var:newtag quantity_descriptor
	 * 
	 * 	import_definition ::= import parm:newtag quantity_descriptor
	 * 
	 *	export parm:newtag resolved_type
	 * 
	 *	exit return:newtag
	 *
	 *  quantity_descriptor ::= resolved_type < Rep count:number >?
	 *  
	 *  	resolved_type
	 *  		::= resolved_structure | simple_type
	 *  		::= INT range lower:number upper:number
	 *  		::= SINT
	 *  
	 *  		resolved_structure ::= structured_type < fixrep count:ordinal >?
	 *  
	 *  			structured_type ::= record_tag:tag
	 */	
	private Variable(int kind, Tag tag) {
		super(kind, tag);
	}
	
	public static Variable ofIMPORT() {
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_Import, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
//		var.address = frame.nextAddress(var.type.size());
//		seg.emitDefaultValue(var.type.size(), "IMPORT " + var.type);
		return var;
	}
	
	public static Variable ofIMPORT(DataSegment seg) {
		if(seg == null) Util.IERR("");
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_Import, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		var.address = seg.nextAddress();
		seg.emitDefaultValue(var.type.size(), var.repCount, "IMPORT " + var.type);
		return var;
	}
	
	public static Variable ofEXPORT() {
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_Export, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
//		var.address = seg.nextAddress();
//		seg.emitDefaultValue(var.type.size(), "EXPORT " + var.type);
		return var;
	}
	
	public static Variable ofEXPORT(DataSegment seg) {
		if(seg == null) Util.IERR("");
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_Export, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		var.address = seg.nextAddress();
//		type.emitDefaultValue(seg, "EXPORT " + type);
		seg.emitDefaultValue(var.type.size(), var.repCount, "EXPORT " + var.type);
		return var;
	}
	
	public static Variable ofEXIT() {
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_Exit, tag);
		var.type = Type.T_PADDR;
//		var.address = returAddr;
		return var;
	}
	
	public static Variable ofEXIT(ObjectAddress returAddr) {
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_Exit, tag);
//		var.type = new Type(Scode.TAG_PADDR);
		var.type = Type.T_PADDR;
		var.address = returAddr;
		return var;
	}
	
	public static Variable ofRETUR(ObjectAddress returAddr) {
		Variable var = new Variable(Kind.K_Retur, null);
		var.type = Type.T_PADDR;
		var.address = returAddr;
		return var;
	}
	
	public static Variable ofLocal(int rela) {
		if(! CALL.USE_FRAME_ON_STACK) Util.IERR("ILLEGAL USE OF: ofLocal");
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_LocalVar, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
//		var.address = new ObjectAddress(null, rela);
		var.address = ObjectAddress.ofRelFrameAddr(rela);
		return var;
	}
	
	public static Variable ofLocal(DataSegment seg) {
		if(CALL.USE_FRAME_ON_STACK) Util.IERR("ILLEGAL USE OF: ofLocal");
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_LocalVar, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		var.address = seg.nextAddress();
//		type.emitDefaultValue(Global.DSEG, var.toString());	
		
//		seg.emitDefaultValue(var.type.size(), "LOCAL " + var.type);
//		if(var.repCount > 1) {
//			Util.IERR("");
//		}
		for(int i=0;i<var.repCount;i++) {
			seg.emitDefaultValue(var.type.size(), var.repCount, "LOCAL " + var.type);			
		}
//		Global.dumpDISPL("Variable.ofGlobal: ");
//		seg.dump("Variable.ofGlobal: ");
//		Util.IERR("");
		return var;
	}
	
	public static Variable ofGlobal(DataSegment seg) {
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_GlobalVar, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		var.address = seg.nextAddress();
		if(Scode.accept(Scode.S_SYSTEM)) {
			String system = Scode.inString();
//			System.out.println("NEW Global Variable: " + Scode.edTag(var.tag) + " " + var.quant + " SYSTEM " + system);
			Value value = null;
			if(system.equalsIgnoreCase("CURINS")) value = null;//new ObjectAddress(true);
			else if(system.equalsIgnoreCase("STATUS")) value = null;//IntegerValue.of(0);
			else if(system.equalsIgnoreCase("ITSIZE")) value = IntegerValue.of(Type.T_INT, 666);
			else if(system.equalsIgnoreCase("MAXLEN")) value = IntegerValue.of(Type.T_SIZE, 20);
			else if(system.equalsIgnoreCase("INPLTH")) value = IntegerValue.of(Type.T_INT, 155);
			else if(system.equalsIgnoreCase("OUTLTH")) value = IntegerValue.of(Type.T_INT, 360);
			else if(system.equalsIgnoreCase("BIOREF")) value = null;//new ObjectAddress(true);
			else if(system.equalsIgnoreCase("MAXINT")) value = IntegerValue.of(Type.T_INT, Integer.MAX_VALUE);
			else if(system.equalsIgnoreCase("MININT")) value = IntegerValue.of(Type.T_INT, Integer.MIN_VALUE);
			else if(system.equalsIgnoreCase("MAXRNK")) value = IntegerValue.of(Type.T_INT, 255);
			else if(system.equalsIgnoreCase("MAXREA")) value = RealValue.of(Float.MAX_VALUE);
//			else if(system.equalsIgnoreCase("MINREA")) value = RealValue.of(Float.MIN_VALUE);
			else if(system.equalsIgnoreCase("MINREA")) value = RealValue.of(-Float.MAX_VALUE);
			else if(system.equalsIgnoreCase("MAXLRL")) value = LongRealValue.of(Double.MAX_VALUE);
//			else if(system.equalsIgnoreCase("MINLRL")) value = LongRealValue.of(Double.MIN_VALUE);
			else if(system.equalsIgnoreCase("MINLRL")) value = LongRealValue.of(-Double.MAX_VALUE);
			else if(system.equalsIgnoreCase("INIERR")) value = null;//new RoutineAddress(true);
			else if(system.equalsIgnoreCase("ALLOCO")) value = null;//new RoutineAddress(true);
			else if(system.equalsIgnoreCase("FREEOB")) value = null;//new RoutineAddress(true);
			else Util.IERR("MISSING: " + system);
			BecGlobal.DSEG.emit(value, var.toString());
		} else {
//			System.out.println("Variable.ofGlobal: "+var);
//			System.out.println("Variable.ofGlobal: size="+var.type.size());
//			System.out.println("Variable.ofGlobal: repCount="+var.repCount);
			int count = var.type.size();
			if(Scode.accept(Scode.S_FIXREP)) {
				int fixrep = Scode.inNumber();
//				System.out.println("Variable.ofGlobal: "+var);
//				System.out.println("Variable.ofGlobal: "+var.type);
//				System.out.println("Variable.ofGlobal: FIXREP "+fixrep);
				RecordDescr rec = (RecordDescr) BecGlobal.getMeaning(var.type.tag);
//				System.out.println("Variable.ofGlobal: descr="+rec);
				count = count + rec.nbrep * fixrep;
//				System.out.println("Variable.ofGlobal: count="+count);
//				Util.IERR("");
			}
			if(count == 0) Util.IERR("");
			seg.emitDefaultValue(count, var.repCount, "GLOBAL " + var.type);
		}

//		Global.dumpDISPL("Variable.ofGlobal: ");
//		seg.dump("Variable.ofGlobal: ");
//		Util.IERR("");
		return var;
	}
	
//	private void emit(DataSegment dseg, String cmnt) {
////		Value value = quant.type.defaultValue();
//		String comment = Scode.edTag(tag) + " Quant=" + quant + "  " + cmnt;
////		dseg.emit(value, comment);
//		
//		quant.type.emitDefaultValue(dseg, comment);
//	}

	@Override
	public void print(final String indent) {
		System.out.println(indent + this);
	}
	
	public String toString() {
		String s = "Variable " +Kind.edKind(kind) + " " + tag + ", type=" + type + ", repCount=" + repCount+ " " + address;
//		if(system != null) s += " " + "SYSTEM " + system;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("Variable.Write: " + this);
		oupt.writeKind(kind); // K_GLOBAL, K_LOCAL, K_IMPORT, K_EXPORT, K_EXIT, K_RETUR
		tag.write(oupt);
		type.write(oupt);
		oupt.writeShort(repCount);
//		address.write(oupt);
		if(address != null) {
			oupt.writeBoolean(true);
			address.write(oupt);
		} else oupt.writeBoolean(false);
	}

	public static Variable read(AttributeInputStream inpt, int kind) throws IOException {
		Tag tag = Tag.read(inpt);
		Variable var = new Variable(kind, tag);
		var.type = Type.read(inpt);
		var.repCount = inpt.readShort();
//		var.address = (ObjectAddress) Value.read(inpt);
		boolean present = inpt.readBoolean();
		if(present) {
			var.address = (ObjectAddress) Value.read(inpt);
		}
		if(BecGlobal.ATTR_INPUT_TRACE) System.out.println("Variable.Read: " + var);
		return var;
	}


}
