package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.elasticsearch.index.query.QueryBuilder;

import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntrySummary;
import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.core.PredicateReference;
import edu.mayo.cts2.framework.model.core.PropertyReference;
import edu.mayo.cts2.framework.model.core.SortCriteria;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.plugin.service.umls.profile.AbstractUmlsBaseService;
import edu.mayo.cts2.framework.plugin.service.umls.profile.entity.EntityQueryStateUpdater;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;
import edu.mayo.cts2.framework.service.meta.StandardModelAttributeReference;
import edu.mayo.cts2.framework.service.profile.ResourceQuery;
import edu.mayo.cts2.framework.service.profile.codesystem.CodeSystemQueryService;

public class UmlsCodeSystemQueryService extends AbstractUmlsBaseService implements CodeSystemQueryService{
	
//	private EntityQueryStateUpdater stateUpdater = new EntityQueryStateUpdater();
	private CodeSystemQueryStateUpdater stateUpdater = new CodeSystemQueryStateUpdater();
	
	@Override
	public Set<MatchAlgorithmReference> getSupportedMatchAlgorithms() {
		return new HashSet<MatchAlgorithmReference>(
			Arrays.asList(StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference()));
	}

	@Override
	public Set<? extends PropertyReference> getSupportedSearchReferences() {
		Set<StateAdjustingPropertyReference<QueryBuilder>> returnSet = 
				new HashSet<StateAdjustingPropertyReference<QueryBuilder>>();
			
			PropertyReference resourceSynopsisRef = 
				StandardModelAttributeReference.RESOURCE_SYNOPSIS.getPropertyReference();
			
			StateAdjustingPropertyReference<QueryBuilder> ref = 
				StateAdjustingPropertyReference.toPropertyReference(resourceSynopsisRef, this.stateUpdater);
			
			returnSet.add(ref);
			
			return returnSet;
	}

	@Override
	public Set<? extends PropertyReference> getSupportedSortReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<PredicateReference> getKnownProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirectoryResult<CodeSystemCatalogEntrySummary> getResourceSummaries(
			ResourceQuery query, SortCriteria sortCriteria, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirectoryResult<CodeSystemCatalogEntry> getResourceList(
			ResourceQuery query, SortCriteria sortCriteria, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(ResourceQuery query) {
		// TODO Auto-generated method stub
		return 0;
	}

}
