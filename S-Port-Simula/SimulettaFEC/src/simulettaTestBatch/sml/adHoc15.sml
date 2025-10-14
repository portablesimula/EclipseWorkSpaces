begin
   SYSINSERT RT,SYSR,KNWN;    


Visible const infix(string) ERR_MSG(MAX_ENO) = (

 "Unspecified error condition.",                              --   0
 "Invalid floating point operation.",                         --   1
 "Floating point division by zero.",                          --   2
 "Floating point overflow.",                                  --   3
 "Floating point underflow.",                                 --   4
 "Inexact result (floating point operation).",                --   5
 "Integer overflow.",                                         --   6
 "Integer division by zero.",                                 --   7
 "Illegal address trap.",                                     --   8
 "Illegal instruction trap.",                                 --   9
 "Breakpoint trap.",                                          --  10
 "User interrupt - NOT YET IMPLEMENTED.",                     --  11
 "Cpu time limit overflow.",                                  --  12
 "Continuation is impossible.",                               --  13
 "Start of statement exception - NOT YET IMPLEMENTED.",       --  14
 "Array index out of range",                                  --  15
 "Attempted attribute access through none",                   --  16
 "Trap caused by interrupt or exception:",                    --  17
 "Internal error in Simula System (RTS): ",                   --  18
 "Internal error in Simula System (ENV): ",                   --  19
 "Internal error in Simula System (SIMOB): ",                 --  20
 "Breakpoint trap, but no observation tool is available.",    --  21
 "Not enough primary storage for predefined instances.",      --  22
 "Qualification check fails.",                                --  23
 "Attempt to access attribute == NONE ('attr.y' or 'attr QUA ...').",
 "Unexpected non-zero status on return from environment routine call.",
 "Exponentiation: Result is undefined.",                      --  26
 "Impossible to satisfy the request, maybe because it is illegal.",
 "Actual parameter value is out of range.",                   --  28
 "The service function is not implemented.",                  --  29
 "Illegal action.",                                           --  30
 "Storage request cannot be met, not enough primary storage.",--  31
 "Requested termination: Not enough available storage after compaction",
 "Illegal goto destination.",                                 --  33
 "x.Detach: x is not on the operating chain.",                --  34
 "Resume(x): x is none.",                                     --  35
 "Resume(x): x is not local to sub-block or prefixed block instance",
 "Resume(x): x is not in detached state.",                    --  37
 "Process is not local to prefixed block, illegal implicit Resume.",
 "Implicit Resume(x): Process object x is not in detached state.",
 "Call(x): x is none.",                                       --  40
 "Call(x): x is not in detached state.",                      --  41
 "Wrong number of parameters in call on formal or virtual procedure.",
 "Virtual attribute has no match.",                           --  43
 "Lower/upperbound(a,i): illegal value of i.",                --  44
 "Incorrect number of array indices.",                        --  45
 "Array index value is out of bounds.",                       --  46
 "Array index value is out of bounds.",                       --  47
 "Blanks(n):  n is negative or too large.",                   --  48
 "Text value assignment x := y: x.Length < y.Length, maybe x == notext",
 "Text value assignment x := y: x.Constant = True.",          --  50
 "Sub(i,n):  i is less than 1.",                              --  51
 "Sub(i,n):  n is negative.",                                 --  52
 "t.Sub(i,n): i + n > t.Length + 1, maybe t == notext.",      --  53
 "t.Get...:  t == notext.",                                   --  54
 "t.Get...:  Non-numeric item.",                              --  55
 "t.Get...:  Numeric item is out of range.",                  --  56
 "t.Get...:  Numeric item is not complete.",                  --  57
 "t.Getchar:  t.More = False, maybe t == notext.",            --  58
 "t.Put...:  t == notext.",                                   --  59
 "t.Put...:  t.Constant = True.",                             --  60
 "t.Put...(r,n):  Fraction size specification n is negative.",--  61
 "t.Putchar:  t.More = False, maybe t == notext.",            --  62
 "Parameter called by name:",                                 --  63
 "Assignment to formal: Actual is no variable, cannot assign.",
 "Parameter transmission: Actual is no variable.",            --  65
 "The types of the actual and the formal parameter are different.",
 "Different qualifications of the actual and the formal parameter.",
 "Assignment to formal:  object is not subordinate to actual.",
 "Occurrence of formal: actual object is not subordinate to formal.",
 "Occurrence of formal: actual procedure is not subordinate to formal.",
 "Parameter transmission to formal or virtual procedure:",    --  71
 "Actual object is not subordinate to formal parameter.",     --  72
 "Actual procedure parameter is not subordinate to formal parameter.",
 "Actual and formal parameter are of different kinds.",       --  74
 "Actual and formal parameter are of different types.",       --  75
 "Actual and formal parameter are of incompatible types.",    --  76
 "Transplantation: actual and formal qualification are dynamically different.",
 "Qualification of actual and formal reference array do not coincide.",
 "Types of actual and formal procedure are neither coincident nor subordinate.",
 "file.Open:  The file is open already.",                     --  80
 "new ...file: FILENAME == notext.",                          --  81
 "file.OPEN = false.",                                        --  82
 "file.ENDFILE == true.",                                     --  83
 "file.In...:  file.image == notext  or  file not open.",     --  84
 "file.Inimage:  file.image.Constant = true.",                --  85
 "Directfile.Inimage:  End of file was encountered.",         --  86
 "file.In...:  Attempt to read through end of file.",         --  87
 "file.In...:  Non-numeric item.",                            --  88
 "file.In...:  Numeric item is out of range.",                --  89
 "file.In...:  Numeric item is not complete.",                --  90
 "file.Intext(n):  n is negative or too large.",              --  91
 "file.Out...:  file.image == notext  or  file not open.",    --  92
 "file.Out...(...,w):  w < 0.",                               --  93
 "file.Out...(...,w):  w > file.image.Length",                --  94
 "file.Out...:  file.image.Constant = true.",                 --  95
 "file.Out...(...,n,...):  Fraction size specification n is negative.",
 "file.Outtext(t):  t.Length > file.image.Length",            --  97
 "Printfile.Spacing(n):  n < 0  or  n > Linesperpage.",       --  98
 "Printfile.Eject(n):  n <= 0.",                              --  99
 "File.Close:  The file is closed already.",                  -- 100
 "Illegal file operation, not compatible with this file.",    -- 101
 "The external record format is not compatible with this directfile.",
 "File.Open:  Illegal file name.",                            -- 103
 "file.Out...:  Output image too long.",                      -- 104
 "file.In...:  Input image too long.",                        -- 105
 "file.Out...:  The file is full.",                           -- 106
 "Directfile:  Location out of range.",                       -- 107
 "I/O error, e.g. hardware fault.",                           -- 108
 "No write access to the file.",                              -- 109
 "File.Open:  Too many files open simultaneously.",           -- 110
 "No read access to the file.",                               -- 111
 "End of file has been encountered already.",                 -- 112
 "The file is closed",                                        -- 113
 "Simulation:  (Re)Activate empties SQS.",                    -- 114
 "Simulation:  Cancel,Passivate or Wait empties SQS",         -- 115
 "Process.Evtime:  The process is idle.",                     -- 116
 "Random drawing:  Actual array parameter is not one-dimensional.",
 "Histd(a,u):  An element of the array a is negative.",       -- 118
 "Linear(a,b,u):  The number of elements in a and b are different.",
 "Linear(a,b,u):  The array a does not satisfy the stated assumptions.",
 "Negexp(a,u) :  a <= 0.",                                    -- 121
 "Randint(a,b,U) or Uniform(a,b,U) :   b < a.",               -- 122
 "Erlang(a,b,u):  a <= 0  or  b <= 0.",                       -- 123
 "Normal(a,b,u):  b <= 0.",                                   -- 124
 "Erlang/Negexp/Normal/Poisson: parameter U <= 0",            -- 125
 "Histo(a,b,c,d):  Array parameter is not one-dimensional.",  -- 126
 "Histo(a,b,c,d):  number of elements in a <= number of elements in b.",
 "Standard function call:  Parameter value is out of range.", -- 128
 "Switch designator:  Index value is out of range.",          -- 129
 "Call on external non-SIMULA procedure:  actual label is not local.",
 "Lowten(c): c is illegal",                                   -- 131
 "Decimalmark(c): ci is neither '.' or ','",                  -- 132
 "RTS_Utility: first parameter is out of range",              -- 133
 "No such data set",                                          -- 134
 "Filename does not describe a data set",                     -- 135
 "File cannot be CREATEd, it exists already",                 -- 136
 "Open/Close: file access code is not implemented",           -- 137
 "A file operation cannot be performed"                       -- 138
 );


VIsible routine ED_ERR;
import range(0:MAX_ENO) eno; ref(filent) fil;
       range(0:MAX_ENO) msg,msx; range(0:MAX_STS) sts;
begin
      ---  Output the error message(s) from the RTS.
%-X   if msx <> 0 then PRT(ERR_MSG(msx)) endif;
%-X   if msg <> 0 then PRT(ERR_MSG(msg)) endif;
end;
	
 end;
