public class Main {

    public static void main(String[] args) {
        LRUTreap LRUTreap = new LRUTreap(5);

        LRUTreap.addToLRU(10);
        LRUTreap.addToLRU(20);
        LRUTreap.accessLRUNode(20);
        LRUTreap.addToLRU(30);
        LRUTreap.addToLRU(40);
        LRUTreap.accessLRUNode(40);
        LRUTreap.addToLRU(50);

        System.out.println("=== Initial BST Heap ===");
        System.out.println(LRUTreap.printTree());

        // Add one more to force eviction
        LRUTreap.addToLRU(60);
        System.out.println("=== After Eviction (add 60) ===");
        System.out.println(LRUTreap.printTree());

        // Add duplicate to increase priority
        System.out.println("=== Add Duplicate Timestamp (40) ===");
        LRUTreap.addToLRU(40);
        System.out.println(LRUTreap.printTree());

        // Insert in descending order
        System.out.println("=== Insert Descending Order ===");
        LRUTreap.addToLRU(55);
        LRUTreap.addToLRU(45);
        LRUTreap.addToLRU(35);
        System.out.println(LRUTreap.printTree());

        // Access existing node to increase priority
        System.out.println("=== Access Existing Node (60) ===");
        LRUTreap.accessLRUNode(60);
        System.out.println(LRUTreap.printTree());

        // Access non-existing node
        System.out.println("=== Access Non-Existent Node (99) ===");
        LRUTreap.accessLRUNode(99);
        System.out.println(LRUTreap.printTree());

        // Find least priority, oldest node
        System.out.println("=== Find Least Priority Oldest Node ===");
        LRUTreap.LRUNode least = LRUTreap.findLeastPriorityOldestLRUNode();
        if (least != null) {
            System.out.println("Least Priority Oldest Node: timestamp = " + least.timestamp + ", priority = " + least.priority);
        }

        // Evict specific node
        System.out.println("=== Evict Node (45) ===");
        LRUTreap.evictFromLRU(45);
        System.out.println(LRUTreap.printTree());

        LRUTreap LRUTreap2 = new LRUTreap(100);

        for(int i = 0; i < 101 ; i++){
            int num = (int) (Math.random()*45);
            LRUTreap2.addToLRU(num);
        }
        System.out.println(LRUTreap2.printTree());

        for(int i = 0; i < 30; i++){
            int num = (int) (Math.random()*45);
            LRUTreap2.evictFromLRU(num);
        }
        System.out.println(LRUTreap2.printTree());
    }
}
