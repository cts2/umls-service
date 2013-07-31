package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem;

import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntryListEntry;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntrySummary;
import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.core.PredicateReference;
import edu.mayo.cts2.framework.model.core.PropertyReference;
import edu.mayo.cts2.framework.model.core.SortCriteria;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemMapper;
import edu.mayo.cts2.framework.plugin.service.umls.profile.AbstractUmlsBaseService;
import edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem.CodeSystemQueryBuilderFactory.CodeSystemQueryBuilder;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;
import edu.mayo.cts2.framework.service.meta.StandardModelAttributeReference;
import edu.mayo.cts2.framework.service.profile.ResourceQuery;
import edu.mayo.cts2.framework.service.profile.codesystem.CodeSystemQueryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class UmlsCodeSystemQueryService extends AbstractUmlsBaseService
        implements CodeSystemQueryService {

    @Resource
    private CodeSystemQueryBuilderFactory codeSystemQueryBuilderFactory;

    @Override
    public Set<StateAdjustingPropertyReference<CodeSystemMapper.SearchObject>> getSupportedSearchReferences() {
        Set<StateAdjustingPropertyReference<CodeSystemMapper.SearchObject>> returnSet =
                new HashSet<StateAdjustingPropertyReference<CodeSystemMapper.SearchObject>>();

        PropertyReference resourceNameRef = StandardModelAttributeReference.RESOURCE_NAME
                .getPropertyReference();

        //PropertyReference resourceSynopsisRef = StandardModelAttributeReference.RESOURCE_SYNOPSIS
        //        .getPropertyReference();

        returnSet.add(StateAdjustingPropertyReference.toPropertyReference(resourceNameRef,
                new AbstractCodeSystemQueryStateUpdater.AbbreviationStateUpdater()));

        return returnSet;
    }

    @Override
    public Set<PropertyReference> getSupportedSortReferences() {
        return new HashSet<PropertyReference>();
    }

    @Override
    public Set<PredicateReference> getKnownProperties() {
        return new HashSet<PredicateReference>();
    }

    @Transactional
    @Override
    public DirectoryResult<CodeSystemCatalogEntrySummary> getResourceSummaries(
            ResourceQuery query, SortCriteria sortCriteria, Page page) {
        CodeSystemQueryBuilder queryBuilder = this.codeSystemQueryBuilderFactory.createCodeSystemQueryBuilder(
                this.getSupportedMatchAlgorithms(),
                this.getSupportedSearchReferences());

        return queryBuilder.addQuery(query)
                .addMaxToReturn(page.getMaxToReturn())
                .addStart(page.getStart()).resolve();
    }

    @Transactional
    @Override
    public DirectoryResult<CodeSystemCatalogEntryListEntry> getResourceList(
            ResourceQuery query, SortCriteria sortCriteria, Page page) {
       throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public int count(ResourceQuery query) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<MatchAlgorithmReference> getSupportedMatchAlgorithms() {

        return new HashSet<MatchAlgorithmReference>(Arrays.asList(
                StandardMatchAlgorithmReference.CONTAINS
                        .getMatchAlgorithmReference(),
                StandardMatchAlgorithmReference.EXACT_MATCH
                        .getMatchAlgorithmReference(),
                StandardMatchAlgorithmReference.STARTS_WITH
                        .getMatchAlgorithmReference()));

    }

}
