public class GraphOperations {
    public static String DFS(Graph g) {
        int n = g.length();
    if (n == 0) return "";
     boolean[] visited = new boolean[n];
        String result = "";

        for (int start = 0; start < n; start++) {
            if (!visited[start]) {
                int[] stack = new int[] { start };
                visited[start] = true;
                result += start + " ";

                while (stack.length > 0) {
                    int v = top(stack);

                    boolean foundUnvisited = false;
                    for (int i = 0; i < n; i++) {
                        if (g.getEdge(v, i) != null && !visited[i]) {
                            visited[i] = true;
                            result += i + " ";
                            stack = push(stack, i);
                            foundUnvisited = true;
                            break;
                        }
                    }

                    if (!foundUnvisited) {
                        stack = pop(stack);
                    }
                }
            }
        }

        return result;

    }

    private static int top(int [] Arrdata){
        return Arrdata[ Arrdata.length-1 ] ;
    }

    private static int [] pop( int [] Arrdata ){
        int[] newArr = new int[Arrdata.length - 1];
        for(int i = 0 ; i < Arrdata.length-1 ; i++){
            newArr[i] = Arrdata[i] ;
        }
        return newArr;
    }   

    private static int [] push( int [] Arrdata , int value){
        int[] newArr = new int[Arrdata.length + 1];
        for(int i = 0 ; i < Arrdata.length; i++){
            newArr[i] = Arrdata[i] ;
        }
        newArr[Arrdata.length] = value ;
        return newArr;
    }  

    public static String BFS(Graph g) {
    int n = g.length();
    if (n == 0) return "";

    boolean[] visited = new boolean[n];
    Queue q = new Queue();
    String result = "";

    for (int start = 0; start < n; start++) {
        if (!visited[start]) {
            visited[start] = true;
            q.enqueue(start);

            while (q.peak() != null) {
                int current = q.dequeue();
                result += current + " ";

                for (int i = 0; i < n; i++) {
                    if (g.getEdge(current, i) != null && !visited[i]) {
                        visited[i] = true;
                        q.enqueue(i);
                    }
                }
            }
        }
    }

    return result;
}


    public static String IterativeTopologicalSort(Graph g) {

        int n = g.length();
        int[] inDegree = new int[n];

        for (int from = 0; from < n; from++) {
            for (int to = 0; to < n; to++) {
                if (g.getEdge(from, to) != null) {
                    inDegree[to]++;
                }
            }
        }

        Queue q = new Queue();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                q.enqueue(i);
            }
        }

        String result = "";
        int count = 0;

        while (q.peak() != null) {
            int current = q.dequeue();
            result += current + " ";
            count++;

            for (int i = 0; i < n; i++) {
                if (g.getEdge(current, i) != null) {
                    inDegree[i]--;
                    if (inDegree[i] == 0) {
                        q.enqueue(i);
                    }
                }
            }
        }

        if (count < n) {
            return "Cycle Detected";
        }

        return result;
    }

    public static String shortestPath(Graph g, int from, int to) {
        int n = g.length();
        if (from < 0 || from >= n || to < 0 || to >= n) {
            return "Invalid vertex";
        }

        int[] dist = new int[n];
        int[] pred = new int[n];
        boolean[] inQueue = new boolean[n];

        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        dist[from] = 0;
        Queue q = new Queue();
        q.enqueue(from);
        inQueue[from] = true;

        while (q.peak() != null) {
            int u = q.dequeue();
            inQueue[u] = false;

            for (int v = 0; v < n; v++) {
                Integer weight = g.getEdge(u, v);
                if (weight != null) {
                    if (dist[u] != Integer.MAX_VALUE && dist[v] > dist[u] + weight) {
                        dist[v] = dist[u] + weight;
                        pred[v] = u;

                        if (!inQueue[v]) {
                            q.enqueue(v);
                            inQueue[v] = true;
                        }
                    }
                }
            }
        }

        if (dist[to] == Integer.MAX_VALUE) {
            return "No path";
        }

        int current = to;
        String path = "";
        while (current != -1) {
            path = current + " " + path;
            current = pred[current];
        }

        return path;
    }
}
