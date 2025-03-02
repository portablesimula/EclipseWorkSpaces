package bec.compileTimeStack;

import bec.util.Type;

//Record Object;
//begin range(0:MaxByte) kind;   --- Object kind code
//      range(0:MaxType) type; 
//end;


//Record StackItem:Object;
public class StackItem {
//	int kind;   // Object kind code
	public Type type;
	public int size;
	public StackItem suc;
    StackItem pred;

}
