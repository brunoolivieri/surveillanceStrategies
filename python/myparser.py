# -*- coding: utf-8 -*-
"""
Created on Tue Aug 21 15:05:22 2018

@author: c061300
"""
import os
import shutil
import mysettings

FILETMP="dadosDeEntrada_TMP_PARSING.txt"


def inplace_change(filename, old_string, new_string):
    with open(filename, "rt") as fin:
        with open(FILETMP, "wt") as fout:
            for line in fin:
                fout.write(line.replace(old_string, new_string))
    os.remove(filename)
    os.rename(FILETMP,filename)

def add_header(filename):  
    with open(filename, "rt") as fin:
        with open(FILETMP, "wt") as fout:
            #fout.write('Strategy;nPOIs;nUAV;SucessTax;V2V_range;ctRounds;dimX;simumationTimeMS;pathTime;TSP_threads;maxData;minData;globalAvgDelay;GSglobalAvgDelay;nMsgs;tourSize;throughput;TaxPerPathSize;memory;mapName\n')
            fout.write('Strategy;nPOIs;nUAV;SucessTax;V2V_range;ctRounds;dimX;pathTime;globalAvgDelay;nMsgs;tourSize;memory;validRVZ;totRVZ;msgsRVZ;msgsRVZbyUAVs;msgsRVZbyUAVsXEnergy;mapName\n')
            for line in fin:
                fout.write(line)
    os.remove(filename)
    os.rename(FILETMP,filename)
        
def clean_lines(filename):  
    inplace_change(filename, ',', '.')      
    inplace_change(filename, '%', '')       
    inplace_change(filename, 'MiliSegs', '')      
    inplace_change(filename, '_TSP_thread', '')      
    inplace_change(filename, 'map_', '')       
    inplace_change(filename, '.txt', '') 
    inplace_change(filename, 'DADCA-parted-nsn', 'DADCA-Parted-NSN')     
    inplace_change(filename, 'DADCA-lkh-cutted', 'DADCA-LKH-Cut')     
    inplace_change(filename, 'TSPConcorde', 'TSP-based')     
    inplace_change(filename, 'DADCA-lkh', 'DADCA-LKH')    
        

def run_parser(): 

    try:
        os.remove(mysettings.FILEPARSED)
    except OSError:
        pass
    
    print('[Parser] Creating the output file...')
    shutil.copyfile(mysettings.FILEIN, mysettings.FILEPARSED)

    print('[Parser] Calling cleaner...')
    clean_lines(mysettings.FILEPARSED)
    print('[Parser] Adding header...')
    add_header(mysettings.FILEPARSED)

    