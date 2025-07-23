package bec;

import java.io.FileOutputStream;
import java.io.IOException;

import bec.descriptor.Descriptor;
import bec.descriptor.Kind;
import bec.segment.Segment;
import bec.util.BecGlobal;
import bec.util.Type;
import bec.util.Util;

public class ModuleIO {

//	%title ***   O u t p u t    M o d u l e   ***
	
	private static void writeDescriptors(AttributeOutputStream modoupt, int nXtag) throws IOException {
//		for(int i=0;i<nXtag;i++) {
//			int tx = Global.TAGTAB.get(i);
//			Descriptor d = Global.DISPL.get(tx);
//			System.out.println("XTAGIDENT: " + i + ": " + Scode.TAGIDENT.get(d.tag.val));
//		}
//		Util.IERR("");
		
		for(Segment seg:BecGlobal.routineSegments) seg.write(modoupt);

		for(int i=0;i<=nXtag;i++) {
			int tx = BecGlobal.iTAGTAB.get(i);
			Descriptor d = BecGlobal.DISPL.get(tx);
			if(d == null) Util.IERR("External tag " + i + " = Tag " + tx + " is not defined (OutModule)");
//			if(Global.ATTR_OUTPUT_TRACE)
//				System.out.println("iTAGTAB["+i+"] " + d);
			d.write(modoupt);
		}
	}

	public static void outputModule(int nXtag) throws IOException {
		if(BecGlobal.ATTR_OUTPUT_TRACE)
			System.out.println("**************   Begin  -  Output-module  " + BecGlobal.modident + "  " + BecGlobal.modcheck + "   **************");
		if(BecGlobal.outputDIR==null || BecGlobal.outputDIR.isEmpty()) Util.IERR("No Output Directory Specified");
//		AttributeOutputStream modoupt = new AttributeOutputStream(new FileOutputStream(Global.getAttrFileName(Global.modident, ".svm")));
		AttributeOutputStream modoupt = new AttributeOutputStream(new FileOutputStream(BecGlobal.outputDIR+BecGlobal.modident+".svm"));
		modoupt.writeKind(Kind.K_Module);
		modoupt.writeString(BecGlobal.modident);
		modoupt.writeString(BecGlobal.modcheck);

//		if(Global.currentModule instanceof InterfaceModule)
			Type.writeRECTYPES(modoupt);
		
//		Segment.writeSegments(modoupt);
		
		writeDescriptors(modoupt, nXtag);
		
//		Segment.writeSegments(modoupt);
		BecGlobal.CSEG.write(modoupt);
		BecGlobal.TSEG.write(modoupt);
		BecGlobal.DSEG.write(modoupt);
		BecGlobal.PSEG.write(modoupt);

//		writePreamble();
		modoupt.writeKind(Kind.K_EndModule);
		
		if(BecGlobal.ATTR_OUTPUT_TRACE)
			System.out.println("**************   Endof  -  Output-module  " + BecGlobal.modident + "  " + BecGlobal.modcheck + "   **************");
	}

}
