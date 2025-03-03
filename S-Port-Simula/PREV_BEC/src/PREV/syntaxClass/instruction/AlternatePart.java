package PREV.syntaxClass.instruction;

import java.io.IOException;
import java.util.Vector;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.programElement.AttributeDefinition;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

/**
 *	alternate_part ::= alt <attribute_definition>*
 *		attribute_definition ::= attr attr:newtag quantity_descriptor
 *		resolved_structure ::= structured_type < fixrep count:ordinal >?
 *			structured_type ::= record_tag:tag
 *
 *		quantity_descriptor ::= resolved_type < Rep count:number >?
 * 
 */
class AlternatePart extends SyntaxClass {
	Vector<AttributeDefinition> attributes;	
	
	public AlternatePart() {
		attributes = new Vector<AttributeDefinition>();
	}

	public int size() {
		int n = 0;
		for(AttributeDefinition attr:attributes) {
			n = n + attr.size();
		}
		return n;
	}
	
	public void printTree(final int indent) {
		boolean first = true;
		for(AttributeDefinition attr:attributes) {
			if(first) { sLIST(indent, "ALT " + attr); first = false; }
			else sLIST(indent + 1, attr.toString());
		}
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private AlternatePart(AttributeInputStream inpt) throws IOException {
		attributes = new Vector<AttributeDefinition>();
		inpt.readInstr();
		while(inpt.curinstr == Scode.S_ATTR) {
			attributes.add(AttributeDefinition.read(inpt));
			inpt.readInstr();
		}
//		System.out.println("NEW AlternatePart:"); printTree(2);
//		Util.IERR(null);
	}
	
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_ALT);
		for(AttributeDefinition attr:attributes) attr.write(oupt);
	}

	public static AlternatePart read(AttributeInputStream inpt) throws IOException {
		return new AlternatePart(inpt);
	}

}
