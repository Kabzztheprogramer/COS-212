public class Main {
    public static void main(String[] args) {
        // Initialize system with 2 cities
        MetaCitySystem system = new MetaCitySystem(2);
        
        // 1. Test City and District Creation
        System.out.println("=== PHASE 1: City/District Setup ===");
        system.addCity(0, "Gotham", 3);
        system.addCity(1, "Metropolis", 2);
        
        // Add districts to Gotham
        system.getCity(0).addDistrict(0, "Downtown");
        system.getCity(0).addDistrict(1, "Uptown");
        system.getCity(0).addDistrict(2, "Industrial Zone");
        
        // Add districts to Metropolis
        system.getCity(1).addDistrict(0, "Business District");
        system.getCity(1).addDistrict(1, "Residential Sector");
        
        System.out.println(system.printSystem());
        
        // 2. Test Intra-City Utility Networks
        System.out.println("\n=== PHASE 2: Intra-City Connections ===");
        // Gotham power grid (fully connected)
        system.getCity(0).addUtilityConnection("POWER", 0, 1, 50);
        system.getCity(0).addUtilityConnection("POWER", 1, 2, 30);
        
        // Metropolis water system
        system.getCity(1).addUtilityConnection("WATER", 0, 1, 20);
        
        // Gotham transportation (one-way)
        system.getCity(0).addUtilityConnection("TRANSPORTATION", 0, 1, 15);
        system.getCity(0).addUtilityConnection("TRANSPORTATION", 1, 2, 10);
        
        System.out.println("Intra-city networks created:");
        System.out.println(system.getCity(0).printCity());
        
        // 3. Test Inter-City Connections
        System.out.println("\n=== PHASE 3: Inter-City Connections ===");
        system.getInterCityNetwork().addConnection(
            0, // Gotham
            1, // Metropolis
            2, // Gotham's Industrial Zone
            0, // Metropolis' Business District
            75 // Power line capacity
        );
        
        System.out.println("Inter-city connections:");
        System.out.println(system.getInterCityNetwork().printConnections(system.cities));
        
        // 4. Test Power Grid Analysis
        System.out.println("\n=== PHASE 4: Power Grid Analysis ===");
        System.out.println("Is power grid fully connected? " + 
            (system.isPowerGridConnected() ? "YES" : "NO"));
        
        System.out.println("Unreachable districts:");
        System.out.println(system.printUnreachableDistricts());
        
        // 5. Test Advanced Functionality
        System.out.println("\n=== PHASE 5: Advanced Tests ===");
        // Test blocked districts in DFS
        boolean[] blocked = {false, true, false}; // Block Uptown
        System.out.println("DFS with blocked districts:");
        System.out.println(system.getCity(0).dfsTransportWithBlocks(0, blocked));
        
        // Test Dijkstra's algorithm
        System.out.println("Shortest path times from Gotham Downtown:");
        System.out.println(MetaCity.printDistances(system.getCity(0), 0));
        
        // Test city coloring
        System.out.println("Intercity coloring:");
        System.out.println(system.getInterCityNetwork().colorIntercityWithBrelaz(system.cities));

        // extra testing
        System.out.println("\n=== EXTRA TESTING ===");

        // Test reverse inter-city connection
        system.getInterCityNetwork().addConnection(1, 0, 1, 1, 60);
        System.out.println("Added reverse inter-city connection:");
        System.out.println(system.getInterCityNetwork().printConnections(system.cities));

        // Test self-loop in Gotham power network
        system.getCity(0).addUtilityConnection("POWER", 2, 2, 99);
        System.out.println("Added self-loop on Gotham's Industrial Zone in POWER network:");
        System.out.println(system.getCity(0).getUtilityNetwork("POWER").printNetwork());

        // Re-run power grid analysis after additional connections
        System.out.println("Re-checking power grid connectivity after new links:");
        System.out.println("Is power grid fully connected? " + 
            (system.isPowerGridConnected() ? "YES" : "NO"));
        System.out.println(system.printUnreachableDistricts());

        // Test full DFS with no blocks
        boolean[] noBlocks = {false, false, false};
        System.out.println("DFS traversal (no blocks):");
        System.out.println(system.getCity(0).dfsTransportWithBlocks(0, noBlocks));

        // Test Dijkstra from isolated district
        System.out.println("Shortest path from Gotham Industrial Zone:");
        System.out.println(MetaCity.printDistances(system.getCity(0), 2));
    }

}
