package iapx.statement;

import static iapx.util.Global.*;

import java.io.FileInputStream;
import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.Kind;
import iapx.descriptor.AttrDescr;
import iapx.descriptor.ConstDescr;
import iapx.descriptor.Descriptor;
import iapx.descriptor.ExtDescr;
import iapx.descriptor.GlobalVar;
import iapx.descriptor.Parameter;
import iapx.descriptor.ProfileDescr;
import iapx.descriptor.RecordDescr;
import iapx.descriptor.RoutineDescr;
import iapx.descriptor.TypeRecord;
import iapx.util.Display;
import iapx.util.Global;
import iapx.util.Option;
import iapx.util.Scode;
import iapx.util.Tag;
import iapx.util.Util;

public class InsertStatement {
    String modid;
    String check;
    String extid;
    int bias;
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
		bias  = Tag.inTag().val;
		limit = Tag.inTag().val;
		
		Tag.bias = bias;

		if(Option.ATTR_INPUT_TRACE) {
			IO.println("**************   Begin  -  Input-module  " + modid + "  " + check  + "  " + extid + "   **************");
			IO.println("NEW InsertStatement: bias=" + bias + ", limit=" + limit);
		}
		
//		Util.IERR("");
		try {
			current = this;
			readDescriptors(sysmod);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Util.IERR("ERROR READING: Input-module  " + modid + "  " + check);//+" from "+Global.sysInsertDir+modid+".svm");
			while(true);
		}
		if(Option.ATTR_INPUT_TRACE)
			IO.println("**************   Endof  -  Input-module  " + modid + "  " + check + "   **************");
	}
	
	private void readDescriptors(boolean sysmod) throws IOException {
		String fileName = null;
		if(sysmod) {
			IO.println("InsertStatement.readDescriptors: Global.sysInsertDir="+Global.sysInsertDir);
			fileName = Global.sysInsertDir+modid+".svm";
		} else {
//			fileName = Global.getAttrFileName(modid, ".svm");
			Util.IERR("NOT IMPL");
		}
		
//		Minut.fileDump("Input: ", fileName, 0, 40);
		
		if(Option.verbose) IO.println("Open Attribute input file: " + fileName);
		AttributeInputStream inpt = new AttributeInputStream(new FileInputStream(fileName));
		Kind kind = Kind.read(inpt);
		if(kind != Kind.K_Module) Util.IERR("Missing MODULE");
		String modid = inpt.readString();
		@SuppressWarnings("unused")
		String check = inpt.readString();
		if(Option.ATTR_INPUT_TRACE) IO.println("**************   Begin  -  Input-module  " + modid + "  " + check + "   **************");
		if(! modid.equalsIgnoreCase(modid)) Util.IERR("WRONG modid");
		
		// ------ Read Descriptors ------
		Descriptor d = null;
		LOOP:while(true) {
			Kind prevKind = kind;
			kind = Kind.read(inpt);
//			IO.println("InsertStatement.readDescriptors'LOOP: " + kind + ':' + kind.ordinal());
			switch(kind) {
				case K_RecordDescr:   d = RecordDescr.read(inpt); break;
				case K_TypeRecord:    d = TypeRecord.read(inpt); break;
				case K_Attribute:     d = AttrDescr.read(inpt); break;
				case K_ProfileDescr:  d = ProfileDescr.read(inpt); break;
				case K_Parameter:     d = Parameter.read(inpt); break;
				case K_ExternVar:     d = ExtDescr.read(inpt); break;
				case K_GlobalVar:     d = GlobalVar.read(inpt); break;
				case K_Constant:      d = ConstDescr.read(inpt); break;
				case K_IntRoutine:	  d = RoutineDescr.read(inpt); break;
				case K_EndModule:     break LOOP;
//				case Kind.K_RECTYPES:		Type.readRECTYPES(inpt); break;
//				case Kind.K_SEG_DATA:		DataSegment.readObject(inpt, kind); break;
//				case Kind.K_SEG_CONST:		DataSegment.readObject(inpt, kind); break;
//				case Kind.K_SEG_CODE:		ProgramSegment.readObject(inpt); break;
//				case Kind.K_Coonst:			ConstDescr.read(inpt); break;
//				case Kind.K_RecordDescr:	RecordDescr.read(inpt); break;
//				case Kind.K_Attribute:		Attribute.read(inpt, kind); break;
//				case Kind.K_GlobalVar:		Variable.read(inpt, kind); break;
//				case Kind.K_LocalVar:		Variable.read(inpt, kind); break;
//				case Kind.K_ProfileDescr:	ProfileDescr.read(inpt); break;
//				case Kind.K_Import:			Variable.read(inpt, kind); break;
//				case Kind.K_Export:			Variable.read(inpt, kind); break;
//				case Kind.K_Exit:			Variable.read(inpt, kind); break;
//				case Kind.K_Retur:			Variable.read(inpt, kind); break;
//				case Kind.K_IntRoutine:		RoutineDescr.read(inpt); break;
//				case Kind.K_IntLabel:		LabelDescr.read(inpt, kind); break;
				default: Util.IERR("MISSING: " + kind + ", prevKind=" + prevKind);
			}
			Display.add(d);
		}
		if(Option.ATTR_INPUT_TRACE) Display.dumpDisplay("Endof  -  Input-module  " + modid + "  " + check);
	}

}
