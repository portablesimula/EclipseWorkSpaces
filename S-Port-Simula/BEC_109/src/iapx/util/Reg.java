package iapx.util;

import java.util.BitSet;

import iapx.Kind;
import iapx.CTStack.CTStack;
import iapx.CTStack.Coonst;
import iapx.instruction.POP;
import iapx.qInstr.Q_LOAD;
import iapx.qInstr.Q_LOADC;
import iapx.qInstr.Q_POPR;
import iapx.value.MemAddr;
import iapx.value.Value;

public class Reg {
//	%title ******   R e g i s t e r    U s a g e   ******
//	------------   For  R e a l    U N I X    3 8 6   ------------
	
	public final static int
	    qAL=0,qCL=1,qDL=2,qBL=3,qAH=4,qCH=5,qDH=6,qBH=7,
	    qAX=8,qCX=9,qDX=10,qBX=11,qSP=12,qBP=13,qSI=14,qDI=15,
	    qEAX=16,qECX=17,qEDX=18,qEBX=19,qESP=20,qEBP=21,qESI=22,qEDI=23,
	    qF=24,qM=25,
	    nregs=26;

	public final static int qw_B=0,qw_W=8,qw_D=16;  // Instruksjons-"bredde"

	public final static int uAL=1,     // 0001H = 0000 0000 0000 0001B
	               uAH=2,     // 0002H = 0000 0000 0000 0010B
	        uAX=3, uEAX=3,    // 0003H = 0000 0000 0000 0011B
	               uCL=4,     // 0004H = 0000 0000 0000 0100B
	               uCH=8,     // 0008H = 0000 0000 0000 1000B
	        uCX=12,uECX=12,   // 000CH = 0000 0000 0000 1100B
	               uDL=16,    // 0010H = 0000 0000 0001 0000B
	               uDH=32,    // 0020H = 0000 0000 0010 0000B
	        uDX=48,uEDX=48,   // 0030H = 0000 0000 0011 0000B
	               uBL=64,    // 0040H = 0000 0000 0100 0000B
	               uBH=128,   // 0080H = 0000 0000 1000 0000B
	       uBX=192,uEBX=192,  // 00C0H = 0000 0000 1100 0000B
	       uSP=256,uESP=256,  // 0100H = 0000 0001 0000 0000B
	       uBP=512,uEBP=512,  // 0200H = 0000 0010 0000 0000B
	      uSI=1024,uESI=1024, // 0400H = 0000 0100 0000 0000B
	      uDI=2048,uEDI=2048, // 0800H = 0000 1000 0000 0000B
	               uF =16384, // 4000H = 0100 0000 0000 0000B
	               uM =32768, // 8000H = 1000 0000 0000 0000B
	            uSPBPM=33536, // 8300H = 1000 0011 0000 0000B
	              uALL=65535, // FFFFH = 1111 1111 1111 1111B
	         uALLbutBP=65023; // FDFFH = 1111 1101 1111 1111B
	
	
//	Visible Const public static int uMask(nregs) = (
//			uAL,uCL,uDL,uBL,uAH,uCH,uDH,uBH,uEAX,uECX,uEDX,uEBX,
//			uESP,uEBP,uESI,uEDI,uEAX,uECX,uEDX,uEBX,uESP,uEBP,uESI,uEDI,uF,uM )
	public static int uMask[] = {
	uAL,uCL,uDL,uBL,uAH,uCH,uDH,uBH,uEAX,uECX,uEDX,uEBX,
	uESP,uEBP,uESI,uEDI,uEAX,uECX,uEDX,uEBX,uESP,uEBP,uESI,uEDI,uF,uM };
	
	public static BitSet mindMask;
	
	public static String edMindMask() {
		String s = ""; String sep = "";
		for (int i = mindMask.nextSetBit(0); i >= 0; i = mindMask.nextSetBit(i+1)) {
			// operate on index i here
			s += sep + edReg(i); sep = "|";
			if (i == Integer.MAX_VALUE)	break; // or (i+1) would overflow
		}
		return s;
	}

	
	
//	Visible Const Range(0:nregs) WordReg(nregs) = (
//			qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,
//			  0,  0,  0,  0,qAX,qCX,qDX,qBX,  0,  0,  0,  0,qF,qM )
	public static int WordReg[] = {
	qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,
	0,  0,  0,  0,qAX,qCX,qDX,qBX,  0,  0,  0,  0,qF,qM };

	// Visible Const Range(0:nregs) WholeReg(nregs) = (
	//		qEAX,qECX,qEDX,qEBX,qEAX,qECX,qEDX,qEBX,qEAX,qECX,qEDX,qEBX,
	//		qESP,qEBP,qESI,qEDI,qEAX,qECX,qEDX,qEBX,qESP,qEBP,qESI,qEDI,qF,qM )
	public static int WholeReg[] = {
	qEAX,qECX,qEDX,qEBX,qEAX,qECX,qEDX,qEBX,qEAX,qECX,qEDX,qEBX,
	qESP,qEBP,qESI,qEDI,qEAX,qECX,qEDX,qEBX,qESP,qEBP,qESI,qEDI,qF,qM };

	// Visible Const Range(0:2) RegSize(nregs) =
	//		 ( 1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,4,4,4,4,4,4,4,4,0,0 )
	public static int RegSize[] = { 1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,4,4,4,4,4,4,4,4,0,0 };

	// Visible Const Range(0:1) wBIT(nregs) =
	// ( 0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0 )
	public static int wBIT[] = { 0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0 };

	// Visible Const Boolean RegParts(nregs) =
	//		 ( false,false,false,false,false,false,false,false,
	//		   true ,true ,true ,true ,false,false,false,false,
	//		   true ,true ,true ,true ,false,false,false,false,false,false )
	static boolean RegParts[] = {
	   false,false,false,false,false,false,false,false,
	   true ,true ,true ,true ,false,false,false,false,
	   true ,true ,true ,true ,false,false,false,false,false,false };

	public static int MindMask; // Registers 'mind' after last qi
	public static int PreReadMask;  // Do 'read'  opposite normal
	public static int PreWriteMask; // Do 'write' opposite normal
	public static int PreMindMask;  // Do 'mind'  opposite normal
	public static int NotMindMask;  // Not 'mind' opposite normal

//	%-E Const Range(0:nregs) accreg(3)=(0,qAL,qAX);
//	Const Range(0:nregs) accreg(5)=(0,qAL,qAX,0,qEAX);
	public static int[] accreg = {0,qAL,qAX,0,qEAX};
	
//	%-E Const Range(0:nregs) extreg(3)=(0,qAH,qDX);
//	Const Range(0:nregs) extreg(5)=(0,qAH,qDX,0,qEDX);
	public static int[] extreg = {0,qAH,qDX,0,qEDX};
	
//	%-E Const Range(0:nregs) countreg(3)=(0,qCL,qCX);
//	Const Range(0:nregs) countreg(5)=(0,qCL,qCX,0,qECX);
	public static int[] countreg = {0,qCL,qCX,0,qECX};
	
//	%-E Const Range(0:nregs) datareg(3)=(0,qDL,qDX);
//	Const Range(0:nregs) datareg(5)=(0,qDL,qDX,0,qEDX);
	public static int[] datareg = {0,qDL,qDX,0,qEDX};


	public static int FreePartReg() { // export range(0:nregs) res;
		if ((MindMask & uEAX) == 0) return qEAX;
		if ((MindMask & uEDX) == 0) return qEDX;
		if ((MindMask & uECX) == 0) return qECX;
	    if ((MindMask & uEBX) == 0) return qEBX;
	    Util.IERR("FreePartReg -- Not available"); return qEAX;
	}

	public static String edReg(int reg) {
		switch(reg) {
			case qEAX: return "EAX"; case qECX: return "ECX";
			case qEBX: return "EBX"; case qEDX: return "EDX";
			case qESP: return "ESP"; case qEBP: return "EBP";
			case qESI: return "ESI"; case qEDI: return "EDI";
			case qAL:  return "AL";  case qCL:  return "CL"; case qDL: return "DL";
			case qBL:  return "BL";  case qAH:  return "AH"; case qCH: return "CH";
			case qDH:  return "DH";  case qBH:  return "BH"; case qAX: return "AX";
			case qCX:  return "CX";  case qDX:  return "DX"; case qBX: return "BX";
			case qF:   return "F";   case qM:   return "M";
			default:   return "R" + reg;
		}
	}

	public static String EdRegMask(String s, int regMask){
//	begin range(0:MaxWord) x;
//	      Ed(F,s); if regMask=0 then Ed(F,":None"); goto E1
		if(regMask == 0) return(s);
	    if(regMask == uALL) return(s + ":ALL");
	    if(regMask == uALLbutBP) return(s + ":ALL but BP");
	    
	    String res = s;
	      int x = regMask & uAX;  if(x == 0) ; // Nothing
	      else if(x == uEAX) res = res + ":EAX";
	      else if(x == uAL  ) res = res + ":AL"; else res = res + ":AH";
	      
	      x = regMask & uCX;  if(x == 0) ; // Nothing
	      else if(x == uECX ) res = res + ":ECX";
	      else if(x == uCL  ) res = res + ":CL"; else res = res + ":CH";
	      
	      x = regMask & uDX;  if(x == 0) ; // Nothing
	      else if(x == uEDX ) res = res + ":EDX";
	      else if(x == uDL  ) res = res + ":DL"; else res = res + ":DH";
	      
	      x = regMask & uBX;  if(x == 0) ; // Nothing
	      else if(x == uEBX ) res = res + ":EBX";
	      else if(x == uBL  ) res = res + ":BL"; else res = res + ":BH";
	      
	      if((regMask & uESP) != 0 ) res = res + ":ESP";
	      if((regMask & uEBP) != 0 ) res = res + ":EBP";
	      if((regMask & uESI) != 0 ) res = res + ":ESI";
	      if((regMask & uEDI) != 0 ) res = res + ":EDI";
	      if((regMask & uF) != 0  ) res = res + ":F";
	      if((regMask & uM) != 0  ) res = res + ":M";
	      return res;
	}

	public static void getTosAdjustedIn86(int reg) {
//	begin range(0:255) nbyte; infix(ValueItem) itm; range(0:MaxByte) type,cTYP;
//	%+D   RST(R_GetTosAdjustedIn86);
		if(CTStack.TOS == null) Util.IERR("CODER.GetTosAdjusted-1");
		Type type = CTStack.TOS.type; int nbyte = type.size;
//	      if type<=T_MAX then cTYP = cTYPE(type) else cTYP = cANY endif;
		if(nbyte == 0) Util.IERR("CODER.GetTosAdjustedIn86-1");
//	%+C   if nbyte > AllignFac
//	%+C   then WARNING("CODER.GetTosAdjusted-2");
//	%+C        repeat while nbyte > AllignFac
//	%+C        do qPOPKill(AllignFac); nbyte = nbyte-AllignFac endrepeat
//	%+C   endif;
		if(CTStack.TOS.kind == Kind.K_Coonst) {
			POP.qPOPKill(nbyte); Value value = ((Coonst)CTStack.TOS).itm;
			switch(type.tag) {
			case Tag.TAG_OADDR,Tag.TAG_PADDR,Tag.TAG_RADDR:
//	%+E             case 0:adrMax (value.base.kind)
//	%+E             case 0: Qf2(qLOADC,0,reg,cTYP,0) -- NONE/NOWHERE/NOBODY
//	%+E             case reladr,locadr: Qf3(qLOADA,0,reg,cTYP,value.base);
//	%+E             case segadr,fixadr,extadr: Qf2b(qLOADC,0,reg,cTYP,0,value.base)
//	%+E %+C         otherwise IERR("CODER.GetTosAdjusted-4")
//		%+E        endcase;
				Util.IERR("");
				break;
			case Tag.TAG_BOOL, Tag.TAG_CHAR, Tag.TAG_INT, Tag.TAG_REAL, Tag.TAG_SIZE, Tag.TAG_AADDR:
//				Qf2(qLOADC,0,reg,cTYP,value.int);
				new Q_LOADC(reg,value);
				break;
			default: Util.IERR("CODER:GetTosAdjusted-6"); // Qf2(qLOADC,0,reg,cTYP,0);
			}
		} else {
			getTosValueIn86(reg);
		}
	}

	public static void getTosValueIn86(int reg) {
		// Må ikke bruke qDI p.g.a. RUPDATE.
		Type type = CTStack.TOS.type;
//	      if type<=T_MAX then cTYP:=cTYPE(type) else cTYP:=cANY endif;
		
		IO.println("Reg.getTosValueIn86: " + CTStack.TOS);
//		CTStack.dumpStack("Reg.getTosValueIn86: ");
		
		switch(CTStack.TOS.kind) {
		case K_Temp,K_Result,K_Coonst:
//			Qf1(qPOPR,reg,cTYP);
			new Q_POPR(reg);
			break;
		case K_Address:
			MemAddr opr = MemAddr.getTosSrcAdr();
//	        Qf4c(qLOAD,0,reg,cTYP,0,opr,0);
			new Q_LOAD(reg,opr);
			CTStack.pop(); CTStack.pushTemp(type);
		default: Util.IERR("");
		}
	}

}
