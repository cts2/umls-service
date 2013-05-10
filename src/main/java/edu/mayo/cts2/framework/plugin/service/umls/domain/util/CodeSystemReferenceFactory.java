package edu.mayo.cts2.framework.plugin.service.umls.domain.util;

import edu.mayo.cts2.framework.core.url.UrlConstructor;
import edu.mayo.cts2.framework.model.core.CodeSystemReference;
import edu.mayo.cts2.framework.plugin.service.umls.domain.uri.CodeSystemUriHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CodeSystemReferenceFactory {

    @Resource
    private CodeSystemUriHandler codeSystemUriHandler;

    @Resource
    private UrlConstructor urlConstructor;

    public CodeSystemReference createReference(String sab){
        CodeSystemReference ref = new CodeSystemReference();
        ref.setContent(sab);
        ref.setUri(this.codeSystemUriHandler.getUri(sab));
        ref.setHref(this.urlConstructor.createCodeSystemUrl(sab));

        return ref;
    }

}
