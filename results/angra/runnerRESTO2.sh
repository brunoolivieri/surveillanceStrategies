#!/bin/bash

cd ..
cd ..

#cd simulationResults/
#rm *.txt

#touch acompanhamento.txt
#touch stats_summary.txt

#cd ..
rm nohup.out

MAPS=200
ULTIMOMAPA=1

#a day in seconds
ROUNDS=259200   # 72h in seconds
REFRESHRATE=259200

GRUPOS_POIS=3
GRUPOS_UAVS=4
N_STRATS=1
LOOPS_EFETUADOS=0
TOTAL_DE_LOOPS=$(echo "($GRUPOS_POIS * $GRUPOS_UAVS * $MAPS * $N_STRATS)" | bc )
FOLDER="savedDistributions/angra/"

#N_UAV=5
#N_POI=20
V2X=V2V

HOST=$(hostname)
TIME=$(date)

./woofyMSG.sh "[$HOST][$TIME] Serao $TOTAL_DE_LOOPS das 3 (ZZ ZZparted FPPWR)"

echo "[$HOST][$TIME] Serao $TOTAL_DE_LOOPS das 3 (ZZ ZZparted FPPWR); começou " >> ./simulationResults/acompanhamento.txt


for N_POI in 1000 500 250 50
do
	for ((i=ULTIMOMAPA; i<=MAPS; i++)); 
	do
		for SNAME in FPPWRMobility ZigZagOverNSNMobility ZigZagPartedOverNSNMobility  
		do
			TIME=$(date)
			echo "$TIME  -->  nPOI = "$N_POI"  strat = "$SNAME"  map = "$i   >> ./simulationResults/acompanhamento.txt
			
			N_UAV=4
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 

			#rm nohup*
			N_UAV=2
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
			
			sleep 2
			#rm nohup*
			N_UAV=8
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
			
			sleep 2
			#rm nohup*
			N_UAV=16
			java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery
		done			
	done	
	./woofyMSG.sh "[$HOST][$TIME] Fim dos 3 x 200 com  $N_POI CHs, indo para o próximo "

done



TIME=$(date)
./woofyMSG.sh "[$HOST][$TIME] Fim das 3 (ZZ ZZparted FPPWR) x 200  ... "
echo "[$HOST];[$TIME]; terminou 3 (ZZ ZZparted FPPWR) x 200" >> ./simulationResults/acompanhamento.txt

echo "O script de execução parece ter terminado legal"




