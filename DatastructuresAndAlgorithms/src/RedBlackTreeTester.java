// Helper class to run the RedBlack code
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class RedBlackTreeTester {
    static Scanner scanner = new Scanner(System.in);


    static RedBlackTree RedBlackTree = new RedBlackTree();

    public static void main(String[] args) {
        char ch;
        do {
            System.out.println("\nRedBlackTree Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. search");
            System.out.println("3. count nodes");
			System.out.println("4. benchmark_average");
			System.out.println("5. benchmark_worst_case");
            System.out.println("6. benchmark");
			System.out.println("7. prettyPrint");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter integer element to insert");
                    RedBlackTree.insert(scanner.nextInt());
                    break;

                case 2:
                    System.out.println("Enter integer element to search");
                    System.out.println("Search result : " + RedBlackTree.searchTree(scanner.nextInt()));
                    break;

                case 3:
                    System.out.println("Nodes = " + RedBlackTree.countNodes());
                    break;
				
				case 4:
                    System.out.println("Enter the amount of random values to generate");
					benchmark_average(scanner.nextInt());
                    break;

				case 5:
                    System.out.println("Enter the amount of random values to generate");
					benchmark_worstCase(scanner.nextInt());
                    break;
                
                case 6:
                    System.out.println("Enter the amount of random values to generate");
					benchmark(scanner.nextInt());
                    break;

				case 7:
                    System.out.println("printing Nodes");
					RedBlackTree.prettyPrint();
                    break;
					
                default:
                    System.out.println("Wrong Entry \n ");
                    break;
            }

            System.out.print("\nIn order : ");
            //RedBlackTree.inorder();

            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scanner.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');
    }

    private static void benchmark(int nextInt) {
        benchmark_worstCase(nextInt);
        benchmark_average(nextInt);
    }

	
    static int[] reverse(int a[], int n)
    {
        int[] b = new int[n];
        int j = n;
        for (int i = 0; i < n; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }
  
        return b;
    }
	
	private static void benchmark_worstCase(int numInserts) {

        int[] arr1 = new int[numInserts];
        int[] arr2 = new int[numInserts];

		for (int i = 0; i < arr1.length; i++) {
		   arr1[i] = i;
		}

        arr2 = reverse(arr1, arr1.length);

		insert_benchmark(arr1, "csv/RedBlack_WorstCase_0n_insert_benchmark.csv");
		search_benchmark(arr1, "csv/RedBlack_WorstCase_0n_search_benchmark.csv");
		delete_benchmark(arr1, "csv/RedBlack_WorstCase_0n_delete_benchmark.csv");

        insert_benchmark(arr2, "csv/RedBlack_WorstCase_n0_insert_benchmark.csv");
		search_benchmark(arr2, "csv/RedBlack_WorstCase_n0_search_benchmark.csv");
		delete_benchmark(arr2, "csv/RedBlack_WorstCase_n0_delete_benchmark.csv");

    }

	private static void benchmark_average(int numInserts) {

		Random rd = new Random(); // creating Random object
		int[] arr = new int[numInserts];
		for (int i = 0; i < arr.length; i++) {
		   arr[i] = rd.nextInt(); // storing random integers in an array
		}

		insert_benchmark(arr, "csv/RedBlack_average_insert_benchmark.csv");
		search_benchmark(arr, "csv/RedBlack_average_search_benchmark.csv");
		delete_benchmark(arr, "csv/RedBlack_average_delete_benchmark.csv");

    }

    private static void insert_benchmark(int[] arr, String fileName) {
		long startTime, elapsedTime;
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (int i = 0; i < arr.length; i++) {
                RedBlackTree.insert(arr[i]);

                elapsedTime = (System.nanoTime() - startTime) / 1000000;

                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	private static void search_benchmark(int[] arr, String fileName) {
		long startTime, elapsedTime;
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (int i = 0; i < arr.length; i++) {
                RedBlackTree.searchTree(arr[i]);

                elapsedTime = (System.nanoTime() - startTime) / 1000000;

                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	private static void delete_benchmark(int[] arr, String fileName) {
		long startTime, elapsedTime;
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (int i = 0; i < arr.length; i++) {
                RedBlackTree.insert(arr[i]);

                elapsedTime = (System.nanoTime() - startTime) / 1000000;

                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	
}
