#!/bin/bash

cd ..
cd ..

#cd simulationResults/
#rm *.txt

#touch acompanhamento.txt
#touch stats_summary.txt

#cd ..
rm nohup.out

MAPS=100
#a day in seconds
ROUNDS=2   # 72h in seconds
REFRESHRATE=259200

GRUPOS_POIS=5
N_STRATS=6
LOOPS_EFETUADOS=0
TOTAL_DE_LOOPS=$(echo "($GRUPOS_POIS * $MAPS * $N_STRATS)" | bc )


#N_UAV=5
#N_POI=20
V2X=V2V

HOST=$(hostname)
TIME=$(date)

./woofyMSG.sh "..."
./woofyMSG.sh "[$HOST][$TIME] Preparando ambientes ..."
./woofyMSG.sh "..."
./woofyMSG.sh "Serao $TOTAL_DE_LOOPS testes"
./woofyMSG.sh "..."


for N_POI in 50 100 150 200 300
do
	for ((i=1; i<=MAPS; i++)); 
	do
		for SNAME in TSPConcordeMobility FPPWRMobility ZigZagOverNSNMobility ZigZagPartedOverNSNMobility ZigZagOverLKHMobility ZigZagOverLKHCuttedMobility
		do
			TIME=$(date)
			#echo "$TIME  -->  nPOI = "$N_POI"  strat = "$SNAME"  map = "$i   >> ./simulationResults/acompanhamento.txt
			
			#N_UAV=$(echo "($N_POI / 20)/1" | bc )
			N_UAV=2
			java -cp binaries/bin/. sinalgo.Run -V2V -RECHARGE -CASUATIES -LOADFILE savedDistributions/13000x13000x5000POIsxR71/$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 

													
			LOOPS_EFETUADOS=$(echo "($LOOPS_EFETUADOS +1)" | bc  )

			PERCENTS=$(echo "($LOOPS_EFETUADOS *100)/$TOTAL_DE_LOOPS" | bc  )

			if ! ((PERCENTS % 10)); then
					echo "LOOPS_EFETUADOS = $LOOPS_EFETUADOS ..."
					TIME=$(date)
					./woofyMSG.sh "[$HOST][$TIME] Testes = $LOOPS_EFETUADOS $PERCENTS% / Size=$N_POI / Map=$i.txt "
					echo "[$HOST];[$TIME]; Testes = $LOOPS_EFETUADOS ; $PERCENTS% ; Size= $N_POI ; Map=$i.txt " >> ./simulationResults/acompanhamento.txt
			fi
			
		done			
	done	
done

TIME=$(date)
./woofyMSG.sh "[$HOST][$TIME] Fim dos testes ...  Liberando slots de processamento para os demais."

echo "O script de execução parece ter terminado legal"

