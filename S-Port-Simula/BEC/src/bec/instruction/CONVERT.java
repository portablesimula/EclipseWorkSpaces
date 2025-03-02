package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ConstItem;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.LongRealValue;
import bec.value.ObjectAddress;
import bec.value.RealValue;
import bec.value.Value;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_CONVERT;

public abstract class CONVERT extends Instruction {
	
	/**
	 * convert_instruction ::= convert simple_type
	 * 
	 * TOS must be of simple type, otherwise: error.
	 * 
	 * The TYPE of TOS is changed to the type specified in the instruction, this may imply code generation.
	 */
	public static void ofScode() {
		CTStack.dumpStack("BEGIN CONVERT.ofScode: ");
		StackItem tos = CTStack.TOS;
		Type toType = Type.ofScode();
		System.out.println("CONVERT: "+tos.getClass().getSimpleName());

		GQconvert(toType);
		
		CTStack.dumpStack("END CONVERT.ofScode: ");
//		Global.PSEG.dump("END CONVERT.ofScode: ");
//		Util.IERR("");
	}
	
	public static void GQconvert(Type toType) {
//		Util.GQfetch("GQconvert " + toType);
		if(CTStack.TOS.type != toType) doConvert(toType);
	}

	private static void doConvert(Type totype) {
		Util.GQfetch("CONVERT: ");
		StackItem TOS = CTStack.TOS;
//		Type fromtype = TOS.type;
		if( TOS instanceof ConstItem) convConst(totype);
		else {
//			boolean OK = false;
//			switch(fromtype.tag) {
//				case Scode.TAG_LREAL, Scode.TAG_REAL, Scode.TAG_INT, Scode.TAG_CHAR:  OK = totype.isArithmetic(); break;
//				case Scode.TAG_OADDR: OK = totype == Type.T_GADDR; break;
//				case Scode.TAG_GADDR: OK = totype == Type.T_AADDR || totype == Type.T_OADDR; break;
//			}
//			if(! OK) Util.IERR("Type conversion is undefined: " + fromtype + " ==> " + totype);
			Global.PSEG.emit(new SVM_CONVERT(totype), "");
			CTStack.pop(); CTStack.pushTemp(totype, RTRegister.qEAX, 1, "CONVERT: ");
//			TOS.type = totype;
		}
	}

	
//	%title ***    C o n v e r t   C o n s t a n t   V a l u e    ***
	private static void convConst(Type totype) {
		ConstItem TOS = (ConstItem) CTStack.TOS;
		Type fromtype = TOS.type;
		Value toValue = null;
		switch(fromtype.tag) {
		case Scode.TAG_CHAR: {
			IntegerValue intval = (IntegerValue)TOS.value;
			int val = intval.value;
			switch(totype.tag) {
				case Scode.TAG_INT:   toValue = new IntegerValue(Type.T_INT, val); break;
				case Scode.TAG_REAL:  toValue = new RealValue(val); break;
				case Scode.TAG_LREAL: toValue = new LongRealValue(val); break;
			}
			break;
		}
		case Scode.TAG_INT: {
			IntegerValue intval = (IntegerValue)TOS.value;
			int val = intval.value;
			switch(totype.tag) {
				case Scode.TAG_CHAR:  toValue = new IntegerValue(Type.T_CHAR, val); break;
				case Scode.TAG_REAL:  toValue = new RealValue(val); break;
				case Scode.TAG_LREAL: toValue = new LongRealValue(val); break;
			}
			break;
		}
		case Scode.TAG_REAL: {
			RealValue intval = (RealValue)TOS.value;
			float val = intval.value;
			switch(totype.tag) {
				case Scode.TAG_CHAR:  toValue = new IntegerValue(Type.T_CHAR, (int)(val+0.5)); break;
				case Scode.TAG_INT:   toValue = new IntegerValue(Type.T_INT, (int)(val+0.5)); break;
				case Scode.TAG_LREAL: toValue = new LongRealValue(val); break;
			}
			break;
		}
		case Scode.TAG_LREAL: {
			RealValue intval = (RealValue)TOS.value;
			float val = intval.value;
			switch(totype.tag) {
				case Scode.TAG_CHAR: toValue = new IntegerValue(Type.T_CHAR, (int)(val+0.5)); break;
				case Scode.TAG_INT:  toValue = new IntegerValue(Type.T_INT, (int)(val+0.5)); break;
				case Scode.TAG_REAL: toValue = new RealValue(val); break;
			}
			break;
		}
		case Scode.TAG_OADDR: {
//	           if totype = T_GADDR
//	           then itm.ofst:=0; Qf2(qPUSHC,0,FreePartReg,cVAL,0);
//	           else ILL:=true endif;
			Util.IERR("NOT IMPL");
		} break;
		case Scode.TAG_GADDR: {
//	           if totype = T_AADDR
//	           then qPOPKill(AllignFac); qPOPKill(AllignFac);
//	%-E             qPOPKill(2);
//	                itm.int:=itm.Ofst; Qf2(qPUSHC,0,FreePartReg,cVAL,itm.wrd);
//	           elsif totype = T_OADDR then qPOPKill(AllignFac);
//	           else ILL:=true endif;
			Util.IERR("NOT IMPL");
		} break;
		default: toValue = null;
		}
		if(toValue == null) Util.ERROR("Constant conversion is undefined; " + fromtype + " ==> " + totype);
		TOS.value = toValue; TOS.type = totype;
	}

}
