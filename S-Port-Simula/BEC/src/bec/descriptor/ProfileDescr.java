package bec.descriptor;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.instruction.CALL;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Tag;
import bec.util.Util;
import bec.value.ObjectAddress;
import bec.value.Value;
import bec.virtualMachine.SVM_CALLSYS;

public class ProfileDescr extends Descriptor {
	public int pKind; // Peculiar Profile Kind
	public Vector<Tag> params;
	private Tag exportTag;
	public ObjectAddress returSlot;
	public DataSegment DSEG;
	public int exportSize; // Size of Export slot
	public int frameSize; // Size of Frame
	
	//	NOT SAVED:
	private Vector<Variable> imports;
	public Variable export;
	public Variable exit;
	
	public int bodyTag;    // peculiar
	private String nature; // peculiar
	public String ident;   // peculiar
	
	private ProfileDescr(int kind, Tag tag) {
		super(kind, tag);
//		System.out.println("NEW ProfileDescr: " + tag);
	}
	
	public Variable getExport() {
		if(exportTag == null) return null;
		Variable export = (Variable) exportTag.getMeaning();
		return export;
	}
	
	public String dsegIdent() {
		return "DSEG_" + Global.moduleID + '_' + tag.ident();

	}

//	%title ***   I n p u t   P r o f i l e   ***
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
	public static ProfileDescr ofProfile() {
		Tag ptag = Tag.ofScode();
//		System.out.println("ProfileDescr.inProfile: " + Scode.edTag(ptag));
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
			prf.pKind = getSysKind(xid);
		} else if(Scode.nextByte() == Scode.S_KNOWN) {
			// peculiar	::= known body:newtag kid:string
			Scode.inputInstr();
			Tag rtag = Tag.ofScode();
			String xid = Scode.inString();
			RoutineDescr rut = new RoutineDescr(Kind.K_IntRoutine, rtag, null);
//			prf.pKind = getKnownKind(xid);
		} else if(Scode.nextByte() == Scode.S_SYSTEM) {
			 //	peculiar ::= system body:newtag sid:string
			Scode.inputInstr();
			Tag rtag = Tag.ofScode();
			String xid = Scode.inString();
			RoutineDescr rut = new RoutineDescr(Kind.K_IntRoutine, rtag, null);
			prf.pKind = getSysKind(xid);
		}
		prf.imports = new Vector<Variable>();
		prf.params = new Vector<Tag>();
		
		if(! CALL.USE_FRAME_ON_STACK) {
			if(prf.DSEG == null) {
//				prf.DSEG = new DataSegment("DSEG_" + Global.moduleID + '_' + ptag.ident(), Kind.K_SEG_DATA);
				prf.DSEG = new DataSegment(prf.dsegIdent(), Kind.K_SEG_DATA);
//				System.out.println("ProfileDescr.ofProfile: SET-DSEG: DSEG="+prf.DSEG.ident + " PTAG="+ptag);
			}
			prf.returSlot = prf.DSEG.nextAddress();
			prf.DSEG.emit(null, "RETUR");
		}

		Scode.inputInstr();
		while(Scode.curinstr == Scode.S_IMPORT) {
			Variable par = null;
			if(CALL.USE_FRAME_ON_STACK) {
				par = Variable.ofIMPORT();				
			} else {
				par = Variable.ofIMPORT(prf.DSEG);
			}
			prf.imports.add(par);
			prf.params.add(par.tag);
			Scode.inputInstr();
		}
		if(Scode.curinstr == Scode.S_EXIT) {
			if(CALL.USE_FRAME_ON_STACK) {
				prf.exit = Variable.ofEXIT();
			} else {
				prf.exit = Variable.ofEXIT(prf.returSlot);
			}
			Scode.inputInstr();
		} else if(Scode.curinstr == Scode.S_EXPORT) {
			if(CALL.USE_FRAME_ON_STACK) {
				prf.export = Variable.ofEXPORT();				
			} else {
				prf.export = Variable.ofEXPORT(prf.DSEG);
			}
			prf.exportTag = prf.export.tag;
			Scode.inputInstr();
		}
		if(prf.exit == null) prf.exit = Variable.ofRETUR(prf.returSlot);
		if(Scode.curinstr != Scode.S_ENDPROFILE)
			Util.IERR("Missing ENDPROFILE. Got " + Scode.edInstr(Scode.curinstr));
		
		if(CALL.USE_FRAME_ON_STACK) {
			// Allocate StackFrame
			int rela = 0;
			if(prf.export != null) {
				prf.export.address = new ObjectAddress(null, rela);
				prf.exportSize = prf.export.type.size();
				rela += prf.exportSize;
			}
			for(Variable par:prf.imports) {
				par.address = new ObjectAddress(null, rela);
				rela += par.type.size();
			}
			// Allocate Return address
			prf.returSlot = new ObjectAddress(null, rela++);
			
			if(prf.exit != null) {
//				Util.IERR("NOT IMPL");				
			}
			
			prf.print("ProfileDescr.ofProfile: PROFILE: ");
//			Util.IERR("NOT IMPL");
			prf.frameSize = rela;
		}		
		
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
		System.out.println(indent + profile);
		if(exportTag != null) System.out.println(indent + "   " + exportTag.getMeaning());
		if(params != null) for(Tag ptag:params) System.out.println(indent + "   " + ptag.getMeaning());
		if(returSlot != null) System.out.println(indent + "   ReturSlot = " + returSlot);
		if(DSEG != null) System.out.println(indent + "   DSEG = " + DSEG);
//		if(DSEG != null) DSEG.dump("ProfileDescr.print: ");
		System.out.println(indent + "ENDPROFILE");	
//		Util.IERR("");
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

	private static int getSysKind(String s) {
		//--- Search for inline index ---
		if(s.equalsIgnoreCase("TERMIN")) return SVM_CALLSYS.P_TERMIN;
		if(s.equalsIgnoreCase("INTRHA")) return SVM_CALLSYS.P_INTRHA;
		if(s.equalsIgnoreCase("STREQL")) return SVM_CALLSYS.P_STREQL;
		if(s.equalsIgnoreCase("PRINTO")) return SVM_CALLSYS.P_PRINTO;
		if(s.equalsIgnoreCase("INITIA")) return SVM_CALLSYS.P_INITIA;
		if(s.equalsIgnoreCase("SETOPT")) return SVM_CALLSYS.P_SETOPT;
		if(s.equalsIgnoreCase("DMPSEG")) return SVM_CALLSYS.P_DMPSEG;
		if(s.equalsIgnoreCase("DMPENT")) return SVM_CALLSYS.P_DMPENT;
		if(s.equalsIgnoreCase("DMPOOL")) return SVM_CALLSYS.P_DMPOOL;
		if(s.equalsIgnoreCase("VERBOSE")) return SVM_CALLSYS.P_VERBOSE;
		if(s.equalsIgnoreCase("GINTIN")) return SVM_CALLSYS.P_GINTIN;
		if(s.equalsIgnoreCase("GTEXIN")) return SVM_CALLSYS.P_GTEXIN;
		if(s.equalsIgnoreCase("SIZEIN")) return SVM_CALLSYS.P_SIZEIN;
		if(s.equalsIgnoreCase("GVIINF")) return SVM_CALLSYS.P_GVIINF;
		if(s.equalsIgnoreCase("GIVINF")) return SVM_CALLSYS.P_GIVINF;
		if(s.equalsIgnoreCase("CPUTIM")) return SVM_CALLSYS.P_CPUTIM;
		if(s.equalsIgnoreCase("DWAREA")) return SVM_CALLSYS.P_DWAREA;
		if(s.equalsIgnoreCase("MOVEIN")) return SVM_CALLSYS.P_MOVEIN;
		if(s.equalsIgnoreCase("OPFILE")) return SVM_CALLSYS.P_OPFILE;
		if(s.equalsIgnoreCase("CLFILE")) return SVM_CALLSYS.P_CLFILE;
		if(s.equalsIgnoreCase("LSTLOC")) return SVM_CALLSYS.P_LSTLOC;
		if(s.equalsIgnoreCase("MAXLOC")) return SVM_CALLSYS.P_MAXLOC;
		if(s.equalsIgnoreCase("CHKPNT")) return SVM_CALLSYS.P_CHKPNT;
		if(s.equalsIgnoreCase("LOCKFI")) return SVM_CALLSYS.P_LOCKFI;
		if(s.equalsIgnoreCase("UNLOCK")) return SVM_CALLSYS.P_UNLOCK;
		if(s.equalsIgnoreCase("INIMAG")) return SVM_CALLSYS.P_INIMAG;
		if(s.equalsIgnoreCase("OUTIMA")) return SVM_CALLSYS.P_OUTIMA;
		if(s.equalsIgnoreCase("BREAKO")) return SVM_CALLSYS.P_BREAKO;
		if(s.equalsIgnoreCase("LOCATE")) return SVM_CALLSYS.P_LOCATE;
		if(s.equalsIgnoreCase("DELETE")) return SVM_CALLSYS.P_DELETE;
		if(s.equalsIgnoreCase("GDSNAM")) return SVM_CALLSYS.P_GDSNAM;
		if(s.equalsIgnoreCase("GDSPEC")) return SVM_CALLSYS.P_GDSPEC;
		if(s.equalsIgnoreCase("GETLPP")) return SVM_CALLSYS.P_GETLPP;
		if(s.equalsIgnoreCase("NEWPAG")) return SVM_CALLSYS.P_NEWPAG;
		if(s.equalsIgnoreCase("PRINTO")) return SVM_CALLSYS.P_PRINTO;
		if(s.equalsIgnoreCase("STREQL")) return SVM_CALLSYS.P_STREQL;
		if(s.equalsIgnoreCase("INBYTE")) return SVM_CALLSYS.P_INBYTE;
		if(s.equalsIgnoreCase("OUTBYT")) return SVM_CALLSYS.P_OUTBYT;
		if(s.equalsIgnoreCase("GETINT")) return SVM_CALLSYS.P_GETINT;
		if(s.equalsIgnoreCase("GTREAL")) return SVM_CALLSYS.P_GTREAL;
		if(s.equalsIgnoreCase("GTFRAC")) return SVM_CALLSYS.P_GTFRAC;
		if(s.equalsIgnoreCase("PUTSTR")) return SVM_CALLSYS.P_PUTSTR;
		if(s.equalsIgnoreCase("PUTINT")) return SVM_CALLSYS.P_PUTINT;
		if(s.equalsIgnoreCase("PUTINT2")) return SVM_CALLSYS.P_PUTINT2;
		if(s.equalsIgnoreCase("PUTSIZE")) return SVM_CALLSYS.P_PUTSIZE;
		if(s.equalsIgnoreCase("PUTHEX")) return SVM_CALLSYS.P_PUTHEX;
		if(s.equalsIgnoreCase("PUTFIX")) return SVM_CALLSYS.P_PUTFIX;
		if(s.equalsIgnoreCase("PUTFIX2")) return SVM_CALLSYS.P_PUTFIX2;
		if(s.equalsIgnoreCase("PTLFIX")) return SVM_CALLSYS.P_PTLFIX;
		if(s.equalsIgnoreCase("PTLFIX2")) return SVM_CALLSYS.P_PTLFIX2;
		if(s.equalsIgnoreCase("PTREAL")) return SVM_CALLSYS.P_PTREAL;
		if(s.equalsIgnoreCase("PTREAL2")) return SVM_CALLSYS.P_PTREAL2;
		if(s.equalsIgnoreCase("PLREAL")) return SVM_CALLSYS.P_PLREAL;
		if(s.equalsIgnoreCase("PLREAL2")) return SVM_CALLSYS.P_PLREAL2;
		if(s.equalsIgnoreCase("PTFRAC")) return SVM_CALLSYS.P_PTFRAC;
		if(s.equalsIgnoreCase("PTSIZE")) return SVM_CALLSYS.P_PTSIZE;
		if(s.equalsIgnoreCase("PTOADR")) return SVM_CALLSYS.P_PTOADR;
		if(s.equalsIgnoreCase("PTOADR2")) return SVM_CALLSYS.P_PTOADR2;
		if(s.equalsIgnoreCase("PTAADR")) return SVM_CALLSYS.P_PTAADR;
		if(s.equalsIgnoreCase("PTAADR2")) return SVM_CALLSYS.P_PTAADR2;
		if(s.equalsIgnoreCase("PTGADR")) return SVM_CALLSYS.P_PTGADR;
		if(s.equalsIgnoreCase("PTGADR2")) return SVM_CALLSYS.P_PTGADR2;
		if(s.equalsIgnoreCase("PTPADR")) return SVM_CALLSYS.P_PTPADR;
		if(s.equalsIgnoreCase("PTPADR2")) return SVM_CALLSYS.P_PTPADR2;
		if(s.equalsIgnoreCase("PTRADR")) return SVM_CALLSYS.P_PTRADR;
		if(s.equalsIgnoreCase("PTRADR2")) return SVM_CALLSYS.P_PTRADR2;
		if(s.equalsIgnoreCase("DRAWRP")) return SVM_CALLSYS.P_DRAWRP;
		if(s.equalsIgnoreCase("DATTIM")) return SVM_CALLSYS.P_DATTIM;
		if(s.equalsIgnoreCase("LOWTEN")) return SVM_CALLSYS.P_LOWTEN;
		if(s.equalsIgnoreCase("DCMARK")) return SVM_CALLSYS.P_DCMARK;
		if(s.equalsIgnoreCase("RSQROO")) return SVM_CALLSYS.P_RSQROO;
		if(s.equalsIgnoreCase("SQROOT")) return SVM_CALLSYS.P_SQROOT;
		if(s.equalsIgnoreCase("RLOGAR")) return SVM_CALLSYS.P_RLOGAR;
		if(s.equalsIgnoreCase("LOGARI")) return SVM_CALLSYS.P_LOGARI;
		if(s.equalsIgnoreCase("RLOG10")) return SVM_CALLSYS.P_RLOG10;
		if(s.equalsIgnoreCase("DLOG10")) return SVM_CALLSYS.P_DLOG10;
		if(s.equalsIgnoreCase("REXPON")) return SVM_CALLSYS.P_REXPON;
		if(s.equalsIgnoreCase("EXPONE")) return SVM_CALLSYS.P_EXPONE;
		if(s.equalsIgnoreCase("RSINUS")) return SVM_CALLSYS.P_RSINUS;
		if(s.equalsIgnoreCase("SINUSR")) return SVM_CALLSYS.P_SINUSR;
		if(s.equalsIgnoreCase("RCOSIN")) return SVM_CALLSYS.P_RCOSIN;
		if(s.equalsIgnoreCase("COSINU")) return SVM_CALLSYS.P_COSINU;
		if(s.equalsIgnoreCase("RTANGN")) return SVM_CALLSYS.P_RTANGN;
		if(s.equalsIgnoreCase("TANGEN")) return SVM_CALLSYS.P_TANGEN;
		if(s.equalsIgnoreCase("RCOTAN")) return SVM_CALLSYS.P_RCOTAN;
		if(s.equalsIgnoreCase("COTANG")) return SVM_CALLSYS.P_COTANG;
		if(s.equalsIgnoreCase("RARTAN")) return SVM_CALLSYS.P_RARTAN;
		if(s.equalsIgnoreCase("ARCTAN")) return SVM_CALLSYS.P_ARCTAN;
		if(s.equalsIgnoreCase("RARCOS")) return SVM_CALLSYS.P_RARCOS;
		if(s.equalsIgnoreCase("ARCCOS")) return SVM_CALLSYS.P_ARCCOS;
		if(s.equalsIgnoreCase("RARSIN")) return SVM_CALLSYS.P_RARSIN;
		if(s.equalsIgnoreCase("ARCSIN")) return SVM_CALLSYS.P_ARCSIN;
		if(s.equalsIgnoreCase("RATAN2")) return SVM_CALLSYS.P_RATAN2;
		if(s.equalsIgnoreCase("ATAN2")) return SVM_CALLSYS.P_ATAN2;
		if(s.equalsIgnoreCase("RSINH")) return SVM_CALLSYS.P_RSINH;
		if(s.equalsIgnoreCase("SINH")) return SVM_CALLSYS.P_SINH;
		if(s.equalsIgnoreCase("RCOSH")) return SVM_CALLSYS.P_RCOSH;
		if(s.equalsIgnoreCase("COSH")) return SVM_CALLSYS.P_COSH;
		if(s.equalsIgnoreCase("RTANH")) return SVM_CALLSYS.P_RTANH;
		if(s.equalsIgnoreCase("TANH")) return SVM_CALLSYS.P_TANH;
		if(s.equalsIgnoreCase("BEGDEB")) return SVM_CALLSYS.P_BEGDEB;
		if(s.equalsIgnoreCase("ENDDEB")) return SVM_CALLSYS.P_ENDDEB;
		if(s.equalsIgnoreCase("BEGTRP")) return SVM_CALLSYS.P_BEGTRP;
		if(s.equalsIgnoreCase("ENDTRP")) return SVM_CALLSYS.P_ENDTRP;
		if(s.equalsIgnoreCase("GTPADR")) return SVM_CALLSYS.P_GTPADR;
		if(s.equalsIgnoreCase("GTOUTM")) return SVM_CALLSYS.P_GTOUTM;
		if(s.equalsIgnoreCase("GTLNID")) return SVM_CALLSYS.P_GTLNID;
		if(s.equalsIgnoreCase("GTLNO")) return SVM_CALLSYS.P_GTLNO;
		if(s.equalsIgnoreCase("BRKPNT")) return SVM_CALLSYS.P_BRKPNT;
		if(s.equalsIgnoreCase("STMNOT")) return SVM_CALLSYS.P_STMNOT;
		if(s.equalsIgnoreCase("DMPOBJ")) return SVM_CALLSYS.P_DMPOBJ;

		// KNOWN 
		if(s.equalsIgnoreCase("MODULO")) return SVM_CALLSYS.P_MODULO;
		if(s.equalsIgnoreCase("RADDEP")) return SVM_CALLSYS.P_RADDEP;
		if(s.equalsIgnoreCase("DADDEP")) return SVM_CALLSYS.P_DADDEP;
		if(s.equalsIgnoreCase("RSUBEP")) return SVM_CALLSYS.P_RSUBEP;
		if(s.equalsIgnoreCase("DSUBEP")) return SVM_CALLSYS.P_DSUBEP;
		if(s.equalsIgnoreCase("IIPOWR")) return SVM_CALLSYS.P_IIPOWR;
		if(s.equalsIgnoreCase("RIPOWR")) return SVM_CALLSYS.P_RIPOWR;
		if(s.equalsIgnoreCase("RRPOWR")) return SVM_CALLSYS.P_RRPOWR;
		if(s.equalsIgnoreCase("RDPOWR")) return SVM_CALLSYS.P_RDPOWR;
		if(s.equalsIgnoreCase("DIPOWR")) return SVM_CALLSYS.P_DIPOWR;
		if(s.equalsIgnoreCase("DRPOWR")) return SVM_CALLSYS.P_DRPOWR;
		if(s.equalsIgnoreCase("DDPOWR")) return SVM_CALLSYS.P_DDPOWR;
		Util.IERR(""+s);
		return 0;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Global.ATTR_OUTPUT_TRACE) System.out.println("ProfileDescr.Write: " + this);
		if(! CALL.USE_FRAME_ON_STACK) {
			DSEG.write(oupt);
		}
		oupt.writeKind(kind);
		tag.write(oupt);
		oupt.writeShort(pKind);
//		oupt.writeString(DSEG.ident);
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
		if(Global.ATTR_INPUT_TRACE) System.out.println("BEGIN ProfileDescr.Read: " + prf);
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
		
		if(Global.ATTR_INPUT_TRACE) System.out.println("ProfileDescr.Read: " + prf);
//		prf.print("   ");
//		Util.IERR("");
		return prf;
	}



}
