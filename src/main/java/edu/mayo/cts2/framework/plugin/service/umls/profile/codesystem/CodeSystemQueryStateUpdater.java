package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference.StateUpdater;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;

public class CodeSystemQueryStateUpdater implements StateUpdater<QueryBuilder>{

	@Override
	public QueryBuilder updateState(QueryBuilder currentState,
			MatchAlgorithmReference matchAlgorithm, 
			String queryString) {
		return 
				QueryBuilders.wildcardQuery("codeSystem.descriptions.value", queryString+"*");
	}

}
