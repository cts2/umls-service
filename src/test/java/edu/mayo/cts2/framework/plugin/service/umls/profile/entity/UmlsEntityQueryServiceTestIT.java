package edu.mayo.cts2.framework.plugin.service.umls.profile.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;

import edu.mayo.cts2.framework.core.xml.Cts2Marshaller;
import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.command.ResolvedFilter;
import edu.mayo.cts2.framework.model.core.EntityReferenceList;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.model.entity.EntityDescription;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;
import edu.mayo.cts2.framework.service.meta.StandardModelAttributeReference;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery;

public class UmlsEntityQueryServiceTestIT extends AbstractTestITBase 
{
	@Resource
	UmlsEntityQueryService service;

	@Resource
	Cts2Marshaller marshaller;
	
	@Resource
	EntityQueryBuilderFactory qFactory;
	
	@Test
	public void TestGetEntries() 
	{
		DirectoryResult<EntityDirectoryEntry> summaries = service.getResourceSummaries(
																	null,
																	null,
																	new Page());
		
		assertNotNull(summaries);
		assertTrue(summaries.getEntries().size() > 0);
	}

	@Test
	public void TestGetEntriesWithRestriction() 
	{
		ResolvedFilter filter = new ResolvedFilter();
		filter.setMatchAlgorithmReference(StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference());
		filter.setMatchValue("pain");
		filter.setPropertyReference(StandardModelAttributeReference.RESOURCE_SYNOPSIS.getPropertyReference());
		
		Set<ResolvedFilter> filters = new HashSet<ResolvedFilter>();
		filters.add(filter);
		
		EntityDescriptionQuery q = new EntityDescriptionQueryImpl(filters, null, null, null);
		
		DirectoryResult<EntityDirectoryEntry> summaries = service.getResourceSummaries(q, null, new Page());
		
		assertNotNull(summaries);
		assertTrue(summaries.getEntries().size() > 0);
	}
	
	
	@Test
	public void TestGetEntityCount() 
	{
		ResolvedFilter filter = new ResolvedFilter();
		filter.setMatchAlgorithmReference(StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference());
		filter.setMatchValue("pain");
		filter.setPropertyReference(StandardModelAttributeReference.RESOURCE_SYNOPSIS.getPropertyReference());
		
		Set<ResolvedFilter> filters = new HashSet<ResolvedFilter>();
		filters.add(filter);
		
		EntityDescriptionQuery q = new EntityDescriptionQueryImpl(filters, null, null, null);


		int count = service.count(q);
		
		assertTrue(count > 0);
	}
	
	@Test
	public void TestGetEntityDescriptionWithRestriction() 
	{
		ResolvedFilter filter = new ResolvedFilter();
		filter.setMatchAlgorithmReference(StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference());
		filter.setMatchValue("pain");
		filter.setPropertyReference(StandardModelAttributeReference.RESOURCE_SYNOPSIS.getPropertyReference());
		
		Set<ResolvedFilter> filters = new HashSet<ResolvedFilter>();
		filters.add(filter);
		
		EntityDescriptionQuery q = new EntityDescriptionQueryImpl(filters, null, null, null);


		DirectoryResult<EntityDescription> descs = service.getResourceList(	q,
																			null,
																			new Page());
		
		assertNotNull(descs);
		assertTrue(descs.getEntries().size() > 0);
	}
	
	@Test
	public void TestResolveAsEntityReferenceList() 
	{
		ResolvedFilter filter = new ResolvedFilter();
		filter.setMatchAlgorithmReference(StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference());
		filter.setMatchValue("pain");
		filter.setPropertyReference(StandardModelAttributeReference.RESOURCE_SYNOPSIS.getPropertyReference());
		
		Set<ResolvedFilter> filters = new HashSet<ResolvedFilter>();
		filters.add(filter);
		
		EntityDescriptionQuery q = new EntityDescriptionQueryImpl(filters, null, null, null);


		EntityReferenceList refList = service.resolveAsEntityReferenceList(q, null);
		
		assertNotNull(refList);
		assertTrue(refList.getEntryCount() > 0);
	}
}
