package bec.virtualMachine.sysrut;

import bec.segment.DataSegment;
import bec.util.Type;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.SVM_CALL_SYS;

public abstract class SysKnown {
//   ---  Transfer all the characters in the source.
//   ---  Blankfill any remaining characters of the destination.
//	 Visible known("CMOVE") C_MOVE; import infix(string) src,dst;
//	 begin integer i,n,rst; i:= -1; n:=src.nchr; rst:=dst.nchr-n;
//	       if rst < 0 then n:=dst.nchr; rst:=0 endif;
//	       repeat i:=i+1 while i < n     --- Move characters
//	       do var(dst.chradr)(i):=var(src.chradr)(i) endrepeat;
//	--	SETOPT(2,1); -- Option.RT_InstrStep=val;
//	       repeat while rst > 0          --- Blank fill
//	       do var(dst.chradr)(n):=' '; n:=n+1; rst:=rst-1 endrepeat;
//	--	SETOPT(2,0); -- Option.RT_InstrStep=val;
//	 end;
	public static void cmove() {
		SVM_CALL_SYS.ENTER("CMOVE: ", 0, 6); // exportSize, importSize
		
		boolean DEBUG = false;
		
		int dstNchr = RTStack.popInt();
		ObjectAddress dstAddr = RTStack.popGADDRasOADDR();
		int srcNchr = RTStack.popInt();
		ObjectAddress srcAddr = RTStack.popGADDRasOADDR();

		if(DEBUG) prtStr("\n\nTESTING CMOVE: Source: ", srcAddr, srcNchr);
		
		int n = srcNchr; int rst = dstNchr-n;
		if(rst < 0) { n = dstNchr; rst = 0; };
		DataSegment dstSeg = dstAddr.segment();
		int dstIdx = dstAddr.getOfst();

//		boolean DEBUG = rst > 0;
		int idxTo = dstIdx+dstNchr;
		if(DEBUG) {
			System.out.println("SVM_SYSCALL.CMOVE: srcAddr="+srcAddr+", srcNchr="+srcNchr);
			System.out.println("SVM_SYSCALL.CMOVE: dstAddr="+dstAddr+", dstNchr="+dstNchr);
			System.out.println("SVM_SYSCALL.CMOVE: rst="+rst);
			prtStr("TESTING CMOVE: dst: ", dstAddr.addOffset(-2), dstNchr+4);
//			dstSeg.dump("SVM_SYSCALL.cmove: ", dstIdx-1, idxTo+1);
		}
		
		if(srcAddr != null) {
			DataSegment srcSeg = srcAddr.segment();
			int srcIdx = srcAddr.getOfst();
	
			// Move characters
			for(int i=0;i<n;i++) {
				Value val = srcSeg.load(srcIdx + i);
//				System.out.println("SVM_SYSCALL.CMOVE: srcSeg[" + (srcIdx + i) + "]=" + val + " ==> dstSeg[" + (dstIdx + i) + "]=");
				dstSeg.store(dstIdx + i, val);
			}
		}
		
		IntegerValue blnk = IntegerValue.of(Type.T_CHAR, ' ');
//		while(rst >= 0) {
//			dstSeg.store(dstIdx + (n++), blnk); rst--;
//			dstSeg.store(dstIdx + rst, blnk); rst--;
//		}
		for(int i=0;i<rst;i++) {
			dstSeg.store(dstIdx + (n++), blnk);
		}
		
		if(DEBUG) {
			prtStr("TESTING CMOVE: src: ", srcAddr, srcNchr);
			prtStr("TESTING CMOVE: dst: ", dstAddr.addOffset(-2), dstNchr+4);
//			dstSeg.dump("SVM_SYSCALL.cmove: ", dstIdx-1, idxTo+1);
//			Util.IERR("");
		}
		
		
//		RTStack.push(null, null); // ?????
		SVM_CALL_SYS.EXIT("CMOVE: ");
	}
	
	public static void prtStr(String title, ObjectAddress addr, int nchr) {
		System.out.print("prtStr: "+title+'"');
		for(int i=0;i<nchr;i++) {
			IntegerValue val = (IntegerValue) addr.addOffset(i).load();
			if(val != null) System.out.print((char)val.value);
			else System.out.print(".");
		}
		System.out.println("\"");
//		Util.IERR("");
	}

	
//	 Visible known("CBLNK") C_BLNK; import infix(string) str;
//	 begin repeat while str.nchr > 0
//	       do var(str.chradr):=' ';      -- Fill in a blank character value.
//	          str.chradr:=name(var(str.chradr)(1));   -- Increment one char.
//	          str.nchr:=str.nchr - 1;
//	       endrepeat;
//	 end;
	public static void cblnk() {
		SVM_CALL_SYS.ENTER("CBLNK: ", 0, 3); // exportSize, importSize
		
		int nchr = RTStack.popInt();
		ObjectAddress addr = RTStack.popGADDRasOADDR();
		
		DataSegment dseg = addr.segment();
		int idx = addr.getOfst();
//		int idxTo = idx+nchr;
//		System.out.println("SVM_SYSCALL.cblnk: ");
//		dseg.dump("SVM_SYSCALL.cblnk: ", idx-1, idxTo+1);

		IntegerValue blnk = IntegerValue.of(Type.T_CHAR, ' ');
		for(int i=0;i<nchr;i++) {
			dseg.store(idx + i, blnk);		
		}
		
//		dseg.dump("SVM_SYSCALL.cblnk: ", idx-1, idxTo+1);
//		Util.IERR("");
		
		
//		RTStack.push(null, null); // ?????
		SVM_CALL_SYS.EXIT("CBLNK: ");
	}

}
