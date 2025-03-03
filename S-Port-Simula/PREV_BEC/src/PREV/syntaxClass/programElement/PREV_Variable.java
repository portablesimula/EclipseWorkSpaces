package PREV.syntaxClass.programElement;

import java.io.IOException;

import PREV.syntaxClass.value.IntegerValue;
import PREV.syntaxClass.value.LongRealValue;
import PREV.syntaxClass.value.ObjectAddress;
import PREV.syntaxClass.value.RealValue;
import PREV.syntaxClass.value.RoutineAddress;
import PREV.syntaxClass.value.SizeValue;
import PREV.util.PREV_QuantityDescriptor;
import PREV.syntaxClass.value.PREV_Value;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.value.MemAddr;

public class PREV_Variable extends ProgramElement {
	public int instr; // S_GLOBAL, S_LOCAL, S_IMPORT, S_EXPORT, S_EXIT
	int tag;
	public MemAddr address;
	public PREV_QuantityDescriptor quant;
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
	 */
	public PREV_Variable() {
		
	}
	
	public static PREV_Variable ofIMPORT(DataSegment seg) {
		if(seg == null) Util.IERR("");
		PREV_Variable var = new PREV_Variable();
		var.instr = Scode.S_IMPORT;
		var.tag = Scode.inTag();
		var.quant = new PREV_QuantityDescriptor();
		var.address = seg.nextAddress();
		if(var.address.seg == null) Util.IERR("");
//		var.quant.type.emitDefaultValue(seg, "IMPORT " + var.quant);
//		System.out.println("Variable ofIMPORT: "+var);
		return var;
	}
	
	public static PREV_Variable ofEXPORT(DataSegment seg) {
		PREV_Variable var = new PREV_Variable();
		var.instr = Scode.S_EXPORT;
		var.tag = Scode.inTag();
		var.quant = new PREV_QuantityDescriptor();
		var.address = seg.nextAddress();
//		var.quant.type.emitDefaultValue(seg, "EXPORT " + var.quant);
		return var;
	}
	
	public static PREV_Variable ofEXIT(DataSegment seg) {
		PREV_Variable var = new PREV_Variable();
		var.instr = Scode.S_EXIT;
		var.tag = Scode.inTag();
		var.address = seg.nextAddress();
//		var.quant.type.emitDefaultValue(seg, "EXIT " + var.quant);
		return var;
	}
	
	public static PREV_Variable ofRETUR(DataSegment seg) {
		PREV_Variable var = new PREV_Variable();
		var.instr = Scode.S_EXIT;
		var.address = seg.nextAddress();
//		var.quant.type.emitDefaultValue(seg, "RETUR ");
		seg.emit(null, "RETUR");
		return var;
	}
	
	public PREV_Variable(int instr) {
		this.instr = instr;
		tag = Scode.inTag();
		if(instr != Scode.S_EXIT)
			quant = new PREV_QuantityDescriptor();

		address = Global.DSEG.nextAddress();
		if(Scode.accept(Scode.S_SYSTEM)) {
			String system = Scode.inString();
//			System.out.println("NEW Variable: " + Scode.edInstr(instr) + " " + Scode.edTag(tag) + " " + quant + " SYSTEM " + system);
			PREV_Value value = null;
			if(system.equalsIgnoreCase("CURINS")) value = new ObjectAddress(true);
			else if(system.equalsIgnoreCase("STATUS")) value = new IntegerValue(0);
			else if(system.equalsIgnoreCase("ITSIZE")) value = new IntegerValue(0);
			else if(system.equalsIgnoreCase("MAXLEN")) value = new SizeValue(20);
			else if(system.equalsIgnoreCase("INPLTH")) value = new IntegerValue(80);
			else if(system.equalsIgnoreCase("OUTLTH")) value = new IntegerValue(172);
			else if(system.equalsIgnoreCase("BIOREF")) value = new ObjectAddress(true);
			else if(system.equalsIgnoreCase("MAXINT")) value = new IntegerValue(Integer.MAX_VALUE);
			else if(system.equalsIgnoreCase("MININT")) value = new IntegerValue(Integer.MIN_VALUE);
			else if(system.equalsIgnoreCase("MAXRNK")) value = new IntegerValue(Byte.MAX_VALUE);
			else if(system.equalsIgnoreCase("MAXREA")) value = new RealValue(Float.MAX_VALUE);
			else if(system.equalsIgnoreCase("MINREA")) value = new RealValue(Float.MIN_VALUE);
			else if(system.equalsIgnoreCase("MAXLRL")) value = new LongRealValue(Double.MAX_VALUE);
			else if(system.equalsIgnoreCase("MINLRL")) value = new LongRealValue(Double.MIN_VALUE);
			else if(system.equalsIgnoreCase("INIERR")) value = new RoutineAddress(true);
			else if(system.equalsIgnoreCase("ALLOCO")) value = new RoutineAddress(true);
			else if(system.equalsIgnoreCase("FREEOB")) value = new RoutineAddress(true);
			else Util.IERR("MISSING: " + system);
//			Global.DSEG.emit(value, this.toString());
			Util.IERR("DON'T USE THIS METHOD");
		} else {
//			quant.type.emitDefaultValue(Global.DSEG, this.toString());			
			if(quant.repCount > 1) {
				Util.IERR("");
			}
		}
		
//		System.out.println("NEW Variable: Line "+this.lineNumber+": "+this);
//		Global.DSEG.dump();
//		Util.IERR("");
		
//		Global.Display.set(tag, this);
		
	}
	
	public void emit(DataSegment dseg, String cmnt) {
//		Value value = quant.type.defaultValue();
		String comment = Scode.edTag(tag) + " Quant=" + quant + "  " + cmnt;
//		dseg.emit(value, comment);
		
//		quant.type.emitDefaultValue(dseg, comment);
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		String s = Scode.edInstr(instr) + " " + Scode.edTag(tag) + " " + address;
//		if(system != null) s += " " + "SYSTEM " + system;
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	private PREV_Variable(AttributeInputStream inpt, int instr) throws IOException {
//		System.out.println("NEW Variable: inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		this.instr = instr;
//		System.out.println("NEW Variable: instr="+Scode.edInstr(instr));
		this.tag = inpt.readTag();
		this.quant = PREV_QuantityDescriptor.read(inpt);
		this.address = MemAddr.read(inpt);
		
//		this.printTree(2);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.TRACE_OUTPUT("BEGIN Write Variable: " + Scode.edTag(tag));
//		System.out.println("WRITE Variable: "+this);
//		Thread.dumpStack();
		oupt.writeKind(instr); // Mark: This is a S_GLOBAL, S_LOCAL, S_IMPORT, S_EXPORT, S_EXIT
		oupt.writeTag(tag);
		quant.write(oupt);
		address.write(oupt);
	}

	public static PREV_Variable readObject(AttributeInputStream inpt, int instr) throws IOException {
		return(new PREV_Variable(inpt, instr));
	}

}
