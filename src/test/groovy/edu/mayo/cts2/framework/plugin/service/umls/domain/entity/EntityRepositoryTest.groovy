package edu.mayo.cts2.framework.plugin.service.umls.domain.entity

import org.junit.Test

import static org.junit.Assert.*

class EntityRepositoryTest {

	@Test
	def void testFloatToDouble(){
		def repo = new EntityRepository()
		assertEquals 0.001d, repo.floatToDouble(0.001), 0.0001
	}
}
