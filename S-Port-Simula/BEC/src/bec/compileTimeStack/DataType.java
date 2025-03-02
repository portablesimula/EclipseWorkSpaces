package bec.compileTimeStack;

import java.util.BitSet;
import java.util.HashMap;
import bec.descriptor.RecordDescr;
import bec.util.Scode;
import bec.util.Type;

public class DataType {
//	Define tUnsigned=0,tSigned=1,tFloat=2;
	enum Kind { tUnsigned , tSigned , tFloat }
	
//	int nbyte; // Size of type in bytes
	int size;  // Size of type in basic cells
	Kind kind; // tUnsigned,tSigned,tFloat
//	Vector<Integer> pntmap; // NULL:no pointers, else: Reladdr of pointers
	public BitSet pntmap; // NULL:no pointers, else: Reladdr of pointers
	String comment;

//    Infix(DataType) TTAB(MaxType); -- Type specification table
//	public static DataType[] TTAB = new DataType[32];
	private static HashMap<Integer, DataType> TMAP = new HashMap<Integer, DataType>();
//	private static Array<DataType> TTAB = new Array<DataType>();
	
	private DataType(int size, Kind kind, String comment) {
		this.size = size;
		this.kind = kind;
		this.comment=comment;
	}

	public static DataType dataType(Type type) {
		DataType dt = TMAP.get(type.tag);
		return dt;
	}

	public static int typeSize(int type) {
		DataType dt = TMAP.get(type);
		if(dt != null) return dt.size;
		return 1;
	}

	public static void initDataTypes() {
//	    -----------        type      size     kind            pnt  )
		newBasType( Scode.TAG_VOID    ,0     ,null                 );
		newBasType( Scode.TAG_INT     ,1     ,Kind.tSigned         );
		newBasType( Scode.TAG_REAL    ,1     ,Kind.tFloat          );
		newBasType( Scode.TAG_LREAL   ,1     ,Kind.tFloat          );
		newBasType( Scode.TAG_BOOL    ,1     ,Kind.tUnsigned       );
		newBasType( Scode.TAG_CHAR    ,1     ,Kind.tUnsigned       );
		newBasType( Scode.TAG_SIZE    ,1     ,Kind.tUnsigned       );
		newPntType( Scode.TAG_OADDR   ,1     ,Kind.tUnsigned ,0    );
		newBasType( Scode.TAG_AADDR   ,1     ,Kind.tUnsigned       );
		newPntType( Scode.TAG_GADDR   ,2     ,Kind.tUnsigned ,1    );
		newBasType( Scode.TAG_PADDR   ,1     ,Kind.tUnsigned       );
		newBasType( Scode.TAG_RADDR   ,1     ,Kind.tUnsigned       );
//		newBasType( Scode.TAG_NOINF   ,0     ,Kind.tUnsigned       );

	}

//	public static void newRecType(RECORD rec) {
//		DataType x = new DataType(rec.size(), null, rec.toString());
//		TMAP.put(rec.tag, x);
//	}

	private static void newRecType(RecordDescr rec) {
		DataType x = new DataType(rec.size, null, rec.tag.toString());
		x.pntmap = rec.pntmap;
		TMAP.put(rec.tag.val, x);
//		System.out.println("DataType.newRecType: " + Scode.edTag(tag) + ", size="+size);
	}

	private static void newBasType(int type, int size, Kind kind) {
		DataType x = new DataType(size, kind, Scode.edTag(type));
		TMAP.put(type, x);
	}

	private static void newPntType(int type, int size, Kind kind, int rela) {
		DataType x = new DataType(size, kind, Scode.edTag(type));
//		x.pntmap = new Vector<Integer>();
//		x.pntmap.add(rela);
		x.pntmap = new BitSet(32);
		x.pntmap.set(rela);
		TMAP.put(type, x);
	}

	public static void dumpDataTypes(String title) {
		System.out.println("============ "+title+" BEGIN Dump DataTypes ================");
//		for(int i=0;i<TTAB.size();i++) {
		for(Integer type:TMAP.keySet()) {
			System.out.println("TTAB["+Scode.edTag(type)+"] = " + TMAP.get(type));
		}
		System.out.println("============ "+title+" ENDOF Dump DataTypes ================");
	}
	
	public String toString() {
		return "DataType: size="+size + " " + kind;
	}

}
