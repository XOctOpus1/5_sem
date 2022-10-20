#var 4


a = gets.to_f
b = gets.to_f
c = gets.to_f
xs = gets.to_f
xf = gets.to_f
dx = gets.to_f

x = xs
while x <= xf
  ra = a.to_i
  rb = b.to_i
  rc = c.to_i
  if ra != 0 || rb != 0 || rc != 0
    f = if c < 0 && x != 0
          -a * x - c
        elsif c > 0 && x == 0
          (x - a) / (-c)
        else
          b * x / (c - a)
        end
  else
    f = (b * x / (c - a)).to_i
  end
  puts "x = #{x} F = #{f}"
  x += dx
end