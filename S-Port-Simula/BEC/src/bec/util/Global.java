/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.util;

import java.io.File;
import java.util.Map;
import java.util.Vector;

import bec.S_Module;
import bec.descriptor.Descriptor;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.segment.Segment;
import bec.value.ProgramAddress;

public class Global {
	
//	public static ConsoleNewEdition console;
//	public static RTS_ConsolePanel console;
	public static Terminal console;
//	public static Writer consoleWriter; // = Reflect.getConsoleWriter();
//	public static Reader consoleReader; // = Reflect.getConsoleReader();
	
	public static Vector<Segment> routineSegments;
	
	public static ProgramAddress PSC; // ProgramSequenceControl during execute
	public static boolean duringEXEC() { return PSC != null; }
//	public static HashMap<String, Segment> SEGMAP;
	public static Map<String, Segment> SEGMAP;

	public static S_Module currentModule;

//	public static Array<SyntaxClass> Display = new Array<SyntaxClass>();
//	public static SyntaxClass getMeaning(int tag) {
//		SyntaxClass x = Display.get(tag);
//		if(x == null) Util.IERR("Missing meaning: " + Scode.edTag(tag));
//		return(x);
//	}
	
	
	public static String scodeSource;	// S-Code source file name
	public static String progIdent;		// S-Code PROG String
	public static String moduleID;		// S-Module ident String or MAIN
	public static String modident;   	// Ident of module being defined
	public static String modcheck;		// Check code of module being defined
	public static String PROGID;		// Ident of program being defined
	
	public final static String rtsDir = "C:/SPORT/RTS/";
	public static String outputDIR = rtsDir; // Attributes and SVM-Code output directory
	public static int sourceLineNumber;
	public static int curline;		// Current source line number
	public static int nTags; // See: INSERT

	public static Array<Integer> iTAGTAB; // Index xTag --> value iTag (during Module I/O)
	public static Array<Integer> xTAGTAB; // Index iTag --> value xTag (during Module I/O)

//	public static boolean insideRoutine;  // Inside Routine Body indicator

	public static DataSegment CSEG; // Constant Segment
	public static DataSegment TSEG; // Constant TextValue Segment
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
