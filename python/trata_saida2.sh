#!/bin/bash


sed 's/%//g' $1 > tmp.txt
sed 's/MiliSegs//g' tmp.txt > tmp2.txt
sed 's/_TSP_thread//g' tmp2.txt > tmp3.txt

echo "Strategy;nPOIs;nUAV;nRounds;SucessTax;V2V_range;ctRounds;dimX;simumationTimeMS;TSP_threads;maxData;minData;globalAvgDelay;nMsgs;tourSize" > dadosBrutos.txt



sed 's/projects.UAV_Surveillance.models.mobilityModels.//g' tmp3.txt >> dadosBrutos.txt

rm tmp.txt
rm tmp2.txt
rm tmp3.txt

python parser.py



