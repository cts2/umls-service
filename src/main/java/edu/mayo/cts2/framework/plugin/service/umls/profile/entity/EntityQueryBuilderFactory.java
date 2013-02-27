package edu.mayo.cts2.framework.plugin.service.umls.profile.entity;

import java.util.Set;

import javax.annotation.Resource;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.filter.directory.AbstractStateBuildingDirectoryBuilder;
import edu.mayo.cts2.framework.filter.directory.AbstractStateBuildingDirectoryBuilder.Callback;
import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.plugin.service.umls.domain.entity.EntityRepository;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery;

@Component
public class EntityQueryBuilderFactory {
	
	@Resource
	private EntityRepository entityRepository;
	
	protected EntityQueryBuilder createEntityQueryBuilder(
			Set<MatchAlgorithmReference> matchAlgorithmReferences,
			Set<StateAdjustingPropertyReference<QueryBuilder>> stateAdjustingPropertyReferences){

		return new EntityQueryBuilder(
				QueryBuilders.matchAllQuery(), 
				new EntityCallback(), 
				matchAlgorithmReferences,
				stateAdjustingPropertyReferences);
	}

	protected static class EntityQueryBuilder
			extends
			AbstractStateBuildingDirectoryBuilder<QueryBuilder, EntityDirectoryEntry> {

		protected EntityQueryBuilder(
				QueryBuilder initialState,
				Callback<QueryBuilder, EntityDirectoryEntry> callback,
				Set<MatchAlgorithmReference> matchAlgorithmReferences,
				Set<StateAdjustingPropertyReference<QueryBuilder>> stateAdjustingPropertyReferences) {
			super(initialState, callback, matchAlgorithmReferences,
					stateAdjustingPropertyReferences);
		}
		
		protected EntityQueryBuilder addQuery(EntityDescriptionQuery query){
			if(query != null){
				if(query.getFilterComponent() != null){
					this.restrict(query.getFilterComponent());
				}
			}
			
			return this;
		}

	}
	
	private class EntityCallback implements Callback<QueryBuilder, EntityDirectoryEntry> {

		@Override
		public DirectoryResult<EntityDirectoryEntry> execute(
				QueryBuilder state, 
				int start, 
				int maxResults) {
			return entityRepository.getEntityDirectoryEntriesByKeyword(state, start, start + maxResults);
		}

		@Override
		public int executeCount(QueryBuilder state) {
			throw new UnsupportedOperationException();
		}
		
	}
}
