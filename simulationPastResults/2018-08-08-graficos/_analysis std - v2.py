# 
#
# basic stats -> sparse/dense
# _0xx: median/means lines regarding 'Amount of Data','Throughput','Mean Msgs Delays','Worst Data Collection','Amount by toursize' 
# _1xx: raw lines regarding 'Tour size by map','Mean msg delay by map','Processing Tour time by map'
# _2xx: boxplots regarding 'Amount of Data','Throughput','Mean msg delay (100x)','Tour size by map (100x)','Processing Tour time (100x)' 
# _3xx: zoom in/specific boxplot regarding 'Mean msg delay','Amount of Data','Throughput'
#
#

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
matplotlib.style.use('ggplot')


fileIn="_stats_summary.txt"
SPARSE=20
DENSE=100
DPI2SAVE=100

#to use with Jupyter. Remove when using bash
#%matplotlib inline


#plot sample
#df_file.loc[df_file['nPOIs'] == 20].boxplot('globalAvgDelay',['nPOIs','Strategy'],sym='') #tá fazendo groupBy pelos colchetes.

print('PASSO - 1')  # getting data into the pandas
            
# reading data | ugly unstack method with a file... but !?!??
df_file = pd.read_csv(fileIn,sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )

print('PASSO - 2')  # median from all evals that matchs (all maps to one line). saving into z_tmp_.txt

df_file.groupby(level=["Strategy","nUAV","nPOIs"]).median().to_csv("z_tmp_median.txt", sep=';', encoding='utf-8')

print('PASSO - 3')  # datasets for sparse and dense sets

# data MEAN to plot
df_data_MEAN = pd.read_csv("z_tmp_median.txt",sep=';', encoding='utf-8' )
df_data_MEAN_sparse = df_data_MEAN.loc[df_data_MEAN['nPOIs'] == SPARSE]
df_data_MEAN_dense = df_data_MEAN.loc[df_data_MEAN['nPOIs'] == DENSE]

print('PASSO - 4')

df_file_raw = pd.read_csv(fileIn,sep=';', index_col =["Strategy", "mapName", "nPOIs"] ) #from original file
df_file_raw.groupby(level=["Strategy","mapName","nPOIs"]).mean().to_csv("z_tmp2_mean_maps.txt", sep=';', encoding='utf-8')
df_data_RAW = pd.read_csv("z_tmp2_mean_maps.txt",sep=';', encoding='utf-8' )

print('PASSO - 5')

## data RAW to plot
df_data_RAW_sparse = df_data_RAW.loc[df_data_RAW['nPOIs'] == SPARSE]
df_data_RAW_dense = df_data_RAW.loc[df_data_RAW['nPOIs'] == DENSE]

print('PASSO - 6')

#markers=['s-','o-','^-']
markers=['s-', 'o--', '^-.', 'h:', '*:', 'x-']

##########################################################################################################################

print('Files imported...\n\n')






##########################################################################################################################
print('  _0  Doing Basic/Line Plots...\n\n')
# last checked on 2018-08-08

tiposStats=['Amount of Data','Throughput','Mean Msgs Delays','Worst Data Collection','Amount by toursize']
yStatsCods=['SucessTax','throughput','globalAvgDelay','maxData','TaxPerPathSize']
xStatsCods=['nUAV','nUAV','nUAV','nUAV','nUAV']

dataFrames=[df_data_MEAN_sparse,df_data_MEAN_dense]
dataFramesNames=['Sparse','Dense']

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
            if (dataFramesNames[j] is 'Sparse'):
                ax.set_xlim(3, 17)
            else:
                ax.set_xlim(2, 34)
            labels.append(key)
            k+=1
        lines, _ = ax.get_legend_handles_labels()
        ax.set(xlabel="Number of UAVs", ylabel=tiposStats[i])
        ax.legend(lines, labels, loc='best')
        plt.title(tiposStats[i] + ' - ' + dataFramesNames[j])
    #plt.title(tiposStats[i])
        plt.savefig('_0' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=DPI2SAVE)
    #plt.savefig('_' + tiposStats[i] + '.png', dpi=DPI2SAVE)


print('Graphics regarding nUAV and MEDIANS done...\n\n')
#import sys
#sys.exit()
##########################################################################################################################


##########################################################################################################################
print('  _1  RAW data, without MEANS neither MEDIANS ...\n\n')
# last checked on 2018-08-08

tiposStats=['Tour size by map','Mean msg delay by map','Processing Tour time by map']
yStatsCods=['tourSize','globalAvgDelay','pathTime']
xStatsCods=['mapName','mapName','mapName']

dataFrames=[df_data_RAW_sparse,df_data_RAW_dense]
dataFramesNames=['Sparse','Dense']

for i in range(0,len(tiposStats)):  
    #plt.figure(i+1)                # the first figure
    for j in range(0,len(dataFrames)):
        #plt.subplot(2,2,j+1)             
        #subplot(nrows, ncols, plot_number)
        print(tiposStats[i] + ' - ' + dataFramesNames[j] + '\n\n')
        fig, ax = plt.subplots()
        #plt.tight_layout()
        #plt.gcf().subplots_adjust(bottom=0.10,right=0.15,top=0.11)
        labels = []
        k=0
        for key, grp in dataFrames[j].groupby('Strategy'):
            ax = grp.plot(ax=ax, kind='line', x=xStatsCods[i], y=yStatsCods[i])#, style=markers[k])
            ax.set_xlim(0, 101);
            labels.append(key)
            k+=1
        lines, _ = ax.get_legend_handles_labels()
        
        ax.set(xlabel="Maps", ylabel=tiposStats[i])
        ax.legend(lines, labels, loc='best')
        plt.title(tiposStats[i] + ' - ' + dataFramesNames[j])
    #plt.title(tiposStats[i])
        plt.savefig('_1' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=DPI2SAVE)
    #plt.savefig('_' + tiposStats[i] + '.png', dpi=DPI2SAVE)

plt.clf()

print('Done RAW data, without MEANS neither MEDIANS ...\n\n')
#import sys
#sys.exit()
##########################################################################################################################




##########################################################################################################################
print('  _2  Doing Boxplots...\n\n')
# last checked on 2018-08-08

tiposStats=['Amount of Data','Throughput','Mean msg delay (100x)','Tour size by map (100x)','Processing Tour time (100x)']
yStatsCods=['SucessTax','throughput','globalAvgDelay','tourSize','pathTime']
#xStatsCods=['mapName','mapName','mapName']

#tiposStats=['Mean msg delay by map']
#yStatsCods=['globalAvgDelay']

dataFrames=[df_data_RAW_sparse,df_data_RAW_dense]
dataFramesNames=['Sparse','Dense']

#df_file.loc[df_file['nPOIs'] == 20].boxplot('globalAvgDelay',['nPOIs','Strategy'],sym='') #tá fazendo groupBy pelos colchetes.

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
        plt.savefig('_2' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=DPI2SAVE)
        plt.show()
        plt.clf()

print('Done Boxplots...\n\n')
#import sys
#sys.exit()
##########################################################################################################################




##########################################################################################################################
print('  _3  Doing SPECIFIC/Zoom-in Boxplots...\n\n')
# last checked on 2018-08-08


tiposStats=['Mean msg delay','Amount of Data','Throughput']
yStatsCods=['globalAvgDelay','SucessTax','throughput']
#xStatsCods=['mapName','mapName','mapName']

#tiposStats=['Mean msg delay by map']
#yStatsCods=['globalAvgDelay']

dataFrames=[df_data_RAW_sparse,df_data_RAW_dense]
dataFramesNames=['Sparse','Dense']

wantedStrats=['TSPbased','DADCA-lkh-cutted','DADCA-lkh']

wantedStrats_EXEPTION=['TSPbased','DADCA-lkh-cutted']


#df_file.loc[df_file['nPOIs'] == 20].boxplot('globalAvgDelay',['nPOIs','Strategy'],sym='') #tá fazendo groupBy pelos colchetes.

def boxplot_sorted(df, by, column):
  df2 = pd.DataFrame({col:vals[column] for col, vals in df.groupby(by)})
  meds = df2.median().sort_values()
  df2[meds.index].boxplot(rot=45,sym='')
            
            
for i in range(0,len(tiposStats)):
    plt.rcParams['grid.color'] = 'k'
    plt.rcParams['grid.linestyle'] = ':'
    plt.rcParams['grid.linewidth'] = 0.4
    for j in range(0,len(dataFrames)):        
        #dataFrames[j].boxplot(yStatsCods[i],['Strategy'],sym='')
        
        dftemp = dataFrames[j]
        if tiposStats[i] is 'Mean msg delay':
            df2plot = dftemp.loc[dftemp['Strategy'].isin(wantedStrats_EXEPTION)]
        else:
            df2plot = dftemp.loc[dftemp['Strategy'].isin(wantedStrats)]
        
        #df2plot.boxplot(yStatsCods[i],['Strategy'],sym='')
        
        boxplot_sorted(df2plot, ['Strategy'], yStatsCods[i])
        
        plt.suptitle("")
        plt.title(tiposStats[i] + ' - ' + dataFramesNames[j])
        #plt.xticks(rotation='vertical')
        plt.tight_layout()
        plt.savefig('_3' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=DPI2SAVE)
        plt.show()
        plt.clf()

print('Done with SPECIFIC/Zoom-in Boxplots...\n\n')
#import sys
#sys.exit()
##########################################################################################################################





























