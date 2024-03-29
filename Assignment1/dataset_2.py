# -*- coding: utf-8 -*-
"""Dataset_2.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1Hv6qoCc64DMcCdjf5Batr7W7qhzLgdcj
"""

# Commented out IPython magic to ensure Python compatibility.
import pandas as pd
import networkx as nx
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
# %matplotlib inline

from google.colab import drive
drive.mount('/content/drive')

df = pd.read_csv('/content/drive/My Drive/M.Tech NITK/Sem 2/WSC/Assignments/facebook.txt', sep=" ")

G = nx.from_pandas_edgelist(df,'src','des',edge_attr=None)

#degree-distribution
degs = {}
for n in G.nodes ():
    deg = G.degree (n)
    if deg not in degs :
        degs [deg ] = 0
    degs [deg] += 1
items = sorted ( degs . items ())
fig = plt.figure()
ax = fig.add_subplot (111)
ax.plot([k for (k,v) in items ], [v for (k,v) in items ])
#ax. set_xscale('log')
#ax. set_yscale('log')
plt.title("Facebook Degree Distribution")
plt.show()

#diameter
nx.diameter(G)

#geodesic-path-length
nx.shortest_path_length(G,source=0,target=4038)

#Average shortest path length
nx.average_shortest_path_length(G)

#clustering-coefficient
nx.clustering(G)

#average-clustering-coefficient
nx.average_clustering(G)

#strongly-connected-components
count = 0;
for c in nx.strongly_connected_components(nx.to_directed(G)):
  count += 1
  print(c)
print ("No. of SCC: ",count)

#sparseness
nx.density(G)

#k-connectedness
c = nx.k_components(G,flow_func=None)
print(c)