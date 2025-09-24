package iapx.util;

import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;

public class Tag {
	public final static int  TAG_BOOL    = 1,  TAG_CHAR    = 2,  TAG_INT     = 3;
	public final static int  TAG_SINT    = 4,  TAG_REAL    = 5,  TAG_LREAL   = 6;
	public final static int  TAG_AADDR   = 7,  TAG_OADDR   = 8,  TAG_GADDR   = 9;
	public final static int  TAG_PADDR   = 10, TAG_RADDR   = 11, TAG_SIZE    = 12;
	public final static int  T_max = TAG_SIZE;
	public static Array<Integer> xTAGTAB; // Tag Table: xTag = xTAGTAB(iTag); (during Module I/O)

	public int val;
	public String ident;    // GENERATE_DEBUG_CODE
	
	public Tag(int val) {
		this.val = val;
	}
	
	public static Integer xTag(int i) {
		Integer x = xTAGTAB.get(i);
		return x;
	}
	
	public boolean isBasic() { return val <= T_max; }
	
	public String getIdent() {
		switch(val) {
			case TAG_BOOL:  return "BOOL";
			case TAG_CHAR:  return "CHAR";
			case TAG_INT:   return "INT";
			case TAG_SINT:  return "SINT";
			case TAG_REAL:  return "REAL";
			case TAG_LREAL: return "LREAL";
			case TAG_AADDR: return "AADDR";
			case TAG_OADDR: return "OADDR";
			case TAG_GADDR: return "GADDR";
			case TAG_PADDR: return "PADDR";
			case TAG_RADDR: return "RADDR";
			case TAG_SIZE:  return "SIZE";
			default: return ident;
		}		
	}

//	public static Array<String> TAGIDENT;

	public static void initTags() {
//		TAGIDENT = new Array<String>();
//
//        TAGIDENT.set(1, "BOOL");
//        TAGIDENT.set(2, "CHAR");
//        TAGIDENT.set(3, "INT");
//        TAGIDENT.set(4, "SINT");
//        TAGIDENT.set(5, "REAL");
//        TAGIDENT.set(6, "LREAL");
//        TAGIDENT.set(7, "AADDR");
//        TAGIDENT.set(8, "OADDR");
//        TAGIDENT.set(9, "GADDR");
//        TAGIDENT.set(10, "PADDR");
//        TAGIDENT.set(11, "RADDR");
//        TAGIDENT.set(12, "SIZE");
//        TAGIDENT.set(13, "TEXT");
	}
	
	public static Tag inTag() {
		int t = Scode.get2Bytes();
		//	IO.println("Scode'INTAG: "+t);
		String ident = null;
		if(t == 0) {
			t = Scode.get2Bytes();
			ident = Scode.getString();
//			TAGIDENT.set(t, ident);
		}
		Tag tag = new Tag(t);
		if(Option.GENERATE_DEBUG_CODE) {
			tag.ident = ident;
		}
		if(Option.SCODE_INPUT_TRACE) {
			Scode.traceBuff.append(" "+tag);
		}
		//	IO.println("Scode.inTag: " + Tag.of(t));
		return tag;
	}

	public String toString() {
		if(Option.GENERATE_DEBUG_CODE)
			 return "T[" + val + ':' + getIdent() + ']';		
		else return "T[" + val + ']';		
	}

	// ***********************************************************************************************
	// *** Attribute File I/O
	// ***********************************************************************************************

    public static void writeTag(Tag tag, AttributeOutputStream oupt) throws IOException {
    	if(AttributeOutputStream.TRACE)
    		IO.println("Tag.writeTag: " + tag);
    	if(tag != null) {
    		oupt.writeBoolean(true); // Present
    		if(tag.val < 32) {
    			oupt.writeBoolean(true); // baseTag
    			oupt.writeShort(tag.val);				
    		} else {
    			oupt.writeBoolean(false); // xTag
    			int xtag = Tag.xTag(tag.val);
    			if(AttributeOutputStream.TRACE) IO.println("Tag.writeTag: " + tag + ", xTag=" + xtag);
    			oupt.writeShort(xtag);
    			if(Option.GENERATE_DEBUG_CODE) {
    				oupt.writeString(tag.ident);
    			}
    		}
    	} else {
    		oupt.writeBoolean(false);
    	}
    }

	public static int bias = 0; // Set by InsertStatement
    public static Tag readTag(AttributeInputStream inpt) throws IOException {
    	boolean present = inpt.readBoolean();
		if(AttributeInputStream.TRACE) IO.println("AttributeInputStream.readTag: present=" + present);
    	Tag tag = null;
    	if(present) {
    		boolean isBasetag = inpt.readBoolean();
    		if(isBasetag) {
	    		int basTag = inpt.readShort();
	    		tag = new Tag(basTag);   			
    		} else {
	    		int xTag = inpt.readShort();
	    		tag = new Tag(xTag + bias);
    			if(Option.GENERATE_DEBUG_CODE) {
    				tag.ident = inpt.readString();
    			}
    		}
    	}
		if(AttributeInputStream.TRACE) IO.println("AttributeInputStream.readTag: " + tag);
//		Util.IERR("");
		return tag;
	}

}
