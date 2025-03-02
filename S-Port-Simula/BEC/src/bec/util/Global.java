package bec.util;

import java.io.File;
import java.util.Map;

import bec.S_Module;
import bec.descriptor.Descriptor;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.value.ProgramAddress;

public class Global {
	public static boolean INLINE_TESTING = false;
	public static boolean verbose = false;
	public static int traceMode = 0;
	public static boolean SCODE_INPUT_TRACE = false;
	public static boolean PRINT_SVM_CODE = false;
	public static boolean ATTR_INPUT_TRACE = false;
	public static boolean ATTR_OUTPUT_TRACE = false;
	public static boolean ATTR_INPUT_DUMP = false;
	public static boolean ATTR_OUTPUT_DUMP = false;
	public static boolean SEGMENT_INPUT_DUMP = false;
	public static boolean SEGMENT_OUTPUT_DUMP = false;
	public static int EXEC_TRACE = 0;
	
	public static ProgramAddress PSC; // ProgramSequenceControl during execute
//	public static HashMap<String, Segment> SEGMAP;
	public static Map<String, Segment> SEGMAP;

	public static S_Module currentModule;

//	public static Array<SyntaxClass> Display = new Array<SyntaxClass>();
//	public static SyntaxClass getMeaning(int tag) {
//		SyntaxClass x = Display.get(tag);
//		if(x == null) Util.IERR("Missing meaning: " + Scode.edTag(tag));
//		return(x);
//	}
	
	public static Array<Descriptor> DISPL;
	public static Descriptor getMeaning(Tag tag) {
		Descriptor x = DISPL.get(tag.val);
		if(x == null) Util.IERR("Missing meaning: " + tag);
		return(x);
	}
	
	public static void intoDisplay(Descriptor d, int tag) {
		if(tag != 0) {
			
//			if(tag == 54) Util.IERR("");
			
			Descriptor prev = DISPL.get(tag);
			if(prev == null) ; // OK
			else if(prev != d) {
				dumpDISPL("Global.intoDisplay: ");
				Util.IERR("Display-entry is already defined: " + Scode.edTag(tag) + "  " + prev);
			}
			DISPL.set(tag, d);
			if(Global.traceMode != 0) System.out.println("Display(" + Scode.edTag(tag) + ") = " +d);
		}
		if(d == null) Util.IERR("");
//		if(tag == 313) {
//			System.out.println("Global.intoDisplay: "+Scode.edTag(tag) + "  " + d);
//		}
	}

	public static void dumpDISPL(String title) {
		System.out.println("============ "+title+" BEGIN Dump DISPL ================");
		for(int i=32;i<DISPL.size();i++) {
			Descriptor elt =DISPL.get(i);
			System.out.println("  " + i + ": " + elt);
		}
		System.out.println("============ "+title+"ENDOF Dump DISPL ================");
	}

	public static void dumpDisplay(String title) {
		System.out.println("============ "+title+" BEGIN Dump Display ================");
//		for(int i=0;i<Display.size();i++) {
//			SyntaxClass elt =Display.get(i);
//			System.out.println("  " + i + ": " + elt);
//		}
		Util.IERR("");
		System.out.println("============ "+title+"ENDOF Dump Display ================");
	}
	
	public static String scodeSource;	// S-Code source file name
	public static String progIdent;		// S-Code PROG String
	public static String moduleID;		// S-Module ident String or MAIN
	public static String modident;   	// Ident of module being defined
	public static String modcheck;		// Check code of module being defined
	public static String PROGID;		// Ident of program being defined
	public static String outputDIR;		// Attributes and SVM-Code output directory
	
	public final static String rtsDir = "C:/SPORT/RTS/";
	public static int sourceLineNumber;
	public static int curline;		// Current source line number
	public static int nTags; // See: INSERT

	public static Array<Integer> iTAGTAB; // Index xTag --> value iTag (during Module I/O)
	public static Array<Integer> xTAGTAB; // Index iTag --> value xTag (during Module I/O)

//	public static boolean insideRoutine;  // Inside Routine Body indicator

	public static DataSegment CSEG; // Constant Segment
	public static DataSegment DSEG; 
	public static ProgramSegment PSEG; // Current PSEG

	public static ProgramAddress[] DESTAB = new ProgramAddress[64];
	public static int ifDepth;

	public static String getSourceID() {
		File file = new File(scodeSource);
		String name = file.getName();
		int p = name.indexOf('.');
		String s = name.substring(0, p);
		return s;
	}

	public static String getAttrFileName(String modident, String suffix) {
		if(modident == null) {
			int p = scodeSource.indexOf('.');
			String s = scodeSource.substring(0, p);
//			Util.IERR(s + ".svm");
			return s + suffix;
		} else {
			return rtsDir + modident + suffix;
		}
	}
}
