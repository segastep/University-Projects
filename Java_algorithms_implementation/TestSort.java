/*************************************************/
/*** Simple test class for Sort class          ***/
/***                                           ***/
/*** Author: Jason Steggles    20/09/2015  
/ * Extended by: Georgi Nikolov    20/10/2015    ***/
/*************************************************/

public class TestSort {

	Sort sortTest = new Sort(100);
	/* Save file names to a string */
	String[] inputs = { "src/test1.txt", "src/test2.txt", "src/test3.txt", "src/test4.txt", "src/test5.txt" };
	int i = 0; // Initialise i 

	public void initialiseFiles() {
		for (int i = 0; i <= inputs.length - 2; i++) {
			this.i = i;
			/* Initialise used file */
			sortTest.readIn(inputs[i]);
			testInsertionSort();
			sortTest.getA(); // resets A[]
			sortTest.readIn(inputs[i]);
			testQuickSort();
			sortTest.getA();
			
		}
	}

	private void testInsertionSort() {
		sortTest.compIS = 0;
		System.out.println("------------------------------------------------------------");
		/* Display used test file */
		System.out.println("Insertion sort test with file: " + inputs[i] + "\n");
		sortTest.display(10, "Unsorted Array of Integers\n"); // Display
																// unsorted
																// array
		sortTest.insertionSort();

		sortTest.display(10, "\nSorted array using Insertion Sort\n"); // Display
																		// Sorted
																		// Array
		System.out.println("\n\nInsertion sort comparison counter: " + sortTest.compIS);
		sortTest.compIS = 0;
		System.out.println("------------------------------------------------------------");
	}

	private void testQuickSort() {
 
		System.out.println("------------------------------------------------------------");
		System.out.println("Quick sort test with file: " + inputs[i] + "\n");
		sortTest.display(10, "Unsorted Array of Integers\n");
		sortTest.quickSort(0, sortTest.getUsedSize() -1);
		sortTest.display(10, "\nSorted array using Quick Sort\n");
		System.out.println("\n\nQuick sort comparison counter: " + sortTest.compQS);
		sortTest.compQS = 0;
		System.out.println("------------------------------------------------------------");
	}

	/** @method - testNewSort
	 * @return - void
	 * Purpose: Sets files to be used with the newSort() method from
	 * Sort class 
	 * 
	 */
	public void testNewSort() {
		for (int i = 2; i <= inputs.length - 1; i++) {
			this.i = i;
			/* Initialise used file */
			sortTest.readIn(inputs[i]);
			sortTest.compNewS=0;
			System.out.println("------------------------------------------------------------");
			/* Display used test file */
			System.out.println("Testing New Sort algorithm with file : " + inputs[i] + "\n");
			sortTest.display(10, "Unsorted Array of Integers\n"); // Display
																	// unsorted
																	// array
			sortTest.newSort();
			sortTest.display(10, "\nSorted array using New Sort\n"); // Display
																		// Sorted
																		// Array
			System.out.println("\n\nNew Sort comparison counter: " + sortTest.compNewS);
			sortTest.compNewS = 0;
			System.out.println("------------------------------------------------------------");
		}

	}
     /**@method - testISfileFive()			  ********
      * @return - void;
      * Purpose: Test method for insertion sort******
      * using test5.txt as a array source************
      */
	private void testISfileFive() {
		sortTest.readIn(inputs[4]);
		System.out.println("------------------------------------------------------------");
		/* Display used test file */
		System.out.println("Insertion sort test with file: " + inputs[4] + "\n");
		sortTest.display(10, "Unsorted Array of Integers\n"); // Display
																// unsorted
																// array
		sortTest.insertionSort();
		sortTest.display(10, "\nSorted array using Insertion Sort\n"); // Display
																		// Sorted
																		// Array
		System.out.println("\n\nInsertion sort comparison counter: " + sortTest.compIS);

		System.out.println("------------------------------------------------------------");
	}

	public static void main(String[] args) {
		
		/**Method calls to execute the test Sort class**/
		TestSort newTest = new TestSort();
		newTest.initialiseFiles();
		newTest.testNewSort();
		newTest.testISfileFive();

	}

} /** End of Test class **/
