package svm.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Util;
import svm.value.ProgramAddress;
import svm.value.Value;

public class SVM_JUMPIF extends SVM_JUMP {
	Relation relation;
	int typeSize;

	private static final boolean DEBUG = false;
	
	public SVM_JUMPIF(Relation relation, int typeSize, ProgramAddress destination) {
		super(destination);
		this.opcode = SVM_Instruction.iJUMPIF;
		this.relation = relation;
		this.typeSize =  typeSize;
	}

	@Override
	public void execute() {
		boolean doJump = false;
		if(typeSize == 1) {
			Value tos = RTStack.pop();
			Value sos = RTStack.pop();
//			System.out.println("SVM_JUMPIF: " + tos + "  " + relation + "  " + sos);
			doJump = relation.compare(sos, tos);
			if(DEBUG) {
				String jmp = (doJump)? "DO JUMP" : "NOT JUMP";
				System.out.println("SVM_JUMPIF: " + tos + "  " + relation + "  " + sos + " = " + doJump + "  " + jmp);
			}
		} else {
			Value[] TOS = new Value[typeSize];
			Value[] SOS = new Value[typeSize];
			for(int i=0;i<typeSize;i++) TOS[i] = RTStack.pop();
			for(int i=0;i<typeSize;i++) SOS[i] = RTStack.pop();
			boolean equals = true;
			Relation eqRel = new Relation(Scode.S_EQ);
			LOOP:for(int i=0;i<typeSize;i++) {
				if(! eqRel.compare(SOS[i], TOS[i])) {
					equals = false; break LOOP;
				}
			}
			switch(relation.relation) {
				case Scode.S_EQ: doJump = equals; break;
				case Scode.S_NE: doJump = ! equals; break;
				default: Util.IERR("");
			}
		}
		
		if(doJump) Global.PSC = destination.copy();
		else Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "JUMPIF   " + relation + " " + destination;
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_JUMPIF(AttributeInputStream inpt) throws IOException {
		super(inpt);
		this.opcode = SVM_Instruction.iJUMPIF;
		this.relation = Relation.read(inpt);
		this.typeSize = inpt.readKind();
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		destination.write(oupt);
		relation.write(oupt);
		oupt.writeKind(typeSize);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_JUMPIF(inpt);
	}

}
