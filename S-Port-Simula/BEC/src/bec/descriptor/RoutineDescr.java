package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.compileTimeStack.CTStack;
import bec.instruction.CALL;
import bec.instruction.Instruction;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.value.FixupAddress;
import bec.value.ObjectAddress;
import bec.value.ProgramAddress;
import bec.value.Value;
import bec.virtualMachine.SVM_ENTER;
import bec.virtualMachine.SVM_NOOP;
import bec.virtualMachine.SVM_POPK;
import bec.virtualMachine.SVM_PUSHC;
import bec.virtualMachine.SVM_RETURN;

public class RoutineDescr extends Descriptor {
	ProgramSegment PSEG;
	private ProgramAddress adr;
	public Tag prftag;
	DataSegment DSEG; // With LOCAL Variables
	Vector<Tag> locals;
	public int localFrameSize;
	
	public RoutineDescr(int kind, Tag tag, Tag prftag) {
		super(kind, tag);
		this.prftag = prftag;
		this.locals = new Vector<Tag>();
	}
	
	public ProgramAddress getAddress() {
		if(adr == null)	adr = new FixupAddress(Type.T_PADDR, this);
		return adr;
	}
	
	public String toString() {
		return "Routine " + tag + ", profile:" + prftag + ", adr:" + adr;
	}
	
	/**
	 * routine_specification
	 *		::= routinespec body:newtag profile:tag
	 * @return
	 */
	public static RoutineDescr ofRoutineSpec() {
		Tag tag = Tag.ofScode();
		Tag prftag = Tag.ofScode();
		RoutineDescr rut = (RoutineDescr) Global.DISPL.get(tag.val);
		if(rut != null) Util.IERR("");
		rut = new RoutineDescr(Kind.K_IntRoutine, tag, prftag);
//		rut.adr = null;
//		Util.IERR("TEST DETTE");
		return rut;
	}

	/**
	 * 	routine_definition
	 * 		::= routine body:spectag profile:tag
	 *			 <local_quantity>* <instruction>* endroutine
	 *
	 *		local_quantity
	 *			::= local var:newtag quantity_descriptor
	 */
	public static void ofRoutineDef() {
		Tag tag = Tag.ofScode();
		Tag prftag = Tag.ofScode();
//		Global.dumpDISPL("RoutineDescr.ofRoutineDef: ");
//		System.out.println("RoutineDescr.ofRoutineDef: tag="+tag + "  prfTag="+prftag);
		
		RoutineDescr rut = (RoutineDescr) Global.DISPL.get(tag.val);
		if(rut == null) rut = new RoutineDescr(Kind.K_IntRoutine, tag, prftag);
		String id = Global.moduleID + '_' + prftag.ident();
		
		DataSegment prevDSEG = null;
		if(! CALL.USE_FRAME_ON_STACK) {
			rut.DSEG = new DataSegment("DSEG_LOCAL_" + id, Kind.K_SEG_DATA);
			prevDSEG = Global.DSEG; Global.DSEG = rut.DSEG;
		}
		rut.PSEG = new ProgramSegment("PSEG_" + id, Kind.K_SEG_CODE);
		ProgramSegment prevPSEG = Global.PSEG; Global.PSEG = rut.PSEG;
		
//		boolean TESTING = id.equalsIgnoreCase("KNWN_REST");
		ProgramAddress rutAddr = new ProgramAddress(Type.T_RADDR, rut.PSEG,0);
		if(rut.adr instanceof FixupAddress fix) {
//			System.out.println("RoutineDescr.ofRoutineDef: rut="+rut);
//			System.out.println("RoutineDescr.ofRoutineDef: fix="+fix);
			fix.setAddress(rutAddr);
//			System.out.println("RoutineDescr.ofRoutineDef: Updated rut="+rut);
//			System.out.println("RoutineDescr.ofRoutineDef: Updated fix="+fix);
			
//			Segment seg = Segment.lookup("PSEG_KNWN_ED_STR");
//			seg.dump("RoutineDescr.ofRoutineDef: ");
//			if(TESTING) Util.IERR("SJEKK DETTE");
		}
		rut.adr = rutAddr;
		
//		System.out.println("RoutineDescr.ofRoutineDef: "+Global.DISPL.get(prftag.val));
		ProfileDescr prf = (ProfileDescr) Global.DISPL.get(prftag.val);
		if(prf == null) Util.IERR("Missing Profile " + Scode.edTag(prftag.val));

		Scode.inputInstr();
		int rela = prf.frameSize;
		while(Scode.curinstr == Scode.S_LOCAL) {
			if(CALL.USE_FRAME_ON_STACK) {
				Variable local = Variable.ofLocal(rela);
				rela = rela + local.type.size();
				rut.locals.add(local.tag);
//				Global.PSEG.emit(new SVM_PUSHC(local.type, null), ""+local);
				Scode.inputInstr();
//				Util.IERR(""+local.type.size());
			} else {
				Variable local = Variable.ofLocal(rut.DSEG);
				rut.locals.add(local.tag);
				Scode.inputInstr();
//				prf.DSEG.dump("RoutineDescr.LOCAL: ");
//				==================== RoutineDescr.ofRoutineDef: MODL02_PEXERRDSEG_ENVIR0_PEXERR DUMP ====================317574433
//					     0: null                        RETUR
//					     1: null                        IMPORT T[3:INT] size=1 pntmap=null null
//				    	 2: null                        IMPORT T[8:OADDR] size=1 pntmap={0} null
//					     3: null                        LOCAL T[10:PADDR] size=1 pntmap=null null
//					     4: null                        LOCAL T[3:INT] size=1 pntmap=null null
//				==================== RoutineDescr.ofRoutineDef: MODL02_PEXERRDSEG_ENVIR0_PEXERR END  ====================
			}
		}
		rut.localFrameSize = rela - prf.frameSize;
		Global.PSEG.emit(new SVM_ENTER(rut.localFrameSize), ""+rut);
	
		System.out.println("RoutineDescr.ofRoutineDef: " + rut + "??????????????????????????????????????????????????????????????????????????????????????");
		while(Instruction.inInstruction()) { Scode.inputInstr(); }
	
		if(Scode.curinstr != Scode.S_ENDROUTINE) Util.IERR("Missing - endroutine");
		CTStack.checkStackEmpty();
		prf.print("RoutineDescr.ofRoutineDef: ");
		Global.PSEG.emit(new SVM_RETURN(prf.returSlot), "");
		CTStack.checkStackEmpty();

		if(! CALL.USE_FRAME_ON_STACK) {
			Global.DSEG = prevDSEG;
		}
		Global.PSEG = prevPSEG;
//		Util.IERR("");
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("RoutineDescr.Write: " + this);
		if(DSEG != null) DSEG.write(oupt);
		if(PSEG != null) PSEG.write(oupt);
		oupt.writeKind(kind);
		if(prftag != null) {
			oupt.writeBoolean(true);
			prftag.write(oupt);
		} else oupt.writeBoolean(false);

		tag.write(oupt);
		if(adr != null) {
			oupt.writeBoolean(true);
			adr.write(oupt);
			oupt.writeString(PSEG.ident);
		} else oupt.writeBoolean(false);
		
		oupt.writeShort(locals.size());
		for(Tag local:locals) local.write(oupt);
	}

	public static RoutineDescr read(AttributeInputStream inpt) throws IOException {
//		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  BEGIN RoutineDescr.Read");
		Tag prftag = null;
		boolean present = inpt.readBoolean();
		if(present) prftag = Tag.read(inpt);
		
		Tag tag = Tag.read(inpt);
		RoutineDescr rut = new RoutineDescr(Kind.K_IntRoutine, tag, prftag);
		present = inpt.readBoolean();
		if(present) {
			rut.adr = (ProgramAddress) Value.read(inpt);
			String segID = inpt.readString();
			rut.PSEG = (ProgramSegment) Segment.lookup(segID);
		}
		
		int n = inpt.readShort();
		for(int i=0;i<n;i++) rut.locals.add(Tag.read(inpt));
		if(Global.ATTR_INPUT_TRACE) System.out.println("RoutineDescr.Read: " + rut);
		
		return(rut);
	}

}
