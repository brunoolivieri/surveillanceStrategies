import pandas as pd

df = pd.read_csv("dadosBrutos.txt",sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )

# print(df.groupby(level=["Strategy","nUAV"]).median()) 

# print(df.groupby(level=["Strategy","nUAV","nPOIs"]).median()) 


df.groupby(level=["Strategy","nUAV","nPOIs"]).median().to_csv("resultsMEDIAN.txt", sep=';', encoding='utf-8')

df.groupby(level=["Strategy","nUAV","nPOIs"]).mean().to_csv("resultsMEAN.txt", sep=';', encoding='utf-8')

df.groupby(level=["Strategy","nUAV","nPOIs"]).describe().to_csv("resultsDESCRIBE.txt", sep=';', encoding='utf-8')

print(df.groupby(level=["Strategy","nUAV","nPOIs"]).describe())
