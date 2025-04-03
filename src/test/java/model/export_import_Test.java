package model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
public class export_import_Test {
    private Library library;
    private ObjectMapper mockObjectMapper;

    @BeforeEach
    void setUp() {
        library = new Library("Test Library");
        mockObjectMapper = mock(ObjectMapper.class);
        library.setObjectMapper(mockObjectMapper);
    }

    @Test
    void exportBooks() throws IOException {
        Book book1 = new Book("1984", "George Orwell", 1949, "123456789");
        Book book2 = new Book("Brave New World", "Aldous Huxley", 1932, "987654321");
        Book book3 = new Book("Fahrenheit 451", "Ray Bradbury", 1953, "5671891234");
        Book book4 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, "111222333");
        Book book5 = new Book("Moby-Dick", "Herman Melville", 1851, "444555666");
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);
        when(mockObjectMapper.writeValueAsString(library.getBooks())).thenReturn("[{\"title\":\"1984\",\"author\":\"George Orwell\",\"year\":1949,\"isbn\":\"123456789\"}," +
                "{\"title\":\"Brave New World\",\"author\":\"Aldous Huxley\",\"year\":1932,\"isbn\":\"987654321\"}," +
                "{\"title\":\"Fahrenheit 451\",\"author\":\"Ray Bradbury\",\"year\":1953,\"isbn\":\"5671891234\"}," +
                "{\"title\":\"The Hobbit\",\"author\":\"J.R.R. Tolkien\",\"year\":1937,\"isbn\":\"111222333\"}," +
                "{\"title\":\"Moby-Dick\",\"author\":\"Herman Melville\",\"year\":1851,\"isbn\":\"444555666\"}]");
        FileWriter mockWriter = mock(FileWriter.class);
        library.setFileWriterSupplierBooks(() -> mockWriter);
        library.exportBooks(false);
        verify(mockWriter, times(1)).write(anyString());
        verify(mockWriter, times(1)).close();
    }
    @Test
    void importBooks() throws IOException {
        List<Book> mockBooks = Arrays.asList(
                new Book("1984", "George Orwell", 1949, "123456789"),
                new Book("Moby-Dick", "Herman Melville", 1851, "444555666")
        );
        when(mockObjectMapper.readValue(any(File.class), ArgumentMatchers.<TypeReference<List<Book>>>any()))
                .thenReturn(mockBooks);
        Member mockMember = new Member("Test", "Member", library);
        library.importBooks();
        Assertions.assertTrue(library.getBooks().containsAll(mockBooks));
    }
    @Test
    void exportMembers() throws IOException {
        Member member1 = new Member("Alice", "Smith", library);
        Member member2 = new Member("Bob", "Johnson", library);

        when(mockObjectMapper.writeValueAsString(library.getMembers())).thenReturn("[{\"memberId\":\"1072601481\",\"name\":\"Alice\"," +
                "\"surname\":\"Smith\",\"borrowedBooks\":[],\"memberOverallInfo\":\"Member ID: 1072601481\\nMember Name: Alice\\" +
                "nMember Surname: Smith\\n\"},{\"memberId\":\"121295574\",\"name\":\"Bob\",\"surname\":\"Johnson\",\"borrowedBooks\":[]," +
                "\"memberOverallInfo\":\"Member ID: 121295574\\nMember Name: Bob\\nMember Surname: Johnson\\n\"}]");
        FileWriter mockWriter = mock(FileWriter.class);
        library.setFileWriterSupplierMembers(() -> mockWriter);
        library.exportMembers();
        verify(mockWriter, times(1)).write(anyString());
        verify(mockWriter, times(1)).close();
    }
    @Test
    void importMembers() throws IOException {
        List<Member> mockMembers = Arrays.asList(
                new Member("Alice", "Smith", library),
                new Member("Bob", "Johnson", library)
        );
        when(mockObjectMapper.readValue(any(File.class), ArgumentMatchers.<TypeReference<List<Member>>>any()))
                .thenReturn(mockMembers);
        library.importMembers();
        Assertions.assertTrue(library.getMembers().containsAll(mockMembers));
    }
}
