import numpy as np
import math

def gauss(matrix, b):
    n = len(matrix)
    for i in range(n):
        for j in range(i + 1, n):
            if matrix[i][i] == 0:
                print("Error")
                return
            else:
                c = matrix[j][i] / matrix[i][i]
                for k in range(n):
                    matrix[j][k] = matrix[j][k] - c * matrix[i][k]
                b[j] = b[j] - c * b[i]
    x = [0 for i in range(n)]
    for i in range(n - 1, -1, -1):
        x[i] = b[i]
        for j in range(i + 1, n):
            x[i] = x[i] - matrix[i][j] * x[j]
        x[i] = x[i] / matrix[i][i]
    return x

def jacobi(matrix, b, eps):
    n = len(matrix)
    x = [0 for i in range(n)]
    x1 = [0 for i in range(n)]
    k = 0
    while True:
        for i in range(n):
            s = 0
            for j in range(n):
                if i != j:
                    s += matrix[i][j] * x[j]
            x1[i] = (b[i] - s) / matrix[i][i]
        k += 1
        if norm(x1, x) < eps:
            break
        for i in range(n):
            x[i] = x1[i]
    return x1

def norm(x1, x):
    n = len(x1)
    s = 0
    for i in range(n):
        s += (x1[i] - x[i]) ** 2
    return math.sqrt(s)

def main():
    matrix = [[0.34, 0.71, 0.63], [0.71, -0.65, -0.18], [1.17, -2.35, 0.75]]
    b = [2.08, 0.17, 1.28]
    print("Gauss method")
    print(gauss(matrix, b))
    print("Jacobi method")
    print(jacobi(matrix, b, 0.0001))

if __name__ == "__main__":
    main()


