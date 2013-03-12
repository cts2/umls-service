package edu.mayo.cts2.framework.plugin.service.umls.mapper;

public class CodeDTO {
	
	private String ui;

	private String id;

	private String abbreviation;
	
	private String name;
	
	public String getSab() {
		return abbreviation;
	}

	public void setSab(String sab) {
		this.abbreviation = sab;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getUi() {
		return ui;
	}

	public void setUi(String ui) {
		this.ui = ui;
	}

	public String getName() {
		return name;
	}

	public void setName(String namep) {
		this.name = namep;
	}
}
