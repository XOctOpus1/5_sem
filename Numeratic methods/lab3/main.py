
import numpy as np
import matplotlib.pyplot as plt

def f(x):
    return x**2 + np.sin(x) - 12*x - 0.25

def df(x):
    return 2*x + np.cos(x) - 12


def newton(x0, f, df, eps):
    x = x0
    while abs(f(x)) > eps:
        x = x - f(x)/df(x)
    return x

def chebyshov(n):
    x = np.zeros(n)
    for i in range(n):
        x[i] = np.cos(np.pi*(2*i+1)/(2*n))
    return x

def newton_interpolation(x, y):
    n = len(x)
    a = np.zeros(n)
    a[0] = y[0]
    for i in range(1, n):
        a[i] = y[i]
        for j in range(i-1, -1, -1):
            a[j] = (a[j] - a[j+1])/(x[j] - x[i])
    return a

def newton_polynomial(x, a):
    n = len(x)
    def p(x0):
        p = a[n-1]
        for i in range(n-2, -1, -1):
            p = p*(x0 - x[i]) + a[i]
        return p
    return p

def inverse_interpolation(x, y, x0):
    n = len(x)
    a = np.zeros(n)
    a[0] = y[0]
    for i in range(1, n):
        a[i] = y[i]
        for j in range(i-1, -1, -1):
            a[j] = (a[j] - a[j+1])/(x[j] - x[i])
    def p(x0):
        p = a[n-1]
        for i in range(n-2, -1, -1):
            p = p*(x0 - x[i]) + a[i]
        return p
    return p(x0)

def main():
    n = 10
    x = chebyshov(n)
    y = f(x)
    a = newton_interpolation(x, y)
    p = newton_polynomial(x, a)
    x0 = newton(0, p, df, 1e-6)
    print('Newton method: x = ', x0)
    print('Inverse interpolation: x = ', inverse_interpolation(x, y, x0))

if __name__ == '__main__':
    main()
    