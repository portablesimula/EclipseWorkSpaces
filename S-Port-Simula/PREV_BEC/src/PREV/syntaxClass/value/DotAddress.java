package PREV.syntaxClass.value;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;
import bec.util.Util;

public class DotAddress extends PREV_Value {
	Vector<Integer> attrTags;
	int globalOrConstTag;
	int terminator;
	
	public DotAddress() {
		attrTags = new Vector<Integer>();
		parse();
		this.type = (terminator == Scode.S_C_AADDR) ? Scode.TAG_AADDR : Scode.S_C_GADDR;
	}

	/**
	 * 	attribute_address ::= < c-dot attribute:tag >* c-aaddr attribute:tag
	 * 		::= anone
	 * 
	 * 	general_address
	 * 		::= < c-dot attr:tag >* c-gaddr global_or_const:tag
	 * 		::= gnone
	 */
	public void parse() {
		do {
			attrTags.add(Scode.inTag());
			Scode.inputInstr();
		} while (Scode.curinstr == Scode.S_C_DOT);

           terminator = Scode.curinstr;
           if(terminator == Scode.S_C_AADDR || terminator == Scode.S_C_GADDR) {
        	   globalOrConstTag = Scode.inTag();
           }
           else Util.IERR("Illegal termination of C-DOT value");
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Integer tag:attrTags) {
			sb.append("C-DOT ").append(tag);
		}
		sb.append(" " + Scode.edInstr(terminator) + " " + Scode.edTag(globalOrConstTag));
		return(sb.toString());
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private DotAddress(AttributeInputStream inpt) throws IOException {
		int n = inpt.readShort();
		attrTags = new Vector<Integer>();
		for(int i=0;i<n;i++) {
			int tag = inpt.readTag();
			attrTags.add(tag);
		}
		globalOrConstTag = inpt.readTag();
		terminator = inpt.readKind();
		this.type = (terminator == Scode.S_C_AADDR) ? Scode.TAG_AADDR : Scode.S_C_GADDR;
//		System.out.println("NEW IMPORT: " + this);
	}

//	Vector<Integer> attrTags;
//	int globalOrConstTag;
//	int terminator;
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_C_DOT);
		oupt.writeShort(attrTags.size());
		for(int t:attrTags) oupt.writeTag(t);
		oupt.writeTag(globalOrConstTag);
		oupt.writeKind(terminator);
	}

	public static DotAddress read(AttributeInputStream inpt) throws IOException {
		return new DotAddress(inpt);
	}
	

}
