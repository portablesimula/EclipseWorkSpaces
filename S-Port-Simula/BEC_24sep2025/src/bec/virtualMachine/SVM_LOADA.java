package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.Reg;
import bec.util.Global;
import bec.util.Util;
import bec.value.Value;
import bec.value.dataAddress.DataAddress;


/**
 * Operation LOADA xReg rtAddr
 * 
 * xReg := rtAddr evaluated
 *
 * A Address is evaluated, then stored in register 'xReg'.
 * All involved register values are included in the resulting 'base' and 'ofst'.
 */
public class SVM_LOADA extends SVM_Instruction {
	DataAddress objadr;
	int xReg;
	
	private final boolean DEBUG = false;

	public SVM_LOADA(int xReg, DataAddress objadr) {
		this.objadr = objadr;
		this.xReg = xReg;
		this.opcode = SVM_Instruction.iPUSHA;
		Reg.reads("SVM_LOADA: ", objadr); Reg.writes("SVM_LOADA: ", xReg);
	}

	@Override
	public void execute() {
		if(DEBUG) IO.println("SVM_PUSHA.execute: BEGIN PUSHA: " + this);
		
//		DataAddress gaddrBase = null;
//		int gaddrOffset = this.offset;
//
//		// First use 'this.objadr' to evaluate GADDR'base and possibly GADDR'offset
//		switch(objadr.kind) {
//			case DataAddress.SEGMNT_ADDR:
//				gaddrBase = objadr; break;
//			case DataAddress.REMOTE_ADDR:
//				RemoteAddress remadr = (RemoteAddress) objadr;
//				SegmentAddress segadr = remadr.toSegAddr();
////				if(DEBUG) IO.println("SVM_PUSHA.execute: segadr="+segadr);
//				gaddrBase = segadr;
//				break;
//			case DataAddress.REL_ADDR: // referAddr = xReg:gaddr + ofst
//				ReferAddress refadr = (ReferAddress) objadr;
//				GeneralAddress gaddr = (GeneralAddress) RTRegister.getAddrValue(refadr.xReg);
//				gaddrBase = gaddr.base;
//				gaddrOffset += gaddr.ofst + refadr.ofst;
////				IO.println("SVM_PUSHA.execute: REFER: gaddrBase=" + gaddrBase + ", gaddrOffset=" + gaddrOffset);
//				break;
//			case DataAddress.FRAME_ADDR:
//			case DataAddress.STACK_ADDR:
//				Util.IERR("SVM_PUSHA.execute: NOTE: CREATE AN ADDRESS TO A VALUE ON THE RTSTACK: " + objadr);	    		
//		}
		
		DataAddress rtAddr = objadr.toRTAddress();
		RTStack.push(rtAddr, "SVM_PUSHA");

		Util.IERR("SJEKK DETTE NØYE !");
		Global.PSC.addOfst(1);
	}

	@Override	
	public String toString() {
		return "PUSHA    " + objadr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	private SVM_LOADA() {
		this.opcode = SVM_Instruction.iPUSHA;
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeReg(xReg);
		objadr.write(oupt);
	}

	public static SVM_LOADA read(AttributeInputStream inpt) throws IOException {
		SVM_LOADA instr = new SVM_LOADA();
		xReg = inpt.readReg();
		instr.objadr = (DataAddress) Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
