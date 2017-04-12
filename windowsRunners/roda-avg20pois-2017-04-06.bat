@echo on

cd..

set MAX_TRIES=30
set ROUNDS=3750000
rem set ROUNDS=3750000
set N_POI=20

rem set STRATS=KingstonImprovedOverNSNMobility ZigZagOverNSNMobility
rem set STRATS=ZigZagOverNSNMobility KingstonImprovedOverNSNMobility

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

	FOR %%U IN (2 4 8 16) DO (

		REM KIMP
		java -cp binaries/bin sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x20POIs/%%A.txt -project UAV_Surveillance -rounds %ROUNDS% -refreshRate %ROUNDS% -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %%U UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %N_POI% UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 

 
		REM TSP
		java -cp binaries/bin sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x20POIs/%%A.txt -project UAV_Surveillance -rounds %ROUNDS% -refreshRate %ROUNDS% -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %%U UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %N_POI% UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 

	)
)
