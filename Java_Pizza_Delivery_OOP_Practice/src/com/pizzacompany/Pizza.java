package com.pizzacompany;

import java.util.HashMap;

/**
 * Pizza class
 * 
 * @author
 *
 */
public class Pizza {

	/**
	 * Pizza base
	 */
	private PizzaBase base;
	/**
	 * toppings
	 */
	private HashMap<String, PizzaTopping> toppings;
	/**
	 * true for vegitarian.
	 */
	private boolean vegetarian;

	public Pizza() {
	}

	public Pizza(PizzaBase base, HashMap<String, PizzaTopping> toppings) {
		this.base = base;
		this.toppings = toppings;
	}

	public final PizzaBase getBase() {
		return base;
	}

	public final void setBase(PizzaBase base) {
		this.base = base;
	}

	public final HashMap<String, PizzaTopping> getToppings() {
		return toppings;
	}

	public final void setToppings(HashMap<String, PizzaTopping> toppings) {
		this.toppings = toppings;
	}

	public final boolean isVegetarian() {
		return vegetarian;
	}

	public final void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

}
