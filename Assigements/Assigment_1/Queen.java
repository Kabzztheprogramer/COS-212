public class Queen extends Chesspiece {

    public Queen() {
        super('Q');
    }

    @Override
    public Chesspiece clone() {
        Queen newQueen = new Queen();
        newQueen.col = this.col ;
        newQueen.row = this.row ;
        newQueen.board = null ;
        return newQueen ;
    }

    @Override
    public boolean canCapture() {
        if (scanMajorDiagonal(this.row, this.col) != null || scanMinorDiagonal(this.row, this.col) != null ||
            scanRow(this.row, this.col) != null || scanColumn(this.row, this.col) != null) {
            return true;
        } 
        return false;
    }

    public Chesspiece searchTopLeft(int rowPos, int colPos) {
        if (rowPos < 0 || colPos < 0) return null;
        if (this.board.getPieceAt(rowPos, colPos) != null) return this.board.getPieceAt(rowPos, colPos);
        return searchTopLeft(rowPos - 1, colPos - 1);
    }

    public Chesspiece searchBottomRight(int rowPos, int colPos) {
        if (rowPos >= this.board.getSize() || colPos >= this.board.getSize()) return null;
        if (this.board.getPieceAt(rowPos, colPos) != null) return this.board.getPieceAt(rowPos, colPos);
        return searchBottomRight(rowPos + 1, colPos + 1);
    }

    public Chesspiece searchBottomLeft(int rowPos, int colPos) {
        if (rowPos >= this.board.getSize() || colPos < 0) return null;
        if (this.board.getPieceAt(rowPos, colPos) != null) return this.board.getPieceAt(rowPos, colPos);
        return searchBottomLeft(rowPos + 1, colPos - 1);
    }

    public Chesspiece searchTopRight(int rowPos, int colPos) {
        if (rowPos < 0 || colPos >= this.board.getSize()) return null;
        if (this.board.getPieceAt(rowPos, colPos) != null) return this.board.getPieceAt(rowPos, colPos);
        return searchTopRight(rowPos - 1, colPos + 1);
    }

    public Chesspiece scanMajorDiagonal(int rowPos, int colPos) {
        Chesspiece foundPiece = searchTopLeft(rowPos - 1, colPos - 1);
        if (foundPiece != null) return foundPiece;
        return searchBottomRight(rowPos + 1, colPos + 1);
    }

    public Chesspiece scanMinorDiagonal(int rowPos, int colPos) {
        Chesspiece foundPiece = searchTopRight(rowPos - 1, colPos + 1);
        if (foundPiece != null) return foundPiece;
        return searchBottomLeft(rowPos + 1, colPos - 1);
    }

    public Chesspiece searchLeft(int rowPos, int colPos) {
        if (colPos < 0) return null;
        if (this.board.getPieceAt(rowPos, colPos) != null) return this.board.getPieceAt(rowPos, colPos);
        return searchLeft(rowPos, colPos - 1);
    }

    public Chesspiece searchRight(int rowPos, int colPos) {
        if (colPos >= this.board.getSize()) return null;
        if (this.board.getPieceAt(rowPos, colPos) != null) return this.board.getPieceAt(rowPos, colPos);
        return searchRight(rowPos, colPos + 1);
    }

    public Chesspiece searchUp(int rowPos, int colPos) {
        if (rowPos < 0) return null;
        if (this.board.getPieceAt(rowPos, colPos) != null) return this.board.getPieceAt(rowPos, colPos);
        return searchUp(rowPos - 1, colPos);
    }

    public Chesspiece searchDown(int rowPos, int colPos) {
        if (rowPos >= this.board.getSize()) return null;
        if (this.board.getPieceAt(rowPos, colPos) != null) return this.board.getPieceAt(rowPos, colPos);
        return searchDown(rowPos + 1, colPos);
    }

    public Chesspiece scanRow(int rowPos, int colPos) {
        Chesspiece foundPiece = searchLeft(rowPos, colPos - 1);
        if (foundPiece != null) return foundPiece;
        return searchRight(rowPos, colPos + 1);
    }

    public Chesspiece scanColumn(int rowPos, int colPos) {
        Chesspiece foundPiece = searchUp(rowPos - 1, colPos);
        if (foundPiece != null) return foundPiece;
        return searchDown(rowPos + 1, colPos);
    }  
}

