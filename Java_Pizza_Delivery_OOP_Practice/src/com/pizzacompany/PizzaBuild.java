package com.pizzacompany;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.pizzacompany.base.ThickBase;
import com.pizzacompany.base.ThinBase;
import com.pizzacompany.toppings.BBQSauceToppings;
import com.pizzacompany.toppings.BasilToppings;
import com.pizzacompany.toppings.CheeseToppings;
import com.pizzacompany.toppings.HamToppings;
import com.pizzacompany.toppings.MozzarellaToppings;
import com.pizzacompany.toppings.TomatoesToppings;

public class PizzaBuild {

	/**
	 * print pizza details.
	 * 
	 * @param pizza
	 */
	public static void printPizza(Pizza pizza) {
		System.out.format("+----------------------+------------------+------------+%n");
		System.out.printf("|Type                  |Item              |Price       |%n");
		System.out.format("+----------------------+------------------+------------+%n");
		String leftAlignFormat = "| %-20s | %-16s | %-10s |%n";
		System.out.format(leftAlignFormat, "Base", pizza.getBase().getBase(), pizza.getBase().getCost());
		for (String key : pizza.getToppings().keySet()) {
			String toppingCost = moneyFormatter(pizza.getToppings().get(key).getCost());
			System.out.format(leftAlignFormat, "Toppings", pizza.getToppings().get(key).getName(), toppingCost);
		}
		String leftAlignTotalFormat = "| %-39s | %-10s |%n";
		System.out.format("+----------------------+------------------+------------+%n");
		System.out.format(leftAlignTotalFormat, "Toppings Total",
				moneyFormatter(calculateTotalCostOfToppings(pizza.getToppings())));
		System.out.format("+----------------------+------------------+------------+%n");
		System.out.format(leftAlignTotalFormat, "Total", moneyFormatter(calculateTotalCost(pizza)));
		System.out.format("+----------------------+------------------+------------+%n");
		System.out.format("********************************************************%n");
		System.out.println();

	}

	/**
	 * Calculate total cost of pizza.
	 * 
	 * @param pizza
	 * @return total cost
	 */
	public static Double calculateTotalCost(final Pizza pizza) {
		return pizza.getBase().getCost() + calculateTotalCostOfToppings(pizza.getToppings());

	}

	/**
	 * calculate total cost of toppings.
	 * 
	 * @param toppings
	 * @return total cost of toppings.
	 */
	public static Double calculateTotalCostOfToppings(final HashMap<String, PizzaTopping> toppings) {
		Double cost = 0.0;
		for (String key : toppings.keySet()) {
			cost += toppings.get(key).getCost();
			;
		}
		return cost;
	}



	public static String moneyFormatter(Double cost) {
		return new DecimalFormat("#0.00").format(cost);
	}

	/**
	 * Main Method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// build thick pizza with cheese, BBQ sauce
		printPizza(buildVeggiPizza());

		// build thin pizza with ham, cheese and BBQ sauce
		printPizza(buildHamPizza());
		
		// build Margherita pizza
		printPizza(buildMargheritaPizza());

	}

	/**
	 * check pizza is vegetarian or not.
	 * 
	 * @param pizza
	 * @return boolean true for vegetarian pizza
	 */
	private static boolean checkVegetarian(Pizza pizza) {
		// check base
		if (!pizza.getBase().isVegetarian()) {
			return false;
		}
		// check toppings
		for (String key : pizza.getToppings().keySet()) {
			if (!pizza.getToppings().get(key).isVegetarian()) {
				return false;
			}
		}
		return true;
	}
	
	/********************************************************************************
	 *********************************PRE DEFINED PIZZA******************************
	 ********************************************************************************
	
	/**
	 * Build thick pizza with cheese and tomatos.
	 * 
	 * @return
	 */
	public static Pizza buildVeggiPizza() {
		// build thick pizza with cheese, tomatos
		Pizza pizza = new Pizza();
		PizzaBase base = new ThickBase();

		HashMap<String, PizzaTopping> toppings = new HashMap<String, PizzaTopping>();
		PizzaTopping cheese = new CheeseToppings();
		PizzaTopping tomatos = new TomatoesToppings();
		toppings.put(cheese.getName(), cheese);
		toppings.put(tomatos.getName(), tomatos);

		pizza.setBase(base);
		pizza.setToppings(toppings);
		pizza.setVegetarian(checkVegetarian(pizza));
		return pizza;
	}

	/**
	 * Build thin pizza with cheese, ham and BBQ sauce.
	 * 
	 * @return
	 */
	public static Pizza buildHamPizza() {
		// build thick pizza with cheese, tomatos
		Pizza pizza = new Pizza();
		PizzaBase base = new ThinBase();

		HashMap<String, PizzaTopping> toppings = new HashMap<String, PizzaTopping>();
		PizzaTopping cheese = new CheeseToppings();
		PizzaTopping ham = new HamToppings();
		PizzaTopping bbq = new BBQSauceToppings();
		toppings.put(cheese.getName(), cheese);
		toppings.put(ham.getName(), ham);
		toppings.put(bbq.getName(), bbq);

		pizza.setBase(base);
		pizza.setToppings(toppings);
		pizza.setVegetarian(checkVegetarian(pizza));
		return pizza;
	}

	/**
	 * Build thin pizza with cheese, ham and BBQ sauce.
	 * 
	 * @return
	 */
	public static Pizza buildMargheritaPizza() {
		// build thick pizza with cheese, tomatos
		Pizza pizza = new Pizza();
		PizzaBase base = new ThinBase();

		HashMap<String, PizzaTopping> toppings = new HashMap<String, PizzaTopping>();
		PizzaTopping tomatoes = new TomatoesToppings();
		PizzaTopping mozzarella = new MozzarellaToppings();
		PizzaTopping basil = new BasilToppings();
		toppings.put(tomatoes.getName(), tomatoes);
		toppings.put(mozzarella.getName(), mozzarella);
		toppings.put(basil.getName(), basil);

		pizza.setBase(base);
		pizza.setToppings(toppings);
		pizza.setVegetarian(checkVegetarian(pizza));
		return pizza;
	}
}
