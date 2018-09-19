#!/bin/bash

cd ..
cd ..

#cd simulationResults/
#rm *.txt

#touch acompanhamento.txt
#touch stats_summary.txt

#cd ..
rm nohup.out

MAPS=1000
MAPSPLUSONE=1001
#a day in seconds
ROUNDS=2   # 72h in seconds
REFRESHRATE=2  #259200

GRUPOS_POIS=1
N_STRATS=1
LOOPS_EFETUADOS=0
TOTAL_DE_LOOPS=$(echo "($GRUPOS_POIS * $MAPS * $N_STRATS)" | bc )
FOLDER="savedDistributions/angra/"
NPARALLELL=3

#N_UAV=5
#N_POI=20
V2X=V2V

HOST=$(hostname)
TIME=$(date)

./woofyMSG.sh "..."
./woofyMSG.sh "[$HOST][$TIME] Vou criar $MAPS mapas ..."
./woofyMSG.sh "..."


#abaixo soh gera mapas
for ((i=1; i<=MAPS; i++)); 
do
	java -cp binaries/bin sinalgo.Run -SAVEFOLDER $FOLDER -batch exitAfter=true exitAfter/Rounds=1 -project UAV_Surveillance -rounds 1 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:ZigZagOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility  R=ReliableDelivery -gen 1100 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
done	


TIME=$(date)
./woofyMSG.sh "[$HOST][$TIME] Terminei de criar $MAPS mapas ..."

echo "O script de execução parece ter terminado legal"
