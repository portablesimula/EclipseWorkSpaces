/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.compileTimeStack;

import bec.util.Type;
import bec.value.Value;

/// Constant Item.
/// 
/// Link to GitHub: <a href="https://github.com/portablesimula/EclipseWorkSpaces/blob/main/S-Port-Simula/BEC/src/bec/compileTimeStack/ConstItem.java"><b>Source File</b></a>.
/// 
/// @author Øystein Myhre Andersen
public class ConstItem extends TempItem {
	public Value value;

	public ConstItem(Type type, Value value) {
		super(CTStackItem.Mode.VAL, type, 0);
		this.value = value;
	}
	
	public String toString() {
		return edMode() + "CNST: " +type + " " + value;
	}
}
