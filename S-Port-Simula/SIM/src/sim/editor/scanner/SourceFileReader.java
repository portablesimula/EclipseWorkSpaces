/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package sim.editor.scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Stack;

import sim.compiler.Global;
import sim.compiler.Util;

/// Utility class SourceFileReader.
/// 
/// Input Utilities for the Simula Scanner.
/// 
/// Link to GitHub: <a href=
/// "https://github.com/portablesimula/EclipseWorkSpaces/blob/main/SimulaCompiler2/Simula/src/simula/compiler/parsing/SourceFileReader.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public final class SourceFileReader {
	
	/// The stack of readers
	private final Stack<Reader> stack = new Stack<Reader>();
	
	/// The current Reader
	private Reader current; // current source file reader;
	
	/// The stack of file names
	private final Stack<String> nameStack = new Stack<String>();
	
	/// The stack of line numbers
	private final Stack<Integer> lineStack = new Stack<Integer>();

	/// Create a new SourceFileReader.
	/// @param reader the initial underlying Reader
	SourceFileReader(final Reader reader) {
		current = reader;
	}

	/// Read next character.
	/// @return next character
	int read() {
		int c = -1;
		try {
			c = current.read();
			while (c == -1) {
				close();
				if (stack.isEmpty())
					return (-1);
				forceEOF();
				c = current.read();
			}
		} catch (IOException e) {
		}
		return (c);
	}

	/// Insert a file.
	/// @param file the file to be inserted
	void insert(final File file) {
		lineStack.push(Global.sourceLineNumber);
		Global.sourceLineNumber = 1;
		try {
			Reader reader = new InputStreamReader(new FileInputStream(file));
			nameStack.push(Global.insertName);
			Global.insertName = file.getName();
			stack.push(current);
			current = reader;
		} catch (IOException e) {
			Util.IERR("Impossible", e);
		}
	}

	/// Force EOF on the current underlying reader.
	void forceEOF() {
		Global.insertName = nameStack.pop();
		Global.sourceLineNumber = lineStack.pop();
		current = stack.pop();
	}

	/// Close this source file reader
	void close() {
		try {
			current.close();
		} catch (IOException e) {
		}
	}

}
