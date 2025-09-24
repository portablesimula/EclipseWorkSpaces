package iapx;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

public class AttributeInputStream {
//	String modID;
	DataInputStream inpt;
	public int curinstr;
	
	public static boolean TRACE = false; //true;

    public AttributeInputStream(InputStream inpt) throws IOException {
    	this.inpt = new DataInputStream(inpt);
    }

	public void close() throws IOException { inpt.close(); }
	
    public BitSet readBitSet() throws IOException {
    	int n = inpt.readShort();
    	byte[] bytes = new byte[n];
    	for(int i=0;i<n;i++) bytes[i] = inpt.readByte();
    	BitSet bitSet = BitSet.valueOf(bytes);
    	if(TRACE) IO.println("AttributeInputStream.readBitSet: "+bitSet);
    	return bitSet;
    }

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
