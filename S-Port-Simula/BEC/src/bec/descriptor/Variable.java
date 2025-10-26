/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.descriptor;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CALL;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Option;
import bec.util.Type;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.LongRealValue;
import bec.value.ObjectAddress;
import bec.value.RealValue;
import bec.value.Value;

/// Global or local Variable.
///
/// S-CODE:
///
/// 	global_definition ::= global internal:newtag quantity_descriptor
/// 
/// 	local_quantity ::= local var:newtag quantity_descriptor
/// 
/// 	import_definition ::= import parm:newtag quantity_descriptor
/// 
///		export parm:newtag resolved_type
/// 
///		exit return:newtag
///
///			quantity_descriptor ::= resolved_type < Rep count:number >?
///  
/// 		resolved_type
/// 	 		::= resolved_structure | simple_type
/// 	 		::= INT range lower:number upper:number
/// 	 		::= SINT
///  
/// 	 		resolved_structure ::= structured_type < fixrep count:ordinal >?
///  
///  				structured_type ::= record_tag:tag
///
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/descriptor/Variable.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class Variable extends Descriptor {
	public ObjectAddress address;
	public Type type;
	public int repCount;

	private Variable(int kind, Tag tag) {
		super(kind, tag);
	}
	
	public static Variable ofIMPORT() {
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_Import, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		return var;
	}
	
	public static Variable ofEXPORT() {
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_Export, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		return var;
	}
	
	public static Variable ofEXIT() {
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_Exit, tag);
		var.type = Type.T_PADDR;
		return var;
	}
	
	public static Variable ofRETUR(ObjectAddress returAddr) {
		Variable var = new Variable(Kind.K_Retur, null);
		var.type = Type.T_PADDR;
		var.address = returAddr;
		return var;
	}
	
	public static Variable ofLocal(int rela) {
		Tag tag = Tag.ofScode();
		Variable var = new Variable(Kind.K_LocalVar, tag);
		var.type = Type.ofScode();
		var.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
		var.address = ObjectAddress.ofRelFrameAddr(rela);
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
			Global.DSEG.emit(value);
		} else {
			int count = var.type.size();
			if(Scode.accept(Scode.S_FIXREP)) {
				int fixrep = Scode.inNumber();
				RecordDescr rec = (RecordDescr) Global.getMeaning(var.type.tag);
				count = count + rec.rep0size * fixrep;
			}
			if(count == 0) Util.IERR("");
			seg.emitDefaultValue(count, var.repCount);
		}
		return var;
	}

	@Override
	public void print(final String indent) {
		IO.println(indent + this);
	}
	
	public String toString() {
		String s = "Variable " +Kind.edKind(kind) + " " + tag + ", type=" + type + ", repCount=" + repCount+ " " + address;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("Variable.Write: " + this);
		oupt.writeByte(kind); // K_GLOBAL, K_LOCAL, K_IMPORT, K_EXPORT, K_EXIT, K_RETUR
		tag.write(oupt);
		type.write(oupt);
		oupt.writeShort(repCount);
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
		boolean present = inpt.readBoolean();
		if(present) {
			var.address = (ObjectAddress) Value.read(inpt);
		}
		if(Option.ATTR_INPUT_TRACE) IO.println("Variable.Read: " + var);
		return var;
	}


}
