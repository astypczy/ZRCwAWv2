package com.pwr.project.entities.search;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    private String searchKey;
    private Object searchValue;
    private Operator operator;
}
