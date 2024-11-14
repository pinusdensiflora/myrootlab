import pandas
import geopandas as gpd
import matplotlib.pyplot as plt

df = gpd.read_file('/daegu/LARD_ADM_SECT_SGG_27_202405.shp')
print(df.head())
print(df)
df.plot()
plt.show()
