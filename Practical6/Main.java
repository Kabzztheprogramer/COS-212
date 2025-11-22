public class Main {
    // Output in output.txt
    public static void main(String[] args) {
        DHeap<Integer> dheap = new DHeap<>(3);
        LeftistHeap<Integer> leftistHeap = new LeftistHeap<>();
        SkewHeap<Integer> skewHeap = new SkewHeap<>();

        // Insert elements into the heaps
        for (int i = 1; i < 4; i++) {
            dheap.enqueue(i * i);
            dheap.enqueue(-1 * i * i);
            leftistHeap.enqueue(i * i);
            leftistHeap.enqueue(-1 * i * i);
            skewHeap.enqueue(i * i);
            skewHeap.enqueue(-1 * i * i);
        }
        
        // Print initial heap states
        System.out.println("Initial DHeap:");
        System.out.println(dheap);
        System.out.println("Initial LeftistHeap:");
        System.out.println(leftistHeap);
        System.out.println("Initial SkewHeap:");
        System.out.println(skewHeap);
        
        // Test peek function
        System.out.println("DHeap Peek: " + dheap.peak());
        System.out.println("LeftistHeap Peek: " + leftistHeap.peak());
        System.out.println("SkewHeap Peek: " + skewHeap.peak());
        
        // Test dequeue function
        System.out.println("Dequeue from DHeap: " + dheap.dequeue());
        System.out.println("Dequeue from LeftistHeap: " + leftistHeap.dequeue());
        System.out.println("Dequeue from SkewHeap: " + skewHeap.dequeue());
        
        // Print heaps after one dequeue operation
        System.out.println("DHeap after one dequeue:");
        System.out.println(dheap);
        System.out.println("LeftistHeap after one dequeue:");
        System.out.println(leftistHeap);
        System.out.println("SkewHeap after one dequeue:");
        System.out.println(skewHeap);
        
        // Further testing with additional elements
        dheap.enqueue(100);
        leftistHeap.enqueue(100);
        skewHeap.enqueue(100);
        
        dheap.enqueue(-50);
        leftistHeap.enqueue(-50);
        skewHeap.enqueue(-50);
        
        System.out.println("DHeap after adding 100 and -50:");
        System.out.println(dheap);
        System.out.println("LeftistHeap after adding 100 and -50:");
        System.out.println(leftistHeap);
        System.out.println("SkewHeap after adding 100 and -50:");
        System.out.println(skewHeap);
    }
}

