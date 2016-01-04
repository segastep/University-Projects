package com.pizzacompany.toppings;

import com.pizzacompany.PizzaTopping;

public class TomatoesToppings extends PizzaTopping {
	
	private static final String NAME = "Tomatos";
	private static final Double COST = 1.75;

	public TomatoesToppings() {
		super(NAME, COST, true);
	}
}
