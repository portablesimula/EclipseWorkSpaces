package bec.value;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.segment.DataSegment;
import bec.util.BecGlobal;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class RepetitionValue extends Value {
	public Vector<Value> values;
	
	private RepetitionValue(Type type, Vector<Value> values) {
		this.type = type;
		this.values = values;
	}

	public static RepetitionValue ofValue(Value value) {
		Vector<Value> values = new Vector<Value>();
		values.add(value);
		return new RepetitionValue(value.type, values);
	}
	
	/**
	 * repetition_value
	 * 		::= <boolean_value>+
	 * 		::= <character_value>+ | text_value
	 * 		::= <integer_value>+ | <size_value>+
	 * 		::= <real_value>+ | <longreal_value>+
	 * 		::= <attribute_address>+ | <object_address>+
	 * 		::= <general_address>+ | <program_address>+
	 * 		::= <routine_address>+ | <record_value>+
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
	 */
	
	public static RepetitionValue ofScode() {
		Type type = null;
		Value value = null;
		Vector<Value> values = new Vector<Value>();
		LOOP:while(true) {
//			System.out.println("RepetitionValue.ofScode: "+Scode.edInstr(Scode.nextByte()));
			switch(Scode.nextByte()) {
				case Scode.S_TEXT:     Scode.inputInstr(); type = Type.T_TEXT;  values.add(TextValue.ofScode()); break; // LOOP;
				case Scode.S_C_INT:    Scode.inputInstr(); type = Type.T_INT;   values.add(IntegerValue.ofScode_INT()); break;
				case Scode.S_C_CHAR:   Scode.inputInstr(); type = Type.T_CHAR;  values.add(IntegerValue.ofScode_CHAR()); break;
				case Scode.S_C_SIZE:   Scode.inputInstr(); type = Type.T_SIZE;  values.add(IntegerValue.ofScode_SIZE()); break;
				case Scode.S_C_REAL:   Scode.inputInstr(); type = Type.T_REAL;  values.add(RealValue.ofScode()); break;
				case Scode.S_C_LREAL:  Scode.inputInstr(); type = Type.T_LREAL; values.add(LongRealValue.ofScode()); break;
				case Scode.S_TRUE:     Scode.inputInstr(); type = Type.T_BOOL;  values.add(BooleanValue.of(true)); break;
				case Scode.S_FALSE:    Scode.inputInstr(); type = Type.T_BOOL;  values.add(BooleanValue.of(false)); break;
				case Scode.S_NOSIZE:   Scode.inputInstr(); type = Type.T_SIZE;  values.add(null); break;
				case Scode.S_ANONE:    Scode.inputInstr(); type = Type.T_AADDR; values.add(null); break;
				case Scode.S_NOWHERE:  Scode.inputInstr(); type = Type.T_PADDR; values.add(null); break;
				case Scode.S_NOBODY:   Scode.inputInstr(); type = Type.T_RADDR; values.add(null); break;
				case Scode.S_ONONE:    Scode.inputInstr(); type = Type.T_OADDR; values.add(null); break;
				case Scode.S_GNONE:    Scode.inputInstr(); type = Type.T_GADDR; values.add(null); values.add(null); break;
				case Scode.S_C_AADDR:  Scode.inputInstr(); type = Type.T_AADDR; values.add(IntegerValue.ofScode_AADDR()); break;
				case Scode.S_C_PADDR:  Scode.inputInstr(); type = Type.T_PADDR; values.add(ProgramAddress.ofScode(Type.T_PADDR)); break;
				case Scode.S_C_RADDR:  Scode.inputInstr(); type = Type.T_RADDR; values.add(ProgramAddress.ofScode(Type.T_RADDR)); break;
				case Scode.S_C_OADDR:  Scode.inputInstr(); type = Type.T_OADDR; values.add(ObjectAddress.ofScode()); break;
				case Scode.S_C_RECORD: Scode.inputInstr(); value = RecordValue.ofScode(); values.add(value); type = value.type; break;
				case Scode.S_C_DOT:
//					Scode.inputInstr(); values.add(DotAddress.ofScode()); break;
					Scode.inputInstr();
					Value dotAddr = DotAddress.ofScode();
					if(dotAddr instanceof GeneralAddress gaddr) {
						type = Type.T_GADDR;
						values.add(gaddr.base);
						values.add(IntegerValue.of(Type.T_INT, gaddr.ofst));						
					} else if(dotAddr instanceof ObjectAddress oaddr) {
						type = Type.T_OADDR;
						values.add(oaddr);
					} else if(dotAddr instanceof IntegerValue aaddr) {
						type = Type.T_AADDR;
						values.add(aaddr);
					} else Util.IERR(""+dotAddr.getClass().getSimpleName());
					break;
				case Scode.S_C_GADDR:
					type = Type.T_GADDR;
					Scode.inputInstr();
					GeneralAddress gaddr = GeneralAddress.ofScode();
					values.add(gaddr.base);
					values.add(IntegerValue.of(Type.T_INT, gaddr.ofst));
					break;
				default:
//					System.out.println("RepetitionValue.ofScode: TERMINATED BY "+Scode.edInstr(Scode.nextByte()));
					break LOOP;
			}
		}
		return new RepetitionValue(type, values);
	}
		
	@Override
	public void emit(DataSegment dseg, String comment) {
		for(Value value:values) {
			if(value == null)
				 dseg.emit(null, comment);
			else value.emit(dseg, comment);
		}
	}
	
	@Override
	public void print(final String indent) {
//		System.out.println(indent + toString());
		for(Value value:values) value.print(indent);
//		Util.IERR("");
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Value value:values) {
			sb.append(value).append(" ");
		}
		return sb.toString();
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	private RepetitionValue(AttributeInputStream inpt) throws IOException {
//		tag = inpt.readTag();
//		type = ResolvedType.read(inpt);
//		System.out.println("NEW IMPORT: " + this);
		values = new Vector<Value>();
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			Value value = Value.read(inpt);
			values.add(value);
		}
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE) System.out.println("Value.write: " + this);
//		oupt.writeKind(Scode.S_C_???);
		Util.IERR("TEST DETTE");
		oupt.writeShort(values.size());
		for(Value value:values) {
			value.write(oupt);
		}
	}

	public static RepetitionValue read(AttributeInputStream inpt) throws IOException {
		return new RepetitionValue(inpt);
	}

}
