package bec.virtualMachine.sysrut;

import bec.value.LongRealValue;
import bec.value.RealValue;
import bec.virtualMachine.RTStack;
import bec.virtualMachine.SVM_CALLSYS;

public abstract class SysMath {


//	 Visible sysroutine("RADDEP") RADDEP;
//	 import real arg; export real val  end;
	public static void raddep() {
		SVM_CALLSYS.ENTER("RADDEP: ", 1, 1); // exportSize, importSize
		float val = RTStack.popReal();
		val = Math.nextUp(val);
		RTStack.push(RealValue.of(val), "EXPORT");
		SVM_CALLSYS.EXIT("RADDEP: ");
	}

//	 Visible sysroutine("DADDEP") DADDEP;
//	 import long real arg; export long real val  end;
	public static void daddep() {
		SVM_CALLSYS.ENTER("DADDEP: ", 1, 1); // exportSize, importSize
		double val = RTStack.popLongReal();
		val = Math.nextUp(val);
		RTStack.push(LongRealValue.of(val), "EXPORT");
		SVM_CALLSYS.EXIT("DADDEP: ");
	}

//	 Visible sysroutine("RSUBEP") RSUBEP;
//	 import real arg; export real val  end;
	public static void rsubep() {
		SVM_CALLSYS.ENTER("RSUBEP: ", 1, 1); // exportSize, importSize
		float val = RTStack.popReal();
		val = Math.nextDown(val);
		RTStack.push(RealValue.of(val), "EXPORT");
		SVM_CALLSYS.EXIT("RSUBEP: ");
	}

//	 Visible sysroutine("DSUBEP") DSUBEP;
//	 import long real arg; export long real val  end;
	public static void dsubep() {
		SVM_CALLSYS.ENTER("DSUBEP: ", 1, 1); // exportSize, importSize
		double val = RTStack.popLongReal();
		val = Math.nextDown(val);
		RTStack.push(LongRealValue.of(val), "EXPORT");
		SVM_CALLSYS.EXIT("DSUBEP: ");
	}

//	 Visible sysroutine("IIPOWR") IIPOWR; --- v:=b**x
//	 import integer b,x; export integer v  end;
//
//	 Visible sysroutine("RIPOWR") RIPOWR; --- v:=b**x
//	 import real b; integer x; export real v  end;
//
//	 Visible sysroutine("RRPOWR") RRPOWR; --- v:=b**x
//	 import real b,x; export real v  end;
//
//	 Visible sysroutine("RDPOWR") RDPOWR; --- v:=b**x
//	 import real b; long real x; export long real v  end;
//
//	 Visible sysroutine("DIPOWR") DIPOWR; --- v:=b**x
//	 import long real b; integer x; export long real v  end;
//
//	 Visible sysroutine("DRPOWR") DRPOWR; --- v:=b**x
//	 import long real b; real x; export real v  end; -- NOTE: real result
//
//	 Visible sysroutine("DDPOWR") DDPOWR; --- v:=b**x
//	 import long real b,x; export long real v  end;


}
