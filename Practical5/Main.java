public class Main {
    public static void main(String[] args) {
        PrimaryHash folding = new PrimaryHash();
        SecondaryHash midSquare = new SecondaryHash();
        TertiaryHash divisionHash = new TertiaryHash(10); // Set divisor to 10 for testing

        PatientCatalogue<String> map = new PatientCatalogue(10, folding, midSquare, divisionHash);

        String[] testKeys = {
                "1234-5678-9012",
                "2468-1357-9024",
                "9876-5432-1098",
                "1111-2222-3333",
                "5555-6666-7777"
        };

        String[] testValues = {
                "Value A",
                "Value B",
                "Value C",
                "Value D",
                "Value E"
        };

        System.out.println("=== INSERTING DATA ===");
        for (int i = 0; i < testKeys.length; i++) {
            String key = testKeys[i];
            String value = testValues[i];

            map.addRecord(key, value);
        }
        System.out.println(map.printMap());

        System.out.println("=== Deleting DATA ===");
        System.out.println(map.deleteRecord("9876-5432-1098"));
        System.out.println(map.printMap());

        System.out.println("=== Deleting DATA ===");
        System.out.println(map.deleteRecord("1111-2222-3333") );
        System.out.println(map.printMap());
    }
}