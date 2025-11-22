import java.util.Arrays;

public class MetaCity {
    private String name;
    private District[] districts;
    private UtilityNetwork[] utilityNetworks;
    boolean[] blockedDistricts;

    public MetaCity(String name, int numDistricts) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be null or empty.");
        }
        if (numDistricts <= 0) {
            throw new IllegalArgumentException("Number of districts must be greater than zero.");
        }

        this.name = name;
        this.districts = new District[numDistricts];
        this.blockedDistricts = new boolean[numDistricts];

        this.utilityNetworks = new UtilityNetwork[3];
        this.utilityNetworks[0] = new UtilityNetwork("POWER", numDistricts);
        this.utilityNetworks[1] = new UtilityNetwork("WATER", numDistricts);
        this.utilityNetworks[2] = new UtilityNetwork("TRANSPORTATION", numDistricts);
    }

    public void addDistrict(int index, String name) {
        if (index < 0 || index >= districts.length) {
            throw new IllegalArgumentException("Index out of bounds for districts array.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("District name cannot be null or empty.");
        }
        if (districts[index] != null) {
            throw new IllegalStateException("A district already exists at this index.");
        }

        try {
            districts[index] = new District(index, name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create district at index " + index + ": " + e.getMessage(), e);
        }
    }

    public void addUtilityConnection(String utilityType, int from, int to, int weight) {
        if (utilityType == null) {
            throw new IllegalArgumentException("Utility type cannot be null.");
        }

        UtilityNetwork network = getUtilityNetwork(utilityType);
        if (network == null) {
            throw new IllegalArgumentException("Invalid utility type: " + utilityType);
        }

        if (utilityType.equals("TRANSPORTATION")) {
            network.addTransportConnection(from, to, weight);
        } else {
            network.addConnection(from, to, weight);
        }
    }

    public String getName() {
        return this.name;
    }

    public District getDistrict(int index) {
        if (index < 0 || index >= districts.length) {
            return null;
        }
        return districts[index];
    }

    public District[] getDistricts() {
        return this.districts;
    }

    public UtilityNetwork getUtilityNetwork(String type) {
        if (type == null) return null;

        switch (type) {
            case "POWER":
                return utilityNetworks[0];
            case "WATER":
                return utilityNetworks[1];
            case "TRANSPORTATION":
                return utilityNetworks[2];
            default:
                return null;
        }
    }

    public String dfsTransportWithBlocks(int startDistrict, boolean[] blockedDistricts) {
        if (startDistrict < 0 || startDistrict >= districts.length) {
            throw new IllegalArgumentException("Invalid start district index.");
        }

        boolean[] actualBlocked = (blockedDistricts == null) ? this.blockedDistricts : blockedDistricts;

        if (actualBlocked.length != districts.length) {
            throw new IllegalArgumentException("Blocked districts array must match number of districts.");
        }

        boolean[] visited = new boolean[districts.length];
        StringBuilder result = new StringBuilder();

        dfsHelper(startDistrict, visited, actualBlocked, getUtilityNetwork("TRANSPORTATION"), result);

        return result.toString();
    }

    private void dfsHelper(int current, boolean[] visited, boolean[] blocked,
                          UtilityNetwork network, StringBuilder result) {
        if (visited[current] || blocked[current] || districts[current] == null) return;

        visited[current] = true;
        result.append("Visiting: ").append(districts[current].getName()).append("\n");

        int[][] matrix = network.getAdjacencyMatrix();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[current][i] > 0 && !visited[i] && !blocked[i]) {
                dfsHelper(i, visited, blocked, network, result);
            }
        }
    }

    public int[] dijkstraTransport(int startDistrict) {
        if (startDistrict < 0 || startDistrict >= districts.length) {
            throw new IllegalArgumentException("Invalid start district index.");
        }

        int numDistricts = districts.length;
        int[] distances = new int[numDistricts];
        boolean[] visited = new boolean[numDistricts];
        UtilityNetwork network = getUtilityNetwork("TRANSPORTATION");
        int[][] matrix = network.getAdjacencyMatrix();

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startDistrict] = 0;

        for (int count = 0; count < numDistricts - 1; count++) {
            int minDist = Integer.MAX_VALUE;
            int minIndex = -1;

            for (int i = 0; i < numDistricts; i++) {
                if (!visited[i] && distances[i] < minDist) {
                    minDist = distances[i];
                    minIndex = i;
                }
            }

            if (minIndex == -1) break;
            visited[minIndex] = true;

            for (int i = 0; i < numDistricts; i++) {
                if (!visited[i] && matrix[minIndex][i] > 0 &&
                        distances[minIndex] != Integer.MAX_VALUE &&
                        distances[minIndex] + matrix[minIndex][i] < distances[i]) {
                    distances[i] = distances[minIndex] + matrix[minIndex][i];
                }
            }
        }

        return distances;
    }

    // DO NOT ALTER THIS METHOD AS IT WILL NEGATIVELY IMPACT YOUR MARKS
    public static String printDistances(MetaCity city, int startDistrict) {
        int[] distances = city.dijkstraTransport(startDistrict);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < distances.length; i++) {
            String districtName = city.getDistrict(i).getName();
            if (distances[i] == Integer.MAX_VALUE) {
                result.append(String.format("%-20s: Unreachable\n", districtName));
            } else {
                result.append(String.format("%-20s: %d minutes\n", districtName, distances[i]));
            }
        }
        return result.toString();
    }

    // DO NOT ALTER THIS METHOD AS IT WILL NEGATIVELY IMPACT YOUR MARKS
    public String printCity() {
        StringBuilder result = new StringBuilder();
        result.append("---- METACITY: ").append(name).append(" ----\n");
        result.append("Districts:\n");

        for (int i = 0; i < districts.length; i++) {
            if (districts[i] != null) {
                result.append("  ").append(i).append(": ").append(districts[i].getName()).append("\n");
            }
        }

        for (UtilityNetwork network : utilityNetworks) {
            result.append("\n").append(network.getType()).append(" Network:\n");
            result.append(network.printNetwork());
        }

        return result.toString();
    }
}
