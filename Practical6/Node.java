public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    public Node<T> left;
    public Node<T> right;
    public T data;

    public Node(T data) {
        this.data = data;
    }

    public String toString() {
        return data.toString();
    }

    public int compareTo(Node<T> o) {
        return data.compareTo(o.data);
    }

    public boolean equals(Node<T> obj) {
        return data.equals(obj.data);
    }
}
