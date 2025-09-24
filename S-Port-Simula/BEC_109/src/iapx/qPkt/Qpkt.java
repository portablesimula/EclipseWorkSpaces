package iapx.qPkt;

import static iapx.qPkt.Qfrm1.qFCOM;
import static iapx.util.Global.*;

import iapx.Kind;
import iapx.Opcode;
import iapx.CTStack.CTStack;
import iapx.massage.Massage;
import iapx.records.Objekt;
import iapx.util.Option;
import iapx.util.Reg;
import iapx.util.Util;

public abstract class Qpkt extends Objekt {

//	Record Object;
//	begin range(0:MaxByte) kind;   --- Object kind code
//	      range(0:MaxType) type; 
//	end;
//	Record Qpkt:Object;
//	begin ref(Qpkt) next,pred;
//	      range(0:MaxWord) read;       // Reg read mask
//	      range(0:MaxWord) write;      // Reg write mask
//	      range(0:20) isize;           // I-code size in bytes
//	      range(0:127) fnc;            // Operation code
//	      range(0:20) subc;            // Operation subcode
//	      range(0:nregs) reg;
//	      range(0:MaxType) type;       // Type of value          --- NEW !!!
//	end;

	//--- Current Location Counter Data ---
	public static Qpkt qfirst,qlast;   // initial(none) q-list of CSEG
	public static int qcount;    // initial(0), No.of Qpkt's in q-list
	//------------------------------------------------------------------------

	Kind kind;   // Object kind code
	public Opcode fnc;    // Operation code
	int subc;   // Operation subcode
	protected int reg;
	int type;
	
	public Qpkt next;
	public Qpkt pred;
	int read;   // Reg read mask
	int write;  // Reg write mask
	int isize;  // I-code size in bytes
	
	public Qpkt() {
//		if(qfirst == null) qfirst = this;
//		this.next = qfirst;
//		qlast = this;
		addQinstr();
	}
	
	protected void reads(int... reg) {
		
	}
	
	protected void writes(int... reg) {
		
	}
	
	protected void minds(int... reg) {
		
	}
	
	public static void printQlist() {
		Qpkt qPkt =qfirst;
		while(qPkt != null) {
			IO.println(qPkt.toString());
			qPkt = qPkt.next;
		}
	}


	public boolean isDeleted() {
		boolean res;
		// --- Check that 'this' is in queue ---
	    if(    (this.pred == null) & (this != qfirst) ) res = true;
	    else if (this.next == null) res = (this !=qlast);
	    else res = false;
	    return res;
	}

	public void remove() {
		if(Option.listq1 > 1) ListQinstr("Remove:   ",this,false);
		if(this.isDeleted()) Util.IERR("(Above) Q-Instruction is already DELETED");
        if(this == qfirst) {
        	qfirst = qfirst.next;
             if(qfirst == null) qlast = null;
             else qfirst.pred = null;
        }
        else if(this == qlast) { qlast = qlast.pred; qlast.next = null; }
        else {
        	this.pred.next = this.next;
            //this.next qua Qpkt.pred = this.pred;
            this.next.pred = this.pred;
        }
			qcount = qcount-1;
		this.pred = null; this.next = null;
	}

//	public static void DeleteQPosibJ(Qpkt this) {
	public void deletePosibJump() {
	      Qfrm5 jmp = null; Qfrm6 dst = null;
	      switch(this.fnc) {
		      case qJMP: jmp = (Qfrm5)this; dst = (Qfrm6)jmp.dst;
			      if(dst != null) {
			    	  if(dst.fnc == Opcode.qBDEST) ; // NOTHING
			    	  else if(dst == this) { // FDEST Not generated, Mark jmp Deleted
			    		  dst = null; jmp.addr.kind = 0;
			    		  jmp.remove(); jmp = null;
			    	  }
			      }
			      break;
			  // %-E case qJMPM,qJMPFM: jmp = this;
		      case qJMPM:
		    	  jmp = (Qfrm5) this;
		    	  break;
		      case qFDEST:
		    	  //jmp = this qua Qfrm6.jmp;
		    	  jmp = ((Qfrm6)this).jmp;
		    	  if(jmp != null) jmp.deletePosibJump();
		    	  jmp = null; // Prevent it from being deleted twice --
		    	  break;
		      // case qBDEST:
		      // %+C      IERR("Masseur.DeleteQPosibJ-3"); -- Not Implemented 
		      case qLABEL: // Nothing
		    	  break;
		      default: jmp = (Qfrm5) this;  // Default Case See Next Line !!!
	      }
	      if(jmp != null) jmp.remove();
	      if(dst != null) dst.remove();
	}

	public static void deleteLastQ() {
		qlast.deleteQinstr();
	}

	public void deleteQinstr() { // import ref(Object) qi ==> this
		this.remove();
	}

	public void addQinstr() { // import ref(Qpkt) this;  ==> this
		//--- Update pred,next links in Q-queue ---
		if(qlast != null) {
			qlast.next = this; this.pred = qlast; this.next = null; qlast = this;
		} else {
			this.pred = this.next = null; qfirst = qlast = this;
		}
	}
	
	public void appendQinstr() { // import ref(Qpkt) this;  ==> this
		// HET DETTE FÆR: public static void AppendQinstr(Qpkt qi) {
		if(InMassage) Util.IERR("InMassage");
		boolean newstate = deadCode; this.isize = 0; qcount = qcount+1;
		//--- Update pred,next links in Q-queue ---
		if(qlast != null) {
			qlast.next = this; this.pred = qlast; this.next = null; qlast = this;
		} else {
			this.pred = this.next = null; qfirst = qlast = this;
		}

		Util.IERR("");
		
		switch(this.fnc) {
	      case qFDYAD:
	    	  this.reg = this.reg | (2*CTStack.StackDepth87);
	    	  CTStack.StackDepth87 = CTStack.StackDepth87-1;
	    	  if(this.subc == qFCOM) CTStack.StackDepth87 = CTStack.StackDepth87-1;
	    	  break;
	      case qFDYADM:
//	    	  this qua Qfrm4.aux.LO = bOR(this qua Qfrm4.aux.LO,2*CTStack.StackDepth87);
	    	  if(this instanceof Qfrm4 frm4)
	    		  frm4.aux = frm4.aux | (2*CTStack.StackDepth87);
	           if(this.subc == qFCOM) CTStack.StackDepth87 = CTStack.StackDepth87-1;
	           break;
	      case qFMONAD: this.reg = this.reg | (2*CTStack.StackDepth87); break;
	      case qFST: this.subc = this.subc | (2*CTStack.StackDepth87); break;
	      case qFPUSH,qFSTP:
	    	  	this.subc = this.subc | (2*CTStack.StackDepth87);
	      		CTStack.StackDepth87 = CTStack.StackDepth87-1;
	      		break;
	      case qFPOP,qFLD,qFLDC,qFDUP: CTStack.StackDepth87 = CTStack.StackDepth87+1;
	      		this.subc = this.subc | (2*CTStack.StackDepth87);
	      		if(CTStack.StackDepth87 == 9) Util.ERROR("Too complicated arithmetic expression");
	      		break;
	      case qFLDCK: CTStack.StackDepth87 = CTStack.StackDepth87+1;
    		if(CTStack.StackDepth87 == 9) Util.ERROR("Too complicated arithmetic expression");
    		break;
	      case qFDEST,qBDEST,qLABEL: newstate = false; deadCode = false;
	      	break;
	      case qJMPM:          newstate = true;
	           if(deadCode) this.deletePosibJump();
	           break;
	      case qJMP: if(this.subc == 0) newstate = true;
	      	   if(deadCode) this.deletePosibJump();
	      	   break;
		default: if(deadCode) this.deleteQinstr();
		}

		if(deadCode) { Reg.PreMindMask = Reg.uSPBPM; Reg.PreReadMask = Reg.NotMindMask = 0; }
	    else {
	    	Regmap.MakeRegmap(this);
	    	if(Option.listq1 > 1) Qpkt.ListQinstr("Qpkt.appendQinstr: ", this, true);
	    	if(Option.MASSLV != 0) {
	    		//--------------------------------------------
	    		RecDepth = Option.MASSDP;  //-- >1 always
	    		InMassage = true; Massage.DOMASSAGE(this); InMassage = false;
	    		//--------------------------------------------
	    	}
	    }

	    deadCode = newstate;
	    if(qcount > QBFLIM) { Qfunc.peepExhaust(false); deleteOK = false; }
	}

	
	public static void ListQinstr(String ms, Qpkt qi, boolean ListI) {
	      if(qi == null) IO.println(ms);
	      else qi.ListQinstr(ms, ListI);
	}
	
	public void ListQinstr(String ms, boolean ListI) {
      String line = ms;
           if(this.isDeleted()) {
           //then sysout.pos = 18; outstring("#") else sysout.pos = 19 endif
        	   while(line.length() < 18) line = line + ' ';
        	   line = line + '#';
           }
           int rela = 0;
           
           IO.println("Qpkt.ListQinstr: Rutine AsmListing MÅ IMPLEMENTERES");
           //AsmListing(rela,this,ListI);
           
           if(ListI) {
        	   //setpos(sysout,10);
        	   while(line.length() < 10) line = line + ' ';
        	   
               line = line + Reg.EdRegMask("Read",this.read) + "  ";
               line = line + Reg.EdRegMask("Write",this.write) + "  ";
                if(! InMassage) line = line + Reg.EdRegMask("Mind",Reg.MindMask);
                //printout(sysout);
                IO.println(line);
           }
	      
	      Util.IERR("SJEKK DETTE NØYE !!!");
	}
	

}
