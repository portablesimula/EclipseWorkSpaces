package iAPX.dataAddress;

import iAPX.util.Global;

//Record Fixup; info "TYPE";
//begin Boolean Matched;
//%+D   range(0:MaxLine) line;     -- Created at line
//%+D   range(0:MaxWord) fixno;    -- Assembly label serial number
//      Infix(WORD) segid;         -- Segment name index
//      variant Infix(WORD) smbx;        -- Unmatched: External Symbol
//      variant Infix(wWORD) rela;       -- Relative byte address
//end;
public class Fixup {
	int line;     // Created at line
	int fixno;    // Assembly label serial number
	String segid; // Segment name index
	String smbx;  // Variant: Unmatched: External Symbol
	int rela;     // Variant: Relative byte address
	

	public Fixup(String segid, String smbx) {
		this.line = Global.curline;
		this.fixno = (Global.nFix++);
		this.segid = segid;
		this.smbx = smbx;
		
		Global.FIXTAB.set(fixno, this);
//		IO.println("NEW " + this);
	}
	
	@Override
	public String toString() {
		return "Fixup: line=" + line + ", fixno=" + fixno + ", segid=" + segid +", smbx=" +smbx + ", rela=" +rela;
	}
}
