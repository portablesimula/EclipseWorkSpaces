package bec.instruction;

import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ConstItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.BooleanValue;
import bec.value.DotAddress;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.LongRealValue;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.RealValue;
import bec.value.RecordValue;
import bec.value.TextValue;
import bec.value.Value;
import bec.virtualMachine.SVM_PUSHC;

public abstract class PUSHC extends Instruction {
	
	/**
	 * stack_instruction ::= pushc value
	 * 
	 *	 value
	 *		::= boolean_value | character_value
	 *		::= integer_value | size_value
	 *		::= real_value | longreal_value
	 *		::= attribute_address | object_address
	 *		::= general_address | program_address
	 *		::= routine_address | record_value
	 * 
	 * text_value      ::= text long_string
	 * boolean_value   ::= true | false 
	 * character_value ::= c-char byte
	 * integer_value   ::= c-int integer_literal:string
	 * real_value      ::= c-real real_literal:string 
	 * longreal_value  ::= c-lreal real_literal:string
	 * size_value      ::= c-size type | nosize
	 * 
	 * attribute_address
	 * 		::= < c-dot attribute:tag >* c-aaddr attribute:tag
	 * 		::= anone
	 * 
	 * object_address
	 * 		::= c-oaddr global_or_const:tag
	 * 		::= onone
	 * 
	 * general_address
	 * 		::= < c-dot attr:tag >* c-gaddr global_or_const:tag
	 * 		::= gnone
	 * 
	 * program_address ::= c-paddr label:tag | nowhere
	 * routine_address ::= c-raddr body:tag | nobody
	 * 
	 * record_value
	 * 		::= c-record structured_type
	 * 			<attribute_value>+ endrecord
	 * 
	 * End-Condition: Scode'nextByte = First byte after the value
	 * 
	 * pushc constant:value
	 * push( VAL, constant.TYPE, "value" );
	 * 
	 * A descriptor of the given value is pushed onto the stack.
	 */
	public static void ofScode() {
		Type type = null;
		Value value = null;
		Scode.inputInstr();
		switch(Scode.curinstr) {
		    case Scode.S_C_INT:    type = Type.T_INT; value = IntegerValue.ofScode_INT(); break;
		    case Scode.S_C_REAL:   type = Type.T_REAL; value = RealValue.ofScode(); break;
		    case Scode.S_C_LREAL:  type = Type.T_LREAL; value = LongRealValue.ofScode(); break;
		    case Scode.S_C_CHAR:   type = Type.T_CHAR; value = IntegerValue.ofScode_CHAR(); break;
		    case Scode.S_NOSIZE:   type = Type.T_SIZE; value = null; break;
		    case Scode.S_C_SIZE:   type = Type.T_SIZE; value = IntegerValue.ofScode_SIZE(); break;
		    case Scode.S_TRUE:     type = Type.T_BOOL; value = BooleanValue.of(true); break;
		    case Scode.S_FALSE:    type = Type.T_BOOL; value = BooleanValue.of(false); break;
		    case Scode.S_ANONE:    type = Type.T_AADDR; value = null; break;
		    case Scode.S_C_AADDR:  type = Type.T_AADDR; value = IntegerValue.ofScode_AADDR(); break;
		    case Scode.S_NOWHERE:  type = Type.T_PADDR; value = null; break;
		    case Scode.S_C_PADDR:  type = Type.T_PADDR; value = ProgramAddress.ofScode(Type.T_PADDR); break;
			case Scode.S_NOBODY:   type = Type.T_RADDR; value = null; break;
		    case Scode.S_C_RADDR:  type = Type.T_RADDR; value = ProgramAddress.ofScode(Type.T_RADDR); break;
		    case Scode.S_ONONE:    type = Type.T_OADDR; value = null; break;
		    case Scode.S_C_OADDR:  type = Type.T_OADDR; value = ObjectAddress.ofScode(); break;
		    case Scode.S_GNONE:    type = Type.T_GADDR; value = null; break;
		    case Scode.S_C_GADDR:  type = Type.T_GADDR; value = GeneralAddress.ofScode(); break;
		    case Scode.S_C_DOT:	   value = DotAddress.ofScode(); type = value.type; break;
		    case Scode.S_C_RECORD: value = RecordValue.ofScode(); type = value.type; break;
		    case Scode.S_TEXT:	   type = Type.T_TEXT; value = TextValue.ofScode(); break;
		    default: Util.IERR("NOT IMPLEMENTED: " + Scode.edInstr(Scode.curinstr));
		}
		
		if(type == Type.T_GADDR) {
			if(value == null) {
//				Global.PSEG.emit(new SVM_PUSHC(Type.T_INT, null), "GADDR'OFST: ");
				Global.PSEG.emit(new SVM_PUSHC(null), "GADDR'OADDR: ");
				Global.PSEG.emit(new SVM_PUSHC(null), "GADDR'OFST: ");
			} else {
				GeneralAddress gval = (GeneralAddress) value;
				Global.PSEG.emit(new SVM_PUSHC(gval.base), "GADDR'OADDR: ");
				Global.PSEG.emit(new SVM_PUSHC(IntegerValue.of(Type.T_INT, gval.ofst)), "GADDR'OFST: ");
			}
		} else if(type == Type.T_TEXT) {
//			Global.CSEG.dump("PUSHC.ofScode: ");
			TextValue txtval = (TextValue) value;
			ObjectAddress addr = txtval.emitChars(Global.TSEG);
			IntegerValue lng = IntegerValue.of(Type.T_INT, txtval.textValue.length());
			Global.PSEG.emit(new SVM_PUSHC(addr), "TEXT'CHRADR'oaddr: ");
			Global.PSEG.emit(new SVM_PUSHC(null), "TEXT'CHRADR'ofst:  ");
			Global.PSEG.emit(new SVM_PUSHC(lng), "TEXT'lng:   ");

			type = Type.T_STRING;				
//			Global.PSEG.dump("PUSHC: "+value+": ");
//			Util.IERR("");
		} else if(type.isRecordType()) {
			RecordValue rval = (RecordValue)value;
//			for(Value val:rval.attrValues)
			for(int i=0;i<rval.attrValues.size();i++) {
//			for(int i=rval.attrValues.size()-1;i>=0;i--) {
				Value val = rval.attrValues.get(i);
				Global.PSEG.emit(new SVM_PUSHC(val), "Record: " + rval.tag);				
			}
//			Util.IERR("");
		} else {
			Global.PSEG.emit(new SVM_PUSHC(value), "");
		}
//		value.print("NEW ConstItem: ");
		ConstItem cns = new ConstItem(type, value);
		CTStack.push(cns);
//		System.out.println("PUSHC.ofScode: "+cns);
//		CTStack.dumpStack("PUSHC: "+value+": ");
//		Util.IERR("DETTE MÅ RETTES");
//		Global.PSEG.dump("PUSHC: "+value+": ");
	}

}
