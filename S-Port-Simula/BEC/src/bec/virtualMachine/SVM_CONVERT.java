package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CONVERT;
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
import bec.virtualMachine.RTStack.RTStackItem;

public class SVM_CONVERT extends SVM_Instruction {
	int fromType;
	int toType;
	
	private final boolean DEBUG = false;
		
	public SVM_CONVERT(int fromType, int toType) {
		this.opcode = SVM_Instruction.iCONVERT;
		this.fromType = fromType;
		this.toType = toType;
	}
	
	@Override
	public void execute() {
		if(DEBUG) {
			System.out.println("SVM_CONVERT.execute: "+fromType+"  ==> " + toType);
			RTStackItem tos = RTStack.peek();
			System.out.println("SVM_CONVERT.execute: TOS="+tos);
		}
		
		Value fromValue = null;
		switch(fromType) {
		case Scode.TAG_OADDR: fromValue = RTStack.popOADDR(); break;
		case Scode.TAG_GADDR: fromValue = RTStack.popGADDR(); break;
		default:			  fromValue = RTStack.pop().value();
		}

		if(DEBUG) {
			System.out.println("SVM_CONVERT.execute: fromValue="+fromValue+"  ==> " + toType);
		}

		Value toValue = convValue(fromValue, fromType, toType);
		RTStack.push(toValue, "CONVERT: ");
		Global.PSC.ofst++;
	}
	
//	%title ***    C o n v e r t   C o n s t a n t   V a l u e    ***
	private static Value convValue(Value fromValue, int fromtype, int totype) {
		Value toValue = null;
		boolean ILL = false;
		switch(fromtype) {
		case Scode.TAG_CHAR: {
			IntegerValue fromval = (IntegerValue)fromValue;
			int val = (fromval == null)? 0 : fromval.value;
			switch(totype) {
				case Scode.TAG_INT:   toValue = IntegerValue.of(Type.T_INT, val); break;
				case Scode.TAG_REAL:  toValue = RealValue.of(val); break;
				case Scode.TAG_LREAL: toValue = LongRealValue.of(val); break;
				default: ILL = true;
			}
			break;
		}
		case Scode.TAG_INT: {
			IntegerValue fromval = (IntegerValue)fromValue;
			int val = (fromval == null)? 0 : fromval.value;
			switch(totype) {
				case Scode.TAG_CHAR:  toValue = IntegerValue.of(Type.T_CHAR, val); break;
				case Scode.TAG_SIZE:  toValue = IntegerValue.of(Type.T_SIZE, val); break;
				case Scode.TAG_REAL:  toValue = RealValue.of(val); break;
				case Scode.TAG_LREAL: toValue = LongRealValue.of(val); break;
				default: ILL = true;
			}
			break;
		}
		case Scode.TAG_REAL: {
			RealValue fromval = (RealValue)fromValue;
			float val = (fromval == null)? 0 : fromval.value;
			switch(totype) {
				case Scode.TAG_CHAR:  toValue = IntegerValue.of(Type.T_CHAR, (int)(val+0.5)); break;
				case Scode.TAG_INT:   toValue = IntegerValue.of(Type.T_INT, (int)(val+0.5)); break;
				case Scode.TAG_LREAL: toValue = LongRealValue.of(val); break;
				default: ILL = true;
			}
			break;
		}
		case Scode.TAG_LREAL: {
			LongRealValue fromval = (LongRealValue)fromValue;
			double val = (fromval == null)? 0 : fromval.value;
			switch(totype) {
				case Scode.TAG_CHAR: toValue = IntegerValue.of(Type.T_CHAR, (int)(val+0.5)); break;
				case Scode.TAG_INT:  toValue = IntegerValue.of(Type.T_INT, (int)(val+0.5)); break;
				case Scode.TAG_REAL: toValue = RealValue.of((float)val); break;
				default: ILL = true;
			}
			break;
		}
		case Scode.TAG_OADDR: {
			// An object address OADDR may be converted to a general address GADDR.
			// In that case the object address is extended with an empty attribute address
			// and the pair comprises the result.
			if(totype == Scode.TAG_GADDR) {
				toValue = new GeneralAddress((ObjectAddress) fromValue, 0);
//				Util.IERR("NOT IMPL");
			} else ILL = true;

		} break;
		case Scode.TAG_GADDR: {
			// Conversion from a GADDR to OADDR (AADDR) means: take the object address (attribute address)
			// part of the general address and return as result.			
			GeneralAddress gaddr = (GeneralAddress) fromValue;
			switch(totype) {
				case Scode.TAG_OADDR:  toValue = gaddr.base; break;
				case Scode.TAG_AADDR:  toValue = IntegerValue.of(Type.T_AADDR, gaddr.ofst); break;
				default: ILL = true;
			}			
		} break;
		default: toValue = null;
		}
//		if(DEBUG) System.out.println("CONVERT.convValue: " + fromValue + " ==> " + toValue + ", type=" + totype);
		if(ILL) {
			Util.ERROR("convValue: conversion is undefined: " + Scode.edTag(fromtype) + " ==> " + Scode.edTag(totype));
			Util.IERR("");
		}
		return toValue;
	}

	
	public String toString() {
		return "CONVERT  " + fromType + " ==> " + toType;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
		oupt.writeShort(fromType);
		oupt.writeShort(toType);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_CONVERT instr = new SVM_CONVERT(inpt.readShort(), inpt.readShort());
		if(Global.ATTR_INPUT_TRACE) System.out.println("SVM.Read: " + instr);
		return instr;
	}

}
