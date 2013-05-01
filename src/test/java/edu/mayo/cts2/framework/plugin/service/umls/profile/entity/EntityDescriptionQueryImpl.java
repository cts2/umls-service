package edu.mayo.cts2.framework.plugin.service.umls.profile.entity;

import java.util.Set;

import edu.mayo.cts2.framework.model.command.ResolvedFilter;
import edu.mayo.cts2.framework.model.command.ResolvedReadContext;
import edu.mayo.cts2.framework.model.service.core.Query;
import edu.mayo.cts2.framework.service.command.restriction.EntityDescriptionQueryServiceRestrictions;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntitiesFromAssociationsQuery;
import edu.mayo.cts2.framework.service.profile.entitydescription.EntityDescriptionQuery;

public class EntityDescriptionQueryImpl implements EntityDescriptionQuery 
{
	private Set<ResolvedFilter> filter_ = null;
	private ResolvedReadContext readCtx_ = null;
	private EntitiesFromAssociationsQuery entityFromAssocQ_ = null;
	private EntityDescriptionQueryServiceRestrictions restrictions_ = null;
	
	public EntityDescriptionQueryImpl(Set<ResolvedFilter> filter, 
			ResolvedReadContext readCtx, 
			EntitiesFromAssociationsQuery entityFromAssocQ, 
			EntityDescriptionQueryServiceRestrictions restrictions)
	{
		this.filter_ = filter;
		this.readCtx_ = readCtx;
		this.entityFromAssocQ_ = entityFromAssocQ;
		this.restrictions_ = restrictions;
		
	}
	
	@Override
	public Query getQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ResolvedFilter> getFilterComponent() {
		// TODO Auto-generated method stub
		return this.filter_;
	}

	@Override
	public ResolvedReadContext getReadContext() {
		// TODO Auto-generated method stub
		return this.readCtx_;
	}

	@Override
	public EntitiesFromAssociationsQuery getEntitiesFromAssociationsQuery() {
		// TODO Auto-generated method stub
		return this.entityFromAssocQ_;
	}

	@Override
	public EntityDescriptionQueryServiceRestrictions getRestrictions() {
		// TODO Auto-generated method stub
		return this.restrictions_;
	}

}
