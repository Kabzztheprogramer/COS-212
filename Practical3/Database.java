
public class Database<T extends Comparable<? super T>> {
    Container<T> firstContainer;
    int m;

    public Database(int m) {
        // impliment this function
        this.m = m ;
    }

    public Container<T> findContainer(int containerID) {
        // impliment this function
        Container<T> current = firstContainer;
        while ( current != null ) {
            int i = 0 ;
            
            while( i < current.keyCount && containerID > current.containerIDs[i]){
                ++i;
            }
            if( i < current.keyCount  && containerID == current.containerIDs[i]){
                return current;
            }
            if(current.isLeaf){
                return current;
            }
            current = current.children[i];

        }
        return null;
    }
public boolean containsID(int containerID, Container<T> current) {
    for (int i = 0; i < current.keyCount; i++) {
        if (containerID == current.containerIDs[i]) {
            return true;
        }
    }
    return false;
}

public int insertID(int containerID, Container<T> current) {
    int i = current.keyCount - 1;

    // Ensure we do not go out of bounds
    while (i >= 0 && containerID < current.containerIDs[i]) {
        current.containerIDs[i + 1] = current.containerIDs[i]; // Shift right
        i--;
    }

    current.containerIDs[i + 1] = containerID;
    current.keyCount++;
    return i + 1;
}

public Container<T> findParent(Container<T> node) {
    if (firstContainer == null || firstContainer == node) {
        return null; // Root has no parent
    }

    Container<T> parent = null;
    Container<T> current = firstContainer;

    while (current != null && !current.isLeaf) {
        for (int i = 0; i <= current.keyCount; i++) {
            if (current.children[i] == node) {
                return current; // Found parent
            }
            if (i < current.keyCount && node.containerIDs[0] < current.containerIDs[i]) {
                parent = current;
                current = current.children[i];
                break;
            } else if (i == current.keyCount) { // Last child case
                parent = current;
                current = current.children[i];
            }
        }
    }
    return parent;
}



public void splitIDs(int newID, int pos, int midID, Container<T> current) {
    int mid = (current.m / 2) - 1; // Middle index
    Container<T> leftContainer = new Container<>(current.m);
    Container<T> rightContainer = new Container<>(current.m);
    Container<T> parent = findParent(current);
    
    int leftIndex = 0, rightIndex = 0;

    // 🔹 Distribute existing keys between left and right (EXCLUDING the middle key)
    for (int i = 0; i < current.keyCount; i++) {
        if (i < mid) {
            leftContainer.containerIDs[leftIndex++] = current.containerIDs[i];
            leftContainer.keyCount++;
        } else if (i > mid) {  // Skip middle key
            rightContainer.containerIDs[rightIndex++] = current.containerIDs[i];
            rightContainer.keyCount++;
        }
    }

    // 🔹 Insert new ID into the correct split
    if (pos < mid) {
        insertID(newID, leftContainer);
    } else if (pos > mid) {
        insertID(newID, rightContainer);
    } 

    leftContainer.isLeaf = current.isLeaf;
    rightContainer.isLeaf = current.isLeaf;

    // 🔹 Distribute children if not a leaf
    if (!current.isLeaf) {
        for (int i = 0; i <= mid; i++) {
            leftContainer.children[i] = current.children[i];
        }
        for (int i = mid + 1, j = 0; i <= current.keyCount; i++, j++) {
            rightContainer.children[j] = current.children[i];
        }
    }

    if (parent == null) {  
        // 🔹 Root split: Create a new root
        firstContainer = new Container<>(current.m);
        firstContainer.containerIDs[0] = current.containerIDs[mid]; // Move middle key up
        firstContainer.children[0] = leftContainer;
        firstContainer.children[1] = rightContainer;
        firstContainer.keyCount = 1;
        firstContainer.isLeaf = false;
    } else {
        // 🔹 Insert middle key into parent
        int newPos = insertID(current.containerIDs[mid], parent);

        // 🔹 Shift children to make space
        for (int i = parent.keyCount; i > newPos + 1; i--) {
            parent.children[i] = parent.children[i - 1];
        }

        parent.children[newPos] = leftContainer;
        parent.children[newPos + 1] = rightContainer;

        // 🔹 If parent is full, recursively split it
        if (parent.isFull()) {
            splitIDs(parent.containerIDs[(parent.m / 2) - 1], newPos, parent.containerIDs[(parent.m / 2) - 1], parent);
        }
    }
}







public void addContainerRecord(int containerID) {
    if (firstContainer == null) {
        firstContainer = new Container<>(m);
        firstContainer.containerIDs[0] = containerID;
        firstContainer.keyCount = 1;
        firstContainer.isLeaf = true;
        return;
    }
    

    Container<T> current = findContainer(containerID);
    if (current == null || containsID(containerID, current)) {
        return; // Prevent duplicate entries
    }
   

    if (!current.isFull()) {
        insertID(containerID, current);
    } else {
        int i = 0;
        while (i < current.keyCount && containerID > current.containerIDs[i]) {
            i++;
        }
        

        int mid = (m / 2)-1;
        
        if (i == mid) {
            
            splitIDs(containerID, i, containerID, current);
        } else {
            
            splitIDs(containerID, i, current.containerIDs[mid], current);
            
        }
    }
}



public int countContainerIDs() {
    return countContainerIDs(firstContainer);
}

private int countContainerIDs(Container<T> node) {
    if (node == null) {
        return 0;
    }
    
    int total = node.keyCount;
    
    if (!node.isLeaf) {
        for (int i = 0; i <= node.keyCount; i++) { // Iterate through all children
            total += countContainerIDs(node.children[i]);
        }
    }
    
    return total;
}

public int countRecords() {
    return countRecords(firstContainer);
}

private int countRecords(Container<T> node) {
    if (node == null) {
        return 0;
    }
    
    int total = 1; // Count this node
    
    if (!node.isLeaf) {
        for (int i = 0; i <= node.keyCount; i++) { // Iterate correctly through children
            total += countRecords(node.children[i]);
        }
    }
    
    return total;
}


    //  Do not edit the below functions as it will negatively affect your mark
    public String printTree() {
        if (firstContainer == null) {
            return "Empty tree";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\nTree structure:\n");
        sb.append(printNode(firstContainer, "", true));
        return sb.toString();
    }

    private String printNode(Container<T> node, String prefix, boolean isTail) {
        if (node == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(isTail ? "└── " : "├── ").append(node.toString()).append("\n");

        if (!node.isLeaf) {
            // Print all children except the last
            for (int i = 0; i < node.keyCount && i < node.children.length - 1; i++) {
                if (node.children[i] != null) {
                    sb.append(printNode(node.children[i],
                            prefix + (isTail ? "    " : "│   "),
                            false));
                }
            }

            if (node.keyCount < node.children.length && node.children[node.keyCount] != null) {
                sb.append(printNode(node.children[node.keyCount],
                        prefix + (isTail ? "    " : "│   "),
                        true));
            }
        }
        return sb.toString();
    }

}
