package edu.mayo.cts2.framework.plugin.service.umls.mapper;

import java.io.Serializable;

public class RootSourceDTO implements Serializable {

	private static final long serialVersionUID = 3750057062167264839L;
	
	private String abbreviation;

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

}
