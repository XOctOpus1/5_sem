from sympy import *


a = float(input("Enter a: "))
b = float(input("Enter b: "))



E = float(input("Enter E: "))
x = Symbol('x')


y = 3*(x**3) - (5*(x**2)) + x -4
ydiff = diff(diff(y))

Xl = []

x0 = a - (y.subs(x, a))*((b-a)/(y.subs(x, b)-y.subs(x, a)))
Xl.append(float(x0))


k = 0
if y.subs(x, a) * ydiff.subs(x, a) > 0:
    while True:
        xk = Xl[k] - ((y.subs(x, Xl[k]))*((Xl[k]-a)/(y.subs(x, Xl[k])-y.subs(x, a))))
        Xl.append(float(xk))
        if abs(Xl[k+1] - Xl[k]) < E:
            print(f"{Xl[k+1]}")
            break
        k += 1   
if y.subs(x, b) * ydiff.subs(x, b) > 0:
    while True:
        xk = Xl[k] - ((y.subs(x, Xl[k]))*((Xl[k]-b)/(y.subs(x, Xl[k])-y.subs(x, b))))
        Xl.append(float(xk))
        if abs(Xl[k+1] - Xl[k]) < E:
            print(f"{Xl[k+1]}")
            break
        k += 1

    