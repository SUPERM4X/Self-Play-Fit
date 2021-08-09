package com.java016.playfit.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="time_period")
public class TimePeriod {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String name;

	//bi-directional many-to-one association to Meal
	@OneToMany(mappedBy="timePeriod")
	private List<Meal> meals;

	public TimePeriod() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Meal> getMeals() {
		return this.meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public Meal addMeal(Meal meal) {
		getMeals().add(meal);
		meal.setTimePeriod(this);

		return meal;
	}

	public Meal removeMeal(Meal meal) {
		getMeals().remove(meal);
		meal.setTimePeriod(null);

		return meal;
	}
}
