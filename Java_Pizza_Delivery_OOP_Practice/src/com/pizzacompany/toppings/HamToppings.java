package com.pizzacompany.toppings;

import com.pizzacompany.PizzaTopping;

public class HamToppings extends PizzaTopping {
	
	private static final String NAME = "Ham";
	private static final Double COST = 3.5;

	public HamToppings() {
		super(NAME, COST, false);
	}

}
