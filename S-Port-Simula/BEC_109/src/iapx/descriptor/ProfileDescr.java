package iapx.descriptor;

import static iapx.util.Global.*;

import java.io.IOException;
import java.util.Vector;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.Kind;
import iapx.PKind;
import iapx.util.Display;
import iapx.util.Option;
import iapx.util.Scode;
import iapx.util.Tag;
import iapx.util.Type;
import iapx.util.Util;
import iapx.value.MemAddr;

//Record ProfileDescr:Descriptor;  // K_ProfileDescr     SIZE = (6+npar*2) align 4
//begin range(0:MaxByte) npar;     // No.of parameters
//      range(0:1) WithExit;
//      range(0:MaxParByte) nparbyte;
//      range(0:P_max) Pkind;
//      infix(ParamSpec) Par(0);   // Parameter Specifications
//end;
public class ProfileDescr extends Descriptor {
//	int npar;     // No.of parameters
//	boolean WithExit;
//	int nparbyte;
//	int Pkind;
//	Vector<ParamSpec> par;   // Parameter Specifications

	private static final boolean DEBUG = false;

//	int tag;  // Inherited
//	int type; // Inherited
	boolean withExit;
	public int nparWords;
	public PKind pKind;
	public Vector<ParamSpec> par;   // Parameter Specifications
	
	Parameter export;          // GENERATE_DEBUG_CODE
	Tag bodyTag;               // GENERATE_DEBUG_CODE
	String nature;             // GENERATE_DEBUG_CODE
	String ident;              // GENERATE_DEBUG_CODE
	Vector<Parameter> imports; // GENERATE_DEBUG_CODE
	
	public int npar() { return par.size(); }
	
	public ProfileDescr() {
		this.kind = Kind.K_ProfileDescr;
		this.par = new Vector<ParamSpec>();
		if(Option.GENERATE_DEBUG_CODE) imports = new Vector<Parameter>();
	}
	

	//%title ***   I n p u t   P r o f i l e   ***
	public static void inProfile(PKind defkind) { //; export ref(ProfileDescr) pr;
		Vector<Parameter> imports = new Vector<Parameter>(); // GENERATE_DEBUG_CODE

		Tag tag = Tag.inTag();
//		int kind = defkind;
//		int ExpType = T_VOID;
		if(DEBUG) IO.println("\nProfileDescr.inProfile: BEGIN PARSING: " + tag);
		
//      nbyte = 0; rut = none; nCnt = 0; segid.val = 0;
		PKind pKind = PKind.P_NULL;
		RoutineDescr rut = null;
		
		if(Scode.accept(Scode.S_EXTERNAL)) {
			Util.IERR("NOT IMPL");
//            pKind = P_EXTERNAL;
//            InXtag(%rtag%);     -- Body'Tag
//            nid = inSymb;        -- Nature'String
//            xs = InString;       -- xid'String
		} else if(Scode.accept(Scode.S_INTERFACE)) {
			Scode.inString(); pKind = PKind.P_INTERFACE;
		} else if(Scode.accept(Scode.S_KNOWN)) {
			Tag rtag =Tag.inTag();
			String xid = Scode.inString();
			rut = new RoutineDescr(rtag, null, MemAddr.ofExtAddr(xid, 0));
			pKind = PKind.getKnownKind(xid);
//			Util.IERR("SJEKK DETTE !!!");
		} else if(Scode.accept(Scode.S_SYSTEM)) {
			Tag rtag = Tag.inTag();
//			Util.IERR("SJEKK DETTE ÅSSÅ !!!");
//	         xid = InExtr('E',EnvCSEG);
			String xid = Scode.inString();
//	         rut = NEWOBJ(K_IntRoutine,size(IntDescr));
//	         if PsysKind=P_KNL
//	         then rut.adr.kind = knladr; rut.adr.knlx = xid
//	         else rut.adr.kind = extadr; rut.adr.smbx = xid endif;
//	         rut.adr.rela.val = 0;
//	 %-E     rut.adr.sbireg = 0;
//	         rut.adr.sibreg = NoIBREG;
//	         if PsysKind <> 0 then kind = PsysKind
//	 -- ????????  rut.adr.segid.val = 0  ?????????????????
//	         else --- Search for inline index ---
			
			rut = new RoutineDescr(rtag, null, MemAddr.ofExtAddr(xid, 0));
			pKind = PKind.getSystemKind(xid);
		}
		
		ProfileDescr prf = new ProfileDescr(); prf.tag = tag; prf.pKind = pKind;
		int nparWords = 0;
		while(Scode.accept(Scode.S_IMPORT)) {
			Tag ptag = Tag.inTag();
			Type type = Scode.inType(); int size = type.getSize();
			if(DEBUG) IO.println("\nProfileDescr.inProfile: BEGIN IMPORT: " + ptag + ", type=" + type + ", size=" + size);
			
			int count = 1;
			if(Scode.accept(Scode.S_REP)) {
				count = Scode.inNumber();
				if(count == 0) { Util.IERR("Illegal 'REP 0'"); count = 1; }
			}
			Parameter par = new Parameter(ptag, type,nparWords);
			if(Option.GENERATE_DEBUG_CODE) {
				imports.add(par);
			}
			prf.par.add(new ParamSpec(type, count));
			if(size == 0) Util.IERR("Illegal Type on Parameter");
			nparWords = nparWords + size;
			if(count > 1) {
				nparWords = nparWords+(count*size)-size;
				Util.IERR("NOT IMPL");
//%+C           if nCnt >= 4
//%+C           then IERR("MINUT: Too many rep-params"); nCnt = 3 endif;
//%+C           if count.val>255 then IERR("MINUT: too large count") endif;
//              Pno(nCnt) = npar; Cnt(nCnt) = count.val; nCnt = nCnt+1;
			}
			if(DEBUG) IO.println("ProfileDescr.inProfile: IMPORT: " + ptag + ", type=" + type + ", size=" + size + ", rela=" + par.rela);
			Display.add(par);
		}

		Parameter export = null;
		if(Scode.accept(Scode.S_EXIT)) {
			Util.IERR("NOT IMPL");
//      then v = NEWOBJ(K_Parameter,size(LocDescr)); v.type = T_PADDR;
//           v.rela = AllignFac; -- Offset of return address in stack head
//           InTag(%ptag%); wxt = qEXIT; IntoDisplay(v,ptag);
//           InputInstr;
			prf.withExit = true;
//      elsif CurInstr=S_EXPORT
		} else if(Scode.accept(Scode.S_EXPORT)) {
			Tag expTag = Tag.inTag();
			Type expType = Scode.inType(); int size = expType.getSize();
			export = new Parameter(expTag, expType, nparWords);
			if(Option.GENERATE_DEBUG_CODE) export.tag = expTag;
			Display.add(export);
		}
		
		if(! Scode.accept(Scode.S_ENDPROFILE)) Util.IERR("Missing ENDPROFILE");

		prf.nparWords = nparWords;
		if(Option.GENERATE_DEBUG_CODE) {
			prf.tag = tag;
			prf.export = export;
			prf.imports = imports;
//			prf.bodyTag = bodyTag;     // GENERATE_DEBUG_CODE
//			String nature;             // GENERATE_DEBUG_CODE
//			String ident;              // GENERATE_DEBUG_CODE
		}
		Display.add(prf);
		if(rut != null) {
			if(export != null) {
				rut.type = export.type; //ExpType;
				prf.type = rut.type;
			}
			Display.add(rut);
		}
		
		if(Option.SCODE_INPUT_DUMP) prf.print("");
	}
	

	@Override
	public void print(final String indent) {
		String profile = "\nPROFILE " + tag + ' ' + pKind;
		if(bodyTag != null) profile += " " + bodyTag;
		if(nature != null) profile += " " + nature;
		if(ident != null) profile += " " + " \"" + ident + '"';
		
		IO.println(indent + profile);
		if(imports != null) for(Parameter par:imports) IO.println(indent + "   IMPORT " + par);
		if(export != null) IO.println(indent + "   EXPORT " + export);
		
//		if(returSlot != null) IO.println(indent + "   ReturSlot = " + returSlot);
//		if(DSEG != null) IO.println(indent + "   DSEG = " + DSEG);
//		if(DSEG != null) DSEG.dump("ProfileDescr.print: ");
		IO.println(indent + "ENDPROFILE"); //  FrameHeadSize="+frameSize);	
//		Util.IERR("");
	}
	

	@Override
	public String toString() {
		String s = "ProfileDescr: type=" + type + ", nparWords=" + nparWords + ", npar=" + npar();
//		if(pntmap != 0) s = s + ", pntmap=" + ???
		return s;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

//	boolean withExit;
//	int nparWords;
//	int pKind;
//	Vector<ParamSpec> par;   // Parameter Specifications
//	
//	int tag;                   // GENERATE_DEBUG_CODE
//	Parameter export;          // GENERATE_DEBUG_CODE
//	int bodyTag;               // GENERATE_DEBUG_CODE
//	String nature;             // GENERATE_DEBUG_CODE
//	String ident;              // GENERATE_DEBUG_CODE
//	Vector<Parameter> imports; // GENERATE_DEBUG_CODE
	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		super.write(oupt);
		oupt.writeBoolean(withExit);
		oupt.writeShort(nparWords);
//		oupt.writeShort(pKind);
		pKind.write(oupt);
		oupt.writeShort(par.size());
		for(ParamSpec p:par) p.write(oupt);
		if(Option.GENERATE_DEBUG_CODE) {
			if(export != null) {
				oupt.writeBoolean(true);
				export.write(oupt);
			} else oupt.writeBoolean(false);
			Tag.writeTag(bodyTag, oupt);
			oupt.writeString(nature);
			oupt.writeString(ident);
			oupt.writeShort(imports.size());
			for(Parameter p:imports) p.write(oupt);
		}
	}

//	private ProfileDescr() {
//		this.kind = Kind.K_ProfileDescr;
//		this.par = new Vector<ParamSpec>();
//		if(Option.GENERATE_DEBUG_CODE) imports = new Vector<Parameter>();
//	}

	public static ProfileDescr read(AttributeInputStream inpt) throws IOException {
		ProfileDescr prf = new ProfileDescr();
		ProfileDescr.read(prf, inpt);
		if(Option.ATTR_INPUT_DUMP) prf.print("");
//		Util.IERR("NOT IMPL");
		return prf;
	}

	public static void read(ProfileDescr prf, AttributeInputStream inpt) throws IOException {
		Descriptor.read(prf, inpt);
		prf.withExit = inpt.readBoolean();
		if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: withExit="+prf.withExit);
		prf.nparWords = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: nparWords="+prf.nparWords);
		prf.pKind = PKind.read(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: pKind="+prf.pKind);
		int npar = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: npar="+npar);
		for(int i=0;i<npar;i++) {
			ParamSpec spec = ParamSpec.read(inpt);
			prf.par.add(spec);
			if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: tag="+prf.tag);
		}
		if(Option.GENERATE_DEBUG_CODE) {
			boolean present = inpt.readBoolean(); // export
			if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: present="+present);
			if(present) {
				Kind kind = Kind.read(inpt);
				if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: kind="+kind);
				if(kind != Kind.K_Parameter) Util.IERR("");
				prf.export = Parameter.read(inpt);
			}
			prf.bodyTag = Tag.readTag(inpt);
			if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: bodyTag="+prf.bodyTag);
			prf.nature = inpt.readString();
			if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: nature="+prf.nature);
			prf.ident = inpt.readString();
			if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: ident="+prf.ident);
			npar = inpt.readShort();
			if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: npar="+npar);
			for(int i=0;i<npar;i++) {
				Kind kind = Kind.read(inpt);
				if(Option.ATTR_INPUT_TRACE) IO.println("ProfileDescr.read: kind="+kind);
				if(kind != Kind.K_Parameter) Util.IERR("");
				prf.imports.add(Parameter.read(inpt));
			}			
		}
	}


}
