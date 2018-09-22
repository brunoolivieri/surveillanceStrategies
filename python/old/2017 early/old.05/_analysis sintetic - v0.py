import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
matplotlib.style.use('ggplot')


fileIn="_2017-06-05 - fully 20 40 200 - TST.txt"
SPARSE=20
DENSE=200

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

print('PASSO - 4')

df_file_raw = pd.read_csv(fileIn,sep=';', index_col =["Strategy", "mapName", "nPOIs"] )
df_file_raw.groupby(level=["Strategy","mapName","nPOIs"]).mean().to_csv("z_tmp2_.txt", sep=';', encoding='utf-8')
df_data_RAW = pd.read_csv("z_tmp2_.txt",sep=';', encoding='utf-8' )

print('PASSO - 5')

## data RAW to plot
df_data_RAW_sparse = df_data_RAW.loc[df_data_RAW['nPOIs'] == SPARSE]
df_data_RAW_dense = df_data_RAW.loc[df_data_RAW['nPOIs'] == DENSE]

print('PASSO - 6')

#markers=['s-','o-','^-']
markers=['s-', 'o--', '^-.', 'h:', '*:', 'x-']

##########################################################################################################################

print('Files imported...\n\n')


tiposStats=['Amount of Data','Throughput','Mean Msgs Delays','Worst Data Collection','Amount by toursize']
yStatsCods=['SucessTax','throughput','globalAvgDelay','maxData','TaxPerPathSize']
xStatsCods=['nUAV','nUAV','nUAV','nUAV','nUAV']

dataFrames=[df_data_MEAN_sparse,df_data_MEAN_dense]
dataFramesNames=['Sparse','Dense']



for i in range(0,len(tiposStats)):  
    #plt.figure(i+1)                # the first figure
    for j in range(0,len(dataFrames)):
        #plt.subplot(2,2,j+1)             
        #subplot(nrows, ncols, plot_number)
        print(tiposStats[i] + ' - ' + dataFramesNames[j] + '\n\n')
        fig, ax = plt.subplots()
        #plt.tight_layout()
        #plt.gcf().subplots_adjust(bottom=0.15)
        #plt.grid(False)
        #plt.rc('grid', linestyle="--", color='gray')
        #plt.grid(color='k', linewidth=.5, linestyle=':')
        #plt.rcParams['axes.facecolor']=0.75
        
        plt.rcParams['grid.color'] = 'k'
        plt.rcParams['grid.linestyle'] = ':'
        plt.rcParams['grid.linewidth'] = 0.4

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
        plt.savefig('_0' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=100)
    #plt.savefig('_' + tiposStats[i] + '.png', dpi=100)


print('Graphics regarding nUAV and MEDIANS done...\n\n')

##########################################################################################################


import sys
sys.exit()

print('Going back do RAW data, without means...\n\n')

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
        plt.savefig('_1' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=100)
    #plt.savefig('_' + tiposStats[i] + '.png', dpi=100)

plt.clf()

print('Done RAW data, without means...\n\n')


##########################################################################################################
##########################################################################################################
##########################################################################################################

print('Doing Boxplots...\n\n')

tiposStats=['Tour size by map','Mean msg delay by map','Processing Tour time by map']
yStatsCods=['tourSize','globalAvgDelay','pathTime']
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
    for j in range(0,len(dataFrames)):        
        #dataFrames[j].boxplot(yStatsCods[i],['Strategy'],sym='')
        boxplot_sorted(dataFrames[j], ['Strategy'], yStatsCods[i])
        plt.suptitle("")
        plt.title(tiposStats[i] + ' - ' + dataFramesNames[j])
        #plt.xticks(rotation='vertical')
        plt.tight_layout()
        plt.savefig('_2' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=100)
        plt.show()
        plt.clf()

#import sys
#sys.exit()
##################################

print('Doing SPECIFIC Boxplots...\n\n')



tiposStats=['Mean msg delay by map']
yStatsCods=['globalAvgDelay']
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
    for j in range(0,len(dataFrames)):        
        #dataFrames[j].boxplot(yStatsCods[i],['Strategy'],sym='')
        dftemp = dataFrames[j]
        df2plot = dftemp.loc[(dftemp['Strategy'] == 'TSPbased') or  (dftemp['Strategy'] == 'DADCA')]
        boxplot_sorted(df2plot, ['Strategy'], yStatsCods[i])
        plt.suptitle("")
        plt.title(tiposStats[i] + ' - ' + dataFramesNames[j])
        #plt.xticks(rotation='vertical')
        plt.tight_layout()
        plt.savefig('_3' + str(i) + str(j) + '_' + tiposStats[i] + ' - ' + dataFramesNames[j] + '.png', dpi=100)
        plt.show()
        plt.clf()

#import sys
#sys.exit()
##################################

