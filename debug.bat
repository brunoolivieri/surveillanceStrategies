@echo on

set MAX_TRIES=50
set ROUNDS=259200
rem set ROUNDS=3750000
rem set N_POI=20

rem set STRATS=KingstonImprovedOverNSNMobility ZigZagOverNSNMobility
rem set STRATS=TSPConcordeMobility ZigZagOverLKHMobility ZigZagPartedOverNSNMobility ZigZagOverNSNMobility
rem set STRATS=TSPConcordeMobility ZigZagOverLKHCuttedMobility ZigZagOverLKHMobility ZigZagPartedOverNSNMobility ZigZagOverNSNMobility

set STRATS=TSPConcordeMobility ZigZagOverLKHCuttedMobility ZigZagOverLKHMobility ZigZagPartedOverNSNMobility

FOR /L %%A IN (31,1,%MAX_TRIES%) DO (

	FOR %%P IN (20 200) DO (

		FOR %%U IN (4 8 12 16) DO (
		
			FOR %%S in (%STRATS%) do (

				java -cp binaries/bin sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/13000x13000x1000POIs/%%A.txt -project UAV_Surveillance -rounds %ROUNDS% -refreshRate %ROUNDS% -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %%U UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:%%S  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %%P UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
			)
		)
	)
)

FOR /L %%A IN (31,1,%MAX_TRIES%) DO (

	FOR %%P IN (200) DO (

		FOR %%U IN (20 24) DO (
		
			FOR %%S in (%STRATS%) do (

				java -cp binaries/bin sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/13000x13000x1000POIs/%%A.txt -project UAV_Surveillance -rounds %ROUNDS% -refreshRate %ROUNDS% -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %%U UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:%%S  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %%P UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
			)
		)
	)
)
