#!/bin/bash

# data header: "Strategy;nPOIs;nUAV;nRounds;SucessTax;V2V_range;ctRounds;dimX";

#sed 's/;/,/g' stats_summary.txt > tmp.txt
sed 's/%//g' tmp.txt > tmp2.txt
sed 's/projects.UAV_Surveillance.models.mobilityModels.//g' tmp2.txt > stat_em_tratamento.txt

Strategy=">> error <<"
tax10=1
tax20=2
tax40=4
tax60=6

Strategy_antTSP="AntiTSPbasedMobility"
AntTSP_min_tax_10=9999
AntTSP_min_tax_20=9999
AntTSP_min_tax_40=9999
AntTSP_min_tax_60=9999
AntTSP_max_tax_10=0
AntTSP_max_tax_20=0
AntTSP_max_tax_40=0
AntTSP_max_tax_60=0
AntTSP_taxsum_10=-1
AntTSP_avg_tax_10=-1
AntTSP_errorTax_10=-1

Strategy_TSP="TSPbasedMobility"
TSP_min_tax_10=9999
TSP_min_tax_20=9999
TSP_min_tax_40=9999
TSP_min_tax_60=9999
TSP_max_tax_10=0
TSP_max_tax_20=0
TSP_max_tax_40=0
TSP_max_tax_60=0
TSP_taxsum_10=-1
TSP_avg_tax_10=-1
TSP_errorTax_10=-1

Strategy_Naive="NaiveOrderedMobility"
Naive_min_tax_10=9999
Naive_min_tax_20=9999
Naive_min_tax_40=9999
Naive_min_tax_60=9999
Naive_max_tax_10=0
Naive_max_tax_20=0
Naive_max_tax_40=0
Naive_max_tax_60=0
Naive_taxsum_10=-1
Naive_avg_tax_10=-1
Naive_errorTax_10=-1

Strategy_Random="RandomSafeMobility"
Random_min_tax_10=9999
Random_min_tax_20=9999
Random_min_tax_40=9999
Random_min_tax_60=9999
Random_max_tax_10=0
Random_max_tax_20=0
Random_max_tax_40=0
Random_max_tax_60=0
Random_taxsum_10=-1
Random_avg_tax_10=-1
Random_errorTax_10=-1

while IFS=, read -ra arr; do
    
	Strategy=${arr[0]}
	nUAV=${arr[2]}
	SucessTax=${arr[3]}

	case "$Strategy" in

		$Strategy_antTSP)  	#echo "Strategy antiTsp"

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy antiTsp ...  " $SucessTax
							if [ $(echo "$SucessTax < $AntTSP_min_tax_10" | bc -l ) -eq 1 ]; then
           						     AntTSP_min_tax_10=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $AntTSP_max_tax_10" | bc -l ) -eq 1 ]; then
           						     AntTSP_max_tax_10=$SucessTax         
            						fi
						    ;;
						$tax20)  #echo " 10% UAV na Strategy antiTsp ...  " $SucessTax
							if [ $(echo "$SucessTax < $AntTSP_min_tax_20" | bc -l ) -eq 1 ]; then
           						     AntTSP_min_tax_20=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $AntTSP_max_tax_20" | bc -l ) -eq 1 ]; then
           						     AntTSP_max_tax_20=$SucessTax         
            						fi
						    ;;
						$tax40)  #echo " 10% UAV na Strategy antiTsp ...  " $SucessTax
							if [ $(echo "$SucessTax < $AntTSP_min_tax_40" | bc -l ) -eq 1 ]; then
           						     AntTSP_min_tax_40=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $AntTSP_max_tax_40" | bc -l ) -eq 1 ]; then
           						     AntTSP_max_tax_40=$SucessTax         
            						fi
						    ;;
						$tax60)  #echo " 10% UAV na Strategy antiTsp ...  " $SucessTax
							if [ $(echo "$SucessTax < $AntTSP_min_tax_60" | bc -l ) -eq 1 ]; then
           						     AntTSP_min_tax_60=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $AntTSP_max_tax_60" | bc -l ) -eq 1 ]; then
           						     AntTSP_max_tax_60=$SucessTax         
            						fi
						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;

		$Strategy_TSP)  	#echo "Strategy Tsp"

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy  ...  " $SucessTax
							if [ $(echo "$SucessTax < $TSP_min_tax_10" | bc -l ) -eq 1 ]; then
           						     TSP_min_tax_10=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $TSP_max_tax_10" | bc -l ) -eq 1 ]; then
           						     TSP_max_tax_10=$SucessTax         
            						fi
						    ;;
						$tax20)  #echo " 10% UAV na Strategy antiTsp ...  " $SucessTax
							if [ $(echo "$SucessTax < $TSP_min_tax_20" | bc -l ) -eq 1 ]; then
           						     TSP_min_tax_20=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $TSP_max_tax_20" | bc -l ) -eq 1 ]; then
           						     TSP_max_tax_20=$SucessTax         
            						fi
						    ;;
						$tax40)  #echo " 10% UAV na Strategy antiTsp ...  " $SucessTax
							if [ $(echo "$SucessTax < $TSP_min_tax_40" | bc -l ) -eq 1 ]; then
           						     TSP_min_tax_40=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $TSP_max_tax_40" | bc -l ) -eq 1 ]; then
           						     TSP_max_tax_40=$SucessTax         
            						fi
						    ;;
						$tax60)  #echo " 10% UAV na Strategy antiTsp ...  " $SucessTax
							if [ $(echo "$SucessTax < $TSP_min_tax_60" | bc -l ) -eq 1 ]; then
           						     TSP_min_tax_60=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $TSP_max_tax_60" | bc -l ) -eq 1 ]; then
           						     TSP_max_tax_60=$SucessTax         
            						fi
						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;

			$Strategy_Naive)  	#echo "Strategy Naive"

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy  ...  " $SucessTax
							if [ $(echo "$SucessTax < $Naive_min_tax_10" | bc -l ) -eq 1 ]; then
           						     Naive_min_tax_10=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $Naive_max_tax_10" | bc -l ) -eq 1 ]; then
           						     Naive_max_tax_10=$SucessTax         
            						fi
						    ;;
						$tax20)  #echo " 10% UAV na Strategy Naive ...  " $SucessTax
							if [ $(echo "$SucessTax < $Naive_min_tax_20" | bc -l ) -eq 1 ]; then
           						     Naive_min_tax_20=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $Naive_max_tax_20" | bc -l ) -eq 1 ]; then
           						     Naive_max_tax_20=$SucessTax         
            						fi
						    ;;
						$tax40)  #echo " 10% UAV na Strategy Naive ...  " $SucessTax
							if [ $(echo "$SucessTax < $Naive_min_tax_40" | bc -l ) -eq 1 ]; then
           						     Naive_min_tax_40=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $Naive_max_tax_40" | bc -l ) -eq 1 ]; then
           						     Naive_max_tax_40=$SucessTax         
            						fi
						    ;;
						$tax60)  #echo " 10% UAV na Strategy Naive ...  " $SucessTax
							if [ $(echo "$SucessTax < $Naive_min_tax_60" | bc -l ) -eq 1 ]; then
           						     Naive_min_tax_60=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $Naive_max_tax_60" | bc -l ) -eq 1 ]; then
           						     Naive_max_tax_60=$SucessTax         
            						fi
						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;

			$Strategy_Random)  	#echo "Strategy Random"

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy  ...  " $SucessTax
							if [ $(echo "$SucessTax < $Random_min_tax_10" | bc -l ) -eq 1 ]; then
           						     Random_min_tax_10=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $Random_max_tax_10" | bc -l ) -eq 1 ]; then
           						     Random_max_tax_10=$SucessTax         
            						fi
						    ;;
						$tax20)  #echo " 10% UAV na Strategy Random ...  " $SucessTax
							if [ $(echo "$SucessTax < $Random_min_tax_20" | bc -l ) -eq 1 ]; then
           						     Random_min_tax_20=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $Random_max_tax_20" | bc -l ) -eq 1 ]; then
           						     Random_max_tax_20=$SucessTax         
            						fi
						    ;;
						$tax40)  #echo " 10% UAV na Strategy Random ...  " $SucessTax
							if [ $(echo "$SucessTax < $Random_min_tax_40" | bc -l ) -eq 1 ]; then
           						     Random_min_tax_40=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $Random_max_tax_40" | bc -l ) -eq 1 ]; then
           						     Random_max_tax_40=$SucessTax         
            						fi
						    ;;
						$tax60)  #echo " 10% UAV na Strategy Random ...  " $SucessTax
							if [ $(echo "$SucessTax < $Random_min_tax_60" | bc -l ) -eq 1 ]; then
           						     Random_min_tax_60=$SucessTax         
            						fi
							if [ $(echo "$SucessTax > $Random_max_tax_60" | bc -l ) -eq 1 ]; then
           						     Random_max_tax_60=$SucessTax         
            						fi
						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;


		2)  #echo  "Sending SIGINT signal"
		    #kill -SIGINT $2
		    ;;

		*) #echo "Strategy not recognized = " $Strategy
		   ;;
	esac













	#echo $Strategy
	#echo nPOIs = ${arr[1]}
	#echo nUAV = ${arr[2]}
	#echo nRounds = ${arr[3]}   	
	#echo V2V_range = ${arr[5]}
	#echo ctRounds = ${arr[6]}
	#echo dimX = ${arr[7]}

done < stat_em_tratamento.txt


AntTSP_taxsum_10=$(echo "$AntTSP_min_tax_10 + $AntTSP_max_tax_10" | bc -l )
AntTSP_avg_tax_10=$(echo "$AntTSP_taxsum_10 / 2" | bc -l )
AntTSP_errorTax_10=$(echo "$AntTSP_max_tax_10 - $AntTSP_avg_tax_10" | bc -l )
echo $Strategy_antTSP";"$tax10";"$AntTSP_avg_tax_10";"$AntTSP_errorTax_10

AntTSP_taxsum_20=$(echo "$AntTSP_min_tax_20 + $AntTSP_max_tax_20" | bc -l )
AntTSP_avg_tax_20=$(echo "$AntTSP_taxsum_20 / 2" | bc -l )
AntTSP_errorTax_20=$(echo "$AntTSP_max_tax_20 - $AntTSP_avg_tax_20" | bc -l )
echo $Strategy_antTSP";"$tax20";"$AntTSP_avg_tax_20";"$AntTSP_errorTax_20

AntTSP_taxsum_40=$(echo "$AntTSP_min_tax_40 + $AntTSP_max_tax_40" | bc -l )
AntTSP_avg_tax_40=$(echo "$AntTSP_taxsum_40 / 2" | bc -l )
AntTSP_errorTax_40=$(echo "$AntTSP_max_tax_40 - $AntTSP_avg_tax_40" | bc -l )
echo $Strategy_antTSP";"$tax40";"$AntTSP_avg_tax_40";"$AntTSP_errorTax_40

AntTSP_taxsum_60=$(echo "$AntTSP_min_tax_60 + $AntTSP_max_tax_60" | bc -l )
AntTSP_avg_tax_60=$(echo "$AntTSP_taxsum_60 / 2" | bc -l )
AntTSP_errorTax_60=$(echo "$AntTSP_max_tax_60 - $AntTSP_avg_tax_60" | bc -l )
echo $Strategy_antTSP";"$tax60";"$AntTSP_avg_tax_60";"$AntTSP_errorTax_60

#------------------------------------------------------------------------------------------

TSP_taxsum_10=$(echo "$TSP_min_tax_10 + $TSP_max_tax_10" | bc -l )
TSP_avg_tax_10=$(echo "$TSP_taxsum_10 / 2" | bc -l )
TSP_errorTax_10=$(echo "$TSP_max_tax_10 - $TSP_avg_tax_10" | bc -l )
echo $Strategy_TSP";"$tax10";"$TSP_avg_tax_10";"$TSP_errorTax_10

TSP_taxsum_20=$(echo "$TSP_min_tax_20 + $TSP_max_tax_20" | bc -l )
TSP_avg_tax_20=$(echo "$TSP_taxsum_20 / 2" | bc -l )
TSP_errorTax_20=$(echo "$TSP_max_tax_20 - $TSP_avg_tax_20" | bc -l )
echo $Strategy_TSP";"$tax20";"$TSP_avg_tax_20";"$TSP_errorTax_20

TSP_taxsum_40=$(echo "$TSP_min_tax_40 + $TSP_max_tax_40" | bc -l )
TSP_avg_tax_40=$(echo "$TSP_taxsum_40 / 2" | bc -l )
TSP_errorTax_40=$(echo "$TSP_max_tax_40 - $TSP_avg_tax_40" | bc -l )
echo $Strategy_TSP";"$tax40";"$TSP_avg_tax_40";"$TSP_errorTax_40

TSP_taxsum_60=$(echo "$TSP_min_tax_60 + $TSP_max_tax_60" | bc -l )
TSP_avg_tax_60=$(echo "$TSP_taxsum_60 / 2" | bc -l )
TSP_errorTax_60=$(echo "$TSP_max_tax_60 - $TSP_avg_tax_60" | bc -l )
echo $Strategy_TSP";"$tax60";"$TSP_avg_tax_60";"$TSP_errorTax_60

#------------------------------------------------------------------------------------------

Naive_taxsum_10=$(echo "$Naive_min_tax_10 + $Naive_max_tax_10" | bc -l )
Naive_avg_tax_10=$(echo "$Naive_taxsum_10 / 2" | bc -l )
Naive_errorTax_10=$(echo "$Naive_max_tax_10 - $Naive_avg_tax_10" | bc -l )
echo $Strategy_Naive";"$tax10";"$Naive_avg_tax_10";"$Naive_errorTax_10

Naive_taxsum_20=$(echo "$Naive_min_tax_20 + $Naive_max_tax_20" | bc -l )
Naive_avg_tax_20=$(echo "$Naive_taxsum_20 / 2" | bc -l )
Naive_errorTax_20=$(echo "$Naive_max_tax_20 - $Naive_avg_tax_20" | bc -l )
echo $Strategy_Naive";"$tax20";"$Naive_avg_tax_20";"$Naive_errorTax_20

Naive_taxsum_40=$(echo "$Naive_min_tax_40 + $Naive_max_tax_40" | bc -l )
Naive_avg_tax_40=$(echo "$Naive_taxsum_40 / 2" | bc -l )
Naive_errorTax_40=$(echo "$Naive_max_tax_40 - $Naive_avg_tax_40" | bc -l )
echo $Strategy_Naive";"$tax40";"$Naive_avg_tax_40";"$Naive_errorTax_40

Naive_taxsum_60=$(echo "$Naive_min_tax_60 + $Naive_max_tax_60" | bc -l )
Naive_avg_tax_60=$(echo "$Naive_taxsum_60 / 2" | bc -l )
Naive_errorTax_60=$(echo "$Naive_max_tax_60 - $Naive_avg_tax_60" | bc -l )
echo $Strategy_Naive";"$tax60";"$Naive_avg_tax_60";"$Naive_errorTax_60

#------------------------------------------------------------------------------------------

Random_taxsum_10=$(echo "$Random_min_tax_10 + $Random_max_tax_10" | bc -l )
Random_avg_tax_10=$(echo "$Random_taxsum_10 / 2" | bc -l )
Random_errorTax_10=$(echo "$Random_max_tax_10 - $Random_avg_tax_10" | bc -l )
echo $Strategy_Random";"$tax10";"$Random_avg_tax_10";"$Random_errorTax_10

Random_taxsum_20=$(echo "$Random_min_tax_20 + $Random_max_tax_20" | bc -l )
Random_avg_tax_20=$(echo "$Random_taxsum_20 / 2" | bc -l )
Random_errorTax_20=$(echo "$Random_max_tax_20 - $Random_avg_tax_20" | bc -l )
echo $Strategy_Random";"$tax20";"$Random_avg_tax_20";"$Random_errorTax_20

Random_taxsum_40=$(echo "$Random_min_tax_40 + $Random_max_tax_40" | bc -l )
Random_avg_tax_40=$(echo "$Random_taxsum_40 / 2" | bc -l )
Random_errorTax_40=$(echo "$Random_max_tax_40 - $Random_avg_tax_40" | bc -l )
echo $Strategy_Random";"$tax40";"$Random_avg_tax_40";"$Random_errorTax_40

Random_taxsum_60=$(echo "$Random_min_tax_60 + $Random_max_tax_60" | bc -l )
Random_avg_tax_60=$(echo "$Random_taxsum_60 / 2" | bc -l )
Random_errorTax_60=$(echo "$Random_max_tax_60 - $Random_avg_tax_60" | bc -l )
echo $Strategy_Random";"$tax60";"$Random_avg_tax_60";"$Random_errorTax_60

#------------------------------------------------------------------------------------------










