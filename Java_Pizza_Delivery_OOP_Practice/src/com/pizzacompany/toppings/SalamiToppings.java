package com.pizzacompany.toppings;

import com.pizzacompany.PizzaTopping;

public class SalamiToppings extends PizzaTopping {

	private static final String NAME = "Salami";
	private static final Double COST = 4.0;

	public SalamiToppings() {
		super(NAME, COST, false);
	}


}
