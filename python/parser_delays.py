import pandas as pd

df = pd.read_csv("stats_delays_KIMPOverNSN.txt",sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )

df.groupby(level=["Strategy","nUAV","nPOIs"]).median().to_csv("DelayKIMP_MEDIAN.txt", sep=';', encoding='utf-8')
df.groupby(level=["Strategy","nUAV","nPOIs"]).mean().to_csv("DelayKIMP_MEAN.txt", sep=';', encoding='utf-8')
df.groupby(level=["Strategy","nUAV","nPOIs"]).describe().to_csv("DelayKIMP_DESCRIBE.txt", sep=';', encoding='utf-8')


df2 = pd.read_csv("stats_delays_TSPbased.txt",sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )

df2.groupby(level=["Strategy","nUAV","nPOIs"]).median().to_csv("DelayTSP_MEDIAN.txt", sep=';', encoding='utf-8')
df2.groupby(level=["Strategy","nUAV","nPOIs"]).mean().to_csv("DelayTSP_MEAN.txt", sep=';', encoding='utf-8')
df2.groupby(level=["Strategy","nUAV","nPOIs"]).describe().to_csv("DelayTSP_DESCRIBE.txt", sep=';', encoding='utf-8')



df3 = pd.read_csv("stats_delays_ZigZagOverNSN.txt",sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )

df3.groupby(level=["Strategy","nUAV","nPOIs"]).median().to_csv("DelayZZ_MEDIAN.txt", sep=';', encoding='utf-8')
df3.groupby(level=["Strategy","nUAV","nPOIs"]).mean().to_csv("DelayZZ_MEAN.txt", sep=';', encoding='utf-8')
df3.groupby(level=["Strategy","nUAV","nPOIs"]).describe().to_csv("DelayZZ_DESCRIBE.txt", sep=';', encoding='utf-8')


