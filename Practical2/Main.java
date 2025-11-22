public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        Book Book1 = new Book("978045152493", "1984", "George Orwell");
        library.firstBook = Book1;
        library.returnBook(library.firstBook, "978006112008","To Kill a Mockingbird", "Harper Lee");
        
        library.returnBook(library.firstBook,"978067972020", "Slaughterhouse-Five", "Kurt Vonnegut");
        System.out.println(library.toString());
        library.returnBook(library.firstBook,"978055329698", "Dune", "Frank Herbert");
        // library.returnBook(library.firstBook,"978030727767", "The Road", "Cormac McCarthy");
        // library.returnBook(library.firstBook,"978034533968", "The Fellowship of the Ring", "J.R.R. Tolkien");
        // library.returnBook(library.firstBook,"978074327356", "The Great Gatsby", "F. Scott Fitzgerald");
        
        
     
        System.out.println( "\n"+ library.inorderPrint(library.firstBook) );
        System.out.println( "\n"+ library.preorderPrint(library.firstBook) );
        System.out.println( "\n"+ library.postorderPrint(library.firstBook) );

        System.out.println("\n" + "The lowest isbn book : " + library.findLowestISBN(library.firstBook).toString());
        System.out.println(library.checkAvailability(library.firstBook ,"978055329698"));
        System.out.println("\n"+"The height of the self "+ library.getShelfHeight(library.firstBook)+"\n");
        System.out.println("\n" + "Borrowed the lowest isbn book : " + library.findLowestISBN(library.firstBook).toString());
        library.borrowBook(Book1,"978055329698", "Dune", "Frank Herbert");




    }
    
}
