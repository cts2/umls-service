package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;


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
import edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem.CodeSystemQueryBuilderFactory.CodeSystemQueryBuilder;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;
import edu.mayo.cts2.framework.service.meta.StandardModelAttributeReference;
import edu.mayo.cts2.framework.service.profile.ResourceQuery;
import edu.mayo.cts2.framework.service.profile.codesystem.CodeSystemQueryService;

public class UmlsCodeSystemQueryService extends AbstractUmlsBaseService
		implements CodeSystemQueryService {

	@Resource
	private CodeSystemQueryBuilderFactory codeSystemQueryBuilderFactory;

	private CodeSystemQueryStateUpdater stateUpdater = new CodeSystemQueryStateUpdater();

	@Override
	public Set<StateAdjustingPropertyReference<String>> getSupportedSearchReferences() {
		Set<StateAdjustingPropertyReference<String>> returnSet = new HashSet<StateAdjustingPropertyReference<String>>();

		PropertyReference resourceSynopsisRef = StandardModelAttributeReference.RESOURCE_SYNOPSIS
				.getPropertyReference();

		StateAdjustingPropertyReference<String> ref = StateAdjustingPropertyReference
				.toPropertyReference(resourceSynopsisRef, this.stateUpdater);

		returnSet.add(ref);

		return returnSet;
	}

	@Override
	public Set<PropertyReference> getSupportedSortReferences() {
		return new HashSet<PropertyReference>();
	}

	@Override
	public Set<PredicateReference> getKnownProperties() {
		return new HashSet<PredicateReference>();
	}

	@Override
	public DirectoryResult<CodeSystemCatalogEntrySummary> getResourceSummaries(
			ResourceQuery query, SortCriteria sortCriteria, Page page) {
		CodeSystemQueryBuilder queryBuilder = this.codeSystemQueryBuilderFactory.createCodeSystemQueryBuilder(
						this.getSupportedMatchAlgorithms(),
						this.getSupportedSearchReferences());

		return queryBuilder.addQuery(query)
				.addMaxToReturn(page.getMaxToReturn())
				.addStart(page.getStart()).resolve();
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

	@Override
	public Set<MatchAlgorithmReference> getSupportedMatchAlgorithms() {

		return new HashSet<MatchAlgorithmReference>(Arrays.asList(
				StandardMatchAlgorithmReference.CONTAINS
						.getMatchAlgorithmReference(),
				StandardMatchAlgorithmReference.EXACT_MATCH
						.getMatchAlgorithmReference(),
				StandardMatchAlgorithmReference.STARTS_WITH
						.getMatchAlgorithmReference()));

	}

}
