import java.util.*;

public class Bst<Key extends Comparable<Key>, Value> {
    public Node root;

    public class Node {
        public Key key;
        public Value val;
        public Node left, right;
        private int N; // size of subtree including this node

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(x.left, key);
        else if (cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {

        if (x.left == null)
            return x;
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null)
            return x;
        return max(x.right);
    }

    public Key floor(Key key) {
        var n = floor(root, key);
        if (n == null)
            return null;
        return n.key;
    }

    /*
     * Returns greatest Node with key smaller or equal to @param key
     * 
     * @param x Node to search in
     * 
     * @param key key to compare and look for
     */
    private Node floor(Node x, Key key) {
        if (x == null)
            return null;
        var cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp < 0)
            return floor(x.left, key);
        var right = floor(x.right, key);
        if (right != null)
            return right;
        else
            return x;
    }

    /*
     * Returns smallest key, larger than or equal to key
     */
    public Key ceiling(Key key) {
        var n = ceiling(root, key);
        if (n == null)
            return null;
        return n.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null)
            return null;
        var cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp > 0)
            return ceiling(x.right, key);
        var left = floor(x.left, key);
        if (left != null)
            return left;
        else
            return x;
    }

    /*
     * Select node which has exactly n nodes before it.
     */
    public Node select(int n) {
        return select(root, n);
    }

    private Node select(Node x, int n) {
        if (x == null)
            return null;
        var t = size(x.left);
        if (t > n)
            return select(x.left, n);
        else if (t < n)
            return select(x.right, n-t-1);
        else return x;
    }

    /*
     * Return number of keys lesser than key
     */
    public int rank(Key k) {
        return rank(k,root);
    }

    private int rank(Key k, Node x) {
        if (x == null)
            return 0;
        int cmp = k.compareTo(x.key);
        if (cmp < 0)
            return rank(k, x.left);
        else if (cmp > 0)
            return 1 + size(x.left) + rank(k, x.right);
        else return size(x.left);
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key k) {
        root = delete(root, k);
    }

    private Node delete(Node x, Key k) {
        if (x == null) return null;
        int cmp = k.compareTo(x.key);
        if (cmp < 0) return delete(x.left, k);
        else if (cmp > 0) return delete(x.right, k);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void print() {
        print(root);
    }

    private void print(Node x) {
        if (x == null)
            return;
        print(x.left);
        System.out.println(x.key);
        print(x.right);
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, min(), max());
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null)
            return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0)
            keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && 0 <= cmphi)
            queue.add(x.key);
        if (cmphi > 0)
            keys(x.right, queue, lo, hi);
    }
}
