/*
 * (CC) This work is licensed under a Creative Commons
 * Attribution 4.0 International License.
 *
 * You find a copy of the License on the following
 * page: https://creativecommons.org/licenses/by/4.0/
 */
package make;

import java.util.Vector;

import bec.BecCompiler;
import bec.util.Global;

public final class REMOVE_RunFull_Simula_TestBatch {
	private static long startTimeMs = System.currentTimeMillis();
	
	private static final String smlSCodeDir = "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaTestBatch/src/simulaTestBatch/scode/";


	public static void main(String[] args) {
		Global.INLINE_TESTING = true;
		
//		SVM_CALLSYS.RUNTIME_VERBOSE = true;
//		Global.verbose = true;
//		Global.SCODE_INPUT_TRACE = true;
//		Global.PRINT_GENERATED_SVM_CODE = true;
//		Global.EXEC_TRACE = 1;
//		Global.CALL_TRACE_LEVEL = 2;
//		Global.DUMPS_AT_EXIT = true;
		
		// Set Compiler Options.
//		Option.EXTENSIONS=false;
//		Option.noExecution=true;

		Vector<String> names = new Vector<String>();
		
//		names.add("adHoc00.scd");
//		names.add("adHoc01.scd");
//		names.add("adHoc02.scd");
//		names.add("adHoc03.scd");
//		names.add("adHoc04.scd");
//		names.add("adHoc05.scd");
//			names.add("adHoc06.scd"); 
//			names.add("adHoc07.scd");
//			names.add("adHoc08.scd");
//		names.add("adHoc09.scd");
//		names.add("adHoc10.scd");
		
		names.add("simtst01"); // OK:  Meaningless test of conditional statements
		names.add("simtst02"); // OK:  Test boolean operators/expressions
		names.add("simtst03"); // OK:  Test Text Value Relations
		names.add("simtst04"); // OK:  To test putint and putreal.
		names.add("simtst05"); // OK:  Test Aritmetisk Relations
		names.add("simtst06"); // OK:  Test Mathematical Functions
		names.add("simtst07"); // OK:  Test Mathematical Library
		names.add("simtst08"); // OK:  Test Scope of Variables.
		names.add("simtst09"); // OK:  Test relation operator and some Funtions
		names.add("simtst10"); // OK:  Test Evaluation of Text-Constants

		names.add("simtst11"); // OK:  Text Attributes constant, start, length, pos and main
		names.add("simtst12"); // OK:  Text value and reference relations.
		names.add("simtst13"); // OK:  Text value assignment and text reference assigment.
		names.add("simtst14"); // OK:  Text attributes: pos, setpos, more, getchar and putchar.
		names.add("simtst15"); // OK:  Text generation procedures copy and blanks.
		names.add("simtst16"); // OK:  Standard Text procedures sub and strip.
		names.add("simtst17"); // OK:  Editing and De-editing
		names.add("simtst18"); // OK:  The put- and get-procedures for texts.
		names.add("simtst19"); // OK:  Text concatenation and text expression evaluation.
		names.add("simtst20"); // OK:  Simple tests: integer relations <, <=, =, >=, > and <>

		names.add("simtst21"); // OK:  Arrays of simple types and text.
		names.add("simtst22"); // OK:  Test for-loops with various for-list elements
		names.add("simtst23"); // OK:  Type conversions in for step-until element.
		names.add("simtst24"); // OK:  Conditional statements with more complex Boolean expressions.
		names.add("simtst25"); // OK:  Multiple arithmetic assignment
//		names.add("simtst26"); // OK:  Designational expressions, goto statement and switch declaration.  SORRY, switch element requiring thunk IS NOT IMPLEMENTED
		names.add("simtst27"); // OK:  Test while-loops.
		names.add("simtst28"); // OK:  Paramenter transmission to procedures by value.
		names.add("simtst29"); // OK:  Procedure parameters by value
		names.add("simtst30a"); // OK:  Simple test of Arrays.
		names.add("simtst30"); // OK:  Name parameters (Modified: 2-dim arrays removed)

//		names.add("simtst31"); // OK:  Labels and switches as parameters to procedures                   SORRY, complex switch element IS NOT IMPLEMENTED
		names.add("simtst32"); // OK:  Simple test of formal procedures.
		names.add("simtst33"); // OK:  Test call by reference
		names.add("simtst34"); // OK:  Procedures with procedures as parameters.
		names.add("simtst35"); // OK:  Type procedures as parameters.
		names.add("simtst36"); // OK:  Jensens Device - call by name. TRENGER FIN-TESTING
		names.add("simtst37"); // OK:  Parameter by name and type conversion
		names.add("simtst38"); // OK:  Test the value of type procedures.
		names.add("simtst39"); // OK:  Check that a formal parameter is global to the procedure body.
//		names.add("p40b");     // OK:  Precompile this for Simtst 40.
//		names.add("p40a");     // OK:  Precompile this for Simtst 40.
//		names.add("p40c");     // OK:  Precompile this for Simtst 40.
//		names.add("simtst40"); // OK:  Test separate compilation of procedures.
//
//		names.add("p41");      // OK:  Precompile this for Simtst 41.
//		names.add("simtst41"); // OK:  Name parameter in external procedure.
		names.add("simtst42"); // OK:  Compute "n-fac", using iteration within the procedure.
		names.add("simtst43"); // OK:  Compute "n-fac", using recursion.
		names.add("simtst44"); // OK:  Test text procedure.
		names.add("simtst45"); // OK:  Test object relations - is and in.
		names.add("simtst46"); // OK:  Test the qualification of a function designator.
		names.add("simtst47"); // OK:  Test of this.
		names.add("simtst48"); // OK:  Test of qua.
		names.add("simtst49"); // OK:  For statements in connection blocks.
		names.add("simtst50"); // OK:  Test binding and qualification in Connection Blocks

		names.add("simtst51"); // OK:  Test 'inner'.
		names.add("simtst52"); // OK:  Test 'inner'
		names.add("simtst53"); // OK:  Syntax check on virtual part
		names.add("simtst54"); // OK:  A VERY Simple Simulation
		names.add("simtst55"); // OK:  Test virtual procedures.
		names.add("simtst56"); // OK:  Test virtual procedures.
		names.add("simtst57"); // OK:  Virtual procedure - different number of parameters
		names.add("simtst58"); // err: Goto from within a connection into otherwise
		names.add("simtst59"); // OK:  Test 2-dim Array by value.
		names.add("simtst60"); // OK:  Visibility of protected attributes.

		names.add("simtst61"); // OK:  Remote access to attributes which are protected.
		names.add("simtst62"); // OK:  Test complex use of detach and resume.
		names.add("simtst63"); // OK:  Transmission by name of reference types. 
		names.add("simtst64"); // OK:  Parameter transmission by value to classes.
		names.add("simtst65"); // OK:  Parameter transmission by reference to classes.
		names.add("simtst66"); // OK:  Test corutines and two infiles which read from the same file.
		names.add("simtst67"); // OK:  Simple test of detach, call and resume.
		names.add("simtst68"); // OK:  Test of coroutines. VIRKER 'NESTEN' - Må FINTESTES
		names.add("simtst69"); // OK:  Test complex use of detach, call and resume.
		names.add("simtst70"); // OK:  Text attributes and relations
		
		names.add("simtst71"); // OK:  Test visibility of identifiers.
		names.add("simtst72"); // OK:  Test of formal procedures.
		names.add("simtst73"); // OK:  Test of formal procedures.
		names.add("simtst74"); // OK:  Test resume and detach.
		names.add("simtst75"); // OK:  Name parameters and virtual procedures.
		names.add("simtst76"); // OK:  Test of detach and resume in SIMSET.
		names.add("simtst77"); // OK:  Two infile objects reading from the same external file.
		names.add("simtst78"); // OK:  Test the text procedure filename of class file.
		names.add("simtst79"); // OK:  Test the attribute 'IsOpen' of class file.
		names.add("simtst80"); // OK:  Test the attribute 'IsOpen' of class file.

		names.add("simtst81"); // OK:  Test the value of close.
		names.add("simtst82"); // OK:  Simple test of the operations +, -, *, / and //.
		names.add("simtst83"); // OK:  Name Parameter with EXTREME BI-EFFECTS
		names.add("simtst84"); // OK:  Test DirectBytefile.
		names.add("simtst85"); // OK:  Test Directfile.
		names.add("Separat");  // OK:  Precompile this for Simtst 86.
	    names.add("simtst86"); // OK:  Test separate compilation of Class.
		names.add("simtst87"); // OK:  Specification of Virtual Procedures.
		names.add("simtst88"); // OK:  Visibility of Hidden attributes.
		names.add("simtst89"); // OK:  Test inbytefile and outbytefile.
		names.add("simtst90"); // OK:  Test getfrac/putfrac.

		names.add("simtst91"); // OK:  Test virtual procedures, simple case.
		names.add("simtst92"); // OK:  Test 1-dim Array by value.
		names.add("simtst93"); // OK:  Test Simset - linkage, head, link, into, out, follow and precede
		names.add("simtst94"); // OK:  Test Simset - first, last, empty, cardinal and clear.
		names.add("simtst95"); // OK:  Test Environment Interface.
		names.add("simtst96"); // OK:  Test hidden, protected attributes.
		names.add("simtst97"); // OK:  Test nested hidden and protected.
		names.add("simtst98"); // OK:  Test attribute protection - complex example. Test visibility of labels.
		names.add("simtst99"); // OK:  Test mod, rem, min, max.
		names.add("simtst100"); // OK: Test that put-get-put delivers the identity.
		names.add("simtst101"); // OK: Test Standard Procedure 'sourceline'.
		names.add("simtst102"); // OK: GOTO out of an operating Process
		names.add("simtst103"); // OK: All kinds of Activation Statements
		names.add("simtst104"); // OK: Procedure parameter 'F' by name.
		names.add("simtst105"); // OK: Multiple assignments.
		//names.add("simtst106"); // ERR: Test SIMULATION, complex example.
		names.add("simtst107"); // OK:  Test Process, activation statements, idle, terminated, time.
		names.add("simtst108"); // OK: Simple Co-Routine Sample 1: detach - call
		names.add("simtst109"); // OK: Simple Co-Routine Sample 2: detach - resume
		names.add("simtst110"); // OK: Simple Co-Routine Sample 3: detach - resume - chain
		names.add("simtst111"); // OK: Virtual Label Sample 1
		names.add("simtst112"); // OK: Virtual Label Sample 2
		names.add("simtst113"); // OK: Virtual Switch Sample 1
		names.add("simtst114"); // OK: Switch Statement
		names.add("simtst115"); // OK: Simple QPS-Sample 1
		names.add("simtst116"); // OK: Simple QPS-Sample 2
		names.add("simtst117"); // OK: Simple QPS-Sample 3
		names.add("simtst118"); // OK: Simple QPS-Sample 4
		names.add("ExternalClass1"); // OK:  Precompile this for Simtst 119.
		names.add("ExternalClass2"); // OK:  Precompile this for Simtst 119.
		names.add("simtst119"); // OK: Uses ExternalClass1-2
		names.add("simtst120"); // OK: VERY LOCAL GOTO SAMPLE
		names.add("simtst121"); // OK: LEGAL GOTO out of an operating Process and the enclosing System.
		names.add("simtst122"); // OK: GOTO LABEL IN SUPER CLASS
//		names.add("simtst123"); // OK: GOTO VIRTUAL LABEL                                                 SORRY, switch element requiring thunk IS NOT IMPLEMENTED
		names.add("simtst124"); // OK: GOTO VIRTUAL LABEL
		names.add("simtst125"); // OK: GOTO LABEL WITHIN NESTED COMPOUND STATEMENTS
//		names.add("simtst126"); // OK: GOTO SIMPLE SWITCH                                                 SORRY, switch element requiring thunk IS NOT IMPLEMENTED
		names.add("simtst127"); // OK: Switch (character) Statement
		names.add("simtst128"); // OK: Standard Procedure edit and edfix
		names.add("Precompiled129"); // OK: Precompile this for Simtst 129.
		names.add("simtst129"); // OK: Switch in precompiled class
//		names.add("simtst130"); // OK: Class SimLib, a set of utility procedures from DEC Handbook.       NOT IMPLEMENTED
//		names.add("simtst131"); // OK: Catching Errors                                                    NOT IMPLEMENTED
		names.add("simtst132"); // OK: SPORT Options
		names.add("simtst133"); // OK: Test infile reading with inimage and inrecord.
		names.add("simtst134"); // OK: Outfile with CREATE, APPEND and PURGE.
		names.add("simtst135"); // OK: OutBytefile with CREATE, APPEND and PURGE.
//		names.add("simtst136"); // OK: Directfile with CREATE, APPEND and PURGE.                          NOT IMPLEMENTED
//		names.add("simtst137"); // OK: DirectBytefile with CREATE, APPEND and PURGE.                      NOT IMPLEMENTED
		names.add("simtst138"); // OK: ref() and Real type Arrays.
		names.add("simtst139"); // OK: Test remote Array access.
		names.add("simtst140"); // OK: Test For-Statement with ControlVariable with Type Conversion.
		names.add("simtst141"); // OK: Test For-Statement with SIMSET and SIMULATION list-processing.
		names.add("simtst142"); // OK: Simple test of Random drawing procedures.
		names.add("simtst143"); // OK: Simple test of utility procedure accum.
//		names.add("Precompiled144"); // OK: Precompile this for Simtst 144.                               NOT IMPLEMENTED
//		names.add("simtst144"); // OK: Test 'is', 'in', 'qua' and 'this' in precompiled attribute file    NOT IMPLEMENTED
		names.add("simtst145"); // OK: Test Label parameter to normal and formal procedure
		names.add("simtst146"); // OK: Test text by value to formal and virtual procedure
		names.add("simtst147"); // OK: Test virtual procedure by name
		names.add("simtst148"); // OK: Test procedure min and max with arguments of all types
		names.add("simtst149"); // OK: Test all mode/type parameters to a Class
		names.add("simtst150"); // OK: Test all mode/type parameters except name, ... to a Procedure
		names.add("simtst151"); // OK: Test multiple assignments
		names.add("simtst152"); // OK: Test nested connection statements
		names.add("simtst153"); // OK: Test GOTO ConditionalExpression
		names.add("simtst154"); // OK: Test function result assignment
		names.add("Pre155");    // OK: Precompile this for Simtst 155.
		names.add("simtst155"); // OK: Test inspection in SubBlock in Precompiled inner class
		names.add("simtst156"); // OK: Test arrays in multiple assignments
		names.add("simtst157"); // OK: Test identifier access


		for (String name : names) {
			String fileName = smlSCodeDir + name + ".scd";
//			try {
				new BecCompiler(fileName);
//				System.out.println("RETURN FROM: BecCompiler");
//			} catch (Exception e) { Util.IERR("Compiler Error: " + e); }
		}

		System.out.println("\n--- END OF SportBEC TESTBATCH.scd");
		long timeUsed = System.currentTimeMillis() - startTimeMs;
		System.out.println("\nElapsed Time: Approximately " + timeUsed / 1000 + " sec.");
	}

}
