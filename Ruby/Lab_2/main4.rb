def decToBi(val)
    value = ''
    while val > 0
      value += (val % 2).to_s
      val /= 2
    end
    return value.reverse
  end
  
  value = 334
  
  puts decToBi(value)