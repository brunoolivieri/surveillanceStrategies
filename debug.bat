@echo on

set MAPS=3
set ROUNDS=2
rem set ROUNDS=3750000
rem set N_POI=20

rem set STRATS=KingstonImprovedOverNSNMobility ZigZagOverNSNMobility
rem set STRATS=TSPConcordeMobility ZigZagOverLKHMobility ZigZagPartedOverNSNMobility ZigZagOverNSNMobility
rem set STRATS=TSPConcordeMobility ZigZagOverLKHCuttedMobility ZigZagOverLKHMobility ZigZagPartedOverNSNMobility ZigZagOverNSNMobility

set STRATS=TSPConcordeMobility ZigZagOverLKHMobility



FOR /L %%A IN (1,1,%MAPS%) DO (

	FOR %%S in (%STRATS%) do (
	
		FOR /L %%B IN (1,1,5) DO (
			
			FOR %%U IN (8) DO (
		
				FOR %%P IN (200) DO (
					java -cp binaries/bin sinalgo.Run -V2V -LOADFILE savedDistributions/13000x13000x5000POIsxR71/%%A.txt -project UAV_Surveillance -rounds %ROUNDS% -refreshRate %ROUNDS% -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %%U UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:%%S  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %%P UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
				)
			)
		)
	)

)