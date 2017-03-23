@echo on

setlocal enableextensions 
setlocal EnableDelayedExpansion


cd..

rem set MAX_TRIES=10
set MAX_TRIES=1
rem set ROUNDS=3750000
set ROUNDS=3750000

set N_POI=20

set STRATS=KingstonImprovedOverNSNMobility

rem start /b runner.bat %ROUNDS% %N_POI% %%U %%S
rem java -cp binaries/bin sinalgo.Run -V2V -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %UAVS% UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:%%S  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %%P UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 


FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

	FOR %%S in (%STRATS%) do (
 
		runner.bat %ROUNDS% 200 100 %%S PoiDistributionNN
		runner.bat %ROUNDS% 200 50 %%S PoiDistributionNN
		runner.bat %ROUNDS% 200 20 %%S PoiDistributionNN
		runner.bat %ROUNDS% 200 10 %%S PoiDistributionNN
		
		runner.bat %ROUNDS% 500 250 %%S PoiDistributionNN
		runner.bat %ROUNDS% 500 125 %%S PoiDistributionNN
		runner.bat %ROUNDS% 500 50 %%S PoiDistributionNN
		runner.bat %ROUNDS% 500 25 %%S PoiDistributionNN
		
		runner.bat %ROUNDS% 1000 500 %%S PoiDistributionNN
		runner.bat %ROUNDS% 1000 250 %%S PoiDistributionNN
		runner.bat %ROUNDS% 1000 100 %%S PoiDistributionNN
		runner.bat %ROUNDS% 1000 50 %%S PoiDistributionNN
		
	)
)
