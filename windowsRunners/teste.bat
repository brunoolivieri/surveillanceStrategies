@echo on

cd..

set MAX_TRIES=1

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (
  java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 10 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 100 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 

)

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (
  java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 20 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 100 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
)

FOR /L %%A IN (1,1,%MAX_TRIES%) DO (
  java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 40 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 100 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
)


FOR /L %%A IN (1,1,%MAX_TRIES%) DO (
  java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 80 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 100 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
)