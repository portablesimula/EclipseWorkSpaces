package PREV.syntaxClass.value;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;

public class RepetitionValue extends PREV_Value {
	public Vector<PREV_Value> values;
	
	public RepetitionValue() {
		values = new Vector<PREV_Value>();
		parse();
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
	public void parse() {
		LOOP:while(true) {
//			System.out.println("RepetitionValue.treatValue: "+Scode.edInstr(Scode.nextByte()));
			switch(Scode.nextByte()) {
				case Scode.S_TEXT:     Scode.inputInstr(); values.add(new TextValue()); break;
				case Scode.S_C_INT:    Scode.inputInstr(); values.add(new IntegerValue()); break;
				case Scode.S_C_REAL:   Scode.inputInstr(); values.add(new RealValue()); break;
				case Scode.S_C_LREAL:  Scode.inputInstr(); values.add(new LongRealValue()); break;
				case Scode.S_C_CHAR:   Scode.inputInstr(); values.add(new CharacterValue()); break;
				case Scode.S_C_SIZE:   Scode.inputInstr(); values.add(new SizeValue(false)); break;
				case Scode.S_TRUE:     Scode.inputInstr(); values.add(new BooleanValue(true)); break;
				case Scode.S_FALSE:    Scode.inputInstr(); values.add(new BooleanValue(false)); break;
				case Scode.S_C_AADDR:  Scode.inputInstr(); values.add(new AttributeAddress(false)); break;
				case Scode.S_C_PADDR:  Scode.inputInstr(); values.add(new ProgramAddress(false)); break;
				case Scode.S_C_RADDR:  Scode.inputInstr(); values.add(new RoutineAddress(false)); break;
				case Scode.S_NOSIZE:   Scode.inputInstr(); values.add(new SizeValue(true)); break;
				case Scode.S_ANONE:    Scode.inputInstr(); values.add(new AttributeAddress(true)); break;
				case Scode.S_NOWHERE:  Scode.inputInstr(); values.add(new ProgramAddress(true)); break;
				case Scode.S_NOBODY:   Scode.inputInstr(); values.add(new RoutineAddress(true)); break;
				case Scode.S_ONONE:    Scode.inputInstr(); values.add(new ObjectAddress(true)); break;
				case Scode.S_C_OADDR:  Scode.inputInstr(); values.add(new ObjectAddress(false)); break;
				case Scode.S_GNONE:    Scode.inputInstr(); values.add(new GeneralAddress(true)); break;
				case Scode.S_C_GADDR:  Scode.inputInstr(); values.add(new GeneralAddress(false)); break;
				case Scode.S_C_DOT:    Scode.inputInstr(); values.add(new DotAddress()); break;
				case Scode.S_C_RECORD: Scode.inputInstr(); values.add(new RecordValue()); break;
				default: break LOOP;
			}
		}
	}
	

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(PREV_Value value:values) {
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
		values = new Vector<PREV_Value>();
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			PREV_Value value = PREV_Value.read(inpt);
			values.add(value);
		}
	}

	public void write(AttributeOutputStream oupt) throws IOException {
//		oupt.writeInstr(Scode.S_EXPORT);
//		oupt.writeTag(tag);
//		type.write(oupt);
		oupt.writeShort(values.size());
		for(PREV_Value value:values) {
			value.write(oupt);
		}
	}

	public static RepetitionValue read(AttributeInputStream inpt) throws IOException {
		return new RepetitionValue(inpt);
	}

}
