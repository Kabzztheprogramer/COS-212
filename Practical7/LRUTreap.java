public class LRUTreap {

    public class LRUNode {
        int timestamp;
        LRUNode left, right;
        int priority = 1;

        LRUNode(int timestamp) {
            this.timestamp = timestamp ;
        }
    }

    public int capacity;
    public int size = 0;
    public LRUNode root;

    LRUTreap(int capacity) {
        this.capacity = capacity ;
    }

    // Get current size
    public int getSize() {
        return this.size;
    }

    // Get capacity
    public int getCapacity() {
        return this.capacity ;
    }

    // Check if the BST is full
    public boolean isFull() {
        
        return (this.capacity == this.size) ;
    }

    public boolean isLeaf(LRUNode LRUNode) {

        return (LRUNode.left == null && LRUNode.right == null) ;
    }

    private LRUNode findNode(LRUNode current, long timestamp) {
        if (current == null) {
            return null;
        }
    
        if (timestamp == current.timestamp) {
            return current;
        } else if (timestamp < current.timestamp) {
            return findNode(current.left, timestamp);
        } else {
            return findNode(current.right, timestamp);
        }
    }


    private LRUNode findParentNode(LRUNode current, int timestamp) {
        if (current == null || isLeaf(current)|| current == this.root) {
            return null;
        }
    
        if ((current.left != null && current.left.timestamp == timestamp) ||
            (current.right != null && current.right.timestamp == timestamp)) {
            return current;
        }
    
        if (timestamp < current.timestamp) {
            return findParentNode(current.left, timestamp);
        } else {
            return findParentNode(current.right, timestamp);
        }
    }
    
        

    public void accessLRUNode(long timestamp) {

        LRUNode current = findNode(root, timestamp);
        if (current == null) {
            return;
        }
        current.priority++;
        balanceAfterPriorityIncrease(current);
       

    }

    private LRUNode balanceAfterPriorityIncrease(LRUNode root) {

        if (root == null) return null;
        
        LRUNode parent = findParentNode(this.root, root.timestamp);
        
        while (parent != null) {
            if (root.priority > parent.priority ||
                (root.priority == parent.priority && root.timestamp < parent.timestamp)) {
                doRotation(root);
                // After rotation, re-evaluate parent from the root again
                parent = findParentNode(this.root, root.timestamp);
            } else {
                break; // Heap property satisfied
            }
        }
        return this.root ;
    }

    void doRotation(LRUNode node){

        LRUNode parent = findParentNode(this.root, node.timestamp);

        if (parent.left == node) {
            // Right rotation
            if (parent == this.root) {
                this.root = rightRotate(parent);
            } else {
                LRUNode grandparent = findParentNode(this.root, parent.timestamp);
                if (grandparent != null) {
                    if (grandparent.left == parent) {
                        grandparent.left = rightRotate(parent);
                    } else if (grandparent.right == parent) {
                        grandparent.right = rightRotate(parent);
                    }
                }
            }
        } else if (parent.right == this.root) {
            // Left rotation
            if (parent == this.root) {
                this.root = leftRotate(parent);
            } else {
                LRUNode grandparent = findParentNode(this.root, parent.timestamp);
                if (grandparent != null) {
                    if (grandparent.left == parent) {
                        grandparent.left = leftRotate(parent);
                    } else if (grandparent.right == parent) {
                        grandparent.right = leftRotate(parent);
                    }
                }
            }
        }
    }

    private LRUNode rightRotate(LRUNode y) {
        
        if (y == null || y.left == null) {
            return y; // Cannot rotate
        }
    
        LRUNode x = y.left;
        LRUNode T2 = x.right;
    
        x.right = y;
        y.left = T2;
    
        return x;

    }

    private LRUNode leftRotate(LRUNode x) {

        if (x== null || x.right == null) {
            return x; // Cannot rotate
        }
    
        LRUNode y = x.right;
        LRUNode T2 = y.left;
    
        y.left = x;
        x.right = T2;
        return y;
    }

    public void addToLRU(int timestamp) {
        LRUNode existing = findNode(root, timestamp);

        if (existing != null) {
            existing.priority++;
            balanceAfterPriorityIncrease(existing);
            return;
        }
    
        if (isFull()) {
            LRUNode toEvict = findLeastPriorityOldestLRUNode();
            System.out.println("Evicting LRUNode: timestamp = " +  toEvict.timestamp + ", priority = " + toEvict.priority);
            evictFromLRU(toEvict.timestamp);
            size--;
        }
    
        LRUNode toAdd = new LRUNode(timestamp);
        root = insert(root, toAdd);
        size++;
    }
    
    private LRUNode insert(LRUNode current, LRUNode toInsert) {
        if (current == null) return toInsert;
    
        if (toInsert.timestamp < current.timestamp) {
            current.left = insert(current.left, toInsert);
            if (current.left.priority > current.priority) {
                current = rightRotate(current);
            }
        } else {
            current.right = insert(current.right, toInsert);
            if (current.right.priority > current.priority) {
                current = leftRotate(current);
            }
        }
    
        return current;
    }


    public void evictFromLRU(int timestamp) {
        this.root = deleteNode(this.root, timestamp);
    }

    private LRUNode deleteNode(LRUNode node, int timestamp) {
        if (node == null) return null;
    
        if (timestamp < node.timestamp) {
            node.left = deleteNode(node.left, timestamp);
        } else if (timestamp > node.timestamp) {
            node.right = deleteNode(node.right, timestamp);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                if (node.left.priority > node.right.priority) {
                    node = rightRotate(node);
                    node.right = deleteNode(node.right, timestamp);
                } else {
                    node = leftRotate(node);
                    node.left = deleteNode(node.left, timestamp);
                }
            }
        }
    
        return node;
    }
    

    public LRUNode findLeastPriorityOldestLRUNode() {
        return findLeastPriorityOldestLRUNode(root);
    }

    private LRUNode findLeastPriorityOldestLRUNode(LRUNode node) {
        if (node == null) return null;
    
        LRUNode left = findLeastPriorityOldestLRUNode(node.left);
        LRUNode right = findLeastPriorityOldestLRUNode(node.right);
    
        LRUNode min = node;
    
        if (left != null) {
            if (left.priority < min.priority || 
                (left.priority == min.priority && left.timestamp < min.timestamp)) {
                min = left;
            }
        }
    
        if (right != null) {
            if (right.priority < min.priority || 
                (right.priority == min.priority && right.timestamp < min.timestamp)) {
                min = right;
            }
        }
    
        return min;
    }

    // PLEASE DO NOT MODIFY ANY OF THE CODE BELOW AS THIS WILL NEGATIVELY IMPACT
    // YOUR MARKS
    String printTree() {
        StringBuilder sb = new StringBuilder();
        buildTreeString(root, 0, sb, "", "");
        return sb.toString();
    }

    private void buildTreeString(LRUNode LRUNode, int level, StringBuilder sb, String prefix, String childrenPrefix) {
        if (LRUNode == null)
            return;

        // Add current LRUNode to the string
        sb.append(prefix);
        sb.append(LRUNode.timestamp).append(" (p:").append(LRUNode.priority).append(")");
        sb.append('\n');

        // Create prefixes for children
        // When both children exist, use different prefixes
        if (LRUNode.left != null && LRUNode.right != null) {
            buildTreeString(LRUNode.right, level + 1, sb, childrenPrefix + "├── ", childrenPrefix + "│   ");
            buildTreeString(LRUNode.left, level + 1, sb, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
        // When only right child exists
        else if (LRUNode.right != null) {
            buildTreeString(LRUNode.right, level + 1, sb, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
        // When only left child exists
        else if (LRUNode.left != null) {
            buildTreeString(LRUNode.left, level + 1, sb, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
    }

}


