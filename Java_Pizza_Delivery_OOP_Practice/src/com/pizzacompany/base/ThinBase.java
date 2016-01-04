package com.pizzacompany.base;

import com.pizzacompany.PizzaBase;

/**
 * Thin pizza base.
 * 
 * @author
 *
 */
public class ThinBase extends PizzaBase {

	private static final String BASE = "Thin";
	private static final Double COST = 4.00;

	public ThinBase() {
		super(BASE, COST, true);
	}
}
