# var 4

def matrix(n)
  matrix = Array.new(n) { Array.new(n) }
  matrix.each_with_index do |row, i|
    row.each_with_index do |col, j|
      if i == j
        matrix[i][j] = 1
      else
        matrix[i][j] = rand(10)
      end
    end
  end
  matrix
end

def vector(n)
  vector = Array.new(n)
  vector.each_with_index do |row, i|
    vector[i] = rand(10)
  end
  vector
end


def multiply_matrix(matrix_a, matrix_b)
  matrix_c = Array.new(matrix_a.size) { Array.new(matrix_b.size) }
  matrix_c.each_with_index do |row, i|
    row.each_with_index do |col, j|
      matrix_c[i][j] = 0
      matrix_a[i].each_with_index do |col, k|
        matrix_c[i][j] += matrix_a[i][k] * matrix_b[k][j]
      end
    end
  end
  matrix_c
end

def multiply_vector(matrix, vector)
  vector_c = Array.new(vector.size)
  vector_c.each_with_index do |row, i|
    vector_c[i] = 0
    matrix[i].each_with_index do |col, j|
      vector_c[i] += matrix[i][j] * vector[j]
    end
  end
  vector_c
end

def print_matrix(matrix)
  matrix.each do |row|
    row.each do |col|
      print col, ' '
    end
    puts
  end
end


def print_vector(vector)
  vector.each do |row|
    print row, ' '
  end
  puts
end

matrix_a = matrix(8)
matrix_b = matrix(8)

vector_x = vector(8)
vector_y = vector(8)

puts 'Matrix A:'
print_matrix(matrix_a)
puts 'Matrix B:'
print_matrix(matrix_b)
puts 'Vector X:'
print_vector(vector_x)
puts 'Vector Y:'
print_vector(vector_y)

matrix_c = multiply_matrix(matrix_a, matrix_b)
vector_c = multiply_vector(matrix_a, vector_x)

puts 'Matrix C:'
print_matrix(matrix_c)
puts 'Vector C:'
print_vector(vector_c)



