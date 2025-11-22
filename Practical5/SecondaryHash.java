public class SecondaryHash extends HashFunctions {

    public SecondaryHash() {
    }


    public int hash(String input) {

        if (input == null || input.isEmpty() || input.equals("-1")) {
            return -1;
        }

        int value = (int) Math.pow(Integer.parseInt(input),2);
        char[] myArray = Integer.toString(value).toCharArray(); 
        String midvalue = "";
        int Spostion = (myArray.length-4)/2 ;
        for(int i = Spostion ; i < Spostion+4 ; i++){
            midvalue += myArray[i] ;
        }
        
        if(midvalue.isEmpty()){
            return -1 ;
        }
        return Integer.parseInt(midvalue);
    }
}