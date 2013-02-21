package edu.mayo.cts2.framework.plugin.service.umls.domain.codesystem

import javax.annotation.Resource;

import org.junit.Test;

import static org.junit.Assert.*

import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase;

class CodeSystemRepositoryTestIT extends AbstractTestITBase {
	
	@Resource
	CodeSystemRepository repo
	
	@Test
	void testSetUp(){
		assertNotNull repo
	}
	
	@Test
	void testGetCodeSystemBySab(){
		assertNotNull repo.getCodeSystemBySab("AIR")
	}

}
