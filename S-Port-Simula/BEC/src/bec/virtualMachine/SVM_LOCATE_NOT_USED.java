package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;


/**
 * addressing_instruction ::= locate
 * 
 * locate (dyadic)
 * force TOS value; check TOS type(AADDR);
 * force SOS value; check SOS type(OADDR,GADDR);
 * pop; pop;
 * push( VAL, GADDR, "value(SOS).BASE, value(SOS).OFFSET++value(TOS)" );
 * 
 * SOS and TOS are replaced by a description of the general address value
 * formed by "addition" of the two original addresses.
 * 
 *                               .===========================.
 *                               |                           |
 *                               |                           |
 *                               |                           |
 *                               |                           |
 *      (SOS) -------------------|-------->.=============.   |
 *                               |         |   |         |   |
 *                               |         |   | (TOS)   |   |
 *                               |         |   V         |   |
 *    The resulting              |         |   .=====,   |   |
 *         TOS ------------------|---------|-->| : : |   |   |
 *    after locate               |         |   '====='   |   |
 *                               |         |             |   |
 *                               |         |             |   |
 *                               |         '============='   |
 *                               |                           |
 *                               |                           |
 *                               |                           |
 *                               '==========================='
 *
 * Remove two items on the Runtime-Stack and push the resulting GeneralAddress' base and offset.
 */
public class SVM_LOCATE_NOT_USED extends SVM_Instruction {
	boolean sosIsGADDR;
	
	private final boolean DEBUG = true;

//	public SVM_LOCATE_NOT_USED(boolean sosIsGADDR) {
//		this.opcode = SVM_Instruction.iLOCATE;
//	}

	@Override
	public void execute() {
//		RTStack.callStackTop.dump("SVM_LOCATE-1: ");
		Value tos = RTStack.pop().value();
		Value sos = RTStack.pop().value();
		if(DEBUG) {
			if(tos != null)	System.out.println("SVM_LOCATE: TOS: " + tos.getClass().getSimpleName() + "  " + tos);
			if(sos != null)	System.out.println("SVM_LOCATE: SOS: " + sos.getClass().getSimpleName() + "  " + sos);
			System.out.println("SVM_LOCATE: " + sos + " + " + tos + ", sosIsGADDR=" + sosIsGADDR);
		}
		if(sosIsGADDR) {
//		Value res = (tos == null)? sos : tos.add(sos);
			Value res = (sos == null)? tos : sos.add(tos);
			if(DEBUG) System.out.println("SVM_LOCATE: " + sos + " + " + tos + " ==> " + res);
			RTStack.push(res, "SVM_LOCATE: " + tos + " + " + sos + " = " + res);
		} else {
//			ObjectAddress base = (ObjectAddress) sos;
//			IntegerValue offset = (IntegerValue)tos;
//			if(DEBUG) {
//				Value res = new GeneralAddress(base, offset.value);
//				System.out.println("SVM_LOCATE: " + sos + " + " + tos + " ==> " + res);
//			}
//			RTStack.push(base, "SVM_LOCATE: Base");
//			RTStack.push(offset, "SVM_LOCATE: Offset");
			
			RTStack.push(sos, "SVM_LOCATE: Base");
			RTStack.push(tos, "SVM_LOCATE: Offset");
			// NOTE: The code above is a noop !!!
		}
		
		Global.PSC.ofst++;
//		RTStack.callStackTop.dump("SVM_LOCATE-2: ");
//		Util.IERR(""+res);
	}

	@Override	
	public String toString() {
		return "LOCATE   ";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeBoolean(sosIsGADDR);
	}

//	public static SVM_LOCATE_NOT_USED read(AttributeInputStream inpt) throws IOException {
//		SVM_LOCATE_NOT_USED instr = new SVM_LOCATE_NOT_USED(inpt.readBoolean());
//		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
//		return instr;
//	}

}
