package edu.mayo.cts2.framework.plugin.service.umls.test

import edu.mayo.cts2.framework.core.xml.Cts2Marshaller
import edu.mayo.cts2.framework.service.profile.ReadService
import org.junit.Test

import javax.annotation.Resource
import javax.xml.transform.stream.StreamResult

import static org.junit.Assert.*

/**
 */
abstract class AbstractReadTestITBase extends AbstractTestITBase {

    def abstract getGoodIdentifier()

    def abstract getBadIdentifier()

    def abstract ReadService getService()

    @Resource
    Cts2Marshaller marshaller

    @Test
    void TestRead(){
        assertNotNull this.getService().read(this.getGoodIdentifier(),null);
    }

    @Test
    void TestReadNotFound(){
        assertNull this.getService().read(this.getBadIdentifier(),null);
    }

    @Test
    void TestExists(){
        assertTrue this.getService().exists(this.getGoodIdentifier(),null);
    }

    @Test
    void TestExistsNotFound(){
        assertFalse this.getService().exists(this.getBadIdentifier(),null);
    }

    @Test
    void TestValidXml(){
        def resource = this.getService().read(this.getGoodIdentifier(),null);
        assertNotNull resource

        marshaller.marshal(resource, new StreamResult(new StringWriter()))
    }
}
