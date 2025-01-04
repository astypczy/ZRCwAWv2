package com.pwr.project.services.search;

import com.pwr.project.entities.Notice;
import com.pwr.project.entities.search.SearchCriteria;
import com.pwr.project.entities.search.Operator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchSpecification implements Specification<Notice> {

    private final SearchCriteria criteria;

    public SearchSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public static Specification<Notice> createSpecification(List<SearchCriteria> searchCriterias) {
        if (searchCriterias == null || searchCriterias.isEmpty()) {
            return null;
        }

        List<Specification<Notice>> specs = searchCriterias.stream()
            .map(SearchSpecification::new)
            .collect(Collectors.toList());

        Specification<Notice> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }

    @Override
    public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getSearchKey() != null && criteria.getSearchValue() != null) {
            switch (criteria.getOperator()) {
                case EQUALS:
                    predicates.add(builder.equal(
                        root.get(criteria.getSearchKey()),
                        convertToTargetType(root, criteria.getSearchKey(), criteria.getSearchValue())
                    ));
                    break;
                case LIKE:
                    predicates.add(builder.like(
                        root.get(criteria.getSearchKey()),
                        "%" + criteria.getSearchValue().toString() + "%"
                    ));
                    break;
                // Add other cases as needed
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private Object convertToTargetType(Root<Notice> root, String attributeName, Object value) {
        Class<?> attributeType = root.get(attributeName).getJavaType();
        if (value == null) {
            return null;
        }

        if (attributeType.equals(String.class)) {
            return value.toString();
        } else if (attributeType.equals(Long.class)) {
            return Long.valueOf(value.toString());
        } else if (attributeType.equals(Boolean.class)) {
            return Boolean.valueOf(value.toString());
        }
        // Add other type conversions as needed

        return value;
    }
}
