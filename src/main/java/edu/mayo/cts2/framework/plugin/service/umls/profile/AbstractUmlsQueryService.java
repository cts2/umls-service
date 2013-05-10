package edu.mayo.cts2.framework.plugin.service.umls.profile;

import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntry;
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntrySummary;
import edu.mayo.cts2.framework.model.core.PredicateReference;
import edu.mayo.cts2.framework.service.profile.BaseQueryService;
import edu.mayo.cts2.framework.service.profile.QueryService;
import edu.mayo.cts2.framework.service.profile.codesystemversion.CodeSystemVersionQuery;

import java.util.HashSet;
import java.util.Set;


public abstract class AbstractUmlsQueryService
        extends AbstractUmlsBaseService
        implements BaseQueryService {

    @Override
    public Set<PredicateReference> getKnownProperties() {
        return new HashSet<PredicateReference>();
    }
}
