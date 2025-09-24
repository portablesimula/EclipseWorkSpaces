package iapx;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;

import iapx.util.Scode;

//import bec.descriptor.Kind;
//import bec.util.Scode;
//import bec.virtualMachine.RTRegister;
//import bec.virtualMachine.SVM_Instruction;

public class AttributeOutputStream {
	DataOutputStream oupt;

	public static boolean TRACE = true;//false; //true;

    public AttributeOutputStream(OutputStream oupt) throws IOException {
    	this.oupt = new DataOutputStream(oupt);
    }
	
//	public void flush() throws IOException { oupt.flush(); }

	public void close() throws IOException { oupt.flush(); oupt.close(); }

//    public void writeKind(Kind kind) throws IOException {
//		if(TRACE) IO.println("AttributeOutputStream.writeKind: " + kind);
//		if(kind.ordinal() == 0) Util.IERR("");
//		Kind.write(kind, this);
//	}
//
//    public void writeKind(int i) throws IOException {
//		if(TRACE) IO.println("AttributeOutputStream.writeKind: "+i+':'+Global.edObjectKind(i));
////		if(i > Scode.S_max || i < 1) throw new IllegalArgumentException("Argument = "+i);
//		if(i > Scode.S_max || i < 0) throw new IllegalArgumentException("Argument = "+i);
//		oupt.writeByte(i);
//	}

    public void writeInstr(Scode instr) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeInstr: "+instr+':'+instr.ordinal());
		if(instr.ordinal() > Scode.S_max || instr.ordinal() < 0) throw new IllegalArgumentException("Argument = "+instr);
		oupt.writeByte(instr.ordinal());
	}

//    public void writeOpcode(int i) throws IOException {
//		if(TRACE) IO.println("AttributeOutputStream.writeOpcode: "+i+':'+SVM_Instruction.edOpcode(i));
//		if(i > SVM_Instruction.iMax || i <= 0) throw new IllegalArgumentException("Argument = "+i);
//		oupt.writeByte(i);
//	}
//
//    public void writeReg(int i) throws IOException {
//		if(TRACE) IO.println("AttributeOutputStream.writeReg: "+i+':'+RTRegister.edReg(i));
//		if(i > RTRegister.rMax || i < 0) throw new IllegalArgumentException("Argument = "+i);
//		oupt.writeByte(i);
//	}

//    public void writeTagID(int i) throws IOException {
//		if(TRACE) IO.println("AttributeOutputStream.writeTag: "+Scode.edTag(i));
//		writeString(Scode.TAGIDENT.get(i));
//		oupt.writeShort(i);
//	}
    
    public void writeBitSet(BitSet bitSet) throws IOException {
    	byte[] bytes = bitSet.toByteArray();
    	int n = bytes.length;
//		writeShort(n);	
//		for(int i=0;i<n;i++) writeShort(bytes[i]);	
		writeShort(n);	
		for(int i=0;i<n;i++) oupt.writeByte(bytes[i]);	
    }
	
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

//    public void writeConstant(Object c) throws IOException {
//		if(TRACE) IO.println("AttributeOutputStream.writeConstant: "+c);
//		if(c == null)						{ oupt.writeByte(Type.T_VOID); }
//		else if(c instanceof Boolean b)		{ oupt.writeByte(Type.T_BOOLEAN);   oupt.writeBoolean(b);	}
//		else if(c instanceof Integer i)		{ oupt.writeByte(Type.T_INTEGER);   oupt.writeShort(i);	}
//		else if(c instanceof Long li)		{ oupt.writeByte(Type.T_INTEGER);   oupt.writeShort(li.intValue()); }
//		else if(c instanceof Character k)	{ oupt.writeByte(Type.T_CHARACTER); oupt.writeChar(k); }
//		else if(c instanceof Float f)		{ oupt.writeByte(Type.T_REAL);      oupt.writeFloat(f); }
//		else if(c instanceof Double f)		{ oupt.writeByte(Type.T_LONG_REAL); oupt.writeDouble(f); }
//		else if(c instanceof String s)		{ oupt.writeByte(Type.T_TEXT);      writeString(s); }
//		else Util.IERR(""+c.getClass().getSimpleName());
//	}

    public void writeString(String s) throws IOException {
		if(TRACE) IO.println("AttributeOutputStream.writeString: "+s);
		if(s == null) {
			oupt.writeShort(0);
//			IO.println("AttributeOutputStream.writeString: writeShort:" + 0);
		} else {
			int lng = s.length();
			oupt.writeShort(lng+1);
//			IO.println("AttributeOutputStream.writeString: writeShort:" + (lng+1));
			for(int i=0;i<lng;i++) {
				oupt.writeChar(s.charAt(i));
//				IO.println("AttributeOutputStream.writeString: writeChar:" + s.charAt(i));
			}
		}
	}

//	public void writeObjectList(ObjectList<?> list) throws IOException {
//		ObjectList.write(list, this);
//	}
//
//    public void writeObj(SyntaxClass obj) throws IOException {
//		if(obj == null) {
//			if(TRACE) IO.println("AttributeOutputStream.writeObj: null");
//			writeKind(ObjectKind.NULL);
//		} else if(obj.OBJECT_SEQU != 0) {
//			if(TRACE) IO.println("AttributeOutputStream.writeObj: ObjectReference "+(obj.OBJECT_SEQU));
//			writeKind(ObjectKind.ObjectReference);
//			oupt.writeShort(obj.OBJECT_SEQU);
//		} else {
//			obj.OBJECT_SEQU = Global.Object_SEQU++;
//			if(TRACE) IO.println("AttributeOutputStream.writeObj: OBJECT_SEQU="+obj.OBJECT_SEQU+": "+obj.getClass().getSimpleName()+"  "+obj);
//			obj.write(this);
//		}
//    }

}