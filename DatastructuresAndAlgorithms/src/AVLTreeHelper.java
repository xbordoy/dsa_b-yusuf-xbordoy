// Helper class to run the AVL code
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class AVLTreeHelper {
    private static final String CSV_FILENAME = "insert_benchmark.csv";
    static Scanner scanner = new Scanner(System.in);
    static AVLTree avlTree = new AVLTree();

    public static void main(String[] args) {
        char ch;
        do {
            System.out.println("\nAVLTree Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. insert_benchmark ");
            System.out.println("3. search");
            System.out.println("4. count nodes");
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



    private static void insert_benchmark(int numInserts) {
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
                avlTree.insert(numInserts);


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