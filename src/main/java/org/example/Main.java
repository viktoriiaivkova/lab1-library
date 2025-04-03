package org.example;
import model.Book;
import model.Library;
import model.Member;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main  {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library("City Library");

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Add Member");
            System.out.println("4. Remove Member");
            System.out.println("5. Request Book");
            System.out.println("6. Return Book");
            System.out.println("7. Show Library Info");
            System.out.println("8. Export Data");
            System.out.println("9. Import Data");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter year of publication: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    Book tempBook = new Book(title, author, year, isbn);
                    library.addBook(tempBook);
                    System.out.println("Book with ID "+tempBook.getId()+ " has been added successfully.");
                    break;
                case 2:
                    System.out.print("Enter book title to remove: ");
                    String bookToRemove = scanner.nextLine();
                    Book book = library.searchForBook(bookToRemove);
                    if (book != null) {
                        library.removeBook(book);
                        System.out.println("Book has been removed successfully.");
                    } else {
                        System.out.println("Book is not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter member first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter member last name: ");
                    String lastName = scanner.nextLine();
                    Member member = new Member(firstName, lastName, library);
                    System.out.println("Member added successfully.");
                    break;
                case 4:
                    System.out.print("Enter member ID to remove: ");
                    String memberId = scanner.nextLine();
                    Member foundMember = library.searchForMember(memberId);
                    if (foundMember != null) {
                        library.removeMember(foundMember);
                        System.out.println("Member removed successfully.");
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter member ID: ");
                    String borrowerId = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String requestTitle = scanner.nextLine();
                    Member requestingMember = library.searchForMember(borrowerId);
                    if (requestingMember != null) {
                        try {
                            requestingMember.requestBook(requestTitle, library);
                            System.out.println("Book borrowed successfully.");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;
                case 6:
                    System.out.print("Enter member ID: ");
                    String returnerId = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String returnTitle = scanner.nextLine();
                    Member returningMember = library.searchForMember(returnerId);
                    if (returningMember != null) {
                        Book returningBook = library.onBookRequested(returnTitle, returningMember);
                        if (returningBook != null) {
                            returningMember.returnBook(returningBook, library);
                            System.out.println("Book returned successfully.");
                        } else {
                            System.out.println("Book not found or not borrowed.");
                        }
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;
                case 7:
                    System.out.println(library.getLibraryOverallInfo());
                    break;
                case 8:
                    try {
                        library.exportBooks(true);
                        library.exportMembers();
                        System.out.println("Data exported successfully.");
                    } catch (IOException e) {
                        System.out.println("Error exporting data: " + e.getMessage());
                    }
                    break;
                case 9:
                    try {
                        library.importBooks();
                        library.importMembers();
                        System.out.println("Data imported successfully.");
                    } catch (IOException e) {
                        System.out.println("Error importing data: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}




