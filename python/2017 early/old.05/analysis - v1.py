import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
matplotlib.style.use('ggplot')


fileIn="cleanDataFile-2code.txt"

#to use with Jupyter. Remove when using bash
#%matplotlib inline


#plot sample
#df_file.loc[df_file['nPOIs'] == 20].boxplot('globalAvgDelay',['nPOIs','Strategy'],sym='') #tá fazendo groupBy pelos colchetes.

# reading data | ugly unstack method with a file... but !?!??
df_file = pd.read_csv(fileIn,sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )

df_file.groupby(level=["Strategy","nUAV","nPOIs"]).mean().to_csv("_tmp_.txt", sep=';', encoding='utf-8')
df_data_MEAN = pd.read_csv("_tmp_.txt",sep=';', encoding='utf-8' )

# data MEAN to plot
df_data_MEAN_sparse = df_data_MEAN.loc[df_data_MEAN['nPOIs'] == 20]
df_data_MEAN_dense = df_data_MEAN.loc[df_data_MEAN['nPOIs'] == 200]

df_file_raw = pd.read_csv(fileIn,sep=';', index_col =["Strategy", "mapName", "nPOIs"] )
df_file_raw.groupby(level=["Strategy","mapName","nPOIs"]).mean().to_csv("_tmp2_.txt", sep=';', encoding='utf-8')
df_data_RAW = pd.read_csv("_tmp2_.txt",sep=';', encoding='utf-8' )


## data RAW to plot
df_data_RAW_sparse = df_data_RAW.loc[df_data_RAW['nPOIs'] == 20]
df_data_RAW_dense = df_data_RAW.loc[df_data_RAW['nPOIs'] == 200]


#markers=['s-','o-','^-']
markers=['s-', 'o--', '^-.', '^:']

##########################################################################################################################

print('Files imported...')


# SucessTax plots  #####################################################################

#### Sparse
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='SucessTax', style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="Taxa de Recuperação de Dados")
ax.legend(lines, labels, loc='best')
plt.title('TD_t - ESPARÇO')

plt.savefig('_SucessTax_Sparse.png', dpi=100)

#### Dense
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_dense.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='SucessTax',style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="Taxa de Recuperação de Dados")
ax.legend(lines, labels, loc='best')
plt.title('TD_t - DENSO')

plt.savefig('_SucessTax_Dense.png', dpi=100)

########################################################################################







# throughput plots  #####################################################################

#### Sparse
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='throughput',style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="throughput")
ax.legend(lines, labels, loc='best')
plt.title('throughput - ESPARÇO')

plt.savefig('_throughput_Sparse.png', dpi=100)

#### Dense
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_dense.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='throughput',style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="throughput")
ax.legend(lines, labels, loc='best')
plt.title('throughput - DENSO')

plt.savefig('_throughput_Dense.png', dpi=100)

########################################################################################


# globalAvgDelay plots  #####################################################################

#### Sparse
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='globalAvgDelay',style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="globalAvgDelay")
ax.legend(lines, labels, loc='best')
plt.title('globalAvgDelay - ESPARÇO')

plt.savefig('_globalAvgDelay_Sparse.png', dpi=100)

#### Dense
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_dense.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='globalAvgDelay',style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="globalAvgDelay")
ax.legend(lines, labels, loc='best')
plt.title('globalAvgDelay - DENSO')

plt.savefig('_globalAvgDelay_Dense.png', dpi=100)

########################################################################################



# maxData/starvation plots  #####################################################################

#### Sparse
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='maxData',style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="maxData")
ax.legend(lines, labels, loc='best')
plt.title('maxData - ESPARÇO')

plt.savefig('_maxData_Sparse.png', dpi=100)

#### Dense
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_dense.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='maxData',style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="maxData")
ax.legend(lines, labels, loc='best')
plt.title('maxData - DENSO')

plt.savefig('_maxData_Dense.png', dpi=100)

########################################################################################




# TaxPerPathSize/Efficient plots  #####################################################################

#### Sparse
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='TaxPerPathSize',style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="TaxPerPathSize")
ax.legend(lines, labels, loc='best')
plt.title('TaxPerPathSize - ESPARÇO')

plt.savefig('_TaxPerPathSize_Sparse.png', dpi=100)

#### Dense
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_MEAN_dense.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='TaxPerPathSize',style=markers[i])
    ax.set_xlim(1, 17);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="Número de UAVs", ylabel="TaxPerPathSize")
ax.legend(lines, labels, loc='best')
plt.title('TaxPerPathSize - DENSO')

plt.savefig('_TaxPerPathSize_Dense.png', dpi=100)

########################################################################################









# tourSize/mapa plots  #####################################################################

#### Sparse
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_RAW_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='mapName', y='tourSize',style=markers[i])
    #ax.set_xlim(0, 101);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="maps", ylabel="tourSize")
ax.legend(lines, labels, loc='best')
plt.title('tourSize - ESPARÇO')

plt.savefig('_tourSize_Sparse.png', dpi=100)

#### Dense
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_RAW_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='mapName', y='tourSize',style=markers[i])
    #ax.set_xlim(0, 101);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="maps", ylabel="tourSize")
ax.legend(lines, labels, loc='best')
plt.title('tourSize - DENSO')

plt.savefig('_tourSize_Dense.png', dpi=100)

########################################################################################



# delay/mapa plots  #####################################################################

#### Sparse
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_RAW_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='mapName', y='globalAvgDelay',style=markers[i])
    #ax.set_xlim(0, 101);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="maps", ylabel="globalAvgDelay")
ax.legend(lines, labels, loc='best')
plt.title('globalAvgDelay - ESPARÇO')

plt.savefig('_tourSize_Sparse.png', dpi=100)

#### Dense
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_RAW_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='mapName', y='globalAvgDelay',style=markers[i])
    #ax.set_xlim(0, 101);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="maps", ylabel="globalAvgDelay")
ax.legend(lines, labels, loc='best')
plt.title('globalAvgDelay - DENSO')

plt.savefig('_globalAvgDelay_Dense.png', dpi=100)

########################################################################################



# tourSize/mapa plots  #####################################################################

#### Sparse
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_RAW_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='mapName', y='tourSize',style=markers[i])
    #ax.set_xlim(0, 101);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="maps", ylabel="tourSize")
ax.legend(lines, labels, loc='best')
plt.title('tourSize - ESPARÇO')

plt.savefig('_tourSize_Sparse.png', dpi=100)

#### Dense
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_RAW_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='mapName', y='tourSize',style=markers[i])
    #ax.set_xlim(0, 101);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="maps", ylabel="tourSize")
ax.legend(lines, labels, loc='best')
plt.title('tourSize - DENSO')

plt.savefig('_tourSize_Dense.png', dpi=100)

########################################################################################



# processingTime/mapa plots  #####################################################################

#### Sparse
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_RAW_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='mapName', y='simumationTimeMS',style=markers[i])
    #ax.set_xlim(0, 101);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="maps", ylabel="simumationTimeMS")
ax.legend(lines, labels, loc='best')
plt.title('simumationTimeMS/map - ESPARÇO')

plt.savefig('_simumationTimeMS_map_Sparse.png', dpi=100)

#### Dense
fig, ax = plt.subplots()
labels = []
i=0
for key, grp in df_data_RAW_sparse.groupby('Strategy'):
    ax = grp.plot(ax=ax, kind='line', x='mapName', y='simumationTimeMS',style=markers[i])
    #ax.set_xlim(0, 101);
    labels.append(key)
    i+=1
lines, _ = ax.get_legend_handles_labels()

ax.set(xlabel="maps", ylabel="simumationTimeMS")
ax.legend(lines, labels, loc='best')
plt.title('simumationTimeMS/map - DENSO')

plt.savefig('_simumationTimeMS_map_Dense.png', dpi=100)

########################################################################################



