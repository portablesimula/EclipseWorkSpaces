package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.ObjectAddress;

/**
 * 
 * refer resolved_type
 * force TOS value; check TOS type(GADDR);
 * TOS.MODE := REF; TOS.TYPE := type;
 * 
 * TOS is modified to describe a quantity of the given type, at the address described by TOS.
 * 
 * RTStak: TOS: gaddr'offset
 *         SOS: gaddr'objaddr
 *         
 * TOS and SOS is removed to form a GADDR value and store it in xReg.
 * 
 *                           =================
 *       (TOS) ==============|==> GADDR VALUE –|----------.
 *                           =================            |
 *                                                        |
 *                                                        |
 *  The resulting                                         V
 *       TOS -------------------------------------------->.==========.
 *   after refer                  REF                     |  object  |
 *                                                        |    of    |
 *                                                        |  "type"  |
 *                                                        '=========='
 */
/**
 * Operation REFER
 * 
 * Runtime Stack
 *    ..., objadr, offset →
 *    ..., gaddr
 *
 * The 'obj' and 'offset' are poped of the Runtime Stack to form a General Address 'gaddr'.
 * The 'gaddr' is pushed onto the Runtime Stack.
 */
public class DELETED_SVM_REFER extends SVM_Instruction {
//	int xReg;       // IKKE HVIS Option.TESTING_xREG
//
//	public SVM_REFER_DELETED(int xReg) {
//		if(Option.TESTING_xREG) Util.IERR("");
//		this.opcode = SVM_Instruction.iREFER_NOT_USED;
//		this.xReg=xReg;
//		RTRegister.writes("SVM_REFER", xReg);
//	}
//
//	public SVM_REFER_DELETED() {
//		if(! Option.TESTING_xREG) Util.IERR("");
//		this.opcode = SVM_Instruction.iREFER_NOT_USED;
//	}
//
//	@Override
//	public void execute() {
//		if(Option.TESTING_REFER) {
//			// NOTHING
//		} else {
//		int gOfst = RTStack.popInt();
//		ObjectAddress objadr = (ObjectAddress) RTStack.pop();
//		
//		if(Option.TESTING_xREG) {
//			RTStack.push(new GeneralAddress(objadr, gOfst), "REFER: ");
//		} else {
//			RTRegister.putValue(xReg, new GeneralAddress(objadr, gOfst));
//		}
//		}
//
//		Global.PSC.addOfst(1);
//	}
//	
//	@Override	
//	public String toString() {
//		if(Option.TESTING_xREG) {
//			return "REFER    STACKED";
//		} else {
//			return "REFER    " + "R" + xReg;
//		}
//	}
//	
//	// ***********************************************************************************************
//	// *** Attribute File I/O
//	// ***********************************************************************************************
//	private SVM_REFER_DELETED(AttributeInputStream inpt) throws IOException {
//		this.opcode = SVM_Instruction.iREFER_NOT_USED;
//		if(! Option.TESTING_xREG) {
//			this.xReg = inpt.readReg();
//		}
//		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
//	}
//
//	@Override
//	public void write(AttributeOutputStream oupt) throws IOException {
//		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
//		oupt.writeOpcode(opcode);
//		if(! Option.TESTING_xREG) {
//			oupt.writeReg(xReg);
//		}
//	}
//
//	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
//		return new SVM_REFER_DELETED(inpt);
//	}

}
