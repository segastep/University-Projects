package com.pizzacompany.toppings;

import com.pizzacompany.PizzaTopping;

public class BBQSauceToppings extends PizzaTopping {
	
	private static final String NAME = "BBQ Sauce";
	private static final Double COST = 2.0;

	public BBQSauceToppings() {
		super(NAME, COST, true);
	}

}
