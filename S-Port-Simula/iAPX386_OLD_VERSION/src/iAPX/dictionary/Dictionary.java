package iAPX.dictionary;

import iAPX.util.Global;

public class Dictionary {
//	%title ***   D i c t i o n a r y    ***
//	Record Dictionary;
//	begin range(0:MaxWord) nSymb;       // No.of Symbols
//	      range(0:MaxWord) nSegm;       // No.of Segments
//	      range(0:MaxWord) nPubl;       // No.of Public Definitions
//	      range(0:MaxWord) nExtr;       // No.of External References
//	      range(0:MaxWord) nModl;       // No.of Module Names
//	      infix(WORD) HashKey(255);     // Hash Key table
//	      infix(WORD) HashKey256;       // !!!! TEMP
//	      ref(RefBlock)  Symb(MxpSymb); // Symbol table
//	      ref(WordBlock) Segm(MxpSegm); // Segment Table
//	      ref(WordBlock) Publ(MxpPubl); // Public Definition Table
//	      ref(WordBlock) Extr(MxpExtr); // External Reference Table
//	      ref(WordBlock) Modl(MxpModl); // Module Definition Table
//	end;

	int nSymb;       // No.of Symbols
	int nSegm;       // No.of Segments
	int nPubl;       // No.of Public Definitions
	int nExtr;       // No.of External References
	int nModl;       // No.of Module Names
	int[] HashKey = new int[255];     // Hash Key table
	int HashKey256;       // !!!! TEMP
	RefBlock[] Symb = new RefBlock[Global.MxpSymb]; // Symbol table
	WordBlock[] Segm = new WordBlock[Global.MxpSegm]; // Segment Table
	WordBlock[] Publ = new WordBlock[Global.MxpPubl]; // Public Definition Table
	WordBlock[] Extr = new WordBlock[Global.MxpExtr]; // External Reference Table
	WordBlock[] Modl = new WordBlock[Global.MxpModl]; // Module Definition Table

}
