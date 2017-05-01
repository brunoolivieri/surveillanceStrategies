#!/bin/bash

cd ..

LOOPS=100
ROUNDS=1
REFRESHRATE=360000

#N_UAV=5

N_POI=20
V2X=V2V

for SNAME in TSPbasedMobility
do

	for N_UAV in 1
	do

		for ((i=1; i<=LOOPS; i++)); do

			date >> acompanhamento.txt
			echo "nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i" N_POI = "$N_POI >> acompanhamento.txt

			java -cp binaries/bin/. sinalgo.Run -V2V -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=UDG I=NoInterference M=NoMobility R=ReliableDelivery

			done

	done

done

echo "O script de execução parece ter terminado legal"
