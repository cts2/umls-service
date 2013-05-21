package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem

import edu.mayo.cts2.framework.core.xml.Cts2Marshaller
import edu.mayo.cts2.framework.model.command.Page
import edu.mayo.cts2.framework.model.command.ResolvedFilter
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference
import edu.mayo.cts2.framework.service.meta.StandardModelAttributeReference
import edu.mayo.cts2.framework.service.profile.ResourceQuery
import org.junit.Test

import javax.annotation.Resource

import static org.junit.Assert.*

class UmlsCodeSystemQueryServiceTestIT extends AbstractTestITBase {

	@Resource
	UmlsCodeSystemQueryService service

	@Resource
	Cts2Marshaller marshaller

	@Test
	void testSetUp(){
		assertNotNull service
	}

	@Test
	void testQueryNotNull(){
        doInDirectory(
        {   dir ->
            assertNotNull dir
        })
	}

    @Test
    void testQuerySize(){
        doInDirectory(
                {   dir ->
                    assertTrue dir.entries.size > 0
                })
    }

    @Test
    void testQueryWithFilter(){
        def filter = new ResolvedFilter()
        filter.setMatchAlgorithmReference(StandardMatchAlgorithmReference.CONTAINS.matchAlgorithmReference)
        filter.setPropertyReference(StandardModelAttributeReference.RESOURCE_NAME.propertyReference)
        filter.setMatchValue("AIR")

        doInDirectory(
                {   dir ->
                    assertEquals 1, dir.entries.size
                },[filter] as Set)
    }

    @Test
    void testQueryWithFilterCaseInsensitive(){
        def filter = new ResolvedFilter()
        filter.setMatchAlgorithmReference(StandardMatchAlgorithmReference.CONTAINS.matchAlgorithmReference)
        filter.setPropertyReference(StandardModelAttributeReference.RESOURCE_NAME.propertyReference)
        filter.setMatchValue("aIr")

        doInDirectory(
                {   dir ->
                    assertEquals 1, dir.entries.size
                },[filter] as Set)
    }

    @Test
    void testQueryCorrectSize(){
        doInDirectory(
                {   dir ->
                    assertEquals 50, dir.entries.size
                })
    }

    @Test
    void testQueryAtEnd(){
        doInDirectory(
                {   dir ->
                    assertFalse dir.atEnd
                })
    }

    @Test
    void testQueryHasHrefs(){
        doInDirectory(
                {   dir ->
                    dir.entries.each {
                        assertNotNull it.href
                    }
                })
    }

    def doInDirectory = { closure, filters=null ->
        def dir = service.getResourceSummaries(
                [
                        getQuery:{null},
                        getReadContext:{null},
                        getFilterComponent:{filters}
                ] as ResourceQuery,
                null,
                new Page()
        )
        closure(dir)
    }


}
