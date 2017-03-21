java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 10 -gen 3 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:ZigZagOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 


java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:ZigZagOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode Circle C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 



kimp:
java -cp binaries/bin sinalgo.Run -V2V -SAVEFOLDER savedDistributions/3000x1250x10POIs/ -project UAV_Surveillance -rounds 1 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode Circle C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 

java -cp binaries/bin sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x10POIs/10.txt -project UAV_Surveillance -rounds 1 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 



tsp:
java -cp binaries/bin sinalgo.Run -V2V -SAVEFOLDER savedDistributions/3000x1250x10POIs/ -project UAV_Surveillance -rounds 3750000 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 

java -cp binaries/bin sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x10POIs/1.txt -project UAV_Surveillance -rounds 3750000 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 









kimp:

java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 37500 -refreshRate 3750000 -batch exitAfter=true exitAfter/Rounds=3750000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 
