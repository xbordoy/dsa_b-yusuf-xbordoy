import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


//AVL Tree Node
class AVLNode {

    AVLNode left, right;
    int data;
    int height;

    public AVLNode() {
        left = null;
        right = null;
        data = 0;
        height = 0;
    }

    public AVLNode(int n) {
        left = null;
        right = null;
        data = n;
        height = 0;
    }
}// AVL Tree Class
class AVLTree {
    private AVLNode root;
    public AVLTree() {
        root = null;
    }


    /**
     *
     * @param avlNode
     * @return
     */
    private int height(AVLNode avlNode) {
        return avlNode == null ? -1 : avlNode.height;
    }

    /**
     *
     * @param lHeight
     * @param rHeight
     * @return
     */
    private int max(int lHeight, int rHeight) {
        return lHeight > rHeight ? lHeight : rHeight;
    }

    /**
     *
     * @param data
     */
    public void insert(int data) {
        root = insert(data, root);
    }


    /**
     *
     * @param data
     * @param avlNode
     * @return
     */
    private AVLNode insert(int data, AVLNode avlNode) {
        if (avlNode == null)
            avlNode = new AVLNode(data);
        else if (data < avlNode.data) {
            avlNode.left = insert(data, avlNode.left);
            if (height(avlNode.left) - height(avlNode.right) == 2)
                if (data < avlNode.left.data)
                    avlNode = leftRotation(avlNode);
                else
                    avlNode = leftRightRotation(avlNode);
        } else if (data > avlNode.data) {
            avlNode.right = insert(data, avlNode.right);
            if (height(avlNode.right) - height(avlNode.left) == 2)
                if (data > avlNode.right.data)
                    avlNode = rightRotation(avlNode);
                else
                    avlNode = rightLeftRotation(avlNode);
        } else
        ; // Duplicate; do nothing
        avlNode.height = max(height(avlNode.left), height(avlNode.right)) + 1;
        return avlNode;
    }

    /**
     *
     * @param avlNode
     * @return
     */
    private AVLNode leftRotation(AVLNode avlNode) {
        AVLNode k1 = avlNode.left;
        avlNode.left = k1.right;
        k1.right = avlNode;
        avlNode.height = max(height(avlNode.left), height(avlNode.right)) + 1;
        k1.height = max(height(k1.left), avlNode.height) + 1;
        return k1;
    }


    /**
     *
     * @param avlNode
     * @return
     */
    private AVLNode rightRotation(AVLNode avlNode) {
        AVLNode node = avlNode.right;
        avlNode.right = node.left;
        node.left = avlNode;
        avlNode.height = max(height(avlNode.left), height(avlNode.right)) + 1;
        node.height = max(height(node.right), avlNode.height) + 1;
        return node;
    }
    /**
     * left-right rotation
     * @param avlNode
     * @return
     */
    private AVLNode leftRightRotation(AVLNode avlNode) {
        avlNode.left = rightRotation(avlNode.left);
        return leftRotation(avlNode);
    }

    /**
     * right-left rotation
     * @param avlNode
     * @return
     */
    private AVLNode rightLeftRotation(AVLNode avlNode) {
        avlNode.right = leftRotation(avlNode.right);
        return rightRotation(avlNode);
    }

    /**
     *
     * @return
     */
    public int countNodes() {
        return countNodes(root);
    }

    /**
     *
     * @param avlNode
     * @return
     */
    private int countNodes(AVLNode avlNode) {
        if (avlNode == null)
            return 0;
        else {
            int l = 1;
            l += countNodes(avlNode.left);
            l += countNodes(avlNode.right);
            return l;
        }
    }

    /**
     *
     * @param data
     * @return
     */
    public boolean search(int data) {
        return search(root, data);
    }

    /**
     *
     * @param avlNode
     * @param data
     * @return
     */
    private boolean search(AVLNode avlNode, int data) {
        boolean found = false;
        while ((avlNode != null) && !found) {
            int rval = avlNode.data;
            if (data < rval)
                avlNode = avlNode.left;
            else if (data > rval)
                avlNode = avlNode.right;
            else {
                found = true;
                break;
            }
            found = search(avlNode, data);
        }
        return found;
    }

    /**
     *
     */
    public void inorder() {
        inorder(root);
    }

    /**
     *
     * @param avlNode
     */
    private void inorder(AVLNode avlNode) {
        if (avlNode != null) {
            inorder(avlNode.left);
            System.out.print(avlNode.data + " ");
            inorder(avlNode.right);
        }
    }


  
    // Driver code
    private static final int MAX_INSERTS = 1000000;
    private static final String CSV_FILENAME = "insert_benchmark.csv";

    public static void main(String[] args) {
        
        AVLTree tree = new AVLTree();
        int numInserts = 100000; // number of insert operations to perform
        long startTime, endTime, elapsedTime;
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            FileWriter writer = new FileWriter(CSV_FILENAME);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (int i = 0; i < numInserts; i++) {
                int value = random.nextInt();
                tree.insert(tree.root, value);

                elapsedTime = (System.nanoTime() - startTime) / 1000000;

                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");

                // print results
                System.out.println("Time taken for " + numInserts + " insert operations: " + elapsedTime + " nanoseconds");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}