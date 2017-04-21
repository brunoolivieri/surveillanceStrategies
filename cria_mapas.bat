@echo on

set MAX_TRIES=100

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

  rem ping 127.0.0.1 -n 3 > nul
  rem java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 3 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 15 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
)

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

	rem ping 127.0.0.1 -n 3 > nul  
	rem java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 3 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:ZigZagOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 15 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
)

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (

	rem ping 127.0.0.1 -n 3 > nul
java -cp binaries/bin sinalgo.Run -V2V -SAVEFOLDER savedDistributions/30000x20000x100POIs/ -project UAV_Surveillance -rounds 2 -refreshRate 1 -batch exitAfter=true exitAfter/Rounds=1 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 100 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
)