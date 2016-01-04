

/**
 * Calculate Tax and Income (after deduct tax) given income.
 * 
 * @author Georgi Nikokolov
 * @author b4036790
 * @purpose: Project1 - Tax calculator - this class is the main algorithm for the program
 * all calculations are done here then the results are passed to TaxChart class. 
 * 
 */
public class TaxCalculator {

	/** declare percentage constants**/
	private static final double ZERO_TO_HUNDRED = 0.0; // 0 to 100
	
	private static final double HUNDRED_TO_ONE_HUNDRED_AND_FIFTY = 0.10; // 101 to 150
	
	private static final double ONE_HUNDRED_AND_FIFTY_TO_TWO_HUNDRED = 0.20; // 151 to 200
	
	private static final double TWO_HUNDRED_TO_THREE_HUNDRED = 0.40; // 201 to 300
	
	private static final double THREE_HUNDRED_TO_FOUR_HUNDRED = 0.60; // 301 to 400
	
	private static final double UPWARDS_FOUR_HUNDRED = 1.2; // upwards 401

	/**
	 * calculate tax for given income.
	 * 
	 * @param income - int value
	 * @return tax - int value
	 */
	public static int calculateTax(final int income) {
		return calculateTaxByIncome(income);
	}

	/**
	 * calculate net income.
	 * 
	 * @param income - int value
	 * @return net income - int value
	 */
	public static int calculateNetIncome(final int income) {
		final int netIncome = income - calculateTaxByIncome(income);
		if (netIncome < 0) {
			return 0;
		} else {
			return netIncome;
		}

	}

	/**
	 * calculate tax.
	 * 
	 * @param income - double value
	 * @return tax by given income - int value
	 */
	private static int calculateTaxByIncome(final int income) {
		double tax = 0.0;
		double tIncome = 0.0;
		if (income <= 100) {
			tax = ZERO_TO_HUNDRED * 100;
		} else if (income > 100 && income <= 150) {
			tIncome = income - 100;
			tax = (ZERO_TO_HUNDRED * 100) + HUNDRED_TO_ONE_HUNDRED_AND_FIFTY * tIncome;
		} else if (income > 150 && income <= 200) {
			tIncome = income - 150;
			tax = (ZERO_TO_HUNDRED * 100) + HUNDRED_TO_ONE_HUNDRED_AND_FIFTY * 50 + ONE_HUNDRED_AND_FIFTY_TO_TWO_HUNDRED * tIncome;
		} else if (income > 200 && income <= 300) {
			tIncome = income - 200;
			tax = ZERO_TO_HUNDRED * 100 + HUNDRED_TO_ONE_HUNDRED_AND_FIFTY * 50 + ONE_HUNDRED_AND_FIFTY_TO_TWO_HUNDRED * 50 + TWO_HUNDRED_TO_THREE_HUNDRED * tIncome;
		} else if (income > 300 && income <= 400) {
			tIncome = income - 300;
			tax = ZERO_TO_HUNDRED * 100 + HUNDRED_TO_ONE_HUNDRED_AND_FIFTY * 50 + ONE_HUNDRED_AND_FIFTY_TO_TWO_HUNDRED * 50 + TWO_HUNDRED_TO_THREE_HUNDRED * 100 + THREE_HUNDRED_TO_FOUR_HUNDRED
					* tIncome;
		} else {
			tIncome = income - 400;
			tax = ZERO_TO_HUNDRED * 100 + HUNDRED_TO_ONE_HUNDRED_AND_FIFTY * 50 + ONE_HUNDRED_AND_FIFTY_TO_TWO_HUNDRED * 50 + TWO_HUNDRED_TO_THREE_HUNDRED * 100 + THREE_HUNDRED_TO_FOUR_HUNDRED
					* 100 + UPWARDS_FOUR_HUNDRED * tIncome;
		}
		
		return (int) Math.round(tax);
	}
}
