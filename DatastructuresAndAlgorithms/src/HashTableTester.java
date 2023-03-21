// Helper class to run the BS code
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;


public class HashTableTester {
    static Scanner scanner = new Scanner(System.in);


    

    public static void main(String[] args) {
        char ch;
        do {
            System.out.println("\nHashTable Operations\n");
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
                    //System.out.println("Enter integer element to insert");
                    //HashTable.insert(null, null);(scanner.nextInt());
                    break;

                case 2:
                    //System.out.println("Enter integer element to search");
                    //System.out.println("Search result : " + HashTable.searchTree(scanner.nextInt()));
                    break;

                case 3:
                    //System.out.println("Nodes = " + HashTable.countNodes());
                    break;
				
				case 4:
                    System.out.println("Enter the amount of random values to generate");
					//benchmark_average(scanner.nextInt());
                    break;

				case 5:
                    System.out.println("Enter the amount of random values to generate");
					//benchmark_worstCase(scanner.nextInt());
                    break;
                
                case 6:
                    System.out.println("Enter the amount of random values to generate");
					benchmark(scanner.nextInt());
                    break;

				case 7:
                    System.out.println("printing Nodes");
					//HashTable.prettyPrint();
                    break;
					
                default:
                    System.out.println("Wrong Entry \n ");
                    break;
            }

            System.out.print("\nIn order : ");

            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scanner.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');
    }
	

	private static void benchmark(int numInserts) {
        // create a list to hold the key-value pairs
        List<Map.Entry<Integer, String>> list = new ArrayList<>();

        // generate 10 key-value pairs with random keys and values
        Random random = new Random();
        for (int i = 0; i < numInserts; i++) {
            int key = random.nextInt(100);  // generate a random integer key between 0 and 99
            String value = UUID.randomUUID().toString();  // generate a random UUID string value
            list.add(new AbstractMap.SimpleEntry<>(key, value));  // add the key-value pair to the list
        }

        insert_benchmark(list, "csv/HashTable_insert_benchmark.csv");
        get_benchmark(list, "csv/HashTable_get_benchmark.csv");
        remove_benchmark(list, "csv/HashTable_remove_benchmark.csv");
    }

    private static void insert_benchmark(List<Entry<Integer, String>> list, String fileName) {
        HashTable HashTable = new HashTable(1000);
        int i =0;
        long startTime, elapsedTime;
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (Map.Entry<Integer, String> entry : list) {
                
                //System.out.println(entry.getKey() + ": " + entry.getValue());
                HashTable.insert(entry.getKey(), entry.getValue());
                elapsedTime = (System.nanoTime() - startTime) / 1000000;
                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");
                i++;
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void get_benchmark(List<Entry<Integer, String>> list, String fileName) {
        HashTable HashTable = new HashTable();
        int i =0;
        long startTime, elapsedTime;
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (Map.Entry<Integer, String> entry : list) {
                
                //System.out.println(entry.getKey() + ": " + entry.getValue());
                HashTable.get(entry.getKey());
                elapsedTime = (System.nanoTime() - startTime) / 1000000;
                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");
                i++;
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void remove_benchmark(List<Entry<Integer, String>> list, String fileName) {
        HashTable HashTable = new HashTable();
        int i =0;
        long startTime, elapsedTime;
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.append("Number of Inserts");
            writer.append(",");
            writer.append("Elapsed Time (ms)");
            writer.append("\n");

            // perform insert operations and measure time taken
            startTime = System.nanoTime();
            for (Map.Entry<Integer, String> entry : list) {
                
                //System.out.println(entry.getKey() + ": " + entry.getValue());
                HashTable.get(entry.getKey());
                elapsedTime = (System.nanoTime() - startTime) / 1000000;
                writer.append(Integer.toString(i));
                writer.append(",");
                writer.append(Long.toString(elapsedTime));
                writer.append("\n");
                i++;
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
