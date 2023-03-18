// Helper class to run the AVL code
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Old_AVL_Tree {
    private static final String InsertBenchmarkCSV = "insert_benchmark.csv";
    private static final String SearchBenchmarkCSV = "search_benchmark.csv";
    private static final String DeleteBenchmarkCSV = "delete_benchmark.csv";
    
    static Scanner scanner = new Scanner(System.in);
    static OLD_AVLTree avlTree = new OLD_AVLTree();

    public static void main(String[] args) {
        char ch;
        do {
            System.out.println("\nAVLTree Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. insert_benchmark");
            System.out.println("3. search");
            System.out.println("4. search_benchmark");
            System.out.println("5. delete_benchmark");
            System.out.println("6. count nodes");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter integer element to insert");
                    avlTree.insert(scanner.nextInt());
                    break;
                
                case 2:
                    System.out.println("Enter integer element to insert");
                    insert_benchmark(scanner.nextInt());
                    break;

                case 3:
                    System.out.println("Enter integer element to search");
                    System.out.println("Search result : " + avlTree.search(scanner.nextInt()));
                    break;

                case 4:
                    System.out.println("Enter integer element to insert");
                    search_benchmark(scanner.nextInt());
                    break;

                case 5:
                    System.out.println("Enter integer element to insert");
                    delete_benchmark(scanner.nextInt());
                    break;

                case 6:
                    System.out.println("Nodes = " + avlTree.countNodes());
                    break;
                default:
                    System.out.println("Wrong Entry \n ");
                    break;
            }

            System.out.print("\nIn order : ");
            avlTree.inorder();

            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scanner.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');
    }

    /**
     * @param js
     * @param numInserts
     */
    private static void search_benchmark(int numSearches) {
        long startTime, elapsedTime;
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            FileWriter writer = new FileWriter(SearchBenchmarkCSV);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (int i = 0; i < numSearches; i++) {
                int value = random.nextInt();
                avlTree.search(value);


                elapsedTime = (System.nanoTime() - startTime) / 1000000;

                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");

                // print results
                //System.out.println("Time taken for " + value + " insert operations: " + elapsedTime + " nanoseconds");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void insert_benchmark(int numInserts) {
        long startTime, endTime, elapsedTime;
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            FileWriter writer = new FileWriter(InsertBenchmarkCSV);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (int i = 0; i < numInserts; i++) {
                int value = random.nextInt();
                avlTree.insert(value);

                elapsedTime = (System.nanoTime() - startTime) / 1000000;

                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");

                // print results
                //System.out.println("Time taken for " + value + " insert operations: " + elapsedTime + " nanoseconds");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void delete_benchmark(int numInserts) {
        long startTime, endTime, elapsedTime;
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
            FileWriter writer = new FileWriter(DeleteBenchmarkCSV);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (int i = 0; i < numInserts; i++) {
                int value = random.nextInt();
                avlTree.deleteNode(value);

                elapsedTime = (System.nanoTime() - startTime) / 1000000;

                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");

                // print results
                //System.out.println("Time taken for " + value + " insert operations: " + elapsedTime + " nanoseconds");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

//AVL Tree Node
class OLD_AVLNode {

    OLD_AVLNode left, right;
    int data;
    int height;

    public OLD_AVLNode() {
        left = null;
        right = null;
        data = 0;
        height = 0;
    }

    public OLD_AVLNode(int n) {
        left = null;
        right = null;
        data = n;
        height = 0;
    }
}

// AVL Tree Class

class OLD_AVLTree {
    private OLD_AVLNode root;
    public OLD_AVLTree() {
        root = null;
    }


    /**
     *
     * @param avlNode
     * @return
     */
    private int height(OLD_AVLNode avlNode) {
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
    private OLD_AVLNode insert(int data, OLD_AVLNode avlNode) {
        if (avlNode == null)
            avlNode = new OLD_AVLNode(data);
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
    private OLD_AVLNode leftRotation(OLD_AVLNode avlNode) {
        OLD_AVLNode k1 = avlNode.left;
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
    private OLD_AVLNode rightRotation(OLD_AVLNode avlNode) {
        OLD_AVLNode node = avlNode.right;
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
    private OLD_AVLNode leftRightRotation(OLD_AVLNode avlNode) {
        avlNode.left = rightRotation(avlNode.left);
        return leftRotation(avlNode);
    }

    /**
     * right-left rotation
     * @param avlNode
     * @return
     */
    private OLD_AVLNode rightLeftRotation(OLD_AVLNode avlNode) {
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
    private int countNodes(OLD_AVLNode avlNode) {
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
    private boolean search(OLD_AVLNode avlNode, int data) {
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
    private void inorder(OLD_AVLNode avlNode) {
        if (avlNode != null) {
            inorder(avlNode.left);
            System.out.print(avlNode.data + " ");
            inorder(avlNode.right);
        }
    }




    /**
     * @param data
     * @param avlNode
     * @return
     */
    private OLD_AVLNode deleteNode(int data, OLD_AVLNode avlNode) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (avlNode == null)
        return avlNode;

        // If the key to be deleted is smaller than
        // the root's key, then it lies in left subtree
        if (data < avlNode.data)
        avlNode.left = deleteNode(data, avlNode.left);

        // If the key to be deleted is greater than the
        // root's key, then it lies in right subtree
        else if (data > avlNode.data)
        avlNode.right = deleteNode(data, avlNode.right);

        // if key is same as root's key, then this is the node
        // to be deleted
        else {
            // node with only one child or no child
            if ((avlNode.left == null) || (avlNode.right == null)) {
                OLD_AVLNode temp = null;
                if (temp == avlNode.left)
                    temp = avlNode.right;
                else
                    temp = avlNode.left;

                // No child case
                if (temp == null){
                    temp = avlNode;
                    avlNode = null;
                }
                else // One child case
                    avlNode = temp; // Copy the contents of
                    // the non-empty child
            }
            else {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                OLD_AVLNode temp = minValueNode(avlNode.right);

                // Copy the inorder successor's data to this node
                avlNode.data = temp.data;

                // Delete the inorder successor
                avlNode.right = deleteNode(temp.data, avlNode.right);
            }
        }
        return avlNode;
    }

        /* Given a non-empty binary search tree, return the
    node with minimum key value found in that tree.
    Note that the entire tree does not need to be
    searched. */
    /**
     * @param avlNode
     * @return
     */
    private OLD_AVLNode minValueNode(OLD_AVLNode avlNode) {
        OLD_AVLNode current = avlNode;
 
        /* loop down to find the leftmost leaf */
        while (current.left != null)
        current = current.left;
 
        return current;
    }
}