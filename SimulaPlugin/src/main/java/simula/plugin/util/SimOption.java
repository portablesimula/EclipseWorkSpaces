/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package simula.plugin.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/// Compile Time Options.
/// 
/// Link to GitHub: <a href=
/// "https://github.com/portablesimula/EclipseWorkSpaces/blob/main/SimulaCompiler2/Simula/src/simula/compiler/utilities/Option.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public final class SimOption {

	/// The Compiler Modes.
	public enum CompilerMode { 
    	/** Generate Java source and use Java compiler to generate JavaClass files. */					viaJavaSource,
    	/** Generate JavaClass files directly. No Java source files are generated. */ 					directClassFiles,
    	/** Generate ClassFile byte array and load it directly. No intermediate files are created. */	simulaClassLoader
    }

	/// The Compiler mode.
	public static CompilerMode compilerMode;
	
	/// Source file is case sensitive.
	public static boolean CaseSensitive=false;
	
	/// Output messages about what the compiler is doing.
	public static boolean verbose = false; 
	
	/// Generate warning messages
	public static boolean WARNINGS=true;

	/// TRUE:Do not create popUps at runtime
	public static boolean noPopup = false; 
	
	/// true: Don't execute generated .jar file
	public static boolean noExecution = false;
	
	/// false: Disable all language extensions. In other words,
	/// follow the Simula Standard literally
	public static boolean EXTENSIONS=true;

    /** Runtime Option */ public boolean VERBOSE = false;
    /** Runtime Option */ public boolean BLOCK_TRACING = false;
    /** Runtime Option */ public boolean GOTO_TRACING = false;
    /** Runtime Option */ public boolean QPS_TRACING = false;
    /** Runtime Option */ public boolean SML_TRACING = false;

    /// Testing and debugging options
	public static class internal {
		/** Default Constructor: NOT USED */ public internal() { Util.IERR(); }

		/// Used to insert code to enforce 'stack size mismatch'
		public static boolean TESTING_STACK_SIZE = false;

		/// List generated .class files
		public static boolean LIST_GENERATED_CLASS_FILES = false;

		/// INLINE_TESTING on/off
		public static boolean INLINE_TESTING = false; 
		
		/// Used by Java-Coding to save the generated .java files.
		/// If not set, a temp directory is used/created.
		public static File keepJava = null;


		// Overall TRACING Options
		/** Debug option */	public static boolean TRACING=false;
		/** Debug option */	public static boolean DEBUGGING=false;		// Set by EditorMenues - doDebugAction

		// Scanner Trace Options
		/** Debug option */	public static boolean TRACE_SCAN=false;
		/** Debug option */	public static boolean TRACE_COMMENTS=false;

		// Parser Trace Options
		/** Debug option */	public static boolean TRACE_PARSE=false;
		/** Debug option */	public static int     PRINT_SYNTAX_TREE=0;
		/** Debug option */	public static boolean TRACE_ATTRIBUTE_OUTPUT=false;
		/** Debug option */	public static boolean TRACE_ATTRIBUTE_INPUT=false;

		// Checker Trace Options
		/** Debug option */	public static boolean TRACE_CHECKER=false;
		/** Debug option */	public static boolean TRACE_CHECKER_OUTPUT=false;
		/** Debug option */	public static int     TRACE_FIND_MEANING=0;

		// Java Coder Options
		/** Debug option */	public static boolean TRACE_CODING=false;         // Only when .java output
		/** Debug option */	public static boolean GNERATE_LINE_CALLS=false;   // Only when .java output

		// Byte code engineering Options
		/** Debug option */	public static boolean TRACE_BYTECODE_OUTPUT=false;
		/** Debug option */	public static boolean LIST_REPAIRED_INSTRUCTION_LIST=false;
		/** Debug option */	public static boolean TRACE_REPAIRING=false;
		/** Debug option */	public static boolean LIST_INPUT_INSTRUCTION_LIST=false;
		/** Debug option */	public static boolean TRACE_REPAIRING_INPUT=false;
		/** Debug option */	public static boolean TRACE_REPAIRING_OUTPUT=false;

		/** Runtime Options */ public static String SOURCE_FILE="";
		/** Runtime Options */ public static String RUNTIME_USER_DIR="";
		
		/// Initiate Compiler options
		public static void InitCompilerOptions() {

			internal.TRACING=false;
			internal.DEBUGGING=false;

			// Scanner Trace Options
			internal.TRACE_SCAN=false;
			internal.TRACE_COMMENTS=false;

			// Parser Trace Options
			internal.TRACE_PARSE=false;

			// Checker Trace Options
			internal.TRACE_CHECKER=false;
			internal.TRACE_CHECKER_OUTPUT=false;

			// Coder Trace Options
			internal.TRACE_CODING=false;
		}

	}
	
	/// The default constructor
    public SimOption() {}
	
	/// Initiate Compiler options.
	public static void InitCompilerOptions() {
//		CompilerMode compilerMode=CompilerMode.viaJavaSource;
		compilerMode=CompilerMode.directClassFiles;
//		compilerMode=CompilerMode.simulaClassLoader;
		SimOption.CaseSensitive=false;
		SimOption.verbose = false;
		SimOption.noExecution = false;
		SimOption.WARNINGS=true;
		SimOption.EXTENSIONS=true;
		
		internal.InitCompilerOptions();
	}

    /// Initiate Runtime options with default values.
    public void InitRuntimeOptions() {
        VERBOSE = false;
//		USE_CONSOLE=true;
        BLOCK_TRACING = false;
        GOTO_TRACING = false;
        QPS_TRACING = false;
        SML_TRACING = false;
    }

    /// Editor Utility: Set Compiler Mode.
    public JPanel selectModeOption() {
        JPanel panel=new JPanel();
        panel.setBackground(Color.white);
        JCheckBox but1 = checkBox("viaJavaSource","Generate Java source and use Java compiler to generate JavaClass files.");
        JCheckBox but2 = checkBox("directClassFiles","Generate JavaClass files directly. No Java source files are generated.");
        JCheckBox but3 = checkBox("simulaClassLoader","Generate ClassFile byte array and load it directly. No intermediate files are created.");

        if(compilerMode == CompilerMode.viaJavaSource) but1.setSelected(true);
        else if(compilerMode == CompilerMode.directClassFiles) but2.setSelected(true);
        else if(compilerMode == CompilerMode.simulaClassLoader) but3.setSelected(true);

        ButtonGroup buttonGroup = new ButtonGroup();
        panel.add(but1); buttonGroup.add(but1);
        panel.add(new JLabel("   The Simula Compiler will generate Java source files and use"));
        panel.add(new JLabel("   the Java compiler to generate JavaClass files which in turn"));
        panel.add(new JLabel("   are collected together with the Runtime System into the"));
        panel.add(new JLabel("   resulting executable jar-file."));
        panel.add(new JLabel(" "));
        panel.add(but2); buttonGroup.add(but2);
        panel.add(new JLabel("   The Simula Compiler will generate JavaClass files directly"));
        panel.add(new JLabel("   which in turn are collected together with the Runtime System"));
        panel.add(new JLabel("   into the resulting executable jar-file."));
        panel.add(new JLabel("   No Java source files are generated."));
        panel.add(new JLabel(" "));
        panel.add(but3); buttonGroup.add(but3);
        panel.add(new JLabel("   The Simula Compiler will generate ClassFile byte array and"));
        panel.add(new JLabel("   load it directly. No intermediate files are created."));
        panel.add(new JLabel(" "));
        panel.add(new JLabel("   NOTE:   In this mode, the editor will terminate after the first"));
        panel.add(new JLabel("                  program execution"));
        panel.add(new JLabel(" "));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       Util.optionDialog(panel,"Select Compiler Mode",JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,"Ok");
//        Global.storeWorkspaceProperties();
        return panel;
    }

    /// Editor Utility: Set Compiler Mode.
    /// @param id the mode String.
    public static void setCompilerMode(String id) {
        if(id.equals("viaJavaSource")) {
            compilerMode = CompilerMode.viaJavaSource;
        } else if(id.equals("directClassFiles")) {
            compilerMode = CompilerMode.directClassFiles;
        } else if(id.equals("simulaClassLoader")) {
            compilerMode = CompilerMode.simulaClassLoader;
        }
    }

    public JPanel selectCTOptions() {
        JPanel panel=new JPanel();
//        panel.setBackground(Color.white);
        panel.add(new JLabel("Compiler Options:"));
        panel.add(checkBox("CaseSensitive","Source file is case sensitive."));
        panel.add(checkBox("Verbose","Output messages about what the compiler is doing"));
        panel.add(checkBox("Warnings","Generate warning messages"));
        panel.add(checkBox("Extensions","Disable all language extensions. In other words, follow the Simula Standard literally"));
        panel.add(checkBox("noExecution","Don't execute generated .jar file"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton moreButton = new JButton("Select more options");
        // Add an ActionListener to the button
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("SimulaSettingsEditor'Button clicked!");
                // You can add more complex logic here, like updating a label, opening a new window, etc.
               selectMoreCTOptions();
            }
        });
        panel.add(moreButton);

        Util.optionDialog(panel,"Select Runtime Options",JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,"Ok");
//    	Global.storeWorkspaceProperties();
        return panel;
    }

    public JPanel selectMoreCTOptions() {
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBackground(Color.white);
        panel.add(new JLabel("Overall TRACING Options:"));
        panel.add(checkBox("TRACING",""));
        panel.add(checkBox("DEBUGGING",""));	// Set by EditorMenues - doDebugAction

        panel.add(new JLabel("Scanner Trace Options:"));
        panel.add(checkBox("TRACE_SCAN",""));
        panel.add(checkBox("TRACE_COMMENTS",""));

        panel.add(new JLabel("Parser Trace Options:"));
        panel.add(checkBox("TRACE_PARSE",""));
        panel.add(checkBox("PRINT_SYNTAX_TREE",""));
        panel.add(checkBox("TRACE_ATTRIBUTE_OUTPUT",""));
        panel.add(checkBox("TRACE_ATTRIBUTE_INPUT",""));

        panel.add(new JLabel("Checker Trace Options:"));
        panel.add(checkBox("TRACE_CHECKER",""));
        panel.add(checkBox("TRACE_CHECKER_OUTPUT",""));
        panel.add(checkBox("TRACE_FIND_MEANING",""));
//        /** Debug option */	public static int     TRACE_FIND_MEANING=0;

        panel.add(new JLabel("Java Coder Options:"));
        panel.add(checkBox("TRACE_CODING",""));         // Only when .java output
        panel.add(checkBox("GNERATE_LINE_CALLS",""));   // Only when .java output

        panel.add(new JLabel("Byte code engineering Options:"));
        panel.add(checkBox("TRACE_BYTECODE_OUTPUT",""));
        panel.add(checkBox("LIST_REPAIRED_INSTRUCTION_LIST",""));
        panel.add(checkBox("TRACE_REPAIRING",""));
        panel.add(checkBox("LIST_INPUT_INSTRUCTION_LIST",""));
        panel.add(checkBox("TRACE_REPAIRING_INPUT",""));
        panel.add(checkBox("TRACE_REPAIRING_OUTPUT",""));

        Util.optionDialog(panel,"Select Runtime Options",JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,"Ok");
//    	Global.storeWorkspaceProperties();
        return panel;
    }
    public JPanel selectRTOptions() {
        JPanel panel=new JPanel();
//        panel.setBackground(Color.white);
        panel.add(new JLabel("Runtime Options:"));
        panel.add(checkBox("Verbose", "Output messages about what happens at runtime"));
        panel.add(checkBox("BLOCK_TRACING", "Trace begin end of all blocks"));
        panel.add(checkBox("GOTO_TRACING", "Trace goto label"));
        panel.add(checkBox("QPS_TRACING", "Trace detach, call and resume"));
        panel.add(checkBox("SML_TRACING", "Trace Simulation actions"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Util.optionDialog(panel,"Select Runtime Options",JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,"Ok");
//    	Global.storeWorkspaceProperties();
        return panel;
    }

    public JPanel selectOptionsPanel() {
        JPanel panel = new JPanel();

        JButton modeButton = new JButton("Set Compiler Mode");
        modeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectModeOption();
            }
        });
        panel.add(modeButton);

        JButton ctButton = new JButton("Set Compiler Options");
        ctButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectCTOptions();
            }
        });
        panel.add(ctButton);

        JButton rtButton = new JButton("Set Runtime Options");
        rtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectRTOptions();
            }
        });
        panel.add(rtButton);

        return panel;
    }

    /// Editor Utility: Create a checkBox with tooltips.
    /// @param id option id
    /// @param tooltip option's tooltip or null
    /// @return the resulting check box
    private JCheckBox checkBox(String id,String tooltip) {
//        return checkBox(id, tooltip,Option.getOption(id));
        return checkBox(id, tooltip, getOption(id));
    }

    /// Returns the option name 'id'
    /// @param id option id
    /// @return the option name 'id'
    private boolean getOption(String id) {
        if(id.equalsIgnoreCase("VERBOSE")) return(VERBOSE);
        if(id.equalsIgnoreCase("BLOCK_TRACING")) return(BLOCK_TRACING);
        if(id.equalsIgnoreCase("GOTO_TRACING")) return(GOTO_TRACING);
        if(id.equalsIgnoreCase("QPS_TRACING")) return(QPS_TRACING);
        if(id.equalsIgnoreCase("SML_TRACING")) return(SML_TRACING);
        return(false);
    }

    /// Set the option named 'id' to the given value
    /// @param id option id
    /// @param val new option value
    private void setOption(String id, boolean val) {
        if(id.equalsIgnoreCase("VERBOSE")) VERBOSE=val;
        if(id.equalsIgnoreCase("BLOCK_TRACING")) BLOCK_TRACING=val;
        if(id.equalsIgnoreCase("GOTO_TRACING")) GOTO_TRACING=val;
        if(id.equalsIgnoreCase("QPS_TRACING")) QPS_TRACING=val;
        if(id.equalsIgnoreCase("SML_TRACING")) SML_TRACING=val;
    }

    /// Editor Utility: Create a checkBox with tooltips.
    /// @param id option id.
    /// @param tooltip option's tooltip or null.
    /// @param selected true: this checkBox is selected.
    /// @return the resulting check box.
    private static JCheckBox checkBox(String id, String tooltip, boolean selected) {
        JCheckBox item = new JCheckBox(id);
        item.setBackground(Color.white);
        item.setSelected(selected);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                if(id.equals("viaJavaSource") || id.equals("directClassFiles") || id.equals("simulaClassLoader")) {
//                    if(Option.verbose) Util.println("Compiler Mode: "+id);
//                    Option.setCompilerMode(id);
//                } else {
//                    Option.setOption(id,item.isSelected());
//                }
            }});
        if(tooltip != null) item.setToolTipText(tooltip);
        item.addMouseListener(new MouseAdapter() {
            Color color = item.getBackground();
            @Override
            public void mouseEntered(MouseEvent me) {
                color = item.getBackground();
                item.setBackground(Color.lightGray); // change the color to lightGray when mouse over a button
            }
            @Override
            public void mouseExited(MouseEvent me) {
                item.setBackground(color);
            }
        });
        return(item);
    }


}