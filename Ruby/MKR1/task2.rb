#var 4



class Abiturients
    def initialize(id, surname, first_name, last_name, address, phone, grades)
        @id = id
        @surname = surname
        @first_name = first_name
        @last_name = last_name
        @address = address
        @phone = phone
        @grades = grades
    end
    
    def id
        @id
    end
    
    def surname
        @surname
    end
    
    def first_name
        @first_name
    end
    
    def last_name
        @last_name
    end
    
    def address
        @address
    end
    
    def phone
        @phone
    end
    
    def grades
        @grades
    end
    
    def sum_grades
        @grades.inject(0) { |sum, x| sum + x }
    end
    
    def to_s
        "#{@id} #{@surname} #{@first_name} #{@last_name} #{@address} #{@phone} #{@grades}"
    end
    end
    
    def input_abiturients
    abiturients = []
    puts 'Enter abiturients (id, surname, first_name, last_name, address, phone, grades):'
    while true
        abiturient = gets.chomp
        break if abiturient == ''
        abiturient = abiturient.split(' ')
        abiturients << Abiturients.new(abiturient[0], abiturient[1], abiturient[2], abiturient[3], abiturient[4], abiturient[5], abiturient[6..-1].map(&:to_i))
    end
    abiturients
    end
    
    def output_abiturients(abiturients)
    puts 'Abiturients:'
    abiturients.each { |abiturient| puts abiturient }
    end
    
    def output_unsatisfactory_grades(abiturients)
    puts 'Abiturients with unsatisfactory grades:'
    abiturients.each { |abiturient| puts abiturient if abiturient.grades.any? { |grade| grade < 4 } }
    end
    
    def output_sum_grades(abiturients, sum)
    puts "Abiturients whose total points are higher than #{sum}:"
    abiturients.each { |abiturient| puts abiturient if abiturient.sum_grades > sum }
    end

    def output_highest_sum_grades(abiturients, count)
    puts "Abiturients with the highest the sum of points (#{count}):"
    abiturients.sort_by { |abiturient| abiturient.sum_grades }.reverse[0..count - 1].each { |abiturient| puts abiturient }
    end

puts 'Enter the number of abiturients'
count = gets.chomp.to_i
abiturients = []
count.times do
    abiturients << input_abiturients
end

output_abiturients(abiturients.flatten)
output_unsatisfactory_grades(abiturients.flatten)
puts 'Enter total points'
sum = gets.chomp.to_i
output_sum_grades(abiturients.flatten, sum)
puts 'Enter count of highest the sum of points'
count = gets.chomp.to_i
output_highest_sum_grades(abiturients.flatten, count)


