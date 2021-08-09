package com.java016.playfit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Meal {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int quantity;

	@Column(name="total_kcal")
	private int totalKcal;

	//bi-directional many-to-one association to DailyRecord
	@ManyToOne
	@JoinColumn(name="daily_record_id")
	private DailyRecord dailyRecord;

	//bi-directional many-to-one association to Food
	@ManyToOne
	private Food food;

	//bi-directional many-to-one association to TimePeriod
	@ManyToOne
	@JoinColumn(name="time_period_id")
	private TimePeriod timePeriod;

	public Meal() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalKcal() {
		return this.totalKcal;
	}

	public void setTotalKcal(int totalKcal) {
		this.totalKcal = totalKcal;
	}

	public DailyRecord getDailyRecord() {
		return this.dailyRecord;
	}

	public void setDailyRecord(DailyRecord dailyRecord) {
		this.dailyRecord = dailyRecord;
	}

	public Food getFood() {
		return this.food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public TimePeriod getTimePeriod() {
		return this.timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}
}
