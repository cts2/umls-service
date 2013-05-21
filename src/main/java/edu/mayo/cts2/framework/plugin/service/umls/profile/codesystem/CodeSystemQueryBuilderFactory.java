package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem;

import java.util.Set;

import javax.annotation.Resource;


import edu.mayo.cts2.framework.filter.directory.AbstractStateBuildingDirectoryBuilder;
import edu.mayo.cts2.framework.filter.directory.AbstractStateBuildingDirectoryBuilder.Callback;
import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntrySummary;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.plugin.service.umls.domain.codesystem.CodeSystemRepository;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemMapper;
import edu.mayo.cts2.framework.service.profile.ResourceQuery;
import org.springframework.stereotype.Component;

@Component
public class CodeSystemQueryBuilderFactory {

	@Resource
	private CodeSystemRepository codeSystemRepository;

	protected CodeSystemQueryBuilder createCodeSystemQueryBuilder(
			Set<MatchAlgorithmReference> matchAlgorithmReferences,
			Set<StateAdjustingPropertyReference<CodeSystemMapper.SearchObject>> stateAdjustingPropertyReferences) {
        CodeSystemMapper.SearchObject initialState = null;
		return new CodeSystemQueryBuilder(
				initialState,
				new CodeSystemCallback(), 
				matchAlgorithmReferences,
				stateAdjustingPropertyReferences);
	}

	protected static class CodeSystemQueryBuilder
			extends
			AbstractStateBuildingDirectoryBuilder<CodeSystemMapper.SearchObject, CodeSystemCatalogEntrySummary> {

		protected CodeSystemQueryBuilder(
                CodeSystemMapper.SearchObject initialState,
				Callback<CodeSystemMapper.SearchObject, CodeSystemCatalogEntrySummary> callback,
				Set<MatchAlgorithmReference> matchAlgorithmReferences,
				Set<StateAdjustingPropertyReference<CodeSystemMapper.SearchObject>> stateAdjustingPropertyReferences) {
			super(initialState, callback, matchAlgorithmReferences,
					stateAdjustingPropertyReferences);
		}

		protected CodeSystemQueryBuilder addQuery(ResourceQuery query) {
			if (query != null) {
				if (query.getFilterComponent() != null) {
					this.restrict(query.getFilterComponent());
				}
			}

			return this;
		}

	}

	private class CodeSystemCallback implements
			Callback<CodeSystemMapper.SearchObject, CodeSystemCatalogEntrySummary> {

		@Override
		public DirectoryResult<CodeSystemCatalogEntrySummary> execute(
				CodeSystemMapper.SearchObject state,
				int start, 
				int maxResults) {
			return codeSystemRepository.searchCodeSystemDirectorySummaries(state, start, start + maxResults);
		}

		@Override
		public int executeCount(CodeSystemMapper.SearchObject state) {
			throw new UnsupportedOperationException();
		}
	}

}
