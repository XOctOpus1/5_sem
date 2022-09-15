from tabulate import tabulate

#format_float = "{:.2f}".format(float)

a = float(input("Enter a: "))
b = float(input("Enter b: "))

E = float(input("Enter E: "))
n = 1
min = b - a

table = [['n','a','b','x','f(a)','f(b)','f(x)',f'{min}/2**n']]

def funk(x):
    s = 3*(x**3) - (5*(x**2)) + x -4
    return s




while min/(2**n) > E:
    x = (a+b)/2
    table.append([n, a, b, x, funk(a), funk(b), funk(x), min/(2**n)])

    if funk(a)*funk(x) < 0:
        b = x
    else:
        a = x
    n +=1


x = (a+b)/2
table.append([n, a, b, x, funk(a), funk(b), funk(x), 1/(2**n)])

print(tabulate(table, headers='firstrow'))