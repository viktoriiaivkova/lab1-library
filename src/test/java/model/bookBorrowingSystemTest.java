package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class bookBorrowingSystemTest {
   private Library library;
   private Library library2;
   private Book book1;
   private Book book2;
   private Book book3;
   private Book book4;
   private Book book5;

   private Member member1;
   private Member member2;

   @BeforeEach
    public void setUp() {
           library = new Library("City Library");
           book1 = new Book("1984", "George Orwell", 1949, "123456789");
           book2 = new Book("Brave New World", "Aldous Huxley", 1932, "987654321");
           book3 = new Book("Fahrenheit 451", "Ray Bradbury", 1953, "5671891234");
           book4 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, "111222333");
           book5 = new Book("Moby-Dick", "Herman Melville", 1851, "444555666");
           book1 = new Book("1984", "George Orwell", 1949, "223456789");
           library.addBook(book1);
           library.addBook(book2);
           library.addBook(book3);
           library.addBook(book4);
           library.addBook(book5);
       }
       @Test
        void requestBook_ok(){
           Member member1 = new Member("Alice", "Smith", library);
           member1.requestBook("Brave New World", library);
           Assertions.assertTrue(member1.getBorrowedBooks().contains(book2));
           Assertions.assertFalse(book2.getIsAvailable());
           Assertions.assertSame(book2.getMemberId(), member1.getMemberId());
       }
        @Test
        void requestBook_failed_book(){
            Member member1 = new Member("Alice", "Smith", library);
            Assertions.assertThrows(SecurityException.class, () -> {member1.requestBook("No such book", library);});
        }
        @Test
        void requestBook_failed_member(){
            library2 = new Library("Test Library");
            Member member1 = new Member("Alice", "Smith", library2);
            Assertions.assertThrows(IllegalArgumentException.class, () -> {member1.requestBook("No such book", library);});
        }
        @Test
        void requestBook_failed_notAvailable(){
        Member member1 = new Member("Alice", "Smith", library);
        Member member2 = new Member("Bob", "Johnson", library);
        Assertions.assertThrows(SecurityException.class, () -> {member2.requestBook("Moby Dick", library);});
    }
        @Test
        void returnBook_ok(){
            Member member1 = new Member("Alice", "Smith", library);
            member1.requestBook("Brave New World", library);
            member1.returnBook(book2, library);
            Assertions.assertFalse(member1.getBorrowedBooks().contains(book2));
            Assertions.assertTrue(book2.getIsAvailable());
            Assertions.assertEquals("0", book2.getMemberId());
        }

        @Test
        void returnBook_failed_book(){
            Member member1 = new Member("Alice", "Smith", library);
            Assertions.assertThrows(IllegalArgumentException.class, () -> {member1.returnBook(book3, library);});
        }

        @Test
        void returnBook_failed_member(){
            library2 = new Library("Test Library");
            Member member1 = new Member("Alice", "Smith", library);
            library2.addMember(member1);
            member1.requestBook("Brave New World", library);
            Assertions.assertThrows(IllegalArgumentException.class, () -> {member1.returnBook(book2, library2);});
        }

       }




