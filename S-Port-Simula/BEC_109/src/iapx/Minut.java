package iapx;

import static iapx.util.Global.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import iapx.descriptor.Descriptor;
import iapx.util.Display;
import iapx.util.Option;
import iapx.util.Tag;
import iapx.util.Util;

public class Minut {


// %title ***   O u t p u t    M o d u l e   ***

	public static void outputModule() throws IOException {
		if(Option.ATTR_OUTPUT_TRACE)
			IO.println("**************   Begin  -  Output-module  " + modident + "  " + modcheck + "   **************");
		if(outputDIR==null || outputDIR.isEmpty()) Util.IERR("No Output Directory Specified");
		String fileName = outputDIR+modident+".svm";
		if(Option.verbose) IO.println("Begin write Attribute file: " + fileName);
		AttributeOutputStream modoupt = new AttributeOutputStream(new FileOutputStream(fileName));
		Kind.K_Module.write(modoupt);
		modoupt.writeString(modident);
		modoupt.writeString(modcheck);
//		TYPMAP = new Array<Integer>();


//		if(Global.currentModule instanceof InterfaceModule)
//			Type.writeRECTYPES(modoupt);
		
//		Segment.writeSegments(modoupt);
		
		writeDescriptors(modoupt);
		
//		Global.CSEG.write(modoupt);
//		Global.TSEG.write(modoupt);
//		Global.DSEG.write(modoupt);
//		Global.PSEG.write(modoupt);

//		writePreamble();
		Kind.K_EndModule.write(modoupt);
		
		if(Option.ATTR_OUTPUT_TRACE)
			IO.println("**************   Endof  -  Output-module  " + modident + "  " + modcheck + "   **************");
		if(Option.verbose) IO.println("Attribute file written to: " + fileName);
		
//		Minut.fileDump("Output: ", fileName, 0, 40);
//		Thread.dumpStack();

	}
	
	private static void writeDescriptors(AttributeOutputStream modoupt) throws IOException {
		int n = Display.size();
		for(int i=0;i<n;i++) {
			Integer x = Tag.xTag(i);
			if(x != null) {
				Descriptor d = Display.lookup(i);
//				IO.println("Minut.writeDescriptors: External tag " + x + " = iTag " + d.tag);
				d.write(modoupt);
			}
		}
	}
	
	public static void fileDump(String title, String fileName, int start, int stop) throws FileNotFoundException, IOException {
		FileInputStream inpt = new FileInputStream(fileName);
		IO.println("Minut.fileDump: " + title + fileName);
		Thread.dumpStack();
		byte[] bytes = inpt.readAllBytes();
		for(int i=start;i<stop;i++) {
			IO.println("Minut.fileDump: byte[" + i + "] = " + bytes[i]);
		}
	}
	
}
