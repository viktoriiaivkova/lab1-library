package model;

public interface BookRequestListener {
    Book onBookRequested(String title,Member member);
    boolean onBookReturn(Book book);
}
