/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import bec.descriptor.Kind;
import bec.util.Scode;
import bec.virtualMachine.SVM_Instruction;

/// Attribute Output Stream.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/AttributeOutputStream.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class AttributeOutputStream {
	DataOutputStream oupt;

	private boolean TRACE = false; //true;

    public AttributeOutputStream(OutputStream oupt) throws IOException {
    	this.oupt = new DataOutputStream(oupt);
    }

	public void close() throws IOException { oupt.flush(); oupt.close(); }

    public void writeKind(int i) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeKind: "+i+':'+Kind.edKind(i));
		if(i > Scode.S_max || i < 0) throw new IllegalArgumentException("Argument = "+i);
		oupt.writeByte(i);
	}

//    public void writeInstr(int i) throws IOException {
//		if(TRACE) IO.println("AttributeOutputStream.writeInstr: "+i+':'+Scode.edInstr(i));
//		if(i > Scode.S_max || i < 0) throw new IllegalArgumentException("Argument = "+i);
//		oupt.writeByte(i);
//	}

    public void writeOpcode(int i) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeOpcode: "+i+':'+SVM_Instruction.edOpcode(i));
		if(i > SVM_Instruction.iMax || i <= 0) throw new IllegalArgumentException("Argument = "+i);
		oupt.writeByte(i);
	}

//    public void writeTag(int i) throws IOException {
//		if(TRACE) IO.println("AttributeOutputStream.writeTag: "+Scode.edTag(i));
//		oupt.writeShort(i);
//	}
//
//    public void writeTagID(int i) throws IOException {
//		if(TRACE) IO.println("AttributeOutputStream.writeTag: "+Scode.edTag(i));
//		writeString(Scode.TAGIDENT.get(i));
//		oupt.writeShort(i);
//	}
	
    public void writeBoolean(boolean b) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeBoolean: "+b);
		oupt.writeBoolean(b);
	}

    public void writeShort(int i) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeShort: "+i);
		if(i > Short.MAX_VALUE || i < Short.MIN_VALUE) throw new IllegalArgumentException("Argument = "+i);
		oupt.writeShort(i);			
	}

    public void writeInt(int i) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeInt: "+i);
		oupt.writeInt(i);			
	}

    public void writeFloat(float i) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeFloat: "+i);
		oupt.writeFloat(i);			
	}

    public void writeDouble(double i) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeDouble: "+i);
		oupt.writeDouble(i);			
	}

    public void writeChar(int i) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeChar: "+i);
		oupt.writeShort(i);			
	}

    public void writeString(String s) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeString: "+s);
		if(s == null) {
			oupt.writeShort(0);
		} else {
			int lng = s.length();
			oupt.writeShort(lng+1);
			for(int i=0;i<lng;i++) {
				oupt.writeChar(s.charAt(i));
			}
		}
	}

}