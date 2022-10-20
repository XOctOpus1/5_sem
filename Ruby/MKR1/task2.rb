#var 4

# abiturient: id, Surname, First name, Last name, Address, Phone, Grades.
# Create an array of objects. Output :
#      a) a list of abiturients with unsatisfactory grades;
#      b ) a list of abiturient whose total points are higher than the given one ;
#      c ) choose a given number and abiturient with the highest the sum of points (also display a complete list of applicants with semi-pass sum ) .

class Abiturient
    attr_accessor :id, :surname, :name, :lastname, :address, :phone, :grades

    def initialize(id, surname, name, lastname, address, phone, grades)
        @id = id
        @surname = surname
        @name = name
        @lastname = lastname
        @address = address
        @phone = phone
        @grades = grades
    end

    def to_s
        "#{@id} #{@surname} #{@name} #{@lastname} #{@address} #{@phone} #{@grades}"
    end
end

def read_file(file_name)
    file = File.open(file_name, 'r')
    abiturients = []
    file.each_line do |line|
        id, surname, name, lastname, address, phone, grades = line.split(' ')
        abiturients << Abiturient.new(id, surname, name, lastname, address, phone, grades)
    end
    file.close
    abiturients
end

def print_abiturients(abiturients)
    abiturients.each do |abiturient|
        puts abiturient
    end
end

def print_abiturients_with_unsatisfactory_grades(abiturients)
    abiturients.each do |abiturient|
        if abiturient.grades.to_i < 4
            puts abiturient
        end
    end
end

def print_abiturients_with_higher_points(abiturients, points)
    abiturients.each do |abiturient|
        if abiturient.grades.to_i > points
            puts abiturient
        end
    end
end

def print_abiturients_with_highest_points(abiturients, number)
    abiturients.sort! { |a, b| b.grades.to_i <=> a.grades.to_i }
    puts "abiturients with highest points: #{abiturients[0..number - 1]}"
    puts "abiturients with semi-pass sum: #{abiturients[number..abiturients.size]}"
end

def main
    if ARGV.size != 2
        puts 'error'
        exit 1
    end
    file_name = ARGV[0]
    points = ARGV[1].to_i
    if points == 0
        error()
    end
    abiturients = read_file(file_name)
    puts 'abiturients:'
    print_abiturients(abiturients)
    puts '1. list of abiturients with unsatisfactory grades:'
    print_abiturients_with_unsatisfactory_grades(abiturients)
    puts "2. abiturients with point higher than #{points.to_s}"
    print_abiturients_with_higher_points(abiturients, points)
    puts '3. abiturients with higher points:'
    print_abiturients_with_highest_points(abiturients, points)
end

main()
    