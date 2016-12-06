import pandas as pd

df = pd.read_csv("dadosBrutos.txt",sep=';', index_col =["Strategy", "nUAV"] )

print(df.groupby(level=["Strategy","nUAV"]).mean())
