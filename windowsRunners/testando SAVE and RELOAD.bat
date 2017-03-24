

kimp:

circle:
java -cp binaries/bin sinalgo.Run -V2V -SAVEFOLDER savedDistributions/3000x1250x10POIs/ -project UAV_Surveillance -rounds 1 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode Circle C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 

none yet:
java -cp binaries/bin sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x10POIs/2.txt -project UAV_Surveillance -rounds 1 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 


random & PoiDistributionNN:

java -cp binaries/bin sinalgo.Run -V2V -SAVEFOLDER savedDistributions/500x500x10POIs/ -project UAV_Surveillance -rounds 1 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 

java -cp binaries/bin sinalgo.Run -V2V -project UAV_Surveillance -rounds 1 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 


LOAD FROM FILE!!!
java -cp binaries/bin sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x20POIs/8.txt -project UAV_Surveillance -rounds 1 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 






tsp:
java -cp binaries/bin sinalgo.Run -V2V -SAVEFOLDER savedDistributions/3000x1250x10POIs/ -project UAV_Surveillance -rounds 3750000 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 

java -cp binaries/bin sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x10POIs/16.txt -project UAV_Surveillance -rounds 3750000 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 




