public class HashTable{
	private HashNode[] buckets;
	private int numOfBuckets;       // Capacity
	private int size;               // Number of key value pairs in hash table or number of hash nodes in a HashTable
	private final double LOAD_FACTOR_TRESHOLD = 0.75;

	// Constructor
    public HashTable(){
		this(10);          // default capacity
	}

	public HashTable(int capacity){
		this.numOfBuckets=capacity;
		this.buckets=new HashNode[numOfBuckets];
        this.size = 0;
	}

	private class HashNode{
		private Integer key; 	    // Can be generic
		private String value; 	    // Can be generic
		private HashNode next;      // reference to next HashNode

		public HashNode(Integer key, String value){
			this.key = key;
			this.value = value;
		}
	}

    public int size(){
        return size;
    }

    public boolean isEmphty(){
        return size == 0;
    }

    public void insert(Integer key, String value){
		if(key==null || value==null){
			throw new IllegalArgumentException("Key or Value is null!");
		}

		int bucketIndex = getBucketIndex(key);
		HashNode head = buckets[bucketIndex];
		while(head!=null){
			if(head.key.equals(key)){
				head.value=value;
				return;
			}
			head=head.next;
		}
		size++;
		head=buckets[bucketIndex];
		HashNode node = new HashNode(key, value); // (key, value) --> null
		node.next = head;
		buckets[bucketIndex]=node;

		if ((double) size / numOfBuckets >= LOAD_FACTOR_TRESHOLD) {
            resize(numOfBuckets*2);
        }
    }

	private void resize(int newCapacity){
		HashNode[] newBuckets = new HashNode[newCapacity];
	
		for(int i=0; i<numOfBuckets; i++){
			HashNode head = buckets[i];
			while(head!=null){
				int bucketIndex = getBucketIndex(head.key, newCapacity); // use modified getBucketIndex()
				HashNode next = head.next;
				head.next = newBuckets[bucketIndex];
				newBuckets[bucketIndex] = head;
				head = next;
			}
		}
		buckets = newBuckets;
		numOfBuckets = newCapacity;
	}
	
	



	// Modular Hash Function
	/*
	public int getBucketIndex(Integer key){
		return key % numOfBuckets; 
	}
	*/


	// Multiplication method hash function:
	public int getBucketIndex(Integer key) {
		double A = 0.6180339887; // a constant between 0 and 1
		double fractionalPart = key * A - Math.floor(key * A);
		return (int)(fractionalPart * numOfBuckets);
	}

	private int getBucketIndex(Integer key, int newCapacity) {
		double A = 0.6180339887; // a constant between 0 and 1
		double fractionalPart = key * A - Math.floor(key * A);
		return (int)(fractionalPart * newCapacity);
	}
	

	// division method hash function
	/*
	public int getBucketIndex(Integer key) {
		int prime = 31; // a prime number
		return Math.abs(key.hashCode() % prime) % numOfBuckets;
	}
	*/



	public String get(Integer key){
		if(key==null){
			throw new IllegalArgumentException("Key is null!");
		}

		int bucketIndex = getBucketIndex(key); 
		HashNode head = buckets[bucketIndex];
		while(head!=null){
			if(head.key.equals(key)){
				return head.value;
			}
			head = head.next;
		}
        return null;
    }

    public String remove(Integer key){
		if(key==null){
			throw new IllegalArgumentException("Key is null!");
		}
		int bucketIndex = getBucketIndex(key);
		HashNode head = buckets[bucketIndex];	// (21, "Tom") ->(31, "Harry") ->(41, "Sana") -> null
		HashNode previous = null;
		
		// we compare to see if the key is found in the list. 
		while(head!=null){
			if (head.key.equals(key)){
				break;
			}
			previous=head;
			head=head.next;
		}

		// if head is == null, then we didnt find the key in the list.
		if(head == null){
			return null;
		}
		size--;

		if(previous!=null){
			// this will be used if we are removing a node in a list
			previous.next = head.next;
		}else{
			// This is only used if we are removing the first node
			buckets[bucketIndex] = head.next;
		}
        return null;
    }

	public static void main(String[] args) {
		HashTable table = new HashTable(10);
		table.insert(105, "Tom");
		table.insert(21, "Sana");
		table.insert(21, "Eirik");
		table.insert(69, "Viera");
		System.out.printf("The size of the table is: %d \n", table.size());
		System.out.println(table.get(69));
		System.out.println(table.get(70));

		table.remove(69);
		System.out.printf("The size of the table is: %d \n", table.size());
		System.out.println(table.get(69));
	}
}

