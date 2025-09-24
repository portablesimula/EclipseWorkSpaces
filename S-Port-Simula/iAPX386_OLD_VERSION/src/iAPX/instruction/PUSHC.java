package iAPX.instruction;

import static iAPX.util.Global.*;

import iAPX.ValueItem;
import iAPX.ctStack.CTStack;
import iAPX.ctStack.Coonst;
import iAPX.enums.Opcode;
import iAPX.qPkt.Qfrm2;
import iAPX.qPkt.Qfunc;
import iAPX.qInstr.Q_PUSHC;
import iAPX.util.Global;
import iAPX.util.Option;
import iAPX.util.Reg;
import iAPX.util.Scode;
import iAPX.util.Type;
import iAPX.util.Util;
import svm.value.BooleanValue;
import svm.value.DotAddress;
import svm.value.IntegerValue;
import svm.value.LongRealValue;
import svm.value.ProgramAddress;
import svm.value.RealValue;
import svm.value.RecordValue;
import svm.value.StringValue;
import svm.value.Value;
import svm.value.dataAddress.GeneralAddress;
import svm.value.dataAddress.SegmentAddress;
import svm.virtualMachine.SVM_LOADC;

public abstract class PUSHC extends Instruction {
	
	/**
	 * stack_instruction : =  pushc value
	 * 
	 *	 value
	 *		: =  boolean_value | character_value
	 *		: =  integer_value | size_value
	 *		: =  real_value | longreal_value
	 *		: =  attribute_address | object_address
	 *		: =  general_address | program_address
	 *		: =  routine_address | record_value
	 * 
	 * text_value      : =  text long_string
	 * boolean_value   : =  true | false 
	 * character_value : =  c-char byte
	 * integer_value   : =  c-int integer_literal:string
	 * real_value      : =  c-real real_literal:string 
	 * longreal_value  : =  c-lreal real_literal:string
	 * size_value      : =  c-size type | nosize
	 * 
	 * attribute_address
	 * 		: =  < c-dot attribute:tag >* c-aaddr attribute:tag
	 * 		: =  anone
	 * 
	 * object_address
	 * 		: =  c-oaddr global_or_const:tag
	 * 		: =  onone
	 * 
	 * general_address
	 * 		: =  < c-dot attr:tag >* c-gaddr global_or_const:tag
	 * 		: =  gnone
	 * 
	 * program_address : =  c-paddr label:tag | nowhere
	 * routine_address : =  c-raddr body:tag | nobody
	 * 
	 * record_value
	 * 		: =  c-record structured_type
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
		    case Scode.S_C_OADDR:  type = Type.T_OADDR; value = SegmentAddress.ofScode(); break;
		    case Scode.S_GNONE:    type = Type.T_GADDR; value = null; break;
		    case Scode.S_C_GADDR:  type = Type.T_GADDR; value = GeneralAddress.ofScode(); break;
		    case Scode.S_C_DOT:	   value = DotAddress.ofScode(); type = value.type; break;
		    case Scode.S_C_RECORD: value = RecordValue.ofScode(); type = value.type; break;
		    case Scode.S_TEXT:   {
		    					   Util.IERR("");
//		    					   type = Type.T_TEXT; TextValue txtval = TextValue.ofScode();
//		    					   SegmentAddress addr = txtval.emitChars(Global.TSEG);
//		    					   value = new StringValue(addr, txtval.textValue.length());
//		    					   type = Type.T_STRING; break;
		    					 }
		    default: Util.IERR("NOT IMPLEMENTED: " + Scode.curinstr);
		}
		
//		if(type == Type.T_GADDR) {
//			Global.PSEG.emit(new SVM_LOADC(type, value), "GADDR: ");				
//		} else if(type == Type.T_TEXT) {
//			TextValue txtval = (TextValue) value;
//			DataAddress addr = txtval.emitChars(Global.TSEG);
//			type = Type.T_STRING;
//			value = new StringValue(addr, txtval.textValue.length());
//			Global.PSEG.emit(new SVM_LOADC(type, value), "");
//		} else if(type.isRecordType()) {
//			Global.PSEG.emit(new SVM_LOADC(type, value), "Record: ");
//		} else {
//			Global.PSEG.emit(new SVM_LOADC(type, value), "");
//		}

		CTStack.pushCoonst(Type.T_INT, value);
		if(Option.GENERATE_Q_CODE) {
			int reg = Reg.FreePartReg();
//			Qfunc.Qf2(Opcode.qPUSHC, 0, reg, cVAL, value);
			new Q_PUSHC(reg, value);
		} else {
			Global.PSEG.emit(new SVM_LOADC(type, value), "");
		}
	}

}
