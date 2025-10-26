/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.RecordValue;
import bec.value.StringValue;
import bec.value.Value;

/// SVM-INSTRUCTION: LOAD typeTag value
/// 
///	  Runtime Stack
///		... →
///		..., value1, value2, ... , value'size
///
/// The the values are loaded and pushed onto the Runtime stack.
/// The number of values are type dependent.
///
///
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/virtualMachine/SVM_LOADC.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class SVM_LOADC extends SVM_Instruction {
	private final int typeTag;
	private final Value value;

	private final boolean DEBUG = false;

	public SVM_LOADC(Type type,Value value) {
		this.opcode = SVM_Instruction.iPUSHC;
		this.typeTag  = type.tag;
		this.value = value;
		if(value != null ) {
			if(value.type.tag != typeTag) {
				IO.println("NEW SVM_LOADC: value="+value+", type="+value.type);
				Util.IERR("INCONSISTENT: typeTag="+Scode.edTag(typeTag) + ", value'tag="+Scode.edTag(value.type.tag));
			}
		}
	}
	
	@Override
	public void execute() {
		switch(typeTag) {
			case Scode.TAG_BOOL, Scode.TAG_CHAR, Scode.TAG_INT, Scode.TAG_SINT, Scode.TAG_REAL, Scode.TAG_LREAL,
			     Scode.TAG_SIZE, Scode.TAG_AADDR, Scode.TAG_PADDR, Scode.TAG_RADDR:
					RTStack.push(value); break;
			case Scode.TAG_OADDR:
				
				ObjectAddress oaddr = (ObjectAddress) value;
				if(oaddr != null && oaddr.segID == null) {
					IO.println("SVM_LOADC.execute: OADDR: "+oaddr);
					RTStack.dumpRTStack("SVM_LOADC.execute: NOTE: ");
//					RTStack.callStack_TOP().dump("SVM_LOADC.execute: NOTE: ");
					Util.IERR("");
				}
				
				RTStack.push(value); break;
			case Scode.TAG_TEXT:
				Util.IERR("IMPOSSIBLE");
				break;
			case Scode.TAG_STRING:
				StringValue sval = (StringValue) value;
				IntegerValue lng = IntegerValue.of(Type.T_INT, sval.lng);
				RTStack.push(sval.addr);
				RTStack.push(null);
				RTStack.push(lng);
				if(DEBUG) RTStack.dumpRTStack("SVM_LOADC.execute: ");
				break;
			case Scode.TAG_GADDR:
				if(value == null) {
					RTStack.push(null);
					RTStack.push(null);
				} else {
					GeneralAddress gaddr = (GeneralAddress) value;
					
					oaddr = gaddr.base;
					if(oaddr != null && oaddr.kind == ObjectAddress.REL_ADDR) {
						gaddr.base = oaddr.toStackAddress();
					}
					
					RTStack.push(gaddr.base);
					RTStack.push(IntegerValue.of(Type.T_INT, gaddr.ofst));
				} break;
			default:
				RecordValue rval = (RecordValue)value;
				for(int i=0;i<rval.attrValues.size();i++) {
					Value val = rval.attrValues.get(i);
					RTStack.push(val);				
				}
				
				if(DEBUG) RTStack.dumpRTStack("SVM_LOADC.execute: ");
				break;
		}
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
		this.typeTag = inpt.readShort();
		boolean present = inpt.readBoolean();
		this.value = (! present)? null : Value.read(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + this);
	}

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(typeTag);
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
