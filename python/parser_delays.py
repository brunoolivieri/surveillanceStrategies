import pandas as pd

df = pd.read_csv("stats_delays_.txt",sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )

df.groupby(level=["Strategy","nUAV","nPOIs"]).median().to_csv("Delay_MEDIAN.txt", sep=';', encoding='utf-8')
df.groupby(level=["Strategy","nUAV","nPOIs"]).mean().to_csv("Delay_MEAN.txt", sep=';', encoding='utf-8')
df.groupby(level=["Strategy","nUAV","nPOIs"]).describe().to_csv("Delay_DESCRIBE.txt", sep=';', encoding='utf-8')

