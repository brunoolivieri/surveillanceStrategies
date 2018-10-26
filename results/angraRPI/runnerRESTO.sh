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
ULTIMOMAPA=100

#a day in seconds
ROUNDS=2   # 72h in seconds
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

#./woofyMSG.sh "[$HOST][$TIME] Serao $TOTAL_DE_LOOPS das 3 (ZZ ZZparted FPPWR)"

echo "[$HOST][$TIME] Serao $TOTAL_DE_LOOPS das 3 (ZZ ZZparted FPPWR) no RPI; começou " >> ./simulationResults/acompanhamento.txt

for ((i=ULTIMOMAPA; i<=MAPS; i++)); 
do
	for N_POI in 700 140 70
	do
		for SNAME in FPPWRMobility ZigZagOverNSNMobility ZigZagPartedOverNSNMobility  
		do
			TIME=$(date)
			#echo "$TIME  -->  nPOI = "$N_POI"  strat = "$SNAME"  map = "$i   >> ./simulationResults/acompanhamento.txt
			
			N_UAV=4
			java -cp binaries/bin/. sinalgo.Run -V2V -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
		done			
	done	
	#./woofyMSG.sh "[$HOST][$TIME] Fim dos 3 x 100 com  $N_POI CHs no RPI, indo para o próximo "

done



TIME=$(date)
./woofyMSG.sh "[$HOST][$TIME] Fim das 3 (ZZ ZZparted FPPWR) x 100 no RPI ... "
echo "[$HOST];[$TIME]; terminou 3 (ZZ ZZparted FPPWR) x 100 no RPI" >> ./simulationResults/acompanhamento.txt

echo "O script de execução parece ter terminado legal"




