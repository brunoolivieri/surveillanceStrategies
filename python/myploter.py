# -*- coding: utf-8 -*-
"""
Created on Tue Aug 28 18:05:36 2018

@author: c061300
"""



import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
matplotlib.style.use('ggplot')

import shutil

import mysettings

#markers=['s-','o-','^-']
markers=['s-', 'o--', '^-.', 'h:', '*:', 'x-', 'x-', 'x-', 'x-']
setSizes=0



#DF que pegsm todos os mapas e colocam em uma linha
df_coll_MapSummd_medians = {} 
df_coll_MapSummd_avgs = {} 

#dataframe_raw_collection_medians_singleUAVset = {} # single size of set of UAV
df_coll_1UAV_median = {}     # single size of set of UAV
df_coll_1UAV_raw = {}     # single size of set of UAV



def line_plots(baseName,titleGraphs,dataFrames,dataFramesNames,xStatsCods,yStatsCods,xLabels,yLabels): 
    print('[Loader][line_plots()] Func called ...')
    plt.clf()
    
    for i in range(0,len(titleGraphs)):  
        #plt.figure(i+1)                # the first figure
        #plt.grid(False)
        #plt.rc('grid', linestyle="--", color='gray')
        #plt.grid(color='k', linewidth=.5, linestyle=':')
        #plt.rcParams['axes.facecolor']=0.75
        
        plt.rcParams['grid.color'] = 'k'
        plt.rcParams['grid.linestyle'] = ':'
        plt.rcParams['grid.linewidth'] = 0.4
    
        for j in range(0,len(dataFrames)):
            #plt.subplot(2,2,j+1)             
            #subplot(nrows, ncols, plot_number)
            print(titleGraphs[i] + ' - ' + dataFramesNames[j] + '\n\n')
            fig, ax = plt.subplots()
            #fig.autolayout : True
            #plt.tight_layout()
            plt.gcf().subplots_adjust(left=0.15)
            labels = []
            k=0
            for key, grp in dataFrames[j].groupby('Strategy'):
                ax = grp.plot(ax=ax, kind='line', x=xStatsCods[i], y=yStatsCods[i], style=markers[k])
#                if (dataFramesNames[j] is 'Sparse'):
#                    ax.set_xlim(3, 17)
#                else:
#                    ax.set_xlim(2, 34)
                labels.append(key)
                k+=1
            lines, _ = ax.get_legend_handles_labels()
            ax.set(xlabel=xLabels, ylabel=yLabels[i])
            ax.legend(lines, labels, loc='best')
            ##plt.title(titleGraphs[i] + ' - ' + dataFramesNames[j])
            plt.title(titleGraphs[i] + ' - ' + dataFramesNames[j] + ' CHs')

        #plt.title(titleGraphs[i])
            ##plt.savefig('_0' + str(i) + str(j) + '_' + titleGraphs[i] + ' - ' + dataFramesNames[j] + '.png', dpi=mysettings.DPI2SAVE)
            plt.savefig(baseName + titleGraphs[i] + ' - ' + dataFramesNames[j] + '_CHs.png', dpi=mysettings.DPI2SAVE)
        #plt.savefig('_' + titleGraphs[i] + '.png', dpi=DPI2SAVE)
    


def boxplots_plots(baseName,titleGraphs,dataFrames,dataFramesNames,yStatsCods,yLabel): 
          
    def boxplot_sorted(df, by, column,i):
        df2 = pd.DataFrame({col:vals[column] for col, vals in df.groupby(by)})
        meds = df2.median().sort_values()
        df2[meds.index].boxplot(rot=45,sym='').set_ylabel(yLabel[i])
        
    for i in range(0,len(titleGraphs)):  
        
        
        fig, axes = plt.subplots(nrows=1, ncols=1)

        
        plt.clf()
        plt.rcParams['grid.color'] = 'k'
        plt.rcParams['grid.linestyle'] = ':'
        plt.rcParams['grid.linewidth'] = 0.4
        for j in range(0,len(dataFrames)):        
            #dataFrames[j].boxplot(yStatsCods[i],['Strategy'],sym='')
            boxplot_sorted(dataFrames[j], ['Strategy'], yStatsCods[i],i)
    
            #plt.suptitle("")
            plt.title(titleGraphs[i] + ' - ' + dataFramesNames[j] + ' CHs')

            #plt.xticks(rotation='vertical')
            
            plt.tight_layout()
            #plt.tick_params(axis='both')#,labelsize=14)

            plt.savefig(baseName + titleGraphs[i] + ' - ' + dataFramesNames[j] + '_CHs.png', dpi=mysettings.DPI2SAVE)
    
            plt.show()
            plt.clf()



def import_files():
    
    
    print('[Loader][import_files()] STEP - 0: map distinct map sizes')  # listings
    # real raw file
    df_file_raw = pd.read_csv(mysettings.FILEPARSED,sep=';', index_col =["Strategy"] ) #from original file
    mysettings.DISTSIZES=df_file_raw.nPOIs.unique()
    print('[Loader][import_files()] Map sizes:' , mysettings.DISTSIZES)

    ## merging maps dataframes ####################################################################################################    
    print('[Loader][import_files()] STEP - 1: loading data')  # getting data into the pandas              
    # reading data | ugly unstack method with a file... but !?!?? || indexing by the triad
    df_in = pd.read_csv(mysettings.FILEPARSED,sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )
    
    # cutting unnecessary colls
    df_file = df_in.drop(['V2V_range','ctRounds','dimX','TSP_threads','maxData','minData','nMsgs','throughput','TaxPerPathSize','GSglobalAvgDelay'], axis=1)
    #miliseconds to minutes    
    df_file = df_file.assign(simumationTimeMS = df_file.simumationTimeMS.mul(1/60000))
    df_file = df_file.assign(pathTime = df_file.pathTime.mul(1/60000))

         
    print('[Loader][import_files()] STEP - 2: genarate files with MEDIANS and AVERAGES by UAV and POIs')  
    # median and mean from all evals that matchs (all maps to one line).   
    df_file.groupby(level=["Strategy","nUAV","nPOIs"]).median().to_csv(mysettings.FILEMEDIANS, sep=';', encoding='utf-8')
    df_file.groupby(level=["Strategy","nUAV","nPOIs"]).mean().to_csv(mysettings.FILEAVGS, sep=';', encoding='utf-8')
    df_file.groupby(level=["Strategy","nUAV","nPOIs"]).describe().to_csv(mysettings.FILESTATS, sep=';', encoding='utf-8')

    
   
    print('[Loader][import_files()] STEP - 4: load summarized dataframes by UAV and POIs')  # listings sumarizing all MAPs for a single line.    
    # putting in different dataframes by number of CH/POIs
    for i in range(0,len(mysettings.DISTSIZES)):
        df_temp = pd.read_csv(mysettings.FILEMEDIANS,sep=';', encoding='utf-8' )
        df_coll_MapSummd_medians[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]]
        
        df_temp = pd.read_csv(mysettings.FILEAVGS,sep=';', encoding='utf-8' )
        df_coll_MapSummd_avgs[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]]
    ## merging maps dataframes ####################################################################################################    

    ## stats with a single set of UAVs ############################################################################################    
    # timing, memory
    print('[Loader][import_files()] STEP - 5: load RAW dataframes')    
    df_file_raw = pd.read_csv(mysettings.FILEPARSED,sep=';', index_col =["Strategy", "mapName", "nPOIs"] ) # from original file 
    df_in = df_file_raw.loc[df_file_raw['nUAV'] == mysettings.SINGLEUAVCOUNT] #filtering
    
    # cutting unnecessary colls
    df_file_raw_single_uav = df_in.drop(['V2V_range','ctRounds','dimX','TSP_threads','maxData','minData','nMsgs','throughput','TaxPerPathSize','GSglobalAvgDelay'], axis=1)
    #miliseconds to minutes    
    df_file_raw_single_uav = df_file_raw_single_uav.assign(simumationTimeMS = df_file_raw_single_uav.simumationTimeMS.mul(1/60000))
    df_file_raw_single_uav = df_file_raw_single_uav.assign(pathTime = df_file_raw_single_uav.pathTime.mul(1/60000))
    
    df_file_raw_single_uav = df_file_raw_single_uav.assign(tourSize = df_file_raw_single_uav.tourSize.mul(1/1000))

    
    print('[Loader][import_files()] STEP - 6: writing filtered DF')    
    print('[Loader][import_files()] STEP - 6: 1/3 raw')    
    df_file_raw_single_uav.reset_index().to_csv(mysettings.FILERAW, sep=';', encoding='utf-8')
    print('[Loader][import_files()] STEP - 6: 2/3 median')    
    # Just saving the filter | does not matter if it is mean() or avg()
    df_file_raw_single_uav.groupby(level=["Strategy","mapName","nPOIs"]).median().to_csv(mysettings.FILEMAPMEDIANS, sep=';', encoding='utf-8')
    #df_file_raw_single_uav.groupby(level=["Strategy","mapName","nPOIs"]).median().to_csv(mysettings.FILEMAPMEDIANS, sep=';', encoding='utf-8')
    print('[Loader][import_files()] STEP - 6: 3/3 describe')    
    #df_file_raw_single_uav.groupby(level=["Strategy","mapName","nPOIs"]).describe().to_csv(mysettings.FILEMAPSTATS, sep=';', encoding='utf-8')
    
     
    print('[Loader][import_files()] STEP - 7: loading the DF filtered')  # listings   
    # saiving raw test for a single uav all maps all strats by size of CH... a DF for each 
    for i in range(0,len(mysettings.DISTSIZES)):
        # just to read and unpack the data
        df_temp = pd.read_csv(mysettings.FILEMAPMEDIANS,sep=';', encoding='utf-8' )
        df_coll_1UAV_median[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]]      
        
        df_temp = pd.read_csv(mysettings.FILERAW,sep=';', encoding='utf-8' ,index_col=0)
        df_coll_1UAV_raw[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]] 
        
       
        
        
    
def select_df(dict,strats): 
    df_collection_temp = {}
    for i in range(0,len(dict)):
        df_temp = dict[i]
        df2plot = df_temp.loc[df_temp['Strategy'].isin(strats)]
        df_collection_temp[i]=df2plot
    return df_collection_temp

def run_thesis_ploter():
    print('[Ploter - thesis one] Importing files ...')
    import_files()
    # No idea the reason of the following 4 lines... I missed something while converting the array  
    distributions=np.array2string(mysettings.DISTSIZES)
    preNome1 = distributions.replace("[","")
    preNome2 = preNome1.replace("]","") 
    preNome3 = preNome2.replace(".","") 
    preNome4 = preNome3.replace("nan","") 
    setSizes = [str(s) for s in preNome4.split() if s.isdigit()]
    
    
    # "_toursizes" TOURSIZE analisys. ########################################################################################
    # Summarized stats by index_col =["Strategy", "mapName", "nPOIs"] -->> MAPAS, sempre com um numero fixo de UAVs
    # Lines for a single set of UAV (usually 8)
    titleGraphs=['Tour size by map']
    yStatsCods=['tourSize']
    xStatsCods=['mapName']
    xLabel="Maps id"
    yLabel=['Tour size (km)']
    
    #line_plots('toursizes_AVG_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_coll_1UAV_median,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    line_plots('toursizes_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_coll_1UAV_median,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    boxplots_plots('toursizes_boxplot_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_coll_1UAV_raw,setSizes,yStatsCods,yLabel)
    
    
    wantedStrats_forZoom=['TSP-based','DADCA-LKH','DADCA-LKH-Cutted']
    df_select = select_df(df_coll_1UAV_median,wantedStrats_forZoom)
    line_plots('toursizes_zoom_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_select,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    df_select = select_df(df_coll_1UAV_raw,wantedStrats_forZoom)
    boxplots_plots('toursizes_zoom_boxplot_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_select,setSizes,yStatsCods,yLabel)
    ## done ###########################################################################################################
    
    
    # "_pathTime" timing analisys. ########################################################################################
    # Summarized stats by index_col =["Strategy", "mapName", "nPOIs"] -->> MAPAS, sempre com um numero fixo de UAVs
    # Lines for a single set of UAV (usually 8)
    titleGraphs=['Tour planning time']
    yStatsCods=['pathTime']
    xStatsCods=['mapName']
    xLabel="Maps id"
    yLabel=['Time (minutes)']
    
    #line_plots('toursizes_AVG_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_coll_1UAV_median,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    line_plots('pathTime_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_coll_1UAV_median,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    boxplots_plots('pathTime_boxplot_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_coll_1UAV_raw,setSizes,yStatsCods,yLabel)
    
    
    wantedStrats_forZoom=['TSP-based','DADCA-LKH','DADCA-LKH-Cutted']
    df_select = select_df(df_coll_1UAV_median,wantedStrats_forZoom)
    line_plots('pathTime_zoom_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_select,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    
    boxplots_plots('pathTime_zoom_boxplot_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,titleGraphs,df_select,setSizes,yStatsCods,yLabel)
    ## done ###########################################################################################################
    
  
    
    # "_amount"  analisys. ########################################################################################
    # Summarized stats by index_col =["Strategy", "nUAVs", "nPOIs"] -->> UAVs UAVs na linha e mapas no boxplot
    titleGraphs=['Amount of Data']
    yStatsCods=['SucessTax']
    xStatsCods=['nUAV']
    xLabel="Number of UAVs"
    yLabel=['Amount of Data']
    
    
    line_plots('amount_' ,titleGraphs,df_coll_MapSummd_avgs,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    boxplots_plots('amount_boxplot_' ,titleGraphs,df_coll_1UAV_median,setSizes,yStatsCods,yLabel)
    
    wantedStrats_forZoom=['TSP-based','DADCA-LKH','DADCA-LKH-Cutted',]
    df_select = select_df(df_coll_MapSummd_avgs,wantedStrats_forZoom)
    line_plots('amount_zoom_'  ,titleGraphs,df_select,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    boxplots_plots('amount_boxplot_zoom_'  ,titleGraphs,df_select,setSizes,yStatsCods,yLabel)
    ## done ###########################################################################################################
    

    # "_delay"  analisys. ########################################################################################
    # Summarized stats by index_col =["Strategy", "nUAVs", "nPOIs"] -->> UAVs na linha e mapas no boxplot
    titleGraphs=['Mean msg delay']
    yStatsCods=['globalAvgDelay']
    xStatsCods=['nUAV']
    xLabel="Number of UAVs"
    yLabel=['Mean msg delay']
    
    
    line_plots('delay_' ,titleGraphs,df_coll_MapSummd_avgs,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    boxplots_plots('delay_boxplot_' ,titleGraphs,df_coll_1UAV_median,setSizes,yStatsCods,yLabel)
    
    wantedStrats_forZoom=['TSP-based','DADCA-LKH','DADCA-LKH-Cutted',]
    df_select = select_df(df_coll_MapSummd_avgs,wantedStrats_forZoom)
    line_plots('delay_zoom_'  ,titleGraphs,df_select,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    boxplots_plots('delay_boxplot_zoom_'  ,titleGraphs,df_select,setSizes,yStatsCods,yLabel)
    ## done ###########################################################################################################
    












    ##########################################################################################################################

    
    


def dataset_analisys():

    distributions=np.array2string(mysettings.DISTSIZES)
    preNome1 = distributions.replace("[","")
    preNome2 = preNome1.replace("]","")    
    setSizes = [str(s) for s in preNome2.split() if s.isdigit()]
    
    
    
    wantedStrats=['TSP-based','DADCA-LKH','DADCA-LKH-Cutted']
    df_select = select_df(df_coll_1UAV_median,wantedStrats)
    
    nMaps=1000
    
    for i in range(0,len(setSizes)):
        #df_thin = df_select[i].drop(['nUAV', 'V2V_range','ctRounds','dimX','TSP_threads','maxData','minData','nMsgs','throughput','TaxPerPathSize'], axis=1)
        
        df_thin = df_select[i]
        
        tspcount=0    
        lkhcount=0
        lkhcuttedcount=0
        tspavg=0
        lkhavg=0
        lkhcutavg=0

        for j in range(1,nMaps+1): 
            
            mapadavez = df_thin.loc[df_thin['mapName'] == j]
    
            try:
                tsp = mapadavez.loc[df_thin['Strategy'] == 'TSP-based']
                lkh = mapadavez.loc[df_thin['Strategy'] == 'DADCA-LKH']
                lkhcutted = mapadavez.loc[df_thin['Strategy'] == 'DADCA-LKH-Cutted']
          
                criterio = 'tourSize' # 'SucessTax' # 
               
                lkhRate = lkh.iloc[0][criterio]
                tspRate = tsp.iloc[0][criterio]
                lkhcuttedRate = lkhcutted.iloc[0][criterio]
                
                tspavg= (tspavg + tspRate) /2
                lkhavg= (lkhavg+ lkhRate) /2
                lkhcutavg= (lkhcutavg + lkhcuttedRate) /2
                
                if criterio=='SucessTax':
                    if lkhRate <= tspRate and lkhcuttedRate <= tspRate :
                        tspcount+=1                     
                    else:
                        if lkhRate >= lkhcuttedRate:
                            lkhcount+=1 
                        else: 
                            lkhcuttedcount+=1
                else:
                    if lkhRate >= tspRate and lkhcuttedRate >= tspRate :
                        tspcount+=1                     
                    else:
                        if lkhRate <= lkhcuttedRate:
                            lkhcount+=1 
                        else: 
                            lkhcuttedcount+=1
                            
            except:
                pass
    
        
        print('setsize ' + setSizes[i] + ' team TSP ' + str(tspcount) + ' avg: ' + str(tspavg))
        print('setsize ' + setSizes[i] + ' team LKH ' + str(lkhcount) + ' avg: ' + str(lkhavg))
        print('setsize ' + setSizes[i] + ' team LKH-cutted ' + str(lkhcuttedcount) + ' avg: ' + str(lkhcutavg))
        print('some ' + str(tspcount+lkhcount+lkhcuttedcount) + ' de ' + str(nMaps))
    
    
    
    
    