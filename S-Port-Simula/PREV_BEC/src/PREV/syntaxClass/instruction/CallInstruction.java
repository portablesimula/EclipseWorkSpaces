package PREV.syntaxClass.instruction;

import java.util.Vector;

import PREV.syntaxClass.programElement.PREV_Variable;
import PREV.syntaxClass.programElement.routine.PREV_PROFILE;
import PREV.syntaxClass.programElement.routine.PREV_ROUTINE;
import PREV.syntaxClass.programElement.routine.PREV_ParameterEval;
import PREV.util.PREV_QuantityDescriptor;
import bec.compileTimeStack.ProfileItem;
import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.compileTimeStack.StackItem;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;
import bec.virtualMachine.SVM_CALL;
import bec.virtualMachine.SVM_CALL_TOS;
import bec.virtualMachine.SVM_POPtoMEM;
import bec.virtualMachine.SVM_STOREC;
import bec.virtualMachine.SVM_SYSCALL;

public class CallInstruction extends PREV_Instruction {
	int n; // Kind
	int profileTag;
	int routineTag;
	Vector<PREV_ParameterEval> argumentEvaluation;
	Vector<PREV_Instruction> CALL_TOS_Instructions;
	
	/**
	 * call_instruction
	 * 		::= connect_profile <parameter_eval>*
	 * 				connect_routine
	 * 
	 * 		connect_profile
	 * 			::= precall profile:tag
	 * 			::= asscall profile:tag
	 * 			::= repcall n:byte profile:tag
	 * 
	 * 		connect_routine ::= call body:tag | <instruction>+ call-tos
	 * 
	 * 		parameter_eval
	 * 			::= <instruction>+ asspar
	 * 			::= <instruction>+ assrep n:byte
	 */
	public CallInstruction(int n) {
		this.n = n;
		argumentEvaluation = new Vector<PREV_ParameterEval>();
		profileTag = Scode.inTag();
		Scode.inputInstr();
		
//		if(Scode.inputTrace > 3) System.out.println("CallInstruction: n="+n+", Curinstr="+Scode.edInstr(Scode.curinstr));
		
		LOOP:while(Scode.curinstr != Scode.S_CALL) {
			Vector<PREV_Instruction> instructions = PREV_Instruction.inInstructionSet();
			if(instructions.isEmpty()) break LOOP;
			
			if(Scode.curinstr == Scode.S_ASSPAR) {
				Scode.inputInstr();
				argumentEvaluation.add(new PREV_ParameterEval(instructions,-1));
			}
			else if(Scode.curinstr == Scode.S_ASSREP) {
				int nRep = Scode.inByte();
				Scode.inputInstr();
				argumentEvaluation.add(new PREV_ParameterEval(instructions,nRep));
//				System.out.println("CallInstruction: ASSREP: NextInstr="+Scode.edInstr(Scode.nextByte()));
			}
			else if(Scode.curinstr == Scode.S_CALL_TOS) {
//				Scode.inputInstr();
				CALL_TOS_Instructions = instructions;
				break LOOP;
			}
			else Util.IERR("Syntax error in call Instruction");
		}
	    //  ---------  Call Routine  ---------
		if(CALL_TOS_Instructions == null) routineTag = Scode.inTag();
	}
	
	public void doCode() {
		// CODING ....
		PREV_PROFILE spec = (PREV_PROFILE) Global.Display.get(profileTag);
		if(spec == null) Util.IERR(""+Scode.edTag(profileTag));
		System.out.println("-------------------------------------------------- BEGIN PRINT CALL Instruction");
		printTree(2);
		spec.printTree(2);
		System.out.println("-------------------------------------------------- ENDOF PRINT CALL Instruction");
		int nstckval = 0;
		if(spec.bodyTag > 0)
			 callSYS(spec, nstckval);
		else {
			System.out.println("CallInsruction.doCode: profileTag="+Scode.edTag(profileTag)+", routineTag="+routineTag);
			callDefault(spec, nstckval);
		}

		// Routines return values on the RT-Stack
		if(spec.export != null) {
			int returnType = spec.export.quant.type.tag;
			System.out.println("CallInstructil.callSYS: returnType="+returnType);
			CTStack.pushTemp(returnType);
		}
//		CTStack.dumpStack();
//		Global.PSEG.dump();
//		Util.IERR("");
	}
	
	private void callSYS(PREV_PROFILE spec, int nstckval) { //,Pkind;
//		range(0:255) npop;
//	    npop:=0;
		
		spec.printTree(2);
		
		ProfileItem pitem = new ProfileItem(Scode.TAG_VOID,spec);
		if(nstckval == 0) CTStack.push(pitem);
		else {
			if(nstckval > 1) Util.IERR("PARSE.CallSYS-2");
			CTStack.precede(pitem,CTStack.TOS);
//			npop:=npop+PutPar(pitem,1)
			Util.IERR("NOT IMPL");
		}
		
	      for(PREV_ParameterEval par:argumentEvaluation) {
	    	  par.doCode();
	    	  putPar(pitem,1);
	      }
//	      Global.PSEG.dump();
//	      spec.DSEG.dump();
//	      ---------  Final Actions  ---------
	      if(pitem.nasspar != pitem.spc.imports.size()) Util.IERR("Wrong number of Parameters");

//	      ---------  Call Routine  ---------
//	      InTag(%rtag%); rut:=DISPL(rtag.HI).elt(rtag.LO);
//	      Qf5(qCALL,Pkind,0,spec.nparbyte,entr);
	      Global.PSEG.emit(new SVM_SYSCALL(spec.ident), "");
	      
//	      repeat while npop<>0 do Pop; npop:=npop-1 endrepeat;
	      if(CTStack.TOS != pitem) Util.IERR("PARSE.CallSYS-3");
	      CTStack.pop();
	}
	
	
	private int putPar(ProfileItem pItm, int nrep) { // export range(0:255) npop;
		int npop = 0;
//	begin range(0:MaxWord) n; range(0:255) i,c; ref(StackItem) s;
//	      range(0:MaxType) st,pt; infix(MemAddr) opr;

//	      int pt = pItm.spc.imports.get(pItm.nasspar).type;
//	      int c = pItm.spc.imports.get(pItm.nasspar).count;
//		QuantityDescriptor pt = pItm.spc.imports.get(pItm.nasspar).quant;
		PREV_Variable param = pItm.spc.imports.get(pItm.nasspar);
		PREV_QuantityDescriptor quant = pItm.spc.imports.get(pItm.nasspar).quant;
		int pt = quant.type.tag;
		int c = quant.repCount;
//	      n:=TTAB(pt).nbyte; i:=nrep;
		if(nrep>c) Util.IERR("Too many values in repeated param: Got "+nrep+", expect "+c);
		pItm.nasspar = pItm.nasspar+1;
		StackItem s = CTStack.TOS;
		int st = s.type;
		//--- First: Treat TOS ---
//		System.out.println("CallInstruction.putPar: "+Scode.edTag(st) + " ===> " + Scode.edTag(pt));
		if(st != pt) Util.GQconvert(pt);
		else if(s instanceof Address) s.type = st;
		else Util.GQconvert(pt);
		
		if(CTStack.TOS instanceof Address) Util.GQfetch("putPar: ");
		CTStack.pop();
		Global.PSEG.emit(new SVM_POPtoMEM(param.address, 1), "putPar: ");
		
//		pItm.spc.printTree(2);
//		Global.PSEG.dump("putPar: ");
//		pItm.spc.DSEG.dump("putPar: ");
//		CTStack.dumpStack("putPar: ");
//		Util.IERR("");
		
		if(nrep > 1) { // --- Then: Treat rest of rep-par ---
			Util.IERR("Parse.XXX: NOT IMPLEMENTED");
//	      repeat i:=i-1 while i <> 0
//	      do s:=s.suc;
//	--??     Husk at integer-type skal legges p} stacken m.h.t spesifikasjon!!!
//	--??     CheckTypesEqual(s.type,p.type);
//	%+C      if s.kind=K_Address then IERR("MODE mismatch below TOS") endif;
//	         if s.type <> pt
//	         then
//	%+S           if SYSGEN <> 0 then
//	%+S           WARNING("PARSE: TYPE mismatch below TOS -- ASSREP") endif;
//	              if    pt=T_WRD4 then ConvRepWRD4(nrep); goto L2;
//	%-E           elsif pt=T_WRD2 then ConvRepWRD2(nrep); goto L1;
//	%+C           else IERR("PARSE: TYPE mismatch below TOS -- ASSREP");
//	              endif;
//	         endif;
//	      endrepeat;
//	%-E  L1:
//	   L2:if nrep < c
//	      then
//	%-E        Qf2(qDYADC,qSUB,qSP,cSTP,(c-nrep)*n);
//	%+E        Qf2(qDYADC,qSUB,qESP,cSTP,(c-nrep)*n);
//	      endif;
		}
		npop = nrep;
		return npop;
	}

	private void callDefault(PREV_PROFILE spec, int nstckval) {
//	import ref(ProfileDescr) spec; range(0:MaxWord) nstckval;
//	begin ref(ProfileItem) pitem; range(0:255) npop; infix(MemAddr) entr;
//	      ref(StackItem) z; ref(Temp) result; ref(Descriptor) rut;
//	      range(0:MaxByte) b; infix(WORD) rtag;
//	      range(0:MaxType) xt;
//	      range(0:MaxByte) nwm,nbi; -- no.word/bytes to be moved/inserted on stack
//	      range(0:MaxByte) xlng;    -- size of export on 86-stack (in bytes)
//
//	%+D   RST(R_CallDefault);
		ProfileItem pitem = new ProfileItem(Scode.TAG_VOID,spec);
//	      rut:=none; npop:=0; xt:=0; xlng:=0;
	      if(nstckval == 0) {
	    	  if(pitem.spc.type != 0) {
//	    		  xt:=pitem.spc.type; pushTemp(xt); result:=takeTOS;
//	                xlng:=TTAB(xt).nbyte;
//	%-E             Qf2(qDYADC,qSUB,qSP,cSTP,wAllign(%xlng%));
//	%+E             Qf2(qDYADC,qSUB,qESP,cSTP,wAllign(%xlng%));
//	                Push(result); result.kind:=K_Result;
	    		  Util.IERR("NOT IMPLEMENTED");
	    	  }
//    		  Util.IERR("NOT IMPLEMENTED");
    		  CTStack.push(pitem);
	      } else {
	    	  StackItem z = CTStack.TOS;
//	    	  nwm:=0; nbi:=0;
	    	  if(nstckval > 1) Util.IERR("PARSE.REPCALL");
	    	  if(pitem.spc.type == 0) CTStack.precede(pitem,z);
	    	  else {
	    		  int xt = pitem.spc.type; CTStack.pushTemp(xt); StackItem result = CTStack.takeTOS();
//	                xlng:=TTAB(xt).nbyte; nbi:=wAllign(%xlng%);
	    		  CTStack.precede(result,z); CTStack.precede(pitem,z);
//	    		  result.kind:=K_Result;
	    	  }
//	           if nbi <> 0
//	           then nwm:=wWordsOnStack(TOS);
//	                if nwm <> 0 then SpaceOnStack(nwm,nbi)
//	                else
//	%-E                  Qf2(qDYADC,qSUB,qSP,cSTP,wAllign(%xlng%));
//	%+E                  Qf2(qDYADC,qSUB,qESP,cSTP,wAllign(%xlng%));
//	                endif;
//	           endif;
//	           npop:=npop+PutPar(pitem,nstckval);
	      }
		
//	      repeat InputInstr while CurInstr <> S_CALL
//	      do repeat while Instruction do InputInstr endrepeat;
//	         if    CurInstr=S_ASSPAR    then npop:=npop+PutPar(pitem,1)
//	         elsif CurInstr=S_ASSREP    then
//	%+D                                      b:=InputByte;
//	%-D                                      InByte(%b%);
//	                                         npop:=npop+PutPar(pitem,b)
//	         elsif CurInstr=S_CALL_TOS  then goto F
//	         else  IERR("Syntax error in call Instruction") endif;
//	%+D      if TraceMode <> 0 then DumpStack endif;
//	      endrepeat;
	      for(PREV_ParameterEval par:argumentEvaluation) {
	    	  par.doCode();
	    	  putPar(pitem,1);
	      }
	      Global.PSEG.dump("CallInstruction: After putpar: ");
	      spec.DSEG.dump("CallInstruction: After putpar: ");
//	      ---------  Final Actions  ---------
	      if(pitem.nasspar != pitem.spc.imports.size()) Util.IERR("Wrong number of Parameters");
//		  Util.IERR("NOT IMPLEMENTED");

//		
//		Scode.inputInstr();
//		LOOP:while(Scode.curinstr != Scode.S_CALL) {
//			while(instruction()) Scode.inputInstr();
//				
//			if(Scode.curinstr == Scode.S_ASSPAR) ; // OK
//			else if(Scode.curinstr == Scode.S_ASSREP) Scode.inByte();
//			else if(Scode.curinstr == Scode.S_CALL_TOS) break LOOP;
//			else Util.IERR("Syntax error in call Instruction");
//		
//			
//			Scode.inputInstr();
//		}
//	      ---------  Call Routine  ---------
	      if(CALL_TOS_Instructions != null) {
	    	  for(PREV_Instruction instr:CALL_TOS_Instructions) instr.doCode();
	    	  Global.PSEG.emit(new SVM_CALL_TOS(), "");
	    	  CTStack.pop();
//	    	  Global.PSEG.dump("");
//	    	  Util.IERR("");
	      } else {

//	      InTag(%rtag%); rut:=DISPL(rtag.HI).elt(rtag.LO);
		      PREV_ROUTINE rut = (PREV_ROUTINE) Global.Display.get(routineTag);
		      if(rut == null) Util.IERR("Unknown Routine: " + Scode.edTag(routineTag));
//		
//	      if rut.kind=K_IntRoutine then entr:=rut qua IntDescr.adr
//	      else entr.kind:=extadr;
//	%-E        entr.sbireg:=0;
//	%+E        entr.sibreg:=NoIBREG;
//	           entr.rela.val:=rut qua ExtDescr.adr.rela;
//	           entr.smbx:=rut qua ExtDescr.adr.smbx;
//	      endif;
//	%+C   if entr=noadr then IERR("Undefined routine entry") endif;
//	F:    ---------  Final Actions  ---------
//	      if(pitem.nasspar != pitem.spc.npar()) Util.IERR("Wrong number of Parameters");
//	%+D   if TraceMode > 1
//	%+D   then setpos(sysout,54); outstring(".   CALL "); print(TOS) endif;
//	      if rut=none
//	      then
//	%+C        CheckTosType(T_RADDR);
//	%-E        GetTosValueIn86R3(qAX,qBX,0); Pop;
//	%-E        entr:=X_CALL; PreReadMask:=wOR(uAX,uBX);
//	%+E        GetTosValueIn86(qEAX); Pop;
//	%+E        entr.kind:=reladr; entr.rela.val:=0; entr.sibreg:=bEAX+iESP;
//	      endif;
//	      Qf5(qCALL,spec.WithExit,0,xlng+pitem.spc.nparbyte,entr);
	      	Global.PSEG.emit(new SVM_CALL(rut), ""+rut);
	      }
	      
//	      repeat while npop<>0 do Pop; npop:=npop-1 endrepeat;
//	      CTStack.dumpStack("CallInstruction.callDefault: ");
	      if(CTStack.TOS != pitem) Util.IERR("SSTMT.Call");
	      CTStack.pop();
//	      Pop;
//	      if xlng <> 0 then Qf2(qADJST,0,0,0,xlng) endif;
//	      if xt <> 0 then result.kind:=K_Temp endif;
//	%-E   if CHKSTK
//	%-E   then if spec.WithExit <> 0
//	%-E        then Qf5(qCALL,1,0,0,X_CHKSTK) endif;
//	%-E   endif;

	      Global.PSEG.dump("END CallDefault: ");
//	      Util.IERR("Parse.XXX: NOT IMPLEMENTED");
	}
	
	@Override
	public void printTree(final int indent) {
//		System.out.println(edLead(indent) + this);
		String lead = null;
		switch(n) {
			case 0:  lead = "PRECALL "; break;
			case 1:  lead = "ASSCALL "; break;
			default: lead = "REPCALL " + n + " "; break;
		}
		sLIST(indent, lead + Scode.edTag(profileTag));
		for(PREV_ParameterEval p:argumentEvaluation) p.printTree(indent + 1);
		if(CALL_TOS_Instructions != null) {
			for(PREV_Instruction instr:CALL_TOS_Instructions)
				sLIST(indent + 1, instr.toString());
			sLIST(indent, "CALL-TOS");
//			Util.IERR("SJEKK DETTE");
		} else {
			sLIST(indent, "CALL " + Scode.edTag(routineTag));
		}
	}
	
	public String toString() {
		String lead = null;
		switch(n) {
			case 0:  lead = "PRECALL "; break;
			case 1:  lead = "ASSCALL "; break;
			default: lead = "REPCALL " + n + " "; break;
		}
		return lead + Scode.edTag(profileTag) + " ...";
	}
	

}
