package com.java016.playfit.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="daily_record")
public class DailyRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String content;

	@Column(name="created_date")
	private Date createdDate;

	@Column(name="kcal_burned")
	private int kcalBurned;

	@Column(name="kcal_intake")
	private int kcalIntake;

	private String title;

	private int status;
	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	//bi-directional many-to-one association to FitAchieve
	@OneToMany(mappedBy="dailyRecord")
	private List<FitAchieve> fitAchieves;

	//bi-directional many-to-one association to Meal
	@OneToMany(mappedBy="dailyRecord")
	private List<Meal> meals;

	public DailyRecord() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getKcalBurned() {
		return this.kcalBurned;
	}

	public void setKcalBurned(int kcalBurned) {
		this.kcalBurned = kcalBurned;
	}

	public int getKcalIntake() {
		return this.kcalIntake;
	}

	public void setKcalIntake(int kcalIntake) {
		this.kcalIntake = kcalIntake;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<FitAchieve> getFitAchieves() {
		return this.fitAchieves;
	}

	public void setFitAchieves(List<FitAchieve> fitAchieves) {
		this.fitAchieves = fitAchieves;
	}

	public FitAchieve addFitAchieve(FitAchieve fitAchieve) {
		getFitAchieves().add(fitAchieve);
		fitAchieve.setDailyRecord(this);

		return fitAchieve;
	}

	public FitAchieve removeFitAchieve(FitAchieve fitAchieve) {
		getFitAchieves().remove(fitAchieve);
		fitAchieve.setDailyRecord(null);

		return fitAchieve;
	}

	public List<Meal> getMeals() {
		return this.meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public Meal addMeal(Meal meal) {
		getMeals().add(meal);
		meal.setDailyRecord(this);

		return meal;
	}

	public Meal removeMeal(Meal meal) {
		getMeals().remove(meal);
		meal.setDailyRecord(null);

		return meal;
	}
}
