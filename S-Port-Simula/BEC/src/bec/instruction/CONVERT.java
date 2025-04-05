package bec.instruction;

import bec.compileTimeStack.AddressItem;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.ConstItem;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;
import bec.value.GeneralAddress;
import bec.value.IntegerValue;
import bec.value.LongRealValue;
import bec.value.ObjectAddress;
import bec.value.RealValue;
import bec.value.Value;
import bec.virtualMachine.RTRegister;
import bec.virtualMachine.SVM_CONVERT;

public abstract class CONVERT extends Instruction {

	private static final boolean DEBUG = false;

	/**
	 * convert_instruction ::= convert simple_type
	 * 
	 * TOS must be of simple type, otherwise: error.
	 * 
	 * The TYPE of TOS is changed to the type specified in the instruction, this may imply code generation.
	 */
	public static void ofScode() {
//		CTStack.dumpStack("BEGIN CONVERT.ofScode: ");
		StackItem tos = CTStack.TOS;
		Type toType = Type.ofScode();
//		System.out.println("CONVERT: "+tos.getClass().getSimpleName());

		GQconvert(toType);
		
//		CTStack.dumpStack("END CONVERT.ofScode: ");
//		Global.PSEG.dump("END CONVERT.ofScode: ");
//		Util.IERR("");
	}
	
	public static void GQconvert(Type toType) {
//		FETCH.doFetch("GQconvert " + toType);
		if(CTStack.TOS.type != toType) doConvert(toType);
	}

	private static void doConvert(Type totype) {
		FETCH.doFetch("CONVERT: ");
		StackItem TOS = CTStack.TOS;
		Type fromtype = TOS.type;
		if(DEBUG) System.out.println("CONVERT.doConvert: " + TOS + " ==> " + totype);
		if( TOS instanceof ConstItem cnst) {
			Value fromValue = cnst.value;
			Value toValue = convValue(fromValue, fromtype, totype);
			cnst.value = toValue; cnst.type = totype;
			if(DEBUG) System.out.println("CONVERT.doConvert: " + totype + " ==> " + cnst);
		}
		else {
//			boolean OK = false;
//			switch(fromtype.tag) {
//				case Scode.TAG_LREAL, Scode.TAG_REAL, Scode.TAG_INT, Scode.TAG_CHAR:  OK = totype.isArithmetic(); break;
//				case Scode.TAG_OADDR: OK = totype == Type.T_GADDR; break;
//				case Scode.TAG_GADDR: OK = totype == Type.T_AADDR || totype == Type.T_OADDR; break;
//			}
//			if(! OK) Util.IERR("Type conversion is undefined: " + fromtype + " ==> " + totype);
			Global.PSEG.emit(new SVM_CONVERT(fromtype, totype), "");
			CTStack.pop(); CTStack.pushTemp(totype, 1, "CONVERT: ");
//			TOS.type = totype;
		}
	}

	
//	%title ***    C o n v e r t   C o n s t a n t   V a l u e    ***
	public static Value convValue(Value fromValue, Type fromtype, Type totype) {
//		ConstItem TOS = (ConstItem) CTStack.TOS;
//		Type fromtype = TOS.type;
		Value toValue = null;
		switch(fromtype.tag) {
		case Scode.TAG_CHAR: {
			IntegerValue intval = (IntegerValue)fromValue;
			int val = intval.value;
			switch(totype.tag) {
				case Scode.TAG_INT:   toValue = IntegerValue.of(Type.T_INT, val); break;
				case Scode.TAG_REAL:  toValue = RealValue.of(val); break;
				case Scode.TAG_LREAL: toValue = LongRealValue.of(val); break;
			}
			break;
		}
		case Scode.TAG_INT: {
			IntegerValue intval = (IntegerValue)fromValue;
			int val = intval.value;
			switch(totype.tag) {
				case Scode.TAG_CHAR:  toValue = IntegerValue.of(Type.T_CHAR, val); break;
				case Scode.TAG_REAL:  toValue = RealValue.of(val); break;
				case Scode.TAG_LREAL: toValue = LongRealValue.of(val); break;
			}
			break;
		}
		case Scode.TAG_REAL: {
			RealValue intval = (RealValue)fromValue;
			float val = intval.value;
			switch(totype.tag) {
				case Scode.TAG_CHAR:  toValue = IntegerValue.of(Type.T_CHAR, (int)(val+0.5)); break;
				case Scode.TAG_INT:   toValue = IntegerValue.of(Type.T_INT, (int)(val+0.5)); break;
				case Scode.TAG_LREAL: toValue = LongRealValue.of(val); break;
			}
			break;
		}
		case Scode.TAG_LREAL: {
			LongRealValue intval = (LongRealValue)fromValue;
			double val = intval.value;
			switch(totype.tag) {
				case Scode.TAG_CHAR: toValue = IntegerValue.of(Type.T_CHAR, (int)(val+0.5)); break;
				case Scode.TAG_INT:  toValue = IntegerValue.of(Type.T_INT, (int)(val+0.5)); break;
				case Scode.TAG_REAL: toValue = RealValue.of((float)val); break;
			}
			break;
		}
		case Scode.TAG_OADDR: {
			// An object address OADDR may be converted to a general address GADDR.
			// In that case the object address is extended with an empty attribute address
			// and the pair comprises the result.
			if(totype.tag == Scode.TAG_GADDR) {
				toValue = new GeneralAddress((ObjectAddress) fromValue, 0);
				Util.IERR("NOT IMPL");
			}
		} break;
		case Scode.TAG_GADDR: {
			// Conversion from a GADDR to OADDR (AADDR) means: take the object address (attribute address)
			// part of the general address and return as result.			
			GeneralAddress gaddr = (GeneralAddress) fromValue;
			switch(totype.tag) {
			case Scode.TAG_OADDR:  toValue = gaddr.base; break;
			case Scode.TAG_AADDR:  toValue = IntegerValue.of(Type.T_AADDR, gaddr.ofst); break;
			}			
//			Util.IERR("NOT IMPL");
		} break;
		default: toValue = null;
		}
		if(DEBUG) System.out.println("CONVERT.convValue: " + fromValue + " ==> " + toValue + ", type=" + totype);
		if(toValue == null) Util.ERROR("convValue: conversion is undefined: " + fromtype + " ==> " + totype);
		return toValue;
	}

}
