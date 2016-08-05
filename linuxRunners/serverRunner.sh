#!/bin/bash

cd ..

LOOPS=50
ROUNDS=3750000 
REFRESHRATE=3750000

#N_UAV=5
N_POI=30


for SNAME in TSPbasedMobility
do

	for N_UAV in 2 4 8 16
	do

	for ((i=1; i<=LOOPS; i++)); do

		echo " "
		echo " ----------------------------------------------------------------------------"
		echo " "
		echo "nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i 
		date >> acompanhamento_server.txt
		echo "nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i >> acompanhamento_server.txt
		echo " "
		echo " ----------------------------------------------------------------------------"
		echo " "
  
		java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
		
	done

	done

done
