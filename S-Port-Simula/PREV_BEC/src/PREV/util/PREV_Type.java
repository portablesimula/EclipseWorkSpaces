package PREV.util;

import java.io.IOException;

import PREV.syntaxClass.instruction.RECORD;
import PREV.syntaxClass.value.AttributeAddress;
import PREV.syntaxClass.value.BooleanValue;
import PREV.syntaxClass.value.CharacterValue;
import PREV.syntaxClass.value.GeneralAddress;
import PREV.syntaxClass.value.IntegerValue;
import PREV.syntaxClass.value.LongRealValue;
import PREV.syntaxClass.value.ObjectAddress;
import PREV.syntaxClass.value.ProgramAddress;
import PREV.syntaxClass.value.RealValue;
import PREV.syntaxClass.value.RoutineAddress;
import PREV.syntaxClass.value.SizeValue;
import PREV.syntaxClass.value.PREV_Value;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.RecordDescr;
import bec.segment.DataSegment;
import bec.util.Global;
import bec.util.Range;
import bec.util.Scode;
import bec.util.Util;

public class PREV_Type {
	public int tag;
	Range range;
	
	/**
	 *	 type ::= structured_type | simple_type | range_type
	 * 
	 *	 	simple_type ::= BOOL | CHAR | INT | REAL | LREAL | SIZE | OADDR | AADDR | GADDR | PADDR | RADDR
	 * 
	 *	 	structured_type ::= record_tag:tag
	 *
	 *		 range_type
	 *			::= INT range lower:number upper:number  -- NOTE: DETTE ER NYTT
	 *			::= SINT                                 -- NOTE: DETTE ER NYTT
	 *
	 */
	public PREV_Type() {
		tag = Scode.inTag();
		if(tag == Scode.TAG_INT) {
			if(Scode.accept(Scode.S_RANGE)) range = new Range();
		}
	}
	
	public boolean isSimple() {
		return tag <= Scode.TAG_SIZE;
	}
	
	public PREV_Value defaultValue() {
		switch(tag) {
			case Scode.TAG_BOOL:  return new BooleanValue(true);
			case Scode.TAG_CHAR:  return new CharacterValue(0);
			case Scode.TAG_INT:   return new IntegerValue(0);
			case Scode.TAG_SINT:  return new IntegerValue(0);
			case Scode.TAG_REAL:  return new RealValue(0);
			case Scode.TAG_LREAL: return new LongRealValue(0);
			case Scode.TAG_SIZE:  return new SizeValue(true);
			case Scode.TAG_OADDR: return new ObjectAddress(true);
			case Scode.TAG_AADDR: return new AttributeAddress(true);
			case Scode.TAG_GADDR: return new GeneralAddress(true);
			case Scode.TAG_PADDR: return new ProgramAddress(true);
			case Scode.TAG_RADDR: return new RoutineAddress(true);
			default: Util.IERR("MISSING: " + Scode.edTag(tag)); return null;
		}
	}
	
	public void emitDefaultValue(DataSegment dseg, String comment) {
//		if(this.isSimple()) {
//			dseg.emit(defaultValue(), comment);
//		} else {
//			Object obj = Global.Display.get(tag);
////			System.out.println("Type.emitDefaultValue: tag="+tag);
//			if(obj instanceof RECORD rec) {
//				rec.emitDefaultValues(dseg, 1, comment);
//			} else Util.IERR(""+obj);
//			
//		}
		Util.IERR("DON'T USE THISD METHOD");
	}
	
	public int size() {
		if(this.isSimple()) return(1);
		Object obj = Global.DISPL.get(tag);
//		if(obj instanceof RECORD rec) {
//			return rec.size();
		if(obj instanceof RecordDescr rec) {
			return rec.size;
//		} else Util.IERR("IMPOSSIBLE: " + Scode.edTag(tag) + "  " + obj.getClass().getSimpleName());
		} else Util.IERR("IMPOSSIBLE: " + Scode.edTag(tag));
		return 0;
	}
	
	public String toString() {
		return Scode.edTag(tag);
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	protected PREV_Type(AttributeInputStream inpt) throws IOException {
		tag = inpt.readTag();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeTag(tag);
	}

	public static PREV_Type read(AttributeInputStream inpt) throws IOException {
		return new PREV_Type(inpt);
	}


}
