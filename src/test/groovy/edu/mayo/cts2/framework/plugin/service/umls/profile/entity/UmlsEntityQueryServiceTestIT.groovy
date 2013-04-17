package edu.mayo.cts2.framework.plugin.service.umls.profile.entity

import static org.junit.Assert.*

import javax.annotation.Resource

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.Cts2Marshaller
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.command.ResolvedFilter
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.core.PropertyReference;
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;
import edu.mayo.cts2.framework.service.meta.StandardModelAttributeReference;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery
import edu.mayo.cts2.framework.service.profile.mapentry.MapEntryQuery

class UmlsEntityQueryServiceTestIT extends AbstractTestITBase {

	@Resource
	UmlsEntityQueryService service

	@Resource
	Cts2Marshaller marshaller
	
	@Test
	void TestGetEntries() {
		def summaries = service.getResourceSummaries(
			null as MapEntryQuery,
			null,
			new Page());
		
		assertNotNull summaries
		assertTrue summaries.entries.size() > 0
	}

	@Test
	void TestGetEntriesWithRestriction() {
		
		def filter = new ResolvedFilter(
				matchAlgorithmReference: StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference(),
				matchValue:"pain",
				propertyReference: StandardModelAttributeReference.RESOURCE_SYNOPSIS.getPropertyReference()
				)
		
		def q = [
			getFilterComponent : { [filter] as Set },
			getReadContext : { },
			getQuery : { },
			getRestrictions : { }
		] as EntityDescriptionQuery

		def summaries = service.getResourceSummaries(
			q,
			null,
			new Page());
		
		assertNotNull summaries
		assertTrue summaries.entries.size() > 0
	}
}
