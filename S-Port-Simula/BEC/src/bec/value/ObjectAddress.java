package bec.value;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.ConstDescr;
import bec.descriptor.Descriptor;
import bec.descriptor.Variable;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.RTStack.RTStackItem;

public class ObjectAddress extends Value {
//	public DataSegment seg;
	String segID;
	public int ofst;
	
	public ObjectAddress(String segID,	int ofst) {
		this.segID = segID;
		this.ofst = ofst;
		if(ofst > 9000 || ofst < 0) Util.IERR("");
	}
	
	private ObjectAddress(DataSegment seg,	int ofst) {
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
	public static ObjectAddress ofScode() {
		Tag tag = Tag.ofScode();
		Descriptor descr = tag.getMeaning();
		if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
//		System.out.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
//		Util.IERR("NOT IMPL");
//		return null;
		if(descr instanceof Variable var) return var.address;
		if(descr instanceof ConstDescr cns) return cns.address;
		Util.IERR("MISSING: " + descr);
		return null;
	}
	
	public static ObjectAddress ofSegAddr(DataSegment seg, int ofst) {
		return new ObjectAddress(seg, ofst);
	}
	
	public static ObjectAddress ofRelAddr(DataSegment seg) {
		return new ObjectAddress(seg, 0);
	}
	
	
	public ObjectAddress ofset(int ofst) {
		return new ObjectAddress(segID, this.ofst + ofst);
	}
	
	public DataSegment segment() {
		if(segID == null) return null;
		return (DataSegment) Segment.lookup(segID);
	}
	
	public void store(Value value, String comment) {
		if(segID == null) {
			// store rel-addr  curFrame + ofst
//			System.out.println("ObjectAddress.store: curFrame="+RTStack.curFrame.rtStackIndex);
			RTStack.store(RTStack.curFrame.rtStackIndex + ofst, value, comment);
//			Util.IERR("");
			
		} else {
		DataSegment dseg = segment();
		dseg.store(ofst, value);
//		dseg.dump("MemAddr.store: ");
//		Util.IERR("");
		}
	}
	
	public Value load() {
		if(segID == null) {
			// load from rel-addr  curFrame + ofst
//			System.out.println("ObjectAddress.load: curFrame="+RTStack.curFrame.rtStackIndex);
			RTStackItem item = RTStack.load(RTStack.curFrame.rtStackIndex + ofst);
//			System.out.println("ObjectAddress.load: value="+value);
//			Util.IERR("");
			return item.value();
		} else {
		DataSegment dseg = segment();
		Value value =  dseg.load(ofst);
//		dseg.dump("MemAddr.load: ");
//		Util.IERR("");	
		return value;
		}
	}
	
//	public void execute() {
//		ProgramSegment seg = (ProgramSegment) segment();
//		SVM_Instruction cur = seg.instructions.get(ofst);
////		System.out.println("MemAddr.execute: " + cur);
//		cur.execute();
////		Util.IERR("");
//	}
	
	public String toString() {
		String s = (segID == null) ? "RELADR" : segID;
		return s + '[' + ofst + ']';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private ObjectAddress(AttributeInputStream inpt) throws IOException {
//		String ident = inpt.readString();
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
		oupt.writeKind(Scode.S_C_OADDR);
		oupt.writeString(segID);
//		oupt.writeInt(ofst);
		oupt.writeShort(ofst);
		if(ofst > 9000 || ofst < 0) Util.IERR("");
//		System.out.println("=============================================================================================================== " + this);
	}

	public static ObjectAddress read(AttributeInputStream inpt) throws IOException {
		return new ObjectAddress(inpt);
	}

}
