public class Container<T extends Comparable<? super T>> {
    final int m;

    boolean isLeaf = true;
    int keyCount = 0;
    Integer[] containerIDs;
    Container<T>[] children;

    @SuppressWarnings("unchecked")
    public Container(int order) {
        // impliment this function
        this.m = order ;
        this.containerIDs = new Integer[order-1] ;
        this.children = new Container[order];
    }


    // Do not edit the below function as it will negatively affect your mark
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;

        // Add container IDs
        for (int i = 0; i < keyCount; i++) {
            if (!first) {
                sb.append(",");
            }
            sb.append(containerIDs[i]);
            first = false;
        }
        sb.append("]");

        if (isLeaf) {
            sb.append(" {");
            sb.append("leaf");
            sb.append("}");
        }

        return sb.toString();
    }

    public boolean isFull() {
        //impliment this function
        if(keyCount == m-1){
            return true ;
        }
        return false ;
    }
}