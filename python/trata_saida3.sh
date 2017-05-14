#!/bin/bash

echo "Backuping results ..."

cp $1 $1.RAW-bckp.txt

echo 

echo "Ziping big files..."
zip stats_delays_full.zip  stats_delays_full.txt
export GZIP=-9
tar cvzf stats_delays_full.tar.gz stats_delays_full.txt
tar cvzf stats_delays_full.txt | split --bytes=200MB - delays.bckp.tar.gz
# rm stats_delays_full.txt

echo 

echo "Erasing nohup.out..."
rm ../nohup.out

echo 

echo "Backuping Config.xml..."
cp ../src/projects/UAV_Surveillance/Config.xml ./

echo "Formating results..."

sed -i.bak 's/%//g' $1 
sed -i.bak 's/MiliSegs//g' $1
sed -i.bak 's/_TSP_thread//g' $1
sed -i.bak 's/map_//g' $1
sed -i.bak 's/.txt//g' $1
sed -i.bak 's/projects.UAV_Surveillance.models.mobilityModels.//g' $1 

echo 

echo "Creating clean data file..."

echo "Strategy;nPOIs;nUAV;SucessTax;V2V_range;nRounds;dimX;simumationTimeMS;TSP_threads;maxData;minData;globalAvgDelay;GSglobalAvgDelay;nMsgs;tourSize;throughput;TaxPerPathSize;mapName" > cleanDataFile.txt

cat $1 >> cleanDataFile.txt

sed 's/./,/g' cleanDataFile.txt > cleanDataFile-excel-BR.txt


echo 

echo "First file to parser..."
python parser_results.py

echo

echo "Second file to parser..."
#python parser_delays.py

echo

#echo "Creating parsed files to excel/BR ..."

#sed 's/./,/g' resultsMEDIAN.txt > resultsMEDIAN-excel-BR.txt
#sed 's/./,/g' resultsMEAN.txt > resultsMEAN-excel-BR.txt
#sed 's/./,/g' resultsDESCRIBE.txt > resultsDESCRIBE-excel-BR.txt
#sed 's/./,/g' delays_MEDIAN.txt > delays_MEDIAN-excel-BR.txt
#sed 's/./,/g' delays_MEAN.txt > delays_MEAN-excel-BR.txt
#sed 's/./,/g' delays_DESCRIBE.txt > delays_DESCRIBE-excel-BR.txt

echo 
echo "End of shell script!"
echo


