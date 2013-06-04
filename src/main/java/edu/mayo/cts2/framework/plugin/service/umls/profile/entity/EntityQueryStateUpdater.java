package edu.mayo.cts2.framework.plugin.service.umls.profile.entity;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference.StateUpdater;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;

public class EntityQueryStateUpdater implements StateUpdater<QueryBuilder> {

	@Override
	public QueryBuilder updateState(
			QueryBuilder currentState,
			MatchAlgorithmReference matchAlgorithm, 
			String queryString) 
	{
		//return 
		//		QueryBuilders.boolQuery().must(currentState).must(QueryBuilders.wildcardQuery("entity.descriptions.value", queryString + "*"));

		String lowerString = queryString;
		
		if (lowerString != null)
			lowerString = lowerString.toLowerCase();
		
		return 
				QueryBuilders.boolQuery().must(currentState).must(QueryBuilders.wildcardQuery("entity.descriptions.value", lowerString + "*"));
	}
}
