package com.winecellar.domain;

import lombok.Data;

@Data
public class Wine {

	private String id;
	private String name;
	private String grapes;
	private String country;
	private String region;
	private String year;
	private String description;
	private String picture;
}
