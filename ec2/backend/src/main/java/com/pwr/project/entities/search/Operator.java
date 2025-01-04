package com.pwr.project.entities.search;

import jakarta.persistence.criteria.*;

import java.util.Set;

public enum Operator {
    LIKE {
        @Override
        public Predicate buildCriteria (Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder, String searchKey, String searchValue){
            CriteriaAttrributeBuilder criteriaAttrributeBuilder = CriteriaAttrributeBuilder.valueOf(searchKey);
            Expression<String> expression = criteriaAttrributeBuilder.build(root, criteriaQuery, criteriaBuilder);
            Predicate predicate = criteriaBuilder.like(expression, "%" + searchValue + "%");
            return criteriaBuilder.and(predicate);
        }
    },
    EQUALS {
        @Override
        public Predicate buildCriteria (Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder, String searchKey, String searchValue){
            CriteriaAttrributeBuilder criteriaAttrributeBuilder = CriteriaAttrributeBuilder.valueOf(searchKey);
            Expression<String> expression = criteriaAttrributeBuilder.build(root, criteriaQuery, criteriaBuilder);
            Predicate predicate = criteriaBuilder.equal(expression, searchValue);
            return criteriaBuilder.and(predicate);
        }
    },
    
    CONTAINS {
        @Override
        public Predicate buildCriteria (Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder, String searchKey, String searchValue){
            CriteriaAttrributeBuilder criteriaAttrributeBuilder = CriteriaAttrributeBuilder.valueOf(searchKey);
            Expression<Set<String>> expression = criteriaAttrributeBuilder.build(root,criteriaQuery,criteriaBuilder);
            Predicate predicate = criteriaBuilder.isMember(searchValue, expression);
            return predicate;
        }
    };

    public abstract Predicate buildCriteria(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder, String searchKey, String searchValue);
}
