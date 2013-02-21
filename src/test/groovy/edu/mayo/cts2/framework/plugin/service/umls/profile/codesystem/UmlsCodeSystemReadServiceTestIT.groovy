package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem

import javax.annotation.Resource

import org.junit.Test
import static org.junit.Assert.*

import edu.mayo.cts2.framework.model.service.core.NameOrURI;
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase

class UmlsCodeSystemReadServiceTestIT extends AbstractTestITBase {
	@Resource
	UmlsCodeSystemReadService readService
	
	@Test
	void testSetUp(){
		assertNotNull readService
	}
	
	@Test
	void testExists(){
		
		NameOrURI csId = new NameOrURI()
		csId.setName("AIR")
		assertTrue readService.exists(csId, null)
		csId.setName("somthingwhichshouldnotexist")
		assertFalse readService.exists(csId, null)
	}
	
	@Test
	void testRead(){
		
		NameOrURI csId = new NameOrURI()
		csId.setName("AIR")
		assertNotNull readService.read(csId, null)
	}
}
