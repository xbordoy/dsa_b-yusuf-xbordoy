import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('insert_benchmark.csv', encoding='utf-8').fillna(0)

# selecting only first 10 datapoints
x = data['Number of Inserts'].iloc[0:10].values
y = data['Elapsed Time (ms)'].iloc[0:10].values
print(x, y)

fig = plt.figure()
ax = fig.add_subplot(111)
ax.set_title('title')
ax.set_xlabel('time')
ax.set_ylabel('xlabel')
ax.plot(x, c='r', label='Data')
leg = ax.legend()
plt.show
