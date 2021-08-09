package com.java016.playfit.model;

import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="fit_activity")
public class FitActivity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="body_part")
	private String bodyPart;

	private int description;

	@Column(name="kcal_burn")
	private float kcalBurn;

	private String name;

	private Time time;

	//bi-directional many-to-one association to FitAchieve
	@OneToMany(mappedBy="fitActivity")
	private List<FitAchieve> fitAchieves;

	//bi-directional many-to-one association to FitActivityVideo
	@ManyToOne
	@JoinColumn(name="video_id")
	private FitActivityVideo fitActivityVideo;


	public FitActivity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBodyPart() {
		return this.bodyPart;
	}

	public void setBodyPart(String bodyPart) {
		this.bodyPart = bodyPart;
	}

	public int getDescription() {
		return this.description;
	}

	public void setDescription(int description) {
		this.description = description;
	}

	public float getKcalBurn() {
		return this.kcalBurn;
	}

	public void setKcalBurn(float kcalBurn) {
		this.kcalBurn = kcalBurn;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public List<FitAchieve> getFitAchieves() {
		return this.fitAchieves;
	}

	public void setFitAchieves(List<FitAchieve> fitAchieves) {
		this.fitAchieves = fitAchieves;
	}

	public FitAchieve addFitAchieve(FitAchieve fitAchieve) {
		getFitAchieves().add(fitAchieve);
		fitAchieve.setFitActivity(this);

		return fitAchieve;
	}

	public FitAchieve removeFitAchieve(FitAchieve fitAchieve) {
		getFitAchieves().remove(fitAchieve);
		fitAchieve.setFitActivity(null);

		return fitAchieve;
	}

	public FitActivityVideo getFitActivityVideo() {
		return this.fitActivityVideo;
	}

	public void setFitActivityVideo(FitActivityVideo fitActivityVideo) {
		this.fitActivityVideo = fitActivityVideo;
	}

	
}