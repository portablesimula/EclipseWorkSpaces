package bec.virtualMachine;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;

/**
 * temp_control ::= t-inito
 * 
 * t-inito
 * force TOS value; check TOS type(OADDR);
 * pop;
 * 
 * Code is generated to initialise a scan of the object described by TOS,
 * i.e. SAVE-OBJECT is set to refer to the object, and SAVE-INDEX is initialized.
 * TOS is popped.
 */
public class SVM_INITO extends SVM_Instruction {
	private static ObjectAddress SAVE_OBJECT;
	private static int SAVE_INDEX;
	private static int SAVE_LENGTH;
	
	private static final boolean DEBUG = false;

	public SVM_INITO() {
		this.opcode = SVM_Instruction.iINITO;
	}
	
//	 Visible record entity;  info "DYNAMIC";
//	 begin ref(inst)                sl;   -- during GC used as GCL!!!!
//	       range(0:MAX_BYT)         sort;
//	       range(0:MAX_BYT)         misc;
//	       variant ref(ptp)         pp;   -- used for instances
//	       variant range(0:MAX_TXT) ncha; -- used for text entities
//	       variant size             lng;  -- used for other entities
//	 end;
//
//	 Visible record inst:entity;
//	 begin ref(entity)              gcl;
//	       variant ref(inst)        dl;
//	               label            lsc;
//	       variant entry(Pmovit)    moveIt;
//	 end;

	static ObjectAddress get() {
		while(SAVE_INDEX < SAVE_LENGTH) {
			Value value = SAVE_OBJECT.addOffset(SAVE_INDEX++).load();
			if(DEBUG) IO.println("SVM_INITO.get: SAVE_OBJECT["+(SAVE_INDEX-1)+"] = "+value);
			if(value instanceof ObjectAddress oaddr) return oaddr;
		}
		if(DEBUG) {
//			Segment.lookup("POOL_0").dump("SVM_INITO.execute: FINAL POOL_0", 0, 20);
			IO.println("SVM_INITO.get: FINISHED !");
//			Util.IERR("");
		}
		return null;
	}
	
	static void set(ObjectAddress oaddr) {
		if(DEBUG) IO.println("SVM_INITO.set: SAVE_OBJECT["+(SAVE_INDEX-1)+"] = "+oaddr);
		SAVE_OBJECT.store(SAVE_INDEX - 1, oaddr, "SETO");
	}

	@Override
	public void execute() {
//		if(DEBUG) Segment.lookup("POOL_0").dump("SVM_INITO.execute: INITIAL POOL_0", 0, 20);
		try {
//			SAVE_OBJECT = RTStack.popOADDR();
			SAVE_OBJECT = RTStack.popOADDR().addOffset(-7);
			SAVE_INDEX = 0+6;
			if(DEBUG) {
				IO.println("SVM_INITO: " + SAVE_OBJECT.getClass().getSimpleName() + " " + SAVE_OBJECT);
				RTUtil.dumpEntity(SAVE_OBJECT);
			}
			IntegerValue sort = (IntegerValue) SAVE_OBJECT.load(1);
//			if(sort.value != 1) Util.IERR("NOT A SAVE OBJECT");
//			if(sort.value != 9) Util.IERR("NOT A SAVE OBJECT");
			if(sort.value != RTUtil.S_SAV) Util.IERR("NOT A SAVE OBJECT");
			IntegerValue lng = (IntegerValue) SAVE_OBJECT.load(3);
			if(DEBUG) IO.println("SVM_INITO: sort="+sort+", lng="+lng);
			SAVE_LENGTH = lng.value;
		} catch(Exception e) {
			IO.println("SVM_INITO: FAILED: " + e);
			e.printStackTrace();
//			System.exit(-1);
		}
		
		Global.PSC.addOfst(1);
	}
	
	@Override	
	public String toString() {
		return "INITO    ";
	}
	
	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("SVM.Write: " + this);
		oupt.writeOpcode(opcode);
	}

	public static SVM_Instruction read(AttributeInputStream inpt) throws IOException {
		SVM_INITO instr = new SVM_INITO();
		if(Option.ATTR_INPUT_TRACE) IO.println("SVM.Read: " + instr);
		return instr;
	}

}
