package bec.value.dataAddress;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Relation;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;
import bec.virtualMachine.CallStackFrame;
import bec.virtualMachine.RTStack;

/**
 * RTRemoteAddress:
 * 
 * 
 *     RTStack ---------------------->.=====================.
 *                                   |                     |
 *                                   .                     .
 *                                   .                     .
 *                                   .                     .
 *                                   |                     |
 *     Frame: RTStack.frameIndex --->.=====================.
 *                                   |       EXPORT        |
 *                                   |       IMPORT        |
 *                                   |        ...          |
 *                                   |       IMPORT        |
 *                                   |       RETUR         |
 *    FrameAddress(ofst) ------------|-----> LOCAL         |
 *                                   |                     |
 *                                   |       LOCAL         |
 *                                   '====================='
 *                                   |                     |
 *                                   .                     .
 *                                   .                     .
 *                                   .                     .
 *                                   |                     |
 *                                   '====================='
 */
public class FrameAddress extends DataAddress {
	
	public FrameAddress(int ofst) {
		this.kind = DataAddress.FRAME_ADDR;
		this.type = Type.T_OADDR;
		this.ofst = ofst;
	}

	@Override
	public Value copy() {
		return new FrameAddress(ofst);
	}

	@Override
	public FrameAddress addOffset(int ofst) {
		return new FrameAddress(this.ofst + ofst);
	}

	public StackAddress toStackAddress() {
//		DataAddress oaddr = this;
//		IO.println("DataAddress.toStackAddress: OADDR: "+this);
//		RTStack.dumpRTStack("DataAddress.toStackAddress: NOTE: ");
//		IO.println("DataAddress.toStackAddress: VALUE: "+this.load());
			
		CallStackFrame callStackTop = RTStack.callStack_TOP();
		int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
		StackAddress oaddr = new StackAddress(bias + ofst);
			
//		IO.println("DataAddress.toStackAddress: VALUE: "+oaddr.load());
//		Util.IERR("");
		return oaddr;
	}

	@Override
	public void store(int idx, Value value, String comment) {
		int frmx = RTStack.frameIndex();
		RTStack.store(frmx + ofst + idx, value, comment);

//		Util.IERR("");
	}
	
	@Override
	public Value load(int idx) {
		// load rel-addr  callStackTop + ofst
		CallStackFrame callStackTop = RTStack.callStack_TOP();
		int bias = (callStackTop == null)? 0 : callStackTop.rtStackIndex;
		Value value = RTStack.load(bias + ofst + idx);
//		IO.println("FrameAddress.load: value="+value);
//		Util.IERR("");
		return value;
	}

	@Override
	public boolean compare(int relation, Value other) {
		FrameAddress othr = (FrameAddress) other;
		boolean res = Relation.compare(this.ofst, relation, othr.ofst);
//		boolean res = Relation.compare(othr.ofst, relation, this.ofst);
		IO.println("FrameAddress.compare: " + (this.ofst) + " " + Scode.edInstr(relation) + " " + (othr.ofst) + " ==> " + res);
//		Util.IERR("");
		return res;
	}

	@Override
	public String toString() {
		return "FRM_ADR[callStackTop+" + ofst + ']';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private FrameAddress(AttributeInputStream inpt) throws IOException {
		kind = DataAddress.FRAME_ADDR;
		type = Type.T_OADDR;
		ofst = inpt.readShort();
//		IO.println("FrameAddress.read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("FrameAddress.write: " + this);
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeKind(kind);
		oupt.writeShort(ofst);
//		IO.println("DataAddress.write: " + this + "   segID="+segID+", ofst="+ofst);
	}

//	@Override
//	public void writeBody(AttributeOutputStream oupt) throws IOException {
//		oupt.writeShort(ofst);
//	}
	
	public static FrameAddress read(AttributeInputStream inpt) throws IOException {
		return new FrameAddress(inpt);
	}

}
