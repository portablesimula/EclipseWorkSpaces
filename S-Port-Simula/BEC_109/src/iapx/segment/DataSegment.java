package iapx.segment;

import static iapx.util.Global.*;

import java.io.IOException;
import java.util.Vector;

import iapx.Kind;
import iapx.descriptor.AttrDescr;
import iapx.descriptor.ConstDescr;
import iapx.util.Display;
import iapx.util.Option;
import iapx.util.Scode;
import iapx.util.Tag;
import iapx.util.Type;
import iapx.util.Util;
import iapx.value.IntegerValue;
import iapx.value.MemAddr;
//import bec.AttributeInputStream;
//import bec.AttributeOutputStream;
//import bec.descriptor.Kind;
//import bec.util.Global;
//import bec.util.Scode;
//import bec.util.Type;
//import bec.util.Util;
//import bec.value.IntegerValue;
//import bec.value.TextValue;
import iapx.value.Value;
//import bec.value.dataAddress.SegmentAddress;

public class DataSegment extends Segment {
	Vector<Value> values;
	Vector<String> comment;
	private int guard = -1;
	
	private static final boolean DEBUG = false;

	public DataSegment(String ident, Kind kSegData) {
		super(ident, kSegData);
//		IO.println("NEW DataSegment: " + this);
//		Thread.dumpStack();
		this.ident = ident.toUpperCase();
		this.segmentKind = kSegData;
		values = new Vector<Value>();
		comment = new Vector<String>();
	}
	
	public void addGuard(int ofst) {
		guard = ofst;
	}
	
//	public SegmentAddress ofOffset(int ofst) {
//		return new SegmentAddress(this.ident,ofst);
//	}
	
	public MemAddr nextAddress() {
//		return DataAddress.ofSegAddr(this,values.size());
//		return new SegmentAddress(this.ident, values.size());
		return MemAddr.ofSegAddr(this.ident, values.size());
	}
	
	public int size() {
		return values.size();
	}
	
//	public void store(int index, Value value) {
////		IO.println("DataSegment.store: " + this + "["+index+"] = "+value);
//		if(index == guard) Util.IERR("FATAL ERROR: Attempt to change Guarded location: "+(new SegmentAddress(this.ident, index))+" from "+values.get(index)+" to "+value);
//		values.set(index, value);
//	}
//	
//	public Value load(int index) {
////		IO.println("DataSegment.load: "+this+", index="+index);
//		try {
//			return values.get(index);
//		} catch(Exception e) {
//			e.printStackTrace();
//			IO.println("DataSegment.load: FAILED - SE PÅ DETTE SEINERE !! e="+e);
////			this.dump("DataSegment.load: FAILED: " + e + " ");
////			Util.IERR("DataSegment.load: FAILED");
//			return null;
//		}
//	}
	
	public MemAddr emitInt(int val, String comment) {
		MemAddr addr = nextAddress();
		emit(IntegerValue.of(Type.T_INT, val), comment);
		return addr;
	}
	
	public MemAddr emitZero(int n, String comment) {
		MemAddr addr = nextAddress();
		for(int i=0;i<n;i++) emit(IntegerValue.of(Type.T_INT, 0), comment);
		return addr;
	}
	
	public MemAddr emit(final Value value,final String cmnt) {
		MemAddr addr = nextAddress();
		values.add(value);
		comment.add(cmnt);
		if(Option.PRINT_GENERATED_SVM_DATA)
			listData("                                 ==> ", value, cmnt, addr.rela);
		return addr;
	}

	private void listData(String indent, Value value,String cmnt, int idx) {
		String line = ident + "[" + idx + "] ";
		while(line.length() < 8) line = " " +line;
		String val = ""+value;
		while(val.length() < 50) val = val + ' ';
		IO.println(indent + line + val + "   " + cmnt);
		
	}

	
	public void emitRepValue(ConstDescr v, int nbyte) {
//	begin range(0:AllignFac) n;
//	%+E   infix(wWORD) ofst32;
//	%+E   ofst32.HighWord.val = DBUF.ofstHI
//	%+E   ofst32.LowWord.val = DBUF.ofstLO
//	%+E   n = AllignDiff(%ofst32.val+DBUF.nxt%);
//	      if n<>0 then EmitZero(n) endif;
//	%+A   if asmgen then AsmSection(DSEGID);
//	%+AD  elsif listsw>0 then AsmSection(DSEGID)
//	%+A   endif;
//	      if v.adr.kind=fixadr then DefDataFixup(v.adr) endif;
//	      v.adr.kind = segadr;
//	%-E   v.adr.rela.val = DBUF.ofst.val+DBUF.nxt;
//	%+E   ofst32.HighWord.val = DBUF.ofstHI
//	%+E   ofst32.LowWord.val = DBUF.ofstLO
//	%+E   v.adr.rela.val = ofst32.val+DBUF.nxt;
//	%-E   v.adr.sbireg = 0;
//	%+E   v.adr.sibreg = NoIBREG;
//	      v.adr.segmid = DSEGID;
		treatValue(nbyte);
	}


//%title ***   T r e a t   V a l u e   I t e m   ***

	private void treatValue(int nbyte) {
//begin infix(MemAddr) addr; ref(Descriptor) d,lab,rut;
//      range(0:MaxType) type;
//      infix(WORD) tag,rtag,lng,smbx;
//      range(0:MaxWord) ofst,i,rest; ref(RecordDescr) rec;
//      ref(LocDescr) atr; infix(ValueItem) itm;
//%+D   infix(String) s;
		if(Option.listq2 > 1) IO.println("TreatValue(" + nbyte + ')');

//NXT:
		LOOP: while(true) {
			switch(Scode.nextByte()) {
			case S_TEXT: Scode.inputInstr();
				Util.IERR("NOT IMPL: " + Scode.curinstr);
//%+D        lng = InputNumber;
//%-D        InNumber(%lng%);
//           repeat rest = sBufLen-SBUF.nxt while lng.val >= rest
//           do
//%+D           if InputTrace <> 0
//%+D           then s.nchr = rest; s.chradr = @SBUF.chr(SBUF.nxt);
//%+D                EdChar(inptrace,'"'); Ed(inptrace,s);
//%+D                EdChar(inptrace,'"'); ITRC("LongStringPart");
//%+D           endif;
//              EmitData(rest,@SBUF.byt(SBUF.nxt));
//              SBUF.nxt = SBUF.nxt+rest; InSbuffer; lng.val = lng.val-rest;
//           endrepeat;
//           if lng.val > 0
//           then
//%+D             if InputTrace <> 0
//%+D             then s.nchr = lng.val; s.chradr = @SBUF.chr(SBUF.nxt);
//%+D                  EdChar(inptrace,'"'); Ed(inptrace,s);
//%+D                  EdChar(inptrace,'"'); ITRC("LongString");
//%+D             endif;
//                EmitData(lng.val,@SBUF.byt(SBUF.nxt));
//                SBUF.nxt = SBUF.nxt+lng.val;
//           endif;
      case S_C_INT: Scode.inputInstr();
//           itm.int = inint; DataSpace(%4%);
//           if nbyte=1 then Emit1Data(%itm.byt%)
//           elsif nbyte=2 then Emit2Data(itm.wrd)
//           elsif nbyte=4 then EmitData(4,@itm) endif;
		int val = Scode.inNumber();
		DSEGID.emitInt(val, "S_C_INT: ");
		break;
      case S_C_REAL: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           itm.rev = inreal;  EmitData(4,@itm)
      case S_C_LREAL: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           itm.lrv = inlreal; EmitData(8,@itm)
      case S_C_CHAR:   Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//%+D        itm.byt = InputByte;
//%-D        InByte(%itm.byt%);
//           DataSpace(%1%); Emit1Data(%itm.byt%)
      case S_C_SIZE: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           TypeLength = 0; type = intype;
//           if type < T_Max
//           then TypeLength = TTAB(type).nbyte endif;
//           Emit2Data(TypeLength);
//%+E        Emit2Zero;
      case S_TRUE:  Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           DataSpace(%1%); Emit1Data(%255%)
      case S_FALSE: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           DataSpace(%1%); Emit1Data(%0%)
      case S_C_AADDR: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           InTag(%tag%); atr = DISPL(tag.HI).elt(tag.LO);
//           Emit2Data(atr.rela);
//%+E        Emit2Zero;
      case S_C_PADDR: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           InTag(%tag%); lab = DISPL(tag.HI).elt(tag.LO);
//           if lab.kind <> K_IntLabel
//           then addr.kind = extadr;
//%-E             addr.sbireg = 0;
//%+E             addr.sibreg = NoIBREG;
//                addr.rela.val = lab qua ExtDescr.adr.rela;
//                addr.smbx = lab qua ExtDescr.adr.smbx;
//                EmitAddrData(addr);
//           else EmitAddrData(lab qua IntDescr.adr) endif;
      case S_C_RADDR: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           InTag(%tag%); rut = DISPL(tag.HI).elt(tag.LO);
//           if rut.kind=K_IntRoutine then addr = rut qua IntDescr.adr
//           else addr.kind = extadr;
//%-E             addr.sbireg = 0;
//%+E             addr.sibreg = NoIBREG;
//                addr.rela.val = rut qua ExtDescr.adr.rela;
//                addr.smbx = rut qua ExtDescr.adr.smbx;
//           endif;
//           addr.rela.val = addr.rela.val+3; EmitAddrData(addr);
      case S_NOSIZE,S_ANONE: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           Emit2Zero;
//%+E        Emit2Zero;
      case S_NOWHERE,S_NOBODY,S_ONONE: Scode.inputInstr();
		DSEGID.emitZero(1, "ONONE: ");
		break;
      case S_GNONE: Scode.inputInstr();
//           EmitZero(6);
//%+E        Emit2Zero;
		DSEGID.emitZero(2, "GNONE: ");
		break;
      case S_C_OADDR: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           InTag(%tag%); d = DISPL(tag.HI).elt(tag.LO);
//           if d.kind=K_GlobalVar
//           then addr = d qua IntDescr.adr
//                if addr.kind=0  -- No address attached (Const-spec)
//                then smbx.val = 0; addr = NewFixAdr(DSEGID,smbx);
//                     d qua IntDescr.adr = addr;
//                endif;
//           elsif d.kind=K_ExternVar
//           then addr.kind = extadr;
//%-E             addr.sbireg = 0;
//%+E             addr.sibreg = NoIBREG;
//                addr.rela.val = d qua ExtDescr.adr.rela;
//                addr.smbx = d qua ExtDescr.adr.smbx;
//           else IERR("MINUT: Illegal tag for C-OADDR") endif;
//           EmitAddrData(addr);
//      case S_C_GADDR: Scode.inputInstr();
//           InTag(%tag%); d = DISPL(tag.HI).elt(tag.LO);
//           if d.kind=K_GlobalVar
//           then addr = d qua IntDescr.adr
//                if addr.kind=0  -- No address attached (Const-spec)
//                then smbx.val = 0; addr = NewFixAdr(DSEGID,smbx);
//                     d qua IntDescr.adr = addr;
//                endif;
//           elsif d.kind=K_ExternVar
//           then addr.kind = extadr;
//%-E             addr.sbireg = 0;
//%+E             addr.sibreg = NoIBREG;
//                addr.rela.val = d qua ExtDescr.adr.rela;
//                addr.smbx = d qua ExtDescr.adr.smbx;
//           else IERR("MINUT: Illegal tag for C-GADDR") endif;
//%+E        Emit2Zero;
//           Emit2Zero; EmitAddrData(addr);
      case S_C_DOT: Scode.inputInstr();
		Util.IERR("NOT IMPL: " + Scode.curinstr);
//           ofst = 0;
//           repeat InTag(%tag%); atr = DISPL(tag.HI).elt(tag.LO);
//                  ofst = ofst+atr.rela; InputInstr;
//           while CurInstr=S_C_DOT do endrepeat;
//           if CurInstr=S_C_AADDR
//           then InTag(%tag%); atr = DISPL(tag.HI).elt(tag.LO);
//                ofst = ofst+atr.rela; Emit2Data(ofst);
//%+E             Emit2Zero;
//           elsif CurInstr=S_C_GADDR
//           then InTag(%tag%); d = DISPL(tag.HI).elt(tag.LO);
//                if d.kind=K_GlobalVar
//                then addr = d qua IntDescr.adr
//                     if addr.kind=0  -- No address attached (Const-spec)
//                     then smbx.val = 0; addr = NewFixAdr(DSEGID,smbx);
//                          d qua IntDescr.adr = addr;
//                     endif;
//                elsif d.kind=K_ExternVar
//                then addr.kind = extadr;
//%-E                  addr.sbireg = 0;
//%+E                  addr.sibreg = NoIBREG;
//                     addr.rela.val = d qua ExtDescr.adr.rela;
//                     addr.smbx = d qua ExtDescr.adr.smbx;
//                else IERR("MINUT: Illegal tag for C-GADDR") endif;
//                Emit2Data(ofst);
//%+E             Emit2Zero;
//                EmitAddrData(addr);
//           else IERR("Illegal termination of C-DOT value") endif;
      		case S_C_RECORD: Scode.inputInstr();
//           InTag(%rtag%); EmitStructValue(nbyte);
      			Tag rtag = Tag.inTag();
      			emitStructValue(nbyte);
      			break;
      		default: break LOOP; //otherwise goto E
			}
//      goto NXT;
		}
//E:
	}

	private void emitStructValue(int nbyte) {
//begin range(0:MaxType) atype; infix(WORD) atag; ref(LocDescr) atr;
//      infix(wWORD) rStart,aStart;
//%+E   infix(wWORD) ofst32;
		if(Option.listq2 > 1) IO.println("EmitStructValue(" + nbyte + ')');
//%+E   ofst32.HighWord.val = DBUF.ofstHI
//%+E   ofst32.LowWord.val = DBUF.ofstLO
//%+E   rStart.val = ofst32.val+DBUF.nxt;
		int startSize = size();
		
//      repeat InputInstr while CurInstr = Scode.S_ATTR
		while(Scode.accept(Scode.S_ATTR)) {
//      do InTag(%atag%); atr = DISPL(atag.HI).elt(atag.LO);
			Tag atag = Tag.inTag();
			AttrDescr atr = (AttrDescr) Display.lookup(atag);
//         TypeLength = 0; atype = intype;
			Type atype = Scode.inType(); int size = atype.getSize();
			
			if(Option.listq2 > 1) IO.println("DataSegment.emitStructValue: Attribute " + atr.type + " <== " + atype);
//         repeat
//%-E             aStart.val = DBUF.ofst.val+DBUF.nxt-rStart.val;
//%+E             ofst32.HighWord.val = DBUF.ofstHI
//%+E             ofst32.LowWord.val = DBUF.ofstLO
//%+E             aStart.val = ofst32.val+DBUF.nxt-rStart.val;
//         while aStart.val < atr.rela
//         do EmitZero(atr.rela-aStart.val) endrepeat;
//         if aStart.val <> atr.rela then IERR("EmitStructValue-1") endif;
			treatValue(size);
		}
//      if CurInstr <> S_ENDRECORD
		if(! Scode.accept(Scode.S_ENDRECORD)) Util.IERR("Syntax error in record-constant");
//      repeat
//%-E          aStart.val = DBUF.ofst.val+DBUF.nxt-rStart.val;
//%+E          ofst32.HighWord.val = DBUF.ofstHI
//%+E          ofst32.LowWord.val = DBUF.ofstLO
//%+E          aStart.val = ofst32.val+DBUF.nxt-rStart.val;
//      while aStart.val < nbyte do EmitZero(nbyte-aStart.val) endrepeat;
		int aSize = size() - startSize;
		if(aSize != nbyte) Util.IERR("EmitStructValue-2: aSize=" + aSize + ", size=" + nbyte);
		
//		Util.IERR("");
	}


//	public void emitDefaultValue(int size, int repCount, String cmnt) {
////		IO.println("DataSegment.emitDefaultValue: size="+size);
//		if(repCount < 1) Util.IERR("");
//		boolean option = Global.PRINT_GENERATED_SVM_DATA;
//		int LIMIT = 30;
//		int n = size * repCount;
//		for(int i=0;i<n;i++) {
//			if(Global.PRINT_GENERATED_SVM_DATA && i == LIMIT) {
//				IO.println("                                 ==> ... " + (n-LIMIT) + " more truncated");
//				Global.PRINT_GENERATED_SVM_DATA = false;
//			}
//			emit(null, cmnt);
//		}
//		Global.PRINT_GENERATED_SVM_DATA = option;
//	}
//	
//	public SegmentAddress emitChars(final String chars, final String cmnt) {
//		SegmentAddress addr = nextAddress();
//		int n = chars.length();
//		for(int i=0;i<n;i++) {
//			emit(IntegerValue.of(Type.T_CHAR, chars.charAt(i)), cmnt);
//		}
//		return addr;
//	}
//	
//	public SegmentAddress emitRepText(String cmnt) {
//		Vector<TextValue> texts = new Vector<TextValue>();
//		do { Scode.inputInstr(); texts.add(TextValue.ofScode());
//		} while(Scode.nextByte() == Scode.S_TEXT);
//		SegmentAddress addr = nextAddress();
//		int n = texts.size();
//		for(int i=0;i<n;i++) {
//			TextValue tval = texts.get(i);
//			if(DEBUG) IO.println("DataSegment.emitRepText["+i+"]: "+tval);
//			
////			emit(tval.addr,"CHRADR");
////			emit(IntegerValue.of(Type.T_INT,0),"OFST");
////			emit(IntegerValue.of(Type.T_INT,tval.length),"LNG");
//			tval.emit(this, cmnt);
//		}
//		return addr;
//	}
//
//
//
//	@Override
//	public void dump(String title) {
//		dump(title,0,values.size());
//	}
//	
//	@Override
//	public void dump(String title,int from,int to) {
//		if(values.size() == 0) return;
//		IO.println("==================== " + title + ident + " DUMP ====================" + this.hashCode());
//		for(int i=from;i<to;i++) {
//			String line = "" + i + ": ";
//			while(line.length() < 8) line = " " +line;
//			String value = ""+values.get(i);
//			while(value.length() < 25) value = value + ' ';
//			IO.println(line + value + "   " + comment.get(i));
//		}
//		IO.println("==================== " + title + ident + " END  ====================");
////		Util.IERR("");
//	}
//	
//	public String toString() {
//		if(segmentKind == Kind.K_SEG_CONST)
//			return "ConstSegment \"" + ident + '"';
//		return "DataSegment \"" + ident + '"';
//	}
//
//	// ***********************************************************************************************
//	// *** Attribute File I/O
//	// ***********************************************************************************************
//	private DataSegment(String ident, int segmentKind, AttributeInputStream inpt) throws IOException {
//		super(ident, segmentKind);
//		values = new Vector<Value>();
//		comment = new Vector<String>();
//		int n = inpt.readShort();
//		for(int i=0;i<n;i++) {
//			comment.add(inpt.readString());
//			values.add(Value.read(inpt));
//		}
////		IO.println("NEW IMPORT: " + this);
//	}
//
//	@Override
//	public void write(AttributeOutputStream oupt) throws IOException {
//		if(Global.ATTR_OUTPUT_TRACE) IO.println("DataSegment.Write: " + this + ", Size=" + values.size());
////		oupt.writeInstr(Scode.S_BSEG);
//		oupt.writeKind(segmentKind);
//		oupt.writeString(ident);
//		oupt.writeShort(values.size());
//		for(int i=0;i<values.size();i++) {
//			oupt.writeString(comment.get(i));
//			Value val = values.get(i);
////			IO.println("DataSegment.Write: "+val);
//			if(val == null)
//				 oupt.writeInstr(Scode.S_NULL);
//			else val.write(oupt);
//		}
//	}
//
//	public static DataSegment readObject(AttributeInputStream inpt, int segmentKind) throws IOException {
////		int segmentKind = inpt.readKind();
//		String ident = inpt.readString();
////		IO.println("DataSegment.readObject: ident="+ident+", segmentKind="+segmentKind);
//		DataSegment seg = new DataSegment(ident, segmentKind, inpt);
//		if(Global.ATTR_INPUT_TRACE) IO.println("DataSegment.Read: " + seg);
//		if(Global.ATTR_INPUT_DUMP) seg.dump("DataSegment.readObject: ");
////		Util.IERR("");
//		return seg;
//	}
	

}
