public class ClassList {
    public ClassListNode root;

    public ClassList() {
        root = null;
    }

    public ClassList(String input) {
        root = ClassListUtility.createFromStr(input);
    }

    @Override
    public String toString() {
        return ClassListUtility.toString(this);
    }

    public ClassListNode findPerson(String fullName) {
        ClassListNode temp = root;
        Person sPerson = new Person(fullName);
    
        while (temp != null) {
            int comparison = sPerson.compareTo(temp.data);
            if (comparison == 0) {
                return temp; 
            } else if (comparison < 0) {
                temp = temp.left;  
            } else {
                temp = temp.right;  
            }
        }
        return null;  
    }
    

    public void addPerson(String fullName) {

        Person Ptoadd = new Person(fullName);
        ClassListNode newNode = new ClassListNode(Ptoadd);
        if (this.root == null) { 
            this.root = newNode;
            newNode.isRed = false;
            return;
        }
        ClassListNode temp = this.root;
        ClassListNode parent = null;
        while (temp != null) {
            parent = temp;
            if (Ptoadd.compareTo(temp.data) < 0) {
                temp = temp.left;
            } else if (Ptoadd.compareTo(temp.data) > 0) {
                temp = temp.right;
            } else {
                return;
            }
        }
        if (Ptoadd.compareTo(parent.data) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        newNode.isRed = true;
        //Checking for Violations (Fix if Needed)
        fixViolations(newNode);

    }
    private void fixViolations(ClassListNode node) {
        while (node != root && node.isRed && findParent(node).isRed) {
            ClassListNode parent = findParent(node);
            ClassListNode grandparent = findParent(parent);
    
            if (grandparent != null) {
                if (parent == grandparent.left) {
                    ClassListNode uncle = grandparent.right;
                    if (uncle != null && uncle.isRed) {
                        // Case 1: Uncle is red (Recoloring)
                        parent.isRed = false;
                        uncle.isRed = false;
                        grandparent.isRed = true;
                        node = grandparent;
                    } else {
                        // Case 2: Node is right child (Left rotation needed)
                        if (node == parent.right) {
                            rotateLeft(parent);
                            node = parent;
                            parent = findParent(node);
                        }
                        // Case 3: Node is left child (Right rotation needed)
                        parent.isRed = false;
                        grandparent.isRed = true;
                        rotateRight(grandparent);
                    }
                } else {
                    ClassListNode uncle = grandparent.left;
                    if (uncle != null && uncle.isRed) {
                        // Case 1: Uncle is red (Recoloring)
                        parent.isRed = false;
                        uncle.isRed = false;
                        grandparent.isRed = true;
                        node = grandparent;
                    } else {
                        // Case 2: Node is left child (Right rotation needed)
                        if (node == parent.left) {
                            rotateRight(parent);
                            node = parent;
                            parent = findParent(node);
                        }
                        // Case 3: Node is right child (Left rotation needed)
                        parent.isRed = false;
                        grandparent.isRed = true;
                        rotateLeft(grandparent);
                    }
                }
            }
        }
        root.isRed = false;
    }
    

    private void rotateRight(ClassListNode node) {
        ClassListNode leftChild = node.left;
        node.left = leftChild.right;
    
        if (leftChild.right != null) {
            leftChild.right = node;
        }
    
        ClassListNode parent = findParent(node);
        if (parent == null) {
            root = leftChild;
        } else if (node == parent.right) {
            parent.right = leftChild;
        } else {
            parent.left = leftChild;
        }
    
        leftChild.right = node;
    }
    
    
    private void rotateLeft(ClassListNode node) {
        ClassListNode rightChild = node.right;
        node.right = rightChild.left;
    
        if (rightChild.left != null) {
            rightChild.left = node;
        }
    
        ClassListNode parent = findParent(node);
        if (parent == null) {
            root = rightChild;
        } else if (node == parent.left) {
            parent.left = rightChild;
        } else {
            parent.right = rightChild;
        }
    
        rightChild.left = node;
    }
    
    

    public ClassListNode findParent(ClassListNode node) {
        if (root == null || node == root) {
            return null;
        }
        ClassListNode temp = root;
        while (temp != null) {
            if (node == temp.left || node == temp.right) {
                return temp; // Directly return the parent
            }
            if (node.data.compareTo(temp.data) < 0) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        return null;
    }
    
    public void removePerson(String fullName) {
        ClassListNode nodeToRemove = findPerson(fullName);
        if (nodeToRemove == null) {
            return;
        }
        if (nodeToRemove.left != null && nodeToRemove.right != null) {
            ClassListNode successor = findMin(nodeToRemove.right);
            nodeToRemove.data = successor.data;
            nodeToRemove = successor; 
        }
    
        ClassListNode child = (nodeToRemove.left != null) ? nodeToRemove.left : nodeToRemove.right;
    
        if (child != null) {
            replaceNode(nodeToRemove, child);
            if (!nodeToRemove.isRed) { 
                fixDeletion(child);
            }
        } else {
            if (!nodeToRemove.isRed) { 
                fixDeletion(nodeToRemove);
            }
            replaceNode(nodeToRemove, null);
        }
    }
    
    
    // private void deleteNode(ClassListNode node) {
    //     if (node.left != null && node.right != null) {
    //         ClassListNode successor = findMin(node.right);
    //         node.data = successor.data;
    //         node = successor;
    //     }
        
    //     ClassListNode child = (node.left != null) ? node.left : node.right;
    //     if (!node.isRed) {
    //         node.isRed = (child != null && child.isRed);
    //         fixDeletion(node);
    //     }
        
    //     replaceNode(node, child);
        
    //     if (node == root && child != null) {
    //         child.isRed = false;
    //     }
    // }
    
    private void fixDeletion(ClassListNode node) {
        if (node == null) {
            System.out.println("fixDeletion called on a null node.");
            return;
        }
    
        while (node != root && !node.isRed) {
            ClassListNode parent = findParent(node);
            if (parent == null) {
                System.out.println("Parent is null, breaking out.");
                break;
            }
            
            ClassListNode sibling = (node == parent.left) ? parent.right : parent.left;
    
            if (sibling == null) {
                System.out.println("Sibling is null, continuing to parent.");
                node = parent;
                continue;
            }
    
            if (sibling.isRed) {
                sibling.isRed = false;
                parent.isRed = true;
                if (node == parent.left) {
                    rotateLeft(parent);
                } else {
                    rotateRight(parent);
                }
                sibling = (node == parent.left) ? parent.right : parent.left;
            }
    
            if ((sibling.left == null || !sibling.left.isRed) &&
                (sibling.right == null || !sibling.right.isRed)) {
                sibling.isRed = true;
                node = parent;
            } else {
                if (node == parent.left) {
                    if (sibling.right == null || !sibling.right.isRed) {
                        if (sibling.left != null) sibling.left.isRed = false;
                        sibling.isRed = true;
                        rotateRight(sibling);
                        sibling = parent.right;
                    }
                    sibling.isRed = parent.isRed;
                    parent.isRed = false;
                    if (sibling.right != null) sibling.right.isRed = false;
                    rotateLeft(parent);
                } else {
                    if (sibling.left == null || !sibling.left.isRed) {
                        if (sibling.right != null) sibling.right.isRed = false;
                        sibling.isRed = true;
                        rotateLeft(sibling);
                        sibling = parent.left;
                    }
                    sibling.isRed = parent.isRed;
                    parent.isRed = false;
                    if (sibling.left != null) sibling.left.isRed = false;
                    rotateRight(parent);
                }
                node = root;
            }
        }
        if (node != null) node.isRed = false;
    }
    
    
    private void replaceNode(ClassListNode node, ClassListNode child) {
        ClassListNode parent = findParent(node);
        if (parent == null) {
            root = child;
        } else if (node == parent.left) {
            parent.left = child;
        } else {
            parent.right = child;
        }
        if (child != null) {
            child.isRed = node.isRed;
        }
    }
    
    private ClassListNode findMin(ClassListNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

}
