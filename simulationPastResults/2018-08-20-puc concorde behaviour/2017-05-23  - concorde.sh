#!/bin/bash

cd ..
cd ..

#cd simulationResults/
#rm *.txt

#touch acompanhamento.txt
#touch stats_summary.txt

#cd ..
rm nohup.out

LOOPS=10
#a day in seconds
ROUNDS=2 #259200   # 72h in seconds
REFRESHRATE=2  #259200

GRUPOS_POIS=1
N_STRATS=1
LOOPS_EFETUADOS=0
TOTAL_DE_LOOPS=$(echo "($GRUPOS_POIS * $LOOPS * $N_STRATS)" | bc )  		


#N_UAV=5
#N_POI=20
V2X=V2V

HOST=$(hostname)
TIME=$(date)

./woofyMSG.sh "[$HOST][$TIME] Iniciando os testes do DACA e do FPPWR com 20POIs ..."

for ((i=1; i<=LOOPS; i++)); 
do
	for N_POI in 20
	do
		for SNAME in FPPWRMobility ZigZagOverNSNMobility TSPConcordeMobility
		do
			TIME=$(date)
			echo "$TIME  -->  nPOI = "$N_POI"  strat = "$SNAME"  loop = "$i   >> ./simulationResults/acompanhamento.txt
			
			#N_UAV=$(echo "($N_POI / 20)/1" | bc )  		
			N_UAV=4
			nohup java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/13000x13000x5000POIsxR71/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &

			#N_UAV=$(echo "($N_POI / 10)/1" | bc  ) 
			N_UAV=2
			nohup java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/13000x13000x5000POIsxR71/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &
			
			#N_UAV=$(echo "($N_POI / 4)/1" | bc  ) 
			N_UAV=8
			nohup java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/13000x13000x5000POIsxR71/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &
			
			#N_UAV=$(echo "($N_POI /2)/1" | bc  )  
			N_UAV=16
			java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/13000x13000x5000POIsxR71/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery
			
			
			LOOPS_EFETUADOS=$(echo "($LOOPS_EFETUADOS +1)" | bc  )
			
			PERCENTS=$(echo "($LOOPS_EFETUADOS *100)/$TOTAL_DE_LOOPS" | bc  )

			if ! ((PERCENTS % 10)); then			
				echo "LOOPS_EFETUADOS = $LOOPS_EFETUADOS ..."
				TIME=$(date)
				./woofyMSG.sh "[$HOST][$TIME] LOOPS_EFETUADOS = $LOOPS_EFETUADOS ... "
			fi
			
			
		done			
	done	
done

TIME=$(date)
./woofyMSG.sh "[$HOST][$TIME] Fim dos testes com DACA, Concorde e FPPWR com 20POIs ..."



echo "O script de execução parece ter terminado legal"
