// Helper class to run the AVL code
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// AVL Binary search tree implementation in Java
// Source: 

// data structure that represents a node in the tree
class AVLNode {
	int data; // holds the key
	AVLNode parent; // pointer to the parent
	AVLNode left; // pointer to left child
	AVLNode right; // pointer to right child
	int bf; // balance factor of the node

	public AVLNode(int data) {
		this.data = data;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.bf = 0;
	}
}

public class AVLTree {
    private static final String InsertBenchmarkCSV = "insert_benchmark.csv";
    private static final String SearchBenchmarkCSV = "search_benchmark.csv";
    private static final String DeleteBenchmarkCSV = "delete_benchmark.csv";
    
    static Scanner scanner = new Scanner(System.in);
    static AVLTree avlTree = new AVLTree();


	private AVLNode root;

	public AVLTree() {
		root = null;
	}

	private void printHelper(AVLNode currPtr, String indent, boolean last) {
		// print the tree structure on the screen
	   	if (currPtr != null) {
		   System.out.print(indent);
		   if (last) {
		      System.out.print("R----");
		      indent += "     ";
		   } else {
		      System.out.print("L----");
		      indent += "|    ";
		   }

		   System.out.println(currPtr.data + "(BF = " + currPtr.bf + ")");

		   printHelper(currPtr.left, indent, false);
		   printHelper(currPtr.right, indent, true);
		}
	}

	private AVLNode searchTreeHelper(AVLNode node, int key) {
		if (node == null || key == node.data) {
			return node;
		}

		if (key < node.data) {
			return searchTreeHelper(node.left, key);
		} 
		return searchTreeHelper(node.right, key);
	}

	private AVLNode deleteNodeHelper(AVLNode node, int key) {
		// search the key
		if (node == null) return node;
		else if (key < node.data) node.left = deleteNodeHelper(node.left, key);
		else if (key > node.data) node.right = deleteNodeHelper(node.right, key);
		else {
			// the key has been found, now delete it

			// case 1: node is a leaf node
			if (node.left == null && node.right == null) {
				node = null;
			}

			// case 2: node has only one child
			else if (node.left == null) {
				AVLNode temp = node;
				node = node.right;
			}

			else if (node.right == null) {
				AVLNode temp = node;
				node = node.left;
			}

			// case 3: has both children
			else {
				AVLNode temp = minimum(node.right);
				node.data = temp.data;
				node.right = deleteNodeHelper(node.right, temp.data);
			}

		} 

		// Write the update balance logic here 
		// YOUR CODE HERE

		return node;
	}

	// update the balance factor the node
	private void updateBalance(AVLNode node) {
		if (node.bf < -1 || node.bf > 1) {
			rebalance(node);
			return;
		}

		if (node.parent != null) {
			if (node == node.parent.left) {
				node.parent.bf -= 1;
			} 

			if (node == node.parent.right) {
				node.parent.bf += 1;
			}

			if (node.parent.bf != 0) {
				updateBalance(node.parent);
			}
		}
	}

	// rebalance the tree
	void rebalance(AVLNode node) {
		if (node.bf > 0) {
			if (node.right.bf < 0) {
				rightRotate(node.right);
				leftRotate(node);
			} else {
				leftRotate(node);
			}
		} else if (node.bf < 0) {
			if (node.left.bf > 0) {
				leftRotate(node.left);
				rightRotate(node);
			} else {
				rightRotate(node);
			}
		}
	}


	private void preOrderHelper(AVLNode node) {
		if (node != null) {
			System.out.print(node.data + " ");
			preOrderHelper(node.left);
			preOrderHelper(node.right);
		} 
	}

	private void inOrderHelper(AVLNode node) {
		if (node != null) {
			inOrderHelper(node.left);
			System.out.print(node.data + " ");
			inOrderHelper(node.right);
		} 
	}

	private void postOrderHelper(AVLNode node) {
		if (node != null) {
			postOrderHelper(node.left);
			postOrderHelper(node.right);
			System.out.print(node.data + " ");
		} 
	}

	// Pre-Order traversal
	// Node.Left Subtree.Right Subtree
	public void preorder() {
		preOrderHelper(this.root);
	}

	// In-Order traversal
	// Left Subtree . Node . Right Subtree
	public void inorder() {
		inOrderHelper(this.root);
	}

	// Post-Order traversal
	// Left Subtree . Right Subtree . Node
	public void postorder() {
		postOrderHelper(this.root);
	}

	// search the tree for the key k
	// and return the corresponding node
	public AVLNode searchTree(int k) {
		return searchTreeHelper(this.root, k);
	}

	// find the node with the minimum key
	public AVLNode minimum(AVLNode node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	// find the node with the maximum key
	public AVLNode maximum(AVLNode node) {
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}

	// find the successor of a given node
	public AVLNode successor(AVLNode x) {
		// if the right subtree is not null,
		// the successor is the leftmost node in the
		// right subtree
		if (x.right != null) {
			return minimum(x.right);
		}

		// else it is the lowest ancestor of x whose
		// left child is also an ancestor of x.
		AVLNode y = x.parent;
		while (y != null && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	// find the predecessor of a given node
	public AVLNode predecessor(AVLNode x) {
		// if the left subtree is not null,
		// the predecessor is the rightmost node in the 
		// left subtree
		if (x.left != null) {
			return maximum(x.left);
		}

		AVLNode y = x.parent;
		while (y != null && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	// rotate left at node x
	void leftRotate(AVLNode x) {
		AVLNode y = x.right;
		x.right = y.left;
		if (y.left != null) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf - 1 - Math.max(0, y.bf);
		y.bf = y.bf - 1 + Math.min(0, x.bf);
	}

	// rotate right at node x
	void rightRotate(AVLNode x) {
		AVLNode y = x.left;
		x.left = y.right;
		if (y.right != null) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf + 1 - Math.min(0, y.bf);
		y.bf = y.bf + 1 + Math.max(0, x.bf);
	}


	// insert the key to the tree in its appropriate position
	public void insert(int key) {
		// PART 1: Ordinary BST insert
		AVLNode node = new AVLNode(key);
		AVLNode y = null;
		AVLNode x = this.root;

		while (x != null) {
			y = x;
			if (node.data < x.data) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		// y is parent of x
		node.parent = y;
		if (y == null) {
			root = node;
		} else if (node.data < y.data) {
			y.left = node;
		} else {
			y.right = node;
		}

		// PART 2: re-balance the node if necessary
		updateBalance(node);
	}

	// delete the node from the tree
	AVLNode deleteNode(int data) {
		return deleteNodeHelper(this.root, data);
	}

	// print the tree structure on the screen
	public void prettyPrint() {
		printHelper(this.root, "", true);
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
     * @param node
     * @return
     */
    private int countNodes(AVLNode node) {
        if (node == null)
            return 0;
        else {
            int l = 1;
            l += countNodes(node.left);
            l += countNodes(node.right);
            return l;
        }
    }




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
			System.out.println("7. prettyPrint");
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
                    System.out.println("Search result : " + avlTree.searchTree(scanner.nextInt()));
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
				
				case 7:
                    System.out.println("printing Nodes");
					avlTree.prettyPrint();
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
                avlTree.searchTree(value);


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

