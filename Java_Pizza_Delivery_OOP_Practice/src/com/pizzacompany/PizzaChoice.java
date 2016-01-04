package com.pizzacompany;

import java.util.HashMap;
import java.util.Scanner;

import com.pizzacompany.base.ThickBase;
import com.pizzacompany.base.ThinBase;
import com.pizzacompany.toppings.BBQSauceToppings;
import com.pizzacompany.toppings.BasilToppings;
import com.pizzacompany.toppings.CheeseToppings;
import com.pizzacompany.toppings.HamToppings;
import com.pizzacompany.toppings.MozzarellaToppings;
import com.pizzacompany.toppings.SalamiToppings;
import com.pizzacompany.toppings.TomatoesToppings;

public class PizzaChoice {

	public static void main(String[] args) {
		System.out.println("=================Order New Pizza==================");
		System.out.println("Please select base of pizza.");
		System.out.println("\t 1) Thick");
		System.out.println("\t 2) Thin");
		System.out.println("Please enter the number for the base?");
		Scanner scan = new Scanner(System.in);
		PizzaBase pizzaBase = null;
		while (true) {
			int base = scan.nextInt();
			if (base == 1) {
				pizzaBase = new ThickBase();
				System.out.println("Thick base selected.");
				break;
			} else if (base == 2) {
				pizzaBase = new ThinBase();
				System.out.println("Thin base selected.");
				break;
			} else {
				System.out.println("Invalid selection. Please select correct base.");
			}
		}

		System.out.println("Please select toppings.");
		System.out.println("\t 1) Cheese");
		System.out.println("\t 2) Ham");
		System.out.println("\t 3) Tomatos");
		System.out.println("\t 4) Salami");
		System.out.println("\t 5) BBQ sauce");
		System.out.println("\t 6) Mozzarella");
		System.out.println("\t 7) Basil");
		System.out.println("\t 8) Finish!");

		// toppings
		HashMap<String, PizzaTopping> toppings = new HashMap<String, PizzaTopping>();

		while (true) {
			int topping = scan.nextInt();
			if (topping == 1) {
				PizzaTopping cheese = new CheeseToppings();
				if (toppings.containsKey(cheese.getName())) {
					System.out.println("Cheese already added.");
				} else {
					toppings.put(cheese.getName(), cheese);
					System.out.println("Cheese added.");
				}
			} else if (topping == 2) {
				PizzaTopping ham = new HamToppings();
				if (toppings.containsKey(ham.getName())) {
					System.out.println("Ham already added.");
				} else {
					toppings.put(ham.getName(), ham);
					System.out.println("Ham added.");
				}
			} else if (topping == 3) {
				PizzaTopping tomatoes = new TomatoesToppings();
				if (toppings.containsKey(tomatoes.getName())) {
					System.out.println("Tomatoes already added.");
				} else {
					toppings.put(tomatoes.getName(), tomatoes);
					System.out.println("Tomatoes added.");
				}
			} else if (topping == 4) {
				PizzaTopping salami = new SalamiToppings();
				if (toppings.containsKey(salami.getName())) {
					System.out.println("Salami already added.");
				} else {
					toppings.put(salami.getName(), salami);
					System.out.println("Salami added.");
				}
			} else if (topping == 5) {
				PizzaTopping bbq = new BBQSauceToppings();
				if (toppings.containsKey(bbq.getName())) {
					System.out.println("BBQ Sauce already added.");
				} else {
					toppings.put(bbq.getName(), bbq);
					System.out.println("BBQ Sauce added.");
				}
			} else if (topping == 6) {
				PizzaTopping mozzarella = new MozzarellaToppings();
				if (toppings.containsKey(mozzarella.getName())) {
					System.out.println("Mozarella already added.");
				} else {
					toppings.put(mozzarella.getName(), mozzarella);
					System.out.println("Mozarella added.");
				}
			} else if (topping == 7) {
				PizzaTopping basil = new BasilToppings();
				if (toppings.containsKey(basil.getName())) {
					System.out.println("Basil already added.");
				} else {
					toppings.put(basil.getName(), basil);
					System.out.println("Basil added.");
				}
			} else if (topping == 8) {
				break;
			} else {
				System.out.println("Please enter correct number.");
			}
		}
		Pizza pizza = new Pizza();
		pizza.setBase(pizzaBase);
		pizza.setToppings(toppings);
		PizzaBuild.printPizza(pizza);
	}
}
