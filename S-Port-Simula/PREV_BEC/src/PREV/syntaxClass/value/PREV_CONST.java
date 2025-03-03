package PREV.syntaxClass.value;

import java.io.IOException;
import java.util.Vector;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.instruction.PREV_Instruction;
import PREV.util.PREV_QuantityDescriptor;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.MemAddr;

public class PREV_CONST extends PREV_Instruction {
	int tag;
	public PREV_QuantityDescriptor quant;
	RepetitionValue value;
	public MemAddr address;
	
	private PREV_CONST(int tag) {
		this.tag = tag;
		Global.Display.set(tag, this);
	}

//	%title ***   C o n s t   and   C o n s t s p e c   ***
	/**
	 *	constant_specification
	 *		::= constspec const:newtag quantity_descriptor
	 *
	 *	constant_definition
	 *		::= const const:spectag quantity_descriptor repetition_value
	 */
	public static PREV_CONST of(boolean constDef) {
//		Tag tag = Tag.inTag();
		int tag=0;
		PREV_CONST cnst = (PREV_CONST) Global.Display.get(tag);
		if(cnst == null) {
			if(constDef) Util.IERR("New CONSPEC but constDef="+constDef);
			cnst = new PREV_CONST(tag);
		}
		cnst.quant = new PREV_QuantityDescriptor();
//		if(constDef) cnst.value = new RepetitionValue();
		if(constDef) {
			String comment = Scode.edTag(tag) + " Quant=" + cnst.quant;
//			System.out.println("NEW CONST: "+comment);
			cnst.address = Global.CSEG.emitValue(comment);
//			Global.CSEG.dump();
//			Util.IERR("");
		}
		
//		if(Scode.inputTrace > 3)
//			cnst.printTree(2);
//		Util.IERR("");
		return cnst;
	}

	@Override
	public void printTree(final int indent) {
		if(value != null) {
			boolean done = false;
			if(value.values instanceof Vector<PREV_Value> vector) {
				if(vector instanceof Vector<?> elts) {
					boolean first = true;
					for(Object rVal:elts) {
						if(first) sLIST(indent, "CONST " + Scode.edTag(tag) + " " + quant);
						first = false;
						((PREV_Value)rVal).printTree(indent + 1);							
					} done = true;
				}
			}
			if(! done) sLIST(indent + 1, toString());
		} else sLIST(indent + 1, toString());
	}
	
	public String toString() {
		if(address != null) {
			 return "CONST " + Scode.edTag(tag) + " " + quant + " " + address;
		} else if(value != null) {
			 return "CONST " + Scode.edTag(tag) + " " + quant + " " + value;
		} else return "CONSTSPEC " + Scode.edTag(tag) + " " + quant;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	public PREV_CONST(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag();
		quant = PREV_QuantityDescriptor.read(inpt);
//		value = RepetitionValue.read(inpt);
		address = MemAddr.read(inpt);

		System.out.println("NEW CONST: ");
		this.printTree(2);
//		Util.IERR("");
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.TRACE_OUTPUT("BEGIN Write CONST: " + Scode.edTag(tag) + "  Address="+address);
		oupt.writeKind(Scode.S_CONST); // Mark: This is a ClassDeclaration
		oupt.writeTag(tag);
		quant.write(oupt);
//		value.write(oupt);
		address.write(oupt);
//		oupt.writeInstr(Scode.S_ENDPROFILE);		
	}

	public static SyntaxClass readObject(AttributeInputStream inpt) throws IOException {
		return new PREV_CONST(inpt);
	}


}
