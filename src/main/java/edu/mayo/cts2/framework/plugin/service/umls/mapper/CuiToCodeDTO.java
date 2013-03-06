package edu.mayo.cts2.framework.plugin.service.umls.mapper;

public class CuiToCodeDTO {

	private String rootSource;
	
	private String conceptUI;
	
	private String codeUI;

	public String getRootSource() {
		return rootSource;
	}

	public void setRootSource(String rootSource) {
		this.rootSource = rootSource;
	}

	public String getConceptUI() {
		return conceptUI;
	}

	public void setConceptUI(String conceptUI) {
		this.conceptUI = conceptUI;
	}

	public String getCodeUI() {
		return codeUI;
	}

	public void setCodeUI(String codeUI) {
		this.codeUI = codeUI;
	}
		
		
}
