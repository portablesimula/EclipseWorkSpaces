/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/// Attribute Output Stream.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/AttributeOutputStream.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class AttributeOutputStream extends DataOutputStream{

	/// Create a new AttributeOutputStream
    public AttributeOutputStream(OutputStream oupt) throws IOException {
    	super(oupt);
    }

    /// Write a String to the underlying OutputStream.
    /// If argument 's' is null a length -1 is written informing
    /// AttributeInputStream.readString to return null.
    public void writeString(String s) throws IOException {
		if(s == null) {
			super.writeShort(-1);
		} else {
			int lng = s.length();
			if(lng > Short.MAX_VALUE) Util.IERR("");
			super.writeShort(lng);
//			for(int i=0;i<lng;i++) {
//				super.writeChar(s.charAt(i));
//			}
			super.writeChars(s);
		}
	}

}