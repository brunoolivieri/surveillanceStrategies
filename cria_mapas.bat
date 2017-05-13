@echo on

set MAX_TRIES=150

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

	rem ping 127.0.0.1 -n 3 > nul
	java -cp binaries/bin sinalgo.Run -V2V -SAVEFOLDER savedDistributions/13000x13000x1000POIs/ -project UAV_Surveillance -rounds 2 -refreshRate 1 -batch exitAfter=true exitAfter/Rounds=1 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 1000 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
)