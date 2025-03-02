package bec.segment;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_Instruction;

public class ProgramSegment extends Segment {
	public Vector<SVM_Instruction> instructions;
	Vector<String> comment;

	public ProgramSegment(String ident, int segmentKind) {
		super(ident, segmentKind);
		this.ident = ident.toUpperCase();
		this.segmentKind = segmentKind;
		instructions = new Vector<SVM_Instruction>();
		comment = new Vector<String>();
	}
	
	public ProgramAddress nextAddress() {
		return new ProgramAddress(Type.T_PADDR, this,instructions.size());
	}
	
	public SVM_Instruction load(int index) {
		return instructions.get(index);
	}
	
	public void emit(SVM_Instruction value,String cmnt) {
		instructions.add(value);
		comment.add(cmnt);
	}
	
	public void dump(String title) {
		System.out.println("========================== " + title + ident + " DUMP ==========================");
		for(int i=0;i<instructions.size();i++) {
			String line = "" + i + ": ";
			while(line.length() < 8) line = " " +line;
			String value = ""+instructions.get(i);
			while(value.length() < 50) value = value + ' ';
			System.out.println(line + value + "   " + comment.get(i));
		}
		System.out.println("========================== " + title + ident + " END  ==========================");
	}

	public String toString() {
		return "ProgramSegment \"" + ident + '"';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private ProgramSegment(String ident, int segmentKind, AttributeInputStream inpt) throws IOException {
		super(ident, segmentKind);
		instructions = new Vector<SVM_Instruction>();
		comment = new Vector<String>();
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			comment.add(inpt.readString());
			instructions.add(SVM_Instruction.readObject(inpt));
		}
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("ProgramSegment.Write: " + this + ", Size=" + instructions.size());
//		this.dump("ProgramSegment.Write: ");
//		oupt.writeInstr(Scode.S_BSEG);
		oupt.writeKind(segmentKind);
		oupt.writeString(ident);
		oupt.writeShort(instructions.size());
		for(int i=0;i<instructions.size();i++) {
			oupt.writeString(comment.get(i));
			SVM_Instruction val = instructions.get(i);
			if(val == null)
				 oupt.writeInstr(Scode.S_NULL);
			else val.write(oupt);
		}
//		Util.IERR("");
	}

	public static ProgramSegment readObject(AttributeInputStream inpt) throws IOException {
//		int segmentKind = inpt.readKind();
		int segmentKind = Kind.K_SEG_CODE;
		String ident = inpt.readString();
//		System.out.println("ProgramSegment.readObject: ident="+ident+", segmentKind="+segmentKind);
		ProgramSegment seg = new ProgramSegment(ident, segmentKind, inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("ProgramSegment.Read: " + seg);
		if(Global.ATTR_INPUT_DUMP) seg.dump("ProgramSegment.readObject: ");
//		Util.IERR("");
		return seg;
	}
	

}
