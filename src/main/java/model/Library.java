package model;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Library implements BookRequestListener  {
    private String name;
    private String id;
    private List<Book> books;
    @JsonManagedReference
    private List<Member> members = new ArrayList<Member>();
    private ObjectMapper d = new ObjectMapper();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Library lib = (Library) obj;
        return id.equals(lib.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public List<Book> getBooks() {
        return books;
    }
    public List<Member> getMembers() {
        return members;
    }
    public void setObjectMapper(ObjectMapper mapper) {
        this.d = mapper;
    }
    private Supplier<FileWriter> fileWriterSupplierBooks = () -> {
        try {
            return new FileWriter("C:\\Users\\vick\\IdeaProjects\\lab1_test\\src\\main\\resources\\data\\books.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public void setFileWriterSupplierBooks(Supplier<FileWriter> fileWriterSupplier) {
        this.fileWriterSupplierBooks = fileWriterSupplier;
    }
    private Supplier<FileWriter> fileWriterSupplierMembers = () -> {
        try {
            return new FileWriter("C:\\Users\\vick\\IdeaProjects\\lab1_test\\src\\main\\resources\\data\\members.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public void setFileWriterSupplierMembers(Supplier<FileWriter> fileWriterSupplier) {
        this.fileWriterSupplierMembers = fileWriterSupplier;
    }
    private Library() {}
    public Library(String Name) {
        books = new ArrayList<Book>();
        this.name = Name;
        this.id = Integer.toString(System.identityHashCode(this));
    }
    // writing data  to file
    public void exportBooks(boolean needsSort) throws IOException {
        if(needsSort) {
            books.sort(Comparator.comparing(Book::getTitle));
        }
        try (FileWriter booksWriter = fileWriterSupplierBooks.get()) {
            booksWriter.write(d.writeValueAsString(books));
        }
    }
    public void importBooks() throws IOException {
        List<Book> importedBooks = d.readValue(new File("C:\\Users\\vick\\IdeaProjects\\lab1_test\\src\\main\\resources\\data\\books.json"),
                new TypeReference<List<Book>>() {});
        for (Book book : importedBooks) {
            if (!books.contains(book)) {
                if (!book.getMemberId().equals("0") && searchForMember(book.getMemberId())!=null){
                    books.add(book);
                    searchForMember(book.getMemberId()).setBorrowedBooks(book);
                    continue;
                }
                books.add(book);
            }
        }
    }
    public void exportMembers() throws IOException {
        members.sort(Comparator.comparing(Member::getMemberId));
        try (FileWriter booksWriter = fileWriterSupplierMembers.get()) {
            booksWriter.write(d.writeValueAsString(members));
        }

    }
    public void importMembers() throws IOException {
        List<Member> importedMembers = d.readValue(new File("C:\\Users\\vick\\IdeaProjects\\lab1_test\\src\\main\\resources\\data\\members.json"),
                new TypeReference<List<Member>>() {});
        for (Member m : importedMembers) {
            if (!members.contains(m)) {
                members.add(m);
                }
            }
        }
    public void addBook(Book book){
        if(books.contains(book)){
            throw new IllegalArgumentException("Book already exists");
        }
        books.add(book);
    }
    public void removeBook(Book book){
        if(books.contains(book)){
            books.remove(book);
        } else {
            throw new IllegalArgumentException("Book is not in book list");
        }
    }
    public void addMember(Member member){
        if(members.contains(member)){
            throw new IllegalArgumentException("Member already exists");
        } else {
            members.add(member);
        }
    }
    public void removeMember(Member member){
        if(members.contains(member)){
            members.remove(member);
        } else {
            throw new IllegalArgumentException("Member is not in the member list");
        }
    }
    public Book searchForBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }
    public boolean strictSearchForBook(Book b) {
        for (Book book : books) {
            if (book.equals(b)) {
                return true;
            }
        }
        return false;
    }
    public Member searchForMember(String id) {
        for (Member member : members) {
            if (member.getMemberId().equals(id)) {
                return member;
            }
        }
        return null;
    }
    @Override
    public Book onBookRequested(String title, Member member) {
        Book book = searchForBook(title);

        if ( book != null) {
            if (book.getIsAvailable()) {
                book.setIsAvailable(false);
                book.setMemberId(member.getMemberId());
                return book;
            }
        }
        return null;

    }
    @Override
    public boolean onBookReturn(Book book){
       if (strictSearchForBook(book)) {
           book.setMemberId("0");
           book.setIsAvailable(true);
           return true;
       }
       return false;
    }

    public String getLibraryOverallInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Library ID: " + id + "\n");
        sb.append("Library Name: " + name + "\n");
        sb.append("\nLibrary Members: \n");
        for (Member member : members) {
            sb.append(member.getMemberOverallInfo() + "\n");
        }
        sb.append("\nBooks: \n");
        for(Book book : books) {
            sb.append(book.getBookOverallInfo() + "\n");
        }
        return sb.toString();
    }

}
