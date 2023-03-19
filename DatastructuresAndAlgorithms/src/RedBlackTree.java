// Helper class to run the AVL code
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Red Black Tree implementation in Java
// Tutorial URL: https://algorithmtutor.com/Data-Structures/Tree/Red-Black-Trees/

// data structure that represents a node in the tree
class RBNode {
	int data; // holds the key
	RBNode parent; // pointer to the parent
	RBNode left; // pointer to left child
	RBNode right; // pointer to right child
	int color; // 1 . Red, 0 . Black
}


// class RedBlackTree implements the operations in Red Black Tree
public class RedBlackTree {
	private RBNode root;
	private RBNode TNULL;


	private static final String InsertBenchmarkCSV = "insert_benchmark.csv";
    private static final String SearchBenchmarkCSV = "search_benchmark.csv";
    private static final String DeleteBenchmarkCSV = "delete_benchmark.csv";
    
    static Scanner scanner = new Scanner(System.in);
    static RedBlackTree rbTree = new RedBlackTree();



	private void preOrderHelper(RBNode node) {
		if (node != TNULL) {
			System.out.print(node.data + " ");
			preOrderHelper(node.left);
			preOrderHelper(node.right);
		} 
	}

	private void inOrderHelper(RBNode node) {
		if (node != TNULL) {
			inOrderHelper(node.left);
			System.out.print(node.data + " ");
			inOrderHelper(node.right);
		} 
	}

	private void postOrderHelper(RBNode node) {
		if (node != TNULL) {
			postOrderHelper(node.left);
			postOrderHelper(node.right);
			System.out.print(node.data + " ");
		} 
	}

	private RBNode searchTreeHelper(RBNode node, int key) {
		if (node == TNULL || key == node.data) {
			return node;
		}

		if (key < node.data) {
			return searchTreeHelper(node.left, key);
		} 
		return searchTreeHelper(node.right, key);
	}

	// fix the rb tree modified by the delete operation
	private void fixDelete(RBNode x) {
		RBNode s;
		while (x != root && x.color == 0) {
			if (x == x.parent.left) {
				s = x.parent.right;
				if (s.color == 1) {
					// case 3.1
					s.color = 0;
					x.parent.color = 1;
					leftRotate(x.parent);
					s = x.parent.right;
				}

				if (s.left.color == 0 && s.right.color == 0) {
					// case 3.2
					s.color = 1;
					x = x.parent;
				} else {
					if (s.right.color == 0) {
						// case 3.3
						s.left.color = 0;
						s.color = 1;
						rightRotate(s);
						s = x.parent.right;
					} 

					// case 3.4
					s.color = x.parent.color;
					x.parent.color = 0;
					s.right.color = 0;
					leftRotate(x.parent);
					x = root;
				}
			} else {
				s = x.parent.left;
				if (s.color == 1) {
					// case 3.1
					s.color = 0;
					x.parent.color = 1;
					rightRotate(x.parent);
					s = x.parent.left;
				}

				if (s.right.color == 0 && s.right.color == 0) {
					// case 3.2
					s.color = 1;
					x = x.parent;
				} else {
					if (s.left.color == 0) {
						// case 3.3
						s.right.color = 0;
						s.color = 1;
						leftRotate(s);
						s = x.parent.left;
					} 

					// case 3.4
					s.color = x.parent.color;
					x.parent.color = 0;
					s.left.color = 0;
					rightRotate(x.parent);
					x = root;
				}
			} 
		}
		x.color = 0;
	}


	private void rbTransplant(RBNode u, RBNode v){
		if (u.parent == null) {
			root = v;
		} else if (u == u.parent.left){
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}

	private void deleteNodeHelper(RBNode node, int key) {
		// find the node containing key
		RBNode z = TNULL;
		RBNode x, y;
		while (node != TNULL){
			if (node.data == key) {
				z = node;
			}

			if (node.data <= key) {
				node = node.right;
			} else {
				node = node.left;
			}
		}

		if (z == TNULL) {
			System.out.println("Couldn't find key in the tree");
			return;
		} 

		y = z;
		int yOriginalColor = y.color;
		if (z.left == TNULL) {
			x = z.right;
			rbTransplant(z, z.right);
		} else if (z.right == TNULL) {
			x = z.left;
			rbTransplant(z, z.left);
		} else {
			y = minimum(z.right);
			yOriginalColor = y.color;
			x = y.right;
			if (y.parent == z) {
				x.parent = y;
			} else {
				rbTransplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}

			rbTransplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (yOriginalColor == 0){
			fixDelete(x);
		}
	}
	
	// fix the red-black tree
	private void fixInsert(RBNode k){
		RBNode u;
		while (k.parent.color == 1) {
			if (k.parent == k.parent.parent.right) {
				u = k.parent.parent.left; // uncle
				if (u.color == 1) {
					// case 3.1
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;
				} else {
					if (k == k.parent.left) {
						// case 3.2.2
						k = k.parent;
						rightRotate(k);
					}
					// case 3.2.1
					k.parent.color = 0;
					k.parent.parent.color = 1;
					leftRotate(k.parent.parent);
				}
			} else {
				u = k.parent.parent.right; // uncle

				if (u.color == 1) {
					// mirror case 3.1
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;	
				} else {
					if (k == k.parent.right) {
						// mirror case 3.2.2
						k = k.parent;
						leftRotate(k);
					}
					// mirror case 3.2.1
					k.parent.color = 0;
					k.parent.parent.color = 1;
					rightRotate(k.parent.parent);
				}
			}
			if (k == root) {
				break;
			}
		}
		root.color = 0;
	}

	private void printHelper(RBNode root, String indent, boolean last) {
		// print the tree structure on the screen
	   	if (root != TNULL) {
		   System.out.print(indent);
		   if (last) {
		      System.out.print("R----");
		      indent += "     ";
		   } else {
		      System.out.print("L----");
		      indent += "|    ";
		   }
            
           String sColor = root.color == 1?"RED":"BLACK";
		   System.out.println(root.data + "(" + sColor + ")");
		   printHelper(root.left, indent, false);
		   printHelper(root.right, indent, true);
		}
	}

	public RedBlackTree() {
		TNULL = new RBNode();
		TNULL.color = 0;
		TNULL.left = null;
		TNULL.right = null;
		root = TNULL;
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
	public RBNode searchTree(int k) {
		return searchTreeHelper(this.root, k);
	}

	// find the node with the minimum key
	public RBNode minimum(RBNode node) {
		while (node.left != TNULL) {
			node = node.left;
		}
		return node;
	}

	// find the node with the maximum key
	public RBNode maximum(RBNode node) {
		while (node.right != TNULL) {
			node = node.right;
		}
		return node;
	}

	// find the successor of a given node
	public RBNode successor(RBNode x) {
		// if the right subtree is not null,
		// the successor is the leftmost node in the
		// right subtree
		if (x.right != TNULL) {
			return minimum(x.right);
		}

		// else it is the lowest ancestor of x whose
		// left child is also an ancestor of x.
		RBNode y = x.parent;
		while (y != TNULL && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	// find the predecessor of a given node
	public RBNode predecessor(RBNode x) {
		// if the left subtree is not null,
		// the predecessor is the rightmost node in the 
		// left subtree
		if (x.left != TNULL) {
			return maximum(x.left);
		}

		RBNode y = x.parent;
		while (y != TNULL && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	// rotate left at node x
	public void leftRotate(RBNode x) {
		RBNode y = x.right;
		x.right = y.left;
		if (y.left != TNULL) {
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
	}

	// rotate right at node x
	public void rightRotate(RBNode x) {
		RBNode y = x.left;
		x.left = y.right;
		if (y.right != TNULL) {
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
	}

	// insert the key to the tree in its appropriate position
	// and fix the tree
	public void insert(int key) {
		// Ordinary Binary Search Insertion
		RBNode node = new RBNode();
		node.parent = null;
		node.data = key;
		node.left = TNULL;
		node.right = TNULL;
		node.color = 1; // new node must be red

		RBNode y = null;
		RBNode x = this.root;

		while (x != TNULL) {
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

		// if new node is a root node, simply return
		if (node.parent == null){
			node.color = 0;
			return;
		}

		// if the grandparent is null, simply return
		if (node.parent.parent == null) {
			return;
		}

		// Fix the tree
		fixInsert(node);
	}

	public RBNode getRoot(){
		return this.root;
	}

	// delete the node from the tree
	public void deleteNode(int data) {
		deleteNodeHelper(this.root, data);
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
    private int countNodes(RBNode node) {
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
                    rbTree.insert(scanner.nextInt());
                    break;
                
                case 2:
                    System.out.println("Enter integer element to insert");
                    insert_benchmark(scanner.nextInt());
                    break;

                case 3:
                    System.out.println("Enter integer element to search");
                    System.out.println("Search result : " + rbTree.searchTree(scanner.nextInt()));
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
                    System.out.println("Nodes = " + rbTree.countNodes());
                    break;
				
				case 7:
                    System.out.println("printing Nodes");
					rbTree.prettyPrint();
                    break;
					
                default:
                    System.out.println("Wrong Entry \n ");
                    break;
            }

            System.out.print("\nIn order : ");
            rbTree.inorder();

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
                rbTree.searchTree(value);


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
                rbTree.insert(value);

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
                rbTree.deleteNode(value);

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