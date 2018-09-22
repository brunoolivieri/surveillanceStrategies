#!/bin/bash

cd ..
cd ..


MAPS=1000
MAPSPLUSONE=1001
#a day in seconds
ROUNDS=259200   # 72h in seconds
REFRESHRATE=259200

GRUPOS_POIS=3
N_STRATS=3
LOOPS_EFETUADOS=0
TOTAL_DE_LOOPS=$(echo "($GRUPOS_POIS * $MAPS * $N_STRATS)" | bc )
FOLDER="savedDistributions/angra/"
NPARALLELL=3

#N_UAV=5
#N_POI=20
V2X=V2V

HOST=$(hostname)
TIME=$(date)

for N_POI in 70 140 700
do
	for SNAME in FPPWRMobility ZigZagOverNSNMobility ZigZagPartedOverNSNMobility
	do
		for N_UAV in 4 2 8 16
		do		
			i="200"
			while [ $i -lt $MAPSPLUSONE ]
			do
				for ((j=1; j < NPARALLELL; j++)); 
				do			
					nohup java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &
					i=$[$i+1]
				done	
				
				java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
				i=$[$i+1] #aqui que pode dar merda  x=$(( $x + 1 ))  ((i++))
								
			done	
			rm nohup*	

		done 
	done
	LOOPS_EFETUADOS=$(echo "($LOOPS_EFETUADOS +1)" | bc  )
	PERCENTS=$(echo "($LOOPS_EFETUADOS *100)/$TOTAL_DE_LOOPS" | bc  )
	if ! ((PERCENTS % 5)); then
			echo "LOOPS_EFETUADOS = $LOOPS_EFETUADOS ..."
			TIME=$(date)
			./woofyMSG.sh "[$HOST][$TIME] TestesRESTO = $LOOPS_EFETUADOS $PERCENTS% / Size=$N_POI / Map=$i.txt "
			echo "[$HOST];[$TIME]; Testes = $LOOPS_EFETUADOS ; $PERCENTS% ; Size= $N_POI ; Map=$i.txt " >> ./simulationResults/acompanhamento.txt
	fi
done

TIME=$(date)
./woofyMSG.sh "[$HOST][$TIME] Fim dos testes RESTO..."

echo "O script de execução parece ter terminado legal"
