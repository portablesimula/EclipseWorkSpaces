package bec;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

//import PREV.syntaxClass.SyntaxClass;
import bec.descriptor.Kind;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class AttributeInputStream {
//	String modID;
	DataInputStream inpt;
	public int curinstr;
//	public String jarFileName;
	
//	/**
//	 * The Object Reference Table.
//	 * Used during Attribute File Input to fixup Object References.
//	 */
//	public ObjectReferenceMap objectReference;
	
	private boolean TRACE = false; //true;

    public AttributeInputStream(InputStream inpt) throws IOException {
    	this.inpt = new DataInputStream(inpt);
//    	this.jarFileName = jarFileName;
//		objectReference = new ObjectReferenceMap();
		
//		File file = new File(jarFileName);
//		String name = file.getName();
//		this.modID = name.substring(0, name.indexOf('.'));
    }

	public void close() throws IOException { inpt.close(); }
    
    public int readKind() throws IOException {
    	int kind = inpt.readByte() & 0xFF;
    	if(TRACE) System.out.println("AttributeInputStream.readKind: "+kind+':'+Kind.edKind(kind));
    	return kind;
	}
    
    public void readInstr() throws IOException {
    	curinstr = inpt.readByte() & 0xFF;
    	if(TRACE) System.out.println("AttributeInputStream.readInstr: "+curinstr+':'+Scode.edInstr(curinstr));
//    	return curinstr;
	}

    public int readTagID() throws IOException {
    	String ident = readString();
    	int tag = inpt.readShort();
    	Scode.TAGIDENT.set(tag, ident);
		if(TRACE) System.out.println("AttributeInputStream.readTag: "+Scode.edTag(tag));
		return tag;
	}

    public int readTag() throws IOException {
    	int tag = inpt.readShort();
		if(TRACE) System.out.println("AttributeInputStream.readTag: "+Scode.edTag(tag));
		return tag;
	}

//    public Type readType() throws IOException {
//		int keyWord = inpt.readUnsignedByte();
//		if(keyWord == 0) {
//	    	if(TRACE) System.out.println("AttributeInputStream.readType: null");
//			return null;
//		}
//		String classIdent = readString();
//		Type type = new Type(keyWord,classIdent);			
//    	if(TRACE) System.out.println("AttributeInputStream.readType: "+type);
//    	return type;
//    }
	
    public boolean readBoolean() throws IOException {
    	boolean b = inpt.readBoolean();
    	if(TRACE) System.out.println("AttributeInputStream.readBoolean: "+b);
    	return b;
    }
	
    public int readChar() throws IOException {
    	int i = inpt.readShort();
    	if(TRACE) System.out.println("AttributeInputStream.readInt: "+i);
    	return i;
	}
	
    public int readShort() throws IOException {
    	short i = inpt.readShort();
    	if(TRACE) System.out.println("AttributeInputStream.readShort: "+i);
    	return i;
	}
	
    public int readInt() throws IOException {
    	int i = inpt.readInt();
    	if(TRACE) System.out.println("AttributeInputStream.readInt: "+i);
    	return i;
	}
	
    public float readFloat() throws IOException {
    	float i = inpt.readFloat();
    	if(TRACE) System.out.println("AttributeInputStream.readFloat: "+i);
    	return i;
	}
	
    public double readDouble() throws IOException {
    	double i = inpt.readDouble();
    	if(TRACE) System.out.println("AttributeInputStream.readDouble: "+i);
    	return i;
	}

//    public Object readConstant() throws IOException {
//    	int key = inpt.readByte();
//    	if(TRACE) System.out.println("AttributeInputStream.readConstant: key=" + (int)key + " \"" + key +"\"");
//    	Object res = null;
//		switch(key) {
//			case Type.T_VOID:		res = null; break;
//			case Type.T_BOOLEAN:	res = inpt.readBoolean(); break;
//			case Type.T_CHARACTER:	res = inpt.readChar(); break;
//			case Type.T_INTEGER:	res = inpt.readShort(); break;
//			case Type.T_REAL:		res = inpt.readFloat(); break;
//			case Type.T_LONG_REAL:	res = inpt.readDouble(); break;
//			case Type.T_TEXT:		res = readString(); break;
//			default: throw new RuntimeException("key = "+key);
//		}
//    	if(TRACE) System.out.println("AttributeInputStream.readDouble: "+res);
//    	return res;
//	}

    public String readString() throws IOException {
//    	int test = inpt.readShort(); if(test != 9999) Util.IERR(""+test);
    	int lng = inpt.readShort()-1;
//    	test = inpt.readShort(); if(test != 8888) Util.IERR(""+test);
    	if(lng < 0) {
        	if(TRACE) System.out.println("AttributeInputStream.readString: null");
    		return null;
    	}
    	if(TRACE) System.out.println("AttributeInputStream.readString: lng="+(char)lng+" "+lng);
    	if(lng > 2000) Thread.dumpStack();
    	StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lng; i++) sb.append(inpt.readChar());
    	String s = sb.toString();
    	if(TRACE) System.out.println("AttributeInputStream.readString: \""+s+'"');
    	return s;
    }

//	public ObjectList<?> readObjectList() throws IOException {
//		return ObjectList.read(this);
//	}
//
//
//    public int readSEQU(SyntaxClass obj) throws IOException {
//    	int OBJECT_SEQU = inpt.readShort();
//    	if(TRACE) System.out.println("AttributeInputStream.readSEQU: " + OBJECT_SEQU + "  ====>  " + obj.getClass().getSimpleName());
//		objectReference.put(OBJECT_SEQU, obj);
//    	return OBJECT_SEQU;
//	}
//    
//
//	public SyntaxClass readObj() throws IOException {
//		int kind = readKind();
//		switch(kind) {
//		System.out.println("AttributeInputStream.readObj: "+Scode.edInstr(kind));
//		switch(kind) {
//			case Scode.S_BSEG:	    return DataSegment.readObject(inpt);
//			case Scode.S_RECORD:    return RECORD.readObject(inpt);
//			case Scode.S_PROFILE:   return PREV_PROFILE.readObject(inpt);
//			case Scode.S_CONST:     return PREV_CONST.readObject(inpt);
//			case Scode.S_GLOBAL, Scode.S_LOCAL, Scode.S_IMPORT, Scode.S_EXPORT, Scode.S_EXIT:
//				return PREV_Variable.readObject(inpt,kind);
//			case Scode.S_ENDMODULE: return null;
//			
//			default: Util.IERR("IMPOSSIBLE "+Scode.edInstr(kind));
//		}
//		case ObjectKind.NULL:
//			if(TRACE) System.out.println("AttributeInputStream.readObj: null");
//			return null;
//		case ObjectKind.ObjectReference:
//			int OBJECT_SEQU = inpt.readShort();
//			if(TRACE) System.out.println("AttributeInputStream.readObj: OBJECT_SEQU="+OBJECT_SEQU);
//			SyntaxClass obj;
//			obj = objectReference.get(OBJECT_SEQU);
//			Util.ASSERT(obj != null, "Invariant: OBJECT_SEQU="+modID+"#"+OBJECT_SEQU);
//			if(TRACE) System.out.println("AttributeInputStream.readObj: "+obj);
//			return(obj);
//		default:
//			obj = readObj(kind,this);
//			Util.ASSERT(obj.OBJECT_SEQU != 0, "Invariant: OBJECT_SEQU="+obj.OBJECT_SEQU);
//			objectReference.put(obj.OBJECT_SEQU, obj);
//			obj.OBJECT_SEQU = 0;
//			if(TRACE)
//				System.out.println("AttributeInputStream.readObj: "+modID+"#"+obj.OBJECT_SEQU+" obj="+obj);
//			return(obj);
//		}	
//	}
//
//	private SyntaxClass readObj(int kind,AttributeInputStream inpt) throws IOException {
//		switch(kind) {
//			case ObjectKind.NULL:						return null;
//			
//			case ObjectKind.StandardClass:				return StandardClass.readObject(inpt);
//			case ObjectKind.ConnectionBlock:			return ConnectionBlock.readObject(inpt);
//			case ObjectKind.CompoundStatement:			return MaybeBlockDeclaration.readObject(inpt,ObjectKind.CompoundStatement);
//			case ObjectKind.SubBlock:					return MaybeBlockDeclaration.readObject(inpt,ObjectKind.SubBlock);
//			case ObjectKind.Procedure:					return ProcedureDeclaration.readObject(inpt);
////			case ObjectKind.Switch:						return SwitchDeclaration.readObject(inpt);
////			case ObjectKind.MemberMethod:				return MemberMethod.readObject(inpt);
////			case ObjectKind.ContextFreeMethod:			return ContextFreeMethod.readObject(inpt);
//			case ObjectKind.Class:						return ClassDeclaration.readObject(inpt);
//			case ObjectKind.PrefixedBlock:				return PrefixedBlockDeclaration.readObject(inpt);
////			case ObjectKind.SimulaProgram:				return SimulaProgram.readObject(inpt);
//			case ObjectKind.ArrayDeclaration:			return ArrayDeclaration.readObject(inpt);
//			case ObjectKind.VirtualSpecification:		return VirtualSpecification.readObject(inpt);
////			case ObjectKind.VirtualMatch:				return VirtualMatch.readObject(inpt);
//			case ObjectKind.Parameter:					return Parameter.readObject(inpt);
////			case ObjectKind.Thunk:						return Thunk.readObject(inpt);
//			case ObjectKind.LabelDeclaration:			return LabelDeclaration.readObject(inpt);
//			case ObjectKind.SimpleVariableDeclaration:	return SimpleVariableDeclaration.readObject(inpt);
//			case ObjectKind.InspectVariableDeclaration:	return InspectVariableDeclaration.readObject(inpt);
//			case ObjectKind.ExternalDeclaration:		return ExternalDeclaration.readObject(inpt);
//			case ObjectKind.HiddenSpecification:		return HiddenSpecification.readObject(inpt);
//			case ObjectKind.ProtectedSpecification:		return ProtectedSpecification.readObject(inpt);
//
//			case ObjectKind.ActivationStatement:		return ActivationStatement.readObject(inpt);
//			case ObjectKind.BlockStatement:				return BlockStatement.readObject(inpt);
//			case ObjectKind.ConditionalStatement:		return ConditionalStatement.readObject(inpt);
//			case ObjectKind.ConnectionStatement:		return ConnectionStatement.readObject(inpt);
//			case ObjectKind.ConnectionDoPart:			return ConnectionDoPart.readObject(inpt);
//			case ObjectKind.ConnectionWhenPart:			return ConnectionWhenPart.readObject(inpt);
//			case ObjectKind.DummyStatement:				return DummyStatement.readObject(inpt);
//			case ObjectKind.ForStatement:				return ForStatement.readObject(inpt);
//			case ObjectKind.ForListElement:				return ForListElement.readObject(inpt);
//			case ObjectKind.ForWhileElement:			return ForWhileElement.readObject(inpt);
//			case ObjectKind.StepUntilElement:			return StepUntilElement.readObject(inpt);
//			case ObjectKind.GotoStatement:				return GotoStatement.readObject(inpt);
////			case ObjectKind.InlineStatement:			return InlineStatement.readObject(inpt);
//			case ObjectKind.InnerStatement:				return InnerStatement.readObject(inpt);
//			case ObjectKind.LabeledStatement:			return LabeledStatement.readObject(inpt);
////			case ObjectKind.ProgramModule:				return ProgramModule.readObject(inpt);
//			case ObjectKind.StandaloneExpression:		return StandaloneExpression.readObject(inpt);
//			case ObjectKind.SwitchStatement:			return SwitchStatement.readObject(inpt);
//			case ObjectKind.WhileStatement:				return WhileStatement.readObject(inpt);
//
//			case ObjectKind.ArithmeticExpression:		return ArithmeticExpression.readObject(inpt);
//			case ObjectKind.AssignmentOperation:		return AssignmentOperation.readObject(inpt);
//			case ObjectKind.BooleanExpression:			return BooleanExpression.readObject(inpt);
//			case ObjectKind.ConditionalExpression:		return ConditionalExpression.readObject(inpt);
//			case ObjectKind.Constant:					return Constant.readObject(inpt);
//			case ObjectKind.LocalObject:				return LocalObject.readObject(inpt);
//			case ObjectKind.ObjectGenerator:			return ObjectGenerator.readObject(inpt);
//			case ObjectKind.ObjectRelation:				return ObjectRelation.readObject(inpt);
//			case ObjectKind.QualifiedObject:			return QualifiedObject.readObject(inpt);
//			case ObjectKind.RelationalOperation:		return RelationalOperation.readObject(inpt);
//			case ObjectKind.RemoteVariable:				return RemoteVariable.readObject(inpt);
//			case ObjectKind.TextExpression:				return TextExpression.readObject(inpt);
//			case ObjectKind.TypeConversion:				return TypeConversion.readObject(inpt);
//			case ObjectKind.UnaryOperation:				return UnaryOperation.readObject(inpt);
//			case ObjectKind.VariableExpression:			return VariableExpression.readObject(inpt);
//
//		}	
//		Util.IERR("IMPOSSIBLE "+ObjectKind.edit(kind));
//		return(null);
//	}


}
