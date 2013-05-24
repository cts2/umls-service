package edu.mayo.cts2.framework.plugin.service.umls.index;

import java.util.ArrayList;
import java.util.List;

public class IndexableEntity {
	
	private String name;
	
	private String sab;

    private String vab;
	
	private List<Description> descriptions = new ArrayList<Description>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}
	
	public String getSab() {
		return sab;
	}

	public void setSab(String sab) {
		this.sab = sab;
	}

    public String getVab() {
        return vab;
    }

    public void setVab(String vab) {
        this.vab = vab;
    }

    public static class Description {
		
		private String value;
		
		public Description(){
			super();
		}
		
		public Description(String value){
			super();
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	
}
