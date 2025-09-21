class Node {
    int[] keys;
    Node[] children;
    int count;
    boolean leaf;

    public Node() {
        keys = new int[3];
        children = new Node[4];
        count = 0;
        leaf = true;
    }
}

class TwoFourTree {
    Node root;

    public TwoFourTree() {
        root = new Node();
    }

    public void insert(int key) {
        if (root.count == 3) {
            Node newRoot = new Node();
            newRoot.leaf = false;
            newRoot.children[0] = root;
            splitChild(newRoot, 0);
            insertNonFull(newRoot, key);
            root = newRoot;
        } else {
            insertNonFull(root, key);
        }
    }

    private void splitChild(Node parent, int index) {
        Node child = parent.children[index];
        Node newChild = new Node();
        newChild.leaf = child.leaf;

        for (int i = 0; i < 2; i++) {
            newChild.keys[i] = child.keys[i + 2];
            newChild.count++;
        }

        if (!child.leaf) {
            for (int i = 0; i < 2; i++) {
                newChild.children[i] = child.children[i + 2];
            }
        }

        child.count = 1;
        for (int i = parent.count; i > index; i--) {
            parent.children[i + 1] = parent.children[i];
        }
        parent.children[index + 1] = newChild;

        for (int i = parent.count - 1; i >= index; i--) {
            parent.keys[i + 1] = parent.keys[i];
        }
        parent.keys[index] = child.keys[1];
        parent.count++;
    }


    private void insertNonFull(Node node, int key) {
        int i = node.count - 1;

        if (node.leaf) {
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.count++;
        } else {
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;
            if (node.children[i].count == 3) {
                splitChild(node, i);
                if (key > node.keys[i]) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(Node node, int key) {
        int i = 0;
        while (i < node.count && key > node.keys[i]) {
            i++;
        }
        if (i < node.count && key == node.keys[i]) {
            return true;
        }
        if (node.leaf) {
            return false;
        }
        return search(node.children[i], key);
    }


    public void print() {
        print(root);
    }

    private void print(Node node) {
        for (int i = 0; i < node.count; i++) {
            if (!node.leaf) {
                print(node.children[i]);
            }
            System.out.print(node.keys[i] + " ");
        }
        if (!node.leaf) {
            print(node.children[node.count]);
        }
    }

    public static void main(String[] args) {
        TwoFourTree tree = new TwoFourTree();

        tree.insert(8);
        tree.insert(10);
        tree.insert(7);
        tree.insert(9);
        tree.insert(6);
        tree.insert(5);
        tree.insert(1);

        System.out.print("Tree keys in order: ");
        tree.print();

        System.out.println();
        System.out.println("Search for 30: " + tree.search(30));
        System.out.println("Search for 100: " + tree.search(100));

        tree.print();
    }


}
