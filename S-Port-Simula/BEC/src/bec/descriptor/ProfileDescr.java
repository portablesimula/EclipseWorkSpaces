/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CALL;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Option;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;
import bec.virtualMachine.SVM_CALL_SYS;

/// Profile descriptor.
///
/// S-CODE:
///
///	routine_profile
///		 ::= profile profile:newtag <peculiar>?
///			   <import_definition>* <export or exit>? endprofile
///
///		peculiar
///			::= known body:newtag kid:string
///			::= system body:newtag sid:string
///			::= external body:newtag nature:string xid:string
///			::= interface pid:string
///
///		import_definition
///			::= import parm:newtag quantity_descriptor
///
///		export_or_exit
///			::= export parm:newtag resolved_type
///			::= exit return:newtag
/// 
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/descriptor/ProfileDescr.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class ProfileDescr extends Descriptor {
	public int pKind; // Peculiar Profile Kind
	public Vector<Tag> params;
	private Tag exportTag;
	public ObjectAddress returSlot;
	public DataSegment DSEG;
	public int exportSize; // Size of Export slot
	public int frameSize; // Size of Frame
	
	private static final boolean DEBUG = false;
	
	//	NOT SAVED:
	private Vector<Variable> imports;
	public Variable export;
	public Variable exit;
	
	public int bodyTag;    // peculiar
	private String nature; // peculiar
	public String ident;   // peculiar
	
	private ProfileDescr(int kind, Tag tag) {
		super(kind, tag);
	}
	
	public String getSimpleName() {
		return tag.ident();
	}
	
	public Variable getExport() {
		if(exportTag == null) return null;
		Variable export = (Variable) exportTag.getMeaning();
		return export;
	}
	
	public String dsegIdent() {
		return "DSEG_" + Global.moduleID + '_' + tag.ident();

	}

	/**
	 * 	routine_profile
	 * 		 ::= profile profile:newtag <peculiar>?
	 * 			   <import_definition>* <export or exit>? endprofile
	 * 
	 * 		peculiar
	 * 			::= known body:newtag kid:string
	 * 			::= system body:newtag sid:string
	 * 			::= external body:newtag nature:string xid:string
	 * 			::= interface pid:string
	 * 
	 * 		import_definition
	 * 			::= import parm:newtag quantity_descriptor
	 * 
	 * 		export_or_exit
	 * 			::= export parm:newtag resolved_type
	 * 			::= exit return:newtag
	 */
	public static ProfileDescr ofScode() {
		Tag ptag = Tag.ofScode();
		ProfileDescr prf = new ProfileDescr(Kind.K_ProfileDescr, ptag);
		if(Scode.nextByte() == Scode.S_EXTERNAL) {
			 // peculiar ::= external body:newtag nature:string xid:string
			Scode.inputInstr();
			Tag.ofScode();
			Scode.inString();
			Util.IERR("External Routines is not part of this implementation");
		} else if(Scode.nextByte() == Scode.S_INTERFACE) {
			 // peculiar ::= interface pid:string
			Scode.inputInstr();
			String xid = Scode.inString();
			prf.pKind = SVM_CALL_SYS.getSysKind(xid);
		} else if(Scode.nextByte() == Scode.S_KNOWN) {
			// peculiar	::= known body:newtag kid:string
			Scode.inputInstr();
			Tag rtag = Tag.ofScode();
			String xid = Scode.inString();
			@SuppressWarnings("unused")
			RoutineDescr rut = new RoutineDescr(Kind.K_IntRoutine, rtag, null);
			prf.pKind = SVM_CALL_SYS.getKnownKind(xid);
		} else if(Scode.nextByte() == Scode.S_SYSTEM) {
			 //	peculiar ::= system body:newtag sid:string
			Scode.inputInstr();
			Tag rtag = Tag.ofScode();
			String xid = Scode.inString();
			@SuppressWarnings("unused")
			RoutineDescr rut = new RoutineDescr(Kind.K_IntRoutine, rtag, null);
			prf.pKind = SVM_CALL_SYS.getSysKind(xid);
		}
		prf.imports = new Vector<Variable>();
		prf.params = new Vector<Tag>();

		Scode.inputInstr();
		while(Scode.curinstr == Scode.S_IMPORT) {
			Variable par = null;
			par = Variable.ofIMPORT();				
			prf.imports.add(par);
			prf.params.add(par.tag);
			Scode.inputInstr();
		}
		if(Scode.curinstr == Scode.S_EXIT) {
			prf.exit = Variable.ofEXIT();
			Scode.inputInstr();
		} else if(Scode.curinstr == Scode.S_EXPORT) {
			prf.export = Variable.ofEXPORT();				
			prf.exportTag = prf.export.tag;
			Scode.inputInstr();
		}
		if(prf.exit == null) prf.exit = Variable.ofRETUR(prf.returSlot);
		if(Scode.curinstr != Scode.S_ENDPROFILE)
			Util.IERR("Missing ENDPROFILE. Got " + Scode.edInstr(Scode.curinstr));
		
		// Allocate StackFrame
		int rela = 0;
		if(prf.export != null) {
			prf.export.address = ObjectAddress.ofRelFrameAddr(rela);
			prf.exportSize = prf.export.type.size();
			rela += prf.exportSize;
		}
		for(Variable par:prf.imports) {
			par.address = ObjectAddress.ofRelFrameAddr(rela);
			rela += par.type.size() * par.repCount;
		}
		// Allocate Return address
		prf.returSlot = ObjectAddress.ofRelFrameAddr(rela++);
		
		if(prf.exit != null) {
			prf.exit.address = prf.returSlot;
		}
		
		if(DEBUG) {
			prf.print("ProfileDescr.ofProfile: PROFILE: ");
		}
		prf.frameSize = rela;
//		prf.print("ProfileDescr.ofProfile: ");
		return prf;
	}

	@Override
	public void print(final String indent) {
		String profile = "PROFILE " + tag;
		switch(kind) {
		case Scode.S_KNOWN ->     profile += " KNOWN "     + Scode.edTag(bodyTag) + " \"" + ident + '"';
		case Scode.S_SYSTEM ->    profile += " SYSTEM "    + Scode.edTag(bodyTag) + " " + ident;
		case Scode.S_EXTERNAL ->  profile += " EXTERNAL "  + Scode.edTag(bodyTag) + " " + nature + " " + ident;
		case Scode.S_INTERFACE -> profile += " INTERFACE " + ident;
		}
		IO.println(indent + profile);
		if(exportTag != null) IO.println(indent + "   " + exportTag.getMeaning());
		if(params != null) for(Tag ptag:params) IO.println(indent + "   " + ptag.getMeaning());
		if(returSlot != null) IO.println(indent + "   ReturSlot = " + returSlot);
		if(DSEG != null) IO.println(indent + "   DSEG = " + DSEG);
		IO.println(indent + "ENDPROFILE  FrameHeadSize="+frameSize);	
	}
	
	public String toString() {
		String profile = "PROFILE " + tag;
		switch(kind) {
			case Scode.S_KNOWN ->     profile += " KNOWN " + Scode.edTag(bodyTag) + " \"" + ident + '"';
			case Scode.S_SYSTEM ->    profile += " SYSTEM " + Scode.edTag(bodyTag) + " " + ident;
			case Scode.S_EXTERNAL ->  profile += " EXTERNAL " + Scode.edTag(bodyTag) + " " + nature + " " + ident;
			case Scode.S_INTERFACE -> profile += " INTERFACE " + ident;
		}
		profile += " DSEG=" + DSEG;
		if(params != null) {
			String cc = "( ";
			for(Tag ptag:params) {
				profile += cc +ptag;
				cc = ", ";
			}
			profile += ")";
		}
		if(exportTag != null) profile += " ==> exportTag=" + exportTag;
		profile += " returSlot=" + returSlot;
		return profile;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("ProfileDescr.Write: " + this);
		if(! CALL.USE_FRAME_ON_STACK) {
			DSEG.write(oupt);
		}
		oupt.writeKind(kind);
		tag.write(oupt);
		oupt.writeShort(pKind);
		oupt.writeString(this.dsegIdent());
		oupt.writeShort(frameSize);
		oupt.writeShort(params.size());
		for(Tag par:params) par.write(oupt);
		returSlot.write(oupt);
		if(export != null) {
			oupt.writeBoolean(true);
			exportTag.write(oupt);
			oupt.writeShort(exportSize);
		} else oupt.writeBoolean(false);
	}

	public static ProfileDescr read(AttributeInputStream inpt) throws IOException {
		Tag tag = Tag.read(inpt);
		ProfileDescr prf = new ProfileDescr(Kind.K_ProfileDescr, tag);
		if(Option.ATTR_INPUT_TRACE) IO.println("BEGIN ProfileDescr.Read: " + prf);
		prf.pKind = inpt.readShort();
		String segID = inpt.readString();
		if(! CALL.USE_FRAME_ON_STACK) {
			prf.DSEG =(DataSegment) Segment.lookup(segID);
		}
		prf.frameSize = inpt.readShort();
		prf.params = new Vector<Tag>();
		int n = inpt.readShort();
		for(int i=0;i<n;i++) {
			prf.params.add(Tag.read(inpt));
		}
		prf.returSlot = (ObjectAddress) Value.read(inpt);
		boolean present = inpt.readBoolean();
		if(present) {
			prf.exportTag = Tag.read(inpt);
			prf.exportSize = inpt.readShort();
		}
		
		if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.Read: " + prf);
		return prf;
	}


}
