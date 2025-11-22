public class Queue {
    private class Node {
        public Integer data;
        public Node next;

        public Node(Integer d) {
            data = d;
            next = null;
        }
    }

    private Node head, tail;

    public Queue() {
        /*
         * Creates an empty Queue
         */
        head = null;
        tail = null;
    }

    public void enqueue(Integer data) {
        /*
         * Adds a value to the end of the queue
         */
        if (head == null) {
            head = new Node(data);
            tail = head;
        } else {
            tail.next = new Node(data);
            tail = tail.next;
        }
    }

    public Integer dequeue() {
        /*
         * Returns and removes the first value in the queue
         */
        if (head == null) {
            return null;
        }
        Integer val = head.data;

        head = head.next;
        if (head == null) {
            tail = null;
        }

        return val;
    }

    public Integer peak() {
        /*
         * Returns the first value in the queue
         */
        if (head == null) {
            return null;
        }
        return head.data;
    }

    public boolean contains(Integer value) {
        /*
         * Returns true if the passed-in value is in the queue.
         * Returns false otherwise
         */
        Node ptr = head;
        while (ptr != null) {
            if (ptr.data.equals(value)) {
                return true;
            }
            ptr = ptr.next;
        }
        return false;
    }
}
