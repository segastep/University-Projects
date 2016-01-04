

/**
 * @author Georgi Nikolov
 * @date : 23/10/2014
 * @purpose: This class is used to draw thin bar chart using TaxCalculator 
 * class which is containing algorithm to calculate the tax based on certain income
 * TaxChart is taking the values from the TaxCalculator and draws a table and thin bar
 * chart, bars height is proportional to the calculated income and tax.
 *
 */
public class TaxChart {

	public TaxChart() {

	}

	/**
	 * print bar chart.
	 * 
	 * @param incomes- int array
	 */
	public void printTable(final int[] incomes) {
		 String i = "|";//used for chart
		 System.out.printf("%7s%6s%14s%n", "Income", "Tax", "Gross Income");
		 System.out.printf("%1s%n", "---------------------------");//Draws a horizontal line
		for (int income : incomes) {
			
			final int netIncome = (int) Math.round(TaxCalculator
					.calculateNetIncome(income));
			final int tax = (int) Math
					.round(TaxCalculator.calculateTax(income));
			System.out.printf("%1s%6d%1s%6d%1s%10d%1s%n" , i, income,i ,tax, i ,netIncome, i);
		}
		drawChartCanvas();
		drawBars(incomes);
	}

	/**
	 * draw chart bars.
	 * 
	 * @param incomes - int array
	 */
	private void drawBars(final int[] incomes) {
		int position = 5;
		for (int income : incomes) {
			
			final int netIncome = (int) Math.round(TaxCalculator
					.calculateNetIncome(income) * 0.25); // reduce 0.75 from actual bar size
				
			final int tax =  +(int) Math
					.round(TaxCalculator.calculateTax(income) * 0.25); // reduce 0.75 from actual bar size
			
			Bar barNetIncome = new Bar(); //creates object	
			barNetIncome.changeSize(3, netIncome); // changes size using netIncome
			barNetIncome.changeColour(Colour.YELLOW); // sets color
			barNetIncome.moveVertical(200 - netIncome); //mover vertical using net income
			// so it can match the Y axes
			barNetIncome.makeVisible();// makes visible
			barNetIncome.moveHorizontal(position);//sets position

			Bar barTax = new Bar();
			barTax.changeSize(3, tax);
			barTax.changeColour(Colour.RED);
			barTax.moveVertical(200 -  netIncome - tax);
			barTax.makeVisible();
			barTax.moveHorizontal(position);
			position += 10; //adds 10 to position so bars can be ordered
		}
	}

	/**
	 * draw canvas and X, Y axis.
	 */
	private void drawChartCanvas() {
		Bar xAxe = new Bar();
		xAxe.changeColour(Colour.BLUE); // changes color
		xAxe.changeSize(1, 270); // changes size
		xAxe.moveHorizontal(-30); // Moves horizontal
		xAxe.moveVertical(-40); // -//- vertical
		xAxe.makeVisible(); // makes visible

		Bar yAxe = new Bar();
		yAxe.changeColour(Colour.BLUE);
		yAxe.makeVisible();
		yAxe.changeSize(270, 1);
		yAxe.moveHorizontal(-40);
		yAxe.moveVertical(200);
	}

}
