package com.java016.playfit.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Food {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private float kcal;

	private String name;

	private String type;

	//bi-directional many-to-one association to Meal
	@OneToMany(mappedBy="food")
	private List<Meal> meals;

	public Food() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getKcal() {
		return this.kcal;
	}

	public void setKcal(float kcal) {
		this.kcal = kcal;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Meal> getMeals() {
		return this.meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public Meal addMeal(Meal meal) {
		getMeals().add(meal);
		meal.setFood(this);

		return meal;
	}

	public Meal removeMeal(Meal meal) {
		getMeals().remove(meal);
		meal.setFood(null);

		return meal;
	}
}
