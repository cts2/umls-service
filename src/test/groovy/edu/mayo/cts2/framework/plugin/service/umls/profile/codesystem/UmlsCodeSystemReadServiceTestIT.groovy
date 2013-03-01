package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem

import static org.junit.Assert.*

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.Cts2Marshaller
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase

class UmlsCodeSystemReadServiceTestIT extends AbstractTestITBase {

	@Resource
	UmlsCodeSystemReadService service

	@Resource
	Cts2Marshaller marshaller
	
	def doRead() {
		def cs = service.read(ModelUtils.nameOrUriFromName("LNC"), null)
	
		cs
	}
	
	@Test
	void testSetUp(){
		assertNotNull service
	}

	@Test
	void TestValidXml() {
		def cs = doRead()

		assertNotNull cs

		marshaller.marshal(cs, new StreamResult(new StringWriter()))
	}
}
