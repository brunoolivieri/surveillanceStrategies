#!/bin/bash

cd ..

LOOPS=1
ROUNDS=1000000 
REFRESHRATE=1000000
N_UAV=5
#N_POI=15



for N_POI in 35 40 50
do

	for Sname in TSPbasedMobility
	do

	for ((i=1; i<=LOOPS; i++)); do

		echo " "
		echo " ----------------------------------------------------------------------------"
		echo " "
		echo " nPOI = "$N_POI"  strat = "$Sname"  i = "$i 
		date >> acompanhamento.txt
		echo "nPOI = "$N_POI"  strat = "$Sname"  i = "$i >> acompanhamento.txt
		echo " "
		echo " ----------------------------------------------------------------------------"
		echo " "
  
		java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$Sname R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
		

	done

	done

done



