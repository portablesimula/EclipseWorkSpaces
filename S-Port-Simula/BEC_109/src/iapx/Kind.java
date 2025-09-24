package iapx;

import java.io.IOException;

public enum Kind {
	// ---------     O b j e c t   K i n d   C o d e s      ---------

   	K_NULL, K_Qfrm1, K_Qfrm2, K_Qfrm2b, K_Qfrm3, K_Qfrm4, K_Qfrm4b, K_Qfrm4c, K_Qfrm5, K_Qfrm6,
   	 //--- Descriptors ---
   	K_RecordDescr, K_TypeRecord, K_Attribute, K_Parameter, K_Export, K_LocalVar,
   	K_GlobalVar, K_ExternVar, K_Constant, K_ProfileDescr, K_IntRoutine, K_ExtRoutine,
   	K_IntLabel, K_ExtLabel, K_SwitchDescr,
   	 //--- Stack Items ---
   	K_ProfileItem, K_Address, K_Temp, K_Coonst, K_Result,
   	 //--- Others ---
 	K_SEG_DATA, K_SEG_CONST, K_SEG_CODE, K_BSEG,
   	K_Module, K_EndModule,
   	 //--- Arrays etc. ---
   	K_RefBlock, K_WordBlock, K_AddrBlock;
   	
	private static Kind[] values = Kind.values();
	private static Kind of(int ordinale) {
		return values[ordinale];
	}
	
//	public static void write(Kind kind, AttributeOutputStream oupt) throws IOException {
//		oupt.writeShort(kind.ordinal());
//	}
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(this.ordinal());
	}

	public static Kind read(AttributeInputStream inpt) throws IOException {
		return Kind.of(inpt.readShort());
	}

}
