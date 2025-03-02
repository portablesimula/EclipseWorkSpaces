package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Descriptor;
import bec.descriptor.LabelDescr;
import bec.descriptor.RoutineDescr;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.SVM_Instruction;

public class ProgramAddress extends Value {
//	public Segment seg;
	String segID;
	public int ofst;
	
	private ProgramAddress(String segID,	int ofst) {
		this.segID = segID;
		this.ofst = ofst;
		if(ofst > 9000 || ofst < 0) Util.IERR("");
	}
	
	public ProgramAddress(Type type, Segment seg,	int ofst) {
		this.type = type;
//		this.seg = seg;
		if(seg != null)	this.segID = seg.ident;
		this.ofst = ofst;
		if(ofst > 9000 || ofst < 0) Util.IERR("");
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
		System.out.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
		if(type == Type.T_RADDR) return ((RoutineDescr)descr).getAddress();
		if(type == Type.T_PADDR) return ((LabelDescr)descr).getAddress();
		Util.IERR("NOT IMPL");
		return null;
	}
	
	public ProgramAddress ofset(int ofst) {
		return new ProgramAddress(segID, this.ofst + ofst);
	}
	
	public Segment segment() {
		if(segID == null) return null;
		return Segment.lookup(segID);
	}
	
//	public void store(Value value) {
//		DataSegment dseg = (DataSegment) segment();
//		dseg.store(ofst, value);
////		dseg.dump("MemAddr.store: ");
////		Util.IERR("");
//	}
//	
//	public Value load() {
//		DataSegment dseg = (DataSegment) segment();
//		Value value =  dseg.load(ofst);
////		dseg.dump("MemAddr.load: ");
////		Util.IERR("");	
//		return value;
//	}
	
	public void execute() {
		ProgramSegment seg = (ProgramSegment) segment();
		if(seg.instructions.size() == 0) {
			System.out.println("ProgramAddress.execute: " + seg.ident + " IS EMPTY -- NOTHING TO EXECUTE");
			System.exit(-1);
		}
		SVM_Instruction cur = seg.instructions.get(ofst);
//		System.out.println("ProgramAddress.execute: " + cur);
		if(Global.EXEC_TRACE > 0) {
//			RTStack.listRTStack();
			System.out.println("EXECUTE: "+Global.PSC+"  "+cur);
//			Util.IERR("");
		}
		cur.execute();
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
//		String ident = inpt.readString();
		type = Type.read(inpt);
		segID = inpt.readString();
//		ofst = inpt.readInt();
		ofst = inpt.readShort();
//		if(ident != null) seg = Segment.lookup(ident);

//		System.out.println("=============================================================================================================== " + this);
		if(ofst > 9000 || ofst < 0) Util.IERR(""+ofst);
//		Util.IERR(""+seg);
//		System.out.println("NEW IMPORT: " + this);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
		oupt.writeKind(Scode.S_C_PADDR);
		type.write(oupt);
		oupt.writeString(segID);
//		oupt.writeInt(ofst);
		oupt.writeShort(ofst);
		if(ofst > 9000 || ofst < 0) Util.IERR("");
//		System.out.println("=============================================================================================================== " + this);
	}

	public static ProgramAddress read(AttributeInputStream inpt) throws IOException {
		return new ProgramAddress(inpt);
	}

}
