package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem;

import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference.StateUpdater;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;

public class CodeSystemQueryStateUpdater implements StateUpdater<String>{

	@Override
	public String updateState(String currentState,
			MatchAlgorithmReference matchAlgorithm, 
			String queryString) {
		if (matchAlgorithm.equals(StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference())) {
			//TODO
			
		}
		if (matchAlgorithm.equals(StandardMatchAlgorithmReference.EXACT_MATCH.getMatchAlgorithmReference())) {
			//TODO
		}
		if (matchAlgorithm.equals(StandardMatchAlgorithmReference.STARTS_WITH.getMatchAlgorithmReference())) {
			//TODO
		}
		
		return null;
				
	}

}
