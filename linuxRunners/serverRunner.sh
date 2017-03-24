#!/bin/bash

cd ..

LOOPS=1
#ROUNDS=3 750 000 
ROUNDS=10
#REFRESHRATE=3750000
REFRESHRATE=3750000


#N_UAV=5
N_POI=20


for SNAME in TSPbasedMobility
do

for N_UAV in 2 4 8 16
do

	for ((i=1; i<=LOOPS; i++)); do

		echo " "
		echo " ----------------------------------------------------------------------------"
		echo " "
		echo "nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i
		date >> acompanhamento.txt
		echo "nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i >> acompanhamento.txt
		echo " "
		echo " ----------------------------------------------------------------------------"
		echo " "

		java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery

		#forced antTSP
		#java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:AntiTSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery


		done

done

done


# NaiveOrderedMobility NotSoNaiveOrderedMobility ZigZagOverNaiveMobility ZigZagOverNSNMobility KingstonImprovedOverNaiveMobility KingstonImprovedOverNSNMobility

for ((i=1; i<=LOOPS; i++)); 
do


	for N_UAV in 2 4 8 16
	do

	#full: NaiveOrderedMobility NotSoNaiveOrderedMobility ZigZagOverNaiveMobility ZigZagOverNSNMobility KingstonImprovedOverNaiveMobility KingstonImprovedOverNSNMobility

	for SNAME in KingstonImprovedOverNSNMobility ZigZagOverNSNMobility
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

		echo "dispatching first test do background..."  
        N_UAV=16
		nohup java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery &

		echo "dispatching second test do background..."  
		N_UAV=2
		nohup java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery &
		
		echo "dispatching third test do background..."  		
		N_UAV=4
		nohup java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery &
		
		echo "dispatching fourth test do run here..."  
		N_UAV=8		
		java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
		
		
	done

	done

done

