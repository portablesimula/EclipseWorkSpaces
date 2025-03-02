package bec.descriptor;

public class Kind {

//	---------     O b j e c t   K i n d   C o d e s      ---------
//
//	 Define K_Qfrm1=1,K_Qfrm2=2,K_Qfrm2b=3,K_Qfrm3=4;
//	 Define K_Qfrm4=5,K_Qfrm4b=6,K_Qfrm4c=7,K_Qfrm5=8,K_Qfrm6=9;
	public static final int K_Module=1;

	public final static int K_SEG_DATA  = 2;
	public final static int K_SEG_CONST = 3;
	public final static int K_SEG_CODE  = 4;

	public static final int K_EndModule=5;
	public static final int K_RECTYPES=6;
//	 --- Descriptors ---
	public static final int K_RecordDescr=10,K_TypeRecord=11,K_Attribute=12;
	public static final int K_Import=13,K_Export=14,K_LocalVar=15;
	public static final int K_GlobalVar=16,K_ExternVar=17;
	public static final int K_ProfileDescr=18,K_IntRoutine=19,K_ExtRoutine=20;
	public static final int K_IntLabel=21,K_ExtLabel=22,K_SwitchDescr=23;
//	 --- Stack Items ---
	public static final int K_ProfileItem=24,K_Address=25,K_Temp=26,K_Coonst=27;
	public static final int K_Result=28;
//	 --- Arrays etc. ---
//	public static final int K_RefBlock=29,K_WordBlock=30,K_AddrBlock=31;
//	 --- Others ---
	public static final int K_BSEG=32;
	public static final int K_Exit=33;
	public static final int K_Retur=34;
//
	public static final int K_Max=35;  // Max value of object kind codes  + 1
	
	public static final int qDCL=40;
	public static final int qSTM=41;

	public static String edKind(int kind) {
		switch(kind) {
			case K_Module:			return "Module";
			case K_RECTYPES:		return "RECTYPES";
			case K_SEG_DATA:		return "SEG_DATA";
			case K_SEG_CONST:		return "SEG_CONST";
			case K_SEG_CODE:		return "SEG_CODE";
			case K_Coonst:			return "Coonst";
			case K_RecordDescr:		return "RecordDescr";
			case K_Attribute:		return "Attribute";
			case K_GlobalVar:		return "GlobalVar";
			case K_LocalVar:		return "LocalVar";
			case K_ProfileDescr:	return "ProfileDescr";
			case K_Import:			return "Import";
			case K_Export:			return "Export";
			case K_Exit:			return "Exit";
			case K_Retur:			return "Retur";
			case K_IntRoutine:		return "IntRoutine";
			case K_IntLabel:		return "IntLabel";
			case K_EndModule:		return "EndModule";
		}
		return "Kind:" + kind;
	}


}
