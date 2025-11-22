public class Bishop extends Chesspiece {

    public Bishop() {
        super('B');
    }

    @Override
    public Chesspiece clone() {
        Bishop newBishop = new Bishop();
        newBishop.col = this.col;
        newBishop.row = this.row;
        newBishop.board = null;
        return newBishop;
    }

    @Override
    public boolean canCapture() {
        // Checks if there is another piece in either diagonal direction
        if (scanMajorDiagonal(this.row, this.col) != null || scanMinorDiagonal(this.row, this.col) != null) {
            return true;
        } 
        return false;
    }

    public Chesspiece searchTopLeft(int rowPos, int colPos) {
        // Stop recursion when moving out of bounds
        if (rowPos < 0 || colPos < 0) {
            return null;
        }

        // Check if there's a piece at this position (excluding the Bishop's current position)
        if (rowPos != this.row || colPos != this.col) {
            if (this.board.getPieceAt(rowPos, colPos) != null) {
                return this.board.getPieceAt(rowPos, colPos);
            }
        }

        // Continue searching diagonally upward to the left
        return searchTopLeft(rowPos - 1, colPos - 1);
    }
    
    public Chesspiece searchBottomRight(int rowPos, int colPos) {
        // Stop recursion when moving out of bounds
        if (rowPos >= this.board.getSize() || colPos >= this.board.getSize()) {
            return null;
        }

        // Check if there's a piece at this position (excluding the Bishop's current position)
        if (rowPos != this.row || colPos != this.col) {
            if (this.board.getPieceAt(rowPos, colPos) != null) {
                return this.board.getPieceAt(rowPos, colPos);
            }
        }

        // Continue searching diagonally downward to the right
        return searchBottomRight(rowPos + 1, colPos + 1);
    }

    public Chesspiece searchBottomLeft(int rowPos, int colPos) {
        // Stop recursion when moving out of bounds
        if (rowPos >= this.board.getSize() || colPos < 0) {
            return null;
        }

        // Check if there's a piece at this position (excluding the Bishop's current position)
        if (rowPos != this.row || colPos != this.col) {
            if (this.board.getPieceAt(rowPos, colPos) != null) {
                return this.board.getPieceAt(rowPos, colPos);
            }
        }

        // Continue searching diagonally downward to the left
        return searchBottomLeft(rowPos + 1, colPos - 1);
    }
    
    public Chesspiece searchTopRight(int rowPos, int colPos) {
        // Stop recursion when moving out of bounds
        if (rowPos < 0 || colPos >= this.board.getSize()) {
            return null;
        }

        // Check if there's a piece at this position (excluding the Bishop's current position)
        if (rowPos != this.row || colPos != this.col) {
            if (this.board.getPieceAt(rowPos, colPos) != null) {
                return this.board.getPieceAt(rowPos, colPos);
            }
        }

        // Continue searching diagonally upward to the right
        return searchTopRight(rowPos - 1, colPos + 1);
    }

    public Chesspiece scanMajorDiagonal(int rowPos, int colPos) {
        // Check diagonally to the top left for a piece
        Chesspiece foundPiece = searchTopLeft(rowPos - 1, colPos - 1);
        if (foundPiece != null) {
            return foundPiece;
        }

        // If no piece is found, check diagonally downward to the bottom right
        return searchBottomRight(rowPos + 1, colPos + 1);
    }

    public Chesspiece scanMinorDiagonal(int rowPos, int colPos) {
        // Check diagonally to the top right for a piece
        Chesspiece foundPiece = searchTopRight(rowPos - 1, colPos + 1);
        if (foundPiece != null) {
            return foundPiece;
        }

        // If no piece is found, check diagonally downward to the bottom left
        return searchBottomLeft(rowPos + 1, colPos - 1);
    }
}

