package edu.mayo.cts2.framework.plugin.service.umls.mapper

import javax.annotation.Resource;

import org.junit.Test;
import static org.junit.Assert.*
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase;

class CodeSystemMapperTestIT extends AbstractTestITBase {
	
	@Resource
	CodeSystemMapper mapper
	
	@Test
	void testSetUp(){
		assertNotNull mapper
	}
	
	@Test
	void testGet(){
		assertNotNull mapper.getRootSourceDTO("AIR")
	}

}
