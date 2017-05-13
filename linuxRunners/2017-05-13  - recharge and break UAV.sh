#!/bin/bash

cd ..

LOOPS=10
#a day in seconds
ROUNDS=259200   # 72h in seconds
REFRESHRATE=259200

#N_UAV=5
#N_POI=20
V2X=V2V

for ((i=1; i<=LOOPS; i++)); do
do
	for N_POI in 20
	do
		for SNAME in FPPWRMobility ZigZagOverNSNMobility TSPbasedMobility

			TIME=$(date)
			echo "$TIME  -->  nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i   >> acompanhamento.txt
			
			#N_UAV=$(echo "($N_POI / 20)/1" | bc )  		
			N_UAV=4
			nohup java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &

			#N_UAV=$(echo "($N_POI / 10)/1" | bc  ) 
			N_UAV=2
			nohup java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &
			
			#N_UAV=$(echo "($N_POI / 4)/1" | bc  ) 
			N_UAV=8
			nohup java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &
			
			#N_UAV=$(echo "($N_POI /2)/1" | bc  )  
			N_UAV=16
			java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery
			
		done			
	done
done

for ((i=1; i<=LOOPS; i++)); do
do
	for N_POI in 800
	do
		for SNAME in FPPWRMobility ZigZagOverNSNMobility

			TIME=$(date)
			echo "$TIME  -->  nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i   >> acompanhamento.txt
			
			#N_UAV=$(echo "($N_POI / 20)/1" | bc )  		
			N_UAV=4
			nohup java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &

			#N_UAV=$(echo "($N_POI / 10)/1" | bc  ) 
			N_UAV=2
			nohup java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &
			
			#N_UAV=$(echo "($N_POI / 4)/1" | bc  ) 
			N_UAV=8
			nohup java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &
			
			#N_UAV=$(echo "($N_POI /2)/1" | bc  )  
			N_UAV=16
			java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/30000x20000x100POIs/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery
			
		done			
	done
done

echo "O script de execução parece ter terminado legal"