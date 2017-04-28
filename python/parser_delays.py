import pandas as pd

df = pd.read_csv("stats_delays_.txt",sep=';', index_col =["Strategy", "nPOIs", "nUAV"] )

df.groupby(level=["Strategy", "nPOIs", "nUAV"]).median().to_csv("delays_MEDIAN.txt", sep=';', encoding='utf-8')
df.groupby(level=["Strategy", "nPOIs", "nUAV"].mean().to_csv("delays_MEAN.txt", sep=';', encoding='utf-8')
df.groupby(level=["Strategy", "nPOIs", "nUAV"]).describe().to_csv("delays_DESCRIBE.txt", sep=';', encoding='utf-8')

