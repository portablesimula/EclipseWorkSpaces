&2025-05-10 09:23:54.647'Simuletta'JavarRT ?  rSYSR ?rKNWN ?^rUTIL ?_4  PROGRAM_NAME    STRING4  PURPOSE    STRING4  FACIT    STRING 	&  
TRACE:BODY  TRACE   REC  PLV 1     NEXT 	4  CREC1  REC4  CREC2  REC  TRACE"  MSG    STRING%(  NERROR (  	TRACECASE 5  PROGRAM_NAME    STRING SIMULETTA TEST NO 405  PURPOSE    STRING Fixup Object Address5  FACIT    STRING 	 
BEGIN TEST x=CSEG_SMLTST40[38] i=4444 j=8888 CREC1.plv=666 CREC1.next=CSEG_SMLTST40[38] CREC2.plv=777 CREC2.next=CSEG_SMLTST40[35] END TEST5  X   CREC25  I 44445  CREC1  REC
  REC  PLV 1   666  NEXT   CREC2	5  J 88885  CREC2  REC
  REC  PLV 1   777  NEXT   CREC1	'  
TRACE:BODY  TRACEC  ΞVERBOSEF  ΟVERBOSE:BODYQ[|C  ED_STR  MSGDF  ED_STR:BODYC  ED_STR Q   TEST AGAINST FACIT:  DF  ED_STR:BODYC  JPRTP  FACIT  	TRACECASEDF  KPRT:BODYY77C  CSTREQL  MSGDP  FACIT  	TRACECASEDF  DSTREQL:BODYmQ[|  NERROR Q1bP  NERRORTC  JPRT Q  DF  KPRT:BODYC  ED_STR Q ERROR in Case DF  ED_STR:BODYC  ED_INT  	TRACECASEDF  ED_INT:BODYC  VED_OUTF  WED_OUT:BODYC  ED_STR Q Trace: DF  ED_STR:BODYC  JPRT  MSGDF  KPRT:BODYC  ED_STR  Q Facit: DF  ED_STR:BODYC  JPRTP  FACIT  	TRACECASEDF  KPRT:BODYY77  	TRACECASE #Q1bP  	TRACECASET)C  ΞVERBOSEF  ΟVERBOSE:BODYQ[|C  MPRT2 0Q --- D  PROGRAM_NAMEDF  N	PRT2:BODYC  MPRT2 0Q --- D  PURPOSEDF  N	PRT2:BODYY77C  TRACE 3Q 
BEGIN TESTDF  
TRACE:BODYC  ED_STR 5Q x=DF  ED_STR:BODYC  AED_OADDR  XDF  BED_OADDR:BODYC  TRACEC  zGET_EDF  {GET_ED:BODYDF  
TRACE:BODYC  ED_STR 6Q i=DF  ED_STR:BODYC  ED_INT  IDF  ED_INT:BODYC  TRACEC  zGET_EDF  {GET_ED:BODYDF  
TRACE:BODYC  ED_STR 7Q j=DF  ED_STR:BODYC  ED_INT  JDF  ED_INT:BODYC  TRACEC  zGET_EDF  {GET_ED:BODYDF  
TRACE:BODYC  ED_STR 9Q 
CREC1.plv=DF  ED_STR:BODYC  ED_INTP  CREC1  PLVDF  ED_INT:BODYC  TRACEC  zGET_EDF  {GET_ED:BODYDF  
TRACE:BODYC  ED_STR :Q CREC1.next=DF  ED_STR:BODYC  AED_OADDRP  CREC1  NEXTDF  BED_OADDR:BODYC  TRACEC  zGET_EDF  {GET_ED:BODYDF  
TRACE:BODYC  ED_STR <Q 
CREC2.plv=DF  ED_STR:BODYC  ED_INTP  CREC2  PLVDF  ED_INT:BODYC  TRACEC  zGET_EDF  {GET_ED:BODYDF  
TRACE:BODYC  ED_STR =Q CREC2.next=DF  ED_STR:BODYC  AED_OADDRP  CREC2  NEXTDF  BED_OADDR:BODYC  TRACEC  zGET_EDF  {GET_ED:BODYDF  
TRACE:BODYC  TRACE ?Q END TESTDF  
TRACE:BODY  NERROR CQ0[|C  MPRT2 CQ --- NO ERRORS FOUND IN D  PROGRAM_NAMEDF  N	PRT2:BODYY77C  ΞVERBOSEF  ΟVERBOSE:BODYQ[|C  ED_STR DQ --- END DF  ED_STR:BODYC  ED_STR  PROGRAM_NAMEDF  ED_STR:BODYC  ED_STR DQ  -- nError=DF  ED_STR:BODYC  ED_INT  NERRORDF  ED_INT:BODYC  VED_OUTF  WED_OUT:BODYY77