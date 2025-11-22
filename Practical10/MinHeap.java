import java.util.PriorityQueue;

public class MinHeap {
    private PriorityQueue<Edge> minHeap;

    public MinHeap() {
        this.minHeap = new PriorityQueue<>();
    }

    public void addEdge(Edge edge) {
        minHeap.add(edge);
    }

    // Get the minimum edge without removing it
    public Edge peekMin() {
        return minHeap.peek();
    }

    // Remove and return the minimum edge
    public Edge getMinElement() {
        return minHeap.poll();
    }

    public boolean isEmpty() {
        return minHeap.isEmpty();
    }

    public int size() {
        return minHeap.size();
    }

    public String printHeap() {
        StringBuilder heapContents = new StringBuilder("Current MinHeap contents:\n");
        for (Edge edge : minHeap) {
            heapContents.append(edge).append("\n");
        }
        return heapContents.toString();
    }
}