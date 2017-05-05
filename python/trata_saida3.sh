#!/bin/bash

echo "Backuping results ..."

cp $1 $1.RAW-bckp.txt

echo "Formating results ..."

sed -i.bak 's/%//g' $1 
sed -i.bak 's/MiliSegs//g' $1
sed -i.bak 's/_TSP_thread//g' $1
sed -i.bak 's/map_//g' $1
sed -i.bak 's/.txt//g' $1
sed -i.bak 's/projects.UAV_Surveillance.models.mobilityModels.//g' $1 

echo "Creating clean data file ..."

echo "Strategy;nPOIs;nUAV;SucessTax;V2V_range;nRounds;dimX;simumationTimeMS;TSP_threads;maxData;minData;globalAvgDelay;nMsgs;tourSize;mapa" > cleanDataFile.txt

cat $1 >> cleanDataFile.txt


echo "First file to parser ..."
# python parser_results.py

echo " "
echo " "

echo "Second file to parser ..."
# python parser_delays.py

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



