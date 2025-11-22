public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Book Book1 = new Book("978045152493", "1984", "George Orwell");
        library.firstBook = Book1;
        library.returnBook(library.firstBook, "978006112008","To Kill a Mockingbird", "Harper Lee");
        System.out.println(library.toString());

        // Test Book
        Book testBook = new Book("978045152493", "1984", "George Orwell");
        Book testBook1 = new Book("", "1984", "George Orwell");
        Book testBook2 = new Book("978045152493", "", "George Orwell");
        Book testBook3 = new Book("978045152493", "1984", "");
        System.out.println(testBook.toString() + "\n");
        System.out.println(testBook1.toString() + "\n");
        System.out.println(testBook2.toString() + "\n");
        System.out.println(testBook3.toString() + "\n");

        //Tests if null
        System.out.println(library.checkAvailability(library.firstBook, "643267167334") + "\n");
        System.out.println(library.getShelfHeight(library.firstBook) + "\n");

        //Test returnBook/toString
        System.out.println(library.toString() + "\n");
        library.returnBook(library.firstBook, "643267167334", "Cat in a hat", "George Orwell");
        library.returnBook(library.firstBook, "978006112008", "To Kill a Mockingbird", "Harper Lee");
        System.out.println(library.toString() + "\n");
        library.returnBook(library.firstBook, "345256712533", "The Art of War", "Sun Tzu");
        System.out.println(library.toString() + "\n");

        // Test preorderprint
        System.out.println(library.preorderPrint(library.firstBook) + "\n");

        // Test inorderprint
        System.out.println(library.inorderPrint(library.firstBook) + "\n");
        
        // Test postorderprint
        System.out.println(library.postorderPrint(library.firstBook) + "\n");
        
        // Test findlowestisbn
        System.out.println(library.findLowestISBN(library.firstBook).toString() + "\n");

        // Test availability if not null
        System.out.println(library.checkAvailability(library.firstBook, "643267167334") + "\n");
        System.out.println(library.checkAvailability(library.firstBook, "621354252553") + "\n");
        System.out.println(library.checkAvailability(library.firstBook, "978006112008") + "\n");
        System.out.println(library.checkAvailability(library.firstBook, "345256712533") + "\n");

        // Test getShelfHeight
        System.out.println(library.getShelfHeight(library.firstBook) + "\n");
        library.returnBook(library.firstBook, "745256712533", "Book", "Book's author");
        System.out.println(library.getShelfHeight(library.firstBook) + "\n");
        library.returnBook(library.firstBook, "445256712533", "Book2", "Book2's author");

        // Test borrowBook
        System.out.println(library.toString() + "\n");
        library.borrowBook(library.firstBook, "445256712533", "Book2", "Book2's author");
        System.out.println(library.toString() + "\n");
        library.borrowBook(library.firstBook, "643267167334", "Cat in a hat", "George Orwell");
        System.out.println(library.toString() + "\n");
    }
}