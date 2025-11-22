public class Main {

    public static void main(String[] args) {
        

        Database db = new Database<>(5);
        for (int i = 20; i >= 1; i--) {
            db.addContainerRecord(i);
        }
        System.out.println(db.printTree());
        System.out.println(db.countContainerIDs());
        System.out.println(db.countRecords());
    }
    
}