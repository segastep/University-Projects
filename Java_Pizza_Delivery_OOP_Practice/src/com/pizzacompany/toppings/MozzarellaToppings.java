package com.pizzacompany.toppings;

import com.pizzacompany.PizzaTopping;

public class MozzarellaToppings extends PizzaTopping {
	
	private static final String NAME = "Mozzarella";
	private static final Double COST = 1.75;

	public MozzarellaToppings() {
		super(NAME, COST, true);
	}

}
