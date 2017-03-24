@echo on

ping -n 4400 127.0.0.1 >nul


cd..

set MAX_TRIES=10
set ROUNDS=3750000
rem set ROUNDS=3750000
set N_POI=20

set STRATS=KingstonImprovedOverNSNMobility ZigZagOverNSNMobility


FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

	FOR %%S in (%STRATS%) do (

		FOR %%U IN (2 4 8 16) DO (
		  rem Despacha pro background e roda um aqui.
		  start /b runner.bat %ROUNDS% %N_POI% %%U %%S
		  start /b runner.bat %ROUNDS% %N_POI% %%U %%S
		  java -cp binaries/bin sinalgo.Run -V2I -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %%U UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:%%S  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %N_POI% UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
		)
	)
	
	
)

set TSPTRIES=30

FOR /L %%A IN (1,1,%TSPTRIES%) DO (

	FOR %%S in (TSPbasedMobility) do (

		FOR %%U IN (2 4 8 16) DO (

		  java -cp binaries/bin sinalgo.Run -V2I -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %%U UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:%%S  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %N_POI% UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
		)
	)
	
	
)
