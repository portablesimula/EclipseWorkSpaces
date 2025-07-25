/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package sim.editor.scanner;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

/// A Default Scanner.
/// 
/// Link to GitHub: <a href=
/// "https://github.com/portablesimula/EclipseWorkSpaces/blob/main/SimulaCompiler2/Simula/src/simula/compiler/parsing/DefaultScanner.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class DefaultScanner {

	/// The LineNumberReader.
	private LineNumberReader lineNumberReader;

	// ********************************************************************************
	// *** CONSTRUCTORS: Scanner
	// ********************************************************************************
	/// Default constructor.
	protected DefaultScanner() {}

	/// Constructs a new Default Scanner that produces Tokens scanned from the
	/// specified source.
	/// @param reader The character source to scan
	public DefaultScanner(final Reader reader) {
		this.lineNumberReader = new LineNumberReader(reader);
	}

	// ********************************************************************************
	// ** nextToken
	// ********************************************************************************
	/// Returns the next Token.
	/// @return the next Token
	public Token nextToken() {
		String line = null;
		try { line = lineNumberReader.readLine(); } catch (IOException e) {}
		if (line == null) return (null);
		return (new Token(line + '\n', KeyWord.NEWLINE, lineNumberReader.getLineNumber()));
	}

}
