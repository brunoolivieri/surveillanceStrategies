#!/bin/bash

cd ..

LOOPS=2
ROUNDS=1000000 
REFRESHRATE=1000000
N_UAV=5
#N_POI=15



for N_POI in 20 25 30 35 40
do

	for Sname in TSPbasedMobility AntiTSPbasedMobility NaiveOrderedMobility RandomSafeMobility
	do

	for ((i=1; i<=LOOPS; i++)); do
  
		java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$Sname R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
		
		echo " "
		echo " ----------------------------------------------------------------------------"
		echo " "
		echo " nUAV = "$N_UAV"  strat = "$Sname"  i = "$i 
		echo " "
		echo " ----------------------------------------------------------------------------"
		echo " "



	done

	done

done



