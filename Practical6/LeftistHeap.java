public class LeftistHeap<T extends Comparable<T>> extends MaxHeap<T> {
    public Node<T> root;

    public String toString() {
        return (root == null ? "Empty Heap" : toStringOneLine() + "\n" + toString(root, "", true));
    }

    private String toString(Node<T> ptr, String prefix, boolean end) {
        String res = "";
        if (ptr.right != null) {
            res += toString(ptr.right, prefix + (end ? "│   " : "    "), false);
        }
        res += prefix + (end ? "└── " : "┌── ") + ptr.toString() + "\n";
        if (ptr.left != null) {
            res += toString(ptr.left, prefix + (end ? "    " : "│   "), true);
        }
        return res;
    }

    public String toStringOneLine() {
        return (root == null ? "Empty Heap" : toStringOneLine(root));
    }

    private String toStringOneLine(Node<T> ptr) {
        if (ptr == null) {
            return "{}";
        }
        return "{" + ptr.toString() + toStringOneLine(ptr.left) + toStringOneLine(ptr.right) + "}";
    }

    @Override
    public void enqueue(T newValue) {
        // Create a new node with the given value
        Node<T> newHeapNode = new Node<T>(newValue);

        // Merge the new node with the existing heap
        root = merge(root, newHeapNode);
    }

    // Computes the null path length (NPL) of a given node
    public int calculateNPL(Node<T> current) {
        // Return -1 if the node is null
        if (current == null) {
            return -1;
        }

        // If the node has at most one child, the NPL is 0
        if (current.left == null || current.right == null) {
            return 0; 
        }
        // Compute NPL for both left and right subtrees
        int leftSubtreeNPL = calculateNPL(current.left);
        int rightSubtreeNPL = calculateNPL(current.right);

        // Return 1 plus the minimum NPL of both subtrees
        return 1 + Math.min(leftSubtreeNPL, rightSubtreeNPL);
    }

    // Merges two leftist heaps and returns the new root node
    private Node<T> merge(Node<T> firstHeap, Node<T> secondHeap) {
        // If either heap is empty, return the other heap
        if (firstHeap == null) {
            return secondHeap;
        }
        if (secondHeap == null) {
            return firstHeap;
        }

        // Ensure the max-heap property is maintained
        if (firstHeap.compareTo(secondHeap) < 0) {
            Node<T> tempHeap = firstHeap;
            firstHeap = secondHeap;
            secondHeap = tempHeap;
        }

        // Merge the right child of the first heap with the second heap
        firstHeap.right = merge(firstHeap.right, secondHeap);

        // Maintain the leftist heap property by ensuring the left child has the greater or equal null path length
        if (nullPathLength(firstHeap.left) < nullPathLength(firstHeap.right)) {
            Node<T> tempNode = firstHeap.left;
            firstHeap.left = firstHeap.right;
            firstHeap.right = tempNode;
        }

        // Return the root of the merged heap
        return firstHeap;
    }

    // Retrieves the null path length (NPL) of a node
    private int nullPathLength(Node<T> current) {
        return (current == null) ? -1 : calculateNPL(current);
    }

    @Override
    public T peak() {
        // If the heap is empty, return null
        if (this.root == null || this.root.data == null) {
            return null;
        }

        // Return the maximum element (root node)
        return this.root.data;
    }

    @Override
    public T dequeue() {
        // If the heap is empty, return null
        if (this.root == null) {
            return null;
        }

        // Store the value of the maximum element (root)
        T maxValue = this.root.data;

        // Merge the left and right children of the root
        this.root = merge(this.root.left, this.root.right);
        
        // Return the maximum value
        return maxValue;
    }
}


