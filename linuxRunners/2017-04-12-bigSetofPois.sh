#!/bin/bash

cd ..

LOOPS=10
ROUNDS=3
REFRESHRATE=3750000

#N_UAV=5
N_POI=20
V2X=V2V




for ((i=1; i<=LOOPS; i++)); do

	for N_POI in 120 140 160 180
	do

		#full: NaiveOrderedMobility NotSoNaiveOrderedMobility ZigZagOverNaiveMobility ZigZagOverNSNMobility KingstonImprovedOverNaiveMobility KingstonImprovedOverNSNMobility


		#forcing TSP run
		#java -cp binaries/bin/. sinalgo.Run -$V2X -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery



		for SNAME in KingstonImprovedOverNSNMobility
		do


			echo " "
			echo " ----------------------------------------------------------------------------"
			echo " "
			echo "nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i" N_POI = "$N_POI
			date >> acompanhamento.txt
			echo "nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i" N_POI = "$N_POI >> acompanhamento.txt
			echo " "
			echo " ----------------------------------------------------------------------------"
			echo " "

			echo "dispatching first test do background..."  
			N_UAV=$(echo "($N_POI / 20)/1" | bc )  		
			N_UAV=1
			echo "" 
			echo "     despachando POI= $N_POI e UAV= $N_UAV "
			echo ""
			nohup java -cp binaries/bin/. sinalgo.Run -$V2X -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery &

			echo "dispatching second test do background..."  
			N_UAV=$(echo "($N_POI / 10)/1" | bc  ) 
			N_UAV=1
			echo "" 
			echo "     despachando POI= $N_POI e UAV= $N_UAV "
			echo ""
			nohup java -cp binaries/bin/. sinalgo.Run -$V2X -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery &
			
			echo "dispatching third test do background..."  		
			N_UAV=$(echo "($N_POI / 4)/1" | bc  ) 
			N_UAV=1
			echo "" 
			echo "     despachando POI= $N_POI e UAV= $N_UAV "
			echo ""
			nohup java -cp binaries/bin/. sinalgo.Run -$V2X -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery &
			
			echo "dispatching fourth test do run here..."  
			N_UAV=$(echo "($N_POI /2)/1" | bc  )  
			N_UAV=1
			echo "" 
			echo "     despachando POI= $N_POI e UAV= $N_UAV "
			echo ""
			java -cp binaries/bin/. sinalgo.Run -$V2X -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
			
			
		done

	done

done


LOOPS=3

for ((i=1; i<=LOOPS; i++)); do

	for N_POI in 2 4 6 8 10 12 14 16 18 20 22 24 26
	do

		#forcing TSP run
		java -cp binaries/bin/. sinalgo.Run -$V2X -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility  R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
			

	done

done



echo "O script de execução terminou legal"
