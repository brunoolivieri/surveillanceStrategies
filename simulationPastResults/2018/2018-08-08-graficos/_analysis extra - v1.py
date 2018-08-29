# 
#
# Extra stats -> sparse/dense/loaded
# _4xx: lines regarding PROCESSING TIME AND TOURSIZE 
# _5xx: boxplot regarding _4xxx
# _6xx: zoom in _5xx
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
LOADED=200
DPI2SAVE=100

#to use with Jupyter. Remove when using bash
#%matplotlib inline


#plot sample
#df_file.loc[df_file['nPOIs'] == 20].boxplot('globalAvgDelay',['nPOIs','Strategy'],sym='') #tá fazendo groupBy pelos colchetes.

print('PASSO - 1')
            
# reading data | ugly unstack method with a file... but !?!??
df_file = pd.read_csv(fileIn,sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )

print('PASSO - 2')

df_file.groupby(level=["Strategy","nUAV","nPOIs"]).median().to_csv("z_tmp_.txt", sep=';', encoding='utf-8')
df_data_MEAN = pd.read_csv("z_tmp_.txt",sep=';', encoding='utf-8' )

print('PASSO - 3')

# data MEAN to plot
df_data_MEAN_sparse = df_data_MEAN.loc[df_data_MEAN['nPOIs'] == SPARSE]
df_data_MEAN_dense = df_data_MEAN.loc[df_data_MEAN['nPOIs'] == DENSE]
df_data_MEAN_loaded = df_data_MEAN.loc[df_data_MEAN['nPOIs'] == LOADED]

print('PASSO - 4')

df_file_raw = pd.read_csv(fileIn,sep=';', index_col =["Strategy", "mapName", "nPOIs"] )
df_file_raw.groupby(level=["Strategy","mapName","nPOIs"]).mean().to_csv("z_tmp2_.txt", sep=';', encoding='utf-8')
df_data_RAW = pd.read_csv("z_tmp2_.txt",sep=';', encoding='utf-8' )

print('PASSO - 5')

## data RAW to plot
df_data_RAW_sparse = df_data_RAW.loc[df_data_RAW['nPOIs'] == SPARSE]
df_data_RAW_dense = df_data_RAW.loc[df_data_RAW['nPOIs'] == DENSE]
df_data_RAW_loaded = df_data_RAW.loc[df_data_RAW['nPOIs'] == LOADED]

print('PASSO - 6')

#markers=['s-','o-','^-']
markers=['s-', 'o--', '^-.', 'h:', '*:', 'x-']

##########################################################################################################################

print('Files imported...\n\n')


##########################################################################################################################




##########################################################################################################################


print('  _4  Going back do RAW data, without means...\n\n')

tiposStats=['Tour size','pathTime']
yStatsCods=['tourSize','pathTime']
xStatsCods=['mapName','mapName']

dataFrames=[df_data_RAW_sparse,df_data_RAW_dense,df_data_RAW_loaded]
dataFramesNames=['Sparse','Dense','Loaded']

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
        plt.savefig('_4' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=DPI2SAVE)
    #plt.savefig('_' + tiposStats[i] + '.png', dpi=DPI2SAVE)

plt.clf()

print('  _4  Done RAW data, without means...\n\n')

#import sys
#sys.exit()

##########################################################################################################################
print('  _5  Doing Boxplots...\n\n')

tiposStats=['Tour size','pathTime']
yStatsCods=['tourSize','pathTime']
xStatsCods=['mapName','mapName']

dataFrames=[df_data_RAW_sparse,df_data_RAW_dense,df_data_RAW_loaded]
dataFramesNames=['Sparse','Dense','Loaded']

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
        plt.savefig('_5' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=DPI2SAVE)
        plt.show()
        plt.clf()

print('  _5    Done Boxplots...\n\n')

#import sys
#sys.exit()

##########################################################################################################################


print('  _6  Doing SPECIFIC Boxplots for tour Size...\n\n')

tiposStats=['Tour size','pathTime']
yStatsCods=['tourSize','pathTime']
xStatsCods=['mapName','mapName']

dataFrames=[df_data_RAW_sparse,df_data_RAW_dense,df_data_RAW_loaded]
dataFramesNames=['Sparse','Dense','Loaded']

wantedStrats_TIME=['FPPWR','DADCA-NSN','DADCA-parted-nsn']

wantedStrats_TOUR=['TSPConcorde','DADCA-lkh-cutted','DADCA-lkh']


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
        if tiposStats[i] is 'Tour size':
            df2plot = dftemp.loc[dftemp['Strategy'].isin(wantedStrats_TOUR)]
        else:
            df2plot = dftemp.loc[dftemp['Strategy'].isin(wantedStrats_TIME)]
        
        #df2plot.boxplot(yStatsCods[i],['Strategy'],sym='')
        
        boxplot_sorted(df2plot, ['Strategy'], yStatsCods[i])
        
        plt.suptitle("")
        plt.title(tiposStats[i] + ' - ' + dataFramesNames[j])
        #plt.xticks(rotation='vertical')
        plt.tight_layout()
        plt.savefig('_6' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=DPI2SAVE)
        plt.show()
        plt.clf()

print('  _6  Done SPECIFIC Boxplots for tour Size...\n\n')

#import sys
#sys.exit()
##########################################################################################################################



















