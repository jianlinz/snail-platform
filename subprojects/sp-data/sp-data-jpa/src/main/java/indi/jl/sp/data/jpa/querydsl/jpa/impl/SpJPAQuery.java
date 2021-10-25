package indi.jl.sp.data.jpa.querydsl.jpa.impl;

import com.querydsl.core.QueryMetadata;
import com.querydsl.jpa.JPQLSerializer;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAProvider;
import com.querydsl.jpa.impl.JPAQuery;
import indi.jl.sp.data.jpa.querydsl.core.SpDefaultQueryMetadata;
import indi.jl.sp.data.jpa.querydsl.jpa.SpJPQLSerializer;

import javax.persistence.EntityManager;

public class SpJPAQuery<T> extends JPAQuery<T> {

    public SpJPAQuery() {
        super((EntityManager)null, JPQLTemplates.DEFAULT, new SpDefaultQueryMetadata());
    }

    public SpJPAQuery(EntityManager em) {
        super(em, JPAProvider.getTemplates(em), new SpDefaultQueryMetadata());
    }

    public SpJPAQuery(EntityManager em, QueryMetadata metadata) {
        super(em, JPAProvider.getTemplates(em), metadata);
    }

    public SpJPAQuery(EntityManager em, JPQLTemplates templates) {
        super(em, templates, new SpDefaultQueryMetadata());
    }

    public SpJPAQuery(EntityManager em, JPQLTemplates templates, QueryMetadata metadata) {
        super(em, templates, metadata);
    }

    @Override
    protected JPQLSerializer createSerializer() {
        return new JPQLSerializer(this.getTemplates(), this.entityManager);
    }

}
