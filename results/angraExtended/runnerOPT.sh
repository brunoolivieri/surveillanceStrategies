#!/bin/bash

cd ..
cd ..

#cd simulationResults/
#rm *.txt

#touch acompanhamento.txt
#touch stats_summary.txt

#cd ..
#rm nohup.out

MAPS=1000
ULTIMOMAPA=200

#a day in seconds
ROUNDS=259200   # 72h in seconds
REFRESHRATE=259200

GRUPOS_POIS=3
GRUPOS_UAVS=1
N_STRATS=3
LOOPS_EFETUADOS=0
TOTAL_DE_LOOPS=$(echo "($GRUPOS_POIS * $GRUPOS_UAVS * $MAPS * $N_STRATS)" | bc )
FOLDER="savedDistributions/angra/"

#N_UAV=5
#N_POI=20
V2X=V2V

HOST=$(hostname)
TIME=$(date)

#./woofyMSG.sh "[$HOST][$TIME] Serao $TOTAL_DE_LOOPS das OPT)"

echo "[$HOST][$TIME] Serao $TOTAL_DE_LOOPS das OPT; começou " >> ./simulationResults/acompanhamento.txt

for ((i=ULTIMOMAPA; i<=MAPS; i++)); 
do
	for N_POI in 70 140 700
	do
		for SNAME in TSPConcordeMobility ZigZagOverLKHMobility ZigZagOverLKHCuttedMobility  
		do
			TIME=$(date)
			#echo "$TIME  -->  nPOI = "$N_POI"  strat = "$SNAME"  map = "$i   >> ./simulationResults/acompanhamento.txt
			
			N_UAV=4
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
			
			N_UAV=2
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 	
			
			N_UAV=8
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
			
			N_UAV=16
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery
		done			
	done	

	LOOPS_EFETUADOS=$(echo "($LOOPS_EFETUADOS +1)" | bc  )
	PERCENTS=$(echo "($LOOPS_EFETUADOS *100)/$TOTAL_DE_LOOPS" | bc  )
	if ! ((PERCENTS % 10)); then
			echo "LOOPS_EFETUADOS = $LOOPS_EFETUADOS ..."
			TIME=$(date)
			./woofyMSG.sh "[$HOST][$TIME] Testes OPT = $LOOPS_EFETUADOS $PERCENTS% / Size=$N_POI / Map=$i.txt "
			echo "[$HOST];[$TIME]; Testes = $LOOPS_EFETUADOS ; $PERCENTS% ; Size= $N_POI ; Map=$i.txt " >> ./simulationResults/acompanhamento.txt
	fi
done



TIME=$(date)
./woofyMSG.sh "[$HOST][$TIME] Fim das OPT x 800  ... "
echo "[$HOST];[$TIME]; terminou as OPT x 800" >> ./simulationResults/acompanhamento.txt

echo "O script de execução parece ter terminado legal"




