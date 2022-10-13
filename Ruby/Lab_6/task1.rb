
class Reader
    attr_accessor :surname, :date_order, :date_issue
    def initialize(surname, date_order, date_issue)
        @surname = surname
        @date_order = date_order
        @date_issue = date_issue
    end
    end
    
    class Book
    attr_accessor :name, :author, :genre
    def initialize(name, author, genre)
        @name = name
        @author = author
        @genre = genre
    end
    end
    
    class Library
    attr_accessor :readers, :books
    def initialize(readers, books)
        @readers = readers
        @books = books
    end
    end
    
    class LibraryCard
    attr_accessor :surname, :date_order, :date_issue, :name, :author, :genre
    def initialize(surname, date_order, date_issue, name, author, genre)
        @surname = surname
        @date_order = date_order
        @date_issue = date_issue
        @name = name
        @author = author
        @genre = genre
    end
    end
    
    def create_library_card(reader, book)
    LibraryCard.new(reader.surname, reader.date_order, reader.date_issue, book.name, book.author, book.genre)
    end
    
    def create_library(readers, books)
    library = Library.new(readers, books)
    library_cards = []
    library.readers.each do |reader|
        library.books.each do |book|
        library_cards << create_library_card(reader, book)
        end
    end
    library_cards
    end
    
    def find_shortest_period(library_cards)
    shortest_period = library_cards[0].date_issue - library_cards[0].date_order
    library_cards.each do |library_card|
        period = library_card.date_issue - library_card.date_order
        shortest_period = period if period < shortest_period
    end
    shortest_period
    end
    
    def find_unsatisfied_orders(library_cards)
    unsatisfied_orders = 0
    library_cards.each do |library_card|
        unsatisfied_orders += 1 if library_card.date_issue.nil?
    end
    unsatisfied_orders
    end
    
    def find_most_popular_reader(library_cards)
    most_popular_reader = library_cards[0].surname
    most_popular_reader_count = 0
    library_cards.each do |library_card|
        reader_count = 0
        library_cards.each do |library_card2|
        reader_count += 1 if library_card.surname == library_card2.surname
        end
        if reader_count > most_popular_reader_count
        most_popular_reader = library_card.surname
        most_popular_reader_count = reader_count
        end
    end
    most_popular_reader
    end

def find_most_popular_book(library_cards)
    most_popular_book = library_cards[0].name
    most_popular_book_count = 0
    library_cards.each do |library_card|
        book_count = 0
        library_cards.each do |library_card2|
        book_count += 1 if library_card.name == library_card2.name
        end
        if book_count > most_popular_book_count
        most_popular_book = library_card.name
        most_popular_book_count = book_count
        end
    end
    most_popular_book
    end

def find_most_popular_books(library_cards)
    most_popular_books = []
    library_cards.each do |library_card|
        book_count = 0
        library_cards.each do |library_card2|
        book_count += 1 if library_card.name == library_card2.name
        end
        most_popular_books << library_card.name if book_count == 3
    end
    most_popular_books
    end

def find_most_popular_readers(library_cards)
    most_popular_readers = []
    library_cards.each do |library_card|
        reader_count = 0
        library_cards.each do |library_card2|
        reader_count += 1 if library_card.surname == library_card2.surname
        end
        most_popular_readers << library_card.surname if reader_count == 3
    end
    most_popular_readers
    end

def main
    reader1 = Reader.new("Иванов", 1, 2)
    reader2 = Reader.new("Петров", 3, 4)
    reader3 = Reader.new("Сидоров", 5, 6)
    readers = [reader1, reader2, reader3]
    
    book1 = Book.new("dgdfg", "gg", "dfgw")
    book2 = Book.new("acva", "jj", "yy")
    book3 = Book.new("zcbbvnt", "ll", "oo")
    books = [book1, book2, book3]
    
    library_cards = create_library(readers, books)
    puts "Кратчайший период: #{find_shortest_period(library_cards)}"
    puts "Количество неудовлетворенных заказов: #{find_unsatisfied_orders(library_cards)}"
    puts "Самый популярный читатель: #{find_most_popular_reader(library_cards)}"
    puts "Самая популярная книга: #{find_most_popular_book(library_cards)}"
    puts "Самые популярные книги: #{find_most_popular_books(library_cards)}"
    puts "Самые популярные читатели: #{find_most_popular_readers(library_cards)}"
    end

