

# my first python...
# just a result parser

import csv
import copy
from collections import defaultdict

#column positions:
#Strategy=0,nPOIs=1,nUAV=2,SucessTax=3,V2V_range=4,nRounds=5,dimX=6,
#simumationTimeMS=7,TSP_threads=8,maxData=9,minData=10

# get the file and column names
file = open('stat_em_tratamento.csv', 'rb') 
reader = csv.reader(file)
headers = reader.next()

# get columns and creates arrays for each one
column = {}
for h in headers:
    column[h] = []

# get all data into column matrix 
for row in reader:  # for each line
    for h, v in zip(headers, row):  # for each value
        column[h].append(v)

# get all UAVs set sizes
nUAVs = {}
for n in column["nUAV"]:  # for each line
    if n not in nUAVs:
        print n
        nUAVs[n] = []
        
print nUAVs

# get all strategies and insert a array of UAV set sizes
stats = {}
for n in column["Strategy"]:  # for each line
    if n not in stats:
        print n
        stats[n] = list(nUAVs)

print stats


'''
with open('stat_em_tratamento.csv', 'r') as csv_file:
    reader = csv.DictReader(csv_file)

    Strategies = defaultdict(int)
    myAvgs = defaultdict(list)
    
    for row in reader:
        Strategies[row["Strategy"]] += 1
        new = row["maxData"]
        old = myAvgs.get("TSPbasedMobility","maxData")
        myAvgs[row["Strategies"],"maxData"] = int(new) + int(old) 


#print Strategies["TSPbasedMobility"]
#print Strategies["ZigZagOverNSNMobility"]
#print Strategies["NotSoNaiveOrderedMobility"]
#print Strategies.keys()
print myAvgs
'''
