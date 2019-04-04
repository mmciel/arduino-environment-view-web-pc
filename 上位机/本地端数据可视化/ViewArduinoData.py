import matplotlib.pyplot as plt 
import pandas as pd 
import pymysql

conn = pymysql.connect("111.230.198.57","root","123456","arduinodata",charset="utf8")
#print("连接成功")

data = pd.read_sql("select time, tem,sun,hum from tabledata",con=conn)
x = list(data.tem)
y = list(data.hum)
z = list(data.sun)
k = list(data.time)
a = list(range(len(k)))

x = list(map(int, x))
y = list(map(int, y))
z = list(map(int, z))

fig = plt.figure()
fig.tight_layout(pad=0.4, w_pad=3.0, h_pad=3.0)
ax1 = plt.subplot(3,1,1);
ax2 = plt.subplot(3,1,2);
ax3 = plt.subplot(3,1,3);

plt.sca(ax1)
plt.xlabel("time")
plt.ylabel("tem")
plt.title("Arduino Data View ")
plt.plot(a,x,"g--")

plt.sca(ax2)
plt.xlabel("time")
plt.ylabel("hum")
plt.plot(a,y,"b--")

plt.sca(ax3)
plt.xlabel("time")
plt.ylabel("sun")
plt.plot(a,z,"r--")

plt.show()
conn.close()
