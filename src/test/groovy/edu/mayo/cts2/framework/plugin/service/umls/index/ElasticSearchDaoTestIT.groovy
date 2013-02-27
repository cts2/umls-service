package edu.mayo.cts2.framework.plugin.service.umls.index;

import javax.annotation.Resource

import org.junit.Test

import static org.junit.Assert.*

import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemMapper
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase

public class ElasticSearchDaoTestIT extends AbstractTestITBase {
	
	@Resource
	ElasticSearchDao dao
	
	@Test
	void testSetUp(){
		assertNotNull dao
	}
	
}