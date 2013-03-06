package edu.mayo.cts2.framework.plugin.service.umls.mapper;

import java.util.Date;

public class ReleaseInfoDTO {
	
	private String name;
	
	private Date releaseDate;
	
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
