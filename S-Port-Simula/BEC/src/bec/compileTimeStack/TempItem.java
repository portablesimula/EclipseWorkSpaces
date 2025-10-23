/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.compileTimeStack;

import bec.util.Scode;
import bec.util.Type;

/// Temp Item.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/compileTimeStack/AddressItem.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class TempItem extends CTStackItem {
	int count;
	
	public TempItem(Mode mode, Type type, int count) {
		this.mode = mode;
		this.type = type;
		this.count = count;
	}

	@Override
	public CTStackItem copy() {
		return new TempItem(mode, type, count);
	}

	@Override
	public String toString() {
		return edMode() + "TEMP: " + Scode.edTag(type.tag) + ", count=" + count;
	}
}
