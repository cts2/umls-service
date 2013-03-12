package edu.mayo.cts2.framework.plugin.service.umls.domain.entity

import static org.junit.Assert.*

import javax.annotation.Resource

import org.elasticsearch.index.query.QueryBuilders
import org.junit.Test

import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase

class EntityRepositoryTestIT extends AbstractTestITBase {

	@Resource
	EntityRepository repo
	
	@Test
	def void testQueryScore(){
		def qb = QueryBuilders.fuzzyQuery("entity.descriptions.value", "blud")
		def result = repo.getEntityDirectoryEntriesByKeyword(qb, 0, 100);
		
		result.entries.each { 
			assertTrue (it.matchStrength != 1)
		}
	}
	
	@Test
	def void testEntityEntryList(){
		def qb = QueryBuilders.fuzzyQuery("entity.descriptions.value", "blud")
		def result = repo.getEntityListEntriesByKeyword(qb, 0, 100);
		
		result.entries.each {
			assertTrue (it.matchStrength != 1)
		}
	}
}
