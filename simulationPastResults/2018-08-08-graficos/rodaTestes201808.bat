@echo on

cd ..
cd ..

set MAX_TRIES=25  rem são os mapas
set ROUNDS=2592 rem div 100

rem set ROUNDS=259200 rem 72 horas!

rem set ROUNDS=3750000
rem set N_POI=20


set STRATS=TSPConcordeMobility FPPWRMobility ZigZagOverNSNMobility ZigZagPartedOverNSNMobility ZigZagOverLKHMobility ZigZagOverLKHCuttedMobility 

FOR %%P IN (20 100 200) DO (

	FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

		FOR %%U IN (2 8) DO (
		
			FOR %%S in (%STRATS%) do (

				java -cp binaries/bin sinalgo.Run -V2V -LOADFILE savedDistributions/13000x13000x1000xR100POIs/%%A.txt -project UAV_Surveillance -rounds %ROUNDS% -refreshRate %ROUNDS% -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %%U UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:%%S  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %%P UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
			)
		)
	)
)

