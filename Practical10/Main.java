
public class Main {
        public static void main(String[] args) {
        Graph graph = new Graph(8, 14); // 8 vertices, max 14 edges
        
        // Add directed edges (creating a sample graph with SCCs)
        graph.addEdge(0, 1, 0, 1);
        graph.addEdge(1, 2, 1, 1);
        graph.addEdge(2, 0, 2, 1);
        graph.addEdge(2, 3, 3, 1);
        graph.addEdge(3, 4, 4, 1);
        graph.addEdge(4, 5, 5, 1);
        graph.addEdge(5, 3, 6, 1);
        graph.addEdge(5, 6, 7, 1);
        graph.addEdge(6, 7, 8, 1);
        graph.addEdge(7, 6, 9, 1);
        
        System.out.println("Finding Strongly Connected Components:");
        String sccCount = graph.findSCC();
        System.out.println( sccCount);

        System.out.println(graph.printAdjMatrix());

        System.out.println("Finding Minimum Spanning Tree:");
        Edge[] mst = graph.findMST();
        System.out.println(graph.printSpanningTree(mst));
    }
}
