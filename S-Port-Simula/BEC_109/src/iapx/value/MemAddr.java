package iapx.value;

import static iapx.util.Global.bEBX;
import static iapx.util.Global.cVAL;
import static iapx.util.Global.*;

import java.io.IOException;

import iapx.qPkt.Qfunc;
import iapx.util.Reg;
import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.CTStack.Address;
import iapx.CTStack.CTStack;
import iapx.descriptor.Descriptor;
import iapx.instruction.POP;
import iapx.qInstr.Q_POPR;
import iapx.records.Fixup;
import iapx.util.Display;
import iapx.util.Option;
import iapx.util.Tag;
import iapx.util.Util;

//Record MemAddr; info "TYPE";
//begin
//      infix(wWORD) rela;       // Relative byte address
//   range(0:MaxByte) sibreg; // <ss>2<ireg>3<breg>3
//                            // ss: Scale Factor 00=1,01=2,10=4,11=8
//                            // ireg,breg: 000:[EAX]   001:[ECX]
//                            //            010:[EDX]   011:[EBX]
//                            //            100:[ESP]   101:[EBP]
//                            //            110:[ESI]   111:[EDI]
//                            // E.g.   10 110 011=DS:[EBX]+[ESI]*4
//                            //        11 111 101=SS:[EBP]+[EDI]*8
//                            // Note:  11 100 100=228 is ruled out,
//                            //        meaning no breg or ireg
//      range(0:adrMax) kind;    // Variant kind code
//                                       // reladr: + rela
//      variant range(0:MaxWord) loca;   // locadr: + rela - loca
//      variant infix(WORD) segmid;      // segadr: SEG(segid)+rela
//      variant infix(WORD) smbx;        // extadr: SYMBOL(smbx)+rela
//      variant infix(WORD) fix;         // fixadr: FIXUP(fix)+rela
//      variant infix(WORD) knlx;        // knladr: KERNEL(knlx)
//end;
public class MemAddr extends Value {
	public final static int reladr=1,locadr=2,segadr=3,extadr=4,fixadr=5,knladr=6;
	public final static int adrMax=6;

	public int rela;       // Relative byte address

//	int sibreg; // <ss>2<ireg>3<breg>3
	public int scale;
	public int iReg;
	public int bReg;
	public boolean noIBREG() {
		if(scale != 0) return false;
		if(iReg != 0) return false;
		if(bReg != 0) return false;
		return true;
	}

	public int kind;    // Variant kind code
                   // Variant: reladr: + rela
	int loca;      // Variant: locadr: + rela - loca
	String segmid; // Variant: segadr: SEG(segid)+rela
	String smbx;   // Variant: extadr: SYMBOL(smbx)+rela
	public int fix;       // Variant: fixadr: FIXUP(fix)+rela
	int knlx;      // Variant: knladr: KERNEL(knlx)
	
	public int getIreg() { return iReg; }
		public int getBreg() { return bReg; }
		public void setIreg(int r) { iReg = r; }
		public void setBreg(int r) { bReg = r; }

	private MemAddr() {
//		sibreg = NoIBREG;
	}

	/**
	 * object_address		::= c-oaddr global_or_const:tag
	 */
	public static MemAddr ofScode() {
		Tag tag = Tag.inTag();
		Descriptor descr = Display.lookup(tag);
		if(descr == null) Util.IERR("IMPOSSIBLE: TESTING FAILED");
//		IO.println("OADDR_Value.ofScode: descr="+descr.getClass().getSimpleName()+"  "+descr);
		if(descr instanceof Variable var) return var.address;
		if(descr instanceof ConstDescr cns) return cns.getAddress();
		Util.IERR("MISSING: " + descr);
		return null;
	}

	public static MemAddr ofSegAddr(String segID, int rela) {
		MemAddr adr = new MemAddr();
		adr.kind = segadr;
		adr.segmid = segID;
		adr.rela = rela;
		return adr;
	}
	
	public static MemAddr ofRelAddr(int rela) {
//        a.kind = reladr; a.rela.val = 0; a.segmid.val = 0;
//%+E        a.sibreg = NoIBREG;
		MemAddr adr = new MemAddr();
		adr.kind = reladr;
		adr.rela = rela;
		return adr;
	}
	
	public static MemAddr ofLocAddr(int loca) {
//        d.kind = locadr; d.rela.val = 0;
//        d.loca = v qua LocDescr.rela;
//        d.sibreg = bEBP+NoIREG;
		MemAddr adr = new MemAddr();
		adr.kind = locadr;
		adr.loca = loca;
		adr.bReg = bEBP;
		adr.rela = 0;
		return adr;
	}

	public static MemAddr ofExtAddr(String smbx, int rela) {
		MemAddr adr = new MemAddr();
		adr.kind = extadr;
		adr.smbx = smbx;
		adr.rela = rela;
//		IO.println("MemAddr.ofExtAddr: " + adr);
		return adr;
	}

	public static MemAddr ofFixAddr(int fix, int rela) {
		MemAddr adr = new MemAddr();
		adr.kind = fixadr;
		adr.fix = fix;
		adr.rela = rela;
//		IO.println("NEW MemAddr: " + adr);
		return adr;
	}

	public static MemAddr NewFixAdr(String segid, String smbx) { // export infix(MemAddr) x;
//	begin infix(Fixup) Fx; Fx.Matched:=false; Fx.smbx:=smbx; Fx.segid:=segid;
//	      x.kind:=fixadr; x.rela.val:=0; x.fix.val:=nFix;
//	%+E   x.sibreg:=NoIBREG;
//	%+D   Fx.line:=curline; Fx.fixno:=nFix;
//	      if x.fix.HI >= MxpFix then CAPERR(CapFixs)
//	      elsif FIXTAB(x.fix.HI)=none
//	      then  FIXTAB(x.fix.HI):=NEWOBX(size(FixBlock)) endif;
//	      FIXTAB(x.fix.HI).elt(x.fix.LO):=Fx; nFix:=x.fix.val+1;
		
		Fixup fixup = new Fixup(segid, smbx);
		MemAddr adr = MemAddr.ofFixAddr(fixup.fixno, 0);
		return adr;
	}


	public static MemAddr getTosSrcAdr() { // export infix(MemAddr) memaddr;
		Address tos = (Address) CTStack.TOS;
		MemAddr memaddr = tos.objadr;
		memaddr.rela = memaddr.rela + tos.offset;
		switch(tos.atrState) {
	      case Address.NotStacked: break; // Nothing
	      case Address.FromConst:  POP.qPOPKill(1); break; // qPOPKill(AllignFac);
	      case Address.Calculated:
	    	  if(memaddr.getIreg() != 0) Util.IERR("getTosSrcAdr-0");
//	    	  Qfunc.Qf1(Opcode.qPOPR, Reg.qESI,cVAL);
	    	  new Q_POPR(Reg.qESI);
	    	  // if memaddr.sibreg=NoIBREG then memaddr.sibreg = bESI+iESI;
	    	  // else memaddr.sibreg = wOR(wAND(memaddr.sibreg,BaseREG),iESI) endif;
	    	  memaddr.setIreg(Reg.qESI);
		}
		switch(tos.objState) {
	      case Address.NotStacked: break; // Nothing
	      case Address.FromConst:  POP.qPOPKill(1); break; // qPOPKill(AllignFac);
	      case Address.Calculated:
//	    	  Qfunc.Qf1(Opcode.qPOPR, Reg.qEBX,cOBJ);
	    	  new Q_POPR(Reg.qEBX);
	    	  // memaddr.sibreg = bOR(bAND(memaddr.sibreg,IndxREG),bEBX);
	    	  memaddr.setBreg(bEBX);
		}
		return memaddr;
	}

	public static MemAddr getTosDstAdr() { // export infix(MemAddr) a;
		Address tos = (Address) CTStack.TOS;
		MemAddr memaddr = tos.objadr;
		memaddr.rela = memaddr.rela + tos.offset;
		
		switch(tos.objState) {
	      case Address.NotStacked: break; // Nothing
	      case Address.FromConst:  POP.qPOPKill(1); break; // qPOPKill(AllignFac);
	      case Address.Calculated:
	    	  if(memaddr.iReg != 0) Util.IERR("GetTosDstAdr-0");
//	    	  Qf1(qPOPR,qEDI,cVAL);
	    	  new Q_POPR(Reg.qEDI);
	    	  // if a.sibreg=NoIBREG then a.sibreg:=bEDI+iEDI;
	    	  // else a.sibreg:=wOR(wAND(a.sibreg,BaseREG),iEDI) endif;
	    	  memaddr.setIreg(Reg.qEDI);
		}
		switch(tos.objState) {
	      case Address.NotStacked: break; // Nothing
	      case Address.FromConst:  POP.qPOPKill(1); break; // qPOPKill(AllignFac);
	      case Address.Calculated:
//	    	  Qf1(qPOPR,qEBX,cOBJ);
	    	  new Q_POPR(Reg.qEBX);
	    	  // a.sibreg:=bOR(bAND(a.sibreg,IndxREG),bEBX);
	    	  memaddr.setBreg(bEBX);
		}
		return memaddr;
	}

	@Override
	public String toString() {
		switch(kind) {
			case reladr: return "RELADR " + rela;
			case locadr: return "LOCADR " + (rela - loca);
			case segadr: return "SEGADR " + segmid + " + " + rela;
			case extadr: return "EXTADR " + smbx + " + " + rela;
			case fixadr: return "FIXADR " + fix + " + " + rela; 
			case knladr: return "KNLADR " + knlx + " + " + rela; 
		}
		return "UNKNOWN ADDRESS KIND";
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(kind);
		oupt.writeShort(rela);
		switch(kind) {
			case reladr: break;
			case locadr: oupt.writeShort(loca); break;
			case segadr: oupt.writeString(segmid); break;
			case extadr: oupt.writeString(smbx); break;
			case fixadr: oupt.writeShort(fix); break;
			case knladr: oupt.writeShort(knlx); break;
			default: Util.IERR("UNKNOWN ADDRESS KIND");
		}
	}

	public static MemAddr read(AttributeInputStream inpt) throws IOException {
		MemAddr adr = new MemAddr();
		adr.kind = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("MemAddr.read: kind="+adr.kind);
		adr.rela = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("MemAddr.read: rela="+adr.rela);
		switch(adr.kind) {
			case reladr: break;
			case locadr: adr.loca = inpt.readShort(); break;
			case segadr: adr.segmid = inpt.readString(); break;
			case extadr: adr.smbx = inpt.readString(); break;
			case fixadr: adr.fix = inpt.readShort(); break;
			case knladr: adr.knlx = inpt.readShort(); break;
			default: Util.IERR("UNKNOWN ADDRESS KIND");
		}
		if(Option.ATTR_INPUT_TRACE) IO.println("MemAddr.read: "+adr);
		return adr;
	}


}
