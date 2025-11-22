public class UtilityNetwork {
    private String type;
    private int[][] adjacencyMatrix;

    public UtilityNetwork(String type, int numDistricts) {
        // Validate type - must be one of the three allowed types
        if (type == null || (!type.equals("POWER") && !type.equals("WATER") && !type.equals("TRANSPORTATION"))) {
            throw new IllegalArgumentException("Invalid type. Must be 'POWER', 'TRANSPORTATION', or 'WATER'.");
        }
        
        // Validate numDistricts - must be >= 0
        if (numDistricts < 0) {
            throw new IllegalArgumentException("Number of districts must be greater than or equal 0.");
        }
        
        this.type = type;
        this.adjacencyMatrix = new int[numDistricts][numDistricts];
        // Matrix is initialized to all zeros by default in Java
    }

    public void addConnection(int from, int to, int weight) {
        // Validate district indices
        if (from < 0 || from >= adjacencyMatrix.length || to < 0 || to >= adjacencyMatrix.length) {
            throw new IllegalArgumentException("Invalid district indices.");
        }
        
        // Validate weight
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }
        
        // Add undirected connection (for POWER and WATER)
        adjacencyMatrix[from][to] = weight;
        adjacencyMatrix[to][from] = weight;
    }

    public void addTransportConnection(int from, int to, int weight) {
        // Validate district indices
        if (from < 0 || from >= adjacencyMatrix.length || to < 0 || to >= adjacencyMatrix.length) {
            throw new IllegalArgumentException("Invalid district indices.");
        }
        
        // Validate weight
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }
        
        // Add directed connection (for TRANSPORTATION)
        adjacencyMatrix[from][to] = weight;
    }

    public int getWeight(int from, int to) {
        // Check bounds before accessing matrix
        if (from < 0 || from >= adjacencyMatrix.length || to < 0 || to >= adjacencyMatrix.length) {
            return 0;
        }
        
        return adjacencyMatrix[from][to];
    }

    public boolean hasConnection(int from, int to) {
        // Check bounds before accessing matrix
        if (from < 0 || from >= adjacencyMatrix.length || to < 0 || to >= adjacencyMatrix.length) {
            return false;
        }
        
        return adjacencyMatrix[from][to] > 0;
    }

    public String getType() {
        return this.type;
    }

    public int[][] getAdjacencyMatrix() {
        return this.adjacencyMatrix;
    }

    public int getNumDistricts() {
        return adjacencyMatrix.length;
    }

    // DO NOT MODIFY THIS METHOD AS IT WILL NEGATIVELY AFFECT YOUR MARKS
    public String printNetwork() {
        int n = adjacencyMatrix.length;
        StringBuilder output = new StringBuilder();

        output.append("    ");
        for (int i = 0; i < n; i++) {
            output.append(String.format("%3d ", i));
        }
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < n; i++) {
            separator.append("----");
        }
        output.append("\n    ").append(separator).append("\n");

        for (int i = 0; i < n; i++) {
            output.append(String.format("%2d | ", i));
            for (int j = 0; j < n; j++) {
                output.append(String.format("%3d ", adjacencyMatrix[i][j]));
            }
            output.append("\n");
        }

        output.append("\n  Connections:\n");
        boolean hasConnections = false;
        if ("TRANSPORTATION".equals(type)) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (adjacencyMatrix[i][j] > 0) {
                        output.append(String.format(" District %d --> District %d (Weight: %d)\n",
                                i, j, adjacencyMatrix[i][j]));
                        hasConnections = true;
                    }
                }
            }
        } else {
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) { // Only print each connection once
                    if (adjacencyMatrix[i][j] > 0) {
                        output.append(String.format("    District %d <--> District %d (Weight: %d)\n",
                                i, j, adjacencyMatrix[i][j]));
                        hasConnections = true;
                    }
                }
            }
        }

        if (!hasConnections) {
            output.append("    No connections\n");
        }

        return output.toString();
    }
}