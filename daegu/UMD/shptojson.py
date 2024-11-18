import pandas as pd
import geopandas as gpd
import matplotlib.pyplot as plt
import os
print(os.path.exists('/daegu/UMD/LSMD_ADM_SECT_UMD_27_202409.shp'))

df = gpd.read_file('LSMD_ADM_SECT_UMD_27_202409.shp', encoding='CP949')
#print(df.head())
#pd.set_option('display.max_rows', None)  # 모든 행 표시
#pd.set_option('display.max_columns', None)  # 모든 열 표시
print(df)

print(df['geometry'])
df.plot()
plt.show()


#df.to_excel('/daegu/UMD/LSMD_ADM_SECT_UMD_27_202409.xlsx', index=False)
