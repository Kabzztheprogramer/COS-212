public class Rook extends Chesspiece {

    public Rook() {
        super('R');
    }

    @Override
    public Chesspiece clone() {
        Rook newRook = new Rook();
        newRook.col = this.col ;
        newRook.row = this.row ;
        newRook.board = null ;
        return newRook ;
    }

    @Override
    public boolean canCapture() {
        return canCaptureRecursive(col ,row , 1,0)||canCaptureRecursive(col ,row , -1,0)||
        canCaptureRecursive(col ,row , 0,1)|| canCaptureRecursive(col ,row , 0,-1);
    }

    public boolean canCaptureRecursive(int checkrow, int checkcol, int rowmove, int colmove) {
        checkrow += rowmove;
        checkcol += colmove;
    
        // Fix: Stop recursion when out of bounds
        if (!board.inBounds(checkrow, checkcol)) { 
            return false; 
        }
    
        
        // If there is a piece at the position, return true
        if (board.getPieceAt(checkrow, checkcol) != null) {
            
            return true;
        }
    
        // Continue searching in the same direction
        return canCaptureRecursive(checkrow, checkcol, rowmove, colmove);
    }
}
