@echo on

set MAX_TRIES=855
set n_POIS=5000

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

	rem ping 127.0.0.1 -n 3 > nul
	java -cp binaries/bin sinalgo.Run -V2V -SAVEFOLDER savedDistributions/13000x13000x5000POIsxR71/ -project UAV_Surveillance -rounds 1 -refreshRate 1 -batch exitAfter=true exitAfter/Rounds=1 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 1 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen %n_POIS% UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
)