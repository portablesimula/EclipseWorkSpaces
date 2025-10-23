/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec;

import java.io.FileOutputStream;
import java.io.IOException;

import bec.descriptor.Descriptor;
import bec.descriptor.Kind;
import bec.segment.Segment;
import bec.util.Global;
import bec.util.Option;
import bec.util.Type;
import bec.util.Util;

/// Module IO Utilities.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/ModuleIO.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class ModuleIO {

//	%title ***   O u t p u t    M o d u l e   ***
	
	private static void writeDescriptors(AttributeOutputStream modoupt, int nXtag) throws IOException {
//		for(int i=0;i<nXtag;i++) {
//			int tx = Global.TAGTAB.get(i);
//			Descriptor d = Global.DISPL.get(tx);
//			IO.println("XTAGIDENT: " + i + ": " + Scode.TAGIDENT.get(d.tag.val));
//		}
//		Util.IERR("");
		
		for(Segment seg:Global.routineSegments) seg.write(modoupt);

		for(int i=0;i<=nXtag;i++) {
			int tx = Global.iTAGTAB.get(i);
			Descriptor d = Global.DISPL.get(tx);
			if(d == null) Util.IERR("External tag " + i + " = Tag " + tx + " is not defined (OutModule)");
//			if(Option.ATTR_OUTPUT_TRACE)
//				IO.println("iTAGTAB["+i+"] " + d);
			d.write(modoupt);
		}
	}

	public static void outputModule(int nXtag) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE)
			IO.println("**************   Begin  -  Output-module  " + Global.modident + "  " + Global.modcheck + "   **************");
		if(Global.outputDIR==null || Global.outputDIR.isEmpty()) Util.IERR("No Output Directory Specified");
//		AttributeOutputStream modoupt = new AttributeOutputStream(new FileOutputStream(Global.getAttrFileName(Global.modident, ".svm")));
		AttributeOutputStream modoupt = new AttributeOutputStream(new FileOutputStream(Global.outputDIR+Global.modident+".svm"));
		modoupt.writeKind(Kind.K_Module);
		modoupt.writeString(Global.modident);
		modoupt.writeString(Global.modcheck);

//		if(Global.currentModule instanceof InterfaceModule)
			Type.writeRECTYPES(modoupt);
		
//		Segment.writeSegments(modoupt);
		
		writeDescriptors(modoupt, nXtag);
		
//		Segment.writeSegments(modoupt);
		Global.CSEG.write(modoupt);
		Global.TSEG.write(modoupt);
		Global.DSEG.write(modoupt);
		Global.PSEG.write(modoupt);

//		writePreamble();
		modoupt.writeKind(Kind.K_EndModule);
		
		if(Option.ATTR_OUTPUT_TRACE)
			IO.println("**************   Endof  -  Output-module  " + Global.modident + "  " + Global.modcheck + "   **************");
	}

}
