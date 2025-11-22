public class Graph {
    private Integer[][] adjacencyMatrix;

    public Graph() {
        /*
         * Creates a graph with no vertices
         */
        adjacencyMatrix = new Integer[0][0];
    }

    public Graph(String input) {
        /*
         * Reconstructs a graph using the same format as the last line of the toString
         * function
         */
        String[] firstSplit = input.split(": ");
        adjacencyMatrix = new Integer[Integer.valueOf(firstSplit[0])][Integer.valueOf(firstSplit[0])];
        String[] edges = firstSplit[1].split(" ");
        for (String e : edges) {
            String[] secondSplit = e.split("=");
            String[] vertices = secondSplit[0].split("->");
            setEdge(Integer.valueOf(vertices[0]), Integer.valueOf(vertices[1]), Integer.valueOf(secondSplit[1]));
        }
    }

    public void addVertex() {
        /*
         * Adds a vertext to the graph
         */
        Integer[][] newAdjacencyMatrix = new Integer[adjacencyMatrix.length + 1][adjacencyMatrix.length + 1];
        for (int i = 0; i <= adjacencyMatrix.length; i++) {
            for (int j = 0; j <= adjacencyMatrix.length; j++) {
                if (i == adjacencyMatrix.length || j == adjacencyMatrix.length) {
                    newAdjacencyMatrix[i][j] = null;
                } else {
                    newAdjacencyMatrix[i][j] = adjacencyMatrix[i][j];
                }
            }
        }
        adjacencyMatrix = newAdjacencyMatrix;
    }

    public Integer getEdge(int from, int to) {
        /*
         * Returns the weight between two vertices.
         * If there is no edge then null is returned.
         * If invalid vertices are passed the program WILL crash.
         */
        return adjacencyMatrix[from][to];
    }

    public int length() {
        /*
         * Returns the number of vertices in the graph
         */
        return adjacencyMatrix.length;
    }

    public void setEdge(int from, int to, Integer weight) {
        /*
         * Creates or updates an edge in the graph.
         * If invalid vertices are passed the program WILL crash.
         * Self links are not allowed (Thus there can't be an edge from a vertex to
         * itself)
         */
        if (from == to) {
            return;
        }
        adjacencyMatrix[from][to] = weight;
    }

    public String toString() {
        /*
         * Returns a string representation of the graph
         */
        String edges = String.valueOf(adjacencyMatrix.length) + ":";
        String res = "\t";
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            res += String.valueOf(i) + "\t";
        }
        res += "\n";
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            res += String.valueOf(i) + "\t";
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                res += (adjacencyMatrix[i][j] == null ? "." : String.valueOf(adjacencyMatrix[i][j])) + "\t";
                if (adjacencyMatrix[i][j] != null) {
                    edges += " " + String.valueOf(i) + "->" + String.valueOf(j)
                            + "=" + String.valueOf(adjacencyMatrix[i][j]);
                }
            }
            res += "\n";
        }
        return res + edges;
    }

}
