package com.pizzacompany;

public abstract class PizzaTopping {

	private String name;
	private double cost;
	private boolean vegetarian;

	public PizzaTopping(String name, double cost, boolean vegetarian) {
		this.name = name;
		this.cost = cost;
		this.vegetarian = vegetarian;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(boolean vegetarian) {
		this.vegetarian = vegetarian;
	}

}
