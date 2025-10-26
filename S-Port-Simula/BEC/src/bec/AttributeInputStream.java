/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

//import PREV.syntaxClass.SyntaxClass;
import bec.descriptor.Kind;
import bec.util.Scode;

/// Attribute Input Stream.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/AttributeInputStream.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class AttributeInputStream {
	DataInputStream inpt;
	public int curinstr;
	
	private boolean TRACE = false; //true;

    public AttributeInputStream(InputStream inpt) throws IOException {
    	this.inpt = new DataInputStream(inpt);
    }

	public void close() throws IOException { inpt.close(); }
    
    public int readKind() throws IOException {
    	int kind = inpt.readByte() & 0xFF;
    	if(TRACE) IO.println("AttributeInputStream.readKind: "+kind+':'+Kind.edKind(kind));
    	return kind;
	}
    
//    public void readInstr() throws IOException {
//    	curinstr = inpt.readByte() & 0xFF;
//    	if(TRACE) IO.println("AttributeInputStream.readInstr: "+curinstr+':'+Scode.edInstr(curinstr));
//	}
//
//    public int readTagID() throws IOException {
//    	String ident = readString();
//    	int tag = inpt.readShort();
//    	Scode.TAGIDENT.set(tag, ident);
//		if(TRACE) IO.println("AttributeInputStream.readTag: "+Scode.edTag(tag));
//		return tag;
//	}
//
//    public int readTag() throws IOException {
//    	int tag = inpt.readShort();
//		if(TRACE) IO.println("AttributeInputStream.readTag: "+Scode.edTag(tag));
//		return tag;
//	}
	
    public boolean readBoolean() throws IOException {
    	boolean b = inpt.readBoolean();
    	if(TRACE) IO.println("AttributeInputStream.readBoolean: "+b);
    	return b;
    }
	
    public int readChar() throws IOException {
    	int i = inpt.readShort();
    	if(TRACE) IO.println("AttributeInputStream.readInt: "+i);
    	return i;
	}
	
    public int readShort() throws IOException {
    	short i = inpt.readShort();
    	if(TRACE) IO.println("AttributeInputStream.readShort: "+i);
    	return i;
	}
	
    public int readInt() throws IOException {
    	int i = inpt.readInt();
    	if(TRACE) IO.println("AttributeInputStream.readInt: "+i);
    	return i;
	}
	
    public float readFloat() throws IOException {
    	float i = inpt.readFloat();
    	if(TRACE) IO.println("AttributeInputStream.readFloat: "+i);
    	return i;
	}
	
    public double readDouble() throws IOException {
    	double i = inpt.readDouble();
    	if(TRACE) IO.println("AttributeInputStream.readDouble: "+i);
    	return i;
	}

    public String readString() throws IOException {
    	int lng = inpt.readShort()-1;
    	if(lng < 0) {
        	if(TRACE) IO.println("AttributeInputStream.readString: null");
    		return null;
    	}
    	if(TRACE) IO.println("AttributeInputStream.readString: lng="+(char)lng+" "+lng);
    	if(lng > 2000) Thread.dumpStack();
    	StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lng; i++) sb.append(inpt.readChar());
    	String s = sb.toString();
    	if(TRACE) IO.println("AttributeInputStream.readString: \""+s+'"');
    	return s;
    }


}
