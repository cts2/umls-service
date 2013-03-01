package edu.mayo.cts2.framework.plugin.service.umls.profile.mapentry

import static org.junit.Assert.*

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import org.junit.Test

import edu.mayo.cts2.framework.core.xml.Cts2Marshaller
import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractTestITBase
import edu.mayo.cts2.framework.service.profile.mapentry.name.MapEntryReadId

class UmlsMapEntryReadServiceTestIT extends AbstractTestITBase {

	@Resource
	UmlsMapEntryReadService service

	@Resource
	Cts2Marshaller marshaller
	
	def doRead() {
		
		def readId = new MapEntryReadId("C0151907", "MTH", ModelUtils.nameOrUriFromName("MTH-SNOMEDCT-2012AB"))
		def cs = service.read(readId, null)
	
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
