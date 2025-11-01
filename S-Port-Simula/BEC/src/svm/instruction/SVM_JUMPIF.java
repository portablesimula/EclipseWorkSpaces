/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package svm.instruction;

import java.io.IOException;

import bec.Global;
import bec.Option;
import bec.scode.Relation;
import bec.scode.Sinstr;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Util;
import svm.RTStack;
import svm.value.ProgramAddress;
import svm.value.Value;

/// SVM-INSTRUCTION: JUMPIF relation typeSize paddr
/// 
/// 	Runtime Stack
/// 	   ..., sos, tos →
/// 	   ...
///
/// The 'tos' and 'sos' are popped off the Runtime stack.
/// The 'result' is calculated as result = sos relation tos.
/// Note: Both 'tos' abd 'sos' may be multi-sized.
///
/// Conditional Jump to paddr.
///
/// A conditional jump is executed, branching only if the relation evaluates true.
/// Ie. The Program Sequence Control PCS := paddr
/// otherwise PCS is incremented by one.
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_JUMPIF.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_JUMPIF extends SVM_JUMP {
	private final Relation relation;
	private final int typeSize;

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
//			IO.println("SVM_JUMPIF: " + tos + "  " + relation + "  " + sos);
			doJump = relation.compare(sos, tos);
			if(DEBUG) {
				String jmp = (doJump)? "DO JUMP" : "NOT JUMP";
				IO.println("SVM_JUMPIF: " + tos + "  " + relation + "  " + sos + " = " + doJump + "  " + jmp);
			}
		} else {
			Value[] TOS = new Value[typeSize];
			Value[] SOS = new Value[typeSize];
			for(int i=0;i<typeSize;i++) TOS[i] = RTStack.pop();
			for(int i=0;i<typeSize;i++) SOS[i] = RTStack.pop();
			boolean equals = true;
			Relation eqRel = new Relation(Sinstr.S_EQ);
			LOOP:for(int i=0;i<typeSize;i++) {
				if(! eqRel.compare(SOS[i], TOS[i])) {
					equals = false; break LOOP;
				}
			}
			switch(relation.relation) {
				case Sinstr.S_EQ: doJump = equals; break;
				case Sinstr.S_NE: doJump = ! equals; break;
				default: Util.IERR("");
			}
		}
		
		if(doJump) Global.PSC = destination.copy();
		else Global.PSC.ofst++;
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
		this.typeSize = inpt.readUnsignedByte();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeByte(opcode);
		destination.write(oupt);
		relation.write(oupt);
		oupt.writeByte(typeSize);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_JUMPIF(inpt);
	}

}
