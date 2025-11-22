public class ClassListUtility {
    /*
     * =============================================================================
     * These are some provided Utility functions which you can use.
     * Don't modify anything in this file since it will be used on Fitchfork to mark
     * =============================================================================
     */
    public static ClassListNode createFromStr(String input) {
        if (input.contains("Empty Tree") || input.contains("False")) {
            return null;
        }
        if (input.contains("True")) {
            String[] inp = input.split("True");
            return createFromStr(inp[1]);
        }

        input = input.substring(1, input.length() - 1);
        if (input.equals("")) {
            return null;
        }
        ClassListNode retVal = new ClassListNode();
        if (input.charAt(0) == 'B') {
            retVal.isRed = false;
        }
        input = input.substring(1);
        retVal.data = new Person(input.substring(1, input.indexOf('{') - 1));
        input = input.substring(input.indexOf('{') + 1);
        String leftChild = "{";
        int numBraces = 1;
        while (numBraces != 0) {
            leftChild += input.charAt(0);
            if (input.charAt(0) == '{') {
                numBraces++;
            } else if (input.charAt(0) == '}') {
                numBraces--;
            }
            input = input.substring(1);
        }
        retVal.left = createFromStr(leftChild);
        retVal.right = createFromStr(input);
        return retVal;
    }

    public static String toString(ClassList list) {
        return (list.root == null ? "Empty Tree" : toStringOneLine(list) + "\n" + toString(list.root, "", true));
    }

    public static int length(ClassList list) {
        return numNodes(list.root);
    }

    public static String toStringOneLine(ClassList list) {
        return (list.root == null ? "0True{}"
                : String.valueOf(numNodes(list.root)) + (validRBTree(list) ? "True" : "False")
                        + toStringOneLine(list.root));
    }

    public static boolean validRBTree(ClassList list) {
        if (list.root == null) {
            return true;
        }
        if (!validBST(list)) {
            return false;
        }
        if (list.root.isRed) {
            return false;
        }
        if (hasConsecutiveReds(list.root)) {
            return false;
        }
        ClassListNode ptr = list.root;
        int numBlacks = 0;
        while (ptr != null) {
            if (!ptr.isRed) {
                numBlacks++;
            }
            ptr = ptr.left;
        }
        return !blackPathLengthIncorrect(list.root, 0, numBlacks);
    }

    /*
     * =============================================================================
     * Everything below this line is private helper functions, which should not be
     * called outside this class
     * =============================================================================
     */

    private static String toString(ClassListNode node, String prefix, boolean end) {
        String res = "";
        if (node.right != null) {
            res += toString(node.right, prefix + (end ? "│   " : "    "), false);
        }
        res += prefix + (end ? "└── " : "┌── ") + node.toString() + "\n";
        if (node.left != null) {
            res += toString(node.left, prefix + (end ? "    " : "│   "), true);
        }
        return res;
    }

    private static int numNodes(ClassListNode ptr) {
        if (ptr == null) {
            return 0;
        }
        return 1 + numNodes(ptr.left) + numNodes(ptr.right);
    }

    private static String toStringOneLine(ClassListNode ptr) {
        if (ptr == null) {
            return "{}";
        }
        return "{" + ptr.toString() + toStringOneLine(ptr.left)
                + toStringOneLine(ptr.right) + "}";
    }

    private static boolean validBST(ClassList list) {
        Person[] inorder = inorderTraversal(list.root, new Person[length(list)]);
        for (int i = 1; i < inorder.length; i++) {
            if (inorder[i].compareTo(inorder[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    private static Person[] inorderTraversal(ClassListNode ptr, Person[] ans) {
        if (ptr.left != null) {
            ans = inorderTraversal(ptr.left, ans);
        }
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] == null) {
                ans[i] = ptr.data;
                break;
            }
        }
        if (ptr.right != null) {
            ans = inorderTraversal(ptr.right, ans);
        }
        return ans;
    }

    private static boolean hasConsecutiveReds(ClassListNode ptr) {
        if (ptr.isRed) {
            if (ptr.left != null && ptr.left.isRed) {
                return true;
            }
            if (ptr.right != null && ptr.right.isRed) {
                return true;
            }
        }
        if (ptr.left != null && hasConsecutiveReds(ptr.left)) {
            return true;
        }
        if (ptr.right != null && hasConsecutiveReds(ptr.right)) {
            return true;
        }
        return false;
    }

    private static boolean blackPathLengthIncorrect(ClassListNode ptr, int currentValue, int correctAnswer) {
        if (ptr == null) {
            return false;
        }
        if (!ptr.isRed) {
            currentValue++;
        }
        if (ptr.left == null || ptr.right == null) {
            if (currentValue != correctAnswer) {
                return true;
            }
        }
        if (ptr.left != null && blackPathLengthIncorrect(ptr.left, currentValue, correctAnswer)) {
            return true;
        }
        if (ptr.right != null && blackPathLengthIncorrect(ptr.right, currentValue, correctAnswer)) {
            return true;
        }
        return false;
    }
}
