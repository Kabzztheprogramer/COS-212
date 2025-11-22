public class MetaCitySystem {
    public MetaCity[] cities;
    private InterCityNetwork interCityNetwork;

    public MetaCitySystem(int numCities) {
        if (numCities < 0) {
            throw new IllegalArgumentException("Number of cities must be greater than or equal to 0.");
        }
        this.cities = new MetaCity[numCities];
        this.interCityNetwork = new InterCityNetwork(numCities);
    }

    public void addCity(int index, String name, int numDistricts) {
        if (index < 0 || index >= cities.length) {
            throw new IllegalArgumentException("Index out of bounds for cities array.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("City name cannot be null or empty.");
        }
        if (numDistricts <= 0) {
            throw new IllegalArgumentException("Number of districts must be greater than zero.");
        }

        this.cities[index] = new MetaCity(name, numDistricts);
    }

    public MetaCity getCity(int index) {
        if (index < 0 || index >= cities.length) {
            throw new IllegalArgumentException("Index out of bounds for cities array.");
        }
        return cities[index];
    }

    public InterCityNetwork getInterCityNetwork() {
        return this.interCityNetwork;
    }

    public int getGlobalDistrictId(int cityIndex, int districtIndex, String utilityType) {
        if (cityIndex < 0 || cityIndex >= cities.length) {
            throw new IllegalArgumentException("City index out of bounds.");
        }
        if (cities[cityIndex] == null) {
            throw new IllegalArgumentException("City at index " + cityIndex + " is null.");
        }
        if (utilityType == null || utilityType.trim().isEmpty()) {
            throw new IllegalArgumentException("Utility type cannot be null or empty.");
        }

        UtilityNetwork network = cities[cityIndex].getUtilityNetwork(utilityType);
        if (network == null) {
            throw new IllegalArgumentException("Utility network not found for type: " + utilityType);
        }
        if (districtIndex < 0 || districtIndex >= network.getNumDistricts()) {
            throw new IllegalArgumentException("District index out of bounds for city " + cityIndex);
        }

        int globalId = 0;
        for (int i = 0; i < cityIndex; i++) {
            if (cities[i] != null) {
                UtilityNetwork prev = cities[i].getUtilityNetwork(utilityType);
                if (prev != null) {
                    globalId += prev.getNumDistricts();
                }
            }
        }
        globalId += districtIndex;
        return globalId;
    }

    public boolean isPowerGridConnected() {
        int[][] matrix = buildCombinedPowerMatrix();
        if (matrix == null || matrix.length == 0) {
            return true;
        }

        boolean[] visited = new boolean[matrix.length];
        dfs(0, matrix, visited);

        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }

    private void dfs(int node, int[][] matrix, boolean[] visited) {
        if (node < 0 || node >= matrix.length || visited[node]) return;

        visited[node] = true;
        for (int i = 0; i < matrix[node].length; i++) {
            if (matrix[node][i] > 0 && !visited[i]) {
                dfs(i, matrix, visited);
            }
        }
    }

    public int[][] buildCombinedPowerMatrix() {
        if (cities == null || cities.length == 0) return new int[0][0];

        int districtTotal = 0;
        for (MetaCity city : cities) {
            if (city != null) {
                UtilityNetwork power = city.getUtilityNetwork("POWER");
                if (power != null) {
                    int n = power.getNumDistricts();
                    if (n > 0 && n < 10000) {
                        districtTotal += n;
                    } else {
                        System.err.println("Warning: Invalid district count.");
                    }
                } else {
                    System.err.println("Warning: Missing power network.");
                }
            }
        }

        if (districtTotal == 0) return new int[0][0];
        if (districtTotal > 10000)
            throw new IllegalStateException("Combined matrix too large");

        int[][] combined = new int[districtTotal][districtTotal];
        int currOffset = 0;

        for (int i = 0; i < cities.length; i++) {
            MetaCity city = cities[i];
            if (city == null) continue;

            UtilityNetwork power = city.getUtilityNetwork("POWER");
            if (power == null) continue;

            int[][] cityMatrix = power.getAdjacencyMatrix();
            int num = power.getNumDistricts();

            if (cityMatrix == null || cityMatrix.length != num) {
                System.err.println("Warning: City " + city.getName() + " has invalid matrix.");
                continue;
            }

            for (int r = 0; r < num; r++) {
                if (cityMatrix[r] == null || cityMatrix[r].length != num) {
                    System.err.println("Warning: Row " + r + " invalid in city " + city.getName());
                    continue;
                }

                for (int c = 0; c < num; c++) {
                    combined[currOffset + r][currOffset + c] = cityMatrix[r][c];
                }
            }

            currOffset += num;
        }

        InterCityNetwork.Node[] adj = interCityNetwork.getAdjacencyList();
        if (adj != null) {
            for (int i = 0; i < adj.length; i++) {
                if (cities[i] == null) continue;
                InterCityNetwork.Node node = adj[i];
                while (node != null) {
                    int t = node.targetCity;
                    if (i < t && t < cities.length && cities[t] != null) {
                        try {
                            int srcId = getGlobalDistrictId(i, node.sourceDistrict, "POWER");
                            int tgtId = getGlobalDistrictId(t, node.targetDistrict, "POWER");

                            if (srcId != tgtId &&
                                srcId >= 0 && srcId < combined.length &&
                                tgtId >= 0 && tgtId < combined.length) {
                                combined[srcId][tgtId] = node.weight;
                                combined[tgtId][srcId] = node.weight;
                            }
                        } catch (Exception e) {
                            System.err.println("Error linking city " + i + " to " + t + ": " + e.getMessage());
                        }
                    }
                    node = node.next;
                }
            }
        }

        return combined;
    }

    // DO NOT EDIT BELOW THIS LINE
    private String getDistrictName(int globalId, String utilityType) {
        int offset = 0;
        for (MetaCity city : cities) {
            if (city == null) continue;
            UtilityNetwork network = city.getUtilityNetwork(utilityType);
            if (network == null) continue;
            int numDistricts = network.getNumDistricts();
            if (globalId < offset + numDistricts) {
                return city.getName() + ":" + city.getDistrict(globalId - offset).getName();
            }
            offset += numDistricts;
        }
        return "Unknown District ID: " + globalId;
    }

    public String printUnreachableDistricts() {
        int[][] matrix = buildCombinedPowerMatrix();
        boolean[] visited = new boolean[matrix.length];
        if (matrix.length > 0) dfs(0, matrix, visited);

        StringBuilder result = new StringBuilder();
        result.append("\nUnreachable districts:\n");
        boolean allConnected = true;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                result.append("- ").append(getDistrictName(i, "POWER")).append("\n");
                allConnected = false;
            }
        }
        if (allConnected) {
            result.append("(All districts are reachable)\n");
        }
        return result.toString();
    }

    public String printSystem() {
        StringBuilder result = new StringBuilder();
        result.append("==== METACITY SYSTEM OVERVIEW ====\n");
        for (int i = 0; i < cities.length; i++) {
            if (cities[i] != null) {
                result.append("\n").append(cities[i].printCity());
            }
        }
        result.append("\n==== INTERCITY CONNECTIONS ====\n");
        result.append(interCityNetwork.printConnections(cities));
        return result.toString();
    }
}
