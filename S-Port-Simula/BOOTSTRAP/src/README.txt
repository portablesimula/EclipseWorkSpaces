
To 'Bootstrap' the old SPORT Simula System, perform following steps:

1) Ensure that the new Open Source Simula System is available in  USR/Simula/Simula-2.0

2) Make the Simuletta FrontEnd Compiler:
	- In Project SimulettaFEC
		- Run: Make_SimulettaFEC_Jarfile.java
			- Output:  SimulettaFEC.jar   ===>   RELEASE_HOME: C:/SPORT
		- Run: FECmpTESTS.java
		- Run: FECmpTBatch.java
		- And finally run: FECmpRTS.java   to produce
			- SCode of the Simula Runtime System in C:\GitHub\EclipseWorkSpaces\S-Port-Simula\FILES\simulaRTS\SCode
			- Interface files:
				- "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaFEC/src/fec/source/RTSINIT.ini"
				   C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FEC/src/fec/source/RTSINIT.ini
	
				- "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaFEC/src/fec/RTS-FEC-INTERFACE1.def"
				- "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/SimulaFEC/src/fec/RTS-FEC-INTERFACE2.def"
			
				- "C:/GitHub/EclipseWorkSpaces/S-Port-Simula/FILES/Attrs/FEC/PREDEF.atr"
		
3) Make the SPORT Simula FrontEnd Compiler:
	- In Project SimulaFEC run: Make_SimulaFEC_Jarfile.java
		- Output: C:/SPORT/SimulaFEC.jar
		
4) Compile Simula TestBatch to SCode:
	- In Project SimulaTestBatch_FEC run: Full_TestBatch2Scode.java
		- Output in: C:\GitHub\EclipseWorkSpaces\S-Port-Simula\SimulaTestBatch_FEC\src\simulaTestBatch\scode
	
5) Make and Test Common BackEnd Compiler:
	- In Project BEC
		- Run: Make_BEC_Jarfile.java
			- Output: CommonBEC.jar   ===>   RELEASE_HOME: C:/SPORT
		- Open bec.inlineTest in Project BEC
			- Run: MiniInlineTest.java
			- Run: RunMake_RTS.java
		- Run: RunFull_RTS_Tests.java
			- Output: 