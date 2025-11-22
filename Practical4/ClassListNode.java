public class ClassListNode {
    public Person data;
    public boolean isRed;
    public ClassListNode left;
    public ClassListNode right;

    public ClassListNode() {
        isRed = true;
    }

    public ClassListNode(Person data) {
        this.data = data;
        isRed = true;
    }

    @Override
    public String toString() {
        return (isRed ? "R" : "B") + "[" + String.valueOf(data) + "]";
    }

}
