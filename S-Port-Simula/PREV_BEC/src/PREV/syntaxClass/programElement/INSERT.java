package PREV.syntaxClass.programElement;

import java.io.FileInputStream;
import java.io.IOException;

import PREV.PREV_S_Module;
import bec.AttributeInputStream;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class INSERT extends ProgramElement {
	boolean sysMode;
    String modid;
    String check;
    String extid;
//    InTag(%bias%); InTag(%limit%);
    int biasTag;
    int limitTag;
	
	public INSERT(boolean sysMode) {
		this.sysMode = sysMode;
		parse();
	}

	public void parse() {
		modid = Scode.inString();
	    check = Scode.inString();
	    extid = Scode.inString();
	    biasTag = Scode.inTag();
	    limitTag = Scode.inTag();
	    Global.nTags = limitTag;
	    
	    System.out.println("NEW "+this);
	    String fileName = Global.getAttrFileName(modid, ".AT2");
//	    S_Module module = new S_Module();
	    try {
	    	insertModule(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//	    Util.IERR(""+fileName);
	}
	
	public static void insertModule(String fileName) throws IOException {
		AttributeInputStream inpt = new AttributeInputStream(new FileInputStream(fileName));
		inpt.readInstr();
		if(inpt.curinstr != Scode.S_MODULE) Util.IERR("Missing MODULE");
		String modident = inpt.readString();
		String modcheck = inpt.readString();
		System.out.println("**************   Begin  -  Input-module  " + modident + "  " + modcheck + "   **************");
//		while(inpt.readObj(inpt) != null) {
//			
//		}
		System.out.println("**************   End of  -  Input-module  " + modident + "  " + modcheck + "   **************");
		Global.dumpDisplay("END INSERT: ");
		Util.IERR("");
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		String res = (sysMode)?"SYS" : "";
		return res + "INSERT " + modid +" "+ check +" "+ extid +" "+ biasTag +" "+ limitTag;
	}
	

}
