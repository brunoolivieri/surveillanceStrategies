@echo on

cd..

set MAX_TRIES=26
set ROUNDS=3750000


FOR /L %%A IN (1,1,%MAX_TRIES%) DO (
	
	FOR %%U IN (5 10 15 20 25) DO (

	  java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds %ROUNDS% -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen %%U UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 100 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
	)
)
