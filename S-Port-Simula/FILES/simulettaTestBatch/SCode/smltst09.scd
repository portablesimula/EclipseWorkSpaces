�&2025-05-10 09:23:54.267'Simuletta'Java�rRT ?  �rSYSR ?��rKNWN ?�^rUTIL ?_�4  �PROGRAM_NAME    STRING4  �PURPOSE    STRING4  �FACIT    STRING 
&  �
TRACE:BODY  �TRACE  �TRACE"  �MSG    STRING%(  �NERROR (  �	TRACECASE (  �A (  �B (  �I (  �J 5  �PROGRAM_NAME    STRING SIMULETTA TEST NO 095  �PURPOSE    STRING 'Boolean Operators ( and, or, xor, not )5  �FACIT    STRING 
 
BEGIN TEST *a and b=false, b and c=false, c and e=true &a or b=false, b or c=true, c or e=true *a xor b=false, b xor c=true, c xor e=false &i and j=0x0, j and k=0x0, k and l=0xAD $i or j=0x0, j or k=0xFF, k or l=0xFF 'i xor j=0x0, j xor k=0xFF, k xor l=0x52 ( not l)=0xFFFFFF52 k and ( not l))=0x52 END TEST5  �C 5  �D 5  �E 5  �K 2555  �L 173'  �
TRACE:BODY  �TRACEC  �VERBOSEF  �VERBOSE:BODYQ[|C  ED_STR�  �MSGDF  ED_STR:BODYC  ED_STR Q   TEST AGAINST FACIT:  DF  ED_STR:BODYC  JPRTP  �FACIT�  �	TRACECASE�DF  KPRT:BODYY77C  CSTREQL�  �MSGDP  �FACIT�  �	TRACECASE�DF  DSTREQL:BODYmQ[|�  �NERROR Q1bP  �NERROR�TC  JPRT Q  DF  KPRT:BODYC  ED_STR Q ERROR in Case DF  ED_STR:BODYC  ED_INT�  �	TRACECASEDF  ED_INT:BODYC  VED_OUTF  WED_OUT:BODYC  ED_STR Q Trace: DF  ED_STR:BODYC  JPRT�  �MSGDF  KPRT:BODYC  ED_STR  Q Facit: DF  ED_STR:BODYC  JPRTP  �FACIT�  �	TRACECASE�DF  KPRT:BODYY77�  �	TRACECASE #Q1bP  �	TRACECASE�T)C  �VERBOSEF  �VERBOSE:BODYQ[|C  MPRT2 (Q --- D�  �PROGRAM_NAMEDF  N	PRT2:BODYC  MPRT2 (Q --- D�  �PURPOSEDF  N	PRT2:BODYY77C  �TRACE +Q 
BEGIN TESTDF  �
TRACE:BODYC  ED_STR -Q a and b=DF  ED_STR:BODYC  (ED_BOOL�  �A�  �BhDF  )ED_BOOL:BODYC  ED_STR -Q 
, b and c=DF  ED_STR:BODYC  (ED_BOOL�  �B�  �ChDF  )ED_BOOL:BODYC  ED_STR -Q 
, c and e=DF  ED_STR:BODYC  (ED_BOOL�  �C�  �EhDF  )ED_BOOL:BODYC  �TRACEC  zGET_EDF  {GET_ED:BODYDF  �
TRACE:BODYC  ED_STR .Q a or b=DF  ED_STR:BODYC  (ED_BOOL�  �A�  �BiDF  )ED_BOOL:BODYC  ED_STR .Q 	, b or c=DF  ED_STR:BODYC  (ED_BOOL�  �B�  �CiDF  )ED_BOOL:BODYC  ED_STR .Q 	, c or e=DF  ED_STR:BODYC  (ED_BOOL�  �C�  �EiDF  )ED_BOOL:BODYC  �TRACEC  zGET_EDF  {GET_ED:BODYDF  �
TRACE:BODYC  ED_STR /Q a xor b=DF  ED_STR:BODYC  (ED_BOOL�  �A�  �BjDF  )ED_BOOL:BODYC  ED_STR /Q 
, b xor c=DF  ED_STR:BODYC  (ED_BOOL�  �B�  �CjDF  )ED_BOOL:BODYC  ED_STR /Q 
, c xor e=DF  ED_STR:BODYC  (ED_BOOL�  �C�  �EjDF  )ED_BOOL:BODYC  �TRACEC  zGET_EDF  {GET_ED:BODYDF  �
TRACE:BODYC  ED_STR 1Q i and j=DF  ED_STR:BODYC  "ED_HEX�  �I�  �JhDF  #ED_HEX:BODYC  ED_STR 1Q 
, j and k=DF  ED_STR:BODYC  "ED_HEX�  �J�  �KhDF  #ED_HEX:BODYC  ED_STR 1Q 
, k and l=DF  ED_STR:BODYC  "ED_HEX�  �K�  �LhDF  #ED_HEX:BODYC  �TRACEC  zGET_EDF  {GET_ED:BODYDF  �
TRACE:BODYC  ED_STR 2Q i or j=DF  ED_STR:BODYC  "ED_HEX�  �I�  �JiDF  #ED_HEX:BODYC  ED_STR 2Q 	, j or k=DF  ED_STR:BODYC  "ED_HEX�  �J�  �KiDF  #ED_HEX:BODYC  ED_STR 2Q 	, k or l=DF  ED_STR:BODYC  "ED_HEX�  �K�  �LiDF  #ED_HEX:BODYC  �TRACEC  zGET_EDF  {GET_ED:BODYDF  �
TRACE:BODYC  ED_STR 3Q i xor j=DF  ED_STR:BODYC  "ED_HEX�  �I�  �JjDF  #ED_HEX:BODYC  ED_STR 3Q 
, j xor k=DF  ED_STR:BODYC  "ED_HEX�  �J�  �KjDF  #ED_HEX:BODYC  ED_STR 3Q 
, k xor l=DF  ED_STR:BODYC  "ED_HEX�  �K�  �LjDF  #ED_HEX:BODYC  �TRACEC  zGET_EDF  {GET_ED:BODYDF  �
TRACE:BODYC  ED_STR 5Q 	( not l)=DF  ED_STR:BODYC  "ED_HEX�  �LmDF  #ED_HEX:BODYC  �TRACEC  zGET_EDF  {GET_ED:BODYDF  �
TRACE:BODYC  ED_STR 6Q k and ( not l))=DF  ED_STR:BODYC  "ED_HEX�  �K�  �LmhDF  #ED_HEX:BODYC  �TRACEC  zGET_EDF  {GET_ED:BODYDF  �
TRACE:BODYC  �TRACE 8Q END TESTDF  �
TRACE:BODY�  �NERROR <Q0[|C  MPRT2 <Q --- NO ERRORS FOUND IN D�  �PROGRAM_NAMEDF  N	PRT2:BODYY77C  �VERBOSEF  �VERBOSE:BODYQ[|C  ED_STR =Q --- END DF  ED_STR:BODYC  ED_STR�  �PROGRAM_NAMEDF  ED_STR:BODYC  ED_STR =Q  -- nError=DF  ED_STR:BODYC  ED_INT�  �NERRORDF  ED_INT:BODYC  VED_OUTF  WED_OUT:BODYY77�