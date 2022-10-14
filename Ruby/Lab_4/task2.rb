class Gauss
  def initialize(n)
    @n = n
    @a = Array.new(@n) { Array.new(@n) }
    @b = Array.new(@n)
    @x = Array.new(@n)
    @k = 2
  end

  def calc
    for i in 0...@n
      for j in 0...@n
        if i == j
          @a[i][j] = 2
        else
          @a[i][j] = @k + 2
        end
      end
      @b[i] = i + 1
    end
    for i in 0...@n
      for j in 0...@n
        if i != j
          @k = @a[j][i] / @a[i][i]
          for k in 0...@n
            @a[j][k] = @a[j][k] - @k * @a[i][k]
          end
          @b[j] = @b[j] - @k * @b[i]
        end
      end
    end
    for i in 0...@n
      @x[i] = @b[i] / @a[i][i]
    end
  end

  def print_a
    puts "Matrix A:"
    for i in 0...@n
      for j in 0...@n
        print @a[i][j].to_s + " "
      end
      puts
    end
  end

  def print_b
    puts "Vector b:"
    for i in 0...@n
      print @b[i].to_s + " "
    end
    puts
  end

  def print_x
    puts "Vector x:"
    for i in 0...@n
      print @x[i].to_s + " "
    end
    puts
  end

  def print_system
    puts "System:"
    for i in 0...@n
      for j in 0...@n
        print @a[i][j].to_s + "x" + (j + 1).to_s + " "
        if j != @n - 1
          print "+ "
        end
      end
      print "= " + @b[i].to_s
        puts 
    end
    end

    def print_solution
        puts "Solution:"
        for i in 0...@n
            print "x" + (i + 1).to_s + " = " + @x[i].to_s
            puts
        end
        end
    end

    puts "Enter the dimension of the system n (from 3 to 9):"
    n = gets.to_i
    if n < 3 || n > 9
        puts "Error! The dimension of the system n must be from 3 to 9."
        exit
    end
    gauss = Gauss.new(n)
    gauss.calc
    gauss.print_a
    gauss.print_b
    gauss.print_system
    gauss.print_solution
    gauss.print_x

    # Output:
    # Enter the dimension of the system n (from 3 to 9):
    # 5
    # Matrix A:
    # 2 4 4 4 4
    # 4 2 4 4 4
    # 4 4 2 4 4
    # 4 4 4 2 4
    # 4 4 4 4 2
    # Vector b:
    # 1 2 3 4 5
    # System:
    # 2x1 + 4x2 + 4x3 + 4x4 + 4x5 = 1
    # 4x1 + 2x2 + 4x3 + 4x4 + 4x5 = 2
    # 4x1 + 4x2 + 2x3 + 4x4 + 4x5 = 3
    # 4x1 + 4x2 + 4x3 + 2x4 + 4x5 = 4
    # 4x1 + 4x2 + 4x3 + 4x4 + 2x5 = 5
    # Solution:
    # x1 = 0.5
    # x2 = 0.75
    # x3 = 1.0
    # x4 = 1.25
    # x5 = 1.5
    # Vector x:
    # 0.5 0.75 1.0 1.25 1.5

