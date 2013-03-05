package edu.mayo.cts2.framework.plugin.service.umls.mapper

import javax.annotation.Resource;

import org.junit.Test;
import static org.junit.Assert.*
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase;

class MetadataMapperTestIT extends AbstractTestITBase {
	
	@Resource
	MetadataMapper mapper
	
	@Test
	void testSetUp(){
		assertNotNull mapper
	}
	
	@Test
	void testGetName(){
		assertEquals "2012AB", mapper.getReleaseInfoDTO().name
	}
	
	@Test
	void testGetReleaseDate(){
		def date = new Date().parse('yyyy/MM/dd/hh/mm', '2012/01/31/00/10')
		assertEquals date.time, mapper.getReleaseInfoDTO().releaseDate.time
	}

}
