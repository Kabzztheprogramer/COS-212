public abstract class MaxHeap<T extends Comparable<T>> {
    public abstract void enqueue(T newValue);

    public abstract T peak();

    public abstract T dequeue();
}