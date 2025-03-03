package PREV.syntaxClass.programElement;

import java.io.IOException;

import PREV.util.PREV_QuantityDescriptor;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class AttributeDefinition extends ProgramElement {
	public int tag;
	public PREV_QuantityDescriptor quant;
	public int rela;
	
	public AttributeDefinition(int tag, PREV_QuantityDescriptor quant, int rela) {
		this.tag = tag;
		this.quant = quant;
		this.rela = rela;
	}

	/**
	 *	attribute_definition ::= attr attr:newtag quantity_descriptor
	 */
	public AttributeDefinition(int rela) {
		this.rela = rela;
		tag = Scode.inTag();
//		System.out.println("NEW AttributeDefinition: tag="+tag);
		quant = new PREV_QuantityDescriptor();
//		System.out.println("NEW AttributeDefinition: " + this);
	}
	
	public int size() {
		return quant.size();
	}
	
	public String toString() {
		return "ATTR[" + rela + "] " + Scode.edTag(tag) + " " + quant;
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	public AttributeDefinition(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag();
		rela = inpt.readShort();
		quant = PREV_QuantityDescriptor.read(inpt);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_ATTR);
		oupt.writeTag(tag);
		oupt.writeShort(rela);
		quant.write(oupt);
	}

	public static AttributeDefinition read(AttributeInputStream inpt) throws IOException {
		return(new AttributeDefinition(inpt));
	}

}
