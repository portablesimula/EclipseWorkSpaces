/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.scode;

import java.io.IOException;

import bec.descriptor.Descriptor;
import bec.descriptor.Display;
import bec.statement.InsertStatement;
import bec.util.AttributeInputStream;
import bec.util.AttributeOutputStream;
import bec.util.Global;
import bec.util.Option;

/// Tag.
///
/// 
/// 	tag
///			::= An ordinal (the "tag-value") associated with a descriptor. See section 2.4.
///			::= The number zero followed by an ordinal (the "tag value") and an identifying string.
///
///	The second form is intended for debugging purposes and is used to associate an identification
///	with the tag.
///
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/scode/Tag.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class Tag {
	
	/// Tag's value
	public int val;

	public final static int TAG_VOID  = 0;
	public final static int TAG_BOOL  = 1;
	public final static int TAG_CHAR  = 2;
	public final static int	TAG_INT   = 3;
	public final static int TAG_SINT  = 4;
	public final static int TAG_REAL  = 5;
	public final static int TAG_LREAL = 6;
	public final static int TAG_AADDR = 7;
	public final static int TAG_OADDR = 8;
	public final static int TAG_GADDR = 9;
	public final static int TAG_PADDR = 10;
	public final static int TAG_RADDR = 11;
	public final static int TAG_SIZE  = 12;
	public final static int TAG_TEXT  = 13;
	public final static int T_max=13; // Max value of predefined type
	public final static int TAG_STRING  = 32;

	public Tag(int val) {
		this.val = val;
	}
	
	public static Tag ofScode() {
		return new Tag(Scode.inTag());
	}
	
	public String ident() {
		return Scode.TAGIDENT.get(val);
	}
	
	public Descriptor getMeaning() {
		return Display.getMeaning(this.val);
	}
	
	private static int xTag(int t) { // export range(0:MaxType) tx;
		Integer xx = Global.xTAGTAB.get(t);
		int tx = (xx == null)? 0 : xx;
		return tx + T_max + 1;
	}
	
	public static void dumpITAGTABLE(String title) {
		IO.println("============ "+title+" BEGIN Dump iTAGTABLE ================");
		for(int i=0;i<Global.iTAGTAB.size();i++) {
			Integer tx = Global.iTAGTAB.get(i);
			int xx = (tx==null)? 0 : tx;
			IO.println("iTAGTABLE["+i+"]  iTag:" + Scode.edTag(xx) + "  ==> xTag:" + i);
		}
		IO.println("============ "+title+" ENDOF Dump iTAGTABLE ================");
	}
	
	public static void dumpXTAGTABLE(String title) {
		IO.println("============ "+title+" BEGIN Dump xTAGTABLE ================");
		for(int i=32;i<Global.xTAGTAB.size();i++) {
			IO.println("xTAGTABLE["+i+"]  xTag:" + xTag(i) + "  ==> iTag:" + Scode.edTag(i));
		}
		IO.println("============ "+title+" ENDOF Dump xTAGTABLE ================");
	}
	
	private static int chgInType(int tx) { // Used by Tag.read
		int t = 0;
		if(tx <= T_max) t = tx; else {
			t = tx - T_max + InsertStatement.current.bias - 1;
		}
		if(Option.ATTR_INPUT_TRACE)
			IO.println("chgInType xTag:" + tx + " ==> " + Scode.edTag(t));
//		Util.IERR("SJEKK DETTE");
		return t;
	}

	public String toString() {
		return Scode.edTag(val);
	}
	

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeString(Scode.TAGIDENT.get(val));
		oupt.writeShort(xTag(val));
	}
	

	public static Tag read(AttributeInputStream inpt) throws IOException {
		String ident = inpt.readString();
		int tag = inpt.readShort();
		tag = chgInType(tag);
		Scode.TAGIDENT.set(tag, ident);
		return new Tag(tag);
	}
	
}
