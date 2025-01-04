package com.pwr.project.entities.search;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteriaRequest {
    private List<SearchCriteria> searchCriteria;
}
