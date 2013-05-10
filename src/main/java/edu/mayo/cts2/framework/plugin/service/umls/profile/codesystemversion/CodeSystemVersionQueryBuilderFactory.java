package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystemversion;

import edu.mayo.cts2.framework.filter.directory.AbstractStateBuildingDirectoryBuilder;
import edu.mayo.cts2.framework.filter.directory.AbstractStateBuildingDirectoryBuilder.Callback;
import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference;
import edu.mayo.cts2.framework.model.codesystemversion.CodeSystemVersionCatalogEntrySummary;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.model.directory.DirectoryResult;
import edu.mayo.cts2.framework.plugin.service.umls.domain.codesystemversion.CodeSystemVersionRepository;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemVersionMapper;
import edu.mayo.cts2.framework.service.profile.ResourceQuery;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class CodeSystemVersionQueryBuilderFactory {

	@Resource
	private CodeSystemVersionRepository codeSystemVersionRepository;

	protected CodeSystemVersionQueryBuilder createCodeSystemVersionQueryBuilder(
			Set<MatchAlgorithmReference> matchAlgorithmReferences,
			Set<StateAdjustingPropertyReference<CodeSystemVersionMapper.SearchObject>> stateAdjustingPropertyReferences) {
        CodeSystemVersionMapper.SearchObject initialState = null;
		return new CodeSystemVersionQueryBuilder(
				initialState,
				new CodeSystemVersionCallback(),
				matchAlgorithmReferences,
				stateAdjustingPropertyReferences);
	}

	protected static class CodeSystemVersionQueryBuilder
			extends
			AbstractStateBuildingDirectoryBuilder<CodeSystemVersionMapper.SearchObject, CodeSystemVersionCatalogEntrySummary> {

		protected CodeSystemVersionQueryBuilder(
                CodeSystemVersionMapper.SearchObject initialState,
				Callback<CodeSystemVersionMapper.SearchObject, CodeSystemVersionCatalogEntrySummary> callback,
				Set<MatchAlgorithmReference> matchAlgorithmReferences,
				Set<StateAdjustingPropertyReference<CodeSystemVersionMapper.SearchObject>> stateAdjustingPropertyReferences) {
			super(initialState, callback, matchAlgorithmReferences,
					stateAdjustingPropertyReferences);
		}

		protected CodeSystemVersionQueryBuilder addQuery(ResourceQuery query) {
			if (query != null) {
				if (query.getFilterComponent() != null) {
					this.restrict(query.getFilterComponent());
				}
			}

			return this;
		}

	}

	private class CodeSystemVersionCallback implements
			Callback<CodeSystemVersionMapper.SearchObject, CodeSystemVersionCatalogEntrySummary> {

		@Override
		public DirectoryResult<CodeSystemVersionCatalogEntrySummary> execute(
				CodeSystemVersionMapper.SearchObject state,
				int start, 
				int maxResults) {
			return codeSystemVersionRepository.searchCodeSystemVersionDirectorySummaries(state, start, start + maxResults);
		}

		@Override
		public int executeCount(CodeSystemVersionMapper.SearchObject state) {
			throw new UnsupportedOperationException();
		}
	}

}
