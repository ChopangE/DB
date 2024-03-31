import numpy as np
#A
M = np.array([i for i in range(2,27)])
print(M)
#B
M = M.reshape(5,5)
print(M)
#C
for i in range(5):
    M[i,0] = 0
print(M)
#D
M = M@M
print(M)
#E
v = M[0,::]
sum = 0
for i in range(5):
   sum += v[i]*v[i]
mag = np.sqrt(sum)
print(mag)
