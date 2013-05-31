package edu.mayo.cts2.framework.plugin.service.umls.profile.entity
import edu.mayo.cts2.framework.core.xml.Cts2Marshaller
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.command.ResolvedFilter
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference
import edu.mayo.cts2.framework.service.meta.StandardModelAttributeReference
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery
import edu.mayo.cts2.framework.service.profile.mapentry.MapEntryQuery
import org.junit.Ignore

import javax.annotation.Resource

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

@Ignore
class UmlsEntityQueryServiceTestITGroovy extends AbstractTestITBase {

	//
	// THESE TESTS ARE IGNORED as there is a java file which includes these tests.
	// April 2013 - Deepak Sharma
	//
	@Resource
	UmlsEntityQueryService service

	@Resource
	Cts2Marshaller marshaller
	
	@Ignore
	void TestGetEntries() {
		def summaries = service.getResourceSummaries(
			null as MapEntryQuery,
			null,
			new Page());
		
		assertNotNull summaries
		assertTrue summaries.entries.size() > 0
	}

	@Ignore
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
	
	@Ignore
	void TestGetEntityCount() {
		
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

		def count = service.count(q);
		
		assertTrue count > 0
	}
	
	@Ignore
	void TestGetEntityDescriptionWithRestriction() {
		
		def filter = new ResolvedFilter(
				matchAlgorithmReference: StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference(),
				matchValue:"blood",
				propertyReference: StandardModelAttributeReference.RESOURCE_SYNOPSIS.getPropertyReference()
				)
		
		def q = [
			getFilterComponent : { [filter] as Set },
			getReadContext : { },
			getQuery : { },
			getRestrictions : { }
		] as EntityDescriptionQuery

		def descs = service.getResourceList(
			q,
			null,
			new Page());
		
		assertNotNull descs
		assertTrue descs.entries.size() > 0
	}
}
