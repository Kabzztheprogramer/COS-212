// Output for this Main is given in output.txt
public class Main {
    public static void main(String[] args) {
        Chessboard board1 = new Chessboard(4, new Queen());
        System.out.println(board1.maxNumPieces());
        System.out.println("======================================");
        Chessboard board2 = new Chessboard("initialState.txt");
        System.out.println(board2.maxNumPieces());

        System.out.println("======================================");

        Chessboard board3 = new Chessboard(4, new Bishop());
        System.out.println(board3.maxNumPieces());
        System.out.println("======================================");
         
    }
}
