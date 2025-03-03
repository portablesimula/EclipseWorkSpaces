package PREV.syntaxClass.instruction;

import PREV.syntaxClass.programElement.AttributeDefinition;
import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.DataType;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_NOT_IMPL;

/**
 * select attr:tag
 * 
 * check TOS ref;
 * TOS.TYPE := attr.TYPE;
 * "TOS.OFFSET := TOS.OFFSET ++ attr.OFFSET";
 * 
 * (note that the BASE component of TOS is unchanged)
 * The area described by TOS is considered to be holding a record of the type, say 'REC', in which
 * the instruction argument attr is an attribute. TOS is modified to describe the designated
 * component of that record. Note that no qualification check is implied, i.e. TOS.TYPE may be
 * different from 'REC'.
 */
public class SELECT extends PREV_Instruction {
	int instr;
	int tag;
	
	public SELECT(int instr) {
		this.instr = instr;
		parse();
	}

	/**
	 * addressing_instruction ::= select attribute:tag | selectv attribute:tag
	 */
	public void parse() {
//		Util.IERR("NOT IMPLEMENTED");
		tag = Scode.inTag();
	}

	@Override
	public void doCode() {
//		CTStack.dumpStack();
		CTStack.checkTosRef();
		AttributeDefinition attr = (AttributeDefinition) Global.getMeaning(tag);
		Address adr = (Address) CTStack.TOS;
		adr.offset = adr.offset + attr.rela;
		adr.type = attr.quant.type.tag;
		adr.repdist = DataType.typeSize(adr.type);
		if(adr.atrState == Address.State.FromConst) {
			adr.atrState = Address.State.NotStacked;
			Global.PSEG.emit(new SVM_NOT_IMPL(), ""+this);
//             qPOPKill(AllignFac);
		}
		if(instr == Scode.S_SELECTV) Util.GQfetch("SELECTV " + Scode.edTag(tag) + ": ");
//		CTStack.dumpStack();
//		Util.IERR("");
	}
	
	@Override
	public void printTree(final int indent) {
//		sLIST(indent, toString() + Scode.edTag(tag));
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr) + " " + Scode.edTag(tag);
	}
	

}
