#!/bin/bash

echo "Backuping results ..."

cp $1 $1.bckp

echo "Formating results ..."

sed 's/%//g' $1 
sed 's/MiliSegs//g' $1
sed 's/_TSP_thread//g' $1
sed 's/map_//g' $1
sed 's/.txt//g' $1


echo "Creating clean data file ..."

echo "Strategy;nPOIs;nUAV;SucessTax;V2V_range;nRounds;dimX;simumationTimeMS;TSP_threads;maxData;minData;globalAvgDelay;nMsgs;tourSize;mapa" > cleanDataFile.txt
sed 's/projects.UAV_Surveillance.models.mobilityModels.//g' $1 >> cleanDataFile.txt


echo "First file to parser ..."
python parser_results.py

echo " "
echo " "

echo "Second file to parser ..."
python parser_delays.py

echo " "
echo " "


echo "Creating parsed files to excel/BR ..."

sed 's/./,/g' delays_MEDIAN-excel-BR.txt
sed 's/./,/g'  delays_MEAN-excel-BR.txt
sed 's/./,/g'  delays_DESCRIBE-excel-BR.txt
sed 's/./,/g'  resultsMEDIAN-excel-BR.txt
sed 's/./,/g'  resultsMEAN-excel-BR.txt
sed 's/./,/g'  resultsDESCRIBE-excel-BR.txt

echo " "
echo " "



