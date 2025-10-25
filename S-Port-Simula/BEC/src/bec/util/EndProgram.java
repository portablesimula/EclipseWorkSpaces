/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.util;

@SuppressWarnings("serial")
public final class EndProgram extends RuntimeException {
	public int exitCode;

	/// Constructs a new RTS_EndProgram exception with the specified detail message. 
	/// @param message the detail message.
	public EndProgram(final int exitCode, final String message) {
		super(message);
		this.exitCode = exitCode;
	}
	
	public String toString() {
		return "EndProgram code=" + exitCode + " " + getMessage();
	}
}
