package iAPX.util;

import iAPX.descriptor.Descriptor;

public class Display {
	private static Array<Descriptor> DISPL;  // Descriptor Display Table

	public static void init() {
		DISPL = new Array<Descriptor>();
	}
	
	public static int size() {
		return DISPL.size();
	}
	
	public static Descriptor maybe(Tag tag) {
		Descriptor d = DISPL.get(tag.val);
		return d;
	}
	
	public static Descriptor lookup(Tag tag) {
		Descriptor d = DISPL.get(tag.val);
		if(d == null) {
			Util.IERR("Display entry is not defined: " + tag);
		}
		return d;
	}
	
	public static Descriptor lookup(int tag) {
		Descriptor d = DISPL.get(tag);
		if(d == null) {
			Util.IERR("Display entry is not defined: " + tag);
		}
		return d;
	}
	
	public static void add(Descriptor d) {
		int idx = d.tag.val;
		if(idx != 0) {
			if(DISPL.get(idx) != null )
				Util.IERR("Display-entry is already defined as : " + DISPL.get(idx));
			DISPL.set(idx,d);
			if(Option.DISPL_TRACE)
				IO.println("Display(" + idx + ") = " + d);

		}
	}
	
	public static void printTags(String title) {
		IO.println("=========== " + title);
		int n = DISPL.size();
		for(int i=0;i<n;i++) {
			Descriptor d = DISPL.get(i);
			Tag tag = (d == null)? null : d.tag;
			Integer tx = Tag.xTag(i);
			IO.println("DISPL[" + i + "] = " + tag + ", xTag=" + tx);
		}
	}
	
	public static void dumpDisplay(String title) {
		IO.println("=========== " + title);
//		int n = DISPL.size();
		for(Descriptor d:DISPL) {
			if(d != null) {
//				IO.print("DISPL[" + d.tag.val + "]  ");
				d.print(" ");
			}
		}
	}

}
