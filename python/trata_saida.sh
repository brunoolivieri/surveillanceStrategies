#!/bin/bash


sed 's/,/\./g' stats_summary.txt > tmp.txt      
sed 's/;/,/g' tmp.txt > tmp2.txt      
sed 's/%//g' tmp2.txt > tmp3.txt



echo Strategy,nPOIs,nUAV,SucessTax,V2V_range,nRounds,dimX,simumationTimeMS,TSP_threads,maxData,minData > stat_em_tratamento.csv

sed 's/projects.UAV_Surveillance.models.mobilityModels.//g' tmp3.txt >> stat_em_tratamento.csv

rm tmp.txt
rm tmp2.txt
rm tmp3.txt