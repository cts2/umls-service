package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystemversion

import edu.mayo.cts2.framework.model.util.ModelUtils
import edu.mayo.cts2.framework.plugin.service.umls.test.AbstractReadTestITBase
import edu.mayo.cts2.framework.service.profile.ReadService

import javax.annotation.Resource

class UmlsCodeSystemVersionReadServiceTestIT extends AbstractReadTestITBase {

	@Resource
	UmlsCodeSystemVersionReadService service

    @Override
    def getGoodIdentifier() {
        new ModelUtils().nameOrUriFromName("AIR93")
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
