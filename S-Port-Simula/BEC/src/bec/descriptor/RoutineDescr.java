/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.compileTimeStack.CTStack;
import bec.instruction.Instruction;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Type;
import bec.util.Util;
import bec.value.FixupAddress;
import bec.value.ProgramAddress;
import bec.value.Value;
import bec.virtualMachine.SVM_ENTER;
import bec.virtualMachine.SVM_RETURN;

/// Routine descriptor.
///
/// S-CODE:
///
/// 	routine_specification
///			::= routinespec body:newtag profile:tag
///
/// 	routine_definition
/// 		::= routine body:spectag profile:tag
///			 <local_quantity>* <instruction>* en///routine
///
///			local_quantity
///				::= local var:newtag quantity_descriptor
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/descriptor/RoutineDescr.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class RoutineDescr extends Descriptor {
	
	/// The ProgramSegment with this routine code.
	ProgramSegment PSEG;
	
	private ProgramAddress adr;
	
	/// The corresponding Profile tag
	public Tag prftag;
	
	/// DataSegment with LOCAL Variables
	DataSegment DSEG;
	
	/// The local Variables
	Vector<Tag> locals;
	
	/// The size of the local Variables on the Frame
	public int localFrameSize;
	
	private static final boolean DEBUG = false;

	/// Create a new RoutineDescr with the given tags
	/// @param tag used to lookup descriptors
	/// @param prftag the corresponding Profile tag
	public RoutineDescr(final Tag tag, final Tag prftag) {
		super(Kind.K_IntRoutine, tag);
		this.prftag = prftag;
		this.locals = new Vector<Tag>();
	}
	
	/// Returns the address of this RoutineDescr
	/// @return the address of this RoutineDescr
	public ProgramAddress getAddress() {
		if(adr == null)	adr = new FixupAddress(Type.T_RADDR, this);
		return adr;
	}
	
	/// Scans the remaining S-Code (if any) belonging to this descriptor.
	/// Then construct a new Attribute instance.
	/// @return an RoutineDescr instance.
	public static RoutineDescr ofRoutineSpec() {
		Tag tag = Tag.ofScode();
		Tag prftag = Tag.ofScode();
		RoutineDescr rut = (RoutineDescr) Display.get(tag.val);
		if(rut != null) Util.IERR("");
		rut = new RoutineDescr(tag, prftag);
		return rut;
	}

	/// Scans the remaining S-Code (if any) belonging to this descriptor.
	/// Then construct a new Attribute instance.
	/// @return an RoutineDescr instance.
	public static void ofRoutineDef() {
		Tag tag = Tag.ofScode();
		Tag prftag = Tag.ofScode();
		
		RoutineDescr rut = (RoutineDescr) Display.get(tag.val);
		if(rut == null) rut = new RoutineDescr(tag, prftag);
		String modID = (Global.moduleID == null)? "" : (Global.moduleID + '_');
		String id = modID + tag.ident();
		
		rut.PSEG = new ProgramSegment("PSEG_" + id);
		ProgramSegment prevPSEG = Global.PSEG; Global.PSEG = rut.PSEG;
		
		ProgramAddress rutAddr = new ProgramAddress(Type.T_RADDR, rut.PSEG.ident, 0);
		if(rut.adr instanceof FixupAddress fix) {
			fix.setAddress(rutAddr);
		}
		rut.adr = rutAddr;
		
		ProfileDescr prf = (ProfileDescr) Display.get(prftag.val);
		if(prf == null) Util.IERR("Missing Profile " + Scode.edTag(prftag.val));

		Scode.inputInstr();
		int rela = prf.frameSize;
		if(Option.TRACE_ALLOC_FRAME) {
			IO.println("\nRoutineDescr.ofRoutineDef: ALLOC LOCALS for "+rutAddr+" First rela="+rela);
			prf.print("RoutineDescr.ofRoutineDef: ");			
		}
		
		while(Scode.curinstr == Scode.S_LOCAL) {
			Variable local = Variable.ofLocal(rela);
			if(Option.TRACE_ALLOC_FRAME) {
				IO.println("RoutineDescr.ofRoutineDef:    LOCAL " + local);
			}
			if(local.repCount == 0) Util.IERR("");
			rela = rela + (local.type.size() * local.repCount);
			rut.locals.add(local.tag);
			Scode.inputInstr();
		}
		rut.localFrameSize = rela - prf.frameSize;
		if(Option.TRACE_ALLOC_FRAME) {
			IO.println("RoutineDescr.ofRoutineDef: ALLOC LOCALS DONE: localFrameSize="+rut.localFrameSize);
		}
		Global.PSEG.emit(new SVM_ENTER(prf.getSimpleName(), rut.localFrameSize));
	
		while(Instruction.inInstruction()) { Scode.inputInstr(); }
	
		if(Scode.curinstr != Scode.S_ENDROUTINE) Util.IERR("Missing - endroutine");
		CTStack.checkStackEmpty();
		if(DEBUG) prf.print("RoutineDescr.ofRoutineDef: ");
		Global.PSEG.emit(new SVM_RETURN(prftag.ident(), prf.returSlot));
		CTStack.checkStackEmpty();

		if(Option.PRINT_GENERATED_SVM_CODE) {
			Global.PSEG.dump("END RoutineDescr.ofRoutineDef:: ");
		}

		if(rut.DSEG != null) Global.routineSegments.add(rut.DSEG);
		if(rut.PSEG != null) Global.routineSegments.add(rut.PSEG);
		
		Global.PSEG = prevPSEG;
	}
	
	@Override
	public String toString() {
		return "Routine " + tag + ", profile:" + prftag + ", adr:" + adr;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(final AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("RoutineDescr.Write: " + this);
		oupt.writeByte(kind);
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

	/// Reads a RoutineDescr from the given input.
	/// @param inpt the input stream
	public static RoutineDescr read(final AttributeInputStream inpt) throws IOException {
		Tag prftag = null;
		boolean present = inpt.readBoolean();
		if(present) prftag = Tag.read(inpt);
		
		Tag tag = Tag.read(inpt);
		RoutineDescr rut = new RoutineDescr(tag, prftag);
		present = inpt.readBoolean();
		if(present) {
			rut.adr = (ProgramAddress) Value.read(inpt);
			String segID = inpt.readString();
			rut.PSEG = (ProgramSegment) Segment.lookup(segID);
		}
		
		int n = inpt.readShort();
		for(int i=0;i<n;i++) rut.locals.add(Tag.read(inpt));
		if(Option.ATTR_INPUT_TRACE) IO.println("RoutineDescr.Read: " + rut);
		
		return(rut);
	}

}
