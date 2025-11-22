@SuppressWarnings("unchecked")
public class DHeap<T extends Comparable<T>> extends MaxHeap<T> {
    public int d;
    public T[] data;

    public DHeap(int d) {
        if (d <= 1) {
            d = 2;
        }
        this.d = d;
        data = (T[]) new Comparable[0];
    }

    public DHeap(int d, T... values) {
        this.d = (d <= 1) ? 2 : d;
        
        // Create a new array and copy the input values into it
        this.data = (T[]) new Comparable[values.length];
        for (int i = 0; i < values.length; i++) {
            this.data[i] = values[i];
        }
        if (this.d == 0) {
            return;
        }
    
        // Apply Floyd’s Heapify Algorithm if heap is non-empty
        for (int parentIdx = (this.data.length - 1) / this.d; parentIdx >= 0; parentIdx--) {
            Heapify_down(parentIdx);
        }
    }

    private void Heapify_down(int parentIdx) {
        int maxIdx = parentIdx;
        while (true) {
            int swapIdx = maxIdx;
            
            // Check all children of the current node
            for (int i = 1; i<= d; i++) {
                int childIdx = d * maxIdx + i;
                
                // Update swap index if a larger child is found
                if (childIdx < data.length && data[childIdx].compareTo(data[swapIdx]) > 0) {
                    swapIdx = childIdx;
                }
            }
            
            // If no swaps are needed, exit loop
            if (swapIdx == maxIdx) {
                break;
            }
            
            // Swap parent with the largest child
            T temp = data[maxIdx];
            data[maxIdx] = data[swapIdx];
            data[swapIdx] = temp;
            
            maxIdx = swapIdx;
        }
    }
    public String toString() {
        return (data.length == 0 ? "Empty Heap" : toStringOneLine() + "\n" + toString(0, "", true));
    }

    private String toString(int idx, String prefix, boolean end) {
        String res = prefix + (end ? "└── " : "├── ") + data[idx].toString() + "\n";
        for (int i = 0; i < d; i++) {
            if (d * idx + i + 1 < data.length) {
                boolean isEnd = ((i == d - 1) || (d * idx + i + 2 >= data.length));
                res += toString(d * idx + i + 1, prefix + (end ? "   " : "│  "), isEnd);
            }
        }
        return res;
    }

    public String toStringOneLine() {
        return String.valueOf(d) + ":"
                + (data.length == 0 ? "Empty Heap" : String.valueOf(data.length) + ":" + toStringOneLine(0));
    }

    private String toStringOneLine(int idx) {
        if (idx >= data.length) {
            return "{}";
        }
        String res = "{" + data[idx].toString();
        for (int i = 0; i < d; i++) {
            res += toStringOneLine(d * idx + i + 1);
        }
        return res + "}";
    }

    public int getLC(int parent, T... arr) {
        int leftChildIdx = (2 * parent) + 1;
        return (leftChildIdx >= arr.length) ? -1 : leftChildIdx;
    }

    public int getRC(int parent, T... arr) {
        int rightChildIdx = (2 * parent) + 2;
        return (rightChildIdx >= arr.length) ? -1 : rightChildIdx;
    }

    @Override
    public void enqueue(T newValue) {
        // Create a new array with increased size
        T[] expandedData = (T[]) new Comparable[this.data.length + 1];
        
        // Copy existing data
        for (int index = 0; index < this.data.length; index++) {
            expandedData[index] = this.data[index];
        }
        expandedData[this.data.length] = newValue;
        this.data = expandedData;

        // Bubble up the newly inserted value to maintain heap property
        int currentIdx = this.data.length - 1;
        while (currentIdx > 0) {
            int parentIdx = (currentIdx - 1) / this.d;
            
            // Stop if heap property is satisfied
            if (this.data[currentIdx].compareTo(this.data[parentIdx]) <= 0) {
                break;
            }
            
            // Swap with parent
            T temp = this.data[currentIdx];
            this.data[currentIdx] = this.data[parentIdx];
            this.data[parentIdx] = temp;
            
            currentIdx = parentIdx;
        }
    }

    @Override
    public T peak() {
        // Return null if the heap is empty
        return (data.length == 0) ? null : this.data[0];
    }

    @Override
    public T dequeue() {
        if (data.length == 0) {
            return null;
        }

        T maxValue = data[0];
        
        // Replace root with the last element and shrink the array
        data[0] = data[data.length - 1];
        T[] reducedData = (T[]) new Comparable[this.data.length - 1];
        for (int index = 0; index < this.data.length - 1; index++) {
            reducedData[index] = this.data[index];
        }
        this.data = reducedData;

        // Restore heap property by heapifying down
        int parentIdx = 0;
        while (true) {
            int largestIdx = parentIdx;
            
            // Check all children of the current node
            for (int childOffset = 1; childOffset <= d; childOffset++) {
                int childIdx = d * parentIdx + childOffset;
                
                if (childIdx < data.length && data[childIdx].compareTo(data[largestIdx]) > 0) {
                    largestIdx = childIdx;
                }
            }
            
            // If no swaps are needed, exit loop
            if (largestIdx == parentIdx) {
                break;
            }
            
            // Swap with the largest child
            T temp = data[parentIdx];
            data[parentIdx] = data[largestIdx];
            data[largestIdx] = temp;
            
            parentIdx = largestIdx;
        }
        
        return maxValue;
    }
}