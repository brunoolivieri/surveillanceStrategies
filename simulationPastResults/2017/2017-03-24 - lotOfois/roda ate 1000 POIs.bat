@echo on

setlocal enableextensions 
setlocal EnableDelayedExpansion


cd..

rem set MAX_TRIES=10
set MAX_TRIES=10
rem set ROUNDS=3750000
set ROUNDS=375000

set N_POI=20

set STRATS=KingstonImprovedOverNSNMobility

rem start /b runner.bat %ROUNDS% %N_POI% %%U %%S
rem java -cp binaries/bin sinalgo.Run -V2V -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %UAVS% UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:%%S  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %%P UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 


FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

	FOR %%S in (%STRATS%) do (
 
		start /b java -cp binaries/bin sinalgo.Run  -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 100 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 200 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
		java -cp binaries/bin sinalgo.Run  -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 50 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 200 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
		start /b java -cp binaries/bin sinalgo.Run  -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 20 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 200 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
		java -cp binaries/bin sinalgo.Run  -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 10 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 200 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
		
	)
)

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

	FOR %%S in (%STRATS%) do (
 
		start /b java -cp binaries/bin sinalgo.Run  -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 25 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 500 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
		java -cp binaries/bin sinalgo.Run  -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 50 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 500 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
		start /b java -cp binaries/bin sinalgo.Run  -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 125 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 500 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
		java -cp binaries/bin sinalgo.Run  -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 250 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 500 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
	
		rem runner.bat %ROUNDS% 1000 500 %%S PoiDistributionNN
		rem runner.bat %ROUNDS% 1000 250 %%S PoiDistributionNN
		rem runner.bat %ROUNDS% 1000 100 %%S PoiDistributionNN
		rem runner.bat %ROUNDS% 1000 50 %%S PoiDistributionNN
		
	)
)
