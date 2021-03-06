#!/bin/bash

cd ..
cd ..

#cd simulationResults/
#rm *.txt

#touch acompanhamento.txt
#touch stats_summary.txt

#cd ..
rm nohup.out

MAPS=300
ULTIMOMAPA=200

#a day in seconds
ROUNDS=259200   # 72h in seconds
REFRESHRATE=259200

GRUPOS_POIS=9
N_STRATS=1
LOOPS_EFETUADOS=0
TOTAL_DE_LOOPS=$(echo "($GRUPOS_POIS * $MAPS * $N_STRATS)" | bc )
FOLDER="savedDistributions/13000x13000x1100POIsxR71/"

#N_UAV=5
#N_POI=20
V2X=V2V

HOST=$(hostname)
TIME=$(date)

#./woofyMSG.sh "..."
#./woofyMSG.sh "[$HOST][$TIME] Preparando ambientes ..."
#./woofyMSG.sh "..."
#./woofyMSG.sh "Serao $TOTAL_DE_LOOPS testes"
#./woofyMSG.sh "..."
echo "[$HOST];[$TIME]; começou " >> ./simulationResults/acompanhamento.txt


for N_POI in 16 32 48 50 80 160 250 320 500
do
	for ((i=ULTIMOMAPA; i<=MAPS; i++)); 
	do
		for SNAME in TSPConcordeMobility
		do
			TIME=$(date)
			echo "$TIME  -->  nPOI = "$N_POI"  strat = "$SNAME"  map = "$i   >> ./simulationResults/acompanhamento.txt
			
			N_UAV=4
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 

			rm nohup*
			N_UAV=2
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
			
			sleep 2
			rm nohup*
			N_UAV=8
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
			
			sleep 2
			rm nohup*
			N_UAV=16
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery
		done			
	done	
	./woofyMSG.sh "[$HOST][$TIME] Fim dos TSP x 100 com  $N_POI CHs, indo para o próximo "

done



TIME=$(date)
./woofyMSG.sh "[$HOST][$TIME] Fim dos TSP x100 TOTAL"
echo "[$HOST];[$TIME]; terminou TSP x 100 TOTAL " >> ./simulationResults/acompanhamento.txt

echo "O script de execução parece ter terminado legal"

