package com.pizzacompany;

/**
 * Pizza base class.
 * 
 * @author
 *
 */
public abstract class PizzaBase {

	/**
	 * Pizza base.
	 */
	private String base;
	/**
	 * Pizza base cost.
	 */
	private Double cost;
	/**
	 * is it vegetarina or not.
	 */
	private boolean vegetarian;

	public PizzaBase(String base, Double cost, boolean vegetarian) {
		this.base = base;
		this.cost = cost;
		this.vegetarian = vegetarian;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public final boolean isVegetarian() {
		return vegetarian;
	}

	public final void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

}
