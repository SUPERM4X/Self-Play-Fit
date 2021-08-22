package com.java016.playfit.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name="fit_achieve")
public class FitAchieve {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="execution_date")
	@Temporal(TemporalType.DATE)
	private Date executionDate;
	
	@Column(name="end_time")
	private Timestamp endTime;
	
	@Column(name="created_at", 
			nullable=false, 
			updatable=false, 
			insertable=false, 
			columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	
	private Timestamp createdAt;
	
	private String status;

	@Column(name="number_groups")
	private int numberGroups;

	@Column(name="total_kcal")
	private int totalKcal;

	//bi-directional many-to-one association to DailyRecord
	@ManyToOne
	@JoinColumn(name="daily_record_id")
	private DailyRecord dailyRecord;

	//bi-directional many-to-one association to FitActivityRepository
	@ManyToOne
	@JoinColumn(name="fit_activity_id")
	private FitActivity fitActivity;

	public FitAchieve() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNumberGroups() {
		return this.numberGroups;
	}

	public void setNumberGroups(int numberGroups) {
		this.numberGroups = numberGroups;
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

	public FitActivity getFitActivity() {
		return this.fitActivity;
	}

	public void setFitActivity(FitActivity fitActivity) {
		this.fitActivity = fitActivity;
	}
}
