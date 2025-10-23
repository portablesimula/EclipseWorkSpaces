package bec.segment;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.util.Option;
import bec.util.Scode;
import bec.util.Type;
import bec.value.ProgramAddress;
import bec.virtualMachine.SVM_Instruction;

public class ProgramSegment extends Segment {
	public Vector<SVM_Instruction> instructions;

	public ProgramSegment(String ident, int segmentKind) {
		super(ident, segmentKind);
		this.ident = ident.toUpperCase();
		this.segmentKind = segmentKind;
		instructions = new Vector<SVM_Instruction>();
	}
	
	public ProgramAddress nextAddress() {
		return new ProgramAddress(Type.T_PADDR, this.ident, instructions.size());
	}
	
	public SVM_Instruction load(int index) {
		return instructions.get(index);
	}
	
	public void emit(SVM_Instruction value) {
		instructions.add(value);
	}
	
	private int lastListed;
	public void listInstructions() {
		while(lastListed < instructions.size()) {
			listIntruction("                                 ==> ",lastListed++);
		}
	}
	
	public void listIntruction(String indent, int idx) {
		String line = ident + "[" + idx + "] ";
		while(line.length() < 8) line = " " +line;
		String value = ""+instructions.get(idx);
		IO.println(indent + line + value);
		
	}
	
	@Override
	public void dump(String title) {
		dump(title,0,instructions.size());
	}
	
	@Override
	public void dump(String title,int from,int to) {
		IO.println("========================== " + title + ident + " DUMP ==========================");
		for(int i=from;i<to;i++) {
			listIntruction("",i);
		}
		IO.println("========================== " + title + ident + " END  ==========================");
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
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			instructions.add(SVM_Instruction.readObject(inpt));
		}
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE)
			IO.println("ProgramSegment.Write: " + this + ", Size=" + instructions.size());
		oupt.writeKind(segmentKind);
		oupt.writeString(ident);
		oupt.writeShort(instructions.size());
		for(int i=0;i<instructions.size();i++) {
			SVM_Instruction val = instructions.get(i);
			if(val == null)
				 oupt.writeInstr(Scode.S_NULL);
			else val.write(oupt);
		}
	}

	public static ProgramSegment readObject(AttributeInputStream inpt) throws IOException {
//		int segmentKind = inpt.readKind();
		int segmentKind = Kind.K_SEG_CODE;
		String ident = inpt.readString();
//		IO.println("ProgramSegment.readObject: ident="+ident+", segmentKind="+segmentKind);
		ProgramSegment seg = new ProgramSegment(ident, segmentKind, inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("ProgramSegment.Read: " + seg);
		if(Option.ATTR_INPUT_DUMP) seg.dump("ProgramSegment.readObject: ");
//		Util.IERR("");
		return seg;
	}
	

}
