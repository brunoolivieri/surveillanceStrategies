#!/bin/bash

# THIS IS A TEMPORARY AND NAIVE TEST!!!

# data header: "Strategy;nPOIs;nUAV;nRounds;SucessTax;V2V_range;ctRounds;dimX";

sed 's/;/,/g' stats_summary.txt > tmp.txt      # necessary to parser lines inside WHILE
sed 's/%//g' tmp.txt > tmp2.txt
sed 's/projects.UAV_Surveillance.models.mobilityModels.//g' tmp2.txt > stat_em_tratamento.txt

Strategy=">> error <<"
tax10=2
tax20=4
tax40=8
tax60=16

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
Naive_avg_tax_40=0
Naive_avg_tax_60=0
Naive_counter=0

Strategy_NSNaive="NotSoNaiveOrderedMobility"
NSNaive_avg_tax_10=0
NSNaive_avg_tax_20=0
NSNaive_avg_tax_40=0
NSNaive_avg_tax_60=0
NSNaive_counter=0

Strategy_Random="RandomSafeMobility"
Random_avg_tax_10=0
Random_avg_tax_20=0
Random_avg_tax_40=0
Random_avg_tax_60=0
Random_counter=0

Strategy_ZigZagOverNSN="ZigZagOverNSNMobility"
ZigZagOverNSN_avg_tax_10=0
ZigZagOverNSN_avg_tax_20=0
ZigZagOverNSN_avg_tax_40=0
ZigZagOverNSN_avg_tax_60=0
ZigZagOverNSN_counter=0

Strategy_ZigZagOverNaive="ZigZagOverNaiveMobility"
ZigZagOverNaive_avg_tax_10=0
ZigZagOverNaive_avg_tax_20=0
ZigZagOverNaive_avg_tax_40=0
ZigZagOverNaive_avg_tax_60=0
ZigZagOverNaive_counter=0

Strategy_KingOverNaive="KingstonImprovedOverNaiveMobility"
KingOverNaive_avg_tax_10=0
KingOverNaive_avg_tax_20=0
KingOverNaive_avg_tax_40=0
KingOverNaive_avg_tax_60=0
KingOverNaive_counter=0

Strategy_KingOverNSN="KingstonImprovedOverNSNMobility"
KingOverNSN_avg_tax_10=0
KingOverNSN_avg_tax_20=0
KingOverNSN_avg_tax_40=0
KingOverNSN_avg_tax_60=0
KingOverNSN_counter=0









while IFS=, read -ra arr; do
    
	Strategy=${arr[0]}
	nUAV=${arr[2]}
	SucessTax=${arr[3]}

	case "$Strategy" in

		$Strategy_antTSP)  	#echo "Strategy antiTsp"

					AntTSP_counter=$(echo "$AntTSP_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy   ...  " $SucessTax
							
							AntTSP_avg_tax_10=$(echo "$AntTSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							AntTSP_avg_tax_20=$(echo "$AntTSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							AntTSP_avg_tax_40=$(echo "$AntTSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							AntTSP_avg_tax_60=$(echo "$AntTSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac



		    ;;

		$Strategy_TSP)  	#echo "Strategy Tsp"

					TSP_counter=$(echo "$TSP_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy   ...  " $SucessTax
							
							TSP_avg_tax_10=$(echo "$TSP_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							TSP_avg_tax_20=$(echo "$TSP_avg_tax_20 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							TSP_avg_tax_40=$(echo "$TSP_avg_tax_40 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							TSP_avg_tax_60=$(echo "$TSP_avg_tax_60 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;

			$Strategy_Naive)  	#echo "Strategy Naive"

					Naive_counter=$(echo "$Naive_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy   ...  " $SucessTax
							
							Naive_avg_tax_10=$(echo "$Naive_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							Naive_avg_tax_20=$(echo "$Naive_avg_tax_20 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							Naive_avg_tax_40=$(echo "$Naive_avg_tax_40 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							Naive_avg_tax_60=$(echo "$Naive_avg_tax_60 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;
	
				$Strategy_NSNaive)  	#echo "Strategy notSoNaive"

					NSNaive_counter=$(echo "$NSNaive_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy   ...  " $SucessTax
							
							NSNaive_avg_tax_10=$(echo "$NSNaive_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							NSNaive_avg_tax_20=$(echo "$NSNaive_avg_tax_20 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							NSNaive_avg_tax_40=$(echo "$NSNaive_avg_tax_40 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							NSNaive_avg_tax_60=$(echo "$NSNaive_avg_tax_60 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;	    

			$Strategy_Random)  	#echo "Strategy Random"

					Random_counter=$(echo "$Random_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy   ...  " $SucessTax
							
							Random_avg_tax_10=$(echo "$Random_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							Random_avg_tax_20=$(echo "$Random_avg_tax_20 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							Random_avg_tax_40=$(echo "$Random_avg_tax_40 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy  ...  " $SucessTax 

							Random_avg_tax_60=$(echo "$Random_avg_tax_60 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;
		    
			$Strategy_ZigZagOverNSN)  	#echo "Strategy ZigZagOverNSN"
									
					ZigZagOverNSN_counter=$(echo "$ZigZagOverNSN_counter + 1" | bc -l )

					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy  ZigZagOverNSN ...  " $SucessTax
							
							ZigZagOverNSN_avg_tax_10=$(echo "$ZigZagOverNSN_avg_tax_10 + $SucessTax" | bc -l )

						    ;;
						$tax20)  #echo " 10% UAV na Strategy ZigZagOverNSN ...  " $SucessTax 

							ZigZagOverNSN_avg_tax_20=$(echo "$ZigZagOverNSN_avg_tax_20 + $SucessTax" | bc -l )

						    ;;
						$tax40)  #echo " 10% UAV na Strategy ZigZagOverNSN ...  " $SucessTax 

							ZigZagOverNSN_avg_tax_40=$(echo "$ZigZagOverNSN_avg_tax_40 + $SucessTax" | bc -l )

						    ;;
						$tax60)  #echo " 10% UAV na Strategy ZigZagOverNSN ...  " $SucessTax 

							ZigZagOverNSN_avg_tax_60=$(echo "$ZigZagOverNSN_avg_tax_60 + $SucessTax" | bc -l )

						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;
		    
			$Strategy_ZigZagOverNaive)  	#echo "Strategy Strategy_ZigZagOverNaive"
									
					ZigZagOverNaive_counter=$(echo "$ZigZagOverNaive_counter + 1" | bc -l )
	
					case "$nUAV" in
						$tax10)  #echo " 10% UAV na Strategy   ...  " $SucessTax
							
							ZigZagOverNaive_avg_tax_10=$(echo "$ZigZagOverNaive_avg_tax_10 + $SucessTax" | bc -l )
	
						    ;;
						$tax20)  #echo " 10% UAV na Strategy  ...  " $SucessTax 
	
							ZigZagOverNaive_avg_tax_20=$(echo "$ZigZagOverNaive_avg_tax_20 + $SucessTax" | bc -l )
	
						    ;;
						$tax40)  #echo " 10% UAV na Strategy  ...  " $SucessTax 
	
							ZigZagOverNaive_avg_tax_40=$(echo "$ZigZagOverNaive_avg_tax_40 + $SucessTax" | bc -l )
	
						    ;;
						$tax60)  #echo " 10% UAV na Strategy  ...  " $SucessTax 
	
							ZigZagOverNaive_avg_tax_60=$(echo "$ZigZagOverNaive_avg_tax_60 + $SucessTax" | bc -l )
	
						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;
		    
		    
			$Strategy_KingOverNaive)  	
									
					KingOverNaive_counter=$(echo "$KingOverNaive_counter + 1" | bc -l )
	
					case "$nUAV" in
						$tax10) 
							
							KingOverNaive_avg_tax_10=$(echo "$KingOverNaive_avg_tax_10 + $SucessTax" | bc -l )
	
						    ;;
						$tax20)  
	
							KingOverNaive_avg_tax_20=$(echo "$KingOverNaive_avg_tax_20 + $SucessTax" | bc -l )
	
						    ;;
						$tax40) 
	
							KingOverNaive_avg_tax_40=$(echo "$KingOverNaive_avg_tax_40 + $SucessTax" | bc -l )
	
						    ;;
						$tax60)  
	
							KingOverNaive_avg_tax_60=$(echo "$KingOverNaive_avg_tax_60 + $SucessTax" | bc -l )
	
						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Stragety " $Strategy
						   ;;
					esac


		    ;;

			$Strategy_KingOverNSN)  	
									
					KingOverNSN_counter=$(echo "$KingOverNSN_counter + 1" | bc -l )
	
					case "$nUAV" in
						$tax10) 
							
							KingOverNSN_avg_tax_10=$(echo "$KingOverNSN_avg_tax_10 + $SucessTax" | bc -l )
	
						    ;;
						$tax20)  
	
							KingOverNSN_avg_tax_20=$(echo "$KingOverNSN_avg_tax_20 + $SucessTax" | bc -l )
	
						    ;;
						$tax40) 
	
							KingOverNSN_avg_tax_40=$(echo "$KingOverNSN_avg_tax_40 + $SucessTax" | bc -l )
	
						    ;;
						$tax60)  
	
							KingOverNSN_avg_tax_60=$(echo "$KingOverNSN_avg_tax_60 + $SucessTax" | bc -l )
	
						    ;;
						*) #echo "[error] nUAV " $nUAV " not recognized on Strategy " $Strategy
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



ZigZagOverNaive_counter=$(echo "$ZigZagOverNaive_counter / 4" | bc -l )
ZigZagOverNaive_avg_tax_10=$(echo "$ZigZagOverNaive_avg_tax_10 / $ZigZagOverNaive_counter" | bc -l )
ZigZagOverNaive_avg_tax_20=$(echo "$ZigZagOverNaive_avg_tax_20 / $ZigZagOverNaive_counter" | bc -l )
ZigZagOverNaive_avg_tax_40=$(echo "$ZigZagOverNaive_avg_tax_40 / $ZigZagOverNaive_counter" | bc -l )
ZigZagOverNaive_avg_tax_60=$(echo "$ZigZagOverNaive_avg_tax_60 / $ZigZagOverNaive_counter" | bc -l )

ZigZagOverNSN_counter=$(echo "$ZigZagOverNSN_counter / 4" | bc -l )
ZigZagOverNSN_avg_tax_10=$(echo "$ZigZagOverNSN_avg_tax_10 / $ZigZagOverNSN_counter" | bc -l )
ZigZagOverNSN_avg_tax_20=$(echo "$ZigZagOverNSN_avg_tax_20 / $ZigZagOverNSN_counter" | bc -l )
ZigZagOverNSN_avg_tax_40=$(echo "$ZigZagOverNSN_avg_tax_40 / $ZigZagOverNSN_counter" | bc -l )
ZigZagOverNSN_avg_tax_60=$(echo "$ZigZagOverNSN_avg_tax_60 / $ZigZagOverNSN_counter" | bc -l )

Random_counter=$(echo "$Random_counter / 4" | bc -l )
Random_avg_tax_10=$(echo "$Random_avg_tax_10 / $Random_counter" | bc -l )
Random_avg_tax_20=$(echo "$Random_avg_tax_20 / $Random_counter" | bc -l )
Random_avg_tax_40=$(echo "$Random_avg_tax_40 / $Random_counter" | bc -l )
Random_avg_tax_60=$(echo "$Random_avg_tax_60 / $Random_counter" | bc -l )

Naive_counter=$(echo "$Naive_counter / 4" | bc -l )
Naive_avg_tax_10=$(echo "$Naive_avg_tax_10 / $Naive_counter" | bc -l )
Naive_avg_tax_20=$(echo "$Naive_avg_tax_20 / $Naive_counter" | bc -l )
Naive_avg_tax_40=$(echo "$Naive_avg_tax_40 / $Naive_counter" | bc -l )
Naive_avg_tax_60=$(echo "$Naive_avg_tax_60 / $Naive_counter" | bc -l )

NSNaive_counter=$(echo "$NSNaive_counter / 4" | bc -l )
NSNaive_avg_tax_10=$(echo "$NSNaive_avg_tax_10 / $NSNaive_counter" | bc -l )
NSNaive_avg_tax_20=$(echo "$NSNaive_avg_tax_20 / $NSNaive_counter" | bc -l )
NSNaive_avg_tax_40=$(echo "$NSNaive_avg_tax_40 / $NSNaive_counter" | bc -l )
NSNaive_avg_tax_60=$(echo "$NSNaive_avg_tax_60 / $NSNaive_counter" | bc -l )

TSP_counter=$(echo "$TSP_counter / 4" | bc -l )
TSP_avg_tax_10=$(echo "$TSP_avg_tax_10 / $TSP_counter" | bc -l )
TSP_avg_tax_20=$(echo "$TSP_avg_tax_20 / $TSP_counter" | bc -l )
TSP_avg_tax_40=$(echo "$TSP_avg_tax_40 / $TSP_counter" | bc -l )
TSP_avg_tax_60=$(echo "$TSP_avg_tax_60 / $TSP_counter" | bc -l )

AntTSP_counter=$(echo "$AntTSP_counter / 4" | bc -l )
AntTSP_avg_tax_10=$(echo "$AntTSP_avg_tax_10 / $AntTSP_counter" | bc -l )
AntTSP_avg_tax_20=$(echo "$AntTSP_avg_tax_20 / $AntTSP_counter" | bc -l )
AntTSP_avg_tax_40=$(echo "$AntTSP_avg_tax_40 / $AntTSP_counter" | bc -l )
AntTSP_avg_tax_60=$(echo "$AntTSP_avg_tax_60 / $AntTSP_counter" | bc -l )     

KingOverNaive_counter=$(echo "$KingOverNaive_counter / 4" | bc -l )
KingOverNaive_avg_tax_10=$(echo "$KingOverNaive_avg_tax_10 / $KingOverNaive_counter" | bc -l )
KingOverNaive_avg_tax_20=$(echo "$KingOverNaive_avg_tax_20 / $KingOverNaive_counter" | bc -l )
KingOverNaive_avg_tax_40=$(echo "$KingOverNaive_avg_tax_40 / $KingOverNaive_counter" | bc -l )
KingOverNaive_avg_tax_60=$(echo "$KingOverNaive_avg_tax_60 / $KingOverNaive_counter" | bc -l )    

KingOverNSN_counter=$(echo "$KingOverNSN_counter / 4" | bc -l )
KingOverNSN_avg_tax_10=$(echo "$KingOverNSN_avg_tax_10 / $KingOverNSN_counter" | bc -l )
KingOverNSN_avg_tax_20=$(echo "$KingOverNSN_avg_tax_20 / $KingOverNSN_counter" | bc -l )
KingOverNSN_avg_tax_40=$(echo "$KingOverNSN_avg_tax_40 / $KingOverNSN_counter" | bc -l )
KingOverNSN_avg_tax_60=$(echo "$KingOverNSN_avg_tax_60 / $KingOverNSN_counter" | bc -l )    



echo $Strategy_ZigZagOverNSN"; counter = "$ZigZagOverNSN_counter
echo $Strategy_ZigZagOverNSN";"$tax10";"$ZigZagOverNSN_avg_tax_10
echo $Strategy_ZigZagOverNSN";"$tax20";"$ZigZagOverNSN_avg_tax_20
echo $Strategy_ZigZagOverNSN";"$tax40";"$ZigZagOverNSN_avg_tax_40
echo $Strategy_ZigZagOverNSN";"$tax60";"$ZigZagOverNSN_avg_tax_60

echo $Strategy_Random"; counter = "$Random_counter
echo $Strategy_Random";"$tax10";"$Random_avg_tax_10
echo $Strategy_Random";"$tax20";"$Random_avg_tax_20
echo $Strategy_Random";"$tax40";"$Random_avg_tax_40
echo $Strategy_Random";"$tax60";"$Random_avg_tax_60

echo $Strategy_Naive"; counter = "$Naive_counter
echo $Strategy_Naive";"$tax10";"$Naive_avg_tax_10
echo $Strategy_Naive";"$tax20";"$Naive_avg_tax_20
echo $Strategy_Naive";"$tax40";"$Naive_avg_tax_40
echo $Strategy_Naive";"$tax60";"$Naive_avg_tax_60

echo $Strategy_TSP"; counter = "$TSP_counter
echo $Strategy_TSP";"$tax10";"$TSP_avg_tax_10
echo $Strategy_TSP";"$tax20";"$TSP_avg_tax_20
echo $Strategy_TSP";"$tax40";"$TSP_avg_tax_40
echo $Strategy_TSP";"$tax60";"$TSP_avg_tax_60

echo $Strategy_antTSP"; counter = "$AntTSP_counter
echo $Strategy_antTSP";"$tax10";"$AntTSP_avg_tax_10
echo $Strategy_antTSP";"$tax20";"$AntTSP_avg_tax_20
echo $Strategy_antTSP";"$tax40";"$AntTSP_avg_tax_40
echo $Strategy_antTSP";"$tax60";"$AntTSP_avg_tax_60

echo $Strategy_NSNaive"; counter = "$NSNaive_counter
echo $Strategy_NSNaive";"$tax10";"$NSNaive_avg_tax_10
echo $Strategy_NSNaive";"$tax20";"$NSNaive_avg_tax_20
echo $Strategy_NSNaive";"$tax40";"$NSNaive_avg_tax_40
echo $Strategy_NSNaive";"$tax60";"$NSNaive_avg_tax_60

echo $Strategy_ZigZagOverNaive"; counter = "$ZigZagOverNaive_counter
echo $Strategy_ZigZagOverNaive";"$tax10";"$ZigZagOverNaive_avg_tax_10
echo $Strategy_ZigZagOverNaive";"$tax20";"$ZigZagOverNaive_avg_tax_20
echo $Strategy_ZigZagOverNaive";"$tax40";"$ZigZagOverNaive_avg_tax_40
echo $Strategy_ZigZagOverNaive";"$tax60";"$ZigZagOverNaive_avg_tax_60

echo $Strategy_KingOverNaive"; counter = "$KingOverNaive_counter
echo $Strategy_KingOverNaive";"$tax10";"$KingOverNaive_avg_tax_10
echo $Strategy_KingOverNaive";"$tax20";"$KingOverNaive_avg_tax_20
echo $Strategy_KingOverNaive";"$tax40";"$KingOverNaive_avg_tax_40
echo $Strategy_KingOverNaive";"$tax60";"$KingOverNaive_avg_tax_60

echo $Strategy_KingOverNSN"; counter = "$KingOverNSN_counter
echo $Strategy_KingOverNSN";"$tax10";"$KingOverNSN_avg_tax_10
echo $Strategy_KingOverNSN";"$tax20";"$KingOverNSN_avg_tax_20
echo $Strategy_KingOverNSN";"$tax40";"$KingOverNSN_avg_tax_40
echo $Strategy_KingOverNSN";"$tax60";"$KingOverNSN_avg_tax_60



















