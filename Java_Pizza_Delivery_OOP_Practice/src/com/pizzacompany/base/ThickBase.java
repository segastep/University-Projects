package com.pizzacompany.base;

import com.pizzacompany.PizzaBase;

/**
 * Thick pizza base.
 * @author
 *
 */
public class ThickBase extends PizzaBase{

	private static final String BASE = "Thick";
	private static final Double COST = 5.00;
	public ThickBase() {
		super(BASE, COST, true);
	}

}
