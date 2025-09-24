package iapx.descriptor;

import java.util.Vector;

import iapx.value.MemAddr;

//Record SwitchDescr:Descriptor;
//begin range(0:MaxSdest) ndest;   // No. of Sdest in this switch
//      range(0:MaxSdest) nleft;   // No. of Sdest left to be defined
//      infix(MemAddr) swtab;      // Start of Sdest-Table
//      ref(AddrBlock) DESTAB(MxpSdest); // All SDEST addresses
//end;
public class SwitchDescr {
	int ndest;   // No. of Sdest in this switch
	int nleft;   // No. of Sdest left to be defined
//  infix(MemAddr) swtab;      // Start of Sdest-Table
//  ref(AddrBlock) DESTAB(MxpSdest); // All SDEST addresses
	Vector<MemAddr> DESTAB;
}
