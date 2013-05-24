package edu.mayo.cts2.framework.plugin.service.umls.profile.entity;

import edu.mayo.cts2.framework.filter.directory.AbstractStateBuildingDirectoryBuilder;
import edu.mayo.cts2.framework.filter.directory.AbstractStateBuildingDirectoryBuilder.Callback;
import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.entity.EntityDescription;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.model.service.core.NameOrURI;
import edu.mayo.cts2.framework.plugin.service.umls.domain.entity.EntityRepository;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

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

	protected EntityDescriptionQueryBuilder createEntityDescriptionQueryBuilder(
			Set<MatchAlgorithmReference> matchAlgorithmReferences,
			Set<StateAdjustingPropertyReference<QueryBuilder>> stateAdjustingPropertyReferences){

		return new EntityDescriptionQueryBuilder(
				QueryBuilders.matchAllQuery(), 
				new EntityDescriptionCallback(), 
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
			return (int) entityRepository.count(state);
		}
		
	}
	
	protected static class EntityDescriptionQueryBuilder
		extends
		AbstractStateBuildingDirectoryBuilder<QueryBuilder, EntityDescription> {

		protected EntityDescriptionQueryBuilder(
				QueryBuilder initialState,
				Callback<QueryBuilder, EntityDescription> callback,
				Set<MatchAlgorithmReference> matchAlgorithmReferences,
				Set<StateAdjustingPropertyReference<QueryBuilder>> stateAdjustingPropertyReferences) {
			super(initialState, callback, matchAlgorithmReferences,
					stateAdjustingPropertyReferences);
		}

		protected EntityDescriptionQueryBuilder addQuery(EntityDescriptionQuery query){
				if(query != null){
					if(query.getFilterComponent() != null){
						this.restrict(query.getFilterComponent());
					}
                    if(query.getRestrictions() != null && query.getRestrictions().getCodeSystemVersions() != null){
                        Set<NameOrURI> versions = query.getRestrictions().getCodeSystemVersions();

                        for(NameOrURI version : versions){
                            this.updateState(
                                QueryBuilders.boolQuery().must(this.getState()).must(QueryBuilders.termQuery("vsab", version)));
                        }
                    }
				}
	
			return this;
		}
	}

	private class EntityDescriptionCallback implements Callback<QueryBuilder, EntityDescription> {

		@Override
		public DirectoryResult<EntityDescription> execute(
				QueryBuilder state, 
				int start, 
				int maxResults) {
			return entityRepository.getEntityDescriptionListEntriesByKeyword(state, start, start + maxResults);
		}

		@Override
		public int executeCount(QueryBuilder state) {
			return (int) entityRepository.count(state);
		}
	}
}
