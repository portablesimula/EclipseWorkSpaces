package iapx.records;

import java.util.BitSet;

//Record DataType; info "TYPE";
//begin range(0:MaxByte) nbyte;    // Size of type in bytes
//      range(0:2) kind;           // tUnsigned,tSigned,tFloat
//      infix(WORD) pntmap;        // 0:no pointers,
//end;                             // else: Reladdr of pointers
public class DataType {
	int nbyte;     // Size of type in bytes
	int kind;      // tUnsigned,tSigned,tFloat
	BitSet pntmap; // 0:no pointers,
}
