public class Book implements Comparable<Book> {

    public String isbn;
    public String title;
    public String author;
    Book lowerISBN;
    Book higherISBN;

    public Book(String isbn, String title, String author) {
        // Implement this function
        this.title = title ;
        this.author = author ;
        this.isbn = isbn;
        lowerISBN = null;
        higherISBN = null;
    }

    // Do not Change this function as it will negatively affect your marks
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book{ISBN: ").append(isbn).append(", Title: ").append(title).append(", Author: ").append(author)
                .append("}");
        if (lowerISBN != null) {
            sb.append("\n").append(lowerISBN.toString());
        }
        if (higherISBN != null) {
            sb.append("\n").append(higherISBN.toString());
        }
        return sb.toString();
    }

    // Do not Change this function as it will negatively affect your marks
    @Override
    public int compareTo(Book other) {
        return this.isbn.compareTo(other.isbn);
    }

}
