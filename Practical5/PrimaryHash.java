public class PrimaryHash extends HashFunctions {
    public PrimaryHash() {
    }

    public int hash(String input) {
        if (input == null || input.isEmpty() ||!input.matches("\\d{4}-\\d{4}-\\d{4}") || input.equals("")) {
            return -1;
        }
         String parts[] = input.split("-") ;

        String firstPart = parts[0]; 
        String middlePart = parts[1]; 
        String lastPart = parts[2]; 
        
        String reversedMiddlePart = new StringBuilder(middlePart).reverse().toString();
        if(firstPart.isEmpty()||reversedMiddlePart.isEmpty() || lastPart.isEmpty()  ){
            return -1 ;
        }
        int firstNum = Integer.parseInt(firstPart);
        int middleNum = Integer.parseInt(reversedMiddlePart);
        int lastNum = Integer.parseInt(lastPart);

        
        return firstNum + middleNum + lastNum;
    }
}