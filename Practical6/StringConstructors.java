public class StringConstructors {

    public static DHeap<Integer> stringToIntegerDHeap(String input) {
        String[] split = input.split(":");
        int d = Integer.valueOf(split[0]);

        if (input.contains("Empty Heap")) {
            return new DHeap<Integer>(d);
        }
        DHeap<Integer> res = new DHeap<Integer>(d);
        res.data = stringToIntegerDHeapHelper(split[2], 0, new Integer[Integer.valueOf(split[1])], d);
        return res;
    }

    public static LeftistHeap<Integer> stringToIntegerLeftistHeap(String input) {
        LeftistHeap<Integer> ret = new LeftistHeap<>();
        ret.root = stringToIntegerHeapHelper(input);
        return ret;
    }

    public static SkewHeap<Integer> stringToIntegerSkewHeap(String input) {
        SkewHeap<Integer> ret = new SkewHeap<>();
        ret.root = stringToIntegerHeapHelper(input);
        return ret;
    }

    private static Integer[] stringToIntegerDHeapHelper(String input, int index, Integer[] current, int d) {
        if (input.equals("{}")) {
            return current;
        }
        input = input.substring(1, input.length() - 1);
        current[index] = Integer.valueOf(input.substring(0, input.indexOf('{')));
        input = input.substring(input.indexOf('{'));
        int i = 0;
        while (input.length() > 0) {
            String child = "";
            int numBraces = 0;
            do {
                child += input.charAt(0);
                if (input.charAt(0) == '{') {
                    numBraces++;
                } else if (input.charAt(0) == '}') {
                    numBraces--;
                }
                input = input.substring(1);
            } while (numBraces != 0);
            current = stringToIntegerDHeapHelper(child, d * index + i + 1, current, d);
            i++;
        }
        return current;
    }

    private static Node<Integer> stringToIntegerHeapHelper(String input) {
        if (input.equals("{}")) {
            return null;
        }
        input = input.substring(1, input.length() - 1);
        Node<Integer> newNode = new Node<Integer>(Integer.valueOf(input.substring(0, input.indexOf('{'))));
        input = input.substring(input.indexOf('{'));
        String child = "";
        int numBraces = 0;
        do {
            child += input.charAt(0);
            if (input.charAt(0) == '{') {
                numBraces++;
            } else if (input.charAt(0) == '}') {
                numBraces--;
            }
            input = input.substring(1);
        } while (numBraces != 0);
        newNode.left = stringToIntegerHeapHelper(child);
        newNode.right = stringToIntegerHeapHelper(input);
        return newNode;
    }
}
