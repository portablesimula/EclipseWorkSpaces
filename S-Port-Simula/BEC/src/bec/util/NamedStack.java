package bec.util;

import java.util.Stack;

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
			System.out.println(lead + ": **Empty**");				
		} else {
			System.out.println(lead);
			lead = indent + "        TOS: ";
			for(int i=this.size()-1;i>=0;i--) {
				E item = this.get(i);
//				String mode = item.edMode();
//				if(item instanceof ProfileItem) System.out.println(lead+"PROFILE:  " + item);
//				else if(item instanceof AddressItem) System.out.println(lead+"REF:      " + item);
//				else System.out.println(lead+"VAL:      " + item.getClass().getSimpleName() + "  " + item);
				System.out.println(lead + item);
				lead = indent + "             ";					
			}
		}
	}
			
}
