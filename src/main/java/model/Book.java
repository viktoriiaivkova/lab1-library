package model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Book {
    private String title;
    private String author;
    private int yearOfPublication;
    private String isbn;
    private String id;
    private boolean isAvailable;
    private String memberId;

    public Book(String title, String author, int yearOfPublication, String isbn) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.isbn = isbn;
        this.id = Integer.toString(System.identityHashCode(this));
        this.isAvailable = true;
        this.memberId = "0";
    }


    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getYearOfPublication() {
        return yearOfPublication;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getId() {
        return id;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String newMemberId) {
            this.memberId = newMemberId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @JsonIgnore
    public String getBookOverallInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Book ID: " + id + "\n");
        sb.append("Title: " + title + "\n");
        sb.append("Author: " + author + "\n");
        sb.append("Year of publication: " + yearOfPublication + "\n");
        sb.append("Isbn: " + isbn + "\n");
        sb.append("Available: " + isAvailable + "\n");
        sb.append("Member ID: " + memberId + "\n");
        return sb.toString();

    }
}
