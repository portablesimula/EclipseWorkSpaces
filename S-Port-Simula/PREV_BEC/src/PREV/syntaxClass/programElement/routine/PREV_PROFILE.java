package PREV.syntaxClass.programElement.routine;

import java.io.IOException;
import java.util.Vector;

import PREV.syntaxClass.SyntaxClass;
import PREV.syntaxClass.programElement.PREV_Variable;
import PREV.syntaxClass.programElement.ProgramElement;
import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class PREV_PROFILE extends ProgramElement {
	int profileTag;

	int kind;      // peculiar
	public int bodyTag;   // peculiar
	String nature; // peculiar
	public String ident;  // peculiar
	
	public DataSegment DSEG;
//	public Vector<IMPORT> imports;
//	EXIT exit;
//	EXPORT export;
	public Vector<PREV_Variable> imports;
	public PREV_Variable exit;
	public PREV_Variable export;

	public int type; // Export type ?????
	public int npar() {
		return(imports.size());
	}
	
	public PREV_PROFILE() {
		imports = new Vector<PREV_Variable>();
		parse();
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
	public void parse() {
		profileTag = Scode.inTag();
		
//		Global.Display.set(profileTag, this);
		
		
		if(Scode.nextByte() == Scode.S_KNOWN) {
			Scode.inputInstr();
			kind = Scode.S_KNOWN;
			bodyTag = Scode.inTag();
			ident = Scode.inString();  // kid'String
		} else if(Scode.nextByte() == Scode.S_SYSTEM) {
			Scode.inputInstr();
			kind = Scode.S_SYSTEM;
			bodyTag = Scode.inTag();
			ident = Scode.inString();  // sid'String
		} else if(Scode.nextByte() == Scode.S_EXTERNAL) {
			Scode.inputInstr();
			kind = Scode.S_EXTERNAL;
			bodyTag = Scode.inTag();
			nature = Scode.inString(); // Nature'String
			ident = Scode.inString();  // xid'String
		} else if(Scode.nextByte() == Scode.S_INTERFACE) {
			Scode.inputInstr();
			kind = Scode.S_INTERFACE;
			ident = Scode.inString();  // pid'String
		}
		
//		String pID = "Name";//Scode.TAGTABLE[profileTag];
		String pID = Scode.edTag(profileTag);
		DSEG = new DataSegment("DSEG_" + Global.moduleID + '_' + pID, Kind.K_SEG_DATA);
		while(Scode.accept(Scode.S_IMPORT)) {
			imports.add(PREV_Variable.ofIMPORT(DSEG));
		}
		if(Scode.accept(Scode.S_EXIT)) exit = PREV_Variable.ofEXIT(DSEG);
		else if(Scode.accept(Scode.S_EXPORT)) export = PREV_Variable.ofEXPORT(DSEG);
		Scode.expect(Scode.S_ENDPROFILE);
//		if(exit == null) exit = Variable.ofRETUR(DSEG);
		
		if(Global.SCODE_INPUT_TRACE) {
			System.out.println("-------------------------------------------------- BEGIN PRINT PROFILE Definition");
			printTree(2);
			System.out.println("-------------------------------------------------- ENDOF PRINT PROFILE Definition");
		}
//		DSEG.dump();
//		if(Scode.inputTrace > 3) printTree(2);
//		Util.IERR("");
	}

	@Override
	public void printTree(final int indent) {
		String profile = "PROFILE " + Scode.edTag(profileTag);
		switch(kind) {
		case Scode.S_KNOWN ->     profile += " KNOWN "     + Scode.edTag(bodyTag) + " \"" + ident + '"';
		case Scode.S_SYSTEM ->    profile += " SYSTEM "    + Scode.edTag(bodyTag) + " " + ident;
		case Scode.S_EXTERNAL ->  profile += " EXTERNAL "  + Scode.edTag(bodyTag) + " " + nature + " " + ident;
		case Scode.S_INTERFACE -> profile += " INTERFACE " + ident;
		}
		sLIST(indent, profile);
		for(PREV_Variable imprt:imports) sLIST(indent + 1, imprt.toString());
		if(exit != null)   sLIST(indent + 1, exit.toString());
		if(export != null) sLIST(indent + 1, export.toString());
		DSEG.dump("");
		sLIST(indent, "ENDPROFILE");		
	}
	
	public String toString() {
		String profile = "PROFILE " + Scode.edTag(profileTag);
		switch(kind) {
			case Scode.S_KNOWN ->     profile += " KNOWN " + Scode.edTag(bodyTag) + " \"" + ident + '"';
			case Scode.S_SYSTEM ->    profile += " SYSTEM " + Scode.edTag(bodyTag) + " " + ident;
			case Scode.S_EXTERNAL ->  profile += " EXTERNAL " + Scode.edTag(bodyTag) + " " + nature + " " + ident;
			case Scode.S_INTERFACE -> profile += " INTERFACE " + ident;
		}
//		for(Variable imprt:imports) profile += " " + imprt;
//		if(exit != null)   profile += " " + exit;
//		if(export != null) profile += " " + export;
		profile += " ...";
		return profile;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	public PREV_PROFILE(AttributeInputStream inpt) throws IOException {
		imports = new Vector<PREV_Variable>();
		profileTag = inpt.readTag();
		
		String ident = inpt.readString();
		DSEG = (DataSegment) Segment.lookup(ident);
		if(DSEG == null) Util.IERR("NEW PROFILE: Unknown segment " + ident);

		inpt.readInstr();
//		System.out.println("NEW PROFILE(1): inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		switch(inpt.curinstr) {
		case Scode.S_KNOWN ->     { kind = Scode.S_KNOWN;     bodyTag = inpt.readTag(); ident = inpt.readString(); inpt.readInstr(); }
		case Scode.S_SYSTEM ->    { kind = Scode.S_SYSTEM;    bodyTag = inpt.readTag(); ident = inpt.readString(); inpt.readInstr(); }
		case Scode.S_EXTERNAL ->  { kind = Scode.S_EXTERNAL;  bodyTag = inpt.readTag(); nature = inpt.readString(); ident = inpt.readString(); inpt.readInstr(); }
		case Scode.S_INTERFACE -> { kind = Scode.S_INTERFACE; ident = inpt.readString(); inpt.readInstr(); }
		}
//		System.out.println("NEW PROFILE(2): inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		while(inpt.curinstr == Scode.S_IMPORT) {
			imports.add(PREV_Variable.readObject(inpt, Scode.S_IMPORT));
			inpt.readInstr();
//			System.out.println("NEW PROFILE(3): inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		}
		if(inpt.curinstr == Scode.S_EXIT)   { exit = PREV_Variable.readObject(inpt, Scode.S_EXIT); inpt.readInstr(); }
		if(inpt.curinstr == Scode.S_EXPORT) { export = PREV_Variable.readObject(inpt, Scode.S_EXPORT); inpt.readInstr(); }
		
//		System.out.println("NEW PROFILE(4): inpt.curinstr="+Scode.edInstr(inpt.curinstr));
		if(inpt.curinstr != Scode.S_ENDPROFILE) Util.IERR("IMPOSSIBLE: " + Scode.edTag(inpt.curinstr));

		if(Global.ATTR_INPUT_TRACE) this.printTree(2);
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.TRACE_OUTPUT("BEGIN Write PROFILE: " + Scode.edTag(profileTag)); // + ", Declared in: " + declaredIn);
		// Write Segments
		DSEG.write(oupt);
		//CSEG.write(oupt);

		oupt.writeKind(Scode.S_PROFILE); // Mark: This is a Profile
		oupt.writeTag(profileTag);
		
		oupt.writeString((DSEG==null)?null:DSEG.ident);

		switch(kind) {
			case Scode.S_KNOWN ->     { oupt.writeInstr(Scode.S_KNOWN); oupt.writeTag(bodyTag); oupt.writeString(ident); }
			case Scode.S_SYSTEM ->    { oupt.writeInstr(Scode.S_SYSTEM); oupt.writeTag(bodyTag); oupt.writeString(ident); }
			case Scode.S_EXTERNAL ->  { oupt.writeInstr(Scode.S_EXTERNAL); oupt.writeTag(bodyTag); oupt.writeString(nature); oupt.writeString(ident); }
			case Scode.S_INTERFACE -> { oupt.writeInstr(Scode.S_INTERFACE); oupt.writeString(ident); }
		}
		for(PREV_Variable imprt:imports) imprt.write(oupt);
		if(exit != null)   exit.write(oupt);
		if(export != null) export.write(oupt);
		oupt.writeInstr(Scode.S_ENDPROFILE);
//		if("PUTSTR".equals(ident)) {
//			printTree(2);
//			Util.IERR("");
//		}
	}

	public static SyntaxClass readObject(AttributeInputStream inpt) throws IOException {
		return new PREV_PROFILE(inpt);
	}


}
