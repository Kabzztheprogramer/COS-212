import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Chessboard {
    private int size;
    private Chesspiece pieceType;
    private Chesspiece[][] board;
    private int numPieces;

    public Chessboard(int size, Chesspiece pieceType) {

        if(size >= 0 && size < 5){
            this.size = size ;
        }else{
            this.size = 0;
        }
        this.pieceType = pieceType ;
        board = new Chesspiece[this.size][this.size];
    }

    public Chessboard(String filename) {
        File file = new File(filename);
        try (Scanner scanner = new Scanner(file)) {
            readFileRecursive(scanner, 0); 
        } catch (FileNotFoundException e) {
            System.out.println("File not found! Initializing empty board...");
            this.size = 0;
            pieceType = new Queen();
        }
    }
    
    private void readFileRecursive(Scanner scanner, int lineNumber) {
        if (!scanner.hasNextLine()) {
            return; 
        }
    
        String line = scanner.nextLine();
    
        if (lineNumber == 0) {
            
            int boardSize = Integer.parseInt(line);
            this.size = (boardSize >= 0 && boardSize < 5) ? boardSize : 0;
            this.board = new Chesspiece[this.size][this.size];
    
        } else if (lineNumber == 1) {
            if (line.equals("Bishop")) {
                pieceType = new Bishop();
            } else if (line.equals("Rook")) {
                pieceType = new Rook();
            } else {
                pieceType = new Queen();
            }
    
        } else {
            // Third line onwards: Read piece positions
            String[] parts = line.split(" ");
            if (parts.length == 2) {
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                placePiece(row, col);
            }
        }
    
        readFileRecursive(scanner, lineNumber + 1); 
    }
    
    public Chessboard clone() {
        Chessboard newChessboard = new Chessboard(this.size,pieceType.clone());
        newChessboard.numPieces = this.numPieces;

        newChessboard.board = new Chesspiece[this.size][this.size];
        clonePieces(this,newChessboard,0,0);
        return newChessboard ;
    }
    public void clonePieces(Chessboard oldChessboard , Chessboard newChessboard , int newRow , int newCol){
        if (newRow >= size) return;

        if (newCol >= size) { 
            clonePieces(oldChessboard,newChessboard, newRow + 1, 0); 
            return;
        }
    
        if (board[newRow][newCol] != null) {
            newChessboard.board[newRow][newCol] = oldChessboard.board[newRow][newCol].clone(); 
        }else{
            newChessboard.board[newRow][newCol] = null;
        }
    
        clonePieces(oldChessboard,newChessboard, newRow, newCol + 1);
    }
    public Boolean inBounds(int row , int col){
        return (row >= 0 && row < size && col >= 0 && col < size) ;
    }

    @Override
    public String toString() {
        return pieceType.toString() + "\n" + toStringRowHelper(0) + numPieces + " pieces";
    }

    public String toStringRowHelper(int row) {
        if (row == size) {
            return "";
        }
        return toStringColHelper(row, 0) + "\n" + toStringRowHelper(row + 1);
    }

    public String toStringColHelper(int row, int col) {
        if (col == size) {
            return "";
        }
        return (board[row][col] == null ? "-" : board[row][col].getSymbol()) + toStringColHelper(row, col + 1);
    }

    public int getSize() {
        return size ;
    }

    public Chesspiece getPieceAt(int row, int col) {
            if(!inBounds(row, col)){
                return null;
            }
            return board[row][col];
        
    }

    public boolean placePiece(int row, int col) {

        
        if(inBounds(row, col) == false ){
            return false ;
        }

        Chesspiece newPiece = pieceType.clone();
        if(getPieceAt(row, col) != null){
            return false;
        }
        newPiece.placePiece(this, row, col);
        board[row][col] = newPiece;
        if (board[row][col].canCapture() == true) {
            board[row][col] = null ;
            return false ;
        }
        ++numPieces ;
        return true ;
    }

    public Chessboard maxNumPieces() {
        return RmaxNumPieces(this.clone(),0,0);
    }
    
    public Chessboard RmaxNumPieces(Chessboard board , int row , int col) {
        
        
        if(row >= board.size){
            
            return board ;
        }
      
        if(col >= board.size){
            return RmaxNumPieces(board, row+1, 0);
        }

        Chessboard currentBest = board ;
        
        if(board.getPieceAt(row, col)== null){
            Chessboard Bclone = board.clone() ;

            if(Bclone.placePiece(row, col) == true){

                Chessboard NewBoard = RmaxNumPieces(Bclone, 0, 0); 
                if(currentBest.numPieces < NewBoard.numPieces){
                    currentBest = NewBoard ;
                }
            }

        }

        Chessboard nextSolution =  RmaxNumPieces(board , row , col+1) ;
        if(currentBest.numPieces < nextSolution.numPieces){
            return nextSolution;
        }

        return currentBest ;
        
    }
}
