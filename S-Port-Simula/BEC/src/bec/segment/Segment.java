package bec.segment;

import java.io.IOException;
import java.util.Vector;

import bec.AttributeOutputStream;
import bec.descriptor.Kind;
import bec.util.Global;
import bec.util.Util;

public class Segment { // extends Descriptor {
	public String ident;
	int segmentKind; // K_SEG_DATA, K_SEG_CONST, K_SEG_CODE

	public Segment(String ident, int segmentKind) {
		if(Global.SEGMAP.get(ident) != null) Util.IERR("Segment allready defined: " + ident);
		this.ident = ident.toUpperCase();
		Global.SEGMAP.put(this.ident, this);
		this.segmentKind = segmentKind;
	}

	public static Segment find(String ident) {
		return Global.SEGMAP.get(ident);
	}

	public static Segment lookup(String ident) {
		Segment seg = Global.SEGMAP.get(ident);
		if(seg == null) {
			Segment.listAll();
			Util.IERR("Can't find Segment \"" + ident + '"');
		}
		return seg;
	}
	
//	public static void writeSegments(AttributeOutputStream oupt) {
//		listAll();
//		try {
//			for(Segment seg:SEGMAP.values()) {
//				if(!seg.inserted)
//					seg.write(oupt);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			Util.IERR("");
//		}
////		Util.IERR("");
//	}
	
	public static void listAll() {
		for(Segment seg:Global.SEGMAP.values()) {
			System.out.println("   " + seg);
		}
	}

	public void dump(String title) {
	}
	
	public static void dumpAll(String title) {
		for(Segment seg:Global.SEGMAP.values()) {
			seg.dump(title);
		}
	}
	
	public String toString() {
		return Kind.edKind(segmentKind) + ':' + segmentKind + " \"" + ident + '"';
	}


	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	public void write(AttributeOutputStream oupt) throws IOException {
		Util.IERR("Method 'write' needs a redefinition in "+this.getClass().getSimpleName());
	}

//	public static Descriptor read(AttributeInputStream inpt, int kind) throws IOException {
//		Util.IERR("Static Method 'readObject' needs a redefiniton");
//		return(null);
//	}


}
