java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 10 -gen 3 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:ZigZagOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 







java -cp binaries/bin sinalgo.Run -project UAV_Surveillance -rounds 3750000 -refreshRate 5 -gen 1 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:ZigZagOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 10 UAV_Surveillance:POInode Circle C=UDG I=NoInterference M=NoMobility R=ReliableDelivery 





















