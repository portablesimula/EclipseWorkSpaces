/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.util;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;

public class Range {
	int low, high;
	
	public Range() {
		parse();
	}

	public void parse() {
		low = Scode.inNumber();
		high = Scode.inNumber();
	}
	
	public String toString() {
		return "RANGE(" + low + ':' + high + ')';
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************
	
	public Range(AttributeInputStream inpt) throws IOException {
		low = inpt.readInt();
		high = inpt.readInt();
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeInstr(Scode.S_RANGE);
		oupt.writeInt(low);
		oupt.writeInt(high);
	}

	public static Range read(AttributeInputStream inpt) throws IOException {
		return new Range(inpt);
	}

	
}
