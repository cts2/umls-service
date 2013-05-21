package edu.mayo.cts2.framework.plugin.service.umls.mapper

import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase
import org.junit.Test

import javax.annotation.Resource

import static org.junit.Assert.assertNotNull;

class CodeSystemVersionMapperTestIT extends AbstractTestITBase {
	
	@Resource
	CodeSystemVersionMapper mapper
	
	@Test
	void testSetUp(){
		assertNotNull mapper
	}
	
	@Test
	void testGet(){
		assertNotNull mapper.getSourceDTO("AIR93")
	}

    @Test
    void testGetBySabAndVersion(){
        assertNotNull mapper.getSourceDTOBySabAndVersion("AIR", "1993")
    }

}
