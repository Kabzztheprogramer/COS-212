public abstract class Chesspiece {
    protected char symbol;
    protected int row;
    protected int col;
    protected Chessboard board;

    public Chesspiece(char symbol) {
        this.symbol = symbol;
        this.row = -1;
        this.col = -1;
        this.board = null ;
    }

    public abstract Chesspiece clone();

    public String toString() {
        return symbol + "[" + row + "," + col + "]";
    }

    public char getSymbol() {
        return symbol ;
    }

    public void placePiece(Chessboard board, int row, int col) {
        this.board = board ;
        this.col = col ;
        this.row = row ;
    }

    public void removePiece() {
        this.row = -1;
        this.col = -1;
        board = null;
    }

    public abstract boolean canCapture();

}
