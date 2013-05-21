package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystemversion;

import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference;
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntry;
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntrySummary;
import edu.mayo.cts2.framework.model.command.Page;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.core.PredicateReference;
import edu.mayo.cts2.framework.model.core.PropertyReference;
import edu.mayo.cts2.framework.model.core.SortCriteria;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemVersionMapper;
import edu.mayo.cts2.framework.plugin.service.umls.profile.AbstractUmlsBaseService;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;
import edu.mayo.cts2.framework.service.meta.StandardModelAttributeReference;
import edu.mayo.cts2.framework.plugin.service.umls.profile.codesystemversion.CodeSystemVersionQueryBuilderFactory.CodeSystemVersionQueryBuilder;
import edu.mayo.cts2.framework.service.profile.codesystemversion.CodeSystemVersionQuery;
import edu.mayo.cts2.framework.service.profile.codesystemversion.CodeSystemVersionQueryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class UmlsCodeSystemVersionQueryService extends AbstractUmlsBaseService
        implements CodeSystemVersionQueryService {

    @Resource
    private CodeSystemVersionQueryBuilderFactory codeSystemVersionQueryBuilderFactory;

    @Override
    public Set<StateAdjustingPropertyReference<CodeSystemVersionMapper.SearchObject>> getSupportedSearchReferences() {
        Set<StateAdjustingPropertyReference<CodeSystemVersionMapper.SearchObject>> returnSet =
                new HashSet<StateAdjustingPropertyReference<CodeSystemVersionMapper.SearchObject>>();

        PropertyReference resourceNameRef = StandardModelAttributeReference.RESOURCE_NAME
                .getPropertyReference();

        //PropertyReference resourceSynopsisRef = StandardModelAttributeReference.RESOURCE_SYNOPSIS
        //        .getPropertyReference();

        returnSet.add(StateAdjustingPropertyReference.toPropertyReference(resourceNameRef,
                new AbstractCodeSystemVersionQueryStateUpdater.AbbreviationStateUpdater()));

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
    public DirectoryResult<CodeSystemVersionCatalogEntrySummary> getResourceSummaries(
            CodeSystemVersionQuery query, SortCriteria sortCriteria, Page page) {
        CodeSystemVersionQueryBuilder queryBuilder = this.codeSystemVersionQueryBuilderFactory.createCodeSystemVersionQueryBuilder(
                this.getSupportedMatchAlgorithms(),
                this.getSupportedSearchReferences());

        return queryBuilder.addQuery(query)
                .addMaxToReturn(page.getMaxToReturn())
                .addStart(page.getStart()).resolve();
    }

    @Transactional
    @Override
    public DirectoryResult<CodeSystemVersionCatalogEntry> getResourceList(
            CodeSystemVersionQuery query, SortCriteria sortCriteria, Page page) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    @Override
    public int count(CodeSystemVersionQuery query) {
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
