/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.descriptor;

import java.io.IOException;

import bec.AttributeInputStream;
import bec.AttributeOutputStream;
import bec.util.Option;
import bec.util.Type;
import bec.util.Util;
import bec.util.Scode;
import bec.util.Tag;

/// Attribute descriptor.
///
/// S-CODE:
///
/// attribute_definition
///		::= attr attr:newtag quantity_descriptor
///
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/descriptor/Attribute.java"><b>Source File</b></a>.
/// 
/// @author S-Port: Definition of S-code
/// @author Øystein Myhre Andersen
public class Attribute extends Descriptor {
	public Type type;
	public int rela;
	public int size;
	public int repCount;
	
	private Attribute(int kind, Tag tag, Type type) {
		super(kind, tag);
		this.type = type;
		if(type == null) Util.IERR("NEW Attribute: Missing type");
	}
	
	public Attribute(int rela) {
		super(Kind.K_Attribute, Tag.ofScode());
		this.rela = rela;
		this.type = Type.ofScode();
		this.size = type.size();
		this.repCount = (Scode.accept(Scode.S_REP)) ? Scode.inNumber() : 1;
	}

	public static Attribute ofLocalVariable(Tag tag, Type type) {
		Util.IERR("DETTE MÅ RETTES");
		return new Attribute(Kind.K_LocalVar, tag, type);
	}
	
	public int allocSize() {
		return size * repCount;
	}
	
	@Override
	public void print(final String indent) {
		IO.println(indent + this);
	}
	
	@Override
	public String toString() {
		return "Attribute: " + tag + " rela=" + rela + ", size=" + size + ", repCount=" + repCount;
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

	@Override
	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("LocDescr.Write: " + this);
		oupt.writeKind(kind);
		tag.write(oupt);
		type.write(oupt);
		
		oupt.writeShort(rela);
		oupt.writeShort(repCount);
		oupt.writeShort(size);
	}

	public static Attribute read(AttributeInputStream inpt, int kind) throws IOException {
		Tag tag = Tag.read(inpt);
		Type type = Type.read(inpt);
		
		Attribute loc = new Attribute(kind, tag, type);
		loc.rela = inpt.readShort();
		loc.repCount = inpt.readShort();
		loc.size = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("LocDescr.Read: " + loc);
		return loc;
	}


}
