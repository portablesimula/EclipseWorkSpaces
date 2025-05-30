
Global rt("RTS");
 begin
       -----------------------------------------------------------
       ---  COPYRIGHT 1989 by                                  ---
       ---  Simula a.s.                                        ---
       ---  Oslo, Norway                                       ---
       ---                                                     ---
       ---           P O R T A B L E     S I M U L A           ---
       ---            R U N T I M E     S Y S T E M            ---
       ---                                                     ---
       ---        G l o b a l     D e f i n i t i o n s        ---
       ---                                                     ---
       -----------------------------------------------------------


Visible define
      MAX_ACT = 4 ,
      ACT_USR      = 0 ,     -- Normal user system execution.
      ACT_SMB      = 1 ,     -- Execution of Simob.
      ACT_TRM      = 2 ,     -- Fast controlled termination after error.
      ACT_INI      = 3 ,     -- Initialisation of the program execution.
      ACT_GC       = 4 ,     -- Garbage collection (invalid pointers).
---   not used: ACT_ERR = 5 ,-- Printing error report and dumps from RTS

      MAX_KIND = 7 ,
      K_SMP = 0 ,      -- simple kind
      K_PRO = 1 ,      -- procedure kind
      K_ARR = 2 ,      -- array kind
      K_LAB = 3 ,      -- label kind
      K_SWT = 4 ,      -- switch kind
      K_CLA = 5 ,      -- class kind (SIMOB only)
      K_REP = 6 ,      -- infix repetition (not fully impl.)
      K_REC = 7 ,      -- record (not fully impl.)

      MAX_MODE = 5 ,
      M_REF       = 0 ,      -- reference parameter
      M_VALUE     = 1 ,      -- value parameter
      M_NAME      = 2 ,      -- name parameter
      M_LOCAL     = 3 ,      -- local quantity
      M_EXTR      = 4 ,      -- external quantity (SIMOB only)
      M_LIT       = 5 ,      -- literal  quantity (SIMOB only)
                             -- Note: M_LIT implies M_LOCAL

      MAX_TYPE = 9 ,
      T_NOTYPE = 0 ,     -- no type
      T_BOO = 1 ,        -- boolean
      T_CHA = 2 ,        -- character
      T_SIN = 3 ,        -- short_integer
      T_INT = 4 ,        -- integer
      T_REA = 5 ,        -- real
      T_LRL = 6 ,        -- long real
      T_REF = 7 ,        -- reference quantity
      T_TXT = 8 ,        -- text descriptor
      T_PTR = 9 ,        -- pointer quantity (non-standard)

      MAX_SORT = 22 ,
      --  Instance sorts:  instances must be first
      S_NOSORT =  0 ,  --  no sort
      S_SUB    =  1 ,  --  Sub-Block
      S_PRO    =  2 ,  --  Procedure
      S_ATT    =  3 ,  --  Attached Class
      S_DET    =  4 ,  --  Detached Class
      S_RES    =  5 ,  --  Resumed Class
      S_TRM    =  6 ,  --  Terminated Class
      S_PRE    =  7 ,  --  Prefixed Block
      S_THK    =  8 ,  --  Thunk
      S_SAV    =  9 ,  --  Save Object
      S_ALLOC  = 10 ,  --  object allocated on request (not SIMULA)
      --  Special entity sorts:
      S_GAP    = 11 ,  --  Dynamic Storage Gap
      S_TXTENT = 12 ,  --  Text Entity
      S_ARHEAD = 13 ,  --  Array Head Entity
      S_ARBODY = 14 ,  --  Array Body Entity      (3 or more dimensions)
      S_ARBREF = 15 ,  --  ref-Array Body Entity  (3 or more dimensions)
      S_ARBTXT = 16 ,  --  text-Array Body Entity (3 or more dimensions)
      S_ARENT2 = 17 ,  --  Array Body Entity      (two dimensions)
      S_ARREF2 = 18 ,  --  ref-Array Body Entity  (two dimensions)
      S_ARTXT2 = 19 ,  --  text-Array Body Entity (two dimensions)
      S_ARENT1 = 20 ,  --  Array Body Entity      (one dimension)
      S_ARREF1 = 21 ,  --  ref-Array Body Entity  (one dimension)
      S_ARTXT1 = 22 ,  --  text-Array Body Entity (one dimension)

      MAX_FIL = 7 ,
      FIL_FILE = 0 ,      -- FILE
      FIL_IN = 1 ,        -- Infile
      FIL_OUT = 2 ,       -- Outfile
      FIL_PRT = 3 ,       -- Printfile
      FIL_DIR = 4 ,       -- Directfile
      FIL_INBYTE = 5 ,    -- Inbytefile
      FIL_OUTBYTE = 6 ,   -- Outbytefile
      FIL_DIRBYTE = 7 ,   -- Directbytefile

      MAX_PAR = 4 ,
      PAR_QNT_LIT = 0, --  the actual parameter is a compile-time const.
      PAR_QNT_NAS = 1, --  the actual parameter is a simple attribute
                       --  which is non-assignable.
      PAR_QNT_ASS = 2, --  the actual parameter is a simple attribute
                       --  which is assignable.
      PAR_THK_NAS = 3, --  the actual parameter is an expression which
                       --  evaluates to a non-assignable attribute or
                       --  quantity.
      PAR_THK_ASS = 4, --  the actual parameter is an expression
                       --  which evaluates to an assignable attribute.

      MAX_OLV = 2 ,
      OLV_NO = 0 ,              -- No attribute
      OLV_BLK = 1 ,             -- Block (e.g. procedure or class)
      OLV_VAL = 2 ,             -- Value attribute (simple or array,
                                -- local variable or parameter)

      MAX_BLK = 6,              -- Type of block.
      BLK_SUB = 0 ,             -- sub-block
      BLK_PRO = 1 ,             -- notype procedure
      BLK_FNC = 2 ,             -- type procedure
      BLK_CLA = 3 ,             -- class
      BLK_PRE = 4 ,             -- prefixed block
      BLK_THK = 5 ,             -- thunk
      BLK_REC = 6 ,             -- record

      MAX_EXC = 17 ,

      MAX_EVT = 4 ,

      MAX_2BT = 65535 ,   -- Value of a double-byte
      MAX_ATR = 32000 ,   -- Number of attributes per instance
      MAX_BUF = 255 ,     -- Length of editing buffer
      MAX_BYT = 255 ,     -- Value of a byte
      MAX_REP = 255 ,     -- Number of elts in infix rep
      MAX_CLV = 255 ,     -- Connection level
      MAX_DIM = 10 ,      -- Number of array dimensions
                          -- NOTE: also max number ops in text conc.
      MAX_BND = 20 ,      -- 2 * MAX_DIM (array bounds)
      MAX_ENO = 139 ,     -- Number of rts-msg
---   MAX_ISZ = 72 ,      -- Itemsize when editing and de-editing
      MAX_KEY = 255 ,     -- Number of file-keys
      MAX_PLV = 63 ,      -- Prefix level
      MAX_PNT = 32000 ,   -- Number of references per instance
      MAX_RBL = 255 ,     -- Relative static block level
      MAX_SPC = 255 ,     -- Value of printfile.spacing
      MAX_STS = 38 ,      -- Error status from environment
      MAX_TXT = 32000 ,   -- Number of characters in a text object
      MAX_TRC = 10 ,      -- Trace Level
      MAX_VER = 255 ,     -- Version numbers (both level and revision)
      MAX_VIR = 32000    -- Number of virtuals per entity
;
%title ******   I n t e r f a c e   ******

 Visible record string;  info "TYPE";
 begin name(character)   chradr;
       integer           nchr;
 end;

 Visible global profile PXCHDL; -- PEXCHDL
 import range(0:MAX_EXC) code;
        infix(string)    msg;
        label            addr;
 export label            cont;
 end;


 Visible global profile PEXERR; -- PINIERR
 import range(0:MAX_ENO) eno;
        ref(filent)      fil;
 end;

 Visible global profile PSIMOB;
 -- implicit parameters: bio.obsEvt, bio.smbP1, bio.smbP2 (detach only)
 end;

 Visible global profile PobSML;
 import short integer code;
        ref(inst)     ins;
 export ref(inst)     result;
 end;

 --- Allocate - free - move  non-simula object  ---

 Visible global profile Palloc;
 import integer nbytes; entry(Pmovit) movrut;
 export name() Nobj;
 end;

 Visible global profile Pfree;
 import name() Nobj;
 end;

 Visible global profile Pmovit;
 import name() old, new;
 end;

 --  Start of variables etc.,  CURINS must be first (as wanted by QZ) --

 Visible       ref(inst)        curins    system "CURINS";
 Visible       range(0:MAX_STS) status    system "STATUS";
 Visible       range(0:MAX_TXT) itsize    system "ITSIZE";-- itemsize
-- Visible const size           maxlen    system "MAXLEN";                -- ØM
 Visible       size             maxlen; -- system "MAXLEN";               -- ØM
-- Visible const integer          inplth    system "INPLTH";-- inptlinelng
-- Visible const integer          outlth    system "OUTLTH";-- ouptlinelng
 Visible integer          inplth    system "INPLTH";-- inptlinelng
 Visible integer          outlth    system "OUTLTH";-- ouptlinelng
 Visible       ref(bioIns)      bioref    system "BIOREF";
 Visible       infix(quant)     tmp       --system "TMPQNT";
 Visible       integer          maxint    system "MAXINT";
 Visible       integer          minint    system "MININT";
 Visible       integer          maxrnk    system "MAXRNK";
 Visible       real             maxrea    system "MAXREA"; -- The largest positive finite value of type real.
 Visible       real             minrea    system "MINREA"; -- The smallest positive nonzero value of type real.
 Visible       long real        maxlrl    system "MAXLRL"; -- The largest positive finite value of type long real.
 Visible       long real        minlrl    system "MINLRL"; -- The smallest positive nonzero value of type long real.
 Visible entry(PEXERR)          errorX    system "INIERR";
 Visible entry(Palloc)          allocO    system "ALLOCO";
 Visible entry(Pfree )          freeO     system "FREEOB";

 Visible entry(PSIMOB) smb;
 Visible entry(PobSML) obSML;

 -- Visible define inlng = 80; -- Input file's line length (in MNTR)
 Visible define utlng = 200;    -- Output file's line length

 Visible const infix(string)
       nostring=record:string(nchr=0,chradr=noname);

 Visible const infix(txtqnt)
       notext=record:txtqnt(sp=0,lp=0,cp=0,ent=none);

 Visible range(0:MAX_ACT) actLvl; -- act_lvl
 Visible infix(txtqnt) txttmp; -- inline-coded text attr. procs use this
 Visible ref()           rstr; -- Used by RESTORE only
 Visible ref(inst)     rstr_x; -- for reuse of save objects

 Visible infix(bioIns) bio;    -- BASICIO infix object, must received
                               -- special treatment in GARB

%title ******   P r o t o t y p e s   ******
 Visible record ptp;
 begin ref(pntvec) refVec; -- pnt_vec
       ref(rptvec) repVec;
       ref(ptpExt) xpp;
       size lng;
 end;

 Visible record subptp:ptp; -- sub_ptp
 begin label cnt end;

 Visible record proptp:ptp; -- pro_ptp
 begin label       start;
       ref(atrvec) parVec;  -- par_vec
 end;

 Visible record claptp:ptp; -- cla_pre_ptp
 begin range(0:MAX_PLV) plv;
       label            dcl,stm,cntInr; -- cnt_inr
       ref(virvec)      virts;          -- vir_vec
       ref(claptp)      prefix(0);
 end;

 Visible record pntvec;
 begin range(0:MAX_CLV)    ncon;
       range(0:MAX_PNT)    npnt;
       field()             pnt(0);
 end;

 Visible record rptvec;
 begin short integer      npnt; -- type REF or TXT, must be first in
       short integer      nrep; --          rptvec (GC optimisation)
       infix(repdes)      rep(0);
 end;

 Visible record repdes;   info  "TYPE";
 begin short integer      nelt;
       range(0:MAX_TYPE)  type;
       field()            fld;
 end;

 Visible record virvec;
 begin range(0:MAX_VIR)   nvir;
       infix(virdes)      vir(0);
 end;

 Visible record virdes;   info  "TYPE";
 begin variant ref(proptp)     ppp; --  procedure
               ref(claptp)     qal;
       variant label           pad; --  label
       variant ref(swtdes)     des; --  switch
 end;

 Visible record atrvec;
 begin range(0:MAX_ATR)   natr;
       ref(atrdes)        atr(0);
 end;

 Visible record atrdes;
 ---   NOTE: named constant has fld=nofield and mode=M_LIT
 begin ref(idfier)           ident;
       field()               fld;
       range(0:MAX_MODE)     mode;
       range(0:MAX_KIND)     kind;
       range(0:MAX_TYPE)     type;
---    range(0:MAX_RBL)      rbl;    dropped jan 91 - not used
 end;

 Visible record refdes:atrdes;
 begin -- field(ref(inst))  con;     dropped jan 91 - not used
       ref(claptp)       qal;
 end;

 Visible record litdes:atrdes; -- descriptor of named const
 begin infix(quant) qnt; end;  -- has fld=nofield and mode=M_LIT

 Visible record swtdes;
 begin integer          nelt;
       infix(swtelt)   elt(0);
 end;

 Visible record swtelt; info  "TYPE";
 begin label            pad;
       field()          fld;
       range(0:MAX_RBL) rbl;
       range(0:MAX_CLV) clv;
       boolean          thk;
 end;
%title ******   E  n  t  i  t  i  e  s   ******

 Visible record entity;  info "DYNAMIC";
 begin ref(inst)                sl;   -- during GC used as GCL!!!!
       range(0:MAX_BYT)         sort;
       range(0:MAX_BYT)         misc;
       variant ref(ptp)         pp;   -- used for instances
       variant range(0:MAX_TXT) ncha; -- used for text entities
       variant size             lng;  -- used for other entities
 end;

 Visible record inst:entity;
 begin ref(entity)              gcl;
       variant ref(inst)        dl;
               label            lsc;
       variant entry(Pmovit)    moveIt;
 end;

%   Visible record booPro:inst; begin boolean       val end;
%   Visible record chaPro:inst; begin character     val end;
%   Visible record sinPro:inst; begin short integer val end;
%   Visible record intPro:inst; begin integer       val end;
%   Visible record reaPro:inst; begin real          val end;
%   Visible record lrlPro:inst; begin long real     val end;
%   Visible record refPro:inst; begin ref(inst)     val end;
%   Visible record txtPro:inst; begin infix(txtqnt) val end;
%   Visible record ptrPro:inst; begin ref()         val end;

 Visible record booPro:inst; begin variant boolean       val; variant infix(quant) qnt end;
 Visible record chaPro:inst; begin variant character     val; variant infix(quant) qnt end;
 Visible record sinPro:inst; begin variant short integer val; variant infix(quant) qnt end;
 Visible record intPro:inst; begin variant integer       val; variant infix(quant) qnt end;
 Visible record reaPro:inst; begin variant real          val; variant infix(quant) qnt end;
 Visible record lrlPro:inst; begin variant long real     val; variant infix(quant) qnt end;
 Visible record refPro:inst; begin variant ref(inst)     val; variant infix(quant) qnt end;
 Visible record txtPro:inst; begin variant infix(txtqnt) val; variant infix(quant) qnt end;
 Visible record ptrPro:inst; begin variant ref()         val; variant infix(quant) qnt end;

 Visible record thunk:inst;
 begin boolean      simple;
       infix(quant) val;
 end;

 Visible record savent:inst;
 begin  end;

 Visible record filent:inst;
 begin ref(filent)        prd;
       ref(filent)        suc;
       infix(txtqnt)      nam;
       infix(txtqnt)      img;
       integer            loc;
       integer            bsize;
       range(0:MAX_FIL)   type;
       range(0:MAX_KEY)   key;
       boolean            eof;
       boolean            locked;
       range(0:MAX_KEY)   trc;  -- 0:no trace, >0:index to trace table
       infix(txtqnt)      action;
 end;

 Visible record prtent:filent;
 begin integer            spc;
       integer            lpp;
       integer            lin;
       integer            pag;
 end;

 --- when bioIns is changed, remember also bioPtp in MNTR !!!!!

 Visible record bioIns:inst;
 begin ref(entity)            nxtAdr;   -- NOT in bioptp'refvec
       ref(entity)            lstAdr;   -- NOT in bioptp'refvec
       ref(prtEnt)            sysout;
       ref(filent)            sysin;
       ref(filent)            files;
       ref(filent)            opfil;    -- USED DURING OPEN
       ref(txtent)            opimg;    -- USED DURING OPEN
       ref(thunk)             thunks;
       ref(txtAr1)            conc;     -- For text conc. - pje sep 87
       ref(entity)            smbP1;    -- SIMOB parameter
       ref(entity)            smbP2;    -- SIMOB extra param (detach)
       integer                edOflo;
       long real              initim;
       range(0:MAX_ACT)       actLvl;
       range(0:MAX_EVT)       obsEvt;
       range(0:MAX_BYT)       pgleft; -- SIMOB-page lines left to write
       range(0:MAX_BYT)       pgsize; -- SIMOB-page lines per page
       range(0:MAX_BYT)       utpos;  -- Current output pos (0..utlng-1)
       range(0:MAX_KEY)       logfile; -- 0: no logfile attached
       boolean                logging; -- true: logfile att. and active
       boolean                stp;
       boolean                trc;
       boolean                realAr;  -- true if real arithm. possible
       character              lwten;
       character              dcmrk;
       character        utbuff(utlng);    -- The output buffer
       --- inbuff moved to MNTR, dumbuf moved to UTIL
       character           ebuf(600); -- edit buffer (leftadj/exactfit)
       short integer          GCval;  -- GCutil 2'param and return val
       infix(txtqnt)          simid;
       infix(labqnt)          erh;      -- Current error return label
       infix(quant)           ern;      -- Current error return variable
       ref(entity)            globalI;
       infix(string)          errmsg;   -- NOT in bioptp'refvec
 end;

 Visible record arbody:entity;
 begin
       ref(arhead) head;
 end;

 Visible record booArr:arbody begin boolean         elt(0) end;
 Visible record chaArr:arbody begin character       elt(0) end;
 Visible record sinArr:arbody begin short integer   elt(0) end;
 Visible record intArr:arbody begin integer         elt(0) end;
 Visible record reaArr:arbody begin real            elt(0) end;
 Visible record lrlArr:arbody begin long real       elt(0) end;
 Visible record refArr:arbody begin ref(inst)       elt(0) end;
 Visible record txtArr:arbody begin infix(txtqnt)   elt(0) end;
 Visible record ptrArr:arbody begin ref()           elt(0) end;

 Visible record arhead:entity;
 begin range(0:MAX_DIM)    ndim;
       integer             nelt;
       infix(arrbnd)       bound(0);
 end;

 Visible record arrbnd;  info  "TYPE";
 begin integer   lb;
       integer   ub;
       variant   integer   dope;
       variant   integer   negbas;
 end;

 Visible record arent2:entity;
 begin integer lb_1;
       integer ub_1;
       integer dope;
       integer lb_2;
       integer ub_2;
       integer negbas;
 end;

 Visible record booAr2:arent2 begin boolean       elt(0) end;
 Visible record chaAr2:arent2 begin character     elt(0) end;
 Visible record sinAr2:arent2 begin short integer elt(0) end;
 Visible record intAr2:arent2 begin integer       elt(0) end;
 Visible record reaAr2:arent2 begin real          elt(0) end;
 Visible record lrlAr2:arent2 begin long real     elt(0) end;
 Visible record refAr2:arent2 begin ref(inst) elt(0) end;
 Visible record txtAr2:arent2 begin infix(txtqnt) elt(0) end;
 Visible record ptrAr2:arent2 begin ref()         elt(0) end;

 Visible record arent1:entity;
 begin integer lb;
       integer ub;
 end;

 Visible record booAr1:arent1 begin boolean       elt(0) end;
 Visible record chaAr1:arent1 begin character     elt(0) end;
 Visible record sinAr1:arent1 begin short integer elt(0) end;
 Visible record intAr1:arent1 begin integer       elt(0) end;
 Visible record reaAr1:arent1 begin real          elt(0) end;
 Visible record lrlAr1:arent1 begin long real     elt(0) end;
 Visible record refAr1:arent1 begin ref(inst) elt(0) end;
 Visible record txtAr1:arent1 begin infix(txtqnt) elt(0) end;
 Visible record ptrAr1:arent1 begin ref()         elt(0) end;

 Visible record txtqnt;  info "TYPE";
 begin ref(txtent)        ent;
       range(0:MAX_TXT)   cp;
       range(0:MAX_TXT)   sp;
       range(0:MAX_TXT)   lp;
 end;

 Visible record txtent:entity;
 begin character cha(0); end;

 Visible record nonObj:inst;
 begin character cha(0); end;

%title ******   Q  u  a  n  t  i  t  i  e  s   ******

 Visible record labqnt;  info "TYPE";
 begin ref(inst)           sl;
       label              pad;
       range(0:MAX_CLV)   clv;
 end;

 Visible record proqnt;  info "TYPE";
 begin ref(inst)      sl;
       ref(proptp)   ppp;
       ref(claptp)   qal;
 end;

 Visible record swtqnt;  info "TYPE";
 begin ref(inst)      sl;
       ref(swtdes)   des;
 end;

 Visible record pardes;
 begin range(0:MAX_PAR)    code;
       range(0:MAX_KIND)   kind;
       range(0:MAX_TYPE)   type;
 end;

 Visible record refPar:pardes;
 begin ref(claptp) qal end;

 Visible record litPar:pardes;
 begin variant  boolean         l_boo;
       variant  character       l_cha;
       variant  short integer   l_sin;
       variant  integer         l_int;
       variant  real            l_rea;
       variant  long real       l_lrl;
       variant  ref(inst)       l_ref;
       variant  ref(txtqnt)     l_txt;
       variant  ref()           l_ptr;
 end;

 Visible record thkPar:pardes;
 begin label pad end;

 Visible record refThk:refPar;
 begin label pad end;

 Visible record PARQNT;  info  "TYPE";
 begin ref(atrdes) fp;
       ref(pardes) ap;
       ref(entity) ent;
       boolean sem;
       variant field() fld;
       variant label pad;
               range(0:MAX_CLV) clv;
       variant ref(proptp) ppp;
               ref(claptp) qal;
       variant ref(swtdes) des;
 end;

 Visible record quant;   info "TYPE";
 begin variant boolean       boo; -- boolean quantity
       variant character     cha; -- character quantity
       variant short integer sin; -- short integer quantity
       variant integer       int; -- integer quantity
       variant real          rea; -- real quantity
       variant long real     lrl; -- long real quantity
       variant ref(inst)     pnt; -- instance reference quantity
       variant infix(txtqnt) txt; -- text reference quantity
       variant ref(entity)   arr; -- array quantity
       variant infix(proqnt) pro; -- procedure quantity
       variant infix(labqnt) lab; -- label quantity
       variant infix(swtqnt) swt; -- switch quantity
       variant ref(inst)     ins; --  pointer to some instance
               field()       fld; --  offset within that instance
       variant ref(entity)   ent; --  pointer to some entity
       variant ref()         ptr; --  pointer to some object unit
 end;

%title ***   M o d u l e   a n d   S i m o b   I  n f o   ***

 Visible record ptpExt;  --- Prototype extension
 begin ref(idfier)          idt;
       ref(modinf)       modulI;
       ref(atrvec)        attrV; -- List of attributes (or none).
       range(0:MAX_BLK)  blkTyp; -- Block type (SUB/PRO/FNC/CLA/PRE)
 end;

 Visible record idfier;   -- identifier
 begin range(0:MAX_BYT)   ncha;
       character          cha(0);
 end;

 Visible record modinf;
 begin ref(modvec)    modulV;   -- reference to list of all modules used
                                -- by this module, =ONONE if no modules
       ref(idfier)    simIdt;   -- Class/procedure identifier,
                                -- =ONONE for a program
       ref(idfier)    source;   -- Name of source file, =ONONE
                                -- if source file is sysin
       ref(idfier)    modIdt;   -- Module identifier in case
                                -- of a separate compilation,
                                -- else =ONONE
       integer         check;   -- Checkcode in case of
                                -- separate compilation, else 0
       range(0:16000) rtsRel;   -- RTS version
       range(0:16000) fecRel;   -- FEC version
       range(0:2)     obsLvl;   -- Denotes the level at which this
                                -- module has been compiled.
                                -- 0:min, 1:descriptor, 2:full
 end;

 Visible record modvec;
 begin range(0:MAX_2BT) nmod;   -- number of modules included
       infix(moddes) mod(0);
 end;

 Visible record moddes;
 begin ref(ptp) pp;               -- prototype of ext. proc or class.
       integer check;             -- expected checkcode
 end;

 end;
