 begin sysinsert rt,sysr,knwn,util,strg,cent

	routine P; import infix(TXTQNT) txt; begin
		integer x;
		x := txt.CP;
	end;

	syspri("Hello World");
	P(notext);
	termin(0,"END OF PROGRAM");
end