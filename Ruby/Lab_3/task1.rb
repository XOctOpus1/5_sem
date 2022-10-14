# var 4

a = false
b = false
c = true
x = 60
y = -10
z = 4

puts "a)#{!(a || b) && (a && !b)}"
puts "b)#{(z != y) == (6 >= y) && a || b && c && x>=1.5}"
puts "c)#{(8 - x * 2 <= z) && (x**2 <= y**2) ||  (z >= 15)}"
puts "d)#{x > 0 && y < 0 || z >= (x * y - (-y / x)) + (-z)}"
puts "e)#{!(a || b && !(c || (!a || b)))}"
puts "f)#{x^2 + y^2 >= 1 && x >= 0 && y >= 0}"
puts "g)#{(a && (c && b == b || a) || c) && b}"



x = -0.5
y = -1
p = true


puts "#2 #{((y * y - x) > x * x) || (Math.cos(x) > 0) && !p}"