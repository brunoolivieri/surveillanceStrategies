# -*- coding: utf-8 -*-

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
matplotlib.style.use('ggplot')

import mysettings

#markers=['s-','o-','^-']
markers=['s-', 'o--', '^-.', 'h:', '*:', 'x-']

#DF que pegsm todos os mapas e colocam em uma linha
dataframe_collection_medians = {} 
dataframe_collection_avgs = {} 



dataframe_raw_collection_medians_singleUAVset = {} # single size of set of UAV
dataframe_raw_collection_avgs_singleUAVset = {}     # single size of set of UAV

dataframe_CH_collection_medians_singleUAVset = {} # single size of set of UAV
dataframe_CH_collection_avgs_singleUAVset = {}     # single size of set of UAV


def import_files():
    print('[Loader][import_files()] STEP - 1: loading data')  # getting data into the pandas              
    # reading data | ugly unstack method with a file... but !?!?? || indexing by the triad
    df_file = pd.read_csv(mysettings.FILEPARSED,sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )
    
    print('[Loader][import_files()] STEP - 2: genarate files with MEDIANS and AVERAGES by UAV and POIs')  # median and mean from all evals that matchs (all maps to one line).   
    df_file.groupby(level=["Strategy","nUAV","nPOIs"]).median().to_csv(mysettings.FILEMEDIANS, sep=';', encoding='utf-8')
    df_file.groupby(level=["Strategy","nUAV","nPOIs"]).mean().to_csv(mysettings.FILEAVGS, sep=';', encoding='utf-8')
   
    print('[Loader][import_files()] STEP - 3: map distinct map sizes')  # listings
    df_file_raw = pd.read_csv(mysettings.FILEPARSED,sep=';', index_col =["Strategy"] ) #from original file
    mysettings.DISTSIZES=df_file_raw.nPOIs.unique()
    print('[Loader][import_files()] Map sizes:' , mysettings.DISTSIZES)

    print('[Loader][import_files()] STEP - 4: load summarized dataframes by UAV and POIs')  # listings    
    for i in range(0,len(mysettings.DISTSIZES)):
        df_temp = pd.read_csv(mysettings.FILEMEDIANS,sep=';', encoding='utf-8' )
        dataframe_collection_medians[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]]
        
        df_temp = pd.read_csv(mysettings.FILEAVGS,sep=';', encoding='utf-8' )
        dataframe_collection_avgs[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]]
    
    
    print('[Loader][import_files()] STEP - 5: load RAW dataframes')    
    df_file_raw = pd.read_csv(mysettings.FILEPARSED,sep=';', index_col =["Strategy", "mapName", "nPOIs"] ) # from original file 
    df_file_raw_single_uav = df_file_raw.loc[df_file_raw['nUAV'] == mysettings.SINGLEUAVCOUNT]
    
    print('[Loader][import_files()] STEP - 6: writing MEDIANS and AVERAGES by maps')    
    df_file_raw_single_uav.groupby(level=["Strategy","mapName","nPOIs"]).mean().to_csv(mysettings.FILEMAPAVGS, sep=';', encoding='utf-8')
    df_file_raw_single_uav.groupby(level=["Strategy","mapName","nPOIs"]).median().to_csv(mysettings.FILEMAPMEDIANS, sep=';', encoding='utf-8')
   
    print('[Loader][import_files()] STEP - 7: load summarized dataframes by MAPs and POIs')  # listings    
    for i in range(0,len(mysettings.DISTSIZES)):
        df_temp = pd.read_csv(mysettings.FILEMAPMEDIANS,sep=';', encoding='utf-8' )        
        dataframe_raw_collection_medians_singleUAVset[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]]
        
        df_temp = pd.read_csv(mysettings.FILEMAPAVGS,sep=';', encoding='utf-8' )
        dataframe_raw_collection_avgs_singleUAVset[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]]

    ##################################################################################################################################

    print('[Loader][import_files()] STEP - ')  
    
    df_file_raw = pd.read_csv(mysettings.FILEPARSED,sep=';', index_col =["Strategy","nPOIs"] ) #from original file
    df_file_raw_single_uav = df_file_raw.loc[df_file_raw['nUAV'] == mysettings.SINGLEUAVCOUNT]
    
    print('[Loader][import_files()] STEP - ')    
    df_file_raw_single_uav.groupby(level=["Strategy","nPOIs"]).mean().to_csv(mysettings.FILECHAVGS, sep=';', encoding='utf-8')
    df_file_raw_single_uav.groupby(level=["Strategy","nPOIs"]).median().to_csv(mysettings.FILECHMEDIANS, sep=';', encoding='utf-8')
    
    dataframe_CH_collection_medians_singleUAVset[0] = pd.read_csv(mysettings.FILECHMEDIANS,sep=';', encoding='utf-8' )
    dataframe_CH_collection_avgs_singleUAVset[0] = pd.read_csv(mysettings.FILECHAVGS,sep=';', encoding='utf-8' )
   
#    print('[Loader][import_files()] STEP - ')  # listings    
#    for i in range(0,len(mysettings.DISTSIZES)):
#        df_temp = pd.read_csv(mysettings.FILEMAPMEDIANS,sep=';', encoding='utf-8' )        
#        dataframe_CH_collection_medians_singleUAVset[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]]
#        
#        df_temp = pd.read_csv(mysettings.FILEMAPAVGS,sep=';', encoding='utf-8' )
#        dataframe_CH_collection_avgs_singleUAVset[i] = df_temp.loc[df_temp['nPOIs'] == mysettings.DISTSIZES[i]]




    print('[Loader][import_files()] STEP - 9: DONE')    


def line_plots(baseName,tiposStats,dataFrames,dataFramesNames,xStatsCods,yStatsCods,xLabels,yLabels): 
    print('[Loader][line_plots()] Func called ...')
    plt.clf()
    
    for i in range(0,len(tiposStats)):  
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
            print(tiposStats[i] + ' - ' + dataFramesNames[j] + '\n\n')
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
            ##plt.title(tiposStats[i] + ' - ' + dataFramesNames[j])
            plt.title(tiposStats[i] + ' - ' + dataFramesNames[j] + ' CHs')

        #plt.title(tiposStats[i])
            ##plt.savefig('_0' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=mysettings.DPI2SAVE)
            plt.savefig(baseName + tiposStats[i] + ' - ' + dataFramesNames[j] + '_CHs.png', dpi=mysettings.DPI2SAVE)
        #plt.savefig('_' + tiposStats[i] + '.png', dpi=DPI2SAVE)
    


def boxplots_plots(baseName,tiposStats,dataFrames,dataFramesNames,yStatsCods,yLabel): 
          
    def boxplot_sorted(df, by, column):
        df2 = pd.DataFrame({col:vals[column] for col, vals in df.groupby(by)})
        meds = df2.median().sort_values()
        df2[meds.index].boxplot(rot=45,sym='')
    for i in range(0,len(tiposStats)):  
        plt.clf()
        plt.rcParams['grid.color'] = 'k'
        plt.rcParams['grid.linestyle'] = ':'
        plt.rcParams['grid.linewidth'] = 0.4
        for j in range(0,len(dataFrames)):        
            #dataFrames[j].boxplot(yStatsCods[i],['Strategy'],sym='')
            boxplot_sorted(dataFrames[j], ['Strategy'], yStatsCods[i])
    
            plt.suptitle("")
            plt.title(tiposStats[i] + ' - ' + dataFramesNames[j])
            #plt.xticks(rotation='vertical')
            plt.tight_layout()
            #plt.set(ylabel=yStatsCods[i])
            ##plt.savefig('_2' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=DPI2SAVE)
            plt.savefig(baseName + tiposStats[i] + ' - ' + dataFramesNames[j] + '_CHs.png', dpi=mysettings.DPI2SAVE)
    
            plt.show()
            plt.clf()



def run_old_ploter():
    print('[Ploter - old one] Importing files ...')
    import_files()
    
    # No idea the reason of the following 4 lines... I missed something    
    distributions=np.array2string(mysettings.DISTSIZES)
    preNome1 = distributions.replace("[","")
    preNome2 = preNome1.replace("]","")    
    setSizes = [str(s) for s in preNome2.split() if s.isdigit()]
    
    
    # "_0_" in the old version ########################################################################################
    # Summarized stats by index_col =["Strategy", "nUAV", "nPOIs"] 
    # Lines by UAV
    tiposStats=['Amount of Data','Throughput','Mean Msgs Delays','Worst Data Collection','Amount by toursize']
    yStatsCods=['SucessTax','throughput','globalAvgDelay','maxData','TaxPerPathSize']
    xStatsCods=['nUAV','nUAV','nUAV','nUAV','nUAV']
    xLabel="Number of UAVs"
    yLabel=['Amount of Data','Throughput','Mean Msgs Delays','Worst Data Collection','Amount by toursize']
    
    line_plots('0_AVG_nUAVs_',tiposStats,dataframe_collection_avgs,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    line_plots('0_MEDIANS_nUAVs_',tiposStats,dataframe_collection_medians,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    ## done ###########################################################################################################
    
    
    # "_1_" in the old version ########################################################################################
    # Summarized stats by index_col =["Strategy", "mapName", "nPOIs"]
    # Lines for a single set of UAV (usually 8)
    tiposStats=['Tour size by map','Mean msg delay by map','Processing Tour time by map']
    yStatsCods=['tourSize','globalAvgDelay','pathTime']
    xStatsCods=['mapName','mapName','mapName']
    xLabel="Maps"
    yLabel=['Tour size by map','Mean msg delay by map','Processing Tour time by map']
    
    line_plots('1_RAW_AVG_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,dataframe_raw_collection_avgs_singleUAVset,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    line_plots('1_RAW_MEDIANS_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,dataframe_raw_collection_medians_singleUAVset,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    ## done ###########################################################################################################
    
    
    # "_2_" in the old version ########################################################################################
    # Boxplots by index_col =["Strategy", "mapName", "nPOIs"]
    # For a single set of UAV (usually 8)   
    tiposStats=['Amount of Data','Throughput','Mean msg delay (100x)','Tour size by map (100x)','Processing Tour time (100x)']
    yStatsCods=['SucessTax','throughput','globalAvgDelay','tourSize','pathTime']

    yLabel=['SucessTax','throughput','globalAvgDelay','tourSize','pathTime']
    
    boxplots_plots('2_boxplot_RAW_AVG_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,dataframe_raw_collection_avgs_singleUAVset,setSizes,yStatsCods,yLabel)
    boxplots_plots('2_boxplot_RAW_MEDIANS_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,dataframe_raw_collection_medians_singleUAVset,setSizes,yStatsCods,yLabel)
    ## done ###########################################################################################################
    
    
    # "_3_" in the old version ########################################################################################
    # Boxplots by index_col =["Strategy", "mapName", "nPOIs"] but for zoom in
    # For a single set of UAV (usually 8)   
    tiposStats=['Mean msg delay']
    yStatsCods=['globalAvgDelay']
    yLabel=['globalAvgDelay']
    wantedStrats_forZoom=['TSP-based','DADCA-LKH-Cutted']  # for Mean msg delay


    df_collection_temp = {}
    for i in range(0,len(dataframe_raw_collection_medians_singleUAVset)):
        df_temp = dataframe_raw_collection_medians_singleUAVset[i]
        df2plot = df_temp.loc[df_temp['Strategy'].isin(wantedStrats_forZoom)]
        df_collection_temp[i]=df2plot
        
    boxplots_plots('3_boxplot_RAW_AVG_zoom_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,df_collection_temp,setSizes,yStatsCods,yLabel)

    
    tiposStats=['Amount of Data','Throughput']
    yStatsCods=['SucessTax','throughput']
    yLabel=['SucessTax','throughput']
    wantedStrats_forZoom=['TSP-based','DADCA-LKH-Cutted','DADCA-LKH']  # for the others!?!?!


    df_collection_temp = {}
    for i in range(0,len(dataframe_raw_collection_medians_singleUAVset)):
        df_temp = dataframe_raw_collection_medians_singleUAVset[i]
        df2plot = df_temp.loc[df_temp['Strategy'].isin(wantedStrats_forZoom)]
        df_collection_temp[i]=df2plot

    boxplots_plots('3_boxplot_RAW_MEDIANS_zoom_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,df_collection_temp,setSizes,yStatsCods,yLabel)
    
    ###################################################################################
    # aqui embaixo é por mapa e zoom in
    
    tiposStats=['Tour size']
    yStatsCods=['tourSize']
    xStatsCods=['mapName']
    wantedStrats_forZoom=['FPPWR','DADCA-NSN','DADCA-Parted-NSN']
    df_collection_temp = {}
    
    for i in range(0,len(dataframe_raw_collection_medians_singleUAVset)):
        df_temp = dataframe_raw_collection_medians_singleUAVset[i]
        df2plot = df_temp.loc[df_temp['Strategy'].isin(wantedStrats_forZoom)]
        df_collection_temp[i]=df2plot

    boxplots_plots('3_boxplot_RAW_MEDIANS_zoom_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,df_collection_temp,setSizes,yStatsCods,yLabel)
    
    
    
    tiposStats=['pathTime']
    yStatsCods=['pathTime']
    xStatsCods=['mapName']
    wantedStrats_forZoom=['TSP-Based','DADCA-LKH-Cutted','DADCA-LKH']
    df_collection_temp = {}
    for i in range(0,len(dataframe_raw_collection_medians_singleUAVset)):
        df_temp = dataframe_raw_collection_medians_singleUAVset[i]
        df2plot = df_temp.loc[df_temp['Strategy'].isin(wantedStrats_forZoom)]
        df_collection_temp[i]=df2plot

    boxplots_plots('3_boxplot_RAW_MEDIANS_zoom_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,df_collection_temp,setSizes,yStatsCods,yLabel)
  
    ## done ###########################################################################################################
      
    
    # Novidade abaixo...
    
    # Not in the old version ########################################################################################
    # Summarized stats by index_col =["Strategy", "nUAV", "nPOIs"] 
    # Lines by UAV
    tiposStats=['Processing time','Delay','TourSize','Throughput','Amount by toursize']  
    yStatsCods=['pathTime','globalAvgDelay','tourSize','throughput','TaxPerPathSize']
    xStatsCods=['nPOIs','nPOIs','nPOIs','nPOIs','nPOIs']
    xLabel="CHs"
    yLabel=['Processing time','Delay','TourSize','throughput','TaxPerPathSize']
       
    line_plots('9_RAW_AVG_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,dataframe_CH_collection_avgs_singleUAVset,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    line_plots('9_RAW_MEDIANS_' + str(mysettings.SINGLEUAVCOUNT) + 'UAVs_' ,tiposStats,dataframe_CH_collection_medians_singleUAVset,setSizes,xStatsCods,yStatsCods,xLabel,yLabel)
    ## done ###########################################################################################################
    





