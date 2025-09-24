package bec.util;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.descriptor.Descriptor;
import bec.statement.InsertStatement;

public class Tag {
	public int val;
	
	public Tag(int val) {
		this.val = val;
	}
	
	public static Tag ofScode() {
		return new Tag(Scode.ofScode());
	}
	
	public String ident() {
		return Scode.TAGIDENT.get(val);
	}
	
	public Descriptor getMeaning() {
		return Global.getMeaning(this);
	}
	
	private static int xTag(int t) { // export range(0:MaxType) tx;
		Integer xx = Global.xTAGTAB.get(t);
		int tx = (xx == null)? 0 : xx;
		return tx + Scode.T_max + 1;
	}
	
	public static void dumpITAGTABLE(String title) {
		System.out.println("============ "+title+" BEGIN Dump iTAGTABLE ================");
		for(int i=0;i<Global.iTAGTAB.size();i++) {
			Integer tx = Global.iTAGTAB.get(i);
			int xx = (tx==null)? 0 : tx;
			System.out.println("iTAGTABLE["+i+"]  iTag:" + Scode.edTag(xx) + "  ==> xTag:" + i);
		}
		System.out.println("============ "+title+" ENDOF Dump iTAGTABLE ================");
	}
	
	public static void dumpXTAGTABLE(String title) {
		System.out.println("============ "+title+" BEGIN Dump xTAGTABLE ================");
		for(int i=32;i<Global.xTAGTAB.size();i++) {
			System.out.println("xTAGTABLE["+i+"]  xTag:" + xTag(i) + "  ==> iTag:" + Scode.edTag(i));
		}
		System.out.println("============ "+title+" ENDOF Dump xTAGTABLE ================");
	}
	
	private static int chgInType(int tx) { // Used by Tag.read
		int t = 0;
		if(tx <= Scode.T_max) t = tx; else {
//			t = tx - Scode.T_max + InsertStatement.current.bias - 2;
			t = tx - Scode.T_max + InsertStatement.current.bias - 1;
		}
		if(Global.ATTR_INPUT_TRACE)
			System.out.println("chgInType xTag:" + tx + " ==> " + Scode.edTag(t));
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
