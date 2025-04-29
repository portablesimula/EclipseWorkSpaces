package bec.virtualMachine;

import bec.segment.DataSegment;
import bec.segment.Segment;
import bec.util.Util;
import bec.value.IntegerValue;
import bec.value.ObjectAddress;
import bec.value.Value;

public class RTUtil {

	// Instance sorts:  instances must be first
	public static final int S_NOSORT =  0; //  no sort
	public static final int S_SUB    =  1; //  Sub-Block
	public static final int S_PRO    =  2; //  Procedure
	public static final int S_ATT    =  3; //  Attached Class
	public static final int S_DET    =  4; //  Detached Class
	public static final int S_RES    =  5; //  Resumed Class
	public static final int S_TRM    =  6; //  Terminated Class
	public static final int S_PRE    =  7; //  Prefixed Block
	public static final int S_THK    =  8; //  Thunk
	public static final int S_SAV    =  9; //  Save Object
	public static final int S_ALLOC  = 10; //  object allocated on request (not SIMULA)
	// Special entity sorts:
	public static final int S_GAP    = 11; //  Dynamic Storage Gap
	public static final int S_TXTENT = 12; //  Text Entity
	public static final int S_ARHEAD = 13; //  Array Head Entity
	public static final int S_ARBODY = 14; //  Array Body Entity      (3 or more dimensions)
	public static final int S_ARBREF = 15; //  ref-Array Body Entity  (3 or more dimensions)
	public static final int S_ARBTXT = 16; //  text-Array Body Entity (3 or more dimensions)
	public static final int S_ARENT2 = 17; //  Array Body Entity      (two dimensions)
	public static final int S_ARREF2 = 18; //  ref-Array Body Entity  (two dimensions)
	public static final int S_ARTXT2 = 19; //  text-Array Body Entity (two dimensions)
	public static final int S_ARENT1 = 20; //  Array Body Entity      (one dimension)
	public static final int S_ARREF1 = 21; //  ref-Array Body Entity  (one dimension)
	public static final int S_ARTXT1 = 22; //  text-Array Body Entity (one dimension)
	public static final int MAX_SORT = 22;

//	 Visible record entity;  info "DYNAMIC";
//	 begin ref(inst)                sl;   -- during GC used as GCL!!!!
//	       range(0:MAX_BYT)         sort;
//	       range(0:MAX_BYT)         misc;
//	       variant ref(ptp)         pp;   -- used for instances
//	       variant range(0:MAX_TXT) ncha; -- used for text entities
//	       variant size             lng;  -- used for other entities
//	 end;
//
//	 Visible record inst:entity;
//	 begin ref(entity)              gcl;
//	       variant ref(inst)        dl;
//	               label            lsc;
//	       variant entry(Pmovit)    moveIt;
//	 end;

//	%title ******   P r o t o t y p e s   ******
//	 Visible record ptp;
//	 begin ref(pntvec) refVec; -- pnt_vec
//	       ref(rptvec) repVec;
//	       ref(ptpExt) xpp;
//	       size lng;
//	 end;
	
	public static int length(ObjectAddress ent) {
		IntegerValue sort = (IntegerValue) ent.addOffset(1).load();
		Value variant = ent.addOffset(3).load();
		switch(sort.value) {
			case S_SUB, S_PRO, S_ATT, S_DET, S_RES, S_TRM, S_PRE:
				ObjectAddress pp = (ObjectAddress) variant;
				IntegerValue lngVal= (IntegerValue) pp.addOffset(3).load();
				return lngVal.value;
			case S_TXTENT:
				lngVal = (IntegerValue) variant;
				return lngVal.value + 4;
			default:
				lngVal = (IntegerValue) variant;
				return lngVal.value;
		}
	}
	
	public static void dumpEntity(ObjectAddress ent) {
		IntegerValue sort = (IntegerValue) ent.addOffset(1).load();
		int lng = length(ent);
		DataSegment dseg = ent.segment();
		int from = ent.getOfst();
		int to = from + lng;
		dseg.dump("Entity: " + edSort(sort.value) + ": ", from, to);
	}
	
//	 Visible record ptpExt;  --- Prototype extension
//	 begin ref(idfier)          idt;
//	       ref(modinf)       modulI;
//	       ref(atrvec)        attrV; -- List of attributes (or none).
//	       range(0:MAX_BLK)  blkTyp; -- Block type (SUB/PRO/FNC/CLA/PRE)
//	 end;
//
//	 Visible record idfier;   -- identifier
//	 begin range(0:MAX_BYT)   ncha;
//	       character          cha(0);
//	 end;
	public static String entID(ObjectAddress ent) {
		try {
		IntegerValue sort = (IntegerValue) ent.addOffset(1).load();
		switch(sort.value) {
			case S_SUB, S_PRO, S_ATT, S_DET, S_RES, S_TRM, S_PRE:
				ObjectAddress pp = (ObjectAddress) ent.addOffset(3).load();
				ObjectAddress xpp = (ObjectAddress) pp.addOffset(2).load();
				if(xpp == null) return "Instance " + " " + edSort(sort.value);
				ObjectAddress idt = (ObjectAddress) xpp.load();
				IntegerValue ncha = (IntegerValue) idt.load();
				return edIDT(idt.addOffset(1), ncha.value) + " " + edSort(sort.value);
			default: return edSort(sort.value);
		}
		} catch(Exception e) {
			return "UNKNOWN";
		}
	}
	
	public static String edIDT(ObjectAddress idt, int ncha) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<ncha;i++) {
			IntegerValue cha = (IntegerValue) idt.addOffset(i).load();
			sb.append((char)((cha==null)?0:cha.value));
		}
		return sb.toString();		
	}
	
	public static boolean printEntity(ObjectAddress ent) {
		ObjectAddress sl = (ObjectAddress) ent.load();
		IntegerValue sort = (IntegerValue) ent.addOffset(1).load();
		if(sort == null) return false;
		IntegerValue misc = (IntegerValue) ent.addOffset(2).load();
		Value variant = ent.addOffset(3).load();
		System.out.println("============ Entity " + entID(ent) + " ============");
		System.out.println(""+ent +              ": SL    " + sl);
		System.out.println(""+ent.addOffset(1) + ": SORT  " + sort);
		System.out.println(""+ent.addOffset(2) + ": MISC  " + misc);
		int bdx = 0;
		int lng = length(ent);
		switch(sort.value) {
			case S_SUB, S_PRO, S_ATT, S_DET, S_RES, S_TRM, S_PRE:
				ObjectAddress pp = (ObjectAddress) variant;
				ObjectAddress gcl = (ObjectAddress) ent.addOffset(4).load();
				Value var1 = ent.addOffset(5).load();
				Value var2 = ent.addOffset(6).load();
				System.out.println(""+ent.addOffset(3) + ": PP    " + pp);
				System.out.println(""+ent.addOffset(4) + ": GCL   " + gcl);
				System.out.println(""+ent.addOffset(5) + ": VAR   " + var1);
				System.out.println(""+ent.addOffset(6) + ": VAR   " + var2);
				IntegerValue lngVal= (IntegerValue) pp.addOffset(3).load();
				lng = lngVal.value;
				bdx = 7;
				break;
			case S_TXTENT:
				lngVal = (IntegerValue) variant;
				System.out.println(""+ent.addOffset(3) + ": LNG   " + lngVal);
				System.out.println(""+ent.addOffset(4) + ": TXT   \"" + edIDT(ent.addOffset(4), lngVal.value) + '"');
				
//				lngVal = (IntegerValue) variant;
//				lng = lngVal.value + 4;
//				bdx = 4;
				
				lng = 0;
//				Util.IERR("");
				break;
			default:
				lngVal = (IntegerValue) variant;
				lng = lngVal.value;
				System.out.println(""+ent.addOffset(3) + ": LNG   " + lngVal);
				bdx = 4;
		}
		
		
		for(int i=bdx;i<lng;i++) {
			System.out.println(""+ent.addOffset(i) + ": BODY  " + ent.addOffset(i).load());
		}
//		System.out.println("============ Entity " + edSort(sort.value) + " ============");
		return true;
	}
	
	public static void dumpCurins() {
		DataSegment rt = (DataSegment) Segment.find("DSEG_RT");
		ObjectAddress curins = (ObjectAddress) rt.load(0);
		System.out.println("SVM_ASSIGN: curins=" + curins);
		RTUtil.printEntity(curins);
//		Util.IERR("");
	}
	
	public static void printCurins() {
		DataSegment rt = (DataSegment) Segment.find("DSEG_RT");
		ObjectAddress curins = (ObjectAddress) rt.load(0);
		RTUtil.printEntity(curins);
		System.out.println("==================================");
	}
	
//    record area;                 -- Definition of storage pool
//    begin ref(area) suc;         -- Used to organize the pool list
//          ref(entity) nxt,lim;   -- Boundary pointers within the pool
//          ref(entity) startgc;   -- "freeze-address" for the pool
//          size stepsize;         -- extend/contract step
//          size mingap;           -- for this pool
//          short integer sequ;    -- Sequence number (1,2, ... )
//    end;

	public static void printPool(String segID) {
		DataSegment dseg = (DataSegment) Segment.lookup(segID);
		dseg.dump("POOL_1: " , 0, 40);

		ObjectAddress pool = new ObjectAddress(segID, 0);
		ObjectAddress suc = (ObjectAddress) pool.load();
		ObjectAddress nxt = (ObjectAddress) pool.addOffset(1).load();
		ObjectAddress lim = (ObjectAddress) pool.addOffset(2).load();
		ObjectAddress startgc = (ObjectAddress) pool.addOffset(3).load();
		Value stepsize = pool.addOffset(4).load();
		Value mingap = pool.addOffset(5).load();
		Value sequ = pool.addOffset(6).load();
		System.out.println("=============== " + segID + " ===============");
		System.out.println(""+pool +              ": SUC      " + suc);
		System.out.println(""+pool.addOffset(1) + ": NXT      " + nxt);
		System.out.println(""+pool.addOffset(2) + ": LIM      " + lim);
		System.out.println(""+pool.addOffset(3) + ": STARTGC  " + startgc);
		System.out.println(""+pool.addOffset(4) + ": STEPSIZE " + stepsize);
		System.out.println(""+pool.addOffset(5) + ": MINGAP   " + mingap);
		System.out.println(""+pool.addOffset(6) + ": SEQU     " + sequ);
		
		ObjectAddress ent = new ObjectAddress(segID, 7);
		
		try {
			while(RTUtil.printEntity(ent)) {
				int lng = length(ent);
				ent = ent.addOffset(lng);				
			}
		} catch(Exception e) {
			e.printStackTrace();
			Util.IERR("");
		}
		System.out.println("=============== END " + segID + " ===============");
	}

	
//	 Visible record bioIns:inst;
//	 begin ref(entity)            nxtAdr;   -- NOT in bioptp'refvec
//	       ref(entity)            lstAdr;   -- NOT in bioptp'refvec
//	       ref(prtEnt)            sysout;
//	       ref(filent)            sysin;
//	       ref(filent)            files;
//	       ref(filent)            opfil;    -- USED DURING OPEN
//	       ref(txtent)            opimg;    -- USED DURING OPEN
//	       ref(thunk)             thunks;
//	       ref(txtAr1)            conc;     -- For text conc. - pje sep 87
//	       ref(entity)            smbP1;    -- SIMOB parameter
//	       ref(entity)            smbP2;    -- SIMOB extra param (detach)
//	       integer                edOflo;
//	       long real              initim;
//	       range(0:MAX_ACT)       actLvl;
//	       range(0:MAX_EVT)       obsEvt;
//	       range(0:MAX_BYT)       pgleft; -- SIMOB-page lines left to write
//	       range(0:MAX_BYT)       pgsize; -- SIMOB-page lines per page
//	       range(0:MAX_BYT)       utpos;  -- Current output pos (0..utlng-1)
//	       range(0:MAX_KEY)       logfile; -- 0: no logfile attached
//	       boolean                logging; -- true: logfile att. and active
//	       boolean                stp;
//	       boolean                trc;
//	       boolean                realAr;  -- true if real arithm. possible
//	       character              lwten;
//	       character              dcmrk;
//	       character        utbuff(utlng);    -- The output buffer
//	       --- inbuff moved to MNTR, dumbuf moved to UTIL
//	       character           ebuf(600); -- edit buffer (leftadj/exactfit)
//	       short integer          GCval;  -- GCutil 2'param and return val
//	       infix(txtqnt)          simid;
//	       infix(labqnt)          erh;      -- Current error return label
//	       infix(quant)           ern;      -- Current error return variable
//	       ref(entity)            globalI;
//	       infix(string)          errmsg;   -- NOT in bioptp'refvec
//	 end;
	
	public static void printBasicIO() {
		DataSegment dseg = (DataSegment) Segment.lookup("DSEG_RT");
		ObjectAddress ent = new ObjectAddress("DSEG_RT", 0);
		
//		dseg.dump("BasicIO ", 30, 60);
		
		System.out.println("============ print BasicIO ============ " + dseg.size());
		int idx = 30;
		prt(dseg, ent, " SL      ", idx++);
		prt(dseg, ent, " SORT    ", idx++);
		prt(dseg, ent, " MISC    ", idx++);
		prt(dseg, ent, " PP      ", idx++);
		prt(dseg, ent, " GCL     ", idx++);
		prt(dseg, ent, " VAR     ", idx++);
		prt(dseg, ent, " VAR     ", idx++);
		prt(dseg, ent, " nxtAdr  ", idx++);
		prt(dseg, ent, " lstAdr  ", idx++);

		prt(dseg, ent, " sysout  ", idx++);
		prt(dseg, ent, " sysin   ", idx++);
		prt(dseg, ent, " files   ", idx++);
		prt(dseg, ent, " opfil   ", idx++);
		prt(dseg, ent, " opimg   ", idx++);
		prt(dseg, ent, " thunks  ", idx++);
		prt(dseg, ent, " conc    ", idx++);
		prt(dseg, ent, " smbP1   ", idx++);
		prt(dseg, ent, " smbP2   ", idx++);
		prt(dseg, ent, " edOflo  ", idx++);
		
		prt(dseg, ent, " initim  ", idx++);
		prt(dseg, ent, " actLvl  ", idx++);
		prt(dseg, ent, " obsEvt  ", idx++);
		prt(dseg, ent, " pgleft  ", idx++);
		prt(dseg, ent, " pgsize  ", idx++);
		prt(dseg, ent, " utpos   ", idx++);
		prt(dseg, ent, " logfile ", idx++);
		prt(dseg, ent, " logging ", idx++);
		prt(dseg, ent, " stp     ", idx++);
		prt(dseg, ent, " trc     ", idx++);

		prt(dseg, ent, " realAr  ", idx++);
		prt(dseg, ent, " lwten   ", idx++);
		prt(dseg, ent, " dcmrk   ", idx++); // 61
		
	    //   character        utbuff(utlng);    -- The output buffer
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<200;i++) {
			IntegerValue value = (IntegerValue) dseg.load(62+i);
			sb.append((char)((value==null)?0:value.value));
		}
		System.out.println(""+ent.addOffset(62) + ":  utbuff   \"" + sb + '"');
		
	    //   character           ebuf(600); -- edit buffer (leftadj/exactfit)
		sb = new StringBuilder();
		for(int i=0;i<600;i++) {
			IntegerValue value = (IntegerValue) dseg.load(200+62+i);
			sb.append((char)((value==null)?0:value.value));
		}
		System.out.println(""+ent.addOffset(200+62) + ": ebuff    \"" + sb + '"');
		idx = idx + 200 + 600;
		
		prt(dseg, ent, "GCval   ", idx++);
		prt(dseg, ent, "simid   ", idx++);
		prt(dseg, ent, "simid   ", idx++);
		prt(dseg, ent, "simid   ", idx++);
		prt(dseg, ent, "simid   ", idx++);
		prt(dseg, ent, "erh     ", idx++);
		prt(dseg, ent, "erh     ", idx++);
		prt(dseg, ent, "erh     ", idx++);
		prt(dseg, ent, "ern     ", idx++);
		prt(dseg, ent, "ern     ", idx++);
		prt(dseg, ent, "ern     ", idx++);
		prt(dseg, ent, "ern     ", idx++);
		prt(dseg, ent, "globalI ", idx++);
		prt(dseg, ent, "errmsg  ", idx++);
		prt(dseg, ent, "errmsg  ", idx++);
		prt(dseg, ent, "errmsg  ", idx++);
		System.out.println("============ endof BasicIO ============");
	}
	
	private static void prt(DataSegment dseg, ObjectAddress ent, String ident, int ofst) {
		Value value = dseg.load(ofst);
		System.out.println(""+ent.addOffset(ofst) + ": " + ident + " " + value);
		
	}
	
	public static void printDSEG_RT() {
		DataSegment dseg = (DataSegment) Segment.lookup("DSEG_RT");
		ObjectAddress ent = new ObjectAddress("DSEG_RT", 0);
		
//		dseg.dump("DSEG_RT ", 0, 60);
		
		System.out.println("============ print DSEG_RT ============");
		prt(dseg, ent, " curins ", 0);
		prt(dseg, ent, " status ", 1);
		prt(dseg, ent, " itsize ", 2);
		prt(dseg, ent, " maxlen ", 3);
		prt(dseg, ent, " inplth ", 4);
		prt(dseg, ent, " outlth ", 5);
		prt(dseg, ent, " outlth ", 6);
		prt(dseg, ent, " tmp    ", 7);
		prt(dseg, ent, " tmp    ", 8);
		prt(dseg, ent, " tmp    ", 9);
		prt(dseg, ent, "tmp    ", 10);
		prt(dseg, ent, "maxint ", 11);
		prt(dseg, ent, "minint ", 12);
		prt(dseg, ent, "maxrnk ", 13);
		prt(dseg, ent, "maxrea ", 14);
		prt(dseg, ent, "minrea ", 15);
		prt(dseg, ent, "maxlrl ", 16);
		prt(dseg, ent, "minlrl ", 17);
		prt(dseg, ent, "inierr ", 18);
		prt(dseg, ent, "alloco ", 19);
		prt(dseg, ent, "freeob ", 20);
		prt(dseg, ent, "smb    ", 21);
		prt(dseg, ent, "obSML  ", 22);
		prt(dseg, ent, "actlvl ", 23);
		prt(dseg, ent, "txttmp ", 24);
		prt(dseg, ent, "txttmp ", 25);
		prt(dseg, ent, "txttmp ", 26);
		prt(dseg, ent, "txttmp ", 27);
		prt(dseg, ent, "rstr   ", 28);
		prt(dseg, ent, "rstr_x ", 29);
		prt(dseg, ent, "bio    ", 30);
		prt(dseg, ent, "bio    ", 31);
		System.out.println("          ... rest of bio truncated");
		System.out.println("============ endof DSEG_RT ============");
	}
	
	public static String edSort(int sort) {
		switch(sort) {
			case S_NOSORT: return("S_NOSORT"); //  no sort
			case S_SUB:    return("S_SUB"); //  Sub-Block
			case S_PRO:    return("S_PRO"); //  Procedure
			case S_ATT:    return("S_ATT"); //  Attached Class
			case S_DET:    return("S_DET"); //  Detached Class
			case S_RES:    return("S_RES"); //  Resumed Class
			case S_TRM:    return("S_TRM"); //  Terminated Class
			case S_PRE:    return("S_PRE"); //  Prefixed Block
			case S_THK:    return("S_THK"); //  Thunk
			case S_SAV:    return("S_SAV"); //  Save Object
			case S_ALLOC:  return("S_ALLOC"); //  object allocated on request (not SIMULA)
			case S_GAP:    return("S_GAP"); //  Dynamic Storage Gap
			case S_TXTENT: return("S_TXTENT"); //  Text Entity
			case S_ARHEAD: return("S_ARHEAD"); //  Array Head Entity
			case S_ARBODY: return("S_ARBODY"); //  Array Body Entity      (3 or more dimensions)
			case S_ARBREF: return("S_ARBREF"); //  ref-Array Body Entity  (3 or more dimensions)
			case S_ARBTXT: return("S_ARBTXT"); //  text-Array Body Entity (3 or more dimensions)
			case S_ARENT2: return("S_ARENT2"); //  Array Body Entity      (two dimensions)
			case S_ARREF2: return("S_ARREF2"); //  ref-Array Body Entity  (two dimensions)
			case S_ARTXT2: return("S_ARTXT2"); //  text-Array Body Entity (two dimensions)
			case S_ARENT1: return("S_ARENT1"); //  Array Body Entity      (one dimension)
			case S_ARREF1: return("S_ARREF1"); //  ref-Array Body Entity  (one dimension)
			case S_ARTXT1: return("S_ARTXT1"); //  text-Array Body Entity (one dimension)
		}
		return("UNKNOWN");
	}
}
