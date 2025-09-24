package iAPX;

import iAPX.ctStack.StackItem;

public class BSEG {
//	---------------------------------------------
//	---------    S  e  g  m  e  n  t    ---------
//	---------------------------------------------
//
//	Record BSEG:Object;
//	begin ref(BSEG) next;
//	      Infix(WORD) segid;       // Program Segment's name index
//	      range(0:8) StackDepth87; // initial(0)
//	      ref(StackItem) TOS;      // Top of Compile-time stack
//	      ref(StackItem) BOS;      // Bot of Compile-time stack
//	      ref(StackItem) SAV;   // Last Compile-time stack-item for which
//	                            // the corresponding Runtime-item is saved.
//	                            // NOTE: SAV =/= none implies TOS =/= none
//	end;

	BSEG next;
	String segid;     // Program Segment's name index
	int StackDepth87; // initial(0)
	StackItem TOS;    // Top of Compile-time stack
	StackItem BOS;    // Bot of Compile-time stack
	StackItem SAV;    // Last Compile-time stack-item for which
	                  // the corresponding Runtime-item is saved.
	                  // NOTE: SAV =/= none implies TOS =/= none

}
