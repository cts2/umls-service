package edu.mayo.cts2.framework.plugin.service.umls.provider

import javax.annotation.Resource;

import static org.junit.Assert.*
import org.junit.Test;

import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase;

class UmlsServiceProviderTestIT extends AbstractTestITBase {
	
	@Resource
	UmlsServiceProvider provider
	
	@Test
	void testSetUp(){
		assertNotNull provider
	}

}
