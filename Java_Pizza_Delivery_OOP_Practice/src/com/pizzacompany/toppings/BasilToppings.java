package com.pizzacompany.toppings;

import com.pizzacompany.PizzaTopping;

public class BasilToppings extends PizzaTopping {
	
	private static final String NAME = "Basil";
	private static final Double COST = 1.25;

	public BasilToppings() {
		super(NAME, COST, true);
	}

}
