public class Main {
    /*
     * Output in output.txt
     */
    public static void main(String[] args) {
        Graph g = new Graph("7: 0->1=2 0->3=2 0->4=2 1->2=5 1->4=-3 2->4=9 3->4=4 3->6=1 4->5=5 6->4=-5");
        System.out.println(g);
        System.out.println("DFS: " + GraphOperations.DFS(g));
        System.out.println("BFS: " + GraphOperations.BFS(g));
        System.out.println("TopSort: " + GraphOperations.IterativeTopologicalSort(g));
        System.out.println("ShortestPath: " + GraphOperations.shortestPath(g, 0, 5));
    }

}
