package com.pizzacompany;

import java.util.Scanner;

public class PizzaMenu {

	public static void main(String[] args) {
		printPizzaMenu();
		System.out.println("Please select pizza!");
		System.out.println("\t 1) Veggi Pizza");
		System.out.println("\t 2) Ham Pizza");
		System.out.println("\t 3) Margherita Pizza");
		Scanner scan = new Scanner(System.in);
		while (true) {
			int pizzaType = scan.nextInt();
			if(pizzaType == 1){
				printPizza(PizzaBuild.buildVeggiPizza(), "Veggi Pizza");
			} else if(pizzaType == 2){
				printPizza(PizzaBuild.buildHamPizza(), "Ham Pizza");
			} else if(pizzaType == 3){
				printPizza(PizzaBuild.buildMargheritaPizza(), "Margherita Pizza");
			} else {
				System.out.println("Exit");
				break;
			}
		
		}
	}

	private static void printPizzaMenu() {
		printLogo();
	}

	private static void printLogo() {
		System.out.println("===========================================================");
		System.out.println("|                      ++Pizza Menu++                     |");
		System.out.println("===========================================================");
		System.out.println();
	}

	private static void printPizza(Pizza pizza, String pizzaName) {
		String headerFormat = "| %-52s |%n";
		System.out.format("+------------------------------------------------------+%n");
		if(pizza.isVegetarian()){
			System.out.format(headerFormat, pizzaName + " - Vegetarian");
		} else {
			System.out.format(headerFormat, pizzaName);
		}
		System.out.format("+----------------------+------------------+------------+%n");
		System.out.printf("|Type                  |Item              |Price       |%n");
		System.out.format("+----------------------+------------------+------------+%n");
		String leftAlignFormat = "| %-20s | %-16s | %-10s |%n";
		System.out.format(leftAlignFormat, "Base", pizza.getBase().getBase(), PizzaBuild.moneyFormatter(pizza.getBase().getCost()));
		for (String key : pizza.getToppings().keySet()) {
			 System.out.format(leftAlignFormat, "Toppings", pizza.getToppings().get(key).getName(), 
					 PizzaBuild.moneyFormatter(pizza.getToppings().get(key).getCost()) );
		}
		String leftAlignTotalFormat = "| %-39s | %-10s |%n";
		System.out.format("+----------------------+------------------+------------+%n");
		System.out.format(leftAlignTotalFormat, "Toppings Total",
				PizzaBuild.moneyFormatter(PizzaBuild.calculateTotalCostOfToppings(pizza.getToppings())));
		System.out.format("+----------------------+------------------+------------+%n");
		System.out.format(leftAlignTotalFormat, "Total", PizzaBuild.moneyFormatter(PizzaBuild.calculateTotalCost(pizza)));
		System.out.format("+----------------------+------------------+------------+%n");
		

	}
}
