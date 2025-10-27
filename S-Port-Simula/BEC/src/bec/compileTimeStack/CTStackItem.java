/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.compileTimeStack;

import bec.util.Type;
import bec.util.Util;

/// Compile time Stack Item.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/compileTimeStack/CTStackItem.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public abstract class CTStackItem {
	
	public enum Mode { VAL, REF, PROFILE }
//	private Mode mode;
	public Type type;
	public int size;
	
	public CTStackItem copy() {
		Util.IERR("Method 'copy' need a redefinition in " + this.getClass().getSimpleName());
		return null;
	}
	
//	public String edMode() {
//		if(mode == null) return("NULL    ");
//		switch(mode) {
//			case PROFILE: return("PRF ");
//			case REF:     return("REF ");
//			case VAL:     return("VAL ");
//		}
//		Util.IERR("");
//		return null;
//	}

}
