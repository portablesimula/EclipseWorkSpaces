/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package simula.runtime;

import java.io.File;

///  S-PORT Options
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/SimulaCompiler2/Simula/src/simula/runtime/RTS_SPORT_Option.java"><b>Source File</b></a>.
/// 
/// @author SIMULA Standards Group
/// @author Øystein Myhre Andersen
public class RTS_SPORT_Option {
	/// Default constructor.
	private RTS_SPORT_Option() {}
	
	/// Returns S-PORT Module name
	/// @return S-PORT Module name
	private static String getModuleName() {
		return (new File(SPORT_SourceFileName).getName());
	}

	/// S-PORT: Source directory name
	static String SourceDirName = "C:/GitHub/SimulaCompiler/Simula/src/sport/rts";

	/// S-PORT: TRUE:Do not map sysout to a popup Console
	static boolean noConsole = false;

	/// S-PORT: Source file name
	static String SPORT_SourceFileName = SourceDirName + "/ModuleName";

	/// S-PORT: Source file name
	static String SPORT_SCodeFileName = null;
	
	/// S-PORT: Sysinsert directory name.
	static String SPORT_SysInsertDirName = "C:/WorkSpaces/SPort-System/S_Port/src/sport/rts";

	/// Returns the source file name.
	/// 
	/// Used by ENVIRONMENT'getTextInfo
	/// @return the source file name
	static String getSourceFileName() {
		return (SPORT_SourceFileName);
	}

	/// The S-PORT listing file
	static String ListingFileName = null; //"#sysout";
	
	/// Utility: Print SPORT Options
	public static void print_SPORT_Options() {
		System.out.println("print_SPORT_Options: FEC_Verbose:            " + FEC_Verbose);
		System.out.println("print_SPORT_Options: FEC_TraceLevel:         " + FEC_TraceLevel);
		System.out.println("print_SPORT_Options: ModuleName:             \"" + getModuleName() + '"');
		System.out.println("print_SPORT_Options: SourceDirName:          \"" + SourceDirName + '"');
		System.out.println("print_SPORT_Options: SPORT_SourceFileName:   \"" + SPORT_SourceFileName + '"');
		System.out.println("print_SPORT_Options: SPORT_SysInsertDirName: \"" + SPORT_SysInsertDirName + '"');
		System.out.println("print_SPORT_Options: ListingFileName:        \"" + ListingFileName + '"');
//		System.out.println("print_SPORT_Options: SCodeFileName():        \"" + getSCodeFileName() + '"');
		System.out.println("print_SPORT_Options: FEC_TraceScode:         " + FEC_TraceScode);
	}

//	/// Returns the S-Code file name.
//	/// 
//	/// Used by ENVIRONMENT'getTextInfo
//	/// @return the S-Code file name
//	static String getSCodeFileName() {
//		if(SPORT_SCodeFileName != null)	return SPORT_SCodeFileName;
//		return (createSubfileName("scode", getModuleName() + ".scd"));
//	}
//
//	/// Utility: Create sub-file-name
//	/// @param subdir sub-directory
//	/// @param name file-name
//	/// @return the resulting file-name
//	private static String createSubfileName(String subdir, String name) {
//		String tempFileName = SourceDirName + "/" + subdir + "/" + name;
//		File file = new File(tempFileName);
//		file.getParentFile().mkdirs();
//		return (tempFileName);
//	}
//
//	/// Returns the scratch file name.
//	/// 
//	/// Used by ENVIRONMENT'getTextInfo
//	/// @return the scratch file name
//	static String getScratchFileName() {
//		return (createSubfileName("temp", getModuleName() + ".tmp"));
//	}
//
//	/// Returns the attribute output file name.
//	/// 
//	/// Used by ENVIRONMENT'getTextInfo
//	/// @return the attribute output file name
//	static String getAttributeOutputFileName() {
//		return (createSubfileName("temp", currentModuleID + ".atr"));
//	}
//
//	/// Returns the external attribute file name.
//	/// 
//	/// Used by ENVIRONMENT'getTextInfo
//	/// @return the external attribute file name
//	static String getExternalAttributeFileName() {
//		// 12: What is the name of the attribute file for an external declaration?
//		// Before this request is issued, the environment will have received the
//		// identifier (extIdent) and the
//		// external identifier (extFile) for the external declaration through the
//		// routine give_textinfo
//		if (extFile != null) {
//			return (extFile);
//		}
//		String fileName = createSubfileName("temp", extIdent + ".atr");
//		File file = new File(fileName);
//		if (!file.exists()) {
//			String name = extIdent;
//			int i = name.indexOf('.');
//			if (i > 0)
//				name = name.substring(0, i);
//			fileName = SPORT_SysInsertDirName + "/temp/" + name + ".atr";
//			file = new File(fileName);
//		}
//		return (fileName);
//	}

	/// Selectors for conditional compilation.
	static String Selectors = "AZ";

//	// The following file is created when front-end compiling the RTS:
//	/// Name of attribute file for the predefined classes etc. 
//	/// 
//	/// NOTE: This file is created when front-end compiling the RTS:
//	static String PredefFileName = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/Attrs/FEC/PREDEF.atr";
////	static String PredefFileName = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaFEC/Attrs/FEC/PREDEF.atr";
//
//	/// Name of a file containing seldom used information for the front end compiler,
//	/// such as extended error messages.
//	static String XmessageFileName = "C:/WorkSpaces/SPort-System/S_Port/src/sport/fec/FECERROR.txt";
//	
//	/** Used by getIntInfo. */ static int GenerateScode = 1;
//	/** Used by getIntInfo. */ static int MaxErrors = 50;
//	/** Used by getIntInfo. */ static int GiveNotes = 1;
////	/** Used by getIntInfo. */ static int TraceLevel = 0;
//	/** Used by getIntInfo. */ static int Recompilation = 0;
//	
//	/**
//	 *  Used by getIntInfo.
//	 *  Result: 0 - minimal information for error reporting.
//	 *  		1 - information at the module and block level, but no information about the attributes.
//	 *  		2 - complete information generated, including information about all attributes.	 */
//	static int SimobLevel = 0;
	
	/** Used by getIntInfo. */ static int FEC_Verbose = 0;
	/** Used by getIntInfo. */ static int FEC_TraceScode = 0;
	/** Used by getIntInfo. */ static int FEC_TraceLevel = 0;

//	/// Used by giveTextInfo(1) The identifier of a class or procedure being separately compiled.
//	/// E.g class or procedure identifier
//	static String currentModuleID;
//
//	/// Used by giveTextInfo(2)The identifier given in an external declaration that is being processed.
//	/// E.g class or procedure identifier
//	static String extIdent;
//
//	/// Used by giveTextInfo(3) The external identification given in an external declaration that
//	/// is being processed. E.g. FileName
//	static String extFile;
	
	
}

