package edu.mayo.cts2.framework.plugin.service.umls.profile.entity

import static org.junit.Assert.*

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.Cts2Marshaller
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase
import edu.mayo.cts2.framework.service.profile.entitydescription.name.EntityDescriptionReadId

class UmlsEntityReadServiceTestIT extends AbstractTestITBase {

	@Resource
	UmlsEntityReadService service

	@Resource
	Cts2Marshaller marshaller
	
	def doRead() {
		def e = service.read(
			new EntityDescriptionReadId
				(ModelUtils.createScopedEntityName("C0042285", "MTH"),
				ModelUtils.nameOrUriFromName("MTH-2012AB")), null)

		e
	}

	def doReadID() {
		def e = service.read(
			new EntityDescriptionReadId
				(ModelUtils.createScopedEntityName("IC0030193", "SNOMEDCT"),
				ModelUtils.nameOrUriFromName("MTH-2012AB")), null)

		e
	}

	@Test
	void testSetUp(){
		assertNotNull service
	}

	@Test
	void TestValidXmlCui() {
		def e = doRead()

		assertNotNull e

		marshaller.marshal(e, new StreamResult(new StringWriter()))
	}
	
	@Test
	void TestValidXmlId() {
		def e = doReadID()

		assertNotNull e

		marshaller.marshal(e, new StreamResult(new StringWriter()))
	}
}
