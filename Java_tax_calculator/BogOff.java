//import java.util.Scanner;
/**
 * @author Georgi Nikolov
 * @date : 04/11/2014
 * @purpose: BoggOff is used to initialize the program and
 *  to give values to the TaxCalculator
 * @version Final
 */



public class BogOff {

	/**
	 * Intialize data.
	 * @category The code below can be used to set manual input of values 
	 * in the program.
	 * 
	 * @return int[] - income data.
	 *
        public int[] inputIncome(){
		Scanner in = new Scanner(System.in); // Construct Scanner object
		int [] incomes = new int [ 12 ]; 
		for(int i = 0; i < 12; i++) { 
		//fill one element at a time 
		System.out.println("Please Enter income:");
		incomes[i] = in.nextInt(); // Read in income
		}
	in.close();
	return incomes;
	
}
		
	/**
	 * Initialize data.
	 * 
	 * @return int[] - income data.
	 * @usage: Test values
	 */
	 public int[] taxTables() {
		
		final int[] incomes = new int[] { 25, 50, 100, 125, 150, 175, 200, 225,
				250, 275, 300, 325, 350, 375, 400, 425, 450, 500, 550, 600 };
		return incomes;
	}
	
	
		
		/**Executes the program**/
	public static void main(String[] args) {
		BogOff bogOff = new BogOff();
		TaxChart taxChart = new TaxChart();
		//taxChart.printTable(bogOff.inputIncome());
		taxChart.printTable(bogOff.taxTables());
		

	}
}
