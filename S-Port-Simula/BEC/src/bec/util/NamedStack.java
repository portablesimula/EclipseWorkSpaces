/// (CC) This work is licensed under a Creative Commons
/// Attribution 4.0 International License.
/// 
/// You find a copy of the License on the following
/// page: https://creativecommons.org/licenses/by/4.0/
package bec.util;

import java.util.Stack;

import bec.compileTimeStack.CTStackItem;

@SuppressWarnings("serial")
public class NamedStack<E> extends Stack<E> {
	private String ident;
	
	public NamedStack(String ident) {
		this.ident = ident;
	}
	
	public String ident() {
		return ident;
	}

	public void dumpStack(String title) {
		dumpStack(0, title);
	}
	
	public void dumpStack(int level, String title) {
		String indent = "";
		for(int i=0;i<level;i++) indent = indent + "      ";
		String lead = indent + title + ": Current Stack " + ident;
//		if(stack.empty()) {
		if(this.size() == 0) {
			IO.println(lead + ": **Empty**");				
		} else {
			IO.println(lead);
			lead = indent + "        TOS: ";
			for(int i=this.size()-1;i>=0;i--) {
				E item = this.get(i);
//				String mode = item.edMode();
//				if(item instanceof ProfileItem) IO.println(lead+"PROFILE:  " + item);
//				else if(item instanceof AddressItem) IO.println(lead+"REF:      " + item);
//				else IO.println(lead+"VAL:      " + item.getClass().getSimpleName() + "  " + item);
				IO.println(lead + item);
				lead = indent + "             ";					
			}
		}
	}
			
}
