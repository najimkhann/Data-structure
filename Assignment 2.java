// Data Structure Assignment 2
// Name: Najim Khan
// Roll No: 2401420003
// Course: B.Tech CSE - Data Structures
// Course Code: ENCS205
// Semester: 3rd
// Session: 2025-26

import java.util.Scanner;

// --------------------------- Book Node ---------------------------
class Book {
    int bookID;
    String bookTitle;
    String authorName;
    String status;
    Book next;

    public Book(int id, String title, String author, String status) {
        this.bookID = id;
        this.bookTitle = title;
        this.authorName = author;
        this.status = status;
        this.next = null;
    }
}

// --------------------------- Linked List ---------------------------
class BookList {
    private Book head;

    // Insert Book
    public void insertBook(int id, String title, String author, String status) {
        Book newBook = new Book(id, title, author, status);
        if (head == null) {
            head = newBook;
        } else {
            Book temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newBook;
        }
        System.out.println("Book added successfully.");
    }

    // Delete Book
    public void deleteBook(int id) {
        if (head == null) {
            System.out.println("No books in the library.");
            return;
        }
        if (head.bookID == id) {
            head = head.next;
            System.out.println("Book deleted successfully.");
            return;
        }
        Book temp = head;
        while (temp.next != null && temp.next.bookID != id)
            temp = temp.next;
        if (temp.next == null) {
            System.out.println("Book not found.");
        } else {
            temp.next = temp.next.next;
            System.out.println("Book deleted successfully.");
        }
    }

    // Search Book
    public Book searchBook(int id) {
        Book temp = head;
        while (temp != null) {
            if (temp.bookID == id)
                return temp;
            temp = temp.next;
        }
        return null;
    }

    // Display Books
    public void displayBooks() {
        if (head == null) {
            System.out.println("No books available.");
            return;
        }
        Book temp = head;
        System.out.println("\n--- Current Books in Library ---");
        while (temp != null) {
            System.out.println("ID: " + temp.bookID + " | Title: " + temp.bookTitle + " | Author: " + temp.authorName + " | Status: " + temp.status);
            temp = temp.next;
        }
    }
}

// --------------------------- Stack Node ---------------------------
class Transaction {
    String action; // Issue / Return
    int bookID;

    public Transaction(String action, int bookID) {
        this.action = action;
        this.bookID = bookID;
    }
}

// --------------------------- Stack ---------------------------
class TransactionStack {
    private java.util.Stack<Transaction> stack = new java.util.Stack<>();

    public void push(String action, int bookID) {
        stack.push(new Transaction(action, bookID));
    }

    public Transaction pop() {
        if (stack.isEmpty())
            return null;
        return stack.pop();
    }

    public void viewTransactions() {
        if (stack.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : stack) {
            System.out.println(t.action + " Book ID: " + t.bookID);
        }
    }
}

// --------------------------- Management System ---------------------------
class LibrarySystem {
    BookList bookList = new BookList();
    TransactionStack transactionStack = new TransactionStack();

    public void issueBook(int id) {
        Book b = bookList.searchBook(id);
        if (b == null) {
            System.out.println("Book not found!");
            return;
        }
        if (b.status.equalsIgnoreCase("Issued")) {
            System.out.println("Book already issued!");
            return;
        }
        b.status = "Issued";
        transactionStack.push("Issue", id);
        System.out.println("Book issued successfully.");
    }

    public void returnBook(int id) {
        Book b = bookList.searchBook(id);
        if (b == null) {
            System.out.println("Book not found!");
            return;
        }
        if (b.status.equalsIgnoreCase("Available")) {
            System.out.println("Book already available!");
            return;
        }
        b.status = "Available";
        transactionStack.push("Return", id);
        System.out.println("Book returned successfully.");
    }

    public void undoTransaction() {
        Transaction last = transactionStack.pop();
        if (last == null) {
            System.out.println("No transaction to undo.");
            return;
        }
        Book b = bookList.searchBook(last.bookID);
        if (b == null) {
            System.out.println("Book record not found.");
            return;
        }
        if (last.action.equalsIgnoreCase("Issue"))
            b.status = "Available";
        else if (last.action.equalsIgnoreCase("Return"))
            b.status = "Issued";

        System.out.println("Last transaction undone successfully.");
    }
}

// --------------------------- Main ---------------------------
public class LibraryBookManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibrarySystem system = new LibrarySystem();

        while (true) {
            System.out.println("\n--- Library Book Management System ---");
            System.out.println("1. Insert Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Search Book");
            System.out.println("4. Display Books");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Undo Last Transaction");
            System.out.println("8. View Transactions");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    system.bookList.insertBook(id, title, author, "Available");
                    break;
                case 2:
                    System.out.print("Enter Book ID to delete: ");
                    system.bookList.deleteBook(sc.nextInt());
                    break;
                case 3:
                    System.out.print("Enter Book ID to search: ");
                    Book b = system.bookList.searchBook(sc.nextInt());
                    if (b != null)
                        System.out.println("Found -> " + b.bookTitle + " by " + b.authorName + " (" + b.status + ")");
                    else
                        System.out.println("Book not found.");
                    break;
                case 4:
                    system.bookList.displayBooks();
                    break;
                case 5:
                    System.out.print("Enter Book ID to issue: ");
                    system.issueBook(sc.nextInt());
                    break;
                case 6:
                    System.out.print("Enter Book ID to return: ");
                    system.returnBook(sc.nextInt());
                    break;
                case 7:
                    system.undoTransaction();
                    break;
                case 8:
                    system.transactionStack.viewTransactions();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
