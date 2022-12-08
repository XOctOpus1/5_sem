#var4
# 4.Нотатки. Створити ієрархію нотатків різних типів (картинки, текст, список і т. д.). Передбачити  пошук
# нотатків у змісті, в заданому часовому діапазоні, тегам, сортування нотатків за часом останньої зміни,
# важливості, заголовку.

class Note
    attr_accessor :title, :body, :tags, :last_change_time
    def initialize(title, body, tags, last_change_time)
        @title = title
        @body = body
        @tags = tags
        @last_change_time = last_change_time
    end
    end

class TextNote < Note
    def initialize(title, body, tags, last_change_time)
        super(title, body, tags, last_change_time)
    end
    end

class ImageNote < Note
    attr_accessor :image
    def initialize(title, body, tags, last_change_time, image)
        super(title, body, tags, last_change_time)
        @image = image
    end
    end

class ListNote < Note
    attr_accessor :list
    def initialize(title, body, tags, last_change_time, list)
        super(title, body, tags, last_change_time)
        @list = list
    end
    end




class Notes
    attr_accessor :notes
    def initialize(notes)
        @notes = notes
    end
    def search_by_title(title)
        @notes.select { |note| note.title == title }
    end
    def search_by_tags(tags)
        @notes.select { |note| note.tags == tags }
    end
    def search_by_time_range(start_time, end_time)
        @notes.select { |note| note.last_change_time >= start_time && note.last_change_time <= end_time }
    end
    def sort_by_last_change_time
        @notes.sort_by { |note| note.last_change_time }
    end
    def sort_by_importance
        @notes.sort_by { |note| note.tags.count }
    end
    def sort_by_title
        @notes.sort_by { |note| note.title }
    end
    end

class TextNotes < Notes
    def initialize(notes)
        super(notes)
    end
    end

class ImageNotes < Notes
    def initialize(notes)
        super(notes)
    end
    end

class ListNotes < Notes
    def initialize(notes)
        super(notes)
    end
    end

class NoteFactory
    def self.create_note(type, title, body, tags, last_change_time, image, list)
        case type
        when :text
            TextNote.new(title, body, tags, last_change_time)
        when :image
            ImageNote.new(title, body, tags, last_change_time, image)
        when :list
            ListNote.new(title, body, tags, last_change_time, list)
        end
    end
    end

class NotesFactory
    def self.create_notes(type, notes)
        case type
        when :text
            TextNotes.new(notes)
        when :image
            ImageNotes.new(notes)
        when :list
            ListNotes.new(notes)
        end
    end
    end

class NoteBuilder
    attr_accessor :title, :body, :tags, :last_change_time, :image, :list
    def initialize
        @title = nil
        @body = nil
        @tags = nil
        @last_change_time = nil
        @image = nil
        @list = nil
    end
    def set_title(title)
        @title = title
    end
    def set_body(body)
        @body = body
    end
    def set_tags(tags)
        @tags = tags
    end
    def set_last_change_time(last_change_time)
        @last_change_time = last_change_time
    end
    def set_image(image)
        @image = image
    end
    def set_list(list)
        @list = list
    end
    def build(type)
        NoteFactory.create_note(type, @title, @body, @tags, @last_change_time, @image, @list)
    end
    end

class NotesBuilder
    attr_accessor :notes
    def initialize
        @notes = []
    end
    def add_note(note)
        @notes << note
    end
    def build(type)
        NotesFactory.create_notes(type, @notes)
    end
    end

class NoteDirector
    def initialize(builder)
        @builder = builder
    end
    def create_note
        @builder.build(:text)
    end
    end

class NotesDirector
    def initialize(builder)
        @builder = builder
    end
    def create_notes
        @builder.build(:text)
    end
    end

  
builder = NoteBuilder.new
builder.set_title('title')
builder.set_body('body')
builder.set_tags(['tag1', 'tag2'])
builder.set_last_change_time(Time.now)
director = NoteDirector.new(builder)
note = director.create_note
puts note.title
puts note.body
puts note.tags
puts note.last_change_time

builder = NotesBuilder.new
builder.add_note(Note.new('title1', 'body1', ['tag1', 'tag2'], Time.now))
builder.add_note(Note.new('title2', 'body2', ['tag1', 'tag2'], Time.now))
director = NotesDirector.new(builder)
notes = director.create_notes
puts notes.notes[0].title
puts notes.notes[0].body
puts notes.notes[0].tags
puts notes.notes[0].last_change_time
puts notes.notes[1].title
puts notes.notes[1].body
puts notes.notes[1].tags
puts notes.notes[1].last_change_time

puts notes.search_by_title('title1')[0].title
puts notes.search_by_tags(['tag1', 'tag2'])[0].title
puts notes.search_by_time_range(Time.now - 100, Time.now + 100)[0].title
puts notes.sort_by_last_change_time[0].title
puts notes.sort_by_importance[0].title
puts notes.sort_by_title[0].title

