package edu.mayo.cts2.framework.plugin.service.umls.profile.codesystem;

import edu.mayo.cts2.framework.filter.match.StateAdjustingPropertyReference.StateUpdater;
import edu.mayo.cts2.framework.model.core.MatchAlgorithmReference;
import edu.mayo.cts2.framework.plugin.service.umls.mapper.CodeSystemMapper;
import edu.mayo.cts2.framework.service.meta.StandardMatchAlgorithmReference;

public abstract class AbstractCodeSystemQueryStateUpdater implements StateUpdater<CodeSystemMapper.SearchObject>{

	@Override
	public CodeSystemMapper.SearchObject updateState(CodeSystemMapper.SearchObject currentState,
			MatchAlgorithmReference matchAlgorithm, 
			String queryString) {
		if (matchAlgorithm.equals(StandardMatchAlgorithmReference.CONTAINS.getMatchAlgorithmReference())) {
            return this.doSet(currentState, queryString);
		}
        if (matchAlgorithm.equals(StandardMatchAlgorithmReference.EXACT_MATCH.getMatchAlgorithmReference())) {
            return this.doSet(currentState, queryString);
		}
		if (matchAlgorithm.equals(StandardMatchAlgorithmReference.STARTS_WITH.getMatchAlgorithmReference())) {
            return this.doSet(currentState, queryString);
		}

        throw new IllegalStateException();
    }

    protected abstract CodeSystemMapper.SearchObject doSet(CodeSystemMapper.SearchObject currentState, String query);

    protected static class AbbreviationStateUpdater extends AbstractCodeSystemQueryStateUpdater {

        @Override
        protected CodeSystemMapper.SearchObject doSet(CodeSystemMapper.SearchObject currentState, String query) {
            currentState.setAbbreviation(query);
            return currentState;
        }
    }

}
