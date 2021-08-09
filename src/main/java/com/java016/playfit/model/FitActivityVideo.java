package com.java016.playfit.model;

import java.sql.Time;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="fit_activity_video")
public class FitActivityVideo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="file_name")
	private String fileName;

	@Column(name="mime_type")
	private String mimeType;

	private String name;

	private Time time;

	@Lob
	private byte[] video;

	//bi-directional many-to-one association to FitActivity
	@OneToMany(mappedBy="fitActivityVideo")
	private List<FitActivity> fitActivities;

	public FitActivityVideo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
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

	public byte[] getVideo() {
		return this.video;
	}

	public void setVideo(byte[] video) {
		this.video = video;
	}

	public List<FitActivity> getFitActivities() {
		return this.fitActivities;
	}

	public void setFitActivities(List<FitActivity> fitActivities) {
		this.fitActivities = fitActivities;
	}

	public FitActivity addFitActivity(FitActivity fitActivity) {
		getFitActivities().add(fitActivity);
		fitActivity.setFitActivityVideo(this);

		return fitActivity;
	}

	public FitActivity removeFitActivity(FitActivity fitActivity) {
		getFitActivities().remove(fitActivity);
		fitActivity.setFitActivityVideo(null);

		return fitActivity;
	}
}
