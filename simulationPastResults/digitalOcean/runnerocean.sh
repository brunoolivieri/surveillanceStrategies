#!/bin/bash

cd ..
cd ..

#cd simulationResults/
#rm *.txt

#touch acompanhamento.txt
#touch stats_summary.txt

#cd ..
rm nohup.out

MAPS=5
MAPSPLUSONE=6
#a day in seconds
ROUNDS=2   # 72h in seconds
REFRESHRATE=259200

GRUPOS_POIS=1
N_STRATS=1
LOOPS_EFETUADOS=0
TOTAL_DE_LOOPS=$(echo "($GRUPOS_POIS * $MAPS * $N_STRATS)" | bc )
FOLDER="savedDistributions/13000x13000x1100POIsxR71/"
NPARALLELL=3

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


#abaixo soh gera mapas
#for ((i=1; i<=MAPS; i++)); 
#do
#	java -cp binaries/bin sinalgo.Run -SAVEFOLDER $FOLDER -batch exitAfter=true exitAfter/Rounds=1 -project UAV_Surveillance -rounds 1 -refreshRate 1 -gen 2 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:ZigZagOverNSNMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility  R=ReliableDelivery -gen 1100 UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionNN C=UDG I=NoInterference M=NoMobility R=ReliableDelivery
#done	


for N_POI in 1000
do
	for SNAME in TSPConcordeMobility ZigZagOverLKHCuttedMobility ZigZagOverLKHMobility
	do
		for N_UAV in 4
		do		
			i="1"
			while [ $i -lt $MAPSPLUSONE ]
			do
				for ((j=1; j < NPARALLELL; j++)); 
				do			
					nohup java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery &
					i=$[$i+1]
				done	
				
				sleep 1
				
				java -cp binaries/bin/. sinalgo.Run -V2V -REUSE -LOADFILE $FOLDER$i.txt -project UAV_Surveillance -rounds $ROUNDS -refreshRate $REFRESHRATE -batch exitAfter=true exitAfter/Rounds=$ROUNDS exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen $N_UAV UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=UAV_Surveillance:$SNAME R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery -gen $N_POI UAV_Surveillance:POInode UAV_Surveillance:PoiDistributionFromFile C=QUDG I=NoInterference M=NoMobility R=ReliableDelivery 
				i=$[$i+1] #aqui que pode dar merda  x=$(( $x + 1 ))  ((i++))
				
				rm nohup*	
				LOOPS_EFETUADOS=$(echo "($LOOPS_EFETUADOS +1)" | bc  )
				PERCENTS=$(echo "($LOOPS_EFETUADOS *100)/$TOTAL_DE_LOOPS" | bc  )
				if ! ((PERCENTS % 5)); then
						echo "LOOPS_EFETUADOS = $LOOPS_EFETUADOS ..."
						TIME=$(date)
						./woofyMSG.sh "[$HOST][$TIME] Testes = $LOOPS_EFETUADOS $PERCENTS% / Size=$N_POI / Map=$i.txt "
						echo "[$HOST];[$TIME]; Testes = $LOOPS_EFETUADOS ; $PERCENTS% ; Size= $N_POI ; Map=$i.txt " >> ./simulationResults/acompanhamento.txt
				fi
				
				sleep 1
				
			done	
		done 
	done
done

TIME=$(date)
./woofyMSG.sh "[$HOST][$TIME] Fim dos testes ...  Liberando slots de processamento para os demais."

echo "O script de execução parece ter terminado legal"
