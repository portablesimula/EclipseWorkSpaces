package svm.virtualMachine;

import java.io.IOException;

import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Global;
import iAPX.util.Scode;
import iAPX.util.Tag;
import iAPX.util.Type;
import iAPX.util.Util;
import svm.value.IntegerValue;
import svm.value.RecordValue;
import svm.value.Value;
import svm.value.dataAddress.GeneralAddress;
import svm.value.dataAddress.DataAddress;

//The value is loaded onto the operand stack.
public class SVM_LOADC extends SVM_Instruction {
	private Type type;
	private Value value;

	private final boolean DEBUG = false;

	public SVM_LOADC(Type type,Value value) {
		this.opcode = SVM_Instruction.iPUSHC;
		this.type  = type;
		this.value = value;
		if(value != null ) {
//			if(value.type.tag != typeTag) {
			if(value.type != type) {
				IO.println("NEW SVM_LOADC: value="+value+", type="+value.type);
				Util.IERR("INCONSISTENT: type="+type + ", value'type="+value.type);
			}
		}
	}
	
	@Override
	public void execute() {
		switch(type.tag.val) {
			case Tag.TAG_BOOL, Tag.TAG_CHAR, Tag.TAG_INT, Tag.TAG_SINT, Tag.TAG_REAL, Tag.TAG_LREAL,
			     Tag.TAG_SIZE, Tag.TAG_AADDR, Tag.TAG_PADDR, Tag.TAG_RADDR:
					RTStack.push(value, "SVM_LOADC"); break;
			case Tag.TAG_OADDR:
				RTStack.push(value, "SVM_LOADC"); break;
//			case Tag.TAG_TEXT:
//				Util.IERR("IMPOSSIBLE");
//				break;
//			case Tag.TAG_STRING:
//				StringValue sval = (StringValue) value;
//				IntegerValue lng = IntegerValue.of(Type.T_INT, sval.lng);
//				RTStack.push(sval.addr, "String'GADDR'OADR: ");
//				RTStack.push(null,      "String'GADDR'OFST: ");
//				RTStack.push(lng,       "String'lng: ");
//				if(DEBUG) RTStack.dumpRTStack("SVM_LOADC.execute: ");
//				break;
			case Tag.TAG_GADDR:
				if(value == null) {
					RTStack.push(null, "GADDR'OADR: ");
					RTStack.push(null, "GADDR'OFST: ");
				} else {
					GeneralAddress gaddr = (GeneralAddress) value;
//					
//					if(Global.TESTING_STACK_ADDRESS) {
						DataAddress oaddr = gaddr.base;
						if(oaddr != null && oaddr.kind == DataAddress.REL_ADDR) {
							gaddr.base = oaddr.toStackAddress();
						}
//					}
					
					RTStack.push(gaddr.base, "GADDR'OADR: ");
					RTStack.push(IntegerValue.of(Type.T_INT, gaddr.ofst), "GADDR'OFST: ");
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
		return "LOADC    " + type + " " + value;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private SVM_LOADC(AttributeInputStream inpt) throws IOException {
		this.opcode = SVM_Instruction.iPUSHC;
		this.typeTag = inpt.readTag();
		boolean present = inpt.readBoolean();
		if(present)	this.value = Value.read(inpt);
		if(Global.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeTag(typeTag);
		if(value != null) {
			oupt.writeBoolean(true);
//			IO.println("SVM_LOADC.write: "+value);
			value.write(oupt);
		} else oupt.writeBoolean(false);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		return new SVM_LOADC(inpt);
	}

}
