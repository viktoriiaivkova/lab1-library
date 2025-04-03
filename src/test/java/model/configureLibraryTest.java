package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class configureLibraryTest {
    private Library lib;
    private Library lib2;
    @BeforeEach
    void setUp() throws Exception {
        System.out.println("setUpLibraryTest is here");
         lib = new Library("Test Library");
    }
    @Test
     void addBook() {
        Book book =  new Book("1984", "George Orwell", 1949, "123456789");
        lib.addBook(book);
        Assertions.assertTrue(lib.getBooks().contains(book));
    }
    @Test
    void addBook_duplicateBook() {
        Book book =  new Book("1984", "George Orwell", 1949, "123456789");
        lib.addBook(book);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {lib.addBook(book);});
    }
    @Test
    void removeBook() {
        Book book =  new Book("1984", "George Orwell", 1949, "123456789");
        lib.addBook(book);
        lib.removeBook(book);
        Assertions.assertFalse(lib.getBooks().contains(book));
    }
    @Test
    void removeBook_notInLibrary() {
        Book book =  new Book("1984", "George Orwell", 1949, "123456789");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {lib.removeBook(book);});
    }

    @Test
    void addMember() {
        Member member = new Member("Alice", "Smith", lib);
        Assertions.assertTrue(lib.getMembers().contains(member));
    }
    @Test
    void addMember_duplicateMember() {
        Member member = new Member("Alice", "Smith", lib);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {lib.addMember(member);});
    }
    @Test
    void removeMember() {
        Member member = new Member("Alice", "Smith", lib);
        lib.removeMember(member);
        Assertions.assertFalse(lib.getMembers().contains(member));
    }

    @Test
    void removeMember_notInLibrary() {
        lib2= new Library("Test2 Library");
        Member member = new Member("Alice", "Smith", lib2);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {lib.removeMember(member);});
    }

}
