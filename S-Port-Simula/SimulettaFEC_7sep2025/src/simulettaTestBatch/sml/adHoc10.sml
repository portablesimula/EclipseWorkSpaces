begin
   SYSINSERT RT,SYSR,KNWN,UTIL;    
 
   boolean a,b,c=true,d=false,e=true,tst;
   integer i,j,k=255,l=173;
	
	prt("    AND");
	prt("  a  \  b  true  false");
	ed_str("  true     "); ed_bool(true and true); ed_str("  "); ed_bool(true and false); ed_out;
	ed_str("  false    "); ed_bool(false and true); ed_str(" "); ed_bool(false and false); ed_out;
	
	prt("    OR");
	prt("  a  \  b  true  false");
	ed_str("  true     "); ed_bool(true or true); ed_str("  "); ed_bool(true or false); ed_out;
	ed_str("  false    "); ed_bool(false or true); ed_str("  "); ed_bool(false or false); ed_out;
	
	prt("    XOR");
	prt("  a  \  b  true  false");
	ed_str("  true     "); ed_bool(true xor true); ed_str(" "); ed_bool(true xor false); ed_out;
	ed_str("  false    "); ed_bool(false xor true); ed_str("  "); ed_bool(false xor false); ed_out;
	
%	The bitwise logical operators are AND(&), OR(|), XOR(^), and NOT(~).
%	ed_str("i and j="); ed_hex(i and j); ed_out;

	ed_str("j="); ed_hex(j);
	ed_str(", k="); ed_hex(k);
	ed_str(", j and k="); ed_hex(j and k); ed_out;
%	ed_str("k and l="); ed_hex(k and l); ed_out;
	
 end;
	 