#!/bin/bash

# THIS IS A TEMPORARY AND NAIVE TEST!!!

# data header: "Strategy;nPOIs;nUAV;nRounds;SucessTax;V2V_range;ctRounds;dimX";

sed 's/;/,/g' stats_summary.txt > tmp.txt      # necessary to parser lines inside WHILE

sed 's/%//g' tmp.txt > tmp2.txt
sed 's/projects.UAV_Surveillance.models.mobilityModels.//g' tmp2.txt > stat_em_tratamento.txt

Strategy=">> error <<"
tax10=1
tax20=2
tax40=4
tax60=6

Strategy_antTSP="AntiTSPbasedMobility"
AntTSP_avg_tax_10=0
AntTSP_avg_tax_20=0
AntTSP_avg_tax_40=0
AntTSP_avg_tax_60=0
AntTSP_counter=0

Strategy_TSP="TSPbasedMobility"
TSP_avg_tax_10=0
TSP_avg_tax_20=0
TSP_avg_tax_40=0
TSP_avg_tax_60=0
TSP_counter=0

Strategy_Naive="NaiveOrderedMobility"
Naive_avg_tax_10=0
Naive_avg_tax_20=0
Naive_avg_tax_30=0
Naive_avg_tax_40=0
Naive_counter=0

Strategy_Random="RandomSafeMobility"
Random_avg_tax_10=0
Random_avg_tax_20=0
Random_avg_tax_40=0
Random_avg_tax_40=0
Random_counter=0

Strategy_KingstonImproved="KingstonImprovedMobility"
KingstonImproved_avg_tax_10=0
KingstonImproved_avg_tax_20=0
KingstonImproved_avg_tax_40=0
KingstonImproved_avg_tax_60=0
KingstonImproved_counter=0

while IFS=, read -ra arr; do
    
	Strategy=${arr[0]}
	nUAV=${arr[2]}
	SucessTax=${arr[3]}

	case "$Strategy" in

		$Strategy_antTSP)  	#echo "Strategy antiTsp"

					AntTSP_counter=$(echo "$AntTSP_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy  KingstonImproved ...  " $SucessTax
							
							AntTSP_avg_tax_10=$(echo "$AntTSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							AntTSP_avg_tax_20=$(echo "$AntTSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							AntTSP_avg_tax_40=$(echo "$AntTSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							AntTSP_avg_tax_60=$(echo "$AntTSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac



		    ;;

		$Strategy_TSP)  	#echo "Strategy Tsp"

					TSP_counter=$(echo "$TSP_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy  KingstonImproved ...  " $SucessTax
							
							TSP_avg_tax_10=$(echo "$TSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							TSP_avg_tax_20=$(echo "$TSP_avg_tax_20 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							TSP_avg_tax_40=$(echo "$TSP_avg_tax_40 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							TSP_avg_tax_60=$(echo "$TSP_avg_tax_60 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;

			$Strategy_Naive)  	#echo "Strategy Naive"

					Naive_counter=$(echo "$Naive_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy  KingstonImproved ...  " $SucessTax
							
							Naive_avg_tax_10=$(echo "$Naive_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							Naive_avg_tax_20=$(echo "$Naive_avg_tax_20 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							Naive_avg_tax_40=$(echo "$Naive_avg_tax_40 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							Naive_avg_tax_60=$(echo "$Naive_avg_tax_60 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;

			$Strategy_Random)  	#echo "Strategy Random"

					Random_counter=$(echo "$Random_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy  KingstonImproved ...  " $SucessTax
							
							Random_avg_tax_10=$(echo "$Random_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							Random_avg_tax_20=$(echo "$Random_avg_tax_20 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							Random_avg_tax_40=$(echo "$Random_avg_tax_40 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							Random_avg_tax_60=$(echo "$Random_avg_tax_60 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;
		    
			$Strategy_KingstonImproved)  	#echo "Strategy KingstonImproved"
									
					KingstonImproved_counter=$(echo "$KingstonImproved_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy  KingstonImproved ...  " $SucessTax
							
							KingstonImproved_avg_tax_10=$(echo "$KingstonImproved_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							KingstonImproved_avg_tax_20=$(echo "$KingstonImproved_avg_tax_20 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							KingstonImproved_avg_tax_40=$(echo "$KingstonImproved_avg_tax_40 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy KingstonImproved ...  " $SucessTax 

							KingstonImproved_avg_tax_60=$(echo "$KingstonImproved_avg_tax_60 + $SucessTax" | bc -l )

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



KingstonImproved_avg_tax_10=$(echo "$KingstonImproved_avg_tax_10 / $KingstonImproved_counter" | bc -l )
KingstonImproved_avg_tax_20=$(echo "$KingstonImproved_avg_tax_20 / $KingstonImproved_counter" | bc -l )
KingstonImproved_avg_tax_40=$(echo "$KingstonImproved_avg_tax_40 / $KingstonImproved_counter" | bc -l )
KingstonImproved_avg_tax_60=$(echo "$KingstonImproved_avg_tax_60 / $KingstonImproved_counter" | bc -l )

echo $KingstonImproved_avg_tax_10 
echo $KingstonImproved_avg_tax_20 
echo $KingstonImproved_avg_tax_40 
echo $KingstonImproved_avg_tax_60
echo $KingstonImproved_counter



#echo $Strategy_KingstonImproved";"$tax60";"$KingstonImproved_avg_tax_60";"$KingstonImproved_errorTax_60



