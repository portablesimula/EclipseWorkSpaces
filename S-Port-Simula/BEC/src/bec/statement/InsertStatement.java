package bec.statement;

import java.io.FileInputStream;
import java.io.IOException;

import bec.descriptor.ConstDescr;
import bec.descriptor.Kind;
import bec.descriptor.LabelDescr;
import bec.AttributeInputStream;
import bec.descriptor.Attribute;
import bec.descriptor.ProfileDescr;
import bec.descriptor.RecordDescr;
import bec.descriptor.RoutineDescr;
import bec.descriptor.Variable;
import bec.segment.DataSegment;
import bec.segment.ProgramSegment;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Type;
import bec.util.Util;

public class InsertStatement {
    String modid;
    String check;
    String extid;
    public int bias;
    int limit;
    
    public static InsertStatement current;

    /**
     * insert_statement
     * 		::= insert module_id:string check_code:string
     * 			external_id:string tagbase:newtag taglimit:newtag
     * 
     * 		::= sysinsert module_id:string check_code:string
     * 			external_id:string tagbase:newtag taglimit:newtag
     * 
     * @param sysmod when SYSINSERT
     * @throws IOException 
     * @throws  
     */
	public InsertStatement(boolean sysmod) {
		modid = Scode.inString();
		check = Scode.inString();
		extid = Scode.inString();
		bias = Scode.ofScode();
		limit = Scode.ofScode();

		if(Global.ATTR_INPUT_TRACE)
			System.out.println("**************   Begin  -  Input-module  " + modid + "  " + check + "   **************");
		try {
			current = this;
			readDescriptors(sysmod);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Util.IERR("ERROR READING: Input-module  " + modid + "  " + check);
		}
		if(Global.ATTR_INPUT_TRACE)
			System.out.println("**************   Endof  -  Input-module  " + modid + "  " + check + "   **************");
//		Global.dumpDISPL("END INSERT: ");
//		Util.IERR("");
	}
	
	private void readDescriptors(boolean sysmod) throws IOException {
		String fileName = null;
		if(sysmod) {
			fileName = Global.rtsDir+modid+".svm";
//			Util.IERR(""+modid+"  "+Global.rtsDir);
		} else {
			fileName = Global.getAttrFileName(modid, ".svm");
		}
		if(Global.ATTR_INPUT_TRACE) System.out.println("ATTRIBUTE INPUT: " + fileName);
		AttributeInputStream inpt = new AttributeInputStream(new FileInputStream(fileName));
		int kind = inpt.readKind();
		if(kind != Kind.K_Module) Util.IERR("Missing MODULE");
		String modident = inpt.readString();
		String modcheck = inpt.readString();
//		System.out.println("**************   Begin  -  Input-module  " + modident + "  " + modcheck + "   **************");
		if(! modident.equalsIgnoreCase(modid)) Util.IERR("WRONG modident");
		
//	       ------ Read Descriptors ------
		LOOP:while(true) {
			int prevKind = kind;
			kind = inpt.readKind();
//			System.out.println("InsertStatement.readDescriptors'LOOP: " + Kind.edKind(kind));
			if(kind == Kind.K_EndModule) break LOOP;
			switch(kind) {
				case Kind.K_RECTYPES:		Type.readRECTYPES(inpt); break;
				case Kind.K_SEG_DATA:		DataSegment.readObject(inpt, kind); break;
				case Kind.K_SEG_CONST:		DataSegment.readObject(inpt, kind); break;
				case Kind.K_SEG_CODE:		ProgramSegment.readObject(inpt); break;
				case Kind.K_Coonst:			ConstDescr.read(inpt); break;
				case Kind.K_RecordDescr:	RecordDescr.read(inpt); break;
				case Kind.K_Attribute:		Attribute.read(inpt, kind); break;
				case Kind.K_GlobalVar:		Variable.read(inpt, kind); break;
				case Kind.K_LocalVar:		Variable.read(inpt, kind); break;
				case Kind.K_ProfileDescr:	ProfileDescr.read(inpt); break;
				case Kind.K_Import:			Variable.read(inpt, kind); break;
				case Kind.K_Export:			Variable.read(inpt, kind); break;
				case Kind.K_Exit:			Variable.read(inpt, kind); break;
				case Kind.K_Retur:			Variable.read(inpt, kind); break;
				case Kind.K_IntRoutine:		RoutineDescr.read(inpt); break;
				case Kind.K_IntLabel:		LabelDescr.read(inpt, kind); break;
				default: Util.IERR("MISSING: " + Kind.edKind(kind) + ", prevKind=" + Kind.edKind(prevKind));
			}
		}
	}
	
}
