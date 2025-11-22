public class Library {
    Book firstBook;

    public Book returnBook(Book firstBook, String isbn, String title, String author) {
        // implement this function
        Book newBook = new Book(isbn, title, author);
        if (this.firstBook == null) { // tree is empty;
            this.firstBook = newBook;
            return this.firstBook;
        }
        Book temp = this.firstBook;
        Book prev = null;
        while (temp != null) {
            prev = temp;
            if (newBook.compareTo(temp) < 0) {
                temp = temp.lowerISBN;
            } else if (newBook.compareTo(temp) > 0) {
                temp = temp.higherISBN;
            } else {
                return this.firstBook;
            }
        }
        if (newBook.compareTo(prev) < 0) {
            prev.lowerISBN = newBook;
        } else {
            prev.higherISBN = newBook;
        }

        return this.firstBook;
    }

    public Book borrowBook(Book firstBook, String isbn, String title, String author) {
        Book targetBook = new Book(isbn, title, author);
        Book previous = null;
        Book current = firstBook;
    
        while (current != null && targetBook.compareTo(current) != 0) {
            previous = current;
            if (targetBook.compareTo(current) < 0) {
                current = current.lowerISBN;
            } else {
                current = current.higherISBN;
            }
        }
    
        if (current == null) {
            return null;
        }
    
        if (current.lowerISBN == null && current.higherISBN == null) {
            if (previous == null) {
                firstBook = null;
            } else if (previous.lowerISBN == current) {
                previous.lowerISBN = null;
            } else {
                previous.higherISBN = null;
            }
        } else if (current.lowerISBN == null || current.higherISBN == null) {
            Book child = (current.lowerISBN != null) ? current.lowerISBN : current.higherISBN;
            if (previous == null) {
                firstBook = child;
            } else if (previous.lowerISBN == current) {
                previous.lowerISBN = child;
            } else {
                previous.higherISBN = child;
            }
        } else {
            Book replacementParent = current;
            Book successor = current.higherISBN;
    
            while (successor.lowerISBN != null) {
                replacementParent = successor;
                successor = successor.lowerISBN;
            }
    
            current.isbn = successor.isbn;
            current.title = successor.title;
            current.author = successor.author;
    
            if (replacementParent == current) {
                replacementParent.higherISBN = successor.higherISBN;
            } else {
                replacementParent.lowerISBN = successor.higherISBN;
            }
        }
    
        return firstBook;
    }
    
    

    public Book findLowestISBN(Book firstBook) {
        // implement this function
        if (firstBook != null) {
            while (firstBook.lowerISBN != null) {
                firstBook = firstBook.lowerISBN;
            }
        }
        return firstBook;
    }

    public boolean checkAvailability(Book firstBook, String isbn) {
        // implement this function
        Book Tbook  =  firstBook ;
        if(Tbook == null){
            return false ;
        }
        if (Tbook.isbn.equals(isbn)) {
            return true ;
        }
        if(Tbook.isbn.compareTo(isbn) > 0){
            return checkAvailability(Tbook.lowerISBN, isbn);
        }else{
            return checkAvailability(Tbook.higherISBN, isbn);
        }
    }

    public String inorderPrint(Book root) {
        // implement this function
        if(root == null){
          return "";
        }
        return inorderPrint(root.lowerISBN) +  
        "Book{ISBN: " + root.isbn + ", Title: " + root.title + ", Author: " + root.author + "}\n" + 
        inorderPrint(root.higherISBN);
    }

    public String preorderPrint(Book root) {
        // implement this function
        if(root == null){
            return "";
          }
          return "Book{ISBN: " + root.isbn + ", Title: " + root.title + ", Author: " + root.author + "}\n" +   
          inorderPrint(root.lowerISBN) +
          inorderPrint(root.higherISBN);

    }

    public String postorderPrint(Book root) {
        // implement this function
        if(root == null){
            return "";
          }
          return inorderPrint(root.lowerISBN) +  
          inorderPrint(root.higherISBN)+
          "Book{ISBN: " + root.isbn + ", Title: " + root.title + ", Author: " + root.author + "}\n" ;
    }

    public int getShelfHeight(Book firstBook) {
        // implement this function
        if (firstBook == null) {
            return 0;
        }

        int lHeight = getShelfHeight(firstBook.lowerISBN);
        int rHeight = getShelfHeight(firstBook.higherISBN);

        if (lHeight > rHeight) {
            return lHeight + 1;
        }
        else
        {
            return rHeight + 1;
        }
    }

    // Do not Change this function as it will negatively affect your marks
    @Override
    public String toString() {
        if (firstBook == null) {
            return "Library is empty.";
        }
        return firstBook.toString();
    }
}