print " Enter x : \n "
$sr = gets.chomp
x = $sr.to_s.to_f

print " Enter j : \n "
$sh = gets.chomp
j = $sh.to_s.to_f


V = Math.cos(24*Math::PI/2)+((Math.tan(Math.log(x**3,Math.exp(1))**5) + 4.2*(10**(-2.8)))/(Math.sqrt((x + Math.exp(j)).abs)))


puts V