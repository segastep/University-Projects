package com.pizzacompany.toppings;

import com.pizzacompany.PizzaTopping;

public class CheeseToppings extends PizzaTopping {

	private static final String NAME = "Cheese";
	private static final Double COST = 1.0;

	public CheeseToppings() {
		super(NAME, COST, true);
	}

}
