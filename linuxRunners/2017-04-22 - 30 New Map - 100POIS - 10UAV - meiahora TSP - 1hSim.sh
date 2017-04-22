#!/bin/bash

cd ..

LOOPS=30
ROUNDS=36000
REFRESHRATE=36000

#N_UAV=5
#N_POI=100
V2X=V2V



for ((i=1; i<=LOOPS; i++)); do

	for N_POI in 100
	do
		for SNAME in ZigZagOverNSNMobility 
		do
			date >> acompanhamento.txt
			echo "nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i" N_POI = "$N_POI >> acompanhamento.txt
		
			N_UAV=$(echo "($N_POI /2)/1" | bc  )  
			N_UAV=10
			echo "" 
			echo "     despachando POI= $N_POI e UAV= $N_UAV "
			echo ""
			java -cp binaries/bin/. sinalgo.Run -V2V -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
			
			
		done

	done

done

N_POI=100

for SNAME in TSPbasedMobility
do

	for N_UAV in 10
	do

		for ((i=1; i<=LOOPS; i++)); do

			date >> acompanhamento.txt
			echo "nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i" N_POI = "$N_POI >> acompanhamento.txt

			java -cp binaries/bin/. sinalgo.Run -V2V -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=UDG I=NoInterference M=NoMobility R=ReliableDelivery

			done

	done

done

echo "O script de execução parece ter terminado legal"
