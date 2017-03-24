#!/bin/bash

cd ..

LOOPS=1
#ROUNDS=3 750 000 
ROUNDS=10
REFRESHRATE=3750000
N_POI=20

# TSPbasedMobility NaiveOrderedMobility NotSoNaiveOrderedMobility ZigZagOverNaiveMobility ZigZagOverNSNMobility KingstonImprovedOverNaiveMobility KingstonImprovedOverNSNMobility

for ((i=1; i<=LOOPS; i++)); 
do

	for N_UAV in 2 4 8 16
	do
	echo " "
	echo " ----------------------------------------------------------------------------"
	echo " "
	echo " nPOI = "$N_POI"  strat = "$SNAME"  i = "$i 
	date >> acompanhamento.txt
	echo "nPOI = "$N_POI"  strat = "$SNAME"  i = "$i >> acompanhamento.txt
	echo " "
	echo " ----------------------------------------------------------------------------"
	echo " "

	#KingstonImprovedOverNSNMobility
	nohup java -cp binaries/bin/. sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x20POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:KingstonImprovedOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=UDG I=NoInterference M=NoMobility R=ReliableDelivery &
	
	#ZigZagOverNSNMobility
	java -cp binaries/bin/. sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x20POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:ZigZagOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=UDG I=NoInterference M=NoMobility R=ReliableDelivery

	#TSPbasedMobility
	java -cp binaries/bin/. sinalgo.Run -V2V -LOADFILE savedDistributions/3000x1250x20POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
	

	done

done

