import java.util.Arrays;

public class InterCityNetwork {

    public static class Node {
        int targetCity;
        int sourceDistrict;
        int targetDistrict;
        int weight;
        Node next;

        public Node(int targetCity, int sourceDistrict, int targetDistrict, int weight) {
            this.targetCity = targetCity;
            this.sourceDistrict = sourceDistrict;
            this.targetDistrict = targetDistrict;
            this.weight = weight;
            this.next = null;
        }

        public String toString() {
            return String.format("Node{targetCity=%d, sourceDistrict=%d, targetDistrict=%d, weight=%d}",
                    targetCity, sourceDistrict, targetDistrict, weight);
        }
    }

    private Node[] adjacencyList;

    public InterCityNetwork(int numCities) {
        if (numCities < 0) {
            throw new IllegalArgumentException("Number of cities must be greater than or equal to 0.");
        }
        this.adjacencyList = new Node[numCities];
    }

    public void addConnection(int fromCity, int toCity, int fromDistrict, int toDistrict, int weight) {
        if (adjacencyList == null) {
            throw new IllegalStateException("Adjacency list not initialized.");
        }

        if (fromCity < 0 || fromCity >= adjacencyList.length ||
            toCity < 0 || toCity >= adjacencyList.length) {
            throw new IllegalArgumentException("Invalid city indices.");
        }

        if (fromDistrict < 0 || toDistrict < 0) {
            throw new IllegalArgumentException("Invalid district indices.");
        }

        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }

        Node newNode1 = new Node(toCity, fromDistrict, toDistrict, weight);
        newNode1.next = adjacencyList[fromCity];
        adjacencyList[fromCity] = newNode1;

        Node newNode2 = new Node(fromCity, toDistrict, fromDistrict, weight);
        newNode2.next = adjacencyList[toCity];
        adjacencyList[toCity] = newNode2;
    }

    public String colorIntercityWithBrelaz(MetaCity[] cities) {
        if (cities == null || cities.length == 0) {
            return "Error: No cities to color\n";
        }

        for (int i = 0; i < cities.length; i++) {
            if (cities[i] == null) {
                return "Error: Null city found at index " + i + "\n";
            }
        }

        if (adjacencyList == null) {
            return "Error: Adjacency list not initialized\n";
        }

        int numCities = cities.length;
        int actualGraphSize = Math.min(numCities, adjacencyList.length);

        int[] colors = new int[numCities];
        int[] saturation = new int[numCities];
        int[] uncoloredNeighbors = new int[numCities];
        boolean[] colored = new boolean[numCities];

        Arrays.fill(colors, -1);
        Arrays.fill(saturation, 0);
        Arrays.fill(uncoloredNeighbors, 0);
        Arrays.fill(colored, false);

        // Count initial uncolored neighbors
        for (int i = 0; i < actualGraphSize; i++) {
            if (adjacencyList[i] != null) {
                Node current = adjacencyList[i];
                while (current != null) {
                    if (current.targetCity >= 0 && current.targetCity < numCities) {
                        uncoloredNeighbors[i]++;
                    }
                    current = current.next;
                }
            }
        }

        // ✅ Properly check if any connections exist
        boolean hasConnections = false;
        for (int i = 0; i < actualGraphSize && !hasConnections; i++) {
            Node current = adjacencyList[i];
            while (current != null) {
                hasConnections = true;
                break;
            }
        }

        if (!hasConnections) {
            Arrays.fill(colors, 0); // Color all with color 0
            return printCompactFinalColoring(cities, colors, saturation, uncoloredNeighbors);
        }

        // DSATUR Algorithm
        for (int step = 0; step < numCities; step++) {
            int selectedCity = -1;
            int maxSaturation = -1;
            int maxUncoloredNeighbors = -1;

            for (int i = 0; i < numCities; i++) {
                if (!colored[i]) {
                    if (saturation[i] > maxSaturation ||
                        (saturation[i] == maxSaturation && uncoloredNeighbors[i] > maxUncoloredNeighbors)) {
                        selectedCity = i;
                        maxSaturation = saturation[i];
                        maxUncoloredNeighbors = uncoloredNeighbors[i];
                    }
                }
            }

            if (selectedCity == -1) {
                break;
            }

            boolean[] usedColors = new boolean[5];
            if (selectedCity < actualGraphSize && adjacencyList[selectedCity] != null) {
                Node current = adjacencyList[selectedCity];
                while (current != null) {
                    if (current.targetCity >= 0 && current.targetCity < numCities &&
                        colored[current.targetCity] && colors[current.targetCity] >= 0 && colors[current.targetCity] < 5) {
                        usedColors[colors[current.targetCity]] = true;
                    }
                    current = current.next;
                }
            }

            int assignedColor = 0;
            while (assignedColor < 5 && usedColors[assignedColor]) {
                assignedColor++;
            }

            colors[selectedCity] = assignedColor;
            colored[selectedCity] = true;

            // Update saturation and uncoloredNeighbors for neighbors of selectedCity
            if (selectedCity < actualGraphSize && adjacencyList[selectedCity] != null) {
                Node current = adjacencyList[selectedCity];
                while (current != null) {
                    int neighborCity = current.targetCity;
                    if (neighborCity >= 0 && neighborCity < numCities && !colored[neighborCity]) {
                        boolean[] neighborColors = new boolean[5];
                        if (neighborCity < actualGraphSize && adjacencyList[neighborCity] != null) {
                            Node neighbor = adjacencyList[neighborCity];
                            while (neighbor != null) {
                                int t = neighbor.targetCity;
                                if (t >= 0 && t < numCities && colored[t] && colors[t] >= 0 && colors[t] < 5) {
                                    neighborColors[colors[t]] = true;
                                }
                                neighbor = neighbor.next;
                            }
                        }

                        int newSat = 0;
                        for (int c = 0; c < 5; c++) {
                            if (neighborColors[c]) {
                                newSat++;
                            }
                        }
                        saturation[neighborCity] = newSat;
                        uncoloredNeighbors[neighborCity]--;
                    }
                    current = current.next;
                }
            }
        }

        return printCompactFinalColoring(cities, colors, saturation, uncoloredNeighbors);
    }

    public Node[] getAdjacencyList() {
        return this.adjacencyList;
    }

    private String getColorName(int color) {
        String[] colorNames = { "Red", "Green", "Blue", "Yellow", "Purple" };
        if (color >= 0 && color < colorNames.length) {
            return colorNames[color];
        }
        return "Unknown";
    }

    // DO NOT ALTER THIS METHOD AS IT WILL NEGATIVELY IMPACT YOUR MARKS
    public String printCompactFinalColoring(MetaCity[] cities, int[] colors, int[] saturation,
            int[] uncoloredNeighbors) {
        StringBuilder result = new StringBuilder();
        result.append("=== Final Coloring ===\n");
        for (int i = 0; i < colors.length; i++) {
            result.append(String.format("%-12s: %-6s (DSATUR=%d, Uncolored=%d)\n",
                    cities[i].getName(),
                    getColorName(colors[i]),
                    saturation[i],
                    uncoloredNeighbors[i]));
        }
        return result.toString();
    }

    // DO NOT ALTER THIS METHOD AS IT WILL NEGATIVELY IMPACT YOUR MARKS
    public String printConnections(MetaCity[] cities) {
        StringBuilder result = new StringBuilder();
        boolean hasConnections = false;

        if (adjacencyList == null || cities == null) {
            return "No intercity connections\n";
        }

        for (int i = 0; i < adjacencyList.length; i++) {
            if (adjacencyList[i] != null && cities[i] != null) {
                String cityName = cities[i].getName();

                Node current = adjacencyList[i];
                while (current != null) {
                    if (current.targetCity >= 0 && current.targetCity < cities.length &&
                        cities[current.targetCity] != null) {

                        String targetCityName = cities[current.targetCity].getName();
                        String sourceDistrictName = cities[i].getDistrict(current.sourceDistrict).getName();
                        String targetDistrictName = cities[current.targetCity].getDistrict(current.targetDistrict).getName();

                        result.append(String.format("%s (%s) ---> %s (%s) [Weight: %d]\n",
                                cityName, sourceDistrictName,
                                targetCityName, targetDistrictName,
                                current.weight));

                        hasConnections = true;
                    }
                    current = current.next;
                }
            }
        }

        if (!hasConnections) {
            result.append("No intercity connections\n");
        }

        return result.toString();
    }
}
