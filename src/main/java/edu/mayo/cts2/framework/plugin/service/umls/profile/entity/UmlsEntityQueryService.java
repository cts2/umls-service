package edu.mayo.cts2.framework.plugin.service.umls.profile.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Component;

import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference;
import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.command.ResolvedFilter;
import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.core.EntityReferenceList;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.core.OpaqueData;
import edu.mayo.cts2.framework.model.core.PredicateReference;
import edu.mayo.cts2.framework.model.core.PropertyReference;
import edu.mayo.cts2.framework.model.core.SortCriteria;
import edu.mayo.cts2.framework.model.core.SourceReference;
import edu.mayo.cts2.framework.model.core.VersionTagReference;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.entity.EntityDescription;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.model.service.core.DocumentedNamespaceReference;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURI;
import edu.mayo.cts2.framework.model.service.core.EntityNameOrURIList;
import edu.mayo.cts2.framework.model.service.core.Query;
import edu.mayo.cts2.framework.plugin.service.umls.profile.AbstractUmlsBaseService;
import edu.mayo.cts2.framework.plugin.service.umls.profile.entity.EntityQueryBuilderFactory.EntityQueryBuilder;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;
import edu.mayo.cts2.framework.service.meta.StandardModelAttributeReference;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQueryService;

@Component
public class UmlsEntityQueryService 
			extends AbstractUmlsBaseService 
			implements EntityDescriptionQueryService {

	@Resource
	private EntityQueryBuilderFactory entityQueryBuilderFactory;
	
	private EntityQueryStateUpdater stateUpdater = new EntityQueryStateUpdater();
	
	@Override
	public DirectoryResult<EntityDirectoryEntry> getResourceSummaries(
			EntityDescriptionQuery query, SortCriteria sortCriteria, Page page) {
		EntityQueryBuilder queryBuilder = 
			this.entityQueryBuilderFactory.createEntityQueryBuilder(
				this.getSupportedMatchAlgorithms(),
				this.getSupportedSearchReferences());
		
		return queryBuilder.
			addQuery(query).
			addMaxToReturn(page.getMaxToReturn()).
			addStart(page.getStart()).
			resolve();	
	}

	@Override
	public DirectoryResult<EntityDescription> getResourceList(
			EntityDescriptionQuery query, SortCriteria sortCriteria, Page page) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int count(EntityDescriptionQuery query) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<MatchAlgorithmReference> getSupportedMatchAlgorithms() {
		return new HashSet<MatchAlgorithmReference>(
			Arrays.asList(StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference()));
	}

	@Override
	public Set<StateAdjustingPropertyReference<QueryBuilder>> getSupportedSearchReferences() {
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
		return new HashSet<PropertyReference>();
	}

	@Override
	public Set<PredicateReference> getKnownProperties() {
		return new HashSet<PredicateReference>();
	}

	@Override
	public boolean isEntityInSet(EntityNameOrURI entity, 
			EntityDescriptionQuery restrictions, ResolvedReadContext readContext) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EntityReferenceList resolveAsEntityReferenceList(
			EntityDescriptionQuery restrictions, ResolvedReadContext readContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EntityNameOrURIList intersectEntityList(
			Set<EntityNameOrURI> entities, 
			EntityDescriptionQuery restrictions, ResolvedReadContext readContext) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<? extends VersionTagReference> getSupportedTags() {
		return new HashSet<VersionTagReference>();
	}

}
