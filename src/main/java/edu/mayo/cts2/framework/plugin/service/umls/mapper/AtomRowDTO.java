package edu.mayo.cts2.framework.plugin.service.umls.mapper;

public class AtomRowDTO {
	
	private String ui;

    private String source;

	private String rootSource;
	
	private String string;

	public String getUi() {
		return ui;
	}

	public void setUi(String ui) {
		this.ui = ui;
	}

	public String getRootSource() {
		return rootSource;
	}

	public void setRootSource(String rootSource) {
		this.rootSource = rootSource;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
