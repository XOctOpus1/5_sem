import timeit

E = 10**(-4)

def funk(x):
    s = x**3 + 6*(x**2) + 9*x + 2
    return s

def funkd(x):
    s = 3*(x**2) + 12*x + 9
    return s

def funkdd(x):
    s = 6*x + 12
    return s

# Dihotomiya method
def dihotomiya(a, b, E):
    A = a
    B = b
    n = 1
    min = b - a

    while min/(2**n) > E:
        x = (a+b)/2
        if funk(x) == 0:
            break

        if funk(a)*funk(x) < 0:
            b = x
        else:
            a = x
        n +=1
    x = (a+b)/2
    print(f"Point from {A} to {B} is {x} with E = {E}")

def dihotomiya_steps(a, b, E):
    n = 1
    min = b - a

    while min/(2**n) > E:
        n += 1
    return n

def dihotomiya_steps_aposteriori(a, b, E):
    n = 1
    min = b - a
    x = (a+b)/2
    while min/(2**n) > E:
        if funk(x) == 0:
            break

        if funk(a)*funk(x) < 0:
            b = x
        else:
            a = x
        n +=1
        x = (a+b)/2
    return n



# From image we can see 3 point
# (-4 , -3.5) (-2.5 , -1.5) (-0.5 , 0)

start = timeit.default_timer()

dihotomiya(-4, -3.5, E)
dihotomiya(-2.5, -1.5, E)
dihotomiya(-0.5 , 0, E)
print('\n')

stop = timeit.default_timer()
print('Time: ', stop - start)
print('\n')
#Nuton method


def Nuton(a, b, E):

    if funk(a)*funkdd(a) > 0:
        x0 = a
    else:
        x0 = b

    xn = x0 - (funk(x0)/funkd(x0))
    while abs(xn - x0) > E:
        x0 = xn
        xn = x0 - (funk(x0)/funkd(x0))
    print(f"Point from {a} to {b} is {xn} with E = {E}")


def Nuton_steps(a, b, E):

    n = 0
    if funk(a)*funkdd(a) > 0:
        x0 = a
    else:
        x0 = b

    xn = x0 - (funk(x0)/funkd(x0))
    while abs(xn - x0) > E:
        x0 = xn
        xn = x0 - (funk(x0)/funkd(x0))
        n += 1
    return n


start = timeit.default_timer()

Nuton(-4, -3.5, E)
Nuton(-2.5, -1.5, E)
Nuton(-0.5 , 0, E)
print('\n')   
stop = timeit.default_timer()
print('Time: ', stop - start)
print('\n')   
print("We can see that Nuton method works faster than dihotomiya approximately in 5 times")



