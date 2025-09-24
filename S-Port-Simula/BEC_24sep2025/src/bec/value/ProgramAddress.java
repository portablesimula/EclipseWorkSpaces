package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Descriptor;
import bec.descriptor.LabelDescr;
import bec.descriptor.RoutineDescr;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.EndProgram;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.CallStackFrame;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.RTUtil;
import bec.virtualMachine.SVM_CALL;
import bec.virtualMachine.SVM_CALL_SYS;
import bec.virtualMachine.SVM_Instruction;
import bec.virtualMachine.SVM_RETURN;

public class ProgramAddress extends Value {
	public String segID;
	private int ofst;
	
	public ProgramAddress(Type type, String segID, int ofst) {
		this.type = type;
		this.segID = segID;
		this.ofst = ofst;
	}
	
	/**
	 * attribute_address	::= c-aaddr attribute:tag
	 * object_address		::= c-oaddr global_or_const:tag
	 * general_address		::= c-gaddr global_or_const:tag
	 * routine_address		::= c-raddr body:tag
	 * program_address		::= c-paddr label:tag
	 */
	public static ProgramAddress ofScode(Type type) {
		Tag tag = Tag.ofScode();
		Descriptor descr = tag.getMeaning();
		if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
//		IO.println("ProgramAddress.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
		if(type == Type.T_RADDR) return ((RoutineDescr)descr).getAddress();
		if(type == Type.T_PADDR) return ((LabelDescr)descr).getAddress();
		Util.IERR("ProgramAddress: NOT IMPL "+type);
		return null;
	}
	
	public int getOfst() {
		return this.ofst;
	}
	
	public void setOfst(int ofst) {
//		if(segID.equalsIgnoreCase("PSEG_ADHOC00")) {
//			if(this.ofst == 51) {
//				IO.println("ProgramAddress.setOfst: NEW OFFSET: "+this.ofst+" ===> "+ofst);
//				Util.IERR("");
//			}
//		}
		this.ofst = ofst;
	}
	
	public void addOfst(int incr) {
//		if(segID.equalsIgnoreCase("PSEG_ADHOC00")) {
//			if(this.ofst == 51) {
//				IO.println("ProgramAddress.addOfst: NEW OFFSET: "+this.ofst+" ===> "+(this.ofst + incr));
//				Util.IERR("");
//			}
//		}
		this.ofst = this.ofst + incr;
	}
	
	public Segment segment() {
		if(segID == null) return null;
		return Segment.lookup(segID);
	}
	
	public ProgramAddress copy() {
		return new ProgramAddress(type, segID, ofst);
	}
	
	@Override
	public void emit(DataSegment dseg, String comment) {
		dseg.emit(this, comment);
	}

	@Override
	public boolean compare(int relation, Value other) {
		String RHSegID = (other == null)? null : ((ProgramAddress)other).segID;
		int rhs = (other == null)? 0 : ((ProgramAddress)other).ofst;
		return Segment.compare(segID, ofst, relation, RHSegID, rhs);
	}
	
	public void execute() {
		ProgramSegment seg = (ProgramSegment) segment();
		int size = seg.instructions.size();
		if(size == 0) {
			throw new EndProgram(-1,"ProgramAddress.execute: " + seg.ident + " IS EMPTY -- NOTHING TO EXECUTE");
		}
		
		if(ofst >= size) {
			if(Global.DUMPS_AT_EXIT) {
//				Segment.lookup("DSEG_ADHOC02").dump("ProgramAddress.execute: ");
				Global.DSEG.dump("ProgramAddress.execute: FINAL DATA SEGMENT ");
				Global.CSEG.dump("ProgramAddress.execute: FINAL CONSTANT SEGMENT ");
				Global.TSEG.dump("ProgramAddress.execute: FINAL CONSTANT TEXT SEGMENT ");
//				Segment.lookup("DSEG_RT").dump("ProgramAddress.execute: BIOINS", 30, 82);
//				Segment.lookup("POOL_1").dump("ProgramAddress.execute: FINAL POOL_1", 0, 60);
				RTUtil.printPool("POOL_1");

			}
			RTStack.checkStackEmpty();
			throw new EndProgram(0,"ProgramAddress.execute: " + seg.ident + " IS FINALIZED -- NOTHING MORE TO EXECUTE");
		} else {
			SVM_Instruction cur = seg.instructions.get(ofst);
//			IO.println("ProgramAddress.execute: " + cur);
			cur.execute();

			if(Global.EXEC_TRACE > 0) {
//				IO.println("ProgramAddress.execute: "+cur.getClass().getSimpleName());
				if(cur instanceof SVM_CALL)        ; // NOTHING
				else if(cur instanceof SVM_RETURN) ; // NOTHING
				else if(cur instanceof SVM_CALL_SYS) ; // NOTHING
				else printInstr(cur,true);
			}
		}
	}
	
	public static void printInstr(SVM_Instruction cur,boolean decr) {
		printInstr(cur.toString(), decr);
	}
	
	public static void printInstr(String cur,boolean decr) {
		CallStackFrame callStackTop = RTStack.callStack_TOP();
		String tail = (callStackTop == null)? RTStack.toLine() : callStackTop.toLine();
		ProgramAddress paddr = Global.PSC.copy();
		if(decr) paddr.ofst--;
		String line = "EXEC: "+paddr+"  "+cur;
		while(line.length()<70) line=line+' ';
		IO.println(line+"   "+tail);
//		RTStack.dumpRTStack("ProgramAddress.execute:");
//		Util.IERR("");
		
	}
	
	public String toString() {
		String s = (segID == null) ? "RELADR" : segID;
		return s + '[' + ofst + ']';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private ProgramAddress(AttributeInputStream inpt) throws IOException {
		type = Type.read(inpt);
		segID = inpt.readString();
		ofst = inpt.readShort();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_PADDR);
		type.write(oupt);
		oupt.writeString(segID);
		oupt.writeShort(ofst);
	}

	public static ProgramAddress read(AttributeInputStream inpt) throws IOException {
		return new ProgramAddress(inpt);
	}

}
