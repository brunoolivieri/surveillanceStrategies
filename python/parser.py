import pandas as pd

df = pd.read_csv("dadosBrutos.txt",sep=';', index_col =["Strategy", "nUAV", "nPOIs"] )

# print(df.groupby(level=["Strategy","nUAV"]).median()) 

print(df.groupby(level=["Strategy","nUAV","nPOIs"]).median()) 

