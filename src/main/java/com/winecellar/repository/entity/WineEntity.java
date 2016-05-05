package com.winecellar.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WineEntity {
	@Id
	@GeneratedValue
    private Long id;
    private String name;
    private String grapes;
    private String country;
    private String region;
    private String year;
    private String description;
    private String picture;

    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrapes() {
		return grapes;
	}
	public void setGrapes(String grapes) {
		this.grapes = grapes;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public WineEntity() {}
	public WineEntity(Long id, String name, String grapes, String country, String region, String year, String description,
			String picture) {
		super();
		this.id = id;
		this.name = name;
		this.grapes = grapes;
		this.country = country;
		this.region = region;
		this.year = year;
		this.description = description;
		this.picture = picture;
	}
	@Override
	public String toString() {
		return "WineEntity [id=" + id + ", name=" + name + ", grapes=" + grapes + ", country=" + country + ", region="
				+ region + ", year=" + year + ", description=" + description + ", picture=" + picture + "]";
	}
	
	
    

}
