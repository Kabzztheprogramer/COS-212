import java.util.Stack;

public class Graph {
    private int vertices;
    private int[][] adjMatrix;
    private Edge[] edges;
    private int edgeCount;

    // for strong dfs
    private int index = 0;
    private int[] num;
    private int[] pred;
    private boolean[] onStack;
    private Stack<Integer> stack;
    private int sccCount = 0;

    public Graph(int vertices, int maxEdges) {
        this.vertices = vertices;
        this.adjMatrix = new int[vertices][vertices];
        this.edges = new Edge[maxEdges];
        this.edgeCount = 0;
        
        // Initialize adjacency matrix with 0 (no edges)
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                adjMatrix[i][j] = 0;
            }
        }
        
        // Initialize SCC-related arrays
        this.num = new int[vertices];
        this.pred = new int[vertices];
        this.onStack = new boolean[vertices];
        this.stack = new Stack<>();
    }

    public Edge[] getEdges() {
        return edges;
    }

    public int getVerticesCount() {
        return vertices;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    // Calculate the total weight of the MST
    public int calculateMSTWeight(Edge[] mst) {
        int totalWeight = 0;
        for (Edge edge : mst) {
            if (edge != null) {
                totalWeight += edge.weight;
            }
        }
        return totalWeight;
    }

    // Add an edge to the graph
    public void addEdge(int src, int dest, int edgeIndex, int weight) {
        if (edgeCount >= edges.length) {
            throw new IllegalStateException("Max edges reached");
        }
        
        // Add edge to edges array
        edges[edgeCount] = new Edge(src, dest, edgeIndex, weight);
        edgeCount++;
        
        // Update adjacency matrix (for directed graph, only update [src][dest])
        adjMatrix[src][dest] = weight;
    }

    public Edge[] findMST() {
        // Kruskal's algorithm implementation
        Edge[] mst = new Edge[vertices - 1];
        int mstEdgeCount = 0;
        
        // Create MinHeap and add all edges
        MinHeap minHeap = new MinHeap();
        for (int i = 0; i < edgeCount; i++) {
            minHeap.addEdge(edges[i]);
        }
        
        // Initialize parent array for Union-Find
        int[] parent = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
        }
        
        // Process edges in order of increasing weight
        while (!minHeap.isEmpty() && mstEdgeCount < vertices - 1) {
            Edge currentEdge = minHeap.getMinElement();
            
            int srcRoot = find(parent, currentEdge.src);
            int destRoot = find(parent, currentEdge.dest);
            
            // If adding this edge doesn't create a cycle
            if (srcRoot != destRoot) {
                mst[mstEdgeCount] = currentEdge;
                mstEdgeCount++;
                union(parent, srcRoot, destRoot);
            }
        }
        
        return mst;
    }

    // Union-Find operations
    private int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent, parent[vertex]); // Path compression
        }
        return parent[vertex];
    }

    private void union(int[] parent, int x, int y) {
        int rootX = find(parent, x);
        int rootY = find(parent, y);
        parent[rootX] = rootY;
    }

    public String findSCC() {
        // Reset SCC-related variables
        index = 0;
        sccCount = 0;
        stack.clear();
        
        // Initialize arrays
        for (int i = 0; i < vertices; i++) {
            num[i] = -1;
            pred[i] = -1;
            onStack[i] = false;
        }
        
        StringBuilder result = new StringBuilder();
        
        // Run DFS from each unvisited vertex
        for (int v = 0; v < vertices; v++) {
            if (num[v] == -1) {
                strongDFS(v, result);
            }
        }
        
        return result.toString();
    }
    
    private void strongDFS(int v, StringBuilder result) {
        num[v] = index;
        pred[v] = index;
        index++;
        stack.push(v);
        onStack[v] = true;
        
        // Visit all adjacent vertices
        for (int w = 0; w < vertices; w++) {
            if (adjMatrix[v][w] != 0) { // There's an edge from v to w
                if (num[w] == -1) {
                    // w is unvisited
                    strongDFS(w, result);
                    pred[v] = Math.min(pred[v], pred[w]);
                } else if (onStack[w]) {
                    // w is on the stack (back edge)
                    pred[v] = Math.min(pred[v], num[w]);
                }
            }
        }
        
        // If v is the root of an SCC
        if (pred[v] == num[v]) {
            sccCount++;
            result.append("SCC ").append(sccCount).append(": ");
            
            int w;
            do {
                w = stack.pop();
                onStack[w] = false;
                result.append(w).append(" ");
            } while (w != v);
            
            result.append("\n");
        }
    }

    //do no alter this method 
    public String printAdjMatrix() {
        StringBuilder sb = new StringBuilder("Adjacency Matrix:\n");
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                sb.append(adjMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    //do no alter this method 
    public String printSpanningTree(Edge[] mst) {
        StringBuilder sb = new StringBuilder();
        for (Edge edge : mst) {
            sb.append(String.format("%d-%d (weight=%d)\n", edge.src, edge.dest, edge.weight));
        }
        return sb.toString();
    }
}