package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ProgramAddress;
import bec.value.RecordValue;
import bec.value.StringValue;
import bec.value.Value;

//The value is loaded onto the operand stack.
public class SVM_LOADC extends SVM_Instruction {
	private int typeTag;
	private Value value;

	private final boolean DEBUG = false;

	public SVM_LOADC(Type type,Value value) {
		this.opcode = SVM_Instruction.iPUSHC;
		this.typeTag  = type.tag;
		this.value = value;
		if(value != null ) {
			if(value.type.tag != typeTag) {
				System.out.println("NEW SVM_LOADC: value="+value+", type="+value.type);
				Util.IERR("INCONSISTENT: typeTag="+Scode.edTag(typeTag) + ", value'tag="+Scode.edTag(value.type.tag));
			}
		}
	}
	
	@Override
	public void execute() {
//		System.out.println("SVM_LOADC.execute: "+this);
//		if(value == null) {
//	 		RTStack.push(null, "SVM_LOADC"); 
//		} else if(value.type == null) {
//			System.out.println("SVM_LOADC.execute: value=" + value.getClass().getSimpleName() + " " + value);
//			Util.IERR("");
//		} else {
//			switch(value.type.tag) {
		
//		if(value != null ) {
//			System.out.println("SVM_LOADC.execute: value="+value.getClass().getSimpleName()+"  "+value);
//			System.out.println("SVM_LOADC.execute: typeTag="+Scode.edTag(typeTag));
//			if(value.type.tag != typeTag) Util.IERR("INCONSISTENT: typeTag="+Scode.edTag(typeTag) + ", value'tag="+Scode.edTag(value.type.tag));
//		}
		switch(typeTag) {
			case Scode.TAG_BOOL, Scode.TAG_CHAR, Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL,
			     Scode.TAG_SIZE, Scode.TAG_AADDR, Scode.TAG_OADDR, Scode.TAG_PADDR, Scode.TAG_RADDR:
					RTStack.push(value, "SVM_LOADC"); break;
			case Scode.TAG_TEXT:
				Util.IERR("IMPOSSIBLE");
				break;
			case Scode.TAG_STRING:
				StringValue sval = (StringValue) value;
				IntegerValue lng = IntegerValue.of(Type.T_INT, sval.lng);
				RTStack.push(sval.addr, "String'GADDR'OADR: ");
				RTStack.push(null,      "String'GADDR'OFST: ");
				RTStack.push(lng,       "String'lng: ");
				if(DEBUG) RTStack.dumpRTStack("SVM_LOADC.execute: ");
//				Util.IERR("");
				break;
			case Scode.TAG_GADDR:
				if(value == null) {
					RTStack.push(null, "GADDR'OADR: ");
					RTStack.push(null, "GADDR'OFST: ");
				} else {
					GeneralAddress gval = (GeneralAddress) value;
					RTStack.push(gval.base, "GADDR'OADR: ");
					RTStack.push(IntegerValue.of(Type.T_INT, gval.ofst), "GADDR'OFST: ");
				} break;
			default:
				RecordValue rval = (RecordValue)value;
//				for(Value val:rval.attrValues)
				for(int i=0;i<rval.attrValues.size();i++) {
					Value val = rval.attrValues.get(i);
					RTStack.push(val, "Record: " + rval.tag);				
				}
				
				if(DEBUG) RTStack.dumpRTStack("SVM_LOADC.execute: ");
				
//				Util.IERR("");
				break;
		}
//		}
		Global.PSC.addOfst(1);
	}
	
	@Override
	public String toString() {
		return "LOADC    " + Scode.edTag(typeTag) + " " + value;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_LOADC(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSHC;
		this.typeTag = inpt.readTag();
		boolean present = inpt.readBoolean();
		if(present)	this.value = Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeTag(typeTag);
		if(value != null) {
			oupt.writeBoolean(true);
//			System.out.println("SVM_LOADC.write: "+value);
			value.write(oupt);
		} else oupt.writeBoolean(false);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_LOADC(inpt);
	}

}
