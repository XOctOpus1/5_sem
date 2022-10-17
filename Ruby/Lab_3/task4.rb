def task_1(n)
    res = 0
    factorial = 2
  
    (2..n).each { |i|
      res += ((factorial / (i - 1)) / (factorial * (i + 1))) ** (i * (i + 1))
      factorial *= (i + 1)
    }
  
    return res
    end
  

def task_2(n)
        pi = 3.14**2/12
        res = 0
        (1..n).each{ |i|
        res += (-1)**i * (1.0 / (i**2))
        }
        puts pi == res
    end
  
  

def task_3(n)
        res = 0
        factorials = [2, 6, 24, 2]
    
        (1..n).each{ |i|

            res += (factorials[0] * factorials[1]) / (factorials[2] * (3 ** (2 * i)) * factorials[3])
            
            #factorial 3n - 1
            factorials[0] *= (3 * i - 1)
            #factorial 3n
            factorials[1] *= (3 * i)
            #factorial 4n
            factorials[2] *= (4 * i)
            #factorial 2n
            factorials[3] *= (2 * i)
        }
    end
  