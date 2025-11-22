class Edge implements Comparable<Edge> {
    int src;
    int dest;
    int edgeIndex;
    int weight;

    public Edge(int src, int dest, int edgeIndex, int weight) {
        // impliment
        this.src = src;
        this.dest = dest;
        this.edgeIndex = edgeIndex;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }

    @Override
    public String toString() {
        return "Edge [" + src + "-" + dest + ", idx=" + edgeIndex + ", weight=" + weight + "]";
    }
}