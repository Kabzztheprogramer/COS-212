public class TertiaryHash extends HashFunctions {
    private int divisor;

    public TertiaryHash(int divisor) {
       this.divisor = divisor ;
    }

    public int hash(String input) {

        if (input == null || input.isEmpty() || input.equals("-1")) {
            return -1;
        }

        int numericValue = Integer.parseInt(input);
        return Math.abs(numericValue % divisor);
    }
  
}