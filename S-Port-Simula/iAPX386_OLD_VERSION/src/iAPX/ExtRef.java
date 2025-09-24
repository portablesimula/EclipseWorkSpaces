package iAPX;

import java.io.IOException;

import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;
import iAPX.util.Option;

public class ExtRef {
//	Record ExtRef; info "TYPE";
//	begin int rela;   // Relative byte address
//	      Infix(WORD) smbx;        // External Symbol index
//	end;

	public String smbx; // External Symbol
	public int rela;    // Relative byte address
	
	public ExtRef(String smbx, int rela) {
		this.smbx = smbx;
		this.rela = rela;
	}
	
	@Override
	public String toString() {
		return "ExtRef: " + smbx + " + " + rela;
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeString(smbx);
		oupt.writeShort(rela);
	}

	public static ExtRef read(AttributeInputStream inpt) throws IOException {
		String smbx = inpt.readString();
		if(Option.ATTR_INPUT_TRACE) IO.println("ExtRef.read: smbx="+smbx);
		int rela = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("ExtRef.read: rela="+rela);
		ExtRef xref = new ExtRef(smbx, rela);
//		Util.IERR("");
		return xref;
	}

}
