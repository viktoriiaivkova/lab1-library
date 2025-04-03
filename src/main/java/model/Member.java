package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Member {
    private String memberId;
    private String name;
    private String surname;
    @JsonBackReference
    private List<BookRequestListener> membershipInLibraries = new ArrayList<BookRequestListener>();
    private List<Book> borrowedBooks = new ArrayList<Book>();


    public String getName() {
       return name;
    }
    public String getMemberId() {
        return memberId;
    }
    public String getSurname() {
        return surname;
    }
    public List<BookRequestListener> getMembershipInLibraries() {
        return membershipInLibraries;
    }
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    public void setBorrowedBooks(Book book) {
        borrowedBooks.add(book);
    }
    public Member( String name, String surname, Library library) {
        this.name = name;
        this.surname = surname;
        this.memberId = Integer.toString(System.identityHashCode(this));
        library.addMember(this);
        membershipInLibraries.add(library);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member member = (Member) obj;
        return memberId.equals(member.memberId);
    }

    @Override
    public int hashCode() {
        return memberId.hashCode();
    }

    public void requestBook(String bookTitle, Library library) {
        if(membershipInLibraries.contains(library)) {
            Book respond = library.onBookRequested(bookTitle, this);
            if (respond != null) {
                borrowedBooks.add(respond);
            } else{
                throw new SecurityException("Book request failed");
            }
        } else{
           throw new IllegalArgumentException("You are not a member of the mentioned library");
        }
    }

    public void returnBook(Book book, Library library) {
        if(membershipInLibraries.contains(library) && borrowedBooks.contains(book)) {
            if(library.onBookReturn(book)) {
                borrowedBooks.remove(book);
            }else{
                throw new SecurityException("Book return failed");
            }
        } else{
            throw new IllegalArgumentException("You are either not a member of the library or have not borrowed such a book");
        }
    }

    public String getMemberOverallInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Member ID: " + memberId + "\n");
        sb.append("Member Name: " + name + "\n");
        sb.append("Member Surname: " + surname + "\n");
        return sb.toString();
    }


}
