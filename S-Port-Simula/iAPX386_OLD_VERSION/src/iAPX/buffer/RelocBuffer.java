package iAPX.buffer;

import iAPX.RelocPkt;

public class RelocBuffer extends Buffer {
//	Record RelocBuffer:Buffer;
//	begin infix(RelocPkt) elt(255);
//	      infix(RelocPkt) elt256;
//	end;

	RelocPkt[] elt = new RelocPkt[255];
	RelocPkt elt256;
//end;
}
