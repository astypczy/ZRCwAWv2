package com.pwr.project.entities.search;

import com.pwr.project.entities.Notice_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

public enum CriteriaAttrributeBuilder {
    TITLE {
        @Override
        public Path build(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return root.get(Notice_.TITLE);
        }
    },
    DESCRIPTION {
        @Override
        public Path build(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return root.get(Notice_.DESCRIPTION);
        }
    },
    TAGS {
        @Override
        public Path build(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return root.get(Notice_.TAGS);
        }
    };

    public abstract Path build(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder);
}
