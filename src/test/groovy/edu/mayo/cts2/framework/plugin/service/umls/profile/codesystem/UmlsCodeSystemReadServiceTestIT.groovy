package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem

import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractReadTestITBase
import edu.mayo.cts2.framework.service.profile.ReadService

import javax.annotation.Resource

class UmlsCodeSystemReadServiceTestIT extends AbstractReadTestITBase {

	@Resource
	UmlsCodeSystemReadService service

    @Override
    def getGoodIdentifier() {
        new ModelUtils().nameOrUriFromName("LNC")
    }

    @Override
    def getBadIdentifier() {
        new ModelUtils().nameOrUriFromName("__INVALID__")
    }

    @Override
    def ReadService getService(){
        service
    }
}
