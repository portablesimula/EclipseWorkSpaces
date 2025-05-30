package PREV.syntaxClass.value;

import java.io.IOException;

import PREV.syntaxClass.instruction.PREV_Instruction;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Scode;
import bec.util.Util;

public class PREV_Value extends PREV_Instruction {
	public int type;

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

	public static PREV_Value read(AttributeInputStream inpt) throws IOException {
		int kind = inpt.readKind();
//		System.out.println("Value.read: kind="+Scode.edInstr(kind));
		switch(kind) {
			case Scode.S_NULL:		return null;
			case Scode.S_TRUE:		return BooleanValue.of(true);
			case Scode.S_FALSE:		return BooleanValue.of(false);
			case Scode.S_C_INT:		return IntegerValue.read(inpt);
			case Scode.S_C_REAL:	return RealValue.read(inpt);
			case Scode.S_C_LREAL:	return LongRealValue.read(inpt);
			case Scode.S_C_CHAR:	return CharacterValue.read(inpt);
			case Scode.S_C_SIZE:	return SizeValue.read(inpt);
			case Scode.S_TEXT:		return TextValue.read(inpt);
			case Scode.S_C_RECORD:	return RecordValue.read(inpt);
			case Scode.S_C_OADDR:	return ObjectAddress.read(inpt);
			case Scode.S_C_AADDR:	return AttributeAddress.read(inpt);
			case Scode.S_C_GADDR:	return GeneralAddress.read(inpt);
			case Scode.S_C_PADDR:	return ProgramAddress.read(inpt);
			case Scode.S_C_RADDR:	return RoutineAddress.read(inpt);
			case Scode.S_C_DOT:		return DotAddress.read(inpt);
			default: Util.IERR("MISSING: " + Scode.edInstr(kind));
		}
		Util.IERR("Method 'readObject' needs a redefiniton");
		return(null);
	}


}
