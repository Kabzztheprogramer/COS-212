public class SkewHeap<T extends Comparable<T>> extends MaxHeap<T> {
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

    Node<T> merge(Node<T> firstHeap, Node<T> secondHeap) {
        // Return the other heap if one is null
        if (firstHeap == null) {
            return secondHeap;
        }
        if (secondHeap == null) {
            return firstHeap;
        }

        // Maintain max-heap property: root with larger value stays on top
        if (firstHeap.compareTo(secondHeap) < 0) {
            Node<T> placeholder = firstHeap;
            firstHeap = secondHeap;
            secondHeap = placeholder;
        }

        // Swap the left and right subtrees of the root
        Node<T> swapNode = firstHeap.left;
        firstHeap.left = firstHeap.right;
        firstHeap.right = swapNode;

        // Merge the left child with the second heap recursively
        firstHeap.left = merge(secondHeap, firstHeap.left);

        // Return the new merged root
        return firstHeap;
    }

    @Override
    public void enqueue(T newValue) {
        // Create a new node with the inserted value
        Node<T> insertedNode = new Node<T>(newValue);

        // Merge it with the current root
        root = merge(root, insertedNode);
    }

    @Override
    public T peak() {
        // Return null if heap is empty or root has no value
        if (this.root == null || this.root.data == null) {
            return null;
        }

        // Return the maximum value (root of the max-heap)
        return this.root.data;
    }

    @Override
    public T dequeue() {
        // Return null if heap is empty
        if (this.root == null) {
            return null;
        }

        // Store the max value (root data) to return later
        T maxValue = this.root.data;

        // Merge the left and right children of the root
        this.root = merge(this.root.left, this.root.right);

        // Return the max value
        return maxValue;
    }
}
