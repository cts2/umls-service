package edu.mayo.cts2.framework.plugin.service.umls.provider

import javax.annotation.Resource

import org.junit.Test
import static org.junit.Assert.*

import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase

class UmlsServiceProviderTestIT extends AbstractTestITBase {
	
	@Resource
	UmlsServiceProvider provider
	
	@Test
	void testSetUp(){
		assertNotNull provider
	}

}
