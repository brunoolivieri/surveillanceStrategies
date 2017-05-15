import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
matplotlib.style.use('ggplot')

#to use with Jupyter. Remove when using bash
#%matplotlib inline
#plot sample
#df_file.loc[df_file['nPOIs'] == 20].boxplot('globalAvgDelay',['nPOIs','Strategy'],sym='') #tá fazendo groupBy pelos colchetes.

# reading data
df_file = pd.read_csv("cleanDataFile.txt",sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )
df_file.groupby(level=["Strategy","nUAV","nPOIs"]).mean().to_csv("_tmp_.txt", sep=';', encoding='utf-8')
df_data = pd.read_csv("_tmp_.txt",sep=';', encoding='utf-8' )

df_data = df_data.loc[df_data['nPOIs'] == 20]
df_data = df_data.loc[df_data['Strategy'] != 'TSPbased']



for i, group in df_data.groupby('Strategy'):
    plt.figure()
    group.plot(x='nUAV', y='SucessTax', c=str(i))

#plt.legend(loc='best')    
#plt.show()

#fig, ax = plt.subplots()
#labels = []
#
#for key, grp in df_data.groupby('Strategy'):
#    #print(key,grp)
#    ax = grp.plot(ax=ax, kind='line', x='nUAV', y='SucessTax', c=key)
#    labels.append(key)
#    
#lines, _ = ax.get_legend_handles_labels()
#ax.legend(lines, labels, loc='best')

#plt.show()
     

#plt.savefig('_TEMP_.png')

