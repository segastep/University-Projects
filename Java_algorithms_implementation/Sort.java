/*****************************************************/
/*** Purpose:                                      ***/
/***                                               ***/
/***     Initial Author: Jason Steggles 20/09/15   ***/
/***     Extended by: Georgi Nikolov    20/10/2015 ***/
/*****************************************************/

import java.io.*;
import java.text.*;
import java.util.*;

public class Sort {

	/** Size of array **/
	private int size;

	/** Number of used elements in array **/
	private int usedSize;

	/** Array of integers **/
	private int[] A;

	/** Global variables for counting sort comparisons **/
	public int compIS;
	/** Global comparison count for Insertion Sort **/
	public int compQS;
	/** Global comparison count for Quicksort **/
	public int compNewS;

	/** Global comparison count for new sort **/

	/*****************/

	/** Constructor **/
	/*****************/
	Sort(int max) {
		/** Initialise global sort count variables **/
		compIS = 0;
		compQS = 0;
		compNewS = 0;

		/** Initialise size variables **/
		usedSize = 0;
		size = max;

		/** Create Array of Integers **/
		A = new int[size];
	}

	/*********************************************/

	/*** Read a file of integers into an array ***/
	/*********************************************/
	public void readIn(String file) {
		try {
			/** Initialise loop variable **/
			usedSize = 0;

			/** Set up file for reading **/
			FileReader reader = new FileReader(file);
			Scanner in = new Scanner(reader);

			/** Loop round reading in data while array not full **/
			while (in.hasNextInt() && (usedSize < size)) {
				A[usedSize] = in.nextInt();
				usedSize++;
			}

		} catch (IOException e) {
			System.out.println("Error processing file " + file);
		}
	}

	/**********************/

	/*** Display array ***/
	/**********************/
	public void display(int line, String header) {
		/*** Integer Formatter - three digits ***/
		NumberFormat FI = NumberFormat.getInstance();
		FI.setMinimumIntegerDigits(3);

		/** Print header string **/
		System.out.print("\n" + header);

		/** Display array data **/
		for (int i = 0; i < usedSize; i++) {
			/** Check if new line is needed **/
			if (i % line == 0) {
				System.out.println();
			}

			/** Display an array element **/
			System.out.print(FI.format(A[i]) + " ");
		}
	}

	/* Implementation of the insertion sort algorithm */
	public void insertionSort() {
		int key, b, j;
		for (b = 0; b < usedSize; b++) {
			key = A[b];
			j = b;

			while ((j > 0) && (key < A[j - 1])) {
				compIS++;
				A[j] = A[j - 1];
				j = j - 1;
			}
			A[j] = key;
			compIS++;
		}

	}

	/* Implementation of recursiveQuickSort algorithm */
	public void quickSort(int left, int right) {
		int pivot;
		if (right > left) {
		
			pivot = partition(left, right);
			quickSort(left, pivot - 1);
			quickSort(pivot + 1, right);
		}
		
	}

	/** Implementation of partition algorithm needed by quickSort **/
	private int partition(int left, int right) {
		
		
	int pivot = A[right];
	int	pL = left;
	int	pR = right;

		while (pL < pR) {
			compQS++;
			while (A[pL] < pivot) {
				pL ++; // Move left pointer to next location
				compQS++;

			}
			compQS++;
			
			while ((A[pR] >= pivot) && pR > left) {
				pR --; // Move pointer to next location
				compQS++;

			}
			
			if (pL < pR)
				swap(pL, pR);
		}

		swap(pL, right);
		
		return pL;
	}

	/* Swap method used to swap needed values for the partition algorithm */
	public void swap(int left, int right) {
		int tmp;
		tmp = A[left];
		A[left] = A[right];
		A[right] = tmp;
		
	}

	/* Set method for usedSize variable used for the calls in testClass */
	public int getUsedSize() {
		return usedSize;
	}

	/****** Implementation of new array sort algorithm *******/
	/** using the pseudo code give in *****/
	/** the specification document ***************
	/ * @retun void */

	public void newSort() {
		int pos = 0;
		while (pos < usedSize) {
			int min = findMinFrom(pos);

			for (int i = pos; i <= usedSize - 1; i++) {
				if (A[i] == min) {
					swap(i, pos);
					pos = pos + 1;
				}
				compNewS++;
			}

		}

	}

	/****
	 * Implementation of findminfrom algorithm******** 
	 * @param pos - position 
	 * @return - minimum value found so far
	 */
	public int findMinFrom(int pos) {
		int min;
		min = A[pos];
		for (int i = pos + 1; i <= usedSize - 1; i++) {
			if (A[i] < min) {
				min = A[i];
			}
			compNewS++;
		}

		return min;
	}

	public int[] getA() {
		return A;
	}

	public  void setA(int[] a) {
		A = a;
	}
	
}
/** End of Sort Class **/